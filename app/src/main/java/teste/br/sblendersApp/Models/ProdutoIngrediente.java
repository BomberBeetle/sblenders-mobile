package teste.br.sblendersApp.Models;

import org.json.JSONObject;

public class ProdutoIngrediente {
    int pIngredientID;
    String name;
    public ProdutoIngrediente(JSONObject o ){
        pIngredientID = o.optInt("pIngredientID");
        name = o.optString("name");
    }

}
