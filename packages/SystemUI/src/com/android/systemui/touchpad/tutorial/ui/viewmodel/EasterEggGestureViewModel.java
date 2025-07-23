package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import android.view.MotionEvent;
import com.android.systemui.touchpad.tutorial.ui.gesture.TouchpadEventsFilterKt;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EasterEggGestureViewModel implements Consumer {
    public final BufferedChannel easterEggFinished;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 easterEggTriggered;
    public final EasterEggGestureViewModel$special$$inlined$filter$1 gestureDone;
    public final GestureRecognizerAdapter gestureRecognizer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Event {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class GestureFinished implements Event {
            public static final GestureFinished INSTANCE = new GestureFinished();

            private GestureFinished() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof GestureFinished);
            }

            public final int hashCode() {
                return -909786848;
            }

            public final String toString() {
                return "GestureFinished";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class StateRestarted implements Event {
            public static final StateRestarted INSTANCE = new StateRestarted();

            private StateRestarted() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof StateRestarted);
            }

            public final int hashCode() {
                return 425467000;
            }

            public final String toString() {
                return "StateRestarted";
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1, kotlinx.coroutines.flow.Flow] */
    public EasterEggGestureViewModel(GestureRecognizerAdapter gestureRecognizerAdapter) {
        this.gestureRecognizer = gestureRecognizerAdapter;
        final ChannelFlowTransformLatest channelFlowTransformLatest = gestureRecognizerAdapter.gestureState;
        final ?? r0 = new Flow() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.touchpad.tutorial.ui.gesture.GestureState r6 = (com.android.systemui.touchpad.tutorial.ui.gesture.GestureState) r6
                        com.android.systemui.touchpad.tutorial.ui.gesture.GestureState$Finished r2 = com.android.systemui.touchpad.tutorial.ui.gesture.GestureState.Finished.INSTANCE
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 == 0) goto L48
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.gestureDone = r0;
        BufferedChannel Channel$default = ChannelKt.Channel$default(0, null, null, 7);
        this.easterEggFinished = Channel$default;
        Flow flow = new Flow() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.touchpad.tutorial.ui.gesture.GestureState r5 = (com.android.systemui.touchpad.tutorial.ui.gesture.GestureState) r5
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$Event$GestureFinished r5 = com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel.Event.GestureFinished.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ChannelAsFlow receiveAsFlow = FlowKt.receiveAsFlow(Channel$default);
        final ChannelLimitedFlowMerge merge = FlowKt.merge(flow, new Flow() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$Event$StateRestarted r5 = com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel.Event.StateRestarted.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.easterEggTriggered = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new EasterEggGestureViewModel$easterEggTriggered$4(null), new Flow() { // from class: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$Event r5 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel.Event) r5
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$Event$GestureFinished r6 = com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel.Event.GestureFinished.INSTANCE
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r6 == 0) goto L3e
                        r5 = r3
                        goto L47
                    L3e:
                        com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$Event$StateRestarted r6 = com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel.Event.StateRestarted.INSTANCE
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r5 == 0) goto L59
                        r5 = 0
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L59:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        TouchpadEventsFilterKt.handleTouchpadMotionEvent(this.gestureRecognizer, (MotionEvent) obj);
    }
}
