package org.nhnnext.josunghwan.gearsliderdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

import org.nhnnext.josunghwan.gearslider.GearSlider
import org.nhnnext.josunghwan.gearsliderdemo.R


public class MainActivity : AppCompatActivity(), GearSlider.OnValueChangeListener {
    private val TAG: String = "MainActivity"
    private var gs: GearSlider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gs = findViewById(R.id.gearslider1) as GearSlider
        gs!!.setChangeValueListener(this);
    }

    override fun onValueChange(value: Int) {
        Log.d(TAG, "value :$value")
    }
}
