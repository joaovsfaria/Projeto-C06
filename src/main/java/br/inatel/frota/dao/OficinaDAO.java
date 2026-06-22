package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Oficina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OficinaDAO implements IDAO<Oficina> {

    @Override
    public void inserir(Oficina o) {
        String sql = "INSERT INTO Oficina (cnpj, rua, numero, bairro, cep) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getCnpj());
            ps.setString(2, o.getRua());
            ps.setString(3, o.getNumero());
            ps.setString(4, o.getBairro());
            ps.setString(5, o.getCep());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    o.setIdOficina(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir oficina: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Oficina o) {
        String sql = "UPDATE Oficina SET cnpj = ?, rua = ?, numero = ?, bairro = ?, cep = ? WHERE id_oficina = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getCnpj());
            ps.setString(2, o.getRua());
            ps.setString(3, o.getNumero());
            ps.setString(4, o.getBairro());
            ps.setString(5, o.getCep());
            ps.setInt(6, o.getIdOficina());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar oficina: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int idOficina) {
        String sql = "DELETE FROM Oficina WHERE id_oficina = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOficina);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar oficina: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Oficina> listar() {
        String sql = "SELECT id_oficina, cnpj, rua, numero, bairro, cep FROM Oficina ORDER BY id_oficina";
        List<Oficina> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar oficinas: " + e.getMessage(), e);
        }
        return lista;
    }

    public Oficina buscarPorCnpj(String cnpj) {
        String sql = "SELECT id_oficina, cnpj, rua, numero, bairro, cep FROM Oficina WHERE cnpj = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cnpj);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar oficina por CNPJ: " + e.getMessage(), e);
        }
        return null;
    }

    private Oficina mapear(ResultSet rs) throws SQLException {
        return new Oficina(
                rs.getInt("id_oficina"),
                rs.getString("cnpj"),
                rs.getString("rua"),
                rs.getString("numero"),
                rs.getString("bairro"),
                rs.getString("cep"));
    }
}
