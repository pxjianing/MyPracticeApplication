package com.jn.mypracticeapplication.entity;

import java.io.Serializable;

/**
 * Created by JN on 2016/11/22 .
 * 首页GridView的实体类
 */

public class SimpleGVEntity implements Serializable{
    private int image;
    private String sIndex;

    SimpleGVEntity(){

    }

    public SimpleGVEntity(int image, String sIndex) {
        this.image = image;
        this.sIndex = sIndex;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getsIndex() {
        return sIndex;
    }

    public void setsIndex(String sIndex) {
        this.sIndex = sIndex;
    }

    @Override
    public String toString() {
        return "SimpleGVEntity{" +
                "image=" + image +
                ", sIndex='" + sIndex + '\'' +
                '}';
    }
}
