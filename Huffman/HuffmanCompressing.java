++package Huffman;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.PriorityQueue;

public class HuffmanCompressing {


    private static PriorityQueue<TREE> priorityQueue = new PriorityQueue<TREE>();
    //private static int[] frequency = new int[300];
    private static int[][][] frequency = new int [256][256][256];
    private static String[] intToString;
    //private static String[] intToString = new String[300];
    private static byte bt;
    private static int numberOfChar; // number of different characters
    private static int numberOfColors; // number of different colors in the frame
    private static int numCodedColors = 0; 
    private static int [][] colors;
    private static String[] RGBHuffmanCode;

    // main tree class
    static class TREE implements Comparable<TREE> {
        TREE leftChild;
        TREE rightChild;
        String deb; // Guarda o código da codificação
        int Bite;
        int[] RGB = null;
        int frequency;

        public int compareTo(TREE tree) {
            return Integer.compare(this.frequency, tree.frequency);
        }
    }

    private static TREE root;

    //Implementar para calcular a frequência de um frame
    
    private static void calculateFrequency(BufferedImage frame) {
    	numberOfColors = 0;
    	
    	for(int x = 0; x<frame.getWidth(); x++) {
    		for(int y = 0; y<frame.getHeight(); y++) {
    			Color c = new Color(frame.getRGB(x, y));
                int red = (int) c.getRed();
                int green = (int) c.getGreen();
                int blue = (int) c.getBlue();
                if (frequency[red][green][blue] == 0) numberOfColors++;
                frequency[red][green][blue]++;
    		}
    	}
    	
    	//intToString = new String[numberOfColors];
    	RGBHuffmanCode = new String[numberOfColors];
    	colors = new int[numberOfColors][5];
    	
    }
    
