package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes3.dex */
public final class VolumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeDialogOverscrollViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel) {
        super(3, continuation);
        this.this$0 = volumeDialogOverscrollViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1 volumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1 = new VolumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        volumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        volumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return volumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow safeFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            float fFloatValue = ((Number) this.L$1).floatValue();
            if (fFloatValue == 0.0f) {
                safeFlow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new VolumeDialogOverscrollViewModel.OverscrollEventModel.Animate(0.0f));
            } else {
                VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel = this.this$0;
                volumeDialogOverscrollViewModel.getClass();
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                final ChannelLimitedFlowMerge channelLimitedFlowMerge = volumeDialogOverscrollViewModel.inputEventsInteractor.event;
                safeFlow = new SafeFlow(new VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1(new Flow() { // from class: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$mapNotNull$1

                    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$mapNotNull$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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
                                SliderInputEvent sliderInputEvent = (SliderInputEvent) obj;
                                SliderInputEvent.Touch touch = sliderInputEvent instanceof SliderInputEvent.Touch ? (SliderInputEvent.Touch) sliderInputEvent : null;
                                if (touch != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(touch, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = channelLimitedFlowMerge.collect(new AnonymousClass2(flowCollector2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, null, ref$ObjectRef, volumeDialogOverscrollViewModel, fFloatValue));
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, safeFlow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
