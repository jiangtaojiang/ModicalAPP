package com.ModicalDroid.modicalapp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.ModicalDroid.modicalpp.R;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener
{
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		setOverflowShowingAlways();
		getActionBar().setDisplayShowHomeEnabled(false);
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		initDatas();

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initDatas()
	{

        mTabs.add(new ConnectFragment());
        mTabs.add(new VisualizationFragment());
        mTabs.add(new ActivityRecognitionFragment());
        mTabs.add(new NotificationsFragment());
        mTabs.add(new GuidelinesFragment());
        mTabs.add(new RemoteStorageFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initTabIndicator()
	{
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);
        ChangeColorIconWithTextView five = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_five);
        ChangeColorIconWithTextView six = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_six);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
        mTabIndicator.add(four);
        mTabIndicator.add(five);
        mTabIndicator.add(six);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);


        one.setIconAlpha(1.0f);
	}

	@Override
	public void onPageSelected(int arg0)
	{
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		// Log.e("TAG", "position = " + position + " , positionOffset = "
		// + positionOffset);

		if (positionOffset > 0)
		{
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	@Override
    public void onClick(View v) {

        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_indicator_one:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_three:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_four:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
            case R.id.id_indicator_five:
                mTabIndicator.get(4).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(4, false);
                break;
            case R.id.id_indicator_six:
                mTabIndicator.get(5).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(5, false);
                break;
        }

    }

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicator.size(); i++)
		{
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e)
				{
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways()
	{
		try
		{
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
