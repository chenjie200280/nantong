package com.tianditu.nantong.control;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.easymap.android.maps.v3.EzMap;
import com.easymap.android.maps.v3.geometry.Envelope;
import com.easymap.android.maps.v3.geometry.GeoPoint;
import com.easymap.android.maps.v3.geometry.SpatialReference;
import com.easymap.android.maps.v3.graphics.BitmapDescriptor;
import com.easymap.android.maps.v3.graphics.BitmapDescriptorFactory;
import com.easymap.android.maps.v3.layers.GraphicsLayer;
import com.easymap.android.maps.v3.layers.MyLocationLayer;
import com.easymap.android.maps.v3.layers.ezmap.EzMapVMLayer;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayer;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayerInfo;
import com.tianditu.nantong.model.LayerType;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 地图控件
 * Created by chenjie on 2016/11/16.
 */
public class MapControl {
    //地图
    private EzMap ezMap;
    //矢量图层方案
    private Map<String, Object> vectorLayers;
    //影像图层方案
    private Map<String, Object> imageLayers;


    private GraphicsLayer graphicslayer;

    private MyLocationLayer myLocationLayer;

    //当前显示方案
    private String layerType = LayerType.VEC;
    public void initEzMap(EzMap ezMap) {
        if(this.ezMap!=null){
            return;
        }
        this.ezMap = ezMap;
        // 设置地图级别
        ezMap.zoomTo(12, false);
        // 设置地图中心点
        ezMap.centerAt(new GeoPoint(120.81, 31.99), false);
        //初始化图层
        initLayers();

        //加载矢量图层
        ezMap.setCompassVisible(false);
        ezMap.setZoomControlsVisible(false);
        ezMap.setWaterMarkerVisible(false);
        ezMap.setScaleViewVisible(false);
        ezMap.setTiltableByGesture(false);
        ezMap.setMyLocationControlVisible(false);
    }
    /**
     * 初始化矢量方案
     */
    private void initLayers() {
        if(vectorLayers==null){
            vectorLayers = new HashMap<>();
        }else{
            vectorLayers.clear();
        }
        if(imageLayers==null){
            imageLayers = new HashMap<>();
        }else{
            imageLayers.clear();
        }

        //矢量注记
        WMTSLayerInfo cvaWMTSLayerInfo;
        WMTSLayer cvaWMTSLayer;
        cvaWMTSLayerInfo = new WMTSLayerInfo();
        cvaWMTSLayerInfo.setVersion("1.0.0");
        cvaWMTSLayerInfo.setFormat("tiles");
        cvaWMTSLayerInfo.setStyle("default");
        cvaWMTSLayerInfo.setLayerName("cva");
        cvaWMTSLayerInfo.setTileMatrixSet("c");
        cvaWMTSLayer = new WMTSLayer("http://t0.tianditu.com/cva_c/wmts", cvaWMTSLayerInfo, "tdt_cva.cache", "/mnt/sdcard/EzMap(2)/st");
        vectorLayers.put("tdt_cva",cvaWMTSLayer);


        //矢量底图
        WMTSLayerInfo vecWMTSLayerInfo;
        WMTSLayer vecWMTSLayer;
        vecWMTSLayerInfo = new WMTSLayerInfo();
        vecWMTSLayerInfo.setVersion("1.0.0");
        vecWMTSLayerInfo.setFormat("tiles");
        vecWMTSLayerInfo.setStyle("default");
        vecWMTSLayerInfo.setLayerName("vec");
        vecWMTSLayerInfo.setTileMatrixSet("c");
        vecWMTSLayer = new WMTSLayer("http://t0.tianditu.com/vec_c/wmts", vecWMTSLayerInfo, "tdt_vec.cache", "/mnt/sdcard/EzMap(1)/st");
        vectorLayers.put("tdt_vec",vecWMTSLayer);

        ezMap.addLayer(vecWMTSLayer);
        ezMap.addLayer(cvaWMTSLayer);

        //影像底图
        WMTSLayerInfo imgWMTSLayerInfo = new WMTSLayerInfo();
        WMTSLayer imgWMTSLayer;
        imgWMTSLayerInfo.setVersion("1.0.0");
        imgWMTSLayerInfo.setFormat("tiles");
        imgWMTSLayerInfo.setStyle("default");
        imgWMTSLayerInfo.setLayerName("img");
        imgWMTSLayerInfo.setTileMatrixSet("c");
        imgWMTSLayer = new WMTSLayer("http://t0.tianditu.com/img_c/wmts", imgWMTSLayerInfo, "tdt_img.cache", "/mnt/sdcard/EzMap(3)/st");
        imgWMTSLayer.setVisible(false);
        imageLayers.put("tdt_img",imgWMTSLayer);
        //影像注记
        WMTSLayerInfo ciaWMTSLayerInfo = new WMTSLayerInfo();
        WMTSLayer ciaWMTSLayer;
        ciaWMTSLayerInfo.setVersion("1.0.0");
        ciaWMTSLayerInfo.setFormat("tiles");
        ciaWMTSLayerInfo.setStyle("default");
        ciaWMTSLayerInfo.setLayerName("cia");
        ciaWMTSLayerInfo.setTileMatrixSet("c");
        ciaWMTSLayer = new WMTSLayer("http://t0.tianditu.com/cia_c/wmts", ciaWMTSLayerInfo, "tdt_cia.cache", "/mnt/sdcard/EzMap(4)/st");
        ciaWMTSLayer.setVisible(false);
        imageLayers.put("tdt_cia",ciaWMTSLayer);

        Envelope envelope = new Envelope();
        envelope.setxMax(180);
        envelope.setxMin(-180);
        envelope.setyMax(90);
        envelope.setyMin(-90);
        graphicslayer = new GraphicsLayer(SpatialReference.create(4326), envelope);

        ezMap.addLayer(imgWMTSLayer);
        ezMap.addLayer(ciaWMTSLayer);

        ezMap.addLayer(graphicslayer);

        myLocationLayer = new MyLocationLayer();
        ezMap.addLayer(myLocationLayer);


        Location location = new Location("1");
        location.setLongitude(120.81);
        location.setLatitude(31.99);

        double longtitude = location.getLongitude();
        double latitude = location.getLatitude();
        /*
        BitmapDescriptor iconloaciton = null;
        try {
            iconloaciton = new BitmapDescriptorFactory().fromAsset("navi_car_locked.png");
        }catch (Exception e){

        }
        myLocationLayer.setIcon(iconloaciton);
        */
        myLocationLayer.setLocation(location);
        DecimalFormat df = new DecimalFormat("#0.000000");
        myLocationLayer.setFillColor(Color.BLACK);
        myLocationLayer.setStrokeWidth(5);
        myLocationLayer.setMode(MyLocationLayer.LocationMode.FOLLOWING);
        ezMap.centerAt(new GeoPoint(longtitude, latitude), false);
        myLocationLayer.refresh();

    }

