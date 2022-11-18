package uaspbo;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import Model.CategoryUser;
import Model.User;

public class UAS {
    public static void mainMenu() {
        JFrame mainMenuForm = new JFrame();
        mainMenuForm.setSize(350, 400);
        mainMenuForm.setTitle("Main Menu");
        mainMenuForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel labelMainMenu = new JLabel("Main Menu");
        labelMainMenu.setFont(new Font("Calibri", Font.BOLD, 30));
        labelMainMenu.setBounds(102, 30, 145, 35);
        mainMenuForm.add(labelMainMenu);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(114, 100, 125, 35);
        mainMenuForm.add(btnLogin);

        btnLogin.addActionListener(e ->
        {
            mainMenuForm.dispose();
            formRegister();
        });

        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(114, 150, 125, 35);
        mainMenuForm.add(btnRegister);

        btnRegister.addActionListener(e ->
        {
            mainMenuForm.dispose();
            formRegister();
        });

        JButton btnPerekaman = new JButton("Lihat Data");
        btnPerekaman.setBounds(114, 200, 125, 35);
        mainMenuForm.add(btnPerekaman);

        btnPerekaman.addActionListener(e ->
        {
            mainMenuForm.dispose();
            formRegister();
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(114, 250, 125, 35);
        mainMenuForm.add(btnExit);

        btnExit.addActionListener(e ->
        {
            System.exit(0);
        });

        mainMenuForm.setLayout(null);  
        mainMenuForm.setVisible(true);
    }

    public static void formRegister() {
        Controller controller =  new Controller();
        ArrayList<CategoryUser> userCategory = controller.getCategoryUser();

        JFrame inputForm = new JFrame();
        inputForm.setSize(500, 500);
        inputForm.setTitle("Form Register");
        
        JLabel labelJudul = new JLabel("Register");
        labelJudul.setFont(new Font("Calibri", Font.BOLD, 30));
        labelJudul.setBounds(190, 30, 145, 35);
        inputForm.add(labelJudul);

        inputForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                inputForm.dispose();
                mainMenu();
            }
        });

        //Email
        JLabel labelEmail = new JLabel("Email: ");
        labelEmail.setBounds(105, 85, 100, 25);
        JTextField inputEmail = new JTextField();
        inputEmail.setBounds(200, 85, 150, 25);
        inputForm.add(labelEmail);
        inputForm.add(inputEmail);

        //Username
        JLabel labelUsername = new JLabel("Username: ");
        labelUsername.setBounds(105, 125, 100, 25);
        JTextField inputUsername = new JTextField();
        inputUsername.setBounds(200, 125, 150, 25);
        inputForm.add(labelUsername);
        inputForm.add(inputUsername);

        //Kelamin
        JLabel labelKelamin = new JLabel("Kelamin: ");
        labelKelamin.setBounds(105, 165, 100, 25);
        
        JRadioButton pria = new JRadioButton("Pria");
        pria.setBounds(200, 165, 50, 25);
        JRadioButton wanita = new JRadioButton("Wanita");
        wanita.setBounds(255, 165, 100, 25);
        ButtonGroup bGroupKelamin = new ButtonGroup();
        bGroupKelamin.add(pria);
        bGroupKelamin.add(wanita);
        inputForm.add(labelKelamin);
        inputForm.add(pria);
        inputForm.add(wanita);

        //UserCategory
        JLabel labelKategori = new JLabel("Kategori User: ");
        labelKategori.setBounds(105, 205, 100, 25);
        String[] tempKategori = new String[userCategory.size()];
        for (int i = 0; i < userCategory.size(); i++) {
            tempKategori[i] = userCategory.get(i).getCategoryName();
        }
        JComboBox inpKategori = new JComboBox(tempKategori);
        inpKategori.setBounds(200, 205, 150, 25);
        inputForm.add(labelKategori);
        inputForm.add(inpKategori);

        //Password
        JLabel labelPassword = new JLabel("Password: ");
        labelPassword.setBounds(105, 245, 100, 25);
        JPasswordField inpPassword = new JPasswordField();
        inpPassword.setBounds(200, 245, 150, 25);
        inputForm.add(labelPassword);
        inputForm.add(inpPassword);

        JButton bRegister = new JButton("Register");
        bRegister.setBounds(110, 345, 100, 35);
        inputForm.add(bRegister);
        
        bRegister.addActionListener(e ->
        {
            User newUser = new User();
            //Username Email
            newUser.setUserName(inputUsername.getText());
            newUser.setUserEmail((inputEmail.getText()));

            //Gender
            if(pria.isSelected()) { //Kelamin
                newUser.setUserGender("Pria");
            } else if(wanita.isSelected()) {
                newUser.setUserGender("Wanita");
            }

            //Category
            String kategoriTemp = (String) inpKategori.getItemAt(inpKategori.getSelectedIndex());
            for (int i = 0; i < tempKategori.length; i++) {
                if (userCategory.get(i).getCategoryName() == kategoriTemp) {
                    newUser.setUserCategory(userCategory.get(i));
                }
            }

            //Id Followers
            newUser.setUserId(0);
            newUser.setUserFollowers(0);

            if (cekTerisi(newUser)) {
                controller.insertNewUser(newUser);
                JOptionPane.showMessageDialog(null, "Register Berhasil");
            } else {
                JOptionPane.showMessageDialog(null, "Register Gagal"); 
            }
        });

        //Button Back
        JButton bBack = new JButton("Back");
        bBack.setBounds(240, 345, 100, 35);
        inputForm.add(bBack);
        
        bBack.addActionListener(e ->
        {
            inputForm.dispose();
            mainMenu();
        });

        inputForm.setLayout(null);  
        inputForm.setVisible(true);
    }
    public static boolean cekTerisi(User user) {
        if (!user.getUserName().equals("") && !user.getUserEmail().equals("")
            && !user.getUserGender().equals("") && user.getUserCategory() != null) {
            return true;
        }
        return false;
    }

    public static boolean cekDataDuplikat(User user) {
        Controller controller = new Controller();
        ArrayList<User> cekUser = controller.getUser();

        for (int i = 0; i < cekUser.size(); i++) {
            if (user.getUserEmail().equals(cekUser.get(i).getUserEmail())) {
                return false;
            }
            if (user.getUserEmail().equals(cekUser.get(i).getUserName())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        mainMenu();
    }
}