package agilpay.client.android.models;

public class Transaction {
    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAccountToken() {
        return AccountToken;
    }

    public void setAccountToken(String accountToken) {
        AccountToken = accountToken;
    }

    public String getIDTransaction() {
        return IDTransaction;
    }

    public void setIDTransaction(String IDTransaction) {
        this.IDTransaction = IDTransaction;
    }

    public String getBatchCode() {
        return BatchCode;
    }

    public void setBatchCode(String batchCode) {
        BatchCode = batchCode;
    }

    public String getAcquirerName() {
        return AcquirerName;
    }

    public void setAcquirerName(String acquirerName) {
        AcquirerName = acquirerName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getAuditNumber() {
        return AuditNumber;
    }

    public void setAuditNumber(String auditNumber) {
        AuditNumber = auditNumber;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getDesicionResponseCode() {
        return DesicionResponseCode;
    }

    public void setDesicionResponseCode(String desicionResponseCode) {
        DesicionResponseCode = desicionResponseCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getAuthNumber() {
        return AuthNumber;
    }

    public void setAuthNumber(String authNumber) {
        AuthNumber = authNumber;
    }

    public String getHostDate() {
        return HostDate;
    }

    public void setHostDate(String hostDate) {
        HostDate = hostDate;
    }

    public String getHostTime() {
        return HostTime;
    }

    public void setHostTime(String hostTime) {
        HostTime = hostTime;
    }

    public String getReference_Code() {
        return Reference_Code;
    }

    public void setReference_Code(String reference_Code) {
        Reference_Code = reference_Code;
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

    private String Account;
    private String AccountToken;
    private String IDTransaction;
    private String BatchCode;
    private String AcquirerName;
    private String Status;
    private String CardHolderName;
    private String AuditNumber;
    private String ResponseCode;
    private String DesicionResponseCode;
    private String Message;
    private String AuthNumber;
    private String HostDate;
    private String HostTime;
    private String Reference_Code;
    private double Amount;
    private String Currency;
}
