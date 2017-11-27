package team5.bjj;

import android.content.Context;
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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
    ListView listview;
    //String[] args= {"Default Offensive", "Default Defensive", "My First Offensive"};
    String[] args= {"strategies"};

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

                case R.id.navigation_remove:
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            argsList.remove(argsList.get(position));
                            adapter.notifyDataSetChanged();
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String temp = argsList.get(position);
                                    Intent intent_strategize = new Intent(Home.this, StrategizeActivity.class);
                                    Bundle b = new Bundle();
                                    intent_strategize.putExtra("key", temp);
                                    startActivity(intent_strategize);
                                }
                            });
                        }
                    });
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

        argsList = new ArrayList<String>();

        Collections.addAll(argsList, args);

        //XML Parser
        try {

            InputStream is = getAssets().open("strategies.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            StreamResult result=new StreamResult(bos);
            transformer.transform(source, result);
            byte []array=bos.toByteArray();

            FileOutputStream fos = openFileOutput("strategies.xml", Context.MODE_PRIVATE);
            fos.write(array);
            fos.close();

            NodeList nList = doc.getElementsByTagName("name");
            int temp = nList.getLength();

            //argsList.add("strategies");

            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    args[i] = node.getTextContent();
                    listview = (ListView) findViewById(R.id.home_list);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String temp = argsList.get(position);
                            Intent intent_strategize = new Intent(Home.this, StrategizeActivity.class);
                            Bundle b = new Bundle();
                            intent_strategize.putExtra("key", temp);
                            startActivity(intent_strategize);
                        }
                    });
                }

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

        //argsList.add("Adding a new strategy");
        adapter.notifyDataSetChanged();
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
                String strEditText = data.getStringExtra("strategyName");
                argsList.add(strEditText);
                adapter.notifyDataSetChanged();

            }
        }
    }

}
