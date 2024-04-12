
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
            //String frequenciaMinStr = request.getParameter("frequenciaMin");
            //String frequenciaMaxStr = request.getParameter("frequenciaMax");
            
            resultado.setEntrada(retornaSinalEntrada(tipoSinal, frequencia));
            
        } catch (Exception e) {
            resultado.setErro(e.getMessage());
            
        } finally {
            Gson gson = new Gson();
            String retorno = gson.toJson(resultado);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(retorno);
        }
    }
    
    private List<Ponto> retornaSinalEntrada(EnumTipoSinal tipoSinal, int frequencia) {
        double periodo = 1.0 / frequencia;
        List<Ponto> pontos = new ArrayList<>();
        double tempo = periodo * 2;
        double incremento = periodo / 100.0;

        for (double i = 0; i <= tempo + incremento; i += incremento) {
            double y;
            double valorModificado = i % periodo;
            
            switch (tipoSinal) {
                case Quadrada -> y = (valorModificado < (periodo / 2.0)) ? 1 : -1;
                case Triangular -> y = (valorModificado < (periodo / 2.0)) ?
                            (valorModificado / (periodo / 2.0)) * 2.0 - 1.0 :
                            ((periodo - valorModificado) / (periodo / 2.0)) * 2.0 - 1.0;
                case DenteDeSerra -> y = valorModificado * 2 - 1;
                case SenoidalRetificada -> y = Math.abs(Math.sin((i / periodo / 2) * 2 * Math.PI));
                default -> throw new IllegalArgumentException("Tipo de sinal inválido: " + tipoSinal.getValue());
            }
            pontos.add(new Ponto(i, y));
        }

        return pontos;
    }


    @Override
    public String getServletInfo() {
        return "Simulacao Controller";
    }
}