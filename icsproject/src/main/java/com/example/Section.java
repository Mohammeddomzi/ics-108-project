package com.example;

import java.io.Serializable;
import java.util.Arrays;

//comment
public class Section implements Serializable {
    private String courseName;
    private int  section;
    private String gender;
    private String lecturer; 
    private String days;
    private String[] time; 
    private String type;// lab or lec
    private String status; 
    private String waitlist; 
    private String crn;
    private String location; 
    
    public Section(String x){
        String[] array=x.split(",");
        String[] courseAndsec= array[0].split("-");
        courseName=courseAndsec[0];
        if((courseAndsec[1].charAt(0)+"").equals("F")){
            gender="F";
            section=Integer.parseInt(courseAndsec[1].substring(1));
        }
        else{
            gender="M";
            section=Integer.parseInt(courseAndsec[1]);
        }
        type=array[1];
        crn=array[2];
        lecturer=array[4];
        days=array[5];
        time=array[6].split("-");
        status=array[7];
        waitlist=array[8];

    }
    public String getCourseName(){
        return courseName;
    }
    public String toString(){
        return courseName+"-"+section+", "+gender +", " +type+", "+crn+", "+lecturer+", "+days+", "+status+", "+waitlist+", "+location+", "+Arrays.toString(time);
    }
    public String toString2() {
        return courseName + "-" + section + "    " + Arrays.toString(time) + " " + days;
    }
    public String getDays() {
        return days;
    }
    public String getType(){
        return type;
    }

    public int[] period() {
        
        String[] starting = {time[0].substring(0,2),time[0].substring(2)};
        int startMin = Integer.parseInt(starting[0]) * 60 + Integer.parseInt(starting[1]);
        
        String[] finshing =  {time[1].substring(0,2),time[1].substring(2)};
        int finishMin = Integer.parseInt(finshing[0]) * 60 + Integer.parseInt(finshing[1]);
        int timeInMin[] = { startMin, finishMin };
        return timeInMin;

    }
}
