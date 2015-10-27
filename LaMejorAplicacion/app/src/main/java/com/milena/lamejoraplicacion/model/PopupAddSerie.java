package com.milena.lamejoraplicacion.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.milena.lamejoraplicacion.R;
import com.milena.lamejoraplicacion.beans.Serie;
import com.milena.lamejoraplicacion.handlers.AddSerieHandler;
import com.milena.lamejoraplicacion.handlers.EditSerieHandler;
import com.milena.lamejoraplicacion.interfaz.MainActivity;

/**
 * Created by ASUS on 22/10/2015.
 */
public class PopupAddSerie extends PopupWindow {

    private Activity context;
    private boolean hasSeasonActive;
    private View layout;
    private Serie serie;
    private AddSerieHandler addSerieHandler;
    private EditSerieHandler editSerieHandler;

    private EditText txtSeason;
    private ImageView icHasSeason;
    private EditText txtNameSerie;
    private EditText txtEpisode;
    private TextView txtError;

    public PopupAddSerie( Activity context ){
        this.context = context;
        hasSeasonActive = false;
        this.addSerieHandler = addSerieHandler;
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.add_serie_layout);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.add_serie, viewGroup);

        setBackgroundDrawable(new BitmapDrawable());
        setContentView(layout);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        init();
    }

    public void init(){
        txtSeason = (EditText) layout.findViewById(R.id.txt_popup_serie_season);
        icHasSeason = (ImageView) layout.findViewById(R.id.ic_popup_has_season);
        txtNameSerie = (EditText) layout.findViewById(R.id.txt_popup_serie_name);
        txtEpisode = (EditText) layout.findViewById(R.id.txt_popup_serie_episode);
        txtError = (TextView) layout.findViewById(R.id.lbl_popup_error);
        txtError.setVisibility(View.GONE);
        if( serie != null ){
            if(serie.getSeason()!=null) {
                icHasSeason.setImageResource(R.drawable.ic_check_box_black_24dp);
                hasSeasonActive = true;
                txtSeason.setText(serie.getSeason()+"");
            }
            txtNameSerie.setText(serie.getName());
            txtEpisode.setText(serie.getEpisode());
        }

        icHasSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasSeasonActive) {
                    icHasSeason.setImageResource(R.drawable.ic_check_box_black_24dp);
                    txtSeason.setVisibility(View.VISIBLE);
                    txtSeason.requestFocus();
                    hasSeasonActive = true;
                } else {
                    icHasSeason.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    txtSeason.setVisibility(View.GONE);
                    hasSeasonActive = false;
                }
            }
        });


        ImageView icDone = (ImageView) layout.findViewById(R.id.ic_popup_done);
        ImageView icClose = (ImageView) layout.findViewById(R.id.ic_popup_close);

        icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        icDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateView() ) {
                    txtError.setVisibility(View.GONE);
                    Serie s = new Serie();
                    s.setEpisode(Integer.parseInt(txtEpisode.getText().toString().trim()));
                    s.setName(txtNameSerie.getText().toString());
                    s.setType(DbHelper.ANIME);
                    if (hasSeasonActive)
                        s.setSeason(Integer.parseInt(txtSeason.getText().toString().trim()));

                    if (addSerieHandler != null)
                        addSerieHandler.onSerieAdded(s);
                    else if (editSerieHandler != null) {
                        if (serie != null && serie.getIdSerie() != null)
                            s.setIdSerie(serie.getIdSerie());
                        editSerieHandler.onEditSerie(s);
                    }
                    dismiss();
                }else{
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Por favor complete todos los campos");
                }

            }
        });
    }

    public void setAddSerieHandler(AddSerieHandler addSerieHandler) {
        this.addSerieHandler = addSerieHandler;
    }

    public void setEditSerieHandler(EditSerieHandler editSerieHandler) {
        this.editSerieHandler = editSerieHandler;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public boolean validateView( ){
        boolean valide = true;
        if( txtEpisode != null && ( txtEpisode.getText() == null || txtEpisode.getText().toString().trim().isEmpty() ) ) valide = false;
        if( txtNameSerie != null && ( txtNameSerie.getText() == null || txtNameSerie.getText().toString().trim().isEmpty() ) ) valide = false;
        if( hasSeasonActive && txtSeason != null && ( txtSeason.getText() == null || txtSeason.getText().toString().trim().isEmpty() ) ) valide = false;
        return valide;
    }
}
