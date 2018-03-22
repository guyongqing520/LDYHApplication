package net.syxsoft.ldyhapplication.model;

/**
 * Created by Administrator on 2018/3/22.
 */

public class rztb_bglx_model {
    public int id;
    public  String Title;

    public rztb_bglx_model(int id, String title) {
        this.id = id;
        Title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
