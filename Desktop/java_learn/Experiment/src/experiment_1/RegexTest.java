package experiment_1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexTest{
//重整合表达式
	
	
	public static void main(String []args) { 
		String rawExpression = "-2+3*(0-5)/3-2*9";
		String pattern = "\\([\\-\\+]\\d+\\)";			//匹配带括号的项
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
		System.out.println(newExpression);
		Pattern p2 = Pattern.compile("^([+\\-]\\d+)");
		Matcher m2 = p2.matcher(newExpression);
		if(m2.find()) {
			newExpression = "0" + m2.group()+newExpression.substring(m2.end());
		}
		System.out.println(newExpression);
		
	}
}