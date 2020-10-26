package teste.br.sblendersApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {
    public PedidosAdapter(TabPedidos context){
        super();
        this.context = context;
    }
    TabPedidos context;
    SharedPreferences prefs;
    RequestQueue queue;
    public class PedidosViewHolder extends  RecyclerView.ViewHolder{
        TextView pedidoNumTxt;
        RecyclerView rcvPratos;
        Button btnRejeita;
        Button btnAceita;
        ImageButton btnCollapse;
        TextView ordens;
        TextView ordensTitle;
        TextView endereco;
        TextView enderecoTitle;
        TextView segure;
        View cardReference;
        Button spaghetti;
        JSONObject pedido;
        boolean collapsed;
        public PedidosViewHolder(View view){
            super(view);
            prefs = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            pedidoNumTxt = view.findViewById(R.id.card_text_n_pedido);
            rcvPratos = view.findViewById(R.id.card_pedido_rcv);
            btnRejeita = view.findViewById(R.id.btn_rejeita_pedido);
            btnAceita = view.findViewById(R.id.btn_pronto_pedido);
            btnCollapse = view.findViewById(R.id.card_collapse_button);
            ordens = view.findViewById(R.id.txt_instrucoes);
            ordensTitle = view.findViewById(R.id.card_text_instrucoes);
            endereco = view.findViewById(R.id.txt_endereco);
            enderecoTitle = view.findViewById(R.id.card_text_endereco);
            segure = view.findViewById(R.id.txt_segure);
            spaghetti = view.findViewById(R.id.card_btn_retract);
            if(prefs.getInt("emp_type_id", 0) == 2){
                btnAceita.setText("Marcar como entregue");
            }
            collapse();
        }
        public void collapse(){
            btnCollapse.setImageDrawable(btnCollapse.getResources().getDrawable(R.drawable.expander_open_holo_light));
            rcvPratos.setVisibility(View.GONE);
            btnRejeita.setVisibility(View.GONE);
            btnAceita.setVisibility(View.GONE);
            ordens.setVisibility(View.GONE);
            ordensTitle.setVisibility(View.GONE);
            endereco.setVisibility(View.GONE);
            enderecoTitle.setVisibility(View.GONE);
            segure.setVisibility(View.GONE);
            collapsed = true;
        }
        public void open(){
            btnCollapse.setImageDrawable(btnCollapse.getResources().getDrawable(R.drawable.expander_close_holo_light));
            rcvPratos.setVisibility(View.VISIBLE);
            btnRejeita.setVisibility(View.VISIBLE);
            btnAceita.setVisibility(View.VISIBLE);
            ordens.setVisibility(View.VISIBLE);
            ordensTitle.setVisibility(View.VISIBLE );
            endereco.setVisibility(View.VISIBLE);
            enderecoTitle.setVisibility(View.VISIBLE );
            segure.setVisibility(View.VISIBLE);
            collapsed = false;
        }
    }
    @Override
    public int getItemCount() {
        return TabPedidos.pedidos.length();
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pedido, viewGroup, false);

        PedidosViewHolder pvh = new PedidosViewHolder(v);
        pvh.cardReference = v;

        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder personViewHolder, int i) {
        try{
        personViewHolder.pedidoNumTxt.setText("Pedido " + TabPedidos.pedidos.getJSONObject(i).getInt("pedidoID"));

        personViewHolder.btnRejeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejeitarPedido(personViewHolder.getAdapterPosition());
            }
        });
        personViewHolder.btnAceita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aceitarPedido(personViewHolder.getAdapterPosition());
                }
            });
        personViewHolder.spaghetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personViewHolder.collapsed) {
                    personViewHolder.open();
                } else {
                    personViewHolder.collapse();
                }
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(personViewHolder.itemView.getContext());
        personViewHolder.rcvPratos.setLayoutManager(llm);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://localhost:44323/api/Pedidos/" + prefs.getInt("id",0) + "/" + TabPedidos.pedidos.getJSONObject(i).getInt("pedidoID"), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                personViewHolder.rcvPratos.setAdapter(new CardPedidoRecyclerViewAdapter(response));
                                personViewHolder.ordens.setText(response.optString("instrucoes", "Nenhuma"));
                                personViewHolder.endereco.setText(response.optString("endereco", "Nenhum"));
                                personViewHolder.cardReference.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(response.optString("endereco")));
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        v.getContext().startActivity(mapIntent);
                                        return true;
                                    }
                                });
                            }
                            catch(Exception e){
                                System.out.println("DEBUG LINE!");
                                e.printStackTrace();
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
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        queue = Volley.newRequestQueue(recyclerView.getContext());
    }
    private void rejeitarPedido(int i){
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, "https://localhost:44323/api/Pedidos/" + prefs.getInt("id",0) + "/" + TabPedidos.pedidos.optJSONObject(i).optInt("pedidoID") + "/5",  new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                         PedidosAdapter.this.context.UpdatePedidos();
                        }
                        catch(Exception e){
                            System.out.println("DEBUG LINE!");
                            e.printStackTrace();
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
    private void aceitarPedido(int i){
        int methodNumber;
        if(prefs.getInt("emp_type_id", 0) == 2){ //se o cara for entrgador
            methodNumber = 4;
        }
        else{
            methodNumber = 3;
        }
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, "https://localhost:44323/api/Pedidos/" + prefs.getInt("id",0) + "/" + TabPedidos.pedidos.optJSONObject(i).optInt("pedidoID") + "/" + methodNumber, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            PedidosAdapter.this.context.UpdatePedidos();
                        }
                        catch(Exception e){
                            System.out.println("DEBUG LINE!");
                            e.printStackTrace();
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
