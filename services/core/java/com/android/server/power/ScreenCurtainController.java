package com.android.server.power;

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
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.view.Display;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.power.HqmDataDispatcher;
import com.att.iqi.libs.PreferenceStore;
import com.samsung.android.hardware.secinputdev.ISemInputDeviceManager;
import com.samsung.android.view.SemWindowManager;
import java.util.Observable;
import java.util.Observer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ScreenCurtainController implements Observer {
    public final CallStateCallback mCallStateCallback;
    public final Context mContext;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public final IBinder mDslToken;
    public boolean mFolded;
    public boolean mFoldedWhenEnabled;
    public final DisplayAssistantHandler mHandler;
    public final HqmDataDispatcher mHqmDataDispatcher;
    public final ISemInputDeviceManager mInputDeviceManager;
    public int mLastScreenCurtainDisabledReason;
    public long mLastScreenCurtainDisabledTime;
    public long mLastUserActivityTime;
    public final Object mLock;
    public final NotificationListener mNotificationListener;
    public boolean mPenInsertStateInitialized;
    public boolean mScreenCurtainEnabled;
    public final Intent mServiceIntent;
    public final TelephonyManager mTelephonyManager;
    public IBinder mToken;
    public String mPackageNameOnScreenCurtain = "";
    public int mWakefulness = 1;
    public int mLastCallState = 0;
    public final AnonymousClass1 mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.server.power.ScreenCurtainController.1
        public final void onFoldStateChanged(boolean z) {
            synchronized (ScreenCurtainController.this.mLock) {
                try {
                    ScreenCurtainController screenCurtainController = ScreenCurtainController.this;
                    if (screenCurtainController.mFolded != z) {
                        screenCurtainController.mFolded = z;
                        if (screenCurtainController.mScreenCurtainEnabled) {
                            DisplayAssistantHandler displayAssistantHandler = screenCurtainController.mHandler;
                            displayAssistantHandler.sendMessageAtTime(displayAssistantHandler.obtainMessage(3, 8), SystemClock.uptimeMillis());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onTableModeChanged(boolean z) {
        }
    };
    public final AnonymousClass2 mReceiver = new BroadcastReceiver() { // from class: com.android.server.power.ScreenCurtainController.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i;
            String action = intent.getAction();
            action.getClass();
            i = 0;
            switch (action) {
                case "android.intent.action.SCREEN_OFF":
                    i = 4;
                    break;
                case "android.samsung.media.action.AUDIO_MODE":
                    if (intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0) >= 1) {
                        i = 3;
                        break;
                    }
                    break;
                case "com.samsung.android.bixby.intent.action.CLIENT_VIEW_STATE_UPDATED":
                    i = 5;
                    break;
                case "com.samsung.pen.INSERT":
                    ScreenCurtainController screenCurtainController = ScreenCurtainController.this;
                    if (!screenCurtainController.mPenInsertStateInitialized) {
                        screenCurtainController.mPenInsertStateInitialized = true;
                        break;
                    } else if (!intent.getBooleanExtra("penInsert", true)) {
                        i = 6;
                        break;
                    }
                    break;
            }
            if (i != 0) {
                DisplayAssistantHandler displayAssistantHandler = ScreenCurtainController.this.mHandler;
                displayAssistantHandler.sendMessageAtTime(displayAssistantHandler.obtainMessage(3, Integer.valueOf(i)), SystemClock.uptimeMillis());
            }
        }
    };
    public final AnonymousClass3 mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.power.ScreenCurtainController.3
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.d("ScreenCurtainController", "DeathRecipient: binderDied()");
            DisplayAssistantHandler displayAssistantHandler = ScreenCurtainController.this.mHandler;
            displayAssistantHandler.sendMessageAtTime(displayAssistantHandler.obtainMessage(3, 7), SystemClock.uptimeMillis());
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallStateCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        public CallStateCallback() {
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            synchronized (ScreenCurtainController.this.mLock) {
                try {
                    if (i == 1) {
                        DisplayAssistantHandler displayAssistantHandler = ScreenCurtainController.this.mHandler;
                        displayAssistantHandler.sendMessageAtTime(displayAssistantHandler.obtainMessage(3, 1), SystemClock.uptimeMillis());
                    } else if (i == 0) {
                        ScreenCurtainController screenCurtainController = ScreenCurtainController.this;
                        if (screenCurtainController.mLastScreenCurtainDisabledReason == 1 && screenCurtainController.mLastCallState == 1) {
                            Slog.d("ScreenCurtainController", "screen curtain auto enable scheduled");
                            screenCurtainController.mHandler.removeMessages(2);
                            DisplayAssistantHandler displayAssistantHandler2 = screenCurtainController.mHandler;
                            displayAssistantHandler2.sendMessageDelayed(displayAssistantHandler2.obtainMessage(2), 5000L);
                        }
                    }
                    ScreenCurtainController.this.mLastCallState = i;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayAssistantHandler extends Handler {
        public DisplayAssistantHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            int i = message.what;
            if (i == 1) {
                ScreenCurtainController screenCurtainController = ScreenCurtainController.this;
                if (!((Boolean) message.obj).booleanValue()) {
                    screenCurtainController.mContext.unregisterReceiver(screenCurtainController.mReceiver);
                    try {
                        screenCurtainController.mNotificationListener.unregisterAsSystemService();
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                screenCurtainController.getClass();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.samsung.android.bixby.intent.action.CLIENT_VIEW_STATE_UPDATED");
                intentFilter.addAction("com.samsung.pen.INSERT");
                intentFilter.addAction("android.samsung.media.action.AUDIO_MODE");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                screenCurtainController.mContext.registerReceiver(screenCurtainController.mReceiver, intentFilter, null, screenCurtainController.mHandler);
                try {
                    screenCurtainController.mNotificationListener.registerAsSystemService(screenCurtainController.mContext, new ComponentName(screenCurtainController.mContext.getPackageName(), ScreenCurtainController.class.getCanonicalName()), -1);
                } catch (RemoteException unused2) {
                }
                HqmDataDispatcher.DisplayStat displayStat = screenCurtainController.mHqmDataDispatcher.getDisplayStat(0);
                if (!displayStat.mScreenCurtainEnabled) {
                    displayStat.mScreenCurtainCount++;
                    HqmDataDispatcher.Timer timer = displayStat.mScreenCurtainTimer;
                    timer.getClass();
                    timer.startTimeMillis = SystemClock.uptimeMillis();
                }
                displayStat.mScreenCurtainEnabled = true;
                return;
            }
            if (i == 2) {
                ScreenCurtainController screenCurtainController2 = ScreenCurtainController.this;
                screenCurtainController2.getClass();
                Slog.d("ScreenCurtainController", "handleAutoEnableScreenCurtain");
                synchronized (screenCurtainController2.mLock) {
                    try {
                        if (screenCurtainController2.mLastScreenCurtainDisabledTime >= screenCurtainController2.mLastUserActivityTime) {
                            screenCurtainController2.mServiceIntent.putExtra(PreferenceStore.PREF_SERVICE_STATE, "StartService");
                            screenCurtainController2.mContext.startService(screenCurtainController2.mServiceIntent);
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i != 3) {
                return;
            }
            ScreenCurtainController screenCurtainController3 = ScreenCurtainController.this;
            int intValue = ((Integer) message.obj).intValue();
            screenCurtainController3.getClass();
            StringBuilder sb = new StringBuilder("handleDisableScreenCurtain: ");
            switch (intValue) {
                case 1:
                    str = "call";
                    break;
                case 2:
                    str = "notification";
                    break;
                case 3:
                    str = "audio";
                    break;
                case 4:
                    str = "screen off";
                    break;
                case 5:
                    str = "bixby";
                    break;
                case 6:
                    str = "pen";
                    break;
                case 7:
                    str = "death";
                    break;
                case 8:
                    str = "fold state";
                    break;
                default:
                    str = Integer.toString(intValue);
                    break;
            }
            sb.append(str);
            Slog.d("ScreenCurtainController", sb.toString());
            synchronized (screenCurtainController3.mLock) {
                try {
                    if (screenCurtainController3.mScreenCurtainEnabled) {
                        if (intValue == 7) {
                            screenCurtainController3.setScreenCurtainEnabledLocked(0, 0, screenCurtainController3.mToken, false);
                        }
                        screenCurtainController3.mLastScreenCurtainDisabledReason = intValue;
                        screenCurtainController3.mServiceIntent.putExtra(PreferenceStore.PREF_SERVICE_STATE, "StopService");
                        screenCurtainController3.mContext.startService(screenCurtainController3.mServiceIntent);
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationListener extends NotificationListenerService {
        public NotificationListener() {
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            if (statusBarNotification == null || statusBarNotification.getNotification() == null) {
                return;
            }
            String str = statusBarNotification.getNotification().category;
            if ((str == null || !(str.equals("call") || str.equals("alarm"))) && statusBarNotification.getNotification().fullScreenIntent == null) {
                return;
            }
            DisplayAssistantHandler displayAssistantHandler = ScreenCurtainController.this.mHandler;
            displayAssistantHandler.sendMessageAtTime(displayAssistantHandler.obtainMessage(3, 2), SystemClock.uptimeMillis());
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.power.ScreenCurtainController$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.power.ScreenCurtainController$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.power.ScreenCurtainController$3] */
    public ScreenCurtainController(Context context, Object obj, Looper looper, ISemInputDeviceManager iSemInputDeviceManager) {
        this.mContext = context;
        this.mLock = obj;
        DisplayAssistantHandler displayAssistantHandler = new DisplayAssistantHandler(looper);
        this.mHandler = displayAssistantHandler;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mTelephonyManager = telephonyManager;
        this.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        this.mInputDeviceManager = iSemInputDeviceManager;
        if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
            displayAssistantHandler.post(new ScreenCurtainController$$ExternalSyntheticLambda0(this, 1));
        }
        this.mNotificationListener = new NotificationListener();
        CallStateCallback callStateCallback = new CallStateCallback();
        this.mCallStateCallback = callStateCallback;
        telephonyManager.registerTelephonyCallback(context.getMainExecutor(), callStateCallback);
        this.mHqmDataDispatcher = HqmDataDispatcher.HqmDataDispatcherHolder.INSTANCE;
        this.mDslToken = new Binder();
        this.mServiceIntent = new Intent();
    }

    public final void setScreenCurtainEnabledLocked(int i, int i2, IBinder iBinder, boolean z) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("enableScreenCurtain: enabled=", ", displayState=", z);
        m.append(Display.stateToString(i2));
        Slog.d("ScreenCurtainController", m.toString());
        IBinder iBinder2 = this.mToken;
        if (iBinder2 != null && iBinder2 != iBinder) {
            Slog.e("ScreenCurtainController", "enableScreenCurtain: Already in use by another client");
            return;
        }
        boolean z2 = this.mScreenCurtainEnabled && z;
        this.mScreenCurtainEnabled = z;
        String str = null;
        if (!z) {
            this.mLastScreenCurtainDisabledTime = SystemClock.elapsedRealtime();
            this.mPenInsertStateInitialized = false;
            this.mToken.unlinkToDeath(this.mDeathRecipient, 0);
            this.mToken = null;
        } else if (!z2) {
            this.mToken = iBinder;
            try {
                iBinder.linkToDeath(this.mDeathRecipient, 0);
            } catch (RemoteException unused) {
                Slog.e("ScreenCurtainController", "Failed to set linkToDeath");
            }
            this.mLastScreenCurtainDisabledReason = 0;
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            if (packagesForUid != null && packagesForUid.length > 0) {
                str = packagesForUid[0];
            }
            if ("com.samsung.android.displayassistant".equals(str)) {
                this.mServiceIntent.setClassName("com.samsung.android.displayassistant", "com.samsung.android.displayassistant.presentation.ui.screencurtain.ScreenCurtainService");
            } else {
                this.mServiceIntent.setClassName("com.samsung.android.statsd", "com.samsung.android.statsd.screencurtain.ScreenCurtainService");
            }
            if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
                this.mFoldedWhenEnabled = this.mFolded;
            }
        }
        if (PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD) {
            boolean z3 = PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY;
            if (!z3 || z || this.mWakefulness != 1 || this.mFoldedWhenEnabled == this.mFolded) {
                try {
                    this.mInputDeviceManager.setTspEnabled((z3 && this.mFolded) ? 2 : 1, z ? 21 : 22, false);
                } catch (RemoteException unused2) {
                }
            }
            this.mDisplayManagerInternal.setDisplayStateOverride(this.mDslToken, i2);
        }
        if (z2) {
            return;
        }
        DisplayAssistantHandler displayAssistantHandler = this.mHandler;
        displayAssistantHandler.sendMessage(displayAssistantHandler.obtainMessage(1, Boolean.valueOf(z)));
    }

    @Override // java.util.Observer
    public final void update(Observable observable, Object obj) {
        if (observable instanceof ForegroundPackageObserver) {
            this.mPackageNameOnScreenCurtain = String.valueOf(obj);
            Slog.d("ScreenCurtainController", "ForegroundPackageObserver update: " + this.mPackageNameOnScreenCurtain);
        }
    }
}
