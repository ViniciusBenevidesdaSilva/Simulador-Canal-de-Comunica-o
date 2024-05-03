
package br.edu.fesa.Models;

import java.util.List;


public class Resultado {
    
    private String erro;
    
    private List<Ponto> entrada;
    private List<Ponto> moduloFrequenciaEntrada;
    private List<Ponto> faseFrequenciaEntrada;
        
    public List<Ponto> getEntrada() {
        return entrada;
    }
    public void setEntrada(List<Ponto> entrada) {
        this.entrada = entrada;
    }

    public List<Ponto> getModuloFrequenciaEntrada() {
        return moduloFrequenciaEntrada;
    }
    public void setModuloFrequenciaEntrada(List<Ponto> moduloFrequenciaEntrada) {
        this.moduloFrequenciaEntrada = moduloFrequenciaEntrada;
    }    

    public List<Ponto> getFaseFrequenciaEntrada() {
        return faseFrequenciaEntrada;
    }
    public void setFaseFrequenciaEntrada(List<Ponto> faseFrequenciaEntrada) {
        this.faseFrequenciaEntrada = faseFrequenciaEntrada;
    }    
    
    public String getErro(){
        return erro;
    }
    public void setErro(String erro){
        this.erro = erro;
    }
    
}