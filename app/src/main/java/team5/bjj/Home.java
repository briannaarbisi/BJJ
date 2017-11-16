package team5.bjj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class Home extends AppCompatActivity {

    ListView temp = new android.widget.ListView(Home.this);
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    Intent intent = new Intent(Home.this,SearchActivity.class);
                    startActivity(new Intent(Home.this, SearchActivity.class));
                    return true;
                case R.id.navigation_new_strategy:
                    mTextMessage.setText(R.string.title_new_strategy);

                    return true;
                case R.id.navigation_eval:
                    mTextMessage.setText(R.string.title_eval);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // ListView temp = new android.widget.ListView(Home.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.search);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //String [] args = {"Default Offensive", "Default Defensive", "My First Offensive"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(temp.getListView().getContext(), android.R.layout.simple_list_item_1, args);
        //temp.getListView().setAdapter(adapter);
        populateListView();
    }

    private void populateListView() {
        String[] args = {"Default Offensive", "Default Defensive", "My First Offensive"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_items,
                args);
        //ListView list = (ListView) findViewById(R.id.)
    }
}
