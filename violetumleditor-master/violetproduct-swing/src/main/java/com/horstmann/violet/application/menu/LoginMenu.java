package com.horstmann.violet.application.menu;
import java.io.*;
import java.util.Scanner;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.ws.jaxme.sqls.impl.SetStatementImpl.SetValueImpl;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMenu {

	public JFrame frame;
	private JTextField userNameTextField;
	private JTextField passwordTextField;
	
	
	String userName;
	String passWord;
	
	boolean loginSucess = false;
	
	public JMenuItem pieChartMenuItem;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginMenu window = new LoginMenu();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public LoginMenu() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 269, 197);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(37, 26, 89, 27);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(39, 75, 65, 21);
		frame.getContentPane().add(lblPassword);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(99, 29, 86, 20);
		frame.getContentPane().add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(99, 75, 86, 20);
		frame.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				readFile("LoginUser.txt");
			}
		});
		btnLogin.setBounds(81, 124, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		//frame.setVisible(true);
	}
	
	
	public void readFile(String inFile)
	{
		Scanner scanner;
		
		try 
		{
			scanner = new Scanner(new FileReader(inFile));
			
			while (scanner.hasNextLine()) 
			{
			     userName = scanner.nextLine();
			     passWord = scanner.nextLine();
			    
			    
			    System.out.println(userName);
			    System.out.println(passWord);
			}
			
			if(userNameTextField.getText().equals(userName) && passwordTextField.getText().equals(passWord))
			{
				JOptionPane.showMessageDialog(frame, "Login Successful", "Success", JOptionPane.DEFAULT_OPTION);
				
				frame.setVisible(false);
				
				pieChartMenuItem.setEnabled(true);
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Access denied, please try again.", "Denied", JOptionPane.ERROR_MESSAGE);
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	}
	
}


