package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger;
import com.android.systemui.BasicRune;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CarrierTextManager {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Builder {
        public final Executor mBgExecutor;
        public final CarrierTextUtil mCarrierTextUtil;
        public final Context mContext;
        public String mDebugLocation;
        public final DeviceBasedSatelliteViewModel mDeviceBasedSatelliteViewModel;
        public final JavaAdapter mJavaAdapter;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final CarrierTextManagerLogger mLogger;
        public final Executor mMainExecutor;
        public final String mSeparator = " | ";
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
            String str = this.mDebugLocation;
            CarrierTextManagerLogger carrierTextManagerLogger = this.mLogger;
            carrierTextManagerLogger.location = str;
            Context context = this.mContext;
            boolean z = this.mShowAirplaneMode;
            boolean z2 = this.mShowMissingSim;
            TelephonyManager telephonyManager = this.mTelephonyManager;
            Executor executor = this.mMainExecutor;
            Executor executor2 = this.mBgExecutor;
            SettingsHelper settingsHelper = this.mSettingsHelper;
            WifiTextManager wifiTextManager = this.mWifiTextManager;
            return new CarrierTextManager(context, this.mSeparator, z, z2, this.mWifiRepository, this.mDeviceBasedSatelliteViewModel, this.mJavaAdapter, telephonyManager, this.mTelephonyListenerManager, this.mCarrierTextUtil, this.mWakefulnessLifecycle, executor, executor2, this.mKeyguardUpdateMonitor, this.mSubscriptionsOrder, carrierTextManagerLogger, settingsHelper, wifiTextManager, 0);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CarrierTextCallbackInfo {
        public final boolean airplaneMode;
        public final boolean anySimReady;
        public final CharSequence carrierText;
        public final boolean isInSatelliteMode;
        public final CharSequence[] listOfCarriers;
        public final String location;
        public final int[] subscriptionIds;

        public CarrierTextCallbackInfo(CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int[] iArr) {
            this(null, charSequence, charSequenceArr, z, false, iArr, false);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CarrierTextCallbackInfo{carrierText=");
            sb.append((Object) this.carrierText);
            sb.append(", listOfCarriers=");
            sb.append(Arrays.toString(this.listOfCarriers));
            sb.append(", anySimReady=");
            sb.append(this.anySimReady);
            sb.append(", isInSatelliteMode=");
            sb.append(this.isInSatelliteMode);
            sb.append(", subscriptionIds=");
            sb.append(Arrays.toString(this.subscriptionIds));
            sb.append(", airplaneMode=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.airplaneMode, '}');
        }

        public CarrierTextCallbackInfo(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, boolean z2, int[] iArr, boolean z3) {
            this.carrierText = charSequence;
            this.listOfCarriers = charSequenceArr;
            this.anySimReady = z;
            this.isInSatelliteMode = z2;
            this.subscriptionIds = iArr;
            this.airplaneMode = z3;
            this.location = str;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public /* synthetic */ CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiRepository wifiRepository, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, JavaAdapter javaAdapter, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, SettingsHelper settingsHelper, WifiTextManager wifiTextManager, int i) {
        this(context, charSequence, z, z2, wifiRepository, deviceBasedSatelliteViewModel, javaAdapter, telephonyManager, telephonyListenerManager, carrierTextUtil, wakefulnessLifecycle, executor, executor2, keyguardUpdateMonitor, subscriptionsOrder, carrierTextManagerLogger, settingsHelper, wifiTextManager);
    }

    public static CharSequence concatenate(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        boolean z = !TextUtils.isEmpty(charSequence);
        boolean z2 = !TextUtils.isEmpty(charSequence2);
        if (!z || !z2) {
            return z ? charSequence : z2 ? charSequence2 : "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(charSequence);
        sb.append(charSequence3);
        sb.append(charSequence2);
        return sb.toString();
    }

    public final CharSequence getCarrierTextForSimState(int i, CharSequence charSequence) {
        CharSequence text;
        switch (getStatusForIccState(i)) {
            case Normal:
                if (!isRTL() || this.hasSpecialChar.booleanValue()) {
                    return charSequence;
                }
                return "\u200f" + ((Object) charSequence);
            case NetworkLocked:
                return makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.keyguard_network_locked_message), charSequence);
            case PersoLocked:
                return this.mContext.getText(R.string.kg_perso_locked_message);
            case SimMissingLocked:
                if (CscRune.SECURITY_KOR_USIM_TEXT) {
                    return makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.kg_missing_sim_message_short), charSequence);
                }
            case SimMissing:
            case SimRestricted:
            case SimUnknown:
            default:
                return null;
            case SimPukLocked:
                text = this.mContext.getText(R.string.keyguard_sim_puk_locked_message);
                if (CscRune.SECURITY_KOR_USIM_TEXT) {
                    text = this.mContext.getText(R.string.kg_puk_locked_message);
                }
                if (CscRune.SECURITY_DIRECT_CALL_TO_ECC) {
                    return charSequence;
                }
                return text;
            case SimLocked:
                text = this.mContext.getText(R.string.kg_sim_locked_message);
                if (CscRune.SECURITY_KOR_USIM_TEXT) {
                    text = this.mContext.getText(R.string.kg_pin_locked_message);
                }
                if (CscRune.SECURITY_DIRECT_CALL_TO_ECC) {
                    return charSequence;
                }
                return text;
            case SimPermDisabled:
                CharSequence makeCarrierStringOnEmergencyCapable = makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.keyguard_permanent_disabled_sim_message_short), charSequence);
                if (!CscRune.SECURITY_KOR_USIM_TEXT) {
                    return makeCarrierStringOnEmergencyCapable;
                }
            case SimNotReady:
                return "";
            case SimIoError:
                return makeCarrierStringOnEmergencyCapable(this.mContext.getText(R.string.keyguard_sim_error_message_short), charSequence);
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
        CarrierTextManagerLogger carrierTextManagerLogger = this.mLogger;
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        if (carrierTextCallback == null) {
            this.mCarrierTextCallback = null;
            this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda6(this, 3));
            ((ArrayList) telephonyListenerManager.mTelephonyCallback.mActiveDataSubscriptionIdListeners).remove(anonymousClass3);
            telephonyListenerManager.updateListening();
            ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).removeCallback(anonymousClass4);
            Job job = this.mSatelliteConnectionJob;
            if (job != null) {
                carrierTextManagerLogger.logStopListeningForSatelliteCarrierText("#handleSetListening has null callback");
                job.cancel(new CancellationException("#handleSetListening has null callback"));
                return;
            }
            return;
        }
        this.mCarrierTextCallback = carrierTextCallback;
        if (!this.mNetworkSupported.get()) {
            if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
                this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda6(this, 0));
                return;
            } else {
                this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda6(carrierTextCallback, 4));
                return;
            }
        }
        this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda6(this, 2));
        telephonyListenerManager.addActiveDataSubscriptionIdListener(anonymousClass3);
        ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).registerCallback(anonymousClass4);
        Job job2 = this.mSatelliteConnectionJob;
        if (job2 != null) {
            carrierTextManagerLogger.logStopListeningForSatelliteCarrierText("Starting new job");
            job2.cancel(new CancellationException("Starting new job"));
        }
        carrierTextManagerLogger.logStartListeningForSatelliteCarrierText();
        this.mSatelliteConnectionJob = this.mJavaAdapter.alwaysCollectFlow(((DeviceBasedSatelliteViewModelImpl) this.mDeviceBasedSatelliteViewModel).carrierText, new Consumer() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CarrierTextManager carrierTextManager = CarrierTextManager.this;
                String str = (String) obj;
                CarrierTextManagerLogger carrierTextManagerLogger2 = carrierTextManager.mLogger;
                carrierTextManagerLogger2.logUpdateCarrierTextForReason(5);
                carrierTextManagerLogger2.logNewSatelliteCarrierText(str);
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
            this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(0, carrierTextCallback, carrierTextCallbackInfo));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0190, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1, "<unknown ssid>") == false) goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x041e  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x03fe  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0298 A[EDGE_INSN: B:191:0x0298->B:119:0x0298 BREAK  A[LOOP:4: B:103:0x0276->B:109:0x02b5], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0199 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCarrierText(android.content.Intent r22) {
        /*
            Method dump skipped, instructions count: 1080
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.CarrierTextManager.updateCarrierText(android.content.Intent):void");
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.keyguard.CarrierTextManager$1] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.keyguard.CarrierTextManager$3] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.keyguard.CarrierTextManager$4] */
    private CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiRepository wifiRepository, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, JavaAdapter javaAdapter, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, SettingsHelper settingsHelper, WifiTextManager wifiTextManager) {
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
        this.mIsEmergencyCallCapable = telephonyManager.isVoiceCapable();
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
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSubscriptionsOrder = subscriptionsOrder;
        this.mLogger = carrierTextManagerLogger;
        if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
            this.mWifiTextManager = wifiTextManager;
            Function2 function2 = new Function2() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Boolean) obj2).booleanValue();
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
        executor2.execute(new CarrierTextManager$$ExternalSyntheticLambda0(1, this, executor));
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface CarrierTextCallback {
        void updateCarrierInfo(CarrierTextCallbackInfo carrierTextCallbackInfo);

        default void finishedWakingUp() {
        }

        default void startedGoingToSleep() {
        }
    }
}
