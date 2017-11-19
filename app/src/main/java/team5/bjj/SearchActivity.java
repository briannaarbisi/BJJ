package team5.bjj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.res.ColorStateList;
import android.graphics.Color;

public class SearchActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_custom_position:

                    //Intent intent = new Intent(SearchActivity.this,SearchActivity.class);
                    //startActivity(new Intent(SearchActivity.this, SearchActivity.class));
                    return true;
                case R.id.navigation_custom_move:


                    return true;

                case R.id.navigation_settings_search:
                    return true;

                case R.id.navigation_home:

                    onBackPressed();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // remove the icon
        }

        BottomNavigationView TopNavigation = (BottomNavigationView) findViewById(R.id.settings_home);
        changeMenuItemCheckedStateColor(TopNavigation, "#999999", "#999999");
        TopNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView BottomNavigation = (BottomNavigationView) findViewById(R.id.custom_position_custom_move);
        changeMenuItemCheckedStateColor(BottomNavigation, "#999999", "#999999");
        BottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String[] args = {"Default Offensive", "Default Defensive", "My First Offensive"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                R.id.textView,
                args);

        ListView list = (ListView) findViewById(R.id.position_move_list);
        list.setAdapter(adapter);
    }

    private void changeMenuItemCheckedStateColor(BottomNavigationView bottomNavigationView, String checkedColorHex, String uncheckedColorHex) {
        int checkedColor = Color.parseColor(checkedColorHex);
        int uncheckedColor = Color.parseColor(uncheckedColorHex);

        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] {android.R.attr.state_checked}, // checked

        };

        int[] colors = new int[] {
                uncheckedColor,
                checkedColor
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        bottomNavigationView.setItemTextColor(colorStateList);
        bottomNavigationView.setItemIconTintList(colorStateList);

    }
}