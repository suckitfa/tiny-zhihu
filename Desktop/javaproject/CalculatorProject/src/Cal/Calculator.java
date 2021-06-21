package Cal;

import java.util.regex.*;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculator{
	    /**
	     *设置运算符的优先级：
	     *	根据题目要求 + - * / 优先级相同
	     */
	    private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();
	    static {
	        basic.put('-', 1);
	        basic.put('+', 1);
	        basic.put('*', 1);
	        basic.put('/', 1);
	        basic.put('(', 0);
	    }

	    /**
	     * 将  中缀表达式  转化为  后缀表达式
	     */
	    public String getPostExpression(String rawExpression){
	        List<String> queue = new ArrayList<String>();                                   
	        List<Character> stack = new ArrayList<Character>();
	        
	        char[] charArr = rawExpression.trim().toCharArray();
	        String standard = "*/+-()";
	        char ch = '&';
	        int len = 0;                                                                    
	        for (int i = 0; i < charArr.length; i++) {
	            ch = charArr[i];
	            if(Character.isDigit(ch)) {
	                len++;    
	            }else if(Character.isLetter(ch)) {
	                len++;
	            }else if(ch == '.'){
	                len++;
	            }else if(Character.isSpaceChar(ch)) {
	                if(len > 0) {
	                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));    //往 队列存入 截取的 字符串 
	                    len = 0;                                                            //长度置空
	                }
	                continue;                                                                //如果空格出现，则一段结束  跳出本次循环
	            }else if(standard.indexOf(ch) != -1) {                                        //如果是上面标准中的 任意一个符号
	                if(len > 0) {                                                            //长度也有
	                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));    //说明符号之前的可以截取下来做数字
	                    len = 0;                                                            //长度置空
	                }
	                if(ch == '(') {                                                            //如果是左括号
	                    stack.add(ch);                                                        //将左括号 放入栈中
	                    continue;                                                            //跳出本次循环  继续找下一个位置
	                }
	                if (!stack.isEmpty()) {                                                   
	                    int size = stack.size() - 1;
	                    boolean flag = false;                                                //设置标志位
	                    while (size >= 0 && ch == ')' && stack.get(size) != '(') { 
	                        queue.add(String.valueOf(stack.remove(size))); 
	                        size--;                                                            //size-- 保证下标永远在栈最后一个元素【栈中概念：指针永远指在栈顶元素】
	                        flag = true;                                                    //设置标志位为true  表明一直在取（）中的元素
	                    }
	                    while (size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)) {   
	                        queue.add(String.valueOf(stack.remove(size)));                    
	                        size--;
	                    }
	                }
	                if(ch != ')') {                                                              
	                    stack.add(ch);                                                        
	                } else {                                                                
	                    stack.remove(stack.size() - 1);
	                }
	            }
	            if(i == charArr.length - 1) {                                                //如果已经走到了  中缀表达式的最后一位
	                if(len > 0) {                                                            //如果len>0  就截取数字
	                    queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len+1, i+1)));
	                }    
	                int size = stack.size() - 1;                                            //size表示栈内最后一个元素下标
	                while (size >= 0) {                                                       
	                    queue.add(String.valueOf(stack.remove(size)));
	                    size--;
	                }
	            }
	            
	        }
	        return queue.stream().collect(Collectors.joining(","));                            //将队列中元素分割 返回字符串
	    }
	    
	    /**
	     * 将 后缀表达式 进行  运算 计算出结果
	     */
	    public String calculate(String expression){
	    	if(this.isLegal(expression)) {
	    		String newExpression = this.resetExpression(expression);
//	    		System.out.println(newExpression);
	    		String postExpression = this.getPostExpression(newExpression);
//	    		System.out.println(postExpression);
	    		String [] arr = postExpression.split(",");                                    //根据, 拆分字符串
	    		List<String> myList = new ArrayList<String>();                            //用于计算时  存储运算过程的集合【例如list中当前放置  100   20  5  /  则取出20/5 最终将结果4存入list   此时list中结果为  100  4 】
	        
	        
	    		for (int i = 0; i < arr.length; i++) {                                    //此处就是上面说的运算过程， 因为list.remove的缘故，所以取出最后一个数个最后两个数  都是size-2
	    			int size = myList.size();
	    			switch (arr[i]) {
	    			case "+": double a = Double.parseDouble(myList.remove(size-2))+ Double.parseDouble(myList.remove(size-2)); myList.add(String.valueOf(a));break;
	    			case "-": double b = Double.parseDouble(myList.remove(size-2))- Double.parseDouble(myList.remove(size-2)); myList.add(String.valueOf(b));     break;
	    			case "*": double c = Double.parseDouble(myList.remove(size-2))* Double.parseDouble(myList.remove(size-2)); myList.add(String.valueOf(c));     break;
	    			case "/": double d = Double.parseDouble(myList.remove(size-2))/ Double.parseDouble(myList.remove(size-2)); myList.add(String.valueOf((int)d));       break;
	    			default: myList.add(arr[i]);     break;                                    //如果是数字  直接放进list中
	    			}
	    		}
	    		if(myList.get(0)=="Infinity")
	    		{
	    			return "Error:Divided by zero!";
	    		}else {
	    			String result = ""+ String.format("%.0f",Double.parseDouble(myList.get(0)));
	    			return expression+"="+result;
	    		}
	    	}else {
	    		return "FORMAT ERROR!";//非法表达式
	    	}
	        
	    }

    /*
    *   判断表达是是否合法
    */
    public static boolean isLegal(String expression){
        //表达式的正确模式
        String pattern = "((\\(-?\\d+\\))|-?(\\d+))([+\\-*\\/]((\\(\\-\\d+\\))|(\\d+)))+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expression);
        if(m.matches()) {
            return true;
        }
        
        return false;
    }
    //获取用户输入的表达式
    public String getExpression() {
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        expression = expression.replace(" ",""); //除去多余空格
        return expression;
    }
    //重整合表达式
    public String resetExpression(String rawExpression) {
        String pattern = "\\([\\-\\+]\\d+\\)";          //匹配带括号的项
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(rawExpression);
        String [] itemArray = rawExpression.split(pattern);
        
        String newExpression = "";
        String newItem = "";
        int i = 0;
        //在前面加 0，重新整合如 （+1）--->（0 + 1）
        while(m.find() && i <itemArray.length)
        {
            newItem = "(0" + rawExpression.substring(m.start()+1,m.end());
            newExpression += (itemArray[i] + newItem);
            i++;
        }
        while(i < itemArray.length) {
            newExpression += itemArray[i];
            i++;
        }
        //诺以带符号的数字开头，则加 0
        Pattern p2 = Pattern.compile("^([+\\-]\\d+)");
        Matcher m2 = p2.matcher(newExpression);
        if(m2.find()) {
            newExpression = "(0" + m2.group()+")"+newExpression.substring(m2.end());
        }
        return newExpression;
    }

    
    
    
    // 测试
    public static void main(String [] args){
        Calculator r = new Calculator();
        String expression = r.getExpression();
        System.out.println(r.calculate(expression));
    }
}