package com.marketplace.adminservice.service;


import org.springframework.stereotype.Service;


import com.marketplace.adminservice.client.*;
import com.marketplace.adminservice.dto.AdminMetricsResponse;


@Service
public class AdminService {


    private final SellerClient sellerClient;
    private final UserClient userClient;
    private final OrderClient orderClient;


    public AdminService(SellerClient sellerClient,
                        UserClient userClient,
                        OrderClient orderClient) {
        this.sellerClient = sellerClient;
        this.userClient = userClient;
        this.orderClient = orderClient;
    }


    public void approveSeller(Long sellerId) {
        sellerClient.approveSeller(sellerId);
    }


    public void blockSeller(Long sellerId) {
        sellerClient.blockSeller(sellerId);
    }


    public void blockUser(String username) {
        userClient.blockUser(username);
    }


    public AdminMetricsResponse getMetrics() {
        AdminMetricsResponse res = new AdminMetricsResponse();
        res.setTotalOrders(orderClient.totalOrders());
        return res;
    }
}