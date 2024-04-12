
package br.edu.fesa.Models;

import java.util.List;


public class Resultado {
    
    private String erro;
    
    private List<Ponto> entrada;
    
    public List<Ponto> getEntrada() {
        return entrada;
    }
    public void setEntrada(List<Ponto> entrada) {
        this.entrada = entrada;
    }

    public String getErro(){
        return erro;
    }
    public void setErro(String erro){
        this.erro = erro;
    }
    
}