    private static void calculateFrequency(String fileName) {
        File file;
        Byte bt;

        file = new File(fileName);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            while (true) {
                try {

                    bt = dataInputStream.readByte();
                    //frequency[byteToBinary(bt)]++;
                } catch (EOFException eof) {
                    System.out.println("End of File");
                    break;
                }
            }
            fileInputStream.close();
            dataInputStream.close();
        } catch (IOException e) {
            System.out.println("IO Exception =: " + e);
        }
        file = null;
    }

    private static int byteToBinary(Byte bt) {
        int temp = bt;

        if (temp < 0) {
            temp = ~bt;
            temp = temp + 1;
            temp = temp ^ 255;
            temp += 1;
        }
        return temp;
    }

    private static void initialize() {

        //numberOfChar = 0;
    	numberOfColors = 0;
        if (root != null)
            byteToBinary(root);
        for(int r=0; r<255; r++) {
        	for(int g=0; g<255; g++) {
        		for(int b=0; b<255; b++) {
        			frequency[r][g][b] = 0;
        		}
        	}
        }
        /*for (int i = 0; i < 300; i++)
            intToString[i] = "";*/
        priorityQueue.clear();
    }

    private static void byteToBinary(TREE tree) {

        if (tree.leftChild == null && tree.rightChild == null) {
            return;
        }
        if (tree.leftChild != null)
            byteToBinary(tree.leftChild);
        if (tree.rightChild != null)
            byteToBinary(tree.rightChild);
    }
    
    /*
     * private static int numCodedColors = 0; 
    private static int [][] colors;
    private static String[] RGBHuffmanCode;
     * */


    private static void depthFirstSearch(TREE tree, String string) {
        tree.deb = string;
        if ((tree.leftChild == null) && (tree.rightChild == null)) {
            //intToString[tree.Bite] = string;
        	if (tree.RGB != null && numCodedColors < numberOfColors) {
        		int rgb=0;
        		for (rgb=0; rgb<3; rgb++) {
        			colors[numCodedColors][rgb] = tree.RGB[rgb];
        		}
        		int r = tree.RGB[0], g = tree.RGB[1], b = tree.RGB[2];
        		colors[numCodedColors][rgb] = frequency[r][g][b]; // Saving color frequency 
        		rgb++;
        		colors[numCodedColors][rgb] = numCodedColors;
        		RGBHuffmanCode[numCodedColors] = string; // Saving the prefix tree path code 
        		numCodedColors++;
        	}
            return;
        }
        if (tree.leftChild != null)
            depthFirstSearch(tree.leftChild, string + "0");
        if (tree.rightChild != null)
            depthFirstSearch(tree.rightChild, string + "1");
    }


    private static void createNode() {

        priorityQueue.clear();
        
        int numberOfSymbols = 0;
        
        for(int r=0; r<255; r++) {
        	for(int g=0; g<255; g++) {
        		for(int b=0; b<255; b++) {
        			if (frequency[r][g][b] != 0){;
        				numberOfSymbols++;
        				TREE Temp = new TREE();
        				 Temp.RGB = new int[3];
        				 Temp.RGB[0] = r;  Temp.RGB[1] = g;  Temp.RGB[2] = b;
        				 Temp.frequency = frequency[r][g][b];
        				 Temp.leftChild = null;
        	             Temp.rightChild = null;
        	             priorityQueue.add(Temp);
        			}
        		}
        	}
        }

        System.out.println("Number of symbols with non-zero freq: "+numberOfSymbols);
        

        TREE temp1, temp2;


        while (priorityQueue.size() != 1) {
            TREE Temp = new TREE();
            temp1 = priorityQueue.poll();
            temp2 = priorityQueue.poll();
            Temp.leftChild = temp2;
            Temp.rightChild = temp1;
            if (temp1 != null && temp2 != null){
                Temp.frequency = temp1.frequency + temp2.frequency;
            }

            priorityQueue.add(Temp);
        }

        root = priorityQueue.poll();
    }
    
    private static String linearSearchCode(int r, int g, int b) {
    	int i;
    	for (i=0; i < numCodedColors; i++){ 
    		if (r == colors[i][0] && g == colors[i][1] && b == colors[i][2]) {
    			return RGBHuffmanCode[i];
    		}
    	}
    	return null;
    }
    
    private static String binarySearchCode(int r, int g, int b) {
    	int ini = 0, fin = numCodedColors; 
    	int half = numCodedColors/2;
    	int[] code;
    	while (ini<=fin) {
    		code = colors[half];
    		if(code[0]==r && code[1]==g && code[2]==b) {
    			return RGBHuffmanCode[code[4]];
    		/*} else if () {
    			
    		} else {*/
    			
    		}
    	}
    	return null;
    	
    }
    
    private static void createCompressedArq(String filePath, BufferedImage frame) {
    	// Saving header
    	// Header : numberOfColors+colors[][]
    	try {
        	FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            
            dataOutputStream.writeInt(numberOfColors);
            int i;
           	for (int code = 0; code < numCodedColors; code++) {
           		for (i=0; i < 4; i++) {
           			dataOutputStream.writeInt(colors[code][i]);
           		}
           	}
         
           	// Saving encoded symbols
           	/*x<frame.getWidth(), y<frame.getHeight()*/
        	
        	for(int x = 0; x<1; x++) {
        		for(int y = 0; y<10; y++) {
        			Color c = new Color(frame.getRGB(x, y));
                    int red = (int) c.getRed();
                    int green = (int) c.getGreen();
                    int blue = (int) c.getBlue();
                    
                    // Fazer uma busca binária
                    String code = linearSearchCode(red, green, blue);
                    
                    if (code != null) {
                    	for (int bit = 0; bit < code.length(); bit++) {
                        	dataOutputStream.writeChar(code.charAt(bit));
                        }
                    } else {
                    	// For testing
                    	code = RGBHuffmanCode[0];
                    	for (int bit = 0; bit < code.length(); bit++) {
                        	dataOutputStream.writeChar(code.charAt(bit));
                        }
                    }
                    
        		}
        	}
        	
            dataOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("IO Exception =: " + e);
        }
    	
    	
    	
    }
    
    public static void mergeSort(int vetor[][], int ini, int fin) {
        int meio;
        if (ini < fin) {
            meio = (ini + fin) / 2;
            mergeSort(vetor, ini, meio);
            mergeSort(vetor, meio + 1, fin);
            merge(vetor, ini, meio, fin);
        }
    }
 
    public static void merge(int vetor[][], int inicio, int meio, int fim) {
        int i, j, k;
        int vetorB[][] = new int[vetor.length][5];
        for (i = inicio; i < meio; i++) {
            vetorB[i] = vetor[i];
        }
        for (j = meio + 1; j < fim; j++) {
            vetorB[fim + meio + 1 - j] = vetor[j];
        }
        i = inicio;
        j = fim;
        for (k = inicio; k < fim; k++) {
            if (vetorB[i][3] <= vetorB[j][3]) {
                vetor[k] = vetorB[i];
                i = i + 1;
            } else {
                vetor[k] = vetorB[j];
                j = j - 1;
            }
        }
    }

    
    private static void printResults() {
    	int index;
    	int coded = numCodedColors;
    	int arrayCode = RGBHuffmanCode.length;
    	int color = numberOfColors;
    	int numColors = colors.length;
    	for (int code=0; code < numberOfColors; code++) {
    		index = colors[code][4];
    		System.out.println("RGB: "+colors[code][0]+", "+colors[code][1]+", "+colors[code][2]+
    				":"+RGBHuffmanCode[index]+", freq: "+colors[code][3]);
    		/*if(colors[code][0] == 33) {
    			System.out.println("RGB: "+colors[code][0]+", "+colors[code][1]+", "+colors[code][2]+
        				":"+RGBHuffmanCode[index]+", freq: "+colors[code][3]);
    		}*/
    	}
    	
    	/*for (int code=0; code < numCodedColors; code++) {
    		index = colors[code][4];
    		System.out.println("RGB: "+colors[code][0]+", "+colors[code][1]+", "+colors[code][2]+
    				":"+RGBHuffmanCode[index]+"--"+colors[code][3]);
    	}*/
    }


    public static void initialize(String arg) {
        initialize();
        calculateFrequency(arg);
        createNode();

        if (numberOfChar > 1) {
            depthFirstSearch(root, "");
        }
        
        printResults();

        //zipping(arg, arg + ".hufz");
        //initialize();
    }
    
    public static void initialize(String filaPath, BufferedImage frame) {
        initialize();
        calculateFrequency(frame);
        createNode();

        /*if (numCodedColors > 1) {
            depthFirstSearch(root, "");
        }*/
        depthFirstSearch(root, "");
        
        //mergeSort(colors, 0, numCodedColors-1);
        
        printResults();
        //createCompressedArq(filaPath, frame);

        //zipping(arg, arg + ".hufz");
        //initialize();
    }

}
