package com.ufes.ms_relatorio.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ErroDTO {
    private Instant timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private String detail;
    private List<CampoErroDTO> erros;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampoErroDTO {
        private String campo;
        private String mensagem;
    }
}
