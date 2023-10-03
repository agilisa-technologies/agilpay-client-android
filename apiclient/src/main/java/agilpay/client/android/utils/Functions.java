package agilpay.client.android.utils;

import okhttp3.ResponseBody;
import retrofit2.Response;

public final class Functions {
    private Functions(){}

    public static String getError(Response response){
         try{
             ResponseBody error = response.errorBody();

             String message = response.message();

             if(error != null){
                 message = error.string();
             }

             return message;

         }catch(Exception e){
             e.printStackTrace();
         }

        return "";
    }
}
