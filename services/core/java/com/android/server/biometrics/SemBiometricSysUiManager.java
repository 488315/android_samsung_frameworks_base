package com.android.server.biometrics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.util.Slog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiService;
import com.samsung.android.biometrics.SemBiometricConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemBiometricSysUiManager implements ServiceConnection, IBinder.DeathRecipient, SemBiometricConstants {
    public static SemBiometricSysUiManager sInstance;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsConnected;
    public boolean mIsWaitingForConnection;
    public ISemBiometricSysUiService mSysUiService;
    public final List mPendingCommand = new ArrayList();
    public final List mSessionList = new ArrayList();
    public final AtomicBoolean mKeepBind = new AtomicBoolean(false);
    public final SemBiometricSysUiManager$$ExternalSyntheticLambda0 mRunnableHandleUnbind = new SemBiometricSysUiManager$$ExternalSyntheticLambda0(this, 2);
    public final SemBiometricSysUiManager$$ExternalSyntheticLambda0 mRunnableHandleBindTimeout = new SemBiometricSysUiManager$$ExternalSyntheticLambda0(this, 3);
    public final Executor mExecutor = new HandlerExecutor(BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler());
    public final AnonymousClass1 mSysUiServiceReceiver = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.SemBiometricSysUiManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends ISemBiometricSysUiCallback.Stub {
        public AnonymousClass1() {
        }

        public final void onError(int i, int i2, int i3) {
            if (Utils.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "onError: ", ", ", ", "), i3, "BiometricSysUiManager");
            }
            SemBiometricSysUiManager.this.mHandler.post(new SemBiometricSysUiManager$$ExternalSyntheticLambda8(this, i, i2, i3, 2));
        }

        public final void onEvent(int i, int i2, int i3) {
            if (Utils.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "onEvent: ", ", ", ", "), i3, "BiometricSysUiManager");
            }
            SemBiometricSysUiManager.this.mHandler.post(new SemBiometricSysUiManager$$ExternalSyntheticLambda8(this, i, i2, i3, 1));
        }

        public final void onSysUiDismissed(int i, int i2, byte[] bArr) {
            if (Utils.DEBUG) {
                ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "onSysUiDismissed: ", ", ", "BiometricSysUiManager");
            }
            SemBiometricSysUiManager.this.mHandler.post(new SemBiometricSysUiManager$$ExternalSyntheticLambda6(this, i, i2, bArr));
        }

        public final void onTouchEvent(int i, int i2) {
            if (Utils.DEBUG) {
                ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "onTouchEvent: ", ",", "BiometricSysUiManager");
            }
            SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.this;
            synchronized (semBiometricSysUiManager) {
                try {
                    Iterator it = ((ArrayList) semBiometricSysUiManager.mSessionList).iterator();
                    while (it.hasNext()) {
                        SysUiServiceSession sysUiServiceSession = (SysUiServiceSession) it.next();
                        try {
                            if (sysUiServiceSession.mListener != null && i != 0) {
                                int i3 = sysUiServiceSession.mId;
                            }
                        } catch (Exception e) {
                            Slog.w("BiometricSysUiManager", "notifySysUiTouchEvent: " + e.getMessage());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SysUiListener {
        default void onDismissed(int i, byte[] bArr) {
        }

        void onError(int i, int i2);

        default void onEvent(int i, int i2) {
        }

        default void onTryAgainPressed(int i) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SysUiServiceSession {
        public final int mId = hashCode();
        public SysUiListener mListener;
        public final String mToken;

        public SysUiServiceSession(String str, SysUiListener sysUiListener) {
            this.mToken = str;
            this.mListener = sysUiListener;
        }

        public final String toString() {
            return "[" + this.mId + "], [" + this.mToken + "], [" + this.mListener + "]";
        }
    }

    public SemBiometricSysUiManager(Context context, Looper looper) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
    }

    public static synchronized void injectMockForTest(SemBiometricSysUiManager semBiometricSysUiManager) {
        synchronized (SemBiometricSysUiManager.class) {
            sInstance = semBiometricSysUiManager;
        }
    }

    public final void bind() {
        try {
            synchronized (this) {
                try {
                    if (this.mIsConnected) {
                        return;
                    }
                    if (this.mIsWaitingForConnection) {
                        Slog.d("BiometricSysUiManager", "openSession: waiting for service connection");
                        return;
                    }
                    Slog.d("BiometricSysUiManager", "bind: started");
                    int i = Build.IS_ENG ? 603979779 : 603979777;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.samsung.android.biometrics.app.setting", "com.samsung.android.biometrics.app.setting.BiometricsUIService"));
                    this.mContext.bindService(intent, i, this.mExecutor, this);
                    this.mIsWaitingForConnection = true;
                    this.mHandler.postDelayed(this.mRunnableHandleBindTimeout, 3000L);
                } finally {
                }
            }
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "bind: " + e.getMessage());
            sendConnectionError(-2);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.e("BiometricSysUiManager", "binderDied: handle error");
        this.mHandler.post(new SemBiometricSysUiManager$$ExternalSyntheticLambda0(this, 0));
    }

    public final void cleanup() {
        synchronized (this) {
            ((ArrayList) this.mPendingCommand).clear();
            this.mIsConnected = false;
            this.mIsWaitingForConnection = false;
            this.mSysUiService = null;
            this.mHandler.removeCallbacks(this.mRunnableHandleBindTimeout);
        }
    }

    public final void closeSession(int i, long j) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "closeSession: sessionId = [" + i + "], " + j);
        }
        synchronized (this) {
            try {
                SysUiServiceSession findSessionId = findSessionId(i);
                if (findSessionId != null) {
                    ((ArrayList) this.mSessionList).remove(findSessionId);
                    findSessionId.mListener = null;
                    if (((ArrayList) this.mSessionList).isEmpty() && this.mIsConnected) {
                        this.mHandler.removeCallbacks(this.mRunnableHandleUnbind);
                        this.mHandler.postDelayed(this.mRunnableHandleUnbind, j);
                    }
                }
                printSessionClient();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SysUiServiceSession findSessionId(int i) {
        Iterator it = ((ArrayList) this.mSessionList).iterator();
        while (it.hasNext()) {
            SysUiServiceSession sysUiServiceSession = (SysUiServiceSession) it.next();
            if (sysUiServiceSession.mId == i) {
                return sysUiServiceSession;
            }
        }
        return null;
    }

    public final void handleRequest(Runnable runnable) {
        synchronized (this) {
            try {
                if (this.mSysUiService != null) {
                    runnable.run();
                } else if (this.mIsWaitingForConnection) {
                    if (Utils.DEBUG) {
                        Slog.i("BiometricSysUiManager", "handleRequest: Add pending list");
                    }
                    ((ArrayList) this.mPendingCommand).add(runnable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifySysUiErrorEvent(int i, int i2, int i3) {
        synchronized (this) {
            Iterator it = ((ArrayList) this.mSessionList).iterator();
            while (it.hasNext()) {
                SysUiServiceSession sysUiServiceSession = (SysUiServiceSession) it.next();
                try {
                    SysUiListener sysUiListener = sysUiServiceSession.mListener;
                    if (sysUiListener != null && (i == 0 || i == sysUiServiceSession.mId)) {
                        sysUiListener.onError(i2, i3);
                    }
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "notifySysUiErrorEvent: " + e.getMessage());
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "onServiceConnected: " + iBinder);
        }
        synchronized (this) {
            this.mHandler.removeCallbacks(this.mRunnableHandleBindTimeout);
            this.mIsWaitingForConnection = false;
            this.mIsConnected = true;
            ISemBiometricSysUiService asInterface = ISemBiometricSysUiService.Stub.asInterface(iBinder);
            this.mSysUiService = asInterface;
            if (asInterface == null) {
                Slog.w("BiometricSysUiManager", "onServiceConnected: failed ");
                sendConnectionError(-1);
                return;
            }
            try {
                asInterface.asBinder().linkToDeath(this, 0);
            } catch (Exception e) {
                Slog.w("BiometricSysUiManager", "onServiceConnected: " + e.getMessage());
            }
            this.mHandler.post(new SemBiometricSysUiManager$$ExternalSyntheticLambda0(this, 1));
            if (((ArrayList) this.mSessionList).isEmpty()) {
                this.mHandler.postDelayed(this.mRunnableHandleUnbind, 4000L);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "onServiceDisconnected: " + this.mSysUiService);
        }
        synchronized (this) {
            try {
                ISemBiometricSysUiService iSemBiometricSysUiService = this.mSysUiService;
                if (iSemBiometricSysUiService != null) {
                    iSemBiometricSysUiService.asBinder().unlinkToDeath(this, 0);
                }
            } catch (Exception e) {
                Slog.w("BiometricSysUiManager", "onServiceDisconnected: " + e.getMessage());
            }
            notifySysUiErrorEvent(0, 2, 0);
            cleanup();
        }
    }

    public final int openSession(String str, SysUiListener sysUiListener) {
        SysUiServiceSession sysUiServiceSession;
        int i;
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "openSession: " + str + ", " + sysUiListener);
        }
        if (str == null) {
            Slog.d("BiometricSysUiManager", "openSession: token is null");
            return 0;
        }
        synchronized (this) {
            try {
                this.mHandler.removeCallbacks(this.mRunnableHandleUnbind);
                Iterator it = ((ArrayList) this.mSessionList).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        sysUiServiceSession = null;
                        break;
                    }
                    sysUiServiceSession = (SysUiServiceSession) it.next();
                    if (sysUiServiceSession.mToken.equals(str)) {
                        break;
                    }
                }
                if (sysUiServiceSession == null) {
                    sysUiServiceSession = new SysUiServiceSession(str, sysUiListener);
                    ((ArrayList) this.mSessionList).add(sysUiServiceSession);
                    if (((ArrayList) this.mSessionList).size() > 10) {
                        Slog.w("BiometricSysUiManager", "openSession: too many session list");
                        ((SysUiServiceSession) ((ArrayList) this.mSessionList).remove(0)).mListener = null;
                    }
                } else {
                    Slog.v("BiometricSysUiManager", "openSession: exist token");
                }
                bind();
                printSessionClient();
                i = sysUiServiceSession.mId;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final void printSessionClient() {
        if (Utils.DEBUG) {
            Iterator it = ((ArrayList) this.mSessionList).iterator();
            while (it.hasNext()) {
                Slog.d("BiometricSysUiManager", "Session Client = " + ((SysUiServiceSession) it.next()).toString());
            }
        }
    }

    public final void sendCommand(final Bundle bundle, final int i, final int i2, final int i3) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager semBiometricSysUiManager = SemBiometricSysUiManager.this;
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                Bundle bundle2 = bundle;
                if (semBiometricSysUiManager.findSessionId(i4) == null) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i4, "sendCommand: No exist ID, ", "BiometricSysUiManager");
                    return;
                }
                try {
                    semBiometricSysUiManager.mSysUiService.sendCommand(i4, i5, i6, bundle2);
                } catch (Exception e) {
                    MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("sendCommand: "), "BiometricSysUiManager");
                }
            }
        });
    }

    public final void sendConnectionError(final int i) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "sendConnectionError: [", "]", "BiometricSysUiManager");
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.notifySysUiErrorEvent(0, 2, i);
            }
        });
    }

    public final void unBind() {
        synchronized (this) {
            Slog.d("BiometricSysUiManager", "unBind: started");
            ISemBiometricSysUiService iSemBiometricSysUiService = this.mSysUiService;
            if (iSemBiometricSysUiService != null) {
                try {
                    iSemBiometricSysUiService.asBinder().unlinkToDeath(this, 0);
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "unBind: " + e.getMessage());
                }
            }
            try {
                this.mContext.unbindService(this);
            } catch (Exception e2) {
                Slog.w("BiometricSysUiManager", "unBind: " + e2.getMessage());
            }
            cleanup();
        }
    }
}
