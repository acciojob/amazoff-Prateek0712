package com.driver;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class OrderService {
    @Autowired
    private OrderRepo Odrepo;

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
        String time=TimeStamp/60+":"+TimeStamp%60;
        return time;
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
