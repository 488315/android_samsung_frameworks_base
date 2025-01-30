package com.android.server.desktopmode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.BleAdvertiserServiceManager;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IBleAdvertiserService;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public class BleAdvertiserServiceManager {
    public static final String TAG = "[DMS]" + BleAdvertiserServiceManager.class.getSimpleName();
    public boolean mBound;
    public int mConnectionBackoffAttempts;
    public final Context mContext;
    public int mCurrentUserId;
    public final IBinder.DeathRecipient mDeathRecipient;
    public final Handler mHandler;
    public IBleAdvertiserService mService;
    public final StateManager.StateListener mStateListener;
    public final Runnable mBindServiceRunnable = new Runnable() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            BleAdvertiserServiceManager.this.bindService();
        }
    };
    public final Runnable mDeferredConnectionCallback = new Runnable() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            BleAdvertiserServiceManager.this.lambda$new$0();
        }
    };
    public final ServiceConnection mServiceConnection = new ServiceConnectionC10981();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        Log.m12w(TAG, "Binder supposed established connection but actual connection to service timed out, trying again");
        retryConnectionWithBackoff();
    }

    /* renamed from: com.android.server.desktopmode.BleAdvertiserServiceManager$1 */
    public class ServiceConnectionC10981 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        public ServiceConnectionC10981() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
            Utils.runOnHandlerThread(BleAdvertiserServiceManager.this.mHandler, new Runnable() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BleAdvertiserServiceManager.ServiceConnectionC10981.this.lambda$onServiceConnected$0(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0(IBinder iBinder) {
            if (BleAdvertiserServiceManager.this.mService != null) {
                return;
            }
            BleAdvertiserServiceManager.this.mConnectionBackoffAttempts = 0;
            BleAdvertiserServiceManager.this.mHandler.removeCallbacks(BleAdvertiserServiceManager.this.mDeferredConnectionCallback);
            BleAdvertiserServiceManager.this.mHandler.removeCallbacks(BleAdvertiserServiceManager.this.mBindServiceRunnable);
            try {
                iBinder.linkToDeath(BleAdvertiserServiceManager.this.mDeathRecipient, 0);
                BleAdvertiserServiceManager.this.mService = IBleAdvertiserService.Stub.asInterface(iBinder);
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(BleAdvertiserServiceManager.TAG, "onServiceConnected(), mService=" + BleAdvertiserServiceManager.this.mService);
                }
            } catch (RemoteException e) {
                Log.m9e(BleAdvertiserServiceManager.TAG, "Lost connection to the service", e);
                BleAdvertiserServiceManager.this.unbindService();
                BleAdvertiserServiceManager.this.retryConnectionWithBackoff();
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(final ComponentName componentName) {
            Utils.runOnHandlerThread(BleAdvertiserServiceManager.this.mHandler, new Runnable() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BleAdvertiserServiceManager.ServiceConnectionC10981.this.lambda$onNullBinding$1(componentName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNullBinding$1(ComponentName componentName) {
            Log.m12w(BleAdvertiserServiceManager.TAG, "Null binding of '" + componentName + "', try reconnecting");
            BleAdvertiserServiceManager.this.retryConnectionWithBackoff();
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(final ComponentName componentName) {
            Utils.runOnHandlerThread(BleAdvertiserServiceManager.this.mHandler, new Runnable() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BleAdvertiserServiceManager.ServiceConnectionC10981.this.lambda$onBindingDied$2(componentName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindingDied$2(ComponentName componentName) {
            Log.m12w(BleAdvertiserServiceManager.TAG, "Binding died of '" + componentName + "', try reconnecting");
            BleAdvertiserServiceManager.this.retryConnectionWithBackoff();
        }
    }

    public BleAdvertiserServiceManager(Context context, ServiceThread serviceThread, IStateManager iStateManager) {
        StateManager.StateListener stateListener = new StateManager.StateListener() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager.2
            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onUserChanged(State state) {
                BleAdvertiserServiceManager.this.setCurrentUserId(state.getCurrentUserId());
            }
        };
        this.mStateListener = stateListener;
        this.mConnectionBackoffAttempts = 0;
        this.mCurrentUserId = -10000;
        this.mContext = context;
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                BleAdvertiserServiceManager.this.lambda$new$1();
            }
        };
        iStateManager.registerListener(stateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        Log.m12w(TAG, "Binder died, reconnecting");
        Utils.runOnHandlerThread(this.mHandler, this.mBindServiceRunnable);
    }

    public void setCurrentUserId(final int i) {
        Utils.runOnHandlerThread(this.mHandler, new Runnable() { // from class: com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BleAdvertiserServiceManager.this.lambda$setCurrentUserId$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurrentUserId$2(int i) {
        if (this.mCurrentUserId != i) {
            this.mCurrentUserId = i;
        }
    }

    public boolean isBound() {
        return this.mBound;
    }

    public void bindService() {
        if (this.mHandler.hasCallbacks(this.mDeferredConnectionCallback)) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "bindService(), mService=" + this.mService);
        }
        unbindService();
        this.mHandler.removeCallbacks(this.mBindServiceRunnable);
        try {
            boolean bindServiceAsUser = this.mContext.bindServiceAsUser(new Intent().setClassName("com.sec.android.desktopmode.uiservice", "com.sec.android.desktopmode.uiservice.ble.BleAdvertiserService"), this.mServiceConnection, 1, UserHandle.of(this.mCurrentUserId));
            this.mBound = bindServiceAsUser;
            if (bindServiceAsUser) {
                this.mHandler.postDelayed(this.mDeferredConnectionCallback, 5000L);
            } else {
                retryConnectionWithBackoff();
            }
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to bind service", e);
        }
    }

    public void unbindService() {
        if (this.mBound || this.mService != null) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "unbindService(), mBound=" + this.mBound + ", mService=" + this.mService);
            }
            if (this.mBound) {
                this.mContext.unbindService(this.mServiceConnection);
                this.mBound = false;
            }
            IBleAdvertiserService iBleAdvertiserService = this.mService;
            if (iBleAdvertiserService != null) {
                try {
                    iBleAdvertiserService.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                } catch (NoSuchElementException e) {
                    Log.m9e(TAG, "Failed to unlink death recipient", e);
                }
                this.mService = null;
            }
        }
    }

    public final void retryConnectionWithBackoff() {
        if (this.mHandler.hasCallbacks(this.mBindServiceRunnable)) {
            return;
        }
        long min = (long) Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
        this.mHandler.postDelayed(this.mBindServiceRunnable, min);
        this.mConnectionBackoffAttempts++;
        Log.m12w(TAG, "Failed to bind service on attempt " + this.mConnectionBackoffAttempts + " will try again in " + min + "ms");
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + BleAdvertiserServiceManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mBound=" + this.mBound);
        indentingPrintWriter.decreaseIndent();
    }
}
