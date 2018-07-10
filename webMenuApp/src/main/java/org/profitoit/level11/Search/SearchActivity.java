package org.webmenu.level11.Search;

import android.annotation.TargetApi;
import android.app.Activity;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.voiceime.VoiceRecognitionTrigger;

import org.webmenu.webmenuapp.R;

import java.util.Iterator;
import java.util.List;

/**
 * Created by EK on 15.2.1.
 */
public class SearchActivity extends Activity {

    private InputMethodManager inputMethodManager;
    public SearchResults searchResView;
    private EditText searchTextView;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDefaultKeyboard();
        setContentView(R.layout.search_layout);
        String locale = getResources().getConfiguration().locale.getDisplayName();
        inputMethodManager = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        container = (FrameLayout) findViewById(R.id.container);
        searchTextView = (EditText) findViewById(R.id.editText);
        searchTextView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    v.setVisibility(View.INVISIBLE);
                    v.setInputType(InputType.TYPE_NULL);
                    searchResView = new SearchResults();
                    searchResView.search = v.getText().toString();
                    container.setVisibility(View.VISIBLE);
                    inputMethodManager.toggleSoftInput(0,0);
                    getFragmentManager().beginTransaction().replace(R.id.container, searchResView).commit();
                    handled = true;
                }
                return handled;
            }
        });
        searchTextView.setOnClickListener( new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                // changeInputMethod(view, 1);
                inputMethodManager.showInputMethodPicker();
            }
        });
        searchTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                //  changeInputMethod(view, 1);
                   // showSoftKeyboard(view);
                } else {
                    hideSoftKeyboard(view);
                }
            }
        });
    }

    public void hideSoftKeyboard(View view) {
       if (view.requestFocus())
           inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
    }

    public void showSoftKeyboard(View view) {
       if (view.requestFocus())
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void changeInputMethod(View view, int inputMethodId) {
            Log.d("Changing input: ","" + inputMethodId);
            Iterator<InputMethodInfo> iterator = inputMethodManager.getInputMethodList().iterator();
            while(iterator.hasNext()){
                InputMethodInfo methodInfo = iterator.next();
              //  if (methodInfo.getId().equals("search_keyboard")) {
                    Log.d("Keyboard type :", methodInfo.getServiceName());
              //  }
            }
            switch (inputMethodId) {
                case 0:
                    inputMethodManager.setInputMethod(view.getWindowToken(), "com.sec.android.inputmethod.SamsungKeypad");
                    break;
                case 1:
                    inputMethodManager.setInputMethod(view.getWindowToken(), "search_keyboard");
                    break;
                case 2:
                    inputMethodManager.setInputMethod(view.getWindowToken(), "other_keyboard_type");
                    break;
            }
    }

    private void showDefaultKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void hideDefaultKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
