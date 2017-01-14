package com.example.happy.acmpdfconvertor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Happy on 10/31/2016.
 */

public class home extends AppCompatActivity {
    static Context cotext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Locale.getContextt(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_page_tollbar);
        setSupportActionBar(toolbar);

        ViewPager pager = (ViewPager) findViewById(R.id.home_page_view_pager_view);
        viewpagerAdapter adapter = new viewpagerAdapter(getSupportFragmentManager());
        adapter.add(new recent(), "Recent");
        adapter.add(new Locale(), "Local");
        adapter.add(new upload(), "Document Cloud");
        pager.setAdapter(adapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.home_page_tablayout);
        tablayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutwo,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_attachh:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Set your required file type
                //intent.setType("*/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "DEMO"),1);
                break;
            case R.id.menu_randomm:
                intent = new Intent(getApplicationContext(), help.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public class viewpagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        public viewpagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public void add(Fragment fragment, String string){
            fragmentList.add(fragment);
            stringList.add(string);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }
}
