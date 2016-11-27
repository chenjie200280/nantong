package com.tianditu.nantong.utils;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;
import net.youmi.android.normal.common.ErrorCode;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

/**
 * 有米广告助手
 * Created by chenjie on 2016/11/27.
 */
public class YouMiAdUtil {
    Context mContext;
    public YouMiAdUtil(Context context,String appid,String adid){
        this.mContext = context;
        AdManager.getInstance(mContext).init(appid,adid,false,false);
        initSpot();
    }
    public void  showBanner(final ViewGroup viewGroup){
        SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);
        View bannerView = BannerManager.getInstance(mContext).getBannerView(new BannerViewListener() {
            @Override
            public void onRequestSuccess() {
                //
            }
            @Override
            public void onSwitchBanner() {
            }
            @Override
            public void onRequestFailed() {
            }
        });
        // 将广告条加入到布局中
        viewGroup.addView(bannerView);
    }


    private  void initSpot(){
        SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);
    }
    /**
     * 设置插屏广告
     */
    public void showSpot() {
        SpotManager.getInstance(mContext).showSpot(mContext, new SpotListener() {
            @Override
            public void onShowSuccess() {

            }
            @Override
            public void onShowFailed(int errorCode) {
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        break;
                    case ErrorCode.NON_AD:
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        break;
                }
            }
            @Override
            public void onSpotClosed() {
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
            }
        });
    }
}
