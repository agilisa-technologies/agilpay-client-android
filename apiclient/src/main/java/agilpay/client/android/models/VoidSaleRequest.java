package agilpay.client.android.models;

public class VoidSaleRequest {
    private String MerchantKey;

    public String getMerchantKey() {
        return MerchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        MerchantKey = merchantKey;
    }

    public String getAuthNumber() {
        return AuthNumber;
    }

    public void setAuthNumber(String authNumber) {
        AuthNumber = authNumber;
    }

    public String getReferenceCode() {
        return ReferenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        ReferenceCode = referenceCode;
    }

    public String getAuditNumber() {
        return AuditNumber;
    }

    public void setAuditNumber(String auditNumber) {
        AuditNumber = auditNumber;
    }

    private String AuthNumber;
    private String ReferenceCode;
    private String AuditNumber;
}
