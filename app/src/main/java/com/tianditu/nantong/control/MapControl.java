package com.tianditu.nantong.control;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.easymap.android.maps.v3.EzMap;
import com.easymap.android.maps.v3.MapView;
import com.easymap.android.maps.v3.geometry.GeoPoint;
import com.easymap.android.maps.v3.layers.ezmap.EzMapVMLayer;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayer;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayerInfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * 地图控件
 * Created by chenjie on 2016/11/16.
 */
public class MapControl {
    private EzMap ezMap;
    private WMTSLayerInfo wmtslayerinfo;
    private WMTSLayer wmtstdt;
    private EzMapVMLayer vmlayer;

    public EzMap getEzMap() {
        return ezMap;
    }

    public void setEzMap(EzMap ezMap) {
        this.ezMap = ezMap;
    }

    public void initLayer(MapView mapView){
        ezMap = mapView.getMap();
        // 设置地图级别
        ezMap.zoomTo(14, false);
        // 设置地图中心点
        ezMap.centerAt(new GeoPoint(116.30489, 39.98107), false);
        wmtslayerinfo = new WMTSLayerInfo();
        wmtslayerinfo.setVersion("1.0.0");
        wmtslayerinfo.setFormat("tiles");
        wmtslayerinfo.setStyle("default");
        wmtslayerinfo.setLayerName("vec");
        wmtslayerinfo.setTileMatrixSet("c");
        wmtstdt = new WMTSLayer("http://t0.tianditu.com/vec_c/wmts", wmtslayerinfo, "tdt_vec.cache", "/mnt/sdcard/EzMap(1)/st");
        vmlayer = new EzMapVMLayer("bj.vmp", "/mnt/sdcard/EzMap(1)/sv");
        ezMap.setCompassVisible(false);
        ezMap.setZoomControlsVisible(false);
        ezMap.setWaterMarkerVisible(false);
        ezMap.setScaleViewVisible(false);
        ezMap.setMyLocationControlVisible(false);
        /**
         WMTSLayerInfo wmtslayerinfo1 = new WMTSLayerInfo();
         wmtslayerinfo1.setVersion("1.0.0");
         wmtslayerinfo1.setFormat("tiles");
         wmtslayerinfo1.setStyle("default");
         wmtslayerinfo1.setLayerName("img");
         wmtslayerinfo1.setTileMatrixSet("c");
         //wmtstdt1 = new WMTSLayer("http://t0.tianditu.com/img_c/wmts", wmtslayerinfo1, "tdt_img.cache", "/mnt/sdcard/EzMap(1)/st");

         WMTSLayerInfo wmtslayerinfo2 = new WMTSLayerInfo();
         wmtslayerinfo2.setVersion("1.0.0");
         wmtslayerinfo2.setFormat("tiles");
         wmtslayerinfo2.setStyle("default");
         wmtslayerinfo2.setLayerName("ter");
         wmtslayerinfo2.setTileMatrixSet("c");
         wmtstdt2 = new WMTSLayer("http://t0.tianditu.com/ter_c/wmts",
         wmtslayerinfo2, "tdt_ter.cache", "/mnt/sdcard/EzMap(1)/st");
         ezMap.addLayer(wmtstdt1);
         ezMap.addLayer(wmtstdt2);
         ezMap.addLayer(wmtstdt);
         ezMap.addLayer(vmlayer);
         */
        ezMap.addLayer(wmtstdt);
        ezMap.addLayer(vmlayer);
    }


}
