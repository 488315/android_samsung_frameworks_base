package com.android.server.cover;

import android.R;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.IBinder;

import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.sepunion.SemPluginManagerLocal;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CoverManagerServiceImpl extends ICoverManager.Stub {
    public boolean mSystemReady;
    public SemPluginManagerLocal mUnionLocal;

    public final void addLedNotification(Bundle bundle) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.addLedNotification(bundle);
    }

    public final void disableCoverManager(boolean z, IBinder iBinder, String str) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.disableCoverManager(z, iBinder, str);
    }

    public final boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.disableLcdOffByCover(iBinder, componentName);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.dump(fileDescriptor, printWriter, strArr, "cover");
    }

    public final boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.enableLcdOffByCover(iBinder, componentName);
    }

    public final CoverState getCoverState() {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        return semPluginManagerLocal == null
                ? new CoverState()
                : semPluginManagerLocal.getCoverState();
    }

    public final CoverState getCoverStateForExternal() {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return null;
        }
        return semPluginManagerLocal.getCoverStateForExternal();
    }

    public final boolean getCoverSwitchState() {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return true;
        }
        return semPluginManagerLocal.getCoverSwitchState();
    }

    public final int getVersion() {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        return semPluginManagerLocal == null
                ? R.animator.fade_in
                : semPluginManagerLocal.getVersion();
    }

    public final boolean isCoverManagerDisabled() {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.isCoverManagerDisabled();
    }

    public final int onCoverAppCovered(boolean z) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return -1;
        }
        return semPluginManagerLocal.onCoverAppCovered(z);
    }

    public final void registerCallback(IBinder iBinder, ComponentName componentName) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.registerCallback(iBinder, componentName);
    }

    public final void registerListenerCallback(
            IBinder iBinder, ComponentName componentName, int i) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.registerListenerCallback(iBinder, componentName, i);
    }

    public final void registerListenerCallbackForExternal(
            IBinder iBinder, ComponentName componentName, int i) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.registerListenerCallbackForExternal(iBinder, componentName, i);
    }

    public final void registerNfcTouchListenerCallback(
            int i, IBinder iBinder, ComponentName componentName) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.registerNfcTouchListenerCallback(i, iBinder, componentName);
    }

    public final void removeLedNotification(Bundle bundle) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.removeLedNotification(bundle);
    }

    public final boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.requestCoverAuthentication(iBinder, componentName);
    }

    public final void sendDataToCover(int i, byte[] bArr) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.sendDataToCover(i, bArr);
    }

    public final void sendDataToNfcLedCover(int i, byte[] bArr) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.sendDataToNfcLedCover(i, bArr);
    }

    public final void sendPowerKeyToCover() {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.sendPowerKeyToCover();
    }

    public final void sendSystemEvent(Bundle bundle) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return;
        }
        semPluginManagerLocal.sendSystemEvent(bundle);
    }

    public final boolean setFotaInProgress(
            boolean z, IBinder iBinder, ComponentName componentName) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.setFotaInProgress(z, iBinder, componentName);
    }

    public final boolean unregisterCallback(IBinder iBinder) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.unregisterCallback(iBinder);
    }

    public final boolean unregisterCallbackForExternal(IBinder iBinder) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.unregisterCallbackForExternal(iBinder);
    }

    public final boolean unregisterNfcTouchListenerCallback(IBinder iBinder) {
        SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
        if (semPluginManagerLocal == null) {
            return false;
        }
        return semPluginManagerLocal.unregisterNfcTouchListenerCallback(iBinder);
    }
}
