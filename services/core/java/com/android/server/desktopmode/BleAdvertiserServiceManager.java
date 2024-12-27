package com.android.server.desktopmode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;

import com.android.server.ServiceThread;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IBleAdvertiserService;

import java.util.NoSuchElementException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BleAdvertiserServiceManager {
    public final BleAdvertiserServiceManager$$ExternalSyntheticLambda0 mBindServiceRunnable;
    public boolean mBound;
    public int mConnectionBackoffAttempts;
    public final Context mContext;
    public int mCurrentUserId;
    public final BleAdvertiserServiceManager$$ExternalSyntheticLambda2 mDeathRecipient;
    public final BleAdvertiserServiceManager$$ExternalSyntheticLambda0 mDeferredConnectionCallback;
    public final Handler mHandler;
    public IBleAdvertiserService mService;
    public final AnonymousClass1 mServiceConnection = new AnonymousClass1();
    public final AnonymousClass2 mStateListener;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.BleAdvertiserServiceManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {}

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            Utils.runOnHandlerThread(
                    BleAdvertiserServiceManager.this.mHandler,
                    new BleAdvertiserServiceManager$1$$ExternalSyntheticLambda0(
                            this, componentName, 1));
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Utils.runOnHandlerThread(
                    BleAdvertiserServiceManager.this.mHandler,
                    new BleAdvertiserServiceManager$1$$ExternalSyntheticLambda0(
                            this, componentName, 0));
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Utils.runOnHandlerThread(
                    BleAdvertiserServiceManager.this.mHandler,
                    new BleAdvertiserServiceManager$1$$ExternalSyntheticLambda0(this, iBinder, 2));
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {}
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda2] */
    public BleAdvertiserServiceManager(
            Context context, ServiceThread serviceThread, IStateManager iStateManager) {
        final int i = 0;
        this.mBindServiceRunnable =
                new Runnable(this) { // from class:
                    // com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ BleAdvertiserServiceManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        BleAdvertiserServiceManager bleAdvertiserServiceManager = this.f$0;
                        switch (i2) {
                            case 0:
                                bleAdvertiserServiceManager.bindService();
                                break;
                            default:
                                bleAdvertiserServiceManager.getClass();
                                Log.w(
                                        "[DMS]BleAdvertiserServiceManager",
                                        "Binder supposed established connection but actual"
                                            + " connection to service timed out, trying again");
                                bleAdvertiserServiceManager.retryConnectionWithBackoff();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mDeferredConnectionCallback =
                new Runnable(this) { // from class:
                    // com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ BleAdvertiserServiceManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        BleAdvertiserServiceManager bleAdvertiserServiceManager = this.f$0;
                        switch (i22) {
                            case 0:
                                bleAdvertiserServiceManager.bindService();
                                break;
                            default:
                                bleAdvertiserServiceManager.getClass();
                                Log.w(
                                        "[DMS]BleAdvertiserServiceManager",
                                        "Binder supposed established connection but actual"
                                            + " connection to service timed out, trying again");
                                bleAdvertiserServiceManager.retryConnectionWithBackoff();
                                break;
                        }
                    }
                };
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.BleAdvertiserServiceManager.2
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onUserChanged(StateManager.InternalState internalState) {
                        final int i3 = internalState.mCurrentUserId;
                        final BleAdvertiserServiceManager bleAdvertiserServiceManager =
                                BleAdvertiserServiceManager.this;
                        Utils.runOnHandlerThread(
                                bleAdvertiserServiceManager.mHandler,
                                new Runnable() { // from class:
                                                 // com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BleAdvertiserServiceManager bleAdvertiserServiceManager2 =
                                                BleAdvertiserServiceManager.this;
                                        int i4 = i3;
                                        if (bleAdvertiserServiceManager2.mCurrentUserId != i4) {
                                            bleAdvertiserServiceManager2.mCurrentUserId = i4;
                                        }
                                    }
                                });
                    }
                };
        this.mConnectionBackoffAttempts = 0;
        this.mCurrentUserId = -10000;
        this.mContext = context;
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mDeathRecipient =
                new IBinder
                        .DeathRecipient() { // from class:
                                            // com.android.server.desktopmode.BleAdvertiserServiceManager$$ExternalSyntheticLambda2
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        BleAdvertiserServiceManager bleAdvertiserServiceManager =
                                BleAdvertiserServiceManager.this;
                        bleAdvertiserServiceManager.getClass();
                        Log.w("[DMS]BleAdvertiserServiceManager", "Binder died, reconnecting");
                        Utils.runOnHandlerThread(
                                bleAdvertiserServiceManager.mHandler,
                                bleAdvertiserServiceManager.mBindServiceRunnable);
                    }
                };
        ((StateManager) iStateManager).registerListener(stateListener);
    }

    public final void bindService() {
        Handler handler = this.mHandler;
        BleAdvertiserServiceManager$$ExternalSyntheticLambda0
                bleAdvertiserServiceManager$$ExternalSyntheticLambda0 =
                        this.mDeferredConnectionCallback;
        if (handler.hasCallbacks(bleAdvertiserServiceManager$$ExternalSyntheticLambda0)) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]BleAdvertiserServiceManager", "bindService(), mService=" + this.mService);
        }
        unbindService();
        handler.removeCallbacks(this.mBindServiceRunnable);
        try {
            boolean bindServiceAsUser =
                    this.mContext.bindServiceAsUser(
                            new Intent()
                                    .setClassName(
                                            "com.sec.android.desktopmode.uiservice",
                                            "com.sec.android.desktopmode.uiservice.ble.BleAdvertiserService"),
                            this.mServiceConnection,
                            1,
                            UserHandle.of(this.mCurrentUserId));
            this.mBound = bindServiceAsUser;
            if (bindServiceAsUser) {
                handler.postDelayed(bleAdvertiserServiceManager$$ExternalSyntheticLambda0, 5000L);
            } else {
                retryConnectionWithBackoff();
            }
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]BleAdvertiserServiceManager", "Failed to bind service", e);
        }
    }

    public final void retryConnectionWithBackoff() {
        Handler handler = this.mHandler;
        BleAdvertiserServiceManager$$ExternalSyntheticLambda0
                bleAdvertiserServiceManager$$ExternalSyntheticLambda0 = this.mBindServiceRunnable;
        if (handler.hasCallbacks(bleAdvertiserServiceManager$$ExternalSyntheticLambda0)) {
            return;
        }
        long min = (long) Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
        handler.postDelayed(bleAdvertiserServiceManager$$ExternalSyntheticLambda0, min);
        this.mConnectionBackoffAttempts++;
        Log.w(
                "[DMS]BleAdvertiserServiceManager",
                "Failed to bind service on attempt "
                        + this.mConnectionBackoffAttempts
                        + " will try again in "
                        + min
                        + "ms");
    }

    public final void unbindService() {
        if (this.mBound || this.mService != null) {
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]BleAdvertiserServiceManager",
                        "unbindService(), mBound=" + this.mBound + ", mService=" + this.mService);
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
                    Log.e(
                            "[DMS]BleAdvertiserServiceManager",
                            "Failed to unlink death recipient",
                            e);
                }
                this.mService = null;
            }
        }
    }
}
