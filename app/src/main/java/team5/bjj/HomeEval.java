package team5.bjj;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by b on 11/18/17.
 */

public class HomeEval extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    return true;

                case R.id.navigation_new_strategy:
                    return true;

                case R.id.navigation_cancel:
                    onBackPressed();
                    return true;

                case R.id.navigation_settings:
                    return true;

                case R.id.navigation_edit:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView TopNavigation = (BottomNavigationView) findViewById(R.id.settings_edit);
        changeMenuItemCheckedStateColor(TopNavigation, "#999999", "#999999");
        TopNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView BottomNavigation = (BottomNavigationView) findViewById(R.id.search_new_strategy_eval);
        changeMenuItemCheckedStateColor(BottomNavigation, "#999999", "#999999");
        BottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String[] args = {"Default Offensive", "Default Defensive", "My First Offensive"};

        try {
            InputStream is = getAssets().open("strategies.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("string");
            int temp = nList.getLength();

            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    args[i] = node.getTextContent();
                    ListView listview = (ListView) findViewById(R.id.home_list);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Object listItem = listview.getItemAtPosition(position);
                            Intent intent_eval_attempted = new Intent(HomeEval.this, EvalPositionsAchieved.class);
                            view.getContext().startActivity(intent_eval_attempted);
                            //startActivity(intent_eval_attempted);
                        }
                    });


                }
            }


        } catch (Exception e) {e.printStackTrace();}

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,
                    R.layout.list_items,
                    R.id.textView,
                    args);

            ListView list = (ListView) findViewById(R.id.home_list);
            list.setAdapter(adapter);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                R.id.textView,
                args);

        ListView list = (ListView) findViewById(R.id.home_list);
        list.setAdapter(adapter);*/
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
