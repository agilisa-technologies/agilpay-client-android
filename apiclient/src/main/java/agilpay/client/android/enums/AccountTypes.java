package agilpay.client.android.enums;

public enum AccountTypes {
    DebitOrCredit(1),
    Checking(2),
    Savings(3);
    private final int id;
    AccountTypes(int id){
        this.id = id;
    }

    public int getId(){return id;}
}
