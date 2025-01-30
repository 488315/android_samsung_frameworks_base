package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import android.telephony.SubscriptionManager;
import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        final ReadonlyStateFlow readonlyStateFlow = mobileIconInteractorImpl.isSimOff;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2 */
            public final class C33282 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CarrierBasedSatelliteViewModelImpl$special$$inlined$map$1$2", m277f = "MobileIconViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33282.this.emit(null, this);
                    }
                }

                public C33282(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C33282(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion);
        Boolean bool = Boolean.FALSE;
        this.isVisible = FlowKt.stateIn(distinctUntilChanged, coroutineScope, WhileSubscribed$default, bool);
        this.icon = mobileIconInteractorImpl.icon;
        this.contentDescription = StateFlowKt.MutableStateFlow(new ContentDescription.Loaded(""));
        this.roaming = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.networkTypeIcon = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
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

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityContainerVisible() {
        return this.activityContainerVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityIcon() {
        return this.activityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityInVisible() {
        return this.activityInVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityOutVisible() {
        return this.activityOutVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getAnyChanges() {
        return this.anyChanges;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getDexStatusBarIcon() {
        return this.dexStatusBarIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getNetworkTypeIcon() {
        return this.networkTypeIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoaming() {
        return this.roaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoamingIcon() {
        return this.roamingIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        return null;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final int getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getUpdateDeXStatusBarIconModel() {
        return this.updateDeXStatusBarIconModel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getVoiceNoServiceIcon() {
        return this.voiceNoServiceIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.isVisible;
    }
}
