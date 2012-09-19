package com.example.vortex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class VortexRenderer implements GLSurfaceView.Renderer {
	private static final String LOG_TAG = VortexRenderer.class.getSimpleName();

	private ShortBuffer _indexBuffer;
	private FloatBuffer _vertexBuffer;	
	private FloatBuffer _colorBuffer;

	private int _nrOfVertices = 0;

	private float _xAngle;
	private float _yAngle;


	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// preparation
		gl.glClearColor(0f, 0f, 0f, 1.0f);
		
		gl.glEnable(GL10.GL_CULL_FACE);
		
		gl.glFrontFace(GL10.GL_CCW);
		
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		initTriangle();
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
	}

	public void setXAngle(float angle) {
		_xAngle = angle;
	}
	
	public float getXAngle() {
		return _xAngle;
	}
	
	public void setYAngle(float angle) {
		_yAngle = angle;
	}
	
	public float getYAngle() {
		return _yAngle;
	}



	public void onDrawFrame(GL10 gl) {

		// define the color we want to be displayed as the "clipping wall"
		gl.glClearColor(0f, 0f, 0f, 1.0f);

		// reset the matrix - good to fix the rotation to a static angle
		gl.glLoadIdentity();

		// clear the color buffer to show the ClearColor we called above...
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// set rotation for the non-static triangle
		gl.glRotatef(_xAngle, 1f, 0f, 1f);
		gl.glRotatef(_yAngle, 0f, 1f, 0f);


		// gl.glColor4f(0.5f, 0f, 0f, 0.5f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices,
				GL10.GL_UNSIGNED_SHORT, _indexBuffer);
	}

	private void initTriangle() {
		float[] coords = {
	            -0.5f, -0.5f, 0.5f, // 0
	            0.5f, -0.5f, 0.5f, // 1
	            0f, -0.5f, -0.5f, // 2
	            0f, 0.5f, 0f, // 3
		};
		_nrOfVertices = coords.length;

		float[] colors = {
	            1f, 0f, 0f, 1f, // point 0 red
	            0f, 1f, 0f, 1f, // point 1 green
	            0f, 0f, 1f, 1f, // point 2 blue
	            1f, 1f, 1f, 1f, // point 3 white
		};
		
		short[] indices = new short[]{
	            0, 1, 3, // rwg
	            0, 2, 1, // rbg
	            0, 3, 2, // rbw
	            1, 2, 3, // bwg
		};
		
		// float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(coords.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		_vertexBuffer = vbb.asFloatBuffer();

		// short has 4 bytes
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		_indexBuffer = ibb.asShortBuffer();

		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		_colorBuffer = cbb.asFloatBuffer();

		_vertexBuffer.put(coords);
		_indexBuffer.put(indices);
		_colorBuffer.put(colors);

		_vertexBuffer.position(0);
		_indexBuffer.position(0);
		_colorBuffer.position(0);
	}

}