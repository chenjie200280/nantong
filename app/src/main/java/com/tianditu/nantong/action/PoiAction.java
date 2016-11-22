package com.tianditu.nantong.action;

import com.tianditu.nantong.service.PoiService;

import org.xutils.common.Callback;
/**
 * Created by chenjie on 2016/11/21.
 */
public class PoiAction {
    PoiService poiService = null;
    public PoiAction(){
        poiService = new PoiService();
    }
    public void getEsriGeometryBuffer(String distances,final Callback.CommonCallback callback){
        poiService.getEsriGeometryBuffer(distances, new Callback.CommonCallback() {
            @Override
            public void onSuccess(Object o) {
                System.out.print(o.toString());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {

            }
            @Override
            public void onCancelled(CancelledException e) {

            }
            @Override
            public void onFinished() {

            }
        });
    }
    /**
     * 查询poi数据
     * @param keyword
     * @param callback
     */
    public void getPoiByWord(String keyword,final Callback.CommonCallback callback){
        poiService.getPoiByWord(keyword,10, new Callback.CommonCallback() {
            @Override
            public void onSuccess(Object o) {

                callback.onSuccess(o);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {

            }
            @Override
            public void onCancelled(CancelledException e) {

            }
            @Override
            public void onFinished() {

            }
        });
    }
}
