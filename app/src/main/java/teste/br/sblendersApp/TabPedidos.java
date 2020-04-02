package teste.br.sblendersApp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabPedidos extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onCreateSavedInstanceBundle){
        View inflatedLayout = inflater.inflate(R.layout.tab_pedido, container, false);
        RecyclerView rcv = (RecyclerView)inflatedLayout.findViewById(R.id.rcv1);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(llm);
        rcv.setAdapter(new PedidosAdapter());
        return inflatedLayout;
    }
}
