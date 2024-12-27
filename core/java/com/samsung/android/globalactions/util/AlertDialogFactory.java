package com.samsung.android.globalactions.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IBinder;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class AlertDialogFactory {
    private AlertDialog mAlertDialog;
    private AlertDialog.Builder mAlertDialogBuilder;
    private final Context mContext;
    private boolean mIsNightMode;
    private EditText mPasswordTextView;

    public AlertDialogFactory(Context context) {
        this.mContext = context;
    }

    private void initAlertDialogBuilder() {
        if (this.mAlertDialog != null && this.mAlertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }
        this.mIsNightMode = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        this.mAlertDialogBuilder = new AlertDialog.Builder(this.mIsNightMode ? new ContextThemeWrapper(this.mContext, 16974120) : this.mContext);
    }

    public AlertDialog getProKioskModeDialog(final Runnable positiveRunnable, final Runnable negativeRunnable) {
        initAlertDialogBuilder();
        this.mAlertDialogBuilder.setTitle(R.string.global_action_pro_kiosk_mode_title);
        this.mAlertDialogBuilder.setMessage(R.string.global_action_pro_kiosk_mode_enter_passcode);
        LinearLayout layout = new LinearLayout(this.mContext);
        layout.setOrientation(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        params.setMargins(50, 0, 50, 0);
        this.mPasswordTextView = new EditText(this.mIsNightMode ? new ContextThemeWrapper(this.mContext, 16974120) : this.mContext);
        this.mPasswordTextView.setInputType(129);
        this.mPasswordTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mPasswordTextView.setGravity(1);
        layout.addView(this.mPasswordTextView, params);
        this.mAlertDialogBuilder.setView(layout);
        this.mAlertDialogBuilder.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.samsung.android.globalactions.util.AlertDialogFactory$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                positiveRunnable.run();
            }
        });
        this.mAlertDialogBuilder.setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.samsung.android.globalactions.util.AlertDialogFactory$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                negativeRunnable.run();
            }
        });
        this.mAlertDialog = this.mAlertDialogBuilder.create();
        this.mAlertDialog.getWindow().getAttributes().setTitle("GlobalActions_ProKioskDialog");
        this.mAlertDialog.getWindow().setType(2008);
        this.mAlertDialog.getWindow().setSoftInputMode(20);
        return this.mAlertDialog;
    }

    public AlertDialog getInsertSimCardDialog() {
        initAlertDialogBuilder();
        this.mAlertDialogBuilder.setTitle(R.string.global_action_insert_sim_card);
        this.mAlertDialogBuilder.setMessage(R.string.global_action_insert_sim_card_message);
        this.mAlertDialogBuilder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        this.mAlertDialog = this.mAlertDialogBuilder.create();
        this.mAlertDialog.getWindow().getAttributes().setTitle("GlobalActions_InsertSimCardDialog");
        this.mAlertDialog.getWindow().setType(2009);
        return this.mAlertDialog;
    }

    public String getProKioskPasswordText() {
        return this.mPasswordTextView.getText().toString();
    }

    public IBinder getProKioskPasswordWindowToken() {
        return this.mPasswordTextView.getWindowToken();
    }
}
