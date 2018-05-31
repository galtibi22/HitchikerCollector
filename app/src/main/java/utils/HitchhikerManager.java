package utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import pojo.Direction;
import pojo.Leg;
import pojo.Step;

public class HitchhikerManager {


    public String getHitchhikers(LatLng startingPoint, LatLng destinationPoint) {
        HttpClient httpClient=new HttpClient();
        String response=httpClient.get("https://maps.googleapis.com/maps/api/directions/json?&origin=" + LocationUtil.latLngToString(startingPoint) + "&destination=" + LocationUtil.latLngToString(destinationPoint) + "&travelmode=driving&key=AIzaSyAPBr94Xd4cFHqdBwvrHEcr-QyJPHdVHGU");
        Gson g = new Gson();
        Direction direction = g.fromJson(response, Direction.class);
        List<List<LatLng>> polygons=findPolygons(direction);
        List<LatLng> hitchhikersLocation=getHitchhikers(destinationPoint);
        List<LatLng> hitchhikersCollection=new ArrayList<>();
        for (LatLng hitchhiker:hitchhikersLocation){
            for (List<LatLng> polygon:polygons){
                if ( PolyUtil.containsLocation(hitchhiker,polygon,true)){
                    hitchhikersCollection.add(hitchhiker);
                    break;
                }
            }
        }

       // String collectPoint =LocationUtil.addressToLatLngStr("Beit Yanai,Israel") + "%7C" + LocationUtil.addressToLatLngStr("Netanya,Israel");
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
        for (LatLng latLng:points2){
            List<LatLng> polygon=new ArrayList<>();
            polygon.add(new LatLng(latLng.latitude+0.005,latLng.longitude));
            polygon.add(new LatLng(latLng.latitude-0.005,latLng.longitude));
            polygon.add(new LatLng(latLng.latitude,latLng.longitude+0.005));
            polygon.add(new LatLng(latLng.latitude,latLng.longitude-0.005));
            polygons.add(polygon);
        }
      /*  for (Leg leg:direction.getRoutes().get(0).getLegs())
            for (Step step:leg.getSteps()){
                List<LatLng> polygonStart=new ArrayList<>();
                List<LatLng> polygonEnd=new ArrayList<>();
                LatLng startPoint=new LatLng(step.getStartLocation().getLat(),step.getStartLocation().getLng());
                LatLng endPoint=new LatLng(step.getEndLocation().getLat(),step.getEndLocation().getLng());
                polygonStart.add(new LatLng(startPoint.latitude+0.005,startPoint.longitude));
                polygonStart.add(new LatLng(startPoint.latitude-0.005,startPoint.longitude));
                polygonStart.add(new LatLng(startPoint.latitude,startPoint.longitude+0.005));
                polygonStart.add(new LatLng(startPoint.latitude,startPoint.longitude-0.005));

                polygonEnd.add(new LatLng(endPoint.latitude+0.005,endPoint.longitude));
                polygonEnd.add(new LatLng(endPoint.latitude-0.005,endPoint.longitude));
                polygonEnd.add(new LatLng(endPoint.latitude,endPoint.longitude+0.005));
                polygonEnd.add(new LatLng(endPoint.latitude,endPoint.longitude-0.005));
                polygons.add(polygonStart);
                polygons.add(polygonEnd);
            }*/
        return polygons;

    }

    private ArrayList<LatLng> getHitchhikers(LatLng destination){
        ArrayList<LatLng> hitchhikersLocation=new ArrayList<>();
        hitchhikersLocation.add(LocationUtil.addressToLatLng("צומת פרדסיה"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("Beit Yanai,Israel"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("Netanya,Israel"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("Berl Katsnelson 4, Hertsliya"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("Berl Katsnelson 38, Hertsliya"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("Yerushalaim Rd 7, Hertsliya"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("המשכית 3 הרצליה"));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("Emma Tauber Fridman 9 Hertsliya"));
        hitchhikersLocation.add(new LatLng(32.178659, 34.852294));
        hitchhikersLocation.add(LocationUtil.addressToLatLng("בן גוריון הרצליה"));

        return  hitchhikersLocation;
    }
}
