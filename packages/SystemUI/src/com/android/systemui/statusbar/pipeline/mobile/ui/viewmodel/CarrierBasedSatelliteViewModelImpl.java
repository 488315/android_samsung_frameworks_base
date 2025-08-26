package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.telephony.SubscriptionManager;
import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
public final class CarrierBasedSatelliteViewModelImpl implements MobileIconViewModelCommon {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityContainerVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityIcon;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityInVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityOutVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 anyChanges;
    public final StateFlowImpl contentDescription;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 dexStatusBarIcon;
    public final Flow icon;
    public final ReadonlyStateFlow isVisible;
    public final StateFlowImpl networkTypeBackground;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 networkTypeIcon;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 roaming;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 roamingIcon;
    public final int slotId;
    public final int subscriptionId;
    public final StateFlowImpl updateDeXStatusBarIconModel;
    public final ReadonlyStateFlow voiceNoServiceIcon;

    public CarrierBasedSatelliteViewModelImpl(int i, AirplaneModeInteractor airplaneModeInteractor, MobileIconInteractor mobileIconInteractor, CoroutineScope coroutineScope) {
        this.subscriptionId = i;
        this.slotId = SubscriptionManager.getSlotIndex(i) == -1 ? 0 : SubscriptionManager.getSlotIndex(i);
        final Flow flowIsSimOn = mobileIconInteractor.isSimOn();
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1

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
                        Boolean boolValueOf = Boolean.valueOf(((Boolean) obj).booleanValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowIsSimOn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isVisible = FlowKt.stateIn(flowDistinctUntilChanged, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.icon = mobileIconInteractor.getSignalLevelIcon();
        this.contentDescription = StateFlowKt.MutableStateFlow(null);
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
