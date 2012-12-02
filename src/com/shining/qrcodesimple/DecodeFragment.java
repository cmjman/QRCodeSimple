package com.shining.qrcodesimple;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DecodeFragment extends Fragment{
	
	
	    public void onCreate(Bundle savedInstanceState)
	    {
	
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	
	        return inflater.inflate(R.layout.fragment_decode, container, false);
	    }

	    @Override
	    public void onStop()
	    {
	       
	        super.onStop();
	    }
	  
}
