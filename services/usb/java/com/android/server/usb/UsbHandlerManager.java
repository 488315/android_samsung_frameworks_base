package com.android.server.usb;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.UserHandle;
import android.util.sysfwutil.Slog;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class UsbHandlerManager {
    public static final String LOG_TAG = "UsbHandlerManager";
    public final Context mContext;

    public UsbHandlerManager(Context context) {
        this.mContext = context;
    }

    public void showUsbAccessoryUriActivity(UsbAccessory usbAccessory, UserHandle userHandle) {
        String uri = usbAccessory.getUri();
        if (uri == null || uri.length() <= 0) {
            return;
        }
        Intent createDialogIntent = createDialogIntent();
        createDialogIntent.setComponent(ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.ext_media_status_unmountable)));
        createDialogIntent.putExtra("accessory", usbAccessory);
        createDialogIntent.putExtra("uri", uri);
        try {
            this.mContext.startActivityAsUser(createDialogIntent, userHandle);
        } catch (ActivityNotFoundException unused) {
            Slog.e(LOG_TAG, "unable to start UsbAccessoryUriActivity");
        }
    }

    public void confirmUsbHandler(ResolveInfo resolveInfo, UsbDevice usbDevice, UsbAccessory usbAccessory) {
        Intent createDialogIntent = createDialogIntent();
        createDialogIntent.setComponent(ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.ext_media_status_unmounted)));
        createDialogIntent.putExtra("rinfo", resolveInfo);
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid);
        if (usbDevice != null) {
            createDialogIntent.putExtra("device", usbDevice);
        } else {
            createDialogIntent.putExtra("accessory", usbAccessory);
        }
        try {
            this.mContext.startActivityAsUser(createDialogIntent, userHandleForUid);
        } catch (ActivityNotFoundException e) {
            Slog.e(LOG_TAG, "unable to start activity " + createDialogIntent, e);
        }
    }

    public void selectUsbHandler(ArrayList arrayList, UserHandle userHandle, Intent intent) {
        Intent createDialogIntent = createDialogIntent();
        createDialogIntent.setComponent(ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.ext_media_unmountable_notification_message)));
        createDialogIntent.putParcelableArrayListExtra("rlist", arrayList);
        createDialogIntent.putExtra("android.intent.extra.INTENT", intent);
        try {
            this.mContext.startActivityAsUser(createDialogIntent, userHandle);
        } catch (ActivityNotFoundException e) {
            Slog.e(LOG_TAG, "unable to start activity " + createDialogIntent, e);
        }
    }

    public final Intent createDialogIntent() {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        return intent;
    }
}
