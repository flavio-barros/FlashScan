package br.ufc.quixada.android.flashscan;

import android.content.Intent;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityPrincipal extends AppCompatActivity {

    List<Documento> documentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        verificarUsuarioLogado();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);

        final ListView listView = (ListView) findViewById(R.id.listView);

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
                return false;
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
                       // shareSelectedItems();
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

        final Documento documento = new Documento("/BB/comprovantes/","Comprovante_25-08-2016_222034.pdf", new Date());
        final Documento documento2 = new Documento("/BB/comprovantes/","Comprovante_09-10-2016_003317.pdf", new Date());
        documentos.add(documento);
        documentos.add(documento2);

        final ArrayAdapter<Documento> documentoArrayAdapter = new ArrayAdapter<Documento>(this, android.R.layout.simple_list_item_1, documentos);
        listView.setAdapter(documentoArrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Documento documento1 = (Documento) parent.getAdapter().getItem(position);
                Toast.makeText(ActivityPrincipal.this, documento1.getNome(), Toast.LENGTH_LONG).show();
                return false;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Documento doc = (Documento) parent.getAdapter().getItem(position);
                Log.d(ActivityPrincipal.class.getSimpleName(), doc.toString());
                Intent intent = new Intent(ActivityPrincipal.this, ActivityDocumento.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Documento", doc);
                startActivity(intent);
                finish();

            }
        });
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

        return true;
    }

    public void btnNovoDocumentoClicked(View view){
        irTelaCapturarImagem();
    }

    protected void irTelaCapturarImagem(){
        Intent intent = new Intent(this, ActivityCapturarImagem.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
