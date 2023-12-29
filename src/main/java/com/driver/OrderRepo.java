package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class OrderRepo {
    private HashMap<String,Order>Omap;
    private HashMap<String,DeliveryPartner>Dpmap;
    private HashMap<String,List<String>>ODpmap;
    HashMap<String, String>Somap;
    OrderRepo(){
        this.Omap=new HashMap<>();
        this.Dpmap=new HashMap<>();
        this.ODpmap=new HashMap<>();
        this.Somap=new HashMap<>();
    }
    public HashMap<String, Order> getOmap() {
        return Omap;
    }

    public void setOmap(HashMap<String, Order> omap) {
        Omap = omap;
    }

    public HashMap<String, DeliveryPartner> getDpmap() {
        return Dpmap;
    }

    public void setDpmap(HashMap<String, DeliveryPartner> dpmap) {
        Dpmap = dpmap;
    }

    public HashMap<String, List<String>> getODpmap() {
        return ODpmap;
    }

    public void setODpmap(HashMap<String, List<String>> ODpmap) {
        this.ODpmap = ODpmap;
    }


    public HashMap<String, String> getSomap() {
        return Somap;
    }

    public void setSomap(HashMap<String, String> somap) {
        Somap = somap;
    }

    /* <------------- Post Metthod ---------------> */
    public void addOrder(Order o)
    {
        if(o.getId().length()!=0 && o.getDeliveryTime()!=0)
        {
            Omap.put(o.getId(),o);
        }
    }
    public void addDp(DeliveryPartner dp)
    {
        if(dp.getId().length()!=0)
        {
            Dpmap.put(dp.getId(),dp);
        }
    }

    /* <------------- Put Method ---------------> */
    public void addDpandOdPair(String Oid,String DpId)
    {
        if(!Omap.containsKey(Oid) || Dpmap.containsKey(DpId))
        {
            return ;
        }
        List<String>OList=ODpmap.getOrDefault(DpId,new ArrayList<>());
        OList.add(Oid);
        ODpmap.put(DpId,OList);
        Somap.put(Oid,DpId);
        Dpmap.get(DpId).setNumberOfOrders(ODpmap.get(DpId).size());
    }

    /* <------------ Get Method ------------> */
    public Order getOrderById(String OId)
    {
        return Omap.getOrDefault(OId,null);
    }
    public DeliveryPartner getPartnerById(String DpId)
    {
        return Dpmap.getOrDefault(DpId,null);
    }
    public int getOdCntByPartnerId(String DpId)
    {

        if(Dpmap.containsKey(DpId))
        {
            return Dpmap.get(DpId).getNumberOfOrders();
        }
        else
        {
            return 0;
        }
    }
    public List<String> getAllOdByPartnerId(String  DpId)
    {
        if(ODpmap.containsKey(DpId))
        {
            return new ArrayList<>(ODpmap.get(DpId));
        }
        return null;
    }
    public List<String> getAllOrder()
    {
        if(Omap.size()>0)
        {
            List<String> orders = new ArrayList(Omap.keySet());
            return orders;
        }
        return null;
    }
    public Integer getUnsignedOrer()
    {

        return Omap.size()-Somap.size();
    }
    public Integer getLeftOnes(String Time,String DpId)
    {
        List<String>OList=ODpmap.getOrDefault(DpId,null);
        if(Time.length()==0 || OList==null)
        {
            return 0;
        }
        Integer ans=0;
        int hr=Integer.parseInt(Time.substring(0,2));
        int mm=Integer.parseInt(Time.substring(3));
        int TimeStamp=hr*60+mm;
        for(String o:OList)
        {
            if(Omap.get(o).getDeliveryTime()>TimeStamp)
            {
                ans++;
            }
        }
        return ans;
    }
    public Integer getLastDelivered(String DpId)
    {
        if(ODpmap.containsKey(DpId))
        {
            Integer maxi=Integer.MIN_VALUE;
            List<String>OList=ODpmap.get(DpId);
            for(String o:OList)
            {
                maxi=Math.max(maxi,Omap.get(o).getDeliveryTime());
            }
            return maxi;
        }
        return  null;
    }

    /* <---------- DELETE METHOD -------------> */
    public void delPartnerById(String DpId)
    {
        if(Dpmap.containsKey(DpId))
        {
            Dpmap.remove(DpId);
        }
        if(ODpmap.containsKey(DpId))
        {
            List<String>OList=ODpmap.get(DpId);
            for(String o:OList)
            {
                Somap.remove(o);
            }
            ODpmap.remove(DpId);
        }
    }
    public void delOrderById(String OId)
    {
        Order o=Omap.get(OId);
        if(Omap.containsKey(OId))
        {
            Omap.remove(OId);
        }
        if(Somap.containsKey(OId))
        {
            String DpId=Somap.get(OId);
            Somap.remove(OId);
            List<String>oList=ODpmap.get(DpId);
            oList.remove(OId);
            Dpmap.get(DpId).setNumberOfOrders(ODpmap.get(DpId).size());
        }
    }














}
