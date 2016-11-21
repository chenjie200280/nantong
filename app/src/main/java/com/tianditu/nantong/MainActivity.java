package com.tianditu.nantong;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easymap.android.maps.v3.EzMap;
import com.easymap.android.maps.v3.MapView;
import com.easymap.android.maps.v3.geometry.GeoPoint;
import com.tianditu.nantong.action.PoiAction;
import com.tianditu.nantong.control.MapControl;
import com.tianditu.nantong.model.LayerType;
import com.tianditu.nantong.service.PoiService;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.IOException;
import java.io.InputStream;


@ContentView(R.layout.main_activity)
public class MainActivity extends Activity implements EzMap.OnStatusChangeListener{
    //地图控件
    @ViewInject(R.id.mapview)
    MapView mapView;
    //图层切换控件
    @ViewInject(R.id.layer_switch_imgbtn)
    ImageButton layer_swtich_imgbtn;
    //图层切换选项视图
    @ViewInject(R.id.layer_switch_view)
    LinearLayout layer_switch_view;
    //图层切换-矢量选项
    @ViewInject(R.id.vec_layer_iv)
    ImageView vec_layer_iv;
    //图层切换-影像选项
    @ViewInject(R.id.img_layer_iv)
    ImageView img_layer_lv;
    //矢量背景
    @ViewInject(R.id.index_background)
    View index_background;
    //关键字搜索  按钮
    @ViewInject(R.id.search_btn)
    Button search_btn;


    @ViewInject(R.id.layer_switch_close_ib)
    ImageButton layer_switch_close_ib;
    @ViewInject(R.id.map_view_rl)
    RelativeLayout map_view_rl;
    @ViewInject(R.id.map_top_tv)
    RelativeLayout map_top_rl;
    @ViewInject(R.id.poi_serach_item_ll)
    LinearLayout poi_serach_item_ll;
    //搜索界面
    @ViewInject(R.id.poi_serach_item_back)
    ImageButton poi_serach_item_back;

    @ViewInject(R.id.poi_serach_item_et)
    EditText poi_serach_item_et;


    //地图控件
    private MapControl mapControl;

    private PoiAction poiAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initMapView(savedInstanceState);
        poiAction = new PoiAction();

    }
    @Override
    public void onStatusChanged(STATUS status) {
        mapControl = new MapControl();
        mapControl.initEzMap(mapView.getMap());
    }

    //弹出图层切换界面
    @Event(value={R.id.layer_switch_imgbtn})
    private void onLayerSwitchImgBtnClick(View view){
        layer_switch_view.setVisibility(View.VISIBLE);
        index_background.setVisibility(View.VISIBLE);
        layer_swtich_imgbtn.setVisibility(View.GONE);
    }


    //矢量影像切换
    @Event(value = {R.id.vec_layer_iv,R.id.img_layer_iv})
    private void onVecLayerSwitchClick(View view){
        switch (view.getId()){
            case R.id.vec_layer_iv:
                if(mapControl.getLayerType().equals(LayerType.VEC)){
                    return;
                }
                mapControl.displayVecLayer();
                mapControl.hideImageLayer();
                img_layer_lv.setImageResource(R.mipmap.maplayer_manager_sate);
                vec_layer_iv.setImageResource(R.mipmap.maplayer_manager_2d_hl);
                break;
            case R.id.img_layer_iv:
                if(mapControl.getLayerType().equals(LayerType.IMAGE)){
                    return;
                }
                mapControl.displayImageLayer();
                mapControl.hideVecLayer();
                img_layer_lv.setImageResource(R.mipmap.maplayer_manager_sate_hl);
                vec_layer_iv.setImageResource(R.mipmap.maplayer_manager_2d);
                break;
        }
        float zoom = mapControl.getEzMap().getZoom();
        GeoPoint geoPoint = mapControl.getEzMap().getCenter();
        mapControl.getEzMap().zoomTo(zoom, false);
        mapControl.getEzMap().centerAt(geoPoint, false);
        mapControl.getEzMap().refreshMap();
    }

    //poi关键字搜索
    @Event(value = {R.id.search_btn})
    private void onSearchBtnClick(View view){
        poiAction.getPoiByWord(poi_serach_item_et.getText().toString(), new Callback.CommonCallback() {
            @Override
            public void onSuccess(Object o) {
                Log.i("test","1");
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

    //矢量影像背景
    @Event(value = {R.id.index_background,R.id.layer_switch_close_ib})
    private void onIndexBackgroudClick(View view){
        //隐藏图层切换界面
        layer_switch_view.setVisibility(View.GONE);
        //隐藏图层背景
        index_background.setVisibility(View.GONE);
        layer_swtich_imgbtn.setVisibility(View.VISIBLE);
    }


    @Event(value = {R.id.map_top_tv})
    private  void onMapTopLlClick(View view){
        InputMethodManager inputMethodManager=(InputMethodManager) poi_serach_item_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //map_top_ll
        poi_serach_item_ll.setVisibility(View.VISIBLE);
        map_view_rl.setVisibility(View.GONE);
        poi_serach_item_et.requestFocus();

        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
    @Event(value = {R.id.poi_serach_item_back})
    private  void onPoiSearchItemBackClick(View view){
        poi_serach_item_ll.setVisibility(View.GONE);
        map_view_rl.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化地图
     * @param savedInstanceState
     */
    void initMapView(Bundle savedInstanceState){
        if (mapView != null) {
            // 注册地图准备就绪监听
            mapView.setOnStatusChangeListener(this);
            InputStream inStream = null;
            try {
                inStream = getResources().getAssets().open("EzServiceClient4Android.lic");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 初始化许可方法
            mapView.initLicenseAsDevelopement(inStream);
            mapView.onCreate(savedInstanceState);
        }
    }
}
