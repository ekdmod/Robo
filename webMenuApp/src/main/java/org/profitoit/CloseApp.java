package org.webmenu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import org.webmenu.webmenuapp.R;


@SuppressLint("NewApi")
public class CloseApp extends DialogFragment {
	 
    LayoutInflater mLayoutInflater;
    ImageView mImgOriginal;
 
    String mId="";
    boolean code;
 
    public static CloseApp newInstance(int dialog_name) {
    	CloseApp exitDialog = new CloseApp();
    	Bundle args = new Bundle();
    	args.putInt("dialog_name",dialog_name);
    	args.putInt("style",3);
    	exitDialog.setArguments(args);
    	return exitDialog;
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mNum = getArguments().getInt("style");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }
 
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int name = getArguments().getInt("dialog_name");
    	
        /** Instantiating Builder object to create an alert dialog window */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Getting layout inflater */
        mLayoutInflater = getActivity().getLayoutInflater();
 
        /** Getting the screen view corresponding to the layout image_dialog_layout */
        View v = mLayoutInflater.inflate(R.layout.app_exit_dialog, null);
 
        /** Getting a reference to the image widget imgOriginal of the layoutfile image_dialog_layout.xml */
        mImgOriginal = (ImageView) v.findViewById(R.id.imgOriginal);
 
        mImgOriginal.setImageResource(R.drawable.about_3);
        
        /** Setting a view on the builder */
        builder.setView(v);
 
        /** Settng a title for the alert dialog window */
        builder.setTitle("Exit application dialog");
 
        builder.setPositiveButton(android.R.string.ok,
            new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		doPositiveClick();
            	}
        	}
        );
        
        builder.setNegativeButton(android.R.string.cancel,
            new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		doNegativeClick();
            		dialog.dismiss();
            	}
        	}
        );
        
        /** Creating a alert window */
        return builder.create();
        
    }
    
    public void doPositiveClick() {
        // Do stuff here.
         // Log.i("FragmentAlertDialog", "Positive click!");
    	System.exit(1);
    //	getActivity().moveTaskToBack(true);
    }

    public void doNegativeClick() {
        // Do stuff here.
    	code = true;
         // Log.i("FragmentAlertDialog", "Negative click!");
    }
}

