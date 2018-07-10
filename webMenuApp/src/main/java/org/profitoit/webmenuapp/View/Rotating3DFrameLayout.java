package org.webmenu.webmenuapp.View;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class Rotating3DFrameLayout extends FrameLayout {

	public Rotating3DFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Canvas canvas) {
		Matrix canvasMat = new Matrix();
		Camera cam = new Camera();
		cam.rotateY(35);
		cam.getMatrix(canvasMat);
		canvasMat.preTranslate(0.0f, 10f);
		canvas.setMatrix(canvasMat);	
		super.draw(canvas);
	}
	
	
	
}
