package br.ufc.quixada.android.flashscan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ActivityPrincipal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ListView listView = (ListView) findViewById(R.id.listView);

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

        final List<String> nomes = new ArrayList<>();
        nomes.add("Sense 8");
        nomes.add("WestWorld");
        nomes.add("Game of Thrones");
        nomes.add("House of Cards");

        final ArrayAdapter<String> nomesApapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(nomesApapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String series = nomesApapter.getItem(position);
                Toast.makeText(ActivityPrincipal.this, series.toString(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

}
