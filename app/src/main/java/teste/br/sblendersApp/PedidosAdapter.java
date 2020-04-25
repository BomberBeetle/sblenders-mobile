package teste.br.sblendersApp;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {

    public class PedidosViewHolder extends  RecyclerView.ViewHolder{
        TextView pedidoNumTxt;
        RecyclerView rcvPratos;
        Button btnRejeita;
        Button btnAceita;
        ImageButton btnCollapse;
        TextView ordens;
        TextView ordensTitle;
        boolean collapsed;
        public PedidosViewHolder(View view){
            super(view);
            pedidoNumTxt = view.findViewById(R.id.card_text_n_pedido);
            rcvPratos = view.findViewById(R.id.card_pedido_rcv);
            btnRejeita = view.findViewById(R.id.btn_rejeita_pedido);
            btnAceita = view.findViewById(R.id.btn_pronto_pedido);
            btnCollapse = view.findViewById(R.id.card_collapse_button);
            ordens = view.findViewById(R.id.txt_instrucoes);
            ordensTitle = view.findViewById(R.id.card_text_instrucoes);
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
            btnCollapse.setImageDrawable(btnCollapse.getResources().getDrawable(R.drawable.expander_open_holo_light));
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
        return 20;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pedido, viewGroup, false);
        PedidosViewHolder pvh = new PedidosViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder personViewHolder, int i) {
        personViewHolder.pedidoNumTxt.setText("Pedido " +Integer.toString(i));
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
        personViewHolder.btnCollapse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(personViewHolder.collapsed){
                    personViewHolder.open();
                }
                else{
                    personViewHolder.collapse();
                }
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
