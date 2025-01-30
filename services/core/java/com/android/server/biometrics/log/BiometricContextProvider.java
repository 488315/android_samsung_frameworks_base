package com.android.server.biometrics.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.IBiometricContextListener;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.ISessionListener;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.biometrics.SemBioFgThread;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor;
import com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayStateMonitor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class BiometricContextProvider implements BiometricContext {
    public static BiometricContextProvider sInstance;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final Handler mHandler;
    public IBiometricContextListener mIBiometricContextListener;
    public ISessionListener mISessionListener;
    public final WindowManager mWindowManager;
    public final Map mSubscribers = new ConcurrentHashMap();
    public final Map mSession = new ConcurrentHashMap();
    public int mDockState = 0;
    public int mFoldState = 0;
    public int mDisplayState = 0;
    final BroadcastReceiver mDockStateReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.log.BiometricContextProvider.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BiometricContextProvider.this.mDockState = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
        }
    };

    public static String displayStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "DISPLAY_STATE_AOD" : "DISPLAY_STATE_SCREENSAVER" : "DISPLAY_STATE_NO_UI" : "DISPLAY_STATE_LOCKSCREEN" : "DISPLAY_STATE_UNKNOWN";
    }

    public static BiometricContextProvider defaultProvider(Context context) {
        synchronized (BiometricContextProvider.class) {
            if (sInstance == null) {
                try {
                    sInstance = new BiometricContextProvider(context, (WindowManager) context.getSystemService("window"), IStatusBarService.Stub.asInterface(ServiceManager.getServiceOrThrow("statusbar")), SemBioFgThread.get().getHandler(), new AuthSessionCoordinator());
                } catch (ServiceManager.ServiceNotFoundException e) {
                    throw new IllegalStateException("Failed to find required service", e);
                }
            }
        }
        return sInstance;
    }

    public BiometricContextProvider(Context context, WindowManager windowManager, IStatusBarService iStatusBarService, Handler handler, AuthSessionCoordinator authSessionCoordinator) {
        this.mWindowManager = windowManager;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mHandler = handler;
        subscribeBiometricContextListener(iStatusBarService);
        subscribeDockState(context);
    }

    public final void subscribeBiometricContextListener(IStatusBarService iStatusBarService) {
        try {
            IBiometricContextListener.Stub stub = new IBiometricContextListener.Stub() { // from class: com.android.server.biometrics.log.BiometricContextProvider.2
                public void onFoldChanged(int i) {
                    if (Utils.DEBUG) {
                        Slog.d("BiometricContextProvider", "onFoldChanged: " + i);
                    }
                    BiometricContextProvider.this.mFoldState = i;
                }

                public void onDisplayStateChanged(int i) {
                    Slog.i("BiometricContextProvider", "onDisplayStateChanged: " + BiometricContextProvider.displayStateToString(i));
                    if (i != BiometricContextProvider.this.mDisplayState) {
                        BiometricContextProvider.this.mDisplayState = i;
                        BiometricContextProvider.this.notifyChanged();
                    }
                }
            };
            this.mIBiometricContextListener = stub;
            iStatusBarService.setBiometicContextListener(stub);
            ISessionListener.Stub stub2 = new ISessionListener.Stub() { // from class: com.android.server.biometrics.log.BiometricContextProvider.3
                public void onSessionStarted(int i, InstanceId instanceId) {
                    if (Utils.DEBUG) {
                        Slog.d("BiometricContextProvider", "onSessionStarted: " + i + ", " + instanceId);
                    }
                    BiometricContextProvider.this.mSession.put(Integer.valueOf(i), new BiometricContextSessionInfo(instanceId));
                }

                public void onSessionEnded(int i, InstanceId instanceId) {
                    if (Utils.DEBUG) {
                        Slog.d("BiometricContextProvider", "onSessionEnded: " + i + ", " + instanceId);
                    }
                    BiometricContextSessionInfo biometricContextSessionInfo = (BiometricContextSessionInfo) BiometricContextProvider.this.mSession.remove(Integer.valueOf(i));
                    if (biometricContextSessionInfo == null || instanceId == null || biometricContextSessionInfo.getId() == instanceId.getId()) {
                        return;
                    }
                    Slog.w("BiometricContextProvider", "session id mismatch");
                }
            };
            this.mISessionListener = stub2;
            iStatusBarService.registerSessionListener(3, stub2);
        } catch (RemoteException e) {
            Slog.e("BiometricContextProvider", "Unable to register biometric context listener", e);
        }
    }

    public final void subscribeDockState(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        try {
            context.registerReceiver(this.mDockStateReceiver, intentFilter);
        } catch (Exception e) {
            Slog.w("BiometricContextProvider", "subscribeDockState: " + e.getMessage());
        }
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public OperationContextExt updateContext(OperationContextExt operationContextExt, boolean z) {
        return operationContextExt.update(this, z);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public BiometricContextSessionInfo getKeyguardEntrySessionInfo() {
        return (BiometricContextSessionInfo) this.mSession.get(1);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public BiometricContextSessionInfo getBiometricPromptSessionInfo() {
        return (BiometricContextSessionInfo) this.mSession.get(2);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isAod() {
        return this.mDisplayState == 4;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isAwake() {
        int i = this.mDisplayState;
        return i == 0 || i == 1 || i == 3;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isDisplayOn() {
        return this.mWindowManager.getDefaultDisplay().getState() == 2;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getDockedState() {
        return this.mDockState;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getFoldState() {
        return this.mFoldState;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getCurrentRotation() {
        return this.mWindowManager.getDefaultDisplay().getRotation();
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getDisplayState() {
        return this.mDisplayState;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public void subscribe(OperationContextExt operationContextExt, Consumer consumer) {
        this.mSubscribers.put(operationContextExt, consumer);
        if (operationContextExt.getDisplayState() != getDisplayState()) {
            consumer.accept(operationContextExt.update(this, operationContextExt.isCrypto()).toAidlContext());
        }
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public void unsubscribe(OperationContextExt operationContextExt) {
        this.mSubscribers.remove(operationContextExt);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public AuthSessionCoordinator getAuthSessionCoordinator() {
        return this.mAuthSessionCoordinator;
    }

    public final void notifyChanged() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.android.server.biometrics.log.BiometricContextProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricContextProvider.this.notifySubscribers();
                }
            });
        } else {
            notifySubscribers();
        }
    }

    public final void notifySubscribers() {
        this.mSubscribers.forEach(new BiConsumer() { // from class: com.android.server.biometrics.log.BiometricContextProvider$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                BiometricContextProvider.this.lambda$notifySubscribers$0((OperationContextExt) obj, (Consumer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySubscribers$0(OperationContextExt operationContextExt, Consumer consumer) {
        consumer.accept(operationContextExt.update(this, operationContextExt.isCrypto()).toAidlContext());
    }

    public String toString() {
        return "[keyguard session: " + getKeyguardEntrySessionInfo() + ", bp session: " + getBiometricPromptSessionInfo() + ", displayState: " + getDisplayState() + ", isAwake: " + isAwake() + ", isDisplayOn: " + isDisplayOn() + ", dock: " + getDockedState() + ", rotation: " + getCurrentRotation() + "]";
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public ISessionListener getISessionListener() {
        return this.mISessionListener;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public void ensureBiometricContextListener(SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor) {
        Slog.i("BiometricContextProvider", "ensureBiometricContextListener");
        try {
            IStatusBarService.Stub.asInterface(ServiceManager.getServiceOrThrow("statusbar")).setBiometicContextListener(this.mIBiometricContextListener);
        } catch (ServiceManager.ServiceNotFoundException | RemoteException e) {
            e.printStackTrace();
        }
        semBiometricDisplayStateMonitor.registerCallback(new SemBiometricDisplayMonitor.Callback() { // from class: com.android.server.biometrics.log.BiometricContextProvider.4
            @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
            public void onDisplayOn() {
            }

            @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
            public void onDisplayOff() {
                if (Utils.DEBUG) {
                    Slog.d("BiometricContextProvider", "onDisplayOff");
                }
                try {
                    BiometricContextProvider.this.mIBiometricContextListener.onDisplayStateChanged(2);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
