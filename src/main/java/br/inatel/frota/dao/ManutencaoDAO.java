package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Manutencao;
import br.inatel.frota.model.Oficina;
import br.inatel.frota.model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoDAO implements IDAO<Manutencao> {

    @Override
    public void inserir(Manutencao m) {
        String sql = "INSERT INTO Manutencao (id_veiculo, id_oficina, data_servico, custo_total) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getVeiculo().getIdVeiculo());
            ps.setInt(2, m.getOficina().getIdOficina());
            ps.setObject(3, m.getDataServico());
            ps.setDouble(4, m.getCustoTotal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setIdManutencao(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir manutencao: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Manutencao m) {
        String sql = "UPDATE Manutencao SET id_veiculo = ?, id_oficina = ?, data_servico = ?, custo_total = ? WHERE id_manutencao = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, m.getVeiculo().getIdVeiculo());
            ps.setInt(2, m.getOficina().getIdOficina());
            ps.setObject(3, m.getDataServico());
            ps.setDouble(4, m.getCustoTotal());
            ps.setInt(5, m.getIdManutencao());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar manutencao: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int idManutencao) {
        String sql = "DELETE FROM Manutencao WHERE id_manutencao = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idManutencao);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar manutencao: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Manutencao> listar() {
        String sql = "SELECT id_manutencao, id_veiculo, id_oficina, data_servico, custo_total FROM Manutencao ORDER BY id_manutencao";
        List<Manutencao> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar manutencoes: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Manutencao> buscarPorVeiculo(int idVeiculo) {
        String sql = "SELECT id_manutencao, id_veiculo, id_oficina, data_servico, custo_total FROM Manutencao WHERE id_veiculo = ? ORDER BY data_servico";
        List<Manutencao> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVeiculo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar manutencoes do veiculo: " + e.getMessage(), e);
        }
        return lista;
    }

    // JOIN
    // mostra cada manutencao com a placa do veiculo e o CNPJ da oficina
    public List<String> listarManutencoesCompletas() {
        String sql =
                "SELECT mn.id_manutencao, mn.data_servico, mn.custo_total, v.placa, o.cnpj "
              + "FROM Manutencao mn "
              + "INNER JOIN Veiculo v ON mn.id_veiculo = v.id_veiculo "
              + "INNER JOIN Oficina o ON mn.id_oficina = o.id_oficina "
              + "ORDER BY mn.data_servico";
        List<String> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(String.format("Manutencao #%d | %s | Custo: R$ %.2f | Veiculo: %s | Oficina CNPJ: %s",
                        rs.getInt("id_manutencao"),
                        rs.getObject("data_servico", LocalDate.class),
                        rs.getDouble("custo_total"),
                        rs.getString("placa"),
                        rs.getString("cnpj")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro no JOIN manutencao/veiculo/oficina: " + e.getMessage(), e);
        }
        return lista;
    }

    private Manutencao mapear(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(rs.getInt("id_veiculo"));
        Oficina oficina = new Oficina();
        oficina.setIdOficina(rs.getInt("id_oficina"));

        return new Manutencao(
                rs.getInt("id_manutencao"),
                veiculo,
                oficina,
                rs.getObject("data_servico", LocalDate.class),
                rs.getDouble("custo_total"));
    }
}
