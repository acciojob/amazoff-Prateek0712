package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class OrderRepo {
    private HashMap<String,Order>Omap=new HashMap<>();
    private HashMap<String,DeliveryPartner>Dpmap=new HashMap<>();
    private HashMap<String,List<String>>ODpmap=new HashMap<>();
    HashSet<String>Uoset=new HashSet<>();
    HashMap<String, String>Somap=new HashMap<>();

    /* <------------- Post Metthod ---------------> */
    public void addOrder(Order o)
    {
        Omap.put(o.getId(),o);
        Uoset.add(o.getId());
    }
    public void addDp(DeliveryPartner dp)
    {
        Dpmap.put(dp.getId(),dp);
    }

    /* <------------- Put Method ---------------> */
    public void addDpandOdPair(String Oid,String DpId)
    {
        if(Uoset.contains(Oid))
        {
            List<String>OList=ODpmap.getOrDefault(DpId,new ArrayList<>());
            OList.add(Oid);
            ODpmap.put(DpId,OList);
            Uoset.remove(Oid);
            Somap.put(Oid,DpId);
            DeliveryPartner Dp=Dpmap.get(DpId);
            Dp.setNumberOfOrders(Dp.getNumberOfOrders()+1);
            Dpmap.put(DpId,Dp);
        }
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
    public Integer getOdCntByPartnerId(String DpId)
    {
        return Dpmap.get(DpId).getNumberOfOrders();
    }
    public List<String> getAllOdByPartnerId(String  DpId)
    {
        return new ArrayList<>(ODpmap.getOrDefault(DpId,null));
    }
    public List<String> getAllOrder()
    {
        List<String>OList=new ArrayList<>();
        for(String key:Omap.keySet())
        {
            OList.add(key);
        }
        return  OList;
    }
    public Integer getUnsignedOrer()
    {
        return Uoset.size();
    }
    public Integer getLeftOnes(String Time,String DpId)
    {
        List<String>OList=ODpmap.get(DpId);
        Integer ans=0;
        int hr=Integer.parseInt(Time.substring(0,2));
        int mm=Integer.parseInt(Time.substring(3));
        int TimeStamp=hr*60+mm;
        for(String o:OList)
        {
            if(Omap.get(o).getDeliveryTime()==TimeStamp)
            {
                ans+=1;
            }
        }
        return ans-1;
    }
    public Integer getLastDelivered(String DpId)
    {
        Integer maxi=Integer.MIN_VALUE;
        List<String>OList=ODpmap.get(DpId);
        for(String o:OList)
        {
            maxi=Math.max(maxi,Omap.get(o).getDeliveryTime());
        }
        return maxi;
    }

    /* <---------- DELETE METHOD -------------> */
    public void delPartnerById(String DpId)
    {
        List<String>OList=ODpmap.get(DpId);
        for(String o:OList)
        {
            Somap.remove(o);
            Uoset.add(o);
        }
        ODpmap.remove(DpId);
        Dpmap.remove(DpId);
    }
    public void delOrderById(String OId)
    {
        Order o=Omap.get(OId);
        if(Somap.containsKey(OId))
        {
            String DpId=Somap.get(OId);
            Somap.remove(OId);
            List<String>oList=ODpmap.get(DpId);
            oList.remove(OId);
            ODpmap.put(DpId,oList);
        }
        else {
            Uoset.remove(OId);
        }
        Omap.remove(OId);
    }














}
