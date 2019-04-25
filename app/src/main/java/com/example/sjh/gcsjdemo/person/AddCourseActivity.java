package com.example.sjh.gcsjdemo.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;

public class AddCourseActivity extends Activity {
    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_do);
        btn=(ImageButton)findViewById(R.id.do_add_course);

    }

public void doAddCourse(View view){
    Toast.makeText(getApplicationContext(),"功能暂未接入", Toast.LENGTH_SHORT).show();


}
    public void GoBack(View view){
        finish();

    }

}
