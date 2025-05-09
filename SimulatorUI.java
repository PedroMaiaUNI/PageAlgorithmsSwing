import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedList;

public class SimulatorUI extends JFrame {
    private JTextField memorySizeField;
    private JTextField memoryField;
    private JTextField queueField;
    private JTextField interruptField;
    private JTextArea resultArea;

    public SimulatorUI() {
        setTitle("Simulador de Substituição de Páginas");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela

        // Painel principal com BoxLayout para centralização vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Tamanho da Memória:"));
        memorySizeField = new JTextField();
        memorySizeField.addActionListener(e -> autoFillMemory());
        inputPanel.add(memorySizeField);

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

        mainPanel.add(inputPanel);

        // Área de resultado
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(scrollPane);

        add(mainPanel);
        setVisible(true);
    }

    private void autoFillMemory() {
        try {
            int size = Integer.parseInt(memorySizeField.getText().trim());
            if (size <= 0) return;

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append("0");
                if (i < size - 1) sb.append(",");
            }
            memoryField.setText(sb.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tamanho inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
