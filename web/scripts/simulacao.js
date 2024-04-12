
function atualizarGraficoLinha(dados, grafico, nome) {
    var chart = Highcharts.chart(grafico, {
        chart: {
            type: 'line'
        },
        title: {
            text: nome
        },
        xAxis: {
            title: {
                text: 'Tempo'
            }
        },
        yAxis: {
            title: {
                text: 'Amplitude'
            }
        },
        series: [{
            name: nome,
            data: dados
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
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "SimulacaoController", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var resultado = JSON.parse(xhr.responseText);
                
                if(resultado.erro){
                    console.error("Erro ao obter os dados do sinal.");
                } else {
                    atualizarGraficoLinha(resultado.entrada, 'sinal-entrada', 'Sinal de Entrada');
                }
            } else {
                console.error("Erro ao obter os dados do sinal.");
            }
        }
    };

    // Adicionando o parâmetro tipoSinal na requisição
    xhr.send("frequencia=" + encodeURIComponent(frequencia) + "&tipoSinal=" + encodeURIComponent(tipoSinal));
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