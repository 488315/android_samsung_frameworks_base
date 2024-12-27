package com.android.systemui.usb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.debug.IAdbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class UsbDebuggingActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CheckBox mAlwaysAllow;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public TextView mCheckBoxText;
    public UsbDisconnectedReceiver mDisconnectedReceiver;
    public String mKey;
    public boolean mServiceNotified;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class UsbDisconnectedReceiver extends BroadcastReceiver {
        public final Activity mActivity;

        public UsbDisconnectedReceiver(Activity activity) {
            this.mActivity = activity;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.hardware.usb.action.USB_STATE".equals(intent.getAction()) && !intent.getBooleanExtra("connected", false)) {
                Log.d("UsbDebuggingActivity", "USB disconnected, notifying service");
                UsbDebuggingActivity usbDebuggingActivity = UsbDebuggingActivity.this;
                int i = UsbDebuggingActivity.$r8$clinit;
                usbDebuggingActivity.notifyService(false, false);
                this.mActivity.finish();
            }
        }
    }

    public UsbDebuggingActivity(BroadcastDispatcher broadcastDispatcher) {
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public final void notifyService(boolean z, boolean z2) {
        try {
            IAdbManager asInterface = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
            if (z) {
                asInterface.allowDebugging(z2, this.mKey);
            } else {
                asInterface.denyDebugging();
            }
            this.mServiceNotified = true;
        } catch (Exception e) {
            Log.e("UsbDebuggingActivity", "Unable to notify Usb service", e);
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        boolean z = false;
        boolean z2 = i == -1;
        if (z2 && this.mAlwaysAllow.isChecked()) {
            z = true;
        }
        notifyService(z2, z);
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        Window window = getWindow();
        window.addSystemFlags(524288);
        window.setType(2008);
        super.onCreate(bundle);
        if (SystemProperties.getInt("service.adb.tcp.port", 0) == 0 && !Build.IS_EMULATOR) {
            this.mDisconnectedReceiver = new UsbDisconnectedReceiver(this);
            this.mBroadcastDispatcher.registerReceiver(new IntentFilter("android.hardware.usb.action.USB_STATE"), this.mDisconnectedReceiver);
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("fingerprints");
        String stringExtra2 = intent.getStringExtra("key");
        this.mKey = stringExtra2;
        if (stringExtra == null || stringExtra2 == null) {
            finish();
            return;
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getString(R.string.usb_debugging_title);
        alertParams.mMessage = getString(R.string.usb_debugging_message, new Object[]{stringExtra});
        alertParams.mPositiveButtonText = getString(R.string.usb_debugging_allow);
        alertParams.mNegativeButtonText = getString(android.R.string.cancel);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonListener = this;
        View inflate = LayoutInflater.from(alertParams.mContext).inflate(android.R.layout.subscription_item_layout, (ViewGroup) null);
        this.mAlwaysAllow = (CheckBox) inflate.findViewById(android.R.id.tabMode);
        TextView textView = (TextView) inflate.findViewById(android.R.id.tabs_container);
        this.mCheckBoxText = textView;
        textView.setText(R.string.usb_debugging_always);
        this.mCheckBoxText.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.usb.UsbDebuggingActivity.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UsbDebuggingActivity.this.mAlwaysAllow.toggle();
                Log.d("UsbDebuggingActivity", "mAlwaysAllow=" + UsbDebuggingActivity.this.mAlwaysAllow.isChecked());
            }
        });
        alertParams.mView = inflate;
        window.setCloseOnTouchOutside(false);
        getWindow().setGravity(80);
        setupAlert();
    }

    public final void onDestroy() {
        UsbDisconnectedReceiver usbDisconnectedReceiver = this.mDisconnectedReceiver;
        if (usbDisconnectedReceiver != null) {
            this.mBroadcastDispatcher.unregisterReceiver(usbDisconnectedReceiver);
        }
        if (isFinishing() && !this.mServiceNotified) {
            notifyService(false, false);
        }
        super.onDestroy();
    }
}
