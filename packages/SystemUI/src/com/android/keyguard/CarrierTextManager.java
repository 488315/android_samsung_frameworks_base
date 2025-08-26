package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticLambda2;
import com.android.settingslib.WirelessUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.carrier.CarrierTextUtil;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes.dex */
public class CarrierTextManager {
    public static final HashMap shortCarrierNameMap = new HashMap();
    public Boolean hasSpecialChar;
    public final Executor mBgExecutor;
    protected final KeyguardUpdateMonitorCallback mCallback;
    public CarrierTextCallback mCarrierTextCallback;
    public final CarrierTextUtil mCarrierTextUtil;
    public final Context mContext;
    public final DeviceBasedSatelliteViewModel mDeviceBasedSatelliteViewModel;
    public final boolean mIsEmergencyCallCapable;
    public final JavaAdapter mJavaAdapter;
    protected KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final AnonymousClass4 mKnoxStateCallback;
    public final CarrierTextManagerLogger mLogger;
    public final Executor mMainExecutor;
    public final AtomicBoolean mNetworkSupported;
    public final AnonymousClass3 mPhoneStateListener;
    public String mSatelliteCarrierText;
    public Job mSatelliteConnectionJob;
    public final CharSequence mSeparator;
    private final SettingsHelper mSettingsHelper;
    public final boolean mShowMissingSim;
    public final boolean[] mSimErrorState;
    public final boolean[] mSimNetworkLock;
    public final int mSimSlotsNumber;
    public final SubscriptionsOrder mSubscriptionsOrder;
    public boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final AnonymousClass1 mWakefulnessObserver;
    public final WifiRepository mWifiRepository;
    public final WifiTextManager mWifiTextManager;
    public final HashMap plmnOfBroadcast;
    public final HashMap voWifiConnected;

    public class Builder {
        public final Executor mBgExecutor;
        public final CarrierTextUtil mCarrierTextUtil;
        public final Context mContext;
        public String mDebugLocation;
        public final DeviceBasedSatelliteViewModel mDeviceBasedSatelliteViewModel;
        public final JavaAdapter mJavaAdapter;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final CarrierTextManagerLogger mLogger;
        public final Executor mMainExecutor;
        private SettingsHelper mSettingsHelper;
        public boolean mShowAirplaneMode;
        public boolean mShowMissingSim;
        public final SubscriptionsOrder mSubscriptionsOrder;
        public final TelephonyListenerManager mTelephonyListenerManager;
        public final TelephonyManager mTelephonyManager;
        public final WakefulnessLifecycle mWakefulnessLifecycle;
        public final WifiRepository mWifiRepository;
        public final WifiTextManager mWifiTextManager;

        public Builder(Context context, Resources resources, WifiRepository wifiRepository, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, JavaAdapter javaAdapter, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, SettingsHelper settingsHelper, WifiTextManager wifiTextManager) {
            this.mContext = context;
            this.mWifiRepository = wifiRepository;
            this.mDeviceBasedSatelliteViewModel = deviceBasedSatelliteViewModel;
            this.mJavaAdapter = javaAdapter;
            this.mTelephonyManager = telephonyManager;
            this.mTelephonyListenerManager = telephonyListenerManager;
            this.mCarrierTextUtil = carrierTextUtil;
            this.mWakefulnessLifecycle = wakefulnessLifecycle;
            this.mMainExecutor = executor;
            this.mBgExecutor = executor2;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mSubscriptionsOrder = subscriptionsOrder;
            this.mLogger = carrierTextManagerLogger;
            if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
                this.mWifiTextManager = wifiTextManager;
            }
            this.mSettingsHelper = settingsHelper;
        }

