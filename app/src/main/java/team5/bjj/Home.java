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
import android.widget.AdapterView;
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
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.System.in;

import android.view.View;
import android.view.Menu;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> argsList;

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
                    startActivityForResult(intent_new_strategy, 1);
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

        argsList = new ArrayList<String>();

        Collections.addAll(argsList, args);

        //ListView tv = findViewById(R.id.home_list);

        ///tv.setOnClickListener(new View.OnClickListener() {
         ///   @Override
            //public void onClick(View v) {
            //    Intent intent_strategize = new Intent(Home.this,StrategizeActivity.class);
           //     startActivity(intent_strategize);
           // }
       /// });


        //XML Parser Goes Here
        try {
            //AssetManager assetManager = getResources().getAssets();

            //String tempDir = System.getProperty("user.dir");
            //String myFile = getFileStreamPath("strategies.xml");
            //InputStream is = (InputStream) getResources().getAssets().open(R.id.Strategy2);
            //InputStream is = getAssets().open("/Users/PEFO/Documents/5115/JiuJitsuAppProject/BJJRepo/app/src/main/res/values/strategies.xml");
            InputStream is = getAssets().open("strategies.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            //NodeList nList = doc.getElementsByTagName("employee");
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
                            Intent intent_strategize = new Intent(Home.this, StrategizeActivity.class);
                            startActivity(intent_strategize);
                        }
                    });


                }




                    /*tv.setOnClickListener(new OnItemClickListener(){
                        @Override
                    public void onItemClick(AdapterView<?> parent)  {
                        Intent intent_strategize = new Intent(Home.this,StrategizeActivity.class);
                        startActivity(intent_strategize);
                     }
                     }); */
                    //AdapterView tempview = AdapterView.newInstance();
                    //listview.setOnItemClickListener(AdapterView tempview = (AdapterView)OnItemClickListener(){
                    //    @Override
                   //     public void onItemClick(AdapterView<?> parent)});
                   // }
                }


        } catch (Exception e) {e.printStackTrace();}

        Collections.addAll(argsList, args);

        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                R.id.textView,
                argsList);

        ListView list = (ListView) findViewById(R.id.home_list);
        list.setAdapter(adapter);

        argsList.add("Adding a new strategy");
        adapter.notifyDataSetChanged();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }*/


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
                String strEditText = data.getStringExtra("strategyName");
                argsList.add(strEditText);
                adapter.notifyDataSetChanged();

            }
        }
    }

}
