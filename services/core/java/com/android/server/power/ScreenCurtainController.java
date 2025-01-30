package com.android.server.power;

import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.IProcessObserver;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManagerInternal;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.view.Display;
import com.android.server.LocalServices;
import com.att.iqi.libs.PreferenceStore;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.hardware.secinputdev.ISemInputDeviceManager;
import com.samsung.android.view.SemWindowManager;
import java.util.LinkedHashSet;

/* loaded from: classes3.dex */
public class ScreenCurtainController {
    public final ActivityManagerInternal mActivityManagerInternal;
    public final IActivityManager mActivityManagerNative;
    public final CallStateCallback mCallStateCallback;
    public final Context mContext;
    public final IBinder.DeathRecipient mDeathRecipient;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public int mDisplayState;
    public final IBinder mDslToken;
    public final SemWindowManager.FoldStateListener mFoldStateListener;
    public boolean mFolded;
    public boolean mFoldedWhenEnabled;
    public final Handler mHandler;
    public final HqmDataDispatcher mHqmDataDispatcher;
    public final ISemInputDeviceManager mInputDeviceManager;
    public int mLastScreenCurtainDisabledReason;
    public long mLastScreenCurtainDisabledTime;
    public long mLastUserActivityTime;
    public final Object mLock;
    public final NotificationListener mNotificationListener;
    public boolean mPenInsertStateInitialized;
    public final IProcessObserver mProcessObserver;
    public final BroadcastReceiver mReceiver;
    public boolean mScreenCurtainEnabled;
    public final Intent mServiceIntent;
    public final TelephonyManager mTelephonyManager;
    public IBinder mToken;
    public String mPackageNameOnScreenCurtain = "";
    public int mWakefulness = 1;
    public int mLastCallState = 0;
    public LinkedHashSet mForegroundPidSet = new LinkedHashSet();

    public ScreenCurtainController(Context context, Object obj, Looper looper) {
        IProcessObserver.Stub stub = new IProcessObserver.Stub() { // from class: com.android.server.power.ScreenCurtainController.1
            public void onForegroundServicesChanged(int i, int i2, int i3) {
            }

            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                synchronized (this) {
                    if (z) {
                        ScreenCurtainController.this.mForegroundPidSet.add(Integer.valueOf(i));
                    } else {
                        ScreenCurtainController.this.mForegroundPidSet.remove(Integer.valueOf(i));
                    }
                    if (ScreenCurtainController.this.mForegroundPidSet.size() > 0) {
                        String packageNameByPid = ScreenCurtainController.this.mActivityManagerInternal.getPackageNameByPid(((Integer) ScreenCurtainController.this.mForegroundPidSet.stream().findFirst().get()).intValue());
                        if (ScreenCurtainController.this.mPackageNameOnScreenCurtain != null && packageNameByPid != null && !ScreenCurtainController.this.mPackageNameOnScreenCurtain.equals(packageNameByPid)) {
                            ScreenCurtainController.this.mPackageNameOnScreenCurtain = packageNameByPid;
                            Slog.m72d("ScreenCurtainController", "onForegroundActivitiesChanged: " + ScreenCurtainController.this.mPackageNameOnScreenCurtain);
                        }
                    }
                }
            }

            public void onProcessDied(int i, int i2) {
                onForegroundActivitiesChanged(i, i2, false);
            }
        };
        this.mProcessObserver = stub;
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.server.power.ScreenCurtainController.2
            public void onTableModeChanged(boolean z) {
            }

