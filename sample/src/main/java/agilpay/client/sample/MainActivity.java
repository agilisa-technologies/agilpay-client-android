package agilpay.client.sample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import agilpay.client.android.ApiClient;
import agilpay.client.android.R;
import agilpay.client.android.models.CustomerAccount;
import agilpay.client.android.models.DeleteTokenRequest;
import agilpay.client.android.models.RegisterTokenRequest;

public class MainActivity extends AppCompatActivity {
    private EditText console;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnPlay).setOnClickListener(view -> initClient());
        console = findViewById(R.id.editText);
        progress = findViewById(R.id.progress);
    }


    private void initClient(){
        String url= "https://sandbox-webapi.agilpay.net/";
        String clientId = "API-001";
        String clientSecret = "Dynapay";
        progress.setVisibility(View.VISIBLE);

        try {
            if(ApiClient.getInstance().isInitialized()){
                testClient(false);
                return;
            }

            ApiClient.initialize(url, clientId, clientSecret, new ApiClient.InitCallback() {
                @Override
                public void onInitSuccess() {
                    writeTextToConsole("Client initialized:"
                            + System.lineSeparator()+"[URL]: [" + url + "]"
                            + System.lineSeparator() + "[ClientId]: ["+ clientId + "]"
                            + System.lineSeparator() + "[ClientSecret]: [" + clientSecret + "]");

                    testClient(true);
                }

                @Override
                public void onInitFailed(String error) {
                    showError(error);
                    progress.setVisibility(View.GONE);
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
            showError(ex.getMessage());
        }
    }

    private void testClient(boolean fromInit){
        Gson gson = new Gson();

        if(!fromInit) {
            console.setText("");
        }
        try {
            new Thread(() -> {
                try {
                    CustomerAccount[] cards = ApiClient.getInstance().getCustomerTokensAsync("mamedina");

                    String json = gson.toJson(cards);

                    writeTextToConsole("getCustomerTokenAsync: "+System.lineSeparator()+"[customerId]: [mamedina] " + System.lineSeparator() + json);

                    RegisterTokenRequest newTokenRequest = new RegisterTokenRequest();
                    newTokenRequest.setAccountType("1");
                    newTokenRequest.setAccountNumber("5252525252525252");
                    newTokenRequest.setCustomerEmail("mmedina@agilisa.com");
                    newTokenRequest.setCustomerId("mamedina");
                    newTokenRequest.setCustomerName("Miguel medina");
                    newTokenRequest.setMerchantKey("TEST-001");
                    newTokenRequest.setZipCode("12345");
                    newTokenRequest.setCVV("725");
                    newTokenRequest.setExpirationMonth("07");
                    newTokenRequest.setExpirationYear("2029");

                    CustomerAccount newToken = ApiClient.getInstance().registerTokenAsync(newTokenRequest);

                    cards = ApiClient.getInstance().getCustomerTokensAsync("mamedina");

                    writeTextToConsole("new card added to wallet");
                    json = gson.toJson(cards);


                    writeTextToConsole("getCustomerTokenAsync: "+System.lineSeparator()+"[customerId]: [mamedina] " + System.lineSeparator() + json);

                    DeleteTokenRequest deleteTokenRequest = new DeleteTokenRequest();
                    deleteTokenRequest.setAccountToken(newToken.getAccountToken());
                    deleteTokenRequest.setCustomerID("mamedina");

                    String deleteText = ApiClient.getInstance().deleteCustomerCardAsync(deleteTokenRequest);

                    writeTextToConsole("New card deleted from wallet: " + deleteText);

                }catch(Exception e){
                    e.printStackTrace();
                }

                runOnUiThread(() -> progress.setVisibility(View.GONE));

            }).start();


        }catch(Exception e){
            e.printStackTrace();
            showError(e.getMessage());
        }
    }

    private synchronized void writeTextToConsole(String text){
        runOnUiThread(() -> {
            try {

                String consoleText = console.getText().toString();

                String newText = consoleText + System.lineSeparator() + System.lineSeparator() + text;

                console.setText(newText);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    private void showError(String message){
        runOnUiThread(() -> new AlertDialog.Builder(MainActivity.this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show());

    }
}