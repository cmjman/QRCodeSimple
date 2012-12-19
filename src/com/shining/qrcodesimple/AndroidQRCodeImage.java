package com.shining.qrcodesimple;

import android.graphics.Bitmap;
import jp.sourceforge.qrcode.data.QRCodeImage;

public class AndroidQRCodeImage implements QRCodeImage {
	
	Bitmap image;
	
	public AndroidQRCodeImage(Bitmap image){
		this.image=image;
	}
	 
	 
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
	
	public int getPixel(int x,int y){
		return image.getPixel(x, y);
	}
}
