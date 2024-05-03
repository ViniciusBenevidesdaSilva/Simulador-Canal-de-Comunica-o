
package br.edu.fesa.Models;

import java.util.List;


public class Resultado {
    
    private String erro;
    
    private List<Ponto> entrada;
    private List<Ponto> moduloFrequenciaEntrada;
    private List<Ponto> faseFrequenciaEntrada;
    
    private List<Ponto> moduloRespostaCanal;
    private List<Ponto> faseRespostaCanal;

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
    
    public List<Ponto> getModuloRespostaCanal() {
        return moduloRespostaCanal;
    }
    public void setModuloRespostaCanal(List<Ponto> moduloRespostaCanal) {
        this.moduloRespostaCanal = moduloRespostaCanal;
    }

    public List<Ponto> getFaseRespostaCanal() {
        return faseRespostaCanal;
    }
    public void setFaseRespostaCanal(List<Ponto> faseRespostaCanal) {
        this.faseRespostaCanal = faseRespostaCanal;
    }
    
    public String getErro(){
        return erro;
    }
    public void setErro(String erro){
        this.erro = erro;
    }
    
}