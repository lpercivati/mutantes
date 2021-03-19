package com.example.demo.Controllers;

import com.example.demo.Models.Estadisticas;
import com.example.demo.Models.Muestra;
import com.example.demo.Services.MutantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MutantesController {

    @Autowired
    private MutantesService service;

    @GetMapping("/stats")
    public ResponseEntity getStats() {
        Estadisticas stats = service.getEstadisticas();

        Map<String, Object> map = new HashMap<>();
        map.put("count_mutant_dna", stats.getMutantes());
        map.put("count_human_dna", stats.getTotal());
        map.put("ratio", stats.getPorcentaje());

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping(path = "/mutant", consumes = "application/json", produces = "application/json")
    public ResponseEntity isMutant(@RequestBody Muestra muestra){
        HttpStatus status;

        try{
            boolean esMutante = service.isMutant(muestra.getDna());
            status = esMutante ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        }
        catch (IllegalArgumentException ex){
            status = HttpStatus.BAD_REQUEST;
        }
        catch (Exception ex){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }
}
