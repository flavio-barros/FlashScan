package br.ufc.quixada.android.flashscan;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;

public class ActivityDocumento extends AppCompatActivity {

    Documento doc;
    TextView info_doc_nome;
    TextView info_doc_caminho;
    TextView info_doc_data;
    ToggleButton publico;

    Button abrir;
    Button voltar;
    Button mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento);

        final Intent intent = getIntent();
        doc = (Documento) intent.getSerializableExtra("Documento");
        Log.d(ActivityDocumento.class.getSimpleName(), doc.toString());
        info_doc_nome = (TextView) findViewById(R.id.info_doc_nome);
        info_doc_nome.setText("Nome: "+ doc.getNome());
        info_doc_caminho = (TextView) findViewById(R.id.info_doc_caminho);
        info_doc_caminho.setText("Caminho: "+doc.getCaminho());
        info_doc_data = (TextView) findViewById(R.id.info_doc_data);
        info_doc_data.setText("Data: "+doc.getDataCriacao().toString());


        abrir = (Button) findViewById(R.id.abrir);
        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(doc.caminho);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setDataAndType(Uri.fromFile(file),"application/pdf");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Log.d("DIRETORIO:", Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ doc.getNome());

                String title = getResources().getString(R.string.chooser_title);
                Intent chooser = Intent.createChooser(sendIntent, title);
                startActivity(chooser);
            }
        });

        voltar = (Button) findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActivityDocumento.this, ActivityPrincipal.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
            }
        });

        mapa = (Button) findViewById(R.id.btnMapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActivityDocumento.this, MapsActivity.class);
                intent1.putExtra("latitude", doc.getLatitude());
                intent1.putExtra("longitude", doc.getLongitude());
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
            }
        });

        publico = (ToggleButton) findViewById(R.id.publico);

        publico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publico.getText() == publico.getTextOn()) {
                    doc.setPublico(true);
                } else {
                    doc.setPublico(false);
                }

                Log.d("Status", String.valueOf(doc.getPublico()));
            }
        });
    }
}
