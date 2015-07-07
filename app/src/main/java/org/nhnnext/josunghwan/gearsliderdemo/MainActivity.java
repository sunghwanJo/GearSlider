package org.nhnnext.josunghwan.gearsliderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.nhnnext.josunghwan.gearslider.GearSlider;


public class MainActivity extends AppCompatActivity implements GearSlider.OnValueChangeListener {

    private Button btn;
    private TextView tv;
    private GearSlider gs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gs = (GearSlider) findViewById(R.id.gss);
        gs.setChangeValueListener(this);
        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gs.setNumberOfBar(10);
//                gs.setValue(10);
                gs.shake();
            }
        });
        tv = (TextView) findViewById(R.id.textt);

    }

    @Override
    public void onValueChange(int value) {
        tv.setText("" + value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
