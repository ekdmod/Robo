package org.webmenu.level11.Menu;

/*
 * Copyright (c) 2010, Sony Ericsson Mobile Communication AB. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 *    * Redistributions of source code must retain the above copyright notice, this 
 *      list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *    * Neither the name of the Sony Ericsson Mobile Communication AB nor the names
 *      of its contributors may be used to endorse or promote products derived from
 *      this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.webmenu.level11.Adapters.Rotating3DListMenuAdapter;
import org.webmenu.webmenuapp.Gesture.Rotating3DListMenuDynamics;
import org.webmenu.webmenuapp.R;

import java.util.ArrayList;

/**
 * Test activity to display the list view
 */
public class Rotating3DListMenuForNews extends Activity {

    /** Id for the toggle rotation menu item */
    private static final int TOGGLE_ROTATION_MENU_ITEM = 0;

    /** Id for the toggle lighting menu item */
    private static final int TOGGLE_LIGHTING_MENU_ITEM = 1;
    private String[] nHeader;
    private String[] nDesc;

    /** The list view */
    private Rotating3DListMenuAdapter mListView;

    /**
     * Small class that represents a contact
     */
    private static class News {

        /** Name of the contact */
        String nHeader;

        /** Phone number of the contact */
        String nDescription;

        /**
         * Constructor
         * 
         * @param name The name
         * @param number The number
         */
        public News(final String title, final String description) {
            nHeader = title;
            nDescription = description;
        }

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_3d_menu);
        
        nHeader = getResources().getStringArray(R.array.news_item_names);
        nDesc = getResources().getStringArray(R.array.news_item_desc);
        
        final ArrayList<News> news = createContactList(20);
        final MyAdapter adapter = new MyAdapter(this, news);

        mListView = (Rotating3DListMenuAdapter)findViewById(R.id.list_item);
        mListView.setAdapter(adapter);

        mListView.setDynamics(new SimpleDynamics(0.9f, 0.6f));

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                    final int position, final long id) {
                final String message = "OnClick: " + news.get(position).nHeader;
                Toast.makeText(Rotating3DListMenuForNews.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                    final int position, final long id) {
                final String message = "OnLongClick: " + news.get(position).nHeader;
                Toast.makeText(Rotating3DListMenuForNews.this, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add(Menu.NONE, TOGGLE_ROTATION_MENU_ITEM, 0, "Toggle Rotation");
        menu.add(Menu.NONE, TOGGLE_LIGHTING_MENU_ITEM, 1, "Toggle Lighting");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case TOGGLE_ROTATION_MENU_ITEM:
                mListView.enableRotation(!mListView.isRotationEnabled());
                return true;

            case TOGGLE_LIGHTING_MENU_ITEM:
                mListView.enableLight(!mListView.isLightEnabled());
                return true;

            default:
                return false;
        }
    }

    /**
     * Creates a list of fake contacts
     * 
     * @param size How many contacts to create
     * @return A list of fake contacts
     */
    private ArrayList<News> createContactList(final int size) {
        final ArrayList<News> news = new ArrayList<News>();
       for (int i = 0; i < nHeader.length; i++) {
/*            news.add(new News("Cult of Elon Musk? Billionaire's Image Shines With Steve Jobs' Aura","es been compared to Thomas Edison, to Henry Ford, to Steve Jobs even to Tony Stark, the high-flying industrialist in Marvel's \"Iron Man\" comic books and movies. But it's hard to imagine any of those characters saying with a straight face that they're planning to retire on Mars. "));
            news.add(new News("Google Search Gets Better Conversation Skills; OpenTable Integration","Google has released a new update for its Search app that improves conversational search, and can help users find restaurants and make reservations with just voice commands.The search giant has also integrated Search with OpenTable, a company that offers online real-time restaurant-reservation service. Google notes that restaurant reservations can be made through the Search app if the restaurant is on OpenTable."));
            news.add(new News("Google's Mobile Search App Will Now Remind You To Pay Your Bills","Google's search app has been helping us with a lot of things, and its latest feature is probably its most useful ever. The app can now keep track of when your bills are due, helping you pay them on time and keep your credit in check."));
            news.add(new News("Apple: The Brand That Has Stood the Test of Time ","Through years of struggle and euphoria alike, Apple's brand name has never faded - we look at the possible reasons why. "));
            news.add(new News("Alpha Software Reduces Mobile Application Costs","It's hard to build mobile computing applications in the enterprise because there are not only so many data sources to integrate, the application itself needs to be able to work offline to minimize telecommunications costs."));
            news.add(new News("Program notes from a software security veteran","I think I learned a lot more than they did. The developers and their project leads viewed security controls in the development process as obstacles to getting real work accomplished, and they had no budget for the hours of remediation work required when security vulnerabilities were discovered. My implementation approach was effective at giving me an education but not that effective at educating them."));
            news.add(new News("Salesforce.com Platform Helps Companies Build Mobile Apps","Salesforce.com introduces ExactTarget Journey Builder for Apps to support engaging, marketing-savvy mobile applications."));
            news.add(new News("Preventing VPN security risks for mobile employees","Expert Kevin Beaver offers VPN security best practices, including how to prevent risks and secure VPN access for mobile employees."));*/
    	   news.add(new News(nHeader[i], nDesc[i]));
        }
        return news;
    }

    /**
     * Adapter class to use for the list
     */
    private static class MyAdapter extends ArrayAdapter<News> {

        /** Re-usable contact image drawable */
        private final Drawable newsIcon;

        /**
         * Constructor
         * 
         * @param context The context
         * @param contacts The list of contacts
         */
        public MyAdapter(final Context context, final ArrayList<News> contacts) {
            super(context, 0, contacts);
            newsIcon = context.getResources().getDrawable(R.drawable.news);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.news_3d_list_menu, null);
            }

            final TextView name = (TextView)view.findViewById(R.id.newsHdr);
            
            name.setText(getItem(position).nHeader);

            final TextView number = (TextView)view.findViewById(R.id.newsDesc);
            number.setText(getItem(position).nDescription);

            final ImageView photo = (ImageView)view.findViewById(R.id.newsIcn);
            photo.setImageDrawable(newsIcon);

            return view;
        }
    }

    /**
     * A very simple dynamics implementation with spring-like behavior
     */
    class SimpleDynamics extends Rotating3DListMenuDynamics {

        /** The friction factor */
        private float mFrictionFactor;

        /** The snap to factor */
        private float mSnapToFactor;

        /**
         * Creates a SimpleDynamics object
         * 
         * @param frictionFactor The friction factor. Should be between 0 and 1.
         *            A higher number means a slower dissipating speed.
         * @param snapToFactor The snap to factor. Should be between 0 and 1. A
         *            higher number means a stronger snap.
         */
        public SimpleDynamics(final float frictionFactor, final float snapToFactor) {
            mFrictionFactor = frictionFactor;
            mSnapToFactor = snapToFactor;
        }

        @Override
        protected void onUpdate(final int dt) {
            // update the velocity based on how far we are from the snap point
            mVelocity += getDistanceToLimit() * mSnapToFactor;

            // then update the position based on the current velocity
            mPosition += mVelocity * dt / 1500;

            // and finally, apply some friction to slow it down
            mVelocity *= mFrictionFactor;
        }
    }
}

