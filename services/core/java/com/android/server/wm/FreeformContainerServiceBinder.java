package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class FreeformContainerServiceBinder {
    public final ActivityTaskManagerService mAtm;
    public final Intent mService = new Intent();
    public final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final ArrayList mServiceHistory = new ArrayList();
    public boolean mIsServiceRunning = false;
    public final AnonymousClass1 mServiceConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.wm.FreeformContainerServiceBinder.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(
                        ComponentName componentName, IBinder iBinder) {}

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    FreeformContainerServiceBinder freeformContainerServiceBinder =
                            FreeformContainerServiceBinder.this;
                    if (freeformContainerServiceBinder.mIsServiceRunning) {
                        freeformContainerServiceBinder.unbindServiceIfNeeded(
                                "service_disconnected");
                        FreeformContainerServiceBinder.this.bindServiceIfNeeded(
                                "service_disconnected");
                    }
                }
            };
    public final String TAG = getClass().getSimpleName();

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.wm.FreeformContainerServiceBinder$1] */
    public FreeformContainerServiceBinder(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public final synchronized void bindServiceIfNeeded(String str) {
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW
                && (this instanceof SmartPopupViewServiceBinder)
                && this.mIsServiceRunning
                && !okToBind()
                && "startUser".equals(str)) {
            Slog.d(this.TAG, "Service running but not needed for this User");
            unbindServiceIfNeeded(str);
            return;
        }
        if (!this.mIsServiceRunning && okToBind()) {
            if (this.mAtm.mContext.bindServiceAsUser(
                    this.mService, this.mServiceConnection, 1, UserHandle.SYSTEM)) {
                this.mIsServiceRunning = true;
                Slog.i(this.TAG, "bind service success, reason=" + str);
            } else {
                Slog.w(this.TAG, "bind service failed, reason=" + str);
            }
            this.mServiceHistory.add(
                    "BindService["
                            + str
                            + "] Success="
                            + this.mIsServiceRunning
                            + " | "
                            + this.mSimpleDateFormat.format(
                                    Long.valueOf(System.currentTimeMillis())));
        }
    }

    public final void dumpLocked(PrintWriter printWriter) {
        StringBuilder sb = new StringBuilder("[");
        String str = this.TAG;
        sb.append(str);
        sb.append("]");
        printWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder("    ");
        sb2.append(str);
        sb2.append(" Running=");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                sb2, this.mIsServiceRunning, printWriter);
        if (this.mServiceHistory.isEmpty()) {
            return;
        }
        printWriter.println("    * History");
        int size = this.mServiceHistory.size();
        int i = size >= 5 ? size - 5 : 0;
        while (i < size) {
            StringBuilder sb3 = new StringBuilder("      #");
            int i2 = i + 1;
            sb3.append(i2);
            sb3.append(" ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb3, (String) this.mServiceHistory.get(i), printWriter);
            i = i2;
        }
    }

    public boolean isServiceRunning() {
        return this.mIsServiceRunning;
    }

    public boolean okToBind() {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = this.mAtm.mDexController.getDexModeLocked() == 1;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return !z;
    }

    public abstract boolean okToUnbind();

    public final synchronized void unbindServiceIfNeeded(String str) {
        if (this.mIsServiceRunning && okToUnbind()) {
            this.mAtm.mContext.unbindService(this.mServiceConnection);
            this.mIsServiceRunning = false;
            Slog.i(this.TAG, "unbind service success, reason=" + str);
            this.mServiceHistory.add(
                    "UnbindService["
                            + str
                            + "] | "
                            + this.mSimpleDateFormat.format(
                                    Long.valueOf(System.currentTimeMillis())));
        }
    }
}
