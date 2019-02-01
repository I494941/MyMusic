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

    private String name;
    private int sex;
    private String imgurl;
    private int imgWidth;
    private int imgHeight;

    private List<BaseBean.ItemBean> mItemBeans;

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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public List<BaseBean.ItemBean> getItemBeans() {
        return mItemBeans;
    }

    public void setItemBeans(List<BaseBean.ItemBean> itemBeans) {
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
