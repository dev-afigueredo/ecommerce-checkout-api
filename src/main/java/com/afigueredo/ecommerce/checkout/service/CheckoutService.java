package com.afigueredo.ecommerce.checkout.service;

import com.afigueredo.ecommerce.checkout.entity.CheckoutEntity;
import com.afigueredo.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);
}
