package org.webmenu.level11.View;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageViewExample extends ImageView implements View.OnTouchListener {
	
	Matrix imageViewMatrix;
	Rect textRect;
	RectF rotateRect;
	Paint imageViewPaint;
	Camera imageCameraView;
	
	public ImageViewExample(Context context, AttributeSet attrs) {
		super(context, attrs);		
		imageViewMatrix = new Matrix();
		rotateRect = new RectF();
		textRect = new Rect();
		imageViewPaint = new Paint();
		imageCameraView = new Camera();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		String gText = "My text 1";
		int measuredWidth = MeasureSpec.getSize(canvas.getClipBounds().width());
		float[] values = new float[9];
		imageViewMatrix.getValues(values);
        float sc = values[Matrix.MSCALE_X];
        int measuredHeight = MeasureSpec.getSize(canvas.getHeight());
        float scaleX = ((float) getWidth()) / canvas.getWidth();
        float scaleY = ((float) getHeight()) / canvas.getHeight();
        // Log.d("DEBUG", "Scale:" + scaleX);
		// scaleX = canvas.getWidth() / 100;
	//	Paint paint = new Paint();
		// Bitmap bm = BitmapFactory.decodeResource(getResources(), mThumbIds.getResourceId(position, -1));
		// imageBm.setImageBitmap(bm);
		//setImageMatrix(imageMatrixView);
/*		imageViewPaint.setColor(Color.WHITE); 
		imageViewPaint.setTextSize(16);
		imageViewPaint.getTextBounds(gText, 0, gText.length(), textRect);
		imageViewPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
		int textAlignX = (canvas.getWidth() - textRect.width())/2;
		int textAlignY = (canvas.getHeight() + textRect.height())/2;
		canvas.drawText("My Text", textAlignX, textAlignY, imageViewPaint);*/
		// imageMatrixView.mapRect(matrixRectangle);
	//	imageCameraView.rotateY(30);
		imageCameraView.getMatrix(imageViewMatrix);
		if (scaleX < scaleY) {
			imageViewMatrix.preScale(scaleX, scaleX);
		} else {
			imageViewMatrix.preScale(scaleY, scaleY);
		}
		//canvas.translate(scaleX * 100, scaleY * 100);
		canvas.concat(imageViewMatrix);
		super.onDraw(canvas);
	}

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return true;
    }

}
