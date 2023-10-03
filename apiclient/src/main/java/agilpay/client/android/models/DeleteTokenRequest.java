package agilpay.client.android.models;

public class DeleteTokenRequest {
    private String AccountToken;
    private String CustomerID;

    public String getAccountToken() {
        return AccountToken;
    }

    public void setAccountToken(String accountToken) {
        AccountToken = accountToken;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }
}
