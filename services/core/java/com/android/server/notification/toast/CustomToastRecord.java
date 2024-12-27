package com.android.server.notification.toast;

import android.app.ITransientNotification;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;

import com.android.internal.util.Preconditions;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.notification.NotificationManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CustomToastRecord extends ToastRecord {
    public final ITransientNotification callback;

    public CustomToastRecord(
            NotificationManagerService notificationManagerService,
            int i,
            int i2,
            String str,
            boolean z,
            IBinder iBinder,
            ITransientNotification iTransientNotification,
            int i3,
            Binder binder,
            int i4) {
        super(notificationManagerService, i, i2, str, z, iBinder, i3, binder, i4);
        this.callback = (ITransientNotification) Preconditions.checkNotNull(iTransientNotification);
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public final void hide() {
        try {
            this.callback.hide();
        } catch (RemoteException unused) {
            StringBuilder sb = new StringBuilder("Object died trying to hide custom toast ");
            sb.append(this.token);
            sb.append(" in package ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, this.pkg, "NotificationService");
        }
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public final boolean isAppRendered() {
        return true;
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public final boolean show() {
        if (NotificationManagerService.DBG) {
            Slog.d("NotificationService", "Show pkg=" + this.pkg + " callback=" + this.callback);
        }
        try {
            this.callback.show(this.windowToken);
            return true;
        } catch (RemoteException unused) {
            StringBuilder sb = new StringBuilder("Object died trying to show custom toast ");
            sb.append(this.token);
            sb.append(" in package ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, this.pkg, "NotificationService");
            NotificationManagerService notificationManagerService = this.mNotificationManager;
            int i = this.pid;
            synchronized (notificationManagerService.mToastQueue) {
                notificationManagerService.keepProcessAliveForToastIfNeededLocked(i);
                return false;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CustomToastRecord{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.pid);
        sb.append(":");
        sb.append(this.pkg);
        sb.append("/");
        sb.append(UserHandle.formatUid(this.uid));
        sb.append(" isSystemToast=");
        sb.append(this.isSystemToast);
        sb.append(" token=");
        sb.append(this.token);
        sb.append(" callback=");
        sb.append(this.callback);
        sb.append(" duration=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mDuration, sb, "}");
    }
}
