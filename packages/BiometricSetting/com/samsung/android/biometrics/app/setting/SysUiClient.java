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

/* loaded from: classes.dex */
public abstract class SysUiClient implements Handler.Callback {
    private boolean mAlreadyCancelled;
    protected Context mContext;
    protected Handler mHandler;
    private final String mPackageName;
    protected Bundle mRawExtraInfo;
    private final int mSessionId;
    private final ISemBiometricSysUiCallback mSysUiCallback;
    protected final ArrayList<SysUiWindow> mWindows = new ArrayList<>();
    protected ClientCallback mCallback = new C02191();

    public SysUiClient(Context context, int i, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str) {
        this.mContext = context;
        this.mRawExtraInfo = bundle;
        this.mHandler = new Handler(looper, this);
        this.mSessionId = i;
        this.mPackageName = str;
        this.mSysUiCallback = iSemBiometricSysUiCallback;
        getUiType();
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final String getPackageName() {
        return this.mPackageName;
    }

    public final Bundle getRawExtraInfo() {
        return this.mRawExtraInfo;
    }

    public final int getSessionId() {
        return this.mSessionId;
    }

    public abstract int getUiType();

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        return false;
    }

    public void onAuthenticationSucceeded(String str) {
        stop();
    }

    public abstract void prepareWindows();

    protected final void sendDismissedEvent(int i) {
        sendDismissedEvent(i, null);
    }

    protected final void sendErrorEventToBioService(int i) {
        try {
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback = this.mSysUiCallback;
            if (iSemBiometricSysUiCallback != null) {
                iSemBiometricSysUiCallback.onError(this.mSessionId, 10, i);
            }
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("sendErrorEventToBioService: "), "BSS_SysUiClient");
        }
    }

    protected final void sendEvent(int i, int i2) {
        try {
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback = this.mSysUiCallback;
            if (iSemBiometricSysUiCallback != null) {
                iSemBiometricSysUiCallback.onEvent(this.mSessionId, i, i2);
            }
        } catch (RemoteException e) {
            Log.w("BSS_SysUiClient", "sendEvent: " + e.getMessage());
        }
    }

    public void start(ClientCallback clientCallback) {
        this.mCallback = clientCallback;
        prepareWindows();
        Iterator<SysUiWindow> it = this.mWindows.iterator();
        while (it.hasNext()) {
            it.next().addView();
        }
    }

    public void stop() {
        Iterator<SysUiWindow> it = this.mWindows.iterator();
        while (it.hasNext()) {
            it.next().removeView();
        }
        this.mWindows.clear();
        this.mCallback.onClientFinished(this);
    }

    public void stopImmediate() {
        stop();
    }

    protected final void sendDismissedEvent(int i, byte[] bArr) {
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

    public void handleOnTaskStackListener() {
    }

    /* renamed from: com.samsung.android.biometrics.app.setting.SysUiClient$1 */
    final class C02191 implements ClientCallback {
        @Override // com.samsung.android.biometrics.app.setting.ClientCallback
        public final void onClientFinished(SysUiClient sysUiClient) {
        }
    }

    public void onDisplayStateChanged(int i) {
    }
}
