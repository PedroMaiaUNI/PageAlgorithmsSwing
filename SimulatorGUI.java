import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class SimulatorGUI extends JFrame {

    private JTextField memorySizeField;
    private JTextField pageQueueField;
    private JTextField initialMemoryField;
    private JTextField clockInterruptField;

    private JTextArea outputArea;

    public SimulatorGUI() {
        setTitle("Simulador de Substituição de Páginas");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(10, 2));

        memorySizeField = createFieldWithLabel(inputPanel, "Tamanho da memória:");      
        pageQueueField = createFieldWithLabel(inputPanel, "Fila de páginas (ex: A|B|C|A):");
        initialMemoryField = createFieldWithLabel(inputPanel, "Estado inicial da memória (ex: 0|0|0):");
        clockInterruptField = createFieldWithLabel(inputPanel, "Interrupção do relógio:");
        
        memorySizeField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateInitialMemory();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateInitialMemory();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateInitialMemory();
            }
        });  

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

    private void updateInitialMemory() {
        try {
            int size = Integer.parseInt(memorySizeField.getText().trim());
            if (size > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append("0");
                    if (i < size - 1) {
                        sb.append("|");
                    }
                }
                initialMemoryField.setText(sb.toString());
            }
        } catch (NumberFormatException ex) {
            // Try e Catch necessários, ou o programa ficaria travando
        }
    }
    

    private void runSimulation(ActionEvent e) {
        try {
            int memorySize = Integer.parseInt(memorySizeField.getText());
            List<String> pageQueue = Arrays.asList(pageQueueField.getText().split("\\|"));
            List<String> initialMemory = Arrays.asList(initialMemoryField.getText().split("\\|"));
            int clockInterrupt = Integer.parseInt(clockInterruptField.getText());

            PageReplacementSimulator simulator = new PageReplacementSimulator(
                                                memorySize, pageQueue, initialMemory, clockInterrupt);

            String result = simulator.runSimulations();
            outputArea.setText(result);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }
}

