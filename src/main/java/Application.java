import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Application {

    private JFrame frame;
    private Insets insets;
    private JPanel introPanel;
    private JPanel leftSortPanel;
    private JTextField textField;
    private JPanel rightSortPanel;
    private JButton[] buttonsSortArr;
    private int[] generatedRandomNumbers;
    private Boolean isNumberArrayReversed;
    private GridBagConstraints gbcForSortButtons;

    Application() {

        frame = new JFrame("Gran-soft test task");
        insets = new Insets(8, 8, 8, 8);
        generatedRandomNumbers = new int[0];
        buttonsSortArr = new JButton[0];
        isNumberArrayReversed = false;
        gbcForSortButtons = new GridBagConstraints(
                0, 0, 1, 1, 0.001, 0.001,
                GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
                insets, 20, 7);

        createIntroJPanel();
        createRightSortPanel();
        createLeftSortPanel();

        frame.add(rightSortPanel, BorderLayout.EAST);
        frame.add(leftSortPanel, BorderLayout.WEST);
        frame.add(introPanel);
        frame.setSize(1025, 530);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Application();
    }

    private void createLeftSortPanel() {
        leftSortPanel = new JPanel();
        leftSortPanel.setLayout(new GridBagLayout());
        leftSortPanel.setVisible(false);
    }

    private void createRightSortPanel() {
        rightSortPanel = new JPanel();
        rightSortPanel.setLayout(new GridBagLayout());
        rightSortPanel.setVisible(false);

        createControlButtons();
    }


    private void createControlButtons() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints(
                0, 0, 1, 1, 0.001, 0.001,
                GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
                insets, 20, 7);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            quickSort(generatedRandomNumbers, 0, generatedRandomNumbers.length - 1);
            if (!isNumberArrayReversed) {
                reverseNumbersArray();
            }
            updateSortButtonsArr();
            isNumberArrayReversed = !isNumberArrayReversed;
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            isNumberArrayReversed = false;
            generatedRandomNumbers = new int[0];

            leftSortPanel.removeAll();
            introPanel.setVisible(true);
            leftSortPanel.setVisible(false);
            rightSortPanel.setVisible(false);
        });

        rightSortPanel.add(sortButton, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 10;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        rightSortPanel.add(resetButton, gridBagConstraints);

    }

    private int[] generateRandomNumbers(int enteredInt) {
        int[] randomNumbers = new int[enteredInt];
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = (int) (Math.random() * 1001);
        }
        setLowestNumberInGeneratedArray(randomNumbers);
        return randomNumbers;
    }

    private void setLowestNumberInGeneratedArray(int[] randomNumbers) {
        int lowestNumber = randomNumbers[0];
        for (int i = 0; i < randomNumbers.length; i++) {
            lowestNumber = randomNumbers[i] < lowestNumber ? randomNumbers[i] : lowestNumber;
            if (lowestNumber < 31) {
                return;
            }
        }
        randomNumbers[0] = (int) (Math.random() * 31);
    }

    private void updateGeneratedRandomArray(int[] randomNumbers) {
        int[] resultArray = new int[generatedRandomNumbers.length + randomNumbers.length];
        System.arraycopy(generatedRandomNumbers, 0, resultArray, 0, generatedRandomNumbers.length);
        System.arraycopy(randomNumbers, 0, resultArray, generatedRandomNumbers.length, randomNumbers.length);
        generatedRandomNumbers = resultArray;
    }

    private void createButtonsTable(int enteredInt) {
        int[] randomNumbers = generateRandomNumbers(enteredInt);
        updateGeneratedRandomArray(randomNumbers);
        updateSortButtonsArr();
        attachButtonsToLeftSortPanel();
        frame.validate();
        frame.repaint();
    }

    private void updateSortButtonsArr() {

        buttonsSortArr = Arrays.copyOf(buttonsSortArr, generatedRandomNumbers.length);
        ActionListener actionListener = createListenerForSortButtons();

        for (int i = 0; i < generatedRandomNumbers.length; i++) {
            String buttonNumbers = String.valueOf(generatedRandomNumbers[i]);
            if (buttonsSortArr[i] != null) {
                buttonsSortArr[i].setText(buttonNumbers);
            } else {
                buttonsSortArr[i] = new JButton(buttonNumbers);
                buttonsSortArr[i].addActionListener(actionListener);
            }
        }
    }

    private ActionListener createListenerForSortButtons() {
        return e -> {
            JButton button = (JButton) e.getSource();
            int enteredInt = Integer.parseInt(button.getText());
            if (enteredInt > 31) {
                JOptionPane.showMessageDialog(null, "Please select a value smaller or equal to 30.”");
            } else if (enteredInt == 0) {

            } else {
                createButtonsTable(enteredInt);
            }
        };
    }

    private void attachButtonsToLeftSortPanel() {
        int count = 0;
        for (int i = 0; i <= generatedRandomNumbers.length / 10; i++) {
            int numberOfRows = i == generatedRandomNumbers.length / 10 ? generatedRandomNumbers.length % 10 : 10;

            for (int j = 0; j < numberOfRows; j++) {
                gbcForSortButtons.gridy = j;

                if (j == numberOfRows - 1
                        && i != generatedRandomNumbers.length / 10
                        || i == 0) {
                    gbcForSortButtons.anchor = GridBagConstraints.FIRST_LINE_START;
                    gbcForSortButtons.weighty *= 100;
                }
                leftSortPanel.add(buttonsSortArr[count], gbcForSortButtons);
                count++;
            }
            gbcForSortButtons.anchor = GridBagConstraints.LINE_START;
            gbcForSortButtons.weightx *= 100;
            gbcForSortButtons.weighty = 0.001;
            gbcForSortButtons.gridx++;
            gbcForSortButtons.gridy = 0;
        }
    }

    private void createIntroJPanel() {
        introPanel = new JPanel();
        introPanel.setVisible(true);
        introPanel.setLayout(new GridBagLayout());

        introPanel.add(new JLabel("How many numbers to display?"), new GridBagConstraints(
                GridBagConstraints.RELATIVE, 0, 0, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 0, 7));

        createIntroTextField();
        introPanel.add(textField, new GridBagConstraints(
                GridBagConstraints.RELATIVE, 1, 0, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 100, 5));

        JButton introButton = new JButton("Enter");
        introButton.addActionListener(e -> {
            if (textField.getText().length() != 0) {
                introPanel.setVisible(false);
                createButtonsTable(Integer.parseInt(textField.getText()));
                leftSortPanel.setVisible(true);
                rightSortPanel.setVisible(true);
            }
        });

        introPanel.add(introButton, new GridBagConstraints(
                GridBagConstraints.RELATIVE, 2, 0, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 40, 5));
    }


    private void createIntroTextField() {
        textField = new JTextField();
        textField.grabFocus();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0'
                        && ke.getKeyChar() <= '9'
                        && Integer.parseInt(textField.getText() + ke.getKeyChar()) < 101
                        && Integer.parseInt(textField.getText() + ke.getKeyChar()) > 0
                        || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE
                        || ke.getKeyChar() == KeyEvent.VK_DELETE) {
                    textField.setEditable(true);
                } else {
                    textField.setEditable(false);
                }
            }
        });
    }

    private void reverseNumbersArray() {
        for (int i = 0; i < generatedRandomNumbers.length / 2; i++) {
            int temp = generatedRandomNumbers[i];
            generatedRandomNumbers[i] = generatedRandomNumbers[generatedRandomNumbers.length - i - 1];
            generatedRandomNumbers[generatedRandomNumbers.length - i - 1] = temp;
        }
    }

    private void quickSort(int[] source, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        int pivot = source[(leftMarker + rightMarker) / 2];
        do {
            while (source[leftMarker] < pivot) {
                leftMarker++;
            }
            while (source[rightMarker] > pivot) {
                rightMarker--;
            }
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    int tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        if (leftMarker < rightBorder) {
            quickSort(source, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            quickSort(source, leftBorder, rightMarker);
        }
    }
}
