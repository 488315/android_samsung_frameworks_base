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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        final FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this.f$0;
                        TableLogBuffer tableLogBuffer2 = fullMobileConnectionRepositoryKairos.tableLogBuffer;
                        State state2 = fullMobileConnectionRepositoryKairos.isCarrierMerged;
                        DiffableKt.logBooleanDiffsForTable(buildScope, state2, tableLogBuffer2, "", "isCarrierMerged");
                        StateInit stateInitCombine = CombineKt.combine(state2, fullMobileConnectionRepositoryKairos.activeRepo, new FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda44());
                        final int i3 = 0;
                        ((BuildScopeImpl) buildScope).observe(stateInitCombine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                switch (i3) {
                                    case 0:
                                        fullMobileConnectionRepositoryKairos.dumpCache = (FullMobileConnectionRepositoryKairos.DumpCache) obj3;
                                        return Unit.INSTANCE;
                                    default:
                                        BuildScope buildScope2 = (BuildScope) obj2;
                                        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos2 = fullMobileConnectionRepositoryKairos;
                                        if (!zBooleanValue) {
                                            return fullMobileConnectionRepositoryKairos2.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos2.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo781invoke(buildScopeImpl);
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
                                        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos22 = fullMobileConnectionRepositoryKairos2;
                                        if (!zBooleanValue) {
                                            return fullMobileConnectionRepositoryKairos22.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos22.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo781invoke(buildScopeImpl);
                                }
                            }
                        });
                }
            }
        });
        final int i3 = 1;
        State stateBuildState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ FullMobileConnectionRepositoryKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        final FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this.f$0;
                        TableLogBuffer tableLogBuffer2 = fullMobileConnectionRepositoryKairos.tableLogBuffer;
                        State state2 = fullMobileConnectionRepositoryKairos.isCarrierMerged;
                        DiffableKt.logBooleanDiffsForTable(buildScope, state2, tableLogBuffer2, "", "isCarrierMerged");
                        StateInit stateInitCombine = CombineKt.combine(state2, fullMobileConnectionRepositoryKairos.activeRepo, new FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda44());
                        final int i32 = 0;
                        ((BuildScopeImpl) buildScope).observe(stateInitCombine, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda45
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                switch (i32) {
                                    case 0:
                                        fullMobileConnectionRepositoryKairos.dumpCache = (FullMobileConnectionRepositoryKairos.DumpCache) obj3;
                                        return Unit.INSTANCE;
                                    default:
                                        BuildScope buildScope2 = (BuildScope) obj2;
                                        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos22 = fullMobileConnectionRepositoryKairos;
                                        if (!zBooleanValue) {
                                            return fullMobileConnectionRepositoryKairos22.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos22.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo781invoke(buildScopeImpl);
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
                                        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
                                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos22 = fullMobileConnectionRepositoryKairos2;
                                        if (!zBooleanValue) {
                                            return fullMobileConnectionRepositoryKairos22.mobileRepo;
                                        }
                                        Function1 function12 = fullMobileConnectionRepositoryKairos22.carrierMergedRepoSpec;
                                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope2;
                                        buildScopeImpl.getClass();
                                        return (MobileConnectionRepositoryKairos) function12.mo781invoke(buildScopeImpl);
                                }
                            }
                        });
                }
            }
        });
        this.activeRepo = stateBuildState;
        final int i4 = 6;
        this.carrierId = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
        this.cdmaRoaming = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
        final StateInit stateInitFlatMap = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i7) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isEmergencyOnly = stateInitFlatMap;
        final int i8 = 19;
        final StateInit stateInitFlatMap2 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i9) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap2;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap2, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isRoaming = stateInitFlatMap2;
        final int i10 = 20;
        final StateInit stateInitFlatMap3 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i11) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap3;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap3, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.operatorAlphaShort = stateInitFlatMap3;
        final int i12 = 0;
        final StateInit stateInitFlatMap4 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i13) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap4;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap4, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isInService = stateInitFlatMap4;
        final int i14 = 18;
        final StateInit stateInitFlatMap5 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i15) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap5;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap5, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isNonTerrestrial = stateInitFlatMap5;
        final int i16 = 21;
        final StateInit stateInitFlatMap6 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i17) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap6;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap6, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isGsm = stateInitFlatMap6;
        final int i18 = 22;
        final StateInit stateInitFlatMap7 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i19) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap7;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap7, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.cdmaLevel = stateInitFlatMap7;
        final int i20 = 23;
        final StateInit stateInitFlatMap8 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i21) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap8;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap8, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.primaryLevel = stateInitFlatMap8;
        final int i22 = 1;
        final StateInit stateInitFlatMap9 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i23) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap9;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap9, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.satelliteLevel = stateInitFlatMap9;
        final int i24 = 2;
        final StateInit stateInitFlatMap10 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i25) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap10;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap10, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataConnectionState = stateInitFlatMap10;
        final int i26 = 3;
        final StateInit stateInitFlatMap11 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i27) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap11;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap11, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataActivityDirection = stateInitFlatMap11;
        final int i28 = 4;
        final StateInit stateInitFlatMap12 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i29) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap12;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap12, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.carrierNetworkChangeActive = stateInitFlatMap12;
        final int i30 = 5;
        final StateInit stateInitFlatMap13 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i31) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap13;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap13, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.resolvedNetworkType = stateInitFlatMap13;
        final int i32 = 7;
        final StateInit stateInitFlatMap14 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i33) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap14;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap14, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataEnabled = stateInitFlatMap14;
        final int i34 = 8;
        final StateInit stateInitFlatMap15 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i35) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap15;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap15, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.inflateSignalStrength = stateInitFlatMap15;
        final int i36 = 9;
        final StateInit stateInitFlatMap16 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i37) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap16;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap16, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.allowNetworkSliceIndicator = stateInitFlatMap16;
        final int i38 = 10;
        this.numberOfLevels = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
        final StateInit stateInitFlatMap17 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i40) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap17;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap17, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.networkName = stateInitFlatMap17;
        final int i41 = 12;
        final StateInit stateInitFlatMap18 = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i42) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    case 2:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                    case 3:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "");
                        break;
                    case 4:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "");
                        break;
                    case 5:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 6:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "");
                        break;
                    case 7:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED);
                        break;
                    case 8:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "inflate");
                        break;
                    case 9:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "allowSlice");
                        break;
                    case 10:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "intent");
                        break;
                    case 11:
                        DiffableKt.logDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "sub");
                        break;
                    case 12:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "emergencyOnly");
                        break;
                    case 13:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "roaming");
                        break;
                    case 14:
                        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitFlatMap18;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(fullMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 15:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "isNtn");
                        break;
                    case 16:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "isGsm");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitFlatMap18, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.carrierName = stateInitFlatMap18;
        final int i43 = 14;
        this.isAllowedDuringAirplaneMode = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
        this.hasPrioritizedNetworkCapabilities = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
        this.isInEcmMode = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
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
