package com.tianditu.nantong.service;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * POI 搜索服务
 * Created by chenjie on 2016/11/20.
 */
public class PoiService {
    //http://218.2.102.194/ntpoiquery/PoiItems/query/?where=domainname%2Blike%2B%27%25%E5%AD%A6%E6%A0%A1%25%27

    /**http://www.mapha.com.cn/ArcGIS/rest/services/Geometry/GeometryServer/buffer?
     * inSR=4490
     * bufferSR=4490
     * geometries=%7B%22geometries%22%3A%5B%7B%22x%22%3A120%2E968429049%2C%22y%22%3A31%2E9525861020001%7D%5D%2C%22
     * geometryType%22%3A%22
     * esriGeometryPoint%22%7D
     * f=json
     * unit=9001
     * unionResults=false
     * distances=500
    **/
    String bufferUrl = "http://www.mapha.com.cn/ArcGIS/rest/services/Geometry/GeometryServer/buffer";
    String poiUrl = "http://218.2.102.194/ntpoiquery/PoiItems/query";
    /**
     * 获取周围边面积
     */
    public void getEsriGeometryBuffer(String distances,final Callback.CommonCallback callback){
        RequestParams params = new RequestParams(bufferUrl);
        params.addParameter("inSR",4490);
        params.addParameter("bufferSR",4490);
        params.addParameter("geometries","{\"geometries\":[{\"x\":120.967476339,\"y\":31.9498005350001}],\"geometryType\":\"esriGeometryPoint\"}");
        params.addParameter("f","json");
        params.addParameter("unit",9001);
        params.addParameter("unionResults",false);
        params.addParameter("distances",50);
        Callback.Cancelable cancelable = x.http().get(params,new Callback.CacheCallback<String>(){
            @Override
            public void onSuccess(String s) {
                callback.onSuccess(s);
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
            @Override
            public boolean onCache(String s) {
                return false;
            }
        });
    }
    /**
     * 关键字搜索
     */
    public void getPoiByWord(String keyword,int count,final Callback.CommonCallback callback){
        RequestParams params = new RequestParams(poiUrl);
        params.addParameter("where","domainname like '%"+keyword+"%'");
        params.addParameter("count",10);
        Callback.Cancelable cancelable = x.http().get(params,new Callback.CacheCallback<String>(){
            @Override
            public void onSuccess(String s) {
                callback.onSuccess(s);
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
            @Override
            public boolean onCache(String s) {
                return false;
            }
        });
    }

}
