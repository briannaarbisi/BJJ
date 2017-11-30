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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_custom_position:

                    Intent intent_new_position = new Intent(SearchActivity.this,AddCustomPositionActivity.class);
                    startActivityForResult(intent_new_position, 1);
                    return true;
                case R.id.navigation_custom_move:
                    Intent intent_new_move = new Intent(SearchActivity.this,AddCustomMoveActivity.class);
                    startActivityForResult(intent_new_move, 1);

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

    ListView list;
    ArrayAdapter<String> adapter;
    List<String> argsList;
    List<String> descriptionsList;

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

        String[] args = {" ", "Americana" ,
                "Arm Bar" ,
                "Arm Lock" ,
                "Baby Bolo" ,
                "Basic Judo Leg Sweep" ,
                "Block Control" ,
                "Bottom Guard" ,
                "Bottom Guard - Butterfly" ,
                "Bottom Guard - Open" ,
                "Bottom Guard - Spider" ,
                "Bottom Guard - X" ,
                "Butterfly Sweep" ,
                "Collar Choke" ,
                "DeLaRiva Knockdown" ,
                "Double Leg" ,
                "Double Under" ,
                "Ezekiel Choke" ,
                "Fireman's" ,
                "Full Mount" ,
                "Guillotine" ,
                "Head-and-Arm Choke " ,
                "High Crotch to Double Leg" ,
                "Kiss of the Dragon" ,
                "Knee Slicer" ,
                "Leg Drag" ,
                "North-South" ,
                "North-South Choke" ,
                "Rear Naked Choke" ,
                "Scissor" ,
                "Side Control" ,
                "Signle Under Knee Control" ,
                "Single Leg" ,
                "Single Under Knee Control with Back Step" ,
                "Sit-Up" ,
                "Snap-Down" ,
                "Standing" ,
                "Standing Guard Break" ,
                "Top Guard" ,
                "Triangle Choke" ,
                "ZZZZZZZZZZZZZ"};
        argsList = new ArrayList<String>();
        Collections.addAll(argsList, args);
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                R.id.textView,
                argsList);

        list = (ListView) findViewById(R.id.position_move_list);
        list.setAdapter(adapter);

        String[] descriptions = new String[] {"\n\nHidden Description","\n\nAmericana Description" ,
                "\n\nArm Bar Description" ,
                "\n\nArm Lock Description" ,
                "\n\nBaby Bolo Description" ,
                "\n\nBasic Judo Leg Sweep Description" ,
                "\n\nBlock Control Description" ,
                "\n\nBottom Guard Description" ,
                "\n\nBottom Guard - Butterfly Description" ,
                "\n\nBottom Guard - Open Description" ,
                "\n\nBottom Guard - Spider Description" ,
                "\n\nBottom Guard - X Description" ,
                "\n\nButterfly Sweep Description" ,
                "\n\nCollar Choke Description" ,
                "\n\nDeLaRiva Knockdown Description" ,
                "\n\nDouble Leg Description" ,
                "\n\nDouble Under Description" ,
                "\n\nEzekiel Choke Description" ,
                "\n\nFireman's Description" ,
                "\n\nFull Mount Description" ,
                "\n\nGuillotine Description" ,
                "\n\nHead-and-Arm Choke Description" ,
                "\n\nHigh Crotch to Double Leg Description" ,
                "\n\nKiss of the Dragon Description" ,
                "\n\nKnee Slicer Description" ,
                "\n\nLeg Drag Description" ,
                "\n\nNorth-South Description" ,
                "\n\nNorth-South Choke Description" ,
                "\n\nRear Naked Choke Description" ,
                "\n\nScissor Description" ,
                "\n\nSide Control Description" ,
                "\n\nSignle Under Knee Control Description" ,
                "\n\nSingle Leg Description" ,
                "\n\nSingle Under Knee Control with Back Step Description" ,
                "\n\nSit-Up Description" ,
                "\n\nSnap-Down Description" ,
                "\n\nStanding Description" ,
                "\n\nStanding Guard Break Description" ,
                "\n\nTop Guard Descripton" ,
                "\n\nTriangle Choke Description" ,
                "Hidden Description"};

        descriptionsList = new ArrayList<String>();
        Collections.addAll(descriptionsList, descriptions);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = list.getItemAtPosition(position);
                String valSelected = (String)listItem;
                Intent intent_search_description = new Intent(SearchActivity.this, SearchDescriptionActivity.class);
                int num;
                num = argsList.indexOf(valSelected);
                intent_search_description.putExtra("description",descriptionsList.get(num));
                startActivity(intent_search_description);
            }
        });
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String position = data.getStringExtra("positionName");
                String description = "\n\n"+ data.getStringExtra("description");
                argsList.remove(argsList.size() -1);
                descriptionsList.remove(descriptionsList.size() -1);
                argsList.add(position);
                argsList.add("ZZZZZZZZZZ");
                Collections.sort(argsList);
                int positionIndex = argsList.indexOf(position);
                descriptionsList.add(positionIndex,description);
                descriptionsList.add("Hidden");
                adapter.notifyDataSetChanged();

            }
        }
    }

}