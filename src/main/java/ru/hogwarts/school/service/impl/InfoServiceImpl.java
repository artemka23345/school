package ru.hogwarts.school.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.Stream;

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
    public Integer getSum() {
        log.info("значение вычисляется по формуле");
        return Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, Integer::sum );
    }
}
