package com.example.demo;

import com.example.demo.Controllers.MutantesController;
import com.example.demo.Models.Estadisticas;
import com.example.demo.Models.Muestra;
import com.example.demo.Services.MutantesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=DemoApplication.class)
public class MutantesControllerTest {

    @Mock
    MutantesService service;

    @InjectMocks
    MutantesController controller;

    @Test
    void isMutantOk(){
        when(service.isMutant(anyObject())).thenReturn(true);

       var response = controller.isMutant(new Muestra());

       Assert.isTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    void isMutantFalse(){
        when(service.isMutant(anyObject())).thenReturn(false);

        var response = controller.isMutant(new Muestra());

        Assert.isTrue(response.getStatusCode() == HttpStatus.FORBIDDEN);
    }

    @Test
    void isMutantBadRequest(){
        when(service.isMutant(anyObject())).thenThrow(new IllegalArgumentException());

        var response = controller.isMutant(new Muestra());

        Assert.isTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    void isMutantInternalError(){
        when(service.isMutant(anyObject())).thenThrow(new RuntimeException());

        var response = controller.isMutant(new Muestra());

        Assert.isTrue(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getStatsOk(){
        when(service.getEstadisticas()).thenReturn(new Estadisticas(10,1,0.1));

        var response = controller.getStats();
        var body = (HashMap<String, Object>) response.getBody();

        Assert.isTrue(response.getStatusCode() == HttpStatus.OK);
        Assert.isTrue((long)body.get("count_mutant_dna") == 1);
        Assert.isTrue((long)body.get("count_human_dna") == 10);
        Assert.isTrue((double)body.get("ratio") == 0.1);
    }
}
