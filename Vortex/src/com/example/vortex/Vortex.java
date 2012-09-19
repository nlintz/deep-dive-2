package com.example.vortex;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Vortex extends Activity {
	private static final String LOG_TAG = Vortex.class.getSimpleName();
	private VortexView _vortexView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	_vortexView = new VortexView(this);
    	setContentView(_vortexView);
    }
}