package com.android.server.policy.keyguard;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.dreams.DreamManagerInternal;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;

import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.server.LocalServices;
import com.android.server.UiThread;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;

import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.rune.CoreRune;

public final class KeyguardServiceDelegate {
    public final PhoneWindowManager.AnonymousClass1 mCallback;
    public final Context mContext;
    public PhoneWindowManager.AnonymousClass1 mDrawnListenerWhenConnect;
    public final AnonymousClass1 mDreamManagerStateListener;
    public final Handler mHandler;
    public final AnonymousClass2 mKeyguardConnection;
    public KeyguardServiceWrapper mKeyguardService;
    public final KeyguardState mKeyguardState;
    public PersonaManagerInternal mPersonaManagerInternal;

    public final class KeyguardExitDelegate extends IKeyguardExitCallback.Stub {
        public WindowManagerPolicy.OnKeyguardExitResult mOnKeyguardExitResult;

        public final void onKeyguardExitResult(boolean z) {
            Log.v("KeyguardServiceDelegate", "**** onKeyguardExitResult(" + z + ") CALLED ****");
            WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult =
                    this.mOnKeyguardExitResult;
            if (onKeyguardExitResult != null) {
                onKeyguardExitResult.onKeyguardExitResult(z);
            }
        }
    }

    public final class KeyguardShowDelegate extends IKeyguardDrawnCallback.Stub {
        public final PhoneWindowManager.AnonymousClass1 mDrawnListener;

        public KeyguardShowDelegate(PhoneWindowManager.AnonymousClass1 anonymousClass1) {
            this.mDrawnListener = anonymousClass1;
        }

        public final void onDrawn() {
            Log.v("KeyguardServiceDelegate", "!@BOOT: **** SHOWN CALLED ****");
            PhoneWindowManager.AnonymousClass1 anonymousClass1 = this.mDrawnListener;
            if (anonymousClass1 != null) {
                if (PhoneWindowManager.DEBUG_WAKEUP) {
                    Slog.d("WindowManager", "mKeyguardDelegate.ShowListener.onDrawn.");
                }
                ((PhoneWindowManager) anonymousClass1.this$0).mHandler.sendEmptyMessage(5);
            }
        }
    }

    public final class KeyguardState {
        public boolean bootAnimFinished;
        public boolean bootCompleted;
        public int currentUser;
        public boolean deviceHasKeyguard;
        public boolean dexOccluded;
        public boolean dreaming;
        public boolean enabled;
        public boolean inputRestricted;
        public int interactiveState;
        public volatile boolean occluded;
        public int offReason;
        public int screenState;
        public boolean secure;
        public boolean showing;
        public boolean systemIsReady;
    }

