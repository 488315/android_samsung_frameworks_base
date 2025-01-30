package com.android.server.biometrics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.biometrics.PromptInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricSysUiManager;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiService;
import com.samsung.android.biometrics.SemBiometricConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class SemBiometricSysUiManager implements ServiceConnection, IBinder.DeathRecipient, SemBiometricConstants {
    public static SemBiometricSysUiManager sInstance;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsConnected;
    public boolean mIsWaitingForConnection;
    public ISemBiometricSysUiService mSysUiService;
    public final List mPendingCommand = new ArrayList();
    public final List mSessionList = new ArrayList();
    public final AtomicBoolean mKeepBind = new AtomicBoolean(false);
    public final Runnable mRunnableHandleUnbind = new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            SemBiometricSysUiManager.this.lambda$new$0();
        }
    };
    public final Runnable mRunnableHandleBindTimeout = new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            SemBiometricSysUiManager.this.lambda$new$1();
        }
    };
    public final ISemBiometricSysUiCallback mSysUiServiceReceiver = new C08281();

    public interface SysUiListener {
        default void onDismissed(int i, byte[] bArr) {
        }

        void onError(int i, int i2);

        default void onEvent(int i, int i2) {
        }

        default void onTouchEvent(int i) {
        }

        default void onTryAgainPressed(int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this) {
            if (this.mSessionList.isEmpty() && this.mIsConnected && !this.mKeepBind.get()) {
                unBind();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        Slog.w("BiometricSysUiManager", "handle binding timeout");
        notifySysUiErrorEvent(0, 2, 2);
        unBind();
    }

    public class SysUiServiceSession {
        public final int mId = hashCode();
        public SysUiListener mListener;
        public final String mToken;

        public SysUiServiceSession(String str, SysUiListener sysUiListener) {
            this.mToken = str;
            this.mListener = sysUiListener;
        }

        public void destroy() {
            this.mListener = null;
        }

        public String toString() {
            return "[" + this.mId + "], [" + this.mToken + "], [" + this.mListener + "]";
        }
    }

    public static SemBiometricSysUiManager get() {
        return sInstance;
    }

    public static synchronized void newInstance(Context context) {
        synchronized (SemBiometricSysUiManager.class) {
            if (sInstance == null) {
                sInstance = new SemBiometricSysUiManager(context, SemBioFgThread.get().getLooper());
            }
        }
    }

    public static synchronized void injectMockForTest(SemBiometricSysUiManager semBiometricSysUiManager) {
        synchronized (SemBiometricSysUiManager.class) {
            sInstance = semBiometricSysUiManager;
        }
    }

    public SemBiometricSysUiManager(Context context, Looper looper) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
    }

    public final void cleanup() {
        synchronized (this) {
            this.mPendingCommand.clear();
            this.mIsConnected = false;
            this.mIsWaitingForConnection = false;
            this.mSysUiService = null;
            this.mHandler.removeCallbacks(this.mRunnableHandleBindTimeout);
        }
    }

    public final SysUiServiceSession findSessionId(int i) {
        for (SysUiServiceSession sysUiServiceSession : this.mSessionList) {
            if (sysUiServiceSession.mId == i) {
                return sysUiServiceSession;
            }
        }
        return null;
    }

    public final SysUiServiceSession findSessionToken(String str) {
        for (SysUiServiceSession sysUiServiceSession : this.mSessionList) {
            if (sysUiServiceSession.mToken.equals(str)) {
                return sysUiServiceSession;
            }
        }
        return null;
    }

    public final void printSessionClient() {
        if (Utils.DEBUG) {
            Iterator it = this.mSessionList.iterator();
            while (it.hasNext()) {
                Slog.d("BiometricSysUiManager", "Session Client = " + ((SysUiServiceSession) it.next()).toString());
            }
        }
    }

    public void keepBindService(boolean z) {
        this.mKeepBind.set(z);
        if (z) {
            return;
        }
        this.mHandler.postDelayed(this.mRunnableHandleUnbind, 4000L);
    }

    public int openSession(String str, SysUiListener sysUiListener) {
        int i;
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "openSession: " + str + ", " + sysUiListener);
        }
        if (str == null) {
            Slog.d("BiometricSysUiManager", "openSession: token is null");
            return 0;
        }
        synchronized (this) {
            this.mHandler.removeCallbacks(this.mRunnableHandleUnbind);
            SysUiServiceSession findSessionToken = findSessionToken(str);
            if (findSessionToken == null) {
                findSessionToken = new SysUiServiceSession(str, sysUiListener);
                this.mSessionList.add(findSessionToken);
                if (this.mSessionList.size() > 10) {
                    Slog.w("BiometricSysUiManager", "openSession: too many session list");
                    ((SysUiServiceSession) this.mSessionList.remove(0)).destroy();
                }
            } else {
                Slog.v("BiometricSysUiManager", "openSession: exist token");
            }
            bind();
            printSessionClient();
            i = findSessionToken.mId;
        }
        return i;
    }

    public void closeSession(int i) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "closeSession: sessionId = [" + i + "]");
        }
        synchronized (this) {
            SysUiServiceSession findSessionId = findSessionId(i);
            if (findSessionId != null) {
                this.mSessionList.remove(findSessionId);
                findSessionId.destroy();
                if (this.mSessionList.isEmpty() && this.mIsConnected) {
                    this.mHandler.removeCallbacks(this.mRunnableHandleUnbind);
                    this.mHandler.postDelayed(this.mRunnableHandleUnbind, 4000L);
                }
            }
            printSessionClient();
        }
    }

    public void cancelUnbind() {
        this.mHandler.removeCallbacks(this.mRunnableHandleUnbind);
    }

    public final void handleRequest(Runnable runnable) {
        synchronized (this) {
            if (this.mSysUiService != null) {
                runnable.run();
            } else if (this.mIsWaitingForConnection) {
                if (Utils.DEBUG) {
                    Slog.i("BiometricSysUiManager", "handleRequest: Add pending list");
                }
                this.mPendingCommand.add(runnable);
            }
        }
    }

    public final void bind() {
        try {
            synchronized (this) {
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
                this.mContext.bindService(intent, i, SemBioFgThread.get(), this);
                this.mIsWaitingForConnection = true;
                this.mHandler.postDelayed(this.mRunnableHandleBindTimeout, 3000L);
            }
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "bind: " + e.getMessage());
            sendConnectionError(-2);
        }
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

    public final void runPendingCommand() {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$runPendingCommand$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runPendingCommand$2() {
        synchronized (this) {
            Iterator it = this.mPendingCommand.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
            this.mPendingCommand.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
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
            runPendingCommand();
            if (this.mSessionList.isEmpty()) {
                this.mHandler.postDelayed(this.mRunnableHandleUnbind, 4000L);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
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

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Slog.e("BiometricSysUiManager", "binderDied: handle error");
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$binderDied$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$3() {
        notifySysUiErrorEvent(0, 1, 0);
        cleanup();
    }

    public void show(int i, int i2, Bundle bundle) {
        show(i, i2, bundle, false, 0, null, 0L, null);
    }

    public void show(final int i, final int i2, final Bundle bundle, final boolean z, final int i3, final String str, final long j, final PromptInfo promptInfo) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$show$4(i, i2, z, i3, str, j, bundle, promptInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$4(int i, int i2, boolean z, int i3, String str, long j, Bundle bundle, PromptInfo promptInfo) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "show: " + i + ", " + i2 + ", " + z + ", " + i3 + ", " + str + ", " + j);
        }
        if (findSessionId(i) == null) {
            Slog.d("BiometricSysUiManager", "show: No exist ID, " + i);
            return;
        }
        try {
            this.mSysUiService.showBiometricDialog(i, i2, bundle, this.mSysUiServiceReceiver, z, i3, str, j, promptInfo);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "show: " + e.getMessage());
            sendConnectionError(-1);
        }
    }

    public void hide(final int i, final int i2, final int i3) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$hide$5(i, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hide$5(int i, int i2, int i3) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "hide: " + i + ", " + i2 + ", " + i3);
        }
        if (findSessionId(i) == null) {
            Slog.d("BiometricSysUiManager", "hide: No exist ID, " + i);
            return;
        }
        try {
            this.mSysUiService.hideBiometricDialog(i, i2, i3);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "hide: " + e.getMessage());
            sendConnectionError(-1);
        }
    }

    public void onAuthenticated(final int i, final int i2, final boolean z, final String str) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$onAuthenticated$6(i, i2, z, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAuthenticated$6(int i, int i2, boolean z, String str) {
        if (findSessionId(i) == null) {
            Slog.d("BiometricSysUiManager", "onAuthenticated: No exist ID, " + i);
            return;
        }
        try {
            this.mSysUiService.onBiometricAuthenticated(i, i2, z, str);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "onAuthenticated: " + e.getMessage());
        }
    }

    public void onBiometricHelp(final int i, final int i2, final int i3, final int i4, final String str) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$onBiometricHelp$7(i, i2, i3, i4, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBiometricHelp$7(int i, int i2, int i3, int i4, String str) {
        if (Utils.DEBUG) {
            Slog.d("BiometricSysUiManager", "onBiometricHelp: session Id = [" + i + "]");
        }
        if (findSessionId(i) == null) {
            Slog.d("BiometricSysUiManager", "onBiometricHelp: No exist ID, " + i);
            return;
        }
        try {
            this.mSysUiService.onBiometricHelp(i, i2, i3, i4, str);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "onBiometricHelp: " + e.getMessage());
        }
    }

    public void onBiometricError(final int i, final int i2, final int i3, final int i4, final String str) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$onBiometricError$8(i, i2, i3, i4, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBiometricError$8(int i, int i2, int i3, int i4, String str) {
        if (findSessionId(i) == null) {
            Slog.d("BiometricSysUiManager", "onBiometricError: No exist ID, " + i);
            return;
        }
        try {
            this.mSysUiService.onBiometricError(i, i2, i3, i4, str);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "onBiometricError: " + e.getMessage());
        }
    }

    public void sendCommand(final int i, final int i2, final int i3, final Bundle bundle) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$sendCommand$9(i, i2, i3, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCommand$9(int i, int i2, int i3, Bundle bundle) {
        if (findSessionId(i) == null) {
            Slog.d("BiometricSysUiManager", "sendCommand: No exist ID, " + i);
            return;
        }
        try {
            this.mSysUiService.sendCommand(i, i2, i3, bundle);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "sendCommand: " + e.getMessage());
        }
    }

    public void sendCommand(final int i, final int i2, final Bundle bundle) {
        bind();
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$sendCommand$10(i, i2, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCommand$10(int i, int i2, Bundle bundle) {
        try {
            this.mSysUiService.sendCommand(0, i, i2, bundle);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "sendCommand: " + e.getMessage());
        }
    }

    public void sendCommandIfSessionExist(final int i, final int i2, final Bundle bundle) {
        handleRequest(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$sendCommandIfSessionExist$11(i, i2, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCommandIfSessionExist$11(int i, int i2, Bundle bundle) {
        if (this.mSessionList.isEmpty()) {
            Slog.d("BiometricSysUiManager", "sendCommandIfSessionExist: No exist Session");
            return;
        }
        try {
            this.mSysUiService.sendCommand(0, i, i2, bundle);
        } catch (Exception e) {
            Slog.w("BiometricSysUiManager", "sendCommandIfSessionExist: " + e.getMessage());
        }
    }

    public final void sendConnectionError(final int i) {
        Slog.d("BiometricSysUiManager", "sendConnectionError: [" + i + "]");
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SemBiometricSysUiManager.this.lambda$sendConnectionError$12(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendConnectionError$12(int i) {
        notifySysUiErrorEvent(0, 2, i);
    }

    public final void notifySysUiTouchEvent(int i, int i2) {
        synchronized (this) {
            for (SysUiServiceSession sysUiServiceSession : this.mSessionList) {
                try {
                    if (sysUiServiceSession.mListener != null && (i == 0 || i == sysUiServiceSession.mId)) {
                        sysUiServiceSession.mListener.onTouchEvent(i2);
                    }
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "notifySysUiTouchEvent: " + e.getMessage());
                }
            }
        }
    }

    public final void notifySysUiErrorEvent(int i, int i2, int i3) {
        synchronized (this) {
            for (SysUiServiceSession sysUiServiceSession : this.mSessionList) {
                try {
                    if (sysUiServiceSession.mListener != null && (i == 0 || i == sysUiServiceSession.mId)) {
                        sysUiServiceSession.mListener.onError(i2, i3);
                    }
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "notifySysUiErrorEvent: " + e.getMessage());
                }
            }
        }
    }

    public final void notifySysUiDismissedEvent(int i, int i2, byte[] bArr) {
        synchronized (this) {
            for (SysUiServiceSession sysUiServiceSession : this.mSessionList) {
                try {
                    if (sysUiServiceSession.mListener != null && (i == 0 || i == sysUiServiceSession.mId)) {
                        sysUiServiceSession.mListener.onDismissed(i2, bArr);
                    }
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "notifySysUiDismissedEvent: " + e.getMessage());
                }
            }
        }
    }

    public final void notifySysUiEvent(int i, int i2, int i3) {
        synchronized (this) {
            for (SysUiServiceSession sysUiServiceSession : this.mSessionList) {
                try {
                    if (sysUiServiceSession.mListener != null && (i == 0 || i == sysUiServiceSession.mId)) {
                        if (i2 == 1001) {
                            sysUiServiceSession.mListener.onTryAgainPressed(i3);
                        } else {
                            sysUiServiceSession.mListener.onEvent(i2, i3);
                        }
                    }
                } catch (Exception e) {
                    Slog.w("BiometricSysUiManager", "notifySysUiEvent: " + e.getMessage());
                }
            }
        }
    }

    /* renamed from: com.android.server.biometrics.SemBiometricSysUiManager$1 */
    public class C08281 extends ISemBiometricSysUiCallback.Stub {
        public C08281() {
        }

        public void onTouchEvent(int i, int i2) {
            if (Utils.DEBUG) {
                Slog.d("BiometricSysUiManager", "onTouchEvent: " + i + "," + i2);
            }
            SemBiometricSysUiManager.this.notifySysUiTouchEvent(i, i2);
        }

        public void onError(final int i, final int i2, final int i3) {
            if (Utils.DEBUG) {
                Slog.d("BiometricSysUiManager", "onError: " + i + ", " + i2 + ", " + i3);
            }
            SemBiometricSysUiManager.this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemBiometricSysUiManager.C08281.this.lambda$onError$0(i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0(int i, int i2, int i3) {
            SemBiometricSysUiManager.this.notifySysUiErrorEvent(i, i2, i3);
        }

        public void onSysUiDismissed(final int i, final int i2, final byte[] bArr) {
            if (Utils.DEBUG) {
                Slog.d("BiometricSysUiManager", "onSysUiDismissed: " + i + ", " + i2);
            }
            SemBiometricSysUiManager.this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SemBiometricSysUiManager.C08281.this.lambda$onSysUiDismissed$1(i, i2, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSysUiDismissed$1(int i, int i2, byte[] bArr) {
            SemBiometricSysUiManager.this.notifySysUiDismissedEvent(i, i2, bArr);
        }

        public void onEvent(final int i, final int i2, final int i3) {
            if (Utils.DEBUG) {
                Slog.d("BiometricSysUiManager", "onEvent: " + i + ", " + i2 + ", " + i3);
            }
            SemBiometricSysUiManager.this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.SemBiometricSysUiManager$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SemBiometricSysUiManager.C08281.this.lambda$onEvent$2(i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEvent$2(int i, int i2, int i3) {
            SemBiometricSysUiManager.this.notifySysUiEvent(i, i2, i3);
        }
    }
}
