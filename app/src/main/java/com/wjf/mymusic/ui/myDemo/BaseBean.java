package com.wjf.mymusic.ui.myDemo;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by wjf on 2019/2/1.
 */
public class BaseBean implements MultiItemEntity {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    private int itemType;

    private String name = "姓名";
    private int sex;
    private int age;
    private String imgurl;

    private List<ItemBean> mItemBeans;

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public List<ItemBean> getItemBeans() {
        return mItemBeans;
    }

    public void setItemBeans(List<ItemBean> itemBeans) {
        mItemBeans = itemBeans;
    }

    public static class ItemBean {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
