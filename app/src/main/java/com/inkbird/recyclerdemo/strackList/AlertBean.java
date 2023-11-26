package com.inkbird.recyclerdemo.strackList;

public class AlertBean {
    private long id;
    private int img;
    private long name;

    public AlertBean(long id, int img, long name) {
        this.id = id;
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }
}
