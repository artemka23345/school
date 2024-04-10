package ru.hogwarts.school.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

@Log4j2
@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    private String currentPort;

    public String getCurrentPort () {
        log.info("Port: {}", currentPort);
        return currentPort;
    }
}
