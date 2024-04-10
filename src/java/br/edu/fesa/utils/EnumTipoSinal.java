
package br.edu.fesa.utils;

import java.util.HashMap;

public enum EnumTipoSinal {
    
    Quadrada(1, "Quadrada"),
    Triangular(2, "Triangular"),
    DenteDeSerra(3, "Dente De Serra"),
    SenoidalRetificada(4, "Senoidal Retificada");

    
    private final int value;
    private final String description;
    
    EnumTipoSinal(int value, String description) {
        this.value = value;
        this.description = description;
    }
    
    public static EnumTipoSinal forInt(int valorInteiro) {
        for (EnumTipoSinal tipoSinal: values()) {
            if (tipoSinal.getValue() == valorInteiro) 
                return tipoSinal;
        }
        throw new IllegalArgumentException("Valor inteiro inválido: " + valorInteiro);
    }   
    
    public int getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static HashMap<Integer, String> getTiposSinais(){
        HashMap<Integer, String> tiposSinais = new HashMap<>();
        
        for (EnumTipoSinal tipoSinal : values()) {
            tiposSinais.put(tipoSinal.getValue(), tipoSinal.getDescription());
        }
        
        return tiposSinais;
    }
}