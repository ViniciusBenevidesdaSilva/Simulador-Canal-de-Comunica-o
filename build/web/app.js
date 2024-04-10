// Gráfico de linha azul
Highcharts.chart('exemplo-entrada', {
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
        data: [0, 2, 0, -2, 0],
        name: 'Entrada'
    }]
});


// Simulação senoide
var sinWave = [];
for (var i = 0; i <= 4; i++) {
    sinWave.push(Math.sin(i*2));
}

// Gráfico de linha vermelho
Highcharts.chart('exemplo-saida', {
    title: {
        text: 'Sinal de Saída'
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
    series: [
        {
            data: sinWave,
            color: '#FF0000',
            name: 'Saída'
        }
    ]
});
    