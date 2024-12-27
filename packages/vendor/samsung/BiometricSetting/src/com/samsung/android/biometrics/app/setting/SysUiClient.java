package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public abstract class SysUiClient implements Handler.Callback {
    public boolean mAlreadyCancelled;
    public final Context mContext;
    public final Handler mHandler;
    public final String mPackageName;
    public final Bundle mRawExtraInfo;
    public final int mSessionId;
    public final ISemBiometricSysUiCallback mSysUiCallback;
    public final ArrayList mWindows = new ArrayList();
    public ClientCallback mCallback = new AnonymousClass1();

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.SysUiClient$1, reason: invalid class name */
    public final class AnonymousClass1 implements ClientCallback {}

    public SysUiClient(
            Context context,
            int i,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Looper looper,
            Bundle bundle,
            String str) {
        this.mContext = context;
        this.mRawExtraInfo = bundle == null ? new Bundle() : bundle;
        this.mHandler = createHandler(looper);
        this.mSessionId = i;
        this.mPackageName = str;
        this.mSysUiCallback = iSemBiometricSysUiCallback;
    }

    public Handler createHandler(Looper looper) {
        return new Handler(looper, this);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        return false;
    }

    public void onAuthenticationSucceeded(String str) {
        stop();
    }

    public abstract void prepareWindows();

    public final void sendDismissedEvent(int i, byte[] bArr) {
        if (this.mAlreadyCancelled) {
            return;
        }
        this.mAlreadyCancelled = true;
        try {
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback = this.mSysUiCallback;
            if (iSemBiometricSysUiCallback != null) {
                iSemBiometricSysUiCallback.onSysUiDismissed(this.mSessionId, i, bArr);
            }
        } catch (RemoteException e) {
            Log.w("BSS_SysUiClient", "sendDismissedEvent: error", e);
        }
    }

    public final void sendEvent(int i, int i2) {
        try {
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback = this.mSysUiCallback;
            if (iSemBiometricSysUiCallback != null) {
                iSemBiometricSysUiCallback.onEvent(this.mSessionId, i, i2);
            }
        } catch (RemoteException e) {
            Log.w("BSS_SysUiClient", "sendEvent: " + e.getMessage());
        }
    }

    public void start(SysUiManager.AnonymousClass1 anonymousClass1) {
        this.mCallback = anonymousClass1;
        prepareWindows();
        Iterator it = this.mWindows.iterator();
        while (it.hasNext()) {
            ((SysUiWindow) it.next()).addView();
        }
        this.mCallback.onClientStarted(this);
    }

    public void stop() {
        Iterator it = this.mWindows.iterator();
        while (it.hasNext()) {
            ((SysUiWindow) it.next()).removeView();
        }
        this.mWindows.clear();
        this.mCallback.onClientFinished(this);
    }

    public void stopImmediate() {
        stop();
    }

    public void handleTspBlock(boolean z) {}

    public void onDisplayStateChanged(int i) {}

    public void handleOnTaskStackListener(int i, int i2) {}
}
