package agilpay.client.android.models;

public class AuthorizationRequest {
    private int AccountType;
    private String AccountNumber;
    private String RoutingNumber;
    private String EffectiveDate;
    private boolean IsDefault;
    private String ExpirationMonth;
    private String ExpirationYear;
    private String CustomerID;
    private String CustomerName;
    private String CustomerEmail;
    private String CustomerAddress;
    private String CustomerCity;
    private String CustomerState;
    private String ZipCode;
    private double Amount;
    private String Currency;
    private double Tax;
    private String CVV;
    private String Invoice;
    private String Transaction_Detail;
    private boolean HoldFunds;
    private String ExtData;

    private boolean SaveWallet;
    private boolean ForceDuplicate;


    public String MerchantKey;

    public String getMerchantKey() {
        return MerchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        MerchantKey = merchantKey;
    }

    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int accountType) {
        AccountType = accountType;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return RoutingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        RoutingNumber = routingNumber;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    public void setDefault(boolean aDefault) {
        IsDefault = aDefault;
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

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
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

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return CustomerCity;
    }

    public void setCustomerCity(String customerCity) {
        CustomerCity = customerCity;
    }

    public String getCustomerState() {
        return CustomerState;
    }

    public void setCustomerState(String customerState) {
        CustomerState = customerState;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getInvoice() {
        return Invoice;
    }

    public void setInvoice(String invoice) {
        Invoice = invoice;
    }

    public String getTransaction_Detail() {
        return Transaction_Detail;
    }

    public void setTransaction_Detail(String transaction_Detail) {
        Transaction_Detail = transaction_Detail;
    }

    public boolean isHoldFunds() {
        return HoldFunds;
    }

    public void setHoldFunds(boolean holdFunds) {
        HoldFunds = holdFunds;
    }

    public String getExtData() {
        return ExtData;
    }

    public void setExtData(String extData) {
        ExtData = extData;
    }

    public boolean isSaveWallet() {
        return SaveWallet;
    }

    public void setSaveWallet(boolean saveWallet) {
        SaveWallet = saveWallet;
    }

    public boolean isForceDuplicate() {
        return ForceDuplicate;
    }

    public void setForceDuplicate(boolean forceDuplicate) {
        ForceDuplicate = forceDuplicate;
    }
    
}
