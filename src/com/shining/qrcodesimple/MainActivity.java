package com.shining.qrcodesimple;






import com.shining.qrcodesimple.DecodeFragment.OnMyButtonClickListener1;
import com.shining.qrcodesimple.EncodeFragment.OnMyButtonClickListener;
import com.shining.qrcodesimple.EncodeFragment;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;


public class MainActivity extends FragmentActivity implements OnMyButtonClickListener,OnMyButtonClickListener1{
	
	private FragmentTabHost mTabHost;
	
	private EncodeFragment encodeFragment;

	private DecodeFragment decodeFragment;
	
	public void onMyButtonClick(int i) {
		
		encodeFragment= (EncodeFragment) getSupportFragmentManager().findFragmentByTag("encode");
		
		switch(i)
		{
			case 1:
			{
				
				/*
 					Size of QRcode is defined as version.
					Version is from 1 to 40.
					Version 1 is 21*21 matrix. And 4 modules increases whenever 1 version increases. 
					So version 40 is 177*177 matrix.
				 */
				encodeFragment.encode(30);break;
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
	
	public void onMyButtonClick1(int i) {
	
		decodeFragment= (DecodeFragment) getSupportFragmentManager().findFragmentByTag("decode");
	
		switch(i)
		{
			case 1:
			{
				decodeFragment.decode();
				break;
			}
			case 2:
			{
				decodeFragment.pdecode();
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
