package agilpay.client.android.models;

public class RegisterTokenRequest {
    private String MerchantKey;
    private String AccountType; //credit or debit card = 1, ach-checking = 2, ach-savings = 3
    private String AccountNumber;
    private String ExpirationMonth;
    private String ExpirationYear;
    private String CustomerId;
    private String CustomerName;
    private String CustomerEmail;

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    private String CVV;

    public String getMerchantKey() {
        return MerchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        MerchantKey = merchantKey;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getExpirationMonth() {
        return ExpirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        ExpirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return ExpirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        ExpirationYear = expirationYear;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    public void setDefault(boolean aDefault) {
        IsDefault = aDefault;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getRoutingNumber() {
        return RoutingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        RoutingNumber = routingNumber;
    }

    private boolean IsDefault;
    private String ZipCode;

    private String RoutingNumber;
}
