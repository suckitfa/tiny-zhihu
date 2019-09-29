 package experiment_1;

import java.util.Random;
import java.util.Scanner;
//学生类
class Student{
	
	//属性
	String studentID;						//学生学号 
	String studentName;						//学生姓名 
	int []scores;							//学生成绩
	double averageScore = 0.0;					//个人平均分
	double bestScore = 0.0;						//个人最高分
	double classAverage = 0.0;
	int index = 0;								//班上平均成绩最好的学生下标
	int classNumber = 30;
	
	
	Student(){								//构造方法
	/*
	 * 通过随机数原理生成名字，分数等数据
	 * 名字：随机生成的名字由4个字母组成（首字母大写）
	 * 分数：生成区间【0，100】
	 * */
		
		//填充分数；每个学生四门成绩
		Random rNumber = new Random();		// Random对象
		scores = new int[4];				// 每个学生四门成绩
		for(int i = 0; i < scores.length; i++) {
			scores[i] = rNumber.nextInt(100);
		}
		
		//填写名字，由随机字母填充
		String myChars = "abcdefghijklmnopqrstuvwxyz";
		int indexNumber;
		char nameArray[] = new char[4];		//名字含有四个字母
		for(int i = 0; i < nameArray.length; i++) {
			indexNumber = rNumber.nextInt(26);
			nameArray[i] = myChars.charAt(indexNumber);
		}
		studentName = new String(nameArray);
		
		//填充学号:生成最后两位随机数
		int idNum;
		String idNumString;
		idNum = (int)(1 + Math.random() * 31 );		// 01-30
		if(idNum < 10) {
			idNumString = "0" + idNum;
		}
		else {
			idNumString = "" + idNum;
		}
		studentID = new String("132011" + idNumString);
		
	}
	



	//求出个人最高分
	public void selectBest() {
		int best = -999999;
		for(int i = 0; i  < this.scores.length; i++) {
			if(this.scores[i] > best)
				best = this.scores[i];
		}
		this.bestScore = (double)best;
	}
	
	//求出个人平均分
	public void calAverage() {
		double sum = 0;
		for(int i = 0 ; i < scores.length; i++) {
			sum += this.scores[i];
		}
		averageScore = (double)sum / 4;
		
	}


	//获取用户输入的信息
	public boolean getInfo() {
		boolean isLegal = true;
		Scanner input = new Scanner(System.in);
		System.out.println("请输入学生姓名:");
		studentName = input.next();
		System.out.println("请输入学生学号:");
		studentID = input.next();
		System.out.println("请输入四门课的成绩，以空格分隔:");
		for(int i = 0; i < this.scores.length; i++) {
			scores[i] = input.nextInt();
		}
		//检测成绩输入成绩是否超过范围【0，100】
		for(int i = 0; i < this.scores.length; i++) {
			if(this.scores[i] > 100)
				isLegal =  false;
		}

		return isLegal;

	}	
	
	//求出班级平均分
		public void calClassAverage(Student[] wholeClass) {

			double average = 0.0;
			for(int i = 0; i < wholeClass.length; i++) {
				average += wholeClass[i].averageScore;
			}
			average /= classNumber;

			for(int i = 0; i < wholeClass.length; i++){
				wholeClass[i].classAverage = average;
			}

		}
	
		/*
		 * 传入对象数组
		 * 返回------平均成绩最高---的index
		 * */
		public void generalBest(Student[] wholeClass) {	
			double best = -99999.0;
			for(int i = 0; i < wholeClass.length; i++) {
				if(wholeClass[i].averageScore > best) {
					best = wholeClass[i].averageScore;
					index = i;
			}
			}
		}
		
		public void showInfo(Student[] wholeClass, Student s,boolean isLegal) {
			
			generalBest(wholeClass);			//取得个人平均最高下标
			calClassAverage(wholeClass);		//求得班级平均分
			

			if(isLegal){
				//个人基本情况
				System.out.printf("%s(ID: %s) 平均分 %.2f 最高分 %.2f\n",s.studentName,s.studentID,s.averageScore,s.bestScore);

				//个人平均分对比
				if(wholeClass[index].averageScore > s.averageScore){
					System.out.printf("%s (ID:%s) 的最高平均分 %.2f 最高分 %.2f\n",wholeClass[index].studentName,wholeClass[index].studentID,
							wholeClass[index].averageScore,wholeClass[index].bestScore);		

				}else{
					System.out.printf("%s 现在是班上成绩最好的学生了\n",s.studentName);
				}

				//班上平均分对比
				if(wholeClass[index].classAverage > s.averageScore){
					System.out.printf("%s 的平均成绩低于全班的平均分\n",s.studentName);

				}else{
					System.out.printf("%s 的平均成绩高于全班的平均分\n",s.studentName);
				}

			}else{
				System.out.println("课程成绩不能高于 100 分!");
			}
			
		}
		
	
		//新同学加入
		public boolean addStudent(Student [] wholeClass,Student s) {
			boolean isLegal = s.getInfo();
			if(isLegal) {
				s.selectBest();
				s.calAverage();
			
				wholeClass[30].averageScore = s.averageScore;
				wholeClass[30].bestScore = s.bestScore;
				for(int i = 0; i < wholeClass.length; i++) {
					wholeClass[i].classNumber  +=  1;
				}
			}
			
			return isLegal;
		}

		public static void main(String[] args) {
		//生成大小为31的对象引用数组
		Student wholeClass[] = new Student[31];
		for(int i = 0; i < wholeClass.length; i++) {
			wholeClass[i] = new Student();				//生成新的对象
			wholeClass[i].selectBest();
			wholeClass[i].calAverage();
		}
		

		//添加新成员
		Student s = new Student();
		boolean isLegal = s.addStudent(wholeClass,s);

		
		s.showInfo(wholeClass, s,isLegal);
	}


}
	

