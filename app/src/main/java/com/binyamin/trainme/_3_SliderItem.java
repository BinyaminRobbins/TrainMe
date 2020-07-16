package com.binyamin.trainme;

public class _3_SliderItem {
    private int image;
    private int favoriteImage;
    private String athleteName;
    private boolean requiresPremium;
    private boolean isFavorite;
    private int tagNum;

    public _3_SliderItem(int image, int favoriteImage, String athleteName,boolean requiresPremium,boolean isFavorite, int tagNum){
        this.image = image;
        this.favoriteImage = favoriteImage;
        this.athleteName = athleteName;
        this.requiresPremium = requiresPremium;
        this.isFavorite = isFavorite;
        this.tagNum = tagNum;
    }
    public int getImage(){
        return image;
    }
    public int getFavoriteImage(){
        return favoriteImage;
    }
    public String getAthleteName(){
        return athleteName;
    }
    public boolean getIfRequiresPremium() { return requiresPremium; }
    public boolean getIsFavorite(){return isFavorite;}
    public int getTagNum(){return tagNum;}


}
