package com.example.Frontend;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.example.Backend.Main;

public class Mainpage {
    public static void Mainpage(int refreshSpeed) {
        JFrame frame = new JFrame("输入验证");
        frame.setSize(400, 300); // 设置窗口大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2)); // 布局，5行2列

        // 创建标签
        JLabel rvInputLabel = new JLabel("请输入 RV (格式: RVXXXXXXXX):");
        JLabel rvTimeLabel = new JLabel("请输入时间 (格式: HH:mm 或 整点):");
        JLabel rvTimeBackupLabel = new JLabel("请输入备份时间 (格式: HH:mm 或 整点, 可为空):");

        // 创建输入框
        JTextField rvInputField = new JTextField();
        JTextField rvTimeField = new JTextField();
        JTextField rvTimeBackupField = new JTextField();
        rvTimeBackupField.setBackground(Color.GRAY);

        // 创建按钮
        JButton startButton = new JButton("开始验证");

        // 按钮点击事件
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入
                String rvInput = rvInputField.getText().trim();
                String rvTime = rvTimeField.getText().trim();
                //getText()==> if empty will return String "";
                String rvTimeBackup = rvTimeBackupField.getText().trim();
                System.out.println("Click start all good");
                
                Main.main(rvInput, rvTime, rvTimeBackup, refreshSpeed);
            }
        });

        // 将标签、输入框和按钮添加到窗口
        frame.add(rvInputLabel);
        frame.add(rvInputField);
        frame.add(rvTimeLabel);
        frame.add(rvTimeField);
        frame.add(rvTimeBackupLabel);
        frame.add(rvTimeBackupField);
        frame.add(new JLabel()); // 占位符
        frame.add(startButton);

        // 设置窗口在屏幕中间
        frame.setLocationRelativeTo(null);

        // 设置窗口可见
        frame.setVisible(true);
    }
}
