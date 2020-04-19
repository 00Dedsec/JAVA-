package CQU.ex2_demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


//import Event;

public class App extends JFrame implements ActionListener
{
	private JPanel keyBoard = new JPanel();
	private JTextField tfAnswer = new JTextField();
	
	private Calculator cal = new Calculator();
	
	private JButton button1 = new JButton("1");
	private JButton button2 = new JButton("2");
	private JButton button3 = new JButton("3");
	private JButton button4 = new JButton("4");
	private JButton button5 = new JButton("5");
	private JButton button6 = new JButton("6");
	private JButton button7 = new JButton("7");
	private JButton button8 = new JButton("8");
	private JButton button9 = new JButton("9");
	private JButton button0 = new JButton("0");
	private JButton button_add = new JButton("+");
	private JButton button_sub = new JButton("-");
	private JButton button_mul = new JButton("*");
	private JButton button_div = new JButton("/");
	private JButton button_C = new JButton("C");
	private JButton button_eq = new JButton("=");
	//private JButton button_dot = new JButton(".");
	private JButton button_left_brackets  = new JButton("(");
	private JButton button_right_brackets = new JButton(")");
	private JButton button_delete = new JButton("←");
	
	private String buffer = "";
	
	//***********************存储相关
	private JPanel memKeyBoard = new JPanel();
	private JTextField tfMemory = new JTextField();
	private JButton button_up = new JButton("↑");
	private JButton button_down = new JButton("↓");
	private JButton button_read = new JButton("Read");
	private ArrayList<String> resultBuffer = new ArrayList<String>();
	private int resultBuffer_index = 0;
	
	public App() {
		this.setBackground(Color.gray);
		setLayout(new BorderLayout());
		
		tfAnswer.setHorizontalAlignment(JTextField.RIGHT);
		tfAnswer.setBackground(Color.lightGray);
		tfAnswer.setText("0.");
		tfAnswer.setEditable(false);//设置只读
		add(tfAnswer,BorderLayout.NORTH);	
		
		keyBoard.setLayout(new GridLayout(5,4));
		keyBoard.add(button7);
		button7.setBackground(Color.white);
		keyBoard.add(button8);
		button8.setBackground(Color.white);
		keyBoard.add(button9);
		button9.setBackground(Color.white);
		keyBoard.add(button_C);
		button_C.setBackground(Color.white);
		
		keyBoard.add(button4);
		button4.setBackground(Color.white);
		keyBoard.add(button5);
		button5.setBackground(Color.white);
		keyBoard.add(button6);
		button6.setBackground(Color.white);
		keyBoard.add(button_delete);
		button_delete.setBackground(Color.white);
		
		keyBoard.add(button1);
		button1.setBackground(Color.white);
		keyBoard.add(button2);
		button2.setBackground(Color.white);
		keyBoard.add(button3);
		button3.setBackground(Color.white);
		//keyBoard.add(button_dot);
		
		keyBoard.add(button0);
		button0.setBackground(Color.white);
		keyBoard.add(button_add);
		button_add.setBackground(Color.white);
		keyBoard.add(button_sub);
		button_sub.setBackground(Color.white);
		keyBoard.add(button_mul);
		button_mul.setBackground(Color.white);
		keyBoard.add(button_div);
		button_div.setBackground(Color.white);
		keyBoard.add(button_left_brackets);
		button_left_brackets.setBackground(Color.white);
		keyBoard.add(button_right_brackets);
		button_right_brackets.setBackground(Color.white);
		keyBoard.add(button_eq);
		button_eq.setBackground(Color.white);
		add(keyBoard,BorderLayout.CENTER);
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		button0.addActionListener(this);

		button_C.addActionListener(this);
		
		button_add.addActionListener(this);
		button_sub.addActionListener(this);
		button_mul.addActionListener(this);
		button_div.addActionListener(this);
		button_eq.addActionListener(this);
		button_delete.addActionListener(this);

		button_left_brackets.addActionListener(this);
		button_right_brackets.addActionListener(this);
		
		//**********************存储相关
		memKeyBoard.setLayout(new GridLayout(4,1));
		tfMemory.setText("NaN");
		tfMemory.setEditable(false);
		tfMemory.setBackground(Color.LIGHT_GRAY);
		memKeyBoard.add(tfMemory);
		memKeyBoard.add(button_up);
		button_up.setBackground(Color.white);
		memKeyBoard.add(button_down);
		button_down.setBackground(Color.white);
		memKeyBoard.add(button_read);
		button_read.setBackground(Color.white);
		
		button_up.addActionListener(this);
		button_down.addActionListener(this);
		button_read.addActionListener(this);
		
		add(memKeyBoard,BorderLayout.EAST);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if( e.getActionCommand() == "1" || 
			e.getActionCommand() == "2" || 
			e.getActionCommand() == "3" || 
			e.getActionCommand() == "4" || 
			e.getActionCommand() == "5" ||
			e.getActionCommand() == "6" ||
			e.getActionCommand() == "7" ||
			e.getActionCommand() == "8" ||
			e.getActionCommand() == "9" ||
			e.getActionCommand() == "0" ||
			e.getActionCommand() == "+" ||
			e.getActionCommand() == "-" ||
			e.getActionCommand() == "*" ||
			e.getActionCommand() == "/" ||
			e.getActionCommand() == "(" ||
			e.getActionCommand() == ")" )
			
			buffer = buffer + e.getActionCommand();
		
		else if(e.getActionCommand() == "C")
			buffer = "";
		else if(e.getActionCommand() == "=") {
			buffer = Alu(buffer);
		}
		else if(e.getActionCommand() == "←") {
			buffer = buffer.substring(0,buffer.length() - 1);
		}
		else if(e.getActionCommand() == "↑") {
			if(resultBuffer_index > 0)
				resultBuffer_index--;
			if(resultBuffer.isEmpty() == false)//防止抛出越界异常:再resultBuffer为空的时候
				tfMemory.setText(resultBuffer.get(resultBuffer_index));
		}
		else if(e.getActionCommand() == "↓") {
			if(resultBuffer_index < resultBuffer.size() - 1 )
				resultBuffer_index++;
			if(resultBuffer.isEmpty() == false)
				tfMemory.setText(resultBuffer.get(resultBuffer_index));
		}
		else if(e.getActionCommand() == "Read"){
			if(buffer != "NaN" && resultBuffer.isEmpty() == false)
				buffer = buffer + resultBuffer.get(resultBuffer_index);
		}
		tfAnswer.setText(buffer);
	}

	
	private String  Alu(String buffer) {
		 try{
			 buffer = buffer.valueOf(cal.conversion(buffer));
			 }
		 catch(Exception e) {
			 buffer = String.valueOf(0.0/0.0);
		 }
		 //结果有效，保存起来
		 if(buffer.equals("NaN") == false) {
			 resultBuffer.add(buffer);
			 resultBuffer_index = resultBuffer.size() - 1;
			 tfMemory.setText(buffer);
		 }
		 System.out.println(buffer);
		 return buffer;
	}
	
    public static void main( String[] args )
    {
        App frame = new App();
        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
