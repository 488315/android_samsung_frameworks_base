package com.android.server.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;

/* loaded from: classes3.dex */
public abstract class MtpNotificationManager {

    public interface OnOpenInAppListener {
    }

    /* renamed from: -$$Nest$fgetmListener, reason: not valid java name */
    public static /* bridge */ /* synthetic */ OnOpenInAppListener m12384$$Nest$fgetmListener(MtpNotificationManager mtpNotificationManager) {
        throw null;
    }

    public class Receiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (((UsbDevice) intent.getExtras().getParcelable("device", UsbDevice.class)) == null) {
                return;
            }
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("com.android.server.usb.ACTION_OPEN_IN_APPS")) {
                MtpNotificationManager.m12384$$Nest$fgetmListener(null);
                throw null;
            }
        }
    }
}
