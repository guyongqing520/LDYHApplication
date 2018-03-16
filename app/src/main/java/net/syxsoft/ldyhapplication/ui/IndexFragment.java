package net.syxsoft.ldyhapplication.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;

import net.syxsoft.ldyhapplication.Adapter.MainAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.MainPanel;
import net.syxsoft.ldyhapplication.callback.GsonArrayCallback;
import net.syxsoft.ldyhapplication.utils.NetWorkUtils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;
import net.syxsoft.ldyhapplication.utils.SliderLayoutHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends BaseFragment {

    private List<MainPanel> mainPanelList =new ArrayList<MainPanel>();
    /**
     * error : false
     * results : [{"_id":"5aa5cc6a421aa9103ed33c7c","createdAt":"2018-03-12T08:40:10.360Z","desc":"3-12","publishedAt":"2018-03-12T08:44:50.326Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg","used":true,"who":"daimajia"},{"_id":"5a8e0c41421aa9133298a259","createdAt":"2018-02-22T08:18:09.547Z","desc":"2-22","publishedAt":"2018-02-22T08:24:35.209Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg","used":true,"who":"代码家"},{"_id":"5a7b93d2421aa90d2cd3d7f8","createdAt":"2018-02-08T08:03:30.905Z","desc":"2-8","publishedAt":"2018-02-08T08:13:24.479Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a6e5f88421aa9115696004f","createdAt":"2018-01-29T07:40:56.269Z","desc":"1-29","publishedAt":"2018-01-29T07:53:57.676Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a65381a421aa91156960022","createdAt":"2018-01-22T09:02:18.715Z","desc":"1-22","publishedAt":"2018-01-23T08:46:45.132Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180122090204_A4hNiG_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a5bfc29421aa9115489927b","createdAt":"2018-01-15T08:56:09.429Z","desc":"1-15","publishedAt":"2018-01-16T08:40:08.101Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180115085556_8AeReR_taeyeon_ss_15_1_2018_7_58_51_833.jpeg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    //@BindView(R.id.user_password)
    //TextInputEditText mUserPsw;

    //@OnClick(R.id.register_btn)
    //public void onRegisterBtnClicked(){
        //pushFragment(VerifyFragment.newInstance(VerifyFragment.REGISTER));
    //}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("主页");

        //禁用返回导航，因为此页就是首页
        actionBar.setNavigationIcon(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater,container,  savedInstanceState);

        //启用底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);

        //滚动图片
        View view= inflater.inflate(R.layout.fragment_index, container, false);
        SliderLayout sliderLayout =view.findViewById(R.id.slider);
        SliderLayoutHelper.SliderLayoutInit(sliderLayout,container.getContext(),view);

        //主功能面板
        final RecyclerView recyclerView= view.findViewById(R.id.recyler_view);
        GridLayoutManager layoutManager= new GridLayoutManager(container.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        if(!getHoldingActivity().isNetWorkAvailable()){
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
        }

        //OkHttp3Utils.getInstance().doGet("http://192.168.1.105/mian.json", new GsonArrayCallback<MainPanel>() {

        //    @Override
        //    public void onSuccess(List<MainPanel> list) {
        //       recyclerView.setAdapter(new MainAdapter(list));
        //    }

         //   @Override
        //    public void onFailed(Call call, IOException e) {

         //   }
        //});

          mainPanelList.clear();
          mainPanelList.add(new MainPanel(R.mipmap.kqgl01,"考勤管理"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl02,"日志上报"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl03,"任务管理"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl04,"学习培训"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl05,"绩效考核"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl06,"投诉举报"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl07,"督查督办"));

          recyclerView.setAdapter(new MainAdapter(mainPanelList));



        return view;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5aa5cc6a421aa9103ed33c7c
         * createdAt : 2018-03-12T08:40:10.360Z
         * desc : 3-12
         * publishedAt : 2018-03-12T08:44:50.326Z
         * source : chrome
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg
         * used : true
         * who : daimajia
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
