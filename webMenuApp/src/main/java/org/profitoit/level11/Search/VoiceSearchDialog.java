package org.webmenu.level11.Search;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import org.webmenu.level11.Adapters.SearchMenuDropdownDialog;
import org.webmenu.webmenuapp.R;

import java.util.List;

/**
 * Created by EK on 15.1.8.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VoiceSearchDialog extends DialogFragment {

    public RecognizerIntent speechActivityIntent;

    private static final int SPEECH_REQUEST_CODE = 0;
    private static final int RESULT_OK = -1; //1234
    public EditText editText;
    public ExpandableListView dropDownMenuDialog;
    public Context context;

    public void VoiceSearchDialog() {
        context = getActivity();
    }

    public void VoiceSearchDialog(Context mContext) {
        context = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchDialog = inflater.inflate(R.layout.search_layout, null);
        editText = (EditText) searchDialog.findViewById(R.id.editTextSearch);
        // setContentView(R.layout.input_method_chooser);
        // imm.setInputMethod(getView().getWindowToken(),"fast_input_label");
        // showSoftKeyboard(getActivity().getCurrentFocus());
        // InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.setInputMethod(getActivity().getCurrentFocus().getWindowToken(),"search_keyboard");
        // showSoftKeyboard(getActivity().getCurrentFocus());
        //  showSoftKeyboard(editText);
//        ImageButton keyboardInput = (ImageButton) searchDialog.findViewById(R.id.keyboard);
//        ImageButton button_search_view = (ImageButton) searchDialog.findViewById(R.id.search_button);
//        button_search_view.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Dialog dialog_1 = new SearchResults(getActivity(), R.style.Dialog, editText.getText());
//                dialog_1.show();
//            }
//        });
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        String id = imm.getEnabledInputMethodList().get(0).getId();
//        Drawable background = imm.getEnabledInputMethodList().get(0).loadIcon(getActivity().getPackageManager());
//        keyboardInput.setImageDrawable(background);
//        keyboardInput.setOnClickListener(new View.OnClickListener() {
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            String id = imm.getEnabledInputMethodList().get(1).getId();
//
//            @Override
//            public void onClick(View view) {
//                showSoftKeyboard(view);
//            }
//        });
/*        if (SpeechRecognizer.isRecognitionAvailable(getActivity())) {
            ImageButton speechInput = (ImageButton) searchDialog.findViewById(R.id.speech);
            // speechInput.setVisibility(View.VISIBLE);
            int icon_size = (int) (getActivity().getResources().getDisplayMetrics().density * 48);
            // speechInput.setLayoutParams(new RelativeLayout.LayoutParams(icon_size, icon_size));s
            // Drawable background_2 = imm.getEnabledInputMethodList().get(1).loadIcon(context.getPackageManager());
            // speechInput.setImageDrawable(background_2);
            speechInput.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // hideSoftKeyboard(view);
                    // displaySpeechRecognizer();
                    // SearchResultViewGenerator searchResult = new SearchResultViewGenerator();
                    SearchResults searchResult = new SearchResults();
                    searchResult.search = editText.getText().toString();
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, searchResult).commit();
                    getDialog().hide();
                  //  InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                  //  imm.showInputMethodPicker();
                    // String id = imm.getEnabledInputMethodList().get(1).getId();
                }
            });
        }*/
        return searchDialog;
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

/*    private Intent createVoiceAppSearchIntent(Intent baseIntent,SearchableInfo searchable){
        ComponentName searchActivity=searchable.getSearchActivity();
        Intent queryIntent=new Intent(Intent.ACTION_SEARCH);
        queryIntent.setComponent(searchActivity);
        PendingIntent pending=PendingIntent.getActivity(getActivity(),0,queryIntent,PendingIntent.FLAG_ONE_SHOT);
        Bundle queryExtras=new Bundle();
        Intent voiceIntent=new Intent(baseIntent);
        String languageModel=RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;
        String prompt=null;
        String language=null;
        int maxResults=1;
        Resources resources=getResources();
        if (searchable.getVoiceLanguageModeId() != 0) {
            languageModel=resources.getString(searchable.getVoiceLanguageModeId());
        }
        if (searchable.getVoicePromptTextId() != 0) {
            prompt=resources.getString(searchable.getVoicePromptTextId());
        }
        if (searchable.getVoiceLanguageId() != 0) {
            language=resources.getString(searchable.getVoiceLanguageId());
        }
        if (searchable.getVoiceMaxResults() != 0) {
            maxResults=searchable.getVoiceMaxResults();
        }
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,languageModel);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,prompt);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,language);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,maxResults);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,searchActivity == null ? null : searchActivity.flattenToShortString());
        voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT,pending);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT_BUNDLE,queryExtras);
        return voiceIntent;
    }*/

    private void displaySpeechRecognizerFromIntent() {
        Intent resolutionIntent = new Intent();
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            editText.append(" " + results.toString());
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void hideSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
        }
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void switchInputMethod(View view) {

    }

    public void voiceToText(View view) {
        hideSoftKeyboard(view);
        displaySpeechRecognizer();
    }

    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
