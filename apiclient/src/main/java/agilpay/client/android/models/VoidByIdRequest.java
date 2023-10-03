package agilpay.client.android.models;

public class VoidByIdRequest {
    private String MerchantKey;
    private String IDTransaction;

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
}
