package PTA1;
//学生装饰类
public class MonitorStudentDecorator extends StudentDecorator{
    public MonitorStudentDecorator(Student decoratedStudent) {
        super(decoratedStudent);
    }
    public void introduceMe() {
        System.out.println();
    }
    public void holdMeeting() {System.out.println("我正在主持班会。");}

}
