package team5.bjj;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
import javax.xml.transform.stream.StreamResult;


public class StrategizeActivity extends AppCompatActivity {

    //private TextView mTextMessage;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> argsList;
    ArrayAdapter<String> adapter;
    ExpandableListAdapter random;
    String[] args= {"Default Offensive", "Default Defensive", "My First Offensive"};
    String strategyName;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    TransformerFactory transformerFactory;
    Transformer transformer;
    String fileName;
    List<String> templist;
    ListView temp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //case R.id.strategize_navigation_delete:
                    //mTextMessage.setText(R.string.title_home);
                 //   return true;
                case R.id.strategize_navigation_add_position:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent intent_new_position = new Intent(StrategizeActivity.this,AddCustomPositionActivityStrategize.class);
                    startActivityForResult(intent_new_position, 2);
                    return true;
                case R.id.strategize_navigation_add_move:
                    Intent intent_new_move = new Intent(StrategizeActivity.this,AddCustomMoveActivityStrategize.class);
                    //intent_new_move.putExtra("key", strategyName);
                    Bundle c = new Bundle();
                    intent_new_move.putExtra("key", strategyName);
                    //intent_new_move.putExtra("key2", positionName);
                    startActivityForResult(intent_new_move, 3);
                    return true;
                case R.id.strategize_navigation_Evaluate:
                    //TextMessage.setText(R.string.title_notifications);
                    Intent intent_eval_moves_attempted = new Intent(StrategizeActivity.this,EvalMovesWereAttempted.class);
                    Bundle b = new Bundle();
                    intent_eval_moves_attempted.putExtra("key", strategyName);
                    startActivity(intent_eval_moves_attempted);
                    return true;
//                    int groupCount = expListView.getExpandableListAdapter().getGroupCount();
//                    for(int i = 0; i < groupCount; i++){
//                        expListView.expandGroup(i);
//                    }
//                    expListView.setOnChildClickListener(new OnChildClickListener() {
//                        @Override
//                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                            //parent.getChildAt(childPosition + groupPosition).setBackgroundColor(Color.CYAN);
//                            int color = Color.TRANSPARENT;
//                            Drawable background = parent.getExpandableListAdapter().getChildView(groupPosition,childPosition,false, v, parent).getBackground();
//                            if(background instanceof ColorDrawable)
//                            {
//                                color = ((ColorDrawable) background).getColor();
//                            }
//                            if (color == Color.CYAN)
//                            {
//                                parent.getExpandableListAdapter().getChildView(groupPosition,childPosition,false, v, parent).setBackgroundColor(Color.TRANSPARENT);
//
//                            } else {
//                                parent.getExpandableListAdapter().getChildView(groupPosition,childPosition,false, v, parent).setBackgroundColor(Color.CYAN);
//                            }
//                            return false;
//                        }
//                    });
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

        strategyName = newString;
        prepareListData(newString);

        random = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(random);

        /*temp = (ListView) findViewById(R.id.position_list);
        //tv.setMovementMethod(new ScrollingMovementMethod());

        temp.q;
        temp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });*/


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

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();


            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            //InputStream currentFile = getAssets().open("strategies.xml");
            fileName = xmlName+".xml";
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {



                String strEditText = data.getStringExtra("positionName");
                listDataHeader.add(strEditText);
                random.notifyDataSetChanged();
                Element element=doc.getDocumentElement();
                element.normalize();
                try {
                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer();

                    Element temp = doc.createElement("position");
                    Attr attr = doc.createAttribute("id");
                    attr.setValue(strEditText);
                    temp.setAttributeNode(attr);


                    Element theElement = doc.getDocumentElement();

                    Element temp2 = doc.createElement("move");
                    templist = new ArrayList<String>();
                    listDataChild.put(strEditText,templist);
                    temp.appendChild((Node)temp2);
                    random.notifyDataSetChanged();

                    theElement.normalize();
                    NodeList n = theElement.getElementsByTagName("strategy");
                    Node strategy = n.item(0);
                    Element d = (Element) strategy;
                    NodeList nList = d.getElementsByTagName("position");

                    strategy.appendChild((Node)temp);

                    DOMSource source = new DOMSource(doc);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    StreamResult result = new StreamResult(bos);
                    transformer.transform(source, result);
                    byte[] array = bos.toByteArray();

                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fos.write(array);
                    fos.close();
                }catch (Exception e) {e.printStackTrace();}

            }
        }
        if (requestCode == 3) {
            if(resultCode == RESULT_OK) {
                templist = new ArrayList<String>();
                String strEditText = data.getStringExtra("moveName");
                String temp = strEditText;
                String strEditText2 = data.getStringExtra("positionName");
                String random3 = strEditText2;
                //argsList.add(strEditText);
                //listDataChild.put(((Element)(child2.getParentNode())).getAttribute("id"), templist);
                templist = listDataChild.get(strEditText2);
                templist.add(temp);
                listDataChild.put(strEditText2,templist);
                random.notifyDataSetChanged();
                Element element=doc.getDocumentElement();
                element.normalize();
                try {
                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer();

                    Element temp2 = doc.createElement("move");
                    temp2.setNodeValue(temp);

                    //Element temp2 = doc.createElement("move");
                    //Attr attr = doc.createAttribute("id");
                    //temp2.setNodeValue(strEditText);;
                    NodeList n = element.getElementsByTagName("strategy");
                    Node strategy = n.item(0);
                    Element d = (Element) strategy;
                    NodeList nList = d.getElementsByTagName("position");
                    Element e;
                    for (int i = 0; i < nList.getLength(); i++) {
                        Node child = nList.item(i);
                       // if (child.getNodeType() == Node.ELEMENT_NODE) {
                            e = (Element) child;
                            e.normalize();
                            String position = e.getAttribute("id");
                            //listDataHeader.add(position);
                            if (position.equals(random3)) {
                                e.appendChild((Node)temp2);
                            }

                        //}
                    }

                    DOMSource source = new DOMSource(doc);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    StreamResult result = new StreamResult(bos);
                    transformer.transform(source, result);
                    byte[] array = bos.toByteArray();

                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fos.write(array);
                    fos.close();
                }catch (Exception e) {e.printStackTrace();}

            }
        }
    }
}
