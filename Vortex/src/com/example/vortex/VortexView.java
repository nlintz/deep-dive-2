package com.example.vortex;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class VortexView extends GLSurfaceView {
	private static final String LOG_TAG = VortexView.class.getSimpleName();
	private VortexRenderer _renderer;
	private float _x = 0;
	private float _y = 0;

	
	public VortexView(Context context){
		super(context);
		_renderer = new VortexRenderer();
		setRenderer(_renderer);
	}
	
	public boolean onTouchEvent(final MotionEvent event) {
		final float xdiff = (_x - event.getX());
		final float ydiff = (_y - event.getY());
		queueEvent(new Runnable() {
			public void run() {
				_renderer.setXAngle(_renderer.getXAngle() + ydiff);
				_renderer.setYAngle(_renderer.getYAngle() + xdiff);
			}
		});
		return true;
	}

}
