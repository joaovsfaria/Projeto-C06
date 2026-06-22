package br.inatel.frota;

import br.inatel.frota.dao.DependenteDAO;
import br.inatel.frota.dao.ManutencaoDAO;
import br.inatel.frota.dao.MotoristaDAO;
import br.inatel.frota.dao.OficinaDAO;
import br.inatel.frota.dao.RastreadorDAO;
import br.inatel.frota.dao.VeiculoDAO;
import br.inatel.frota.dao.ViagemDAO;
import br.inatel.frota.model.Dependente;
import br.inatel.frota.model.Manutencao;
import br.inatel.frota.model.Motorista;
import br.inatel.frota.model.Oficina;
import br.inatel.frota.model.Rastreador;
import br.inatel.frota.model.Veiculo;
import br.inatel.frota.model.Viagem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT_DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static final RastreadorDAO rastreadorDAO = new RastreadorDAO();
    private static final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private static final DependenteDAO dependenteDAO = new DependenteDAO();
    private static final ViagemDAO viagemDAO = new ViagemDAO();
    private static final OficinaDAO oficinaDAO = new OficinaDAO();
    private static final ManutencaoDAO manutencaoDAO = new ManutencaoDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n========= GESTAO DE FROTA =========");
            System.out.println("1 - Veiculos");
            System.out.println("2 - Rastreadores");
            System.out.println("3 - Motoristas");
            System.out.println("4 - Dependentes");
            System.out.println("5 - Viagens");
            System.out.println("6 - Oficinas");
            System.out.println("7 - Manutencoes");
            System.out.println("8 - Consultas com JOIN");
            System.out.println("0 - Sair");
            opcao = lerInt("Escolha: ");

            try {
                switch (opcao) {
                    case 1: menuVeiculo(); break;
                    case 2: menuRastreador(); break;
                    case 3: menuMotorista(); break;
                    case 4: menuDependente(); break;
                    case 5: menuViagem(); break;
                    case 6: menuOficina(); break;
                    case 7: menuManutencao(); break;
                    case 8: menuJoins(); break;
                    case 0: System.out.println("Encerrando..."); break;
                    default: System.out.println("Opcao invalida.");
                }
            } catch (RuntimeException e) {
                System.out.println(">> ERRO: " + traduzirErro(e));
            }
        } while (opcao != 0);
    }

    // ===================== VEICULO =====================
    private static void menuVeiculo() {
        int op;
        do {
            System.out.println("\n--- VEICULOS ---");
            System.out.println("1 - Listar | 2 - Buscar por placa | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(veiculoDAO.listar());
                    break;
                case 2: {
                    String placa = lerTexto("Placa: ");
                    Veiculo v = veiculoDAO.buscarPorPlaca(placa);
                    System.out.println(v != null ? v : "Nenhum veiculo encontrado.");
                    break;
                }
                case 3: {
                    Veiculo v = new Veiculo();
                    v.setPlaca(lerTexto("Placa: "));
                    v.setCapacidadeCarga(lerDouble("Capacidade de carga (kg): "));
                    veiculoDAO.inserir(v);
                    System.out.println("Veiculo inserido com id " + v.getIdVeiculo());
                    break;
                }
                case 4: {
                    Veiculo v = new Veiculo();
                    v.setIdVeiculo(lerInt("Id do veiculo a atualizar: "));
                    v.setPlaca(lerTexto("Nova placa: "));
                    v.setCapacidadeCarga(lerDouble("Nova capacidade (kg): "));
                    veiculoDAO.atualizar(v);
                    System.out.println("Veiculo atualizado.");
                    break;
                }
                case 5:
                    veiculoDAO.deletar(lerInt("Id do veiculo a deletar: "));
                    System.out.println("Veiculo deletado.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== RASTREADOR =====================
    private static void menuRastreador() {
        int op;
        do {
            System.out.println("\n--- RASTREADORES ---");
            System.out.println("1 - Listar | 2 - Buscar por numero de serie | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(rastreadorDAO.listar());
                    break;
                case 2: {
                    Rastreador r = rastreadorDAO.buscarPorNumeroSerie(lerTexto("Numero de serie: "));
                    System.out.println(r != null ? r : "Nenhum rastreador encontrado.");
                    break;
                }
                case 3: {
                    Rastreador r = new Rastreador();
                    Veiculo vId = new Veiculo();
                    vId.setIdVeiculo(lerInt("Id do veiculo: "));
                    r.setVeiculo(vId);
                    r.setNumeroSerie(lerTexto("Numero de serie: "));
                    r.setDataAtivacao(lerData("Data de ativacao (yyyy-MM-dd): "));
                    rastreadorDAO.inserir(r);
                    System.out.println("Rastreador inserido com id " + r.getIdRastreador());
                    break;
                }
                case 4: {
                    Rastreador r = new Rastreador();
                    r.setIdRastreador(lerInt("Id do rastreador a atualizar: "));
                    Veiculo vId = new Veiculo();
                    vId.setIdVeiculo(lerInt("Novo id do veiculo: "));
                    r.setVeiculo(vId);
                    r.setNumeroSerie(lerTexto("Novo numero de serie: "));
                    r.setDataAtivacao(lerData("Nova data de ativacao (yyyy-MM-dd): "));
                    rastreadorDAO.atualizar(r);
                    System.out.println("Rastreador atualizado.");
                    break;
                }
                case 5:
                    rastreadorDAO.deletar(lerInt("Id do rastreador a deletar: "));
                    System.out.println("Rastreador deletado.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== MOTORISTA =====================
    private static void menuMotorista() {
        int op;
        do {
            System.out.println("\n--- MOTORISTAS ---");
            System.out.println("1 - Listar | 2 - Buscar por nome | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(motoristaDAO.listar());
                    break;
                case 2:
                    imprimirLista(motoristaDAO.buscarPorNome(lerTexto("Nome (ou parte): ")));
                    break;
                case 3: {
                    Motorista m = new Motorista();
                    m.setNome(lerTexto("Nome: "));
                    m.setCpf(lerTexto("CPF: "));
                    m.setDataNascimento(lerData("Data de nascimento (yyyy-MM-dd): "));
                    m.setStatusAtivo(lerBoolean("Ativo? (s/n): "));
                    motoristaDAO.inserir(m);
                    System.out.println("Motorista inserido com id " + m.getIdMotorista());
                    break;
                }
                case 4: {
                    Motorista m = new Motorista();
                    m.setIdMotorista(lerInt("Id do motorista a atualizar: "));
                    m.setNome(lerTexto("Novo nome: "));
                    m.setCpf(lerTexto("Novo CPF: "));
                    m.setDataNascimento(lerData("Nova data de nascimento (yyyy-MM-dd): "));
                    m.setStatusAtivo(lerBoolean("Ativo? (s/n): "));
                    motoristaDAO.atualizar(m);
                    System.out.println("Motorista atualizado.");
                    break;
                }
                case 5:
                    motoristaDAO.deletar(lerInt("Id do motorista a deletar: "));
                    System.out.println("Motorista deletado.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== DEPENDENTE =====================
    private static void menuDependente() {
        int op;
        do {
            System.out.println("\n--- DEPENDENTES ---");
            System.out.println("1 - Listar | 2 - Buscar por motorista | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(dependenteDAO.listar());
                    break;
                case 2:
                    imprimirLista(dependenteDAO.buscarPorMotorista(lerInt("Id do motorista: ")));
                    break;
                case 3: {
                    Dependente d = new Dependente();
                    Motorista mId = new Motorista();
                    mId.setIdMotorista(lerInt("Id do motorista responsavel: "));
                    d.setMotorista(mId);
                    d.setNome(lerTexto("Nome do dependente: "));
                    dependenteDAO.inserir(d);
                    System.out.println("Dependente inserido.");
                    break;
                }
                case 4: {
                    Dependente d = new Dependente();
                    d.setIdDependente(lerInt("Id do dependente: "));
                    Motorista mId = new Motorista();
                    mId.setIdMotorista(lerInt("Id do motorista: "));
                    d.setMotorista(mId);
                    d.setNome(lerTexto("Novo nome do dependente: "));
                    dependenteDAO.atualizar(d);
                    System.out.println("Dependente atualizado.");
                    break;
                }
                case 5:
                    dependenteDAO.deletar(lerInt("Id do dependente a deletar: "));
                    System.out.println("Dependente deletado.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== VIAGEM =====================
    private static void menuViagem() {
        int op;
        do {
            System.out.println("\n--- VIAGENS ---");
            System.out.println("1 - Listar | 2 - Buscar por destino | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(viagemDAO.listar());
                    break;
                case 2:
                    imprimirLista(viagemDAO.buscarPorDestino(lerTexto("Destino (ou parte): ")));
                    break;
                case 3: {
                    Viagem v = new Viagem();
                    Veiculo veic = new Veiculo();
                    veic.setIdVeiculo(lerInt("Id do veiculo: "));
                    v.setVeiculo(veic);
                    Motorista mot = new Motorista();
                    mot.setIdMotorista(lerInt("Id do motorista: "));
                    v.setMotorista(mot);
                    v.setDataHoraSaida(lerDataHora("Data/hora de saida (yyyy-MM-dd HH:mm:ss): "));
                    v.setDestino(lerTexto("Destino: "));
                    viagemDAO.inserir(v);
                    System.out.println("Viagem inserida com id " + v.getIdViagem());
                    break;
                }
                case 4: {
                    Viagem v = new Viagem();
                    v.setIdViagem(lerInt("Id da viagem a atualizar: "));
                    Veiculo veic = new Veiculo();
                    veic.setIdVeiculo(lerInt("Novo id do veiculo: "));
                    v.setVeiculo(veic);
                    Motorista mot = new Motorista();
                    mot.setIdMotorista(lerInt("Novo id do motorista: "));
                    v.setMotorista(mot);
                    v.setDataHoraSaida(lerDataHora("Nova data/hora (yyyy-MM-dd HH:mm:ss): "));
                    v.setDestino(lerTexto("Novo destino: "));
                    viagemDAO.atualizar(v);
                    System.out.println("Viagem atualizada.");
                    break;
                }
                case 5:
                    viagemDAO.deletar(lerInt("Id da viagem a deletar: "));
                    System.out.println("Viagem deletada.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== OFICINA =====================
    private static void menuOficina() {
        int op;
        do {
            System.out.println("\n--- OFICINAS ---");
            System.out.println("1 - Listar | 2 - Buscar por CNPJ | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(oficinaDAO.listar());
                    break;
                case 2: {
                    Oficina o = oficinaDAO.buscarPorCnpj(lerTexto("CNPJ: "));
                    System.out.println(o != null ? o : "Nenhuma oficina encontrada.");
                    break;
                }
                case 3: {
                    Oficina o = new Oficina();
                    o.setCnpj(lerTexto("CNPJ: "));
                    o.setRua(lerTexto("Rua: "));
                    o.setNumero(lerTexto("Numero: "));
                    o.setBairro(lerTexto("Bairro: "));
                    o.setCep(lerTexto("CEP: "));
                    oficinaDAO.inserir(o);
                    System.out.println("Oficina inserida com id " + o.getIdOficina());
                    break;
                }
                case 4: {
                    Oficina o = new Oficina();
                    o.setIdOficina(lerInt("Id da oficina a atualizar: "));
                    o.setCnpj(lerTexto("Novo CNPJ: "));
                    o.setRua(lerTexto("Nova rua: "));
                    o.setNumero(lerTexto("Novo numero: "));
                    o.setBairro(lerTexto("Novo bairro: "));
                    o.setCep(lerTexto("Novo CEP: "));
                    oficinaDAO.atualizar(o);
                    System.out.println("Oficina atualizada.");
                    break;
                }
                case 5:
                    oficinaDAO.deletar(lerInt("Id da oficina a deletar: "));
                    System.out.println("Oficina deletada.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== MANUTENCAO =====================
    private static void menuManutencao() {
        int op;
        do {
            System.out.println("\n--- MANUTENCOES ---");
            System.out.println("1 - Listar | 2 - Buscar por veiculo | 3 - Inserir | 4 - Atualizar | 5 - Deletar | 0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1:
                    imprimirLista(manutencaoDAO.listar());
                    break;
                case 2:
                    imprimirLista(manutencaoDAO.buscarPorVeiculo(lerInt("Id do veiculo: ")));
                    break;
                case 3: {
                    Manutencao m = new Manutencao();
                    Veiculo veic = new Veiculo();
                    veic.setIdVeiculo(lerInt("Id do veiculo: "));
                    m.setVeiculo(veic);
                    Oficina ofi = new Oficina();
                    ofi.setIdOficina(lerInt("Id da oficina: "));
                    m.setOficina(ofi);
                    m.setDataServico(lerData("Data do servico (yyyy-MM-dd): "));
                    m.setCustoTotal(lerDouble("Custo total (R$): "));
                    manutencaoDAO.inserir(m);
                    System.out.println("Manutencao inserida com id " + m.getIdManutencao());
                    break;
                }
                case 4: {
                    Manutencao m = new Manutencao();
                    m.setIdManutencao(lerInt("Id da manutencao a atualizar: "));
                    Veiculo veic = new Veiculo();
                    veic.setIdVeiculo(lerInt("Novo id do veiculo: "));
                    m.setVeiculo(veic);
                    Oficina ofi = new Oficina();
                    ofi.setIdOficina(lerInt("Novo id da oficina: "));
                    m.setOficina(ofi);
                    m.setDataServico(lerData("Nova data (yyyy-MM-dd): "));
                    m.setCustoTotal(lerDouble("Novo custo (R$): "));
                    manutencaoDAO.atualizar(m);
                    System.out.println("Manutencao atualizada.");
                    break;
                }
                case 5:
                    manutencaoDAO.deletar(lerInt("Id da manutencao a deletar: "));
                    System.out.println("Manutencao deletada.");
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== CONSULTAS COM JOIN =====================
    private static void menuJoins() {
        int op;
        do {
            System.out.println("\n--- CONSULTAS COM JOIN ---");
            System.out.println("1 - Viagens (Veiculo + Motorista)");
            System.out.println("2 - Manutencoes (Veiculo + Oficina)");
            System.out.println("3 - Dependentes (Motorista)");
            System.out.println("4 - Rastreadores (Veiculo)");
            System.out.println("0 - Voltar");
            op = lerInt("Escolha: ");
            switch (op) {
                case 1: imprimirLista(viagemDAO.listarViagensCompletas()); break;
                case 2: imprimirLista(manutencaoDAO.listarManutencoesCompletas()); break;
                case 3: imprimirLista(dependenteDAO.listarComMotorista()); break;
                case 4: imprimirLista(rastreadorDAO.listarComVeiculo()); break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (op != 0);
    }

    // ===================== TRADUCAO DE ERROS =====================
    private static String traduzirErro(RuntimeException e) {
        String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
        if (msg.contains("foreign key constraint fails")) {
            if (msg.contains("delete") || msg.contains("parent row")) {
                return "Nao e possivel deletar: existem outros registros vinculados a ele "
                     + "(viagens, manutencoes, etc). Remova primeiro os registros dependentes.";
            }
            return "Operacao invalida: o id informado nao existe em outra tabela "
                 + "(verifique se o veiculo/motorista/oficina realmente existe).";
        }
        if (msg.contains("duplicate entry")) {
            return "Ja existe um registro com esse valor unico (placa, CPF, CNPJ ou numero de serie repetido).";
        }
        if (msg.contains("cannot be null") || msg.contains("doesn't have a default value")) {
            return "Campo obrigatorio nao preenchido.";
        }
        return e.getMessage();
    }

    // ===================== HELPERS DE ENTRADA/SAIDA =====================
    private static void imprimirLista(List<?> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("(nenhum registro encontrado)");
            return;
        }
        for (Object item : lista) {
            System.out.println(item);
        }
        System.out.println("Total: " + lista.size() + " registro(s).");
    }

    private static String lerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private static double lerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            String linha = sc.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido (ex: 1500.50).");
            }
        }
    }

    private static boolean lerBoolean(String msg) {
        String resp = lerTexto(msg).toLowerCase();
        return resp.startsWith("s") || resp.equals("true") || resp.equals("1");
    }

    private static LocalDate lerData(String msg) {
        while (true) {
            try {
                return LocalDate.parse(lerTexto(msg));
            } catch (Exception e) {
                System.out.println("Data invalida. Use o formato yyyy-MM-dd (ex: 2026-05-20).");
            }
        }
    }

    private static LocalDateTime lerDataHora(String msg) {
        while (true) {
            try {
                return LocalDateTime.parse(lerTexto(msg), FMT_DATA_HORA);
            } catch (Exception e) {
                System.out.println("Data/hora invalida. Use yyyy-MM-dd HH:mm:ss (ex: 2026-05-20 08:30:00).");
            }
        }
    }
}
