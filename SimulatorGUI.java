import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class SimulatorGUI extends JFrame {

    private JTextField memorySizeField;
    private JTextField queueSizeField;
    private JTextField uniquePagesField;
    private JTextField pageNamesField;
    private JTextField pageQueueField;
    private JTextField actionQueueField;
    private JTextField initialMemoryField;
    private JTextField clockInterruptField;
    private JTextField tauField;

    private JTextArea outputArea;

    public SimulatorGUI() {
        setTitle("Simulador de Substituição de Páginas");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(10, 2));

        memorySizeField = createFieldWithLabel(inputPanel, "Tamanho da memória:");
        queueSizeField = createFieldWithLabel(inputPanel, "Tamanho da fila de páginas:");
        uniquePagesField = createFieldWithLabel(inputPanel, "Quantidade de páginas únicas:");
        pageNamesField = createFieldWithLabel(inputPanel, "Nomes das páginas (ex: A|B|C):");
        pageQueueField = createFieldWithLabel(inputPanel, "Fila de páginas (ex: A|B|C|A):");
        actionQueueField = createFieldWithLabel(inputPanel, "Fila de ações (ex: L|E|L|L):");
        initialMemoryField = createFieldWithLabel(inputPanel, "Estado inicial da memória (ex: 0|0|0):");
        clockInterruptField = createFieldWithLabel(inputPanel, "Interrupção do relógio:");
        tauField = createFieldWithLabel(inputPanel, "Tau (τ):");

        JButton simulateButton = new JButton("Simular");
        simulateButton.addActionListener(this::runSimulation);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(simulateButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JTextField createFieldWithLabel(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField field = new JTextField();
        panel.add(label);
        panel.add(field);
        return field;
    }

    private void runSimulation(ActionEvent e) {
        try {
            int memorySize = Integer.parseInt(memorySizeField.getText());
            int queueSize = Integer.parseInt(queueSizeField.getText());
            int uniquePages = Integer.parseInt(uniquePagesField.getText());
            List<String> pageNames = Arrays.asList(pageNamesField.getText().split("\\|"));
            List<String> pageQueue = Arrays.asList(pageQueueField.getText().split("\\|"));
            List<String> actionQueue = Arrays.asList(actionQueueField.getText().split("\\|"));
            List<String> initialMemory = Arrays.asList(initialMemoryField.getText().split("\\|"));
            int clockInterrupt = Integer.parseInt(clockInterruptField.getText());
            int tau = Integer.parseInt(tauField.getText());

            PageReplacementSimulator simulator = new PageReplacementSimulator(
                    memorySize, queueSize, uniquePages, pageNames,
                    pageQueue, actionQueue, initialMemory, clockInterrupt, tau
            );

            String result = simulator.runSimulationsAndGetOutput();
            outputArea.setText(result);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }
}

