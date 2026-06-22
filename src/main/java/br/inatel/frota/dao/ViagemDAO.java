package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Motorista;
import br.inatel.frota.model.Veiculo;
import br.inatel.frota.model.Viagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ViagemDAO implements IDAO<Viagem> {

    @Override
    public void inserir(Viagem v) {
        String sql = "INSERT INTO Viagem (id_veiculo, id_motorista, data_hora_saida, destino) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, v.getVeiculo().getIdVeiculo());
            ps.setInt(2, v.getMotorista().getIdMotorista());
            ps.setObject(3, v.getDataHoraSaida());
            ps.setString(4, v.getDestino());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setIdViagem(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir viagem: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Viagem v) {
        String sql = "UPDATE Viagem SET id_veiculo = ?, id_motorista = ?, data_hora_saida = ?, destino = ? WHERE id_viagem = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, v.getVeiculo().getIdVeiculo());
            ps.setInt(2, v.getMotorista().getIdMotorista());
            ps.setObject(3, v.getDataHoraSaida());
            ps.setString(4, v.getDestino());
            ps.setInt(5, v.getIdViagem());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar viagem: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int idViagem) {
        String sql = "DELETE FROM Viagem WHERE id_viagem = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idViagem);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar viagem: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Viagem> listar() {
        String sql = "SELECT id_viagem, id_veiculo, id_motorista, data_hora_saida, destino FROM Viagem ORDER BY id_viagem";
        List<Viagem> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar viagens: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Viagem> buscarPorDestino(String destino) {
        String sql = "SELECT id_viagem, id_veiculo, id_motorista, data_hora_saida, destino FROM Viagem WHERE destino LIKE ? ORDER BY data_hora_saida";
        List<Viagem> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + destino + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar viagem por destino: " + e.getMessage(), e);
        }
        return lista;
    }

    // JOIN
    // mostra cada viagem com a placa do veiculo e o nome do motorista
    public List<String> listarViagensCompletas() {
        String sql =
                "SELECT vi.id_viagem, vi.data_hora_saida, vi.destino, v.placa, m.nome "
              + "FROM Viagem vi "
              + "INNER JOIN Veiculo v ON vi.id_veiculo = v.id_veiculo "
              + "INNER JOIN Motorista m ON vi.id_motorista = m.id_motorista "
              + "ORDER BY vi.data_hora_saida";
        List<String> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(String.format("Viagem #%d | %s | Destino: %s | Veiculo: %s | Motorista: %s",
                        rs.getInt("id_viagem"),
                        rs.getObject("data_hora_saida", LocalDateTime.class),
                        rs.getString("destino"),
                        rs.getString("placa"),
                        rs.getString("nome")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro no JOIN viagem/veiculo/motorista: " + e.getMessage(), e);
        }
        return lista;
    }

    private Viagem mapear(ResultSet rs) throws SQLException {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        Veiculo veiculo = veiculoDAO.buscarPorId(rs.getInt("id_veiculo"));
        Motorista motorista = new Motorista();
        motorista.setIdMotorista(rs.getInt("id_motorista"));

        return new Viagem(
                rs.getInt("id_viagem"),
                veiculo,
                motorista,
                rs.getObject("data_hora_saida", LocalDateTime.class),
                rs.getString("destino"));
    }
}
