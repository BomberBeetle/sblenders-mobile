package teste.br.sblendersApp;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {
    SharedPreferences prefs;
    public class PedidosViewHolder extends  RecyclerView.ViewHolder{
        TextView pedidoNumTxt;
        RecyclerView rcvPratos;
        Button btnRejeita;
        Button btnAceita;
        ImageButton btnCollapse;
        TextView ordens;

        TextView ordensTitle;
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
            spaghetti = view.findViewById(R.id.card_btn_retract);
            collapse();
        }
        public void collapse(){
            btnCollapse.setImageDrawable(btnCollapse.getResources().getDrawable(R.drawable.expander_open_holo_light));
            rcvPratos.setVisibility(View.GONE);
            btnRejeita.setVisibility(View.GONE);
            btnAceita.setVisibility(View.GONE);
            ordens.setVisibility(View.GONE);
            ordensTitle.setVisibility(View.GONE);
            collapsed = true;
        }
        public void open(){
            btnCollapse.setImageDrawable(btnCollapse.getResources().getDrawable(R.drawable.expander_close_holo_light));
            rcvPratos.setVisibility(View.VISIBLE);
            btnRejeita.setVisibility(View.VISIBLE);
            btnAceita.setVisibility(View.VISIBLE);
            ordens.setVisibility(View.VISIBLE);
            ordensTitle.setVisibility(View.VISIBLE );
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

        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder personViewHolder, int i) {
        try{
        personViewHolder.pedidoNumTxt.setText("Pedido " + TabPedidos.pedidos.getJSONObject(i).getInt("pedidoID"));
        personViewHolder.btnRejeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(view.getContext(), PedidoActivity.class);
                intent.putExtra("PEDIDO_NUM", i);
                view.getContext().startActivity(intent);
                */
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
                            }
                            catch(Exception e){

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

        }
        catch (Exception e){

        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
