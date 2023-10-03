package agilpay.client.android.models;

import java.util.ArrayList;
import java.util.HashMap;

public class BalanceResponse {
    public String ResponseCode;
    public String Message;
    public String CustomerName;
    public String CustomerEmail;
    public CustomerAddress CustomerAddress;
    public String TotalAmount;
    public String MaxAmount;
    public String MinAmount;
    public HashMap<String, String> ExtData;
    public Invoice[] Invoices;
    public LastTransaction[] Last_Transactions;
}
