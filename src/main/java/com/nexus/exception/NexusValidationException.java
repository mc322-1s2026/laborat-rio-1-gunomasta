package com.nexus.exception;
/**
 * Exceção customizada utilizada para aplicar a filosofia Fail-Fast no sistema Nexus.
 * Deve ser lançada imediatamente após detectar qualquer violação de regra de negócio, 
 * impedindo que o sistema entre em um estado inconsistente e incrementando o contador
 * global de ValidationErrors ao ser chamado.
 * 
 * @param message A mensagem detalhando que regra fora violada.
 */
public class NexusValidationException extends RuntimeException {
    public static int totalValidationErrors = 0;

    public NexusValidationException(String message) {
        super(message);
        totalValidationErrors++;
    }
}