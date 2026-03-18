# FinTrack API - Back-end

Este repositório contém o código-fonte da API RESTful do **FinTrack**, um sistema de gerenciamento financeiro pessoal.

## Tecnologias Utilizadas

* **Java** (Linguagem principal)
* **Spring Boot** (Framework base para a API REST)
* **Spring Data JPA / Hibernate** (Mapeamento Objeto-Relacional e persistência de dados)
* **Maven** (Gerenciamento de dependências e build)
* **Banco de Dados:** [H2 em memória]

---

## Decisões Arquiteturais e Boas Práticas

Para garantir que o sistema seja escalável, testável e manutenível, as seguintes abordagens foram aplicadas:

* **Arquitetura em Camadas:** Separação clara de responsabilidades utilizando `Controllers` (camada de apresentação REST), `Services` (regras de negócio) e `Repositories` (acesso a dados).
* **Precisão Financeira:** Utilização da classe `BigDecimal` nativa do Java para todos os valores monetários (`amount`), evitando os clássicos problemas de arredondamento de ponto flutuante (`Double` ou `Float`) em sistemas financeiros.
* **Segurança e Integração (CORS):** Configuração controlada de *Cross-Origin Resource Sharing* (`@CrossOrigin`) diretamente na Controller, garantindo que o Front-end possa consumir os recursos de forma segura durante o desenvolvimento em portas distintas.

---

## 🔌 Endpoints da API

A API foi documentada e expõe os seguintes recursos a partir da rota base `/api/transactions`:

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/` | Retorna a lista de todas as transações cadastradas. |
| `GET` | `/{id}` | Busca os detalhes de uma transação específica pelo ID. |
| `GET` | `/balance` | Retorna o saldo total (Receitas subtraídas das Despesas). |
| `POST` | `/` | Cria uma nova transação. |
| `PUT` | `/{id}` | Atualiza integralmente os dados de uma transação existente. |
| `DELETE` | `/{id}` | Remove uma transação do sistema. |

---

## 🚀 Como executar o projeto localmente

1. Certifique-se de ter o **Java (JDK 25 ou superior)** e o **Maven** instalados em sua máquina.
2. Clone este repositório:
   ```bash
   git clone [https://github.com/seu-usuario/fintrack-api.git](https://github.com/seu-usuario/fintrack-api.git)
   ```
3. Navegue até o diretório raiz do projeto:
    ```bash
   cd fintrack-api
   ```
4. Compile e instale as dependências:
    ```bash
   mvn clean install
   ```
5. Inicie a aplicação Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
6. O servidor iniciará localmente na porta 8080.

## Autor
Feito por Arthur Sena.
