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
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstantsImpl;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        State buildState;
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.iconInteractor = mobileIconInteractorKairos;
        ConnectivityConstantsImpl connectivityConstantsImpl = (ConnectivityConstantsImpl) connectivityConstants;
        if (connectivityConstantsImpl.hasDataCapabilities) {
            final int i2 = 2;
            buildState = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
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
                            State isAllowedDuringAirplaneMode = ((MobileIconInteractorKairosImpl) mobileIconInteractorKairos2).connectionRepository.isAllowedDuringAirplaneMode();
                            MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos2;
                            StateInit combine = CombineKt.combine(state, isAllowedDuringAirplaneMode, mobileIconInteractorKairosImpl.isForceHidden, new CellularIconViewModelKairos$$ExternalSyntheticLambda10());
                            DiffableKt.logBooleanDiffsForTable(buildScope, combine, mobileIconInteractorKairosImpl.connectionRepository.getTableLogBuffer(), "", "visible");
                            return combine;
                    }
                }
            });
        } else {
            buildState = StateKt.stateOf(Boolean.FALSE);
        }
        this.isVisible = buildState;
        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos;
        this.contentDescription = CombineKt.combine(mobileIconInteractorKairosImpl.signalLevelIcon, mobileIconInteractorKairosImpl.networkName, new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda1
            /* JADX WARN: Code restructure failed: missing block: B:15:0x002f, code lost:
            
                if (r6 != 6) goto L24;
             */
            @Override // kotlin.jvm.functions.Function3
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r5, java.lang.Object r6, java.lang.Object r7) {
                /*
                    r4 = this;
                    com.android.systemui.kairos.KairosScope r5 = (com.android.systemui.kairos.KairosScope) r5
                    com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel r6 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel) r6
                    com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel r7 = (com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel) r7
                    boolean r4 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular
                    if (r4 == 0) goto L48
                    com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular r4 = new com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular
                    java.lang.String r5 = r7.getName()
                    com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel$Cellular r6 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular) r6
                    int r7 = r6.level
                    r0 = 2131951874(0x7f130102, float:1.9540175E38)
                    if (r7 == 0) goto L44
                    r1 = 1
                    if (r7 == r1) goto L41
                    r1 = 2
                    if (r7 == r1) goto L3d
                    r1 = 3
                    if (r7 == r1) goto L39
                    r1 = 4
                    r2 = 2131951946(0x7f13014a, float:1.954032E38)
                    r3 = 6
                    int r6 = r6.numberOfLevels
                    if (r7 == r1) goto L33
                    r1 = 5
                    if (r7 == r1) goto L2f
                    goto L44
                L2f:
                    if (r6 != r3) goto L44
                L31:
                    r0 = r2
                    goto L44
                L33:
                    if (r6 != r3) goto L31
                    r0 = 2131951814(0x7f1300c6, float:1.9540053E38)
                    goto L44
                L39:
                    r0 = 2131951970(0x7f130162, float:1.954037E38)
                    goto L44
                L3d:
                    r0 = 2131951973(0x7f130165, float:1.9540376E38)
                    goto L44
                L41:
                    r0 = 2131951879(0x7f130107, float:1.9540185E38)
                L44:
                    r4.<init>(r5, r0)
                    return r4
                L48:
                    r4 = 0
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
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
            public final Object mo779invoke(Object obj) {
                final Init init = StateInit.this.init;
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = state.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2 = state2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3 = state3.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos4 = carrierNetworkChangeActive.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                return StateImplKt.mapStateImpl(new StateImplKt$$ExternalSyntheticLambda6(StateImplKt.zipStateList("combine", 5, new Init(null, new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda15
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        NetworkScope networkScope = (NetworkScope) obj2;
                        return Arrays.asList(Init.this.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos2.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos3.connect(networkScope), init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos4.connect(networkScope));
                    }
                })), 2), "combine", "combine", new StateImplKt$$ExternalSyntheticLambda5(new CombineKt$$ExternalSyntheticLambda9(cellularIconViewModelKairos$$ExternalSyntheticLambda2), 2));
            }
        }));
        final int i3 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
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
                        State isAllowedDuringAirplaneMode = ((MobileIconInteractorKairosImpl) mobileIconInteractorKairos2).connectionRepository.isAllowedDuringAirplaneMode();
                        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl2 = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos2;
                        StateInit combine = CombineKt.combine(state4, isAllowedDuringAirplaneMode, mobileIconInteractorKairosImpl2.isForceHidden, new CellularIconViewModelKairos$$ExternalSyntheticLambda10());
                        DiffableKt.logBooleanDiffsForTable(buildScope, combine, mobileIconInteractorKairosImpl2.connectionRepository.getTableLogBuffer(), "", "visible");
                        return combine;
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
            public final Object mo779invoke(Object obj) {
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
                        State isAllowedDuringAirplaneMode = ((MobileIconInteractorKairosImpl) mobileIconInteractorKairos2).connectionRepository.isAllowedDuringAirplaneMode();
                        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl2 = (MobileIconInteractorKairosImpl) mobileIconInteractorKairos2;
                        StateInit combine = CombineKt.combine(state4, isAllowedDuringAirplaneMode, mobileIconInteractorKairosImpl2.isForceHidden, new CellularIconViewModelKairos$$ExternalSyntheticLambda10());
                        DiffableKt.logBooleanDiffsForTable(buildScope, combine, mobileIconInteractorKairosImpl2.connectionRepository.getTableLogBuffer(), "", "visible");
                        return combine;
                }
            }
        });
        this.roaming = stateInit3;
        State stateOf = !connectivityConstantsImpl.shouldShowActivityConfig ? StateKt.stateOf(null) : mobileIconInteractorKairosImpl.connectionRepository.getDataActivityDirection();
        final int i6 = 1;
        this.activityInVisible = StateKt.map(stateOf, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
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
        this.activityOutVisible = StateKt.map(stateOf, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
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
        this.activityContainerVisible = StateKt.map(stateOf, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModelKairos$$ExternalSyntheticLambda5
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
