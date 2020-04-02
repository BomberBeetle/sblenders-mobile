package teste.br.sblendersApp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {

    public class PedidosViewHolder extends  RecyclerView.ViewHolder{
        TextView tv;
        RecyclerView rcvPratos;
        public PedidosViewHolder(View view){
            super(view);
            tv = (TextView)view.findViewById(R.id.card_text_n_pedido);
            rcvPratos = (RecyclerView)view.findViewById(R.id.card_pedido_rcv);
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pedido, viewGroup, false);
        PedidosViewHolder pvh = new PedidosViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder personViewHolder, int i) {
        personViewHolder.tv.setText("Pedido " +Integer.toString(i));
        LinearLayoutManager llm = new LinearLayoutManager(personViewHolder.itemView.getContext());
        personViewHolder.rcvPratos.setLayoutManager(llm);
        personViewHolder.rcvPratos.setAdapter(new CardPedidoRecyclerViewAdapter());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
