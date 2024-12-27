package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.telephony.SubscriptionManager;
import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class CarrierBasedSatelliteViewModelImpl implements MobileIconViewModelCommon {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityContainerVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityIcon;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityInVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityOutVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 anyChanges;
    public final StateFlowImpl contentDescription;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 dexStatusBarIcon;
    public final ReadonlyStateFlow icon;
    public final ReadonlyStateFlow isVisible;
    public final StateFlowImpl networkTypeBackground;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 networkTypeIcon;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 roaming;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 roamingIcon;
    public final int slotId;
    public final int subscriptionId;
    public final StateFlowImpl updateDeXStatusBarIconModel;
    public final ReadonlyStateFlow voiceNoServiceIcon;

    public CarrierBasedSatelliteViewModelImpl(int i, MobileIconInteractor mobileIconInteractor, CoroutineScope coroutineScope) {
        this.subscriptionId = i;
        this.slotId = i == Integer.MAX_VALUE ? 0 : SubscriptionManager.getSlotIndex(i);
        MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
        final ReadonlyStateFlow readonlyStateFlow = mobileIconInteractorImpl.isSimOn;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isVisible = FlowKt.stateIn(distinctUntilChanged, coroutineScope, WhileSubscribed$default, bool);
        this.icon = mobileIconInteractorImpl.signalLevelIcon;
        this.contentDescription = StateFlowKt.MutableStateFlow(new ContentDescription.Loaded(""));
        this.roaming = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.networkTypeIcon = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        this.networkTypeBackground = StateFlowKt.MutableStateFlow(null);
        this.activityInVisible = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.activityOutVisible = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.activityContainerVisible = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.activityIcon = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        this.roamingIcon = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        this.anyChanges = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        this.dexStatusBarIcon = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Unit.INSTANCE);
        this.updateDeXStatusBarIconModel = StateFlowKt.MutableStateFlow(null);
        this.voiceNoServiceIcon = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(0));
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityContainerVisible() {
        return this.activityContainerVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityIcon() {
        return this.activityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityInVisible() {
        return this.activityInVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityOutVisible() {
        return this.activityOutVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getAnyChanges() {
        return this.anyChanges;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getDexStatusBarIcon() {
        return this.dexStatusBarIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getNetworkTypeBackground() {
        return this.networkTypeBackground;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getNetworkTypeIcon() {
        return this.networkTypeIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoaming() {
        return this.roaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoamingIcon() {
        return this.roamingIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        return null;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final int getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getUpdateDeXStatusBarIconModel() {
        return this.updateDeXStatusBarIconModel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getVoiceNoServiceIcon() {
        return this.voiceNoServiceIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.isVisible;
    }
}
