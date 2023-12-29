package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class OrderRepo {
    private HashMap<String,Order>Omap;
    private HashMap<String,DeliveryPartner>Dpmap;
    private HashMap<String,List<String>>ODpmap;
    HashSet<String>Uoset;
    HashMap<String, String>Somap;
    OrderRepo(){
        this.Omap=new HashMap<>();
        this.Dpmap=new HashMap<>();
        this.ODpmap=new HashMap<>();
        this.Uoset=new HashSet<>();
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

    public HashSet<String> getUoset() {
        return Uoset;
    }

    public void setUoset(HashSet<String> uoset) {
        Uoset = uoset;
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
        if(o.getId().length()!=0)
        {
            Omap.put(o.getId(),o);
            Uoset.add(o.getId());
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
        if(Omap.containsKey(Oid)&&Uoset.contains(Oid) && Dpmap.containsKey(DpId))
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
        else {
            return ;
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
                ans+=1;
            }
        }
        return ans;
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
        if(ODpmap.containsKey(DpId))
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
        else
        {
            return ;
        }
    }
    public void delOrderById(String OId)
    {
        Order o=Omap.get(OId);
        if(Omap.containsKey(OId))
        {
            if(Somap.containsKey(OId))
            {
                String DpId=Somap.get(OId);
                Somap.remove(OId);
                List<String>oList=ODpmap.get(DpId);
                oList.remove(OId);
                ODpmap.put(DpId,oList);
                Dpmap.get(DpId).setNumberOfOrders(Dpmap.get(DpId).getNumberOfOrders()-1);
            }
            else {
                Uoset.remove(OId);
            }
            Omap.remove(OId);
        }
        else
        {
            return ;
        }
    }














}
