package PixelNode;


public class PixelNode implements Comparable<PixelNode>{
	private int red;
	private int green;
	private int blue;
	private int frequency;
	private int codeIndex;
	
	public PixelNode(){
		this.red = 0;
		this.green = 0;
		this.blue = 0;
		this.frequency = 0;
		this.codeIndex = 0;
	}
	
	public int compareTo(PixelNode pixel) {
        return Integer.compare(this.frequency, pixel.frequency);
    }
	
	
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getCodeIndex() {
		return codeIndex;
	}
	public void setCodeIndex(int codeIndex) {
		this.codeIndex = codeIndex;
	}
	
	
	
	
}
