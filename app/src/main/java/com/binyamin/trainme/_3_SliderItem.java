package com.binyamin.trainme;

public class _3_SliderItem {
    private int image;
    private String athleteName;
    private boolean requiresPremium;
    private boolean isFavorite;
    private int tagNum;

    public _3_SliderItem(int image, String athleteName,boolean requiresPremium,boolean isFavorite, int tagNum){
        this.image = image;
        this.athleteName = athleteName;
        this.requiresPremium = requiresPremium;
        this.isFavorite = isFavorite;
        this.tagNum = tagNum;
    }
    public int getImage(){
        return image;
    }
    public String getAthleteName(){
        return athleteName;
    }
    public boolean getIfRequiresPremium() { return requiresPremium; }
    public boolean getIsFavorite(){return isFavorite;}
    public int getTagNum(){return tagNum;}


}
