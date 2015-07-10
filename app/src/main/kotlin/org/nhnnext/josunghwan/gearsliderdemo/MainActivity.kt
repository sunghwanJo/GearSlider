package org.nhnnext.josunghwan.gearsliderdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

import org.nhnnext.josunghwan.gearslider.GearSlider
import org.nhnnext.josunghwan.gearsliderdemo.R


public class MainActivity : AppCompatActivity(), GearSlider.OnValueChangeListener {

    private var btn: Button? = null
    private var tv: TextView? = null
    private var gs: GearSlider? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gs = findViewById(R.id.gss) as GearSlider
        gs!!.setChangeValueListener(this)
        btn = findViewById(R.id.btn1) as Button
        btn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                //                gs.setNumberOfBar(10);
                //                gs.setValue(10);
                gs!!.shake()
            }
        })
        tv = findViewById(R.id.textt) as TextView

    }

    override fun onValueChange(value: Int) {
        tv!!.setText("" + value)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item!!.getItemId()

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super<AppCompatActivity>.onOptionsItemSelected(item)
    }

}
