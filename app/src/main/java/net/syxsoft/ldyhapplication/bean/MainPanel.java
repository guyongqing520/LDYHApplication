package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/2/28.
 */

public class MainPanel {
    private String name;

    private int imageId;

    public MainPanel(int imageId,String name){
        this.imageId=imageId;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }


}
