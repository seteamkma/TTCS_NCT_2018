package com.laptopnct.dichvunhadat.Control;



import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.laptopnct.dichvunhadat.Model.DownloadPolyLine;
import com.laptopnct.dichvunhadat.Model.ParserPolyline;

import java.util.List;
import java.util.concurrent.ExecutionException;



public class DanDuongToiCongTyController {
    ParserPolyline parserPolyline;
    DownloadPolyLine downloadPolyLine;

    public DanDuongToiCongTyController(){

    }

    public void HienThiDanDuongToiQuanAn(GoogleMap googleMap,String duongdan){
        parserPolyline = new ParserPolyline();
        downloadPolyLine = new DownloadPolyLine();
        downloadPolyLine.execute(duongdan);

        try {
            String dataJSON = downloadPolyLine.get();
            List<LatLng> latLngList = parserPolyline.LayDanhSachToaDo(dataJSON);

            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng toado : latLngList){
                polylineOptions.add(toado);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
