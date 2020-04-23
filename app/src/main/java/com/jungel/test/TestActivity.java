package com.jungel.test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class TestActivity extends AppCompatActivity {

    private static final String[] titles = {
            "币币", "合约", "法币", "杠杆"
    };

    private ViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    private AppBarLayout mAppBarLayout;
    private LinearLayout mLayoutTab;
    private View mLayoutSearchBack;
    private LinearLayout mLayoutSearch;
    private View mViewLine;
    private EditText mEditSearch;
    private int mCurrentIndex;
    private View mViewSpace;
    private LinearLayout mLayoutIndicator;
    private View mViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mViewPager = findViewById(R.id.viewPager);
        TestFragmentAdapter adapter = new TestFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);

        mMagicIndicator = findViewById(R.id.magicIndicator);
        initMagicIndicator();

        mAppBarLayout = findViewById(R.id.appbar);
        mLayoutTab = findViewById(R.id.layoutTab);
        mLayoutSearchBack = findViewById(R.id.layoutSearchBack);
        mLayoutSearch = findViewById(R.id.layoutSearch);
        mViewLine = findViewById(R.id.viewLine);
        mEditSearch = findViewById(R.id.editSearch);
        mViewSpace = findViewById(R.id.viewSpace);
        mLayoutIndicator = findViewById(R.id.layoutIndicator);
        mViewBack = findViewById(R.id.viewBack);

        changeBackground(0);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int tabHeight = mLayoutTab.getMeasuredHeight()
                        - mLayoutSearchBack.getMeasuredHeight();
                if (Math.abs(verticalOffset) >= tabHeight) {
                    int alpha = (mLayoutTab.getMeasuredHeight() + verticalOffset) * 255
                            / mLayoutSearchBack.getMeasuredHeight();
                    mLayoutSearch.getBackground().setAlpha(alpha);
                    if (alpha == 0) {
                        mViewLine.setVisibility(View.GONE);
                        mEditSearch.setHintTextColor(Color.parseColor("#72FFFFFF"));
                        mEditSearch.setTextColor(Color.WHITE);
                    } else {
                        mViewLine.setVisibility(View.VISIBLE);
                        mEditSearch.setHintTextColor(Color.parseColor("#80909A"));
                        mEditSearch.setTextColor(Color.parseColor("#1E2631"));
                    }
                } else {
                    mLayoutSearch.getBackground().setAlpha(255);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                changeBackground(position, positionOffsetPixels);
            }

            public void onPageSelected(int position) {
                changeBackground(position);
            }

            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    mCurrentIndex = mViewPager.getCurrentItem();
                }
            }
        });
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(titles[index]);
                clipPagerTitleView.setTextColor(Color.parseColor("#B6BFC6"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }


    private void changeBackground(int nextIndex) {
        final int nextColor = getBackgroundColor(nextIndex);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(
                Color.blue(getBackgroundColor(mViewPager.getCurrentItem())),
                Color.blue(nextColor)
        );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer blue = (Integer) animation.getAnimatedValue();
                setBackgroundColor(Color.rgb(Color.red(nextColor), Color.green(nextColor),
                        blue));
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    private void changeBackground(int nextIndex, int offset) {
        int nextColor;
        if (mCurrentIndex == nextIndex) {
            nextColor = getBackgroundColor(nextIndex + 1);
        } else {
            nextColor = getBackgroundColor(nextIndex);
        }
        int starRed = Color.red(getBackgroundColor(mCurrentIndex));
        int endRed = Color.red(nextColor);
        int starGreen = Color.green(getBackgroundColor(mCurrentIndex));
        int endGreen = Color.green(nextColor);
        int starBlue = Color.blue(getBackgroundColor(mCurrentIndex));
        int endBlue = Color.blue(nextColor);
        int red, green, blue;
        if (offset == 0) {
            return;
        } else {
            int screenWidth = UIUtil.getScreenWidth(this);
            if (mCurrentIndex == nextIndex) {
                //左滑，增大
                red = starRed + (endRed - starRed) * offset
                        / screenWidth;
                green = starGreen + (endGreen - starGreen) * offset
                        / screenWidth;
                blue = starBlue + (endBlue - starBlue) * offset
                        / screenWidth;
            } else {
                //右滑，减小
                red = starRed + (endRed - starRed)
                        * (screenWidth - offset)
                        / screenWidth;
                green = starGreen + (endGreen - starGreen)
                        * (screenWidth - offset)
                        / screenWidth;
                blue = starBlue + (endBlue - starBlue)
                        * (screenWidth - offset)
                        / screenWidth;
            }
        }
        setBackgroundColor(Color.rgb(red, green, blue));
    }


    private void setBackgroundColor(int color) {
        mViewSpace.setBackgroundColor(color);
        mLayoutIndicator.setBackgroundColor(color);
        mLayoutTab.setBackgroundColor(color);
        mViewBack.setBackgroundColor(color);
        mLayoutSearchBack.setBackgroundColor(color);
    }

    private int getBackgroundColor(int index) {
        switch (index) {
            case 0:
                return Color.parseColor("#1783d4");
            case 1:
                return Color.parseColor("#35375d");
            case 2:
                return Color.parseColor("#0f386d");
            case 3:
                return Color.parseColor("#3d475d");
        }
        return Color.parseColor("#1783d4");
    }
}
