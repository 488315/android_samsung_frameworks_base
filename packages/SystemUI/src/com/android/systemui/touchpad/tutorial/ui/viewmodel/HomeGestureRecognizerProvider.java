package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.touchpad.tutorial.ui.gesture.VelocityTracker;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HomeGestureRecognizerProvider implements GestureRecognizerProvider {
    public final HomeGestureRecognizerProvider$special$$inlined$map$1 recognizer;
    public final VelocityTracker velocityTracker;

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1] */
    public HomeGestureRecognizerProvider(TouchpadGestureResources touchpadGestureResources, VelocityTracker velocityTracker) {
        this.velocityTracker = velocityTracker;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(touchpadGestureResources.distanceThreshold(), FlowKt.distinctUntilChanged(new TouchpadGestureResources$velocityThreshold$$inlined$map$1(((ConfigurationInteractorImpl) touchpadGestureResources.configurationInteractor).onAnyConfigurationChange, touchpadGestureResources, R.dimen.touchpad_home_gesture_velocity_threshold)), new HomeGestureRecognizerProvider$recognizer$1(null));
        this.recognizer = new Flow() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ HomeGestureRecognizerProvider this$0;

                /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, HomeGestureRecognizerProvider homeGestureRecognizerProvider) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = homeGestureRecognizerProvider;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1$2$1 r0 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1$2$1 r0 = new com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5c
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Pair r6 = (kotlin.Pair) r6
                        java.lang.Object r7 = r6.component1()
                        java.lang.Number r7 = (java.lang.Number) r7
                        int r7 = r7.intValue()
                        java.lang.Object r6 = r6.component2()
                        java.lang.Number r6 = (java.lang.Number) r6
                        float r6 = r6.floatValue()
                        com.android.systemui.touchpad.tutorial.ui.gesture.HomeGestureRecognizer r2 = new com.android.systemui.touchpad.tutorial.ui.gesture.HomeGestureRecognizer
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider r4 = r5.this$0
                        com.android.systemui.touchpad.tutorial.ui.gesture.VelocityTracker r4 = r4.velocityTracker
                        r2.<init>(r7, r6, r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r2, r0)
                        if (r5 != r1) goto L5c
                        return r1
                    L5c:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureRecognizerProvider$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerProvider
    public final Flow getRecognizer() {
        return this.recognizer;
    }
}
