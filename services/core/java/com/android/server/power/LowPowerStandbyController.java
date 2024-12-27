package com.android.server.power;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PowerAllowlistInternal;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda0;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LowPowerStandbyController {
    static final PowerManager.LowPowerStandbyPolicy DEFAULT_POLICY =
            new PowerManager.LowPowerStandbyPolicy(
                    "DEFAULT_POLICY", Collections.emptySet(), 1, Collections.emptySet());
    public boolean mActiveDuringMaintenance;
    public final Supplier mActivityManager;
    public ActivityManagerInternal mActivityManagerInternal;
    public AlarmManager mAlarmManager;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Clock mClock;
    public final Context mContext;
    public final DeviceConfigWrapper mDeviceConfig;
    public boolean mEnableCustomPolicy;
    public boolean mEnableStandbyPorts;
    public boolean mEnabledByDefaultConfig;
    public boolean mForceActive;
    public final LowPowerStandbyHandler mHandler;
    public boolean mIdleSinceNonInteractive;
    public boolean mIsActive;
    public boolean mIsDeviceIdle;
    public boolean mIsEnabled;
    public boolean mIsInteractive;
    public long mLastInteractiveTimeElapsed;
    public final AnonymousClass1 mPackageBroadcastReceiver;
    public PowerManager.LowPowerStandbyPolicy mPolicy;
    public final File mPolicyFile;
    public PowerManager mPowerManager;
    public final SettingsObserver mSettingsObserver;
    public int mStandbyTimeoutConfig;
    public boolean mSupportedConfig;
    public final AnonymousClass1 mUserReceiver;
    public final Object mLock = new Object();
    public final LowPowerStandbyController$$ExternalSyntheticLambda1 mOnStandbyTimeoutExpired =
            new AlarmManager
                    .OnAlarmListener() { // from class:
                                         // com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda1
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    LowPowerStandbyController lowPowerStandbyController =
                            LowPowerStandbyController.this;
                    synchronized (lowPowerStandbyController.mLock) {
                        lowPowerStandbyController.updateActiveLocked();
                    }
                }
            };
    public final LocalService mLocalService = new LocalService();
    public final SparseIntArray mUidAllowedReasons = new SparseIntArray();
    public final List mLowPowerStandbyManagingPackages = new ArrayList();
    public final List mStandbyPortLocks = new ArrayList();
    public final TempAllowlistChangeListener mTempAllowlistChangeListener =
            new TempAllowlistChangeListener();
    public final PhoneCallServiceTracker mPhoneCallServiceTracker = new PhoneCallServiceTracker();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Clock {
        long elapsedRealtime();

        long uptimeMillis();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class DeviceConfigWrapper {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LowPowerStandbyHandler extends Handler {
        public LowPowerStandbyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                LowPowerStandbyController lowPowerStandbyController =
                        LowPowerStandbyController.this;
                synchronized (lowPowerStandbyController.mLock) {
                    lowPowerStandbyController.updateActiveLocked();
                }
                return;
            }
            if (i == 1) {
                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                LowPowerStandbyController.this.getClass();
                PowerManagerInternal powerManagerInternal =
                        (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
                NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl
                        networkPolicyManagerInternalImpl =
                                (NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl)
                                        LocalServices.getService(
                                                NetworkPolicyManagerService
                                                        .NetworkPolicyManagerInternalImpl.class);
                powerManagerInternal.setLowPowerStandbyActive(booleanValue);
                networkPolicyManagerInternalImpl.getClass();
                Trace.traceBegin(2097152L, "setLowPowerStandbyActive");
                try {
                    synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                        try {
                            if (NetworkPolicyManagerService.this.mLowPowerStandbyActive
                                    != booleanValue) {
                                NetworkPolicyManagerService.this.mLowPowerStandbyActive =
                                        booleanValue;
                                synchronized (
                                        NetworkPolicyManagerService.this
                                                .mNetworkPoliciesSecondLock) {
                                    if (NetworkPolicyManagerService.this.mSystemReady) {
                                        NetworkPolicyManagerService.this.forEachUid(
                                                "updateRulesForRestrictPower",
                                                new NetworkPolicyManagerService$$ExternalSyntheticLambda0(
                                                        4, networkPolicyManagerInternalImpl));
                                        NetworkPolicyManagerService.this
                                                .updateRulesForLowPowerStandbyUL();
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                } finally {
                    Trace.traceEnd(2097152L);
                }
            }
            if (i != 2) {
                if (i == 3) {
                    LowPowerStandbyController lowPowerStandbyController2 =
                            LowPowerStandbyController.this;
                    lowPowerStandbyController2.sendExplicitBroadcast(
                            "android.os.action.LOW_POWER_STANDBY_POLICY_CHANGED");
                    return;
                }
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    LowPowerStandbyController lowPowerStandbyController3 =
                            LowPowerStandbyController.this;
                    lowPowerStandbyController3.getClass();
                    Intent intent = new Intent("android.os.action.LOW_POWER_STANDBY_PORTS_CHANGED");
                    intent.addFlags(268435456);
                    lowPowerStandbyController3.mContext.sendBroadcastAsUser(
                            intent, UserHandle.ALL, "android.permission.MANAGE_LOW_POWER_STANDBY");
                    return;
                }
                int i2 = message.arg1;
                PhoneCallServiceTracker phoneCallServiceTracker =
                        LowPowerStandbyController.this.mPhoneCallServiceTracker;
                boolean z = phoneCallServiceTracker.mUidsWithPhoneCallService.get(i2);
                boolean hasRunningForegroundService =
                        LowPowerStandbyController.this.mActivityManagerInternal
                                .hasRunningForegroundService(i2, 4);
                if (hasRunningForegroundService == z) {
                    return;
                }
                if (hasRunningForegroundService) {
                    phoneCallServiceTracker.mUidsWithPhoneCallService.append(i2, true);
                    LowPowerStandbyController.m792$$Nest$maddToAllowlistInternal(
                            LowPowerStandbyController.this, i2, 4);
                    return;
                } else {
                    phoneCallServiceTracker.mUidsWithPhoneCallService.delete(i2);
                    LowPowerStandbyController.m793$$Nest$mremoveFromAllowlistInternal(
                            LowPowerStandbyController.this, i2, 4);
                    return;
                }
            }
            int[] iArr = (int[]) message.obj;
            LowPowerStandbyController.this.getClass();
            PowerManagerInternal powerManagerInternal2 =
                    (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
            NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl
                    networkPolicyManagerInternalImpl2 =
                            (NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl)
                                    LocalServices.getService(
                                            NetworkPolicyManagerService
                                                    .NetworkPolicyManagerInternalImpl.class);
            powerManagerInternal2.setLowPowerStandbyAllowlist(iArr);
            synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                try {
                    SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                    for (int i3 = 0;
                            i3
                                    < NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids
                                            .size();
                            i3++) {
                        int keyAt =
                                NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids
                                        .keyAt(i3);
                        if (!ArrayUtils.contains(iArr, keyAt)) {
                            sparseBooleanArray.put(keyAt, true);
                        }
                    }
                    for (int i4 = 0; i4 < sparseBooleanArray.size(); i4++) {
                        NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.delete(
                                sparseBooleanArray.keyAt(i4));
                    }
                    for (int i5 : iArr) {
                        if (NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids
                                        .indexOfKey(i5)
                                < 0) {
                            sparseBooleanArray.append(i5, true);
                            NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.append(
                                    i5, true);
                        }
                    }
                    if (NetworkPolicyManagerService.this.mLowPowerStandbyActive) {
                        synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            if (NetworkPolicyManagerService.this.mSystemReady) {
                                for (int i6 = 0; i6 < sparseBooleanArray.size(); i6++) {
                                    int keyAt2 = sparseBooleanArray.keyAt(i6);
                                    NetworkPolicyManagerService.this
                                            .updateRulesForPowerRestrictionsUL(keyAt2, -1);
                                    NetworkPolicyManagerService.this.updateRuleForLowPowerStandbyUL(
                                            keyAt2);
                                }
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhoneCallServiceTracker extends IForegroundServiceObserver.Stub {
        public boolean mRegistered = false;
        public final SparseBooleanArray mUidsWithPhoneCallService = new SparseBooleanArray();

        public PhoneCallServiceTracker() {}

        public final void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
            try {
                Message obtainMessage =
                        LowPowerStandbyController.this.mHandler.obtainMessage(
                                4,
                                LowPowerStandbyController.this
                                        .mContext
                                        .getPackageManager()
                                        .getPackageUidAsUser(str, i),
                                0);
                LowPowerStandbyController lowPowerStandbyController =
                        LowPowerStandbyController.this;
                lowPowerStandbyController.mHandler.sendMessageAtTime(
                        obtainMessage, lowPowerStandbyController.mClock.uptimeMillis());
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RealClock implements Clock {
        @Override // com.android.server.power.LowPowerStandbyController.Clock
        public final long elapsedRealtime() {
            return SystemClock.elapsedRealtime();
        }

        @Override // com.android.server.power.LowPowerStandbyController.Clock
        public final long uptimeMillis() {
            return SystemClock.uptimeMillis();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(LowPowerStandbyHandler lowPowerStandbyHandler) {
            super(lowPowerStandbyHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            LowPowerStandbyController.this.onSettingsChanged();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StandbyPortsLock implements IBinder.DeathRecipient {
        public final List mPorts;
        public final IBinder mToken;
        public final int mUid;

        public StandbyPortsLock(IBinder iBinder, int i, List list) {
            this.mToken = iBinder;
            this.mUid = i;
            this.mPorts = list;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            LowPowerStandbyController.this.releaseStandbyPorts(this.mToken);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TempAllowlistChangeListener
            implements PowerAllowlistInternal.TempAllowlistChangeListener {
        public TempAllowlistChangeListener() {}

        public final void onAppAdded(int i) {
            LowPowerStandbyController.m792$$Nest$maddToAllowlistInternal(
                    LowPowerStandbyController.this, i, 2);
        }

        public final void onAppRemoved(int i) {
            LowPowerStandbyController.m793$$Nest$mremoveFromAllowlistInternal(
                    LowPowerStandbyController.this, i, 2);
        }
    }

    /* renamed from: -$$Nest$maddToAllowlistInternal, reason: not valid java name */
    public static void m792$$Nest$maddToAllowlistInternal(
            LowPowerStandbyController lowPowerStandbyController, int i, int i2) {
        synchronized (lowPowerStandbyController.mLock) {
            try {
                if (lowPowerStandbyController.mSupportedConfig) {
                    if (i2 != 0
                            && (lowPowerStandbyController.mUidAllowedReasons.get(i) & i2) == 0) {
                        lowPowerStandbyController.mUidAllowedReasons.put(
                                i, lowPowerStandbyController.mUidAllowedReasons.get(i) | i2);
                        if ((lowPowerStandbyController.getPolicy().getAllowedReasons() & i2) != 0) {
                            lowPowerStandbyController.enqueueNotifyAllowlistChangedLocked();
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mremoveFromAllowlistInternal, reason: not valid java name */
    public static void m793$$Nest$mremoveFromAllowlistInternal(
            LowPowerStandbyController lowPowerStandbyController, int i, int i2) {
        synchronized (lowPowerStandbyController.mLock) {
            try {
                if (lowPowerStandbyController.mSupportedConfig) {
                    if (i2 != 0
                            && (lowPowerStandbyController.mUidAllowedReasons.get(i) & i2) != 0) {
                        int i3 = lowPowerStandbyController.mUidAllowedReasons.get(i);
                        if (i3 != 0) {
                            int i4 = i3 & (~i2);
                            if (i4 == 0) {
                                SparseIntArray sparseIntArray =
                                        lowPowerStandbyController.mUidAllowedReasons;
                                sparseIntArray.removeAt(sparseIntArray.indexOfKey(i));
                            } else {
                                lowPowerStandbyController.mUidAllowedReasons.put(i, i4);
                            }
                        }
                        if ((lowPowerStandbyController.getPolicy().getAllowedReasons() & i2) != 0) {
                            lowPowerStandbyController.enqueueNotifyAllowlistChangedLocked();
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.power.LowPowerStandbyController$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.power.LowPowerStandbyController$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.power.LowPowerStandbyController$1] */
    public LowPowerStandbyController(
            Context context,
            Looper looper,
            Clock clock,
            DeviceConfigWrapper deviceConfigWrapper,
            Supplier supplier,
            File file) {
        final int i = 0;
        this.mBroadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.power.LowPowerStandbyController.1
                    public final /* synthetic */ LowPowerStandbyController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        boolean z = true;
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                action.getClass();
                                switch (action) {
                                    case "android.intent.action.SCREEN_OFF":
                                        this.this$0.onNonInteractive();
                                        return;
                                    case "android.intent.action.SCREEN_ON":
                                        this.this$0.onInteractive();
                                        return;
                                    case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                                        LowPowerStandbyController lowPowerStandbyController =
                                                this.this$0;
                                        synchronized (lowPowerStandbyController.mLock) {
                                            boolean isDeviceIdleMode =
                                                    lowPowerStandbyController.mPowerManager
                                                            .isDeviceIdleMode();
                                            lowPowerStandbyController.mIsDeviceIdle =
                                                    isDeviceIdleMode;
                                            if (!lowPowerStandbyController.mIdleSinceNonInteractive
                                                    && !isDeviceIdleMode) {
                                                z = false;
                                            }
                                            lowPowerStandbyController.mIdleSinceNonInteractive = z;
                                            lowPowerStandbyController.updateActiveLocked();
                                        }
                                        return;
                                    default:
                                        return;
                                }
                            case 1:
                                if (intent.getBooleanExtra(
                                        "android.intent.extra.REPLACING", false)) {
                                    return;
                                }
                                Uri data = intent.getData();
                                String schemeSpecificPart =
                                        data != null ? data.getSchemeSpecificPart() : null;
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (this.this$0
                                                .getPolicy()
                                                .getExemptPackages()
                                                .contains(schemeSpecificPart)) {
                                            this.this$0.enqueueNotifyAllowlistChangedLocked();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    this.this$0.enqueueNotifyAllowlistChangedLocked();
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        this.mPackageBroadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.power.LowPowerStandbyController.1
                    public final /* synthetic */ LowPowerStandbyController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        boolean z = true;
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                action.getClass();
                                switch (action) {
                                    case "android.intent.action.SCREEN_OFF":
                                        this.this$0.onNonInteractive();
                                        return;
                                    case "android.intent.action.SCREEN_ON":
                                        this.this$0.onInteractive();
                                        return;
                                    case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                                        LowPowerStandbyController lowPowerStandbyController =
                                                this.this$0;
                                        synchronized (lowPowerStandbyController.mLock) {
                                            boolean isDeviceIdleMode =
                                                    lowPowerStandbyController.mPowerManager
                                                            .isDeviceIdleMode();
                                            lowPowerStandbyController.mIsDeviceIdle =
                                                    isDeviceIdleMode;
                                            if (!lowPowerStandbyController.mIdleSinceNonInteractive
                                                    && !isDeviceIdleMode) {
                                                z = false;
                                            }
                                            lowPowerStandbyController.mIdleSinceNonInteractive = z;
                                            lowPowerStandbyController.updateActiveLocked();
                                        }
                                        return;
                                    default:
                                        return;
                                }
                            case 1:
                                if (intent.getBooleanExtra(
                                        "android.intent.extra.REPLACING", false)) {
                                    return;
                                }
                                Uri data = intent.getData();
                                String schemeSpecificPart =
                                        data != null ? data.getSchemeSpecificPart() : null;
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (this.this$0
                                                .getPolicy()
                                                .getExemptPackages()
                                                .contains(schemeSpecificPart)) {
                                            this.this$0.enqueueNotifyAllowlistChangedLocked();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    this.this$0.enqueueNotifyAllowlistChangedLocked();
                                }
                                return;
                        }
                    }
                };
        final int i3 = 2;
        this.mUserReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.power.LowPowerStandbyController.1
                    public final /* synthetic */ LowPowerStandbyController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        boolean z = true;
                        switch (i3) {
                            case 0:
                                String action = intent.getAction();
                                action.getClass();
                                switch (action) {
                                    case "android.intent.action.SCREEN_OFF":
                                        this.this$0.onNonInteractive();
                                        return;
                                    case "android.intent.action.SCREEN_ON":
                                        this.this$0.onInteractive();
                                        return;
                                    case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                                        LowPowerStandbyController lowPowerStandbyController =
                                                this.this$0;
                                        synchronized (lowPowerStandbyController.mLock) {
                                            boolean isDeviceIdleMode =
                                                    lowPowerStandbyController.mPowerManager
                                                            .isDeviceIdleMode();
                                            lowPowerStandbyController.mIsDeviceIdle =
                                                    isDeviceIdleMode;
                                            if (!lowPowerStandbyController.mIdleSinceNonInteractive
                                                    && !isDeviceIdleMode) {
                                                z = false;
                                            }
                                            lowPowerStandbyController.mIdleSinceNonInteractive = z;
                                            lowPowerStandbyController.updateActiveLocked();
                                        }
                                        return;
                                    default:
                                        return;
                                }
                            case 1:
                                if (intent.getBooleanExtra(
                                        "android.intent.extra.REPLACING", false)) {
                                    return;
                                }
                                Uri data = intent.getData();
                                String schemeSpecificPart =
                                        data != null ? data.getSchemeSpecificPart() : null;
                                synchronized (this.this$0.mLock) {
                                    try {
                                        if (this.this$0
                                                .getPolicy()
                                                .getExemptPackages()
                                                .contains(schemeSpecificPart)) {
                                            this.this$0.enqueueNotifyAllowlistChangedLocked();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    this.this$0.enqueueNotifyAllowlistChangedLocked();
                                }
                                return;
                        }
                    }
                };
        this.mContext = context;
        LowPowerStandbyHandler lowPowerStandbyHandler = new LowPowerStandbyHandler(looper);
        this.mHandler = lowPowerStandbyHandler;
        this.mClock = clock;
        this.mSettingsObserver = new SettingsObserver(lowPowerStandbyHandler);
        this.mDeviceConfig = deviceConfigWrapper;
        this.mActivityManager = supplier;
        this.mPolicyFile = file;
    }

    public static void writeTagValue(
            TypedXmlSerializer typedXmlSerializer, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.attribute((String) null, "value", str2);
        typedXmlSerializer.endTag((String) null, str);
    }

    public final void acquireStandbyPorts(IBinder iBinder, List list, int i) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int portNumber =
                    ((PowerManager.LowPowerStandbyPortDescription) it.next()).getPortNumber();
            if (portNumber < 0 || portNumber > 65535) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                portNumber, "port out of range:"));
            }
        }
        StandbyPortsLock standbyPortsLock = new StandbyPortsLock(iBinder, i, list);
        synchronized (this.mLock) {
            if (findIndexOfStandbyPorts(iBinder) != -1) {
                return;
            }
            try {
                iBinder.linkToDeath(standbyPortsLock, 0);
                ((ArrayList) this.mStandbyPortLocks).add(standbyPortsLock);
                if (this.mEnableStandbyPorts && isEnabled() && isPackageExempt(i)) {
                    LowPowerStandbyHandler lowPowerStandbyHandler = this.mHandler;
                    lowPowerStandbyHandler.sendMessageAtTime(
                            lowPowerStandbyHandler.obtainMessage(5), this.mClock.uptimeMillis());
                }
            } catch (RemoteException unused) {
                android.util.Slog.i("LowPowerStandbyController", "StandbyPorts token already died");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00a5 A[Catch: all -> 0x0060, TryCatch #0 {all -> 0x0060, blocks: (B:4:0x0015, B:6:0x005b, B:9:0x008f, B:11:0x00a5, B:12:0x00f6, B:13:0x0102, B:15:0x010a, B:17:0x0112, B:19:0x012d, B:22:0x0130, B:24:0x013f, B:25:0x014e, B:27:0x0154, B:29:0x0162, B:30:0x0165, B:35:0x0063), top: B:3:0x0015 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x010a A[Catch: all -> 0x0060, TryCatch #0 {all -> 0x0060, blocks: (B:4:0x0015, B:6:0x005b, B:9:0x008f, B:11:0x00a5, B:12:0x00f6, B:13:0x0102, B:15:0x010a, B:17:0x0112, B:19:0x012d, B:22:0x0130, B:24:0x013f, B:25:0x014e, B:27:0x0154, B:29:0x0162, B:30:0x0165, B:35:0x0063), top: B:3:0x0015 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x013f A[Catch: all -> 0x0060, TryCatch #0 {all -> 0x0060, blocks: (B:4:0x0015, B:6:0x005b, B:9:0x008f, B:11:0x00a5, B:12:0x00f6, B:13:0x0102, B:15:0x010a, B:17:0x0112, B:19:0x012d, B:22:0x0130, B:24:0x013f, B:25:0x014e, B:27:0x0154, B:29:0x0162, B:30:0x0165, B:35:0x0063), top: B:3:0x0015 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r5) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.LowPowerStandbyController.dump(java.io.PrintWriter):void");
    }

    public final void dumpProto(ProtoOutputStream protoOutputStream) {
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(1146756268088L);
                protoOutputStream.write(1133871366145L, this.mIsActive);
                protoOutputStream.write(1133871366146L, this.mIsEnabled);
                protoOutputStream.write(1133871366147L, this.mSupportedConfig);
                protoOutputStream.write(1133871366148L, this.mEnabledByDefaultConfig);
                protoOutputStream.write(1133871366149L, this.mIsInteractive);
                protoOutputStream.write(1112396529670L, this.mLastInteractiveTimeElapsed);
                protoOutputStream.write(1120986464263L, this.mStandbyTimeoutConfig);
                protoOutputStream.write(1133871366152L, this.mIdleSinceNonInteractive);
                protoOutputStream.write(1133871366153L, this.mIsDeviceIdle);
                for (int i : getAllowlistUidsLocked()) {
                    protoOutputStream.write(2220498092042L, i);
                }
                PowerManager.LowPowerStandbyPolicy policy = getPolicy();
                if (policy != null) {
                    long start2 = protoOutputStream.start(1146756268043L);
                    protoOutputStream.write(1138166333441L, policy.getIdentifier());
                    Iterator it = policy.getExemptPackages().iterator();
                    while (it.hasNext()) {
                        protoOutputStream.write(2237677961218L, (String) it.next());
                    }
                    protoOutputStream.write(1120986464259L, policy.getAllowedReasons());
                    Iterator it2 = policy.getAllowedFeatures().iterator();
                    while (it2.hasNext()) {
                        protoOutputStream.write(2237677961220L, (String) it2.next());
                    }
                    protoOutputStream.end(start2);
                }
                protoOutputStream.end(start);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enqueueNotifyAllowlistChangedLocked() {
        int[] allowlistUidsLocked = getAllowlistUidsLocked();
        LowPowerStandbyHandler lowPowerStandbyHandler = this.mHandler;
        lowPowerStandbyHandler.sendMessageAtTime(
                lowPowerStandbyHandler.obtainMessage(2, allowlistUidsLocked),
                this.mClock.uptimeMillis());
    }

    public final int findIndexOfStandbyPorts(IBinder iBinder) {
        for (int i = 0; i < ((ArrayList) this.mStandbyPortLocks).size(); i++) {
            if (((StandbyPortsLock) ((ArrayList) this.mStandbyPortLocks).get(i)).mToken
                    == iBinder) {
                return i;
            }
        }
        return -1;
    }

    public final List getActiveStandbyPorts() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                if (isEnabled() && this.mEnableStandbyPorts) {
                    List exemptPackageAppIdsLocked = getExemptPackageAppIdsLocked();
                    Iterator it = ((ArrayList) this.mStandbyPortLocks).iterator();
                    while (it.hasNext()) {
                        StandbyPortsLock standbyPortsLock = (StandbyPortsLock) it.next();
                        if (((ArrayList) exemptPackageAppIdsLocked)
                                .contains(
                                        Integer.valueOf(
                                                UserHandle.getAppId(standbyPortsLock.mUid)))) {
                            arrayList.addAll(standbyPortsLock.mPorts);
                        }
                    }
                    return arrayList;
                }
                return arrayList;
            } finally {
            }
        }
    }

    public final int[] getAllowlistUidsLocked() {
        List userHandles =
                ((UserManager) this.mContext.getSystemService(UserManager.class))
                        .getUserHandles(true);
        ArraySet arraySet = new ArraySet(this.mUidAllowedReasons.size());
        PowerManager.LowPowerStandbyPolicy policy = getPolicy();
        if (policy == null) {
            return new int[0];
        }
        int allowedReasons = policy.getAllowedReasons();
        for (int i = 0; i < this.mUidAllowedReasons.size(); i++) {
            Integer valueOf = Integer.valueOf(this.mUidAllowedReasons.keyAt(i));
            if ((this.mUidAllowedReasons.valueAt(i) & allowedReasons) != 0) {
                arraySet.add(valueOf);
            }
        }
        Iterator it = ((ArrayList) getExemptPackageAppIdsLocked()).iterator();
        while (it.hasNext()) {
            int appId = UserHandle.getAppId(((Integer) it.next()).intValue());
            int size = userHandles.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < userHandles.size(); i2++) {
                iArr[i2] = ((UserHandle) userHandles.get(i2)).getUid(appId);
            }
            for (int i3 = 0; i3 < size; i3++) {
                arraySet.add(Integer.valueOf(iArr[i3]));
            }
        }
        int[] iArr2 = new int[arraySet.size()];
        for (int i4 = 0; i4 < arraySet.size(); i4++) {
            iArr2[i4] = ((Integer) arraySet.valueAt(i4)).intValue();
        }
        Arrays.sort(iArr2);
        return iArr2;
    }

    public final List getExemptPackageAppIdsLocked() {
        PackageManager packageManager = this.mContext.getPackageManager();
        PowerManager.LowPowerStandbyPolicy policy = getPolicy();
        ArrayList arrayList = new ArrayList();
        if (policy == null) {
            return arrayList;
        }
        Iterator it = policy.getExemptPackages().iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(
                        Integer.valueOf(
                                UserHandle.getAppId(
                                        packageManager.getPackageUid(
                                                (String) it.next(),
                                                PackageManager.PackageInfoFlags.of(0L)))));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return arrayList;
    }

    public final PowerManager.LowPowerStandbyPolicy getPolicy() {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    return null;
                }
                if (!this.mEnableCustomPolicy) {
                    return DEFAULT_POLICY;
                }
                PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy = this.mPolicy;
                if (lowPowerStandbyPolicy == null) {
                    lowPowerStandbyPolicy = DEFAULT_POLICY;
                }
                return lowPowerStandbyPolicy;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsActive;
        }
        return z;
    }

    public final boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mSupportedConfig && this.mIsEnabled;
            } finally {
            }
        }
        return z;
    }

    public final boolean isPackageExempt(int i) {
        synchronized (this.mLock) {
            try {
                if (!isEnabled()) {
                    return true;
                }
                return ((ArrayList) getExemptPackageAppIdsLocked())
                        .contains(Integer.valueOf(UserHandle.getAppId(i)));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final PowerManager.LowPowerStandbyPolicy loadPolicy() {
        char c;
        AtomicFile atomicFile = new AtomicFile(this.mPolicyFile);
        if (!atomicFile.exists()) {
            return null;
        }
        try {
            try {
                FileInputStream openRead = atomicFile.openRead();
                try {
                    ArraySet arraySet = new ArraySet();
                    ArraySet arraySet2 = new ArraySet();
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                    String str = null;
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy =
                                    new PowerManager.LowPowerStandbyPolicy(
                                            str, arraySet, i, arraySet2);
                            if (openRead != null) {
                                openRead.close();
                            }
                            return lowPowerStandbyPolicy;
                        }
                        if (next == 2) {
                            int depth = resolvePullParser.getDepth();
                            String name = resolvePullParser.getName();
                            if (depth != 1) {
                                switch (name.hashCode()) {
                                    case -1618432855:
                                        if (name.equals("identifier")) {
                                            c = 0;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -764820798:
                                        if (name.equals("allowed-features")) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 1342665610:
                                        if (name.equals("allowed-reasons")) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 2046809176:
                                        if (name.equals("exempt-package")) {
                                            c = 1;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    default:
                                        c = 65535;
                                        break;
                                }
                                if (c == 0) {
                                    str =
                                            resolvePullParser.getAttributeValue(
                                                    (String) null, "value");
                                } else if (c == 1) {
                                    arraySet.add(
                                            resolvePullParser.getAttributeValue(
                                                    (String) null, "value"));
                                } else if (c == 2) {
                                    i = resolvePullParser.getAttributeInt((String) null, "value");
                                } else if (c != 3) {
                                    android.util.Slog.e(
                                            "LowPowerStandbyController", "Invalid tag: " + name);
                                } else {
                                    arraySet2.add(
                                            resolvePullParser.getAttributeValue(
                                                    (String) null, "value"));
                                }
                            } else if (!"low-power-standby-policy".equals(name)) {
                                android.util.Slog.e(
                                        "LowPowerStandbyController", "Invalid root tag: " + name);
                                if (openRead != null) {
                                    openRead.close();
                                }
                                return null;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException
                    | IllegalArgumentException
                    | NullPointerException
                    | XmlPullParserException e) {
                android.util.Slog.e(
                        "LowPowerStandbyController",
                        "Failed to read policy file " + atomicFile.getBaseFile(),
                        e);
                return null;
            }
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    public final void onDisabledLocked() {
        this.mAlarmManager.cancel(this.mOnStandbyTimeoutExpired);
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mPackageBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mUserReceiver);
        ((PowerAllowlistInternal) LocalServices.getService(PowerAllowlistInternal.class))
                .unregisterTempAllowlistChangeListener(this.mTempAllowlistChangeListener);
        updateActiveLocked();
    }

    public final void onInteractive() {
        synchronized (this.mLock) {
            this.mAlarmManager.cancel(this.mOnStandbyTimeoutExpired);
            this.mIsInteractive = true;
            this.mIsDeviceIdle = false;
            this.mIdleSinceNonInteractive = false;
            updateActiveLocked();
        }
    }

    public final void onNonInteractive() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        synchronized (this.mLock) {
            this.mIsInteractive = false;
            this.mIsDeviceIdle = false;
            this.mLastInteractiveTimeElapsed = elapsedRealtime;
            if (this.mStandbyTimeoutConfig > 0) {
                this.mAlarmManager.setExact(
                        2,
                        this.mClock.elapsedRealtime() + this.mStandbyTimeoutConfig,
                        "LowPowerStandbyController.StandbyTimeout",
                        this.mOnStandbyTimeoutExpired,
                        this.mHandler);
            }
            updateActiveLocked();
        }
    }

    public void onSettingsChanged() {
        synchronized (this.mLock) {
            try {
                boolean z = this.mIsEnabled;
                updateSettingsLocked();
                boolean z2 = this.mIsEnabled;
                if (z2 != z) {
                    if (z2) {
                        if (this.mPowerManager.isInteractive()) {
                            onInteractive();
                        } else {
                            onNonInteractive();
                        }
                        registerListeners();
                    } else {
                        onDisabledLocked();
                    }
                    sendExplicitBroadcast("android.os.action.LOW_POWER_STANDBY_ENABLED_CHANGED");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerListeners() {
        this.mContext.registerReceiver(
                this.mBroadcastReceiver,
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "android.os.action.DEVICE_IDLE_MODE_CHANGED",
                        "android.intent.action.SCREEN_ON",
                        "android.intent.action.SCREEN_OFF"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiver(this.mPackageBroadcastReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter2, null, this.mHandler);
        ((PowerAllowlistInternal) LocalServices.getService(PowerAllowlistInternal.class))
                .registerTempAllowlistChangeListener(this.mTempAllowlistChangeListener);
        PhoneCallServiceTracker phoneCallServiceTracker = this.mPhoneCallServiceTracker;
        if (phoneCallServiceTracker.mRegistered) {
            return;
        }
        try {
            ((IActivityManager) LowPowerStandbyController.this.mActivityManager.get())
                    .registerForegroundServiceObserver(phoneCallServiceTracker);
            phoneCallServiceTracker.mRegistered = true;
        } catch (RemoteException unused) {
        }
    }

    public final void releaseStandbyPorts(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                int findIndexOfStandbyPorts = findIndexOfStandbyPorts(iBinder);
                if (findIndexOfStandbyPorts == -1) {
                    return;
                }
                StandbyPortsLock standbyPortsLock =
                        (StandbyPortsLock)
                                ((ArrayList) this.mStandbyPortLocks)
                                        .remove(findIndexOfStandbyPorts);
                standbyPortsLock.mToken.unlinkToDeath(standbyPortsLock, 0);
                if (this.mEnableStandbyPorts
                        && isEnabled()
                        && isPackageExempt(standbyPortsLock.mUid)) {
                    LowPowerStandbyHandler lowPowerStandbyHandler = this.mHandler;
                    lowPowerStandbyHandler.sendMessageAtTime(
                            lowPowerStandbyHandler.obtainMessage(5), this.mClock.uptimeMillis());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendExplicitBroadcast(String str) {
        this.mContext.sendBroadcastAsUser(
                BatteryService$$ExternalSyntheticOutline0.m(1342177280, str), UserHandle.ALL);
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        Iterator it = ((ArrayList) this.mLowPowerStandbyManagingPackages).iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Intent intent2 = new Intent(intent);
            intent2.setPackage(str2);
            this.mContext.sendBroadcastAsUser(
                    intent2, UserHandle.ALL, "android.permission.MANAGE_LOW_POWER_STANDBY");
        }
    }

    public void setActiveDuringMaintenance(boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    android.util.Slog.w(
                            "LowPowerStandbyController",
                            "Low Power Standby settings cannot be changed because it is not"
                                + " supported on this device");
                } else {
                    Settings.Global.putInt(
                            this.mContext.getContentResolver(),
                            "low_power_standby_active_during_maintenance",
                            z ? 1 : 0);
                    onSettingsChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    android.util.Slog.w(
                            "LowPowerStandbyController",
                            "Low Power Standby cannot be enabled because it is not supported on"
                                + " this device");
                } else {
                    Settings.Global.putInt(
                            this.mContext.getContentResolver(),
                            "low_power_standby_enabled",
                            z ? 1 : 0);
                    onSettingsChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setPolicy(final PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    android.util.Slog.w(
                            "LowPowerStandbyController",
                            "Low Power Standby policy cannot be changed because it is not supported"
                                + " on this device");
                    return;
                }
                if (!this.mEnableCustomPolicy) {
                    android.util.Slog.d(
                            "LowPowerStandbyController", "Custom policies are not enabled.");
                    return;
                }
                if (Objects.equals(this.mPolicy, lowPowerStandbyPolicy)) {
                    return;
                }
                PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy2 = this.mPolicy;
                if (lowPowerStandbyPolicy2 == null) {
                    lowPowerStandbyPolicy2 = DEFAULT_POLICY;
                }
                PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy3 =
                        lowPowerStandbyPolicy == null ? DEFAULT_POLICY : lowPowerStandbyPolicy;
                int i = 0;
                for (int i2 = 0; i2 < this.mUidAllowedReasons.size(); i2++) {
                    i |= this.mUidAllowedReasons.valueAt(i2);
                }
                boolean z =
                        ((lowPowerStandbyPolicy2.getAllowedReasons()
                                                        ^ lowPowerStandbyPolicy3
                                                                .getAllowedReasons())
                                                & i)
                                        != 0
                                || (lowPowerStandbyPolicy2
                                                .getExemptPackages()
                                                .equals(lowPowerStandbyPolicy3.getExemptPackages())
                                        ^ true);
                this.mPolicy = lowPowerStandbyPolicy;
                this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                FileOutputStream startWrite;
                                LowPowerStandbyController lowPowerStandbyController =
                                        LowPowerStandbyController.this;
                                PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy4 =
                                        lowPowerStandbyPolicy;
                                lowPowerStandbyController.getClass();
                                AtomicFile atomicFile =
                                        new AtomicFile(lowPowerStandbyController.mPolicyFile);
                                if (lowPowerStandbyPolicy4 == null) {
                                    atomicFile.delete();
                                    return;
                                }
                                FileOutputStream fileOutputStream = null;
                                try {
                                    atomicFile.getBaseFile().mkdirs();
                                    startWrite = atomicFile.startWrite();
                                } catch (IOException e) {
                                    e = e;
                                }
                                try {
                                    TypedXmlSerializer resolveSerializer =
                                            Xml.resolveSerializer(startWrite);
                                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                                    resolveSerializer.startTag(
                                            (String) null, "low-power-standby-policy");
                                    LowPowerStandbyController.writeTagValue(
                                            resolveSerializer,
                                            "identifier",
                                            lowPowerStandbyPolicy4.getIdentifier());
                                    Iterator it =
                                            lowPowerStandbyPolicy4.getExemptPackages().iterator();
                                    while (it.hasNext()) {
                                        LowPowerStandbyController.writeTagValue(
                                                resolveSerializer,
                                                "exempt-package",
                                                (String) it.next());
                                    }
                                    int allowedReasons = lowPowerStandbyPolicy4.getAllowedReasons();
                                    resolveSerializer.startTag((String) null, "allowed-reasons");
                                    resolveSerializer.attributeInt(
                                            (String) null, "value", allowedReasons);
                                    resolveSerializer.endTag((String) null, "allowed-reasons");
                                    Iterator it2 =
                                            lowPowerStandbyPolicy4.getAllowedFeatures().iterator();
                                    while (it2.hasNext()) {
                                        LowPowerStandbyController.writeTagValue(
                                                resolveSerializer,
                                                "allowed-features",
                                                (String) it2.next());
                                    }
                                    resolveSerializer.endTag(
                                            (String) null, "low-power-standby-policy");
                                    resolveSerializer.endDocument();
                                    atomicFile.finishWrite(startWrite);
                                } catch (IOException e2) {
                                    e = e2;
                                    fileOutputStream = startWrite;
                                    android.util.Slog.e(
                                            "LowPowerStandbyController",
                                            "Failed to write policy to file "
                                                    + atomicFile.getBaseFile(),
                                            e);
                                    atomicFile.failWrite(fileOutputStream);
                                }
                            }
                        });
                if (z) {
                    enqueueNotifyAllowlistChangedLocked();
                }
                PowerManager.LowPowerStandbyPolicy policy = getPolicy();
                LowPowerStandbyHandler lowPowerStandbyHandler = this.mHandler;
                lowPowerStandbyHandler.sendMessageAtTime(
                        lowPowerStandbyHandler.obtainMessage(3, policy),
                        this.mClock.uptimeMillis());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void systemReady() {
        Resources resources = this.mContext.getResources();
        synchronized (this.mLock) {
            try {
                boolean z = resources.getBoolean(R.bool.config_navBarAlwaysShowOnSideEdgeGesture);
                this.mSupportedConfig = z;
                if (z) {
                    for (PackageInfo packageInfo :
                            this.mContext
                                    .getPackageManager()
                                    .getPackagesHoldingPermissions(
                                            new String[] {
                                                "android.permission.MANAGE_LOW_POWER_STANDBY"
                                            },
                                            1048576)) {
                        ((ArrayList) this.mLowPowerStandbyManagingPackages)
                                .add(packageInfo.packageName);
                    }
                    this.mAlarmManager =
                            (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
                    this.mPowerManager =
                            (PowerManager) this.mContext.getSystemService(PowerManager.class);
                    this.mActivityManagerInternal =
                            (ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class);
                    this.mStandbyTimeoutConfig =
                            resources.getInteger(
                                    R.integer.config_notificationStripRemoteViewSizeBytes);
                    this.mEnabledByDefaultConfig =
                            resources.getBoolean(
                                    R.bool.config_multiuserVisibleBackgroundUsersOnDefaultDisplay);
                    this.mIsInteractive = this.mPowerManager.isInteractive();
                    this.mContext
                            .getContentResolver()
                            .registerContentObserver(
                                    Settings.Global.getUriFor("low_power_standby_enabled"),
                                    false,
                                    this.mSettingsObserver,
                                    -1);
                    this.mContext
                            .getContentResolver()
                            .registerContentObserver(
                                    Settings.Global.getUriFor(
                                            "low_power_standby_active_during_maintenance"),
                                    false,
                                    this.mSettingsObserver,
                                    -1);
                    DeviceConfigWrapper deviceConfigWrapper = this.mDeviceConfig;
                    Executor mainExecutor = this.mContext.getMainExecutor();
                    DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener =
                            new DeviceConfig
                                    .OnPropertiesChangedListener() { // from class:
                                                                     // com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda3
                                public final void onPropertiesChanged(
                                        DeviceConfig.Properties properties) {
                                    LowPowerStandbyController lowPowerStandbyController =
                                            LowPowerStandbyController.this;
                                    synchronized (lowPowerStandbyController.mLock) {
                                        try {
                                            lowPowerStandbyController.mDeviceConfig.getClass();
                                            boolean z2 =
                                                    DeviceConfig.getBoolean(
                                                            "low_power_standby",
                                                            "enable_policy",
                                                            true);
                                            if (lowPowerStandbyController.mEnableCustomPolicy
                                                    != z2) {
                                                PowerManager.LowPowerStandbyPolicy policy =
                                                        lowPowerStandbyController.getPolicy();
                                                LowPowerStandbyController.LowPowerStandbyHandler
                                                        lowPowerStandbyHandler =
                                                                lowPowerStandbyController.mHandler;
                                                lowPowerStandbyHandler.sendMessageAtTime(
                                                        lowPowerStandbyHandler.obtainMessage(
                                                                3, policy),
                                                        lowPowerStandbyController.mClock
                                                                .uptimeMillis());
                                                lowPowerStandbyController
                                                        .enqueueNotifyAllowlistChangedLocked();
                                                lowPowerStandbyController.mEnableCustomPolicy = z2;
                                            }
                                            lowPowerStandbyController.mDeviceConfig.getClass();
                                            lowPowerStandbyController.mEnableStandbyPorts =
                                                    DeviceConfig.getBoolean(
                                                            "low_power_standby",
                                                            "enable_standby_ports",
                                                            true);
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            };
                    deviceConfigWrapper.getClass();
                    DeviceConfig.addOnPropertiesChangedListener(
                            "low_power_standby", mainExecutor, onPropertiesChangedListener);
                    this.mDeviceConfig.getClass();
                    this.mEnableCustomPolicy =
                            DeviceConfig.getBoolean("low_power_standby", "enable_policy", true);
                    this.mDeviceConfig.getClass();
                    this.mEnableStandbyPorts =
                            DeviceConfig.getBoolean(
                                    "low_power_standby", "enable_standby_ports", true);
                    if (this.mEnableCustomPolicy) {
                        this.mPolicy = loadPolicy();
                    } else {
                        this.mPolicy = DEFAULT_POLICY;
                    }
                    ContentResolver contentResolver = this.mContext.getContentResolver();
                    if (this.mSupportedConfig
                            && Settings.Global.getInt(
                                            contentResolver, "low_power_standby_enabled", -1)
                                    == -1) {
                        Settings.Global.putInt(
                                contentResolver,
                                "low_power_standby_enabled",
                                this.mEnabledByDefaultConfig ? 1 : 0);
                    }
                    updateSettingsLocked();
                    if (this.mIsEnabled) {
                        registerListeners();
                    }
                    LocalServices.addService(LocalService.class, this.mLocalService);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateActiveLocked() {
        Clock clock = this.mClock;
        boolean z = false;
        boolean z2 =
                clock.elapsedRealtime() - this.mLastInteractiveTimeElapsed
                        >= ((long) this.mStandbyTimeoutConfig);
        boolean z3 = this.mIdleSinceNonInteractive && !this.mIsDeviceIdle;
        if (this.mForceActive
                || (this.mIsEnabled
                        && !this.mIsInteractive
                        && z2
                        && (!z3 || this.mActiveDuringMaintenance))) {
            z = true;
        }
        if (this.mIsActive != z) {
            this.mIsActive = z;
            Boolean valueOf = Boolean.valueOf(z);
            LowPowerStandbyHandler lowPowerStandbyHandler = this.mHandler;
            lowPowerStandbyHandler.sendMessageAtTime(
                    lowPowerStandbyHandler.obtainMessage(1, valueOf), clock.uptimeMillis());
        }
    }

    public final void updateSettingsLocked() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mIsEnabled =
                this.mSupportedConfig
                        && Settings.Global.getInt(
                                        contentResolver,
                                        "low_power_standby_enabled",
                                        this.mEnabledByDefaultConfig ? 1 : 0)
                                != 0;
        this.mActiveDuringMaintenance =
                Settings.Global.getInt(
                                contentResolver, "low_power_standby_active_during_maintenance", 0)
                        != 0;
        updateActiveLocked();
    }
}
