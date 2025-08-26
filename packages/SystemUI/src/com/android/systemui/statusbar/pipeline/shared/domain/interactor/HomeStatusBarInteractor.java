package com.android.systemui.statusbar.pipeline.shared.domain.interactor;

import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.CarrierConfigInteractor;
import com.android.systemui.statusbar.pipeline.shared.domain.model.StatusBarDisableFlagsVisibilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class HomeStatusBarInteractor {
    public final ChannelFlowTransformLatest defaultDataSubConfigShowOperatorView;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shouldShowOperatorName;
    public final HomeStatusBarInteractor$special$$inlined$map$1 visibilityViaDisableFlags;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor$special$$inlined$map$1] */
    public HomeStatusBarInteractor(AirplaneModeInteractor airplaneModeInteractor, CarrierConfigInteractor carrierConfigInteractor, DisableFlagsInteractor disableFlagsInteractor) {
        final ReadonlyStateFlow readonlyStateFlow = disableFlagsInteractor.disableFlags;
        this.visibilityViaDisableFlags = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) obj;
                        StatusBarDisableFlagsVisibilityModel statusBarDisableFlagsVisibilityModel = new StatusBarDisableFlagsVisibilityModel(disableFlagsModel.isClockEnabled, disableFlagsModel.areNotificationIconsEnabled, disableFlagsModel.isSystemInfoEnabled, disableFlagsModel.animate);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(statusBarDisableFlagsVisibilityModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(carrierConfigInteractor.defaultDataSubscriptionCarrierConfig, new HomeStatusBarInteractor$special$$inlined$flatMapLatest$1(null));
        this.defaultDataSubConfigShowOperatorView = channelFlowTransformLatestTransformLatest;
        this.shouldShowOperatorName = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestTransformLatest, airplaneModeInteractor.isAirplaneMode, new HomeStatusBarInteractor$shouldShowOperatorName$1(null));
    }
}
