package com.android.server.desktopmode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import com.samsung.android.desktopmode.IDesktopModeUiService;
import com.samsung.android.desktopmode.IDesktopModeUiServiceCallback;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UiManager {
    public final UiManager$$ExternalSyntheticLambda6 mBindServiceRunnable;
    public boolean mBound;
    public boolean mChangingStandaloneMode;
    public int mConnectionBackoffAttempts;
    public final Context mContext;
    public int mCurrentUserId;
    public final UiManager$$ExternalSyntheticLambda9 mDeathRecipient;
    public final UiManager$$ExternalSyntheticLambda6 mDeferredConnectionCallback;
    public final UiCommandHandler mHandler;
    public final PendingUiCommands mPendingUiCommands;
    public IDesktopModeUiService mService;
    public final AnonymousClass1 mServiceConnection = new AnonymousClass1();
    public final AnonymousClass2 mStateListener;
    public final IStateManager mStateManager;
    public final UiManager$$ExternalSyntheticLambda6 mUnbindServiceRunnable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.UiManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            Utils.runOnHandlerThread(UiManager.this.mHandler, new UiManager$1$$ExternalSyntheticLambda0(this, componentName, 1));
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Utils.runOnHandlerThread(UiManager.this.mHandler, new UiManager$1$$ExternalSyntheticLambda0(this, componentName, 0));
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Utils.runOnHandlerThread(UiManager.this.mHandler, new UiManager$1$$ExternalSyntheticLambda0(this, iBinder, 2));
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.UiManager$5, reason: invalid class name */
    public final class AnonymousClass5 extends IDesktopModeUiServiceCallback.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object val$callback;

        public /* synthetic */ AnonymousClass5(int i, Object obj) {
            this.$r8$classId = i;
            this.val$callback = obj;
        }

        private final void onAnimationComplete$com$android$server$desktopmode$UiManager$4() {
        }

        private final void onAnimationComplete$com$android$server$desktopmode$UiManager$6() {
        }

        private final void onClickButtonNegative$com$android$server$desktopmode$UiManager$4() {
        }

        private final void onClickButtonNegative$com$android$server$desktopmode$UiManager$5() {
        }

        private final void onClickButtonPositive$com$android$server$desktopmode$UiManager$4() {
        }

        private final void onClickButtonPositive$com$android$server$desktopmode$UiManager$5() {
        }

        private final void onDismiss$com$android$server$desktopmode$UiManager$5() {
        }

        private final void onShow$com$android$server$desktopmode$UiManager$4() {
        }

        private final void onShow$com$android$server$desktopmode$UiManager$5() {
        }

        public final void onAnimationComplete() {
            switch (this.$r8$classId) {
                case 0:
                    ((InternalUiCallback) this.val$callback).onAnimationComplete();
                    break;
            }
        }

        public final void onClickButtonNegative() {
            switch (this.$r8$classId) {
                case 1:
                    InternalUiCallback internalUiCallback = (InternalUiCallback) this.val$callback;
                    if (internalUiCallback != null) {
                        internalUiCallback.getClass();
                        break;
                    }
                    break;
            }
        }

        public final void onClickButtonPositive() {
            switch (this.$r8$classId) {
                case 1:
                    InternalUiCallback internalUiCallback = (InternalUiCallback) this.val$callback;
                    if (internalUiCallback != null) {
                        internalUiCallback.onClickButtonPositive();
                        break;
                    }
                    break;
            }
        }

        public final void onDismiss() {
            switch (this.$r8$classId) {
                case 0:
                    break;
                case 1:
                    InternalUiCallback internalUiCallback = (InternalUiCallback) this.val$callback;
                    if (internalUiCallback != null) {
                        internalUiCallback.onDismiss();
                        break;
                    }
                    break;
                default:
                    ((UiManager) this.val$callback).postUnbindServiceRunnable();
                    break;
            }
        }

        public final void onShow() {
            switch (this.$r8$classId) {
                case 1:
                    InternalUiCallback internalUiCallback = (InternalUiCallback) this.val$callback;
                    if (internalUiCallback != null) {
                        internalUiCallback.onShow();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DockTaWarningDialogMessage implements Runnable {
        public Runnable mNotFastChargerDialogCommand;

        @Override // java.lang.Runnable
        public final void run() {
            Runnable runnable = this.mNotFastChargerDialogCommand;
            if (runnable != null) {
                runnable.run();
            }
            this.mNotFastChargerDialogCommand = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class InternalUiCallback {
        public void onAnimationComplete() {
        }

        public void onClickButtonPositive() {
        }

        public void onDismiss() {
        }

        public void onShow() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UiCommandHandler extends Handler {
        public final DockTaWarningDialogMessage mDockTaWarningDialogMessage;

        public UiCommandHandler(Looper looper) {
            super(looper);
            DockTaWarningDialogMessage dockTaWarningDialogMessage = new DockTaWarningDialogMessage();
            dockTaWarningDialogMessage.mNotFastChargerDialogCommand = null;
            this.mDockTaWarningDialogMessage = dockTaWarningDialogMessage;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (DesktopModeFeature.DEBUG) {
                StringBuilder sb = new StringBuilder("handleMessage(), msg=");
                int i = message.what;
                sb.append(i != 1 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=") : "MSG_DOCK_TA_WARNING_DIALOG");
                Log.d("[DMS]UiManager", sb.toString());
            }
            ((Runnable) message.obj).run();
        }

        public final void schedule(Runnable runnable, int i) {
            int i2 = i == 3 ? 1 : -1;
            if (i2 == -1) {
                post(runnable);
                return;
            }
            if (DesktopModeFeature.DEBUG) {
                StringBuilder sb = new StringBuilder("schedule(), type=");
                sb.append(DesktopModeUiConstants.typeToString(i));
                sb.append(", ");
                sb.append(i2 != 1 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Unknown=") : "MSG_DOCK_TA_WARNING_DIALOG");
                Log.d("[DMS]UiManager", sb.toString());
            }
            if (i2 == 1) {
                DockTaWarningDialogMessage dockTaWarningDialogMessage = this.mDockTaWarningDialogMessage;
                if (i == 3) {
                    dockTaWarningDialogMessage.mNotFastChargerDialogCommand = runnable;
                } else {
                    dockTaWarningDialogMessage.getClass();
                }
                runnable = dockTaWarningDialogMessage;
            }
            removeMessages(i2);
            sendMessageDelayed(obtainMessage(i2, i, 0, runnable), 1000L);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda9] */
    public UiManager(Context context, ServiceThread serviceThread, IStateManager iStateManager) {
        final int i = 0;
        this.mBindServiceRunnable = new Runnable(this) { // from class: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6
            public final /* synthetic */ UiManager f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Code restructure failed: missing block: B:19:0x002c, code lost:
            
                if (r4.mService.hasUiElement() != false) goto L15;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r4 = this;
                    int r0 = r2
                    com.android.server.desktopmode.UiManager r4 = r4.f$0
                    switch(r0) {
                        case 0: goto L5b;
                        case 1: goto L15;
                        default: goto L7;
                    }
                L7:
                    r4.getClass()
                    java.lang.String r0 = "[DMS]UiManager"
                    java.lang.String r1 = "Binder supposed established connection but actual connection to service timed out, trying again"
                    com.android.server.desktopmode.Log.w(r0, r1)
                    r4.retryConnectionWithBackoff()
                    return
                L15:
                    com.samsung.android.desktopmode.IDesktopModeUiService r0 = r4.mService
                    com.android.server.desktopmode.PendingUiCommands r1 = r4.mPendingUiCommands
                    r2 = 0
                    if (r0 == 0) goto L36
                    java.util.List r0 = r1.mUiCommands     // Catch: android.os.RemoteException -> L2f
                    java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: android.os.RemoteException -> L2f
                    boolean r0 = r0.isEmpty()     // Catch: android.os.RemoteException -> L2f
                    if (r0 == 0) goto L31
                    com.samsung.android.desktopmode.IDesktopModeUiService r0 = r4.mService     // Catch: android.os.RemoteException -> L2f
                    boolean r0 = r0.hasUiElement()     // Catch: android.os.RemoteException -> L2f
                    if (r0 == 0) goto L36
                    goto L31
                L2f:
                    r0 = move-exception
                    goto L33
                L31:
                    r2 = 1
                    goto L36
                L33:
                    r4.handleRemoteException(r0)
                L36:
                    boolean r0 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG
                    if (r0 == 0) goto L4e
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r3 = "unbindServiceIfHasNoUiElement(), hasElement="
                    r0.<init>(r3)
                    r0.append(r2)
                    java.lang.String r0 = r0.toString()
                    java.lang.String r3 = "[DMS]UiManager"
                    com.android.server.desktopmode.Log.d(r3, r0)
                L4e:
                    if (r2 != 0) goto L5a
                    java.util.List r0 = r1.mUiCommands
                    java.util.ArrayList r0 = (java.util.ArrayList) r0
                    r0.clear()
                    r4.unbindService()
                L5a:
                    return
                L5b:
                    r4.bindService()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6.run():void");
            }
        };
        final int i2 = 1;
        this.mUnbindServiceRunnable = new Runnable(this) { // from class: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6
            public final /* synthetic */ UiManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                /*
                    this = this;
                    int r0 = r2
                    com.android.server.desktopmode.UiManager r4 = r4.f$0
                    switch(r0) {
                        case 0: goto L5b;
                        case 1: goto L15;
                        default: goto L7;
                    }
                L7:
                    r4.getClass()
                    java.lang.String r0 = "[DMS]UiManager"
                    java.lang.String r1 = "Binder supposed established connection but actual connection to service timed out, trying again"
                    com.android.server.desktopmode.Log.w(r0, r1)
                    r4.retryConnectionWithBackoff()
                    return
                L15:
                    com.samsung.android.desktopmode.IDesktopModeUiService r0 = r4.mService
                    com.android.server.desktopmode.PendingUiCommands r1 = r4.mPendingUiCommands
                    r2 = 0
                    if (r0 == 0) goto L36
                    java.util.List r0 = r1.mUiCommands     // Catch: android.os.RemoteException -> L2f
                    java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: android.os.RemoteException -> L2f
                    boolean r0 = r0.isEmpty()     // Catch: android.os.RemoteException -> L2f
                    if (r0 == 0) goto L31
                    com.samsung.android.desktopmode.IDesktopModeUiService r0 = r4.mService     // Catch: android.os.RemoteException -> L2f
                    boolean r0 = r0.hasUiElement()     // Catch: android.os.RemoteException -> L2f
                    if (r0 == 0) goto L36
                    goto L31
                L2f:
                    r0 = move-exception
                    goto L33
                L31:
                    r2 = 1
                    goto L36
                L33:
                    r4.handleRemoteException(r0)
                L36:
                    boolean r0 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG
                    if (r0 == 0) goto L4e
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r3 = "unbindServiceIfHasNoUiElement(), hasElement="
                    r0.<init>(r3)
                    r0.append(r2)
                    java.lang.String r0 = r0.toString()
                    java.lang.String r3 = "[DMS]UiManager"
                    com.android.server.desktopmode.Log.d(r3, r0)
                L4e:
                    if (r2 != 0) goto L5a
                    java.util.List r0 = r1.mUiCommands
                    java.util.ArrayList r0 = (java.util.ArrayList) r0
                    r0.clear()
                    r4.unbindService()
                L5a:
                    return
                L5b:
                    r4.bindService()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6.run():void");
            }
        };
        final int i3 = 2;
        this.mDeferredConnectionCallback = new Runnable(this) { // from class: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6
            public final /* synthetic */ UiManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                /*
                    this = this;
                    int r0 = r2
                    com.android.server.desktopmode.UiManager r4 = r4.f$0
                    switch(r0) {
                        case 0: goto L5b;
                        case 1: goto L15;
                        default: goto L7;
                    }
                L7:
                    r4.getClass()
                    java.lang.String r0 = "[DMS]UiManager"
                    java.lang.String r1 = "Binder supposed established connection but actual connection to service timed out, trying again"
                    com.android.server.desktopmode.Log.w(r0, r1)
                    r4.retryConnectionWithBackoff()
                    return
                L15:
                    com.samsung.android.desktopmode.IDesktopModeUiService r0 = r4.mService
                    com.android.server.desktopmode.PendingUiCommands r1 = r4.mPendingUiCommands
                    r2 = 0
                    if (r0 == 0) goto L36
                    java.util.List r0 = r1.mUiCommands     // Catch: android.os.RemoteException -> L2f
                    java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: android.os.RemoteException -> L2f
                    boolean r0 = r0.isEmpty()     // Catch: android.os.RemoteException -> L2f
                    if (r0 == 0) goto L31
                    com.samsung.android.desktopmode.IDesktopModeUiService r0 = r4.mService     // Catch: android.os.RemoteException -> L2f
                    boolean r0 = r0.hasUiElement()     // Catch: android.os.RemoteException -> L2f
                    if (r0 == 0) goto L36
                    goto L31
                L2f:
                    r0 = move-exception
                    goto L33
                L31:
                    r2 = 1
                    goto L36
                L33:
                    r4.handleRemoteException(r0)
                L36:
                    boolean r0 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG
                    if (r0 == 0) goto L4e
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r3 = "unbindServiceIfHasNoUiElement(), hasElement="
                    r0.<init>(r3)
                    r0.append(r2)
                    java.lang.String r0 = r0.toString()
                    java.lang.String r3 = "[DMS]UiManager"
                    com.android.server.desktopmode.Log.d(r3, r0)
                L4e:
                    if (r2 != 0) goto L5a
                    java.util.List r0 = r1.mUiCommands
                    java.util.ArrayList r0 = (java.util.ArrayList) r0
                    r0.clear()
                    r4.unbindService()
                L5a:
                    return
                L5b:
                    r4.bindService()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda6.run():void");
            }
        };
        StateManager.StateListener stateListener = new StateManager.StateListener() { // from class: com.android.server.desktopmode.UiManager.2
            @Override // com.android.server.desktopmode.StateManager.StateListener
            public final void onUserChanged(StateManager.InternalState internalState) {
                int i4 = internalState.mCurrentUserId;
                UiManager uiManager = UiManager.this;
                uiManager.getClass();
                Utils.runOnHandlerThread(uiManager.mHandler, new UiManager$$ExternalSyntheticLambda0(uiManager, i4, 3));
            }
        };
        this.mConnectionBackoffAttempts = 0;
        this.mChangingStandaloneMode = false;
        this.mCurrentUserId = -10000;
        this.mContext = context;
        PendingUiCommands pendingUiCommands = new PendingUiCommands();
        pendingUiCommands.mUiCommands = new ArrayList();
        this.mPendingUiCommands = pendingUiCommands;
        this.mHandler = new UiCommandHandler(serviceThread.getLooper());
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(stateListener);
        this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.desktopmode.UiManager$$ExternalSyntheticLambda9
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                UiManager uiManager = UiManager.this;
                uiManager.getClass();
                Log.w("[DMS]UiManager", "Binder died, reconnecting");
                Utils.runOnHandlerThread(uiManager.mHandler, uiManager.mBindServiceRunnable);
            }
        };
    }

    public final void bindService() {
        UiCommandHandler uiCommandHandler = this.mHandler;
        UiManager$$ExternalSyntheticLambda6 uiManager$$ExternalSyntheticLambda6 = this.mDeferredConnectionCallback;
        if (uiCommandHandler.hasCallbacks(uiManager$$ExternalSyntheticLambda6)) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "bindService(), mService=" + this.mService);
        }
        unbindService();
        uiCommandHandler.removeCallbacks(this.mBindServiceRunnable);
        removeUnbindServiceRunnable();
        try {
            boolean bindServiceAsUser = this.mContext.bindServiceAsUser(new Intent().setClassName("com.sec.android.desktopmode.uiservice", "com.sec.android.desktopmode.uiservice.DesktopModeUiService"), this.mServiceConnection, 1, UserHandle.of(this.mCurrentUserId));
            this.mBound = bindServiceAsUser;
            if (bindServiceAsUser) {
                uiCommandHandler.postDelayed(uiManager$$ExternalSyntheticLambda6, 5000L);
            } else {
                retryConnectionWithBackoff();
            }
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]UiManager", "Failed to bind service", e);
        }
    }

    public final boolean bindUiServiceWithPendingCommand(int i, int i2, int i3, Runnable runnable) {
        if (this.mService != null) {
            return true;
        }
        this.mPendingUiCommands.queue(i, i2, i3, runnable);
        bindService();
        return false;
    }

    public final void dismissDialog(int i, int i2) {
        if (DesktopModeFeature.DEBUG) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "dismissDialog(), displayId=", ", type=");
            m.append(DesktopModeUiConstants.typeToString(i2));
            Log.d("[DMS]UiManager", m.toString());
        }
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda2(this, i, i2, 1), i2);
    }

    public final void dismissOverlay(int i, int i2) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "dismissOverlay(), where=" + DesktopModeUiConstants.whereToString(i) + ", type=" + DesktopModeUiConstants.typeToString(i2));
        }
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda2(this, i, i2, 0), i2);
    }

    public final void finishActivity(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "finishActivity(), type=" + DesktopModeUiConstants.typeToString(i));
        }
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda0(this, i, 2), i);
    }

    public final int getCurrentOverlayType(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "getCurrentOverlayType(), where=" + DesktopModeUiConstants.whereToString(i));
        }
        IDesktopModeUiService iDesktopModeUiService = this.mService;
        if (iDesktopModeUiService == null) {
            return -1;
        }
        try {
            return iDesktopModeUiService.getCurrentOverlayType(i);
        } catch (RemoteException e) {
            this.handleRemoteException(e);
            return -1;
        }
    }

    public final void handleNavBarIcon() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleNavBarIcon(), type=" + DesktopModeUiConstants.typeToString(400));
        }
        if (bindUiServiceWithPendingCommand(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, 400, -1, new UiManager$$ExternalSyntheticLambda0(this, 13))) {
            try {
                this.mService.showNavBarIcon(400);
            } catch (RemoteException e) {
                handleRemoteException(e, true, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, 400, -1, new UiManager$$ExternalSyntheticLambda0(this, 14));
            }
        }
    }

    public final void handleRemoteException(RemoteException remoteException) {
        handleRemoteException(remoteException, false, -1, -1, -1, null);
    }

    public final void handleRemoteException(RemoteException remoteException, boolean z, int i, int i2, int i3, Runnable runnable) {
        Log.e("[DMS]UiManager", "handleRemoteException(), preserve=" + z, remoteException);
        if (!z || runnable == null) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleRemoteException(), adding pending commands, type=" + DesktopModeUiConstants.typeToString(i2) + ", where=" + DesktopModeUiConstants.whereToString(i3));
        }
        this.mPendingUiCommands.queue(i, i2, i3, runnable);
    }

    public final void handleRemoveNavBarIcon() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleRemoveNavBarIcon(), type=" + DesktopModeUiConstants.typeToString(400));
        }
        if (bindUiServiceWithPendingCommand(901, 400, -1, new UiManager$$ExternalSyntheticLambda0(this, 11))) {
            try {
                this.mService.removeNavBarIcon(400);
                postUnbindServiceRunnable();
            } catch (RemoteException e) {
                handleRemoteException(e, true, 901, 400, -1, new UiManager$$ExternalSyntheticLambda0(this, 12));
            }
        }
    }

    public final void handleRemoveNotification(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleRemoveNotification(), type=" + DesktopModeUiConstants.typeToString(i));
        }
        if (bindUiServiceWithPendingCommand(901, i, -1, new UiManager$$ExternalSyntheticLambda0(this, i, 7))) {
            try {
                this.mService.removeNotification(i);
                postUnbindServiceRunnable();
            } catch (RemoteException e) {
                handleRemoteException(e, true, 901, i, -1, new UiManager$$ExternalSyntheticLambda0(this, i, 8));
            }
        }
    }

    public final void handleShowDialog(int i, int i2, final InternalUiCallback internalUiCallback) {
        if (DesktopModeFeature.DEBUG) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "handleShowDialog(), displayId=", ", dialogType=");
            m.append(DesktopModeUiConstants.typeToString(i2));
            Log.d("[DMS]UiManager", m.toString());
        }
        UiManager$$ExternalSyntheticLambda1 uiManager$$ExternalSyntheticLambda1 = new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 3);
        if (bindUiServiceWithPendingCommand(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, i, uiManager$$ExternalSyntheticLambda1)) {
            if (this.mChangingStandaloneMode) {
                this.mPendingUiCommands.queue(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, i, uiManager$$ExternalSyntheticLambda1);
                StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m(i, "showDialog() mChangingStandaloneMode!! displayId=", ", dialogType=");
                m2.append(DesktopModeUiConstants.typeToString(i2));
                Log.w("[DMS]UiManager", m2.toString());
                return;
            }
            IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback = null;
            switch (i2) {
                case 1:
                case 2:
                case 7:
                case 9:
                    break;
                case 3:
                    iDesktopModeUiServiceCallback = new AnonymousClass5(2, this);
                    break;
                case 4:
                case 5:
                case 8:
                case 10:
                    iDesktopModeUiServiceCallback = new IDesktopModeUiServiceCallback.Stub() { // from class: com.android.server.desktopmode.UiManager.3
                        public final void onAnimationComplete() {
                        }

                        public final void onClickButtonNegative() {
                            InternalUiCallback internalUiCallback2 = internalUiCallback;
                            if (internalUiCallback2 != null) {
                                UiCommandHandler uiCommandHandler = UiManager.this.mHandler;
                                Objects.requireNonNull(internalUiCallback2);
                                uiCommandHandler.post(new UiManager$3$$ExternalSyntheticLambda0(internalUiCallback2, 1));
                            }
                        }

                        public final void onClickButtonPositive() {
                            InternalUiCallback internalUiCallback2 = internalUiCallback;
                            if (internalUiCallback2 != null) {
                                UiCommandHandler uiCommandHandler = UiManager.this.mHandler;
                                Objects.requireNonNull(internalUiCallback2);
                                uiCommandHandler.post(new UiManager$3$$ExternalSyntheticLambda0(internalUiCallback2, 0));
                            }
                        }

                        public final void onDismiss() {
                            InternalUiCallback internalUiCallback2 = internalUiCallback;
                            if (internalUiCallback2 != null) {
                                UiCommandHandler uiCommandHandler = UiManager.this.mHandler;
                                Objects.requireNonNull(internalUiCallback2);
                                uiCommandHandler.post(new UiManager$3$$ExternalSyntheticLambda0(internalUiCallback2, 3));
                            }
                            UiManager.this.postUnbindServiceRunnable();
                        }

                        public final void onShow() {
                            InternalUiCallback internalUiCallback2 = internalUiCallback;
                            if (internalUiCallback2 != null) {
                                UiCommandHandler uiCommandHandler = UiManager.this.mHandler;
                                Objects.requireNonNull(internalUiCallback2);
                                uiCommandHandler.post(new UiManager$3$$ExternalSyntheticLambda0(internalUiCallback2, 2));
                            }
                        }
                    };
                    break;
                case 6:
                default:
                    Log.e("[DMS]UiManager", "showDialog() wrong type value!! dialogType=" + DesktopModeUiConstants.typeToString(i2));
                    break;
            }
            IDesktopModeUiService iDesktopModeUiService = this.mService;
            if (iDesktopModeUiService != null) {
                try {
                    iDesktopModeUiService.showDialog(i, i2, iDesktopModeUiServiceCallback);
                } catch (RemoteException e) {
                    handleRemoteException(e, true, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, i, new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 4));
                }
            }
        }
    }

    public final void handleShowNotification(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleShowNotification(), type=" + DesktopModeUiConstants.typeToString(i));
        }
        if (bindUiServiceWithPendingCommand(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i, -1, new UiManager$$ExternalSyntheticLambda0(this, i, 4))) {
            if (!this.mChangingStandaloneMode) {
                try {
                    this.mService.showNotification(i);
                    return;
                } catch (RemoteException e) {
                    handleRemoteException(e, true, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i, -1, new UiManager$$ExternalSyntheticLambda0(this, i, 6));
                    return;
                }
            }
            this.mPendingUiCommands.queue(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i, -1, new UiManager$$ExternalSyntheticLambda0(this, i, 5));
            Log.w("[DMS]UiManager", "handleShowNotification(), mChangingStandaloneMode!!type=" + DesktopModeUiConstants.typeToString(i));
        }
    }

    public final void handleShowOverlay(int i, int i2, InternalUiCallback internalUiCallback) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleShowOverlay(), where=" + DesktopModeUiConstants.whereToString(i) + ", type=" + DesktopModeUiConstants.typeToString(i2));
        }
        UiManager$$ExternalSyntheticLambda1 uiManager$$ExternalSyntheticLambda1 = new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 2);
        if (bindUiServiceWithPendingCommand(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, i, uiManager$$ExternalSyntheticLambda1)) {
            try {
                this.mService.showOverlay(i, i2, internalUiCallback != null ? new AnonymousClass5(0, internalUiCallback) : null);
            } catch (RemoteException e) {
                handleRemoteException(e, true, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, -1, uiManager$$ExternalSyntheticLambda1);
            }
        }
    }

    public final void handleStartActivity(int i, int i2, InternalUiCallback internalUiCallback) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "handleStartActivity(), displayId=" + i + ", type=" + i2);
        }
        UiManager$$ExternalSyntheticLambda1 uiManager$$ExternalSyntheticLambda1 = new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 1);
        if (bindUiServiceWithPendingCommand(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, -1, uiManager$$ExternalSyntheticLambda1)) {
            try {
                this.mService.startActivity(i, i2, new AnonymousClass5(1, internalUiCallback));
            } catch (RemoteException e) {
                handleRemoteException(e, true, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, i2, -1, uiManager$$ExternalSyntheticLambda1);
            }
        }
    }

    public final boolean hasOverlay(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "hasOverlay(), where=" + DesktopModeUiConstants.whereToString(101) + ", type=" + DesktopModeUiConstants.typeToString(i));
        }
        IDesktopModeUiService iDesktopModeUiService = this.mService;
        if (iDesktopModeUiService == null) {
            return false;
        }
        try {
            return iDesktopModeUiService.hasOverlay(101, i);
        } catch (RemoteException e) {
            this.handleRemoteException(e);
            return false;
        }
    }

    public final void postUnbindServiceRunnable() {
        int i;
        if ((!this.mBound && this.mService == null) || (i = ((StateManager) this.mStateManager).getState().mDesktopModeState.enabled) == 3 || i == 4 || i == 1) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "postUnbindServiceRunnable()");
        }
        removeUnbindServiceRunnable();
        this.mHandler.postDelayed(this.mUnbindServiceRunnable, 3000L);
    }

    public final void removeNotification(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "removeNotification(), type=" + DesktopModeUiConstants.typeToString(i));
        }
        removeUnbindServiceRunnable();
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda0(this, i, 0), i);
    }

    public final void removeUnbindServiceRunnable() {
        this.mHandler.removeCallbacks(this.mUnbindServiceRunnable);
    }

    public final void retryConnectionWithBackoff() {
        UiCommandHandler uiCommandHandler = this.mHandler;
        UiManager$$ExternalSyntheticLambda6 uiManager$$ExternalSyntheticLambda6 = this.mBindServiceRunnable;
        if (uiCommandHandler.hasCallbacks(uiManager$$ExternalSyntheticLambda6)) {
            return;
        }
        long min = (long) Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
        uiCommandHandler.postDelayed(uiManager$$ExternalSyntheticLambda6, min);
        this.mConnectionBackoffAttempts++;
        Log.w("[DMS]UiManager", "Failed to bind service on attempt " + this.mConnectionBackoffAttempts + " will try again in " + min + "ms");
    }

    public final void showDialog(int i, int i2, InternalUiCallback internalUiCallback) {
        if (DesktopModeFeature.DEBUG) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "showDialog(), displayId=", ", dialogType=");
            m.append(DesktopModeUiConstants.typeToString(i2));
            Log.d("[DMS]UiManager", m.toString());
        }
        removeUnbindServiceRunnable();
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 5), i2);
    }

    public final void showNotification(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "showNotification(), type=" + DesktopModeUiConstants.typeToString(i));
        }
        removeUnbindServiceRunnable();
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda0(this, i, 1), i);
    }

    public final void showOverlay(int i, int i2, InternalUiCallback internalUiCallback) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "showOverlay(), where=" + DesktopModeUiConstants.whereToString(i) + ", type=" + DesktopModeUiConstants.typeToString(i2));
        }
        removeUnbindServiceRunnable();
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 0), i2);
    }

    public final void startActivity(int i, int i2, InternalUiCallback internalUiCallback) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]UiManager", "startActivity(), displayId=" + i + ", type=" + i2);
        }
        removeUnbindServiceRunnable();
        this.mHandler.schedule(new UiManager$$ExternalSyntheticLambda1(this, i, i2, internalUiCallback, 6), i2);
    }

    public final void unbindService() {
        if (this.mBound || this.mService != null) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]UiManager", "unbindService(), mBound=" + this.mBound + ", mService=" + this.mService);
            }
            if (this.mBound) {
                this.mContext.unbindService(this.mServiceConnection);
                this.mBound = false;
            }
            IDesktopModeUiService iDesktopModeUiService = this.mService;
            if (iDesktopModeUiService != null) {
                try {
                    iDesktopModeUiService.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                } catch (NoSuchElementException e) {
                    Log.e("[DMS]UiManager", "Failed to unlink death recipient", e);
                }
                this.mService = null;
            }
        }
    }
}
