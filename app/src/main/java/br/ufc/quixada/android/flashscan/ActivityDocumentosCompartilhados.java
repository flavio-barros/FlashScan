package br.ufc.quixada.android.flashscan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.android.flashscan.util.BancoDeDados;
import br.ufc.quixada.android.flashscan.util.Constantes;

public class ActivityDocumentosCompartilhados extends Activity {

    private Button btnVoltar;
    private ListView listDocsCompartilhados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_compartilhados);

        btnVoltar = (Button) findViewById(R.id.btn_voltar);
        listDocsCompartilhados = (ListView) findViewById(R.id.list_documentos_compartilhados);

        listDocsCompartilhados.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Documento doc = (Documento) parent.getAdapter().getItem(position);
                Log.d(ActivityPrincipal.class.getSimpleName(), doc.toString());
                Intent intent = new Intent(ActivityDocumentosCompartilhados.this, ActivityDocumento.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Documento", doc);
                startActivity(intent);
                finish();

            }
        });

        final ProgressDialog dialog = ProgressDialog.show(ActivityDocumentosCompartilhados.this, "",
                "Carregando...", true);

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent != null) {
                    if(intent.getExtras() != null){
                        Documento documento1 = (Documento) intent.getSerializableExtra("documento1");
                        Documento documento2 = (Documento) intent.getSerializableExtra("documento2");

                        List<Documento> documentos = new ArrayList<Documento>();
                        documentos.add(documento1);
                        documentos.add(documento2);

                        final ArrayAdapter<Documento> documentosAdapter = new ArrayAdapter<Documento>(ActivityDocumentosCompartilhados.this, android.R.layout.simple_list_item_1, documentos);
                        listDocsCompartilhados.setAdapter(documentosAdapter);

                        dialog.hide();
                    }
                }
                Toast.makeText(context, "Documentos recuperados", Toast.LENGTH_SHORT).show();
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(Constantes.RECUPERAR_DOCUMENTOS_COMPARTILHADOS));

    }

    public void btnVoltarClicked(View view){
        irTelaPrincipal();
    }

    public void irTelaPrincipal(){
        Intent intent = new Intent(this, ActivityPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
