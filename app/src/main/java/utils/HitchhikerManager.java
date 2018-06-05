package utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import pojo.Direction;
import pojo.HichhikerWrapper;
import pojo.HitchHiker;
import pojo.Leg;
import pojo.Step;

public class HitchhikerManager {

    public String getHitchhikers(final LatLng startingPoint, final LatLng destinationPoint) {
        final String[] response = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    HttpClient httpClient=new HttpClient();
                    response[0] =httpClient.get("https://maps.googleapis.com/maps/api/directions/json?&origin=" + LocationUtil.latLngToString(startingPoint) + "&destination=" + LocationUtil.latLngToString(destinationPoint) + "&travelmode=driving&key=AIzaSyAPBr94Xd4cFHqdBwvrHEcr-QyJPHdVHGU");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Direction direction = g.fromJson(response[0], Direction.class);
        List<List<LatLng>> polygons=findPolygons(direction);
        List<HitchHiker> hitchhikersLocation=getHitchhikers(destinationPoint);
        List<LatLng> hitchhikersCollection=new ArrayList<>();
        for (HitchHiker hitchhiker:hitchhikersLocation){
            for (List<LatLng> polygon:polygons){
                if ( PolyUtil.containsLocation(hitchhiker.getStarting_point(),polygon,true)){
                    hitchhikersCollection.add(hitchhiker.getStarting_point());
                    break;
                }
            }
        }
        return hitchhikersCollectionToApiString(hitchhikersCollection);
    }

    private String hitchhikersCollectionToApiString(List<LatLng> hitchhikers) {
        String hitchhikersStr="";
        for (LatLng hitchhiker:hitchhikers){
            hitchhikersStr+=LocationUtil.latLngToString(hitchhiker)+"%7C";
        }
        if (hitchhikersStr.length()>0)
            hitchhikersStr=hitchhikersStr.substring(0,hitchhikersStr.length()-3);
        return  hitchhikersStr;
    }

    public  List<List<LatLng>> findPolygons(Direction direction){
        List<List<LatLng>> polygons=new ArrayList<>();
        List<LatLng> points=PolyUtil.decode(direction.getRoutes().get(0).getOverviewPolyline().getPoints());
        List<LatLng> points2=new ArrayList<>();
        for (Leg leg:direction.getRoutes().get(0).getLegs())
            for (Step step:leg.getSteps()) {
                points2.addAll(PolyUtil.decode(step.getPolyline().getPoints()));
            }
        for (LatLng latLng:points2)
            polygons.add(initPolygon(latLng));
        return polygons;

    }

    private List<HitchHiker> getHitchhikers(LatLng destination){
        final String[] response = new String[1];
       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {*/
                    HttpClient httpClient=new HttpClient();
                    response[0] =httpClient.get("https://hitchikercollector.firebaseio.com/.json");
                //} catch (Exception e) {
                  //  e.printStackTrace();
                //}
            //}
        //});
        //thread.start();
        //try {
          //  thread.join();
        //} catch (InterruptedException e) {
          //  e.printStackTrace();
        //}
        Gson g = new Gson();
        List<HitchHiker> hitchHikerList =g.fromJson("{\"hitchHikers\":"+response[0]+"}", HichhikerWrapper.class).getHitchHikers();;
        List<HitchHiker> hitchhikersLocation=new ArrayList<>();
        List<LatLng> poly=initPolygon(destination);
        for (int i=1;i<hitchHikerList.size();i++){
            if (PolyUtil.containsLocation(hitchHikerList.get(i).getDestination_point(),poly,true))
                hitchhikersLocation.add(hitchHikerList.get(i));
        }

        return  hitchhikersLocation;
    }

    private  List<LatLng> initPolygon(LatLng latLng){
        List<LatLng> polygon=new ArrayList<>();
        polygon.add(new LatLng(latLng.latitude+0.005,latLng.longitude));
        polygon.add(new LatLng(latLng.latitude-0.005,latLng.longitude));
        polygon.add(new LatLng(latLng.latitude,latLng.longitude+0.005));
        polygon.add(new LatLng(latLng.latitude,latLng.longitude-0.005));
        return polygon;
    }
}
