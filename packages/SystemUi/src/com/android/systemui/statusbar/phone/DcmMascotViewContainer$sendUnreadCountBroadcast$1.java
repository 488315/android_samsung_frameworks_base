package com.android.systemui.statusbar.phone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import com.nttdocomo.android.screenlockservice.IScreenLockService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DcmMascotViewContainer$sendUnreadCountBroadcast$1 implements Runnable {
    public final /* synthetic */ DcmMascotViewContainer this$0;

    public DcmMascotViewContainer$sendUnreadCountBroadcast$1(DcmMascotViewContainer dcmMascotViewContainer) {
        this.this$0 = dcmMascotViewContainer;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00e4  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        DcmMascotViewContainer dcmMascotViewContainer = this.this$0;
        boolean z = DcmMascotViewContainer.DEBUG;
        dcmMascotViewContainer.getClass();
        DcmMascotViewContainer.log("sendUnreadCountBroadcast");
        boolean z2 = false;
        try {
            this.this$0.getContext().getPackageManager().getPackageInfo("com.nttdocomo.android.screenlockservice", 4);
            ((LinkedBlockingDeque) this.this$0.blockingQueue).clear();
            if (this.this$0.getContext().bindService(new Intent(DcmMascotViewContainer.DCM_SCREEN_LOCK_SERVICE_ACTION).setPackage("com.nttdocomo.android.screenlockservice"), this.this$0.serviceConnection, 1)) {
                try {
                    IScreenLockService iScreenLockService = (IScreenLockService) ((LinkedBlockingDeque) this.this$0.blockingQueue).poll(1500L, TimeUnit.MILLISECONDS);
                    if (iScreenLockService != null) {
                        int unreadCount = ((IScreenLockService.Stub.Proxy) iScreenLockService).getUnreadCount();
                        this.this$0.getClass();
                        DcmMascotViewContainer.log("unread count: " + unreadCount);
                        this.this$0.getContext().sendBroadcast(new Intent("jp.co.nttdocomo.carriermail.APP_LINK_RECEIVED_MESSAGE_LOCAL").putExtra("spcnt", unreadCount), "com.nttdocomo.android.screenlockservice.DCM_SCREEN");
                    }
                    z2 = true;
                } catch (PackageManager.NameNotFoundException unused) {
                    z2 = true;
                    this.this$0.getClass();
                    DcmMascotViewContainer.log("no package exists: com.nttdocomo.android.screenlockservice");
                    if (z2) {
                    }
                } catch (RemoteException e) {
                    e = e;
                    z2 = true;
                    DcmMascotViewContainer dcmMascotViewContainer2 = this.this$0;
                    String str = "exception " + e.getMessage();
                    dcmMascotViewContainer2.getClass();
                    DcmMascotViewContainer.log(str);
                    if (z2) {
                    }
                } catch (InterruptedException e2) {
                    e = e2;
                    z2 = true;
                    DcmMascotViewContainer dcmMascotViewContainer3 = this.this$0;
                    String str2 = "exception " + e.getMessage();
                    dcmMascotViewContainer3.getClass();
                    DcmMascotViewContainer.log(str2);
                    if (z2) {
                    }
                }
            } else {
                this.this$0.getClass();
                DcmMascotViewContainer.log("failed to bind service");
            }
        } catch (PackageManager.NameNotFoundException unused2) {
        } catch (RemoteException e3) {
            e = e3;
        } catch (InterruptedException e4) {
            e = e4;
        }
        if (z2) {
            return;
        }
        this.this$0.getContext().unbindService(this.this$0.serviceConnection);
    }
}
