package PTA1;

import javax.swing.*;

public class CommissaryStudent extends Student {
    public CommissaryStudent(String name,String id){super(name,id);}
    @Override
    public void introduceMe(){
        System.out.println("我是"+getId()+"号同学"+getName()+"。");
    }

}
