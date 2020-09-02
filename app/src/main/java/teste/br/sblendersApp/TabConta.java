package teste.br.sblendersApp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabConta extends Fragment {

    Button btnLogoff;
    SharedPreferences prefs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onCreateSavedInstanceBundle){
        View tabConta = inflater.inflate(R.layout.tab_conta, container, false);
        prefs = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        btnLogoff = tabConta.findViewById(R.id.btn_logoff);
        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().remove("id").remove("token").commit();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return tabConta;
    }
}
