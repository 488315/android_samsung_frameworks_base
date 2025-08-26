package com.android.systemui.statusbar.pipeline.battery.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor;
import com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1;
import com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph;
import java.util.Collections;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class BatteryViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BatteryInteractor $interactor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BatteryViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, BatteryInteractor batteryInteractor, BatteryViewModel batteryViewModel) {
        super(3, continuation);
        this.$interactor$inlined = batteryInteractor;
        this.this$0 = batteryViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BatteryViewModel$special$$inlined$flatMapLatest$1 batteryViewModel$special$$inlined$flatMapLatest$1 = new BatteryViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$interactor$inlined, this.this$0);
        batteryViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        batteryViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return batteryViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowCombine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                BatteryInteractor$special$$inlined$map$1 batteryInteractor$special$$inlined$map$1 = this.$interactor$inlined.isFull;
                BatteryViewModel batteryViewModel = this.this$0;
                flowCombine = FlowKt.combine(batteryInteractor$special$$inlined$map$1, batteryViewModel.levelGlyphs, batteryViewModel.attributionGlyph, new BatteryViewModel$_glyphList$1$1(null));
            } else {
                final BatteryViewModel$special$$inlined$map$1 batteryViewModel$special$$inlined$map$1 = this.this$0.attributionGlyph;
                flowCombine = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$_glyphList$lambda$3$$inlined$map$1

                    /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$_glyphList$lambda$3$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel$_glyphList$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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
                                AttributionGlyph attributionGlyph = (AttributionGlyph) obj;
                                Object objSingletonList = attributionGlyph == null ? EmptyList.INSTANCE : Collections.singletonList(attributionGlyph.standalone);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(objSingletonList, anonymousClass1) == coroutineSingletons) {
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = batteryViewModel$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowCombine, this) == coroutineSingletons) {
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
