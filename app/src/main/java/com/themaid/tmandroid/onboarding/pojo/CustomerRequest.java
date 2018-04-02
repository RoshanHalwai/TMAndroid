package com.themaid.tmandroid.onboarding.pojo;

import java.io.Serializable;

/**
 * TheMaid
 * Created by Roshan Halwai on 3/25/2018.
 * Copyright © 2016 TheMaid Inc. All rights reserved.
 */

public class CustomerRequest implements Serializable {

    private final String customerName;
    private final String customerAddress;
    private final String customerPhone;
    private final String serviceRequested;

    public CustomerRequest(String customerName, String customerPhone, String customerAddress, String serviceRequested) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.serviceRequested = serviceRequested;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getServiceRequested() {
        return serviceRequested;
    }

}
