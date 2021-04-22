package PTA1;

public class CommonStudentDecorator extends StudentDecorator{
    public CommonStudentDecorator(Student student) {
        super(student);
    }
    public void introduceMe(){
        decoratedStudent.introduceMe();
    }
    public void doHomework(){
        decoratedStudent.doHomework();
    }
}
