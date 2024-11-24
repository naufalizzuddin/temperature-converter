import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TemperatureConverter {
    public double convert(double value, String fromUnit, String toUnit) {
        double celsius = switch (fromUnit) {
            case "Celsius" -> value;
            case "Fahrenheit" -> (value - 32) * 5 / 9;
            case "Reamur" -> value * 5 / 4;
            case "Kelvin" -> value - 273.15;
            default -> throw new IllegalArgumentException("Invalid source unit");
        };

        return switch (toUnit) {
            case "Celsius" -> celsius;
            case "Fahrenheit" -> (celsius * 9 / 5) + 32;
            case "Reamur" -> celsius * 4 / 5;
            case "Kelvin" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Invalid target unit");
        };
    }
}

class TemperatureConverterView extends JFrame {
    private JTextField inputField;
    private JComboBox<String> sourceUnitDropdown;
    private JComboBox<String> targetUnitDropdown;
    private JButton convertButton;
    private JLabel resultLabel;

    public TemperatureConverterView() {
        setTitle("Naufal Izzuddin Taufik 21120122140102");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(60, 99, 130));
        JLabel headerLabel = new JLabel("Temperature Converter");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Oswald", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(223, 249, 251));

        JLabel inputLabel = new JLabel("Enter Temperature:");
        inputLabel.setFont(new Font("Oswald", Font.PLAIN, 16));
        inputField = new JTextField();

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Oswald", Font.PLAIN, 16));
        String[] units = {"Celsius", "Fahrenheit", "Reamur", "Kelvin"};
        sourceUnitDropdown = new JComboBox<>(units);

        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Oswald", Font.PLAIN, 16));
        targetUnitDropdown = new JComboBox<>(units);

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(fromLabel);
        inputPanel.add(sourceUnitDropdown);
        inputPanel.add(toLabel);
        inputPanel.add(targetUnitDropdown);

        add(inputPanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(2, 1, 10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(new Color(223, 249, 251));

        convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(46, 204, 113));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFont(new Font("Oswald", Font.BOLD, 16));
        convertButton.setFocusPainted(false);

        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Oswald", Font.BOLD, 18));
        resultLabel.setForeground(new Color(52, 73, 94));

        resultPanel.add(convertButton);
        resultPanel.add(resultLabel);

        add(resultPanel, BorderLayout.SOUTH);
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JComboBox<String> getSourceUnitDropdown() {
        return sourceUnitDropdown;
    }

    public JComboBox<String> getTargetUnitDropdown() {
        return targetUnitDropdown;
    }

    public JButton getConvertButton() {
        return convertButton;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }
}

class TemperatureConverterController {
    private TemperatureConverter model;
    private TemperatureConverterView view;

    public TemperatureConverterController(TemperatureConverter model, TemperatureConverterView view) {
        this.model = model;
        this.view = view;

        this.view.getConvertButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        try {
            double inputTemperature = Double.parseDouble(view.getInputField().getText());
            String fromUnit = (String) view.getSourceUnitDropdown().getSelectedItem();
            String toUnit = (String) view.getTargetUnitDropdown().getSelectedItem();

            if (fromUnit.equals(toUnit)) {
                view.getResultLabel().setText("Result: " + inputTemperature);
                return;
            }

            double result = model.convert(inputTemperature, fromUnit, toUnit);

            view.getResultLabel().setText(String.format("Result: %.2f %s", result, toUnit));
        } catch (NumberFormatException ex) {
            view.getResultLabel().setText("Invalid input. Please enter a valid number.");
        } catch (IllegalArgumentException ex) {
            view.getResultLabel().setText("Conversion error: " + ex.getMessage());
        }
    }
}

public class TemperatureConverterApp {
    public static void main(String[] args) {
        TemperatureConverter model = new TemperatureConverter();
        TemperatureConverterView view = new TemperatureConverterView();

        new TemperatureConverterController(model, view);

        view.setVisible(true);
    }
}
