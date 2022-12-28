package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Student implements Serializable{
    String[] completedCourses;
    public Student(String[] x){
        completedCourses=new String[x.length];
        
        for(int i=0; i<x.length;i++){
            completedCourses[i]=x[i].split(",")[0];
        }
    }
    public String[] getCompletedCourses(){
        return completedCourses.clone();
    }
    private String[] getAvailableCourse(Course[] plan){
        ArrayList temp= new ArrayList<String>();
        String[] preReq;
        String[] returnString;
        int counter=0;
        int validCounter=0;
        for(int i=0; i<plan.length;i++){
            boolean alreadyFinished= false; 
            boolean valid=false; 
            preReq=plan[i].getPreReq();
            validCounter=0;
            for(int j=0; j<preReq.length;j++){
                valid=false; 
                
                for(int k=0;k<completedCourses.length;k++){

                    if(completedCourses[k].equals(preReq[j])) validCounter++;
                    
                    else if(completedCourses[k].equals(plan[i].getCourseName())){
                        alreadyFinished=true;
                        break;
                    }
                    else if(completedCourses[k].equals("None")) validCounter++; 

                    }

                }
            
            if(validCounter==preReq.length & !alreadyFinished){ // if valid was true for the last pre requisite there won't be any need to check the earlier ones
                temp.add(plan[i].getCourseName());
                counter++;
            }
        }
        Object[] x= temp.toArray();
        returnString= new String[x.length];
        for(int i=0; i<x.length;i++)
            returnString[i]= (String) x[i];
        return returnString;
        
            
    }


    public ArrayList getOfferedAvaliableCourse(Course[] plan,Section[] courseOffering){
        ArrayList temp= new ArrayList<Section>();
        String[] validCourses= getAvailableCourse(plan);
        try {
            for(int i=0; i<validCourses.length;i++){
                for(int j=0;j<courseOffering.length;j++){
                    if(validCourses[i].equals(courseOffering[j].getCourseName())){
                        temp.add(courseOffering[j]);
                    }
                }
            }
            
            return  temp;
        } catch (Exception e) {
            return null;
        }
    }


    public String toString(){
        return Arrays.toString(completedCourses);
    }

}