            public void onFoldStateChanged(boolean z) {
                synchronized (ScreenCurtainController.this.mLock) {
                    if (ScreenCurtainController.this.mFolded != z) {
                        ScreenCurtainController.this.mFolded = z;
                        if (ScreenCurtainController.this.mScreenCurtainEnabled) {
                            ScreenCurtainController.this.mHandler.sendMessageAtTime(ScreenCurtainController.this.mHandler.obtainMessage(1, 8), SystemClock.uptimeMillis());
                        }
                    }
                }
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.power.ScreenCurtainController.3
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:29:0x0063, code lost:
            
                if (r7.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0) >= 1) goto L35;
             */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onReceive(Context context2, Intent intent) {
                int i;
                String action = intent.getAction();
                action.hashCode();
                i = 3;
                switch (action) {
                    case "android.intent.action.SCREEN_OFF":
                        i = 4;
                        break;
                    case "android.samsung.media.action.AUDIO_MODE":
                        break;
                    case "com.samsung.android.bixby.intent.action.CLIENT_VIEW_STATE_UPDATED":
                        i = 5;
                        break;
                    case "com.samsung.pen.INSERT":
                        if (!ScreenCurtainController.this.mPenInsertStateInitialized) {
                            ScreenCurtainController.this.mPenInsertStateInitialized = true;
                        } else if (!intent.getBooleanExtra("penInsert", true)) {
                            i = 6;
                            break;
                        }
                        i = 0;
                        break;
                    default:
                        i = 0;
                        break;
                }
                if (i != 0) {
                    ScreenCurtainController.this.mHandler.sendMessageAtTime(ScreenCurtainController.this.mHandler.obtainMessage(1, Integer.valueOf(i)), SystemClock.uptimeMillis());
                }
            }
        };
        this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.power.ScreenCurtainController.4
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                Slog.m72d("ScreenCurtainController", "DeathRecipient: binderDied()");
                ScreenCurtainController.this.mHandler.sendMessageAtTime(ScreenCurtainController.this.mHandler.obtainMessage(1, 7), SystemClock.uptimeMillis());
            }
        };
        this.mContext = context;
        this.mLock = obj;
        this.mHandler = new ScreenCurtainHandler(looper);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        this.mInputDeviceManager = ISemInputDeviceManager.Stub.asInterface(ServiceManager.getService("SemInputDeviceManagerService"));
        IActivityManager iActivityManager = ActivityManagerNative.getDefault();
        this.mActivityManagerNative = iActivityManager;
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        try {
            iActivityManager.registerProcessObserver(stub);
        } catch (Exception unused) {
        }
        if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenCurtainController.this.lambda$new$0();
                }
            });
        }
        this.mNotificationListener = new NotificationListener();
        CallStateCallback callStateCallback = new CallStateCallback();
        this.mCallStateCallback = callStateCallback;
        this.mTelephonyManager.registerTelephonyCallback(this.mContext.getMainExecutor(), callStateCallback);
        this.mHqmDataDispatcher = HqmDataDispatcher.getInstance();
        this.mDslToken = new Binder();
        Intent intent = new Intent();
        this.mServiceIntent = intent;
        intent.setClassName("com.samsung.android.statsd", "com.samsung.android.statsd.screencurtain.ScreenCurtainService");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    public void setScreenCurtainEnabledLocked(IBinder iBinder, final boolean z, final int i) {
        try {
            if (this.mScreenCurtainEnabled != z) {
                Slog.m72d("ScreenCurtainController", "enableScreenCurtain: enabled=" + z + ", displayState=" + Display.stateToString(i));
                boolean z2 = PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY;
                final boolean z3 = true;
                final int i2 = (z2 && this.mFolded) ? 2 : 1;
                if (z) {
                    this.mToken = iBinder;
                    iBinder.linkToDeath(this.mDeathRecipient, 0);
                    this.mScreenCurtainEnabled = true;
                    this.mLastScreenCurtainDisabledReason = -1;
                    this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScreenCurtainController.this.lambda$setScreenCurtainEnabledLocked$1();
                        }
                    });
                    if (z2) {
                        this.mFoldedWhenEnabled = this.mFolded;
                    }
                    if (PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD) {
                        this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenCurtainController.this.lambda$setScreenCurtainEnabledLocked$2(i2, i);
                            }
                        });
                    }
                } else {
                    this.mScreenCurtainEnabled = false;
                    if (this.mLastScreenCurtainDisabledReason == -1) {
                        this.mLastScreenCurtainDisabledReason = 0;
                    }
                    this.mLastScreenCurtainDisabledTime = SystemClock.elapsedRealtime();
                    this.mPenInsertStateInitialized = false;
                    this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScreenCurtainController.this.lambda$setScreenCurtainEnabledLocked$3();
                        }
                    });
                    if (PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD) {
                        if (this.mWakefulness != 1 || !z2 || this.mFoldedWhenEnabled == this.mFolded) {
                            z3 = false;
                        }
                        this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenCurtainController.this.lambda$setScreenCurtainEnabledLocked$4(z3, i2);
                            }
                        });
                        this.mToken.unlinkToDeath(this.mDeathRecipient, 0);
                        this.mToken = null;
                    }
                }
                this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenCurtainController.this.lambda$setScreenCurtainEnabledLocked$5(z);
                    }
                });
                return;
            }
            if (PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD && z) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.power.ScreenCurtainController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenCurtainController.this.lambda$setScreenCurtainEnabledLocked$6(i);
                    }
                });
                IBinder iBinder2 = this.mToken;
                if (iBinder2 != iBinder) {
                    iBinder2.unlinkToDeath(this.mDeathRecipient, 0);
                    this.mToken = iBinder;
                    iBinder.linkToDeath(this.mDeathRecipient, 0);
                }
            }
        } catch (RemoteException e) {
            Slog.m74e("ScreenCurtainController", "Failed to set screen curtain");
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenCurtainEnabledLocked$1() {
        try {
            this.mNotificationListener.registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()), -1);
        } catch (RemoteException unused) {
        }
        registerBroadCastReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenCurtainEnabledLocked$2(int i, int i2) {
        try {
            this.mInputDeviceManager.setTspEnabled(i, 21, false);
        } catch (RemoteException unused) {
        }
        this.mDisplayManagerInternal.setDisplayStateLimit(this.mDslToken, i2);
        this.mDisplayState = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenCurtainEnabledLocked$3() {
        try {
            this.mNotificationListener.unregisterAsSystemService();
        } catch (RemoteException unused) {
        }
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenCurtainEnabledLocked$4(boolean z, int i) {
        if (!z) {
            try {
                this.mInputDeviceManager.setTspEnabled(i, 22, false);
            } catch (RemoteException unused) {
            }
        }
        this.mDisplayManagerInternal.setDisplayStateLimit(this.mDslToken, 0);
        this.mDisplayState = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenCurtainEnabledLocked$5(boolean z) {
        this.mHqmDataDispatcher.noteScreenCurtainEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenCurtainEnabledLocked$6(int i) {
        if (this.mDisplayState != i) {
            this.mDisplayManagerInternal.setDisplayStateLimit(this.mDslToken, i);
            this.mDisplayState = i;
        }
    }

    public void onWakefulnessChangeStarted(int i) {
        this.mWakefulness = i;
    }

    public void onUserActivity() {
        this.mLastUserActivityTime = SystemClock.elapsedRealtime();
    }

    public boolean isScreenCurtainEnabledLocked() {
        return this.mScreenCurtainEnabled;
    }

    public String getPackageNameOnScreenCurtainLocked() {
        String str;
        synchronized (this) {
            Slog.m72d("ScreenCurtainController", "getPackageNameOnScreenCurtainLocked: " + this.mPackageNameOnScreenCurtain);
            str = this.mPackageNameOnScreenCurtain;
        }
        return str;
    }

    public boolean isScreenCurtainAvailableLocked() {
        boolean z;
        boolean z2;
        try {
            z = new SemGameManager().isForegroundGame();
        } catch (Exception unused) {
            z = false;
        }
        if (this.mTelephonyManager.getCallState() != 0) {
            z2 = true;
            boolean z3 = z && !z2;
            Slog.m72d("ScreenCurtainController", "isScreenCurtainAvailableLocked: " + z3 + ", game=" + z + ", callActive=" + z2);
            return z3;
        }
        z2 = false;
        if (z) {
        }
        Slog.m72d("ScreenCurtainController", "isScreenCurtainAvailableLocked: " + z3 + ", game=" + z + ", callActive=" + z2);
        return z3;
    }

    public final void handleDisableScreenCurtain(int i) {
        Slog.m72d("ScreenCurtainController", "handleDisableScreenCurtain: " + disableReasonToString(i));
        synchronized (this.mLock) {
            if (this.mScreenCurtainEnabled) {
                if (i == 7) {
                    setScreenCurtainEnabledLocked(this.mToken, false, 0);
                }
                this.mLastScreenCurtainDisabledReason = i;
                this.mServiceIntent.putExtra(PreferenceStore.PREF_SERVICE_STATE, "StopService");
                this.mContext.startService(this.mServiceIntent);
            }
        }
    }

    public final void scheduleAutoEnableScreenCurtain() {
        Slog.m72d("ScreenCurtainController", "screen curtain auto enable scheduled");
        this.mHandler.removeMessages(2);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2), 5000L);
    }

    public final void handleAutoEnableScreenCurtain() {
        Slog.m72d("ScreenCurtainController", "handleAutoEnableScreenCurtain");
        synchronized (this.mLock) {
            if (this.mLastScreenCurtainDisabledTime < this.mLastUserActivityTime) {
                return;
            }
            this.mServiceIntent.putExtra(PreferenceStore.PREF_SERVICE_STATE, "StartService");
            this.mContext.startService(this.mServiceIntent);
        }
    }

    public final class CallStateCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        public CallStateCallback() {
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public void onCallStateChanged(int i) {
            synchronized (ScreenCurtainController.this.mLock) {
                try {
                    if (i == 1) {
                        ScreenCurtainController.this.mHandler.sendMessageAtTime(ScreenCurtainController.this.mHandler.obtainMessage(1, 1), SystemClock.uptimeMillis());
                    } else if (i == 0 && ScreenCurtainController.this.mLastScreenCurtainDisabledReason == 1 && ScreenCurtainController.this.mLastCallState == 1) {
                        ScreenCurtainController.this.scheduleAutoEnableScreenCurtain();
                    }
                    ScreenCurtainController.this.mLastCallState = i;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class ScreenCurtainHandler extends Handler {
        public ScreenCurtainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ScreenCurtainController.this.handleDisableScreenCurtain(((Integer) message.obj).intValue());
            } else {
                if (i != 2) {
                    return;
                }
                ScreenCurtainController.this.handleAutoEnableScreenCurtain();
            }
        }
    }

    public final void registerBroadCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.bixby.intent.action.CLIENT_VIEW_STATE_UPDATED");
        intentFilter.addAction("com.samsung.pen.INSERT");
        intentFilter.addAction("android.samsung.media.action.AUDIO_MODE");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(this.mReceiver, intentFilter, null, this.mHandler);
    }

    public class NotificationListener extends NotificationListenerService {
        @Override // android.service.notification.NotificationListenerService
        public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        }

        public NotificationListener() {
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            if (statusBarNotification == null || statusBarNotification.getNotification() == null) {
                return;
            }
            String str = statusBarNotification.getNotification().category;
            if ((str == null || !(str.equals("call") || str.equals("alarm"))) && statusBarNotification.getNotification().fullScreenIntent == null) {
                return;
            }
            ScreenCurtainController.this.mHandler.sendMessageAtTime(ScreenCurtainController.this.mHandler.obtainMessage(1, 2), SystemClock.uptimeMillis());
        }
    }

    public final String disableReasonToString(int i) {
        switch (i) {
            case 1:
                return "call";
            case 2:
                return "notification";
            case 3:
                return "audio";
            case 4:
                return "screen off";
            case 5:
                return "bixby";
            case 6:
                return "pen";
            case 7:
                return "death";
            case 8:
                return "fold state";
            default:
                return Integer.toString(i);
        }
    }
}
