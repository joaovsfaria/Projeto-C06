package br.inatel.frota.dao;

import br.inatel.frota.db.Conexao;
import br.inatel.frota.model.Caminhao;
import br.inatel.frota.model.Van;
import br.inatel.frota.model.Veiculo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements IDAO<Veiculo> {

    @Override
    public void inserir(Veiculo v) {
        try (Connection con = Conexao.conectar()) {
            boolean temCamposHeranca = tabelaTemCamposHeranca(con);
            String sql = temCamposHeranca
                    ? "INSERT INTO Veiculo (placa, capacidade_carga, tipo_veiculo, quantidade_eixos, quantidade_passageiros) VALUES (?, ?, ?, ?, ?)"
                    : "INSERT INTO Veiculo (placa, capacidade_carga) VALUES (?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, v.getPlaca());
                ps.setDouble(2, v.getCapacidadeCarga());

                if (temCamposHeranca) {
                    ps.setString(3, getTipoBanco(v));
                    preencherDadosEspecificos(ps, v, 4, 5);
                }

                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        v.setIdVeiculo(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir veiculo: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Veiculo v) {
        try (Connection con = Conexao.conectar()) {
            boolean temCamposHeranca = tabelaTemCamposHeranca(con);
            String sql = temCamposHeranca
                    ? "UPDATE Veiculo SET placa = ?, capacidade_carga = ?, tipo_veiculo = ?, quantidade_eixos = ?, quantidade_passageiros = ? WHERE id_veiculo = ?"
                    : "UPDATE Veiculo SET placa = ?, capacidade_carga = ? WHERE id_veiculo = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, v.getPlaca());
                ps.setDouble(2, v.getCapacidadeCarga());

                if (temCamposHeranca) {
                    ps.setString(3, getTipoBanco(v));
                    preencherDadosEspecificos(ps, v, 4, 5);
                    ps.setInt(6, v.getIdVeiculo());
                } else {
                    ps.setInt(3, v.getIdVeiculo());
                }

                ps.executeUpdate();
            }
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
        String sql = "SELECT * FROM Veiculo ORDER BY id_veiculo";
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
        String sql = "SELECT * FROM Veiculo WHERE placa = ?";
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

    public Veiculo buscarPorId(int idVeiculo) {
        String sql = "SELECT * FROM Veiculo WHERE id_veiculo = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVeiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo por id: " + e.getMessage(), e);
        }
        return null;
    }

    private Veiculo mapear(ResultSet rs) throws SQLException {
        int idVeiculo = rs.getInt("id_veiculo");
        String placa = rs.getString("placa");
        double capacidadeCarga = rs.getDouble("capacidade_carga");
        String tipo = temColuna(rs, "tipo_veiculo") ? rs.getString("tipo_veiculo") : "GENERICO";

        if ("CAMINHAO".equalsIgnoreCase(tipo)) {
            int quantidadeEixos = temColuna(rs, "quantidade_eixos") ? rs.getInt("quantidade_eixos") : 0;
            return new Caminhao(idVeiculo, placa, capacidadeCarga, quantidadeEixos);
        }
        if ("VAN".equalsIgnoreCase(tipo)) {
            int quantidadePassageiros = temColuna(rs, "quantidade_passageiros") ? rs.getInt("quantidade_passageiros") : 0;
            return new Van(idVeiculo, placa, capacidadeCarga, quantidadePassageiros);
        }
        return new Veiculo(idVeiculo, placa, capacidadeCarga);
    }

    private String getTipoBanco(Veiculo v) {
        if (v instanceof Caminhao) {
            return "CAMINHAO";
        }
        if (v instanceof Van) {
            return "VAN";
        }
        return "GENERICO";
    }

    private void preencherDadosEspecificos(PreparedStatement ps, Veiculo v, int indiceEixos, int indicePassageiros)
            throws SQLException {
        if (v instanceof Caminhao) {
            ps.setInt(indiceEixos, ((Caminhao) v).getQuantidadeEixos());
        } else {
            ps.setNull(indiceEixos, java.sql.Types.INTEGER);
        }

        if (v instanceof Van) {
            ps.setInt(indicePassageiros, ((Van) v).getQuantidadePassageiros());
        } else {
            ps.setNull(indicePassageiros, java.sql.Types.INTEGER);
        }
    }

    private boolean tabelaTemCamposHeranca(Connection con) throws SQLException {
        DatabaseMetaData metaData = con.getMetaData();
        try (ResultSet rs = metaData.getColumns(con.getCatalog(), null, "Veiculo", "tipo_veiculo")) {
            if (rs.next()) {
                return true;
            }
        }
        try (ResultSet rs = metaData.getColumns(con.getCatalog(), null, "veiculo", "tipo_veiculo")) {
            return rs.next();
        }
    }

    private boolean temColuna(ResultSet rs, String nomeColuna) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (nomeColuna.equalsIgnoreCase(metaData.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }
}
