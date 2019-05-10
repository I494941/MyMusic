package com.wjf.mymusic.ui.themeActivity;

import android.os.Build;

import com.wjf.mymusic.MainActivity;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.sp.SharePreferenceManager;
import com.wjf.mymusic.ui.themeActivity.adapter.ThemeAdapter;
import com.wjf.mymusic.ui.themeActivity.bean.ThemeInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by wjf on 2019/1/17.
 */
public class ThemeActivity extends BaseToolbarActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private String[] themeType = {"哔哩粉", "知乎蓝", "酷安绿", "网易红", "藤萝紫", "碧海蓝", "樱草绿", "咖啡棕", "柠檬橙", "星空灰", "夜间模式"};
    private int[] colors = {R.color.biliPink, R.color.zhihuBlue, R.color.kuanGreen, R.color.cloudRed,
            R.color.tengluoPurple, R.color.seaBlue, R.color.grassGreen, R.color.coffeeBrown,
            R.color.lemonOrange, R.color.startSkyGray, R.color.nightActionbar};

    private List<ThemeInfo> mList = new ArrayList<>();
    private ThemeAdapter mAdapter;
    private int selectTheme = 0;

    private SharePreferenceManager sp = new SharePreferenceManager(this);

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void initViewsAndEvents() {
        selectTheme = sp.getInt(Constants.THEME_SELECT);
        initList();
        initRecyclerview();
    }

    private void initList() {
        for (int i = 0; i < themeType.length; i++) {
            ThemeInfo info = new ThemeInfo();
            info.setName(themeType[i]);
            info.setColor(colors[i]);
            info.setSelect((selectTheme == i) ? true : false);
            if (i == themeType.length - 1) {
                info.setBackground(R.color.nightBg);
            } else {
                info.setBackground(R.color.white);
            }
            mList.add(info);
        }
    }

    private void initRecyclerview() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setNestedScrollingEnabled(false);
        mAdapter = new ThemeAdapter(R.layout.item_theme, mList, selectTheme);
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            selectTheme = position;
            sp.putInt(Constants.THEME_SELECT, position);
            mToolbar.setBackgroundColor(getResources().getColor(mList.get(position).getColor()));
            mRv.setBackgroundColor(getResources().getColor(mList.get(position).getBackground()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                getWindow().setStatusBarColor(getResources().getColor(mList.get(position).getColor()));

            if (sp.getInt(Constants.THEME_SELECT) == Constants.THEME_SIZE - 1)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            for (int i = 0; i < mList.size(); i++) {
                if (i == position)
                    mList.get(i).setSelect(true);
                else
                    mList.get(i).setSelect(false);
            }
            mAdapter.setSelectTheme(selectTheme);
        });
    }

    @Override
    public void back() {
        startActivity(MainActivity.class);
        super.back();
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
