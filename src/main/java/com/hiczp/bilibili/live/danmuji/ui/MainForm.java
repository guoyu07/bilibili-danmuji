package com.hiczp.bilibili.live.danmuji.ui;

import com.hiczp.bilibili.live.danmu.api.LiveDanMuAPI;
import com.hiczp.bilibili.live.danmuji.LiveDanMuCallback;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created by czp on 17-5-31.
 */
public class MainForm extends JFrame {
    private static final String BILIBILI_LIVE_URL_PREFIX = "http://live.bilibili.com/";

    private JPanel mainFormJPanel;
    private JTextField textField;
    private JButton startButton;
    private JButton stopButton;
    private JTextPane textPane;

    private LiveDanMuAPI liveDanMuAPI;
    private StyledDocument styledDocument;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    public MainForm() {
        styledDocument = textPane.getStyledDocument();

        startButton.addActionListener(actionEvent -> {
            try {
                liveDanMuAPI = new LiveDanMuAPI(BILIBILI_LIVE_URL_PREFIX + textField.getText())
                        .setPrintDebugInfo(true)
                        .addCallback(new LiveDanMuCallback(this, textPane))
                        .connect();
                printInfo("Connect succeed!");
                textField.setEnabled(false);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            } catch (IOException | IllegalArgumentException e) {
                printInfo("%s: %s", e.getClass().getName(), e.getMessage());
                printInfo("Connect failed!");
                e.printStackTrace();
            }
        });

        stopButton.addActionListener(actionEvent -> {
            try {
                liveDanMuAPI.close();
            } catch (IOException e) {
                printInfo("%s: %s", e.getClass().getName(), e.getMessage());
                printInfo("Cannot close connection, reopen program may solve this problem.");
                e.printStackTrace();
            } finally {
                stopButton.setEnabled(false);
                textField.setEnabled(true);
                startButton.setEnabled(true);
            }
        });

        setContentPane(mainFormJPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void printInfo(String message, Object... objects) {
        try {
            styledDocument.insertString(styledDocument.getLength(), String.format("[%s] ", new Date()) + String.format(message, objects) + "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainFormJPanel = new JPanel();
        mainFormJPanel.setLayout(new GridLayoutManager(2, 4, new Insets(5, 5, 5, 5), -1, -1));
        textField = new JTextField();
        textField.setText("");
        mainFormJPanel.add(textField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("http://live.bilibili.com/");
        mainFormJPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        mainFormJPanel.add(startButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setEnabled(false);
        stopButton.setText("Stop");
        mainFormJPanel.add(stopButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainFormJPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 300), null, 0, false));
        textPane = new JTextPane();
        textPane.setEditable(false);
        scrollPane1.setViewportView(textPane);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainFormJPanel;
    }
}
