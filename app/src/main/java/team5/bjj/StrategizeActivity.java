package team5.bjj;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.AbstractList;


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
                case R.id.strategize_navigation_Evaluate:
                    //TextMessage.setText(R.string.title_notifications);
                    int groupCount = expListView.getExpandableListAdapter().getGroupCount();
                    for(int i = 0; i < groupCount; i++){
                        expListView.expandGroup(i);
                    }
                    expListView.setOnChildClickListener(new OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            //parent.getChildAt(childPosition + groupPosition).setBackgroundColor(Color.CYAN);
                            int color = Color.TRANSPARENT;
                            Drawable background = parent.getExpandableListAdapter().getChildView(groupPosition,childPosition,false, v, parent).getBackground();
                            if(background instanceof ColorDrawable)
                            {
                                color = ((ColorDrawable) background).getColor();
                            }
                            if (color == Color.CYAN)
                            {
                                parent.getExpandableListAdapter().getChildView(groupPosition,childPosition,false, v, parent).setBackgroundColor(Color.TRANSPARENT);

                            } else {
                                parent.getExpandableListAdapter().getChildView(groupPosition,childPosition,false, v, parent).setBackgroundColor(Color.CYAN);
                            }
                            return false;
                        }
                    });
                    hideNavigationView();
//                    NavigationView navigationView = (NavigationView) findViewById(R.id.strategize_navigation_id);
//                    Menu menu = navigationView.getMenu();
//                    MenuItem target = menu.findItem(R.id.strategize_navigation_id);
//                    target.setVisible(false);
//                    expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            parent.getItemAtPosition(position);
//                            parent.get
//                        }
//                    });
                    return true;
                case R.id.navigation_settings_search:
                    return true;

                case R.id.navigation_home:
                    return true;
            }
            return false;
        }
    };

    private void hideNavigationView() {

    }

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
        List<String> templist = new ArrayList<String>();
        Collections.addAll(argsList, args);

        //XML Parser Goes Here
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //InputStream currentFile = getAssets().open("strategies.xml");
            String fileName = xmlName+".xml";
            InputStream currentFile = openFileInput(fileName);

            doc = dBuilder.parse(currentFile);
            currentFile.close();
            Element element=doc.getDocumentElement();

            element.normalize();
            NodeList n = element.getElementsByTagName("strategy");

            //for (int i = 2; i < nList.getLength(); i++) {
            Node strategy = n.item(0);
            Element d = (Element) strategy;
            NodeList nList = d.getElementsByTagName("position");
            for (int i = 0; i < nList.getLength(); i++) {
                Node child = nList.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) child;
                    e.normalize();
                    String position = e.getAttribute("id");
                    listDataHeader.add(position);

                    NodeList loopNodeChildren = e.getElementsByTagName("move");
                    templist = new ArrayList<String>();
                    int length = loopNodeChildren.getLength();
                    for (int j = 0; j < length; j++) {
                        Node child2 = loopNodeChildren.item(j);
                        //  if ((child != null)&&(child.getNodeType() == Node.ELEMENT_NODE)) {
                        if (child2.getNodeType() == Node.ELEMENT_NODE) {
                            //Element temp = (Element) child;
                            String move = child2.getTextContent();
                            templist.add(move);
                            listDataChild.put(((Element)(child2.getParentNode())).getAttribute("id"), templist);
                        }
                        //listDataChild.put(position, templist);
                    }

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
