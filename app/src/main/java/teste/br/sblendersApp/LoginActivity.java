package teste.br.sblendersApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.TotalCaptureResult;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import javax.net.ssl.TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ResourceBundle;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText caixaUser;
    EditText caixaPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoginActivity a = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RequestQueue queue = Volley.newRequestQueue(this);
        caixaPass = findViewById(R.id.passEditText);
        caixaUser = findViewById(R.id.loginEditText);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, "https://localhost:44323/api/AgenteToken/"+ URLEncoder.encode(caixaUser.getText().toString())+"/" + URLEncoder.encode(caixaPass.getText().toString()), null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Toast.makeText(a, response.optString("token", "deu merda"), Toast.LENGTH_SHORT).show();
                                SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
                                prefs.edit().putString("token", response.optString("token", "")).putInt("id", response.optInt("id", 0)).commit();
                                Intent intent = new Intent(a, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                if(error.networkResponse.statusCode == 403){
                                    Toast.makeText(a, "Credencias Incorretas", Toast.LENGTH_SHORT).show();
                                }
                                error.printStackTrace();
                                //if(error.networkResponse.statusCode == 403){
                                  //  Toast.makeText(v.getContext(), "Falha ao entrar: Senha ou Login errados.", Toast.LENGTH_SHORT);
                               // }
                            }
                        });

                queue.add(jsonObjectRequest);
            }
        });
    }
}

