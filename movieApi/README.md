# 🎬 Movie API

API REST desenvolvida em Spring Boot para gerenciamento de filmes e listas pessoais de usuários, com integração à API externa do **The Movie Database (TMDB)**.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Cloud OpenFeign** - Integração com API externa
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória
- **Flyway** - Versionamento de banco de dados
- **Lombok** - Redução de boilerplate
- **Bean Validation** - Validação de dados

## ✨ Funcionalidades

### 🎯 Principais Destaques

- ✅ **Integração com API Externa (TMDB)**
  - Busca de filmes em tempo real
  - Obtenção de detalhes completos de filmes
  - Autenticação via Bearer Token
  - Cache local de filmes consultados

- ✅ **Gerenciamento de Usuários**
  - CRUD completo de usuários
  - Validação de email e username únicos

- ✅ **Listas Pessoais de Filmes**
  - Adicionar filmes à lista pessoal
  - Gerenciar status de assistência (WATCHLIST, WATCHING, COMPLETED, DROPPED)
  - Visualizar filmes da lista

## 🔧 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- Chave de API do TMDB ([obter aqui](https://www.themoviedb.org/settings/api))

## 📦 Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd movieApi
```

2. Configure a variável de ambiente com sua chave da API TMDB:
```bash
export TMDB_API_TOKEN=sua_chave_api_aqui
```

Ou configure diretamente no arquivo `application.yaml` (não recomendado para produção).

3. Execute o projeto:
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## 📚 Endpoints da API

### 🎥 Filmes (Integração com TMDB)

#### Buscar Filmes
```http
GET /movies?query={nome_filme}&lang={idioma}
```
**Exemplo:**
```bash
GET /movies?query=avatar&lang=pt-BR
```

#### Buscar Filme por ID
```http
GET /movies/{id}
```
**Exemplo:**
```bash
GET /movies/550
```

### 👤 Usuários

#### Listar Todos os Usuários
```http
GET /users
```

#### Criar Usuário
```http
POST /users
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "senha123"
}
```

#### Atualizar Usuário
```http
PUT /users/{id}
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "novaSenha123"
}
```

#### Deletar Usuário
```http
DELETE /users/{id}
```

### 📋 Listas Pessoais

#### Adicionar Filme à Lista
```http
POST /my-list?userId={user_id}&tmdbId={tmdb_id}&status={status}
```
**Status disponíveis:** `WATCHLIST`, `WATCHING`, `COMPLETED`, `DROPPED`

**Exemplo:**
```bash
POST /my-list?userId=1&tmdbId=550&status=WATCHLIST
```

#### Listar Filmes do Usuário
```http
GET /my-list/{userId}
```

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas:

```
📁 com.projeto.movieApi
  ├── 📁 client/          # Integração com APIs externas (Feign)
  ├── 📁 controller/      # Camada de apresentação (REST)
  ├── 📁 service/         # Lógica de negócio
  ├── 📁 repository/      # Camada de persistência
  ├── 📁 model/           # Entidades JPA
  └── 📁 dto/             # Data Transfer Objects
```

### 🔌 Integração com API Externa

A integração com a API do TMDB é realizada através do **Spring Cloud OpenFeign**:

```java
@FeignClient(name = "TmdbSearchClient", url = "${tmdb.api.url}")
public interface TmdbSearchClient {
    @GetMapping("/search/movie")
    TmdbResponseDTO getMovies(...);
    
    @GetMapping("/movie/{id}")
    MovieResponseDTO getMovie(...);
}
```

**Características:**
- Autenticação via Bearer Token configurável
- Mapeamento automático de DTOs
- Cache local para evitar chamadas repetidas à API externa

## 🗄️ Estrutura do Banco de Dados

### Tabelas

- **users** - Dados dos usuários
- **movies** - Filmes obtidos da API TMDB (cache local)
- **user_movies** - Relacionamento entre usuários e seus filmes (com status e avaliação)

### Migrations

As migrations são gerenciadas pelo Flyway e estão localizadas em:
```
src/main/resources/db/migration/
```

## 🔐 Configuração

Configure suas credenciais no arquivo `application.yaml`:

```yaml
tmdb:
  api:
    url: https://api.themoviedb.org/3
    token: ${TMDB_API_TOKEN}  # Variável de ambiente
```

## 🧪 Testando a API

### Usando Bruno Collection

O projeto inclui uma coleção de requisições Bruno (na pasta `Movie Api/`) para testar todos os endpoints.

### Usando cURL

Exemplo de busca de filmes:
```bash
curl "http://localhost:8080/movies?query=avatar&lang=pt-BR"
```

Exemplo de criação de usuário:
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

## 📊 Funcionalidades de Integração com API Externa

### 🎯 Destaques Técnicos

1. **Spring Cloud OpenFeign**
   - Cliente declarativo para consumo de APIs REST
   - Configuração centralizada

2. **Mapeamento de DTOs**
   - Conversão automática de JSON da API externa para objetos Java
   - Isolamento entre dados da API e modelo interno

3. **Cache Inteligente**
   - Filmes consultados são salvos localmente
   - Reduz chamadas à API externa
   - Método `getOrCreateMovie()` implementa essa lógica

4. **Configuração Externa**
   - URL e token configuráveis via `application.yaml`
   - Suporte a variáveis de ambiente

## 🎓 Objetivo do Projeto

Este projeto foi desenvolvido para demonstrar:
- ✅ Integração com APIs externas usando Spring Cloud OpenFeign
- ✅ Consumo de APIs REST públicas
- ✅ Autenticação via Bearer Token
- ✅ Arquitetura em camadas com Spring Boot
- ✅ Persistência de dados com JPA/Hibernate
- ✅ Versionamento de banco com Flyway

## 📝 Licença

Este projeto é apenas para fins educacionais e portfólio.

## 👨‍💻 Autor

Desenvolvido como projeto de portfólio para demonstrar habilidades em integração com APIs externas.

---

**Nota:** Este projeto utiliza a API pública do [The Movie Database (TMDB)](https://www.themoviedb.org/). Você precisará de uma chave de API gratuita para utilizar o projeto.
