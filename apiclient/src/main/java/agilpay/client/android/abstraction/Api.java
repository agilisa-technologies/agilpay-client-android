package agilpay.client.android.abstraction;

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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("oauth/token")
    Call<TokenResponse> oAuth2Token(@Field("grant_type")String grant_type,
                                    @Field("client_id") String client_id,
                                    @Field("client_secret")String client_secret);

    @POST("v6/Authorize")
    Call<Transaction> authorizePayment(@Body AuthorizationRequest request);

    @POST("v6/AuthorizeToken")
    Call<Transaction> authorizePaymentToken(@Body AuthorizationTokenRequest request);

    @GET("v6/GetCustomerTokens")
    Call<CustomerAccount[]> getCustomerTokens(@Query("CustomerID") String CustomerID);

    @POST("Payment6/GetBalance")
    Call<BalanceResponse> getBalance(@Body BalanceRequest request);

    @GET("v6/IsValidCard")
    Call<Boolean> isValidCard(@Query("CardNumber") String CardNumber);

    @GET("v6/IsValidRoutingNumber")
    Call<Boolean> isValidRoutingNumber(@Query("RoutingNumber") String RoutingNumber);

    @POST("v6/DeleteCustomerToken")
    Call<String> deleteCustomerCard(@Body DeleteTokenRequest request);

    @POST("v6/RegisterToken")
    Call<CustomerAccount> registerToken(@Body RegisterTokenRequest request);

    @POST("v6/CloseBatchResumen")
    Call<String> closeBatchResumen(@Body CloseBatchRequest request);

    @POST("v6/VoidByID")
    Call<Transaction> voidById(@Body VoidByIdRequest request);

    @POST("v6/VoidSale")
    Call<Transaction> voidSale(@Body VoidSaleRequest request);

    @POST("v6/CaptureByID")
    Call<Transaction> captureByID(@Body VoidByIdRequest request);

    @POST("v6/CaptureAdjustmendByID")
    Call<Transaction> captureAdjustmendByID(@Body CaptureAdjustmendByIDRequest request);

    @GET("v6/GetTransactionByID")
    Call<Transaction> getTransactionByID(@Query("MerchantKey") String MerchantKey, @Query("IDTransaction") String IDTransaction);

    @POST("v6/Recurring/Add")
    Call<RecurringScheduleAddResponse> recurringScheduleAdd(@Body RecurringScheduleAddRequest request);

    @GET("/v6/Recurring/Get")
    Call<RecurringSchedule> recurringScheduleGet(@Query("MerchantKey") String MerchantKey,
                                                 @Query("Service") String Service,
                                                 @Query("CustomerId") String CustomerId);

    @GET("v6/Recurring/Change")
    Call<RecurringScheduleAddResponse> recurringScheduleChangeStatus(RecurringScheduleChangeStatusRequest request);

    @POST("v6/Recurring/Update")
    Call<RecurringScheduleAddResponse> recurringScheduleUpdate(@Body RecurringSchedule request);
}
