package PTA1;

public class MonitorStudent extends Student{
    public MonitorStudent(String name,String id) {super(name,id);}
    @Override
    public void introduceMe(){
        System.out.print("我是班长。");
    }
}
