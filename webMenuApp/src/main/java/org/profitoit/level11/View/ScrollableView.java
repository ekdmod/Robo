package org.webmenu.level11.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.webmenu.webmenuapp.R;

public class ScrollableView extends View {
	
	private RelativeLayout layout;
	
	@SuppressLint("NewApi")
	public ScrollableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		ImageView image = new ImageView(context);
		for (int i=0,m=0;i<100;i++,m++) {
			if (m == 10) {
				image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				image.setBaselineAlignBottom(true);
			}
			image.setBackgroundResource(R.drawable.aaa5);		
			layout.addView(image);
		}
	}



	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}

}
