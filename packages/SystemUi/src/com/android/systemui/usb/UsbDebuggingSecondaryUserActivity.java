package com.android.systemui.usb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.debug.IAdbManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class UsbDebuggingSecondaryUserActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public UsbDisconnectedReceiver mDisconnectedReceiver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UsbDisconnectedReceiver extends BroadcastReceiver {
        public final Activity mActivity;

        public UsbDisconnectedReceiver(UsbDebuggingSecondaryUserActivity usbDebuggingSecondaryUserActivity, Activity activity) {
            this.mActivity = activity;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (!"android.hardware.usb.action.USB_STATE".equals(intent.getAction()) || intent.getBooleanExtra("connected", false)) {
                return;
            }
            this.mActivity.finish();
        }
    }

    public UsbDebuggingSecondaryUserActivity(BroadcastDispatcher broadcastDispatcher) {
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        getWindow().addSystemFlags(524288);
        super.onCreate(bundle);
        if (SystemProperties.getInt("service.adb.tcp.port", 0) == 0) {
            this.mDisconnectedReceiver = new UsbDisconnectedReceiver(this, this);
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getString(R.string.usb_debugging_secondary_user_title);
        alertParams.mMessage = getString(R.string.usb_debugging_secondary_user_message);
        alertParams.mPositiveButtonText = getString(android.R.string.ok);
        alertParams.mPositiveButtonListener = this;
        setupAlert();
    }

    public final void onStart() {
        super.onStart();
        if (this.mDisconnectedReceiver != null) {
            this.mBroadcastDispatcher.registerReceiver(new IntentFilter("android.hardware.usb.action.USB_STATE"), this.mDisconnectedReceiver);
        }
    }

    public final void onStop() {
        UsbDisconnectedReceiver usbDisconnectedReceiver = this.mDisconnectedReceiver;
        if (usbDisconnectedReceiver != null) {
            this.mBroadcastDispatcher.unregisterReceiver(usbDisconnectedReceiver);
        }
        try {
            IAdbManager.Stub.asInterface(ServiceManager.getService("adb")).denyDebugging();
        } catch (RemoteException e) {
            Log.e("UsbDebuggingSecondaryUserActivity", "Unable to notify Usb service", e);
        }
        super.onStop();
    }
}
