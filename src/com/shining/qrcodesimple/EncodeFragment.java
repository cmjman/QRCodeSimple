package com.shining.qrcodesimple;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class EncodeFragment  extends Fragment{
	
	private static final String SAVE_PICTURE_PATH=Environment.getExternalStorageDirectory().toString()+"/QRCode";
	
	private ImageButton button_encode;
	
	private ImageButton button_save;
	
	private ImageView imageview_qrcode;

	private EditText edittext_content;
	
	private Bitmap mBitmap;
	
	private File file;

	public interface OnMyButtonClickListener {
		
		public void onMyButtonClick(int i) throws FileNotFoundException;
		
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
	    
	    button_encode=(ImageButton)getActivity().findViewById(R.id.button_encode);
	    button_save=(ImageButton)getActivity().findViewById(R.id.button_save);
    	
    	edittext_content=(EditText)getActivity().findViewById(R.id.edittext_content);
    	imageview_qrcode=(ImageView)getActivity().findViewById(R.id.imageview_qrcode);
        
        MyButtonClickListener clickListener = new MyButtonClickListener();
        
        button_encode.setOnClickListener(clickListener);
			
        button_save.setOnClickListener(clickListener);
			       
		imageview_qrcode.setLongClickable(true);
		imageview_qrcode.setOnLongClickListener(new OnLongClickListener(){
			 
			 public boolean onLongClick(View v){
				 
				 if(file==null)
				 {
	        			Toast.makeText(getActivity(), "请先保存二维码！",
     	                    Toast.LENGTH_SHORT).show();
	        			return false;
				 }
        			
        		Uri uri=Uri.fromFile(file);
        		
        		Intent intent=new Intent(Intent.ACTION_SEND);   
        		intent.setType("image/*");
        		intent.putExtra(Intent.EXTRA_STREAM, uri);
        		intent.putExtra(Intent.EXTRA_TEXT, "二维码：");    
        		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
            	
        		startActivity(Intent.createChooser(intent,"分享我的QRCode"));  
				 
				 return true;
			 }
			 
		 });
			        
	}

	public void onCreate(Bundle savedInstanceState){
	        
		super.onCreate(savedInstanceState);
	   
	}

	    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	        
	    	return inflater.inflate(R.layout.fragment_encode, container, false);
	}
	    
	    
	public void encode(int qrcodeVersion){
	    	
		try{
				
			String strEncoding=edittext_content.getText().toString();
				
			if(strEncoding.isEmpty())
				Toast.makeText(getActivity(), "输入内容为空，请先输入需要编码的内容！",Toast.LENGTH_SHORT).show();
				
			com.swetake.util.Qrcode qrcode=new com.swetake.util.Qrcode();
	
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
			
	    int  width = 240; 
	    int  height = 240; 

	    mBitmap=Bitmap.createBitmap(width,height,Config.ARGB_8888);
			
	    Canvas mCanvas=new Canvas(mBitmap);
		mCanvas.drawARGB(169,169, 169, 169);
	
		Paint mPaint=new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(colorFill);
		mPaint.setStrokeWidth(2.0F);
			
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
	    
	    public void saveQRCode() throws  FileNotFoundException{
	    	
			File path=new File(SAVE_PICTURE_PATH);
			
			if(!path.exists()){
				path.mkdir();
			}
			
	
			String filename="QRCard_"+System.currentTimeMillis()+".png";
			file=new File(SAVE_PICTURE_PATH + File.separator+filename);
			
	
			ContentResolver cr =getActivity().getContentResolver();
			
			try{
				
				BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(file));
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
			
			MediaStore.Images.Media.insertImage(cr, SAVE_PICTURE_PATH + File.separator+filename, filename, "");
				 
			getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
		}
	    
	    public void onResume()
		{
	    	super.onResume();
		}
	
	    public void onStop()
	    {
	   
	        super.onStop();
	    }
	    
	    class MyButtonClickListener implements OnClickListener
		{
			public void onClick(View v)
			{
				ImageButton button = (ImageButton) v;
				try {
				if (button == button_encode)
					
					mListener.onMyButtonClick(1);
			
				if (button == button_save)
					
					mListener.onMyButtonClick(2); 
				
				} catch (FileNotFoundException e) {
				
					e.printStackTrace();
				}
			}
		}
}
