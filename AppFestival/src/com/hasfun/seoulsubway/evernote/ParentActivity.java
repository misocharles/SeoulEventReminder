package com.hasfun.seoulsubway.evernote;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.evernote.client.android.EvernoteSession;
import com.hasfun.seoulsubway.R;
import com.hasfun.seoulsubway.common.Constants;

/**
 * This is the parent activity that all sample activites extend from. This creates the Evernote Session in onCreate
 * and stores the CONSUMER_KEY and CONSUMER_SECRET
 *
 * In this example, it also takes care of dialogs
 */
public class ParentActivity extends Activity {

  /**
   * ************************************************************************
   * You MUST change the following values to run this sample application.    *
   * *************************************************************************
   */

  // Your Evernote API key. See http://dev.evernote.com/documentation/cloud/
  // Please obfuscate your code to help keep these values secret.
  private static final String CONSUMER_KEY = Constants.EVERNOTE_KEY;
  private static final String CONSUMER_SECRET = Constants.EVERNOTE_SECRET;

  // Initial development is done on Evernote's testing service, the sandbox.
  // Change to HOST_PRODUCTION to use the Evernote production service
  // once your code is complete, or HOST_CHINA to use the Yinxiang Biji
  // (Evernote China) production service.
  private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

  /**
   * ************************************************************************
   * The following values are simply part of the demo application.           *
   * *************************************************************************
   */

  protected EvernoteSession mEvernoteSession;
  protected final int DIALOG_PROGRESS = 101;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //Set up the Evernote Singleton Session
    mEvernoteSession = EvernoteSession.getInstance(this, CONSUMER_KEY, CONSUMER_SECRET, EVERNOTE_SERVICE);
  }

  // using createDialog, could use Fragments instead
  @SuppressWarnings("deprecation")
  @Override
  protected Dialog onCreateDialog(int id) {
    switch (id) {
      case DIALOG_PROGRESS:
        return new ProgressDialog(ParentActivity.this);
    }
    return super.onCreateDialog(id);
  }

  @Override
  @SuppressWarnings("deprecation")
  protected void onPrepareDialog(int id, Dialog dialog) {
    switch (id) {
      case DIALOG_PROGRESS:
        ((ProgressDialog) dialog).setIndeterminate(true);
        dialog.setCancelable(false);
        ((ProgressDialog) dialog).setMessage(getString(R.string.esdk__loading));
    }
  }
}
