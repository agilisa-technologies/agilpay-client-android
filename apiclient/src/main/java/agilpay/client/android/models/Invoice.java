package agilpay.client.android.models;

public class Invoice {
    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }

    public double getMin_Amount() {
        return Min_Amount;
    }

    public void setMin_Amount(double min_Amount) {
        Min_Amount = min_Amount;
    }

    public double getMax_Amount() {
        return Max_Amount;
    }

    public void setMax_Amount(double max_Amount) {
        Max_Amount = max_Amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private String Number;
    private String Date;
    private String Total_Amount;
    private double Min_Amount;
    private double Max_Amount;
    private String Description;
}
