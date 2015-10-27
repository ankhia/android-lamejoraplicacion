package com.milena.lamejoraplicacion.interfaz;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.milena.lamejoraplicacion.R;
import com.milena.lamejoraplicacion.beans.Serie;
import com.milena.lamejoraplicacion.handlers.AddSerieHandler;
import com.milena.lamejoraplicacion.handlers.EditSerieHandler;
import com.milena.lamejoraplicacion.handlers.SelectRowHandler;
import com.milena.lamejoraplicacion.model.CursorSeriesAdapter;
import com.milena.lamejoraplicacion.model.DbAdapter;
import com.milena.lamejoraplicacion.model.PopupAddSerie;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private AddSerieHandler addSerieHandler;

    private EditSerieHandler editSerieHandler;

    private CursorSeriesAdapter listAdapter;

    private SelectRowHandler selectRowHandler;

    private DbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(listAdapter);

    }

    public void bind( ){

        db = DbAdapter.getInstance( this);
        db.open();
        selectRowHandler = new SelectRowHandler() {
            @Override
            public void onRowSelected(long position) {
                invalidateOptionsMenu();
            }
        };

        listAdapter = new CursorSeriesAdapter(this, db.getAllItems(), 0, selectRowHandler);

        addSerieHandler = new AddSerieHandler() {
            @Override
            public void onSerieAdded(Serie serie) {
                if(serie!=null) {
                    db.insertSerie(serie.getType(), serie.getName(), serie.getEpisode(), serie.getImage() == null ? "" : serie.getImage(), serie.getSeason() != null ? serie.getSeason(): -1);
                    listAdapter.swapCursor(db.getAllItems());
                }
            }
        };

        editSerieHandler = new EditSerieHandler() {
            @Override
            public void onEditSerie(Serie s) {
                db.updateSerie(s.getIdSerie(), s.getType(), s.getName(), s.getEpisode(), s.getSeason());
                listAdapter.swapCursor(db.getAllItems());
            }
        };

        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Set<Long> rowsSelected = listAdapter.getRowsSelected();
        if( rowsSelected.size() > 0 ){
            if(rowsSelected.size() == 1)
                menu.findItem(R.id.action_edit).setVisible(true);
            else
                menu.findItem(R.id.action_edit).setVisible(false);
            menu.findItem(R.id.action_delete).setVisible(true);
        }else{
            menu.findItem(R.id.action_edit).setVisible(false);
            menu.findItem(R.id.action_delete).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Set<Long> rowsSelected = listAdapter.getRowsSelected();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            PopupAddSerie popup = new PopupAddSerie(this);

            popup.setEditSerieHandler(editSerieHandler);
            Serie s = new Serie();
            for ( Long l : rowsSelected ){

            }
            popup.setSerie(null);
            popup.showAtLocation(listView, Gravity.CENTER,0,0);
        }

        if (id == R.id.action_delete) {
            for ( Long l : rowsSelected ){
                db.deleteSerie(l);
            }
            listAdapter.swapCursor(db.getAllItems());
            listAdapter.clear();
            invalidateOptionsMenu();
        }

        if( id == R.id.action_add ){
            PopupAddSerie popup = new PopupAddSerie(this);
            popup.setAddSerieHandler(addSerieHandler);
            popup.showAtLocation(listView, Gravity.CENTER,0,0);
        }

        return super.onOptionsItemSelected(item);
    }
}
