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

        String[] args = {"Hidden", "Single Leg Takedown", "Double Leg Takedown", "Top Guard"};
        argsList = new ArrayList<String>();
        Collections.addAll(argsList, args);
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                R.id.textView,
                argsList);

        list = (ListView) findViewById(R.id.position_move_list);
        list.setAdapter(adapter);

        String[] descriptions = new String[] {"\n\nHidden Description",
                "\n\nThe single leg takedown is a wrestling fundamentals move that is used to take an opponent from the standing position ideally to a bottom position which you have dominance over, most commonly side control. The footwork for the move follows the 'Giant Steps' drill where a lunging motion is performed and the back leg is dragged with a step forward, effectively switching the side of the lunge. This movement is intended to press you forward into the opponents hip while lifting one of his legs off of the mat. The head position is on the inner thigh of the leg you are attacking, with your body pressed tightly against the leg, and the top of the head pressing directly toward the opponent's abdomen as you rise up. This is done to prevent attacks such as the guillotine. Common hand positions are one hand on the back of the knee and one on the calf, or both hands on the calf. The movement is meant to travel forward and cause the opponent to backstep, once this occurs, most commonly a 'bow down' where you perform a back step, turning 90 degrees and dropping your stance level to bring the opponent to the ground." ,
                "\n\nThe double leg takedown is a wrestling fundamentals move that is used to take an opponent from the standing position to a bottom position, ideally with dominance but can often result in being caught the opponent's guard. The footwork starts with a penetration step where one foot steps past the middle of the opponents stance between their legs, your level is dropped, and this same leg does a 'Giant Step'pressing the knee into the ground and lunging the back leg to the forward position. Simultaneously, your head is pressed into their abdomen, and your hands either push their hands behind their back or clasp the opponents calves. The giant step is pressed forward until the opponent is forced to the ground. If the head is removed from the abdomen and brought to the outside, it must be done only for a short instant, as a guillotine counterattack is almost inevitable.",
                "\n\nHolding the top position in guard is often referred to as being caught in your opponent's guard. This is considered a neutral position from which you must actively work to gain posture, break the opponent's guard to open and collapse the leg/knee defenses to pass into a more dominant position. Some things to keep in mind are to keep either both hands in the guard or both hands outside, not attempt any submissions, and consider rising to the standing position. Some common moves from top guard are the classical kneeling passes, as well as Standing Guard Break, Sitting Thigh Guard Break, and leg drag passes."};

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
                argsList.add(position);
                descriptionsList.add(description);
                adapter.notifyDataSetChanged();

            }
        }
    }

}