package com.wjf.mymusic.ui.myDemo.threadPool;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.util.LogUtil;

import java.util.concurrent.*;

/**
 * Created by wjf on 2019/4/30.
 */
public class ThreadPoolActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv2)
    TextView mTv2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {
        mTv1.setText("基本线程池");
        mTv3.setText("可重用固定线程数");
        mTv3.setVisibility(View.VISIBLE);
        mTv4.setText("Cached线程池(按需创建)");
        mTv4.setVisibility(View.VISIBLE);
        mTv2.setText("单个核线的fixed");
    }

    @OnClick({R.id.tv1, R.id.tv3, R.id.tv4, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                //创建基本线程池
                final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(25));
                for (int i = 0; i < 30; i++) {
                    final int finali = i;
                    Runnable runnable = () -> {
                        try {
                            Thread.sleep(2000);
                            LogUtil.e("Thread", "run: " + finali);
                            LogUtil.e("当前线程：", Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    threadPoolExecutor.execute(runnable);
                }
                break;
            case R.id.tv3:
                //创建fixed线程池
                final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
                for (int i = 0; i < 30; i++) {
                    final int finali = i;
                    Runnable runnable = () -> {
                        try {
                            Thread.sleep(2000);
                            LogUtil.e("Thread", "run: " + finali);
                            LogUtil.e("当前线程：", Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    fixedThreadPool.execute(runnable);
                }
                break;
            case R.id.tv4:
                //创建Cached线程池
                final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
                for (int i = 0; i < 30; i++) {
                    final int finali = i;
                    Runnable runnable = () -> {
                        try {
                            Thread.sleep(2000);
                            LogUtil.e("Thread", "run: " + finali);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    cachedThreadPool.execute(runnable);
                }
                break;
            case R.id.tv2:
                //创建Single线程池
                final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
                for (int i = 0; i < 30; i++) {
                    final int finali = i;
                    Runnable runnable = () -> {
                        try {
                            Thread.sleep(2000);
                            LogUtil.e("Thread", "run: " + finali);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    singleThreadExecutor.execute(runnable);
                }
                break;
        }
    }
}
