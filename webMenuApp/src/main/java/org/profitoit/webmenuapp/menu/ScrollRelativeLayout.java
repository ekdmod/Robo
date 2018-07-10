package org.webmenu.webmenuapp.menu;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.webmenu.webmenuapp.R;

public class ScrollRelativeLayout extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_scrollable_group_view);
        // Get the parent view
        View parentView = findViewById(R.id.parent_view);
        
        parentView.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                // The bounds for the delegate view (an ImageButton
                // in this example)
                Rect delegateArea = new Rect();
                final View menu_2 = getLayoutInflater().inflate(R.layout.frame_image_relative, null);
                ImageView background = (ImageView) menu_2.findViewById(R.id.image_menu);
                background.setBackgroundResource(R.drawable.start_0);
                background.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
                background.setEnabled(true);
                background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
     
                // The hit rectangle for the ImageButton
                background.getHitRect(delegateArea);
            
                // Extend the touch area of the ImageButton beyond its bounds
                // on the right and bottom.
                delegateArea.right += 100;
                delegateArea.bottom += 100;
            
                // Instantiate a TouchDelegate.
                // "delegateArea" is the bounds in local coordinates of 
                // the containing view to be mapped to the delegate view.
                // "myButton" is the child view that should receive motion
                // events.
                TouchDelegate touchDelegate = new TouchDelegate(delegateArea, 
                		background);
     
                // Sets the TouchDelegate on the parent view, such that touches 
                // within the touch delegate bounds are routed to the child.
                if (View.class.isInstance(background.getParent())) {
                    ((View) background.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }
    
    

}