    public KeyguardServiceDelegate(
            Context context, PhoneWindowManager.AnonymousClass1 anonymousClass1) {
        KeyguardState keyguardState = new KeyguardState();
        keyguardState.currentUser = 0;
        keyguardState.showing = true;
        keyguardState.occluded = false;
        keyguardState.secure = true;
        keyguardState.deviceHasKeyguard = true;
        keyguardState.enabled = true;
        keyguardState.bootAnimFinished = false;
        this.mKeyguardState = keyguardState;
        this.mDreamManagerStateListener =
                new DreamManagerInternal
                        .DreamManagerStateListener() { // from class:
                                                       // com.android.server.policy.keyguard.KeyguardServiceDelegate.1
                    public final void onDreamingStarted() {
                        KeyguardServiceDelegate keyguardServiceDelegate =
                                KeyguardServiceDelegate.this;
                        KeyguardServiceWrapper keyguardServiceWrapper =
                                keyguardServiceDelegate.mKeyguardService;
                        if (keyguardServiceWrapper != null) {
                            keyguardServiceWrapper.onDreamingStarted();
                        }
                        keyguardServiceDelegate.mKeyguardState.dreaming = true;
                    }

                    public final void onDreamingStopped() {
                        KeyguardServiceDelegate keyguardServiceDelegate =
                                KeyguardServiceDelegate.this;
                        KeyguardServiceWrapper keyguardServiceWrapper =
                                keyguardServiceDelegate.mKeyguardService;
                        if (keyguardServiceWrapper != null) {
                            keyguardServiceWrapper.onDreamingStopped();
                        }
                        keyguardServiceDelegate.mKeyguardState.dreaming = false;
                    }
                };
        this.mKeyguardConnection =
                new ServiceConnection() { // from class:
                    // com.android.server.policy.keyguard.KeyguardServiceDelegate.2
                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(
                            ComponentName componentName, IBinder iBinder) {
                        Log.v("KeyguardServiceDelegate", "!@BOOT: *** Keyguard connected (yay!)");
                        KeyguardServiceDelegate keyguardServiceDelegate =
                                KeyguardServiceDelegate.this;
                        Context context2 = keyguardServiceDelegate.mContext;
                        IKeyguardService asInterface = IKeyguardService.Stub.asInterface(iBinder);
                        PhoneWindowManager.AnonymousClass1 anonymousClass12 =
                                KeyguardServiceDelegate.this.mCallback;
                        KeyguardServiceWrapper keyguardServiceWrapper =
                                new KeyguardServiceWrapper();
                        keyguardServiceWrapper.mService = asInterface;
                        Slog.v(
                                "KeyguardServiceWrapper",
                                "*** Started to create KeyguardServiceWrapper.");
                        KeyguardStateMonitor keyguardStateMonitor =
                                new KeyguardStateMonitor(context2, asInterface, anonymousClass12);
                        keyguardServiceWrapper.mKeyguardStateMonitor = keyguardStateMonitor;
                        Slog.v(
                                "KeyguardServiceWrapper",
                                "*** Finished to create KeyguardServiceWrapper,"
                                    + " mKeyguardStateMonitor="
                                        + keyguardStateMonitor);
                        keyguardServiceDelegate.mKeyguardService = keyguardServiceWrapper;
                        Log.v(
                                "KeyguardServiceDelegate",
                                "!@BOOT: *** Created keyguardService="
                                        + KeyguardServiceDelegate.this.mKeyguardService);
                        KeyguardServiceDelegate keyguardServiceDelegate2 =
                                KeyguardServiceDelegate.this;
                        if (keyguardServiceDelegate2.mKeyguardState.systemIsReady) {
                            keyguardServiceDelegate2.mKeyguardService.onSystemReady();
                            KeyguardServiceDelegate keyguardServiceDelegate3 =
                                    KeyguardServiceDelegate.this;
                            int i = keyguardServiceDelegate3.mKeyguardState.currentUser;
                            if (i != -10000) {
                                keyguardServiceDelegate3.mKeyguardService.setCurrentUser(i);
                            }
                            KeyguardServiceDelegate keyguardServiceDelegate4 =
                                    KeyguardServiceDelegate.this;
                            int i2 = keyguardServiceDelegate4.mKeyguardState.interactiveState;
                            if (i2 == 2 || i2 == 1) {
                                keyguardServiceDelegate4.mKeyguardService.onStartedWakingUp(
                                        0, false);
                            }
                            KeyguardServiceDelegate keyguardServiceDelegate5 =
                                    KeyguardServiceDelegate.this;
                            if (keyguardServiceDelegate5.mKeyguardState.interactiveState == 2) {
                                keyguardServiceDelegate5.mKeyguardService.onFinishedWakingUp();
                            }
                            KeyguardServiceDelegate keyguardServiceDelegate6 =
                                    KeyguardServiceDelegate.this;
                            int i3 = keyguardServiceDelegate6.mKeyguardState.screenState;
                            if (i3 == 2 || i3 == 1) {
                                keyguardServiceDelegate6.mKeyguardService.onScreenTurningOn(
                                        new KeyguardShowDelegate(
                                                KeyguardServiceDelegate.this
                                                        .mDrawnListenerWhenConnect));
                            }
                            KeyguardServiceDelegate keyguardServiceDelegate7 =
                                    KeyguardServiceDelegate.this;
                            if (keyguardServiceDelegate7.mKeyguardState.screenState == 2) {
                                keyguardServiceDelegate7.mKeyguardService.onScreenTurnedOn();
                            }
                            KeyguardServiceDelegate.this.mDrawnListenerWhenConnect = null;
                        }
                        KeyguardServiceDelegate keyguardServiceDelegate8 =
                                KeyguardServiceDelegate.this;
                        if (keyguardServiceDelegate8.mKeyguardState.bootCompleted) {
                            keyguardServiceDelegate8.mKeyguardService.onBootCompleted();
                        }
                        if (KeyguardServiceDelegate.this.mKeyguardState.occluded) {
                            KeyguardServiceDelegate keyguardServiceDelegate9 =
                                    KeyguardServiceDelegate.this;
                            keyguardServiceDelegate9.mKeyguardService.setOccluded(
                                    keyguardServiceDelegate9.mKeyguardState.occluded, false);
                        }
                        KeyguardServiceDelegate keyguardServiceDelegate10 =
                                KeyguardServiceDelegate.this;
                        boolean z = keyguardServiceDelegate10.mKeyguardState.enabled;
                        if (!z) {
                            keyguardServiceDelegate10.mKeyguardService.setKeyguardEnabled(z);
                        }
                        KeyguardServiceDelegate keyguardServiceDelegate11 =
                                KeyguardServiceDelegate.this;
                        if (keyguardServiceDelegate11.mKeyguardState.dreaming) {
                            keyguardServiceDelegate11.mKeyguardService.onDreamingStarted();
                        }
                        KeyguardServiceDelegate keyguardServiceDelegate12 =
                                KeyguardServiceDelegate.this;
                        if (keyguardServiceDelegate12.mKeyguardState.bootAnimFinished) {
                            keyguardServiceDelegate12.mKeyguardService.onFinishedBootAnim();
                        }
                        KeyguardServiceDelegate keyguardServiceDelegate13 =
                                KeyguardServiceDelegate.this;
                        boolean z2 = keyguardServiceDelegate13.mKeyguardState.dexOccluded;
                        if (z2) {
                            keyguardServiceDelegate13.mKeyguardService.setDexOccluded(z2);
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                        Log.v("KeyguardServiceDelegate", "*** Keyguard disconnected (boo!)");
                        KeyguardServiceDelegate keyguardServiceDelegate =
                                KeyguardServiceDelegate.this;
                        keyguardServiceDelegate.mKeyguardService = null;
                        KeyguardState keyguardState2 = keyguardServiceDelegate.mKeyguardState;
                        keyguardState2.showing = true;
                        keyguardState2.occluded = false;
                        keyguardState2.secure = true;
                        keyguardState2.deviceHasKeyguard = true;
                        keyguardState2.enabled = true;
                        keyguardState2.bootAnimFinished = false;
                        KeyguardServiceDelegate.this.mHandler.post(
                                new KeyguardServiceDelegate$2$$ExternalSyntheticLambda0());
                    }
                };
        this.mContext = context;
        this.mHandler = UiThread.getHandler();
        this.mCallback = anonymousClass1;
    }

    public final void bindService(Context context) {
        Intent intent = new Intent();
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(
                        context.getApplicationContext()
                                .getResources()
                                .getString(R.string.emailTypeOther));
        intent.addFlags(256);
        intent.setComponent(unflattenFromString);
        if (context.bindServiceAsUser(
                intent, this.mKeyguardConnection, 1, this.mHandler, UserHandle.SYSTEM)) {
            Log.v("KeyguardServiceDelegate", "!@BOOT: *** Keyguard started");
        } else {
            Log.v(
                    "KeyguardServiceDelegate",
                    "!@BOOT: *** Keyguard: can't bind to " + unflattenFromString);
            KeyguardState keyguardState = this.mKeyguardState;
            keyguardState.showing = false;
            keyguardState.secure = false;
            synchronized (keyguardState) {
                this.mKeyguardState.deviceHasKeyguard = false;
            }
        }
        DreamManagerInternal dreamManagerInternal =
                (DreamManagerInternal) LocalServices.getService(DreamManagerInternal.class);
        if (CoreRune.SYSUI_GRADLE_BUILD) {
            dreamManagerInternal.unregisterDreamManagerStateListener(
                    this.mDreamManagerStateListener);
        }
        dreamManagerInternal.registerDreamManagerStateListener(this.mDreamManagerStateListener);
    }

    public final boolean isShowing() {
        KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
        if (keyguardServiceWrapper != null) {
            this.mKeyguardState.showing = keyguardServiceWrapper.mKeyguardStateMonitor.mIsShowing;
        }
        return this.mKeyguardState.showing;
    }

    public final boolean isSimLocked() {
        KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardService;
        if (keyguardServiceWrapper == null) {
            return false;
        }
        KeyguardStateMonitor keyguardStateMonitor = keyguardServiceWrapper.mKeyguardStateMonitor;
        if (keyguardStateMonitor != null) {
            return keyguardStateMonitor.mSimSecure;
        }
        Slog.w(
                "KeyguardServiceWrapper",
                "mKeyguardStateMonitor is null caller=" + Debug.getCallers(5));
        return false;
    }

    public final void onScreenTurningOn(PhoneWindowManager.AnonymousClass1 anonymousClass1) {
        if (this.mKeyguardService != null) {
            Log.v(
                    "KeyguardServiceDelegate",
                    "onScreenTurnedOn(showListener = " + anonymousClass1 + ")");
            this.mKeyguardService.onScreenTurningOn(new KeyguardShowDelegate(anonymousClass1));
        } else {
            Slog.w("KeyguardServiceDelegate", "onScreenTurningOn(): no keyguard service!");
            this.mDrawnListenerWhenConnect = anonymousClass1;
        }
        this.mKeyguardState.screenState = 1;
    }

    public final void setOccluded(boolean z) {
        if (this.mKeyguardService != null) {
            Log.v("KeyguardServiceDelegate", "setOccluded(" + z + ")");
            EventLog.writeEvent(31008, Integer.valueOf(z ? 1 : 0), 0, 0, "setOccluded");
            this.mKeyguardService.setOccluded(z, false);
        }
        this.mKeyguardState.occluded = z;
    }
}
