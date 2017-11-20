package team5.bjj;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.System.in;


public class Home extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    Intent intent_search = new Intent(Home.this,SearchActivity.class);
                    startActivity(intent_search);
                    return true;

                case R.id.navigation_new_strategy:
                    Intent intent_new_strategy = new Intent(Home.this,AddCustomStrategyActivity.class);
                    startActivity(intent_new_strategy);
                    return true;

                case R.id.navigation_eval:
                    Intent intent_eval = new Intent(Home.this,HomeEval.class);
                    startActivity(intent_eval);
                    return true;

                case R.id.navigation_settings:
                    // mTextMessage.setText(R.string.title_new_strategy);

                    return true;
                case R.id.navigation_edit:
                    //mTextMessage.setText(R.string.title_eval);
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


        //XML Parser Goes Here
        try {
            InputStream is = getAssets().open("./res/values/strategies.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            //NodeList nList = doc.getElementsByTagName("employee");
            NodeList nList = doc.getElementsByTagName("strategy");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    args[i] = node.getTextContent();
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
