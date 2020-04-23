package teste.br.sblendersApp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ProdutosRecyclerViewAdapter extends RecyclerView.Adapter<ProdutosRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ConstraintLayout l;
        public ViewHolder(View view){
            super(view);
            tv = view.findViewById(R.id.item_produto_tv);
            l = view.findViewById(R.id.item_produto_layout);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public ProdutosRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_produto_view, viewGroup, false);
        ProdutosRecyclerViewAdapter.ViewHolder pvh = new ProdutosRecyclerViewAdapter.ViewHolder(v);
        return pvh;
    }

    public void onBindViewHolder(ProdutosRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv.setText("Produto" + i + "(Gelo x3, Mozzarella x2)*" );
        viewHolder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext().getApplicationContext(), ("clicou" + i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
