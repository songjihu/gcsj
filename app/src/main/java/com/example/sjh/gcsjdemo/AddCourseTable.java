package com.example.sjh.gcsjdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjh.gcsjdemo.person.AddCourseActivity;
import com.example.sjh.gcsjdemo.person.DBUtils;

import com.example.sjh.gcsjdemo.person.dCourse;
import com.example.sjh.gcsjdemo.person.dCourseInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddCourseTable extends Activity {
    private ImageButton btn1;
   int i=0;
   String mon="1";
   String tue="2";
   String wed="3";
   String thr="4";
   String fri="5";








    LinearLayout weekPanels[]=new LinearLayout[7];
    List courseData[]=new ArrayList[7];
    int itemHeight;
    int marTop,marLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_table);

        btn1 = (ImageButton) findViewById(R.id.add_course);//课表中的 加号按钮





        //
        itemHeight=getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
        marTop=getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
        marLeft=getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
//数据
        getData();




        for (int i = 0; i < weekPanels.length; i++) {
            weekPanels[i]=(LinearLayout) findViewById(R.id.weekPanel_1+i);
            initWeekPanel(weekPanels[i], courseData[i]);
        }

    }





   // Intent intent =getIntent();
      // Bundle MonCourse=intent.getExtras();

    public void getData(){
       // dCourse dcourse=getIntent().getParcelableExtra("cou_list");
        ArrayList<dCourse> cou_list = new ArrayList();
        ArrayList<dCourseInfo> cou_list_info=new ArrayList<>();
        cou_list=getIntent().getParcelableArrayListExtra("cou_list");
        cou_list_info=getIntent().getParcelableArrayListExtra("cou_list_info");
        List<Course>list1=new ArrayList<Course>();


        for( i=0;i<cou_list_info.size();i++){
           // System.out.println("------------------------------>"+cou_list_info.get(i).getScId());
            //System.out.println("+++++++------------>"+cou_list.get(0).getD_one());
            if(cou_list_info.get(i).getScId().equals(cou_list.get(0).getD_one())==true&&cou_list_info.get(i).getDay().equals(mon)) {


               // if (cou_list.get(0).getD_one() == null) {

               // } else {
                System.out.println("+++++++------------>"+cou_list_info.get(i).getcName());
                    Course c1 = new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 1, 2, cou_list_info.get(i).getcTeacherName(), "1001");
                    list1.add(c1);
              //  }

            }
        }


        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(0).getD_two())==true&&cou_list_info.get(i).getDay().equals(mon)) {
                System.out.println("+++++++------------>"+cou_list.get(0).getD_two());
                System.out.println("+++++++------------>"+i);
                System.out.println("+++++++------------>"+cou_list_info.get(i).getcName());

                list1.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 3, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }


           // list1.add(new Course(cou_list.get(0).getD_two(), "A103", 3, 2, "甘宁", "1003"));

        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(0).getD_three())==true&&cou_list_info.get(i).getDay().equals(mon)) {


                list1.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 5, 2, cou_list_info.get(i).getcTeacherName(), "1003"));



            }
        }





            //list1.add(new Course(cou_list.get(0).getD_three(), "A104", 5, 2, "甘宁", "1004"));



        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(0).getD_four())==true&&cou_list_info.get(i).getDay().equals(mon)) {


                list1.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 7, 2, cou_list_info.get(i).getcTeacherName(), "1004"));



            }
        }


           //list1.add(new Course(cou_list.get(0).getD_four(), "A105", 7, 2, "甘宁", "1005"));



        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(0).getD_five())==true&&cou_list_info.get(i).getDay().equals(mon)) {


                list1.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 9, 2, cou_list_info.get(i).getcTeacherName(), "1005"));



            }
        }


           // list1.add(new Course(cou_list.get(0).getD_five(), "A105", 9, 2, "甘宁", "1005"));

        courseData[0]=list1;










        List<Course>list2=new ArrayList<Course>();
        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(1).getD_one())==true&&cou_list_info.get(i).getDay().equals(tue)) {



                System.out.println("+++++++------------>"+cou_list_info.get(i).getcName());
                Course c1 = new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 1, 2, cou_list_info.get(i).getcTeacherName(), "1002");
                list2.add(c1);


            }
        }


        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(1).getD_two())==true&&cou_list_info.get(i).getDay().equals(tue)) {


                list2.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 3, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }




        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(1).getD_three())==true&&cou_list_info.get(i).getDay().equals(tue)) {


                list2.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 5, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }









        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(1).getD_four())==true&&cou_list_info.get(i).getDay().equals(tue)) {


                list2.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 7, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }






        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(1).getD_five())==true&&cou_list_info.get(i).getDay().equals(tue)) {


                list2.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 9, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }
        courseData[1]=list2;

        List<Course>list3=new ArrayList<Course>();
        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(2).getD_one())==true&&cou_list_info.get(i).getDay().equals(wed)) {



                System.out.println("+++++++------------>"+cou_list_info.get(i).getcName());
                Course c1 = new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 1, 2, cou_list_info.get(i).getcTeacherName(), "1002");
                list3.add(c1);


            }
        }


        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(2).getD_two())==true&&cou_list_info.get(i).getDay().equals(wed)) {


                list3.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 3, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }




        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(2).getD_three())==true&&cou_list_info.get(i).getDay().equals(wed)) {


                list3.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 5, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }









        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(2).getD_four())==true&&cou_list_info.get(i).getDay().equals(wed)) {


                list3.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 7, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }






        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(2).getD_five())==true&&cou_list_info.get(i).getDay().equals(wed)) {


                list3.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 9, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }
        courseData[2]=list3;

        List<Course>list4=new ArrayList<Course>();
        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(3).getD_one())==true&&cou_list_info.get(i).getDay().equals(thr)) {



                System.out.println("+++++++------------>"+cou_list_info.get(i).getcName());
                Course c1 = new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 1, 2, cou_list_info.get(i).getcTeacherName(), "1002");
                list4.add(c1);


            }
        }


        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(3).getD_two())==true&&cou_list_info.get(i).getDay().equals(thr)) {


                list4.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 3, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }




        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(3).getD_three())==true&&cou_list_info.get(i).getDay().equals(thr)) {


                list4.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 5, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }









        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(3).getD_four())==true&&cou_list_info.get(i).getDay().equals(thr)) {


                list4.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 7, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }






        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(3).getD_five())==true&&cou_list_info.get(i).getDay().equals(thr)) {


                list4.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 9, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }
        courseData[3]=list4;

        List<Course>list5=new ArrayList<Course>();
        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(4).getD_one())==true&&cou_list_info.get(i).getDay().equals(fri)) {



                System.out.println("+++++++------------>"+cou_list_info.get(i).getcName());
                Course c1 = new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 1, 2, cou_list_info.get(i).getcTeacherName(), "1002");
                list5.add(c1);


            }
        }


        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(4).getD_two())==true&&cou_list_info.get(i).getDay().equals(fri)) {


                list5.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 3, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }




        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(4).getD_three())==true&&cou_list_info.get(i).getDay().equals(fri)) {


                list5.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 5, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }









        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(4).getD_four())==true&&cou_list_info.get(i).getDay().equals(fri)) {


                list5.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 7, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }






        for( i=0;i<cou_list_info.size();i++){

            if(cou_list_info.get(i).getScId().equals(cou_list.get(4).getD_five())==true&&cou_list_info.get(i).getDay().equals(fri)) {


                list5.add(new Course(cou_list_info.get(i).getcName(), cou_list_info.get(i).getcAddress(), 9, 2, cou_list_info.get(i).getcTeacherName(), "1002"));



            }
        }
        courseData[4]=list5;
    }



    public void initWeekPanel(LinearLayout ll,List<Course>data){
        if(ll==null || data==null || data.size()<1)return;
        Log.i("Msg", "初始化面板");
        Course pre=data.get(0);
        for (int i = 0; i < data.size(); i++) {
            Course c =data.get(i);
            TextView tv =new TextView(this);
            LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT ,
                    itemHeight*c.getStep()+marTop*(c.getStep()-1));
            if(i>0){
                lp.setMargins(marLeft, (c.getStart()-(pre.getStart()+pre.getStep()))*(itemHeight+marTop)+marTop, 0, 0);
            }else{
                lp.setMargins(marLeft, (c.getStart()-1)*(itemHeight+marTop)+marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
            tv.setText(c.getName()+"\n"+c.getRoom()+"\n"+c.getTeach());
            //tv.setBackgroundColor(getResources().getColor(R.color.classIndex));
            tv.setBackground(getResources().getDrawable(R.drawable.course_bg));
            ll.addView(tv);
            pre=c;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.course_table, menu);
        return true;
    }




    public void GoBack(View view){
        finish();

    }

    public void addCourse(View view){
        Intent intent=new Intent();
        intent.setClass(this,AddCourseActivity.class);
        startActivity(intent);

    }

}
