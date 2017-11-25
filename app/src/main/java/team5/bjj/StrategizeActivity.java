package team5.bjj;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

public class StrategizeActivity extends AppCompatActivity {

    //private TextView mTextMessage;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> argsList;
    ArrayAdapter<String> adapter;
    String[] args= {"Default Offensive", "Default Defensive", "My First Offensive"};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.strategize_navigation_delete:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.strategize_navigation_add_position:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.strategize_navigation_custom_move:
                    //TextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_settings_search:
                    return true;

                case R.id.navigation_home:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategize);

        BottomNavigationView TopNavigation = (BottomNavigationView) findViewById(R.id.settings_home);
        changeMenuItemCheckedStateColor(TopNavigation, "#999999", "#999999");
        TopNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView BottomNavigation = (BottomNavigationView) findViewById(R.id.strategize_navigation_id);
        changeMenuItemCheckedStateColor(BottomNavigation, "#999999", "#999999");
        BottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        expListView = (ExpandableListView)findViewById(R.id.expandable_list);
        expListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("key");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("key");
        }

        prepareListData(newString);

        ExpandableListAdapter random = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(random);


    }

    /*
 * Preparing the list data
 */
    private void prepareListData(String xmlName) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        argsList = new ArrayList<String>();

        Collections.addAll(argsList, args);

        //XML Parser Goes Here
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            FileInputStream temp2 = openFileInput(xmlName + ".xml");


           // InputStream is = getAssets().open(xmlName + ".xml");
           //OpenFileInput()

            doc = dBuilder.parse(temp2);
            temp2.close();
            Element element=doc.getDocumentElement();
            //Element element=doc.getDocumentElement();

            element.normalize();

            NodeList nList = doc.getElementsByTagName("item");
            int temp = nList.getLength();

            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(0);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    listDataHeader.add(node.getLocalName());
                    NodeList nodes = node.getChildNodes();

                    int temp3 = nodes.getLength();
                    List<String> templist = new ArrayList<String>();
                    for (int j=0; j<nodes.getLength(); j++) {
                        Node nodesc = nodes.item(j);

                        templist.add(nodesc.getLocalName());
                    }
                    listDataChild.put(node.getLocalName(), templist);
                }
            }

        } catch (Exception e) {e.printStackTrace();}
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

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}
