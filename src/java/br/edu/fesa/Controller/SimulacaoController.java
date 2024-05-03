
package br.edu.fesa.Controller;

import br.edu.fesa.Models.Ponto;
import br.edu.fesa.Models.Resultado;
import br.edu.fesa.utils.EnumTipoSinal;
import com.google.gson.Gson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "SimulacaoController", urlPatterns = {"/SimulacaoController"})
public class SimulacaoController extends HttpServlet {
    
    private final int qtdHarmonicas = 100;
    private final double PI = Math.PI;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Resultado do processo
        Resultado resultado = new Resultado();
         
        try {
            
            // Dados gerais de entrada
            Integer frequencia = Integer.valueOf(request.getParameter("frequencia"));
            EnumTipoSinal tipoSinal = EnumTipoSinal.forInt(Integer.parseInt(request.getParameter("tipoSinal")));
            double frequenciaMin = Double.valueOf(request.getParameter("frequenciaMin"));
            double frequenciaMax = Double.valueOf(request.getParameter("frequenciaMax"));
            
            resultado.setModuloFrequenciaEntrada(retornaModuloFrequenciaEntrada(tipoSinal, frequencia));
            resultado.setFaseFrequenciaEntrada(retornaFaseFrequenciaEntrada(tipoSinal, frequencia));
            resultado.setEntrada(retornaSinalEntrada(tipoSinal, frequencia, resultado));
            
            resultado.setModuloRespostaCanal(retornaModuloRespostaCanal(frequenciaMin, frequenciaMax));
            resultado.setFaseRespostaCanal(retornaFaseRespostaCanal(frequenciaMin, frequenciaMax));

        } catch (NumberFormatException e) {  
            resultado.setErro(e.getMessage());
            
        }catch (Exception e) {  
            resultado.setErro(e.getMessage());
            
        } finally {
            Gson gson = new Gson();
            String retorno = gson.toJson(resultado);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(retorno);
        }
    }
    
    // ENTRADA
    private List<Ponto> retornaModuloFrequenciaEntrada(EnumTipoSinal tipoSinal, int frequencia) {
        
        List<Ponto> pontos = new ArrayList<>();

        for (double i = 1; i <= qtdHarmonicas ; i++) {
            double y = 0;
            
            switch (tipoSinal) {
                case Quadrada -> y = i % 2 == 0 ? 0 : 4 / (i * PI);
                case Triangular -> y = i % 2 == 0 ? 0 : 8 / (PI * PI * i * i);
                case DenteDeSerra -> y = 2 / (i * PI);
                case SenoidalRetificada -> y = 4 / (PI - 4 * PI * i * i);
                default -> throw new IllegalArgumentException("Tipo de sinal inválido: " + tipoSinal.getValue());
            } 
            
            pontos.add(new Ponto(i * frequencia, y));
        }
        return pontos;
    }
    
    private List<Ponto> retornaFaseFrequenciaEntrada(EnumTipoSinal tipoSinal, int frequencia) {
        
        List<Ponto> pontos = new ArrayList<>();

        for (double i = 1; i <= qtdHarmonicas ; i++) {
            double y;
            
            switch (tipoSinal) {
                case Quadrada -> y = i % 2 == 0 ? 0 : - PI / 2;
                case Triangular -> y = i % 2 == 0 ? 0 : - Math.sin(PI * i / 2) * PI / 2;
                case DenteDeSerra -> y = (i % 2 == 0 ? 1 : -1) * PI / 2;
                case SenoidalRetificada -> y = 0;
                default -> throw new IllegalArgumentException("Tipo de sinal inválido: " + tipoSinal.getValue());
            }
            pontos.add(new Ponto(i * frequencia, y)); 
        }

        return pontos;
    }
    
    private List<Ponto> retornaSinalEntrada(EnumTipoSinal tipoSinal, int frequencia, Resultado resultado)
    {
        double periodo = 1.0 / frequencia;
        double tempo = periodo * 2;  // Serão exibidos dois ciclos completos da onda
        double incremento = periodo / 100.0;  // Quantidade de pontos por periodo
        
        List<Ponto> pontos = new ArrayList<>();
        List<Ponto> moduloFrequenciaEntrada = resultado.getModuloFrequenciaEntrada();
        List<Ponto> faseFrequenciaEntrada = resultado.getFaseFrequenciaEntrada();
        
        for (double i = 0; i <= tempo ; i += incremento) {
            double y = tipoSinal == EnumTipoSinal.SenoidalRetificada ? 2 / PI : 0;  // a0
            
            for(int j = 0; j < moduloFrequenciaEntrada.size(); j++){       
                
                double harmonica = moduloFrequenciaEntrada.get(j).getX();
                double modulo = moduloFrequenciaEntrada.get(j).getY();  // An
                double fase = faseFrequenciaEntrada.get(j).getY();  // Fn
                
                y += modulo * Math.cos(2 * Math.PI * harmonica * i + fase);
            }
            pontos.add(new Ponto(i, y));
        }
        
        return pontos;
    }

    // CANAL
    private List<Ponto> retornaModuloRespostaCanal(double frequenciaMin, double frequenciaMax){
        List<Ponto> retorno = new ArrayList<Ponto>();
        double y;
        
        if(frequenciaMax == 0 || frequenciaMin > frequenciaMax){
            throw new IllegalArgumentException("Frequência máxima inválida.");
        }
        
        // Passa-baixas
        if(frequenciaMin == 0){
            for(int i = 0; i <= qtdHarmonicas; i++){
                y = 1 / (Math.sqrt(1 + Math.pow((i / frequenciaMax), 2)));
                retorno.add(new Ponto(i, y));
            }
            
        // Passa-faixas
        } else {
            for(int i = 0; i <= qtdHarmonicas; i++){
                y = i / (frequenciaMin * Math.sqrt(
                    (1 + Math.pow((i / frequenciaMin), 2)) *
                    (1 + Math.pow((i / frequenciaMax), 2))
                ));                
                retorno.add(new Ponto(i, y));
            }
        }
        
        return retorno;
    }
    
    private List<Ponto> retornaFaseRespostaCanal(double frequenciaMin, double frequenciaMax){
        List<Ponto> retorno = new ArrayList<Ponto>();
        double y;
        
        if(frequenciaMax == 0 || frequenciaMin > frequenciaMax){
            throw new IllegalArgumentException("Frequência máxima inválida.");
        }
        
        // Passa-baixas
        if(frequenciaMin == 0){
            for(int i = 0; i <= qtdHarmonicas; i++){
                y = Math.atan(- i / frequenciaMax);
                retorno.add(new Ponto(i, y));
            }
            
        // Passa-faixas
        } else {
            for(int i = 0; i <= qtdHarmonicas; i++){
                y = - PI / 2 - Math.atan((i * (frequenciaMin + frequenciaMax))/((frequenciaMin * frequenciaMax) - i * i));
                retorno.add(new Ponto(i, y));
            }
        }
        
        return retorno;
    }

    @Override
    public String getServletInfo() {
        return "Simulacao Controller";
    }
}