import java.util.regex.*;
import java.util.*;
import java.util.Scanner;

public class CalculatorTest{
    int result;
    int flag = 0;
    CalculatorTest(){

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
    
    //设置优先级 + - *  同属于一个优先级，（从左到右计算）
    public int setPriority(char op) {
        if(op == '(') {
            return 1;
        }else if(op == '+' || op == '-' || op == '*'|| op == '/') {
            return 2;
        }else {
            return 3;
        }
    }
    //转化成后缀表达式计算
    public String setPostExpression(String expression) {
        Stack<Character> myStack = new Stack<Character>();
        int size = expression.length();
        int i = 0;
        char tmp;
        String res = "";
         while (i < size) {
                if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9'){
                    res += expression.charAt(i);
                }
                else if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                    if (myStack.empty()) {
                        myStack.push(expression.charAt(i));
                    }
                    else {
                        while (!myStack.empty()) {
                            tmp = myStack.peek();
                            if (setPriority(tmp) >= setPriority(expression.charAt(i))) {
                                //弹出栈顶元素
                                res += tmp;
                                myStack.pop();
                            }
                            else break;
                        }
                        myStack.push(expression.charAt(i));
                    }
                }
                else {
                    if(expression.charAt(i)=='(') myStack.push(expression.charAt(i));
                    else {
                        while (myStack.peek() != '(') {
                            tmp = myStack.peek();
                            res += tmp;
                            myStack.pop();
                        }
                        myStack.pop();
                    }
                }
                i++;
            }
         //遍历完后，若栈非空，弹出所有元素
            while (!myStack.empty()) {
                tmp = myStack.peek();
                res += tmp;
                myStack.pop();
            }
            return res;
    }
     
    public String calculate(String expression)
    {
        //计算后缀表达式的值，默认中缀表达式所有数字都是一位的，在0-9之间
        Stack<Double> myStack = new Stack<Double>();
        int size = expression.length();
        double num1, num2, num3=0;
        for (int i = 0; i < size; i++) {
            if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                myStack.push((double)(expression.charAt(i) - '0'));
            }
            else {
                num2 = myStack.pop();
                num1 = myStack.pop();
                if (expression.charAt(i) == '+') {
                    num3 = num1 + num2;
                }
                else if (expression.charAt(i) == '-') {
                    num3 = num1 - num2;
                }
                else if (expression.charAt(i) == '*') {
                    num3 = num1 * num2;
                }
                else if (expression.charAt(i) == '/') {
                    num3 = num1 / num2;
                }
                myStack.push(num3);
            }
        }
        return myStack.peek() + "";
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
            newExpression = "0" + m2.group()+newExpression.substring(m2.end());
        }
        return newExpression;
    }

    // 测试
    public static void main(String [] args){
        CalculatorTest r = new CalculatorTest();
        String expression = r.getExpression();
        if(r.isLegal(expression)) {
            String newExpression = r.resetExpression(expression);
//          System.out.println(newExpression);
            String Postexpression = r.setPostExpression(newExpression);
            String result = r.calculate(Postexpression);
            if("Infinity".equals(result)) {//借助Java中 Infinity
                System.out.print("Error:Divided by zero!");
            }else {
                String newResult = String.format("%.0f",Double.parseDouble(result));
                System.out.print(expression + "=" + newResult);
            }
        }else {
            System.out.print("FORMAT ERROR!");
        }
        

    }
}
