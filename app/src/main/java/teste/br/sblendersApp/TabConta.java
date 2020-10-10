package teste.br.sblendersApp;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import teste.br.sblendersApp.Models.Produto;


public class TabConta extends Fragment {

    Button btnLogoff;
    SharedPreferences prefs;
    TextView userNameText;
    ImageView userPic;
    RequestQueue queue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onCreateSavedInstanceBundle){
        View tabConta = inflater.inflate(R.layout.tab_conta, container, false);
        prefs = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        queue = Volley.newRequestQueue(getContext());

        userPic = tabConta.findViewById(R.id.imageView);
        ImageRequest imageRequest = new ImageRequest("https://localhost:44323/api/FuncionarioFoto/" + prefs.getInt("id", 1), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                userPic.setImageBitmap(response);
            }
        }, 256, 256, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("BRUH MOMENT...");
                error.printStackTrace();
                userPic.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.logo, null));
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", prefs.getString("token", ""));
                return params;
            }
        };
        queue.add(imageRequest);
        userNameText = tabConta.findViewById(R.id.userNameText);
        userNameText.setText(prefs.getString("emp_name", "easter egg"));

        btnLogoff = tabConta.findViewById(R.id.btn_logoff);
        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().commit();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return tabConta;
    }
}
