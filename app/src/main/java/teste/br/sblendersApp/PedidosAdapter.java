package teste.br.sblendersApp;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {

    public class PedidosViewHolder extends  RecyclerView.ViewHolder{
        TextView tv;
        RecyclerView rcvPratos;
        Button btnDetalhes;
        public PedidosViewHolder(View view){
            super(view);
            tv = view.findViewById(R.id.card_text_n_pedido);
            rcvPratos = view.findViewById(R.id.card_pedido_rcv);
            btnDetalhes = view.findViewById(R.id.btn_detalhes_pedido);
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
        personViewHolder.btnDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PedidoActivity.class);
                intent.putExtra("PEDIDO_NUM", i);
                view.getContext().startActivity(intent);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(personViewHolder.itemView.getContext());
        personViewHolder.rcvPratos.setLayoutManager(llm);
        personViewHolder.rcvPratos.setAdapter(new CardPedidoRecyclerViewAdapter());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
