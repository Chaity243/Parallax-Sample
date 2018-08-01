package com.chaitanya.www.parallaxsample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTestActivity extends Activity {
    private static final int MAX_PAGES = 10;
    private ImageView image_grey,image_orange;
    private int num_pages = 10;
    ValueAnimator animator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        image_orange =findViewById(R.id.image_orange);
        image_grey=findViewById(R.id.image_grey);
        final ViewPagerParallax pager = (ViewPagerParallax) findViewById(R.id.pager);
        pager.set_max_pages(MAX_PAGES);
        pager.setBackgroundAsset(R.raw.bubbles);
        pager.setAdapter(new my_adapter());
        final Button add_page_button = (Button) findViewById(R.id.add_page_button);

         animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                image_orange.setAlpha((Float) animation.getAnimatedValue());

            }
        });

        animator.setDuration(1500);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(0);
        add_page_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num_pages = Math.min(num_pages+1, MAX_PAGES);
                pager.getAdapter().notifyDataSetChanged();
            }
        });

        if (savedInstanceState!=null) {
            num_pages = savedInstanceState.getInt("num_pages");
            pager.setCurrentItem(savedInstanceState.getInt("current_page"), false);
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                animator.start();

            }

            @Override
            public void onPageSelected(int i) {
               animator.end();

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num_pages", num_pages);
        final ViewPagerParallax pager = (ViewPagerParallax) findViewById(R.id.pager);
        outState.putInt("current_page", pager.getCurrentItem());
    }

    private class my_adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return num_pages;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View new_view=null;

            LayoutInflater inflater = getLayoutInflater();
            new_view = inflater.inflate(R.layout.page, null);
            TextView num = (TextView) new_view.findViewById(R.id.page_number);
            num.setText(R.string.pager_text);

            container.addView(new_view);

            return new_view;
        }

    }



}

