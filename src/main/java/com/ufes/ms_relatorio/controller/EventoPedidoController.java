package com.ufes.ms_relatorio.controller;

import com.ufes.ms_relatorio.dto.EventoPedidoRequest;
import com.ufes.ms_relatorio.dto.EventoPedidoResponse;
import com.ufes.ms_relatorio.service.EventoPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoPedidoController {

  private final EventoPedidoService eventoPedidoService;

  @PostMapping("/pedidos")
  public ResponseEntity<EventoPedidoResponse> receberEvento(@Valid @RequestBody EventoPedidoRequest request) {
    EventoPedidoResponse response = eventoPedidoService.salvar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
