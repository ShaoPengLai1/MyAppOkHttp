package com.example.recyclestaggered;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.recyclestaggered.fragment.FlowFragment;
import com.example.recyclestaggered.fragment.GriderFragment;
import com.example.recyclestaggered.fragment.LinearFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioButton linear,grider,flow;
    private ViewPager viewPager;
    private List<Fragment> list;
    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        linear=findViewById(R.id.linear);
        grider=findViewById(R.id.grider);
        flow=findViewById(R.id.flow);
        viewPager=findViewById(R.id.contents);
        group=findViewById(R.id.radio);
        list=new ArrayList<>();
        list.add(new LinearFragment());
        list.add(new GriderFragment());
        list.add(new FlowFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.linear:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.grider:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.flow:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group.check(R.id.linear);
                        break;
                    case 1:
                        group.check(R.id.grider);
                        break;
                    case 2:
                        group.check(R.id.flow);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}
