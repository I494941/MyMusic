package com.wjf.mymusic.ui.about;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.MyDemosActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/21.
 */
public class AboutActivity extends BaseToolbarActivity {

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents() {
        mTvVersion.setText(getVersion());
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.can_not_find_version_name);
        }
    }

    @OnClick({R.id.iv_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                startActivity(MyDemosActivity.class);
                break;
        }
    }
}
