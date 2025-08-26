package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.view.animation.PathInterpolator;
import com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $direction$inlined;
    final /* synthetic */ Ref$ObjectRef $startPosition$inlined;
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogOverscrollViewModel this$0;

    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ float $direction$inlined;
        public final /* synthetic */ Ref$ObjectRef $startPosition$inlined;
        public final /* synthetic */ VolumeDialogOverscrollViewModel this$0;

        /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C06511 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C06511(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, Ref$ObjectRef ref$ObjectRef, VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel, float f) {
            this.$startPosition$inlined = ref$ObjectRef;
            this.this$0 = volumeDialogOverscrollViewModel;
            this.$direction$inlined = f;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
        
            if (r6.emit(r7, r0) == r1) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00a9, code lost:
        
            if (r6.emit(r7, r0) == r1) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00ab, code lost:
        
            return r1;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /* JADX WARN: Type inference failed for: r7v9, types: [T, java.lang.Float] */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            C06511 c06511;
            if (continuation instanceof C06511) {
                c06511 = (C06511) continuation;
                int i = c06511.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    c06511.label = i - Integer.MIN_VALUE;
                } else {
                    c06511 = new C06511(continuation);
                }
            }
            Object obj2 = c06511.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = c06511.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                SliderInputEvent.Touch touch = (SliderInputEvent.Touch) obj;
                boolean z = touch instanceof SliderInputEvent.Touch.End;
                float interpolation = 0.0f;
                Ref$ObjectRef ref$ObjectRef = this.$startPosition$inlined;
                FlowCollector flowCollector = this.$$this$flow;
                if (z) {
                    ref$ObjectRef.element = null;
                    VolumeDialogOverscrollViewModel.OverscrollEventModel.Animate animate = new VolumeDialogOverscrollViewModel.OverscrollEventModel.Animate(0.0f);
                    c06511.label = 1;
                } else {
                    Float f = (Float) ref$ObjectRef.element;
                    float y = touch.getY();
                    if (f == null) {
                        ref$ObjectRef.element = new Float(y);
                    } else {
                        float fFloatValue = y - f.floatValue();
                        VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel = this.this$0;
                        volumeDialogOverscrollViewModel.getClass();
                        float f2 = fFloatValue / 3.0f;
                        float f3 = this.$direction$inlined;
                        if (f3 >= 0.0f ? !(f3 <= 0.0f ? f2 != 0.0f : f2 <= 0.0f) : f2 < 0.0f) {
                            float fSignum = Math.signum(f2);
                            PathInterpolator pathInterpolator = volumeDialogOverscrollViewModel.offsetInterpolator;
                            float fAbs = Math.abs(f2);
                            float f4 = volumeDialogOverscrollViewModel.maxDeviation;
                            interpolation = pathInterpolator.getInterpolation(fAbs / f4) * f4 * fSignum;
                        }
                        VolumeDialogOverscrollViewModel.OverscrollEventModel.Move move = new VolumeDialogOverscrollViewModel.OverscrollEventModel.Move(interpolation);
                        c06511.label = 2;
                    }
                }
            } else {
                if (i2 != 1 && i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1(Flow flow, Continuation continuation, Ref$ObjectRef ref$ObjectRef, VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel, float f) {
        super(2, continuation);
        this.$this_transform = flow;
        this.$startPosition$inlined = ref$ObjectRef;
        this.this$0 = volumeDialogOverscrollViewModel;
        this.$direction$inlined = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1 volumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1 = new VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1(this.$this_transform, continuation, this.$startPosition$inlined, this.this$0, this.$direction$inlined);
        volumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1.L$0 = obj;
        return volumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogOverscrollViewModel$overscrollEvents$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.$startPosition$inlined, this.this$0, this.$direction$inlined);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
