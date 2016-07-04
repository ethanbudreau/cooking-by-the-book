package com.ethan.cookingbythebook;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class CreateRecipeActivity extends AppCompatActivity {


    //Declaring All The Variables Needed

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        /*
        Assigning view variables to thier respective view in xml
        by findViewByID method
         */

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);

        /*
        Creating Adapter and setting that adapter to the viewPager
        setSupportActionBar method takes the toolbar and sets it as
        the default action bar thus making the toolbar work like a normal
        action bar.
         */
        viewPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        /*
        TabLayout.newTab() method creates a tab view, Now a Tab view is not the view
        which is below the tabs, its the tab itself.
         */

        final TabLayout.Tab recipe = tabLayout.newTab();
        final TabLayout.Tab ingredients = tabLayout.newTab();
        final TabLayout.Tab steps = tabLayout.newTab();

        /*
        Setting Title text for our tabs respectively
         */

        recipe.setText("Recipe");
        ingredients.setText("Ingredients");
        steps.setText("Steps");

        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(recipe, 0);
        tabLayout.addTab(ingredients, 1);
        tabLayout.addTab(steps, 2);

        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

/*
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));
*/

        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.
         */

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


      /**
         * The number of pages (wizard steps) to show in this demo.
         */
        private static final int NUM_PAGES = 3;

        /**
         * The pager widget, which handles animation and allows swiping horizontally to access previous
         * and next wizard steps.
         */
        private ViewPager mPager;

        /**
         * The pager adapter, which provides the pages to the view pager widget.
         */
        private PagerAdapter mPagerAdapter;

    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_recipe);

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
        }

        @Override
        public void onBackPressed() {
            if (mPager.getCurrentItem() == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button. This calls finish() on this activity and pops the back stack.
                super.onBackPressed();
            } else {
                // Otherwise, select the previous step.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        }
*/
        /**
         * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
         * sequence.
         */
        private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
            public ScreenSlidePagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {

                String fragmentName=null;

                if (position==0) {
                    fragmentName="addRecipeInfo";
                }
                if (position==1){
                    fragmentName="addIngredients";
                }
                if (position==2){
                    fragmentName="addSteps";
                }

                return new ScreenSlidePageFragment(fragmentName);
            }

            @Override
            public int getCount() {
                return NUM_PAGES;
            }
        }



        public class ScreenSlidePageFragment extends Fragment {

            private String fragmentType;

            public ScreenSlidePageFragment(String fragmentName){
                fragmentType = fragmentName;
            }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            ViewGroup rootView=null;

            if(fragmentType=="addRecipeInfo"){
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.content_add_recipe_info, container, false);
            }

            if(fragmentType=="addIngredients") {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.content_add_ingredients, container, false);
            }

            if(fragmentType=="addSteps") {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.content_add_steps, container, false);
            }

            return rootView;
        }
    }
}

