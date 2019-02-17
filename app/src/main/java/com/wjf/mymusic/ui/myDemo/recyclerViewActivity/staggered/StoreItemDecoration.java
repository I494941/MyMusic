package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by wjf on 2019/2/17.
 */
public class StoreItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int bottomSpace;

    public StoreItemDecoration(int space) {
        this.space = space;
    }

    public StoreItemDecoration(int space, int bottomSpace) {
        this.space = space;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //StaggeredGridLayoutManager随机的宽高GridLayoutManager等高等宽
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = params.getSpanIndex();
        if (spanIndex == 0) {
            outRect.left = 30;
        } else {
            outRect.left = space;
        }
        outRect.bottom = bottomSpace;
    }
}