package PTA1;

public class CommissaryStudentDecorator extends StudentDecorator{
    public CommissaryStudentDecorator(Student student) {
        super(student);
    }
    // 学习委员收作业
    public void collectHomework() {
        System.out.println(decoratedStudent.getName()+ "同学正在写作业。");
    }
    // 学习委员介绍自己
    public void introduceMe() {
        System.out.println("我是学习委员。我是"+decoratedStudent.getId()+"号同学"+decoratedStudent.getName()+"。");
    }
}
