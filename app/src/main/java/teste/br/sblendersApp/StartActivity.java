package teste.br.sblendersApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        setContentView(R.layout.activity_start);
        SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
        StartActivity a = this;
        if(prefs.contains("token") && prefs.contains("id")){
            int id = prefs.getInt("id", 0);
            String Token = prefs.getString("token", "");
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://localhost:44323/api/Agente/" + URLEncoder.encode(Integer.toString(id)), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                if(!response.has("emp_id")){
                                    prefs.edit().clear().commit();
                                    Toast.makeText(StartActivity.this, "Usuário desprivilegiado.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    prefs.edit()
                                            .putInt("emp_id", response.getInt("emp_id"))
                                            .putString("emp_name", response.getString("emp_name"))
                                            .putInt("emp_type_id", response.getInt("emp_type_id"))
                                            .putInt("restaurant_id", response.getInt("restaurant_id"))
                                            .apply();
                                }

                                Intent i = new Intent(a, MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                            catch(Exception e){
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Intent i = new Intent(a, LoginActivity.class);
                            startActivity(i);
                            finish();
                            error.printStackTrace();
                            //if(error.networkResponse.statusCode == 403){
                            //  Toast.makeText(v.getContext(), "Falha ao entrar: Senha ou Login errados.", Toast.LENGTH_SHORT);
                            // }
                        }
                    }
                    ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", Token);
                    return params;
                }
            };

            queue.add(jsonObjectRequest);

        }
        else{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}