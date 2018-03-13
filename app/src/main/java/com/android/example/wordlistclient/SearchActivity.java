package com.android.example.wordlistclient;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.android.example.wordlistclient.Contract.WordList.KEY_WORD;

/**
 * Created by bryon on 3/11/18.
 */

public class SearchActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mEditWordView;
    private Context mContext;
    private String queryUri = Contract.CONTENT_URI.toString();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mTextView = findViewById(R.id.search_result);
        mEditWordView = findViewById(R.id.search_word);
        mContext = getApplicationContext();

    }

    //todo: implement search
    public void showResult(View view) {
        String word = String.valueOf(mEditWordView.getText());
        mEditWordView.setText("");
        mTextView.setText(Html.fromHtml(getString(R.string.search_result_prefix) + " <b>" + word + "</b>:<br/><br/>"));

        String[] projection = new String[]{Contract.CONTENT_PATH};
        String searchString = "%" + word + "%";
        String where = KEY_WORD + " LIKE ?";
        String[] whereArgs = new String[]{word};

        Cursor cursor = mContext.getContentResolver().query(Uri.parse(queryUri),
        projection,
        where,
        whereArgs,
        null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            int index;
            String result;
            do {
                index = cursor.getColumnIndex(KEY_WORD);
                result = cursor.getString(index);
                mTextView.append("\n" + result);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
