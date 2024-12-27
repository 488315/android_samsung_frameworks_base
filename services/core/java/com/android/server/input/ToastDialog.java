package com.android.server.input;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.android.internal.os.SomeArgs;
import com.android.server.DisplayThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ToastDialog {
    public AlertDialog mAlertDialog;
    public final Context mContext;
    public final ToastDialogHandler mHandler = new ToastDialogHandler(DisplayThread.get().getLooper());
    public Toast mToast;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ToastDialogHandler extends Handler {
        public ToastDialogHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = R.style.Theme.DeviceDefault.Light.Dialog.Alert;
            ToastDialog toastDialog = ToastDialog.this;
            switch (i) {
                case 1:
                    AlertDialog alertDialog = toastDialog.mAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        toastDialog.mAlertDialog = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    break;
                case 8:
                case 9:
                default:
                    return;
                case 10:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    String str = (String) someArgs.arg1;
                    String str2 = (String) someArgs.arg2;
                    AlertDialog alertDialog2 = toastDialog.mAlertDialog;
                    if (alertDialog2 != null) {
                        alertDialog2.dismiss();
                        toastDialog.mAlertDialog = null;
                    }
                    if (toastDialog.mContext.getResources().getConfiguration().isNightModeActive()) {
                        i2 = 16974545;
                    }
                    AlertDialog.Builder message2 = new AlertDialog.Builder(toastDialog.mContext, i2).setTitle(str).setMessage(str2);
                    final int i3 = 0;
                    AlertDialog create = message2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.server.input.ToastDialog.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            switch (i3) {
                                case 0:
                                    dialogInterface.dismiss();
                                    break;
                                default:
                                    dialogInterface.dismiss();
                                    break;
                            }
                        }
                    }).create();
                    toastDialog.mAlertDialog = create;
                    create.getWindow().setType(2008);
                    toastDialog.mAlertDialog.show();
                    return;
                case 11:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    int i4 = someArgs2.argi1;
                    String str3 = (String) someArgs2.arg1;
                    String str4 = (String) someArgs2.arg2;
                    AlertDialog alertDialog3 = toastDialog.mAlertDialog;
                    if (alertDialog3 != null) {
                        alertDialog3.dismiss();
                        toastDialog.mAlertDialog = null;
                    }
                    if (i4 == 1) {
                        if (toastDialog.mContext.getResources().getConfiguration().isNightModeActive()) {
                            i2 = 16974545;
                        }
                        AlertDialog.Builder message3 = new AlertDialog.Builder(toastDialog.mContext, i2).setTitle(str3).setMessage(str4);
                        final int i5 = 1;
                        AlertDialog create2 = message3.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.server.input.ToastDialog.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i42) {
                                switch (i5) {
                                    case 0:
                                        dialogInterface.dismiss();
                                        break;
                                    default:
                                        dialogInterface.dismiss();
                                        break;
                                }
                            }
                        }).create();
                        toastDialog.mAlertDialog = create2;
                        create2.getWindow().setType(2008);
                        toastDialog.mAlertDialog.show();
                        return;
                    }
                    return;
            }
            String str5 = (String) ((SomeArgs) message.obj).arg1;
            Toast toast = toastDialog.mToast;
            if (toast != null) {
                toast.cancel();
                toastDialog.mToast = null;
            }
            Toast makeText = Toast.makeText(toastDialog.mContext, str5, 0);
            toastDialog.mToast = makeText;
            makeText.show();
        }
    }

    public ToastDialog(Context context) {
        this.mContext = context;
    }
}
