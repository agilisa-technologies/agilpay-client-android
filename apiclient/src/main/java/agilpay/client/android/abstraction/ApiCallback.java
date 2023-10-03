package agilpay.client.android.abstraction;

public interface ApiCallback<T> {
    void onResponse(T response);
    void onFailure(String error);
}
