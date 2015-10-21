package com.milena.lamejoraplicacion.model;

import android.content.Context;
import android.database.Cursor;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.milena.lamejoraplicacion.R;
import com.milena.lamejoraplicacion.model.DbAdapter;

import java.util.List;

/**
 * Created by Milena on 31/08/2015.
 */
public class CursorSeriesAdapter extends CursorAdapter {

    private int currentEditOnFocus;
    private DbAdapter db;

    public CursorSeriesAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
        currentEditOnFocus = -1;
        db = DbAdapter.getInstance(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return li.inflate(R.layout.list_item, null);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        final TextView count = (TextView) view.findViewById(R.id.count);
        final EditText editCount = (EditText) view.findViewById(R.id.count_edit);

        final String nameText = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String chapterText = cursor.getInt(cursor.getColumnIndexOrThrow("chapter"))+"";

        name.setText(nameText);
        count.setText(chapterText);

        final long position = cursor.getLong(cursor.getColumnIndex("_id"));

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.setVisibility(View.GONE);
                editCount.setText(count.getText());
                editCount.setVisibility(View.VISIBLE);
                editCount.requestFocus();
                editCount.setSelection(editCount.getText().length());
            }
        });

        editCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int total = Integer.parseInt(editCount.getText() + "");
                    db.updateChapter(position, total);
                    count.setText(total + "");
                    editCount.setVisibility(View.GONE);
                    count.setVisibility(View.VISIBLE);
                    swapCursor(db.getAllItems());
                }
            }
        });

        editCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int total = Integer.parseInt(editCount.getText() + "");
                    db.updateChapter( position, total );
                    editCount.setVisibility(View.GONE);
                    count.setVisibility(View.VISIBLE);
                    count.setText(editCount.getText());
                    handled = true;
                }
                return handled;
            }
        });

        LinearLayout linearMinus = (LinearLayout) view.findViewById(R.id.minus);
        linearMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(count.getText() + "")-1;
                db.updateChapter(position, total);
                count.setText(total+"");
            }
        });

        LinearLayout linearPlus = (LinearLayout) view.findViewById(R.id.plus);
        linearPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(count.getText() + "")+1;
                db.updateChapter(position, total);
                count.setText(total+"");
            }
        });

    }

}
