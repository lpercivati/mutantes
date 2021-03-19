package com.example.demo;

import com.example.demo.Repositories.MutantesRepository;
import com.example.demo.Services.MutantesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.mockito.Mockito.*;

@SpringBootTest(classes=DemoApplication.class)
public class MutantesServiceTest {

    @Mock
    MutantesRepository repository;

    @InjectMocks
    MutantesService service;

    @Test
    void isMutantQuintaFila(){

        String[] input = new String[5];
        input[0] = "CTCCA";
        input[1] = "ACCGA";
        input[2] = "TTAGA";
        input[3] = "ATCGC";
        input[4] = "CTTTT";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantPrimeraFila(){

        String[] input = new String[5];
        input[4] = "CTCCA";
        input[1] = "ACCGA";
        input[2] = "TTAGA";
        input[3] = "ATCGC";
        input[0] = "CTTTT";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantMasFilas(){

        String[] input = new String[8];
        input[0] = "CTCCAACTG";
        input[1] = "ACCGACTGA";
        input[2] = "TTAGATGAC";
        input[3] = "ATCGCGACT";
        input[4] = "CTCTTACTG";
        input[5] = "CTCCAACTG";
        input[6] = "ACCGACTGA";
        input[7] = "TTAGAAAAA";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantFalso(){

        String[] input = new String[5];
        input[0] = "CTGAT";
        input[1] = "ACCGA";
        input[2] = "TTAGA";
        input[3] = "ATCGC";
        input[4] = "CTCCA";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(!esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantPorColumna(){

        String[] input = new String[5];
        input[0] = "CTGGT";
        input[1] = "ACCGA";
        input[2] = "TTAGA";
        input[3] = "ATCGC";
        input[4] = "CTCCA";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantPorDiagonal(){

        String[] input = new String[5];
        input[0] = "CTGGT";
        input[1] = "ACCTA";
        input[2] = "TAAGA";
        input[3] = "ATAGC";
        input[4] = "CTCAA";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantPorDiagonalInversa(){

        String[] input = new String[5];
        input[0] = "CTGGT";
        input[1] = "ACCTA";
        input[2] = "TATGA";
        input[3] = "ATAGC";
        input[4] = "CTCCA";

        boolean esMutante = service.isMutant(input);

        Assert.isTrue(esMutante);
        verify(repository).save(any());
    }

    @Test
    void isMutantCaracterInvalido(){
        String[] input = new String[5];
        input[0] = "CTLGT";
        input[1] = "ACCTA";
        input[2] = "TATGA";
        input[3] = "ATAGC";
        input[4] = "CTCCA";

        try{
            boolean esMutante = service.isMutant(input);
            Assert.isTrue(false);
        } catch (IllegalArgumentException ex){
            verifyNoInteractions(repository);
        }
    }

    @Test
    void getEstadisticas(){
        when(repository.countByIsMutant(true)).thenReturn((long)2);
        when(repository.count()).thenReturn((long)4);

        var estadisticas = service.getEstadisticas();

        Assert.isTrue(estadisticas.getTotal() == 4);
        Assert.isTrue(estadisticas.getMutantes() == 2);
        Assert.isTrue(estadisticas.getPorcentaje() == (double)0.5);
    }
}
