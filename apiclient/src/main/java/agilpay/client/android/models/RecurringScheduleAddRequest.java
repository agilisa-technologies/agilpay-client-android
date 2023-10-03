package agilpay.client.android.models;

public class RecurringScheduleAddRequest {
    private String MerchantKey;
    private String Service;
    private String CustomerId;
    private String AccountToken;
    private double Amount;
    private String Currency;

    public String getMerchantKey() {
        return MerchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        MerchantKey = merchantKey;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
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

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int period) {
        Period = period;
    }

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    private int Period;
    private int Frequency;
    private int Day;
    private int Quantity;
    private String FromDate;
    private String ToDate;
}
