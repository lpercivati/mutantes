package com.example.demo.Models;

public class Estadisticas {
    private long mutantes;
    private long total;
    private double porcentaje;

    public Estadisticas(long total, long mutantes, double porcentaje){
        this.total = total;
        this.mutantes = mutantes;
        this.porcentaje = porcentaje;
    }

    public long getTotal(){
        return total;
    }

    public long getMutantes(){
        return mutantes;
    }

    public double getPorcentaje(){
        return porcentaje;
    }
}
