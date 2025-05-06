import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedList;

public class SimulatorUI extends JFrame {
    private JTextField memoryField;
    private JTextField queueField;
    private JTextField interruptField;
    private JTextArea resultArea;

    public SimulatorUI() {
        setTitle("Simulador de Substituição de Páginas");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Memória Inicial (ex: A,B,C):"));
        memoryField = new JTextField();
        inputPanel.add(memoryField);

        inputPanel.add(new JLabel("Fila de Requisições (ex: A,B,D,A,E):"));
        queueField = new JTextField();
        inputPanel.add(queueField);

        inputPanel.add(new JLabel("Clock Interrupt (ex: 4):"));
        interruptField = new JTextField();
        inputPanel.add(interruptField);

        JButton runButton = new JButton("Executar Simulação");
        runButton.addActionListener(this::runSimulation);
        inputPanel.add(runButton);

        add(inputPanel, BorderLayout.NORTH);

        // Área de saída
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void runSimulation(ActionEvent e) {
        try {
            LinkedList<String> memoryList = new LinkedList<>(Arrays.asList(memoryField.getText().split(",")));
            LinkedList<String> queueList = new LinkedList<>(Arrays.asList(queueField.getText().split(",")));
            int interrupt = Integer.parseInt(interruptField.getText().trim());

            Simulator simulator = new Simulator(interrupt, queueList, memoryList);
            String result = simulator.run();

            resultArea.setText(result);
        } catch (Exception ex) {
            resultArea.setText("Erro na entrada de dados. Verifique os campos e tente novamente.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimulatorUI::new);
    }
}
