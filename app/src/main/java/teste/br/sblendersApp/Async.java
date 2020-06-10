package teste.br.sblendersApp;

import android.os.AsyncTask;
import android.util.Log;

public class Async extends AsyncTask<String, Integer, Object[]> {
    @Override
    protected Object[] doInBackground(String... strings) {
        return new Object[0];
    }

    /*@Override
    protected <HttpClient> Object[] doInBackground(String... params) {
        // TODO Auto-generated method stub

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0].toString());
        Log.d("http client post set","");

        try{
            HttpResponse response = httpclient.execute(httppost);
            Log.d("YourAsync","Executed");
            return new Object[]{response, new BasicResponseHandler().handleResponse(response)};
        }catch(Exception e){
            Log.d("" + e, "");
        }
        return new Object[0];

    }*/
}
