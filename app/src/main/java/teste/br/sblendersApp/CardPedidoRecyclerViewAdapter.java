package teste.br.sblendersApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import teste.br.sblendersApp.Models.Produto;

public class CardPedidoRecyclerViewAdapter extends RecyclerView.Adapter<CardPedidoRecyclerViewAdapter.CardPedidoViewHolder>{
    JSONObject pedido;
    SharedPreferences prefs;
    RequestQueue queue;
    public CardPedidoRecyclerViewAdapter(JSONObject pedido){
        super();
        this.pedido = pedido;
    }
    public class CardPedidoViewHolder extends  RecyclerView.ViewHolder{
        TextView t;
        public CardPedidoViewHolder(View view){
            super(view);
            prefs =  view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            queue = Volley.newRequestQueue(view.getContext());
            t = (TextView)view;
        }
    }
    @Override
    public int getItemCount() {
        try{return pedido.getJSONArray("produtos").length();}
        catch(Exception e){
            e.printStackTrace();
         return 0;
        }
    }

    @Override
    public CardPedidoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TextView t = new TextView(viewGroup.getContext());
        CardPedidoRecyclerViewAdapter.CardPedidoViewHolder pvh = new CardPedidoRecyclerViewAdapter.CardPedidoViewHolder(t);
        return pvh;

    }

    @Override
    public void onBindViewHolder(CardPedidoRecyclerViewAdapter.CardPedidoViewHolder cardPedidoViewHolder, int i) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://localhost:44323/api/Produtos/" + URLEncoder.encode(pedido.getJSONArray("produtos").getJSONObject(i).getInt("produtoID")+""), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                JSONArray ingredientes = pedido.getJSONArray("produtos").getJSONObject(cardPedidoViewHolder.getAdapterPosition()).getJSONArray("ingredientes");
                                Produto p = new Produto(response);
                                StringBuilder ingredientesString = new StringBuilder();
                                if(ingredientes.length() > 0) {
                                    ingredientesString.append("(");
                                    for (int i = 0; i < ingredientes.length(); i++) {
                                        ingredientesString.append(ingredientes.getJSONObject(i).getInt("quantidade") + "x " + p.getIngNameByPPID(ingredientes.getJSONObject(i).getInt("produtoIngredienteID")) + "/ ");
                                    }
                                    ingredientesString.append(")");
                                }
                                cardPedidoViewHolder.t.setText(pedido.getJSONArray("produtos").getJSONObject(cardPedidoViewHolder.getAdapterPosition()).getInt("pedidoProdutoQtde")+ "x " + response.getString("name") + ingredientesString.toString());
                            }
                            catch(Exception e){
                                cardPedidoViewHolder.t.setText("Erro");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                        }
                    }
                    ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", prefs.getString("token", ""));
                    return params;
                }
            };
            queue.add(jsonObjectRequest);
            queue.start();
        }
        catch (Exception e){
        e.printStackTrace();
        }

        cardPedidoViewHolder.t.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
