package agilpay.client.android.models;

public class AuthorizationTokenRequest {

    private String MerchantKey;

    public String getMerchantKey() {
        return MerchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        MerchantKey = merchantKey;
    }

    public String getAccountToken() {
        return AccountToken;
    }

    public void setAccountToken(String accountToken) {
        AccountToken = accountToken;
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

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getExtData() {
        return ExtData;
    }

    public void setExtData(String extData) {
        ExtData = extData;
    }

    public boolean isInstallments() {
        return IsInstallments;
    }

    public void setInstallments(boolean installments) {
        IsInstallments = installments;
    }

    public int getInstallmentsCount() {
        return InstallmentsCount;
    }

    public void setInstallmentsCount(int installmentsCount) {
        InstallmentsCount = installmentsCount;
    }

    private String AccountToken;
    private double Amount;
    private String Currency;
    private double Tax;
    private String Invoice;
    private String Transaction_Detail;
    private boolean HoldFunds;
    private String CVV;
    private String EffectiveDate;
    private String ExtData;
    private boolean IsInstallments;
    private int InstallmentsCount;
}
