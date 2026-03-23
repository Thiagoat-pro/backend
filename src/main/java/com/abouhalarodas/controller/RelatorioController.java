package com.abouhalarodas.controller;

import com.abouhalarodas.dto.relatorio.RelatorioResponseDTO;
import com.abouhalarodas.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/relatorio")
    public RelatorioResponseDTO gerarRelatorio() {
        return relatorioService.gerarRelatorio();
    }
}