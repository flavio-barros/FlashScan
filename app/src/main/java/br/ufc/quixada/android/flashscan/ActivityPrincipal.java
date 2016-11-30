package br.ufc.quixada.android.flashscan;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufc.quixada.android.flashscan.util.Constantes;

public class ActivityPrincipal extends AppCompatActivity {

    public List<Documento> documentos;
    public Documento d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        verificarUsuarioLogado();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        final ListView listView = (ListView) findViewById(R.id.listView);

        listView.setLongClickable(true);
        listView.setClickable(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                menu.findItem(R.id.menu_rename).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                menu.findItem(R.id.menu_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                menu.findItem(R.id.menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        deleteSelectedItems();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    case R.id.menu_share:
                        shareSelectedItems(d.getCaminho());
                        mode.finish();
                        return true;
                    case R.id.menu_rename:
                        //renameSelectedItem();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            private void shareSelectedItems(String caminho) {
                File file = new File(caminho);
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setDataAndType(Uri.fromFile(file), "application/pdf");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                String title = getResources().getString(R.string.chooser_title_share);
                Intent chooser = Intent.createChooser(sendIntent, title);
                startActivity(chooser);
            }

            private void deleteSelectedItems() {
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }
        });

        documentos = new ArrayList<>();

        Intent intent = getIntent();
        if(intent != null) {
            if(intent.getExtras() != null){
                Documento documento = (Documento) intent.getSerializableExtra("documento");
                documentos.add(documento);
            }
        }

        final Documento documento = new Documento("/BB/comprovantes/", "Comprovante_25-08-2016_222034.pdf", new Date());
        final Documento documento2 = new Documento("/BB/comprovantes/", "Comprovante_09-10-2016_003317.pdf", new Date());
        documentos.add(documento);
        documentos.add(documento2);

        ArrayAdapter<Documento> documentoArrayAdapter = new ArrayAdapter<Documento>(this, android.R.layout.simple_list_item_1, documentos);
        listView.setAdapter(documentoArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Documento doc = (Documento) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ActivityPrincipal.this, ActivityDocumento.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Documento", doc);
                startActivity(intent);
                finish();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Caminho", "que boooooooooooooooooooooooosta");
                Documento d = (Documento) parent.getAdapter().getItem(position);
                Log.d("Caminho", d.getCaminho());
                Toast.makeText(ActivityPrincipal.this, d.getNome(), Toast.LENGTH_SHORT).show();
                return true;

            }
        });


        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null) {
                    if(intent.getExtras() != null){
                        Documento documento = (Documento) intent.getSerializableExtra("documento");
                        documentos.add(documento);

                        final ArrayAdapter<Documento> documentosAdapter = new ArrayAdapter<Documento>(ActivityPrincipal.this, android.R.layout.simple_list_item_1, documentos);
                        listView.setAdapter(documentosAdapter);
                    }
                }
                Toast.makeText(context, "Documento gerado com sucesso", Toast.LENGTH_SHORT).show();
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(Constantes.DOCUMENTO_GERADO));
}

    protected void verificarUsuarioLogado(){

        //Usuário não logado
        if(AccessToken.getCurrentAccessToken() == null){
            irTelaLogin();
        }

    }

    protected void irTelaLogin(){
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void logout(){
        LoginManager.getInstance().logOut();
        irTelaLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sair){
            logout();
        }

        if (id == R.id.criar) {
            irTelaCapturarImagem();
        }

        return true;
    }

    protected void irTelaCapturarImagem(){
        Intent intent = new Intent(this, ActivityCapturarImagem.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
