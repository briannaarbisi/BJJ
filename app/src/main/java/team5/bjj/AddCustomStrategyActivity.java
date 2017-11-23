package team5.bjj;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class AddCustomStrategyActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //case R.id.navigation_home:
                 //   mTextMessage.setText(R.string.title_home);
                 //   return true;
                case R.id.navigation_cancel:
                    //mTextMessage.setText(R.string.title_dashboard);
                    onBackPressed();
                    return true;
                case R.id.navigation_create:
                    //mTextMessage.setText(R.string.title_notifications);
                    Intent intent = new Intent();

                    mTextMessage = (TextView) findViewById(R.id.name);
                    String name = mTextMessage.getText().toString();
                    intent.putExtra("strategyName", name);
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_strategy);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // remove the icon
        }

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.cancel_create);
        changeMenuItemCheckedStateColor(navigation, "#999999", "#999999");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
