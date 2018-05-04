package helloworld;

import java.awt.*;
import imagereader.*;
import java.awt.image.*;

//four color
enum COLOR {  
    RED, GREEN, BLUE, GREY
}

public class ImplementImageProcessor implements IImageProcessor
{
	//change the color to Red
    public Image showChanelR(Image sourceImage) {
  	  return Toolkit.getDefaultToolkit().createImage
  			  (new FilteredImageSource(sourceImage.getSource(), new rgbgFilter(COLOR.RED)));
    }
 	//change the color to Green
    public Image showChanelG(Image sourceImage) {
  	  return Toolkit.getDefaultToolkit().createImage
  			  (new FilteredImageSource(sourceImage.getSource(), new rgbgFilter(COLOR.GREEN)));
    }
    //change the color to Blue
    public Image showChanelB(Image sourceImage) {     
        return Toolkit.getDefaultToolkit().createImage
      		   (new FilteredImageSource(sourceImage.getSource(), new rgbgFilter(COLOR.BLUE)));
    }
    //change the color to Grey
    public Image showGray(Image sourceImage) {
  	  return Toolkit.getDefaultToolkit().createImage
     		   (new FilteredImageSource(sourceImage.getSource(), new rgbgFilter(COLOR.GREY)));
    }
}

//change color
class rgbgFilter extends RGBImageFilter
{
     private COLOR my_color;
     public rgbgFilter(COLOR temp_Color) {
            my_color = temp_Color;
            canFilterIndexColorModel = true;
     }

     public int filterRGB(int x, int y, int rgb) {
    	 //Red:16-23
         if (my_color.equals(COLOR.RED)) {
             return (rgb & 0xFFFF0000);
         }
         //Green:8-15
         else if (my_color.equals(COLOR.GREEN)) {
        	 return (rgb & 0xFF00FF00);
         }
         
         //Blue:0-7
         else if (my_color.equals(COLOR.BLUE)) {
             return (rgb & 0xFF0000FF);       
         }
         
         else if (my_color.equals(COLOR.GREY)) {
             int r = (int)(((rgb & 0x00FF0000) >> 16) * 0.299);
             int g = (int)(((rgb & 0x0000FF00) >> 8) * 0.587);
             int b = (int)((rgb & 0x000000FF) * 0.114);
             int grey = (int)(r + g + b);
             
             //Alpha:24-31
             return (rgb & 0xFF000000 | grey << 16 | grey << 8 | grey);

         }
         return rgb;
     }
}