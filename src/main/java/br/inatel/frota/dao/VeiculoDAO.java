package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements IDAO<Veiculo> {

    @Override
    public void inserir(Veiculo v) {
        String sql = "INSERT INTO Veiculo (placa, capacidade_carga) VALUES (?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getPlaca());
            ps.setDouble(2, v.getCapacidadeCarga());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setIdVeiculo(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir veiculo: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Veiculo v) {
        String sql = "UPDATE Veiculo SET placa = ?, capacidade_carga = ? WHERE id_veiculo = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getPlaca());
            ps.setDouble(2, v.getCapacidadeCarga());
            ps.setInt(3, v.getIdVeiculo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veiculo: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int idVeiculo) {
        String sql = "DELETE FROM Veiculo WHERE id_veiculo = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVeiculo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veiculo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Veiculo> listar() {
        String sql = "SELECT id_veiculo, placa, capacidade_carga FROM Veiculo ORDER BY id_veiculo";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veiculos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT id_veiculo, placa, capacidade_carga FROM Veiculo WHERE placa = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo por placa: " + e.getMessage(), e);
        }
        return null;
    }

    private Veiculo mapear(ResultSet rs) throws SQLException {
        return new Veiculo(
                rs.getInt("id_veiculo"),
                rs.getString("placa"),
                rs.getDouble("capacidade_carga"));
    }
}
