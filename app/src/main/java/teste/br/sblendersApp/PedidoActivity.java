package teste.br.sblendersApp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

public class PedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pedido " + getIntent().getIntExtra("PEDIDO_NUM", 0));
        actionBar.setDisplayHomeAsUpEnabled(true);
        RecyclerView rcv = findViewById(R.id.rcv_produtos);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcv.setLayoutManager(llm);
        rcv.setAdapter(new ProdutosRecyclerViewAdapter());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
