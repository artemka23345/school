package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.service.InfoService;


@RestController
@Log4j2
@RequestMapping()
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/port")
    @Operation(summary = "Получить порт")
    public ResponseEntity<String> getPort() {
        return ResponseEntity.ok(infoService.getCurrentPort());
    }

}
