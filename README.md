# Projeto-C06 — Sistema de Gestão de Frota

Projeto final da disciplina **C06 - Programação Orientada a Objetos (INATEL)**.

O sistema foi desenvolvido em **Java** com forte ênfase nos pilares da **Programação Orientada a Objetos (POO)**:
- **Herança e Polimorfismo**: Uso de classes base para especialização de comportamentos (ex: classe abstrata `Pessoa` estendida por `Motorista` e `Dependente`).
- **Encapsulamento**: Proteção dos dados sensíveis através de modificadores de acesso (`private`, `protected`) e métodos `getters`/`setters`.
- **Modelagem Orientada a Objetos**: Mapeamento das entidades do sistema de frota (Veículo, Viagem, Rastreador, Manutenção) para objetos Java, separando suas responsabilidades.
- **Padrão DAO (Data Access Object)**: Desacoplamento da lógica orientada a objetos da lógica de persistência de dados.

O projeto conta também com integração a um banco **MySQL** via **JDBC** e um menu interativo no console para fazer o **CRUD completo** (Inserts, Updates, Deletes e Selects) em todas as tabelas e entidades.

---

## Pré-requisitos

| Ferramenta | Observação |
|---|---|
| **MySQL Server** rodando | em `localhost:3306` |
| **MySQL Workbench** | para criar o banco e acompanhar as tabelas |
| **IntelliJ IDEA** | já vem com **JDK** e **Maven** embutidos (não precisa instalar à parte) |

> O driver JDBC (`mysql-connector-j`) **não precisa ser baixado à mão** — o Maven
> baixa sozinho a partir do `pom.xml`.

---

## Passo 1 — Criar o banco de dados

1. Abra o **MySQL Workbench** e conecte no seu servidor.
2. Abra o arquivo [`gestao_frota.sql`](gestao_frota.sql).
3. Execute o script inteiro.

Isso cria o banco `gestao_frota` com as tabelas, dados iniciais, a view, a
procedure, a trigger e os usuários de acesso (`user_caua` e `user_luiz`).

---

## Passo 2 — Abrir o projeto no IntelliJ

1. **File → Open** e selecione a **pasta do projeto** (a que contém o `pom.xml`).
2. O IntelliJ detecta que é um projeto **Maven** e começa a baixar as
   dependências automaticamente (o driver MySQL). Aguarde terminar — aparece uma
   barrinha de progresso no rodapé.
3. Se ele perguntar, clique em **"Load Maven Project"** / **"Trust Project"**.

> Se as dependências não baixarem sozinhas: abra a aba **Maven** (lado direito)
> e clique no botão de **Reload** (🔄).

---

## Passo 3 — Conferir os dados de conexão

A conexão fica centralizada em
[`Conexao.java`](src/main/java/br/inatel/frota/db/Conexao.java):

```
URL:     jdbc:mysql://localhost:3306/gestao_frota
Usuario: user_caua
Senha:   CauaGrupo3!
```

Se o seu MySQL usar outro host, porta, usuário ou senha, **altere apenas esse
arquivo** — todos os DAOs usam ele.

---

## Passo 4 — Rodar a aplicação

Abra [`Main.java`](src/main/java/br/inatel/frota/Main.java) e clique no
**▶ (play) verde** ao lado do método `main` → **Run 'Main'**.

O menu aparece no terminal embutido do IntelliJ:

```
========= GESTAO DE FROTA =========
1 - Veiculos
2 - Rastreadores
3 - Motoristas
4 - Dependentes
5 - Viagens
6 - Oficinas
7 - Manutencoes
8 - Consultas com JOIN
0 - Sair
```

> **Alternativa por terminal** (se tiver Maven instalado no PATH):
> ```bash
> mvn compile
> mvn exec:java
> ```

---

## Passo 5 — Ver as mudanças no banco

Para acompanhar os Inserts/Updates/Deletes feitos pelo menu Java acontecendo no
banco em tempo real:

1. No **MySQL Workbench**, vá até o final do [`gestao_frota.sql`](gestao_frota.sql),
   onde estão os `SELECT * FROM` de todas as tabelas.
2. Após cada operação feita no menu, **reexecute esse bloco** (selecione e
   `Ctrl+Shift+Enter`) para ver a tabela atualizada.

---

## Funcionalidades

Para **cada tabela** (Veículo, Rastreador, Motorista, Dependente, Viagem,
Oficina, Manutenção):

- **Inserir** (INSERT), **Atualizar** (UPDATE), **Deletar** (DELETE), **Listar** (SELECT)
- **Buscar por atributo**: placa, nº de série, nome, motorista, destino, CNPJ, veículo

### Consultas com JOIN (opção 8 do menu)

1. **Viagens** → `Viagem` + `Veiculo` + `Motorista`
2. **Manutenções** → `Manutencao` + `Veiculo` + `Oficina`
3. **Dependentes** → `Dependente` + `Motorista`
4. **Rastreadores** → `Rastreador` + `Veiculo`