package PTA1;

import javax.swing.plaf.synth.SynthTextAreaUI;

public abstract class Student {
    private  String name;
    private String id;

    public Student() {

    }
    public Student(String name,String id) {
        this.name = name;
        this.id = id;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    // 不同的学生实现不同的写作业方法
    public void doHomework() {
        System.out.println(name+"同学正在写作业。");
    }
    public abstract  void introduceMe();
}
