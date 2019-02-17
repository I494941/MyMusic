package com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.BaseBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by wjf on 2019/2/1.
 */
public class StaggeredRecyclerViewActivity extends BaseToolbarActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    //图片链接
    private String[] mDatas = {
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2056059545,3075726884&fm=27&gp=0.jpg",
            "http://cn.bing.com/az/hprichbg/rb/MinnewankaBoathouse_ZH-CN0548323518_1920x1080.jpg",
            "http://cn.bing.com/az/hprichbg/rb/PangolinDay_ZH-CN4393242380_1920x1080.jpg",
            "http://cn.bing.com/az/hprichbg/rb/GBBC_ZH-CN4481989355_1920x1080.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1240426408,3396216424&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1765208127,2618259413&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1447507835,3654535229&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2716881984,3272848008&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4056436047,3626959226&fm=27&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519994047809&di=5b646d6c9d8fca47ff0749aeebf46fba&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201407%2F20%2F20140720201056_HmZ4d.jpeg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1286656969,4109636359&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1928187901,3300785708&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3941443566,3889552161&fm=27&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519994047808&di=af68834c33967cd6a82bd047fbf8f809&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201310%2F23%2F20131023105257_zNeA3.jpeg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1820318310,3796454650&fm=27&gp=0.jpg",
            "http://img.zcool.cn/community/01638059302785a8012193a36096b8.jpg@2o.jpg",
            "http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg",
            "http://pic2.nipic.com/20090424/1242397_110033072_2.jpg",
            "http://news.cnhubei.com/ctjb/ctjbsgk/ctjb40/200808/W020080822221006461534.jpg",
            "http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg",
            "http://d.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40fe871b36bad64034f79f019d4.jpg",
            "http://pic40.nipic.com/20140424/13846002_113008517141_2.jpg",
            "http://i9.download.fd.pchome.net/t_960x600/g1/M00/0B/10/oYYBAFQlOmuIZDQRAALvMZ8mYRIAAB9HAKQEtcAAu9J875.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037235_3453.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037235_9280.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037234_3539.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037234_6318.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037194_2965.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037193_1687.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037193_1286.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037192_8379.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037178_9374.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037177_1254.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037177_6203.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037152_6352.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037151_9565.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037151_7904.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037148_7104.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037129_8825.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037128_5291.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037128_3531.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037127_1085.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037095_7515.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037094_8001.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037093_7168.jpg",
            "https://img-my.csdn.net/uploads/201309/01/1378037091_4950.jpg",
            "https://img-my.csdn.net/uploads/201308/31/1377949643_6410.jpg",
            "https://img-my.csdn.net/uploads/201308/31/1377949642_6939.jpg",
            "https://img-my.csdn.net/uploads/201308/31/1377949630_4505.jpg",
            "https://img-my.csdn.net/uploads/201308/31/1377949630_4593.jpg",
            "https://img-my.csdn.net/uploads/201308/31/1377949629_7309.jpg",
            "https://img-my.csdn.net/uploads/201308/31/1377949629_8247.jpg",
            "http://img2.imgtn.bdimg.com/it/u=834235734,679217072&fm=27&gp=0.jpg"};

    private ArrayList<BaseBean> mList = new ArrayList<>();
    private StaggeredAdapter mAdapter;
    private int mSpan = 2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void initViewsAndEvents() {
        initRecyclerview();
        initList();
    }

    private void initRecyclerview() {
        //设置layoutManager
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(mSpan, StaggeredGridLayoutManager.VERTICAL);
        //解决item跳动
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRv.setLayoutManager(manager);
        mRv.setItemAnimator(null);
        //设置分割线
        mRv.addItemDecoration(new StoreItemDecoration(30,30));

        mAdapter = new StaggeredAdapter(R.layout.item_iv_tv, mList, mSpan);
        mRv.setAdapter(mAdapter);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[mSpan];
                manager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    manager.invalidateSpanAssignments();
                }
            }
        });
    }

    private void initList() {
        for (int i = 0; i < mDatas.length; i++) {
            BaseBean baseBean = new BaseBean();
            baseBean.setImgurl(mDatas[i]);
            mList.add(baseBean);
        }
        mAdapter.notifyDataSetChanged();
    }
}
