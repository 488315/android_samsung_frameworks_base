package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.logging.CarrierTextManagerLogger;
import com.android.settingslib.WirelessUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.shade.carrier.CarrierTextUtil;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CarrierTextManager {
    public Boolean hasSpecialChar;
    public final Executor mBgExecutor;
    protected final KeyguardUpdateMonitorCallback mCallback;
    public CarrierTextCallback mCarrierTextCallback;
    public final CarrierTextUtil mCarrierTextUtil;
    public final Context mContext;
    public final GlobalSettings mGlobalSettings;
    public final boolean mIsEmergencyCallCapable;
    protected KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final C06384 mKnoxStateCallback;
    public final CarrierTextManagerLogger mLogger;
    public final Executor mMainExecutor;
    public final AtomicBoolean mNetworkSupported;
    public final C06373 mPhoneStateListener;
    public final CharSequence mSeparator;
    public final boolean mShowMissingSim;
    public final boolean[] mSimErrorState;
    public final boolean[] mSimNetworkLock;
    public final int mSimSlotsNumber;
    public final SubscriptionsOrder mSubscriptionsOrder;
    public boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final C06351 mWakefulnessObserver;
    public final WifiRepository mWifiRepository;
    public final WifiTextManager mWifiTextManager;
    public final HashMap plmnOfBroadcast;
    public final HashMap voWifiConnected;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.CarrierTextManager$5 */
    public abstract /* synthetic */ class AbstractC06395 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode;

        static {
            int[] iArr = new int[StatusMode.values().length];
            $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode = iArr;
            try {
                iArr[StatusMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimNotReady.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.NetworkLocked.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.PersoLocked.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimMissing.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimPermDisabled.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimMissingLocked.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimLocked.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimPukLocked.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimIoError.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimRestricted.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimUnknown.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final Executor mBgExecutor;
        public final CarrierTextUtil mCarrierTextUtil;
        public final Context mContext;
        public String mDebugLocation;
        public final GlobalSettings mGlobalSettings;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final CarrierTextManagerLogger mLogger;
        public final Executor mMainExecutor;
        public final String mSeparator = " | ";
        public boolean mShowAirplaneMode;
        public boolean mShowMissingSim;
        public final SubscriptionsOrder mSubscriptionsOrder;
        public final TelephonyListenerManager mTelephonyListenerManager;
        public final TelephonyManager mTelephonyManager;
        public final WakefulnessLifecycle mWakefulnessLifecycle;
        public final WifiRepository mWifiRepository;
        public final WifiTextManager mWifiTextManager;

        public Builder(Context context, Resources resources, WifiRepository wifiRepository, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, GlobalSettings globalSettings, WifiTextManager wifiTextManager) {
            this.mContext = context;
            this.mWifiRepository = wifiRepository;
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
            this.mGlobalSettings = globalSettings;
        }

        public final CarrierTextManager build() {
            String str = this.mDebugLocation;
            CarrierTextManagerLogger carrierTextManagerLogger = this.mLogger;
            carrierTextManagerLogger.location = str;
            return new CarrierTextManager(this.mContext, this.mSeparator, this.mShowAirplaneMode, this.mShowMissingSim, this.mWifiRepository, this.mTelephonyManager, this.mTelephonyListenerManager, this.mCarrierTextUtil, this.mWakefulnessLifecycle, this.mMainExecutor, this.mBgExecutor, this.mKeyguardUpdateMonitor, this.mSubscriptionsOrder, carrierTextManagerLogger, this.mGlobalSettings, this.mWifiTextManager, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CarrierTextCallbackInfo {
        public final boolean airplaneMode;
        public final boolean anySimReady;
        public final CharSequence carrierText;
        public final CharSequence[] listOfCarriers;
        public final String location;
        public final int[] subscriptionIds;

        public CarrierTextCallbackInfo(CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int[] iArr) {
            this(null, charSequence, charSequenceArr, z, iArr, false);
        }

        public final String toString() {
            return "CarrierTextCallbackInfo{carrierText=" + ((Object) this.carrierText) + ", listOfCarriers=" + Arrays.toString(this.listOfCarriers) + ", anySimReady=" + this.anySimReady + ", subscriptionIds=" + Arrays.toString(this.subscriptionIds) + ", airplaneMode=" + this.airplaneMode + '}';
        }

        public CarrierTextCallbackInfo(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int[] iArr, boolean z2) {
            this.carrierText = charSequence;
            this.listOfCarriers = charSequenceArr;
            this.anySimReady = z;
            this.subscriptionIds = iArr;
            this.airplaneMode = z2;
            this.location = str;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public /* synthetic */ CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiRepository wifiRepository, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, GlobalSettings globalSettings, WifiTextManager wifiTextManager, int i) {
        this(context, charSequence, z, z2, wifiRepository, telephonyManager, telephonyListenerManager, carrierTextUtil, wakefulnessLifecycle, executor, executor2, keyguardUpdateMonitor, subscriptionsOrder, carrierTextManagerLogger, globalSettings, wifiTextManager);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final CharSequence getCarrierTextForSimState(int i, CharSequence charSequence) {
        CharSequence text;
        int i2 = AbstractC06395.$SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[getStatusForIccState(i).ordinal()];
        Context context = this.mContext;
        switch (i2) {
            case 1:
                if (!isRTL() || this.hasSpecialChar.booleanValue()) {
                    return charSequence;
                }
                return "\u200f" + ((Object) charSequence);
            case 2:
                return "";
            case 3:
                return makeCarrierStringOnEmergencyCapable(context.getText(R.string.keyguard_network_locked_message), charSequence);
            case 4:
                return context.getText(R.string.kg_perso_locked_message);
            case 5:
            case 11:
            case 12:
            default:
                return null;
            case 6:
                CharSequence makeCarrierStringOnEmergencyCapable = makeCarrierStringOnEmergencyCapable(context.getText(R.string.keyguard_permanent_disabled_sim_message_short), charSequence);
                if (!LsRune.SECURITY_KOR_USIM_TEXT) {
                    return makeCarrierStringOnEmergencyCapable;
                }
                return "";
            case 7:
                if (LsRune.SECURITY_KOR_USIM_TEXT) {
                    return makeCarrierStringOnEmergencyCapable(context.getText(R.string.kg_missing_sim_message_short), charSequence);
                }
                return null;
            case 8:
                text = context.getText(R.string.kg_sim_locked_message);
                if (LsRune.SECURITY_KOR_USIM_TEXT) {
                    text = context.getText(R.string.kg_pin_locked_message);
                }
                if (LsRune.SECURITY_DIRECT_CALL_TO_ECC) {
                    return charSequence;
                }
                return text;
            case 9:
                text = context.getText(R.string.keyguard_sim_puk_locked_message);
                if (LsRune.SECURITY_KOR_USIM_TEXT) {
                    text = context.getText(R.string.kg_puk_locked_message);
                }
                if (LsRune.SECURITY_DIRECT_CALL_TO_ECC) {
                    return charSequence;
                }
                return text;
            case 10:
                return makeCarrierStringOnEmergencyCapable(context.getText(R.string.keyguard_sim_error_message_short), charSequence);
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
        C06384 c06384 = this.mKnoxStateCallback;
        C06373 c06373 = this.mPhoneStateListener;
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        Executor executor = this.mMainExecutor;
        if (carrierTextCallback == null) {
            this.mCarrierTextCallback = null;
            executor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(this, 3));
            ((ArrayList) telephonyListenerManager.mTelephonyCallback.mActiveDataSubscriptionIdListeners).remove(c06373);
            telephonyListenerManager.updateListening();
            ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).removeCallback(c06384);
            return;
        }
        this.mCarrierTextCallback = carrierTextCallback;
        if (this.mNetworkSupported.get()) {
            executor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(this, 1));
            telephonyListenerManager.addActiveDataSubscriptionIdListener(c06373);
            ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).registerCallback(c06384);
        } else if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
            executor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(this, 2));
        } else {
            executor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(carrierTextCallback, 4));
        }
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
            this.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextCallback, carrierTextCallbackInfo));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006d, code lost:
    
        if (r8.getIntExtra("phone", 0) == 0) goto L20;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0391 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03c3  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x02b9 A[EDGE_INSN: B:187:0x02b9->B:98:0x02b9 BREAK  A[LOOP:3: B:82:0x0280->B:88:0x02b4], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x00cd A[EDGE_INSN: B:219:0x00cd->B:26:0x00cd BREAK  A[LOOP:1: B:19:0x00ab->B:217:0x00ca], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x019e A[EDGE_INSN: B:57:0x019e->B:58:0x019e BREAK  A[LOOP:2: B:27:0x00d0->B:53:0x0190], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0288  */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateCarrierText(Intent intent) {
        String str;
        Integer num;
        int i;
        int i2;
        boolean z;
        SubscriptionsOrder subscriptionsOrder;
        HashMap hashMap;
        CharSequence charSequence;
        Context context;
        HashMap hashMap2;
        Context context2;
        HashMap hashMap3;
        CharSequence charSequence2;
        boolean isEmpty;
        CarrierTextManagerLogger carrierTextManagerLogger;
        int i3;
        boolean z2;
        CustomSdkMonitor customSdkMonitor;
        boolean z3;
        CharSequence charSequence3;
        String string;
        int i4;
        CharSequence charSequence4;
        GlobalSettings globalSettings;
        String str2;
        HashMap hashMap4;
        List list;
        int[] iArr;
        Boolean bool;
        Boolean bool2;
        String str3;
        CharSequence string2;
        Integer num2;
        ArrayList arrayList;
        Trace.beginSection("CarrierTextManager#updateCarrierText");
        List filteredSubscriptionInfo = this.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo();
        Context context3 = this.mContext;
        String string3 = context3.getString(android.R.string.permdesc_accessWimaxState);
        ArrayList arrayList2 = (ArrayList) filteredSubscriptionInfo;
        int size = arrayList2.size();
        int[] iArr2 = new int[size];
        int i5 = this.mSimSlotsNumber;
        int[] iArr3 = new int[i5];
        for (int i6 = 0; i6 < i5; i6++) {
            iArr3[i6] = -1;
        }
        CharSequence[] charSequenceArr = new CharSequence[size];
        CarrierTextManagerLogger carrierTextManagerLogger2 = this.mLogger;
        carrierTextManagerLogger2.logUpdate(size);
        HashMap hashMap5 = this.plmnOfBroadcast;
        Intent registerReceiver = (intent == null && TextUtils.isEmpty((CharSequence) hashMap5.get(0))) ? context3.registerReceiver(null, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED")) : intent;
        HashMap hashMap6 = this.voWifiConnected;
        if (registerReceiver != 0) {
            str = string3;
            ?? r14 = registerReceiver.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1) == -1 ? 0 : 0;
            int intExtra = registerReceiver.getIntExtra("phone", r14);
            num = 0;
            hashMap6.put(Integer.valueOf(intExtra), Boolean.valueOf(registerReceiver.getBooleanExtra("showEpdg", r14)));
            Integer valueOf = Integer.valueOf(intExtra);
            CarrierTextUtil carrierTextUtil = this.mCarrierTextUtil;
            hashMap5.put(valueOf, carrierTextUtil.updateNetworkName(registerReceiver));
            carrierTextManagerLogger2.logUpdateFromStickyBroadcast(String.valueOf(intExtra), carrierTextUtil.updateNetworkName(registerReceiver));
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
            i2 = 0;
            boolean z4 = false;
            z = true;
            while (true) {
                subscriptionsOrder = this.mSubscriptionsOrder;
                hashMap = hashMap6;
                charSequence = "";
                if (i2 < size) {
                    break;
                }
                HashMap hashMap7 = hashMap5;
                int subscriptionId = ((SubscriptionInfo) arrayList2.get(i2)).getSubscriptionId();
                Context context4 = context3;
                int simOrder = subscriptionsOrder.getSimOrder(subscriptionId, filteredSubscriptionInfo) >= size ? i2 : subscriptionsOrder.getSimOrder(subscriptionId, filteredSubscriptionInfo);
                charSequenceArr[simOrder] = "";
                iArr2[simOrder] = subscriptionId;
                iArr3[((SubscriptionInfo) arrayList2.get(i2)).getSimSlotIndex()] = i2;
                int simState = this.mKeyguardUpdateMonitor.getSimState(subscriptionId);
                CharSequence carrierName2 = ((SubscriptionInfo) arrayList2.get(i2)).getCarrierName();
                if (isRTL()) {
                    arrayList = arrayList2;
                    if ("dea!".equals(carrierName2)) {
                        carrierName2 = "dea!";
                    }
                } else {
                    arrayList = arrayList2;
                }
                CharSequence carrierTextForSimState = getCarrierTextForSimState(simState, carrierName2);
                carrierTextManagerLogger2.logUpdateLoopStart(subscriptionId, simState, String.valueOf(carrierName2));
                List list2 = filteredSubscriptionInfo;
                Log.d("CarrierTextController", "carrierTextForSimState(" + subscriptionId + ")-(order: " + simOrder + ") : " + ((Object) carrierTextForSimState));
                if (carrierTextForSimState != null) {
                    charSequenceArr[simOrder] = carrierTextForSimState;
                    z = false;
                }
                if (simState == 5) {
                    Trace.beginSection("WFC check");
                    ServiceState serviceState = (ServiceState) this.mKeyguardUpdateMonitor.mServiceStates.get(Integer.valueOf(subscriptionId));
                    if (serviceState != null && serviceState.getDataRegistrationState() == 0 && (serviceState.getRilDataRadioTechnology() != 18 || this.mWifiRepository.isWifiConnectedWithValidSsid())) {
                        carrierTextManagerLogger2.logUpdateWfcCheck();
                        z4 = true;
                    }
                    Trace.endSection();
                }
                i2++;
                hashMap6 = hashMap;
                context3 = context4;
                hashMap5 = hashMap7;
                arrayList2 = arrayList;
                filteredSubscriptionInfo = list2;
            }
            List list3 = filteredSubscriptionInfo;
            context = context3;
            hashMap2 = hashMap5;
            if (z || z4) {
                context2 = context;
                hashMap3 = hashMap2;
                charSequence2 = null;
            } else {
                if (LsRune.SECURITY_CLEAR_NO_SIM_DEFAULT_TEXT) {
                    str3 = "";
                    context2 = context;
                } else if (LsRune.SECURITY_DISAPPEAR_DEFAULT_CARRIER_TEXT) {
                    context2 = context;
                    string2 = context2.getString(R.string.kg_default_carrier_text_searching);
                    num2 = num;
                    hashMap3 = hashMap2;
                    if (!TextUtils.isEmpty((CharSequence) hashMap3.get(num2)) && !((CharSequence) hashMap3.get(num2)).equals("")) {
                        string2 = (CharSequence) hashMap3.get(num2);
                    }
                    charSequence2 = makeCarrierStringOnEmergencyCapable((this.mShowMissingSim || !this.mTelephonyCapable) ? "" : context2.getString(R.string.kg_missing_sim_message_short), string2);
                } else {
                    context2 = context;
                    str3 = str;
                }
                string2 = str3;
                num2 = num;
                hashMap3 = hashMap2;
                if (!TextUtils.isEmpty((CharSequence) hashMap3.get(num2))) {
                    string2 = (CharSequence) hashMap3.get(num2);
                }
                charSequence2 = makeCarrierStringOnEmergencyCapable((this.mShowMissingSim || !this.mTelephonyCapable) ? "" : context2.getString(R.string.kg_missing_sim_message_short), string2);
            }
            isEmpty = TextUtils.isEmpty(charSequence2);
            CharSequence charSequence5 = this.mSeparator;
            if (isEmpty) {
                carrierTextManagerLogger = carrierTextManagerLogger2;
            } else {
                Boolean valueOf2 = Boolean.valueOf(isRTL());
                Boolean bool3 = this.hasSpecialChar;
                if (size == 0) {
                    carrierTextManagerLogger = carrierTextManagerLogger2;
                    charSequence2 = "";
                } else {
                    StringBuilder sb = new StringBuilder();
                    carrierTextManagerLogger = carrierTextManagerLogger2;
                    int i7 = 0;
                    while (i7 < size) {
                        if (TextUtils.isEmpty(charSequenceArr[i7])) {
                            bool = valueOf2;
                            bool2 = bool3;
                        } else if (TextUtils.isEmpty(sb)) {
                            bool = valueOf2;
                            bool2 = bool3;
                            sb.append(charSequenceArr[i7]);
                        } else if (bool3.booleanValue() && valueOf2.booleanValue()) {
                            bool = valueOf2;
                            sb.insert(0, charSequence5);
                            bool2 = bool3;
                            sb.insert(0, charSequenceArr[i7]);
                        } else {
                            bool = valueOf2;
                            bool2 = bool3;
                            sb.append(charSequence5);
                            sb.append(charSequenceArr[i7]);
                        }
                        i7++;
                        valueOf2 = bool;
                        bool3 = bool2;
                    }
                    charSequence2 = sb.toString();
                }
            }
            CharSequence carrierTextForSimState2 = getCarrierTextForSimState(8, "");
            CharSequence charSequence6 = charSequence2;
            i3 = 0;
            while (true) {
                if (i3 >= this.mTelephonyManager.getActiveModemCount()) {
                    break;
                }
                if (!this.mSimErrorState[i3]) {
                    iArr = iArr3;
                } else {
                    if (z) {
                        charSequence6 = concatenate(carrierTextForSimState2, context2.getText(android.R.string.indeterminate_progress_08), charSequence5);
                        break;
                    }
                    int i8 = iArr3[i3];
                    iArr = iArr3;
                    if (i8 != -1) {
                        charSequenceArr[i8] = concatenate(carrierTextForSimState2, charSequenceArr[i8], charSequence5);
                    } else {
                        charSequence6 = concatenate(charSequence6, carrierTextForSimState2, charSequence5);
                    }
                }
                i3++;
                iArr3 = iArr;
            }
            if (WirelessUtils.isAirplaneModeOn(context2)) {
                z2 = false;
            } else {
                CharSequence text = context2.getText(R.string.kg_flight_mode);
                StringBuilder sb2 = new StringBuilder();
                charSequence6 = text;
                int i9 = 0;
                while (i9 < size) {
                    int slotIndex = SubscriptionManager.getSlotIndex(iArr2[i9]);
                    Boolean bool4 = Boolean.FALSE;
                    if (slotIndex != -1) {
                        try {
                            globalSettings = this.mGlobalSettings;
                            i4 = size;
                            str2 = slotIndex == 0 ? "phone1_on" : "phone2_on";
                            charSequence4 = charSequence6;
                        } catch (Settings.SettingNotFoundException unused) {
                            i4 = size;
                            charSequence4 = charSequence6;
                        }
                        try {
                            try {
                                bool4 = Boolean.valueOf(Integer.parseInt(((GlobalSettingsImpl) globalSettings).getStringForUser(globalSettings.getUserId(), str2)) == 1);
                            } catch (NumberFormatException unused2) {
                                throw new Settings.SettingNotFoundException(str2);
                            }
                        } catch (Settings.SettingNotFoundException unused3) {
                            Log.e("CarrierTextController", "Sim settings - SettingNotFound");
                            if (hashMap.isEmpty()) {
                            }
                            list = list3;
                            if (sb2.length() != 0) {
                            }
                            i9++;
                            list3 = list;
                            hashMap = hashMap4;
                            charSequence6 = charSequence4;
                            size = i4;
                        }
                    } else {
                        i4 = size;
                        charSequence4 = charSequence6;
                    }
                    if (hashMap.isEmpty()) {
                        hashMap4 = hashMap;
                        if (Boolean.TRUE.equals(hashMap4.get(Integer.valueOf(slotIndex))) && bool4.booleanValue()) {
                            Log.d("CarrierTextController", "WFC PLMN INFO");
                            if (TextUtils.isEmpty(sb2)) {
                                sb2.append((CharSequence) hashMap3.get(Integer.valueOf(slotIndex)));
                            } else {
                                list = list3;
                                if (subscriptionsOrder.getSimOrder(iArr2[i9], list) == 0) {
                                    StringBuilder sb3 = new StringBuilder((CharSequence) hashMap3.get(Integer.valueOf(slotIndex)));
                                    sb3.append(charSequence5);
                                    sb3.append((CharSequence) sb2);
                                    sb2 = sb3;
                                } else {
                                    sb2.append(charSequence5);
                                    sb2.append((CharSequence) hashMap3.get(Integer.valueOf(slotIndex)));
                                }
                                if (sb2.length() != 0) {
                                    charSequence4 = sb2.toString();
                                }
                                i9++;
                                list3 = list;
                                hashMap = hashMap4;
                                charSequence6 = charSequence4;
                                size = i4;
                            }
                        }
                    } else {
                        hashMap4 = hashMap;
                    }
                    list = list3;
                    if (sb2.length() != 0) {
                    }
                    i9++;
                    list3 = list;
                    hashMap = hashMap4;
                    charSequence6 = charSequence4;
                    size = i4;
                }
                z2 = true;
            }
            customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
            if (customSdkMonitor != null) {
                if ((customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 4) == 0) {
                    z3 = true;
                    if (z3) {
                        charSequence = charSequence6;
                    } else {
                        Log.d("CarrierTextController", "CarrierText is clear by knoxstate");
                    }
                    if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
                        WifiTextManager wifiTextManager = this.mWifiTextManager;
                        boolean z5 = wifiTextManager.connected;
                        Context context5 = wifiTextManager.context;
                        if (z5) {
                            string = wifiTextManager.ssid;
                            if (string == null) {
                                string = context5.getString(R.string.wifi_connected_notification_title);
                            } else {
                                int length = string.length();
                                if (length > 1 && string.charAt(0) == '\"') {
                                    int i10 = length - 1;
                                    if (string.charAt(i10) == '\"') {
                                        string = string.substring(1, i10);
                                    }
                                }
                            }
                        } else {
                            string = z2 ? context5.getString(R.string.kg_flight_mode) : context5.getString(R.string.data_connection_no_internet);
                        }
                        charSequence3 = string;
                    } else {
                        charSequence3 = charSequence;
                    }
                    Log.d("CarrierTextController", "setText : " + ((Object) charSequence3));
                    CarrierTextManagerLogger carrierTextManagerLogger3 = carrierTextManagerLogger;
                    CarrierTextCallbackInfo carrierTextCallbackInfo = new CarrierTextCallbackInfo(carrierTextManagerLogger3.location, charSequence3, charSequenceArr, true ^ z, iArr2, z2);
                    carrierTextManagerLogger3.logCallbackSentFromUpdate(carrierTextCallbackInfo);
                    postToCallback(carrierTextCallbackInfo);
                    Trace.endSection();
                }
            }
            z3 = false;
            if (z3) {
            }
            if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
            }
            Log.d("CarrierTextController", "setText : " + ((Object) charSequence3));
            CarrierTextManagerLogger carrierTextManagerLogger32 = carrierTextManagerLogger;
            CarrierTextCallbackInfo carrierTextCallbackInfo2 = new CarrierTextCallbackInfo(carrierTextManagerLogger32.location, charSequence3, charSequenceArr, true ^ z, iArr2, z2);
            carrierTextManagerLogger32.logCallbackSentFromUpdate(carrierTextCallbackInfo2);
            postToCallback(carrierTextCallbackInfo2);
            Trace.endSection();
        }
        str = string3;
        num = 0;
        this.hasSpecialChar = Boolean.FALSE;
        i = 0;
        while (true) {
            if (i >= size) {
            }
            i++;
        }
        i2 = 0;
        boolean z42 = false;
        z = true;
        while (true) {
            subscriptionsOrder = this.mSubscriptionsOrder;
            hashMap = hashMap6;
            charSequence = "";
            if (i2 < size) {
            }
            i2++;
            hashMap6 = hashMap;
            context3 = context4;
            hashMap5 = hashMap7;
            arrayList2 = arrayList;
            filteredSubscriptionInfo = list2;
        }
        List list32 = filteredSubscriptionInfo;
        context = context3;
        hashMap2 = hashMap5;
        if (z) {
        }
        context2 = context;
        hashMap3 = hashMap2;
        charSequence2 = null;
        isEmpty = TextUtils.isEmpty(charSequence2);
        CharSequence charSequence52 = this.mSeparator;
        if (isEmpty) {
        }
        CharSequence carrierTextForSimState22 = getCarrierTextForSimState(8, "");
        CharSequence charSequence62 = charSequence2;
        i3 = 0;
        while (true) {
            if (i3 >= this.mTelephonyManager.getActiveModemCount()) {
            }
            i3++;
            iArr3 = iArr;
        }
        if (WirelessUtils.isAirplaneModeOn(context2)) {
        }
        customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if (customSdkMonitor != null) {
        }
        z3 = false;
        if (z3) {
        }
        if (BasicRune.STATUS_NETWORK_WIFI_DISPLAY_AP_NAME) {
        }
        Log.d("CarrierTextController", "setText : " + ((Object) charSequence3));
        CarrierTextManagerLogger carrierTextManagerLogger322 = carrierTextManagerLogger;
        CarrierTextCallbackInfo carrierTextCallbackInfo22 = new CarrierTextCallbackInfo(carrierTextManagerLogger322.location, charSequence3, charSequenceArr, true ^ z, iArr2, z2);
        carrierTextManagerLogger322.logCallbackSentFromUpdate(carrierTextCallbackInfo22);
        postToCallback(carrierTextCallbackInfo22);
        Trace.endSection();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.CarrierTextManager$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.keyguard.CarrierTextManager$3] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.keyguard.CarrierTextManager$4] */
    private CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiRepository wifiRepository, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, CarrierTextUtil carrierTextUtil, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionsOrder subscriptionsOrder, CarrierTextManagerLogger carrierTextManagerLogger, GlobalSettings globalSettings, WifiTextManager wifiTextManager) {
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
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("onSimStateChanged() - slotId invalid: ", i2, " mTelephonyCapable: ");
                    m1m.append(Boolean.toString(carrierTextManager.mTelephonyCapable));
                    Log.d("CarrierTextController", m1m.toString());
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
                        } else if ((i3 != 2 && i3 != 3 && i3 != 12) || i == -1) {
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
            BuildersKt.launch$default(wifiTextManager.scope, null, null, new WifiTextManager$register$1(wifiTextManager, function2, null), 3);
            BuildersKt.launch$default(wifiTextManager.scope, null, null, new WifiTextManager$register$2(wifiTextManager, function2, null), 3);
        }
        this.mGlobalSettings = globalSettings;
        executor2.execute(new CarrierTextManager$$ExternalSyntheticLambda2(this, 0));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CarrierTextCallback {
        void updateCarrierInfo(CarrierTextCallbackInfo carrierTextCallbackInfo);

        default void finishedWakingUp() {
        }

        default void startedGoingToSleep() {
        }
    }
}
