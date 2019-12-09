package com.AEDtest;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.text.html.ImageView;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import com.AEDtest.VideoFrameProcessing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

public class TransmissionTest {
	//@FXML StackPane s;
	//private static Stage stage;
    //private static Scene menuPrincipalScene;
    
    private static FlowLayout        flowLayout = new FlowLayout(FlowLayout.CENTER);
    
    
	/*public static void main() {
		launch(args);
	}*/
	
	/*public void start(Stage primaryStage) throws IOException {
		String mp4Path= "C:\\Users\\lucas\\Documents\\UFABC\\Disciplinas\\Algoritmos e estrutura de dados II (AEDII)\\Projeto\\res\\640.wmv";  
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(mp4Path);  
        frameGrabber.start();  
        stage = primaryStage;
        BufferedImage frameImage;
        
        StackPane stack = new StackPane();
        
        int i;
        for(i=1; i<20; i++) {
        	frameImage = MovieToImage.getFrame(i, frameGrabber);
        	Image img = SwingFXUtils.toFXImage(frameImage, null);
        	ImageView v = new ImageView(img);
            stack.getChildren().add(v);
        }
        
        
       //.setStage(stage);
        primaryStage.setTitle("Teste");

        //Parent fxmlMenuPrincipal = FXMLLoader.load(getClass().getResource("/View/MenuPrincipal.fxml"));
        //menuPrincipalScene = new Scene(fxmlMenuPrincipal, 600, 400);

       

        primaryStage.setScene(menuPrincipalScene);
        primaryStage.show();
    }*/
	
	/*private void loadGUI(){
        // Create VideoPanel 
        Panel videoPanel = new Panel(); 
        setLayout(new BorderLayout()); 
        add(videoPanel, BorderLayout.WEST); 
        
         
        // Panel 
        GridLayout grid = new GridLayout(1,2); 
        JPanel panelTop = new JPanel(grid); 
        JPanel p1 = new JPanel(flowLayout); 
        p1.add(new JLabel("ORIGINAL")); 
        //JPanel p2 = new JPanel(flowLayout); 
        //p2.add(new JLabel("PROCESSED")); 
        panelTop.add(p1); 
        //panelTop.add(p2); 
        add(panelTop, BorderLayout.NORTH); 
    } */
	
	
	public void startTransmission(String videoPath, int typeCompression) {
		VideoFrameProcessing grabber = new VideoFrameProcessing();
		grabber.setGrabber(videoPath);

		
		int numFrames = grabber.getGrabber().getLengthInFrames();
		BufferedImage frameImage;
		// Load gui (graphical interface)
		
		try {
			grabber.getGrabber().start();
			for(int frame = 1; frame <= numFrames; frame++) {
				//frameImage = grabber.getFrame(frame);
				// Compression of one frame (using Huffman)
				// Decoding of the compressed frame
				// set frame in graphical interface
			}
			grabber.getGrabber().stop();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// close graphical interface
		
		System.out.println("Video transmission completed.");
	}
	
	

}
