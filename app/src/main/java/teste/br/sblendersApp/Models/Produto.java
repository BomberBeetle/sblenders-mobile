package teste.br.sblendersApp.Models;

import org.json.JSONObject;

import java.util.ArrayList;


public class Produto {
    ProdutoIngrediente[] ingredientes;
    public Produto(JSONObject o){
        ArrayList<ProdutoIngrediente> ings = new ArrayList<ProdutoIngrediente>();
        for(int i = 0; i < o.optJSONArray("ingredientes").length(); i++){
            ings.add(new ProdutoIngrediente( o.optJSONArray("ingredientes").optJSONObject(i)));
        }
        ingredientes = ings.toArray(new ProdutoIngrediente[0]);
    }
    public String getIngNameByPPID(int ppid){
        for(int i = 0; i < ingredientes.length; i++){
            if(ingredientes[i].pIngredientID == ppid){
                return  ingredientes[i].name;
            }
        }
        return "Erro";
    }
}
