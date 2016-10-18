package com.example.mukesh.medisys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by sujeet on 18/10/16.
 */

public class Profile  extends AppCompatActivity {
    ScrollView mScrollView;
    ImageView mPhotoIV;
    FrameLayout mWrapperFL;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mPhotoIV = (ImageView) findViewById(R.id.profile);
        name=(TextView)findViewById(R.id.user_profile_name);

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ScrollPositionObserver());
    }


    private class ScrollPositionObserver implements ViewTreeObserver.OnScrollChangedListener {

        private int mImageViewHeight;

        public ScrollPositionObserver() {
            mImageViewHeight = getResources().getDimensionPixelSize(R.dimen.contact_photo_height);
        }

        @Override
        public void onScrollChanged() {
            int scrollY = Math.min(Math.max(mScrollView.getScrollY(), 0), mImageViewHeight);

            // changing position of ImageView
           if(scrollY>5)
               setTitle(name.getText());
            else
               setTitle("Profile");


        }
    }
}