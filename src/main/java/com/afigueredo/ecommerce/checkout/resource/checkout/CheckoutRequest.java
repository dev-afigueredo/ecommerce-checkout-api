package com.afigueredo.ecommerce.checkout.resource.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest implements Serializable {

    @NotEmpty(message = "Primeiro nome é obrigatório")
    @Length(min = 2, max = 50, message= "O tamanho do nome deve ficar entre 5 a 50 caracteres")
    private String firstName;

    @NotEmpty(message = "Último nome é obrigatório")
    @Length(min = 2, max = 50, message= "O tamanho do último nome deve ficar entre 5 a 50 caracteres")
    private String lastName;

    private String email;

    @NotEmpty(message = "Endereço é obrigatório")
    @Length(min = 2, max = 50, message= "O tamanho do endereço deve ficar entre 10 a 200 caracteres")
    private String address;

    private String complement;

    @NotEmpty(message = "Cidade é obrigatório")
    private String country;

    @NotEmpty(message = "Estado é obrigatório")
    private String state;

    @NotEmpty(message = "CEP é obrigatório")
    private String cep;

    private Boolean saveAddress;

    private Boolean saveInfo;

    @NotEmpty(message = "Método de pagamento é obrigatório")
    private String paymentMethod;

    @NotEmpty(message = "Nome do cartão é obrigatório")
    private String cardName;

    @NotEmpty(message = "Número do cartão é obrigatório")
    private String cardNumber;

    @NotEmpty(message = "Data do cartão é obrigatório")
    private String cardDate;

    @NotEmpty(message = "CVV é obrigatório")
    private String cardCvv;

    @NotEmpty(message = "Lista de itens é obrigatório")
    private List<String> products;
}

