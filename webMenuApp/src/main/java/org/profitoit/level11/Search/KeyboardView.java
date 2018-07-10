package org.webmenu.level11.Search;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.webmenu.webmenuapp.R;

/**
 * Created by EK on 15.2.18.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class KeyboardView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View keyboardView = inflater.inflate(R.layout.keyboard, null);

        return keyboardView;
    }
}
