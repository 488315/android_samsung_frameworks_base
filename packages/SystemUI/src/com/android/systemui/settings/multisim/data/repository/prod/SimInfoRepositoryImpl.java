package com.android.systemui.settings.multisim.data.repository.prod;

import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.multisim.data.repository.SimInfoRepository;
import com.android.systemui.settings.multisim.ui.viewmodel.ButtonType;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.telephonyui.multisimicons.MultiSimIcons;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SimInfoRepositoryImpl implements SimInfoRepository {
    public static final Uri INTERNAL_URI;
    public static final int OFFHOOK;
    public static final int RINGING;
    public final StateFlowImpl _defaultDataSimId;
    public final StateFlowImpl _defaultSmsSimId;
    public final StateFlowImpl _defaultVoiceSimId;
    public String _invalidSimInfo;
    public final StateFlowImpl _isCalling;
    public final StateFlowImpl _isDataEnabled;
    public final StateFlowImpl _isDataSimSwitching;
    public final StateFlowImpl _isESim1;
    public final StateFlowImpl _isESim2;
    public final StateFlowImpl _isMultiSIMReady;
    public final StateFlowImpl _isNetModeChanging;
    public final StateFlowImpl _isSRoaming;
    public final StateFlowImpl _isSlotReversed;
    public String _networkNameDefault;
    public final StateFlowImpl _sim1CarrierName;
    public final StateFlowImpl _sim1PhoneNumber;
    public final StateFlowImpl _sim2CarrierName;
    public final StateFlowImpl _sim2PhoneNumber;
    public String _unknownPhoneNumber;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final Handler bgHandler;
    public final DataUsageController dataController;
    public final ReadonlyStateFlow defaultDataSimId;
    public final ReadonlyStateFlow defaultSmsSimId;
    public final ReadonlyStateFlow defaultVoiceSimId;
    public final ReadonlyStateFlow isAirplaneMode;
    public final ReadonlyStateFlow isCalling;
    public final ReadonlyStateFlow isDataEnabled;
    public final ReadonlyStateFlow isDataSimSwitching;
    public final ReadonlyStateFlow isESim1;
    public final ReadonlyStateFlow isESim2;
    public final ReadonlyStateFlow isMultiSIMReady;
    public final ReadonlyStateFlow isNetModeChanging;
    public boolean isRegistered;
    public final ReadonlyStateFlow isRestrictionsForMmsUse;
    public final ReadonlyStateFlow isSRoaming;
    public final ReadonlyStateFlow isSatelliteMode;
    public final ReadonlyStateFlow isSlotReversed;
    public final SimInfoRepositoryImpl$mChangeNetModeObserver$1 mChangeNetModeObserver;
    public final Context mContext;
    public final List mDefaultIdUpdateList;
    public boolean mHasOpportunisticESim;
    public final SimInfoRepositoryImpl$mIntentReceiver$1 mIntentReceiver;
    public boolean mIsLoadedMultiSim;
    public final SimInfoRepositoryImpl$mMobileDataObserver$1 mMobileDataObserver;
    public boolean mNeedCheckOpportunisticESim;
    public final SimInfoRepositoryImpl$mOnSubscriptionsChangeListener$1 mOnSubscriptionsChangeListener;
    public final SimInfoRepositoryImpl$mPreferredVoiceObserver$1 mPreferredVoiceObserver;
    public final int maxSimIconNumber;
    public MultiSIMViewModelImpl$$ExternalSyntheticLambda0 needUpdatePhoneNumber;
    public final ReadonlyStateFlow sim1CarrierName;
    public final ReadonlyStateFlow sim1IconIndex;
    public final ReadonlyStateFlow sim1Name;
    public final ReadonlyStateFlow sim1PhoneNumber;
    public final ReadonlyStateFlow sim2CarrierName;
    public final ReadonlyStateFlow sim2IconIndex;
    public final ReadonlyStateFlow sim2Name;
    public final ReadonlyStateFlow sim2PhoneNumber;
    public SimInfoRepositoryImpl$registerSimCardManagerCallback$1 simCardCallback;
    public SimCardManagerServiceProvider simCardManagerService;
    public final SubscriptionManager subscriptionManager;
    public final SimInfoRepositoryImpl$updateDataHandler$1 updateDataHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KoreanSimCarrier {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ KoreanSimCarrier[] $VALUES;
        private final String numeric;

        static {
            KoreanSimCarrier[] koreanSimCarrierArr = {new KoreanSimCarrier("KT", 0, "45008"), new KoreanSimCarrier("LG_U_PLUS", 1, "45006")};
            $VALUES = koreanSimCarrierArr;
            $ENTRIES = EnumEntriesKt.enumEntries(koreanSimCarrierArr);
        }

        private KoreanSimCarrier(String str, int i, String str2) {
            this.numeric = str2;
        }

        public static KoreanSimCarrier valueOf(String str) {
            return (KoreanSimCarrier) Enum.valueOf(KoreanSimCarrier.class, str);
        }

        public static KoreanSimCarrier[] values() {
            return (KoreanSimCarrier[]) $VALUES.clone();
        }

        public final String getNumeric() {
            return this.numeric;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PhoneNumberSource {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ PhoneNumberSource[] $VALUES;
        private final int value;

        static {
            PhoneNumberSource[] phoneNumberSourceArr = {new PhoneNumberSource("CARRIER", 0, 2), new PhoneNumberSource("UICC", 1, 1), new PhoneNumberSource("IMS", 2, 3)};
            $VALUES = phoneNumberSourceArr;
            $ENTRIES = EnumEntriesKt.enumEntries(phoneNumberSourceArr);
        }

        private PhoneNumberSource(String str, int i, int i2) {
            this.value = i2;
        }

        public static PhoneNumberSource valueOf(String str) {
            return (PhoneNumberSource) Enum.valueOf(PhoneNumberSource.class, str);
        }

        public static PhoneNumberSource[] values() {
            return (PhoneNumberSource[]) $VALUES.clone();
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ButtonType.values().length];
            try {
                iArr[ButtonType.VOICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ButtonType.SMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ButtonType.DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        RINGING = 1;
        OFFHOOK = 2;
        INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v4, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mMobileDataObserver$1] */
    /* JADX WARN: Type inference failed for: r11v5, types: [com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$updateDataHandler$1] */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.content.BroadcastReceiver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mIntentReceiver$1] */
    /* JADX WARN: Type inference failed for: r5v10, types: [android.telephony.SubscriptionManager$OnSubscriptionsChangedListener, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mOnSubscriptionsChangeListener$1] */
    /* JADX WARN: Type inference failed for: r8v43, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mChangeNetModeObserver$1] */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mPreferredVoiceObserver$1] */
    public SimInfoRepositoryImpl(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, final Handler handler, SubscriptionManager subscriptionManager, BroadcastDispatcher broadcastDispatcher, NetworkController networkController) {
        boolean z;
        int i;
        this.mContext = context;
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.bgHandler = handler;
        this.subscriptionManager = subscriptionManager;
        this.dataController = ((NetworkControllerImpl) networkController).mDataUsageController;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isMultiSIMReady = MutableStateFlow;
        this.isMultiSIMReady = FlowKt.asStateFlow(MutableStateFlow);
        this.mNeedCheckOpportunisticESim = true;
        this._networkNameDefault = "";
        this._unknownPhoneNumber = context.getString(R.string.qs_multisim_unknown_number);
        this._invalidSimInfo = "";
        this.maxSimIconNumber = 14;
        final int i2 = 0;
        this.isAirplaneMode = toStateFlow(SettingsHelper.INDEX_AIRPLANE_MODE_ON, new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                r0 = 0;
                int i3 = 0;
                r0 = false;
                r0 = false;
                boolean z2 = false;
                int i4 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i2) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i5 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i6 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i6, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i6 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i7 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i7 >= 0 && i7 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i3 = i7;
                        }
                        return Integer.valueOf(i3);
                    default:
                        int i8 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i8 >= 0 && i8 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i4 = i8;
                        }
                        return Integer.valueOf(i4);
                }
            }
        });
        final int i3 = 1;
        this.isSatelliteMode = toStateFlow("satellite_mode_enabled", new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                i3 = 0;
                int i32 = 0;
                z2 = false;
                z2 = false;
                boolean z2 = false;
                int i4 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i3) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i5 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i6 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i6, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i6 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i7 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i7 >= 0 && i7 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i32 = i7;
                        }
                        return Integer.valueOf(i32);
                    default:
                        int i8 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i8 >= 0 && i8 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i4 = i8;
                        }
                        return Integer.valueOf(i4);
                }
            }
        });
        final int i4 = 2;
        this.isRestrictionsForMmsUse = toStateFlow("multi_sim_datacross_slot", new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                i32 = 0;
                int i32 = 0;
                z2 = false;
                z2 = false;
                boolean z2 = false;
                int i42 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i4) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i5 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i6 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i6, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i6 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i7 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i7 >= 0 && i7 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i32 = i7;
                        }
                        return Integer.valueOf(i32);
                    default:
                        int i8 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i8 >= 0 && i8 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i42 = i8;
                        }
                        return Integer.valueOf(i42);
                }
            }
        });
        final int i5 = 3;
        this.sim1Name = toStateFlow(SettingsHelper.INDEX_SIM_SELECT_NAME_1, new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                i32 = 0;
                int i32 = 0;
                z2 = false;
                z2 = false;
                boolean z2 = false;
                int i42 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i5) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i52 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i6 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i6, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i6 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i7 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i7 >= 0 && i7 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i32 = i7;
                        }
                        return Integer.valueOf(i32);
                    default:
                        int i8 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i8 >= 0 && i8 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i42 = i8;
                        }
                        return Integer.valueOf(i42);
                }
            }
        });
        final int i6 = 4;
        this.sim2Name = toStateFlow(SettingsHelper.INDEX_SIM_SELECT_NAME_2, new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                i32 = 0;
                int i32 = 0;
                z2 = false;
                z2 = false;
                boolean z2 = false;
                int i42 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i6) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i52 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i62 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i62, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i62 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i7 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i7 >= 0 && i7 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i32 = i7;
                        }
                        return Integer.valueOf(i32);
                    default:
                        int i8 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i8 >= 0 && i8 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i42 = i8;
                        }
                        return Integer.valueOf(i42);
                }
            }
        });
        final int i7 = 5;
        this.sim1IconIndex = toStateFlow("select_icon_1", new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                i32 = 0;
                int i32 = 0;
                z2 = false;
                z2 = false;
                boolean z2 = false;
                int i42 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i7) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i52 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i62 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i62, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i62 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i72 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i72 >= 0 && i72 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i32 = i72;
                        }
                        return Integer.valueOf(i32);
                    default:
                        int i8 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i8 >= 0 && i8 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i42 = i8;
                        }
                        return Integer.valueOf(i42);
                }
            }
        });
        final int i8 = 6;
        this.sim2IconIndex = toStateFlow("select_icon_2", new Function0(this) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ SimInfoRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                i32 = 0;
                int i32 = 0;
                z2 = false;
                z2 = false;
                boolean z2 = false;
                int i42 = 1;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.f$0;
                switch (i8) {
                    case 0:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1);
                    case 1:
                        return Boolean.valueOf(Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "satellite_mode_enabled", 0) == 1);
                    case 2:
                        int i52 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.getClass();
                        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                            int i62 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i62, "isMMSuse =", "MULTISIM-PROD-REPO");
                            if (i62 != -1) {
                                z2 = true;
                            }
                        }
                        return Boolean.valueOf(z2);
                    case 3:
                        String string = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                        return string == null ? "SIM 1" : string;
                    case 4:
                        String string2 = Settings.Global.getString(simInfoRepositoryImpl.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                        return string2 == null ? "SIM 2" : string2;
                    case 5:
                        int i72 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_1", 0);
                        if (i72 >= 0 && i72 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i32 = i72;
                        }
                        return Integer.valueOf(i32);
                    default:
                        int i82 = Settings.Global.getInt(simInfoRepositoryImpl.mContext.getContentResolver(), "select_icon_2", 1);
                        if (i82 >= 0 && i82 < simInfoRepositoryImpl.maxSimIconNumber) {
                            i42 = i82;
                        }
                        return Integer.valueOf(i42);
                }
            }
        });
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(this._networkNameDefault);
        this._sim1CarrierName = MutableStateFlow2;
        this.sim1CarrierName = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(this._networkNameDefault);
        this._sim2CarrierName = MutableStateFlow3;
        this.sim2CarrierName = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(this._unknownPhoneNumber);
        this._sim1PhoneNumber = MutableStateFlow4;
        this.sim1PhoneNumber = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(this._unknownPhoneNumber);
        this._sim2PhoneNumber = MutableStateFlow5;
        this.sim2PhoneNumber = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isESim1 = MutableStateFlow6;
        this.isESim1 = FlowKt.asStateFlow(MutableStateFlow6);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._isESim2 = MutableStateFlow7;
        this.isESim2 = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(0);
        this._defaultVoiceSimId = MutableStateFlow8;
        this.defaultVoiceSimId = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(0);
        this._defaultSmsSimId = MutableStateFlow9;
        this.defaultSmsSimId = FlowKt.asStateFlow(MutableStateFlow9);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(0);
        this._defaultDataSimId = MutableStateFlow10;
        this.defaultDataSimId = FlowKt.asStateFlow(MutableStateFlow10);
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._isSRoaming = MutableStateFlow11;
        this.isSRoaming = FlowKt.asStateFlow(MutableStateFlow11);
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(Boolean.valueOf(checkDataOn()));
        this._isDataEnabled = MutableStateFlow12;
        this.isDataEnabled = FlowKt.asStateFlow(MutableStateFlow12);
        if (DeviceState.isVoiceCapable(context)) {
            int callState = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(0));
            int callState2 = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(1));
            Log.i("MULTISIM-PROD-REPO", "Check Call SIM1 : " + callState + ", SIM2 : " + callState2);
            int i9 = RINGING;
            if (callState == i9 || callState == (i = OFFHOOK) || callState2 == i9 || callState2 == i) {
                z = true;
                StateFlowImpl MutableStateFlow13 = StateFlowKt.MutableStateFlow(Boolean.valueOf(z));
                this._isCalling = MutableStateFlow13;
                this.isCalling = FlowKt.asStateFlow(MutableStateFlow13);
                StateFlowImpl MutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
                this._isNetModeChanging = MutableStateFlow14;
                this.isNetModeChanging = FlowKt.asStateFlow(MutableStateFlow14);
                StateFlowImpl MutableStateFlow15 = StateFlowKt.MutableStateFlow(bool);
                this._isDataSimSwitching = MutableStateFlow15;
                this.isDataSimSwitching = FlowKt.asStateFlow(MutableStateFlow15);
                StateFlowImpl MutableStateFlow16 = StateFlowKt.MutableStateFlow(Boolean.valueOf(DeviceState.isSubInfoReversed(context)));
                this._isSlotReversed = MutableStateFlow16;
                this.isSlotReversed = FlowKt.asStateFlow(MutableStateFlow16);
                ?? r5 = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mOnSubscriptionsChangeListener$1
                    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                    public final void onSubscriptionsChanged() {
                        Log.d("MULTISIM-PROD-REPO", "onSubscriptionsChanged: ");
                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, VolteConstants.ErrorCode.CALL_FORBIDDEN);
                    }
                };
                this.mOnSubscriptionsChangeListener = r5;
                ?? r8 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mChangeNetModeObserver$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z2, Uri uri) {
                        onChange(z2);
                        if (uri != null && uri.equals(Settings.Global.getUriFor("set_network_mode_by_quick_panel"))) {
                            boolean z3 = Settings.Global.getInt(SimInfoRepositoryImpl.this.mContext.getContentResolver(), "set_network_mode_by_quick_panel", 0) != 0;
                            EmergencyButtonController$$ExternalSyntheticOutline0.m("ChangeNetModeObserver onChange() ", "MULTISIM-PROD-REPO", z3);
                            if (z3) {
                                SimInfoRepositoryImpl.this._isNetModeChanging.updateState(null, Boolean.TRUE);
                                SimInfoRepositoryImpl$updateDataHandler$1 simInfoRepositoryImpl$updateDataHandler$1 = SimInfoRepositoryImpl.this.updateDataHandler;
                                simInfoRepositoryImpl$updateDataHandler$1.removeMessages(1001);
                                simInfoRepositoryImpl$updateDataHandler$1.sendMessageDelayed(simInfoRepositoryImpl$updateDataHandler$1.obtainMessage(1001), 1000L);
                            }
                        }
                    }
                };
                this.mChangeNetModeObserver = r8;
                ?? r9 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mPreferredVoiceObserver$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z2) {
                        Log.d("MULTISIM-PROD-REPO", "PreferredVoiceObserver onChange()");
                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2010);
                    }
                };
                this.mPreferredVoiceObserver = r9;
                ?? r10 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mMobileDataObserver$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z2) {
                        Log.d("MULTISIM-PROD-REPO", "MobileDataObserver onChange()");
                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2006);
                    }
                };
                this.mMobileDataObserver = r10;
                ?? r12 = new BroadcastReceiver() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mIntentReceiver$1
                    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onReceive() - action = ", action, "MULTISIM-PROD-REPO");
                        if (action != null) {
                            switch (action.hashCode()) {
                                case -2125003962:
                                    if (action.equals("com.samsung.android.softsim.ServiceStatus")) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2007);
                                        return;
                                    }
                                    break;
                                case -2104353374:
                                    if (action.equals("android.intent.action.SERVICE_STATE")) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2000);
                                        return;
                                    }
                                    break;
                                case -1909638742:
                                    if (action.equals("com.samsung.settings.SIMCARD_MGT_ACTIVATED")) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2008);
                                        return;
                                    }
                                    break;
                                case -1465084191:
                                    if (action.equals("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2010);
                                        return;
                                    }
                                    break;
                                case -1326089125:
                                    if (action.equals("android.intent.action.PHONE_STATE")) {
                                        String stringExtra = intent.getStringExtra("state");
                                        if (stringExtra == null || stringExtra.length() == 0) {
                                            return;
                                        }
                                        SimInfoRepositoryImpl.this._isCalling.updateState(null, Boolean.valueOf(Intrinsics.areEqual(TelephonyManager.EXTRA_STATE_RINGING, stringExtra) || Intrinsics.areEqual(TelephonyManager.EXTRA_STATE_OFFHOOK, stringExtra)));
                                        return;
                                    }
                                    break;
                                case -874111300:
                                    if (action.equals("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED")) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2010);
                                        return;
                                    }
                                    break;
                                case -602747103:
                                    if (action.equals("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                        if (((Boolean) SimInfoRepositoryImpl.this._isDataSimSwitching.getValue()).booleanValue()) {
                                            SimInfoRepositoryImpl$updateDataHandler$1 simInfoRepositoryImpl$updateDataHandler$1 = SimInfoRepositoryImpl.this.updateDataHandler;
                                            simInfoRepositoryImpl$updateDataHandler$1.removeMessages(1000);
                                            simInfoRepositoryImpl$updateDataHandler$1.sendMessageDelayed(simInfoRepositoryImpl$updateDataHandler$1.obtainMessage(1000), 60000L);
                                            return;
                                        }
                                        return;
                                    }
                                    break;
                                case -271221703:
                                    if (action.equals("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED")) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2011);
                                        return;
                                    }
                                    break;
                                case -229777127:
                                    if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2008);
                                        String stringExtra2 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                                        if ("READY".equals(stringExtra2)) {
                                            SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
                                            return;
                                        } else {
                                            if ("LOADED".equals(stringExtra2)) {
                                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423);
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                    break;
                                case -25388475:
                                    if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2012);
                                        return;
                                    }
                                    break;
                                case -19011148:
                                    if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2009);
                                        return;
                                    }
                                    break;
                                case 551474169:
                                    if (action.equals("android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH")) {
                                        if (((Boolean) SimInfoRepositoryImpl.this._isDataSimSwitching.getValue()).booleanValue()) {
                                            SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 1000);
                                            return;
                                        }
                                        return;
                                    }
                                    break;
                            }
                        }
                        MotionLayout$$ExternalSyntheticOutline0.m("Unsupport - ", action, "MULTISIM-PROD-REPO");
                    }
                };
                this.mIntentReceiver = r12;
                this.mDefaultIdUpdateList = new ArrayList();
                final Looper looper = handler.getLooper();
                this.updateDataHandler = new Handler(looper) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$updateDataHandler$1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(message.what, "HANDLE_MSG(", ")", "MULTISIM-PROD-REPO");
                        int i10 = message.what;
                        SimInfoRepositoryImpl simInfoRepositoryImpl = SimInfoRepositoryImpl.this;
                        if (i10 == 1000) {
                            simInfoRepositoryImpl._isDataSimSwitching.updateState(null, Boolean.FALSE);
                            return;
                        }
                        if (i10 == 1001) {
                            simInfoRepositoryImpl._isNetModeChanging.updateState(null, Boolean.FALSE);
                            return;
                        }
                        switch (i10) {
                            case 2000:
                                simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                                simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(false);
                                break;
                            case VolteConstants.ErrorCode.CALL_FORBIDDEN /* 2001 */:
                                simInfoRepositoryImpl.mNeedCheckOpportunisticESim = true;
                                simInfoRepositoryImpl.updateMultiSimReadyState(false);
                                simInfoRepositoryImpl._isSlotReversed.updateState(null, Boolean.valueOf(DeviceState.isSubInfoReversed(simInfoRepositoryImpl.mContext)));
                                Log.d("MULTISIM-PROD-REPO", "sub reversed " + simInfoRepositoryImpl.isSlotReversed.$$delegate_0.getValue());
                                simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                                simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(false);
                                break;
                            case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F /* 2002 */:
                                int i11 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(false);
                                break;
                            case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403 /* 2003 */:
                                simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                                simInfoRepositoryImpl.updateSimSlotType();
                                break;
                            case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423 /* 2004 */:
                                simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                                simInfoRepositoryImpl.updateSimSlotType();
                                simInfoRepositoryImpl.updateCurrentDefaultSlot(ButtonType.VOICE);
                                simInfoRepositoryImpl.updateCurrentDefaultSlot(ButtonType.SMS);
                                simInfoRepositoryImpl.updateCurrentDefaultSlot(ButtonType.DATA);
                                break;
                            default:
                                switch (i10) {
                                    case 2006:
                                        simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                                        break;
                                    case 2007:
                                        StateFlowImpl stateFlowImpl = simInfoRepositoryImpl._isSRoaming;
                                        Context context2 = simInfoRepositoryImpl.mContext;
                                        int i12 = 9;
                                        if (context2 == null) {
                                            Log.d("MULTISIM-PROD-REPO", "context is null : com.samsung.android.globalroaming");
                                        } else {
                                            try {
                                                context2.getPackageManager().getApplicationInfo("com.samsung.android.globalroaming", 128).getClass();
                                                Log.i("MULTISIM-PROD-REPO", "has sroaming package");
                                                String mSimSystemProperty = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", 0, "default");
                                                String mSimSystemProperty2 = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", 1, "default");
                                                mSimSystemProperty.getClass();
                                                int sRoamingStatus = SimInfoRepositoryImpl.getSRoamingStatus(mSimSystemProperty);
                                                mSimSystemProperty2.getClass();
                                                int sRoamingStatus2 = SimInfoRepositoryImpl.getSRoamingStatus(mSimSystemProperty2);
                                                if (sRoamingStatus == 1 || sRoamingStatus2 == 1) {
                                                    i12 = 1;
                                                } else if (sRoamingStatus == 0 && sRoamingStatus2 == 0) {
                                                    i12 = 0;
                                                }
                                                ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i12, "sroaming status : ", "MULTISIM-PROD-REPO");
                                            } catch (PackageManager.NameNotFoundException unused) {
                                                Log.e("MULTISIM-PROD-REPO", "Package not found : com.samsung.android.globalroaming");
                                            }
                                        }
                                        stateFlowImpl.updateState(null, Boolean.valueOf(i12 == 1));
                                        break;
                                    case 2008:
                                        int i13 = SimInfoRepositoryImpl.RINGING;
                                        simInfoRepositoryImpl.updateMultiSimReadyState(true);
                                        break;
                                    case 2009:
                                        int i14 = SimInfoRepositoryImpl.RINGING;
                                        simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(true);
                                        break;
                                    case 2010:
                                        ButtonType buttonType = ButtonType.VOICE;
                                        int i15 = SimInfoRepositoryImpl.RINGING;
                                        simInfoRepositoryImpl.updateCurrentDefaultSlot(buttonType);
                                        break;
                                    case 2011:
                                        ButtonType buttonType2 = ButtonType.SMS;
                                        int i16 = SimInfoRepositoryImpl.RINGING;
                                        simInfoRepositoryImpl.updateCurrentDefaultSlot(buttonType2);
                                        break;
                                    case 2012:
                                        ButtonType buttonType3 = ButtonType.DATA;
                                        int i17 = SimInfoRepositoryImpl.RINGING;
                                        simInfoRepositoryImpl.updateCurrentDefaultSlot(buttonType3);
                                        break;
                                    default:
                                        Log.w("MULTISIM-PROD-REPO", "MSG Unknown");
                                        break;
                                }
                        }
                    }
                };
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                intentFilter.addAction("com.samsung.settings.SIMCARD_MGT_ACTIVATED");
                intentFilter.addAction("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED");
                intentFilter.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
                intentFilter.addAction("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED");
                KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED", "android.intent.action.SIM_STATE_CHANGED", "com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS", "android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH");
                KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.PHONE_STATE", "android.intent.action.SERVICE_STATE", "com.samsung.android.softsim.ServiceStatus", EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED);
                BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r12, intentFilter, null, null, 0, null, 60);
                context.getContentResolver().registerContentObserver(Settings.System.getUriFor("prefered_voice_call"), false, r9);
                context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA), false, r10);
                context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, r10);
                context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("set_network_mode_by_quick_panel"), false, r8);
                subscriptionManager.addOnSubscriptionsChangedListener(ExecutorsKt.asExecutor(coroutineDispatcher), r5);
                updateCurrentDefaultSlot(ButtonType.VOICE);
                updateCurrentDefaultSlot(ButtonType.SMS);
                updateCurrentDefaultSlot(ButtonType.DATA);
                updateMultiSimReadyState(true);
                updateSimSlotType();
                updateCarrierNameAndPhoneNumber(true);
            }
        }
        z = false;
        StateFlowImpl MutableStateFlow132 = StateFlowKt.MutableStateFlow(Boolean.valueOf(z));
        this._isCalling = MutableStateFlow132;
        this.isCalling = FlowKt.asStateFlow(MutableStateFlow132);
        StateFlowImpl MutableStateFlow142 = StateFlowKt.MutableStateFlow(bool);
        this._isNetModeChanging = MutableStateFlow142;
        this.isNetModeChanging = FlowKt.asStateFlow(MutableStateFlow142);
        StateFlowImpl MutableStateFlow152 = StateFlowKt.MutableStateFlow(bool);
        this._isDataSimSwitching = MutableStateFlow152;
        this.isDataSimSwitching = FlowKt.asStateFlow(MutableStateFlow152);
        StateFlowImpl MutableStateFlow162 = StateFlowKt.MutableStateFlow(Boolean.valueOf(DeviceState.isSubInfoReversed(context)));
        this._isSlotReversed = MutableStateFlow162;
        this.isSlotReversed = FlowKt.asStateFlow(MutableStateFlow162);
        ?? r52 = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mOnSubscriptionsChangeListener$1
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                Log.d("MULTISIM-PROD-REPO", "onSubscriptionsChanged: ");
                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, VolteConstants.ErrorCode.CALL_FORBIDDEN);
            }
        };
        this.mOnSubscriptionsChangeListener = r52;
        ?? r82 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mChangeNetModeObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2, Uri uri) {
                onChange(z2);
                if (uri != null && uri.equals(Settings.Global.getUriFor("set_network_mode_by_quick_panel"))) {
                    boolean z3 = Settings.Global.getInt(SimInfoRepositoryImpl.this.mContext.getContentResolver(), "set_network_mode_by_quick_panel", 0) != 0;
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("ChangeNetModeObserver onChange() ", "MULTISIM-PROD-REPO", z3);
                    if (z3) {
                        SimInfoRepositoryImpl.this._isNetModeChanging.updateState(null, Boolean.TRUE);
                        SimInfoRepositoryImpl$updateDataHandler$1 simInfoRepositoryImpl$updateDataHandler$1 = SimInfoRepositoryImpl.this.updateDataHandler;
                        simInfoRepositoryImpl$updateDataHandler$1.removeMessages(1001);
                        simInfoRepositoryImpl$updateDataHandler$1.sendMessageDelayed(simInfoRepositoryImpl$updateDataHandler$1.obtainMessage(1001), 1000L);
                    }
                }
            }
        };
        this.mChangeNetModeObserver = r82;
        ?? r92 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mPreferredVoiceObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                Log.d("MULTISIM-PROD-REPO", "PreferredVoiceObserver onChange()");
                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2010);
            }
        };
        this.mPreferredVoiceObserver = r92;
        ?? r102 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mMobileDataObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                Log.d("MULTISIM-PROD-REPO", "MobileDataObserver onChange()");
                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2006);
            }
        };
        this.mMobileDataObserver = r102;
        ?? r122 = new BroadcastReceiver() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mIntentReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onReceive() - action = ", action, "MULTISIM-PROD-REPO");
                if (action != null) {
                    switch (action.hashCode()) {
                        case -2125003962:
                            if (action.equals("com.samsung.android.softsim.ServiceStatus")) {
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2007);
                                return;
                            }
                            break;
                        case -2104353374:
                            if (action.equals("android.intent.action.SERVICE_STATE")) {
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2000);
                                return;
                            }
                            break;
                        case -1909638742:
                            if (action.equals("com.samsung.settings.SIMCARD_MGT_ACTIVATED")) {
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2008);
                                return;
                            }
                            break;
                        case -1465084191:
                            if (action.equals("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2010);
                                return;
                            }
                            break;
                        case -1326089125:
                            if (action.equals("android.intent.action.PHONE_STATE")) {
                                String stringExtra = intent.getStringExtra("state");
                                if (stringExtra == null || stringExtra.length() == 0) {
                                    return;
                                }
                                SimInfoRepositoryImpl.this._isCalling.updateState(null, Boolean.valueOf(Intrinsics.areEqual(TelephonyManager.EXTRA_STATE_RINGING, stringExtra) || Intrinsics.areEqual(TelephonyManager.EXTRA_STATE_OFFHOOK, stringExtra)));
                                return;
                            }
                            break;
                        case -874111300:
                            if (action.equals("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED")) {
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2010);
                                return;
                            }
                            break;
                        case -602747103:
                            if (action.equals("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                if (((Boolean) SimInfoRepositoryImpl.this._isDataSimSwitching.getValue()).booleanValue()) {
                                    SimInfoRepositoryImpl$updateDataHandler$1 simInfoRepositoryImpl$updateDataHandler$1 = SimInfoRepositoryImpl.this.updateDataHandler;
                                    simInfoRepositoryImpl$updateDataHandler$1.removeMessages(1000);
                                    simInfoRepositoryImpl$updateDataHandler$1.sendMessageDelayed(simInfoRepositoryImpl$updateDataHandler$1.obtainMessage(1000), 60000L);
                                    return;
                                }
                                return;
                            }
                            break;
                        case -271221703:
                            if (action.equals("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2011);
                                return;
                            }
                            break;
                        case -229777127:
                            if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2008);
                                String stringExtra2 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                                if ("READY".equals(stringExtra2)) {
                                    SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
                                    return;
                                } else {
                                    if ("LOADED".equals(stringExtra2)) {
                                        SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423);
                                        return;
                                    }
                                    return;
                                }
                            }
                            break;
                        case -25388475:
                            if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2012);
                                return;
                            }
                            break;
                        case -19011148:
                            if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                                SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 2009);
                                return;
                            }
                            break;
                        case 551474169:
                            if (action.equals("android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH")) {
                                if (((Boolean) SimInfoRepositoryImpl.this._isDataSimSwitching.getValue()).booleanValue()) {
                                    SimInfoRepositoryImpl.sendToUpdateDataHandler$default(SimInfoRepositoryImpl.this, 1000);
                                    return;
                                }
                                return;
                            }
                            break;
                    }
                }
                MotionLayout$$ExternalSyntheticOutline0.m("Unsupport - ", action, "MULTISIM-PROD-REPO");
            }
        };
        this.mIntentReceiver = r122;
        this.mDefaultIdUpdateList = new ArrayList();
        final Looper looper2 = handler.getLooper();
        this.updateDataHandler = new Handler(looper2) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$updateDataHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(message.what, "HANDLE_MSG(", ")", "MULTISIM-PROD-REPO");
                int i10 = message.what;
                SimInfoRepositoryImpl simInfoRepositoryImpl = SimInfoRepositoryImpl.this;
                if (i10 == 1000) {
                    simInfoRepositoryImpl._isDataSimSwitching.updateState(null, Boolean.FALSE);
                    return;
                }
                if (i10 == 1001) {
                    simInfoRepositoryImpl._isNetModeChanging.updateState(null, Boolean.FALSE);
                    return;
                }
                switch (i10) {
                    case 2000:
                        simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                        simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.CALL_FORBIDDEN /* 2001 */:
                        simInfoRepositoryImpl.mNeedCheckOpportunisticESim = true;
                        simInfoRepositoryImpl.updateMultiSimReadyState(false);
                        simInfoRepositoryImpl._isSlotReversed.updateState(null, Boolean.valueOf(DeviceState.isSubInfoReversed(simInfoRepositoryImpl.mContext)));
                        Log.d("MULTISIM-PROD-REPO", "sub reversed " + simInfoRepositoryImpl.isSlotReversed.$$delegate_0.getValue());
                        simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                        simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F /* 2002 */:
                        int i11 = SimInfoRepositoryImpl.RINGING;
                        simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403 /* 2003 */:
                        simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                        simInfoRepositoryImpl.updateSimSlotType();
                        break;
                    case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423 /* 2004 */:
                        simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                        simInfoRepositoryImpl.updateSimSlotType();
                        simInfoRepositoryImpl.updateCurrentDefaultSlot(ButtonType.VOICE);
                        simInfoRepositoryImpl.updateCurrentDefaultSlot(ButtonType.SMS);
                        simInfoRepositoryImpl.updateCurrentDefaultSlot(ButtonType.DATA);
                        break;
                    default:
                        switch (i10) {
                            case 2006:
                                simInfoRepositoryImpl._isDataEnabled.updateState(null, Boolean.valueOf(simInfoRepositoryImpl.checkDataOn()));
                                break;
                            case 2007:
                                StateFlowImpl stateFlowImpl = simInfoRepositoryImpl._isSRoaming;
                                Context context2 = simInfoRepositoryImpl.mContext;
                                int i12 = 9;
                                if (context2 == null) {
                                    Log.d("MULTISIM-PROD-REPO", "context is null : com.samsung.android.globalroaming");
                                } else {
                                    try {
                                        context2.getPackageManager().getApplicationInfo("com.samsung.android.globalroaming", 128).getClass();
                                        Log.i("MULTISIM-PROD-REPO", "has sroaming package");
                                        String mSimSystemProperty = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", 0, "default");
                                        String mSimSystemProperty2 = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", 1, "default");
                                        mSimSystemProperty.getClass();
                                        int sRoamingStatus = SimInfoRepositoryImpl.getSRoamingStatus(mSimSystemProperty);
                                        mSimSystemProperty2.getClass();
                                        int sRoamingStatus2 = SimInfoRepositoryImpl.getSRoamingStatus(mSimSystemProperty2);
                                        if (sRoamingStatus == 1 || sRoamingStatus2 == 1) {
                                            i12 = 1;
                                        } else if (sRoamingStatus == 0 && sRoamingStatus2 == 0) {
                                            i12 = 0;
                                        }
                                        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i12, "sroaming status : ", "MULTISIM-PROD-REPO");
                                    } catch (PackageManager.NameNotFoundException unused) {
                                        Log.e("MULTISIM-PROD-REPO", "Package not found : com.samsung.android.globalroaming");
                                    }
                                }
                                stateFlowImpl.updateState(null, Boolean.valueOf(i12 == 1));
                                break;
                            case 2008:
                                int i13 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.updateMultiSimReadyState(true);
                                break;
                            case 2009:
                                int i14 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.updateCarrierNameAndPhoneNumber(true);
                                break;
                            case 2010:
                                ButtonType buttonType = ButtonType.VOICE;
                                int i15 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.updateCurrentDefaultSlot(buttonType);
                                break;
                            case 2011:
                                ButtonType buttonType2 = ButtonType.SMS;
                                int i16 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.updateCurrentDefaultSlot(buttonType2);
                                break;
                            case 2012:
                                ButtonType buttonType3 = ButtonType.DATA;
                                int i17 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.updateCurrentDefaultSlot(buttonType3);
                                break;
                            default:
                                Log.w("MULTISIM-PROD-REPO", "MSG Unknown");
                                break;
                        }
                }
            }
        };
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter2.addAction("com.samsung.settings.SIMCARD_MGT_ACTIVATED");
        intentFilter2.addAction("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED");
        intentFilter2.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        intentFilter2.addAction("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter2, "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED", "android.intent.action.SIM_STATE_CHANGED", "com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS", "android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter2, "android.intent.action.PHONE_STATE", "android.intent.action.SERVICE_STATE", "com.samsung.android.softsim.ServiceStatus", EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED);
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r122, intentFilter2, null, null, 0, null, 60);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("prefered_voice_call"), false, r92);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA), false, r102);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, r102);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("set_network_mode_by_quick_panel"), false, r82);
        subscriptionManager.addOnSubscriptionsChangedListener(ExecutorsKt.asExecutor(coroutineDispatcher), r52);
        updateCurrentDefaultSlot(ButtonType.VOICE);
        updateCurrentDefaultSlot(ButtonType.SMS);
        updateCurrentDefaultSlot(ButtonType.DATA);
        updateMultiSimReadyState(true);
        updateSimSlotType();
        updateCarrierNameAndPhoneNumber(true);
    }

    public static int getSRoamingStatus(String str) {
        switch (str.hashCode()) {
            case -1770111376:
                return !str.equals("deactivated") ? 9 : 0;
            case 204392913:
                return str.equals("activated") ? 1 : 9;
            case 961126449:
                return !str.equals("deactivating") ? 9 : 0;
            case 2041217264:
                return !str.equals("activating") ? 9 : 1;
            default:
                return 9;
        }
    }

    public static int getSubId(int i) {
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId != null && subId.length > 0) {
            return subId[0];
        }
        Log.e("MULTISIM-PROD-REPO", "getSubId: no valid subs");
        return -1;
    }

    public static void sendToUpdateDataHandler$default(SimInfoRepositoryImpl simInfoRepositoryImpl, int i) {
        SimInfoRepositoryImpl$updateDataHandler$1 simInfoRepositoryImpl$updateDataHandler$1 = simInfoRepositoryImpl.updateDataHandler;
        simInfoRepositoryImpl$updateDataHandler$1.removeMessages(i);
        simInfoRepositoryImpl$updateDataHandler$1.sendMessageDelayed(simInfoRepositoryImpl$updateDataHandler$1.obtainMessage(i), 0L);
    }

    public final boolean checkDataOn() {
        DataUsageController dataUsageController = this.dataController;
        return dataUsageController != null && dataUsageController.isMobileDataSupported() && dataUsageController.isMobileDataEnabled();
    }

    public final int getCurrentVoiceSlotByMethodCall() {
        try {
            Bundle call = this.mContext.getContentResolver().call(INTERNAL_URI, "getCurrentVoiceCall", (String) null, new Bundle());
            if (call == null) {
                Log.d("MULTISIM-PROD-REPO", "bundle is null : getCurrentVoiceCall");
                return 0;
            }
            boolean z = call.getBoolean("success");
            int i = call.getInt("result");
            Log.d("MULTISIM-PROD-REPO", "getCurrentVoiceCall, " + z + ", " + i);
            return i;
        } catch (Throwable th) {
            Log.e("MULTISIM-PROD-REPO", "getCurrentVoiceCall, " + th);
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007e A[EDGE_INSN: B:14:0x007e->B:15:0x007e BREAK  A[LOOP:0: B:2:0x002c->B:55:0x002c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x002c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getPhoneNumber(int r11) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl.getPhoneNumber(int):java.lang.String");
    }

    public final int getSimIcon(int i, boolean z) {
        if (DeviceType.isSupportESim() && z) {
            if (i >= 0 && i < 14) {
                return MultiSimIcons.ESIM_ICON_16DP_RES[i][0];
            }
        } else if (i >= 0 && i < 14) {
            return MultiSimIcons.PSIM_ICON_16DP_RES[i][0];
        }
        return 0;
    }

    public final boolean isDataBlocked(int i) {
        boolean z = false;
        if (this.simCardManagerService != null && SimCardManagerServiceProvider.isServiceRunningCheck(this.mContext)) {
            try {
                this.simCardManagerService.getClass();
                return !r7.isDefaultDataSlotAllowed(i);
            } catch (Exception e) {
                Log.d("MULTISIM-PROD-REPO", "Caught exception from isDataBlocked", e);
                return false;
            }
        }
        if (this.simCardManagerService == null) {
            Log.e("MULTISIM-PROD-REPO", "isDataBlocked : mSimCardManagerService is null.");
            return false;
        }
        Log.e("MULTISIM-PROD-REPO", "isDataBlocked : isDefaultDataSlotAllowedByMethodCall");
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("selectItem", i);
            Bundle call = this.mContext.getContentResolver().call(INTERNAL_URI, "isDefaultDataSlotAllowed", (String) null, bundle);
            if (call == null) {
                Log.d("MULTISIM-PROD-REPO", "bundle is null : isDefaultDataSlotAllowed");
            } else {
                boolean z2 = call.getBoolean("success");
                boolean z3 = call.getBoolean("result");
                Log.d("MULTISIM-PROD-REPO", "isDefaultDataSlotAllowed, " + z2 + ", " + z3);
                z = z3;
            }
        } catch (Throwable th) {
            Log.e("MULTISIM-PROD-REPO", "isDefaultDataSlotAllowed, " + th);
        }
        boolean z4 = !z;
        this.simCardManagerService = SimCardManagerServiceProvider.getService(this.mContext);
        return z4;
    }

    public final ReadonlyStateFlow toStateFlow(String str, final Function0 function0) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SimInfoRepositoryImpl$toStateFlow$3(null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(new SimInfoRepositoryImpl$toStateFlow$1(this, str, null)), new SimInfoRepositoryImpl$toStateFlow$2(str, null)));
        return FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function0 $getVal$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, Function0 function0) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$getVal$inlined = function0;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1$2$1 r0 = (com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1$2$1 r0 = new com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        kotlin.jvm.functions.Function0 r5 = r4.$getVal$inlined
                        java.lang.Object r5 = r5.invoke()
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function0), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this.bgDispatcher), this.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), function0.invoke());
    }

    public final void updateCarrierNameAndPhoneNumber(boolean z) {
        CharSequence carrierName;
        CharSequence carrierName2;
        if (z) {
            this._networkNameDefault = this.mContext.getString(android.R.string.permlab_accessHiddenProfile);
            this._unknownPhoneNumber = this.mContext.getString(R.string.qs_multisim_unknown_number);
            this._invalidSimInfo = this.mContext.getString(R.string.qs_multisim_invalid_sim_info);
        }
        String str = this._networkNameDefault;
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = this.subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(0);
        if (activeSubscriptionInfoForSimSlotIndex != null && (carrierName2 = activeSubscriptionInfoForSimSlotIndex.getCarrierName()) != null && carrierName2.length() != 0) {
            str = activeSubscriptionInfoForSimSlotIndex.getCarrierName().toString();
        }
        this._sim1CarrierName.setValue(str);
        String str2 = this._networkNameDefault;
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex2 = this.subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(1);
        if (activeSubscriptionInfoForSimSlotIndex2 != null && (carrierName = activeSubscriptionInfoForSimSlotIndex2.getCarrierName()) != null && carrierName.length() != 0) {
            str2 = activeSubscriptionInfoForSimSlotIndex2.getCarrierName().toString();
        }
        this._sim2CarrierName.setValue(str2);
        updatePhoneNumberWhenNeeded();
    }

    public final void updateCurrentDefaultSlot(ButtonType buttonType) {
        int currentVoiceSlotByMethodCall;
        if (!this.isRegistered) {
            if (!((ArrayList) this.mDefaultIdUpdateList).contains(buttonType)) {
                ((ArrayList) this.mDefaultIdUpdateList).add(buttonType);
            }
            Log.d("MULTISIM-PROD-REPO", "updateCurrentDefaultSlot later type = " + buttonType);
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[buttonType.ordinal()];
        boolean z = true;
        if (i != 1) {
            if (i == 2) {
                int phoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultSmsSubscriptionId());
                Log.d("MULTISIM-PROD-REPO", "updateCurrentDefaultSlot : sms = " + phoneId);
                this._defaultSmsSimId.updateState(null, Integer.valueOf(phoneId));
                return;
            }
            if (i != 3) {
                return;
            }
            int phoneId2 = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
            Log.d("MULTISIM-PROD-REPO", "updateCurrentDefaultSlot : data = " + phoneId2);
            this._defaultDataSimId.updateState(null, Integer.valueOf(phoneId2));
            return;
        }
        try {
            if (this.simCardManagerService == null || !SimCardManagerServiceProvider.isServiceRunningCheck(this.mContext)) {
                z = false;
            }
            if (z) {
                SimCardManagerServiceProvider simCardManagerServiceProvider = this.simCardManagerService;
                simCardManagerServiceProvider.getClass();
                currentVoiceSlotByMethodCall = simCardManagerServiceProvider.GetCurrentVoiceCall();
            } else {
                currentVoiceSlotByMethodCall = getCurrentVoiceSlotByMethodCall();
            }
            Log.d("MULTISIM-PROD-REPO", "updateCurrentDefaultSlot : voice = " + currentVoiceSlotByMethodCall + " " + z);
            this._defaultVoiceSimId.updateState(null, Integer.valueOf(currentVoiceSlotByMethodCall));
            Unit unit = Unit.INSTANCE;
        } catch (Exception e) {
            Log.e("MULTISIM-PROD-REPO", "Caught exception from updateCurrentDefaultSlot", e);
        }
    }

    public final void updateMultiSimReadyState(boolean z) {
        if (z) {
            this.mIsLoadedMultiSim = DeviceState.getLoadedSimCount(this.mContext) == 2;
        }
        if (this.mIsLoadedMultiSim && this.mNeedCheckOpportunisticESim) {
            this.mHasOpportunisticESim = false;
            List<SubscriptionInfo> completeActiveSubscriptionInfoList = this.subscriptionManager.getCompleteActiveSubscriptionInfoList();
            if (completeActiveSubscriptionInfoList.size() == 2) {
                SubscriptionInfo subscriptionInfo = completeActiveSubscriptionInfoList.get(0);
                SubscriptionInfo subscriptionInfo2 = completeActiveSubscriptionInfoList.get(1);
                if (subscriptionInfo.getGroupUuid() != null && Intrinsics.areEqual(subscriptionInfo.getGroupUuid(), subscriptionInfo2.getGroupUuid()) && (subscriptionInfo.isOpportunistic() || subscriptionInfo2.isOpportunistic())) {
                    this.mHasOpportunisticESim = true;
                }
            }
            this.mNeedCheckOpportunisticESim = false;
        }
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateMultiSimReadyState: MultiSimLoaded ", " hasOpportunisticESim ", "MULTISIM-PROD-REPO", this.mIsLoadedMultiSim, this.mHasOpportunisticESim);
        this._isMultiSIMReady.updateState(null, Boolean.valueOf(this.mIsLoadedMultiSim && !this.mHasOpportunisticESim));
    }

    public final void updatePhoneNumberWhenNeeded() {
        MultiSIMViewModelImpl$$ExternalSyntheticLambda0 multiSIMViewModelImpl$$ExternalSyntheticLambda0 = this.needUpdatePhoneNumber;
        if (multiSIMViewModelImpl$$ExternalSyntheticLambda0 == null || !((Boolean) multiSIMViewModelImpl$$ExternalSyntheticLambda0.invoke()).booleanValue()) {
            return;
        }
        this._sim1PhoneNumber.setValue(getPhoneNumber(0));
        this._sim2PhoneNumber.setValue(getPhoneNumber(1));
    }

    public final void updateSimSlotType() {
        boolean isESIM = DeviceState.isESIM(this.mContext, 0);
        boolean isESIM2 = DeviceState.isESIM(this.mContext, 1);
        this._isESim1.updateState(null, Boolean.valueOf(isESIM));
        this._isESim2.updateState(null, Boolean.valueOf(isESIM2));
        Log.d("MULTISIM-PROD-REPO", "updateSimSlotType() - " + isESIM + " " + isESIM2);
    }
}
