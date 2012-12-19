package com.shining.qrcodesimple;






import jp.sourceforge.qrcode.QRCodeDecoder;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class DecodeFragment extends Fragment{
	
		private ImageButton button_decode;
		private ImageButton button_pdecode;
		private TextView textview_content;
		
		private String strQR;
		
		public interface OnMyButtonClickListener1 {
			
			public void onMyButtonClick1(int i);
			
		}
		
		private OnMyButtonClickListener1 mListener1;
		
		public void onAttach(Activity activity) {
			
			super.onAttach(activity);
			
			try {
			mListener1 = (OnMyButtonClickListener1) activity;
			} catch (ClassCastException e) {
			
				throw new ClassCastException(activity.toString() + "must implement OnbtnSendClickListener");
			}
		}
	
	    public void onCreate(Bundle savedInstanceState)
	    {
	
	        super.onCreate(savedInstanceState);
	    }


	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	
	        return inflater.inflate(R.layout.fragment_decode, container, false);
	    }
	    
	    public void onActivityCreated(Bundle savedInstanceState){
			
		    super.onCreate(savedInstanceState);
	    
		    button_decode=(ImageButton)getActivity().findViewById(R.id.button_decode);
		    button_pdecode=(ImageButton)getActivity().findViewById(R.id.button_pdecode);
		    
		    textview_content=(TextView)getActivity().findViewById(R.id.textview_content);
		    
		    textview_content.setOnLongClickListener(new OnLongClickListener(){
			 
			 public boolean onLongClick(View v){
				 
				 if(textview_content.getText()==null)
				 {
	        			Toast.makeText(getActivity(), "内容为空！请先扫码或者解析图片！",
  	                    Toast.LENGTH_SHORT).show();
	        			return false;
				 }
				 copyToClipboard();
				 return true;
				 
			 }
		    });
			 
		    
		    MyButtonClickListener1 clickListener1 = new MyButtonClickListener1();
		    
		    button_decode.setOnClickListener(clickListener1
					);
		    
		    button_pdecode.setOnClickListener(clickListener1
					);
	    }
	    
	    
	    public void decode(){
	    	
	    Intent intent =new Intent("com.shining.qrcodesimple.library.com.google.zxing.client.android.SCAN");
	  
	     intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	     startActivityForResult(intent, 1);
	    	
	    }	
	    
	    public void pdecode(){
	    	
	    //	Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	    	intent.setType("image/*");
			startActivityForResult(intent, 0);
			
			
	    }
	    
		public void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        
			super.onActivityResult(requestCode, resultCode, data);  
	        
	        if (null != data) { 
	        
	        if(requestCode==0){
	        
	        
	            Uri selectedImage = data.getData();  
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };  
	      
	            Cursor cursor = getActivity().getContentResolver().query(selectedImage,  
	                    filePathColumn, null, null, null);  
	            cursor.moveToFirst();  
	      
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	            String picturePath = cursor.getString(columnIndex);  
	            cursor.close();  
	            
	            Bitmap bm=BitmapFactory.decodeFile(picturePath);
	            
	            if(strQR!="")
	            	strQR="";
	            
	            strQR=decodeQRImage(bm);
	            }  
	        
	        
	        else if(requestCode==1){
	        	
	        	strQR=data.getStringExtra("SCAN_RESULT");
	        }
	        }
	        textview_content.setText(strQR);
	       
		}
		
		public String decodeQRImage(Bitmap mBmp){
			
			String strDecodedData="";
			try{
				QRCodeDecoder decoder =new QRCodeDecoder();
				strDecodedData =new String(decoder.decode(new AndroidQRCodeImage(mBmp)));
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return strDecodedData;
		}
		
		private void copyToClipboard(){
			
			@SuppressWarnings("deprecation")
			ClipboardManager clip = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

			clip.setText(strQR); 
			
			Toast.makeText(getActivity(), "已成功复制文本到剪贴板!", Toast.LENGTH_LONG).show();
			
		}


	    public void onStop()
	    {
	       
	        super.onStop();
	    }
	    
	    class MyButtonClickListener1 implements OnClickListener
		{
			public void onClick(View v)
			{
				ImageButton button = (ImageButton) v;
				if (button == button_decode)
					mListener1.onMyButtonClick1(1); 
				if (button == button_pdecode)
					mListener1.onMyButtonClick1(2); 
				
			}
		}
}
