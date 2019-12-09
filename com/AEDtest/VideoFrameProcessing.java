package com.AEDtest;


import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
import java.util.Scanner;  
import javax.imageio.ImageIO;  
import org.bytedeco.javacv.FFmpegFrameGrabber;  
import org.bytedeco.javacv.Frame;  
import org.bytedeco.javacv.FrameGrabber.Exception;  
import org.bytedeco.javacv.Java2DFrameConverter;

import Huffman.HuffmanCompressing;  

//import com.sum.xml.internal.rngom.parse.host.Base;
//import org.apache.commons.codec.binary.Base64;

public class VideoFrameProcessing {  
	 private FFmpegFrameGrabber frameGrabber;
     /**  
      * @param args  
      * @throws IOException  
      */  
      public static void main(String []args) throws Exception, IOException  {  
                //Scanner s=new Scanner(System.in);  
                //System.out.println("Enter the path of mp4 (for eg c:\\test.mp4)");  
                String mp4Path= "C:\\Users\\lucas\\Documents\\UFABC\\Disciplinas\\Algoritmos e estrutura de dados II (AEDII)\\Projeto\\res\\teapot.mp4";//"c:\\teapot.mp4" ;     360.wmv      //s.nextLine();  
                //System.out.println("Enter the folder path where the images will be saved (eg c:\\convertedImages)");  
                String imagePath="c:\\convertedImages";      // s.nextLine();  
                String compPath="c:\\convertedImages\\test.txt";
                //convertMovietoJPG(mp4Path, imagePath,"jpg",30);  
                System.out.println("Conversion complete. Please find the images at "+imagePath);  
                
                
                VideoFrameProcessing grabber = new VideoFrameProcessing();
                //frameGrabber = new FFmpegFrameGrabber(mp4Path); 
                BufferedImage imgFrame = grabber.getFrame(1, mp4Path);
                if (imgFrame != null) HuffmanCompressing.initialize(compPath, imgFrame);
                
                
       } 
      
      public static void convertMovietoJPG(String mp4Path, String imagePath, String imgType, int frameJump) throws Exception, IOException  {
           Java2DFrameConverter converter = new Java2DFrameConverter();  
           FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(mp4Path);  
           frameGrabber.start();  
           Frame frame;  
           double frameRate=frameGrabber.getFrameRate();  
           int imgNum=0;  
           System.out.println("Video has "+frameGrabber.getLengthInFrames()+" frames and has frame rate of "+frameRate);  
           try {           
             for(int ii=1;ii<=frameGrabber.getLengthInFrames();ii++){  
            	 imgNum++;       
            	 frameGrabber.setFrameNumber(ii);  
            	 frame = frameGrabber.grab();  
            	 BufferedImage bi = converter.convert(frame);  
            	 String path = imagePath+File.separator+imgNum+".jpg";  
            	 ImageIO.write(bi,imgType, new File(path));  
            	 ii+=frameJump;  
           }  
           frameGrabber.stop(); 
           //frameGrabber.close();
           } catch (Exception e) {  
             e.printStackTrace();  
         }  
       }  
      
      public BufferedImage getFrame(int numFrame, String path)  {
    	  FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(path);
    	  Frame frame;
    	  try {       
    		  frameGrabber.start(); 
              if(numFrame<=frameGrabber.getLengthInFrames()){  
            	  Java2DFrameConverter converter = new Java2DFrameConverter();
            	  frameGrabber.setFrameNumber(numFrame);  
            	  frame = frameGrabber.grab();  
            	  BufferedImage bi = converter.convert(frame);  
            	  frameGrabber.stop(); 
            	  return bi;
              }  
            } catch (Exception e) {  
              e.printStackTrace();  
            }
    	  return null;
    	  
      }
      
      public void setGrabber(String videoPath) {
    	  this.frameGrabber = new FFmpegFrameGrabber(videoPath);
      }
      
      public FFmpegFrameGrabber getGrabber() {
    	  return this.frameGrabber;
      }
      
      
} 
