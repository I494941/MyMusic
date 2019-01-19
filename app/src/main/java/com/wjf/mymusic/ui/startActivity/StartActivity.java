package com.wjf.mymusic.ui.startActivity;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjf.mymusic.MainActivity;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseAppCompatActivity;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.sp.SharePreferenceManager;

import butterknife.BindView;

/**
 * Created by wjf on 2019/1/11.
 */
public class StartActivity extends BaseAppCompatActivity implements StartContract.View {

    @BindView(R.id.iv_bing)
    ImageView mIvBing;

    private StartPresenter mPrensenter;
    private SharePreferenceManager sp = new SharePreferenceManager(this);

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViewsAndEvents() {
        mPrensenter = new StartPresenter(this, this);
        mPrensenter.getBingPic();
    }

    @Override
    public void getBingPicSucc(String str) {
        sp.putString(Constants.BING_URL, str);
        Glide.with(StartActivity.this).load(str).into(mIvBing);
    }

    @Override
    public void getBingPicComplete() {
        startAnimation();
    }

    private void startAnimation() {
        //创建动画，参数表示他的子动画是否共用一个插值器
        AnimationSet animationSet = new AnimationSet(true);
        //添加动画
        animationSet.addAnimation(new AlphaAnimation(0f, 1.0f));
        animationSet.addAnimation(new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        //        animationSet.addAnimation(new  TranslateAnimation(0, 150,0, 0));
        animationSet.addAnimation(new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        //设置插值器
        animationSet.setInterpolator(new LinearInterpolator());
        //设置动画持续时长
        animationSet.setDuration(3000);
        //设置动画结束之后是否保持动画的目标状态
        animationSet.setFillAfter(true);
        //设置动画结束之后是否保持动画开始时的状态
        animationSet.setFillBefore(false);
        //设置重复模式
        animationSet.setRepeatMode(AnimationSet.REVERSE);
        //设置重复次数
        animationSet.setRepeatCount(AnimationSet.INFINITE);
        //设置动画延时时间
        animationSet.setStartOffset(1000);
        //取消动画
        animationSet.cancel();
        //释放资源
        animationSet.reset();

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //开始动画
        mIvBing.startAnimation(animationSet);
    }
}
