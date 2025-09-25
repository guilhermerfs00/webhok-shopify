# Sistema de Tratamento de Exceções

Este documento explica o sistema de tratamento de exceções implementado na aplicação.

## 📋 Estrutura de Exceções

### 🔧 Classes Base

#### `BaseException`
- Classe abstrata base para todas as exceções personalizadas
- Contém `HttpStatus` e mensagem de erro
- Facilita o tratamento padronizado de erros

#### `BusinessException`
- Extende `BaseException`
- Usado para erros de regra de negócio
- Status padrão: `BAD_REQUEST (400)`
- Pode ser configurado com outros status HTTP

#### `NotFoundException` 
- Extende `BaseException`
- Usado para recursos não encontrados
- Status fixo: `NOT_FOUND (404)`
- Construtor de conveniência para ID de recursos

### 🎯 GlobalExceptionHandler

O `GlobalExceptionHandler` captura automaticamente as exceções e retorna respostas HTTP padronizadas:

```java
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
```

#### Handlers Implementados:
- `handleBaseException()` - Trata exceções base
- `handleBusinessException()` - Trata erros de negócio  
- `handleNotFoundException()` - Trata recursos não encontrados
- `handleRuntimeException()` - Trata RuntimeException genéricas
- `handleGenericException()` - Fallback para exceções não tratadas

## 🚀 Como Usar

### 1. Lançando Exceções de Negócio

```java
// Erro simples
throw new BusinessException("Dados inválidos");

// Com status específico
throw new BusinessException(HttpStatus.CONFLICT, "Recurso já existe");
```

### 2. Lançando Exceções de Recurso Não Encontrado

```java
// Forma simples
throw new NotFoundException("Pedido não encontrado");

// Com identificador
throw new NotFoundException("Order", orderId);
```

### 3. Resposta Padronizada

Todas as exceções retornam uma resposta JSON padronizada:

```json
{
  "status": 404,
  "error": "Resource Not Found",
  "message": "Order with id '123' not found",
  "path": "/api/orders/123",
  "timestamp": "2024-01-15T10:30:00"
}
```

## 🧪 Endpoints de Exemplo

Para testar o sistema, foram criados endpoints de exemplo:

- `GET /api/examples/business-error` - Gera BusinessException
- `GET /api/examples/not-found` - Gera NotFoundException  
- `GET /api/examples/runtime-error` - Gera RuntimeException
- `GET /api/examples/order/{orderId}` - Busca pedido (só existe ORDER-123)
- `POST /api/examples/validate?data=xxx` - Validação de dados

## ⚡ Integração com Clients HTTP

O `BockbussClient` foi atualizado para usar as exceções customizadas:

```java
// Erro 404 -> NotFoundException
// Erro 4xx -> BusinessException  
// Erro conectividade -> BusinessException com SERVICE_UNAVAILABLE
// Outros erros -> BusinessException com INTERNAL_SERVER_ERROR
```

## 📝 Logs

- `BusinessException`: Log level WARN
- `NotFoundException`: Log level WARN  
- `RuntimeException`: Log level ERROR
- `Exception`: Log level ERROR

## ✅ Benefícios

1. **Padronização**: Todas as respostas de erro seguem o mesmo formato
2. **Rastreabilidade**: Logs automáticos com níveis apropriados
3. **Flexibilidade**: Status HTTP customizáveis
4. **Manutenibilidade**: Centralização do tratamento de erros
5. **Debugging**: Informações detalhadas de contexto