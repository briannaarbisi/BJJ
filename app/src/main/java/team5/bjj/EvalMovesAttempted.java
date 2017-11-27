package team5.bjj;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

/**
 * Created by b on 11/18/17.
 */

public class EvalMovesAttempted extends AppCompatActivity {

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
                case R.id.navigation_previous:
                    onBackPressed();
                    return true;

                case R.id.navigation_next:
                    Intent intent_eval = new Intent(EvalMovesAttempted.this,EvalMovesSuccessful.class);
                    startActivity(intent_eval);
                    return true;

                case R.id.navigation_settings:
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
        setContentView(R.layout.activity_home);

        BottomNavigationView TopNavigation = (BottomNavigationView) findViewById(R.id.settings_home);
        changeMenuItemCheckedStateColor(TopNavigation, "#999999", "#999999");
        TopNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView BottomNavigation = (BottomNavigationView) findViewById(R.id.search_new_strategy_eval);
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


        int groupCount = expListView.getExpandableListAdapter().getGroupCount();
        for(int i = 0; i < groupCount; i++){
            expListView.expandGroup(i);
        }
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
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

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}