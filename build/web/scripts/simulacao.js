
function atualizarGraficoLinha(dados, grafico, nome, xName, yName, cor) {
    var chart = Highcharts.chart(grafico, {
        chart: {
            type: 'line'
        },
        title: {
            text: nome
        },
        xAxis: {
            title: {
                text: xName
            }
        },
        yAxis: {
            title: {
                text: yName
            }
        },
        series: [{
            name: nome,
            data: dados,
            color: cor
        }]
    });
}

function atualizarGraficoBarras(dados, grafico, nome, xName, yName, cor){
     var chart = Highcharts.chart(grafico, {
        chart: {
            type: 'column'
        },
        title: {
            text: nome
        },
        xAxis: {
            title: {
                text: xName
            }
        },
        yAxis: {
            title: {
                text: yName
            }
        },
        series: [{
            name: nome,
            data: dados,
            color: cor
        }]
    });
}

// Range
const frequenciaMin = document.getElementById("frequenciaMin");
const frequenciaMax = document.getElementById("frequenciaMax");

frequenciaMin.addEventListener("change", (event) => {
  frequenciaMax.min = frequenciaMin.value;
});

frequenciaMax.addEventListener("change", (event) => {
  frequenciaMin.max = frequenciaMax.value;
});

// Form envio dados simulação
const form = document.getElementById('formSimular');

form.addEventListener('submit', function(event) {
    
    event.preventDefault();
    
    if(!realizaValidacoes()){
        return;
    }
    
    var frequencia = document.getElementById("frequencia").value;
    var tipoSinal = document.getElementById("tipoSinal").value;
    var frequenciaMin = document.getElementById("frequenciaMin").value;
    var frequenciaMax = document.getElementById("frequenciaMax").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "SimulacaoController", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var resultado = JSON.parse(xhr.responseText);
                
                if(resultado.erro){
                    alert(`Erro ao obter os dados do sinal: ${resultado.erro}`);
                    console.error(`Erro ao obter os dados do sinal: ${resultado.erro}`);
                } else {
                    let indiceFase =  180 / Math.PI;
                    
                    atualizarGraficoLinha(resultado.entrada, 'sinal-entrada', 'Sinal de Entrada', 'Tempo (ms)', 'Amplitude', '#00c8ff');
                    atualizarGraficoBarras(resultado.moduloFrequenciaEntrada, 'modulo-frequencia-entrada', 'Módulo da Resposta em frequência Entrada', 'Frequência (kHz)', 'Amplitude', '#00c8ff');
                    atualizarGraficoBarras(resultado.faseFrequenciaEntrada.map(obj => ({ x: obj.x, y: obj.y * indiceFase })), 'fase-frequencia-entrada', 'Fase da Resposta em frequência Entrada', 'Frequência (kHz)', 'Fase (graus)', '#00c8ff');
                    
                    atualizarGraficoLinha(resultado.moduloRespostaCanal, 'modulo-resposta-canal', 'Modulo da resposta em Frequência', 'Frequência (kHz)', 'Amplitude', '#ff0055');
                    atualizarGraficoLinha(resultado.faseRespostaCanal.map(obj => ({ x: obj.x, y: obj.y * indiceFase })), 'fase-resposta-canal', 'Fase da resposta em Frequência', 'Frequência (kHz)', 'Fase (graus)', '#ff0055');
                    
                    atualizarGraficoLinha(resultado.saida, 'sinal-saida', 'Sinal de Saída', 'Tempo (ms)', 'Amplitude', '#2be331');
                    atualizarGraficoBarras(resultado.moduloFrequenciaSaida, 'modulo-frequencia-saida', 'Módulo da Resposta em frequência Saída', 'Frequência (kHz)', 'Amplitude', '#2be331');
                    atualizarGraficoBarras(resultado.faseFrequenciaSaida.map(obj => ({ x: obj.x, y: obj.y * indiceFase })), 'fase-frequencia-saida', 'Fase da Resposta em frequência Saída', 'Frequência (kHz)', 'Fase (graus)', '#2be331');
                                        
                    document.getElementById("btn-collapse-form").click();
                }
            } else {
                    alert(`Erro ao obter os dados do sinal: ${xhr.status}`);
                    console.error(`Erro ao obter os dados do sinal: ${xhr.status}`);
            }
        }
    };

    // Adicionando o parâmetro tipoSinal na requisição
    xhr.send(
            "frequencia=" + encodeURIComponent(frequencia) + 
            "&tipoSinal=" + encodeURIComponent(tipoSinal) + 
            "&frequenciaMin=" + encodeURIComponent(frequenciaMin) + 
            "&frequenciaMax=" + encodeURIComponent(frequenciaMax));
});

function realizaValidacoes(){
    var retorno = true;
    
    if(parseFloat(frequenciaMin.value) > parseFloat(frequenciaMax.value)){
        frequenciaMin.classList.add('input-obrigatorio');
        frequenciaMax.classList.add('input-obrigatorio');
        
        document.getElementById('rangeFrequenciaErro').textContent = " Intervalo inválido";
        retorno = false;
    } else{
        frequenciaMin.classList.remove('input-obrigatorio');
        frequenciaMax.classList.remove('input-obrigatorio');
        
        document.getElementById('rangeFrequenciaErro').textContent = "";
    }
    
    return retorno;
}
