import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        Random random = new Random(123);

        int[] tamanhosDaTabela = {100, 1000, 10000, 100000, 1000000};
        int[] tamanhosDosConjuntos = {20000, 100000, 500000, 1000000, 5000000};

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int tamanhoDaTabela : tamanhosDaTabela) {
            for (int tamanhoDoConjunto : tamanhosDosConjuntos) {
                TabelaHash tabelaDivisao = new TabelaHash(tamanhoDaTabela);
                TabelaHash tabelaMultiplicacao = new TabelaHash(tamanhoDaTabela);
                TabelaHash tabelaDobramento = new TabelaHash(tamanhoDaTabela);

                for (int i = 0; i < tamanhoDoConjunto; i++) {
                    int chave = random.nextInt(1000000000);
                    Registro registro = new Registro(chave);

                    tabelaDivisao.inserir(registro, tabelaDivisao.funcaoHashDivisao(chave));
                    tabelaMultiplicacao.inserir(registro, tabelaMultiplicacao.funcaoHashMultiplicacao(chave));
                    tabelaDobramento.inserir(registro, tabelaDobramento.funcaoHashDobramento(chave));
                }

                int colisoesDivisao = tabelaDivisao.contarColisoes(tamanhoDaTabela);
                int colisoesMultiplicacao = tabelaMultiplicacao.contarColisoes(tamanhoDaTabela);
                int colisoesDobramento = tabelaDobramento.contarColisoes(tamanhoDaTabela);

                dataset.addValue(colisoesDivisao, "Colisões (Divisão)", String.format("Tabela %d - Conjunto %d", tamanhoDaTabela, tamanhoDoConjunto));
                dataset.addValue(colisoesMultiplicacao, "Colisões (Multiplicação)", String.format("Tabela %d - Conjunto %d", tamanhoDaTabela, tamanhoDoConjunto));
                dataset.addValue(colisoesDobramento, "Colisões (Dobramento)", String.format("Tabela %d - Conjunto %d", tamanhoDaTabela, tamanhoDoConjunto));
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Resultados de Colisões na Tabela Hash",
                "Experimentos",
                "Número de Colisões",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame frame = new JFrame("Tabela Hash - Resultados Gráficos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}