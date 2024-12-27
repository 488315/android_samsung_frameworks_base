package com.android.server.notification.toast;

import android.app.ITransientNotificationCallback;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.Preconditions;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.notification.NotificationManagerService;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TextToastRecord extends ToastRecord {
    public final ITransientNotificationCallback mCallback;
    public final StatusBarManagerInternal mStatusBar;
    public final CharSequence text;

    public TextToastRecord(NotificationManagerService notificationManagerService, StatusBarManagerInternal statusBarManagerInternal, int i, int i2, String str, boolean z, IBinder iBinder, CharSequence charSequence, int i3, Binder binder, int i4, ITransientNotificationCallback iTransientNotificationCallback) {
        super(notificationManagerService, i, i2, str, z, iBinder, i3, binder, i4);
        this.mStatusBar = statusBarManagerInternal;
        this.mCallback = iTransientNotificationCallback;
        this.text = (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public final void hide() {
        Preconditions.checkNotNull(this.mStatusBar, "Cannot hide toast that wasn't shown");
        StatusBarManagerInternal statusBarManagerInternal = this.mStatusBar;
        String str = this.pkg;
        IBinder iBinder = this.token;
        IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.hideToast(str, iBinder);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public final boolean isAppRendered() {
        return false;
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public final boolean show() {
        if (NotificationManagerService.DBG) {
            Slog.d("NotificationService", "Show pkg=" + this.pkg + " text=" + ((Object) this.text));
        }
        StatusBarManagerInternal statusBarManagerInternal = this.mStatusBar;
        if (statusBarManagerInternal == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("StatusBar not available to show text toast for package "), this.pkg, "NotificationService");
            return false;
        }
        int i = this.uid;
        String str = this.pkg;
        IBinder iBinder = this.token;
        CharSequence charSequence = this.text;
        Binder binder = this.windowToken;
        int i2 = this.mDuration;
        ITransientNotificationCallback iTransientNotificationCallback = this.mCallback;
        int i3 = this.displayId;
        IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
        if (iStatusBar == null) {
            return true;
        }
        try {
            iStatusBar.showToast(i, str, iBinder, charSequence, binder, i2, iTransientNotificationCallback, i3);
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TextToastRecord{");
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
        sb.append(" text=");
        sb.append((Object) this.text);
        sb.append(" duration=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mDuration, sb, "}");
    }
}
