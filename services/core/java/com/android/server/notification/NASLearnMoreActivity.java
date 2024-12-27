package com.android.server.notification;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class NASLearnMoreActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AlertDialog create =
                new AlertDialog.Builder(this)
                        .setMessage(R.string.roamingText9)
                        .setPositiveButton(
                                R.string.ok,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.server.notification.NASLearnMoreActivity.1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        NASLearnMoreActivity.this.finish();
                                    }
                                })
                        .create();
        create.getWindow().setType(2003);
        create.show();
    }
}
