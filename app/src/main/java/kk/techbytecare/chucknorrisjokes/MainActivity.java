package kk.techbytecare.chucknorrisjokes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.library.bubbleview.BubbleTextView;
import com.google.gson.Gson;

import kk.techbytecare.chucknorrisjokes.Helper.Helper;
import kk.techbytecare.chucknorrisjokes.Model.ChuckNorris;

public class MainActivity extends AppCompatActivity {

    private String API_URL = "http://api.icndb.com/jokes/random";

    Button btnJoke;
    BubbleTextView txtJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJoke = findViewById(R.id.btnJoke);
        txtJoke = findViewById(R.id.txtJoke);

        btnJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<String,Void,String> asyncTask = new AsyncTask<String, Void, String>() {

                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog.setTitle("Please wait...");
                        dialog.show();
                    }

                    @Override
                    protected String doInBackground(String... params) {

                        Helper helper = new Helper();

                        return helper.getHTTPData(API_URL);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        dialog.dismiss();

                        ChuckNorris chuckNorris = new Gson().fromJson(s,ChuckNorris.class);
                        txtJoke.setText(chuckNorris.value.joke);

                        if (txtJoke.getVisibility() == View.INVISIBLE)  {
                            txtJoke.setVisibility(View.VISIBLE);
                        }
                    }
                };

                asyncTask.execute();
            }
        });
    }
}
