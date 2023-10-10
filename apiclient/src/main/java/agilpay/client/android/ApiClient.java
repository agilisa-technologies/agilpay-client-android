package agilpay.client.android;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import agilpay.client.android.abstraction.Api;
import agilpay.client.android.abstraction.ApiCallback;
import agilpay.client.android.models.AuthorizationRequest;
import agilpay.client.android.models.AuthorizationTokenRequest;
import agilpay.client.android.models.BalanceRequest;
import agilpay.client.android.models.BalanceResponse;
import agilpay.client.android.models.CaptureAdjustmendByIDRequest;
import agilpay.client.android.models.CloseBatchRequest;
import agilpay.client.android.models.CustomerAccount;
import agilpay.client.android.models.DeleteTokenRequest;
import agilpay.client.android.models.RecurringSchedule;
import agilpay.client.android.models.RecurringScheduleAddRequest;
import agilpay.client.android.models.RecurringScheduleAddResponse;
import agilpay.client.android.models.RecurringScheduleChangeStatusRequest;
import agilpay.client.android.models.RegisterTokenRequest;
import agilpay.client.android.models.TokenResponse;
import agilpay.client.android.models.Transaction;
import agilpay.client.android.models.VoidByIdRequest;
import agilpay.client.android.models.VoidSaleRequest;
import agilpay.client.android.utils.Functions;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private Api api;
    private String token;
    private String clientId;
    private String clientSecret;
    private static volatile ApiClient _instance;
    private static final Object locker = new Object();

    public static ApiClient getInstance(){
        synchronized (locker){
            if(_instance == null){
                _instance = new ApiClient();
            }
            return _instance;
        }
    }
    private ApiClient(){}

    public boolean isInitialized(){
        return getInstance().api != null && getInstance().token != null && !getInstance().token.trim().isEmpty();
    }

    public static void initialize(String apiUrl, final String clientId, final String clientSecret, InitCallback callback){

        if(getInstance().isInitialized()){
            return;
        }

        String sessionId = UUID.randomUUID().toString();

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request.Builder request = chain.request().newBuilder();
            request.header("Content-Type", "application/json");
            if (getInstance().token != null && !getInstance().token.trim().isEmpty()) {
                request.addHeader("Authorization", "Bearer " + getInstance().token);
            }
            request.addHeader("SessionId", sessionId);
            request.addHeader("SiteId", clientId);

            return chain.proceed(request.build());
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        _instance = new ApiClient();
        _instance.api = retrofit.create(Api.class);
        _instance.clientId = clientId;
        _instance.clientSecret = clientSecret;
        _instance.oAuth2Token(clientId, clientSecret, callback);
    }
    private void oAuth2Token(String clientId, String clientSecret, InitCallback callback){

        Call< TokenResponse> call = api.oAuth2Token("client_credentials", clientId, clientSecret);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful()) {
                    TokenResponse atoken = response.body();
                    if (callback != null && atoken != null) {
                        _instance.token = atoken.getAccess_token();

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, (int)atoken.getExpires_in());

                        TokenExpireTime = cal.getTime();
                        callback.onInitSuccess();
                    }
                    return;
                }

                callback.onInitFailed(response.message());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                if(callback != null){
                    callback.onInitFailed(t.getMessage());
                }
            }
        });

    }

    private void oAuth2TokenAsync(String clientId, String clientSecret) throws Exception{
        Response<TokenResponse> response = api.oAuth2Token("client_credentials", clientId, clientSecret).execute();

        if(!response.isSuccessful()){
            return;
        }
        TokenResponse token = response.body();

        getInstance().token = token.getAccess_token();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, (int)token.getExpires_in());

        getInstance().TokenExpireTime = cal.getTime();
    }

    private Date TokenExpireTime;
    private void checkTokenExpirationAsync() throws Exception{
        if(!getInstance().isInitialized()){
            throw new Exception("You must call initialize first");
        }
        if(tokenIsExpired()) {
            oAuth2TokenAsync(getInstance().clientId, getInstance().clientSecret);
        }
    }

    private void checkTokenExpiration(Runnable run, ApiCallback<?> callback){
        if(!getInstance().isInitialized()){
            callback.onFailure("You must call initialize first");
        }
        if(tokenIsExpired()){
            oAuth2Token(getInstance().clientId, getInstance().clientSecret, new InitCallback() {
                @Override
                public void onInitSuccess() {
                    run.run();
                }

                @Override
                public void onInitFailed(String error) {
                    callback.onFailure("Error getting access token: " + error);
                }
            });
            return;
        }
        run.run();
    }

    private boolean tokenIsExpired(){
        Date currentDate = Calendar.getInstance().getTime();
        int result = currentDate.compareTo(TokenExpireTime);
        return result > 0;
    }

    public void authorizePayment(AuthorizationRequest request, ApiCallback<Transaction> callback){
        checkTokenExpiration(() -> {
            Call<Transaction> call = api.authorizePayment(request);

            call.enqueue(new Callback<Transaction>() {
                @Override
                public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                    if(!response.isSuccessful()){
                        callback.onFailure(Functions.getError(response));
                        return;
                    }

                    callback.onResponse(response.body());
                }

                @Override
                public void onFailure(Call<Transaction> call, Throwable t) {
                    callback.onFailure(t.getMessage());
                }
            });
        }, callback);

    }

    public Transaction authorizePaymentAsync(AuthorizationRequest request) throws Exception{
        Call<Transaction> call = api.authorizePayment(request);

        checkTokenExpirationAsync();

        Response<Transaction> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void authorizePaymentToken(AuthorizationTokenRequest request, ApiCallback<Transaction> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Transaction> call = api.authorizePaymentToken(request);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public Transaction authorizePaymentTokenAsync(AuthorizationTokenRequest request) throws Exception{
        checkTokenExpirationAsync();

        Call<Transaction> call = api.authorizePaymentToken(request);

        Response<Transaction> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void getCustomerTokens(String customerId, ApiCallback<CustomerAccount[]> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<CustomerAccount[]> call = api.getCustomerTokens(customerId);

                call.enqueue(new Callback<CustomerAccount[]>() {
                    @Override
                    public void onResponse(Call<CustomerAccount[]> call, Response<CustomerAccount[]> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<CustomerAccount[]> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }
    public CustomerAccount[] getCustomerTokensAsync(String customerId) throws Exception{
        checkTokenExpirationAsync();
        Call<CustomerAccount[]> call = api.getCustomerTokens(customerId);

        Response<CustomerAccount[]> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void getBalance(BalanceRequest request, ApiCallback<BalanceResponse> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<BalanceResponse> call = api.getBalance(request);

                call.enqueue(new Callback<BalanceResponse>() {
                    @Override
                    public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<BalanceResponse> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public BalanceResponse getBalanceAsync(BalanceRequest request) throws Exception{
        checkTokenExpirationAsync();
        Call<BalanceResponse> call = api.getBalance(request);

        Response<BalanceResponse> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void isValidCard(String CardNumber, ApiCallback<Boolean> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Boolean> call = api.isValidCard(CardNumber);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public boolean isValidCardAsync(String cardNumber) throws Exception{
        checkTokenExpirationAsync();
        Call<Boolean> call = api.isValidCard(cardNumber);

        Response<Boolean> response = call.execute();

        if(response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return Boolean.TRUE.equals(response.body());
    }

    public void isValidRoutingNumber(String RoutingNumber, ApiCallback<Boolean> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Boolean> call = api.isValidRoutingNumber(RoutingNumber);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public boolean isValidRoutingNumberAsync(String routingNumber) throws Exception{
        checkTokenExpirationAsync();
        Call<Boolean> call = api.isValidRoutingNumber(routingNumber);

        Response<Boolean> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return Boolean.TRUE.equals(response.body());
    }

    public void deleteCustomerCard(final DeleteTokenRequest request, final ApiCallback<String> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<String> call = api.deleteCustomerCard(request);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public String deleteCustomerCardAsync(DeleteTokenRequest request) throws Exception{
        checkTokenExpirationAsync();
        Call<String> call = api.deleteCustomerCard(request);

        Response<String> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void registerToken(RegisterTokenRequest request, ApiCallback<CustomerAccount> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<CustomerAccount> call = api.registerToken(request);

                call.enqueue(new Callback<CustomerAccount>() {
                    @Override
                    public void onResponse(Call<CustomerAccount> call, Response<CustomerAccount> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<CustomerAccount> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public CustomerAccount registerTokenAsync(RegisterTokenRequest request) throws Exception{
        checkTokenExpirationAsync();
        Call<CustomerAccount> call = api.registerToken(request);

        Response<CustomerAccount> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void closeBatchResumen(CloseBatchRequest request, ApiCallback<String> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<String> call = api.closeBatchResumen(request);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public String closeBatchResumenAsync(CloseBatchRequest request) throws Exception{
        checkTokenExpirationAsync();
        Call<String> call = api.closeBatchResumen(request);

        Response<String> response = call.execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void voidById(VoidByIdRequest request, ApiCallback<Transaction> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Transaction> call = api.voidById(request);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public Transaction voidByIdAsync(VoidByIdRequest request) throws Exception{
        checkTokenExpirationAsync();
        Response<Transaction> response = api.voidById(request).execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void voidSale(VoidSaleRequest request, ApiCallback<Transaction> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Transaction> call = api.voidSale(request);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public Transaction voidSaleAsync(VoidSaleRequest request) throws Exception{
        checkTokenExpirationAsync();
        Response<Transaction> response = api.voidSale(request).execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void captureByID(VoidByIdRequest request, ApiCallback<Transaction> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Transaction> call = api.captureByID(request);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }

                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public Transaction captureByIdAsync(VoidByIdRequest request) throws Exception{
        checkTokenExpirationAsync();
        Response<Transaction> response = api.captureByID(request).execute();
        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }
        return response.body();
    }

    public void captureAdjustmendByID(CaptureAdjustmendByIDRequest request, ApiCallback<Transaction> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Transaction> call = api.captureAdjustmendByID(request);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public Transaction captureAdjustmendByIdAsync(CaptureAdjustmendByIDRequest request) throws Exception{
        checkTokenExpirationAsync();
        Response<Transaction> response = api.captureAdjustmendByID(request).execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void getTransactionByID(String MerchantKey, String IDTransaction, ApiCallback<Transaction> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<Transaction> call = api.getTransactionByID(MerchantKey, IDTransaction);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);
    }

    public Transaction getTransactionByIdAsync(String merchantKey, String IDTransaction) throws Exception{
        checkTokenExpirationAsync();
        Response<Transaction> response = api.getTransactionByID(merchantKey, IDTransaction).execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void recurringScheduleAdd(RecurringScheduleAddRequest request, ApiCallback<RecurringScheduleAddResponse> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<RecurringScheduleAddResponse> call = api.recurringScheduleAdd(request);

                call.enqueue(new Callback<RecurringScheduleAddResponse>() {
                    @Override
                    public void onResponse(Call<RecurringScheduleAddResponse> call, Response<RecurringScheduleAddResponse> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecurringScheduleAddResponse> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public RecurringScheduleAddResponse recurringScheduleAddAsync(RecurringScheduleAddRequest request) throws Exception{
        checkTokenExpirationAsync();
        Response<RecurringScheduleAddResponse> response = api.recurringScheduleAdd(request).execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void recurringScheduleGet(String MerchantKey, String Service, String CustomerId, ApiCallback<RecurringSchedule> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<RecurringSchedule> call = api.recurringScheduleGet(MerchantKey, Service, CustomerId);

                call.enqueue(new Callback<RecurringSchedule>() {
                    @Override
                    public void onResponse(Call<RecurringSchedule> call, Response<RecurringSchedule> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecurringSchedule> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public RecurringSchedule recurringScheduleGetAsync(String merchantKey, String service, String customerId) throws Exception{
        checkTokenExpirationAsync();
        Response<RecurringSchedule> response = api.recurringScheduleGet(merchantKey, service, customerId).execute();
        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }
        return response.body();
    }

    public void recurringScheduleChangeStatus(RecurringScheduleChangeStatusRequest request, ApiCallback<RecurringScheduleAddResponse> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<RecurringScheduleAddResponse> call = api.recurringScheduleChangeStatus(request);

                call.enqueue(new Callback<RecurringScheduleAddResponse>() {
                    @Override
                    public void onResponse(Call<RecurringScheduleAddResponse> call, Response<RecurringScheduleAddResponse> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecurringScheduleAddResponse> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public RecurringScheduleAddResponse recurringScheduleChangeStatusAsync(RecurringScheduleChangeStatusRequest request) throws Exception{
        checkTokenExpirationAsync();
        Response<RecurringScheduleAddResponse> response = api.recurringScheduleChangeStatus(request).execute();

        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }

        return response.body();
    }

    public void recurringScheduleUpdate(RecurringSchedule request, ApiCallback<RecurringScheduleAddResponse> callback){
        checkTokenExpiration(new Runnable() {
            @Override
            public void run() {
                Call<RecurringScheduleAddResponse> call = api.recurringScheduleUpdate(request);

                call.enqueue(new Callback<RecurringScheduleAddResponse>() {
                    @Override
                    public void onResponse(Call<RecurringScheduleAddResponse> call, Response<RecurringScheduleAddResponse> response) {
                        if(!response.isSuccessful()){
                            callback.onFailure(Functions.getError(response));
                            return;
                        }
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecurringScheduleAddResponse> call, Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
            }
        }, callback);

    }

    public RecurringScheduleAddResponse recurringScheduleUpdateAsync(RecurringSchedule request) throws Exception{
        checkTokenExpirationAsync();
        Response<RecurringScheduleAddResponse> response = api.recurringScheduleUpdate(request).execute();
        if(!response.isSuccessful()){
            throw new Exception(Functions.getError(response));
        }
        return response.body();
    }

    public interface InitCallback{
        void onInitSuccess();
        void onInitFailed(String error);
    }
}
