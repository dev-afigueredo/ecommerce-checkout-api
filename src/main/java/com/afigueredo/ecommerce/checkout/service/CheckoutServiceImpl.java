package com.afigueredo.ecommerce.checkout.service;

import com.afigueredo.ecommerce.checkout.entity.BoardingEntity;
import com.afigueredo.ecommerce.checkout.entity.CheckoutEntity;
import com.afigueredo.ecommerce.checkout.entity.CheckoutItemEntity;
import com.afigueredo.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.afigueredo.ecommerce.checkout.repository.CheckoutRepository;
import com.afigueredo.ecommerce.checkout.resource.checkout.CheckoutRequest;
import com.afigueredo.ecommerce.checkout.streaming.CheckoutCreatedSource;
import com.afigueredo.ecommerce.checkout.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        log.info("M=create, checkoutRequest={}", checkoutRequest);
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(uuidUtil.createUUID().toString())
                .status(CheckoutEntity.Status.CREATED)
                .saveAddress(checkoutRequest.getSaveAddress())
                .saveInformation(checkoutRequest.getSaveInfo())
                .boarding(BoardingEntity.builder()
                                  .address(checkoutRequest.getAddress())
                                  .complement(checkoutRequest.getComplement())
                                  .country(checkoutRequest.getCountry())
                                  .state(checkoutRequest.getState())
                                  .cep(checkoutRequest.getCep())
                                  .build())
                .build();
        checkoutEntity.setItems(checkoutRequest.getProducts()
                                        .stream()
                                        .map(product -> CheckoutItemEntity.builder()
                                                .checkout(checkoutEntity)
                                                .product(product)
                                                .build())
                                        .collect(Collectors.toList()));
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());

        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }

}
