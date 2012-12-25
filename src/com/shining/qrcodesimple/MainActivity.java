package com.shining.qrcodesimple;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import com.shining.qrcodesimple.DecodeFragment.OnMyButtonClickListener1;
import com.shining.qrcodesimple.EncodeFragment;
import com.shining.qrcodesimple.EncodeFragment.OnMyButtonClickListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



public class MainActivity extends FragmentActivity  implements OnMyButtonClickListener,OnMyButtonClickListener1
															{

	private FragmentTabHost mTabHost;
	
	private EncodeFragment encodeFragment;

	private DecodeFragment decodeFragment;
	
	public void onMyButtonClick(int i) throws FileNotFoundException {
		
		encodeFragment= (EncodeFragment) getSupportFragmentManager().findFragmentByTag("encode");
		
		switch(i)
		{
			case 1:
			{
				encodeFragment.encode(15);break;
			}
			case 2:
			{
				if(encodeFragment.isQRCodeEmpty())
					Toast.makeText(MainActivity.this,"请先生成二维码再保存！",Toast.LENGTH_SHORT).show();
				else 
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
	    mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
	
	    mTabHost.addTab(mTabHost.newTabSpec("encode").setIndicator("编码",
	        		getResources().getDrawable(R.drawable.button_encode)),
	                EncodeFragment.class, null);
	    
	    mTabHost.addTab(mTabHost.newTabSpec("decode").setIndicator("解码",
	        		getResources().getDrawable(R.drawable.button_decode)),
	                DecodeFragment.class, null);
	   }
	
	public void ExitProgram(){
		 
		  Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exit);
        System.exit(0);
               
		 
	 }
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			
			if(keyCode==KeyEvent.KEYCODE_BACK) {
				
				new AlertDialog.Builder(this)
				 .setTitle("退出")
				 .setMessage("确定退出吗？")
				 .setIcon(R.drawable.ic_launcher)
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				                 
				      public void onClick(DialogInterface dialog, int which) {
				                
				    	  ExitProgram();
				    	
				      }
				  })
				 .setNegativeButton("取消", new DialogInterface.OnClickListener() {
				                 
				        public void onClick(DialogInterface dialog, int which) {
				                  
				           dialog.cancel();
				        }
			            
				 })
			     .show();
				
				return true;
			}
			 
			return super.onKeyDown(keyCode, event);
		}
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
	 
		 
		 menu.add(0, 0, 0, "帮助");
		 menu.add(0, 1, 1, "退出");
		 
		 return super.onCreateOptionsMenu(menu);
	 }
	 

     public boolean onOptionsItemSelected(MenuItem item) {
   
    	 if(item.getItemId() == 0){
    		 
    		 LayoutInflater inflater = getLayoutInflater();
    		 
    		 View layout = inflater.inflate(R.layout.menu_help,(ViewGroup) findViewById(R.id.dialog));

    		 new AlertDialog.Builder(this)
    		 				.setTitle("使用帮助")
    		 				.setView(layout)
    		 				.setPositiveButton("返回",new DialogInterface.OnClickListener() {
    						                 
    		 					public void onClick(DialogInterface dialog, int which) {
    		 						                  
    		 						dialog.cancel();
    		 					}}).show();
    	 }
    	 else
    	 if(item.getItemId() == 1){
    		 
    		  ExitProgram();
	        	 
    	 }
    	
         return true;
     }
}
