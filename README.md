
# 📚 Spring HATEOAS PoC

Esta é uma prova de conceito (PoC) de uma API RESTful usando **Spring Boot 3.5.3** e **Spring HATEOAS**, com suporte a:

- CRUD completo de livros
- DTO + ModelMapper para desacoplamento
- Links HATEOAS (`self`, `collection`, etc.)
- Paginação com `PagedModel`
- Tratamento de exceções
- Endpoint raiz navegável

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot (Web, Data JPA, HATEOAS)
- ModelMapper
- PostgreSQL (ou H2 para testes)
- Maven

## 📦 Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/Matheus-Freitas0/Poc-Spring-Hateoas.git
   cd spring-hateoas-poc
   ```

2. Configure o banco de dados no `application.properties` ou use H2 para testes.

3. Rode a aplicação com:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Teste os endpoints com Postman, Insomnia ou cURL:

- `GET /books` – lista paginada com links
- `GET /books/{id}` – retorna um livro com links
- `POST /books` – cria um livro
- `PUT /books/{id}` – atualiza
- `DELETE /books/{id}` – remove
- `GET /` – endpoint raiz com link para `/books`

## 🛠️ Endpoints principais

| Método | Endpoint       | Descrição               |
|--------|----------------|-------------------------|
| GET    | `/books`       | Lista paginada de livros|
| GET    | `/books/{id}`  | Detalhes de um livro    |
| POST   | `/books`       | Cria um novo livro      |
| PUT    | `/books/{id}`  | Atualiza um livro       |
| DELETE | `/books/{id}`  | Remove um livro         |
| GET    | `/`            | Endpoint raiz da API    |

## 🧪 Exemplo de resposta HATEOAS

```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "_links": {
    "self": { "href": "http://localhost:8080/books/1" },
    "books": { "href": "http://localhost:8080/books" }
  }
}
```

---

Feito por Matheus Freitas
