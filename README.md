# ms-relatorio - Docker automatico com Spring Boot

## Gerar imagem Docker automaticamente
O projeto esta configurado para gerar a imagem OCI automaticamente no `package` quando o profile `docker` estiver ativo.

```bash
./mvnw clean package -Pdocker
```

Isso cria a imagem:

```bash
ms-relatorio:0.0.1-SNAPSHOT
```

## Subir containers com Docker Compose
Para construir e subir direto com Compose:

```bash
docker compose up -d --build
```

Servicos:
- `ms-relatorio` em `http://localhost:8082`
- `postgres` em `localhost:5432`
