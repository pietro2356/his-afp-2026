package com.sio.backend.exception;

import com.sio.backend.generics.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

/**
 * Gestore globale delle eccezioni per l'applicazione SIO.
 * Intercetta le eccezioni sollevate dai controller e le incapsula
 * in risposte HTTP standardizzate tramite {@link ApiResponse}.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Gestisce qualsiasi eccezione non prevista (Fallback generale).
     * Restituisce HTTP 500 Internal Server Error.
     *
     * @param ex l'eccezione imprevista catturata
     * @return ApiResponse con i dettagli dell'errore interno
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleAll(Exception ex){
        log.error("Codifica errore: THX403");
        log.error("Errore imprevisto non gestito: {} - StakTrace: {}", ex.getMessage(), ex.getStackTrace());

        return ApiResponse.error(
                "Errore inaspettato dal server",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "THX403"
        );
    }

    /**
     * Gestisce argomenti non validi o richieste malformate.
     * Restituisce HTTP 400 Bad Request.
     *
     * @param ex l'eccezione IllegalArgumentException sollevata
     * @return ResponseEntity contenente l'ApiResponse di fallimento
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex){
        ApiResponse<Void> body = ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    /**
     * Gestisce il fallimento nel recupero di un'entità JPA dal database.
     * Restituisce HTTP 404 Not Found.
     *
     * @param ex l'eccezione EntityNotFoundException sollevata
     * @return ApiResponse con lo stato di fallimento
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(EntityNotFoundException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    /**
     * Gestisce il tentativo di accesso a elementi inesistenti.
     * Restituisce HTTP 404 Not Found.
     *
     * @param ex l'eccezione NoSuchElementException sollevata
     * @return ApiResponse con lo stato di fallimento
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNoSuchElement(NoSuchElementException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    /**
     * Gestisce gli errori di validazione dei DTO (es. annotazioni @NotNull, @Size).
     * Restituisce HTTP 400 Bad Request aggregando i messaggi d'errore dei singoli campi.
     *
     * @param ex l'eccezione MethodArgumentNotValidException sollevata
     * @return ApiResponse con l'elenco dei campi non validi
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethNotValid(MethodArgumentNotValidException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                        .reduce((s1, s2) -> s1 + "; " + s2)
                        .orElse("Validation error")
        );
    }

    /**
     * Gestisce corpi di richieste HTTP illeggibili o JSON non parsabili.
     * Restituisce HTTP 400 Bad Request.
     *
     * @param ex l'eccezione HttpMessageNotReadableException sollevata
     * @return ApiResponse con lo stato di fallimento
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethNotValid(HttpMessageNotReadableException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    /**
     * Gestisce errori generati a livello di transazione sul database.
     * Restituisce HTTP 400 Bad Request.
     *
     * @param ex l'eccezione TransactionSystemException sollevata
     * @return ApiResponse con lo stato di fallimento
     */
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleTransaction(TransactionSystemException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleUnsupportedOperation(UnsupportedOperationException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }
}
