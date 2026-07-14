# Finances API

API REST para gestão financeira pessoal, construída com **Ktor** (Kotlin) seguindo princípios de **Clean Architecture**.

## Stack

| Tecnologia | Versão |
|------------|--------|
| Kotlin | 2.1.10 |
| Ktor (Server) | 3.2.0 |
| Exposed (ORM) | 0.61.0 |
| PostgreSQL | - |
| Flyway (Migration) | 11.9.1 |
| Koin (DI) | 3.4.3 |
| JWT (Auth) | HMAC256 |
| BCrypt (Hashing) | jbcrypt 0.4 |
| Swagger/OpenAPI | ktor-openapi 5.1.0 |
| Test | JUnit 5 + MockK |
| Cobertura | JaCoCo 0.8.12 |

## Endpoints

| Método | Rota | Autenticação | Descrição |
|--------|------|--------------|-----------|
| POST | `/auth/register` | ❌ | Criar conta |
| POST | `/auth/login` | ❌ | Autenticar e obter JWT |
| GET | `/customers` | ✅ JWT | Dados do perfil |
| POST | `/transactions` | ✅ JWT | Criar transação(ões) |
| GET | `/transactions` | ✅ JWT | Histórico de transações |
| GET | `/swagger` | ❌ | Swagger UI |
| GET | `/api.json` | ❌ | Schema OpenAPI |

## Arquitetura

```
src/
├── api/          # DTOs, requests, rotas, exceções, validação
├── domain/       # Modelos e interfaces de repositório
├── data/         # Implementação dos repositórios (Exposed DAO), schemas de tabela
├── service/      # Regras de negócio
├── plugins/      # Configurações do Ktor (DI, DB, JWT, CORS, Swagger etc.)
└── util/         # Constantes, mappers, extensions, serializers
```

## Pré-requisitos

- JDK 21+
- PostgreSQL

## Configuração

As configurações são injetadas via variáveis de ambiente no `application.yaml`:

| Variável | Descrição |
|----------|-----------|
| `dbUrl` | URL de conexão do banco |
| `dbUser` | Usuário do banco |
| `dbPassword` | Senha do banco |
| `jwtSecret` | Segredo para assinar tokens JWT |
| `jwtIssuer` | Emissor do token |

## Executar

```bash
./gradlew run
```

O servidor inicia em `http://0.0.0.0:8080`.

## Testes

```bash
./gradlew test
```

Relatório de cobertura (JaCoCo) é gerado automaticamente após os testes em `build/reports/jacoco/`.

## Migrações

As migrações Flyway estão em `src/main/resources/db/migration/` e são executadas automaticamente na inicialização.