package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Dependente;
import br.inatel.frota.model.Motorista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DependenteDAO implements IDAO<Dependente> {

    @Override
    public void inserir(Dependente d) {
        String sql = "INSERT INTO Dependente (id_dependente, id_motorista, nome_dependente) "
                   + "VALUES ((SELECT COALESCE(MAX(d2.id_dependente), 0) + 1 "
                   + "         FROM Dependente d2 WHERE d2.id_motorista = ?), ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getMotorista().getIdMotorista());
            ps.setInt(2, d.getMotorista().getIdMotorista());
            ps.setString(3, d.getNome());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dependente: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Dependente d) {
        String sql = "UPDATE Dependente SET nome_dependente = ? WHERE id_dependente = ? AND id_motorista = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNome());
            ps.setInt(2, d.getIdDependente());
            ps.setInt(3, d.getMotorista().getIdMotorista());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dependente: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int idDependente) {
        String sql = "DELETE FROM Dependente WHERE id_dependente = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDependente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar dependente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Dependente> listar() {
        String sql = "SELECT id_dependente, id_motorista, nome_dependente FROM Dependente ORDER BY id_motorista, id_dependente";
        List<Dependente> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar dependentes: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Dependente> buscarPorMotorista(int idMotorista) {
        String sql = "SELECT id_dependente, id_motorista, nome_dependente FROM Dependente WHERE id_motorista = ? ORDER BY id_dependente";
        List<Dependente> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMotorista);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dependentes do motorista: " + e.getMessage(), e);
        }
        return lista;
    }

    // JOIN
    // lista cada dependente junto do nome do motorista responsavel
    public List<String> listarComMotorista() {
        String sql =
                "SELECT d.id_dependente, d.nome_dependente, m.nome AS nome_motorista "
              + "FROM Dependente d "
              + "INNER JOIN Motorista m ON d.id_motorista = m.id_motorista "
              + "ORDER BY m.nome, d.id_dependente";
        List<String> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(String.format("Dependente: %s  ->  Motorista: %s",
                        rs.getString("nome_dependente"),
                        rs.getString("nome_motorista")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro no JOIN dependente/motorista: " + e.getMessage(), e);
        }
        return lista;
    }

    private Dependente mapear(ResultSet rs) throws SQLException {
        Motorista motorista = new Motorista();
        motorista.setIdMotorista(rs.getInt("id_motorista"));
        return new Dependente(
                rs.getInt("id_dependente"),
                motorista,
                rs.getString("nome_dependente"));
    }
}