        public final CarrierTextManager build() {
            this.mLogger.location = this.mDebugLocation;
            Context context = this.mContext;
            boolean z = this.mShowAirplaneMode;
            boolean z2 = this.mShowMissingSim;
            TelephonyManager telephonyManager = this.mTelephonyManager;
            Executor executor = this.mMainExecutor;
            Executor executor2 = this.mBgExecutor;
            SettingsHelper settingsHelper = this.mSettingsHelper;
            WifiTextManager wifiTextManager = this.mWifiTextManager;
            return new CarrierTextManager(context, z, z2, this.mWifiRepository, this.mDeviceBasedSatelliteViewModel, this.mJavaAdapter, telephonyManager, this.mTelephonyListenerManager, this.mCarrierTextUtil, this.mWakefulnessLifecycle, executor, executor2, this.mKeyguardUpdateMonitor, this.mSubscriptionsOrder, this.mLogger, settingsHelper, wifiTextManager);
        }
    }

    public final class CarrierTextCallbackInfo {
        public final boolean airplaneMode;
        public final boolean anySimReady;
        public final CharSequence carrierText;
        public final CharSequence carrierTextShort;
        public final boolean isInSatelliteMode;
        public final CharSequence[] listOfCarriers;
        public final String location;
        public final int[] subscriptionIds;

        public CarrierTextCallbackInfo(CharSequence charSequence, CharSequence charSequence2, CharSequence[] charSequenceArr, boolean z, int[] iArr) {
            this(null, charSequence, charSequence2, charSequenceArr, z, false, iArr, false);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CarrierTextCallbackInfo{carrierText=");
            sb.append((Object) this.carrierText);
            sb.append("carrierTextShort=");
            sb.append((Object) this.carrierTextShort);
            sb.append(", listOfCarriers=");
            sb.append(Arrays.toString(this.listOfCarriers));
            sb.append(", anySimReady=");
            sb.append(this.anySimReady);
            sb.append(", isInSatelliteMode=");
            sb.append(this.isInSatelliteMode);
            sb.append(", subscriptionIds=");
            sb.append(Arrays.toString(this.subscriptionIds));
            sb.append(", airplaneMode=");
            sb.append(this.airplaneMode);
            sb.append(", location=");
            return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.location, '}');
        }

        public CarrierTextCallbackInfo(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence[] charSequenceArr, boolean z, boolean z2, int[] iArr, boolean z3) {
            this.carrierText = charSequence;
            this.carrierTextShort = charSequence2;
            this.listOfCarriers = charSequenceArr;
            this.anySimReady = z;
            this.isInSatelliteMode = z2;
            this.subscriptionIds = iArr;
            this.airplaneMode = z3;
            this.location = str;
        }
    }

    public enum StatusMode {
        Normal,
        NetworkLocked,
        PersoLocked,
        SimMissing,
        SimMissingLocked,
        SimPukLocked,
        SimLocked,
        SimPermDisabled,
        SimNotReady,
        SimIoError,
        SimRestricted,
        SimUnknown
    }

    public /* synthetic */ CarrierTextManager(Context context, boolean z, boolean z2, WifiRepository wifiRepository, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, JavaAdapter javaAdapter, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, SettingsHelper settingsHelper, WifiTextManager wifiTextManager) {
        this(context, " | ", z, z2, wifiRepository, deviceBasedSatelliteViewModel, javaAdapter, telephonyManager, telephonyListenerManager, carrierTextUtil, wakefulnessLifecycle, executor, executor2, keyguardUpdateMonitor, subscriptionsOrder, carrierTextManagerLogger, settingsHelper, wifiTextManager);
    }

    public static CharSequence concatenate(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        boolean zIsEmpty = TextUtils.isEmpty(charSequence);
        boolean zIsEmpty2 = TextUtils.isEmpty(charSequence2);
        if (zIsEmpty || zIsEmpty2) {
            return !zIsEmpty ? charSequence : !zIsEmpty2 ? charSequence2 : "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(charSequence);
        sb.append(charSequence3);
        sb.append(charSequence2);
        return sb.toString();
    }

    public static CharSequence joinNotEmpty(CharSequence charSequence, CharSequence[] charSequenceArr, Boolean bool, Boolean bool2) {
        int length = charSequenceArr.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(charSequenceArr[i])) {
                if (TextUtils.isEmpty(sb)) {
                    sb.append(charSequenceArr[i]);
                } else if (bool2.booleanValue() && bool.booleanValue()) {
                    sb.insert(0, charSequence);
                    sb.insert(0, charSequenceArr[i]);
                } else {
                    sb.append(charSequence);
                    sb.append(charSequenceArr[i]);
                }
            }
        }
        return sb.toString();
    }

    public final void cancelSatelliteCollectionJob(String str) {
        Job job = this.mSatelliteConnectionJob;
        if (job != null) {
            CarrierTextManagerLogger carrierTextManagerLogger = this.mLogger;
            carrierTextManagerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CarrierTextManagerLogger$$ExternalSyntheticLambda2 carrierTextManagerLogger$$ExternalSyntheticLambda2 = new CarrierTextManagerLogger$$ExternalSyntheticLambda2(8);
            LogBuffer logBuffer = carrierTextManagerLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$$ExternalSyntheticLambda2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = carrierTextManagerLogger.location;
            logMessageImpl.str2 = str;
            logBuffer.commit(logMessageObtain);
            job.cancel(new CancellationException(str));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00b2 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CharSequence getCarrierTextForSimState(int i, CharSequence charSequence) {
        switch (getStatusForIccState(i)) {
            case Normal:
                if (isRTL() && !this.hasSpecialChar.booleanValue()) {
                    return "\u200f" + ((Object) charSequence);
                }
            case NetworkLocked:
                return makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.keyguard_network_locked_message), charSequence);
            case PersoLocked:
                return this.mContext.getText(R.string.kg_perso_locked_message);
            case SimMissingLocked:
                if (CscRune.SECURITY_KOR_USIM_TEXT) {
                    return makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.kg_missing_sim_message_short), charSequence);
                }
            case SimMissing:
                return null;
            case SimPukLocked:
                CharSequence text = this.mContext.getText(R.string.keyguard_sim_puk_locked_message);
                if (CscRune.SECURITY_KOR_USIM_TEXT) {
                    text = this.mContext.getText(R.string.kg_puk_locked_message);
                }
                return CscRune.SECURITY_SHOW_EMERGENCY_CALL_ONLY_PLMN_ON_SIM_LOCK ? charSequence : text;
            case SimLocked:
                CharSequence text2 = this.mContext.getText(R.string.kg_sim_locked_message);
                if (CscRune.SECURITY_KOR_USIM_TEXT) {
                    text2 = this.mContext.getText(R.string.kg_pin_locked_message);
                }
                if (!CscRune.SECURITY_SHOW_EMERGENCY_CALL_ONLY_PLMN_ON_SIM_LOCK) {
                    return text2;
                }
                break;
            case SimPermDisabled:
                return CscRune.SECURITY_KOR_USIM_TEXT ? "" : makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.keyguard_permanent_disabled_sim_message_short), charSequence);
            case SimNotReady:
                return "";
            case SimIoError:
                return makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.keyguard_sim_error_message_short), charSequence);
            case SimRestricted:
            case SimUnknown:
            default:
                return null;
        }
    }

    public StatusMode getStatusForIccState(int i) {
        if (!this.mKeyguardUpdateMonitor.mDeviceProvisioned && (i == 1 || i == 7)) {
            return StatusMode.SimMissingLocked;
        }
        if (i == 12) {
            return StatusMode.PersoLocked;
        }
        switch (i) {
            case 0:
                return StatusMode.SimUnknown;
            case 1:
                return StatusMode.SimMissing;
            case 2:
                return StatusMode.SimLocked;
            case 3:
                return StatusMode.SimPukLocked;
            case 4:
                return StatusMode.NetworkLocked;
            case 5:
                return StatusMode.Normal;
            case 6:
                return StatusMode.SimNotReady;
            case 7:
                return StatusMode.SimPermDisabled;
            case 8:
                return StatusMode.SimIoError;
            case 9:
                return StatusMode.SimRestricted;
            default:
                return StatusMode.SimUnknown;
        }
    }

    public final void handleSetListening(CarrierTextCallback carrierTextCallback) {
        AnonymousClass4 anonymousClass4 = this.mKnoxStateCallback;
        AnonymousClass3 anonymousClass3 = this.mPhoneStateListener;
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        if (carrierTextCallback == null) {
            this.mCarrierTextCallback = null;
            this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda7(this, 3));
            ((ArrayList) telephonyListenerManager.mTelephonyCallback.mActiveDataSubscriptionIdListeners).remove(anonymousClass3);
            telephonyListenerManager.updateListening();
            ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).removeCallback(anonymousClass4);
            cancelSatelliteCollectionJob("#handleSetListening has null callback");
            return;
        }
        this.mCarrierTextCallback = carrierTextCallback;
        if (!this.mNetworkSupported.get()) {
            if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
                this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda7(this, 0));
                return;
            } else {
                this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda7(carrierTextCallback, 4));
                return;
            }
        }
        this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda7(this, 2));
        telephonyListenerManager.addActiveDataSubscriptionIdListener(anonymousClass3);
        ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).registerCallback(anonymousClass4);
        cancelSatelliteCollectionJob("Starting new job");
        CarrierTextManagerLogger carrierTextManagerLogger = this.mLogger;
        carrierTextManagerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        CarrierTextManagerLogger$$ExternalSyntheticLambda2 carrierTextManagerLogger$$ExternalSyntheticLambda2 = new CarrierTextManagerLogger$$ExternalSyntheticLambda2(1);
        LogBuffer logBuffer = carrierTextManagerLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$$ExternalSyntheticLambda2, null);
        ((LogMessageImpl) logMessageObtain).str1 = carrierTextManagerLogger.location;
        logBuffer.commit(logMessageObtain);
        this.mSatelliteConnectionJob = this.mJavaAdapter.alwaysCollectFlow(((DeviceBasedSatelliteViewModelImpl) this.mDeviceBasedSatelliteViewModel).carrierText, new Consumer() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                CarrierTextManager carrierTextManager = this.f$0;
                String str = (String) obj;
                CarrierTextManagerLogger carrierTextManagerLogger2 = carrierTextManager.mLogger;
                carrierTextManagerLogger2.logUpdateCarrierTextForReason(5);
                LogLevel logLevel2 = LogLevel.VERBOSE;
                CarrierTextManagerLogger$$ExternalSyntheticLambda2 carrierTextManagerLogger$$ExternalSyntheticLambda22 = new CarrierTextManagerLogger$$ExternalSyntheticLambda2(2);
                LogBuffer logBuffer2 = carrierTextManagerLogger2.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("CarrierTextManagerLog", logLevel2, carrierTextManagerLogger$$ExternalSyntheticLambda22, null);
                ((LogMessageImpl) logMessageObtain2).str1 = str;
                logBuffer2.commit(logMessageObtain2);
                carrierTextManager.mSatelliteCarrierText = str;
                carrierTextManager.updateCarrierText(null);
            }
        });
    }

    public final boolean isRTL() {
        return (this.mContext.getResources().getConfiguration().screenLayout & 192) == 128;
    }

    public final CharSequence makeCarrierStringOnEmergencyCapable(CharSequence charSequence, CharSequence charSequence2) {
        return this.mIsEmergencyCallCapable ? concatenate(charSequence, charSequence2, this.mSeparator) : charSequence;
    }

    public void postToCallback(CarrierTextCallbackInfo carrierTextCallbackInfo) {
        CarrierTextCallback carrierTextCallback = this.mCarrierTextCallback;
        if (carrierTextCallback != null) {
            this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda1(carrierTextCallback, carrierTextCallbackInfo));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:115:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0109 A[EDGE_INSN: B:158:0x0109->B:30:0x0109 BREAK  A[LOOP:1: B:23:0x00e7->B:29:0x0106], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0238 A[EDGE_INSN: B:162:0x0238->B:71:0x0238 BREAK  A[LOOP:2: B:31:0x010c->B:70:0x022a], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0244 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02d4  */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateCarrierText(Intent intent) throws Resources.NotFoundException {
        String str;
        int[] iArr;
        int i;
        int i2;
        SubscriptionsOrder subscriptionsOrder;
        CarrierTextManagerLogger carrierTextManagerLogger;
        CharSequence charSequence;
        boolean z;
        CharSequence charSequenceJoinNotEmpty;
        CharSequence charSequenceJoinNotEmpty2;
        CharSequence charSequenceUpdateCarrierTextWithSimIoError;
        boolean z2;
        CustomSdkMonitor customSdkMonitor;
        String str2;
        Throwable th;
        boolean z3;
        CharSequence charSequence2;
        String string;
        String str3;
        CharSequence charSequence3;
        int i3;
        List list;
        ArrayList arrayList;
        String str4;
        ?? r9;
        Trace.beginSection("CarrierTextManager#updateCarrierText");
        List filteredSubscriptionInfo = this.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo();
        String string2 = this.mContext.getString(android.R.string.permlab_accessLastKnownCellId);
        ArrayList arrayList2 = (ArrayList) filteredSubscriptionInfo;
        int size = arrayList2.size();
        int[] iArr2 = new int[size];
        int i4 = this.mSimSlotsNumber;
        int[] iArr3 = new int[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            iArr3[i5] = -1;
        }
        CharSequence[] charSequenceArr = new CharSequence[size];
        CharSequence[] charSequenceArr2 = new CharSequence[size];
        CarrierTextManagerLogger carrierTextManagerLogger2 = this.mLogger;
        carrierTextManagerLogger2.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        CarrierTextManagerLogger$$ExternalSyntheticLambda2 carrierTextManagerLogger$$ExternalSyntheticLambda2 = new CarrierTextManagerLogger$$ExternalSyntheticLambda2(5);
        LogBuffer logBuffer = carrierTextManagerLogger2.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$$ExternalSyntheticLambda2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = size;
        logMessageImpl.str1 = carrierTextManagerLogger2.location;
        logBuffer.commit(logMessageObtain);
        Intent intentRegisterReceiver = (intent == null && TextUtils.isEmpty((CharSequence) this.plmnOfBroadcast.get(0))) ? this.mContext.registerReceiver(null, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED")) : intent;
        if (intentRegisterReceiver != 0) {
            str = string2;
            if (intentRegisterReceiver.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1) == -1) {
                r9 = 0;
                if (intentRegisterReceiver.getIntExtra("phone", 0) == 0) {
                }
                this.hasSpecialChar = Boolean.FALSE;
                i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    CharSequence carrierName = ((SubscriptionInfo) arrayList2.get(i)).getCarrierName();
                    if (carrierName != null && carrierName.toString().contains("&")) {
                        this.hasSpecialChar = Boolean.TRUE;
                        break;
                    }
                    i++;
                }
                boolean z4 = false;
                i2 = 0;
                boolean z5 = true;
                while (true) {
                    subscriptionsOrder = this.mSubscriptionsOrder;
                    carrierTextManagerLogger = carrierTextManagerLogger2;
                    charSequence = "";
                    if (i2 >= size) {
                        break;
                    }
                    boolean z6 = z4;
                    int subscriptionId = ((SubscriptionInfo) arrayList2.get(i2)).getSubscriptionId();
                    int[] iArr4 = iArr3;
                    int simSlotIndex = ((SubscriptionInfo) arrayList2.get(i2)).getSimSlotIndex();
                    int simOrder = subscriptionsOrder.getSimOrder(subscriptionId, filteredSubscriptionInfo);
                    List list2 = filteredSubscriptionInfo;
                    if (simOrder >= size || simOrder == -1) {
                        simOrder = i2;
                    }
                    charSequenceArr[simOrder] = "";
                    iArr[simOrder] = subscriptionId;
                    iArr4[simSlotIndex] = i2;
                    int simStateForSlotId = this.mKeyguardUpdateMonitor.getSimStateForSlotId(simSlotIndex);
                    CharSequence carrierName2 = ((SubscriptionInfo) arrayList2.get(i2)).getCarrierName();
                    CharSequence charSequence4 = (CharSequence) shortCarrierNameMap.get(carrierName2);
                    if (charSequence4 == null) {
                        charSequence4 = carrierName2;
                    }
                    if (isRTL()) {
                        arrayList = arrayList2;
                        if ("dea!".equals(carrierName2)) {
                            carrierName2 = "dea!";
                        }
                    } else {
                        arrayList = arrayList2;
                    }
                    CharSequence carrierTextForSimState = getCarrierTextForSimState(simStateForSlotId, carrierName2);
                    CharSequence carrierTextForSimState2 = getCarrierTextForSimState(simStateForSlotId, charSequence4);
                    String strValueOf = String.valueOf(carrierName2);
                    int i6 = i2;
                    LogLevel logLevel2 = LogLevel.VERBOSE;
                    int i7 = size;
                    boolean z7 = z5;
                    LogMessage logMessageObtain2 = logBuffer.obtain("CarrierTextManagerLog", logLevel2, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(6), null);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                    logMessageImpl2.int1 = subscriptionId;
                    logMessageImpl2.int2 = simStateForSlotId;
                    logMessageImpl2.str1 = strValueOf;
                    logBuffer.commit(logMessageObtain2);
                    Log.d("CarrierTextController", "carrierTextForSimState(" + subscriptionId + ")-(order: " + simOrder + ") : " + ((Object) carrierTextForSimState) + ", " + ((Object) carrierTextForSimState2));
                    if (carrierTextForSimState != null) {
                        charSequenceArr[simOrder] = carrierTextForSimState;
                        charSequenceArr2[simOrder] = carrierTextForSimState2;
                        z5 = false;
                    } else {
                        z5 = z7;
                    }
                    if (simStateForSlotId == 5) {
                        Trace.beginSection("WFC check");
                        ServiceState serviceState = (ServiceState) this.mKeyguardUpdateMonitor.mServiceStates.get(Integer.valueOf(subscriptionId));
                        if (serviceState == null || serviceState.getDataRegistrationState() != 0) {
                            z4 = z6;
                            Trace.endSection();
                        } else {
                            if (serviceState.getRilDataRadioTechnology() == 18) {
                                WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.mWifiRepository.getWifiNetwork().getValue();
                                if (!(wifiNetworkModel instanceof WifiNetworkModel.Active) || (str4 = ((WifiNetworkModel.Active) wifiNetworkModel).ssid) == null || Intrinsics.areEqual(str4, "<unknown ssid>")) {
                                }
                                Trace.endSection();
                            }
                            logBuffer.commit(logBuffer.obtain("CarrierTextManagerLog", logLevel2, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(3), null));
                            z4 = true;
                            Trace.endSection();
                        }
                    } else {
                        z4 = z6;
                    }
                    i2 = i6 + 1;
                    carrierTextManagerLogger2 = carrierTextManagerLogger;
                    filteredSubscriptionInfo = list2;
                    iArr3 = iArr4;
                    arrayList2 = arrayList;
                    size = i7;
                }
                List list3 = filteredSubscriptionInfo;
                boolean z8 = z4;
                int i8 = size;
                int[] iArr5 = iArr3;
                z = z5;
                if (!z || z8) {
                    charSequenceJoinNotEmpty = null;
                    charSequenceJoinNotEmpty2 = null;
                } else {
                    charSequenceJoinNotEmpty = makeCarrierStringOnEmergencyCapable((this.mShowMissingSim && this.mTelephonyCapable) ? this.mContext.getString(R.string.kg_missing_sim_message_short) : "", (TextUtils.isEmpty((CharSequence) this.plmnOfBroadcast.get(0)) || ((CharSequence) this.plmnOfBroadcast.get(0)).equals("")) ? str : (CharSequence) this.plmnOfBroadcast.get(0));
                    charSequenceJoinNotEmpty2 = charSequenceJoinNotEmpty;
                }
                if (TextUtils.isEmpty(charSequenceJoinNotEmpty)) {
                    charSequenceJoinNotEmpty = joinNotEmpty(this.mSeparator, charSequenceArr, Boolean.valueOf(isRTL()), this.hasSpecialChar);
                    charSequenceJoinNotEmpty2 = joinNotEmpty(this.mSeparator, charSequenceArr2, Boolean.valueOf(isRTL()), this.hasSpecialChar);
                }
                CharSequence charSequenceUpdateCarrierTextWithSimIoError2 = updateCarrierTextWithSimIoError(charSequenceJoinNotEmpty, charSequenceArr, iArr5, z);
                charSequenceUpdateCarrierTextWithSimIoError = updateCarrierTextWithSimIoError(charSequenceJoinNotEmpty2, charSequenceArr2, iArr5, z);
                if (WirelessUtils.isAirplaneModeOn(this.mContext)) {
                    charSequenceUpdateCarrierTextWithSimIoError2 = this.mContext.getText(R.string.kg_flight_mode);
                    StringBuilder sb = new StringBuilder();
                    for (int i9 = 0; i9 < i8; i9 = i3 + 1) {
                        int slotIndex = SubscriptionManager.getSlotIndex(iArr[i9]);
                        Boolean boolValueOf = Boolean.FALSE;
                        if (slotIndex != -1) {
                            boolValueOf = Boolean.valueOf(this.mSettingsHelper.isSimSettingOn(slotIndex));
                        }
                        if (this.voWifiConnected.isEmpty()) {
                            charSequence3 = charSequenceUpdateCarrierTextWithSimIoError2;
                            i3 = i9;
                        } else {
                            charSequence3 = charSequenceUpdateCarrierTextWithSimIoError2;
                            i3 = i9;
                            if (Boolean.TRUE.equals(this.voWifiConnected.get(Integer.valueOf(slotIndex))) && boolValueOf.booleanValue()) {
                                Log.d("CarrierTextController", "WFC PLMN INFO");
                                if (TextUtils.isEmpty(sb)) {
                                    sb.append((CharSequence) this.plmnOfBroadcast.get(Integer.valueOf(slotIndex)));
                                } else {
                                    list = list3;
                                    if (subscriptionsOrder.getSimOrder(iArr[i3], list) == 0) {
                                        StringBuilder sb2 = new StringBuilder((CharSequence) this.plmnOfBroadcast.get(Integer.valueOf(slotIndex)));
                                        sb2.append(this.mSeparator);
                                        sb2.append((CharSequence) sb);
                                        sb = sb2;
                                    } else {
                                        sb.append(this.mSeparator);
                                        sb.append((CharSequence) this.plmnOfBroadcast.get(Integer.valueOf(slotIndex)));
                                    }
                                }
                            }
                            charSequenceUpdateCarrierTextWithSimIoError2 = sb.length() == 0 ? sb.toString() : charSequence3;
                            list3 = list;
                        }
                        list = list3;
                        if (sb.length() == 0) {
                        }
                        list3 = list;
                    }
                    charSequenceUpdateCarrierTextWithSimIoError = charSequenceUpdateCarrierTextWithSimIoError2;
                    z2 = true;
                } else {
                    z2 = false;
                }
                customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
                if (customSdkMonitor == null || (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 4) != 0) {
                    Log.d("CarrierTextController", "CarrierText is clear by knoxstate");
                    charSequenceUpdateCarrierTextWithSimIoError = "";
                } else {
                    charSequence = charSequenceUpdateCarrierTextWithSimIoError2;
                }
                str2 = this.mSatelliteCarrierText;
                if (str2 != null) {
                    th = null;
                    LogMessage logMessageObtain3 = logBuffer.obtain("CarrierTextManagerLog", LogLevel.VERBOSE, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(7), null);
                    ((LogMessageImpl) logMessageObtain3).str1 = str2;
                    logBuffer.commit(logMessageObtain3);
                    charSequenceUpdateCarrierTextWithSimIoError = str2;
                    charSequence = charSequenceUpdateCarrierTextWithSimIoError;
                } else {
                    th = null;
                }
                if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
                    WifiTextManager wifiTextManager = this.mWifiTextManager;
                    if (wifiTextManager.connected) {
                        str3 = wifiTextManager.ssid;
                        if (str3 == null) {
                            String string3 = wifiTextManager.context.getString(R.string.wifi_connected_notification_title);
                            string3.getClass();
                            str3 = string3;
                            z3 = false;
                        } else {
                            int length = str3.length();
                            z3 = false;
                            if (length > 1 && str3.charAt(0) == '\"') {
                                int i10 = length - 1;
                                if (str3.charAt(i10) == '\"') {
                                    string = str3.substring(1, i10);
                                }
                            }
                        }
                        charSequence2 = str3;
                        charSequence = charSequence2;
                    } else {
                        z3 = false;
                        if (z2) {
                            string = wifiTextManager.context.getString(R.string.kg_flight_mode);
                            string.getClass();
                        } else {
                            string = wifiTextManager.context.getString(R.string.data_connection_no_internet);
                            string.getClass();
                        }
                    }
                    str3 = string;
                    charSequence2 = str3;
                    charSequence = charSequence2;
                } else {
                    z3 = false;
                    charSequence2 = charSequenceUpdateCarrierTextWithSimIoError;
                }
                StringBuilder sb3 = new StringBuilder("setCarrierText : ");
                sb3.append((Object) charSequence);
                sb3.append(", ");
                sb3.append((Object) charSequence2);
                sb3.append(" allSimsMissing : ");
                CarrierTextManager$$ExternalSyntheticOutline0.m(sb3, z, " anySimReady : ", z8, "CarrierTextController");
                if (this.mSatelliteCarrierText != null) {
                    z3 = true;
                }
                CarrierTextCallbackInfo carrierTextCallbackInfo = new CarrierTextCallbackInfo(carrierTextManagerLogger.location, charSequence, charSequence2, charSequenceArr, !z, z3, iArr, z2);
                LogMessage logMessageObtain4 = logBuffer.obtain("CarrierTextManagerLog", LogLevel.VERBOSE, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(0), th);
                LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain4;
                logMessageImpl3.str1 = ((Object) carrierTextCallbackInfo.carrierText) + ", " + ((Object) carrierTextCallbackInfo.carrierTextShort);
                logMessageImpl3.bool1 = carrierTextCallbackInfo.anySimReady;
                logMessageImpl3.bool2 = carrierTextCallbackInfo.airplaneMode;
                logBuffer.commit(logMessageObtain4);
                postToCallback(carrierTextCallbackInfo);
                Trace.endSection();
            }
            r9 = 0;
            int intExtra = intentRegisterReceiver.getIntExtra("phone", r9);
            iArr = iArr2;
            this.voWifiConnected.put(Integer.valueOf(intExtra), Boolean.valueOf(intentRegisterReceiver.getBooleanExtra("showEpdg", r9)));
            HashMap map = this.plmnOfBroadcast;
            Integer numValueOf = Integer.valueOf(intExtra);
            CarrierTextUtil carrierTextUtil = this.mCarrierTextUtil;
            map.put(numValueOf, carrierTextUtil.updateNetworkName(intentRegisterReceiver));
            String strValueOf2 = String.valueOf(intExtra);
            String strUpdateNetworkName = carrierTextUtil.updateNetworkName(intentRegisterReceiver);
            LogMessage logMessageObtain5 = logBuffer.obtain("CarrierTextManagerLog", logLevel, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(4), null);
            LogMessageImpl logMessageImpl4 = (LogMessageImpl) logMessageObtain5;
            logMessageImpl4.str1 = strValueOf2;
            logMessageImpl4.str2 = strUpdateNetworkName;
            logBuffer.commit(logMessageObtain5);
            this.hasSpecialChar = Boolean.FALSE;
            i = 0;
            while (true) {
                if (i >= size) {
                }
                i++;
            }
            boolean z42 = false;
            i2 = 0;
            boolean z52 = true;
            while (true) {
                subscriptionsOrder = this.mSubscriptionsOrder;
                carrierTextManagerLogger = carrierTextManagerLogger2;
                charSequence = "";
                if (i2 >= size) {
                }
                i2 = i6 + 1;
                carrierTextManagerLogger2 = carrierTextManagerLogger;
                filteredSubscriptionInfo = list2;
                iArr3 = iArr4;
                arrayList2 = arrayList;
                size = i7;
            }
            List list32 = filteredSubscriptionInfo;
            boolean z82 = z42;
            int i82 = size;
            int[] iArr52 = iArr3;
            z = z52;
            if (z) {
                charSequenceJoinNotEmpty = null;
                charSequenceJoinNotEmpty2 = null;
            }
            if (TextUtils.isEmpty(charSequenceJoinNotEmpty)) {
            }
            CharSequence charSequenceUpdateCarrierTextWithSimIoError22 = updateCarrierTextWithSimIoError(charSequenceJoinNotEmpty, charSequenceArr, iArr52, z);
            charSequenceUpdateCarrierTextWithSimIoError = updateCarrierTextWithSimIoError(charSequenceJoinNotEmpty2, charSequenceArr2, iArr52, z);
            if (WirelessUtils.isAirplaneModeOn(this.mContext)) {
            }
            customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
            if (customSdkMonitor == null) {
                Log.d("CarrierTextController", "CarrierText is clear by knoxstate");
                charSequenceUpdateCarrierTextWithSimIoError = "";
            }
            str2 = this.mSatelliteCarrierText;
            if (str2 != null) {
            }
            if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
            }
            StringBuilder sb32 = new StringBuilder("setCarrierText : ");
            sb32.append((Object) charSequence);
            sb32.append(", ");
            sb32.append((Object) charSequence2);
            sb32.append(" allSimsMissing : ");
            CarrierTextManager$$ExternalSyntheticOutline0.m(sb32, z, " anySimReady : ", z82, "CarrierTextController");
            if (this.mSatelliteCarrierText != null) {
            }
            CarrierTextCallbackInfo carrierTextCallbackInfo2 = new CarrierTextCallbackInfo(carrierTextManagerLogger.location, charSequence, charSequence2, charSequenceArr, !z, z3, iArr, z2);
            LogMessage logMessageObtain42 = logBuffer.obtain("CarrierTextManagerLog", LogLevel.VERBOSE, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(0), th);
            LogMessageImpl logMessageImpl32 = (LogMessageImpl) logMessageObtain42;
            logMessageImpl32.str1 = ((Object) carrierTextCallbackInfo2.carrierText) + ", " + ((Object) carrierTextCallbackInfo2.carrierTextShort);
            logMessageImpl32.bool1 = carrierTextCallbackInfo2.anySimReady;
            logMessageImpl32.bool2 = carrierTextCallbackInfo2.airplaneMode;
            logBuffer.commit(logMessageObtain42);
            postToCallback(carrierTextCallbackInfo2);
            Trace.endSection();
        }
        str = string2;
        iArr = iArr2;
        this.hasSpecialChar = Boolean.FALSE;
        i = 0;
        while (true) {
            if (i >= size) {
            }
            i++;
        }
        boolean z422 = false;
        i2 = 0;
        boolean z522 = true;
        while (true) {
            subscriptionsOrder = this.mSubscriptionsOrder;
            carrierTextManagerLogger = carrierTextManagerLogger2;
            charSequence = "";
            if (i2 >= size) {
            }
            i2 = i6 + 1;
            carrierTextManagerLogger2 = carrierTextManagerLogger;
            filteredSubscriptionInfo = list2;
            iArr3 = iArr4;
            arrayList2 = arrayList;
            size = i7;
        }
        List list322 = filteredSubscriptionInfo;
        boolean z822 = z422;
        int i822 = size;
        int[] iArr522 = iArr3;
        z = z522;
        if (z) {
        }
        if (TextUtils.isEmpty(charSequenceJoinNotEmpty)) {
        }
        CharSequence charSequenceUpdateCarrierTextWithSimIoError222 = updateCarrierTextWithSimIoError(charSequenceJoinNotEmpty, charSequenceArr, iArr522, z);
        charSequenceUpdateCarrierTextWithSimIoError = updateCarrierTextWithSimIoError(charSequenceJoinNotEmpty2, charSequenceArr2, iArr522, z);
        if (WirelessUtils.isAirplaneModeOn(this.mContext)) {
        }
        customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if (customSdkMonitor == null) {
        }
        str2 = this.mSatelliteCarrierText;
        if (str2 != null) {
        }
        if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
        }
        StringBuilder sb322 = new StringBuilder("setCarrierText : ");
        sb322.append((Object) charSequence);
        sb322.append(", ");
        sb322.append((Object) charSequence2);
        sb322.append(" allSimsMissing : ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(sb322, z, " anySimReady : ", z822, "CarrierTextController");
        if (this.mSatelliteCarrierText != null) {
        }
        CarrierTextCallbackInfo carrierTextCallbackInfo22 = new CarrierTextCallbackInfo(carrierTextManagerLogger.location, charSequence, charSequence2, charSequenceArr, !z, z3, iArr, z2);
        LogMessage logMessageObtain422 = logBuffer.obtain("CarrierTextManagerLog", LogLevel.VERBOSE, new CarrierTextManagerLogger$$ExternalSyntheticLambda2(0), th);
        LogMessageImpl logMessageImpl322 = (LogMessageImpl) logMessageObtain422;
        logMessageImpl322.str1 = ((Object) carrierTextCallbackInfo22.carrierText) + ", " + ((Object) carrierTextCallbackInfo22.carrierTextShort);
        logMessageImpl322.bool1 = carrierTextCallbackInfo22.anySimReady;
        logMessageImpl322.bool2 = carrierTextCallbackInfo22.airplaneMode;
        logBuffer.commit(logMessageObtain422);
        postToCallback(carrierTextCallbackInfo22);
        Trace.endSection();
    }

    public final CharSequence updateCarrierTextWithSimIoError(CharSequence charSequence, CharSequence[] charSequenceArr, int[] iArr, boolean z) {
        CharSequence carrierTextForSimState = getCarrierTextForSimState(8, "");
        for (int i = 0; i < this.mTelephonyManager.getActiveModemCount(); i++) {
            if (this.mSimErrorState[i]) {
                if (z) {
                    return concatenate(carrierTextForSimState, this.mContext.getText(android.R.string.keyguard_accessibility_pin_unlock), this.mSeparator);
                }
                int i2 = iArr[i];
                if (i2 != -1) {
                    charSequenceArr[i2] = concatenate(carrierTextForSimState, charSequenceArr[i2], this.mSeparator);
                } else {
                    charSequence = concatenate(charSequence, carrierTextForSimState, this.mSeparator);
                }
            }
        }
        return charSequence;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.CarrierTextManager$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.CarrierTextManager$3] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.CarrierTextManager$4] */
    private CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiRepository wifiRepository, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, JavaAdapter javaAdapter, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, final Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, SettingsHelper settingsHelper, WifiTextManager wifiTextManager) {
        this.mNetworkSupported = new AtomicBoolean();
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.keyguard.CarrierTextManager.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                CarrierTextCallback carrierTextCallback = CarrierTextManager.this.mCarrierTextCallback;
                if (carrierTextCallback != null) {
                    carrierTextCallback.finishedWakingUp();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                CarrierTextCallback carrierTextCallback = CarrierTextManager.this.mCarrierTextCallback;
                if (carrierTextCallback != null) {
                    carrierTextCallback.startedGoingToSleep();
                }
            }
        };
        this.mCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.CarrierTextManager.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDeviceProvisioned() throws Resources.NotFoundException {
                CarrierTextManager.this.updateCarrierText(null);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRefreshCarrierInfo(Intent intent) throws Resources.NotFoundException {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                carrierTextManager.mLogger.logUpdateCarrierTextForReason(1);
                carrierTextManager.updateCarrierText(intent);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) throws Resources.NotFoundException {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                if (i2 < 0 || i2 >= carrierTextManager.mSimSlotsNumber) {
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onSimStateChanged() - slotId invalid: ", " mTelephonyCapable: ");
                    sbM.append(Boolean.toString(carrierTextManager.mTelephonyCapable));
                    Log.d("CarrierTextController", sbM.toString());
                    return;
                }
                boolean z3 = true;
                if (carrierTextManager.getStatusForIccState(i3) == StatusMode.SimIoError) {
                    carrierTextManager.mSimErrorState[i2] = true;
                } else {
                    boolean[] zArr = carrierTextManager.mSimErrorState;
                    if (zArr[i2]) {
                        zArr[i2] = false;
                    } else if (i3 == 4) {
                        carrierTextManager.mSimNetworkLock[i2] = true;
                    } else {
                        boolean[] zArr2 = carrierTextManager.mSimNetworkLock;
                        if (zArr2[i2]) {
                            zArr2[i2] = false;
                        } else if (i3 == 2 || i3 == 3 || i3 == 12 ? i == -1 : i3 != 0 && i3 != 7) {
                            z3 = false;
                        }
                    }
                }
                Log.d("CarrierTextController", "onSimStateChanged: " + carrierTextManager.getStatusForIccState(i3) + ", update: " + z3);
                if (z3) {
                    carrierTextManager.updateCarrierText(null);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTelephonyCapable(boolean z3) throws Resources.NotFoundException {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                carrierTextManager.mLogger.logUpdateCarrierTextForReason(2);
                carrierTextManager.mTelephonyCapable = z3;
                carrierTextManager.updateCarrierText(null);
            }
        };
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.keyguard.CarrierTextManager.3
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(int i) throws Resources.NotFoundException {
                if (CarrierTextManager.this.mNetworkSupported.get()) {
                    CarrierTextManager carrierTextManager = CarrierTextManager.this;
                    if (carrierTextManager.mCarrierTextCallback != null) {
                        carrierTextManager.mLogger.logUpdateCarrierTextForReason(4);
                        CarrierTextManager.this.updateCarrierText(null);
                    }
                }
            }
        };
        this.mKnoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.keyguard.CarrierTextManager.4
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onUpdateLockscreenHiddenItems() throws Resources.NotFoundException {
                CarrierTextManager.this.updateCarrierText(null);
            }
        };
        this.hasSpecialChar = Boolean.FALSE;
        this.mContext = context;
        final boolean zHasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        this.mIsEmergencyCallCapable = telephonyManager.isVoiceCapable() && zHasSystemFeature;
        this.mShowMissingSim = z2;
        this.mWifiRepository = wifiRepository;
        this.mDeviceBasedSatelliteViewModel = deviceBasedSatelliteViewModel;
        this.mJavaAdapter = javaAdapter;
        this.mTelephonyManager = telephonyManager;
        this.mSeparator = " • ";
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mCarrierTextUtil = carrierTextUtil;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        int supportedModemCount = telephonyManager.getSupportedModemCount();
        this.mSimSlotsNumber = supportedModemCount;
        this.mSimErrorState = new boolean[supportedModemCount];
        this.mSimNetworkLock = new boolean[supportedModemCount];
        this.voWifiConnected = new HashMap();
        this.plmnOfBroadcast = new HashMap();
        shortCarrierNameMap.put("MONGOLIA UNITEL LLC", "UNITEL");
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSubscriptionsOrder = subscriptionsOrder;
        this.mLogger = carrierTextManagerLogger;
        if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
            this.mWifiTextManager = wifiTextManager;
            Function2 function2 = new Function2() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                    ((Boolean) obj2).booleanValue();
                    HashMap map = CarrierTextManager.shortCarrierNameMap;
                    this.f$0.updateCarrierText(null);
                    return null;
                }
            };
            wifiTextManager.getClass();
            WifiTextManager$register$1 wifiTextManager$register$1 = new WifiTextManager$register$1(wifiTextManager, function2, null);
            CoroutineScope coroutineScope = wifiTextManager.scope;
            BuildersKt.launch$default(coroutineScope, null, null, wifiTextManager$register$1, 3);
            BuildersKt.launch$default(coroutineScope, null, null, new WifiTextManager$register$2(wifiTextManager, function2, null), 3);
        }
        this.mSettingsHelper = settingsHelper;
        executor2.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CarrierTextManager carrierTextManager = this.f$0;
                boolean z3 = zHasSystemFeature;
                Executor executor3 = executor;
                carrierTextManager.mTelephonyManager.isDataCapable();
                if (z3 && carrierTextManager.mNetworkSupported.compareAndSet(false, z3)) {
                    carrierTextManager.handleSetListening(carrierTextManager.mCarrierTextCallback);
                    executor3.execute(new CarrierTextManager$$ExternalSyntheticLambda7(carrierTextManager, 1));
                }
            }
        });
    }

    public interface CarrierTextCallback {
        void updateCarrierInfo(CarrierTextCallbackInfo carrierTextCallbackInfo);

        default void finishedWakingUp() {
        }

        default void startedGoingToSleep() {
        }
    }
}
