package com.hasfun.seoulsubway.evernote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;
import com.hasfun.seoulsubway.R;

import java.util.List;
/**
 * This sample shows how to list Evernote notebooks and create a note in the specific notebook.
 * <p/>
 * class created by @tylersmithnet
 */
public class CreateNote extends ParentActivity {

  /**
   * *************************************************************************
   * The following values and code are simply part of the demo application.  *
   * *************************************************************************
   */

  private static final String LOGTAG = "CreateNote";

  private EditText mEditTextTitle;
  private EditText mEditTextContent;
  private Button mBtnSave;
  private Button mBtnSelect;

  private String mSelectedNotebookGuid;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create_note);

    mEditTextTitle = (EditText) findViewById(R.id.text_title);
    mEditTextContent = (EditText) findViewById(R.id.text_content);
    mBtnSelect = (Button) findViewById(R.id.select_button);
    mBtnSave = (Button) findViewById(R.id.save_button);
    
    // Login
    mEvernoteSession.authenticate(this);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    Intent intent = getIntent(); //
    
    Log.d(LOGTAG, intent.getStringExtra("title"));
    Log.d(LOGTAG, intent.getStringExtra("content"));
    
    mEditTextTitle.setText(intent.getStringExtra("title"));
    mEditTextContent.setText(intent.getStringExtra("content"));
  }
  
  /**
   * Saves text field content as note to selected notebook, or default notebook if no notebook select
   */
  public void saveNote(View view) {
    String title = mEditTextTitle.getText().toString();
    String content = mEditTextContent.getText().toString();
    if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
      Toast.makeText(getApplicationContext(), R.string.empty_content_error, Toast.LENGTH_LONG).show();
    }

    Note note = new Note();
    note.setTitle(title);

    //TODO: line breaks need to be converted to render in ENML
    note.setContent(EvernoteUtil.NOTE_PREFIX + content + EvernoteUtil.NOTE_SUFFIX);

    //If User has selected a notebook guid, assign it now
    if (!TextUtils.isEmpty(mSelectedNotebookGuid)) {
      note.setNotebookGuid(mSelectedNotebookGuid);
    }
    showDialog(DIALOG_PROGRESS);
    try {
      mEvernoteSession.getClientFactory().createNoteStoreClient().createNote(note, new OnClientCallback<Note>() {
        @Override
        public void onSuccess(Note data) {
          Toast.makeText(getApplicationContext(), R.string.note_saved, Toast.LENGTH_LONG).show();
          removeDialog(DIALOG_PROGRESS);
        }

        @Override
        public void onException(Exception exception) {
          Log.e(LOGTAG, "Error saving note", exception);
          Toast.makeText(getApplicationContext(), R.string.error_saving_note, Toast.LENGTH_LONG).show();
          removeDialog(DIALOG_PROGRESS);
        }
      });
    } catch (TTransportException exception) {
      Log.e(LOGTAG, "Error creating notestore", exception);
      Toast.makeText(getApplicationContext(), R.string.error_creating_notestore, Toast.LENGTH_LONG).show();
      removeDialog(DIALOG_PROGRESS);
    }

  }

  /**
   * Select notebook, create AlertDialog to pick notebook guid
   */
  public void selectNotebook(View view) {

    try {
      mEvernoteSession.getClientFactory().createNoteStoreClient().listNotebooks(new OnClientCallback<List<Notebook>>() {
        int mSelectedPos = -1;

        @Override
        public void onSuccess(final List<Notebook> notebooks) {
          CharSequence[] names = new CharSequence[notebooks.size()];
          int selected = -1;
          Notebook notebook = null;
          for (int index = 0; index < notebooks.size(); index++) {
            notebook = notebooks.get(index);
            names[index] = notebook.getName();
            if (notebook.getGuid().equals(mSelectedNotebookGuid)) {
              selected = index;
            }
          }

          AlertDialog.Builder builder = new AlertDialog.Builder(CreateNote.this);

          builder
              .setSingleChoiceItems(names, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  mSelectedPos = which;
                }
              })
              .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  if (mSelectedPos > -1) {
                    mSelectedNotebookGuid = notebooks.get(mSelectedPos).getGuid();
                  }
                  dialog.dismiss();
                }
              })
              .create()
              .show();
        }

        @Override
        public void onException(Exception exception) {
          Log.e(LOGTAG, "Error listing notebooks", exception);
          Toast.makeText(getApplicationContext(), R.string.error_listing_notebooks, Toast.LENGTH_LONG).show();
          removeDialog(DIALOG_PROGRESS);
        }
      });
    } catch (TTransportException exception) {
      Log.e(LOGTAG, "Error creating notestore", exception);
      Toast.makeText(getApplicationContext(), R.string.error_creating_notestore, Toast.LENGTH_LONG).show();
      removeDialog(DIALOG_PROGRESS);
    }
  }
}
