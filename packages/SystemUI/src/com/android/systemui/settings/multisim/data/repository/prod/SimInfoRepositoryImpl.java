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
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Operator;
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
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.telephonyui.multisimicons.MultiSimIcons;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.AbstractCollection;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
    public final SimInfoRepositoryImpl$simCardCallback$1 simCardCallback;
    public volatile SimCardManagerServiceProvider simCardManagerService;
    public final SubscriptionManager subscriptionManager;
    public final SimInfoRepositoryImpl$updateDataHandler$1 updateDataHandler;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    /* renamed from: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $this_toStateFlow;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, Continuation continuation) {
            super(2, continuation);
            this.$this_toStateFlow = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SimInfoRepositoryImpl.this.new AnonymousClass1(this.$this_toStateFlow, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v0, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$1$observer$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Handler handler = SimInfoRepositoryImpl.this.bgHandler;
                final ?? r3 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$1$observer$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Unit.INSTANCE);
                    }
                };
                SimInfoRepositoryImpl.this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(this.$this_toStateFlow), false, r3);
                final SimInfoRepositoryImpl simInfoRepositoryImpl = SimInfoRepositoryImpl.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        simInfoRepositoryImpl.mContext.getContentResolver().unregisterContentObserver(r3);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $this_toStateFlow;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, Continuation continuation) {
            super(2, continuation);
            this.$this_toStateFlow = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$this_toStateFlow, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d("MULTISIM-PROD-REPO", this.$this_toStateFlow + " changed");
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Unit unit = Unit.INSTANCE;
                this.label = 1;
                if (flowCollector.emit(unit, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
        RINGING = 1;
        OFFHOOK = 2;
        INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0153  */
    /* JADX WARN: Type inference failed for: r10v4, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mMobileDataObserver$1] */
    /* JADX WARN: Type inference failed for: r11v6, types: [com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$updateDataHandler$1] */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.content.BroadcastReceiver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mIntentReceiver$1] */
    /* JADX WARN: Type inference failed for: r5v10, types: [android.telephony.SubscriptionManager$OnSubscriptionsChangedListener, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mOnSubscriptionsChangeListener$1] */
    /* JADX WARN: Type inference failed for: r8v43, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mChangeNetModeObserver$1] */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mPreferredVoiceObserver$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isMultiSIMReady = stateFlowImplMutableStateFlow;
        this.isMultiSIMReady = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
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
                i = 0;
                int i3 = 0;
                z = false;
                z = false;
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
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(this._networkNameDefault);
        this._sim1CarrierName = stateFlowImplMutableStateFlow2;
        this.sim1CarrierName = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(this._networkNameDefault);
        this._sim2CarrierName = stateFlowImplMutableStateFlow3;
        this.sim2CarrierName = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(this._unknownPhoneNumber);
        this._sim1PhoneNumber = stateFlowImplMutableStateFlow4;
        this.sim1PhoneNumber = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(this._unknownPhoneNumber);
        this._sim2PhoneNumber = stateFlowImplMutableStateFlow5;
        this.sim2PhoneNumber = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isESim1 = stateFlowImplMutableStateFlow6;
        this.isESim1 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._isESim2 = stateFlowImplMutableStateFlow7;
        this.isESim2 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(0);
        this._defaultVoiceSimId = stateFlowImplMutableStateFlow8;
        this.defaultVoiceSimId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(0);
        this._defaultSmsSimId = stateFlowImplMutableStateFlow9;
        this.defaultSmsSimId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(0);
        this._defaultDataSimId = stateFlowImplMutableStateFlow10;
        this.defaultDataSimId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
        StateFlowImpl stateFlowImplMutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._isSRoaming = stateFlowImplMutableStateFlow11;
        this.isSRoaming = FlowKt.asStateFlow(stateFlowImplMutableStateFlow11);
        StateFlowImpl stateFlowImplMutableStateFlow12 = StateFlowKt.MutableStateFlow(Boolean.valueOf(checkDataOn()));
        this._isDataEnabled = stateFlowImplMutableStateFlow12;
        this.isDataEnabled = FlowKt.asStateFlow(stateFlowImplMutableStateFlow12);
        if (DeviceState.isVoiceCapable(context)) {
            int callState = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(0));
            int callState2 = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(1));
            Log.i("MULTISIM-PROD-REPO", "Check Call SIM1 : " + callState + ", SIM2 : " + callState2);
            int i9 = RINGING;
            if (callState == i9 || callState == (i = OFFHOOK) || callState2 == i9 || callState2 == i) {
                z = true;
            }
        } else {
            z = false;
        }
        StateFlowImpl stateFlowImplMutableStateFlow13 = StateFlowKt.MutableStateFlow(Boolean.valueOf(z));
        this._isCalling = stateFlowImplMutableStateFlow13;
        this.isCalling = FlowKt.asStateFlow(stateFlowImplMutableStateFlow13);
        StateFlowImpl stateFlowImplMutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this._isNetModeChanging = stateFlowImplMutableStateFlow14;
        this.isNetModeChanging = FlowKt.asStateFlow(stateFlowImplMutableStateFlow14);
        StateFlowImpl stateFlowImplMutableStateFlow15 = StateFlowKt.MutableStateFlow(bool);
        this._isDataSimSwitching = stateFlowImplMutableStateFlow15;
        this.isDataSimSwitching = FlowKt.asStateFlow(stateFlowImplMutableStateFlow15);
        StateFlowImpl stateFlowImplMutableStateFlow16 = StateFlowKt.MutableStateFlow(Boolean.valueOf(DeviceState.isSubInfoReversed(context)));
        this._isSlotReversed = stateFlowImplMutableStateFlow16;
        this.isSlotReversed = FlowKt.asStateFlow(stateFlowImplMutableStateFlow16);
        ?? r5 = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mOnSubscriptionsChangeListener$1
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                Log.d("MULTISIM-PROD-REPO", "onSubscriptionsChanged: ");
                this.this$0.sendToUpdateDataHandler(VolteConstants.ErrorCode.CALL_FORBIDDEN, 0L);
            }
        };
        this.mOnSubscriptionsChangeListener = r5;
        ?? r8 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mChangeNetModeObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2, Uri uri) {
                onChange(z2);
                if (uri != null && uri.equals(Settings.Global.getUriFor("set_network_mode_by_quick_panel"))) {
                    boolean z3 = Settings.Global.getInt(this.this$0.mContext.getContentResolver(), "set_network_mode_by_quick_panel", 0) != 0;
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("ChangeNetModeObserver onChange() ", "MULTISIM-PROD-REPO", z3);
                    if (z3) {
                        this.this$0._isNetModeChanging.updateState(null, Boolean.TRUE);
                        this.this$0.sendToUpdateDataHandler(1001, 1000L);
                    }
                }
            }
        };
        this.mChangeNetModeObserver = r8;
        ?? r9 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mPreferredVoiceObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                Log.d("MULTISIM-PROD-REPO", "PreferredVoiceObserver onChange()");
                this.this$0.sendToUpdateDataHandler(2010, 0L);
            }
        };
        this.mPreferredVoiceObserver = r9;
        ?? r10 = new ContentObserver(handler) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$mMobileDataObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                Log.d("MULTISIM-PROD-REPO", "MobileDataObserver onChange()");
                this.this$0.sendToUpdateDataHandler(2006, 0L);
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
                                this.this$0.sendToUpdateDataHandler(2007, 0L);
                                return;
                            }
                            break;
                        case -2104353374:
                            if (action.equals("android.intent.action.SERVICE_STATE")) {
                                this.this$0.sendToUpdateDataHandler(2000, 0L);
                                return;
                            }
                            break;
                        case -1909638742:
                            if (action.equals("com.samsung.settings.SIMCARD_MGT_ACTIVATED")) {
                                this.this$0.sendToUpdateDataHandler(2008, 0L);
                                return;
                            }
                            break;
                        case -1465084191:
                            if (action.equals("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                this.this$0.sendToUpdateDataHandler(2010, 0L);
                                return;
                            }
                            break;
                        case -1326089125:
                            if (action.equals("android.intent.action.PHONE_STATE")) {
                                String stringExtra = intent.getStringExtra("state");
                                if (stringExtra == null || stringExtra.length() == 0) {
                                    return;
                                }
                                this.this$0._isCalling.updateState(null, Boolean.valueOf(Intrinsics.areEqual(TelephonyManager.EXTRA_STATE_RINGING, stringExtra) || Intrinsics.areEqual(TelephonyManager.EXTRA_STATE_OFFHOOK, stringExtra)));
                                return;
                            }
                            break;
                        case -874111300:
                            if (action.equals("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED")) {
                                this.this$0.sendToUpdateDataHandler(2010, 0L);
                                return;
                            }
                            break;
                        case -602747103:
                            if (action.equals("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                if (((Boolean) this.this$0._isDataSimSwitching.getValue()).booleanValue()) {
                                    this.this$0.sendToUpdateDataHandler(1000, 60000L);
                                    return;
                                }
                                return;
                            }
                            break;
                        case -271221703:
                            if (action.equals("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                this.this$0.sendToUpdateDataHandler(2011, 0L);
                                return;
                            }
                            break;
                        case -229777127:
                            if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                                this.this$0.sendToUpdateDataHandler(2008, 0L);
                                String stringExtra2 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                                if ("READY".equals(stringExtra2)) {
                                    this.this$0.sendToUpdateDataHandler(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403, 0L);
                                    return;
                                } else {
                                    if ("LOADED".equals(stringExtra2)) {
                                        this.this$0.sendToUpdateDataHandler(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423, 0L);
                                        return;
                                    }
                                    return;
                                }
                            }
                            break;
                        case -25388475:
                            if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MULTISIM-PROD-REPO");
                                this.this$0.sendToUpdateDataHandler(2012, 0L);
                                return;
                            }
                            break;
                        case -19011148:
                            if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                                SimInfoRepositoryImpl simInfoRepositoryImpl = this.this$0;
                                int i10 = SimInfoRepositoryImpl.RINGING;
                                simInfoRepositoryImpl.sendToUpdateDataHandler(2009, 500L);
                                return;
                            }
                            break;
                        case 551474169:
                            if (action.equals("android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH")) {
                                if (((Boolean) this.this$0._isDataSimSwitching.getValue()).booleanValue()) {
                                    this.this$0.sendToUpdateDataHandler(1000, 0L);
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
        this.simCardCallback = new SimInfoRepositoryImpl$simCardCallback$1(this);
        final Looper looper = handler.getLooper();
        this.updateDataHandler = new Handler(looper) { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$updateDataHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(message.what, "HANDLE_MSG(", ")", "MULTISIM-PROD-REPO");
                int i10 = message.what;
                SimInfoRepositoryImpl simInfoRepositoryImpl = this.this$0;
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

    public final boolean checkDataOn() {
        DataUsageController dataUsageController = this.dataController;
        return dataUsageController != null && dataUsageController.isMobileDataSupported() && dataUsageController.isMobileDataEnabled();
    }

    public final int getCurrentVoiceSlotByMethodCall() {
        try {
            Bundle bundleCall = this.mContext.getContentResolver().call(INTERNAL_URI, "getCurrentVoiceCall", (String) null, new Bundle());
            if (bundleCall == null) {
                Log.d("MULTISIM-PROD-REPO", "bundle is null : getCurrentVoiceCall");
                return 0;
            }
            boolean z = bundleCall.getBoolean("success");
            int i = bundleCall.getInt("result");
            Log.d("MULTISIM-PROD-REPO", "getCurrentVoiceCall, " + z + ", " + i);
            return i;
        } catch (Throwable th) {
            Log.e("MULTISIM-PROD-REPO", "getCurrentVoiceCall, " + th);
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x007e A[EDGE_INSN: B:59:0x007e->B:22:0x007e BREAK  A[LOOP:0: B:3:0x002c->B:60:0x002c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x002c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getPhoneNumber(int i) {
        String simOperatorNumericForPhone = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getSimOperatorNumericForPhone(i);
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        String string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_DisablePhoneNumberSource", "", false);
        AbstractList abstractList = (AbstractList) PhoneNumberSource.$ENTRIES;
        abstractList.getClass();
        AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
        String phoneNumber = "";
        while (iteratorImpl.hasNext()) {
            PhoneNumberSource phoneNumberSource = (PhoneNumberSource) iteratorImpl.next();
            string.getClass();
            if (!StringsKt__StringsKt.contains(string, phoneNumberSource.name(), false)) {
                int subId = getSubId(i);
                int value = phoneNumberSource.getValue();
                SubscriptionManager subscriptionManager = this.subscriptionManager;
                if (subscriptionManager != null) {
                    try {
                        phoneNumber = subscriptionManager.getPhoneNumber(subId, value);
                    } catch (IllegalArgumentException e) {
                        Log.e("MULTISIM-PROD-REPO", "failed to get SubscriptionManager.getPhoneNumber: " + e.getMessage());
                    } catch (IllegalStateException e2) {
                        Log.e("MULTISIM-PROD-REPO", "failed to get SubscriptionManager.getPhoneNumber: " + e2.getMessage());
                    } catch (SecurityException e3) {
                        Log.e("MULTISIM-PROD-REPO", "failed to get SubscriptionManager.getPhoneNumber: " + e3.getMessage());
                    }
                    if (phoneNumber == null) {
                        phoneNumber = "";
                    }
                    if (TextUtils.isEmpty(phoneNumber)) {
                        break;
                    }
                }
                phoneNumber = null;
                if (phoneNumber == null) {
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                }
            }
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            if ("AIS".equals(SystemProperties.get("ro.csc.sales_code", "unknown"))) {
                String mSimSystemProperty = DeviceState.getMSimSystemProperty("gsm.sim.state", 0, "NOT_READY");
                String mSimSystemProperty2 = DeviceState.getMSimSystemProperty("gsm.sim.state", 0, "NOT_READY");
                if (i != 0) {
                    if (i == 1 && Intrinsics.areEqual(mSimSystemProperty2, "NETWORK_LOCKED")) {
                        Log.d("MULTISIM-PROD-REPO", "sim2 Network Lock!!");
                        phoneNumber = this._invalidSimInfo;
                    }
                    phoneNumber = this._unknownPhoneNumber;
                } else {
                    if (Intrinsics.areEqual(mSimSystemProperty, "NETWORK_LOCKED")) {
                        Log.d("MULTISIM-PROD-REPO", "sim1 Network Lock!!");
                        phoneNumber = this._invalidSimInfo;
                    }
                    phoneNumber = this._unknownPhoneNumber;
                }
            } else {
                phoneNumber = this._unknownPhoneNumber;
            }
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            return phoneNumber;
        }
        Collection collection = KoreanSimCarrier.$ENTRIES;
        if (collection != null && ((AbstractCollection) collection).isEmpty()) {
            return phoneNumber;
        }
        Iterator it = ((AbstractList) collection).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((KoreanSimCarrier) it.next()).getNumeric(), simOperatorNumericForPhone)) {
                return phoneNumber.startsWith("+82") ? AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("0", phoneNumber.substring(3)) : phoneNumber;
            }
        }
        return phoneNumber;
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
            Bundle bundleCall = this.mContext.getContentResolver().call(INTERNAL_URI, "isDefaultDataSlotAllowed", (String) null, bundle);
            if (bundleCall == null) {
                Log.d("MULTISIM-PROD-REPO", "bundle is null : isDefaultDataSlotAllowed");
            } else {
                boolean z2 = bundleCall.getBoolean("success");
                boolean z3 = bundleCall.getBoolean("result");
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

    public final void sendToUpdateDataHandler(int i, long j) {
        SimInfoRepositoryImpl$updateDataHandler$1 simInfoRepositoryImpl$updateDataHandler$1 = this.updateDataHandler;
        simInfoRepositoryImpl$updateDataHandler$1.removeMessages(i);
        simInfoRepositoryImpl$updateDataHandler$1.sendMessageDelayed(simInfoRepositoryImpl$updateDataHandler$1.obtainMessage(i), j);
    }

    public final ReadonlyStateFlow toStateFlow(String str, final Function0 function0) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass3(null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(str, null)), new AnonymousClass2(str, null)));
        return FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$toStateFlow$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Object objInvoke = this.$getVal$inlined.invoke();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objInvoke, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, function0), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, this.bgDispatcher), this.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), function0.invoke());
    }

    public final void updateCarrierNameAndPhoneNumber(boolean z) {
        CharSequence carrierName;
        CharSequence carrierName2;
        if (z) {
            this._networkNameDefault = this.mContext.getString(android.R.string.permlab_accessLastKnownCellId);
            this._unknownPhoneNumber = this.mContext.getString(R.string.qs_multisim_unknown_number);
            this._invalidSimInfo = this.mContext.getString(R.string.qs_multisim_invalid_sim_info);
        }
        String string = this._networkNameDefault;
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = this.subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(0);
        if (activeSubscriptionInfoForSimSlotIndex != null && (carrierName2 = activeSubscriptionInfoForSimSlotIndex.getCarrierName()) != null && carrierName2.length() != 0) {
            string = activeSubscriptionInfoForSimSlotIndex.getCarrierName().toString();
        }
        this._sim1CarrierName.setValue(string);
        String string2 = this._networkNameDefault;
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex2 = this.subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(1);
        if (activeSubscriptionInfoForSimSlotIndex2 != null && (carrierName = activeSubscriptionInfoForSimSlotIndex2.getCarrierName()) != null && carrierName.length() != 0) {
            string2 = activeSubscriptionInfoForSimSlotIndex2.getCarrierName().toString();
        }
        this._sim2CarrierName.setValue(string2);
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
                ListPopupWindow$$ExternalSyntheticOutline0.m(phoneId, "updateCurrentDefaultSlot : sms = ", "MULTISIM-PROD-REPO");
                this._defaultSmsSimId.updateState(null, Integer.valueOf(phoneId));
                return;
            } else {
                if (i != 3) {
                    return;
                }
                int phoneId2 = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
                ListPopupWindow$$ExternalSyntheticOutline0.m(phoneId2, "updateCurrentDefaultSlot : data = ", "MULTISIM-PROD-REPO");
                this._defaultDataSimId.updateState(null, Integer.valueOf(phoneId2));
                return;
            }
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
        boolean zIsESIM = DeviceState.isESIM(this.mContext, 0);
        boolean zIsESIM2 = DeviceState.isESIM(this.mContext, 1);
        this._isESim1.updateState(null, Boolean.valueOf(zIsESIM));
        this._isESim2.updateState(null, Boolean.valueOf(zIsESIM2));
        Log.d("MULTISIM-PROD-REPO", "updateSimSlotType() - " + zIsESIM + " " + zIsESIM2);
    }
}
