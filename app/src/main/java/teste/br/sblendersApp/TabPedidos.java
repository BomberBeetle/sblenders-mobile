package teste.br.sblendersApp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TabPedidos extends Fragment {

    public static JSONArray pedidos;
    RecyclerView rcv;
    FloatingActionButton refresh;
    Timer oTimer;
    public static SharedPreferences prefs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onCreateSavedInstanceBundle){
        prefs = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        View inflatedLayout = inflater.inflate(R.layout.tab_pedido, container, false);
        rcv = (RecyclerView)inflatedLayout.findViewById(R.id.rcv1);
        UpdatePedidos();
        refresh = inflatedLayout.findViewById(R.id.refreshingButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePedidos();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(llm);
        oTimer = new Timer();
        oTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdatePedidos();
            }
        }, 30000, 30000);
        createNotificationChannel();
        return inflatedLayout;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sblenders notifs";
            String description = "Notificações do Sblenders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Canal", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification(){
        PackageManager pm = getContext().getPackageManager();
        Intent appStartIntent = pm.getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
        PendingIntent p = PendingIntent.getService(getContext(),10, appStartIntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Canal")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notificação Sblenders")
                .setContentText("Essa é uma notificação do Sblenders")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(p)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(1, builder.build());
    }

    public void UpdatePedidos(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, "https://localhost:44323/api/Pedidos/" + prefs.getInt("id",0), null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            pedidos = response;
                            rcv.setAdapter(new PedidosAdapter(TabPedidos.this));
                            addNotification();
                        }
                        catch(Exception e){
                            getActivity().finish();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                        //if(error.networkResponse.statusCode == 403){
                        //  Toast.makeText(v.getContext(), "Falha ao entrar: Senha ou Login errados.", Toast.LENGTH_SHORT);
                        // }
                    }
                }
                ){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Authorization", prefs.getString("token", ""));
            return params;
        }
        };

        queue.add(jsonObjectRequest);
    }

}
