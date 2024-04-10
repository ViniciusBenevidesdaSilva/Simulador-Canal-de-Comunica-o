
function atualizarGrafico(pontosDaOndaQuadrada) {
    var chart = Highcharts.chart('sinal-entrada', {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Sinal de Entrada'
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
            name: 'Onda Quadrada',
            data: pontosDaOndaQuadrada
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
    
    if(parseFloat(frequenciaMin.value) > parseFloat(frequenciaMax.value)){
        frequenciaMin.classList.add('input-obrigatorio');
        frequenciaMax.classList.add('input-obrigatorio');
        
        document.getElementById('rangeFrequenciaErro').textContent = " Intervalo inválido";
        return;
    } else{
        frequenciaMin.classList.remove('input-obrigatorio');
        frequenciaMax.classList.remove('input-obrigatorio');
        
        document.getElementById('rangeFrequenciaErro').textContent = "";
    }
    
    var frequencia = document.getElementById("frequencia").value;
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "SimulacaoController", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var pontosDaOndaQuadrada = JSON.parse(xhr.responseText);
                atualizarGrafico(pontosDaOndaQuadrada);
            } else {
                console.error("Erro ao obter os dados da onda quadrada.");
            }
        }
    };
    xhr.send("frequencia=" + encodeURIComponent(frequencia));
});
