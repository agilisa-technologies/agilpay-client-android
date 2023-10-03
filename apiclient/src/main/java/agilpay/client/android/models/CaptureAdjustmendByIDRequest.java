package agilpay.client.android.models;

public class CaptureAdjustmendByIDRequest {
    private String MerchantKey;
    private String IDTransaction;
    private String Amount;

    public String getMerchantKey() {
        return MerchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        MerchantKey = merchantKey;
    }

    public String getIDTransaction() {
        return IDTransaction;
    }

    public void setIDTransaction(String IDTransaction) {
        this.IDTransaction = IDTransaction;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
