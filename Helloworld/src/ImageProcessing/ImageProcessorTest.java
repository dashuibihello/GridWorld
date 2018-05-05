package ImageProcessing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import imagereader.IImageIO;
import imagereader.IImageProcessor;  

public class ImageProcessorTest
{
    IImageIO imageioer = new ImplementImageIO();
    IImageProcessor processor = new ImplementImageProcessor();
    private Image parentimage_1;
    private Image parentimage_2;
    private Image goalimage_1_red;
    private Image goalimage_1_green;
    private Image goalimage_1_blue;
    private Image goalimage_1_grey;
    private Image childimage_1_red;
    private Image childimage_1_green;
    private Image childimage_1_blue;
    private Image childimage_1_grey;
    
    private Image goalimage_2_red;
    private Image goalimage_2_green;
    private Image goalimage_2_blue;
    private Image goalimage_2_grey;
    private Image childimage_2_red;
    private Image childimage_2_green;
    private Image childimage_2_blue;
    private Image childimage_2_grey;
   
    //input all image
    @Before
    public void setup() throws Exception {
    	try {
        	parentimage_1 = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/1.bmp");
		
		//goal文件内的图片
        	goalimage_1_red = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_goal.bmp");
        	goalimage_1_green = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_green_goal.bmp");
        	goalimage_1_blue = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_blue_goal.bmp");
        	goalimage_1_grey = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_gray_goal.bmp");
		
		//通过processor生成的进行比较的图片
        	childimage_1_red = processor.showChanelR(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/1.bmp"));
        	childimage_1_green = processor.showChanelG(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/1.bmp"));
        	childimage_1_blue = processor.showChanelB(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/1.bmp"));
        	childimage_1_grey = processor.showGray(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/1.bmp"));
        	
        	parentimage_2 = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/2.bmp");
		
		//goal文件内的图片
        	goalimage_2_red = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/2_red_goal.bmp");
        	goalimage_2_green = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/2_green_goal.bmp");
        	goalimage_2_blue = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/2_blue_goal.bmp");
        	goalimage_2_grey = imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/2_gray_goal.bmp");
		
		//通过processor生成的进行比较的图片
        	childimage_2_red = processor.showChanelR(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/2.bmp"));
        	childimage_2_green = processor.showChanelG(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/2.bmp"));
        	childimage_2_blue = processor.showChanelB(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/2.bmp"));
        	childimage_2_grey = processor.showGray(imageioer.myRead("/home/kiddion/Downloads/bmptest_v3/bmptest/2.bmp"));
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }

    //比较位图宽度
    @Test
    public void testwidth()
    {   
    	assertEquals(parentimage_1.getWidth(null), goalimage_1_red.getWidth(null));
    	assertEquals(parentimage_2.getWidth(null), goalimage_2_red.getWidth(null));
        assertEquals(childimage_1_red.getWidth(null), goalimage_1_red.getWidth(null));
        assertEquals(childimage_1_green.getWidth(null), goalimage_1_green.getWidth(null));
        assertEquals(childimage_1_blue.getWidth(null), goalimage_1_blue.getWidth(null));
        assertEquals(childimage_1_grey.getWidth(null), goalimage_1_grey.getWidth(null));
        assertEquals(childimage_2_red.getWidth(null), goalimage_2_red.getWidth(null));
        assertEquals(childimage_2_green.getWidth(null), goalimage_2_green.getWidth(null));
        assertEquals(childimage_2_blue.getWidth(null), goalimage_2_blue.getWidth(null));
        assertEquals(childimage_2_grey.getWidth(null), goalimage_2_grey.getWidth(null));
    }
    
    //比较位图高度
    @Test
    public void testHeight() {
    	assertEquals(parentimage_1.getHeight(null), goalimage_1_red.getHeight(null));
    	assertEquals(parentimage_2.getHeight(null), goalimage_2_red.getHeight(null));
        assertEquals(childimage_1_red.getHeight(null), goalimage_1_red.getHeight(null));
        assertEquals(childimage_1_green.getHeight(null), goalimage_1_green.getHeight(null));
        assertEquals(childimage_1_blue.getHeight(null), goalimage_1_blue.getHeight(null));
        assertEquals(childimage_1_grey.getHeight(null), goalimage_1_grey.getHeight(null));
        assertEquals(childimage_2_red.getHeight(null), goalimage_2_red.getHeight(null));
        assertEquals(childimage_2_green.getHeight(null), goalimage_2_green.getHeight(null));
        assertEquals(childimage_2_blue.getHeight(null), goalimage_2_blue.getHeight(null));
        assertEquals(childimage_2_grey.getHeight(null), goalimage_2_grey.getHeight(null));
	}
    
    //比较像素值
    @Test
    public void testpixvalue() throws IOException {
    	imageioer.myWrite(childimage_1_red, "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_child.bmp");
    	imageioer.myWrite(childimage_1_grey, "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_grey_child.bmp");
    	assertEquals(textpix("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_goal.bmp", "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_child.bmp", parentimage_1.getHeight(null), parentimage_1.getWidth(null), 24), true);
    	assertEquals(textpix("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_gray_goal.bmp", "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_grey_child.bmp", parentimage_2.getHeight(null), parentimage_2.getWidth(null), 24), true);
    }
    
    @SuppressWarnings("resource")
	boolean textpix(String goalPath, String childPath, int height, int width, int bitcount) throws IOException {
		BufferedInputStream child = new BufferedInputStream(new FileInputStream(childPath));
    	BufferedInputStream goal = new BufferedInputStream(new FileInputStream(goalPath));  	
    	child.skip(54);
    	goal.skip(54);
	   
       //每一行颜色占用的字节数规定为4的整数倍
       int skipnum = (bitcount * width / 8) % 4;
       if (skipnum != 0) {
           skipnum = 4 - skipnum;
       }
  	   //get every pixel
       for (int i = height - 1; i >= 0; i--) {
           for (int j = 0; j < width; j++) {
               int goalblue = goal.read();
               int goalgreen = goal.read();
               int goalred = goal.read();
               int childblue = child.read();
               int childgreen = child.read();
               int childred = child.read();
               if(goalblue != childblue || goalgreen != childgreen || goalred != childred) {
            	   goal.close();
            	   child.close();
            	   return false;
               }
          }
            if (skipnum != 0) {
            	goal.skip(skipnum); 
                child.skip(skipnum);
            }
      }
		goal.close();
		child.close();
       return true;
    }
}
