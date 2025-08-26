package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.StateImplKt;
import com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda5;
import com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda6;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstantsImpl;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class CellularIconViewModelKairos implements MobileIconViewModelKairosCommon, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit activityContainerVisible;
    public final StateInit activityInVisible;
    public final StateInit activityOutVisible;
    public final StateInit contentDescription;
    public final MobileIconInteractorKairos iconInteractor;
    public final State isVisible;
    public final StateInit networkTypeBackground;
    public final StateInit networkTypeIcon;
    public final StateInit roaming;
    public final StateInit showNetworkTypeIcon;

    public CellularIconViewModelKairos(int i, MobileIconInteractorKairos mobileIconInteractorKairos, final AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, FeatureFlagsClassic featureFlagsClassic) {
        State stateBuildState;
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.iconInteractor = mobileIconInteractorKairos;
        ConnectivityConstantsImpl connectivityConstantsImpl = (ConnectivityConstantsImpl) connectivityConstants;
        if (connectivityConstantsImpl.hasDataCapabilities) {
            final int i2 = 2;
            stateBuildState = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    BuildScope buildScope = (BuildScope) obj;
                    switch (i2) {
                        case 0:
                            DiffableKt.logBooleanDiffsForTable(buildScope, (StateInit) airplaneModeInteractor, ((MobileIconInteractorKairosImpl) this.iconInteractor).connectionRepository.getTableLogBuffer(), "", "showNetworkTypeIcon");
                            return Unit.INSTANCE;
                        case 1:
                            DiffableKt.logBooleanDiffsForTable(buildScope, (StateInit) airplaneModeInteractor, ((MobileIconInteractorKairosImpl) this.iconInteractor).connectionRepository.getTableLogBuffer(), "", "roaming");
                            return Unit.INSTANCE;
                        default:
                            ReadonlyStateFlow readonlyStateFlow = ((AirplaneModeInteractor) airplaneModeInteractor).isAirplaneMode;
                            BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                            buildScopeImpl.getClass();
                            StateInit state = BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                            MobileIconInteractorKairos mobileIconInteractorKairos2 = this.iconInteractor;
                            State stateIsAllowedDuringAirplaneMode = ((MobileIconInteractorKairosImpl) mobileIconInteractorKairos2).connectionRepository.isAllowedDuringAirplaneMode();
                            MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos2;
                            StateInit stateInitCombine = CombineKt.combine(state, stateIsAllowedDuringAirplaneMode, mobileIconInteractorKairosImpl.isForceHidden, new CellularIconViewModelKairos$$ExternalSyntheticLambda10());
                            DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine, mobileIconInteractorKairosImpl.connectionRepository.getTableLogBuffer(), "", "visible");
                            return stateInitCombine;
                    }
                }
            });
        } else {
            stateBuildState = StateKt.stateOf(Boolean.FALSE);
        }
        this.isVisible = stateBuildState;
        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos;
        this.contentDescription = CombineKt.combine(mobileIconInteractorKairosImpl.signalLevelIcon, mobileIconInteractorKairosImpl.networkName, new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda1
            /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
            @Override // kotlin.jvm.functions.Function3
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SignalIconModel signalIconModel = (SignalIconModel) obj2;
                NetworkNameModel networkNameModel = (NetworkNameModel) obj3;
                if (!(signalIconModel instanceof SignalIconModel.Cellular)) {
                    return null;
                }
                String name = networkNameModel.getName();
                SignalIconModel.Cellular cellular = (SignalIconModel.Cellular) signalIconModel;
                int i3 = cellular.level;
                int i4 = R.string.accessibility_no_signal;
                if (i3 != 0) {
                    if (i3 == 1) {
                        i4 = R.string.accessibility_one_bar;
                    } else if (i3 == 2) {
                        i4 = R.string.accessibility_two_bars;
                    } else if (i3 != 3) {
                        int i5 = cellular.numberOfLevels;
                        if (i3 != 4) {
                            if (i3 == 5 && i5 == 6) {
                                i4 = R.string.accessibility_signal_full;
                            }
                        } else if (i5 == 6) {
                            i4 = R.string.accessibility_four_bars;
                        }
                    } else {
                        i4 = R.string.accessibility_three_bars;
                    }
                }
                return new MobileContentDescription$Cellular(name, i4);
            }
        });
        final StateInit stateInit = mobileIconInteractorKairosImpl.isDataConnected;
        final State state = mobileIconInteractorKairosImpl.isDataEnabled;
        final State state2 = mobileIconInteractorKairosImpl.alwaysShowDataRatIcon;
        final State state3 = mobileIconInteractorKairosImpl.mobileIsDefault;
        final State carrierNetworkChangeActive = mobileIconInteractorKairosImpl.connectionRepository.getCarrierNetworkChangeActive();
        final CellularIconViewModelKairos$$ExternalSyntheticLambda2 cellularIconViewModelKairos$$ExternalSyntheticLambda2 = new CellularIconViewModelKairos$$ExternalSyntheticLambda2();
        final StateInit stateInit2 = new StateInit(new Init("combine", new Function1() { // from class: com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                final Init init = stateInit.init;
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = state.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2 = state2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3 = state3.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos4 = carrierNetworkChangeActive.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                return StateImplKt.mapStateImpl(new StateImplKt$$ExternalSyntheticLambda6(StateImplKt.zipStateList("combine", 5, new Init(null, new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda15
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        NetworkScope networkScope = (NetworkScope) obj2;
                        return Arrays.asList(init.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos4.connect(networkScope));
                    }
                })), 2), "combine", "combine", new StateImplKt$$ExternalSyntheticLambda5(new CombineKt$$ExternalSyntheticLambda9(cellularIconViewModelKairos$$ExternalSyntheticLambda2), 2));
            }
        }));
        final int i3 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, (StateInit) stateInit2, ((MobileIconInteractorKairosImpl) this.iconInteractor).connectionRepository.getTableLogBuffer(), "", "showNetworkTypeIcon");
                        return Unit.INSTANCE;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, (StateInit) stateInit2, ((MobileIconInteractorKairosImpl) this.iconInteractor).connectionRepository.getTableLogBuffer(), "", "roaming");
                        return Unit.INSTANCE;
                    default:
                        ReadonlyStateFlow readonlyStateFlow = ((AirplaneModeInteractor) stateInit2).isAirplaneMode;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        StateInit state4 = BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                        MobileIconInteractorKairos mobileIconInteractorKairos2 = this.iconInteractor;
                        State stateIsAllowedDuringAirplaneMode = ((MobileIconInteractorKairosImpl) mobileIconInteractorKairos2).connectionRepository.isAllowedDuringAirplaneMode();
                        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl2 = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos2;
                        StateInit stateInitCombine = CombineKt.combine(state4, stateIsAllowedDuringAirplaneMode, mobileIconInteractorKairosImpl2.isForceHidden, new CellularIconViewModelKairos$$ExternalSyntheticLambda10());
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine, mobileIconInteractorKairosImpl2.connectionRepository.getTableLogBuffer(), "", "visible");
                        return stateInitCombine;
                }
            }
        });
        this.showNetworkTypeIcon = stateInit2;
        this.networkTypeIcon = CombineKt.combine(mobileIconInteractorKairosImpl.networkTypeIconGroup, stateInit2, new CellularIconViewModelKairos$$ExternalSyntheticLambda4());
        final int i4 = 0;
        this.networkTypeBackground = StateKt.map(mobileIconInteractorKairosImpl.showSliceAttribution, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i4) {
                    case 0:
                        if (((Boolean) obj2).booleanValue()) {
                            return new Icon.Resource(R.drawable.mobile_network_type_background, null);
                        }
                        return null;
                    case 1:
                        DataActivityModel dataActivityModel = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityIn : false);
                    case 2:
                        DataActivityModel dataActivityModel2 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel2 != null ? dataActivityModel2.hasActivityOut : false);
                    default:
                        DataActivityModel dataActivityModel3 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel3 != null && (dataActivityModel3.hasActivityIn || dataActivityModel3.hasActivityOut));
                }
            }
        });
        final StateInit stateInit3 = mobileIconInteractorKairosImpl.isRoaming;
        final int i5 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i5) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, (StateInit) stateInit3, ((MobileIconInteractorKairosImpl) this.iconInteractor).connectionRepository.getTableLogBuffer(), "", "showNetworkTypeIcon");
                        return Unit.INSTANCE;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, (StateInit) stateInit3, ((MobileIconInteractorKairosImpl) this.iconInteractor).connectionRepository.getTableLogBuffer(), "", "roaming");
                        return Unit.INSTANCE;
                    default:
                        ReadonlyStateFlow readonlyStateFlow = ((AirplaneModeInteractor) stateInit3).isAirplaneMode;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        StateInit state4 = BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                        MobileIconInteractorKairos mobileIconInteractorKairos2 = this.iconInteractor;
                        State stateIsAllowedDuringAirplaneMode = ((MobileIconInteractorKairosImpl) mobileIconInteractorKairos2).connectionRepository.isAllowedDuringAirplaneMode();
                        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl2 = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos2;
                        StateInit stateInitCombine = CombineKt.combine(state4, stateIsAllowedDuringAirplaneMode, mobileIconInteractorKairosImpl2.isForceHidden, new CellularIconViewModelKairos$$ExternalSyntheticLambda10());
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine, mobileIconInteractorKairosImpl2.connectionRepository.getTableLogBuffer(), "", "visible");
                        return stateInitCombine;
                }
            }
        });
        this.roaming = stateInit3;
        State stateStateOf = !connectivityConstantsImpl.shouldShowActivityConfig ? StateKt.stateOf(null) : mobileIconInteractorKairosImpl.connectionRepository.getDataActivityDirection();
        final int i6 = 1;
        this.activityInVisible = StateKt.map(stateStateOf, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i6) {
                    case 0:
                        if (((Boolean) obj2).booleanValue()) {
                            return new Icon.Resource(R.drawable.mobile_network_type_background, null);
                        }
                        return null;
                    case 1:
                        DataActivityModel dataActivityModel = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityIn : false);
                    case 2:
                        DataActivityModel dataActivityModel2 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel2 != null ? dataActivityModel2.hasActivityOut : false);
                    default:
                        DataActivityModel dataActivityModel3 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel3 != null && (dataActivityModel3.hasActivityIn || dataActivityModel3.hasActivityOut));
                }
            }
        });
        final int i7 = 2;
        this.activityOutVisible = StateKt.map(stateStateOf, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i7) {
                    case 0:
                        if (((Boolean) obj2).booleanValue()) {
                            return new Icon.Resource(R.drawable.mobile_network_type_background, null);
                        }
                        return null;
                    case 1:
                        DataActivityModel dataActivityModel = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityIn : false);
                    case 2:
                        DataActivityModel dataActivityModel2 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel2 != null ? dataActivityModel2.hasActivityOut : false);
                    default:
                        DataActivityModel dataActivityModel3 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel3 != null && (dataActivityModel3.hasActivityIn || dataActivityModel3.hasActivityOut));
                }
            }
        });
        final int i8 = 3;
        this.activityContainerVisible = StateKt.map(stateStateOf, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i8) {
                    case 0:
                        if (((Boolean) obj2).booleanValue()) {
                            return new Icon.Resource(R.drawable.mobile_network_type_background, null);
                        }
                        return null;
                    case 1:
                        DataActivityModel dataActivityModel = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityIn : false);
                    case 2:
                        DataActivityModel dataActivityModel2 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel2 != null ? dataActivityModel2.hasActivityOut : false);
                    default:
                        DataActivityModel dataActivityModel3 = (DataActivityModel) obj2;
                        return Boolean.valueOf(dataActivityModel3 != null && (dataActivityModel3.hasActivityIn || dataActivityModel3.hasActivityOut));
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
        return ((MobileIconInteractorKairosImpl) this.iconInteractor).signalLevelIcon;
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
