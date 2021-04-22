package PTA1;

public class StudentDecorator extends Student{
    protected Student decoratedStudent;
    public StudentDecorator(Student decoratedStudent) {
        this.decoratedStudent = decoratedStudent;
    }
    @Override
    public void doHomework() {
        decoratedStudent.doHomework();
    }
    @Override
    public void introduceMe() {
        decoratedStudent.introduceMe();
    }
}
