<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="br.edu.fesa.utils.EnumTipoSinal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simulador Canal de Comunicação</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <link href="style.css" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/x-icon" href="assets/icon.ico">
</head>
<body>
    <%
        HashMap<Integer, String> tiposSinais = EnumTipoSinal.getTiposSinais();
    %>
    
    <nav class="fixed-top pt-3 bg-light">
        <ul class="nav nav-tabs me-auto">
            <li class="nav-item ps-5">
                <a class="nav-link" aria-current="page" href="/PBL">Home</a>
            </li>
            <li class="nav-item ps-3">
                <a class="nav-link active" aria-current="page" href="simulador.jsp">Simulador</a>
            </li>
            <li class="nav-item ps-3">
                <a class="nav-link" aria-current="page" href="sobre.html">Sobre</a>
            </li>
        </ul>
    </nav>
    <main>
        <h1 class="pb-3">Simulador de canal de comunicação</h1>
        
        <!-- Formulário Simulação -->
        <section class="accordion" id="accordionForm">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseForm" aria-expanded="true" aria-controls="collapseForm">
                        Dados Simulação
                    </button>
                </h2>
                <div id="collapseForm" class="accordion-collapse collapse show" data-bs-parent="#accordionForm">
                    <div class="accordion-body">
                        <form id="formSimular" action="SimulacaoController" method="POST">
                            <label for="frequencia" class="form-label mt-3">Frequência [kHz] <span class="text-danger">*</span></label>
                            <input id="frequencia" name="frequencia" type="number" class="form-control my-2" required placeholder="Frequencia" min="1" max="100">

                            <label for="tipoSinal" class="form-label mt-3">Tipo de Sinal <span class="text-danger">*</span></label>
                            <select id="tipoSinal" name="tipoSinal" class="form-select my-2" aria-label="Tipo de Sinal" required>
                                <option value="" disabled selected hidden>Selecione um tipo de sinal</option>
                                <% for (Map.Entry<Integer, String> tipo : tiposSinais.entrySet()) { %>
                                <option value="<%= tipo.getKey() %>"><%= tipo.getValue() %></option>
                                <% } %>
                            </select>

                            <label for="frequenciaMin" class="form-label mt-3">Frequências de corte do canal [kHz] <span class="text-danger">*</span></label>
                            <p id="rangeFrequenciaErro" class="text-danger"></p>
                            <div class="d-flex">
                                <input id="frequenciaMin" name="frequenciaMin" type="number" class="form-control m-2" required placeholder="Frequencia Mínima" min="0" max="100">
                                <input id="frequenciaMax" name="frequenciaMax" type="number" class="form-control m-2" required placeholder="Frequencia Máxima" min="0" max="100">
                            </div>
                            
                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-primary mt-4">Simular</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <!-- Formulário Simulação -->
        
        <section id="graficos" class="mt-5">
            <div id="sinal-entrada" class="grafico"></div>
        </section>
        
           
    </main>
    <footer class="fixed-bottom py-3 border-top bg-light">
        <p class="text-muted ms-5">Projeto desenvolvido no 7° semestre do curso de Engenharia de Computação</p>
    </footer>
    <script src="simulacao.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
        <% String errorMessage = (String)request.getAttribute("errorMessage");
               if (errorMessage != null && !errorMessage.isEmpty()) { %>
                   alert("<%= errorMessage %>");
        <% } %>
    </script>
</body>
</html>

