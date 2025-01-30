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

/* loaded from: classes2.dex */
public class ToastDialog {
  public AlertDialog mAlertDialog;
  public Context mContext;
  public final ToastDialogHandler mHandler =
      new ToastDialogHandler(DisplayThread.get().getLooper());
  public Toast mToast;

  public ToastDialog(Context context) {
    this.mContext = context;
  }

  public void showToastWKSDeviceSwitching(String str) {
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = str;
    this.mHandler.removeMessages(2);
    this.mHandler.obtainMessage(2, obtain).sendToTarget();
  }

  public void showToastWKSUnregister(String str) {
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = str;
    this.mHandler.removeMessages(3);
    this.mHandler.obtainMessage(3, obtain).sendToTarget();
  }

  public void showToastWKSParingFail(String str) {
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = str;
    Message obtainMessage = this.mHandler.obtainMessage(1, obtain);
    this.mHandler.removeMessages(1);
    this.mHandler.sendMessageDelayed(obtainMessage, 60000L);
  }

  public void showToastWKSRegister() {
    this.mHandler.removeMessages(1);
  }

  public void showUntrustedTouch(String str) {
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = str;
    this.mHandler.removeMessages(7);
    this.mHandler.obtainMessage(7, obtain).sendToTarget();
  }

  public void showWarningWrongPogo(String str) {
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = str;
    this.mHandler.removeMessages(7);
    this.mHandler.obtainMessage(7, obtain).sendToTarget();
  }

  public void dismissAlertDialogWKS() {
    AlertDialog alertDialog = this.mAlertDialog;
    if (alertDialog != null) {
      alertDialog.dismiss();
      this.mAlertDialog = null;
    }
  }

  public void showAlertDialogWKS(String str, String str2) {
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = str;
    obtain.arg2 = str2;
    this.mHandler.removeMessages(10);
    this.mHandler.obtainMessage(10, obtain).sendToTarget();
  }

  public final class ToastDialogHandler extends Handler {
    public ToastDialogHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 1:
          ToastDialog.this.dismissAlertDialogWKS();
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
          ToastDialog.this.showAlertDialogWKSInner((String) someArgs.arg1, (String) someArgs.arg2);
          return;
        case 11:
          SomeArgs someArgs2 = (SomeArgs) message.obj;
          int i = someArgs2.argi1;
          ToastDialog.this.showAlertDialogInner(
              (String) someArgs2.arg1, (String) someArgs2.arg2, i);
          return;
      }
      ToastDialog.this.showToastInner((String) ((SomeArgs) message.obj).arg1);
    }
  }

  public final void showToastInner(String str) {
    Toast toast = this.mToast;
    if (toast != null) {
      toast.cancel();
      this.mToast = null;
    }
    Toast makeText = Toast.makeText(this.mContext, str, 0);
    this.mToast = makeText;
    makeText.show();
  }

  public final void showAlertDialogWKSInner(String str, String str2) {
    AlertDialog alertDialog = this.mAlertDialog;
    if (alertDialog != null) {
      alertDialog.dismiss();
      this.mAlertDialog = null;
    }
    AlertDialog create =
        new AlertDialog.Builder(
                this.mContext,
                this.mContext.getResources().getConfiguration().isNightModeActive()
                    ? R.style.Theme.DeviceDefault.Dialog.Alert
                    : R.style.Theme.DeviceDefault.Light.Dialog.Alert)
            .setTitle(str)
            .setMessage(str2)
            .setPositiveButton(
                R.string.ok,
                new DialogInterface
                    .OnClickListener() { // from class: com.android.server.input.ToastDialog.1
                  @Override // android.content.DialogInterface.OnClickListener
                  public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                  }
                })
            .create();
    this.mAlertDialog = create;
    create.getWindow().setType(2008);
    this.mAlertDialog.show();
  }

  public final void showAlertDialogInner(String str, String str2, int i) {
    AlertDialog alertDialog = this.mAlertDialog;
    if (alertDialog != null) {
      alertDialog.dismiss();
      this.mAlertDialog = null;
    }
    if (i == 1) {
      AlertDialog create =
          new AlertDialog.Builder(
                  this.mContext,
                  this.mContext.getResources().getConfiguration().isNightModeActive()
                      ? R.style.Theme.DeviceDefault.Dialog.Alert
                      : R.style.Theme.DeviceDefault.Light.Dialog.Alert)
              .setTitle(str)
              .setMessage(str2)
              .setPositiveButton(
                  R.string.ok,
                  new DialogInterface
                      .OnClickListener() { // from class: com.android.server.input.ToastDialog.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                      dialogInterface.dismiss();
                    }
                  })
              .create();
      this.mAlertDialog = create;
      create.getWindow().setType(2008);
      this.mAlertDialog.show();
    }
  }
}
