package PTA1;

public class DrivenAgent {


    public static void main(String[] args) {
        Student  stu = new CommonStudent();
        Scanner in = new Scanner(System.in);
        System.out.println("请输入学号：");
        String num = in.nextLine();
        stu.setNum(num);
        System.out.println("请输入姓名：");
        String name = in.nextLine();
        stu.setName(name);
        stu.doHomework();
        System.out.println("请输入该学生的职务：0、无1、班长 2、学习委员3、班长兼学习委员");
        int i = in.nextInt();
        switch(i) {
            case 0 :
                // 自我介绍
                stu.introduceMe();
                stu.doHomework();
                break;
            case 1:
                //插入语句生成班长实例m
                StudentDecorator m = new MonitorStudentDecorator(new MonitorStudent(name,num));
                ((MonitorStudentDecorator)m).introduceMe();//输出班长的自我介绍。
                ((MonitorStudentDecorator) m).holdMeeting();//输出班长正在开班会
                break;
            case 2:
                //插入语句生成学习委员实例s
                StudentDecorator s = new CommissaryStudentDecorator(new CommissaryStudent(name,num));
                ((CommissaryStudentDecorator)s).introduceMe();//输出学习委员的自我介绍。
                ((CommissaryStudentDecorator)s).collectHomework();//输出学习委员正在收作业
                break;
            case 3:
                //插入语句生成学习委员实例s
                //插入语句生成班长实例m
                Student s = new CommissaryStudent(name,num);
                StudentDecorator m = new MonitorCommissaryDecorator(s);
                m.introduceMe();
                s.collectHomework();
                m.holdMeeting();
                break;
        }
    }
}
