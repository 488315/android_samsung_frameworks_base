package com.android.server.usb;

import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class UsbPortManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UsbPortManager f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ UsbPortManager$$ExternalSyntheticLambda0(
            UsbPortManager usbPortManager, Intent intent, int i) {
        this.$r8$classId = i;
        this.f$0 = usbPortManager;
        this.f$1 = intent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UsbPortManager usbPortManager = this.f$0;
                usbPortManager.mContext.sendBroadcastAsUser(
                        this.f$1, UserHandle.ALL, "android.permission.MANAGE_USB");
                break;
            default:
                UsbPortManager usbPortManager2 = this.f$0;
                usbPortManager2.mContext.sendBroadcastAsUser(
                        this.f$1, UserHandle.ALL, "android.permission.MANAGE_USB");
                break;
        }
    }
}
