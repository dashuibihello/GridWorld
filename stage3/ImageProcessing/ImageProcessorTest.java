package imageioimplement;

import static org.junit.assert.*;

import org.junit.before;
import org.junit.test;
import static org.junit.assert.*;

import java.io.fileinputstream;
import java.util.collection;
import java.util.arrays;  

import java.awt.*;
import javax.imageio.*;
import java.lang.string;
import org.junit.runner.runwith;  
import org.junit.runners.parameterized;  
import org.junit.runners.parameterized.parameters;  

@runwith(parameterized.class)
public class imagereadertest
{
    //private string filepath0;
    //private string filepath1;
    private fileinputstream pfile0;
    private fileinputstream pfile1;
    private image pimage0;
    private image pimage1;

    
    @parameters   
    public static collection<object[]> preparedata()
    {  
         return arrays.aslist(new object[][]{
                 {"/home/gumc/bitmap/bmptest/my/1_blue_goal.bmp", "/home/gumc/bitmap/bmptest/goal/1_blue_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/1_red_goal.bmp", "/home/gumc/bitmap/bmptest/goal/1_red_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/1_green_goal.bmp", "/home/gumc/bitmap/bmptest/goal/1_green_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/1_gray_goal.bmp", "/home/gumc/bitmap/bmptest/goal/1_gray_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/2_blue_goal.bmp", "/home/gumc/bitmap/bmptest/goal/2_blue_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/2_red_goal.bmp", "/home/gumc/bitmap/bmptest/goal/2_red_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/2_green_goal.bmp", "/home/gumc/bitmap/bmptest/goal/2_green_goal.bmp"},
                 {"/home/gumc/bitmap/bmptest/my/2_gray_goal.bmp", "/home/gumc/bitmap/bmptest/goal/2_gray_goal.bmp"}});
    }  
    
    public imagereadertest(string file1, string file2) throws exception 
    {
        //this.filepath0 = file1;
        //this.filepath1 = file2;
        this.pfile0 = new fileinputstream(file1);
        this.pfile1 = new fileinputstream(file2);
        this.pimage0 = imageio.read(pfile0);
        this.pimage1 = imageio.read(pfile1);
    }
    
    @before
    public void setup() throws exception {
    }

    //比较分辨率（长宽像素值都相等）
    @test
    public void testresolution()
    {
        assertequals(pimage0.getheight(null), pimage1.getheight(null));
        assertequals(pimage0.getwidth(null), pimage1.getwidth(null));
    }
    
    //比较像素值
    @test
    public void testpixvalue() throws exception 
    {
        int sizeimage = pimage0.getheight(null) * pimage1.getwidth(null);
            
        //将两个图片的位图数据部分全部读出，比较。
        byte imagecolor0[] = new byte[sizeimage];
        byte imagecolor1[] = new byte[sizeimage];
            
        //跳过不属于位图信息的部分
        pfile0.skip(54);
        pfile1.skip(54);
            
        pfile0.read(imagecolor0, 0, sizeimage);
        pfile1.read(imagecolor1, 0, sizeimage);
        //是否相等
        assertequals(imagecolor1, imagecolor1);    
    }

}
