package teste.br.sblendersApp;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

public class CardPedidoRecyclerViewAdapter extends RecyclerView.Adapter<CardPedidoRecyclerViewAdapter.CardPedidoViewHolder>{
    JSONObject pedido;
    public CardPedidoRecyclerViewAdapter(JSONObject pedido){
        super();
        this.pedido = pedido;
    }
    public class CardPedidoViewHolder extends  RecyclerView.ViewHolder{
        TextView t;
        public CardPedidoViewHolder(View view){
            super(view);
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

            cardPedidoViewHolder.t.setText(pedido.getJSONArray("produtos").getJSONObject(i).getInt("pedidoProdutoQtde")+ "x ID" + pedido.getJSONArray("produtos").getJSONObject(i).getInt("produtoID"));
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
