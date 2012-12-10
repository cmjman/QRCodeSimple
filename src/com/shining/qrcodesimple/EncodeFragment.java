package com.shining.qrcodesimple;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EncodeFragment  extends Fragment{
	
	private static final String SAVE_PICTURE_PATH=Environment.getExternalStorageDirectory().toString()+"/QRCode";
	
	private Button button_encode;
	private Button button_save;
	
	private ImageView imageview_qrcode;

	private EditText edittext_content;
	
	private Bitmap mBitmap;

	public interface OnMyButtonClickListener {
		
		public void onMyButtonClick(int i);
		
	}
	
	private OnMyButtonClickListener mListener;
	
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		try {
		mListener = (OnMyButtonClickListener) activity;
		} catch (ClassCastException e) {
		
			throw new ClassCastException(activity.toString() + "must implement OnbtnSendClickListener");
		}
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		
	    super.onCreate(savedInstanceState);
	    
	    button_encode=(Button)getActivity().findViewById(R.id.button_encode);
    	button_save=(Button)getActivity().findViewById(R.id.button_save);
    	
    	edittext_content=(EditText)getActivity().findViewById(R.id.edittext_content);
    	imageview_qrcode=(ImageView)getActivity().findViewById(R.id.imageview_qrcode);
        
        MyButtonClickListener clickListener = new MyButtonClickListener();
        
		
		
		 button_encode.setOnClickListener(clickListener
					);
			        
			       
			        button_save.setOnClickListener(clickListener);
			        
			        
	}
	
	 @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	        return inflater.inflate(R.layout.fragment_encode, container, false);
	    }
	    
	    
	    public void encode(int qrcodeVersion){
	    	
			try{
				
				String strEncoding=edittext_content.getText().toString();
				
				if(strEncoding.isEmpty())
					Toast.makeText(getActivity(), "输入内容为空，请先输入需要编码的内容！",
		                    Toast.LENGTH_SHORT).show();
				
				com.swetake.util.Qrcode qrcode=new com.swetake.util.Qrcode();
				
				
				// level L : About 7% or less errors can be corrected.
				//level M : About 15% or less errors can be corrected.
				//level Q : About 25% or less errors can be corrected.
				//level H : About 30% or less errors can be corrected.
				
				qrcode.setQrcodeErrorCorrect('H');
				
				qrcode.setQrcodeEncodeMode('B');
				
				qrcode.setQrcodeVersion(qrcodeVersion);
				
				byte[] bytesEncoding=strEncoding.getBytes("utf-8");
				if(bytesEncoding.length>0&&bytesEncoding.length<120){
					boolean[][] bEncoding=qrcode.calQrcode(bytesEncoding);
					
					drawQRCode(bEncoding,R.color.black);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	    
	    public void drawQRCode(boolean[][] bRect,int colorFill){
			
	    	  int  width = 540; 
			  int  height = 480; 
	
		
			 mBitmap=Bitmap.createBitmap(width,height,Config.ARGB_8888);
			 
			Canvas mCanvas=new Canvas();
			mCanvas.setBitmap(mBitmap);
	
			Paint mPaint=new Paint();
		
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setColor(colorFill);
			mPaint.setStrokeWidth(1.0F);
			
			for(int i=0;i<bRect.length;i++){
				for(int j=0;j<bRect.length;j++){
					if(bRect[j][i]){
						mCanvas.drawRect(new Rect(
								j*3+2,
								i*3+2,
								j*3+2+3,
								i*3+2+3
								),mPaint);
					}
				}
			}
			imageview_qrcode.setImageBitmap(mBitmap);
	
			
		}
	    
	    public boolean isQRCodeEmpty(){
	    	
	    	if(mBitmap==null)
	    		return true;
	    	return false;
	    }
	    
	    public void saveQRCode(){
			
			File path=new File(SAVE_PICTURE_PATH);
			
			if(!path.exists()){
				path.mkdir();
			}
			
			File f=new File(SAVE_PICTURE_PATH + File.separator+"QRCard_"+System.currentTimeMillis()+".jpg");
				
			try{
				
				BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(f));
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
				os.flush();
				os.close();
				
				Toast.makeText(getActivity(), "保存QRCode成功！保存位置为："+SAVE_PICTURE_PATH,
	                    Toast.LENGTH_SHORT).show();
				
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	    
	    public void onResume()
		{
	    	super.onResume();
		}
	    

	    @Override
	    public void onStop()
	    {
	   
	        super.onStop();
	    }
	    
	    class MyButtonClickListener implements OnClickListener
		{
			public void onClick(View v)
			{
				Button button = (Button) v;
				if (button == button_encode)
					mListener.onMyButtonClick(1); 
				if (button == button_save)
					mListener.onMyButtonClick(2); 
				
			}
		}
	   
	
}