    /**
     *
     * @return
     */
    public void hideAllLayer(){
        hideVecLayer();
        hideImageLayer();
    }

    /**
     * 隐藏矢量方案
     */
    public void hideVecLayer(){
         for(Object vecLayer:vectorLayers.values()){
             WMTSLayer layer = (WMTSLayer)vecLayer;
             layer.setVisible(false);
         }
    }
    //显示矢量方案
    public void displayVecLayer(){
         for(Object vecLayer:vectorLayers.values()){
             WMTSLayer layer = (WMTSLayer)vecLayer;
             layer.setVisible(true);
         }
         this.layerType = LayerType.VEC;
    }

    //隐藏影像方案
    public void hideImageLayer(){
         for(Object imgLayer:imageLayers.values()){
             WMTSLayer layer = (WMTSLayer)imgLayer;
             layer.setVisible(false);
         }
    }
    //显示影像方案
    public void displayImageLayer(){
         for(Object imgLayer:imageLayers.values()){
             WMTSLayer layer = (WMTSLayer)imgLayer;
             layer.setVisible(true);
         }
         this.layerType = LayerType.IMAGE;
    }


    public GraphicsLayer getGraphicslayer() {
        return graphicslayer;
    }

    public void setGraphicslayer(GraphicsLayer graphicslayer) {
        this.graphicslayer = graphicslayer;
    }
    public EzMap getEzMap() {
        return ezMap;
    }

    public void setEzMap(EzMap ezMap) {
        this.ezMap = ezMap;
    }

    public Map<String, Object> getVectorLayers() {
        return vectorLayers;
    }

    public void setVectorLayers(Map<String, Object> vectorLayers) {
        this.vectorLayers = vectorLayers;
    }

    public Map<String, Object> getImageLayers() {
        return imageLayers;
    }

    public void setImageLayers(Map<String, Object> imageLayers) {
        this.imageLayers = imageLayers;
    }

    public String getLayerType() {
        return layerType;
    }

    public void setLayerType(String layerType) {
        this.layerType = layerType;
    }
}
