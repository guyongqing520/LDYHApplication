package net.syxsoft.ldyhapplication.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import net.syxsoft.ldyhapplication.R;

import java.util.HashMap;

/**
 * Created by 谷永庆 on 2018/2/14.
 */

public class SliderLayoutHelper {

    public  static void SliderLayoutInit(SliderLayout sliderLayout, Context context, View view){
        HashMap<String,String> urlMaps = new HashMap<>();
        urlMaps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        urlMaps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        urlMaps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");

        for(String name : urlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description(name)//描述
                    .image(urlMaps.get(name))//image方法可以传入图片url、资源id、File
                    .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            // Toast.makeText(MainActivity.this,slider.getBundle().get("extra") + "",
                            // Toast.LENGTH_SHORT).show();
                        }
                    });//图片点击
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);//传入参数
            sliderLayout.addSlider(textSliderView);//添加一个滑动页面
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
        sliderLayout.setCustomIndicator((PagerIndicator)view.findViewById(R.id.custom_indicator2));//自定义指示器
        sliderLayout.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        sliderLayout.setDuration(4000);//设置滚动时间，也是计时器时间
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
