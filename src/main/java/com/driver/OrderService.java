package com.driver;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class OrderService {
    @Autowired
    private OrderRepo Odrepo=new OrderRepo();

    /* <-----------Post Method-------------> */
    public void addOrder(Order o)
    {
        Odrepo.addOrder(o);
    }
    public void addDp(DeliveryPartner dp)
    {
        Odrepo.addDp(dp);
    }

    /* <----------- Put Method ------------> */
    public void addDpandOdPair(String OId,String DpId)
    {
        Odrepo.addDpandOdPair(OId,DpId);
    }

    /* <----------- Get Method ------------> */
    public Order getOrderById(String OId)
    {
        return Odrepo.getOrderById(OId);
    }
    public DeliveryPartner getPartnerById(String DpId)
    {
        return Odrepo.getPartnerById(DpId);
    }
    public Integer getOdCntByPartnerId(String  DpId)
    {
        return Odrepo.getOdCntByPartnerId(DpId);
    }
    public List<String> getAllOdByPartnerId(String DpId)
    {
        return Odrepo.getAllOdByPartnerId(DpId);
    }
    public List<String> getAllOrder()
    {
        return Odrepo.getAllOrder();
    }
    public Integer getUnsignedOrder()
    {
        return Odrepo.getUnsignedOrer();
    }
    public Integer getLeftOnes(String Time,String DpId)
    {
        return Odrepo.getLeftOnes(Time,DpId);
    }
    public String getLastDelivered(String DpId)
    {
        Integer TimeStamp=Odrepo.getLastDelivered(DpId);
        if(TimeStamp==null)
        {
            return null;
        }
        int h = TimeStamp/60;
        int m = TimeStamp- (h*60);

        String hrs = String.format("%02d", h);
        String mnts = String.format("%02d", m);

        return (hrs + ":" + mnts);
    }

    /* <----------- DELETE METHOD ---------> */

    public void delPartnerById(String DpId)
    {
        Odrepo.delPartnerById(DpId);
    }
    public void delOrderById(String Oid)
    {
        Odrepo.getOrderById(Oid);
    }












}
