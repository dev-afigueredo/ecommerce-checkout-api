package com.afigueredo.ecommerce.checkout.resource.checkout;

import com.afigueredo.ecommerce.checkout.entity.CheckoutEntity;
import com.afigueredo.ecommerce.checkout.response.Response;
import com.afigueredo.ecommerce.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/checkout")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CheckoutResource {

    private final CheckoutService checkoutService;

    @GetMapping("/")
    public String messageInitial() {
        return "API de Checkout orientado a eventos!.";
    }

    @PostMapping("/")
    public ResponseEntity<Response<CheckoutResponse>> create(@Valid CheckoutRequest checkoutRequest, BindingResult bindingResult) {

        Response<CheckoutResponse> response = new Response<>();

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        final CheckoutEntity checkoutEntity = checkoutService.create(checkoutRequest).orElseThrow();
        final CheckoutResponse checkoutResponse = CheckoutResponse.builder()
                .code(checkoutEntity.getCode())
                .build();

        response.setData(checkoutResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
