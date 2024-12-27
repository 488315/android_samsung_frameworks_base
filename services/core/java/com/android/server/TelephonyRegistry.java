package com.android.server;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.BarringInfo;
import android.telephony.CallQuality;
import android.telephony.CallState;
import android.telephony.CellIdentity;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthTdscdma;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.LinkCapacityEstimate;
import android.telephony.LocationAccessPolicy;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.PreciseCallState;
import android.telephony.PreciseDataConnectionState;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.LocalLog;
import android.util.Pair;
import android.util.SparseArray;

import com.android.internal.app.IBatteryStats;
import com.android.internal.telephony.ICarrierConfigChangeListener;
import com.android.internal.telephony.ICarrierPrivilegesCallback;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.TelephonyPermissions;
import com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.am.BatteryStatsService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import dalvik.annotation.optimization.NeverCompile;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class TelephonyRegistry extends ITelephonyRegistry.Stub {
    public static final List INVALID_LCE_LIST =
            new ArrayList(Arrays.asList(new LinkCapacityEstimate(2, -1, -1)));
    public static final Set REQUIRE_PRECISE_PHONE_STATE_PERMISSION;
    public int[] mAllowedNetworkTypeReason;
    public long[] mAllowedNetworkTypeValue;
    public final AppOpsManager mAppOps;
    public int[] mBackgroundCallState;
    public final List mBarringInfo;
    public int[] mCallDisconnectCause;
    public boolean[] mCallForwarding;
    public String[] mCallIncomingNumber;
    public int[] mCallNetworkType;
    public int[] mCallPreciseDisconnectCause;
    public CallQuality[] mCallQuality;
    public int[] mCallState;
    public final ArrayList mCallStateLists;
    public boolean[] mCarrierNetworkChangeState;
    public final List mCarrierPrivilegeStates;
    public final List mCarrierRoamingNtnAvailableServices;
    public boolean[] mCarrierRoamingNtnEligible;
    public boolean[] mCarrierRoamingNtnMode;
    public NtnSignalStrength[] mCarrierRoamingNtnSignalStrength;
    public final List mCarrierServiceStates;
    public CellIdentity[] mCellIdentity;
    public final ArrayList mCellInfo;
    public final ConfigurationProvider mConfigurationProvider;
    public final Context mContext;
    public int[] mDataActivationState;
    public int[] mDataActivity;
    public int[] mDataConnectionNetworkType;
    public int[] mDataConnectionState;
    public int[] mDataEnabledReason;
    public int[] mECBMReason;
    public boolean[] mECBMStarted;
    public Map mEmergencyNumberList;
    public int[] mForegroundCallState;
    public final List mImsReasonInfo;
    public boolean[] mIsDataEnabled;
    public final List mLinkCapacityEstimateLists;
    public final List mMediaQualityStatus;
    public boolean[] mMessageWaiting;
    public int mNumPhones;
    public EmergencyNumber[] mOutgoingCallEmergencyNumber;
    public EmergencyNumber[] mOutgoingSmsEmergencyNumber;
    public final List mPhysicalChannelConfigs;
    public PreciseCallState[] mPreciseCallState;
    public final List mPreciseDataConnectionStates;
    public int[] mRingingCallState;
    public int[] mSCBMReason;
    public boolean[] mSCBMStarted;
    public SemSatelliteServiceState mSatServiceState;
    public SemSatelliteSignalStrength mSatSignalStrength;
    public ServiceState[] mServiceState;
    public SignalStrength[] mSignalStrength;
    public int[] mSrvccState;
    public TelephonyDisplayInfo[] mTelephonyDisplayInfos;
    public boolean[] mUserMobileDataState;
    public int[] mVoiceActivationState;
    public final ArrayList mRemoveList = new ArrayList();
    public final ArrayList mRecords = new ArrayList();
    public boolean mHasNotifySubscriptionInfoChangedOccurred = false;
    public boolean mHasNotifyOpportunisticSubscriptionInfoChangedOccurred = false;
    public int mDefaultSubId = -1;
    public int mDefaultPhoneId = -1;
    public PhoneCapability mPhoneCapability = null;
    public int mActiveDataSubId = -1;
    public int mRadioPowerState = 2;
    public final LocalLog mLocalLog = new LocalLog(256);
    public final LocalLog mListenLog = new LocalLog(256);
    public int[] mSimultaneousCellularCallingSubIds = new int[0];
    public final AnonymousClass1 mHandler =
            new Handler() { // from class: com.android.server.TelephonyRegistry.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        TelephonyRegistry telephonyRegistry = TelephonyRegistry.this;
                        List list = TelephonyRegistry.INVALID_LCE_LIST;
                        int activeModemCount =
                                ((TelephonyManager)
                                                telephonyRegistry.mContext.getSystemService(
                                                        "phone"))
                                        .getActiveModemCount();
                        for (int i2 = 0; i2 < activeModemCount; i2++) {
                            int subscriptionId = SubscriptionManager.getSubscriptionId(i2);
                            if (!SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
                                subscriptionId = Integer.MAX_VALUE;
                            }
                            TelephonyRegistry telephonyRegistry2 = TelephonyRegistry.this;
                            telephonyRegistry2.notifyCellLocationForSubscriber(
                                    subscriptionId, telephonyRegistry2.mCellIdentity[i2], true);
                        }
                        return;
                    }
                    if (i != 2) {
                        return;
                    }
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    synchronized (TelephonyRegistry.this.mRecords) {
                        try {
                            Iterator it = TelephonyRegistry.this.mRecords.iterator();
                            while (it.hasNext()) {
                                Record record = (Record) it.next();
                                if (record.subId == Integer.MAX_VALUE) {
                                    TelephonyRegistry.m96$$Nest$mcheckPossibleMissNotify(
                                            TelephonyRegistry.this, record, i3);
                                }
                            }
                            TelephonyRegistry.this.handleRemoveListLocked();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    TelephonyRegistry telephonyRegistry3 = TelephonyRegistry.this;
                    telephonyRegistry3.mDefaultSubId = i4;
                    telephonyRegistry3.mDefaultPhoneId = i3;
                    telephonyRegistry3.mLocalLog.log(
                            "Default subscription updated: mDefaultPhoneId="
                                    + TelephonyRegistry.this.mDefaultPhoneId
                                    + ", mDefaultSubId="
                                    + TelephonyRegistry.this.mDefaultSubId);
                }
            };
    public final AnonymousClass2 mBroadcastReceiver =
            new BroadcastReceiver() { // from class: com.android.server.TelephonyRegistry.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.intent.action.USER_SWITCHED".equals(action)) {
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                        AnonymousClass1 anonymousClass1 = TelephonyRegistry.this.mHandler;
                        anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1, intExtra, 0));
                        return;
                    }
                    if (!action.equals("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED")) {
                        if (action.equals("android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
                            TelephonyRegistry telephonyRegistry = TelephonyRegistry.this;
                            List list = TelephonyRegistry.INVALID_LCE_LIST;
                            telephonyRegistry.onMultiSimConfigChanged();
                            return;
                        }
                        return;
                    }
                    int intExtra2 =
                            intent.getIntExtra(
                                    "android.telephony.extra.SUBSCRIPTION_INDEX",
                                    SubscriptionManager.getDefaultSubscriptionId());
                    TelephonyRegistry telephonyRegistry2 = TelephonyRegistry.this;
                    List list2 = TelephonyRegistry.INVALID_LCE_LIST;
                    int intExtra3 =
                            intent.getIntExtra(
                                    "android.telephony.extra.SLOT_INDEX",
                                    telephonyRegistry2.getPhoneIdFromSubId(intExtra2));
                    if (TelephonyRegistry.this.validatePhoneId(intExtra3)) {
                        TelephonyRegistry telephonyRegistry3 = TelephonyRegistry.this;
                        if (intExtra2 != telephonyRegistry3.mDefaultSubId
                                || intExtra3 != telephonyRegistry3.mDefaultPhoneId) {
                            AnonymousClass1 anonymousClass12 = telephonyRegistry3.mHandler;
                            anonymousClass12.sendMessage(
                                    anonymousClass12.obtainMessage(2, intExtra3, intExtra2));
                            return;
                        }
                    }
                    if (TelephonyRegistry.this.getPhoneIdFromSubId(intExtra2) == -1) {
                        TelephonyRegistry telephonyRegistry4 = TelephonyRegistry.this;
                        telephonyRegistry4.mDefaultSubId = -1;
                        telephonyRegistry4.mDefaultPhoneId = -1;
                    }
                }
            };
    public final IBatteryStats mBatteryStats = BatteryStatsService.getService();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.TelephonyRegistry$3, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[]
                $SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult;

        static {
            int[] iArr = new int[LocationAccessPolicy.LocationPermissionResult.values().length];
            $SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult = iArr;
            try {
                iArr[LocationAccessPolicy.LocationPermissionResult.DENIED_HARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult[
                                LocationAccessPolicy.LocationPermissionResult.DENIED_SOFT
                                        .ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConfigurationProvider {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Record {
        public IBinder binder;
        public IPhoneStateListener callback;
        public int callerPid;
        public int callerUid;
        public String callingFeatureId;
        public String callingPackage;
        public ICarrierConfigChangeListener carrierConfigChangeListener;
        public ICarrierPrivilegesCallback carrierPrivilegesCallback;
        public Context context;
        public TelephonyRegistryDeathRecipient deathRecipient;
        public Set eventList;
        public IOnSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListenerCallback;
        public IOnSubscriptionsChangedListener onSubscriptionsChangedListenerCallback;
        public int phoneId;
        public boolean renounceCoarseLocationAccess;
        public boolean renounceFineLocationAccess;
        public int subId;

        public final boolean canReadCallLog() {
            try {
                return TelephonyPermissions.checkReadCallLog(
                        this.context,
                        this.subId,
                        this.callerPid,
                        this.callerUid,
                        this.callingPackage,
                        this.callingFeatureId);
            } catch (SecurityException unused) {
                return false;
            }
        }

        public final boolean matchTelephonyCallbackEvent(int i) {
            return this.callback != null && this.eventList.contains(Integer.valueOf(i));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{callingPackage=");
            String str = this.callingPackage;
            List list = TelephonyRegistry.INVALID_LCE_LIST;
            if (!Build.IS_DEBUGGABLE) {
                str = "***";
            }
            sb.append(str);
            sb.append(" callerUid=");
            sb.append(this.callerUid);
            sb.append(" binder=");
            sb.append(this.binder);
            sb.append(" callback=");
            sb.append(this.callback);
            sb.append(" onSubscriptionsChangedListenererCallback=");
            sb.append(this.onSubscriptionsChangedListenerCallback);
            sb.append(" onOpportunisticSubscriptionsChangedListenererCallback=");
            sb.append(this.onOpportunisticSubscriptionsChangedListenerCallback);
            sb.append(" carrierPrivilegesCallback=");
            sb.append(this.carrierPrivilegesCallback);
            sb.append(" carrierConfigChangeListener=");
            sb.append(this.carrierConfigChangeListener);
            sb.append(" subId=");
            sb.append(this.subId);
            sb.append(" phoneId=");
            sb.append(this.phoneId);
            sb.append(" events=");
            sb.append(this.eventList);
            sb.append("}");
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TelephonyRegistryDeathRecipient implements IBinder.DeathRecipient {
        public final IBinder binder;

        public TelephonyRegistryDeathRecipient(IBinder iBinder) {
            this.binder = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            TelephonyRegistry telephonyRegistry = TelephonyRegistry.this;
            IBinder iBinder = this.binder;
            List list = TelephonyRegistry.INVALID_LCE_LIST;
            telephonyRegistry.remove(iBinder);
        }
    }

    /* renamed from: -$$Nest$mcheckPossibleMissNotify, reason: not valid java name */
    public static void m96$$Nest$mcheckPossibleMissNotify(
            TelephonyRegistry telephonyRegistry, Record record, int i) {
        telephonyRegistry.getClass();
        Set set = record.eventList;
        if (set == null || set.isEmpty()) {
            log("checkPossibleMissNotify: events = null.");
            return;
        }
        if (set.contains(1)) {
            try {
                ServiceState serviceState = new ServiceState(telephonyRegistry.mServiceState[i]);
                if (telephonyRegistry.checkFineLocationAccess(record, 29)) {
                    record.callback.onServiceStateChanged(serviceState);
                } else if (telephonyRegistry.checkCoarseLocationAccess(record, 29)) {
                    record.callback.onServiceStateChanged(
                            serviceState.createLocationInfoSanitizedCopy(false));
                } else {
                    record.callback.onServiceStateChanged(
                            serviceState.createLocationInfoSanitizedCopy(true));
                }
            } catch (RemoteException unused) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(9)) {
            try {
                SignalStrength signalStrength = telephonyRegistry.mSignalStrength[i];
                if (signalStrength != null) {
                    record.callback.onSignalStrengthsChanged(new SignalStrength(signalStrength));
                }
            } catch (RemoteException unused2) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(2)) {
            try {
                SignalStrength signalStrength2 = telephonyRegistry.mSignalStrength[i];
                if (signalStrength2 != null) {
                    int gsmSignalStrength = signalStrength2.getGsmSignalStrength();
                    IPhoneStateListener iPhoneStateListener = record.callback;
                    if (gsmSignalStrength == 99) {
                        gsmSignalStrength = -1;
                    }
                    iPhoneStateListener.onSignalStrengthChanged(gsmSignalStrength);
                }
            } catch (RemoteException unused3) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (validateEventAndUserLocked(record, 11)) {
            try {
                if (telephonyRegistry.checkCoarseLocationAccess(record, 1)
                        && telephonyRegistry.checkFineLocationAccess(record, 29)) {
                    record.callback.onCellInfoChanged((List) telephonyRegistry.mCellInfo.get(i));
                }
            } catch (RemoteException unused4) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(20)) {
            try {
                record.callback.onUserMobileDataStateChanged(
                        telephonyRegistry.mUserMobileDataState[i]);
            } catch (RemoteException unused5) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(21)) {
            try {
                TelephonyDisplayInfo telephonyDisplayInfo =
                        telephonyRegistry.mTelephonyDisplayInfos[i];
                if (telephonyDisplayInfo != null) {
                    record.callback.onDisplayInfoChanged(telephonyDisplayInfo);
                }
            } catch (RemoteException unused6) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(3)) {
            try {
                record.callback.onMessageWaitingIndicatorChanged(
                        telephonyRegistry.mMessageWaiting[i]);
            } catch (RemoteException unused7) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(4)) {
            try {
                record.callback.onCallForwardingIndicatorChanged(
                        telephonyRegistry.mCallForwarding[i]);
            } catch (RemoteException unused8) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (validateEventAndUserLocked(record, 5)) {
            try {
                if (telephonyRegistry.checkCoarseLocationAccess(record, 1)
                        && telephonyRegistry.checkFineLocationAccess(record, 29)) {
                    record.callback.onCellLocationChanged(telephonyRegistry.mCellIdentity[i]);
                }
            } catch (RemoteException unused9) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(7)) {
            try {
                record.callback.onDataConnectionStateChanged(
                        telephonyRegistry.mDataConnectionState[i],
                        telephonyRegistry.mDataConnectionNetworkType[i]);
            } catch (RemoteException unused10) {
                telephonyRegistry.mRemoveList.add(record.binder);
            }
        }
    }

    static {
        HashSet hashSet = new HashSet();
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION = hashSet;
        hashSet.add(13);
        hashSet.add(14);
        hashSet.add(12);
        hashSet.add(26);
        hashSet.add(27);
        hashSet.add(28);
        hashSet.add(31);
        hashSet.add(32);
        hashSet.add(33);
        hashSet.add(34);
        hashSet.add(37);
        hashSet.add(39);
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.server.TelephonyRegistry$1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.server.TelephonyRegistry$2] */
    public TelephonyRegistry(Context context, ConfigurationProvider configurationProvider) {
        this.mImsReasonInfo = null;
        this.mBarringInfo = null;
        this.mCarrierNetworkChangeState = null;
        this.mCarrierRoamingNtnMode = null;
        this.mCarrierRoamingNtnEligible = null;
        this.mSatServiceState = null;
        this.mSatSignalStrength = null;
        this.mContext = context;
        this.mConfigurationProvider = configurationProvider;
        int activeModemCount =
                ((TelephonyManager) context.getSystemService("phone")).getActiveModemCount();
        this.mNumPhones = activeModemCount;
        this.mCallState = new int[activeModemCount];
        this.mDataActivity = new int[activeModemCount];
        this.mDataConnectionState = new int[activeModemCount];
        this.mDataConnectionNetworkType = new int[activeModemCount];
        this.mCallIncomingNumber = new String[activeModemCount];
        this.mServiceState = new ServiceState[activeModemCount];
        this.mVoiceActivationState = new int[activeModemCount];
        this.mDataActivationState = new int[activeModemCount];
        this.mUserMobileDataState = new boolean[activeModemCount];
        this.mSignalStrength = new SignalStrength[activeModemCount];
        this.mMessageWaiting = new boolean[activeModemCount];
        this.mCallForwarding = new boolean[activeModemCount];
        this.mCellIdentity = new CellIdentity[activeModemCount];
        this.mSrvccState = new int[activeModemCount];
        this.mPreciseCallState = new PreciseCallState[activeModemCount];
        this.mForegroundCallState = new int[activeModemCount];
        this.mBackgroundCallState = new int[activeModemCount];
        this.mRingingCallState = new int[activeModemCount];
        this.mCallDisconnectCause = new int[activeModemCount];
        this.mCallPreciseDisconnectCause = new int[activeModemCount];
        this.mCallQuality = new CallQuality[activeModemCount];
        this.mMediaQualityStatus = new ArrayList();
        this.mCallNetworkType = new int[activeModemCount];
        this.mCallStateLists = new ArrayList();
        this.mPreciseDataConnectionStates = new ArrayList();
        this.mCellInfo = new ArrayList(activeModemCount);
        this.mImsReasonInfo = new ArrayList();
        this.mEmergencyNumberList = new HashMap();
        this.mOutgoingCallEmergencyNumber = new EmergencyNumber[activeModemCount];
        this.mOutgoingSmsEmergencyNumber = new EmergencyNumber[activeModemCount];
        this.mBarringInfo = new ArrayList();
        this.mCarrierNetworkChangeState = new boolean[activeModemCount];
        this.mTelephonyDisplayInfos = new TelephonyDisplayInfo[activeModemCount];
        this.mPhysicalChannelConfigs = new ArrayList();
        this.mAllowedNetworkTypeReason = new int[activeModemCount];
        this.mAllowedNetworkTypeValue = new long[activeModemCount];
        this.mIsDataEnabled = new boolean[activeModemCount];
        this.mDataEnabledReason = new int[activeModemCount];
        this.mLinkCapacityEstimateLists = new ArrayList();
        this.mCarrierPrivilegeStates = new ArrayList();
        this.mCarrierServiceStates = new ArrayList();
        this.mECBMReason = new int[activeModemCount];
        this.mECBMStarted = new boolean[activeModemCount];
        this.mSCBMReason = new int[activeModemCount];
        this.mSCBMStarted = new boolean[activeModemCount];
        this.mCarrierRoamingNtnMode = new boolean[activeModemCount];
        this.mCarrierRoamingNtnEligible = new boolean[activeModemCount];
        this.mCarrierRoamingNtnAvailableServices = new ArrayList();
        this.mCarrierRoamingNtnSignalStrength = new NtnSignalStrength[activeModemCount];
        this.mSatServiceState = new SemSatelliteServiceState();
        this.mSatSignalStrength = new SemSatelliteSignalStrength();
        for (int i = 0; i < activeModemCount; i++) {
            this.mCallState[i] = 0;
            this.mDataActivity[i] = 0;
            this.mDataConnectionState[i] = -1;
            this.mVoiceActivationState[i] = 0;
            this.mDataActivationState[i] = 0;
            this.mCallIncomingNumber[i] = "";
            this.mServiceState[i] = new ServiceState();
            this.mSignalStrength[i] = null;
            this.mUserMobileDataState[i] = false;
            this.mMessageWaiting[i] = false;
            this.mCallForwarding[i] = false;
            this.mCellIdentity[i] = null;
            this.mCellInfo.add(i, Collections.EMPTY_LIST);
            ((ArrayList) this.mImsReasonInfo).add(i, new ImsReasonInfo());
            this.mSrvccState[i] = -1;
            this.mCallDisconnectCause[i] = -1;
            this.mCallPreciseDisconnectCause[i] = -1;
            this.mCallQuality[i] = createCallQuality();
            ((ArrayList) this.mMediaQualityStatus).add(i, new SparseArray());
            this.mCallStateLists.add(i, new ArrayList());
            this.mCallNetworkType[i] = 0;
            this.mPreciseCallState[i] = new PreciseCallState(-1, -1, -1, -1, -1);
            this.mRingingCallState[i] = 0;
            this.mForegroundCallState[i] = 0;
            this.mBackgroundCallState[i] = 0;
            ((ArrayList) this.mPreciseDataConnectionStates).add(new ArrayMap());
            ((ArrayList) this.mBarringInfo).add(i, new BarringInfo());
            this.mCarrierNetworkChangeState[i] = false;
            this.mTelephonyDisplayInfos[i] = null;
            this.mIsDataEnabled[i] = false;
            this.mDataEnabledReason[i] = 0;
            ((ArrayList) this.mPhysicalChannelConfigs).add(i, new ArrayList());
            this.mAllowedNetworkTypeReason[i] = -1;
            this.mAllowedNetworkTypeValue[i] = -1;
            ((ArrayList) this.mLinkCapacityEstimateLists).add(i, INVALID_LCE_LIST);
            ((ArrayList) this.mCarrierPrivilegeStates)
                    .add(i, new Pair(Collections.emptyList(), new int[0]));
            ((ArrayList) this.mCarrierServiceStates).add(i, new Pair(null, -1));
            this.mECBMReason[i] = 0;
            this.mECBMStarted[i] = false;
            this.mSCBMReason[i] = 0;
            this.mSCBMStarted[i] = false;
            this.mCarrierRoamingNtnMode[i] = false;
            this.mCarrierRoamingNtnEligible[i] = false;
            ((ArrayList) this.mCarrierRoamingNtnAvailableServices).add(i, new IntArray());
            this.mCarrierRoamingNtnSignalStrength[i] = new NtnSignalStrength(0);
        }
        this.mAppOps = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
    }

    public static CallQuality createCallQuality() {
        return new CallQuality(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public static BroadcastOptions createServiceStateBroadcastOptions(int i, int i2, String str) {
        return new BroadcastOptions()
                .setDeliveryGroupPolicy(1)
                .setDeliveryGroupMatchingKey(
                        "android.intent.action.SERVICE_STATE",
                        i
                                + PackageManagerShellCommandDataLoader.STDIN_PATH
                                + i2
                                + PackageManagerShellCommandDataLoader.STDIN_PATH
                                + str)
                .setDeferralPolicy(2);
    }

    public static Intent createServiceStateIntent(
            ServiceState serviceState, int i, int i2, boolean z) {
        Intent m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        16777216, "android.intent.action.SERVICE_STATE");
        Bundle bundle = new Bundle();
        if (z) {
            serviceState.createLocationInfoSanitizedCopy(true).fillInNotifierBundle(bundle);
        } else {
            serviceState.fillInNotifierBundle(bundle);
        }
        m.putExtras(bundle);
        m.putExtra("subscription", i);
        m.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        m.putExtra("slot", i2);
        m.putExtra("android.telephony.extra.SLOT_INDEX", i2);
        return m;
    }

    public static void cutListToSize(int i, List list) {
        if (list == null) {
            return;
        }
        while (list.size() > i) {
            list.remove(list.size() - 1);
        }
    }

    public static boolean doesLimitApplyForListeners(int i, int i2) {
        return (i == 1000 || i == 1001 || i == i2) ? false : true;
    }

    public static String getApnTypesStringFromBitmask(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 17) == 17) {
            arrayList.add("default");
            i &= -18;
        }
        while (i != 0) {
            int highestOneBit = Integer.highestOneBit(i);
            String apnTypeString = ApnSetting.getApnTypeString(highestOneBit);
            if (!TextUtils.isEmpty(apnTypeString)) {
                arrayList.add(apnTypeString);
            }
            i &= ~highestOneBit;
        }
        return TextUtils.join(",", arrayList);
    }

    public static List getLocationSanitizedConfigs(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((PhysicalChannelConfig) it.next()).createLocationInfoSanitizedCopy());
        }
        return arrayList;
    }

    public static void log(String str) {
        Rlog.d("TelephonyRegistry", str);
    }

    public static void loge(String str) {
        Rlog.e("TelephonyRegistry", str);
    }

    public static String pii(List list) {
        if (list.isEmpty() || Build.IS_DEBUGGABLE) {
            return list.toString();
        }
        return "[***, size=" + list.size() + "]";
    }

    public static boolean validateEventAndUserLocked(Record record, int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (UserHandle.getUserId(record.callerUid) == ActivityManager.getCurrentUser()) {
                if (record.matchTelephonyCallbackEvent(i)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Record add(final int i, int i2, IBinder iBinder, boolean z) {
        synchronized (this.mRecords) {
            try {
                int size = this.mRecords.size();
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    Record record = (Record) this.mRecords.get(i4);
                    if (iBinder == record.binder) {
                        return record;
                    }
                    if (record.callerPid == i2) {
                        i3++;
                    }
                }
                this.mConfigurationProvider.getClass();
                int intValue =
                        ((Integer)
                                        Binder.withCleanCallingIdentity(
                                                new TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda6()))
                                .intValue();
                if (z && intValue >= 1 && i3 >= intValue) {
                    String str =
                            "Pid "
                                    + i2
                                    + " has exceeded the number of permissible registered"
                                    + " listeners. Ignoring request to add.";
                    loge(str);
                    this.mConfigurationProvider.getClass();
                    if (((Boolean)
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingSupplier() { // from class:
                                                                          // com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda1
                                                public final Object getOrThrow() {
                                                    return Boolean.valueOf(
                                                            CompatChanges.isChangeEnabled(
                                                                    150880553L, i));
                                                }
                                            }))
                            .booleanValue()) {
                        throw new IllegalStateException(str);
                    }
                } else if (i3 >= 25) {
                    Rlog.w(
                            "TelephonyRegistry",
                            "Pid "
                                    + i2
                                    + " has exceeded half the number of permissible registered"
                                    + " listeners. Now at "
                                    + i3);
                }
                Record record2 = new Record();
                record2.subId = -1;
                record2.phoneId = -1;
                record2.binder = iBinder;
                TelephonyRegistryDeathRecipient telephonyRegistryDeathRecipient =
                        new TelephonyRegistryDeathRecipient(iBinder);
                record2.deathRecipient = telephonyRegistryDeathRecipient;
                try {
                    iBinder.linkToDeath(telephonyRegistryDeathRecipient, 0);
                    this.mRecords.add(record2);
                    return record2;
                } catch (RemoteException unused) {
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addCarrierConfigChangeListener(
            ICarrierConfigChangeListener iCarrierConfigChangeListener, String str, String str2) {
        UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        synchronized (this.mRecords) {
            try {
                Record add =
                        add(
                                Binder.getCallingUid(),
                                Binder.getCallingPid(),
                                iCarrierConfigChangeListener.asBinder(),
                                doesLimitApplyForListeners(
                                        Binder.getCallingUid(), Process.myUid()));
                if (add == null) {
                    loge("Can not create Record instance!");
                    return;
                }
                add.context = this.mContext;
                add.carrierConfigChangeListener = iCarrierConfigChangeListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = Binder.getCallingUid();
                add.callerPid = Binder.getCallingPid();
                add.eventList = new ArraySet();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addCarrierPrivilegesCallback(
            int i, ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str, String str2) {
        UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.READ_PRIVILEGED_PHONE_STATE", "addCarrierPrivilegesCallback");
        onMultiSimConfigChanged();
        synchronized (this.mRecords) {
            try {
                if (!validatePhoneId(i)) {
                    throw new IllegalArgumentException("Invalid slot index: " + i);
                }
                Record add =
                        add(
                                Binder.getCallingUid(),
                                Binder.getCallingPid(),
                                iCarrierPrivilegesCallback.asBinder(),
                                false);
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.carrierPrivilegesCallback = iCarrierPrivilegesCallback;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = Binder.getCallingUid();
                add.callerPid = Binder.getCallingPid();
                add.phoneId = i;
                add.eventList = new ArraySet();
                Pair pair = (Pair) ((ArrayList) this.mCarrierPrivilegeStates).get(i);
                Pair pair2 = (Pair) ((ArrayList) this.mCarrierServiceStates).get(i);
                try {
                    ICarrierPrivilegesCallback iCarrierPrivilegesCallback2 =
                            add.carrierPrivilegesCallback;
                    if (iCarrierPrivilegesCallback2 != null) {
                        List unmodifiableList = Collections.unmodifiableList((List) pair.first);
                        Object obj = pair.second;
                        iCarrierPrivilegesCallback2.onCarrierPrivilegesChanged(
                                unmodifiableList, Arrays.copyOf((int[]) obj, ((int[]) obj).length));
                        add.carrierPrivilegesCallback.onCarrierServiceChanged(
                                (String) pair2.first, ((Integer) pair2.second).intValue());
                    }
                } catch (RemoteException unused) {
                    remove(add.binder);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addOnOpportunisticSubscriptionsChangedListener(
            String str,
            String str2,
            IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
        UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        synchronized (this.mRecords) {
            try {
                Record add =
                        add(
                                Binder.getCallingUid(),
                                Binder.getCallingPid(),
                                iOnSubscriptionsChangedListener.asBinder(),
                                doesLimitApplyForListeners(
                                        Binder.getCallingUid(), Process.myUid()));
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.onOpportunisticSubscriptionsChangedListenerCallback =
                        iOnSubscriptionsChangedListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = Binder.getCallingUid();
                add.callerPid = Binder.getCallingPid();
                add.eventList = new ArraySet();
                if (this.mHasNotifyOpportunisticSubscriptionInfoChangedOccurred) {
                    try {
                        add.onOpportunisticSubscriptionsChangedListenerCallback
                                .onSubscriptionsChanged();
                    } catch (RemoteException unused) {
                        remove(add.binder);
                    }
                } else {
                    log("listen ooscl: hasNotifyOpptSubInfoChangedOccurred==false no callback");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addOnSubscriptionsChangedListener(
            String str,
            String str2,
            IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
        UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        synchronized (this.mRecords) {
            try {
                Record add =
                        add(
                                Binder.getCallingUid(),
                                Binder.getCallingPid(),
                                iOnSubscriptionsChangedListener.asBinder(),
                                doesLimitApplyForListeners(
                                        Binder.getCallingUid(), Process.myUid()));
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.onSubscriptionsChangedListenerCallback = iOnSubscriptionsChangedListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = Binder.getCallingUid();
                add.callerPid = Binder.getCallingPid();
                add.eventList = new ArraySet();
                if (this.mHasNotifySubscriptionInfoChangedOccurred) {
                    try {
                        add.onSubscriptionsChangedListenerCallback.onSubscriptionsChanged();
                    } catch (RemoteException unused) {
                        remove(add.binder);
                    }
                } else {
                    log(
                            "listen oscl: mHasNotifySubscriptionInfoChangedOccurred==false no"
                                + " callback");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void broadcastCallStateChanged(int i, int i2, int i3, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (i == 0) {
                this.mBatteryStats.notePhoneOff();
                FrameworkStatsLog.write(95, 0);
            } else {
                this.mBatteryStats.notePhoneOn();
                FrameworkStatsLog.write(95, 1);
            }
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Intent intent = new Intent("android.intent.action.PHONE_STATE");
        intent.putExtra(
                LauncherConfigurationInternal.KEY_STATE_BOOLEAN,
                i != 1
                        ? i != 2
                                ? TelephonyManager.EXTRA_STATE_IDLE
                                : TelephonyManager.EXTRA_STATE_OFFHOOK
                        : TelephonyManager.EXTRA_STATE_RINGING);
        if (i3 != -1) {
            intent.setAction("android.intent.action.SUBSCRIPTION_PHONE_STATE");
            intent.putExtra("subscription", i3);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i3);
        }
        if (i2 != -1) {
            intent.putExtra("slot", i2);
            intent.putExtra("android.telephony.extra.SLOT_INDEX", i2);
        }
        intent.addFlags(16777216);
        Intent intent2 = new Intent(intent);
        intent2.putExtra("incoming_number", str);
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.sendBroadcastAsUser(
                intent2, userHandle, "android.permission.READ_PRIVILEGED_PHONE_STATE");
        this.mContext.sendBroadcastAsUser(
                intent, userHandle, "android.permission.READ_PHONE_STATE", 51);
        this.mContext.sendBroadcastAsUserMultiplePermissions(
                intent2,
                userHandle,
                new String[] {
                    "android.permission.READ_PHONE_STATE", "android.permission.READ_CALL_LOG"
                });
    }

    public final void broadcastDataConnectionStateChanged(
            int i, int i2, PreciseDataConnectionState preciseDataConnectionState) {
        Intent intent = new Intent("android.intent.action.ANY_DATA_STATE");
        intent.putExtra(
                LauncherConfigurationInternal.KEY_STATE_BOOLEAN,
                TelephonyUtils.dataStateToString(preciseDataConnectionState.getState()));
        intent.putExtra("apn", preciseDataConnectionState.getApnSetting().getApnName());
        intent.putExtra(
                "apnType",
                getApnTypesStringFromBitmask(
                        preciseDataConnectionState.getApnSetting().getApnTypeBitmask()));
        intent.putExtra("slot", i);
        intent.putExtra("subscription", i2);
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.sendBroadcastAsUser(intent, userHandle, "android.permission.READ_PHONE_STATE");
        this.mContext
                .createContextAsUser(userHandle, 0)
                .sendBroadcastMultiplePermissions(
                        intent,
                        new String[] {"android.permission.READ_PRIVILEGED_PHONE_STATE"},
                        new String[] {"android.permission.READ_PHONE_STATE"});
    }

    public final void broadcastServiceStateChanged(int i, int i2, ServiceState serviceState) {
        try {
            this.mBatteryStats.notePhoneState(serviceState.getState());
        } catch (RemoteException unused) {
        }
        Context context = this.mContext;
        if (LocationAccessPolicy.isLocationModeEnabled(context, context.getUserId())) {
            Intent createServiceStateIntent = createServiceStateIntent(serviceState, i2, i, false);
            Context context2 = this.mContext;
            UserHandle userHandle = UserHandle.ALL;
            context2.createContextAsUser(userHandle, 0)
                    .sendBroadcastMultiplePermissions(
                            createServiceStateIntent,
                            new String[] {
                                "android.permission.READ_PHONE_STATE",
                                "android.permission.ACCESS_FINE_LOCATION"
                            },
                            createServiceStateBroadcastOptions(i2, i, "I:RA"));
            this.mContext
                    .createContextAsUser(userHandle, 0)
                    .sendBroadcastMultiplePermissions(
                            createServiceStateIntent,
                            new String[] {
                                "android.permission.READ_PRIVILEGED_PHONE_STATE",
                                "android.permission.ACCESS_FINE_LOCATION"
                            },
                            new String[] {"android.permission.READ_PHONE_STATE"},
                            null,
                            createServiceStateBroadcastOptions(i2, i, "I:RPA,E:R"));
            Intent createServiceStateIntent2 = createServiceStateIntent(serviceState, i2, i, true);
            this.mContext
                    .createContextAsUser(userHandle, 0)
                    .sendBroadcastMultiplePermissions(
                            createServiceStateIntent2,
                            new String[] {"android.permission.READ_PHONE_STATE"},
                            new String[] {"android.permission.ACCESS_FINE_LOCATION"},
                            null,
                            createServiceStateBroadcastOptions(i2, i, "I:R,E:A"));
            this.mContext
                    .createContextAsUser(userHandle, 0)
                    .sendBroadcastMultiplePermissions(
                            createServiceStateIntent2,
                            new String[] {"android.permission.READ_PRIVILEGED_PHONE_STATE"},
                            new String[] {
                                "android.permission.READ_PHONE_STATE",
                                "android.permission.ACCESS_FINE_LOCATION"
                            },
                            null,
                            createServiceStateBroadcastOptions(i2, i, "I:RP,E:RA"));
            return;
        }
        String[] strArr =
                (String[])
                        Binder.withCleanCallingIdentity(
                                new FunctionalUtils
                                        .ThrowingSupplier() { // from class:
                                                              // com.android.server.TelephonyRegistry$$ExternalSyntheticLambda0
                                    public final Object getOrThrow() {
                                        return LocationAccessPolicy.getLocationBypassPackages(
                                                TelephonyRegistry.this.mContext);
                                    }
                                });
        for (String str : strArr) {
            Intent createServiceStateIntent3 = createServiceStateIntent(serviceState, i2, i, false);
            createServiceStateIntent3.setPackage(str);
            Context context3 = this.mContext;
            UserHandle userHandle2 = UserHandle.ALL;
            context3.createContextAsUser(userHandle2, 0)
                    .sendBroadcastMultiplePermissions(
                            createServiceStateIntent3,
                            new String[] {"android.permission.READ_PHONE_STATE"},
                            createServiceStateBroadcastOptions(i2, i, "I:R"));
            this.mContext
                    .createContextAsUser(userHandle2, 0)
                    .sendBroadcastMultiplePermissions(
                            createServiceStateIntent3,
                            new String[] {"android.permission.READ_PRIVILEGED_PHONE_STATE"},
                            new String[] {"android.permission.READ_PHONE_STATE"},
                            null,
                            createServiceStateBroadcastOptions(i2, i, "I:RP,E:R"));
        }
        Intent createServiceStateIntent4 = createServiceStateIntent(serviceState, i2, i, true);
        Context context4 = this.mContext;
        UserHandle userHandle3 = UserHandle.ALL;
        context4.createContextAsUser(userHandle3, 0)
                .sendBroadcastMultiplePermissions(
                        createServiceStateIntent4,
                        new String[] {"android.permission.READ_PHONE_STATE"},
                        new String[0],
                        strArr,
                        createServiceStateBroadcastOptions(i2, i, "I:R,lbp"));
        this.mContext
                .createContextAsUser(userHandle3, 0)
                .sendBroadcastMultiplePermissions(
                        createServiceStateIntent4,
                        new String[] {"android.permission.READ_PRIVILEGED_PHONE_STATE"},
                        new String[] {"android.permission.READ_PHONE_STATE"},
                        strArr,
                        createServiceStateBroadcastOptions(i2, i, "I:RP,E:R,lbp"));
    }

    public final boolean checkCoarseLocationAccess(Record record, int i) {
        if (record.renounceCoarseLocationAccess) {
            return false;
        }
        return ((Boolean)
                        Binder.withCleanCallingIdentity(
                                new TelephonyRegistry$$ExternalSyntheticLambda2(
                                        this,
                                        new LocationAccessPolicy.LocationPermissionQuery.Builder()
                                                .setCallingPackage(record.callingPackage)
                                                .setCallingFeatureId(record.callingFeatureId)
                                                .setCallingPid(record.callerPid)
                                                .setCallingUid(record.callerUid)
                                                .setMethod("TelephonyRegistry push")
                                                .setLogAsInfo(true)
                                                .setMinSdkVersionForCoarse(i)
                                                .setMinSdkVersionForFine(Integer.MAX_VALUE)
                                                .setMinSdkVersionForEnforcement(i)
                                                .build(),
                                        1)))
                .booleanValue();
    }

    public final boolean checkFineLocationAccess(Record record, int i) {
        if (record.renounceFineLocationAccess) {
            return false;
        }
        return ((Boolean)
                        Binder.withCleanCallingIdentity(
                                new TelephonyRegistry$$ExternalSyntheticLambda2(
                                        this,
                                        new LocationAccessPolicy.LocationPermissionQuery.Builder()
                                                .setCallingPackage(record.callingPackage)
                                                .setCallingFeatureId(record.callingFeatureId)
                                                .setCallingPid(record.callerPid)
                                                .setCallingUid(record.callerUid)
                                                .setMethod("TelephonyRegistry push")
                                                .setLogAsInfo(true)
                                                .setMinSdkVersionForFine(i)
                                                .setMinSdkVersionForCoarse(i)
                                                .setMinSdkVersionForEnforcement(i)
                                                .build(),
                                        0)))
                .booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00e8, code lost:

       if (((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0(0, r0, r11))).booleanValue() != false) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x010a, code lost:

       if (((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0(3, r0, r11))).booleanValue() != false) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x012e, code lost:

       if (((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0(4, r0, r11))).booleanValue() == false) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c4, code lost:

       if (((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0(2, r0, r11))).booleanValue() != false) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0136, code lost:

       if (com.android.internal.telephony.TelephonyPermissions.checkCallingOrSelfReadPhoneState(r8.mContext, r10, r11, r12, "listen") == false) goto L44;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkListenerPermission(
            java.util.Set r9, int r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 488
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.TelephonyRegistry.checkListenerPermission(java.util.Set,"
                    + " int, java.lang.String, java.lang.String):boolean");
    }

    public final boolean checkNotifyPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE")
                == 0) {
            return true;
        }
        Binder.getCallingPid();
        Binder.getCallingUid();
        return false;
    }

    public final void clearPreciseDataConnectionStates(int i) {
        synchronized (this.mRecords) {
            ((Map) ((ArrayList) this.mPreciseDataConnectionStates).get(i)).clear();
        }
    }

    @NeverCompile
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        if (DumpUtils.checkDumpPermission(
                this.mContext, "TelephonyRegistry", indentingPrintWriter)) {
            synchronized (this.mRecords) {
                try {
                    int size = this.mRecords.size();
                    indentingPrintWriter.println("last known state:");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0;
                            i
                                    < ((TelephonyManager) this.mContext.getSystemService("phone"))
                                            .getActiveModemCount();
                            i++) {
                        indentingPrintWriter.println("Phone Id=" + i);
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println("mCallState=" + this.mCallState[i]);
                        indentingPrintWriter.println(
                                "mRingingCallState=" + this.mRingingCallState[i]);
                        indentingPrintWriter.println(
                                "mForegroundCallState=" + this.mForegroundCallState[i]);
                        indentingPrintWriter.println(
                                "mBackgroundCallState=" + this.mBackgroundCallState[i]);
                        indentingPrintWriter.println(
                                "mPreciseCallState=" + this.mPreciseCallState[i]);
                        indentingPrintWriter.println(
                                "mCallDisconnectCause=" + this.mCallDisconnectCause[i]);
                        indentingPrintWriter.println(
                                "mCallIncomingNumber=" + this.mCallIncomingNumber[i]);
                        indentingPrintWriter.println(
                                "mCallStateLists="
                                        + SemTelephonyUtils.callStateListToString(
                                                (List) this.mCallStateLists.get(i)));
                        indentingPrintWriter.println("mServiceState=" + this.mServiceState[i]);
                        indentingPrintWriter.println(
                                "mVoiceActivationState= " + this.mVoiceActivationState[i]);
                        indentingPrintWriter.println(
                                "mDataActivationState= " + this.mDataActivationState[i]);
                        indentingPrintWriter.println(
                                "mUserMobileDataState= " + this.mUserMobileDataState[i]);
                        indentingPrintWriter.println("mSignalStrength=" + this.mSignalStrength[i]);
                        indentingPrintWriter.println("mMessageWaiting=" + this.mMessageWaiting[i]);
                        indentingPrintWriter.println("mCallForwarding=" + this.mCallForwarding[i]);
                        indentingPrintWriter.println("mDataActivity=" + this.mDataActivity[i]);
                        indentingPrintWriter.println(
                                "mDataConnectionState=" + this.mDataConnectionState[i]);
                        indentingPrintWriter.println("mCellIdentity=" + this.mCellIdentity[i]);
                        indentingPrintWriter.println("mCellInfo=" + this.mCellInfo.get(i));
                        indentingPrintWriter.println(
                                "mImsCallDisconnectCause="
                                        + ((ArrayList) this.mImsReasonInfo).get(i));
                        indentingPrintWriter.println("mSrvccState=" + this.mSrvccState[i]);
                        indentingPrintWriter.println(
                                "mCallPreciseDisconnectCause="
                                        + this.mCallPreciseDisconnectCause[i]);
                        indentingPrintWriter.println("mCallQuality=" + this.mCallQuality[i]);
                        indentingPrintWriter.println(
                                "mCallNetworkType=" + this.mCallNetworkType[i]);
                        indentingPrintWriter.println(
                                "mPreciseDataConnectionStates="
                                        + ((ArrayList) this.mPreciseDataConnectionStates).get(i));
                        indentingPrintWriter.println(
                                "mOutgoingCallEmergencyNumber="
                                        + this.mOutgoingCallEmergencyNumber[i]);
                        indentingPrintWriter.println(
                                "mOutgoingSmsEmergencyNumber="
                                        + this.mOutgoingSmsEmergencyNumber[i]);
                        indentingPrintWriter.println(
                                "mBarringInfo=" + ((ArrayList) this.mBarringInfo).get(i));
                        indentingPrintWriter.println(
                                "mCarrierNetworkChangeState=" + this.mCarrierNetworkChangeState[i]);
                        indentingPrintWriter.println(
                                "mTelephonyDisplayInfo=" + this.mTelephonyDisplayInfos[i]);
                        indentingPrintWriter.println("mIsDataEnabled=" + this.mIsDataEnabled[i]);
                        indentingPrintWriter.println(
                                "mDataEnabledReason=" + this.mDataEnabledReason[i]);
                        indentingPrintWriter.println(
                                "mAllowedNetworkTypeReason=" + this.mAllowedNetworkTypeReason[i]);
                        indentingPrintWriter.println(
                                "mAllowedNetworkTypeValue=" + this.mAllowedNetworkTypeValue[i]);
                        indentingPrintWriter.println(
                                "mPhysicalChannelConfigs="
                                        + ((ArrayList) this.mPhysicalChannelConfigs).get(i));
                        indentingPrintWriter.println(
                                "mLinkCapacityEstimateList="
                                        + ((ArrayList) this.mLinkCapacityEstimateLists).get(i));
                        indentingPrintWriter.println("mECBMReason=" + this.mECBMReason[i]);
                        indentingPrintWriter.println("mECBMStarted=" + this.mECBMStarted[i]);
                        indentingPrintWriter.println("mSCBMReason=" + this.mSCBMReason[i]);
                        indentingPrintWriter.println("mSCBMStarted=" + this.mSCBMStarted[i]);
                        indentingPrintWriter.println(
                                "mCarrierRoamingNtnMode=" + this.mCarrierRoamingNtnMode[i]);
                        indentingPrintWriter.println(
                                "mCarrierRoamingNtnEligible=" + this.mCarrierRoamingNtnEligible[i]);
                        indentingPrintWriter.println(
                                "mCarrierRoamingNtnSignalStrength="
                                        + this.mCarrierRoamingNtnSignalStrength[i]);
                        Pair pair = (Pair) ((ArrayList) this.mCarrierPrivilegeStates).get(i);
                        indentingPrintWriter.println(
                                "mCarrierPrivilegeState=<packages="
                                        + pii((List) pair.first)
                                        + ", uids="
                                        + Arrays.toString((int[]) pair.second)
                                        + ">");
                        Pair pair2 = (Pair) ((ArrayList) this.mCarrierServiceStates).get(i);
                        StringBuilder sb = new StringBuilder();
                        sb.append("mCarrierServiceState=<package=");
                        String str = (String) pair2.first;
                        if (!Build.IS_DEBUGGABLE) {
                            str = "***";
                        }
                        sb.append(str);
                        sb.append(", uid=");
                        sb.append(pair2.second);
                        sb.append(">");
                        indentingPrintWriter.println(sb.toString());
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.println("mPhoneCapability=" + this.mPhoneCapability);
                    indentingPrintWriter.println("mActiveDataSubId=" + this.mActiveDataSubId);
                    indentingPrintWriter.println("mRadioPowerState=" + this.mRadioPowerState);
                    indentingPrintWriter.println(
                            "mEmergencyNumberList=" + this.mEmergencyNumberList);
                    indentingPrintWriter.println("mDefaultPhoneId=" + this.mDefaultPhoneId);
                    indentingPrintWriter.println("mDefaultSubId=" + this.mDefaultSubId);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("local logs:");
                    indentingPrintWriter.increaseIndent();
                    this.mLocalLog.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("listen logs:");
                    indentingPrintWriter.increaseIndent();
                    this.mListenLog.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("registrations: count=" + size);
                    indentingPrintWriter.increaseIndent();
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println((Record) it.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final int getPhoneIdFromSubId(int i) {
        SubscriptionManager subscriptionManager =
                (SubscriptionManager)
                        this.mContext.getSystemService("telephony_subscription_service");
        if (subscriptionManager == null) {
            return -1;
        }
        if (i == Integer.MAX_VALUE) {
            i = SubscriptionManager.getDefaultSubscriptionId();
        }
        SubscriptionInfo activeSubscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(i);
        if (activeSubscriptionInfo == null) {
            return -1;
        }
        return activeSubscriptionInfo.getSimSlotIndex();
    }

    public final void handleRemoveListLocked() {
        if (this.mRemoveList.size() > 0) {
            Iterator it = this.mRemoveList.iterator();
            while (it.hasNext()) {
                remove((IBinder) it.next());
            }
            this.mRemoveList.clear();
        }
    }

    public final boolean idMatch(Record record, int i, int i2) {
        if (i < 0) {
            return record.phoneId == i2;
        }
        int i3 = record.subId;
        return i3 == Integer.MAX_VALUE ? i == this.mDefaultSubId : i3 == i;
    }

    public final void listen(
            boolean z,
            boolean z2,
            String str,
            String str2,
            IPhoneStateListener iPhoneStateListener,
            Set set,
            boolean z3,
            int i) {
        SemSatelliteSignalStrength semSatelliteSignalStrength;
        SemSatelliteServiceState semSatelliteServiceState;
        CallState callState;
        int callingUserId = UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        StringBuilder sb = new StringBuilder("listen: E pkg=");
        sb.append(Build.IS_DEBUGGABLE ? str : "***");
        sb.append(" uid=");
        sb.append(Binder.getCallingUid());
        sb.append(" events=");
        sb.append(set);
        sb.append(" notifyNow=");
        sb.append(z3);
        sb.append(" subId=");
        sb.append(i);
        sb.append(" myUserId=");
        sb.append(UserHandle.myUserId());
        sb.append(" callerUserId=");
        sb.append(callingUserId);
        this.mListenLog.log(sb.toString());
        if (set.isEmpty()) {
            set.clear();
            remove(iPhoneStateListener.asBinder());
            return;
        }
        if (checkListenerPermission(set, i, str, str2)) {
            if (!SubscriptionManager.isValidSubscriptionId(i)) {
                i = Integer.MAX_VALUE;
            }
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                try {
                    Record add =
                            add(
                                    Binder.getCallingUid(),
                                    Binder.getCallingPid(),
                                    iPhoneStateListener.asBinder(),
                                    doesLimitApplyForListeners(
                                            Binder.getCallingUid(), Process.myUid()));
                    if (add == null) {
                        return;
                    }
                    add.context = this.mContext;
                    add.callback = iPhoneStateListener;
                    add.callingPackage = str;
                    add.callingFeatureId = str2;
                    add.renounceCoarseLocationAccess = z2;
                    add.renounceFineLocationAccess = z;
                    add.callerUid = Binder.getCallingUid();
                    add.callerPid = Binder.getCallingPid();
                    add.subId = i;
                    add.phoneId = phoneIdFromSubId;
                    add.eventList = set;
                    if (z3 && validatePhoneId(phoneIdFromSubId)) {
                        if (set.contains(1)) {
                            try {
                                ServiceState serviceState =
                                        new ServiceState(this.mServiceState[add.phoneId]);
                                if (checkFineLocationAccess(add, 29)) {
                                    add.callback.onServiceStateChanged(serviceState);
                                } else if (checkCoarseLocationAccess(add, 29)) {
                                    add.callback.onServiceStateChanged(
                                            serviceState.createLocationInfoSanitizedCopy(false));
                                } else {
                                    add.callback.onServiceStateChanged(
                                            serviceState.createLocationInfoSanitizedCopy(true));
                                }
                            } catch (RemoteException unused) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(2)) {
                            try {
                                SignalStrength signalStrength = this.mSignalStrength[add.phoneId];
                                if (signalStrength != null) {
                                    int gsmSignalStrength = signalStrength.getGsmSignalStrength();
                                    IPhoneStateListener iPhoneStateListener2 = add.callback;
                                    if (gsmSignalStrength == 99) {
                                        gsmSignalStrength = -1;
                                    }
                                    iPhoneStateListener2.onSignalStrengthChanged(gsmSignalStrength);
                                }
                            } catch (RemoteException unused2) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(3)) {
                            try {
                                add.callback.onMessageWaitingIndicatorChanged(
                                        this.mMessageWaiting[add.phoneId]);
                            } catch (RemoteException unused3) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(4)) {
                            try {
                                add.callback.onCallForwardingIndicatorChanged(
                                        this.mCallForwarding[add.phoneId]);
                            } catch (RemoteException unused4) {
                                remove(add.binder);
                            }
                        }
                        if (validateEventAndUserLocked(add, 5)) {
                            try {
                                if (checkCoarseLocationAccess(add, 1)
                                        && checkFineLocationAccess(add, 29)) {
                                    add.callback.onCellLocationChanged(
                                            this.mCellIdentity[add.phoneId]);
                                }
                            } catch (RemoteException unused5) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(36)) {
                            try {
                                IPhoneStateListener iPhoneStateListener3 = add.callback;
                                int[] iArr = this.mCallState;
                                int i2 = add.phoneId;
                                iPhoneStateListener3.onLegacyCallStateChanged(
                                        iArr[i2],
                                        add.canReadCallLog() ? this.mCallIncomingNumber[i2] : "");
                            } catch (RemoteException unused6) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(6)) {
                            try {
                                add.callback.onCallStateChanged(this.mCallState[add.phoneId]);
                            } catch (RemoteException unused7) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(7)) {
                            try {
                                IPhoneStateListener iPhoneStateListener4 = add.callback;
                                int[] iArr2 = this.mDataConnectionState;
                                int i3 = add.phoneId;
                                iPhoneStateListener4.onDataConnectionStateChanged(
                                        iArr2[i3], this.mDataConnectionNetworkType[i3]);
                            } catch (RemoteException unused8) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(8)) {
                            try {
                                add.callback.onDataActivity(this.mDataActivity[add.phoneId]);
                            } catch (RemoteException unused9) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(9)) {
                            try {
                                SignalStrength signalStrength2 = this.mSignalStrength[add.phoneId];
                                if (signalStrength2 != null) {
                                    add.callback.onSignalStrengthsChanged(signalStrength2);
                                }
                            } catch (RemoteException unused10) {
                                remove(add.binder);
                            }
                        }
                        if (validateEventAndUserLocked(add, 11)) {
                            try {
                                if (checkCoarseLocationAccess(add, 1)
                                        && checkFineLocationAccess(add, 29)) {
                                    add.callback.onCellInfoChanged(
                                            (List) this.mCellInfo.get(add.phoneId));
                                }
                            } catch (RemoteException unused11) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(12)) {
                            try {
                                add.callback.onPreciseCallStateChanged(
                                        this.mPreciseCallState[add.phoneId]);
                            } catch (RemoteException unused12) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(26)) {
                            try {
                                IPhoneStateListener iPhoneStateListener5 = add.callback;
                                int[] iArr3 = this.mCallDisconnectCause;
                                int i4 = add.phoneId;
                                iPhoneStateListener5.onCallDisconnectCauseChanged(
                                        iArr3[i4], this.mCallPreciseDisconnectCause[i4]);
                            } catch (RemoteException unused13) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(28)) {
                            ImsReasonInfo imsReasonInfo =
                                    (ImsReasonInfo)
                                            ((ArrayList) this.mImsReasonInfo).get(add.phoneId);
                            if (imsReasonInfo != null) {
                                try {
                                    add.callback.onImsCallDisconnectCauseChanged(imsReasonInfo);
                                } catch (RemoteException unused14) {
                                    remove(add.binder);
                                }
                            }
                        }
                        if (set.contains(13)) {
                            try {
                                Iterator it =
                                        ((Map)
                                                        ((ArrayList)
                                                                        this
                                                                                .mPreciseDataConnectionStates)
                                                                .get(add.phoneId))
                                                .values()
                                                .iterator();
                                while (it.hasNext()) {
                                    add.callback.onPreciseDataConnectionStateChanged(
                                            (PreciseDataConnectionState) it.next());
                                }
                            } catch (RemoteException unused15) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(17)) {
                            try {
                                add.callback.onCarrierNetworkChange(
                                        this.mCarrierNetworkChangeState[add.phoneId]);
                            } catch (RemoteException unused16) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(18)) {
                            try {
                                add.callback.onVoiceActivationStateChanged(
                                        this.mVoiceActivationState[add.phoneId]);
                            } catch (RemoteException unused17) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(19)) {
                            try {
                                add.callback.onDataActivationStateChanged(
                                        this.mDataActivationState[add.phoneId]);
                            } catch (RemoteException unused18) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(20)) {
                            try {
                                add.callback.onUserMobileDataStateChanged(
                                        this.mUserMobileDataState[add.phoneId]);
                            } catch (RemoteException unused19) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(21)) {
                            try {
                                TelephonyDisplayInfo telephonyDisplayInfo =
                                        this.mTelephonyDisplayInfos[add.phoneId];
                                if (telephonyDisplayInfo != null) {
                                    add.callback.onDisplayInfoChanged(telephonyDisplayInfo);
                                }
                            } catch (RemoteException unused20) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(25)) {
                            try {
                                add.callback.onEmergencyNumberListChanged(
                                        this.mEmergencyNumberList);
                            } catch (RemoteException unused21) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(22)) {
                            try {
                                add.callback.onPhoneCapabilityChanged(this.mPhoneCapability);
                            } catch (RemoteException unused22) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(23)) {
                            try {
                                add.callback.onActiveDataSubIdChanged(this.mActiveDataSubId);
                            } catch (RemoteException unused23) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(24)) {
                            try {
                                add.callback.onRadioPowerStateChanged(this.mRadioPowerState);
                            } catch (RemoteException unused24) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(16)) {
                            try {
                                add.callback.onSrvccStateChanged(this.mSrvccState[add.phoneId]);
                            } catch (RemoteException unused25) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(27)) {
                            try {
                                add.callback.onCallStatesChanged(
                                        (List) this.mCallStateLists.get(add.phoneId));
                            } catch (RemoteException unused26) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(32)) {
                            BarringInfo barringInfo =
                                    (BarringInfo) ((ArrayList) this.mBarringInfo).get(add.phoneId);
                            if (barringInfo != null) {
                                BarringInfo createLocationInfoSanitizedCopy =
                                        barringInfo.createLocationInfoSanitizedCopy();
                                try {
                                    IPhoneStateListener iPhoneStateListener6 = add.callback;
                                    if (!checkFineLocationAccess(add, 1)) {
                                        barringInfo = createLocationInfoSanitizedCopy;
                                    }
                                    iPhoneStateListener6.onBarringInfoChanged(barringInfo);
                                } catch (RemoteException unused27) {
                                    remove(add.binder);
                                }
                            }
                        }
                        if (set.contains(33)) {
                            try {
                                IPhoneStateListener iPhoneStateListener7 = add.callback;
                                int i5 = add.callerUid;
                                iPhoneStateListener7.onPhysicalChannelConfigChanged(
                                        (i5 == 1001 || i5 == 1000)
                                                ? (List)
                                                        ((ArrayList) this.mPhysicalChannelConfigs)
                                                                .get(add.phoneId)
                                                : getLocationSanitizedConfigs(
                                                        (List)
                                                                ((ArrayList)
                                                                                this
                                                                                        .mPhysicalChannelConfigs)
                                                                        .get(add.phoneId)));
                            } catch (RemoteException unused28) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(34)) {
                            try {
                                IPhoneStateListener iPhoneStateListener8 = add.callback;
                                boolean[] zArr = this.mIsDataEnabled;
                                int i6 = add.phoneId;
                                iPhoneStateListener8.onDataEnabledChanged(
                                        zArr[i6], this.mDataEnabledReason[i6]);
                            } catch (RemoteException unused29) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(41)) {
                            try {
                                add.callback.onSimultaneousCallingStateChanged(
                                        this.mSimultaneousCellularCallingSubIds);
                            } catch (RemoteException unused30) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(37)) {
                            try {
                                if (((ArrayList) this.mLinkCapacityEstimateLists).get(add.phoneId)
                                        != null) {
                                    add.callback.onLinkCapacityEstimateChanged(
                                            (List)
                                                    ((ArrayList) this.mLinkCapacityEstimateLists)
                                                            .get(add.phoneId));
                                }
                            } catch (RemoteException unused31) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(39)) {
                            Iterator it2 =
                                    ((List) this.mCallStateLists.get(add.phoneId)).iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    callState = null;
                                    break;
                                } else {
                                    callState = (CallState) it2.next();
                                    if (callState.getCallState() == 1) {
                                        break;
                                    }
                                }
                            }
                            if (callState != null) {
                                String imsCallSessionId = callState.getImsCallSessionId();
                                try {
                                    MediaQualityStatus mediaQualityStatus =
                                            (MediaQualityStatus)
                                                    ((SparseArray)
                                                                    ((ArrayList)
                                                                                    this
                                                                                            .mMediaQualityStatus)
                                                                            .get(add.phoneId))
                                                            .get(1);
                                    if (mediaQualityStatus != null
                                            && mediaQualityStatus
                                                    .getCallSessionId()
                                                    .equals(imsCallSessionId)) {
                                        add.callback.onMediaQualityStatusChanged(
                                                mediaQualityStatus);
                                    }
                                    MediaQualityStatus mediaQualityStatus2 =
                                            (MediaQualityStatus)
                                                    ((SparseArray)
                                                                    ((ArrayList)
                                                                                    this
                                                                                            .mMediaQualityStatus)
                                                                            .get(add.phoneId))
                                                            .get(2);
                                    if (mediaQualityStatus2 != null
                                            && mediaQualityStatus2
                                                    .getCallSessionId()
                                                    .equals(imsCallSessionId)) {
                                        add.callback.onMediaQualityStatusChanged(
                                                mediaQualityStatus2);
                                    }
                                } catch (RemoteException unused32) {
                                    remove(add.binder);
                                }
                            }
                        }
                        if (set.contains(40)) {
                            try {
                                boolean[] zArr2 = this.mECBMStarted;
                                int i7 = add.phoneId;
                                if (zArr2[i7]) {
                                    add.callback.onCallBackModeStarted(1);
                                } else {
                                    add.callback.onCallBackModeStopped(1, this.mECBMReason[i7]);
                                }
                                boolean[] zArr3 = this.mSCBMStarted;
                                int i8 = add.phoneId;
                                if (zArr3[i8]) {
                                    add.callback.onCallBackModeStarted(2);
                                } else {
                                    add.callback.onCallBackModeStopped(2, this.mSCBMReason[i8]);
                                }
                            } catch (RemoteException unused33) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(42)) {
                            try {
                                add.callback.onCarrierRoamingNtnModeChanged(
                                        this.mCarrierRoamingNtnMode[add.phoneId]);
                            } catch (RemoteException unused34) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(43)) {
                            try {
                                add.callback.onCarrierRoamingNtnEligibleStateChanged(
                                        this.mCarrierRoamingNtnEligible[add.phoneId]);
                            } catch (RemoteException unused35) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(44)) {
                            try {
                                add.callback.onCarrierRoamingNtnAvailableServicesChanged(
                                        ((IntArray)
                                                        ((ArrayList)
                                                                        this
                                                                                .mCarrierRoamingNtnAvailableServices)
                                                                .get(add.phoneId))
                                                .toArray());
                            } catch (RemoteException unused36) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(45)) {
                            try {
                                add.callback.onCarrierRoamingNtnSignalStrengthChanged(
                                        this.mCarrierRoamingNtnSignalStrength[add.phoneId]);
                            } catch (RemoteException unused37) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(10000)
                                && (semSatelliteServiceState = this.mSatServiceState) != null) {
                            try {
                                add.callback.onSemSatelliteServiceStateChanged(
                                        semSatelliteServiceState);
                            } catch (RemoteException unused38) {
                                remove(add.binder);
                            }
                        }
                        if (set.contains(10001)
                                && (semSatelliteSignalStrength = this.mSatSignalStrength) != null) {
                            try {
                                add.callback.onSemSatelliteSignalStrengthChanged(
                                        semSatelliteSignalStrength);
                            } catch (RemoteException unused39) {
                                remove(add.binder);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void listenWithEventList(
            boolean z,
            boolean z2,
            int i,
            String str,
            String str2,
            IPhoneStateListener iPhoneStateListener,
            int[] iArr,
            boolean z3) {
        listen(
                z,
                z2,
                str,
                str2,
                iPhoneStateListener,
                (Set) Arrays.stream(iArr).boxed().collect(Collectors.toSet()),
                z3,
                i);
    }

    public final void notifyActiveDataSubIdChanged(int i) {
        if (checkNotifyPermission()) {
            log("notifyActiveDataSubIdChanged: activeDataSubId=" + i);
            this.mLocalLog.log("notifyActiveDataSubIdChanged: activeDataSubId=" + i);
            this.mActiveDataSubId = i;
            synchronized (this.mRecords) {
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(23)) {
                        try {
                            record.callback.onActiveDataSubIdChanged(i);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mAllowedNetworkTypeReason[i] = i3;
                    this.mAllowedNetworkTypeValue[i] = j;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(35) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onAllowedNetworkTypesChanged(i3, j);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyBarringInfoChanged(int i, int i2, BarringInfo barringInfo) {
        if (checkNotifyPermission()) {
            if (!validatePhoneId(i)) {
                loge("Received invalid phoneId for BarringInfo = " + i);
                return;
            }
            synchronized (this.mRecords) {
                try {
                    if (barringInfo == null) {
                        loge("Received null BarringInfo for subId=" + i2 + ", phoneId=" + i);
                        ((ArrayList) this.mBarringInfo).set(i, new BarringInfo());
                        return;
                    }
                    if (barringInfo.equals(((ArrayList) this.mBarringInfo).get(i))) {
                        return;
                    }
                    ((ArrayList) this.mBarringInfo).set(i, barringInfo);
                    BarringInfo createLocationInfoSanitizedCopy =
                            barringInfo.createLocationInfoSanitizedCopy();
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(32) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onBarringInfoChanged(
                                        checkFineLocationAccess(record, 1)
                                                ? barringInfo
                                                : createLocationInfoSanitizedCopy);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyCallForwardingChanged(boolean z) {
        notifyCallForwardingChangedForSubscriber(Integer.MAX_VALUE, z);
    }

    public final void notifyCallForwardingChangedForSubscriber(int i, boolean z) {
        if (checkNotifyPermission()) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                if (validatePhoneId(phoneIdFromSubId)) {
                    this.mCallForwarding[phoneIdFromSubId] = z;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(4)
                                && idMatch(record, i, phoneIdFromSubId)) {
                            try {
                                record.callback.onCallForwardingIndicatorChanged(z);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyCallQualityChanged(CallQuality callQuality, int i, int i2, int i3) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        int[] iArr = this.mCallNetworkType;
                        boolean z = iArr[i] != i3;
                        this.mCallQuality[i] = callQuality;
                        iArr[i] = i3;
                        if (((List) this.mCallStateLists.get(i)).size() <= 0) {
                            log("There is no call to report CallQuality");
                            return;
                        }
                        if (((CallState) ((List) this.mCallStateLists.get(i)).get(0)).getCallState()
                                        != 1
                                && !z) {
                            log(
                                    "There is no active call to report CallQuality and call network"
                                        + " type is not changed");
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        arrayList.addAll((Collection) this.mCallStateLists.get(i));
                        ((List) this.mCallStateLists.get(i)).clear();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            CallState callState = (CallState) it.next();
                            ((List) this.mCallStateLists.get(i))
                                    .add(
                                            new CallState.Builder(callState.getCallState())
                                                    .setNetworkType(i3)
                                                    .setCallQuality(
                                                            callState.getCallState() == 1
                                                                    ? callQuality
                                                                    : createCallQuality())
                                                    .setCallClassification(
                                                            callState.getCallClassification())
                                                    .setImsCallSessionId(
                                                            callState.getImsCallSessionId())
                                                    .setImsCallServiceType(
                                                            callState.getImsCallServiceType())
                                                    .setImsCallType(callState.getImsCallType())
                                                    .build());
                        }
                        log(
                                "notifyCallQualityChanged - phoneId: "
                                        + i
                                        + ", subId: "
                                        + i2
                                        + ", callNetworkType: "
                                        + i3
                                        + ", callState: "
                                        + SemTelephonyUtils.callStateListToString(
                                                (List) this.mCallStateLists.get(i)));
                        Iterator it2 = this.mRecords.iterator();
                        while (it2.hasNext()) {
                            Record record = (Record) it2.next();
                            if (record.matchTelephonyCallbackEvent(27) && idMatch(record, i2, i)) {
                                try {
                                    record.callback.onCallStatesChanged(
                                            (List) this.mCallStateLists.get(i));
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyCallState(int i, int i2, int i3, String str) {
        int i4;
        int i5;
        if (!checkNotifyPermission()) {
            log("notifyCallState: checkNotifyPermission condition not met");
            return;
        }
        this.mLocalLog.log("notifyCallState: subId=" + i2 + " state=" + i3);
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mCallState[i] = i3;
                this.mCallIncomingNumber[i] = str;
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(36)
                            && (i5 = record.subId) == i2
                            && i5 != Integer.MAX_VALUE) {
                        try {
                            record.callback.onLegacyCallStateChanged(
                                    i3, record.canReadCallLog() ? this.mCallIncomingNumber[i] : "");
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                    if (record.matchTelephonyCallbackEvent(6)
                            && (i4 = record.subId) == i2
                            && i4 != Integer.MAX_VALUE) {
                        try {
                            record.callback.onCallStateChanged(i3);
                        } catch (RemoteException unused2) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
        broadcastCallStateChanged(i3, i, i2, str);
    }

    public final void notifyCallStateForAllSubs(int i, String str) {
        if (checkNotifyPermission()) {
            this.mLocalLog.log("notifyCallStateForAllSubs: state=" + i);
            synchronized (this.mRecords) {
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(36)
                            && record.subId == Integer.MAX_VALUE) {
                        try {
                            record.callback.onLegacyCallStateChanged(
                                    i, record.canReadCallLog() ? str : "");
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                    if (record.matchTelephonyCallbackEvent(6)
                            && record.subId == Integer.MAX_VALUE) {
                        try {
                            record.callback.onCallStateChanged(i);
                        } catch (RemoteException unused2) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
            broadcastCallStateChanged(i, -1, -1, str);
        }
    }

    public final void notifyCallbackModeStarted(int i, int i2, int i3) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        if (i3 == 1) {
                            this.mECBMStarted[i] = true;
                        } else if (i3 == 2) {
                            this.mSCBMStarted[i] = true;
                        }
                    }
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(40)) {
                            try {
                                record.callback.onCallBackModeStarted(i3);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyCallbackModeStopped(int i, int i2, int i3, int i4) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        if (i3 == 1) {
                            this.mECBMStarted[i] = false;
                            this.mECBMReason[i] = i4;
                        } else if (i3 == 2) {
                            this.mSCBMStarted[i] = false;
                            this.mSCBMReason[i] = i4;
                        }
                    }
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(40)) {
                            try {
                                record.callback.onCallBackModeStopped(i3, i4);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) {
        if (!validatePhoneId(i)) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid phoneId: "));
        }
        if (!checkNotifyPermission()) {
            loge("Caller has no notify permission!");
            return;
        }
        synchronized (this.mRecords) {
            this.mRemoveList.clear();
            Iterator it = this.mRecords.iterator();
            while (it.hasNext()) {
                Record record = (Record) it.next();
                ICarrierConfigChangeListener iCarrierConfigChangeListener =
                        record.carrierConfigChangeListener;
                if (iCarrierConfigChangeListener != null) {
                    try {
                        iCarrierConfigChangeListener.onCarrierConfigChanged(i, i2, i3, i4);
                    } catch (RemoteException unused) {
                        this.mRemoveList.add(record.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyCarrierNetworkChange(boolean z) {
        int[] array =
                Arrays.stream(
                                SubscriptionManager.from(this.mContext)
                                        .getCompleteActiveSubscriptionIdList())
                        .filter(
                                new IntPredicate() { // from class:
                                                     // com.android.server.TelephonyRegistry$$ExternalSyntheticLambda1
                                    @Override // java.util.function.IntPredicate
                                    public final boolean test(int i) {
                                        return TelephonyPermissions.checkCarrierPrivilegeForSubId(
                                                TelephonyRegistry.this.mContext, i);
                                    }
                                })
                        .toArray();
        if (ArrayUtils.isEmpty(array)) {
            loge("notifyCarrierNetworkChange without carrier privilege");
            throw new SecurityException("notifyCarrierNetworkChange without carrier privilege");
        }
        for (int i : array) {
            notifyCarrierNetworkChangeWithPermission(i, z);
        }
    }

    public final void notifyCarrierNetworkChangeWithPermission(int i, boolean z) {
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            this.mCarrierNetworkChangeState[phoneIdFromSubId] = z;
            Iterator it = this.mRecords.iterator();
            while (it.hasNext()) {
                Record record = (Record) it.next();
                if (record.matchTelephonyCallbackEvent(17)
                        && idMatch(record, i, phoneIdFromSubId)) {
                    try {
                        record.callback.onCarrierNetworkChange(z);
                    } catch (RemoteException unused) {
                        this.mRemoveList.add(record.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyCarrierNetworkChangeWithSubId(int i, boolean z) {
        if (!TelephonyPermissions.checkCarrierPrivilegeForSubId(this.mContext, i)) {
            throw new SecurityException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "notifyCarrierNetworkChange without carrier privilege on subId "));
        }
        notifyCarrierNetworkChangeWithPermission(i, z);
    }

    public final void notifyCarrierPrivilegesChanged(int i, List list, int[] iArr) {
        if (checkNotifyPermission()) {
            onMultiSimConfigChanged();
            synchronized (this.mRecords) {
                if (!validatePhoneId(i)) {
                    throw new IllegalArgumentException("Invalid slot index: " + i);
                }
                ((ArrayList) this.mCarrierPrivilegeStates).set(i, new Pair(list, iArr));
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.carrierPrivilegesCallback != null && idMatch(record, -1, i)) {
                        try {
                            record.carrierPrivilegesCallback.onCarrierPrivilegesChanged(
                                    Collections.unmodifiableList(list),
                                    Arrays.copyOf(iArr, iArr.length));
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyCarrierRoamingNtnAvailableServicesChanged(int i, int[] iArr) {
        if (!checkNotifyPermission()) {
            log(
                    "notifyCarrierRoamingNtnAvailableServicesChanged: caller does not have required"
                        + " permissions.");
            return;
        }
        synchronized (this.mRecords) {
            try {
                int phoneIdFromSubId = getPhoneIdFromSubId(i);
                if (!validatePhoneId(phoneIdFromSubId)) {
                    loge("Invalid phone ID " + phoneIdFromSubId + " for " + i);
                    return;
                }
                IntArray intArray = new IntArray(iArr.length);
                intArray.addAll(iArr);
                ((ArrayList) this.mCarrierRoamingNtnAvailableServices)
                        .set(phoneIdFromSubId, intArray);
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(44)
                            && idMatch(record, i, phoneIdFromSubId)) {
                        try {
                            record.callback.onCarrierRoamingNtnAvailableServicesChanged(iArr);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyCarrierRoamingNtnEligibleStateChanged(int i, boolean z) {
        if (!checkNotifyPermission()) {
            log(
                    "notifyCarrierRoamingNtnEligibleStateChanged: caller does not have required"
                        + " permissions.");
            return;
        }
        synchronized (this.mRecords) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            this.mCarrierRoamingNtnEligible[phoneIdFromSubId] = z;
            Iterator it = this.mRecords.iterator();
            while (it.hasNext()) {
                Record record = (Record) it.next();
                if (record.matchTelephonyCallbackEvent(43)
                        && idMatch(record, i, phoneIdFromSubId)) {
                    try {
                        record.callback.onCarrierRoamingNtnEligibleStateChanged(z);
                    } catch (RemoteException unused) {
                        this.mRemoveList.add(record.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyCarrierRoamingNtnModeChanged(int i, boolean z) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                int phoneIdFromSubId = getPhoneIdFromSubId(i);
                this.mCarrierRoamingNtnMode[phoneIdFromSubId] = z;
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(42)
                            && idMatch(record, i, phoneIdFromSubId)) {
                        try {
                            record.callback.onCarrierRoamingNtnModeChanged(z);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyCarrierRoamingNtnSignalStrengthChanged(
            int i, NtnSignalStrength ntnSignalStrength) {
        if (!checkNotifyPermission()) {
            log(
                    "nnotifyCarrierRoamingNtnSignalStrengthChanged: caller does not have required"
                        + " permissions.");
            return;
        }
        synchronized (this.mRecords) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            this.mCarrierRoamingNtnSignalStrength[phoneIdFromSubId] = ntnSignalStrength;
            Iterator it = this.mRecords.iterator();
            while (it.hasNext()) {
                Record record = (Record) it.next();
                if (record.matchTelephonyCallbackEvent(45)
                        && idMatch(record, i, phoneIdFromSubId)) {
                    try {
                        record.callback.onCarrierRoamingNtnSignalStrengthChanged(ntnSignalStrength);
                    } catch (RemoteException unused) {
                        this.mRemoveList.add(record.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyCarrierServiceChanged(int i, String str, int i2) {
        if (checkNotifyPermission() && validatePhoneId(i)) {
            onMultiSimConfigChanged();
            synchronized (this.mRecords) {
                ((ArrayList) this.mCarrierServiceStates).set(i, new Pair(str, Integer.valueOf(i2)));
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.carrierPrivilegesCallback != null && idMatch(record, -1, i)) {
                        try {
                            record.carrierPrivilegesCallback.onCarrierServiceChanged(str, i2);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyCellInfo(List list) {
        notifyCellInfoForSubscriber(Integer.MAX_VALUE, list);
    }

    public final void notifyCellInfoForSubscriber(int i, List list) {
        if (checkNotifyPermission()) {
            if (list == null) {
                loge("notifyCellInfoForSubscriber() received a null list");
                list = Collections.EMPTY_LIST;
            }
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                if (validatePhoneId(phoneIdFromSubId)) {
                    this.mCellInfo.set(phoneIdFromSubId, list);
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (validateEventAndUserLocked(record, 11)
                                && idMatch(record, i, phoneIdFromSubId)
                                && checkCoarseLocationAccess(record, 1)
                                && checkFineLocationAccess(record, 29)) {
                            try {
                                record.callback.onCellInfoChanged(list);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyCellLocationForSubscriber(int i, CellIdentity cellIdentity) {
        notifyCellLocationForSubscriber(i, cellIdentity, false);
    }

    public final void notifyCellLocationForSubscriber(int i, CellIdentity cellIdentity, boolean z) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "notifyCellLocationForSubscriber: subId=", " cellIdentity=");
        m.append(Rlog.pii(false, cellIdentity));
        log(m.toString());
        if (checkNotifyPermission()) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(phoneIdFromSubId)) {
                        if (!z) {
                            if (!Objects.equals(
                                    cellIdentity, this.mCellIdentity[phoneIdFromSubId])) {}
                        }
                        this.mCellIdentity[phoneIdFromSubId] = cellIdentity;
                        Iterator it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            Record record = (Record) it.next();
                            if (validateEventAndUserLocked(record, 5)
                                    && idMatch(record, i, phoneIdFromSubId)
                                    && checkCoarseLocationAccess(record, 1)
                                    && checkFineLocationAccess(record, 29)) {
                                try {
                                    record.callback.onCellLocationChanged(cellIdentity);
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyDataActivityForSubscriber(int i, int i2) {
        if (checkNotifyPermission()) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                if (validatePhoneId(phoneIdFromSubId)) {
                    this.mDataActivity[phoneIdFromSubId] = i2;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(8)
                                && idMatch(record, i, phoneIdFromSubId)) {
                            try {
                                record.callback.onDataActivity(i2);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mDataActivity[i] = i3;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(8) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onDataActivity(i3);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyDataConnectionForSubscriber(
            int i, int i2, PreciseDataConnectionState preciseDataConnectionState) {
        int i3;
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i) && preciseDataConnectionState.getApnSetting() != null) {
                    Pair create =
                            Pair.create(
                                    Integer.valueOf(preciseDataConnectionState.getTransportType()),
                                    preciseDataConnectionState.getApnSetting());
                    if (!Objects.equals(
                            (PreciseDataConnectionState)
                                    ((Map) ((ArrayList) this.mPreciseDataConnectionStates).get(i))
                                            .remove(create),
                            preciseDataConnectionState)) {
                        Iterator it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            Record record = (Record) it.next();
                            if (record.matchTelephonyCallbackEvent(13) && idMatch(record, i2, i)) {
                                try {
                                    record.callback.onPreciseDataConnectionStateChanged(
                                            preciseDataConnectionState);
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                        handleRemoveListLocked();
                        broadcastDataConnectionStateChanged(i, i2, preciseDataConnectionState);
                        String str =
                                "notifyDataConnectionForSubscriber: phoneId="
                                        + i
                                        + " subId="
                                        + i2
                                        + " "
                                        + preciseDataConnectionState;
                        log(str);
                        this.mLocalLog.log(str);
                    }
                    if (preciseDataConnectionState.getState() != 0) {
                        ((Map) ((ArrayList) this.mPreciseDataConnectionStates).get(i))
                                .put(create, preciseDataConnectionState);
                    }
                    ArrayMap arrayMap = new ArrayMap();
                    int i4 = 0;
                    if (preciseDataConnectionState.getState() == 0
                            && preciseDataConnectionState
                                    .getApnSetting()
                                    .getApnTypes()
                                    .contains(17)) {
                        arrayMap.put(0, preciseDataConnectionState);
                    }
                    for (Map.Entry entry :
                            ((Map) ((ArrayList) this.mPreciseDataConnectionStates).get(i))
                                    .entrySet()) {
                        if (((Integer) ((Pair) entry.getKey()).first).intValue() == 1
                                && ((ApnSetting) ((Pair) entry.getKey()).second)
                                        .getApnTypes()
                                        .contains(17)) {
                            arrayMap.put(
                                    Integer.valueOf(
                                            ((PreciseDataConnectionState) entry.getValue())
                                                    .getState()),
                                    (PreciseDataConnectionState) entry.getValue());
                        }
                    }
                    int[] iArr = {2, 3, 1, 4, 0};
                    int i5 = 0;
                    while (true) {
                        if (i5 >= 5) {
                            i3 = 0;
                            break;
                        }
                        int i6 = iArr[i5];
                        if (arrayMap.containsKey(Integer.valueOf(i6))) {
                            i3 =
                                    ((PreciseDataConnectionState) arrayMap.get(Integer.valueOf(i6)))
                                            .getNetworkType();
                            i4 = i6;
                            break;
                        }
                        i5++;
                    }
                    if (this.mDataConnectionState[i] != i4
                            || this.mDataConnectionNetworkType[i] != i3) {
                        String str2 =
                                "onDataConnectionStateChanged("
                                        + TelephonyUtils.dataStateToString(i4)
                                        + ", "
                                        + TelephonyManager.getNetworkTypeName(i3)
                                        + ") subId="
                                        + i2
                                        + ", phoneId="
                                        + i;
                        log(str2);
                        this.mLocalLog.log(str2);
                        Iterator it2 = this.mRecords.iterator();
                        while (it2.hasNext()) {
                            Record record2 = (Record) it2.next();
                            if (record2.matchTelephonyCallbackEvent(7) && idMatch(record2, i2, i)) {
                                try {
                                    record2.callback.onDataConnectionStateChanged(i4, i3);
                                } catch (RemoteException unused2) {
                                    this.mRemoveList.add(record2.binder);
                                }
                            }
                        }
                        this.mDataConnectionState[i] = i4;
                        this.mDataConnectionNetworkType[i] = i3;
                        handleRemoveListLocked();
                    }
                }
            }
        }
    }

    public final void notifyDataEnabled(int i, int i2, boolean z, int i3) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mIsDataEnabled[i] = z;
                    this.mDataEnabledReason[i] = i3;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(34) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onDataEnabledChanged(z, i3);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyDisconnectCause(int i, int i2, int i3, int i4) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mCallDisconnectCause[i] = i3;
                    this.mCallPreciseDisconnectCause[i] = i4;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(26) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onCallDisconnectCauseChanged(
                                        this.mCallDisconnectCause[i],
                                        this.mCallPreciseDisconnectCause[i]);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyDisplayInfoChanged(
            int i, int i2, TelephonyDisplayInfo telephonyDisplayInfo) {
        if (checkNotifyPermission()) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "notifyDisplayInfoChanged: PhoneId=",
                            " subId=",
                            " telephonyDisplayInfo=");
            m.append(telephonyDisplayInfo);
            this.mLocalLog.log(m.toString());
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        this.mTelephonyDisplayInfos[i] = telephonyDisplayInfo;
                        Iterator it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            Record record = (Record) it.next();
                            if (record.matchTelephonyCallbackEvent(21) && idMatch(record, i2, i)) {
                                try {
                                    ConfigurationProvider configurationProvider =
                                            this.mConfigurationProvider;
                                    String str = record.callingPackage;
                                    UserHandle callingUserHandle = Binder.getCallingUserHandle();
                                    configurationProvider.getClass();
                                    if (((Boolean)
                                                    Binder.withCleanCallingIdentity(
                                                            new TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0(
                                                                    1, callingUserHandle, str)))
                                            .booleanValue()) {
                                        record.callback.onDisplayInfoChanged(telephonyDisplayInfo);
                                    } else {
                                        IPhoneStateListener iPhoneStateListener = record.callback;
                                        int networkType = telephonyDisplayInfo.getNetworkType();
                                        int overrideNetworkType =
                                                telephonyDisplayInfo.getOverrideNetworkType();
                                        if (networkType == 20) {
                                            overrideNetworkType = 0;
                                        } else if (networkType == 13 && overrideNetworkType == 5) {
                                            overrideNetworkType = 4;
                                        }
                                        iPhoneStateListener.onDisplayInfoChanged(
                                                new TelephonyDisplayInfo(
                                                        networkType,
                                                        overrideNetworkType,
                                                        telephonyDisplayInfo.isRoaming()));
                                    }
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyEmergencyNumberList(int i, int i2) {
        if (checkNotifyPermission()) {
            if (!Flags.enforceTelephonyFeatureMappingForPublicApis()
                    || this.mContext
                            .getPackageManager()
                            .hasSystemFeature("android.hardware.telephony.calling")) {
                synchronized (this.mRecords) {
                    if (validatePhoneId(i)) {
                        this.mEmergencyNumberList =
                                ((TelephonyManager) this.mContext.getSystemService("phone"))
                                        .getEmergencyNumberList();
                        Iterator it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            Record record = (Record) it.next();
                            if (record.matchTelephonyCallbackEvent(25) && idMatch(record, i2, i)) {
                                try {
                                    record.callback.onEmergencyNumberListChanged(
                                            this.mEmergencyNumberList);
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                }
            }
        }
    }

    public final void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) {
        if (checkNotifyPermission()) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(phoneIdFromSubId)) {
                        if (imsReasonInfo == null) {
                            loge(
                                    "ImsReasonInfo is null, subId="
                                            + i
                                            + ", phoneId="
                                            + phoneIdFromSubId);
                            ((ArrayList) this.mImsReasonInfo)
                                    .set(phoneIdFromSubId, new ImsReasonInfo());
                            return;
                        }
                        ((ArrayList) this.mImsReasonInfo).set(phoneIdFromSubId, imsReasonInfo);
                        Iterator it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            Record record = (Record) it.next();
                            if (record.matchTelephonyCallbackEvent(28)
                                    && idMatch(record, i, phoneIdFromSubId)) {
                                try {
                                    record.callback.onImsCallDisconnectCauseChanged(
                                            (ImsReasonInfo)
                                                    ((ArrayList) this.mImsReasonInfo)
                                                            .get(phoneIdFromSubId));
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyLinkCapacityEstimateChanged(int i, int i2, List list) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    ((ArrayList) this.mLinkCapacityEstimateLists).set(i, list);
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(37) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onLinkCapacityEstimateChanged(list);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyMediaQualityStatusChanged(
            int i, int i2, MediaQualityStatus mediaQualityStatus) {
        CallState callState;
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        if (((List) this.mCallStateLists.get(i)).size() > 0) {
                            Iterator it = ((List) this.mCallStateLists.get(i)).iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    callState = null;
                                    break;
                                } else {
                                    callState = (CallState) it.next();
                                    if (callState.getCallState() == 1) {
                                        break;
                                    }
                                }
                            }
                            if (callState == null) {
                                log("There is no active call to report CallQaulity");
                                return;
                            }
                            String imsCallSessionId = callState.getImsCallSessionId();
                            if (imsCallSessionId == null
                                    || !imsCallSessionId.equals(
                                            mediaQualityStatus.getCallSessionId())) {
                                log(
                                        "SessionId mismatch active call:"
                                                + imsCallSessionId
                                                + " media quality:"
                                                + mediaQualityStatus.getCallSessionId());
                                return;
                            }
                            ((SparseArray) ((ArrayList) this.mMediaQualityStatus).get(i))
                                    .put(
                                            mediaQualityStatus.getMediaSessionType(),
                                            mediaQualityStatus);
                        }
                        Iterator it2 = this.mRecords.iterator();
                        while (it2.hasNext()) {
                            Record record = (Record) it2.next();
                            if (record.matchTelephonyCallbackEvent(39) && idMatch(record, i2, i)) {
                                try {
                                    record.callback.onMediaQualityStatusChanged(mediaQualityStatus);
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mMessageWaiting[i] = z;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(3) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onMessageWaitingIndicatorChanged(z);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(15) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onOemHookRawEvent(bArr);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyOpportunisticSubscriptionInfoChanged() {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (!this.mHasNotifyOpportunisticSubscriptionInfoChangedOccurred) {
                        log(
                                "notifyOpptSubscriptionInfoChanged: first invocation mRecords.size="
                                        + this.mRecords.size());
                    }
                    this.mHasNotifyOpportunisticSubscriptionInfoChangedOccurred = true;
                    this.mRemoveList.clear();
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener =
                                record.onOpportunisticSubscriptionsChangedListenerCallback;
                        if (iOnSubscriptionsChangedListener != null) {
                            try {
                                iOnSubscriptionsChangedListener.onSubscriptionsChanged();
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyOutgoingEmergencyCall(int i, int i2, EmergencyNumber emergencyNumber) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        this.mOutgoingCallEmergencyNumber[i] = emergencyNumber;
                    }
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(29)) {
                            try {
                                record.callback.onOutgoingEmergencyCall(emergencyNumber, i2);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyOutgoingEmergencySms(int i, int i2, EmergencyNumber emergencyNumber) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mOutgoingSmsEmergencyNumber[i] = emergencyNumber;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(30)) {
                            try {
                                record.callback.onOutgoingEmergencySms(emergencyNumber, i2);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                this.mPhoneCapability = phoneCapability;
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(22)) {
                        try {
                            record.callback.onPhoneCapabilityChanged(phoneCapability);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyPhysicalChannelConfigForSubscriber(int i, int i2, List list) {
        if (checkNotifyPermission()) {
            List locationSanitizedConfigs = getLocationSanitizedConfigs(list);
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    ((ArrayList) this.mPhysicalChannelConfigs).set(i, list);
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(33) && idMatch(record, i2, i)) {
                            try {
                                IPhoneStateListener iPhoneStateListener = record.callback;
                                int i3 = record.callerUid;
                                iPhoneStateListener.onPhysicalChannelConfigChanged(
                                        (i3 == 1001 || i3 == 1000)
                                                ? list
                                                : locationSanitizedConfigs);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifyPreciseCallState(
            int i, int i2, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3) {
        boolean z;
        if (checkNotifyPermission()) {
            boolean z2 = false;
            int i3 = iArr[0];
            int i4 = iArr[1];
            int i5 = iArr[2];
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        this.mRingingCallState[i] = i3;
                        this.mForegroundCallState[i] = i4;
                        this.mBackgroundCallState[i] = i5;
                        PreciseCallState preciseCallState =
                                new PreciseCallState(i3, i4, i5, -1, -1);
                        if (preciseCallState.equals(this.mPreciseCallState[i])) {
                            z = false;
                        } else {
                            this.mPreciseCallState[i] = preciseCallState;
                            z = true;
                        }
                        if (this.mCallQuality != null) {
                            if (this.mPreciseCallState[i].getForegroundCallState() != 1) {
                                this.mCallQuality[i] = createCallQuality();
                            }
                            if (this.mCallNetworkType[i] != 0
                                    && this.mPreciseCallState[i].getRingingCallState() <= 0
                                    && this.mPreciseCallState[i].getForegroundCallState() <= 0
                                    && this.mPreciseCallState[i].getBackgroundCallState() <= 0) {
                                log("notifyPreciseCallState - Reset call network type");
                                this.mCallNetworkType[i] = 0;
                            }
                            ArrayList arrayList = new ArrayList();
                            arrayList.addAll((Collection) this.mCallStateLists.get(i));
                            ((List) this.mCallStateLists.get(i)).clear();
                            if (i4 != -1 && i4 != 0) {
                                CallState.Builder callClassification =
                                        new CallState.Builder(iArr[1])
                                                .setNetworkType(this.mCallNetworkType[i])
                                                .setCallQuality(this.mCallQuality[i])
                                                .setCallClassification(1);
                                if (strArr != null && iArr2 != null && iArr3 != null) {
                                    callClassification =
                                            callClassification
                                                    .setImsCallSessionId(strArr[1])
                                                    .setImsCallServiceType(iArr2[1])
                                                    .setImsCallType(iArr3[1]);
                                }
                                ((List) this.mCallStateLists.get(i))
                                        .add(callClassification.build());
                            }
                            if (i5 != -1 && i5 != 0) {
                                CallState.Builder callClassification2 =
                                        new CallState.Builder(iArr[2])
                                                .setNetworkType(this.mCallNetworkType[i])
                                                .setCallQuality(createCallQuality())
                                                .setCallClassification(2);
                                if (strArr != null && iArr2 != null && iArr3 != null) {
                                    callClassification2 =
                                            callClassification2
                                                    .setImsCallSessionId(strArr[2])
                                                    .setImsCallServiceType(iArr2[2])
                                                    .setImsCallType(iArr3[2]);
                                }
                                ((List) this.mCallStateLists.get(i))
                                        .add(callClassification2.build());
                            }
                            if (i3 != -1 && i3 != 0) {
                                CallState.Builder callClassification3 =
                                        new CallState.Builder(iArr[0])
                                                .setNetworkType(this.mCallNetworkType[i])
                                                .setCallQuality(createCallQuality())
                                                .setCallClassification(0);
                                if (strArr != null && iArr2 != null && iArr3 != null) {
                                    callClassification3 =
                                            callClassification3
                                                    .setImsCallSessionId(strArr[0])
                                                    .setImsCallServiceType(iArr2[0])
                                                    .setImsCallType(iArr3[0]);
                                }
                                ((List) this.mCallStateLists.get(i))
                                        .add(callClassification3.build());
                            }
                            z2 = !arrayList.equals(this.mCallStateLists.get(i));
                            Iterator it = ((List) this.mCallStateLists.get(i)).iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    ((SparseArray) ((ArrayList) this.mMediaQualityStatus).get(i))
                                            .clear();
                                    break;
                                } else if (((CallState) it.next()).getCallState() != 7) {
                                    break;
                                }
                            }
                        } else {
                            log(
                                    "notifyPreciseCallState: mCallQuality is null, skipping call"
                                        + " attributes");
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("notifyPreciseCallState - phoneId: ");
                        sb.append(i);
                        sb.append(", subId: ");
                        sb.append(i2);
                        sb.append(", preciseCallStateChanged: ");
                        sb.append(
                                z
                                        ? SemTelephonyUtils.preciseCallStateToString(
                                                this.mPreciseCallState[i])
                                        : "false");
                        sb.append(", notifyCallState: ");
                        sb.append(
                                z2
                                        ? SemTelephonyUtils.callStateListToString(
                                                (List) this.mCallStateLists.get(i))
                                        : "false");
                        log(sb.toString());
                        if (z) {
                            Iterator it2 = this.mRecords.iterator();
                            while (it2.hasNext()) {
                                Record record = (Record) it2.next();
                                if (record.matchTelephonyCallbackEvent(12)
                                        && idMatch(record, i2, i)) {
                                    try {
                                        record.callback.onPreciseCallStateChanged(
                                                this.mPreciseCallState[i]);
                                    } catch (RemoteException unused) {
                                        this.mRemoveList.add(record.binder);
                                    }
                                }
                            }
                        }
                        if (z2) {
                            Iterator it3 = this.mRecords.iterator();
                            while (it3.hasNext()) {
                                Record record2 = (Record) it3.next();
                                if (record2.matchTelephonyCallbackEvent(27)
                                        && idMatch(record2, i2, i)) {
                                    try {
                                        record2.callback.onCallStatesChanged(
                                                (List) this.mCallStateLists.get(i));
                                    } catch (RemoteException unused2) {
                                        this.mRemoveList.add(record2.binder);
                                    }
                                }
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0040, code lost:

       if (r9 == 0) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0059, code lost:

       if (r10 == r8.mDefaultSubId) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x005c, code lost:

       if (r6 == r10) goto L22;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyRadioPowerStateChanged(int r9, int r10, int r11) {
        /*
            r8 = this;
            boolean r0 = r8.checkNotifyPermission()
            if (r0 != 0) goto L7
            return
        L7:
            java.util.ArrayList r0 = r8.mRecords
            monitor-enter(r0)
            boolean r1 = r8.validatePhoneId(r9)     // Catch: java.lang.Throwable -> L67
            if (r1 == 0) goto L71
            r8.mRadioPowerState = r11     // Catch: java.lang.Throwable -> L67
            java.util.ArrayList r1 = r8.mRecords     // Catch: java.lang.Throwable -> L67
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L67
        L18:
            boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L67
            if (r2 == 0) goto L71
            java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> L67
            com.android.server.TelephonyRegistry$Record r2 = (com.android.server.TelephonyRegistry.Record) r2     // Catch: java.lang.Throwable -> L67
            r3 = 24
            boolean r3 = r2.matchTelephonyCallbackEvent(r3)     // Catch: java.lang.Throwable -> L67
            if (r3 == 0) goto L18
            boolean r3 = com.android.internal.telephony.flags.Flags.useRelaxedIdMatch()     // Catch: java.lang.Throwable -> L67
            if (r3 != 0) goto L37
            boolean r3 = r8.idMatch(r2, r10, r9)     // Catch: java.lang.Throwable -> L67
            goto L5f
        L37:
            r3 = -1
            r4 = 0
            r5 = 1
            if (r10 >= 0) goto L49
            int r6 = r2.phoneId     // Catch: java.lang.Throwable -> L67
            if (r6 != r3) goto L46
            if (r9 != 0) goto L44
        L42:
            r3 = r5
            goto L5f
        L44:
            r3 = r4
            goto L5f
        L46:
            if (r6 != r9) goto L44
            goto L42
        L49:
            int r6 = r2.subId     // Catch: java.lang.Throwable -> L67
            r7 = 2147483647(0x7fffffff, float:NaN)
            if (r6 != r7) goto L5c
            int r6 = r2.phoneId     // Catch: java.lang.Throwable -> L67
            if (r6 != r3) goto L57
            if (r9 != 0) goto L44
            goto L42
        L57:
            int r3 = r8.mDefaultSubId     // Catch: java.lang.Throwable -> L67
            if (r10 != r3) goto L44
            goto L42
        L5c:
            if (r6 != r10) goto L44
            goto L42
        L5f:
            if (r3 == 0) goto L18
            com.android.internal.telephony.IPhoneStateListener r3 = r2.callback     // Catch: java.lang.Throwable -> L67 android.os.RemoteException -> L69
            r3.onRadioPowerStateChanged(r11)     // Catch: java.lang.Throwable -> L67 android.os.RemoteException -> L69
            goto L18
        L67:
            r8 = move-exception
            goto L76
        L69:
            java.util.ArrayList r3 = r8.mRemoveList     // Catch: java.lang.Throwable -> L67
            android.os.IBinder r2 = r2.binder     // Catch: java.lang.Throwable -> L67
            r3.add(r2)     // Catch: java.lang.Throwable -> L67
            goto L18
        L71:
            r8.handleRemoveListLocked()     // Catch: java.lang.Throwable -> L67
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L67
            return
        L76:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L67
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.TelephonyRegistry.notifyRadioPowerStateChanged(int, int,"
                    + " int):void");
    }

    public final void notifyRegistrationFailed(
            int i, int i2, CellIdentity cellIdentity, String str, int i3, int i4, int i5) {
        Record record;
        int i6 = i;
        if (checkNotifyPermission()) {
            CellIdentity sanitizeLocationInfo = cellIdentity.sanitizeLocationInfo();
            String plmn = cellIdentity.getPlmn();
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i6, i2, "Registration Failed for phoneId=", " subId=", "primaryPlmn=");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    m, plmn, " chosenPlmn=", str, " domain=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    i3, i4, " causeCode=", " additionalCauseCode=", m);
            m.append(i5);
            this.mLocalLog.log(m.toString());
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record2 = (Record) it.next();
                        if (record2.matchTelephonyCallbackEvent(31) && idMatch(record2, i2, i6)) {
                            try {
                                record = record2;
                            } catch (RemoteException unused) {
                                record = record2;
                            }
                            try {
                                record2.callback.onRegistrationFailed(
                                        checkFineLocationAccess(record2, 1)
                                                ? cellIdentity
                                                : sanitizeLocationInfo,
                                        str,
                                        i3,
                                        i4,
                                        i5);
                            } catch (RemoteException unused2) {
                                this.mRemoveList.add(record.binder);
                                i6 = i;
                            }
                        }
                        i6 = i;
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifySemSatelliteServiceStateChanged(
            int i, int i2, SemSatelliteServiceState semSatelliteServiceState) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.SATELLITE_COMMUNICATION")
                != 0) {
            Binder.getCallingPid();
            Binder.getCallingUid();
            return;
        }
        synchronized (this.mRecords) {
            this.mSatServiceState = new SemSatelliteServiceState(semSatelliteServiceState);
            Iterator it = this.mRecords.iterator();
            while (it.hasNext()) {
                Record record = (Record) it.next();
                if (record.matchTelephonyCallbackEvent(10000) && idMatch(record, i2, i)) {
                    try {
                        record.callback.onSemSatelliteServiceStateChanged(semSatelliteServiceState);
                    } catch (RemoteException unused) {
                        this.mRemoveList.add(record.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifySemSatelliteSignalStrengthChanged(
            int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.SATELLITE_COMMUNICATION")
                != 0) {
            Binder.getCallingPid();
            Binder.getCallingUid();
            return;
        }
        synchronized (this.mRecords) {
            this.mSatSignalStrength = new SemSatelliteSignalStrength(semSatelliteSignalStrength);
            Iterator it = this.mRecords.iterator();
            while (it.hasNext()) {
                Record record = (Record) it.next();
                if (record.matchTelephonyCallbackEvent(10001) && idMatch(record, i2, i)) {
                    try {
                        record.callback.onSemSatelliteSignalStrengthChanged(
                                semSatelliteSignalStrength);
                    } catch (RemoteException unused) {
                        this.mRemoveList.add(record.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public final void notifyServiceStateForPhoneId(int i, int i2, ServiceState serviceState) {
        if (checkNotifyPermission()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mRecords) {
                    try {
                        this.mLocalLog.log(
                                "notifyServiceStateForSubscriber: subId="
                                        + i2
                                        + " phoneId="
                                        + i
                                        + " state="
                                        + serviceState);
                        if (validatePhoneId(i) && SubscriptionManager.isValidSubscriptionId(i2)) {
                            this.mServiceState[i] = serviceState;
                            Iterator it = this.mRecords.iterator();
                            while (it.hasNext()) {
                                Record record = (Record) it.next();
                                if (record.matchTelephonyCallbackEvent(1)
                                        && idMatch(record, i2, i)) {
                                    try {
                                        record.callback.onServiceStateChanged(
                                                checkFineLocationAccess(record, 29)
                                                        ? new ServiceState(serviceState)
                                                        : checkCoarseLocationAccess(record, 29)
                                                                ? serviceState
                                                                        .createLocationInfoSanitizedCopy(
                                                                                false)
                                                                : serviceState
                                                                        .createLocationInfoSanitizedCopy(
                                                                                true));
                                    } catch (RemoteException unused) {
                                        this.mRemoveList.add(record.binder);
                                    }
                                }
                            }
                        } else {
                            log(
                                    "notifyServiceStateForSubscriber: INVALID phoneId="
                                            + i
                                            + " or subId="
                                            + i2);
                        }
                        handleRemoveListLocked();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                broadcastServiceStateChanged(i, i2, serviceState);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void notifySignalStrengthForPhoneId(int i, int i2, SignalStrength signalStrength) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mSignalStrength[i] = signalStrength;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(9) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onSignalStrengthsChanged(
                                        new SignalStrength(signalStrength));
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                        if (record.matchTelephonyCallbackEvent(2) && idMatch(record, i2, i)) {
                            try {
                                int gsmSignalStrength = signalStrength.getGsmSignalStrength();
                                if (gsmSignalStrength == 99) {
                                    gsmSignalStrength = -1;
                                }
                                record.callback.onSignalStrengthChanged(gsmSignalStrength);
                            } catch (RemoteException unused2) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                } else {
                    log("notifySignalStrengthForPhoneId: invalid phoneId=" + i);
                }
                handleRemoveListLocked();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mBatteryStats.notePhoneSignalStrength(signalStrength);
            } catch (RemoteException unused3) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Intent intent = new Intent("android.intent.action.SIG_STR");
            Bundle bundle = new Bundle();
            signalStrength.fillInNotifierBundle(bundle);
            for (CellSignalStrength cellSignalStrength : signalStrength.getCellSignalStrengths()) {
                if (cellSignalStrength instanceof CellSignalStrengthLte) {
                    bundle.putParcelable("Lte", (CellSignalStrengthLte) cellSignalStrength);
                } else if (cellSignalStrength instanceof CellSignalStrengthCdma) {
                    bundle.putParcelable("Cdma", (CellSignalStrengthCdma) cellSignalStrength);
                } else if (cellSignalStrength instanceof CellSignalStrengthGsm) {
                    bundle.putParcelable("Gsm", (CellSignalStrengthGsm) cellSignalStrength);
                } else if (cellSignalStrength instanceof CellSignalStrengthWcdma) {
                    bundle.putParcelable("Wcdma", (CellSignalStrengthWcdma) cellSignalStrength);
                } else if (cellSignalStrength instanceof CellSignalStrengthTdscdma) {
                    bundle.putParcelable("Tdscdma", (CellSignalStrengthTdscdma) cellSignalStrength);
                } else if (cellSignalStrength instanceof CellSignalStrengthNr) {
                    bundle.putParcelable("Nr", (CellSignalStrengthNr) cellSignalStrength);
                }
            }
            intent.putExtras(bundle);
            intent.putExtra("subscription", i2);
            intent.putExtra("slot", i);
            this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
        }
    }

    public final void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (validatePhoneId(i)) {
                        if (i3 == 0) {
                            this.mVoiceActivationState[i] = i4;
                        } else if (i3 != 1) {
                            return;
                        } else {
                            this.mDataActivationState[i] = i4;
                        }
                        Iterator it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            Record record = (Record) it.next();
                            if (i3 == 0) {
                                try {
                                    if (record.matchTelephonyCallbackEvent(18)
                                            && idMatch(record, i2, i)) {
                                        record.callback.onVoiceActivationStateChanged(i4);
                                    }
                                } catch (RemoteException unused) {
                                    this.mRemoveList.add(record.binder);
                                }
                            }
                            if (i3 == 1
                                    && record.matchTelephonyCallbackEvent(19)
                                    && idMatch(record, i2, i)) {
                                record.callback.onDataActivationStateChanged(i4);
                            }
                        }
                    } else {
                        log("notifySimActivationStateForPhoneId: INVALID phoneId=" + i);
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                this.mSimultaneousCellularCallingSubIds = iArr;
                Iterator it = this.mRecords.iterator();
                while (it.hasNext()) {
                    Record record = (Record) it.next();
                    if (record.matchTelephonyCallbackEvent(41)) {
                        try {
                            record.callback.onSimultaneousCallingStateChanged(iArr);
                        } catch (RemoteException unused) {
                            this.mRemoveList.add(record.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void notifySrvccStateChanged(int i, int i2) {
        if (checkNotifyPermission()) {
            int phoneIdFromSubId = getPhoneIdFromSubId(i);
            synchronized (this.mRecords) {
                if (validatePhoneId(phoneIdFromSubId)) {
                    this.mSrvccState[phoneIdFromSubId] = i2;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(16)
                                && idMatch(record, i, phoneIdFromSubId)) {
                            try {
                                record.callback.onSrvccStateChanged(i2);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                    handleRemoveListLocked();
                } else {
                    Iterator it2 = this.mRecords.iterator();
                    while (it2.hasNext()) {
                        Record record2 = (Record) it2.next();
                        if (record2.matchTelephonyCallbackEvent(16)) {
                            try {
                                record2.callback.onSrvccStateChanged(i2);
                            } catch (RemoteException unused2) {
                                this.mRemoveList.add(record2.binder);
                            }
                        }
                    }
                    handleRemoveListLocked();
                }
            }
        }
    }

    public final void notifySubscriptionInfoChanged() {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                try {
                    if (!this.mHasNotifySubscriptionInfoChangedOccurred) {
                        log(
                                "notifySubscriptionInfoChanged: first invocation mRecords.size="
                                        + this.mRecords.size());
                    }
                    this.mHasNotifySubscriptionInfoChangedOccurred = true;
                    this.mRemoveList.clear();
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener =
                                record.onSubscriptionsChangedListenerCallback;
                        if (iOnSubscriptionsChangedListener != null) {
                            try {
                                iOnSubscriptionsChangedListener.onSubscriptionsChanged();
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                    handleRemoveListLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) {
        if (checkNotifyPermission()) {
            synchronized (this.mRecords) {
                if (validatePhoneId(i)) {
                    this.mUserMobileDataState[i] = z;
                    Iterator it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        Record record = (Record) it.next();
                        if (record.matchTelephonyCallbackEvent(20) && idMatch(record, i2, i)) {
                            try {
                                record.callback.onUserMobileDataStateChanged(z);
                            } catch (RemoteException unused) {
                                this.mRemoveList.add(record.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public final void onMultiSimConfigChanged() {
        synchronized (this.mRecords) {
            try {
                int i = this.mNumPhones;
                int activeModemCount =
                        ((TelephonyManager) this.mContext.getSystemService("phone"))
                                .getActiveModemCount();
                this.mNumPhones = activeModemCount;
                if (i == activeModemCount) {
                    return;
                }
                int[] copyOf = Arrays.copyOf(this.mCallState, activeModemCount);
                this.mCallState = copyOf;
                this.mDataActivity = Arrays.copyOf(copyOf, this.mNumPhones);
                this.mDataConnectionState = Arrays.copyOf(this.mCallState, this.mNumPhones);
                this.mDataConnectionNetworkType = Arrays.copyOf(this.mCallState, this.mNumPhones);
                this.mCallIncomingNumber =
                        (String[]) Arrays.copyOf(this.mCallIncomingNumber, this.mNumPhones);
                this.mServiceState =
                        (ServiceState[]) Arrays.copyOf(this.mServiceState, this.mNumPhones);
                this.mVoiceActivationState =
                        Arrays.copyOf(this.mVoiceActivationState, this.mNumPhones);
                this.mDataActivationState =
                        Arrays.copyOf(this.mDataActivationState, this.mNumPhones);
                this.mUserMobileDataState =
                        Arrays.copyOf(this.mUserMobileDataState, this.mNumPhones);
                SignalStrength[] signalStrengthArr = this.mSignalStrength;
                if (signalStrengthArr != null) {
                    this.mSignalStrength =
                            (SignalStrength[]) Arrays.copyOf(signalStrengthArr, this.mNumPhones);
                } else {
                    this.mSignalStrength = new SignalStrength[this.mNumPhones];
                }
                this.mMessageWaiting = Arrays.copyOf(this.mMessageWaiting, this.mNumPhones);
                this.mCallForwarding = Arrays.copyOf(this.mCallForwarding, this.mNumPhones);
                this.mCellIdentity =
                        (CellIdentity[]) Arrays.copyOf(this.mCellIdentity, this.mNumPhones);
                this.mSrvccState = Arrays.copyOf(this.mSrvccState, this.mNumPhones);
                this.mPreciseCallState =
                        (PreciseCallState[]) Arrays.copyOf(this.mPreciseCallState, this.mNumPhones);
                this.mForegroundCallState =
                        Arrays.copyOf(this.mForegroundCallState, this.mNumPhones);
                this.mBackgroundCallState =
                        Arrays.copyOf(this.mBackgroundCallState, this.mNumPhones);
                this.mRingingCallState = Arrays.copyOf(this.mRingingCallState, this.mNumPhones);
                this.mCallDisconnectCause =
                        Arrays.copyOf(this.mCallDisconnectCause, this.mNumPhones);
                this.mCallPreciseDisconnectCause =
                        Arrays.copyOf(this.mCallPreciseDisconnectCause, this.mNumPhones);
                this.mCallQuality =
                        (CallQuality[]) Arrays.copyOf(this.mCallQuality, this.mNumPhones);
                this.mCallNetworkType = Arrays.copyOf(this.mCallNetworkType, this.mNumPhones);
                this.mOutgoingCallEmergencyNumber =
                        (EmergencyNumber[])
                                Arrays.copyOf(this.mOutgoingCallEmergencyNumber, this.mNumPhones);
                this.mOutgoingSmsEmergencyNumber =
                        (EmergencyNumber[])
                                Arrays.copyOf(this.mOutgoingSmsEmergencyNumber, this.mNumPhones);
                this.mTelephonyDisplayInfos =
                        (TelephonyDisplayInfo[])
                                Arrays.copyOf(this.mTelephonyDisplayInfos, this.mNumPhones);
                this.mCarrierNetworkChangeState =
                        Arrays.copyOf(this.mCarrierNetworkChangeState, this.mNumPhones);
                this.mIsDataEnabled = Arrays.copyOf(this.mIsDataEnabled, this.mNumPhones);
                this.mDataEnabledReason = Arrays.copyOf(this.mDataEnabledReason, this.mNumPhones);
                this.mAllowedNetworkTypeReason =
                        Arrays.copyOf(this.mAllowedNetworkTypeReason, this.mNumPhones);
                this.mAllowedNetworkTypeValue =
                        Arrays.copyOf(this.mAllowedNetworkTypeValue, this.mNumPhones);
                this.mECBMReason = Arrays.copyOf(this.mECBMReason, this.mNumPhones);
                this.mECBMStarted = Arrays.copyOf(this.mECBMStarted, this.mNumPhones);
                this.mSCBMReason = Arrays.copyOf(this.mSCBMReason, this.mNumPhones);
                this.mSCBMStarted = Arrays.copyOf(this.mSCBMStarted, this.mNumPhones);
                this.mCarrierRoamingNtnMode =
                        Arrays.copyOf(this.mCarrierRoamingNtnMode, this.mNumPhones);
                this.mCarrierRoamingNtnEligible =
                        Arrays.copyOf(this.mCarrierRoamingNtnEligible, this.mNumPhones);
                NtnSignalStrength[] ntnSignalStrengthArr = this.mCarrierRoamingNtnSignalStrength;
                if (ntnSignalStrengthArr != null) {
                    this.mCarrierRoamingNtnSignalStrength =
                            (NtnSignalStrength[])
                                    Arrays.copyOf(ntnSignalStrengthArr, this.mNumPhones);
                } else {
                    this.mCarrierRoamingNtnSignalStrength = new NtnSignalStrength[this.mNumPhones];
                }
                int i2 = this.mNumPhones;
                if (i2 < i) {
                    cutListToSize(i2, this.mCellInfo);
                    cutListToSize(this.mNumPhones, this.mImsReasonInfo);
                    cutListToSize(this.mNumPhones, this.mPreciseDataConnectionStates);
                    cutListToSize(this.mNumPhones, this.mBarringInfo);
                    cutListToSize(this.mNumPhones, this.mPhysicalChannelConfigs);
                    cutListToSize(this.mNumPhones, this.mLinkCapacityEstimateLists);
                    cutListToSize(this.mNumPhones, this.mCarrierPrivilegeStates);
                    cutListToSize(this.mNumPhones, this.mCarrierServiceStates);
                    cutListToSize(this.mNumPhones, this.mCallStateLists);
                    cutListToSize(this.mNumPhones, this.mMediaQualityStatus);
                    cutListToSize(this.mNumPhones, this.mCarrierRoamingNtnAvailableServices);
                    return;
                }
                while (i < this.mNumPhones) {
                    this.mCallState[i] = 0;
                    this.mDataActivity[i] = 0;
                    this.mDataConnectionState[i] = -1;
                    this.mVoiceActivationState[i] = 0;
                    this.mDataActivationState[i] = 0;
                    this.mCallIncomingNumber[i] = "";
                    this.mServiceState[i] = new ServiceState();
                    this.mSignalStrength[i] = null;
                    this.mUserMobileDataState[i] = false;
                    this.mMessageWaiting[i] = false;
                    this.mCallForwarding[i] = false;
                    this.mCellIdentity[i] = null;
                    this.mCellInfo.add(i, Collections.EMPTY_LIST);
                    ((ArrayList) this.mImsReasonInfo).add(i, null);
                    this.mSrvccState[i] = -1;
                    this.mCallDisconnectCause[i] = -1;
                    this.mCallPreciseDisconnectCause[i] = -1;
                    this.mCallQuality[i] = createCallQuality();
                    ((ArrayList) this.mMediaQualityStatus).add(i, new SparseArray());
                    this.mCallStateLists.add(i, new ArrayList());
                    this.mCallNetworkType[i] = 0;
                    this.mPreciseCallState[i] = new PreciseCallState(-1, -1, -1, -1, -1);
                    this.mRingingCallState[i] = 0;
                    this.mForegroundCallState[i] = 0;
                    this.mBackgroundCallState[i] = 0;
                    ((ArrayList) this.mPreciseDataConnectionStates).add(new ArrayMap());
                    ((ArrayList) this.mBarringInfo).add(i, new BarringInfo());
                    this.mCarrierNetworkChangeState[i] = false;
                    this.mTelephonyDisplayInfos[i] = null;
                    this.mIsDataEnabled[i] = false;
                    this.mDataEnabledReason[i] = 0;
                    ((ArrayList) this.mPhysicalChannelConfigs).add(i, new ArrayList());
                    this.mAllowedNetworkTypeReason[i] = -1;
                    this.mAllowedNetworkTypeValue[i] = -1;
                    ((ArrayList) this.mLinkCapacityEstimateLists).add(i, INVALID_LCE_LIST);
                    ((ArrayList) this.mCarrierPrivilegeStates)
                            .add(i, new Pair(Collections.emptyList(), new int[0]));
                    ((ArrayList) this.mCarrierServiceStates).add(i, new Pair(null, -1));
                    this.mECBMReason[i] = 0;
                    this.mECBMStarted[i] = false;
                    this.mSCBMReason[i] = 0;
                    this.mSCBMStarted[i] = false;
                    this.mCarrierRoamingNtnMode[i] = false;
                    this.mCarrierRoamingNtnEligible[i] = false;
                    ((ArrayList) this.mCarrierRoamingNtnAvailableServices).add(i, new IntArray());
                    this.mCarrierRoamingNtnSignalStrength[i] = new NtnSignalStrength(0);
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void remove(IBinder iBinder) {
        synchronized (this.mRecords) {
            int size = this.mRecords.size();
            for (int i = 0; i < size; i++) {
                Record record = (Record) this.mRecords.get(i);
                if (record.binder == iBinder) {
                    TelephonyRegistryDeathRecipient telephonyRegistryDeathRecipient =
                            record.deathRecipient;
                    if (telephonyRegistryDeathRecipient != null) {
                        try {
                            iBinder.unlinkToDeath(telephonyRegistryDeathRecipient, 0);
                        } catch (NoSuchElementException unused) {
                        }
                    }
                    this.mRecords.remove(i);
                    return;
                }
            }
        }
    }

    public final void removeCarrierConfigChangeListener(
            ICarrierConfigChangeListener iCarrierConfigChangeListener, String str) {
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        remove(iCarrierConfigChangeListener.asBinder());
    }

    public final void removeCarrierPrivilegesCallback(
            ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str) {
        this.mAppOps.checkPackage(Binder.getCallingUid(), str);
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.READ_PRIVILEGED_PHONE_STATE",
                "removeCarrierPrivilegesCallback");
        remove(iCarrierPrivilegesCallback.asBinder());
    }

    public final void removeOnSubscriptionsChangedListener(
            String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
        remove(iOnSubscriptionsChangedListener.asBinder());
    }

    public final void systemRunning() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.telephony.action.MULTI_SIM_CONFIG_CHANGED");
        log("systemRunning register for intents");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        int defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
        this.mDefaultSubId = defaultSubscriptionId;
        this.mDefaultPhoneId = getPhoneIdFromSubId(defaultSubscriptionId);
    }

    public final boolean validatePhoneId(int i) {
        return i >= 0
                && i
                        < ((TelephonyManager) this.mContext.getSystemService("phone"))
                                .getActiveModemCount();
    }
}
