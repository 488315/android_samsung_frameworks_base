package com.android.server.sensorprivacy;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.hardware.ISensorPrivacyListener;
import android.hardware.ISensorPrivacyManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.SensorPrivacyManagerInternal;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.safetycenter.SafetyCenterManager;
import android.service.voice.VoiceInteractionManagerInternal;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.emergency.EmergencyNumber;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.widget.Toast;

import com.android.internal.camera.flags.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;
import com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.AnonymousClass4;

import com.samsung.android.feature.SemFloatingFeature;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SensorPrivacyService extends SystemService {
    public static final String ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY =
            SensorPrivacyService.class.getName().concat(".action.disable_sensor_privacy");
    public final ActivityManager mActivityManager;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final ActivityTaskManager mActivityTaskManager;
    public final AppOpsManager mAppOpsManager;
    public final AppOpsManagerInternal mAppOpsManagerInternal;
    public final IBinder mAppOpsRestrictionToken;
    public CallStateHelper mCallStateHelper;
    public List mCameraPrivacyAllowlist;
    public final Context mContext;
    public int mCurrentUser;
    public final boolean mIsFlipModel;
    public boolean mIsFolded;
    public final boolean mIsLargeCoverScreen;
    public KeyguardManager mKeyguardManager;
    public final NotificationManager mNotificationManager;
    public final PackageManagerInternal mPackageManagerInternal;
    public SensorPrivacyManagerInternalImpl mSensorPrivacyManagerInternal;
    public final SensorPrivacyServiceImpl mSensorPrivacyServiceImpl;
    public final TelephonyManager mTelephonyManager;
    public final UserManagerInternal mUserManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallStateHelper {
        public final CallStateCallback mCallStateCallback;
        public final Object mCallStateLock;
        public boolean mCameraBlockedForEmergencyCall;
        public final OutgoingEmergencyStateCallback mEmergencyStateCallback;
        public final Handler mHandler;
        public boolean mIsInEmergencyCall;
        public boolean mMicUnmutedForEmergencyCall;
        public TextToSpeech mTts;
        public final HashMap mTtsHashForTTSPath;
        public final HashMap mTtsHashForVoiceCallPath;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.sensorprivacy.SensorPrivacyService$CallStateHelper$2, reason: invalid class name */
        public final class AnonymousClass2 implements Runnable {
            public final /* synthetic */ int val$callState;

            public AnonymousClass2(int i) {
                this.val$callState = i;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Locale defaultLanguage = CallStateHelper.this.mTts.getDefaultLanguage();
                Locale locale = Locale.ENGLISH;
                Locale normalizeTTSLocale =
                        defaultLanguage != null
                                ? TtsEngines.normalizeTTSLocale(defaultLanguage)
                                : locale;
                int language = CallStateHelper.this.mTts.setLanguage(normalizeTTSLocale);
                String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                StringBuilder sb = new StringBuilder("readGuideString ttsLanguage=");
                sb.append(normalizeTTSLocale);
                sb.append(" result=");
                sb.append(language);
                sb.append(" callState= ");
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        sb, this.val$callState, "SensorPrivacyService");
                if (language == -2) {
                    CallStateHelper.this.mTts.setLanguage(locale);
                } else {
                    locale = normalizeTTSLocale;
                }
                CallStateHelper callStateHelper = CallStateHelper.this;
                TextToSpeech textToSpeech = callStateHelper.mTts;
                Context context = SensorPrivacyService.this.mContext;
                callStateHelper.getClass();
                Configuration configuration =
                        new Configuration(context.getResources().getConfiguration());
                configuration.setLocale(locale);
                String charSequence =
                        context.createConfigurationContext(configuration)
                                .getText(17042771)
                                .toString();
                int i = this.val$callState;
                CallStateHelper callStateHelper2 = CallStateHelper.this;
                textToSpeech.speak(
                        charSequence,
                        0,
                        i == 1
                                ? callStateHelper2.mTtsHashForTTSPath
                                : callStateHelper2.mTtsHashForVoiceCallPath);
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class CallStateCallback extends TelephonyCallback
                implements TelephonyCallback.CallStateListener {
            public CallStateCallback() {}

            @Override // android.telephony.TelephonyCallback.CallStateListener
            public final void onCallStateChanged(final int i) {
                if (i != 0) {
                    CallStateHelper callStateHelper = CallStateHelper.this;
                    callStateHelper.getClass();
                    String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                    Slog.d("SensorPrivacyService", "onCall");
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (callStateHelper.mCallStateLock) {
                            SensorPrivacyService.this.mSensorPrivacyServiceImpl.showSensorUseDialog(
                                    1);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (SensorPrivacyService.this.mSensorPrivacyServiceImpl
                                .isToggleSensorPrivacyEnabled(1, 1)) {
                            final CallStateHelper callStateHelper2 = CallStateHelper.this;
                            if (callStateHelper2.mTts == null) {
                                callStateHelper2.mTts =
                                        new TextToSpeech(
                                                SensorPrivacyService.this.mContext,
                                                new TextToSpeech
                                                        .OnInitListener() { // from class:
                                                                            // com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.1
                                                    @Override // android.speech.tts.TextToSpeech.OnInitListener
                                                    public final void onInit(int i2) {
                                                        CallStateHelper callStateHelper3 =
                                                                CallStateHelper.this;
                                                        callStateHelper3.mHandler.postDelayed(
                                                                callStateHelper3
                                                                .new AnonymousClass2(i),
                                                                1000L);
                                                    }
                                                });
                                return;
                            } else {
                                callStateHelper2.mHandler.postDelayed(
                                        callStateHelper2.new AnonymousClass2(i), 1000L);
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                CallStateHelper callStateHelper3 = CallStateHelper.this;
                synchronized (callStateHelper3.mCallStateLock) {
                    try {
                        if (callStateHelper3.mIsInEmergencyCall) {
                            callStateHelper3.mIsInEmergencyCall = false;
                            if (callStateHelper3.mMicUnmutedForEmergencyCall) {
                                SensorPrivacyService sensorPrivacyService =
                                        SensorPrivacyService.this;
                                SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                                        sensorPrivacyService.mSensorPrivacyServiceImpl;
                                int i2 = sensorPrivacyService.mCurrentUser;
                                int i3 = SensorPrivacyServiceImpl.$r8$clinit;
                                sensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(
                                        1, i2, 5, 1, true);
                                callStateHelper3.mMicUnmutedForEmergencyCall = false;
                            }
                            if (callStateHelper3.mCameraBlockedForEmergencyCall) {
                                SensorPrivacyService sensorPrivacyService2 =
                                        SensorPrivacyService.this;
                                SensorPrivacyServiceImpl sensorPrivacyServiceImpl2 =
                                        sensorPrivacyService2.mSensorPrivacyServiceImpl;
                                int i4 = sensorPrivacyService2.mCurrentUser;
                                int i5 = SensorPrivacyServiceImpl.$r8$clinit;
                                sensorPrivacyServiceImpl2.setToggleSensorPrivacyUnchecked(
                                        1, i4, 5, 2, true);
                                callStateHelper3.mCameraBlockedForEmergencyCall = false;
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class OutgoingEmergencyStateCallback extends TelephonyCallback
                implements TelephonyCallback.OutgoingEmergencyCallListener {
            public OutgoingEmergencyStateCallback() {}

            public final void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) {
                CallStateHelper callStateHelper = CallStateHelper.this;
                synchronized (callStateHelper.mCallStateLock) {
                    try {
                        if (!callStateHelper.mIsInEmergencyCall) {
                            callStateHelper.mIsInEmergencyCall = true;
                            if (SensorPrivacyService.this.mSensorPrivacyServiceImpl
                                    .isToggleSensorPrivacyEnabled(1, 1)) {
                                SensorPrivacyService sensorPrivacyService =
                                        SensorPrivacyService.this;
                                sensorPrivacyService.mSensorPrivacyServiceImpl
                                        .setToggleSensorPrivacyUnchecked(
                                                1, sensorPrivacyService.mCurrentUser, 5, 1, false);
                                callStateHelper.mMicUnmutedForEmergencyCall = true;
                            } else {
                                callStateHelper.mMicUnmutedForEmergencyCall = false;
                            }
                            SensorPrivacyService sensorPrivacyService2 = SensorPrivacyService.this;
                            if (sensorPrivacyService2.mSensorPrivacyServiceImpl
                                    .isToggleSensorPrivacyEnabled(
                                            sensorPrivacyService2.mCurrentUser, 2)) {
                                SensorPrivacyService sensorPrivacyService3 =
                                        SensorPrivacyService.this;
                                sensorPrivacyService3.mSensorPrivacyServiceImpl
                                        .setToggleSensorPrivacyUnchecked(
                                                1, sensorPrivacyService3.mCurrentUser, 5, 2, false);
                                callStateHelper.mCameraBlockedForEmergencyCall = true;
                            } else {
                                callStateHelper.mCameraBlockedForEmergencyCall = false;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public CallStateHelper() {
            HashMap hashMap = new HashMap();
            this.mTtsHashForVoiceCallPath = hashMap;
            HashMap hashMap2 = new HashMap();
            this.mTtsHashForTTSPath = hashMap2;
            this.mCallStateLock = new Object();
            OutgoingEmergencyStateCallback outgoingEmergencyStateCallback =
                    new OutgoingEmergencyStateCallback();
            this.mEmergencyStateCallback = outgoingEmergencyStateCallback;
            CallStateCallback callStateCallback = new CallStateCallback();
            this.mCallStateCallback = callStateCallback;
            SensorPrivacyService.this.mTelephonyManager.registerTelephonyCallback(
                    FgThread.getExecutor(), outgoingEmergencyStateCallback);
            SensorPrivacyService.this.mTelephonyManager.registerTelephonyCallback(
                    FgThread.getExecutor(), callStateCallback);
            hashMap.put("streamType", Integer.toString(0));
            hashMap2.put("streamType", Integer.toString(2));
            this.mHandler = new Handler(Looper.getMainLooper());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeathRecipient implements IBinder.DeathRecipient {
        public final ISensorPrivacyListener mListener;

        public DeathRecipient(ISensorPrivacyListener iSensorPrivacyListener) {
            this.mListener = iSensorPrivacyListener;
            try {
                iSensorPrivacyListener.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                    SensorPrivacyService.this.mSensorPrivacyServiceImpl;
            ISensorPrivacyListener iSensorPrivacyListener = this.mListener;
            SensorPrivacyHandler sensorPrivacyHandler = sensorPrivacyServiceImpl.mHandler;
            int i = SensorPrivacyHandler.$r8$clinit;
            sensorPrivacyHandler.removeDeathRecipient(iSensorPrivacyListener);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SensorPrivacyHandler extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final ArrayMap mDeathRecipients;
        public final Object mListenerLock;
        public final RemoteCallbackList mListeners;
        public final RemoteCallbackList mToggleSensorListeners;

        public SensorPrivacyHandler(Looper looper) {
            super(looper);
            this.mListenerLock = new Object();
            this.mListeners = new RemoteCallbackList();
            this.mToggleSensorListeners = new RemoteCallbackList();
            this.mDeathRecipients = new ArrayMap();
        }

        public final void addDeathRecipient(ISensorPrivacyListener iSensorPrivacyListener) {
            Pair pair;
            IBinder asBinder = iSensorPrivacyListener.asBinder();
            Pair pair2 = (Pair) this.mDeathRecipients.get(asBinder);
            if (pair2 == null) {
                pair =
                        new Pair(
                                SensorPrivacyService.this
                                .new DeathRecipient(iSensorPrivacyListener),
                                1);
            } else {
                pair =
                        new Pair(
                                (DeathRecipient) pair2.first,
                                Integer.valueOf(((Integer) pair2.second).intValue() + 1));
            }
            this.mDeathRecipients.put(asBinder, pair);
        }

        public final void handleSensorPrivacyChanged(final int i, int i2, int i3, final boolean z) {
            int i4;
            ArraySet arraySet;
            SensorPrivacyManagerInternalImpl sensorPrivacyManagerInternalImpl =
                    SensorPrivacyService.this.mSensorPrivacyManagerInternal;
            synchronized (sensorPrivacyManagerInternalImpl.mLock) {
                try {
                    ArraySet arraySet2 =
                            (ArraySet)
                                    sensorPrivacyManagerInternalImpl.mAllUserListeners.get(
                                            Integer.valueOf(i3));
                    if (arraySet2 != null) {
                        for (int i5 = 0; i5 < arraySet2.size(); i5++) {
                            final SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener
                                    onUserSensorPrivacyChangedListener =
                                            (SensorPrivacyManagerInternal
                                                            .OnUserSensorPrivacyChangedListener)
                                                    arraySet2.valueAt(i5);
                            BackgroundThread.getHandler()
                                    .post(
                                            new Runnable() { // from class:
                                                             // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyManagerInternalImpl$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    onUserSensorPrivacyChangedListener
                                                            .onSensorPrivacyChanged(i, z);
                                                }
                                            });
                        }
                    }
                    ArrayMap arrayMap =
                            (ArrayMap)
                                    sensorPrivacyManagerInternalImpl.mListeners.get(
                                            Integer.valueOf(i));
                    if (arrayMap != null
                            && (arraySet = (ArraySet) arrayMap.get(Integer.valueOf(i3))) != null) {
                        for (int i6 = 0; i6 < arraySet.size(); i6++) {
                            final SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener
                                    onSensorPrivacyChangedListener =
                                            (SensorPrivacyManagerInternal
                                                            .OnSensorPrivacyChangedListener)
                                                    arraySet.valueAt(i6);
                            BackgroundThread.getHandler()
                                    .post(
                                            new Runnable() { // from class:
                                                             // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyManagerInternalImpl$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    onSensorPrivacyChangedListener
                                                            .onSensorPrivacyChanged(z);
                                                }
                                            });
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            SensorPrivacyService sensorPrivacyService = SensorPrivacyService.this;
            if (i == sensorPrivacyService.mCurrentUser) {
                SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                        sensorPrivacyService.mSensorPrivacyServiceImpl;
                sensorPrivacyServiceImpl.setGlobalRestriction(
                        i3, sensorPrivacyServiceImpl.isCombinedToggleSensorPrivacyEnabled(i3));
            }
            if (i != SensorPrivacyService.this.mCurrentUser) {
                return;
            }
            synchronized (this.mListenerLock) {
                try {
                    int beginBroadcast = this.mToggleSensorListeners.beginBroadcast();
                    for (i4 = 0; i4 < beginBroadcast; i4++) {
                        ISensorPrivacyListener broadcastItem =
                                this.mToggleSensorListeners.getBroadcastItem(i4);
                        try {
                            broadcastItem.onSensorPrivacyChanged(i2, i3, z);
                        } catch (RemoteException e) {
                            String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                            Log.e(
                                    "SensorPrivacyService",
                                    "Caught an exception notifying listener "
                                            + broadcastItem
                                            + ": ",
                                    e);
                        }
                    }
                } finally {
                    this.mToggleSensorListeners.finishBroadcast();
                }
            }
            SensorPrivacyServiceImpl.m857$$Nest$mshowSensorStateChangedActivity(
                    SensorPrivacyService.this.mSensorPrivacyServiceImpl, i3, i2);
        }

        public final void removeDeathRecipient(ISensorPrivacyListener iSensorPrivacyListener) {
            IBinder asBinder = iSensorPrivacyListener.asBinder();
            Pair pair = (Pair) this.mDeathRecipients.get(asBinder);
            if (pair == null) {
                return;
            }
            int intValue = ((Integer) pair.second).intValue() - 1;
            if (intValue != 0) {
                this.mDeathRecipients.put(
                        asBinder, new Pair((DeathRecipient) pair.first, Integer.valueOf(intValue)));
                return;
            }
            this.mDeathRecipients.remove(asBinder);
            DeathRecipient deathRecipient = (DeathRecipient) pair.first;
            deathRecipient.getClass();
            try {
                deathRecipient.mListener.asBinder().unlinkToDeath(deathRecipient, 0);
            } catch (NoSuchElementException unused) {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SensorPrivacyManagerInternalImpl extends SensorPrivacyManagerInternal {
        public final ArrayMap mListeners = new ArrayMap();
        public final ArrayMap mAllUserListeners = new ArrayMap();
        public final Object mLock = new Object();

        public SensorPrivacyManagerInternalImpl() {}

        public final void addSensorPrivacyListener(
                int i,
                int i2,
                SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener
                        onSensorPrivacyChangedListener) {
            synchronized (this.mLock) {
                try {
                    ArrayMap arrayMap = (ArrayMap) this.mListeners.get(Integer.valueOf(i));
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                        this.mListeners.put(Integer.valueOf(i), arrayMap);
                    }
                    ArraySet arraySet = (ArraySet) arrayMap.get(Integer.valueOf(i2));
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                        arrayMap.put(Integer.valueOf(i2), arraySet);
                    }
                    arraySet.add(onSensorPrivacyChangedListener);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void addSensorPrivacyListenerForAllUsers(
                int i,
                SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener
                        onUserSensorPrivacyChangedListener) {
            synchronized (this.mLock) {
                try {
                    ArraySet arraySet = (ArraySet) this.mAllUserListeners.get(Integer.valueOf(i));
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                        this.mAllUserListeners.put(Integer.valueOf(i), arraySet);
                    }
                    arraySet.add(onUserSensorPrivacyChangedListener);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isSensorPrivacyEnabled(int i, int i2) {
            SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                    SensorPrivacyService.this.mSensorPrivacyServiceImpl;
            int i3 = SensorPrivacyServiceImpl.$r8$clinit;
            return sensorPrivacyServiceImpl.isToggleSensorPrivacyEnabledInternal(i, 1, i2);
        }

        public final void setPhysicalToggleSensorPrivacy(int i, int i2, boolean z) {
            SensorPrivacyService sensorPrivacyService = SensorPrivacyService.this;
            SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                    sensorPrivacyService.mSensorPrivacyServiceImpl;
            if (i == -2) {
                i = sensorPrivacyService.mCurrentUser;
            }
            int userId = i == -10000 ? sensorPrivacyService.mContext.getUserId() : i;
            int i3 = SensorPrivacyServiceImpl.$r8$clinit;
            sensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(2, userId, 5, i2, z);
            if (z) {
                return;
            }
            sensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(1, userId, 5, i2, z);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SensorPrivacyServiceImpl extends ISensorPrivacyManager.Stub
            implements AppOpsManager.OnOpNotedInternalListener,
                    AppOpsManager.OnOpStartedListener,
                    IBinder.DeathRecipient,
                    UserManagerInternal.UserRestrictionsListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final SensorPrivacyHandler mHandler;
        public boolean mIsConfirmPopupShowing;
        public final SensorPrivacyStateControllerImpl mSensorPrivacyStateController;
        public final Object mLock = new Object();
        public final ArrayMap mSuppressReminders = new ArrayMap();
        public final ArrayMap mQueuedSensorUseReminderDialogs = new ArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$1, reason: invalid class name */
        public final class AnonymousClass1 extends BroadcastReceiver {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ SensorPrivacyServiceImpl this$1;

            public /* synthetic */ AnonymousClass1(
                    SensorPrivacyServiceImpl sensorPrivacyServiceImpl, int i) {
                this.$r8$classId = i;
                this.this$1 = sensorPrivacyServiceImpl;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                switch (this.$r8$classId) {
                    case 0:
                        this.this$1.setToggleSensorPrivacy(
                                ((UserHandle)
                                                intent.getParcelableExtra(
                                                        "android.intent.extra.USER",
                                                        UserHandle.class))
                                        .getIdentifier(),
                                5,
                                intent.getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, 0),
                                false);
                        int intExtra =
                                intent.getIntExtra(SensorPrivacyManager.EXTRA_NOTIFICATION_ID, 0);
                        if (intExtra != 0) {
                            SensorPrivacyService.this.mNotificationManager.cancel(intExtra);
                            return;
                        }
                        return;
                    default:
                        SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                                this.this$1.mSensorPrivacyStateController;
                        SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                                sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1 =
                                        new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1(
                                                2, this);
                        synchronized (sensorPrivacyStateControllerImpl.mLock) {
                            sensorPrivacyStateControllerImpl.forEachStateLocked(
                                    sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1);
                        }
                        SensorPrivacyServiceImpl sensorPrivacyServiceImpl = this.this$1;
                        int intExtra2 = intent.getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, 0);
                        sensorPrivacyServiceImpl.getClass();
                        SensorPrivacyService.this.mNotificationManager.cancel(
                                intExtra2 == 1 ? 65 : 66);
                        return;
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$3, reason: invalid class name */
        public final class AnonymousClass3 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$4, reason: invalid class name */
        public final class AnonymousClass4 implements DialogInterface.OnClickListener {
            public final /* synthetic */ int val$displayId;
            public final /* synthetic */ int val$sensor;
            public final /* synthetic */ int val$source;
            public final /* synthetic */ int val$userId;

            public AnonymousClass4(int i, int i2, int i3, int i4) {
                this.val$userId = i;
                this.val$source = i2;
                this.val$sensor = i3;
                this.val$displayId = i4;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                int i2 = this.val$userId;
                if (i2 == -2) {
                    i2 = SensorPrivacyService.this.mCurrentUser;
                }
                final int profileParentId =
                        SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i2);
                String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                profileParentId, "showConfirmDialog parentId ", " source=");
                m.append(this.val$source);
                m.append(" sensor=");
                m.append(this.val$sensor);
                m.append(" displayId=");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                        m, this.val$displayId, "SensorPrivacyService");
                SensorPrivacyService sensorPrivacyService = SensorPrivacyService.this;
                final int i3 = this.val$source;
                final int i4 = this.val$sensor;
                SensorPrivacyService.m856$$Nest$mforAllUsers(
                        sensorPrivacyService,
                        new FunctionalUtils
                                .ThrowingConsumer() { // from class:
                                                      // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$4$$ExternalSyntheticLambda0
                            public final void acceptOrThrow(Object obj) {
                                SensorPrivacyService.SensorPrivacyServiceImpl.AnonymousClass4
                                        anonymousClass4 =
                                                SensorPrivacyService.SensorPrivacyServiceImpl
                                                        .AnonymousClass4.this;
                                int i5 = profileParentId;
                                int i6 = i3;
                                int i7 = i4;
                                Integer num = (Integer) obj;
                                if (i5
                                        == SensorPrivacyService.this.mUserManagerInternal
                                                .getProfileParentId(num.intValue())) {
                                    SensorPrivacyService.SensorPrivacyServiceImpl.this
                                            .setToggleSensorPrivacy(num.intValue(), i6, i7, true);
                                }
                            }
                        });
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class SensorUseReminderDialogInfo {
            public String mPackageName;
            public int mTaskId;
            public UserHandle mUser;

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || !(obj instanceof SensorUseReminderDialogInfo)) {
                    return false;
                }
                SensorUseReminderDialogInfo sensorUseReminderDialogInfo =
                        (SensorUseReminderDialogInfo) obj;
                return this.mTaskId == sensorUseReminderDialogInfo.mTaskId
                        && Objects.equals(this.mUser, sensorUseReminderDialogInfo.mUser)
                        && Objects.equals(
                                this.mPackageName, sensorUseReminderDialogInfo.mPackageName);
            }

            public final int hashCode() {
                return Objects.hash(Integer.valueOf(this.mTaskId), this.mUser, this.mPackageName);
            }
        }

        /* renamed from: -$$Nest$mshowSensorStateChangedActivity, reason: not valid java name */
        public static void m857$$Nest$mshowSensorStateChangedActivity(
                SensorPrivacyServiceImpl sensorPrivacyServiceImpl, int i, int i2) {
            String string =
                    SensorPrivacyService.this
                            .mContext
                            .getResources()
                            .getString(R.string.face_acquired_recalibrate);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString(string));
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setTaskOverlay(true, true);
            intent.addFlags(8650752);
            intent.putExtra(SensorPrivacyManager.EXTRA_SENSOR, i);
            intent.putExtra(SensorPrivacyManager.EXTRA_TOGGLE_TYPE, i2);
            SensorPrivacyService.this.mContext.startActivityAsUser(
                    intent, makeBasic.toBundle(), UserHandle.SYSTEM);
        }

        /* renamed from: -$$Nest$muserSwitching, reason: not valid java name */
        public static void m858$$Nest$muserSwitching(
                final SensorPrivacyServiceImpl sensorPrivacyServiceImpl,
                final int i,
                final int i2) {
            int i3;
            final boolean[] zArr = new boolean[2];
            final boolean[] zArr2 = new boolean[2];
            final boolean[] zArr3 = new boolean[2];
            final boolean[] zArr4 = new boolean[2];
            final int i4 = 0;
            sensorPrivacyServiceImpl.mSensorPrivacyStateController.atomic(
                    new Runnable(
                            sensorPrivacyServiceImpl) { // from class:
                                                        // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda8
                        public final /* synthetic */ SensorPrivacyService.SensorPrivacyServiceImpl
                                f$0;

                        {
                            this.f$0 = sensorPrivacyServiceImpl;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i4) {
                                case 0:
                                    SensorPrivacyService.SensorPrivacyServiceImpl
                                            sensorPrivacyServiceImpl2 = this.f$0;
                                    boolean[] zArr5 = zArr3;
                                    int i5 = i;
                                    boolean[] zArr6 = zArr4;
                                    boolean[] zArr7 = zArr;
                                    int i6 = i2;
                                    boolean[] zArr8 = zArr2;
                                    zArr5[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(i5, 1, 1);
                                    zArr6[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(i5, 1, 2);
                                    zArr7[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(i6, 1, 1);
                                    zArr8[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(i6, 1, 2);
                                    break;
                                default:
                                    SensorPrivacyService.SensorPrivacyServiceImpl
                                            sensorPrivacyServiceImpl3 = this.f$0;
                                    boolean[] zArr9 = zArr3;
                                    int i7 = i;
                                    boolean[] zArr10 = zArr4;
                                    boolean[] zArr11 = zArr;
                                    int i8 = i2;
                                    boolean[] zArr12 = zArr2;
                                    zArr9[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i7, 2, 1);
                                    zArr10[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i7, 2, 2);
                                    zArr11[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i8, 2, 1);
                                    zArr12[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i8, 2, 2);
                                    break;
                            }
                        }
                    });
            final int i5 = 1;
            sensorPrivacyServiceImpl.mSensorPrivacyStateController.atomic(
                    new Runnable(
                            sensorPrivacyServiceImpl) { // from class:
                                                        // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda8
                        public final /* synthetic */ SensorPrivacyService.SensorPrivacyServiceImpl
                                f$0;

                        {
                            this.f$0 = sensorPrivacyServiceImpl;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i5) {
                                case 0:
                                    SensorPrivacyService.SensorPrivacyServiceImpl
                                            sensorPrivacyServiceImpl2 = this.f$0;
                                    boolean[] zArr5 = zArr3;
                                    int i52 = i;
                                    boolean[] zArr6 = zArr4;
                                    boolean[] zArr7 = zArr;
                                    int i6 = i2;
                                    boolean[] zArr8 = zArr2;
                                    zArr5[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(
                                                            i52, 1, 1);
                                    zArr6[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(
                                                            i52, 1, 2);
                                    zArr7[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(i6, 1, 1);
                                    zArr8[0] =
                                            sensorPrivacyServiceImpl2
                                                    .isToggleSensorPrivacyEnabledInternal(i6, 1, 2);
                                    break;
                                default:
                                    SensorPrivacyService.SensorPrivacyServiceImpl
                                            sensorPrivacyServiceImpl3 = this.f$0;
                                    boolean[] zArr9 = zArr3;
                                    int i7 = i;
                                    boolean[] zArr10 = zArr4;
                                    boolean[] zArr11 = zArr;
                                    int i8 = i2;
                                    boolean[] zArr12 = zArr2;
                                    zArr9[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i7, 2, 1);
                                    zArr10[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i7, 2, 2);
                                    zArr11[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i8, 2, 1);
                                    zArr12[1] =
                                            sensorPrivacyServiceImpl3
                                                    .isToggleSensorPrivacyEnabledInternal(i8, 2, 2);
                                    break;
                            }
                        }
                    });
            if (i != -10000 && zArr3[0] == zArr[0] && zArr3[1] == zArr[1]) {
                i3 = i2;
            } else {
                i3 = i2;
                sensorPrivacyServiceImpl.mHandler.handleSensorPrivacyChanged(i3, 1, 1, zArr[0]);
                sensorPrivacyServiceImpl.mHandler.handleSensorPrivacyChanged(i3, 2, 1, zArr[1]);
                sensorPrivacyServiceImpl.setGlobalRestriction(1, zArr[0] || zArr[1]);
            }
            if (i != -10000 && zArr4[0] == zArr2[0] && zArr4[1] == zArr2[1]) {
                return;
            }
            sensorPrivacyServiceImpl.mHandler.handleSensorPrivacyChanged(i3, 1, 2, zArr2[0]);
            sensorPrivacyServiceImpl.mHandler.handleSensorPrivacyChanged(i3, 2, 2, zArr2[1]);
            sensorPrivacyServiceImpl.setGlobalRestriction(2, zArr2[0] || zArr2[1]);
        }

        public SensorPrivacyServiceImpl() {
            Looper looper = FgThread.get().getLooper();
            Context context = SensorPrivacyService.this.mContext;
            SensorPrivacyHandler sensorPrivacyHandler =
                    SensorPrivacyService.this.new SensorPrivacyHandler(looper);
            this.mHandler = sensorPrivacyHandler;
            if (SensorPrivacyStateControllerImpl.sInstance$1 == null) {
                if (SensorPrivacyStateControllerImpl.sInstance == null) {
                    SensorPrivacyStateControllerImpl.sInstance =
                            new SensorPrivacyStateControllerImpl();
                }
                SensorPrivacyStateControllerImpl.sInstance$1 =
                        SensorPrivacyStateControllerImpl.sInstance;
            }
            SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                    SensorPrivacyStateControllerImpl.sInstance$1;
            this.mSensorPrivacyStateController = sensorPrivacyStateControllerImpl;
            SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                    sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1 =
                            new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1(
                                    1, this);
            synchronized (sensorPrivacyStateControllerImpl.mLock) {
                sensorPrivacyStateControllerImpl.forEachStateLocked(
                        sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1);
            }
            int[] iArr = {27, 100, 26, 101, 121};
            SensorPrivacyService.this.mAppOpsManager.startWatchingNoted(iArr, this);
            SensorPrivacyService.this.mAppOpsManager.startWatchingStarted(iArr, this);
            SensorPrivacyService.this.mContext.registerReceiver(
                    new AnonymousClass1(this, 0),
                    new IntentFilter(SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY),
                    "android.permission.MANAGE_SENSOR_PRIVACY",
                    null,
                    2);
            SensorPrivacyService.this.mContext.registerReceiver(
                    new AnonymousClass1(this, 1),
                    new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
            SensorPrivacyService.this.mUserManagerInternal.addUserRestrictionsListener(this);
            SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                    sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda12 =
                            new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1(
                                    0, sensorPrivacyHandler);
            synchronized (sensorPrivacyStateControllerImpl.mLock) {
                AllSensorStateController allSensorStateController =
                        sensorPrivacyStateControllerImpl.mAllSensorStateController;
                allSensorStateController.getClass();
                if (allSensorStateController.mListener != null) {
                    throw new IllegalStateException("Listener is already set");
                }
                allSensorStateController.mListener =
                        sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda12;
                allSensorStateController.mListenerHandler = sensorPrivacyHandler;
            }
            SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2
                    sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2 =
                            new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2(
                                    this);
            synchronized (sensorPrivacyStateControllerImpl.mLock) {
                try {
                    if (sensorPrivacyStateControllerImpl.mListener != null) {
                        throw new IllegalStateException("Listener is already set");
                    }
                    sensorPrivacyStateControllerImpl.mListener =
                            sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2;
                    sensorPrivacyStateControllerImpl.mListenerHandler = sensorPrivacyHandler;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static boolean isAutomotive(Context context) {
            return (context.getResources().getConfiguration().uiMode & 15) == 3;
        }

        public static void logSensorPrivacyToggle(int i, int i2, long j, boolean z, boolean z2) {
            long max = Math.max(0L, (SystemClock.elapsedRealtime() - j) / 60000);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.PRIVACY_SENSOR_TOGGLE_INTERACTION,
                    i2 != 1 ? i2 != 2 ? 0 : 2 : 1,
                    z2 ? 0 : z ? 2 : 1,
                    i != 1 ? i != 2 ? i != 3 ? 0 : 1 : 2 : 3,
                    max);
        }

        public final void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new NullPointerException("listener cannot be null");
            }
            SensorPrivacyHandler sensorPrivacyHandler = this.mHandler;
            synchronized (sensorPrivacyHandler.mListenerLock) {
                try {
                    if (sensorPrivacyHandler.mListeners.register(iSensorPrivacyListener)) {
                        sensorPrivacyHandler.addDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void addToggleSensorPrivacyListener(
                ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new IllegalArgumentException("listener cannot be null");
            }
            SensorPrivacyHandler sensorPrivacyHandler = this.mHandler;
            synchronized (sensorPrivacyHandler.mListenerLock) {
                try {
                    if (sensorPrivacyHandler.mToggleSensorListeners.register(
                            iSensorPrivacyListener)) {
                        sensorPrivacyHandler.addDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied(IBinder iBinder) {
            synchronized (this.mLock) {
                try {
                    Iterator it = this.mSuppressReminders.keySet().iterator();
                    while (it.hasNext()) {
                        removeSuppressPackageReminderToken((Pair) it.next(), iBinder);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean canChangeToggleSensorPrivacy(int i, int i2) {
            boolean z;
            KeyguardManager keyguardManager;
            if (i2 == 1 || i2 == 2) {
                CallStateHelper callStateHelper = SensorPrivacyService.this.mCallStateHelper;
                synchronized (callStateHelper.mCallStateLock) {
                    z = callStateHelper.mIsInEmergencyCall;
                }
                if (z) {
                    String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                    Log.i(
                            "SensorPrivacyService",
                            "Can't change mic toggle during an emergency call");
                    return false;
                }
            }
            if (requiresAuthentication()
                    && (keyguardManager = SensorPrivacyService.this.mKeyguardManager) != null
                    && keyguardManager.isDeviceLocked(i)) {
                String str2 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                Log.i("SensorPrivacyService", "Can't change mic/cam toggle while device is locked");
                return false;
            }
            if (i2 == 1
                    && SensorPrivacyService.this.mUserManagerInternal.getUserRestriction(
                            i, "disallow_microphone_toggle")) {
                String str3 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                Log.i("SensorPrivacyService", "Can't change mic toggle due to admin restriction");
                return false;
            }
            if (i2 != 2
                    || !SensorPrivacyService.this.mUserManagerInternal.getUserRestriction(
                            i, "disallow_camera_toggle")) {
                return true;
            }
            String str4 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
            Log.i("SensorPrivacyService", "Can't change camera toggle due to admin restriction");
            return false;
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            String str;
            Objects.requireNonNull(fileDescriptor);
            Context context = SensorPrivacyService.this.mContext;
            String str2 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
            if (DumpUtils.checkDumpPermission(context, "SensorPrivacyService", printWriter)) {
                int i = 0;
                boolean z = false;
                while (i < strArr.length
                        && (str = strArr[i]) != null
                        && str.length() > 0
                        && str.charAt(0) == '-') {
                    i++;
                    if ("--proto".equals(str)) {
                        z = true;
                    } else {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "Unknown argument: ", str, "; use -h for help");
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (z) {
                        SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                                this.mSensorPrivacyStateController;
                        DualDumpOutputStream dualDumpOutputStream =
                                new DualDumpOutputStream(new ProtoOutputStream(fileDescriptor));
                        synchronized (sensorPrivacyStateControllerImpl.mLock) {
                            sensorPrivacyStateControllerImpl.mAllSensorStateController.getClass();
                            sensorPrivacyStateControllerImpl.dumpLocked(dualDumpOutputStream);
                        }
                        dualDumpOutputStream.flush();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                    printWriter.println("SENSOR PRIVACY MANAGER STATE (dumpsys sensor_privacy)");
                    SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl2 =
                            this.mSensorPrivacyStateController;
                    DualDumpOutputStream dualDumpOutputStream2 =
                            new DualDumpOutputStream(new IndentingPrintWriter(printWriter, "  "));
                    synchronized (sensorPrivacyStateControllerImpl2.mLock) {
                        sensorPrivacyStateControllerImpl2.mAllSensorStateController.getClass();
                        sensorPrivacyStateControllerImpl2.dumpLocked(dualDumpOutputStream2);
                    }
                    dualDumpOutputStream2.flush();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public final void enforceManageSensorPrivacyPermission() {
            if (SensorPrivacyService.this.mContext.checkCallingOrSelfPermission(
                            "android.permission.MANAGE_SENSOR_PRIVACY")
                    != 0) {
                throw new SecurityException(
                        "Changing sensor privacy requires the following permission:"
                            + " android.permission.MANAGE_SENSOR_PRIVACY");
            }
        }

        public final void enforceObserveSensorPrivacyPermission() {
            if (UserHandle.getCallingAppId()
                            != UserHandle.getAppId(
                                    SensorPrivacyService.this.mPackageManagerInternal.getPackageUid(
                                            SensorPrivacyService.this.mContext.getString(
                                                    R.string.config_systemUi),
                                            1048576L,
                                            0))
                    && SensorPrivacyService.this.mContext.checkCallingOrSelfPermission(
                                    "android.permission.OBSERVE_SENSOR_PRIVACY")
                            != 0) {
                throw new SecurityException(
                        "Observing sensor privacy changes requires the following permission:"
                            + " android.permission.OBSERVE_SENSOR_PRIVACY");
            }
        }

        public final void enqueueSensorUseReminderDialogAsync(
                int i, int i2, UserHandle userHandle, String str) {
            this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda11(),
                            this,
                            Integer.valueOf(i),
                            userHandle,
                            str,
                            Integer.valueOf(i2)));
        }

        public final List getCameraPrivacyAllowlist() {
            enforceObserveSensorPrivacyPermission();
            return SensorPrivacyService.this.mCameraPrivacyAllowlist;
        }

        public final String getSensorUseActivityName(ArraySet arraySet) {
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                if (isToggleSensorPrivacyEnabled(2, ((Integer) it.next()).intValue())) {
                    return SensorPrivacyService.this
                            .mContext
                            .getResources()
                            .getString(R.string.face_acquired_roll_too_extreme);
                }
            }
            return SensorPrivacyService.this
                    .mContext
                    .getResources()
                    .getString(R.string.face_acquired_recalibrate_alt);
        }

        public final int getToggleSensorPrivacyState(int i, int i2) {
            enforceObserveSensorPrivacyPermission();
            return this.mSensorPrivacyStateController.getState(
                            i, SensorPrivacyService.this.mCurrentUser, i2)
                    .mStateType;
        }

        public final boolean isCameraPrivacyEnabled(String str) {
            enforceObserveSensorPrivacyPermission();
            int i =
                    this.mSensorPrivacyStateController.getState(
                                    1, SensorPrivacyService.this.mCurrentUser, 2)
                            .mStateType;
            if (i == 1) {
                return true;
            }
            if (i == 2 || i != 3) {
                return false;
            }
            Iterator it =
                    ((ArrayList) SensorPrivacyService.this.mCameraPrivacyAllowlist).iterator();
            while (it.hasNext()) {
                if (str.equals((String) it.next())) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isCombinedToggleSensorPrivacyEnabled(int i) {
            return isToggleSensorPrivacyEnabled(1, i) || isToggleSensorPrivacyEnabled(2, i);
        }

        public final boolean isSensorPrivacyEnabled() {
            boolean z;
            enforceObserveSensorPrivacyPermission();
            SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                    this.mSensorPrivacyStateController;
            synchronized (sensorPrivacyStateControllerImpl.mLock) {
                z = sensorPrivacyStateControllerImpl.mAllSensorStateController.mEnabled;
            }
            return z;
        }

        public final boolean isToggleSensorPrivacyEnabled(int i, int i2) {
            enforceObserveSensorPrivacyPermission();
            return this.mSensorPrivacyStateController
                    .getState(i, SensorPrivacyService.this.mCurrentUser, i2)
                    .isEnabled();
        }

        public final boolean isToggleSensorPrivacyEnabledInternal(int i, int i2, int i3) {
            return this.mSensorPrivacyStateController.getState(i2, i, i3).isEnabled();
        }

        public final void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
            String str3 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "onOpNoted  ", str, "  code=", "  uid="),
                    i2,
                    "SensorPrivacyService");
            if ((i3 & 13) == 0) {
                return;
            }
            int i5 = 1;
            if (i4 == 1) {
                if (i != 27 && i != 100 && i != 121) {
                    if (i != 26 && i != 101) {
                        return;
                    } else {
                        i5 = 2;
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    onSensorUseStarted(i2, i5, str);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void onOpStarted(int i, int i2, String str, String str2, int i3, int i4) {
            onOpNoted(i, i2, str, str2, i3, i4);
        }

        public final void onSensorUseStarted(int i, int i2, String str) {
            KeyguardManager keyguardManager;
            UserHandle of = UserHandle.of(SensorPrivacyService.this.mCurrentUser);
            if (Flags.cameraPrivacyAllowlist()
                    && i2 == 2
                    && isAutomotive(SensorPrivacyService.this.mContext)) {
                if (!isCameraPrivacyEnabled(str)) {
                    return;
                }
            } else if (!isCombinedToggleSensorPrivacyEnabled(i2)) {
                return;
            }
            if (i == 1000) {
                return;
            }
            synchronized (this.mLock) {
                try {
                    if (this.mSuppressReminders.containsKey(new Pair(Integer.valueOf(i2), of))) {
                        String str2 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        Log.d(
                                "SensorPrivacyService",
                                "Suppressed sensor privacy reminder for " + str + "/" + of);
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    List tasks =
                            SensorPrivacyService.this.mActivityTaskManager.getTasks(
                                    Integer.MAX_VALUE);
                    int size = tasks.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        ActivityManager.RunningTaskInfo runningTaskInfo =
                                (ActivityManager.RunningTaskInfo) tasks.get(i3);
                        if (runningTaskInfo.isVisible) {
                            if (runningTaskInfo.topActivity.getPackageName().equals(str)) {
                                SensorPrivacyService sensorPrivacyService =
                                        SensorPrivacyService.this;
                                if (!sensorPrivacyService.mIsLargeCoverScreen
                                        && sensorPrivacyService.mIsFlipModel
                                        && runningTaskInfo.displayId == 1) {
                                    String str3 =
                                            SensorPrivacyService
                                                    .ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                                    Log.d(
                                            "SensorPrivacyService",
                                            "popup will not be displayed at subscreen at flip model"
                                                + " : "
                                                    + runningTaskInfo);
                                } else if (runningTaskInfo.displayId == 4) {
                                    String str4 =
                                            SensorPrivacyService
                                                    .ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                                    Log.d(
                                            "SensorPrivacyService",
                                            "popup will not be displayed at view cover display : "
                                                    + runningTaskInfo);
                                } else {
                                    if (runningTaskInfo.isFocused) {
                                        enqueueSensorUseReminderDialogAsync(
                                                runningTaskInfo.taskId, i2, of, str);
                                        return;
                                    }
                                    arrayList.add(runningTaskInfo);
                                }
                            } else if (runningTaskInfo
                                            .topActivity
                                            .flattenToString()
                                            .equals(
                                                    getSensorUseActivityName(
                                                            new ArraySet(
                                                                    Arrays.asList(
                                                                            Integer.valueOf(i2)))))
                                    && runningTaskInfo.isFocused) {
                                enqueueSensorUseReminderDialogAsync(
                                        runningTaskInfo.taskId, i2, of, str);
                            }
                        }
                    }
                    if (arrayList.size() == 1) {
                        enqueueSensorUseReminderDialogAsync(
                                ((ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId,
                                i2,
                                of,
                                str);
                        return;
                    }
                    if (arrayList.size() > 1) {
                        showSensorUseReminderNotification(i2, of, str);
                        return;
                    }
                    if (i2 == 1
                            && TextUtils.equals(str, "com.sec.android.app.voicenote")
                            && (keyguardManager = SensorPrivacyService.this.mKeyguardManager)
                                    != null
                            && keyguardManager.isKeyguardLocked()) {
                        String str5 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        Log.d("SensorPrivacyService", "display toast for voicenote in lock state ");
                        try {
                            CharSequence loadLabel =
                                    SensorPrivacyService.this
                                            .getUiContext()
                                            .getPackageManager()
                                            .getApplicationInfoAsUser(str, 0, of)
                                            .loadLabel(
                                                    SensorPrivacyService.this.mContext
                                                            .getPackageManager());
                            SensorPrivacyService sensorPrivacyService2 = SensorPrivacyService.this;
                            Toast.makeText(
                                            sensorPrivacyService2.mContext,
                                            Html.fromHtml(
                                                    sensorPrivacyService2
                                                            .getUiContext()
                                                            .getString(17042773, loadLabel),
                                                    0),
                                            0)
                                    .show();
                        } catch (PackageManager.NameNotFoundException unused) {
                            String str6 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                    "Cannot show sensor use toast for ",
                                    str,
                                    "SensorPrivacyService");
                        }
                    }
                    List<ActivityManager.RunningServiceInfo> runningServices =
                            SensorPrivacyService.this.mActivityManager.getRunningServices(
                                    Integer.MAX_VALUE);
                    int size2 = runningServices.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        ActivityManager.RunningServiceInfo runningServiceInfo =
                                runningServices.get(i4);
                        if (runningServiceInfo.foreground
                                && runningServiceInfo.service.getPackageName().equals(str)) {
                            showSensorUseReminderNotification(i2, of, str);
                            return;
                        }
                    }
                    String stringForUser =
                            Settings.Secure.getStringForUser(
                                    SensorPrivacyService.this.mContext.getContentResolver(),
                                    "default_input_method",
                                    of.getIdentifier());
                    String packageName =
                            stringForUser != null
                                    ? ComponentName.unflattenFromString(stringForUser)
                                            .getPackageName()
                                    : null;
                    try {
                        int uidCapability =
                                SensorPrivacyService.this.mActivityManagerInternal.getUidCapability(
                                        i);
                        if (i2 == 1) {
                            VoiceInteractionManagerInternal voiceInteractionManagerInternal =
                                    (VoiceInteractionManagerInternal)
                                            LocalServices.getService(
                                                    VoiceInteractionManagerInternal.class);
                            if (voiceInteractionManagerInternal != null
                                    && voiceInteractionManagerInternal.hasActiveSession(str)) {
                                enqueueSensorUseReminderDialogAsync(-1, i2, of, str);
                                return;
                            }
                            if (TextUtils.equals(str, packageName) && (4 & uidCapability) != 0) {
                                enqueueSensorUseReminderDialogAsync(-1, i2, of, str);
                                return;
                            } else if (TextUtils.equals(str, "com.sec.android.app.voicenote")) {
                                SensorPrivacyService sensorPrivacyService3 =
                                        SensorPrivacyService.this;
                                if (sensorPrivacyService3.mIsLargeCoverScreen
                                        && sensorPrivacyService3.mIsFlipModel
                                        && sensorPrivacyService3.mIsFolded) {
                                    enqueueSensorUseReminderDialogAsync(-1, i2, of, str);
                                    return;
                                }
                            }
                        }
                        if (i2 == 2
                                && TextUtils.equals(str, packageName)
                                && (2 & uidCapability) != 0) {
                            enqueueSensorUseReminderDialogAsync(-1, i2, of, str);
                            return;
                        }
                        String str7 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append("/");
                        sb.append(i);
                        sb.append(" started using sensor ");
                        sb.append(i2);
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                sb,
                                " but no activity or foreground service was running. The user will"
                                    + " not be informed. System components should check if sensor"
                                    + " privacy is enabled for the sensor before accessing it.",
                                "SensorPrivacyService");
                    } catch (IllegalArgumentException e) {
                        String str8 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        Log.w("SensorPrivacyService", e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new ShellCommand() { // from class:
                // com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.7
                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x0067, code lost:

                   if (r10.equals("microphone") == false) goto L26;
                */
                /* JADX WARN: Code restructure failed: missing block: B:39:0x0095, code lost:

                   if (r8.equals("microphone") == false) goto L41;
                */
                /* JADX WARN: Removed duplicated region for block: B:16:0x006d  */
                /* JADX WARN: Removed duplicated region for block: B:18:0x0071  */
                /* JADX WARN: Removed duplicated region for block: B:45:0x00d2  */
                /* JADX WARN: Removed duplicated region for block: B:47:0x00d6  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final int onCommand(java.lang.String r12) {
                    /*
                        Method dump skipped, instructions count: 244
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.AnonymousClass7.onCommand(java.lang.String):int");
                }

                public final void onHelp() {
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    outPrintWriter.println("Sensor privacy manager (sensor_privacy) commands:");
                    outPrintWriter.println("  help");
                    outPrintWriter.println("    Print this help text.");
                    outPrintWriter.println("");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "  enable USER_ID SENSOR",
                            "    Enable privacy for a certain sensor.",
                            "",
                            "  disable USER_ID SENSOR");
                    outPrintWriter.println("    Disable privacy for a certain sensor.");
                    outPrintWriter.println("");
                    if (Flags.cameraPrivacyAllowlist()
                            && SensorPrivacyServiceImpl.isAutomotive(
                                    SensorPrivacyService.this.mContext)) {
                        BatteryService$$ExternalSyntheticOutline0.m(
                                outPrintWriter,
                                "  enable_except_allowlisted_apps USER_ID SENSOR",
                                "    Enable privacy except for automotive apps which are required"
                                    + " by OEM.",
                                "");
                    }
                }
            }.exec(
                    this,
                    fileDescriptor,
                    fileDescriptor2,
                    fileDescriptor3,
                    strArr,
                    shellCallback,
                    resultReceiver);
        }

        @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
        public final void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) {
            if (!bundle2.getBoolean("disallow_camera_toggle")
                    && bundle.getBoolean("disallow_camera_toggle")) {
                setToggleSensorPrivacyUnchecked(1, i, 5, 2, false);
            }
            if (bundle2.getBoolean("disallow_microphone_toggle")
                    || !bundle.getBoolean("disallow_microphone_toggle")) {
                return;
            }
            setToggleSensorPrivacyUnchecked(1, i, 5, 1, false);
        }

        public final void removeSensorPrivacyListener(
                ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new NullPointerException("listener cannot be null");
            }
            SensorPrivacyHandler sensorPrivacyHandler = this.mHandler;
            synchronized (sensorPrivacyHandler.mListenerLock) {
                try {
                    if (sensorPrivacyHandler.mListeners.unregister(iSensorPrivacyListener)) {
                        sensorPrivacyHandler.removeDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeSuppressPackageReminderToken(Pair pair, IBinder iBinder) {
            synchronized (this.mLock) {
                try {
                    ArrayList arrayList = (ArrayList) this.mSuppressReminders.get(pair);
                    if (arrayList == null) {
                        String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        Log.e("SensorPrivacyService", "No tokens for " + pair);
                        return;
                    }
                    if (arrayList.remove(iBinder)) {
                        iBinder.unlinkToDeath(this, 0);
                        if (arrayList.isEmpty()) {
                            this.mSuppressReminders.remove(pair);
                        }
                    } else {
                        String str2 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        Log.w(
                                "SensorPrivacyService",
                                "Could not remove sensor use reminder suppression token "
                                        + iBinder
                                        + " from "
                                        + pair);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeToggleSensorPrivacyListener(
                ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new IllegalArgumentException("listener cannot be null");
            }
            SensorPrivacyHandler sensorPrivacyHandler = this.mHandler;
            synchronized (sensorPrivacyHandler.mListenerLock) {
                try {
                    if (sensorPrivacyHandler.mToggleSensorListeners.unregister(
                            iSensorPrivacyListener)) {
                        sensorPrivacyHandler.removeDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean requiresAuthentication() {
            enforceObserveSensorPrivacyPermission();
            return SensorPrivacyService.this
                    .mContext
                    .getResources()
                    .getBoolean(R.bool.config_silenceRingerOnSleepKey);
        }

        public final void setCameraPrivacyAllowlist(List list) {
            enforceManageSensorPrivacyPermission();
            SensorPrivacyService.this.mCameraPrivacyAllowlist = new ArrayList(list);
        }

        public final void setGlobalRestriction(int i, boolean z) {
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                SensorPrivacyService sensorPrivacyService = SensorPrivacyService.this;
                sensorPrivacyService.mAppOpsManagerInternal.setGlobalRestriction(
                        26, z, sensorPrivacyService.mAppOpsRestrictionToken);
                SensorPrivacyService sensorPrivacyService2 = SensorPrivacyService.this;
                sensorPrivacyService2.mAppOpsManagerInternal.setGlobalRestriction(
                        101, z, sensorPrivacyService2.mAppOpsRestrictionToken);
                return;
            }
            SensorPrivacyService sensorPrivacyService3 = SensorPrivacyService.this;
            sensorPrivacyService3.mAppOpsManagerInternal.setGlobalRestriction(
                    27, z, sensorPrivacyService3.mAppOpsRestrictionToken);
            SensorPrivacyService sensorPrivacyService4 = SensorPrivacyService.this;
            sensorPrivacyService4.mAppOpsManagerInternal.setGlobalRestriction(
                    136, z, sensorPrivacyService4.mAppOpsRestrictionToken);
            SensorPrivacyService sensorPrivacyService5 = SensorPrivacyService.this;
            sensorPrivacyService5.mAppOpsManagerInternal.setGlobalRestriction(
                    100, z, sensorPrivacyService5.mAppOpsRestrictionToken);
            SensorPrivacyService sensorPrivacyService6 = SensorPrivacyService.this;
            sensorPrivacyService6.mAppOpsManagerInternal.setGlobalRestriction(
                    120, z, sensorPrivacyService6.mAppOpsRestrictionToken);
            boolean z2 =
                    Settings.Global.getInt(
                                    SensorPrivacyService.this.mContext.getContentResolver(),
                                    "receive_explicit_user_interaction_audio_enabled",
                                    1)
                            == 1;
            SensorPrivacyService sensorPrivacyService7 = SensorPrivacyService.this;
            sensorPrivacyService7.mAppOpsManagerInternal.setGlobalRestriction(
                    121, z && !z2, sensorPrivacyService7.mAppOpsRestrictionToken);
        }

        public final void setSensorPrivacy(boolean z) {
            Handler handler;
            enforceManageSensorPrivacyPermission();
            SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                    this.mSensorPrivacyStateController;
            synchronized (sensorPrivacyStateControllerImpl.mLock) {
                AllSensorStateController allSensorStateController =
                        sensorPrivacyStateControllerImpl.mAllSensorStateController;
                if (allSensorStateController.mEnabled != z) {
                    allSensorStateController.mEnabled = z;
                    SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                            sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1 =
                                    allSensorStateController.mListener;
                    if (sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                                    != null
                            && (handler = allSensorStateController.mListenerHandler) != null) {
                        handler.sendMessage(
                                PooledLambda.obtainMessage(
                                        new AllSensorStateController$$ExternalSyntheticLambda0(
                                                0,
                                                sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1),
                                        Boolean.valueOf(z)));
                    }
                }
            }
        }

        public final void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = SensorPrivacyService.this.mCurrentUser;
            }
            int i4 = i;
            if (canChangeToggleSensorPrivacy(i4, i3)) {
                if (!z || supportsSensorToggle(1, i3)) {
                    setToggleSensorPrivacyUnchecked(1, i4, i2, i3, z);
                }
            }
        }

        public final void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = SensorPrivacyService.this.mCurrentUser;
            }
            SensorPrivacyService.m856$$Nest$mforAllUsers(
                    SensorPrivacyService.this,
                    new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda3(
                            this,
                            SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i),
                            i2,
                            i3,
                            z,
                            0));
        }

        public final void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(
                int i, int i2, int i3, boolean z, int i4) {
            enforceManageSensorPrivacyPermission();
            if (z) {
                if (this.mIsConfirmPopupShowing) {
                    return;
                }
                this.mIsConfirmPopupShowing = true;
                this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new QuadConsumer() { // from class:
                                                     // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda5
                                    public final void accept(
                                            Object obj, Object obj2, Object obj3, Object obj4) {
                                        String str;
                                        String str2;
                                        Display realDisplay;
                                        final SensorPrivacyService.SensorPrivacyServiceImpl
                                                sensorPrivacyServiceImpl =
                                                        SensorPrivacyService
                                                                .SensorPrivacyServiceImpl.this;
                                        int intValue = ((Integer) obj).intValue();
                                        int intValue2 = ((Integer) obj2).intValue();
                                        int intValue3 = ((Integer) obj3).intValue();
                                        int intValue4 = ((Integer) obj4).intValue();
                                        sensorPrivacyServiceImpl.getClass();
                                        String str3 =
                                                SensorPrivacyService
                                                        .ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                                        Log.i("SensorPrivacyService", "showConfirmDialog");
                                        Context uiContext =
                                                SensorPrivacyService.this.getUiContext();
                                        if (intValue4 != -1
                                                && (realDisplay =
                                                                DisplayManagerGlobal.getInstance()
                                                                        .getRealDisplay(intValue4))
                                                        != null) {
                                            uiContext =
                                                    SensorPrivacyService.this.mContext
                                                            .createDisplayContext(realDisplay);
                                        }
                                        if (intValue3 == 2) {
                                            str =
                                                    SensorPrivacyService.this
                                                            .getUiContext()
                                                            .getString(17043255);
                                            str2 =
                                                    SensorPrivacyService.this
                                                            .getUiContext()
                                                            .getString(17043254);
                                        } else if (intValue3 == 1) {
                                            str =
                                                    SensorPrivacyService.this
                                                            .getUiContext()
                                                            .getString(17043257);
                                            str2 =
                                                    SensorPrivacyService.this
                                                            .getUiContext()
                                                            .getString(17043256);
                                        } else {
                                            str = "";
                                            str2 = "";
                                        }
                                        AlertDialog create =
                                                new AlertDialog.Builder(
                                                                new ContextThemeWrapper(
                                                                        uiContext,
                                                                        R.style
                                                                                .Theme
                                                                                .DeviceDefault
                                                                                .Settings))
                                                        .setTitle(str)
                                                        .setMessage(str2)
                                                        .setPositiveButton(
                                                                17043258,
                                                                sensorPrivacyServiceImpl
                                                                .new AnonymousClass4(
                                                                        intValue, intValue2,
                                                                        intValue3, intValue4))
                                                        .setNegativeButton(
                                                                R.string.cancel,
                                                                new SensorPrivacyService
                                                                        .SensorPrivacyServiceImpl
                                                                        .AnonymousClass3())
                                                        .setCancelable(true)
                                                        .create();
                                        create.getWindow().setType(2008);
                                        create.setCanceledOnTouchOutside(true);
                                        create.getWindow().getAttributes().privateFlags |= 16;
                                        create.setOnDismissListener(
                                                new DialogInterface
                                                        .OnDismissListener() { // from class:
                                                                               // com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.5
                                                    @Override // android.content.DialogInterface.OnDismissListener
                                                    public final void onDismiss(
                                                            DialogInterface dialogInterface) {
                                                        SensorPrivacyServiceImpl.this
                                                                        .mIsConfirmPopupShowing =
                                                                false;
                                                    }
                                                });
                                        create.show();
                                    }
                                },
                                Integer.valueOf(i),
                                Integer.valueOf(i2),
                                Integer.valueOf(i3),
                                Integer.valueOf(i4)));
                return;
            }
            if (i == -2) {
                i = SensorPrivacyService.this.mCurrentUser;
            }
            int profileParentId =
                    SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i);
            String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            profileParentId,
                            i2,
                            "setToggleSensorPrivacyForProfileGroupWithConfirmPopup parentId ",
                            " source=",
                            " sensor=");
            m.append(i3);
            m.append(" enable=");
            m.append(z);
            m.append(" displayId=");
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(m, i4, "SensorPrivacyService");
            SensorPrivacyService.m856$$Nest$mforAllUsers(
                    SensorPrivacyService.this,
                    new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda3(
                            this, profileParentId, i2, i3, z, 1));
        }

        public final void setToggleSensorPrivacyState(
                int i, final int i2, final int i3, final int i4) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = SensorPrivacyService.this.mCurrentUser;
            }
            final int i5 = i;
            if (canChangeToggleSensorPrivacy(i5, i3) && supportsSensorToggle(1, i3)) {
                final long[] jArr = new long[1];
                this.mSensorPrivacyStateController.atomic(
                        new Runnable() { // from class:
                                         // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda10
                            public final /* synthetic */ int f$1 = 1;

                            @Override // java.lang.Runnable
                            public final void run() {
                                final SensorPrivacyService.SensorPrivacyServiceImpl
                                        sensorPrivacyServiceImpl = this;
                                int i6 = this.f$1;
                                final int i7 = i5;
                                final int i8 = i3;
                                final long[] jArr2 = jArr;
                                final int i9 = i4;
                                final int i10 = i2;
                                jArr2[0] =
                                        sensorPrivacyServiceImpl.mSensorPrivacyStateController
                                                .getState(i6, i7, i8)
                                                .mLastChange;
                                SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                                        sensorPrivacyServiceImpl.mSensorPrivacyStateController;
                                SensorPrivacyService.SensorPrivacyHandler sensorPrivacyHandler =
                                        sensorPrivacyServiceImpl.mHandler;
                                SensorPrivacyStateController$SetStateResultCallback
                                        sensorPrivacyStateController$SetStateResultCallback =
                                                new SensorPrivacyStateController$SetStateResultCallback() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda14
                                                    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController$SetStateResultCallback
                                                    public final void callback(boolean z) {
                                                        SensorPrivacyService
                                                                        .SensorPrivacyServiceImpl
                                                                sensorPrivacyServiceImpl2 =
                                                                        sensorPrivacyServiceImpl;
                                                        if (!z) {
                                                            sensorPrivacyServiceImpl2.getClass();
                                                            return;
                                                        }
                                                        UserManagerInternal userManagerInternal =
                                                                SensorPrivacyService.this
                                                                        .mUserManagerInternal;
                                                        int i11 = i7;
                                                        if (i11
                                                                == userManagerInternal
                                                                        .getProfileParentId(i11)) {
                                                            sensorPrivacyServiceImpl2.mHandler
                                                                    .sendMessage(
                                                                            PooledLambda
                                                                                    .obtainMessage(
                                                                                            new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda15(
                                                                                                    0),
                                                                                            sensorPrivacyServiceImpl2,
                                                                                            Integer
                                                                                                    .valueOf(
                                                                                                            i10),
                                                                                            Integer
                                                                                                    .valueOf(
                                                                                                            i8),
                                                                                            Integer
                                                                                                    .valueOf(
                                                                                                            i9),
                                                                                            Long
                                                                                                    .valueOf(
                                                                                                            jArr2[
                                                                                                                    0]),
                                                                                            Boolean
                                                                                                    .FALSE));
                                                        }
                                                    }
                                                };
                                synchronized (sensorPrivacyStateControllerImpl.mLock) {
                                    PersistedState persistedState =
                                            sensorPrivacyStateControllerImpl.mPersistedState;
                                    SensorState sensorState =
                                            (SensorState)
                                                    persistedState.mStates.get(
                                                            new PersistedState.TypeUserSensor(
                                                                    i6, i7, i8));
                                    if (sensorState == null) {
                                        if (i9 == 2) {
                                            SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                                    sensorPrivacyHandler,
                                                    sensorPrivacyStateController$SetStateResultCallback,
                                                    false);
                                        } else {
                                            SensorState sensorState2 = new SensorState(i9);
                                            sensorPrivacyStateControllerImpl
                                                    .notifyStateChangeLocked(
                                                            i6, i7, i8, sensorState2);
                                            SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                                    sensorPrivacyHandler,
                                                    sensorPrivacyStateController$SetStateResultCallback,
                                                    true);
                                        }
                                    } else if (sensorState.mStateType != i9) {
                                        sensorState.mStateType = i9;
                                        String str =
                                                SensorPrivacyService
                                                        .ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                                        sensorState.mLastChange = SystemClock.elapsedRealtime();
                                        sensorPrivacyStateControllerImpl.notifyStateChangeLocked(
                                                i6, i7, i8, sensorState);
                                        SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                                sensorPrivacyHandler,
                                                sensorPrivacyStateController$SetStateResultCallback,
                                                true);
                                    } else {
                                        SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                                sensorPrivacyHandler,
                                                sensorPrivacyStateController$SetStateResultCallback,
                                                false);
                                    }
                                }
                            }
                        });
            }
        }

        public final void setToggleSensorPrivacyStateForProfileGroup(
                int i, final int i2, final int i3, final int i4) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = SensorPrivacyService.this.mCurrentUser;
            }
            final int profileParentId =
                    SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i);
            SensorPrivacyService.m856$$Nest$mforAllUsers(
                    SensorPrivacyService.this,
                    new FunctionalUtils
                            .ThrowingConsumer() { // from class:
                                                  // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda4
                        public final void acceptOrThrow(Object obj) {
                            SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                                    SensorPrivacyService.SensorPrivacyServiceImpl.this;
                            int i5 = profileParentId;
                            int i6 = i2;
                            int i7 = i3;
                            int i8 = i4;
                            Integer num = (Integer) obj;
                            if (i5
                                    == SensorPrivacyService.this.mUserManagerInternal
                                            .getProfileParentId(num.intValue())) {
                                sensorPrivacyServiceImpl.setToggleSensorPrivacyState(
                                        num.intValue(), i6, i7, i8);
                            }
                        }
                    });
        }

        public final void setToggleSensorPrivacyUnchecked(
                final int i, final int i2, final int i3, final int i4, final boolean z) {
            final long[] jArr = new long[1];
            this.mSensorPrivacyStateController.atomic(
                    new Runnable() { // from class:
                                     // com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final SensorPrivacyService.SensorPrivacyServiceImpl
                                    sensorPrivacyServiceImpl =
                                            SensorPrivacyService.SensorPrivacyServiceImpl.this;
                            int i5 = i;
                            final int i6 = i2;
                            final int i7 = i4;
                            final long[] jArr2 = jArr;
                            final boolean z2 = z;
                            final int i8 = i3;
                            jArr2[0] =
                                    sensorPrivacyServiceImpl.mSensorPrivacyStateController.getState(
                                                    i5, i6, i7)
                                            .mLastChange;
                            SensorPrivacyStateControllerImpl sensorPrivacyStateControllerImpl =
                                    sensorPrivacyServiceImpl.mSensorPrivacyStateController;
                            SensorPrivacyService.SensorPrivacyHandler sensorPrivacyHandler =
                                    sensorPrivacyServiceImpl.mHandler;
                            SensorPrivacyStateController$SetStateResultCallback
                                    sensorPrivacyStateController$SetStateResultCallback =
                                            new SensorPrivacyStateController$SetStateResultCallback() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda7
                                                @Override // com.android.server.sensorprivacy.SensorPrivacyStateController$SetStateResultCallback
                                                public final void callback(boolean z3) {
                                                    SensorPrivacyService.SensorPrivacyServiceImpl
                                                            sensorPrivacyServiceImpl2 =
                                                                    SensorPrivacyService
                                                                            .SensorPrivacyServiceImpl
                                                                            .this;
                                                    if (!z3) {
                                                        sensorPrivacyServiceImpl2.getClass();
                                                        return;
                                                    }
                                                    UserManagerInternal userManagerInternal =
                                                            SensorPrivacyService.this
                                                                    .mUserManagerInternal;
                                                    int i9 = i6;
                                                    if (i9
                                                            == userManagerInternal
                                                                    .getProfileParentId(i9)) {
                                                        sensorPrivacyServiceImpl2.mHandler
                                                                .sendMessage(
                                                                        PooledLambda.obtainMessage(
                                                                                new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda15(
                                                                                        1),
                                                                                sensorPrivacyServiceImpl2,
                                                                                Integer.valueOf(i8),
                                                                                Integer.valueOf(i7),
                                                                                Boolean.valueOf(z2),
                                                                                Long.valueOf(
                                                                                        jArr2[0]),
                                                                                Boolean.FALSE));
                                                    }
                                                }
                                            };
                            synchronized (sensorPrivacyStateControllerImpl.mLock) {
                                PersistedState persistedState =
                                        sensorPrivacyStateControllerImpl.mPersistedState;
                                SensorState sensorState =
                                        (SensorState)
                                                persistedState.mStates.get(
                                                        new PersistedState.TypeUserSensor(
                                                                i5, i6, i7));
                                if (sensorState == null) {
                                    if (!z2) {
                                        SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                                sensorPrivacyHandler,
                                                sensorPrivacyStateController$SetStateResultCallback,
                                                false);
                                    } else if (z2) {
                                        SensorState sensorState2 = new SensorState(true);
                                        sensorPrivacyStateControllerImpl.notifyStateChangeLocked(
                                                i5, i6, i7, sensorState2);
                                        SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                                sensorPrivacyHandler,
                                                sensorPrivacyStateController$SetStateResultCallback,
                                                true);
                                    }
                                }
                                int i9 = z2 ? 1 : 2;
                                if (sensorState.mStateType != i9) {
                                    sensorState.mStateType = i9;
                                    String str =
                                            SensorPrivacyService
                                                    .ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                                    sensorState.mLastChange = SystemClock.elapsedRealtime();
                                    sensorPrivacyStateControllerImpl.notifyStateChangeLocked(
                                            i5, i6, i7, sensorState);
                                    SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                            sensorPrivacyHandler,
                                            sensorPrivacyStateController$SetStateResultCallback,
                                            true);
                                } else {
                                    SensorPrivacyStateControllerImpl.sendSetStateCallback(
                                            sensorPrivacyHandler,
                                            sensorPrivacyStateController$SetStateResultCallback,
                                            false);
                                }
                            }
                        }
                    });
            if (z) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SensorPrivacyService.this.mNotificationManager.cancel(i4 == 1 ? 65 : 66);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void showSensorUseDialog(int i) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Can only be called by the system uid");
            }
            if (isCombinedToggleSensorPrivacyEnabled(i)) {
                String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "showSensorUseDialog  sensor=", "  pid=");
                m.append(Binder.getCallingPid());
                Slog.d("SensorPrivacyService", m.toString());
                enqueueSensorUseReminderDialogAsync(
                        -1, i, UserHandle.of(SensorPrivacyService.this.mCurrentUser), "android");
            }
        }

        public final void showSensorUseReminderNotification(
                int i, UserHandle userHandle, String str) {
            int i2;
            int i3;
            int i4;
            int i5;
            try {
                CharSequence loadLabel =
                        SensorPrivacyService.this
                                .getUiContext()
                                .getPackageManager()
                                .getApplicationInfoAsUser(str, 0, userHandle)
                                .loadLabel(SensorPrivacyService.this.mContext.getPackageManager());
                if (i == 1) {
                    i2 = R.drawable.ic_popup_sync_2;
                    i3 = 17042978;
                    i4 = 17042773;
                    i5 = 65;
                } else {
                    i2 = R.drawable.ic_corp_badge;
                    i3 = 17042976;
                    i4 = 17042772;
                    i5 = 66;
                }
                NotificationChannel notificationChannel =
                        new NotificationChannel(
                                "sensor_privacy",
                                SensorPrivacyService.this.getUiContext().getString(17042975),
                                4);
                notificationChannel.setSound(null, null);
                notificationChannel.setBypassDnd(true);
                notificationChannel.enableVibration(false);
                notificationChannel.setBlockable(false);
                SensorPrivacyService.this.mNotificationManager.createNotificationChannel(
                        notificationChannel);
                Icon createWithResource =
                        Icon.createWithResource(
                                SensorPrivacyService.this.getUiContext().getResources(), i2);
                String string = SensorPrivacyService.this.getUiContext().getString(i3);
                Spanned fromHtml =
                        Html.fromHtml(
                                SensorPrivacyService.this.getUiContext().getString(i4, loadLabel),
                                0);
                SensorPrivacyService.this.mNotificationManager.notify(
                        i5,
                        new Notification.Builder(
                                        SensorPrivacyService.this.mContext, "sensor_privacy")
                                .setContentTitle(string)
                                .setContentText(fromHtml)
                                .setSmallIcon(createWithResource)
                                .addAction(
                                        new Notification.Action.Builder(
                                                        createWithResource,
                                                        SensorPrivacyService.this
                                                                .getUiContext()
                                                                .getString(17042977),
                                                        PendingIntent.getBroadcast(
                                                                SensorPrivacyService.this.mContext,
                                                                i,
                                                                new Intent(
                                                                                SensorPrivacyService
                                                                                        .ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY)
                                                                        .setPackage(
                                                                                SensorPrivacyService
                                                                                        .this
                                                                                        .mContext
                                                                                        .getPackageName())
                                                                        .putExtra(
                                                                                SensorPrivacyManager
                                                                                        .EXTRA_SENSOR,
                                                                                i)
                                                                        .putExtra(
                                                                                SensorPrivacyManager
                                                                                        .EXTRA_NOTIFICATION_ID,
                                                                                i5)
                                                                        .putExtra(
                                                                                "android.intent.extra.USER",
                                                                                userHandle),
                                                                201326592))
                                                .build())
                                .setContentIntent(
                                        PendingIntent.getActivity(
                                                SensorPrivacyService.this.mContext,
                                                i,
                                                new Intent(
                                                        ((SafetyCenterManager)
                                                                                SensorPrivacyService
                                                                                        .this
                                                                                        .mContext
                                                                                        .getSystemService(
                                                                                                SafetyCenterManager
                                                                                                        .class))
                                                                        .isSafetyCenterEnabled()
                                                                ? "android.settings.PRIVACY_CONTROLS"
                                                                : "android.settings.PRIVACY_SETTINGS"),
                                                201326592))
                                .extend(new Notification.TvExtender())
                                .setTimeoutAfter(
                                        (SensorPrivacyService.this
                                                                        .mContext
                                                                        .getResources()
                                                                        .getConfiguration()
                                                                        .uiMode
                                                                & 15)
                                                        == 4
                                                ? 1L
                                                : 0L)
                                .setAutoCancel(true)
                                .build());
            } catch (PackageManager.NameNotFoundException unused) {
                String str2 = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        "Cannot show sensor use notification for ", str, "SensorPrivacyService");
            }
        }

        public final boolean supportsSensorToggle(int i, int i2) {
            if (i == 1) {
                if (i2 == 1) {
                    return SensorPrivacyService.this
                            .mContext
                            .getResources()
                            .getBoolean(R.bool.config_tintNotificationActionButtons);
                }
                if (i2 == 2) {
                    return SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_PRIVACY_TOGGLE");
                }
            } else if (i == 2) {
                if (i2 == 1) {
                    return SensorPrivacyService.this
                            .mContext
                            .getResources()
                            .getBoolean(R.bool.config_telephony5gStandalone);
                }
                if (i2 == 2) {
                    return SensorPrivacyService.this
                            .mContext
                            .getResources()
                            .getBoolean(R.bool.config_telephony5gNonStandalone);
                }
            }
            throw new IllegalArgumentException(
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "Invalid arguments. toggleType=", " sensor="));
        }

        public final void suppressToggleSensorPrivacyReminders(
                int i, int i2, IBinder iBinder, boolean z) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = SensorPrivacyService.this.mCurrentUser;
            }
            Objects.requireNonNull(iBinder);
            Pair pair = new Pair(Integer.valueOf(i2), UserHandle.of(i));
            synchronized (this.mLock) {
                try {
                    if (z) {
                        try {
                            iBinder.linkToDeath(this, 0);
                            ArrayList arrayList = (ArrayList) this.mSuppressReminders.get(pair);
                            if (arrayList == null) {
                                arrayList = new ArrayList(1);
                                this.mSuppressReminders.put(pair, arrayList);
                            }
                            arrayList.add(iBinder);
                        } catch (RemoteException e) {
                            String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                            Log.e(
                                    "SensorPrivacyService",
                                    "Could not suppress sensor use reminder",
                                    e);
                        }
                    } else {
                        SensorPrivacyHandler sensorPrivacyHandler = this.mHandler;
                        sensorPrivacyHandler.getClass();
                        sensorPrivacyHandler.sendMessage(
                                PooledLambda.obtainMessage(
                                        new SensorPrivacyService$SensorPrivacyHandler$$ExternalSyntheticLambda0(),
                                        SensorPrivacyService.this.mSensorPrivacyServiceImpl,
                                        pair,
                                        iBinder));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mforAllUsers, reason: not valid java name */
    public static void m856$$Nest$mforAllUsers(
            SensorPrivacyService sensorPrivacyService,
            FunctionalUtils.ThrowingConsumer throwingConsumer) {
        for (int i : sensorPrivacyService.mUserManagerInternal.getUserIds()) {
            throwingConsumer.accept(Integer.valueOf(i));
        }
    }

    public SensorPrivacyService(Context context) {
        super(context);
        this.mAppOpsRestrictionToken = new Binder();
        this.mCameraPrivacyAllowlist = new ArrayList();
        this.mCurrentUser = -10000;
        this.mContext = context;
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAppOpsManagerInternal =
                (AppOpsManagerInternal) getLocalService(AppOpsManagerInternal.class);
        this.mUserManagerInternal =
                (UserManagerInternal) getLocalService(UserManagerInternal.class);
        this.mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        this.mActivityManagerInternal =
                (ActivityManagerInternal) getLocalService(ActivityManagerInternal.class);
        this.mActivityTaskManager =
                (ActivityTaskManager) context.getSystemService(ActivityTaskManager.class);
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mPackageManagerInternal =
                (PackageManagerInternal) getLocalService(PackageManagerInternal.class);
        this.mNotificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mSensorPrivacyServiceImpl = new SensorPrivacyServiceImpl();
        Iterator it = SystemConfig.getInstance().mAllowlistCameraPrivacy.iterator();
        while (it.hasNext()) {
            this.mCameraPrivacyAllowlist.add((String) it.next());
        }
        this.mIsFlipModel =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        this.mIsLargeCoverScreen =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i != 500) {
            if (i == 550) {
                new CameraPrivacyLightController(this.mContext, FgThread.get().getLooper());
                return;
            }
            return;
        }
        this.mKeyguardManager =
                (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        DeviceStateManager deviceStateManager =
                (DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class);
        if (this.mIsLargeCoverScreen && deviceStateManager != null) {
            deviceStateManager.registerCallback(
                    FgThread.getExecutor(),
                    new DeviceStateManager.FoldStateListener(
                            this.mContext,
                            new Consumer() { // from class:
                                             // com.android.server.sensorprivacy.SensorPrivacyService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    SensorPrivacyService sensorPrivacyService =
                                            SensorPrivacyService.this;
                                    sensorPrivacyService.getClass();
                                    sensorPrivacyService.mIsFolded = ((Boolean) obj).booleanValue();
                                }
                            }));
        }
        this.mCallStateHelper = new CallStateHelper();
        final SensorPrivacyServiceImpl sensorPrivacyServiceImpl = this.mSensorPrivacyServiceImpl;
        SensorPrivacyService.this
                .mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor(
                                "receive_explicit_user_interaction_audio_enabled"),
                        false,
                        new ContentObserver(
                                sensorPrivacyServiceImpl
                                        .mHandler) { // from class:
                                                     // com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.6
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                SensorPrivacyServiceImpl sensorPrivacyServiceImpl2 =
                                        SensorPrivacyServiceImpl.this;
                                sensorPrivacyServiceImpl2.setGlobalRestriction(
                                        1,
                                        sensorPrivacyServiceImpl2
                                                .isCombinedToggleSensorPrivacyEnabled(1));
                            }
                        });
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("sensor_privacy", this.mSensorPrivacyServiceImpl);
        SensorPrivacyManagerInternalImpl sensorPrivacyManagerInternalImpl =
                new SensorPrivacyManagerInternalImpl();
        this.mSensorPrivacyManagerInternal = sensorPrivacyManagerInternalImpl;
        publishLocalService(SensorPrivacyManagerInternal.class, sensorPrivacyManagerInternalImpl);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == -10000) {
            this.mCurrentUser = targetUser.getUserIdentifier();
            SensorPrivacyServiceImpl.m858$$Nest$muserSwitching(
                    this.mSensorPrivacyServiceImpl, -10000, targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        this.mCurrentUser = targetUser2.getUserIdentifier();
        SensorPrivacyServiceImpl.m858$$Nest$muserSwitching(
                this.mSensorPrivacyServiceImpl,
                targetUser.getUserIdentifier(),
                targetUser2.getUserIdentifier());
    }
}
