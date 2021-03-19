package com.example.demo.Services;

import com.example.demo.Models.Estadisticas;
import com.example.demo.Repositories.MutantesRepository;
import com.example.demo.Entities.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantesService {

    @Autowired
    private MutantesRepository repository;

    public Estadisticas getEstadisticas(){
        var totalRegistros = repository.count();
        var cantidadMutantes = repository.countByIsMutant(true);

        return new Estadisticas(totalRegistros, cantidadMutantes, (double)cantidadMutantes/totalRegistros);
    }

    public boolean isMutant(String[] dna){
        validar(dna);

        int longitud = dna.length;
        char[][] matriz = new char[longitud][longitud];

        for(int i = 0; i < longitud; i++){
            matriz[i] = dna[i].toCharArray();
        }

        boolean esMutante = esMutantePorFilas(matriz) || esMutantePorColumnas(matriz) || esMutantePorDiagonal(matriz) || esMutantePorDiagonalInversa(matriz);

        String id = String.join("", dna);
        repository.save(new Persona(id, esMutante));

        return esMutante;
    }

    private void validar(String[] dna) {
        var encontrado = false;
        var i = 0;
        var letrasAceptadas = "ATCG";

        while(i < dna.length && !encontrado){
            String palabra = dna[i];
            var otrasLetras = palabra.chars().mapToObj(c -> (char) c).filter(letra -> !letrasAceptadas.contains(letra.toString()));

            if(otrasLetras.count() > 0){
                encontrado = true;
            }
            i++;
        }

        if(encontrado){
          throw new IllegalArgumentException();
        }
    }

    private boolean esMutantePorFilas(char[][] matriz){
        boolean mutante = false;
        int j = 0;

        while(!mutante && j<matriz.length){
            int l = 0;
            while(!mutante && l <= matriz[j].length-4){
                if(matriz[j][l] == matriz[j][l+1] &&
                        matriz[j][l] == matriz[j][l+2] &&
                        matriz[j][l] == matriz[j][l+3]){
                    mutante = true;
                }
                l++;
            }

            j++;
        }

        return mutante;
    }

    private boolean esMutantePorColumnas(char[][] matriz){
        int longitud = matriz.length;
        char[][] matrizTranspuesta = new char[longitud][longitud];

        for(int i = 0; i < longitud; i++){
            for(int j = 0; j < longitud; j++){
                matrizTranspuesta[i][j] = matriz[j][i];
            }
        }

        return esMutantePorFilas(matrizTranspuesta);
    }

    private boolean esMutantePorDiagonal(char[][] matriz){
        int longitud = matriz.length;
        int altura = (matriz.length - 3) * (matriz.length - 3);
        char[][] matrizDeDiagonales = new char[altura][4];
        int contadorFila = 0;

        for(int i = 0; i <= longitud - 4; i++){
            for(int j = 0; j <= longitud - 4; j++){
                matrizDeDiagonales[contadorFila] = new char[]{ matriz[i][j], matriz[i+1][j+1], matriz[i+2][j+2], matriz[i+3][j+3] };

                contadorFila++;
            }
        }
        return esMutantePorFilas(matrizDeDiagonales);
    }

    private boolean esMutantePorDiagonalInversa(char[][] matriz){
        int longitud = matriz.length;
        char[][] matrizFilasInvertidas = new char[longitud][longitud];

        for(int i = 0; i < longitud; i++){
            matrizFilasInvertidas[i] = matriz[longitud-i-1];
        }
        return esMutantePorDiagonal(matrizFilasInvertidas);
    }
}
