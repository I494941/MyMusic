package com.wjf.mymusic.ui.myDemo.baseRecyclerViewActivity.Multi;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class MultiBean implements MultiItemEntity {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    private int itemType;

    private String name;

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
