package PTA1;

import java.awt.*;

public class CommonStudent extends Student{
    @Override
    public void introduceMe(){
        System.out.println("我是"+getId()+"号同学"+getName()+"。");
    }
}
