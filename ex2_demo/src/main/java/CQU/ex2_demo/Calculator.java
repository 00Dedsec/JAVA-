package CQU.ex2_demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {
	private String buffer = "";
	private String result = "";
	private Stack<String> numStack = new Stack<String>();//操作数栈
	private Stack<String> opStack = new Stack<String>();//运算符栈
	
	public String calculate(String buffer) {
		//增加冗余的0
		transform();
		//入栈
		pushIntoStack();
		
		return buffer;
	}
	
	private String Alu(String src1,String src2,String op) {
		if(op.equals("+")) {
	        java.math.BigDecimal a = new java.math.BigDecimal(src2);
	        java.math.BigDecimal b = new java.math.BigDecimal(src1);
	        return a.add(b).toString();
		}
		else if(op.equals("-")) {
			java.math.BigDecimal a = new java.math.BigDecimal(src2);
	        java.math.BigDecimal b = new java.math.BigDecimal(src1);
	        return a.subtract(b).toString();
		}
		else if(op.equals("*")) {
			java.math.BigDecimal a = new java.math.BigDecimal(src2);
	        java.math.BigDecimal b = new java.math.BigDecimal(src1);
	        return a.multiply(b).toString();
		}
		else //if(op == "/") 
		{
			java.math.BigDecimal a = new java.math.BigDecimal(src2);
	        java.math.BigDecimal b = new java.math.BigDecimal(src1);
	        return a.divide(b,0, RoundingMode.HALF_UP).toString();
		}
		
	}
	
	private void pushIntoStack() {		
		//切割字符串
		char[] cBuffer = this.buffer.toCharArray();
		String src1,src2,operator;
		ArrayList<String> src = new ArrayList<String>();
		int index = 0;
		String temp = "";
		for(int i = 0; i < cBuffer.length; i++) {
			if(this.isNum(cBuffer[i]))
				//src[index] = src[index] + String.valueOf(cBuffer[i]);
				temp = temp + String.valueOf(cBuffer[i]);
			else {
				//index = index + 1;
				//src[index] = String.valueOf(cBuffer[i]);
				if(temp != "")
					src.add(temp);
				src.add(String.valueOf(cBuffer[i]));
				temp = "";
			}
		}
		if(temp.isEmpty() == false)
			src.add(temp);
		
		//test
		System.out.println("src = " + src);
		
		//压栈
		try {
		for(int i = 0; i < src.size(); i++) {
			//遇到数字直接入栈
			if(this.isNum(src.get(i))) {
				numStack.push(src.get(i));
				//System.out.println("入数字栈：" + src.get(i));
			}
			//遇到操作符若栈为空直接入栈
			else if(this.isOP(src.get(i)) && opStack.isEmpty()) {
				opStack.push(src.get(i));
				//System.out.println("入符号栈：" + src.get(i));
			}
			//优先级大于栈顶则直接入栈
			else if(this.isOP(src.get(i)) && (this.getPriority(src.get(i)) > this.getPriority(opStack.peek()))) {
				opStack.push(src.get(i));
				//System.out.println("入符号栈：" + src.get(i));
			}
			//优先级低则弹出
			else if(this.isOP(src.get(i)) && (this.getPriority(src.get(i)) <= this.getPriority(opStack.peek()))) {
				while(this.getPriority(src.get(i)) <= this.getPriority(opStack.peek())) {
					src1 = numStack.pop();
					src2 = numStack.pop();
					System.out.println("src1 = " + src1);
					System.out.println("src2 = " + src2);
					operator = opStack.pop();
					String result =  this.Alu(src1, src2, operator);
					numStack.push(result);
					//System.out.println("result = " + result);
					if(opStack.isEmpty())
						break;
				}
				opStack.push(src.get(i));
			}
			//遇到左括号直接入栈
			else if(src.get(i).equals("("))
				opStack.push(src.get(i));
			//遇到右括号则一直弹出直到遇到左括号
			else if(src.get(i).equals(")")){
				while(opStack.peek().equals("(") == false) {
					src1 = numStack.pop();
					src2 = numStack.pop();
					operator = opStack.pop();
					String result =  this.Alu(src1, src2, operator);
					numStack.push(result);
					if(opStack.isEmpty())
						break;
				}
				opStack.pop();//弹出左括号
			}
		}
		
//		System.out.println(opStack.pop());
//		System.out.println(numStack.pop());
//		System.out.println(numStack.pop());
		while(opStack.isEmpty() == false) {
			src1 = numStack.pop();
			src2 = numStack.pop();
			System.out.println("src1 = " + src1);
			System.out.println("src2 = " + src2);
			operator = opStack.pop();
			String result =  this.Alu(src1, src2, operator);
			//System.out.println("result = " + result);
			numStack.push(result);
		}
		this.buffer = numStack.pop();
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
			this.buffer = String.valueOf(0.0/0.0);
		}
		
//		if(numStack.isEmpty() == false)
//			this.buffer = String.valueOf(0.0/0.0);
			
	}
	
	private int getPriority(String c) {
		int pri = 0;
		if(c.equals("+") || c.equals("-"))
			pri = 1;
		else if(c.equals("*") || c.equals("/"))
			pri = 2;
		return pri;
		
	}
	
	private boolean isOP(String op) {
		if(op.equals("+") || op.equals("-") || op.equals("*")|| op.equals("/"))
			return true;
		return false;
	}
	
	
	private boolean isNum(String c) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isnum = pattern.matcher(c);
        if( !isnum.matches() ){
            return false;
        }
        return true;
	}
	
	
	private boolean isNum(char c) {
		if(c == '1' || c == '2'|| c == '3'|| c == '4'|| c == '5'
				|| c == '6'|| c == '7'|| c == '8'|| c == '9'|| c == '0')
			return true;
		return false;
	}
	
	private void transform() throws ArrayIndexOutOfBoundsException{
		char[] cBuffer = this.buffer.toCharArray();
		StringBuffer sbBuffer = new StringBuffer(buffer);
		for(int i = 0; i < cBuffer.length; i++) {
			if(cBuffer[i] == '+' || 
			   cBuffer[i] == '-' || 
			   cBuffer[i] == '*' || 
			   cBuffer[i] == '/' ||
			   cBuffer[i] == '(') {
				if(cBuffer[i+1] == '-') {
					int j;
					//int flag = 0;
					//cBuffer[i+1] = '~';
					sbBuffer.insert(i+1, '0');
					sbBuffer.insert(i+1, '(');
					for(j = i+2; j < cBuffer.length;j++) {
						if(this.isNum(cBuffer[j]) == false) {
							sbBuffer.insert(j+2, ')');
							break;
						}
					} 
					if(j == cBuffer.length)
						sbBuffer.append(")");
					
				}
				
			}
		}
		if(cBuffer[0] == '-') {
			//cBuffer[0] = '~';
			int j;
			sbBuffer.insert(0, '0');
			sbBuffer.insert(0, '(');
			for(j = 2; j < cBuffer.length;j++) {
				if(this.isNum(cBuffer[j]) == false) {
					sbBuffer.insert(j+2, ')');
					break;
				}
			} 
			if(j == cBuffer.length)
				sbBuffer.append(")");
			
		}
		
		this.buffer = sbBuffer.toString();

	}
	
	
	public String conversion(String buffer) {
		this.buffer = buffer;
		calculate(buffer);
		result = this.buffer;
		return  result;
	}
	
	public static void main(String[] args) {
		Calculator cal = new Calculator();
		String test = cal.conversion("10/3");
		//String test = cal.conversion("1-2+3");
		System.out.println(test);
	}
	
}
