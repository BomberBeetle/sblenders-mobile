package teste.br.sblendersApp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardPedidoRecyclerViewAdapter extends RecyclerView.Adapter<CardPedidoRecyclerViewAdapter.CardPedidoViewHolder>{
    public class CardPedidoViewHolder extends  RecyclerView.ViewHolder{
        TextView t;
        public CardPedidoViewHolder(View view){
            super(view);
            t = (TextView)view;
        }
    }
    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public CardPedidoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TextView t = new TextView(viewGroup.getContext());
        CardPedidoRecyclerViewAdapter.CardPedidoViewHolder pvh = new CardPedidoRecyclerViewAdapter.CardPedidoViewHolder(t);
        return pvh;

    }

    @Override
    public void onBindViewHolder(CardPedidoRecyclerViewAdapter.CardPedidoViewHolder personViewHolder, int i) {
        personViewHolder.t.setText("Prato " +Integer.toString(i + 1));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
