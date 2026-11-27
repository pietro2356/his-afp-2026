package com.sio.backend.generics;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Modello generico standardizzato (Wrapper) per tutte le risposte REST dell'applicazione SIO.
 * Incapsula lo stato dell'operazione, i metadati temporali e il payload dei dati o degli errori.
 *
 * @param <T> il tipo di dato trasportato nel payload della risposta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Wrapper standard per le risposte API del sistema SIO")
public class ApiResponse<T> {

    @Schema(description = "Stato esito della richiesta (SUCCESS, FAIL, ERROR)", example = "SUCCESS", requiredMode = Schema.RequiredMode.REQUIRED)
    private ApiResponseStatus status;

    @Schema(description = "Codice di stato HTTP associato alla risposta", example = "200", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusCode;

    @Schema(description = "Timestamp temporale in cui è stata generata la risposta", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant timestamp;

    @Schema(description = "Payload dei dati restituiti dall'operazione")
    private T data;

    @Schema(description = "Messaggio descrittivo associato all'esito o all'errore", example = "Operazione completata con successo")
    private String message;

    @Schema(description = "Codice errore applicativo interno personalizzato", example = "THX403")
    private String errorCode;

    @Schema(description = "Traccia dello stack di errore (visibile solo in ambienti di debug/sviluppo)")
    private String stackTrace;

    // --- SUCCESS ---

    /**
     * Crea un'istanza di {@link ApiResponse} con esito positivo per dati singoli.
     *
     * @param data il payload dei dati da restituire
     * @param <T> il tipo del dato
     * @return un oggetto ApiResponse configurato come SUCCESS (HTTP 200)[cite: 2]
     */
    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .data(data)
                .build();
    }

    // -- ERROR --

    /**
     * Crea un'istanza di {@link ApiResponse} per errori di tipo critico/server.
     *
     * @param message il messaggio descrittivo dell'errore
     * @param statusCode il codice HTTP di errore (es. 500)
     * @param errorCode il codice errore applicativo interno
     * @param <T> il tipo del payload (tipicamente Void)
     * @return un oggetto ApiResponse configurato come ERROR[cite: 2]
     */
    public static <T> ApiResponse<T> error(String message, Integer statusCode, String errorCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.ERROR)
                .statusCode(statusCode)
                .errorCode(errorCode)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }

    /**
     * Crea un'istanza di {@link ApiResponse} per errori critici includendo lo stack trace.
     *
     * @param message il messaggio descrittivo dell'errore
     * @param statusCode il codice HTTP di errore
     * @param errorCode il codice errore applicativo interno
     * @param stackTrace la traccia dell'eccezione
     * @param <T> il tipo del payload
     * @return un oggetto ApiResponse configurato come ERROR con stack trace[cite: 2]
     */
    public static <T> ApiResponse<T> error(String message, Integer statusCode, String errorCode, String stackTrace){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.ERROR)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .errorCode(errorCode)
                .stackTrace(stackTrace)
                .build();
    }

    // --- FAIL ---

    /**
     * Crea un'istanza di {@link ApiResponse} per fallimenti di validazione o richieste errate.
     *
     * @param message il messaggio descrittivo del fallimento
     * @param statusCode il codice HTTP (es. 400, 404)
     * @param <T> il tipo del payload
     * @return un oggetto ApiResponse configurato come FAIL[cite: 2]
     */
    public static <T> ApiResponse<T> fail(String message, Integer statusCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.FAIL)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }

    /**
     * Crea un'istanza di {@link ApiResponse} per fallimenti con codice errore specifico.
     *
     * @param message il messaggio descrittivo del fallimento
     * @param statusCode il codice HTTP
     * @param errorCode il codice errore applicativo interno
     * @param <T> il tipo del payload
     * @return un oggetto ApiResponse configurato come FAIL con error code[cite: 2]
     */
    public static <T> ApiResponse<T> fail(String message, Integer statusCode, String errorCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.FAIL)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .errorCode(errorCode)
                .build();
    }
}