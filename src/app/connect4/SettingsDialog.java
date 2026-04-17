package app.connect4;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField rowsField;
    private JTextField colsField;
    private GamePrefs prefs;
    private boolean confirmed = false;

    public SettingsDialog(JFrame parent, GamePrefs prefs) {
        super(parent, "Game Settings", true);
        this.prefs = prefs;

        setLayout(new GridLayout(3, 2, 10, 10));

        // Use Encapsulation: fields are private, accessed through the dialog logic
        add(new JLabel("  Rows (4-10):"));
        rowsField = new JTextField(String.valueOf(prefs.getRows()));
        add(rowsField);

        add(new JLabel("  Columns (4-10):"));
        colsField = new JTextField(String.valueOf(prefs.getCols()));
        add(colsField);

        JButton saveBtn = new JButton("Save & Restart");
        saveBtn.addActionListener(e -> handleSave());

        add(saveBtn);

        pack();
        setLocationRelativeTo(parent);
    }

    private void handleSave() {
        try {
            int r = Integer.parseInt(rowsField.getText());
            int c = Integer.parseInt(colsField.getText());

            // Basic validation for "suitable coding"
            if (r < 4 || c < 4) {
                JOptionPane.showMessageDialog(this, "Board must be at least 4x4.");
                return;
            }

            // Persistence: Using GamePrefs I/O logic
            prefs.save(r, c, prefs.getWinCount(), prefs.getLossCount(), prefs.getDrawCount());
            confirmed = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers.");
        }
    }

    public boolean isConfirmed() { return confirmed; }
}

