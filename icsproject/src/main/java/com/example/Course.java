package com.example;

import java.io.Serializable;
import java.util.Arrays;

public class Course implements Serializable{
    private String courseName; 
    private String[] preReq; 
    private int credits;
    private String[] coReq;
    public Course(String string){
        String[] array= string.split(",");
        String[] coReq=stringToArray(array[3], ";");;
        String[] preReq= stringToArray(array[2], ";");

        this.courseName=array[0];
        this.preReq=preReq;
        this.coReq=coReq;
        this.credits=Integer.parseInt(array[1]);
        
        
    }
    public String getCourseName(){
        return courseName;
    }
    public String[] getPreReq(){
        return preReq;
    }
    public String[] getCoReq(){
        return coReq;
    }
    public int getCredit(){
        return credits;
    }
    public String toString(){
        return getCourseName()+ " "+Arrays.toString(preReq)+" "+Arrays.toString(coReq)+" "+getCredit();
    }
    public static String[] stringToArray(String x,String regix){
        if(x.split(regix).length==0){
            String[] returnArray= {x};
            return returnArray;    
        }
        return x.split(regix);
    }
}
