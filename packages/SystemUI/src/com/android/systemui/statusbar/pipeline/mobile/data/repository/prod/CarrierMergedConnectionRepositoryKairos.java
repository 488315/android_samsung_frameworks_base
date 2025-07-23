package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CarrierMergedConnectionRepositoryKairos implements MobileConnectionRepositoryKairos, KairosBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit allowNetworkSliceIndicator;
    public final StateInit carrierId;
    public final StateInit carrierNetworkChangeActive;
    public final StateInit cdmaLevel;
    public final StateInit cdmaRoaming;
    public final State dataActivityDirection;
    public final StateInit dataConnectionState;
    public final StateInit hasPrioritizedNetworkCapabilities;
    public final StateInit inflateSignalStrength;
    public final StateInit isAllowedDuringAirplaneMode;
    public final StateInit isEmergencyOnly;
    public final StateInit isGsm;
    public final State isInEcmMode;
    public final StateInit isInService;
    public final StateInit isNonTerrestrial;
    public final StateInit isRoaming;
    public final State isWifiEnabled;
    public final StateInit network;
    public final StateInit networkName;
    public final StateInit numberOfLevels;
    public final StateInit operatorAlphaShort;
    public final StateInit primaryLevel;
    public final StateInit resolvedNetworkType;
    public final StateInit satelliteLevel;
    public final int subId;
    public final TableLogBuffer tableLogBuffer;
    public final TelephonyManager telephonyManager;
    public final WifiRepository wifiRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public final TelephonyManager telephonyManager;
        public final WifiRepository wifiRepository;

        public Factory(TelephonyManager telephonyManager, WifiRepository wifiRepository) {
            this.telephonyManager = telephonyManager;
            this.wifiRepository = wifiRepository;
        }
    }

    static {
        new Companion(null);
    }

    public CarrierMergedConnectionRepositoryKairos(int i, TableLogBuffer tableLogBuffer, TelephonyManager telephonyManager, WifiRepository wifiRepository, State state) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        this.telephonyManager = telephonyManager;
        this.wifiRepository = wifiRepository;
        this.isInEcmMode = state;
        if (telephonyManager.getSubscriptionId() != i) {
            throw new IllegalStateException(StringsKt__IndentKt.trimMargin$default("CarrierMergedRepo: TelephonyManager should be created with subId(" + i + ").\n                    | Found " + telephonyManager.getSubscriptionId() + " instead.").toString());
        }
        final int i2 = 0;
        State buildState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ CarrierMergedConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        StateFlow isWifiEnabled = this.f$0.wifiRepository.isWifiEnabled();
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, isWifiEnabled);
                    case 1:
                        StateFlow isWifiDefault = this.f$0.wifiRepository.isWifiDefault();
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl2, isWifiDefault);
                    case 2:
                        StateFlow wifiNetwork = this.f$0.wifiRepository.getWifiNetwork();
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, wifiNetwork);
                    default:
                        StateFlow wifiActivity = this.f$0.wifiRepository.getWifiActivity();
                        BuildScopeImpl buildScopeImpl4 = (BuildScopeImpl) buildScope;
                        buildScopeImpl4.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl4, wifiActivity);
                }
            }
        });
        this.isWifiEnabled = buildState;
        final int i3 = 1;
        State buildState2 = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ CarrierMergedConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        StateFlow isWifiEnabled = this.f$0.wifiRepository.isWifiEnabled();
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, isWifiEnabled);
                    case 1:
                        StateFlow isWifiDefault = this.f$0.wifiRepository.isWifiDefault();
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl2, isWifiDefault);
                    case 2:
                        StateFlow wifiNetwork = this.f$0.wifiRepository.getWifiNetwork();
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, wifiNetwork);
                    default:
                        StateFlow wifiActivity = this.f$0.wifiRepository.getWifiActivity();
                        BuildScopeImpl buildScopeImpl4 = (BuildScopeImpl) buildScope;
                        buildScopeImpl4.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl4, wifiActivity);
                }
            }
        });
        final int i4 = 2;
        State buildState3 = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ CarrierMergedConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i4) {
                    case 0:
                        StateFlow isWifiEnabled = this.f$0.wifiRepository.isWifiEnabled();
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, isWifiEnabled);
                    case 1:
                        StateFlow isWifiDefault = this.f$0.wifiRepository.isWifiDefault();
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl2, isWifiDefault);
                    case 2:
                        StateFlow wifiNetwork = this.f$0.wifiRepository.getWifiNetwork();
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, wifiNetwork);
                    default:
                        StateFlow wifiActivity = this.f$0.wifiRepository.getWifiActivity();
                        BuildScopeImpl buildScopeImpl4 = (BuildScopeImpl) buildScope;
                        buildScopeImpl4.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl4, wifiActivity);
                }
            }
        });
        StateInit combine = CombineKt.combine(buildState, buildState2, buildState3, new Function4() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                boolean booleanValue2 = ((Boolean) obj3).booleanValue();
                WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj4;
                int i5 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                if (!booleanValue || !booleanValue2 || !(wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged)) {
                    return null;
                }
                WifiNetworkModel.CarrierMerged carrierMerged = (WifiNetworkModel.CarrierMerged) wifiNetworkModel;
                int i6 = carrierMerged.subscriptionId;
                int i7 = CarrierMergedConnectionRepositoryKairos.this.subId;
                if (i6 == i7) {
                    return carrierMerged;
                }
                Log.w("CarrierMergedConnectionRepository", StringsKt__IndentKt.trimMargin$default("Connection repo subId=" + i7 + " does not equal wifi repo\n                            | subId=" + i6 + "; not showing carrier merged"));
                return null;
            }
        });
        this.network = combine;
        Boolean bool = Boolean.FALSE;
        this.cdmaRoaming = StateKt.stateOf(bool);
        this.networkName = StateKt.map(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new NetworkNameModel.SimDerived(CarrierMergedConnectionRepositoryKairos.this.telephonyManager.getSimOperatorName());
            }
        });
        final int i5 = 2;
        this.numberOfLevels = StateKt.map(buildState3, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int i6;
                switch (i5) {
                    case 0:
                        WifiNetworkModel.CarrierMerged carrierMerged = (WifiNetworkModel.CarrierMerged) obj2;
                        int i7 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged != null ? DataConnectionState.Connected : DataConnectionState.Disconnected;
                    case 1:
                        WifiNetworkModel.CarrierMerged carrierMerged2 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i8 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged2 != null ? ResolvedNetworkType.CarrierMergedNetworkType.INSTANCE : ResolvedNetworkType.UnknownNetworkType.INSTANCE;
                    case 2:
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj2;
                        int i9 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        if (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) {
                            i6 = ((WifiNetworkModel.CarrierMerged) wifiNetworkModel).numberOfLevels;
                        } else {
                            MobileConnectionRepositoryKairos.Companion.getClass();
                            i6 = MobileConnectionRepositoryKairos.Companion.DEFAULT_NUM_LEVELS;
                        }
                        return Integer.valueOf(i6);
                    case 3:
                        WifiNetworkModel.CarrierMerged carrierMerged3 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i10 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged3 != null ? carrierMerged3.level : 0);
                    default:
                        WifiNetworkModel.CarrierMerged carrierMerged4 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i11 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged4 != null ? carrierMerged4.level : 0);
                }
            }
        });
        final int i6 = 3;
        this.primaryLevel = StateKt.map(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int i62;
                switch (i6) {
                    case 0:
                        WifiNetworkModel.CarrierMerged carrierMerged = (WifiNetworkModel.CarrierMerged) obj2;
                        int i7 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged != null ? DataConnectionState.Connected : DataConnectionState.Disconnected;
                    case 1:
                        WifiNetworkModel.CarrierMerged carrierMerged2 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i8 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged2 != null ? ResolvedNetworkType.CarrierMergedNetworkType.INSTANCE : ResolvedNetworkType.UnknownNetworkType.INSTANCE;
                    case 2:
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj2;
                        int i9 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        if (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) {
                            i62 = ((WifiNetworkModel.CarrierMerged) wifiNetworkModel).numberOfLevels;
                        } else {
                            MobileConnectionRepositoryKairos.Companion.getClass();
                            i62 = MobileConnectionRepositoryKairos.Companion.DEFAULT_NUM_LEVELS;
                        }
                        return Integer.valueOf(i62);
                    case 3:
                        WifiNetworkModel.CarrierMerged carrierMerged3 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i10 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged3 != null ? carrierMerged3.level : 0);
                    default:
                        WifiNetworkModel.CarrierMerged carrierMerged4 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i11 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged4 != null ? carrierMerged4.level : 0);
                }
            }
        });
        final int i7 = 4;
        this.cdmaLevel = StateKt.map(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int i62;
                switch (i7) {
                    case 0:
                        WifiNetworkModel.CarrierMerged carrierMerged = (WifiNetworkModel.CarrierMerged) obj2;
                        int i72 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged != null ? DataConnectionState.Connected : DataConnectionState.Disconnected;
                    case 1:
                        WifiNetworkModel.CarrierMerged carrierMerged2 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i8 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged2 != null ? ResolvedNetworkType.CarrierMergedNetworkType.INSTANCE : ResolvedNetworkType.UnknownNetworkType.INSTANCE;
                    case 2:
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj2;
                        int i9 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        if (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) {
                            i62 = ((WifiNetworkModel.CarrierMerged) wifiNetworkModel).numberOfLevels;
                        } else {
                            MobileConnectionRepositoryKairos.Companion.getClass();
                            i62 = MobileConnectionRepositoryKairos.Companion.DEFAULT_NUM_LEVELS;
                        }
                        return Integer.valueOf(i62);
                    case 3:
                        WifiNetworkModel.CarrierMerged carrierMerged3 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i10 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged3 != null ? carrierMerged3.level : 0);
                    default:
                        WifiNetworkModel.CarrierMerged carrierMerged4 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i11 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged4 != null ? carrierMerged4.level : 0);
                }
            }
        });
        final int i8 = 3;
        this.dataActivityDirection = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ CarrierMergedConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i8) {
                    case 0:
                        StateFlow isWifiEnabled = this.f$0.wifiRepository.isWifiEnabled();
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, isWifiEnabled);
                    case 1:
                        StateFlow isWifiDefault = this.f$0.wifiRepository.isWifiDefault();
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl2, isWifiDefault);
                    case 2:
                        StateFlow wifiNetwork = this.f$0.wifiRepository.getWifiNetwork();
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, wifiNetwork);
                    default:
                        StateFlow wifiActivity = this.f$0.wifiRepository.getWifiActivity();
                        BuildScopeImpl buildScopeImpl4 = (BuildScopeImpl) buildScope;
                        buildScopeImpl4.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl4, wifiActivity);
                }
            }
        });
        final int i9 = 1;
        this.resolvedNetworkType = StateKt.map(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int i62;
                switch (i9) {
                    case 0:
                        WifiNetworkModel.CarrierMerged carrierMerged = (WifiNetworkModel.CarrierMerged) obj2;
                        int i72 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged != null ? DataConnectionState.Connected : DataConnectionState.Disconnected;
                    case 1:
                        WifiNetworkModel.CarrierMerged carrierMerged2 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i82 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged2 != null ? ResolvedNetworkType.CarrierMergedNetworkType.INSTANCE : ResolvedNetworkType.UnknownNetworkType.INSTANCE;
                    case 2:
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj2;
                        int i92 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        if (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) {
                            i62 = ((WifiNetworkModel.CarrierMerged) wifiNetworkModel).numberOfLevels;
                        } else {
                            MobileConnectionRepositoryKairos.Companion.getClass();
                            i62 = MobileConnectionRepositoryKairos.Companion.DEFAULT_NUM_LEVELS;
                        }
                        return Integer.valueOf(i62);
                    case 3:
                        WifiNetworkModel.CarrierMerged carrierMerged3 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i10 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged3 != null ? carrierMerged3.level : 0);
                    default:
                        WifiNetworkModel.CarrierMerged carrierMerged4 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i11 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged4 != null ? carrierMerged4.level : 0);
                }
            }
        });
        final int i10 = 0;
        this.dataConnectionState = StateKt.map(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int i62;
                switch (i10) {
                    case 0:
                        WifiNetworkModel.CarrierMerged carrierMerged = (WifiNetworkModel.CarrierMerged) obj2;
                        int i72 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged != null ? DataConnectionState.Connected : DataConnectionState.Disconnected;
                    case 1:
                        WifiNetworkModel.CarrierMerged carrierMerged2 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i82 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return carrierMerged2 != null ? ResolvedNetworkType.CarrierMergedNetworkType.INSTANCE : ResolvedNetworkType.UnknownNetworkType.INSTANCE;
                    case 2:
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) obj2;
                        int i92 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        if (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) {
                            i62 = ((WifiNetworkModel.CarrierMerged) wifiNetworkModel).numberOfLevels;
                        } else {
                            MobileConnectionRepositoryKairos.Companion.getClass();
                            i62 = MobileConnectionRepositoryKairos.Companion.DEFAULT_NUM_LEVELS;
                        }
                        return Integer.valueOf(i62);
                    case 3:
                        WifiNetworkModel.CarrierMerged carrierMerged3 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i102 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged3 != null ? carrierMerged3.level : 0);
                    default:
                        WifiNetworkModel.CarrierMerged carrierMerged4 = (WifiNetworkModel.CarrierMerged) obj2;
                        int i11 = CarrierMergedConnectionRepositoryKairos.$r8$clinit;
                        return Integer.valueOf(carrierMerged4 != null ? carrierMerged4.level : 0);
                }
            }
        });
        this.isRoaming = StateKt.stateOf(bool);
        this.carrierId = StateKt.stateOf(-1);
        this.inflateSignalStrength = StateKt.stateOf(bool);
        this.allowNetworkSliceIndicator = StateKt.stateOf(bool);
        this.isEmergencyOnly = StateKt.stateOf(bool);
        this.operatorAlphaShort = StateKt.stateOf(null);
        Boolean bool2 = Boolean.TRUE;
        this.isInService = StateKt.stateOf(bool2);
        this.isNonTerrestrial = StateKt.stateOf(bool);
        this.isGsm = StateKt.stateOf(bool);
        this.carrierNetworkChangeActive = StateKt.stateOf(bool);
        this.satelliteLevel = StateKt.stateOf(0);
        this.isAllowedDuringAirplaneMode = StateKt.stateOf(bool2);
        this.hasPrioritizedNetworkCapabilities = StateKt.stateOf(bool);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getAllowNetworkSliceIndicator() {
        return this.allowNetworkSliceIndicator;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierNetworkChangeActive() {
        return this.carrierNetworkChangeActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCdmaLevel() {
        return this.cdmaLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCdmaRoaming() {
        return this.cdmaRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataActivityDirection() {
        return this.dataActivityDirection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataConnectionState() {
        return this.dataConnectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataEnabled() {
        return this.isWifiEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getHasPrioritizedNetworkCapabilities() {
        return this.hasPrioritizedNetworkCapabilities;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getInflateSignalStrength() {
        return this.inflateSignalStrength;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getNetworkName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getNumberOfLevels() {
        return this.numberOfLevels;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getOperatorAlphaShort() {
        return this.operatorAlphaShort;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getPrimaryLevel() {
        return this.primaryLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getResolvedNetworkType() {
        return this.resolvedNetworkType;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getSatelliteLevel() {
        return this.satelliteLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final int getSubId() {
        return this.subId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final TableLogBuffer getTableLogBuffer() {
        return this.tableLogBuffer;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isAllowedDuringAirplaneMode() {
        return this.isAllowedDuringAirplaneMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isEmergencyOnly() {
        return this.isEmergencyOnly;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isGsm() {
        return this.isGsm;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isInEcmMode() {
        return this.isInEcmMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isInService() {
        return this.isInService;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isNonTerrestrial() {
        return this.isNonTerrestrial;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isRoaming() {
        return this.isRoaming;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
