package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class MobileIconViewModelKairos implements MobileIconViewModelKairosCommon, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit activityContainerVisible;
    public final StateInit activityInVisible;
    public final StateInit activityOutVisible;
    public final AirplaneModeInteractor airplaneModeInteractor;
    public final ConnectivityConstants constants;
    public final StateInit contentDescription;
    public final FeatureFlagsClassic flags;
    public final StateInit icon;
    public final MobileIconInteractorKairos iconInteractor;
    public final State isAirplaneMode;
    public final StateInit isVisible;
    public final StateInit networkTypeBackground;
    public final StateInit networkTypeIcon;
    public final StateInit roaming;
    public final Lazy satelliteProvider$delegate;
    public final int subscriptionId;

    public MobileIconViewModelKairos(int i, MobileIconInteractorKairos mobileIconInteractorKairos, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, FeatureFlagsClassic featureFlagsClassic) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.subscriptionId = i;
        this.iconInteractor = mobileIconInteractorKairos;
        this.airplaneModeInteractor = airplaneModeInteractor;
        this.constants = connectivityConstants;
        this.flags = featureFlagsClassic;
        final int i2 = 0;
        this.isAirplaneMode = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ MobileIconViewModelKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        ReadonlyStateFlow readonlyStateFlow = this.f$0.airplaneModeInteractor.isAirplaneMode;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                    default:
                        final MobileIconViewModelKairos mobileIconViewModelKairos = this.f$0;
                        return ((BuildScopeImpl) buildScope).mapLatestBuild(((MobileIconInteractorKairosImpl) mobileIconViewModelKairos.iconInteractor).connectionRepository.isNonTerrestrial(), new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                BuildScope buildScope2 = (BuildScope) obj2;
                                boolean zBooleanValue = ((Boolean) obj3).booleanValue();
                                MobileIconViewModelKairos mobileIconViewModelKairos2 = mobileIconViewModelKairos;
                                if (zBooleanValue) {
                                    return (CarrierBasedSatelliteViewModelKairosImpl) mobileIconViewModelKairos2.satelliteProvider$delegate.getValue();
                                }
                                MobileIconViewModelKairos mobileIconViewModelKairos3 = new MobileIconViewModelKairos$vmProvider$1$1$1(mobileIconViewModelKairos2).this$0;
                                CellularIconViewModelKairos cellularIconViewModelKairos = new CellularIconViewModelKairos(mobileIconViewModelKairos3.subscriptionId, mobileIconViewModelKairos3.iconInteractor, mobileIconViewModelKairos3.airplaneModeInteractor, mobileIconViewModelKairos3.constants, mobileIconViewModelKairos3.flags);
                                cellularIconViewModelKairos.activate(buildScope2);
                                return cellularIconViewModelKairos;
                            }
                        });
                }
            }
        });
        this.satelliteProvider$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MobileIconViewModelKairos mobileIconViewModelKairos = this.f$0;
                return new CarrierBasedSatelliteViewModelKairosImpl(mobileIconViewModelKairos.subscriptionId, mobileIconViewModelKairos.iconInteractor, mobileIconViewModelKairos.isAirplaneMode);
            }
        });
        final int i3 = 1;
        State stateBuildState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda0
            public final /* synthetic */ MobileIconViewModelKairos f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        ReadonlyStateFlow readonlyStateFlow = this.f$0.airplaneModeInteractor.isAirplaneMode;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                    default:
                        final MobileIconViewModelKairos mobileIconViewModelKairos = this.f$0;
                        return ((BuildScopeImpl) buildScope).mapLatestBuild(((MobileIconInteractorKairosImpl) mobileIconViewModelKairos.iconInteractor).connectionRepository.isNonTerrestrial(), new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                BuildScope buildScope2 = (BuildScope) obj2;
                                boolean zBooleanValue = ((Boolean) obj3).booleanValue();
                                MobileIconViewModelKairos mobileIconViewModelKairos2 = mobileIconViewModelKairos;
                                if (zBooleanValue) {
                                    return (CarrierBasedSatelliteViewModelKairosImpl) mobileIconViewModelKairos2.satelliteProvider$delegate.getValue();
                                }
                                MobileIconViewModelKairos mobileIconViewModelKairos3 = new MobileIconViewModelKairos$vmProvider$1$1$1(mobileIconViewModelKairos2).this$0;
                                CellularIconViewModelKairos cellularIconViewModelKairos = new CellularIconViewModelKairos(mobileIconViewModelKairos3.subscriptionId, mobileIconViewModelKairos3.iconInteractor, mobileIconViewModelKairos3.airplaneModeInteractor, mobileIconViewModelKairos3.constants, mobileIconViewModelKairos3.flags);
                                cellularIconViewModelKairos.activate(buildScope2);
                                return cellularIconViewModelKairos;
                            }
                        });
                }
            }
        });
        final int i4 = 4;
        this.isVisible = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i4) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i5 = 5;
        this.icon = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i5) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i6 = 6;
        this.contentDescription = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i6) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i7 = 7;
        this.roaming = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i7) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i8 = 8;
        this.networkTypeIcon = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i8) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i9 = 1;
        this.networkTypeBackground = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i9) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i10 = 2;
        this.activityInVisible = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i10) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i11 = 0;
        this.activityOutVisible = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i11) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
        final int i12 = 3;
        this.activityContainerVisible = StateKt.flatMap(stateBuildState, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MobileIconViewModelKairosCommon mobileIconViewModelKairosCommon = (MobileIconViewModelKairosCommon) obj2;
                switch (i12) {
                    case 0:
                        return mobileIconViewModelKairosCommon.getActivityOutVisible();
                    case 1:
                        return mobileIconViewModelKairosCommon.getNetworkTypeBackground();
                    case 2:
                        return mobileIconViewModelKairosCommon.getActivityInVisible();
                    case 3:
                        return mobileIconViewModelKairosCommon.getActivityContainerVisible();
                    case 4:
                        return mobileIconViewModelKairosCommon.isVisible();
                    case 5:
                        return mobileIconViewModelKairosCommon.getIcon();
                    case 6:
                        return mobileIconViewModelKairosCommon.getContentDescription();
                    case 7:
                        return mobileIconViewModelKairosCommon.getRoaming();
                    default:
                        return mobileIconViewModelKairosCommon.getNetworkTypeIcon();
                }
            }
        });
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getActivityContainerVisible() {
        return this.activityContainerVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getActivityInVisible() {
        return this.activityInVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getActivityOutVisible() {
        return this.activityOutVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getNetworkTypeBackground() {
        return this.networkTypeBackground;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getNetworkTypeIcon() {
        return this.networkTypeIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getRoaming() {
        return this.roaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State isVisible() {
        return this.isVisible;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
