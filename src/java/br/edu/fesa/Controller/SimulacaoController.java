
package br.edu.fesa.Controller;

import br.edu.fesa.utils.Point;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
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
    
    private static final String jspFile = "simulador.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String frequenciaStr = request.getParameter("frequencia");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String frequenciaStr = request.getParameter("frequencia");
            String tipoSinalStr = request.getParameter("tipoSinal");
            String frequenciaMinStr = request.getParameter("frequenciaMin");
            String frequenciaMaxStr = request.getParameter("frequenciaMax");
            
            Integer frequencia = Integer.valueOf(frequenciaStr);
            List<Point> pontosDaOndaQuadrada = calcularPontosDaOndaQuadrada(frequencia);
            
            Gson gson = new Gson();
            String dadosDaOndaQuadradaJson = gson.toJson(pontosDaOndaQuadrada);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(dadosDaOndaQuadradaJson);
        } catch (Exception e) {
            String errorMessage = "Erro ao realizar a simulação: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher(jspFile);
            dispatcher.forward(request, response);
        }
    }
    
    private List<Point> calcularPontosDaOndaQuadrada(int frequencia) {
        
        List<Point> pontos = new ArrayList<>();
        
        for (double i = 0; i < frequencia * 2; i += frequencia / 100.0) {
            
            double y =  (i % frequencia) < (frequencia / 2.0) ? 1 : -1;
            
            pontos.add(new Point(i, y));
        }
        return pontos;
    }
    
    @Override
    public String getServletInfo() {
        return "Simulacao Controller";
    }
}
