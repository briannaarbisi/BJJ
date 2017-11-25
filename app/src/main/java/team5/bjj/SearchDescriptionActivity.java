package team5.bjj;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchDescriptionActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

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

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_description);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // remove the icon
        }

//        BottomNavigationView TopNavigation = (BottomNavigationView) findViewById(R.id.settings_home);
//        changeMenuItemCheckedStateColor(TopNavigation, "#999999", "#999999");
//        TopNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle b = getIntent().getExtras();
        int value = -1;
        if(b == null) {
            value= b.getInt("key");
        }

        String[] descriptions = new String[] {"                                                     " +
                "ABCDEFGHIJKLM1ABCDEFGHIJKLM12ABCDEFGHIJKLM123ABCDEFGHIJKLM1234ABCDEFGHIJKLM12345" +
                "                                                                                   " +
                "                                                                           " +
                "             The rest won't appear, but this will."};

        final List<String> descriptionsList = new ArrayList<String>();
        Collections.addAll(descriptionsList, descriptions);

        final TextView helloTextView = (TextView) findViewById(R.id.text_view_id);
        helloTextView.setText(descriptionsList.get(0));

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
