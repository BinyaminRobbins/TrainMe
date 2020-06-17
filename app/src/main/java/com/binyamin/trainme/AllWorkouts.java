package com.binyamin.trainme;

import java.util.ArrayList;

public class AllWorkouts {
        private String athleteName;
        private ArrayList<AthleteWorkouts> athleteWorkoutArrayList;
        public AllWorkouts(String athleteName, ArrayList<AthleteWorkouts> athleteWorkoutArrayList){
            this.athleteName = athleteName;
            this.athleteWorkoutArrayList = athleteWorkoutArrayList;
        }
        public ArrayList<AthleteWorkouts> getAthleteWorkoutArrayList(){
            return athleteWorkoutArrayList;
        }
        public String getAthleteName(){
            return athleteName;
        }
    }
