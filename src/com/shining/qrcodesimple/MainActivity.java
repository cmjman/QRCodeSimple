package com.shining.qrcodesimple;




import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.shining.qrcodesimple.EncodeFragment.OnMyButtonClickListener;
import com.shining.qrcodesimple.EncodeFragment;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements OnMyButtonClickListener{
	


	
	private FragmentTabHost mTabHost;
	
	private EncodeFragment encodeFragment;


	private DecodeFragment decodeFragment;
	

	
	
	
//	private ViewPager mViewPager;  
	

	
	
	
	public void onMyButtonClick(int i) {
		
		 encodeFragment= (EncodeFragment) getSupportFragmentManager().findFragmentByTag("encode");
		
		switch(i)
		{
			case 1:
			{
			//	getFragmentManager().findFragmentByTag("encode").;
				
			//	System.out.println("1111");
				
				encodeFragment.encode(10);break;
			}
			case 2:
			{
			//	if(mBitmap==null)
			//		Toast.makeText(MainActivity.this,"请先生成二维码再保存！",Toast.LENGTH_SHORT).show();
		//		else 
					encodeFragment.saveQRCode();
				break;
			}
			
		}
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		 mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
	        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

	        mTabHost.addTab(mTabHost.newTabSpec("encode").setIndicator("编码"),
	                EncodeFragment.class, null);
	        mTabHost.addTab(mTabHost.newTabSpec("decode").setIndicator("解码"),
	                DecodeFragment.class, null);
	        
	       
	        
	       
	        
	       
	      
	
	
		
	}
	
	
	
	
	
	
	
	
}
