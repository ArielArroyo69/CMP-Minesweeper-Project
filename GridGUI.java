import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridGUI extends JFrame {
    private Grid grid;
    private JButton[][] buttons;
    private boolean[][] revealed;
    private int safeGrid = 0;

    public GridGUI(Grid grid) {
        this.grid = grid;
        this.buttons = new JButton[grid.getNumRows()][grid.getNumColumns()];
        this.revealed = new boolean[grid.getNumRows()][grid.getNumColumns()];

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setSize(500, 500);
        setLayout(new GridLayout(grid.getNumRows(), grid.getNumColumns()));

        createButtons();
    }

    private void createButtons() {
        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j = 0; j < grid.getNumColumns(); j++) {
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(i, j));
                buttons[i][j] = button;
                add(button);
            }
        }
    }

    private void revealCell(int row, int col) {
        if (!revealed[row][col]) {
            revealed[row][col] = true;
            buttons[row][col].setText(Integer.toString(grid.getCountAtLocation(row, col)));
            if (grid.getCountAtLocation(row, col) == 0) {
                for (int i = Math.max(0, row - 1); i <= Math.min(grid.getNumRows() - 1, row + 1); i++) {
                    for (int j = Math.max(0, col - 1); j <= Math.min(grid.getNumColumns() - 1, col + 1); j++) {
                        revealCell(i, j);
                    }
                }
            }
        }
    }

    private void revealGrid() {
        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j = 0; j < grid.getNumColumns(); j++) {
                if (grid.isBombAtLocation(i, j)) {
                    buttons[i][j].setText("*");
                } else {
                    buttons[i][j].setText(Integer.toString(grid.getCountAtLocation(i, j)));
                }
            }
        }
    }

    private void playAgain() {
        int choice = JOptionPane.showConfirmDialog(GridGUI.this, "Play again?");
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new GridGUI(new Grid(grid.getNumRows(), grid.getNumColumns(), grid.getNumBombs()));
        } else {
            dispose();
        }
    }

    private class ButtonListener implements ActionListener {
        private int row;
        private int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (grid.isBombAtLocation(row, col)) {
                revealGrid();
                JOptionPane.showMessageDialog(GridGUI.this, "You lost!");
                playAgain();
            } else {
                revealCell(row, col);
                safeGrid++;
                int gridSize = grid.getNumRows() * grid.getNumColumns();
                int totalSafeGrid = gridSize - grid.getNumBombs();
                if (safeGrid == totalSafeGrid) {
                    JOptionPane.showMessageDialog(GridGUI.this, "You won!");
                    playAgain();
                }
            }
        }
    }

    public static void main(String[] args) {
        new GridGUI(new Grid(10, 10, 10)).setVisible(true);
    }
}
