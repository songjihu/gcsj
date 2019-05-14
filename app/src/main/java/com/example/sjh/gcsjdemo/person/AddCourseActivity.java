package com.example.sjh.gcsjdemo.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.R;

public class AddCourseActivity extends Activity {
    private ImageButton btn;
    private Spinner spin1;
    private Spinner spin2;
    private EditText editC1;
    private EditText editC2;
    private EditText editC3;

    String week;
    String section;
    String courseName;
    String courseTeacher;
    String courseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_do);
        btn=(ImageButton)findViewById(R.id.do_add_course);

        spin1=(Spinner)findViewById(R.id.spinner1);
        spin2=(Spinner)findViewById(R.id.spinner2);
        editC1=(EditText)findViewById(R.id.edit_c_1);
        editC2=(EditText)findViewById(R.id.edit_c_2);
        editC3=(EditText)findViewById(R.id.edit_c_3);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        week=(String)spin1.getSelectedItem();
                        section=(String)spin2.getSelectedItem();
                        courseName=editC1.getText().toString();
                        courseTeacher=editC2.getText().toString();
                        courseTime=editC3.getText().toString();









                        System.out.println(week+"+"+section+"+"+courseName+"+"+courseTeacher+"+"+courseTime);





                    }
                });



    }









public void doAddCourse(View view){
    Toast.makeText(getApplicationContext(),"功能暂未接入", Toast.LENGTH_SHORT).show();


}

    public void GoBack(View view){
        finish();

    }

}
