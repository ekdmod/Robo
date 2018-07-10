package org.webmenu.level11.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.webmenu.webmenuapp.R;

/**
 * Created by EK on 14.12.3.
 */
public class View extends android.view.View {

    Camera imageCameraView;
    Matrix imageMatrixView;
    TextView titleName;
    TextSwitcher textSwitch;
    private ShapeDrawable mDrawable;
    private Context context;
    Rect textRect;
    Path textPath;
    RectF startRect;
    RectF middleRect;
    RectF endRect;
    Rect roundUpRect;
    Rect canvasSizeRect;
    RectF alphaRectF;
    Paint imageViewPaint;
    Paint backgroundPaint;
    Paint titlePaint;
    Paint textViewPaint;
    float maxTextWidth;
    float[] maxTextWidths;

    public View(Context context) {
        super(context);
        imageCameraView = new Camera();
        imageMatrixView = new Matrix();
        textRect = new Rect();
        backgroundPaint = new Paint();
        backgroundPaint.setStrokeWidth(25);
        backgroundPaint.setColor(getResources().getColor(R.color.transparent_blue));
        imageViewPaint = new Paint();
        titlePaint = new Paint();
        titlePaint.setColor(getResources().getColor(R.color.text_color_style));
        titlePaint.setTextSize(98);
        textViewPaint = new Paint();
        textViewPaint.setTextSize(98);
        maxTextWidths = new float[200];
        textViewPaint.setColor(getResources().getColor(R.color.abc_primary_text_disable_only_material_dark));
        int x = 10;
        int y = 10;
        int width = 300;
        int height = 50;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.setBounds(x, y, x + width, y + height);
        textPath = new Path();
        titleName = new TextView(context);
       // textSwitch = new TextSwitcher(context);
       // textSwitch.setCurrentText(Html.fromHtml(String.format(getResources().getString(R.string.menu_1), "bbb", 100)));
        canvasSizeRect = new Rect();
        roundUpRect = new Rect();
        startRect = new RectF();
        middleRect = new RectF();
        endRect = new RectF();
        alphaRectF = new RectF();
        context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String gText = "About software";
        int measuredWidth = MeasureSpec.getSize(canvas.getClipBounds().width());
        float[] values = new float[9];
        imageMatrixView.getValues(values);
        float sc = values[Matrix.MSCALE_X];
        // mDrawable.draw(canvas);
        int measuredHeight = MeasureSpec.getSize(canvas.getHeight());
        float scaleX = ((float) getWidth()) / canvas.getWidth();
        float scaleY = ((float) getHeight()) / canvas.getHeight();
        // Log.d("DEBUG", "Scale:" + scaleX);
        // scaleX = canvas.getWidth() / 100;
        //	Paint paint = new Paint();
        // Bitmap bm = BitmapFactory.decodeResource(getResources(), mThumbIds.getResourceId(position, -1));
        // imageBm.setImageBitmap(bm);
        //setImageMatrix(imageMatrixView);
        canvas.getClipBounds(canvasSizeRect);
        int height = canvasSizeRect.height() / 2;
        startRect.set(canvasSizeRect.left + 45, canvasSizeRect.top + 45, canvasSizeRect.right - 45, height - 45);
        // endRect.set(canvasSizeRect.left + 55, canvasSizeRect.top + height / 2.8f, canvasSizeRect.right - 55, canvasSizeRect.bottom - 55);
        middleRect.set(canvasSizeRect.left + 50, canvasSizeRect.top + 50, canvasSizeRect.right - 50, canvasSizeRect.bottom - 50);
         //bold.setStyle(Paint.Style.FILL);
        //bold.setStrokeCap(Paint.Cap.BUTT);
        // bold.setAntiAlias(true);
		titlePaint.getTextBounds(gText, 0, gText.length(), textRect);
		//imageViewPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
		int textAlignX = (canvas.getWidth() - textRect.width())/2;
        int textAlignY = canvasSizeRect.top + textRect.height() + 120;
       // canvas.drawRect(startRect ,bold);
       // canvas.drawRect(endRect, bold);
        canvas.drawRoundRect(middleRect, 50, 80, backgroundPaint);
      //  canvas.clipRect(middleRect)
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setColor(getResources().getColor(R.color.darkgreen));
        canvas.drawRoundRect(middleRect, 50, 80, backgroundPaint);
        //canvas.clipRect(endRect);
       // Bitmap menu = BitmapFactory.decodeResource(getResources(),R.drawable.front_menu_shape_view);
       // canvas.drawBitmap(menu, 50, 50, imageViewPaint);
       // canvas.drawText("My software", textAlignX, textAlignY, imageViewPaint);
        canvas.drawText("About software",textAlignX, textAlignY, titlePaint);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.menu_1), "", -1);
        CharSequence styledText = Html.fromHtml(text);
       // textViewPaint.getTextBounds(text, 0, text.length(), textRect);
       // textViewPaint.getTextWidths(styledText, 0, styledText.length(), maxTextWidths);
       // textViewPaint.breakText(styledText, 0, styledText.length(), true, textRect.width(), maxTextWidths);
        textViewPaint.getTextPath(text, 0, text.length(), canvasSizeRect.left + 100, textAlignY + textRect.height() + 50, textPath);
        // textPath.computeBounds(middleRect, true);
        // textViewPaint.setTextAlign(Paint.Align.CENTER);
       // textPath.addRect(middleRect, Path.Direction.CW);
      // canvas.drawText(styledText, 0, styledText.length(), canvasSizeRect.left + 80, textAlignY + textRect.height() + 50, textViewPaint);
      //  canvas.drawTextOnPath(text, textPath, canvasSizeRect.right - 80, 100,  textViewPaint);
        // imageMatrixView.mapRect(matrixRectangle);
        //	imageCameraView.rotateY(30);
        // imageCameraView.getMatrix(imageMatrixView);
        if (scaleX < scaleY) {
           // imageMatrixView.preScale(scaleX, scaleX);
        } else {
           // imageMatrixView.preScale(scaleY, scaleY);
        }
        //canvas.translate(scaleX * 100, scaleY * 100);
       // textSwitch.draw(canvas);
       // titleName.getBackground().draw(canvas);
        canvas.concat(imageMatrixView);
    }

    /**
     * The {@link android.widget.ViewSwitcher.ViewFactory} used to create {@link android.widget.TextView}s that the
     * {@link android.widget.TextSwitcher} will switch between.
     */
    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {

            // Create a new TextView
            View t = new View(context);
            //t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            //t.setTextAppearance(context, android.R.style.TextAppearance_Large);
            return t;
        }
    };

}
