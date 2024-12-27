package com.android.server.am;

import android.R;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class BaseErrorDialog extends AlertDialog {
    public boolean mConsuming;
    public final AnonymousClass2 mHandler;
    public AnonymousClass1 mReceiver;

    /* renamed from: -$$Nest$msetEnabled, reason: not valid java name */
    public static void m179$$Nest$msetEnabled(BaseErrorDialog baseErrorDialog, boolean z) {
        Button button = (Button) baseErrorDialog.findViewById(R.id.button1);
        if (button != null) {
            button.setEnabled(z);
        }
        Button button2 = (Button) baseErrorDialog.findViewById(R.id.button2);
        if (button2 != null) {
            button2.setEnabled(z);
        }
        Button button3 = (Button) baseErrorDialog.findViewById(R.id.button3);
        if (button3 != null) {
            button3.setEnabled(z);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.am.BaseErrorDialog$2] */
    public BaseErrorDialog(Context context) {
        super(context, R.style.Theme.Leanback.Settings.Dialog.Alert);
        this.mConsuming = true;
        this.mHandler =
                new Handler() { // from class: com.android.server.am.BaseErrorDialog.2
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i = message.what;
                        BaseErrorDialog baseErrorDialog = BaseErrorDialog.this;
                        if (i == 0) {
                            baseErrorDialog.mConsuming = false;
                            BaseErrorDialog.m179$$Nest$msetEnabled(baseErrorDialog, true);
                        } else if (i == 1) {
                            BaseErrorDialog.m179$$Nest$msetEnabled(baseErrorDialog, false);
                        }
                    }
                };
        context.assertRuntimeOverlayThemable();
        getWindow().setType(2003);
        getWindow().setFlags(131072, 131072);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Error Dialog");
        getWindow().setAttributes(attributes);
    }

    public void closeDialog() {
        if (((AlertDialog) this).mCancelable) {
            cancel();
        } else {
            dismiss();
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mConsuming) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.am.BaseErrorDialog$1] */
    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        sendEmptyMessage(1);
        AnonymousClass2 anonymousClass2 = this.mHandler;
        anonymousClass2.sendMessageDelayed(anonymousClass2.obtainMessage(0), 1000L);
        if (this.mReceiver == null) {
            this.mReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.BaseErrorDialog.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS"
                                    .equals(intent.getAction())) {
                                BaseErrorDialog.this.closeDialog();
                            }
                        }
                    };
            getContext()
                    .registerReceiver(
                            this.mReceiver,
                            new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"),
                            2);
        }
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        if (this.mReceiver != null) {
            try {
                getContext().unregisterReceiver(this.mReceiver);
            } catch (IllegalArgumentException e) {
                Slog.e("BaseErrorDialog", "unregisterReceiver threw exception: " + e.getMessage());
            }
            this.mReceiver = null;
        }
    }
}
