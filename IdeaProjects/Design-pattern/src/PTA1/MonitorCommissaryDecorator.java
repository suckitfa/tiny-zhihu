package PTA1;
// 班长兼职学习委员
public class MonitorCommissaryDecorator extends StudentDecorator {
    public MonitorCommissaryDecorator(Student student) {
        super(student);
    }
    // 班长学习委员主持班会
    public void holdMeeting() {
        System.out.println("我正在主持班会。");
    }
    // 班长学习委员收集作业
    public void collectHomework(){
        System.out.println("我正在收作业。");
    }
}
