import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Image;


public class BMICalculator {
	
	static JTextField heightInput;
	static JTextField weightInput;
	static JComboBox heightUnitSelector;
	static JComboBox weightUnitSelector;
	static JLabel outputText = new JLabel();
	static JLabel outputResult = new JLabel();
	
	static int HEIGHT = 600;
	static int WIDTH = 400;
	
	static void calculateBMI() {
		float heightFinal = 0;
		float weightFinal = 0;
		float BMI = 0;
		
		String heightInputValue = heightInput.getText();
		String weightInputValue = weightInput.getText();
		String heightUnit = (String) heightUnitSelector.getSelectedItem();
		String weightUnit = (String) weightUnitSelector.getSelectedItem();
		String errorText = " ";
		
		//testing if input works
		//System.out.println(heightInputValue + " " + heightUnit + "  " + weightInputValue + " " + weightUnit);
		
		if(heightInputValue.isEmpty() || (weightInputValue.isEmpty())) {
			heightInputValue = "0"; 
			weightInputValue = "0";
		}
		
		if(Pattern.matches("^\\d*\\.?\\d*$", heightInputValue) && Pattern.matches("^\\d*\\.?\\d*$", weightInputValue)) {
			heightFinal = Float.parseFloat(heightInputValue);
			weightFinal = Float.parseFloat(weightInputValue);
			//System.out.println("--Converted to float--");
		}else {
			errorText = "Invalid Input";
			showError(errorText);
			showResult("");
			heightInput.requestFocusInWindow();
			return;
		}
		
		//convert the units to standard kg and m
		if(heightUnit.equals("cm")) heightFinal /= 100;
		if(weightUnit.equals("lb")) weightFinal *= 0.453;
		
		//System.out.println(heightFinal + " " + weightFinal);
		
		if (!(heightFinal == 0 && weightFinal == 0)) {
			if(0.54 < heightFinal && heightFinal < 2.72) {
				if(5 < weightFinal && weightFinal < 500) {
					//write code to setText() the BMI and result
					BMI = (weightFinal)/(heightFinal*heightFinal);
					//System.out.println(BMI);
					errorText = BMI + "";
					showError("");
					outputText.setText("Your BMI is:    " + errorText);
					outputText.setFont(new Font("Calibri", Font.BOLD, 20));
					outputText.setForeground(Color.BLACK);
				}
				else {
					//write code to incorrect weight value
					errorText = "Incorrect weight";
					showError(errorText);
					showResult("");
					weightInput.requestFocusInWindow();
					return;
					//System.out.println(errorText);
				}
			}
			else {
				//write code to setText() incorrect height
				errorText = "Incorrect height";
				showError(errorText);
				showResult("");
				heightInput.requestFocusInWindow();
				return;
				//System.out.println(errorText);
			}
		}else {
			errorText = "Can't be zero";
			showError(errorText);
			showResult("");
			heightInput.requestFocusInWindow();
			return;
			//System.out.println(errorText);
		}
		
		if(BMI <= 18.5) {
			//System.out.println("You are: " + "Underweight");
			showResult("You are:     " + "Underweight");
		} else if(18.5 < BMI && BMI < 24.9) {
			//System.out.println("You are: " + "Normal");
			showResult("You are:     " + "Normal");
		} else if(25.0 < BMI && BMI < 29.9) {
			//System.out.println("You are: " + "Overweight");
			showResult("You are:     " + "Overweight");
		} else {
			//System.out.println("You are: " + "Obese");
			showResult("You are:     " + "Obese");
		}
		
	}
	
	protected static void resetFields() {
		heightInput.setText(null);
		weightInput.setText(null);
		outputText.setText(null);
		outputResult.setText(null);
		heightInput.requestFocusInWindow();
		//System.out.println("Clear!");
	}
	
	protected static void showError(String err) {
		outputText.setText("Error:   " + err);
		outputText.setFont(new Font("Calibri", Font.ITALIC, 20));
		outputText.setForeground(Color.RED);
		outputText.setBounds(WIDTH/2-20, 260+20, 250, 20);
	}
	
	protected static void showResult(String result) {
		outputResult.setText(result);
		outputResult.setFont(new Font("Calibri", Font.PLAIN, 18));
		outputResult.setForeground(Color.DARK_GRAY);
		outputResult.setBounds(WIDTH/2+20, 280+22, 250, 20);
		heightInput.requestFocusInWindow();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("BMI Calculator");
		
		ImageIcon imgIcon = new ImageIcon("bmiLogo.png");
		Image iconLogo = imgIcon.getImage();

		JLabel bmiCalcLabel = new JLabel("BMI Calculator");
		JLabel heightLabel = new JLabel("Height:");
		JLabel weightLabel = new JLabel("Weight:");
		JLabel underline = new JLabel("_____________");
		
		Font defaultFont = new Font("Calibri", Font.BOLD, 22);
		
		bmiCalcLabel.setFont(defaultFont);
		bmiCalcLabel.setBounds(WIDTH/2 + 20, 0, 500, 100);
		underline.setFont(defaultFont);
		underline.setBounds(WIDTH/2+15, 50, 500, 20);
		heightLabel.setBounds(WIDTH-200-35, 120-10, 500, 30);
		heightLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
		weightLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
		weightLabel.setBounds(WIDTH-200-35, 160-10, 500, 30);
		
		heightInput = new JTextField();
		weightInput = new JTextField();
		
		heightInput.setBounds(WIDTH-130-35, 120-10, 100, 30);
		weightInput.setBounds(WIDTH-130-35, 160-10, 100, 30);
		
		String[] heightUnits = {"m","cm"};
		String[] weightUnits = {"kg","lb"};
		
		heightUnitSelector = new JComboBox(heightUnits);
		heightUnitSelector.setSelectedIndex(1);
		heightUnitSelector.setBounds(WIDTH-20-35, 120-10, 50, 28);
		
		weightUnitSelector = new JComboBox(weightUnits);
		weightUnitSelector.setSelectedIndex(0);
		weightUnitSelector.setBounds(WIDTH-20-35, 150, 50, 28);
		
		JButton resetBtn = new JButton("Reset");
		JButton submitBtn = new JButton("Submit");
		submitBtn.setBounds(WIDTH-200, 200, 80, 35);
		resetBtn.setBounds(WIDTH-110, 200, 80, 35);
		
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submitBtn) {
					calculateBMI();
				}
				else if (e.getSource() == resetBtn) {
					resetFields();
				}
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == resetBtn) {
					resetFields();
				}
			}
		});
		
		heightInput.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	               weightInput.requestFocusInWindow();
	            }
	        }

	    });
		
		weightInput.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	               submitBtn.doClick();
	            }
	        }

	    });
		
		frame.setIconImage(iconLogo);
		frame.add(bmiCalcLabel);
		frame.add(heightLabel);
		frame.add(weightLabel);
		frame.add(underline);
		frame.add(heightInput);
		frame.add(weightInput);
		frame.add(submitBtn);
		frame.add(resetBtn);
		frame.add(heightUnitSelector);
		frame.add(weightUnitSelector);
		frame.add(outputText);
		frame.add(outputResult);
		frame.setSize(HEIGHT, WIDTH);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
