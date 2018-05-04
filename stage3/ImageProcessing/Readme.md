# ImageProcessorTest.java
## 测试宽度
### 比较了每一个生成图片与goal文件对应图片的宽度和原图与其中一个goal的宽度
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

------
## 测试高度
### 比较了每一个生成图片与goal文件对应图片的高度和原图与其中一个goal的高度
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

-----
## 测试像素值
### 有两个测试案例。分别测试了processor.showChanelR()和processor.showGray()。并且在开头测试了imageioer.myWrite()
	//比较像素值
    @Test
    public void testpixvalue() throws IOException {
    	imageioer.myWrite(childimage_1_red, "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_child.bmp");
    	imageioer.myWrite(childimage_1_grey, "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_grey_child.bmp");
    	assertEquals(textpix("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_goal.bmp", "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_red_child.bmp", parentimage_1.getHeight(null), parentimage_1.getWidth(null), 24), true);
    	assertEquals(textpix("/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_gray_goal.bmp", "/home/kiddion/Downloads/bmptest_v3/bmptest/goal/1_grey_child.bmp", parentimage_2.getHeight(null), parentimage_2.getWidth(null), 24), true);
    }
### 其中的比较函数参考之前ImplementImageIO.java采用BufferedInputStream
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
