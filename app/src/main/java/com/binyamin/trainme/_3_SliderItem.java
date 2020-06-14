package com.binyamin.trainme;

public class _3_SliderItem {
    private int image;
    private String athleteName;
    private boolean requiresPremium;


    public _3_SliderItem(int image, String athleteName,boolean requiresPremium){
        this.image = image;
        this.athleteName = athleteName;
        this.requiresPremium = requiresPremium;
    }
    public int getImage(){
        return image;
    }
    public String getAthleteName(){
        return athleteName;
    }
    public boolean getIfRequiresPremium() { return requiresPremium; }
}
