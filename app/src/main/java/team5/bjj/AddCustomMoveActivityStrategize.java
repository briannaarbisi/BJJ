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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
//import android.widget.ListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by b on 11/26/17.
 */

public class AddCustomMoveActivityStrategize extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> argsList;
    String[] args = {"Default Offensive", "Default Defensive", "My First Offensive"};
    String parent = "random";
    String newString;
    private TextView mTextMessage;
    String strategyName;
    ArrayAdapter<String> adapter;
    String currentPosition;
    //ListAdapter random;


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

                   // mTextMessage = (TextView) findViewById(R.id.description);
                    //String description = mTextMessage.getText().toString();

                    intent.putExtra("moveName", name);
                    intent.putExtra("positionName", currentPosition);
                    //intent.putExtra("description", description);
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                case R.id.navigation_settings:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_move_strategize);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // remove the icon
        }

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.cancel_create);
        changeMenuItemCheckedStateColor(navigation, "#999999", "#999999");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("key");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("key");
        }
        strategyName = newString;
        prepareListData(strategyName);
        //random = new ListAdapter(this, listDataHeader);
        List<String> a = listDataHeader;
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                R.id.textView,
                listDataHeader);
        ListView list = (ListView) findViewById(R.id.position_list);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddCustomMoveActivityStrategize.this, (CharSequence) listDataHeader.get(position), Toast.LENGTH_SHORT).show();
                currentPosition = listDataHeader.get(position);
            }
        });
        adapter.notifyDataSetChanged();



        //ListView position = (ListView)findViewById(R.id.position_list);
        //position.setAdapter(new ListAdapter(this, R.id.position_list, listDataHeader));
                //listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
            String fileName = xmlName + ".xml";
            InputStream currentFile = openFileInput(fileName);

            doc = dBuilder.parse(currentFile);
            currentFile.close();
            Element element = doc.getDocumentElement();

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
                            listDataChild.put(((Element) (child2.getParentNode())).getAttribute("id"), templist);
                        }
                        //listDataChild.put(position, templist);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void changeMenuItemCheckedStateColor(BottomNavigationView bottomNavigationView, String checkedColorHex, String uncheckedColorHex) {
        int checkedColor = Color.parseColor(checkedColorHex);
        int uncheckedColor = Color.parseColor(uncheckedColorHex);

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}, // checked

        };

        int[] colors = new int[]{
                uncheckedColor,
                checkedColor
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        bottomNavigationView.setItemTextColor(colorStateList);
        bottomNavigationView.setItemIconTintList(colorStateList);

    }
}