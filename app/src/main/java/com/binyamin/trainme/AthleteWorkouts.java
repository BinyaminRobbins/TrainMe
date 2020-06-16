package com.binyamin.trainme;

import java.util.ArrayList;

public class AthleteWorkouts {
    //private String athleteName;
    private String category;
    private String workoutName;
    private int gifResource;
    private String repCount;
    private String setCount;


    public AthleteWorkouts(/*String athleteName,*/ String category, String workoutName, int gifResource, String repCount, String setCount){
       // this.athleteName = athleteName;
        this.category = category;
        this.workoutName = workoutName;
        this.gifResource = gifResource;
        this.repCount = repCount;
        this.setCount = setCount;

    }
   /*public String getAthleteName(){
        return athleteName;
    }*/
    public String getCategory() {
        return category;
    }
    public String getWorkoutName(){
        return workoutName;
    }
    public int getGif(){
        return gifResource;
    }
    public String getRepCount(){
        return repCount;
    }
    public String getSetCount(){
        return repCount;
    }

}