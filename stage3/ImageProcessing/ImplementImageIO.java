package helloworld;

import imagereader.*;
import java.awt.*;
import java.io.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;


public class ImplementImageIO implements IImageIO
{
     public Image myRead(String filePath) {
       try {
         BufferedInputStream bufferimage = new BufferedInputStream(new FileInputStream(filePath));
         
         //width:18-21
         bufferimage.skip(18);       
                  
         //get width
         byte[] b_width = new byte[4];
         bufferimage.read(b_width);
         
         //height:22-25 get height
         byte[] b_height = new byte[4];
         bufferimage.read(b_height);

         //get integer of width and height
         int width = bytetoInt(b_width);
         int height = bytetoInt(b_height);
         
         //bit:28-29
         bufferimage.skip(2);
         int bitcount = (int)bufferimage.read();
         bitcount = bitcount | bufferimage.read() << 8;
         
         bufferimage.skip(24);

        
       if (bitcount == 24) {
    	   //give space to save all pixels
    	   int[] pixMatrix = new int[height * width];
    	   
           //每一行颜色占用的字节数规定为4的整数倍
           int skipnum = (bitcount * width / 8) % 4;
           if (skipnum != 0) {
               skipnum = 4 - skipnum;
           }
         
      	   //get every pixel
           //from bottom to top ,from left to right
    	   //from b to g to r
           for (int i = height - 1; i >= 0; i--) {
               for (int j = 0; j < width; j++) {
                   int blue = bufferimage.read();
                   int green = bufferimage.read();
                   int red = bufferimage.read();
                   Color temp = new Color(red, green, blue);
                   pixMatrix[i * width + j] = temp.getRGB();
              }
                if (skipnum != 0) {
                    bufferimage.skip(skipnum); 
                }
          }
        bufferimage.close();
        return Toolkit.getDefaultToolkit().createImage
        		(new MemoryImageSource(width, height, pixMatrix, 0, width));
        }
       
       //Only can process whose bits of pixel is 24 now
       else {
    	   System.out.println("We only can process whose bits of pixel is 24 now");
           bufferimage.close();
           return null;
        }

     } catch (Exception e) {
       e.printStackTrace();
     }
       
       return null;
     }

     //change byte to integer(get width and height) 
     public int bytetoInt(byte[] target) {
           int bit_0 = target[0] & 0xFF;
           int bit_1 = target[1] & 0xFF;
           int bit_2 = target[2] & 0xFF;
           int bit_3 = target[3] & 0xFF;
           int num = bit_3 << 24 | bit_2 << 16 | bit_1 << 8 | bit_0;
           return num;
      }

     //write image to file
     public Image myWrite(Image image, String savePath) {
    	 BufferedImage saveimage;
    	 saveimage = new BufferedImage
       		  (image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
    	 Graphics g = saveimage.getGraphics();
         g.drawImage(image, 0, 0, null);
         
         //you can input xxx or xxx.bmp to save a bmp image
         try {               
        	 if(savePath.indexOf(".bmp") == -1) {
        		 ImageIO.write(saveimage, "bmp", new File(savePath + ".bmp" ));
        	 }
        	 else {
        		 ImageIO.write(saveimage, "bmp", new File(savePath));
        	 }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
}
