package com.amcoder.pranku.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amcoder.pranku.Utility.Constant;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by philips on 6/6/17.
 */

public class PayYouActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onStartTransaction(View view) {

        //String hashSequence = key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|salt;
        //String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitilizer.PaymentParam.Builder builder=new
                PayUmoneySdkInitilizer.PaymentParam.Builder().
        setMerchantId("XXXXXX")
        .setKey("YYYYYYYY")
        //.setIsDebug(true)                     // for Live mode -
        .setIsDebug(false)
        .setAmount(20)
        .setTnxId("0nf7" + System.currentTimeMillis())
        .setPhone("8882434664")
        .setProductName("product_name")
        .setFirstName("piyush")
        .setEmail("test@payu.in")
        .setsUrl("https://www.PayUmoney.com/mobileapp/PayUmoney/success.php")
        .setfUrl("https://www.PayUmoney.com/mobileapp/PayUmoney/failure.php")
        .setUdf1("")
        .setUdf2("")
        .setUdf3("")
        .setUdf4("")
        .setUdf5("");

    }

    public static String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBufferhexString = new StringBuffer();
        try { MessageDigest algorithm = MessageDigest.getInstance(type); algorithm.reset();
            algorithm.update(hashseq);
            bytemessageDigest[] = algorithm.digest();
            for (inti = 0; i<messageDigest.length; i++) {String hex = Integer.toHexString(0xFF &messageDigest[i]);
                if (hex.length() == 1) {hexString.append("0");}
                hexString.append(hex);}} catch (NoSuchAlgorithmExceptionnsae) {}
        returnhexString. toString();}
}
