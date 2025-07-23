package com.android.keyguard;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticLambda2;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.WakefulnessLifecycle;
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
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean isEmpty = TextUtils.isEmpty(charSequence);
        boolean isEmpty2 = TextUtils.isEmpty(charSequence2);
        if (isEmpty || isEmpty2) {
            return !isEmpty ? charSequence : !isEmpty2 ? charSequence2 : "";
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
            LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$$ExternalSyntheticLambda2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = carrierTextManagerLogger.location;
            logMessageImpl.str2 = str;
            logBuffer.commit(obtain);
            job.cancel(new CancellationException(str));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00b2 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.CharSequence getCarrierTextForSimState(int r3, java.lang.CharSequence r4) {
        /*
            r2 = this;
            com.android.keyguard.CarrierTextManager$StatusMode r3 = r2.getStatusForIccState(r3)
            int r3 = r3.ordinal()
            java.lang.String r0 = ""
            r1 = 0
            switch(r3) {
                case 0: goto L94;
                case 1: goto L86;
                case 2: goto L7c;
                case 3: goto L7b;
                case 4: goto L69;
                case 5: goto L4d;
                case 6: goto L31;
                case 7: goto L1e;
                case 8: goto L1d;
                case 9: goto Lf;
                case 10: goto Le;
                case 11: goto Le;
                default: goto Le;
            }
        Le:
            return r1
        Lf:
            android.content.Context r3 = r2.mContext
            r0 = 2131953985(0x7f130941, float:1.9544456E38)
            java.lang.CharSequence r3 = r3.getText(r0)
            java.lang.CharSequence r2 = r2.makeCarrierStringOnEmergencyCapable(r3, r4)
            return r2
        L1d:
            return r0
        L1e:
            android.content.Context r3 = r2.mContext
            r1 = 2131953975(0x7f130937, float:1.9544436E38)
            java.lang.CharSequence r3 = r3.getText(r1)
            java.lang.CharSequence r2 = r2.makeCarrierStringOnEmergencyCapable(r3, r4)
            boolean r3 = com.android.systemui.CscRune.SECURITY_KOR_USIM_TEXT
            if (r3 == 0) goto L30
            return r0
        L30:
            return r2
        L31:
            android.content.Context r3 = r2.mContext
            r0 = 2131954439(0x7f130b07, float:1.9545377E38)
            java.lang.CharSequence r3 = r3.getText(r0)
            boolean r0 = com.android.systemui.CscRune.SECURITY_KOR_USIM_TEXT
            if (r0 == 0) goto L47
            android.content.Context r2 = r2.mContext
            r3 = 2131954345(0x7f130aa9, float:1.9545187E38)
            java.lang.CharSequence r3 = r2.getText(r3)
        L47:
            boolean r2 = com.android.systemui.CscRune.SECURITY_DIRECT_CALL_TO_ECC
            if (r2 == 0) goto L4c
            goto Lb2
        L4c:
            return r3
        L4d:
            android.content.Context r3 = r2.mContext
            r0 = 2131953987(0x7f130943, float:1.954446E38)
            java.lang.CharSequence r3 = r3.getText(r0)
            boolean r0 = com.android.systemui.CscRune.SECURITY_KOR_USIM_TEXT
            if (r0 == 0) goto L63
            android.content.Context r2 = r2.mContext
            r3 = 2131954414(0x7f130aee, float:1.9545327E38)
            java.lang.CharSequence r3 = r2.getText(r3)
        L63:
            boolean r2 = com.android.systemui.CscRune.SECURITY_DIRECT_CALL_TO_ECC
            if (r2 == 0) goto L68
            goto Lb2
        L68:
            return r3
        L69:
            boolean r3 = com.android.systemui.CscRune.SECURITY_KOR_USIM_TEXT
            if (r3 == 0) goto L7b
            android.content.Context r3 = r2.mContext
            r0 = 2131954217(0x7f130a29, float:1.9544927E38)
            java.lang.CharSequence r3 = r3.getText(r0)
            java.lang.CharSequence r2 = r2.makeCarrierStringOnEmergencyCapable(r3, r4)
            return r2
        L7b:
            return r1
        L7c:
            android.content.Context r2 = r2.mContext
            r3 = 2131954342(0x7f130aa6, float:1.954518E38)
            java.lang.CharSequence r2 = r2.getText(r3)
            return r2
        L86:
            android.content.Context r3 = r2.mContext
            r0 = 2131953971(0x7f130933, float:1.9544428E38)
            java.lang.CharSequence r3 = r3.getText(r0)
            java.lang.CharSequence r2 = r2.makeCarrierStringOnEmergencyCapable(r3, r4)
            return r2
        L94:
            boolean r3 = r2.isRTL()
            if (r3 == 0) goto Lb2
            java.lang.Boolean r2 = r2.hasSpecialChar
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto Lb2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "\u200f"
            r2.<init>(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            return r2
        Lb2:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.CarrierTextManager.getCarrierTextForSimState(int, java.lang.CharSequence):java.lang.CharSequence");
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
        LogMessage obtain = logBuffer.obtain("CarrierTextManagerLog", logLevel, carrierTextManagerLogger$$ExternalSyntheticLambda2, null);
        ((LogMessageImpl) obtain).str1 = carrierTextManagerLogger.location;
        logBuffer.commit(obtain);
        this.mSatelliteConnectionJob = this.mJavaAdapter.alwaysCollectFlow(((DeviceBasedSatelliteViewModelImpl) this.mDeviceBasedSatelliteViewModel).carrierText, new Consumer() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                String str = (String) obj;
                CarrierTextManagerLogger carrierTextManagerLogger2 = carrierTextManager.mLogger;
                carrierTextManagerLogger2.logUpdateCarrierTextForReason(5);
                LogLevel logLevel2 = LogLevel.VERBOSE;
                CarrierTextManagerLogger$$ExternalSyntheticLambda2 carrierTextManagerLogger$$ExternalSyntheticLambda22 = new CarrierTextManagerLogger$$ExternalSyntheticLambda2(2);
                LogBuffer logBuffer2 = carrierTextManagerLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("CarrierTextManagerLog", logLevel2, carrierTextManagerLogger$$ExternalSyntheticLambda22, null);
                ((LogMessageImpl) obtain2).str1 = str;
                logBuffer2.commit(obtain2);
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x008e, code lost:
    
        if (r7.getIntExtra("phone", 0) == 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0210, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1, "<unknown ssid>") == false) goto L66;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0109 A[EDGE_INSN: B:160:0x0109->B:25:0x0109 BREAK  A[LOOP:1: B:18:0x00e7->B:158:0x0106], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0238 A[EDGE_INSN: B:67:0x0238->B:68:0x0238 BREAK  A[LOOP:2: B:26:0x010c->B:60:0x022a], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02d4  */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCarrierText(android.content.Intent r28) {
        /*
            Method dump skipped, instructions count: 1183
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.CarrierTextManager.updateCarrierText(android.content.Intent):void");
    }

    public final CharSequence updateCarrierTextWithSimIoError(CharSequence charSequence, CharSequence[] charSequenceArr, int[] iArr, boolean z) {
        CharSequence carrierTextForSimState = getCarrierTextForSimState(8, "");
        for (int i = 0; i < this.mTelephonyManager.getActiveModemCount(); i++) {
            if (this.mSimErrorState[i]) {
                if (z) {
                    return concatenate(carrierTextForSimState, this.mContext.getText(R.string.keyguard_accessibility_pattern_area), this.mSeparator);
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
            public final void onDeviceProvisioned() {
                CarrierTextManager.this.updateCarrierText(null);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRefreshCarrierInfo(Intent intent) {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                carrierTextManager.mLogger.logUpdateCarrierTextForReason(1);
                carrierTextManager.updateCarrierText(intent);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                if (i2 < 0 || i2 >= carrierTextManager.mSimSlotsNumber) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onSimStateChanged() - slotId invalid: ", " mTelephonyCapable: ");
                    m.append(Boolean.toString(carrierTextManager.mTelephonyCapable));
                    Log.d("CarrierTextController", m.toString());
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
            public final void onTelephonyCapable(boolean z3) {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                carrierTextManager.mLogger.logUpdateCarrierTextForReason(2);
                carrierTextManager.mTelephonyCapable = z3;
                carrierTextManager.updateCarrierText(null);
            }
        };
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.keyguard.CarrierTextManager.3
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(int i) {
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
            public final void onUpdateLockscreenHiddenItems() {
                CarrierTextManager.this.updateCarrierText(null);
            }
        };
        this.hasSpecialChar = Boolean.FALSE;
        this.mContext = context;
        final boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        this.mIsEmergencyCallCapable = telephonyManager.isVoiceCapable() && hasSystemFeature;
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
                public final Object invoke(Object obj, Object obj2) {
                    ((Boolean) obj2).booleanValue();
                    HashMap hashMap = CarrierTextManager.shortCarrierNameMap;
                    CarrierTextManager.this.updateCarrierText(null);
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
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                boolean z3 = hasSystemFeature;
                Executor executor3 = executor;
                carrierTextManager.mTelephonyManager.isDataCapable();
                if (z3 && carrierTextManager.mNetworkSupported.compareAndSet(false, z3)) {
                    carrierTextManager.handleSetListening(carrierTextManager.mCarrierTextCallback);
                    executor3.execute(new CarrierTextManager$$ExternalSyntheticLambda7(carrierTextManager, 1));
                }
            }
        });
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface CarrierTextCallback {
        void updateCarrierInfo(CarrierTextCallbackInfo carrierTextCallbackInfo);

        default void finishedWakingUp() {
        }

        default void startedGoingToSleep() {
        }
    }
}
