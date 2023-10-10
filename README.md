# agilpay-client-android


# Client class for Agilpay REST API service  
Agilpay client class android

Agilpay REST API can be accessed through standard protocols such as REST. 
This library can be used by applications to perform operations on the Agilpay Gateway server using java code

Endpoint authentication uses OAUTH 2.0 standard

This repository includes 2 projects
* agilpay.client class: sample implementation to connect to Agilpay REST API
* sample: android application to test agilpay.client class


# Available endpoints

* getBalance
* AuthorizePayment
* AuthorizePaymentToken
* getCustomerTokens
* isValidCard
* isValidRoutingNumber
* deleteCustomerCard
* closeBatchResumen
* voidById
* voidSale
* captureByID
* captureAdjustmendByID
* getTransactionByID
* recurringScheduleAdd
* recurringScheduleGet
* recurringScheduleChangeStatus
* recurringScheduleUpdate
* registerToken

# Initializing library

The environment URL must be supplied on initialize method
* for test environment: https://sandbox-webpay.agilpay.net/ 
* for production environment: https://webpay.agilpay.net/
* 
> URL address could change depending on configuration. Please check with your account representative

For authentication, this information must be provided to the Initialize method, the identity provider will issue a token to the requesting application.
* client_id: Uniquely identifies the client requesting the token
* client_secret: Password used to authenticate the token request

``` java
String _url = "https://sandbox-webapi.agilpay.net/";

ApiClient.initialize(_url, client_id, secret, new ApiClient.InitCallback(){
        @Override
        public void onInitSuccess() {
            //now you can use the library
        }

        @Override
        public void onInitFailed(String error) {
            //an error occurred
        }
});
```

# Authorizing Payment
```
AuthorizationRequest authorizationRequest = new AuthorizationRequest();
authorizationRequest.setMerchantKey("TEST-001");
authorizationRequest.setAccountNumber("4242424242424242");
authorizationRequest.setExpirationMonth("07");
authorizationRequest.setExpirationYear("2027");
authorizationRequest.setCustomerName("John Doe");
authorizationRequest.setCustomerID("mamedina");
authorizationRequest.setAccountType(AccountTypes.DebitOrCredit.getId());
authorizationRequest.setCustomerEmail("test@gmail.com");
authorizationRequest.setZipCode("12345");
authorizationRequest.setAmount(20.50);
authorizationRequest.setCurrency("840");
authorizationRequest.setTax(0);
authorizationRequest.setInvoice("123465465");
authorizationRequest.setTransaction_Detail("payment information detail");

 Log.i("TAG", "Requesting authorization...");

ApiClient.getInstance().authorizePayment(authorizationRequest, new ApiCallback<Transaction>() {
    @Override
    public void onResponse(Transaction response) {
        //if response.getResponseCode().equals("00"); the transaction was approved otherwise was declined
    }

    @Override
    public void onFailure(String error) {

    }
});
```

**Notes**
Every method has a Async version if you dont want use the ApiCallback, but it must be called from a background thread.
