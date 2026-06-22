package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Motorista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDAO implements IDAO<Motorista> {

    @Override
    public void inserir(Motorista m) {
        String sql = "INSERT INTO Motorista (nome, cpf, data_nascimento, status_ativo) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getCpf());
            ps.setObject(3, m.getDataNascimento());
            ps.setBoolean(4, m.isStatusAtivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setIdMotorista(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir motorista: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Motorista m) {
        String sql = "UPDATE Motorista SET nome = ?, cpf = ?, data_nascimento = ?, status_ativo = ? WHERE id_motorista = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getCpf());
            ps.setObject(3, m.getDataNascimento());
            ps.setBoolean(4, m.isStatusAtivo());
            ps.setInt(5, m.getIdMotorista());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar motorista: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int idMotorista) {
        String sql = "DELETE FROM Motorista WHERE id_motorista = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMotorista);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar motorista: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Motorista> listar() {
        String sql = "SELECT id_motorista, nome, cpf, data_nascimento, status_ativo FROM Motorista ORDER BY id_motorista";
        List<Motorista> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Motorista> buscarPorNome(String nome) {
        String sql = "SELECT id_motorista, nome, cpf, data_nascimento, status_ativo FROM Motorista WHERE nome LIKE ? ORDER BY nome";
        List<Motorista> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar motorista por nome: " + e.getMessage(), e);
        }
        return lista;
    }

    private Motorista mapear(ResultSet rs) throws SQLException {
        return new Motorista(
                rs.getInt("id_motorista"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getObject("data_nascimento", LocalDate.class),
                rs.getBoolean("status_ativo"));
    }
}
