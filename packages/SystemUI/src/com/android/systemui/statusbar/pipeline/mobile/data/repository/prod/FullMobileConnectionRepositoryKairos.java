package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda9;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos;
import com.sec.ims.IMSParameter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FullMobileConnectionRepositoryKairos implements MobileConnectionRepositoryKairos, KairosBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final State activeRepo;
    public final StateInit allowNetworkSliceIndicator;
    public final StateInit carrierId;
    public final Function1 carrierMergedRepoSpec;
    public final StateInit carrierName;
    public final StateInit carrierNetworkChangeActive;
    public final StateInit cdmaLevel;
    public final StateInit cdmaRoaming;
    public final StateInit dataActivityDirection;
    public final StateInit dataConnectionState;
    public final StateInit dataEnabled;
    public DumpCache dumpCache;
    public final StateInit hasPrioritizedNetworkCapabilities;
    public final StateInit inflateSignalStrength;
    public final StateInit isAllowedDuringAirplaneMode;
    public final State isCarrierMerged;
    public final StateInit isEmergencyOnly;
    public final StateInit isGsm;
    public final StateInit isInEcmMode;
    public final StateInit isInService;
    public final StateInit isNonTerrestrial;
    public final StateInit isRoaming;
    public final MobileConnectionRepositoryKairos mobileRepo;
    public final StateInit networkName;
    public final StateInit numberOfLevels;
    public final StateInit operatorAlphaShort;
    public final StateInit primaryLevel;
    public final StateInit resolvedNetworkType;
    public final StateInit satelliteLevel;
    public final int subId;
    public final TableLogBuffer tableLogBuffer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DumpCache {
        public final MobileConnectionRepositoryKairos activeRepo;
        public final boolean isCarrierMerged;

        public DumpCache(boolean z, MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos) {
            this.isCarrierMerged = z;
            this.activeRepo = mobileConnectionRepositoryKairos;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DumpCache)) {
                return false;
            }
            DumpCache dumpCache = (DumpCache) obj;
            return this.isCarrierMerged == dumpCache.isCarrierMerged && Intrinsics.areEqual(this.activeRepo, dumpCache.activeRepo);
        }

        public final int hashCode() {
            return this.activeRepo.hashCode() + (Boolean.hashCode(this.isCarrierMerged) * 31);
        }

        public final String toString() {
            return "DumpCache(isCarrierMerged=" + this.isCarrierMerged + ", activeRepo=" + this.activeRepo + ")";
        }
    }

    static {
        new Companion(null);
    }

    public FullMobileConnectionRepositoryKairos(int i, TableLogBuffer tableLogBuffer, MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos, Function1 function1, State state) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        this.mobileRepo = mobileConnectionRepositoryKairos;
        this.carrierMergedRepoSpec = function1;
        this.isCarrierMerged = state;
        final int i2 = 0;
        onActivated(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ FullMobileConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        final FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this.f$0;
                        TableLogBuffer tableLogBuffer2 = fullMobileConnectionRepositoryKairos.tableLogBuffer;
                        State state2 = fullMobileConnectionRepositoryKairos.isCarrierMerged;
                        DiffableKt.logBooleanDiffsForTable(buildScope, state2, tableLogBuffer2, "", "isCarrierMerged");
                        StateInit combine = CombineKt.combine(state2, fullMobileConnectionRepositoryKairos.activeRepo, new FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda44());
                        final int i3 = 0;
                        ((BuildScopeImpl) buildScope).observe(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                switch (i3) {
                                    case 0:
                                        fullMobileConnectionRepositoryKairos.dumpCache = (FullMobileConnectionRepositoryKairos.DumpCache) obj3;
                                        return Unit.INSTANCE;
                                    default:
                                        BuildScope buildScope2 = (BuildScope) obj2;
                                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos2 = fullMobileConnectionRepositoryKairos;
                                        if (!booleanValue) {
                                            return fullMobileConnectionRepositoryKairos2.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos2.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo779invoke(buildScopeImpl);
                                }
                            }
                        });
                        return Unit.INSTANCE;
                    default:
                        final FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos2 = this.f$0;
                        final int i4 = 1;
                        return ((BuildScopeImpl) buildScope).mapLatestBuild(fullMobileConnectionRepositoryKairos2.isCarrierMerged, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                switch (i4) {
                                    case 0:
                                        fullMobileConnectionRepositoryKairos2.dumpCache = (FullMobileConnectionRepositoryKairos.DumpCache) obj3;
                                        return Unit.INSTANCE;
                                    default:
                                        BuildScope buildScope2 = (BuildScope) obj2;
                                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos22 = fullMobileConnectionRepositoryKairos2;
                                        if (!booleanValue) {
                                            return fullMobileConnectionRepositoryKairos22.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos22.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo779invoke(buildScopeImpl);
                                }
                            }
                        });
                }
            }
        });
        final int i3 = 1;
        State buildState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ FullMobileConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        final FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this.f$0;
                        TableLogBuffer tableLogBuffer2 = fullMobileConnectionRepositoryKairos.tableLogBuffer;
                        State state2 = fullMobileConnectionRepositoryKairos.isCarrierMerged;
                        DiffableKt.logBooleanDiffsForTable(buildScope, state2, tableLogBuffer2, "", "isCarrierMerged");
                        StateInit combine = CombineKt.combine(state2, fullMobileConnectionRepositoryKairos.activeRepo, new FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda44());
                        final int i32 = 0;
                        ((BuildScopeImpl) buildScope).observe(combine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                switch (i32) {
                                    case 0:
                                        fullMobileConnectionRepositoryKairos.dumpCache = (FullMobileConnectionRepositoryKairos.DumpCache) obj3;
                                        return Unit.INSTANCE;
                                    default:
                                        BuildScope buildScope2 = (BuildScope) obj2;
                                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos22 = fullMobileConnectionRepositoryKairos;
                                        if (!booleanValue) {
                                            return fullMobileConnectionRepositoryKairos22.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos22.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo779invoke(buildScopeImpl);
                                }
                            }
                        });
                        return Unit.INSTANCE;
                    default:
                        final FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos2 = this.f$0;
                        final int i4 = 1;
                        return ((BuildScopeImpl) buildScope).mapLatestBuild(fullMobileConnectionRepositoryKairos2.isCarrierMerged, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                switch (i4) {
                                    case 0:
                                        fullMobileConnectionRepositoryKairos2.dumpCache = (FullMobileConnectionRepositoryKairos.DumpCache) obj3;
                                        return Unit.INSTANCE;
                                    default:
                                        BuildScope buildScope2 = (BuildScope) obj2;
                                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos22 = fullMobileConnectionRepositoryKairos2;
                                        if (!booleanValue) {
                                            return fullMobileConnectionRepositoryKairos22.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos22.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo779invoke(buildScopeImpl);
                                }
                            }
                        });
                }
            }
        });
        this.activeRepo = buildState;
        final int i4 = 6;
        this.carrierId = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i4) {
                    case 0:
                        int i5 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i6 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i7 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i8 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i9 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i10 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i11 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i12 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i13 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i14 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i5 = 13;
        this.cdmaRoaming = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i5) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i6 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i7 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i8 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i9 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i10 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i11 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i12 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i13 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i14 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i6 = 17;
        final StateInit flatMap = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i6) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i7 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i8 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i9 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i10 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i11 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i12 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i13 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i14 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i7 = 12;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i7) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isEmergencyOnly = flatMap;
        final int i8 = 19;
        final StateInit flatMap2 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i8) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i9 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i10 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i11 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i12 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i13 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i14 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i9 = 13;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i9) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap2;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap2, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isRoaming = flatMap2;
        final int i10 = 20;
        final StateInit flatMap3 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i10) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i11 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i12 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i13 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i14 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i11 = 14;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i11) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap3;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap3, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.operatorAlphaShort = flatMap3;
        final int i12 = 0;
        final StateInit flatMap4 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i12) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i13 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i14 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i13 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i13) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap4;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap4, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isInService = flatMap4;
        final int i14 = 18;
        final StateInit flatMap5 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i14) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i15 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i16 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i15 = 15;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i15) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap5;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap5, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isNonTerrestrial = flatMap5;
        final int i16 = 21;
        final StateInit flatMap6 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i16) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i17 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i18 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i17 = 16;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i17) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap6;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap6, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isGsm = flatMap6;
        final int i18 = 22;
        final StateInit flatMap7 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i18) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i19 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i20 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i19 = 17;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i19) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap7;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap7, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.cdmaLevel = flatMap7;
        final int i20 = 23;
        final StateInit flatMap8 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i20) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i21 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i22 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i21 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i21) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap8;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap8, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.primaryLevel = flatMap8;
        final int i22 = 1;
        final StateInit flatMap9 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i22) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i23 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i24 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i23 = 2;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i23) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap9;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap9, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.satelliteLevel = flatMap9;
        final int i24 = 2;
        final StateInit flatMap10 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i24) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i25 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i26 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i25 = 3;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i25) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap10;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap10, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataConnectionState = flatMap10;
        final int i26 = 3;
        final StateInit flatMap11 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i26) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i27 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i28 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i27 = 4;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i27) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap11;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap11, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataActivityDirection = flatMap11;
        final int i28 = 4;
        final StateInit flatMap12 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i28) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i29 = 5;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i29) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap12;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap12, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.carrierNetworkChangeActive = flatMap12;
        final int i30 = 5;
        final StateInit flatMap13 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i30) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i31 = 6;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i31) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap13;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap13, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.resolvedNetworkType = flatMap13;
        final int i32 = 7;
        final StateInit flatMap14 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i32) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i33 = 7;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i33) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap14;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap14, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataEnabled = flatMap14;
        final int i34 = 8;
        final StateInit flatMap15 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i34) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i35 = 8;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i35) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap15;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap15, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.inflateSignalStrength = flatMap15;
        final int i36 = 9;
        final StateInit flatMap16 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i36) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i37 = 9;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i37) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap16;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap16, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.allowNetworkSliceIndicator = flatMap16;
        final int i38 = 10;
        this.numberOfLevels = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i38) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i39 = 11;
        final StateInit flatMap17 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i39) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i40 = 10;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i40) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap17;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap17, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.networkName = flatMap17;
        final int i41 = 12;
        final StateInit flatMap18 = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i41) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i42 = 11;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i42) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = flatMap18;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, flatMap18, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.carrierName = flatMap18;
        final int i43 = 14;
        this.isAllowedDuringAirplaneMode = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i43) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i44 = 15;
        this.hasPrioritizedNetworkCapabilities = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i44) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
        final int i45 = 16;
        this.isInEcmMode = StateKt.flatMap(buildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = (MobileConnectionRepositoryKairos) obj2;
                switch (i45) {
                    case 0:
                        int i52 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInService();
                    case 1:
                        int i62 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getSatelliteLevel();
                    case 2:
                        int i72 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataConnectionState();
                    case 3:
                        int i82 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataActivityDirection();
                    case 4:
                        int i92 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierNetworkChangeActive();
                    case 5:
                        int i102 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getResolvedNetworkType();
                    case 6:
                        int i112 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierId();
                    case 7:
                        int i122 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getDataEnabled();
                    case 8:
                        int i132 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getInflateSignalStrength();
                    case 9:
                        int i142 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getAllowNetworkSliceIndicator();
                    case 10:
                        int i152 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNumberOfLevels();
                    case 11:
                        int i162 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getNetworkName();
                    case 12:
                        int i172 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCarrierName();
                    case 13:
                        int i182 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaRoaming();
                    case 14:
                        int i192 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isAllowedDuringAirplaneMode();
                    case 15:
                        int i202 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getHasPrioritizedNetworkCapabilities();
                    case 16:
                        int i212 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isInEcmMode();
                    case 17:
                        int i222 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isEmergencyOnly();
                    case 18:
                        int i232 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isNonTerrestrial();
                    case 19:
                        int i242 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isRoaming();
                    case 20:
                        int i252 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getOperatorAlphaShort();
                    case 21:
                        int i262 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.isGsm();
                    case 22:
                        int i272 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getCdmaLevel();
                    default:
                        int i282 = FullMobileConnectionRepositoryKairos.$r8$clinit;
                        return mobileConnectionRepositoryKairos2.getPrimaryLevel();
                }
            }
        });
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
        return this.carrierName;
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
        return this.dataEnabled;
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

    public static /* synthetic */ void getActiveRepo$annotations() {
    }
}
