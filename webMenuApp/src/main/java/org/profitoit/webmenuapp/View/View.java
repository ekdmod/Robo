package org.webmenu.webmenuapp.View;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

/**
 * Created by EK on 14.12.3.
 */
public class View extends android.view.View {

    Camera imageCameraView;
    Matrix imageMatrixView;

    public View(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String gText = "My text 1";
        int measuredWidth = MeasureSpec.getSize(canvas.getClipBounds().width());
        float[] values = new float[9];
        imageMatrixView.getValues(values);
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
        imageCameraView.getMatrix(imageMatrixView);
        if (scaleX < scaleY) {
            imageMatrixView.preScale(scaleX, scaleX);
        } else {
            imageMatrixView.preScale(scaleY, scaleY);
        }
        //canvas.translate(scaleX * 100, scaleY * 100);
        canvas.concat(imageMatrixView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
    }
}
