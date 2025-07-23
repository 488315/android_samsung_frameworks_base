package com.android.systemui.blur;

import android.animation.ValueAnimator;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import com.android.systemui.blur.di.SecPanelBackgroundBinding;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.blur.di.SecPanelCapturedBlurBinding;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurBitmapGenerator;
import com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder;
import com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.phone.SecPanelBackground;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecQpBlurController implements PanelScreenShotLogger.LogProvider {
    public static final String TAG;
    public float animatedFraction;
    public final Choreographer choreographer;
    public final boolean isBlurAnimatorRunning;
    public boolean isBouncerShowing;
    public boolean isMirrorVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final Lazy lazyUnlockedScreenOffAnimationController;
    public final SecPanelBackgroundBinding panelBackgroundBinding;
    public final SecPanelBlurBinding panelBlurBinding;
    public float panelExpandedFraction;
    public final SecCapturedBlurBitmapGenerator secCapturedBlurBitmapGenerator;
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;
    public final Function1 updateBlurCallback = new SecQpBlurController$updateBlurCallback$1(this);
    public ValueAnimator blurAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.blur.SecQpBlurController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1, reason: invalid class name and collision with other inner class name */
        final class C00501 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                /* synthetic */ float F$0;
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                    anonymousClass2.F$0 = ((Number) obj).floatValue();
                    return anonymousClass2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    float f = this.F$0;
                    SecQpBlurController secQpBlurController = this.this$0;
                    if (secQpBlurController.panelExpandedFraction != f) {
                        secQpBlurController.panelExpandedFraction = f;
                        Log.d(SecQpBlurController.TAG, "setTransitionToFullShadeAmount : " + f);
                        SecQpBlurController secQpBlurController2 = this.this$0;
                        secQpBlurController2.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController2.updateBlurCallback));
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00501(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00501(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00501) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final SecQpBlurController secQpBlurController = this.this$0;
                    final StateFlowImpl stateFlowImpl = secQpBlurController.secPanelExpansionStateInteractor.lockscreenShadeFraction;
                    Flow flow = new Flow() { // from class: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ SecQpBlurController this$0;

                            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, SecQpBlurController secQpBlurController) {
                                this.$this_unsafeFlow = flowCollector;
                                this.this$0 = secQpBlurController;
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
                                    boolean r0 = r6 instanceof com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L4e
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    r6 = r5
                                    java.lang.Number r6 = (java.lang.Number) r6
                                    r6.floatValue()
                                    com.android.systemui.blur.SecQpBlurController r6 = r4.this$0
                                    com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor r6 = r6.secPanelExpansionStateInteractor
                                    int r6 = r6.getstatusBarState()
                                    if (r6 == r3) goto L43
                                    goto L4e
                                L43:
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L4e
                                    return r1
                                L4e:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, secQpBlurController), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, anonymousClass2, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2$2, reason: invalid class name and collision with other inner class name */
            final class C00512 extends SuspendLambda implements Function2 {
                /* synthetic */ float F$0;
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00512(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00512 c00512 = new C00512(this.this$0, continuation);
                    c00512.F$0 = ((Number) obj).floatValue();
                    return c00512;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00512) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    float f = this.F$0;
                    SecQpBlurController secQpBlurController = this.this$0;
                    if (secQpBlurController.panelExpandedFraction != f) {
                        secQpBlurController.panelExpandedFraction = f;
                        Log.d(SecQpBlurController.TAG, "onPanelExpansionChanged : " + f);
                        SecQpBlurController.access$doFrame(this.this$0);
                    }
                    SecQpBlurController secQpBlurController2 = this.this$0;
                    secQpBlurController2.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController2.updateBlurCallback));
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final SecQpBlurController secQpBlurController = this.this$0;
                    final StateFlowImpl stateFlowImpl = secQpBlurController.secPanelExpansionStateInteractor.shadeFraction;
                    Flow flow = new Flow() { // from class: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ SecQpBlurController this$0;

                            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, SecQpBlurController secQpBlurController) {
                                this.$this_unsafeFlow = flowCollector;
                                this.this$0 = secQpBlurController;
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
                                    boolean r0 = r6 instanceof com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L4e
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    r6 = r5
                                    java.lang.Number r6 = (java.lang.Number) r6
                                    r6.floatValue()
                                    com.android.systemui.blur.SecQpBlurController r6 = r4.this$0
                                    com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor r6 = r6.secPanelExpansionStateInteractor
                                    int r6 = r6.getstatusBarState()
                                    if (r6 != r3) goto L43
                                    goto L4e
                                L43:
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L4e
                                    return r1
                                L4e:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, secQpBlurController), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    C00512 c00512 = new C00512(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, c00512, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$3$1, reason: invalid class name and collision with other inner class name */
            final class C00521 extends SuspendLambda implements Function2 {
                /* synthetic */ int I$0;
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00521(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00521 c00521 = new C00521(this.this$0, continuation);
                    c00521.I$0 = ((Number) obj).intValue();
                    return c00521;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00521) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    int i = this.I$0;
                    SecQpBlurController secQpBlurController = this.this$0;
                    secQpBlurController.panelExpandedFraction = i != 1 ? i != 2 ? ((Number) secQpBlurController.secPanelExpansionStateInteractor.shadeFraction.getValue()).floatValue() : 1.0f : 0.0f;
                    Log.d(SecQpBlurController.TAG, "statusBarState : " + StatusBarState.toString(i) + " , panelExpandedFraction : " + this.this$0.panelExpandedFraction);
                    SecQpBlurController secQpBlurController2 = this.this$0;
                    secQpBlurController2.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController2.updateBlurCallback));
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    SecQpBlurController secQpBlurController = this.this$0;
                    StateFlowImpl stateFlowImpl = secQpBlurController.secPanelExpansionStateInteractor.statusBarState;
                    C00521 c00521 = new C00521(secQpBlurController, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(stateFlowImpl, c00521, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SecQpBlurController.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new C00501(SecQpBlurController.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(SecQpBlurController.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(SecQpBlurController.this, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(SecQpBlurController.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public SecQpBlurController(CoroutineScope coroutineScope, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, SecPanelBackgroundBinding secPanelBackgroundBinding, KeyguardInteractor keyguardInteractor, Choreographer choreographer, Lazy lazy, SecPanelBlurBinding secPanelBlurBinding, SecCapturedBlurBitmapGenerator secCapturedBlurBitmapGenerator) {
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.panelBackgroundBinding = secPanelBackgroundBinding;
        this.keyguardInteractor = keyguardInteractor;
        this.choreographer = choreographer;
        this.lazyUnlockedScreenOffAnimationController = lazy;
        this.panelBlurBinding = secPanelBlurBinding;
        this.secCapturedBlurBitmapGenerator = secCapturedBlurBitmapGenerator;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        PanelScreenShotLogger.INSTANCE.addLogProvider(TAG, this);
        this.isBlurAnimatorRunning = this.blurAnimator.isRunning();
    }

    public static final void access$doFrame(SecQpBlurController secQpBlurController) {
        float interpolation = ((UnlockedScreenOffAnimationController) secQpBlurController.lazyUnlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying ? 0.0f : secQpBlurController.isMirrorVisible ? secQpBlurController.animatedFraction : (secQpBlurController.isBouncerShowing && secQpBlurController.panelExpandedFraction == 1.0f) ? 1.0f : secQpBlurController.panelBlurBinding.getInterpolation(secQpBlurController.panelExpandedFraction);
        secQpBlurController.doBlur(interpolation, SecPanelBlurBinding.BlurType.QUICK_PANEL);
        SecPanelBackgroundBinder secPanelBackgroundBinder = (SecPanelBackgroundBinder) secQpBlurController.panelBackgroundBinding;
        if (secPanelBackgroundBinder.view.getVisibility() != 0 || ((UnlockedScreenOffAnimationController) secQpBlurController.lazyUnlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying) {
            return;
        }
        boolean isTablet = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
        SecPanelBackground secPanelBackground = secPanelBackgroundBinder.view;
        NotificationShadeWindowView notificationShadeWindowView = secPanelBackgroundBinder.shadeWindowView;
        if (isTablet) {
            SecPanelSplitHelper.Companion companion = SecPanelSplitHelper.Companion;
            companion.getClass();
            float f = interpolation - (SecPanelSplitHelper.isEnabled ? 0.5f : 0.0f);
            float f2 = 1;
            companion.getClass();
            float constrain = MathUtils.constrain(f / (f2 - (!SecPanelSplitHelper.isEnabled ? 0.0f : 0.5f)), 0.0f, 1.0f);
            SecPanelBackground secPanelBackground2 = (SecPanelBackground) notificationShadeWindowView.findViewById(R.id.qs_new_blur_background);
            if (secPanelBackground2 != null) {
                secPanelBackground2.setAlpha(secPanelBackgroundBinder.getMaxAlpha() * constrain);
            }
            secPanelBackground.setAlpha(0.0f);
        } else {
            secPanelBackground.setAlpha(secPanelBackgroundBinder.getMaxAlpha() * interpolation);
            SecQSNewBlurView secQSNewBlurView = (SecQSNewBlurView) notificationShadeWindowView.findViewById(R.id.qs_new_blur);
            if (secQSNewBlurView != null) {
                secQSNewBlurView.setAlpha(0.0f);
            }
        }
        Log.d(SecPanelBackgroundBinder.TAG, "setAlpha = " + (secPanelBackgroundBinder.getMaxAlpha() * interpolation));
    }

    public final void doBlur(float f, SecPanelBlurBinding.BlurType blurType) {
        SecPanelBlurBinding secPanelBlurBinding = this.panelBlurBinding;
        secPanelBlurBinding.setFraction(f);
        secPanelBlurBinding.doBlur(blurType);
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(TAG + " =================================================================================== ");
        arrayList.add("  mIsMirrorVisible = " + this.isMirrorVisible + " mIsBouncerShowing = " + this.isBouncerShowing);
        arrayList.add("  animatedFraction = " + this.animatedFraction + " panelExpandedFraction = " + this.panelExpandedFraction);
        arrayList.add("======================================================================================================= ");
        return arrayList;
    }

    public final void makeAnimationAndRun(float f, float f2, int i) {
        if (this.isBlurAnimatorRunning) {
            this.blurAnimator.cancel();
            Log.d(TAG, "Cancel Blur Animator");
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        this.blurAnimator = ofFloat;
        ofFloat.setDuration(i);
        this.blurAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.blur.SecQpBlurController$makeAnimationAndRun$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SecQpBlurController.this.animatedFraction = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SecQpBlurController secQpBlurController = SecQpBlurController.this;
                secQpBlurController.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController.updateBlurCallback));
            }
        });
        this.blurAnimator.start();
    }

    public final void setBrightnessMirrorVisible(boolean z) {
        Log.d(TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setBrightnessMirrorVisible: ", z));
        this.isMirrorVisible = z;
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            ((SecCapturedBlurRepositoryImpl) ((SecCapturedBlurContainerBinder) ((SecPanelCapturedBlurBinding) this.panelBlurBinding)).secCapturedBlurInteractor.secCapturedBlurRepository)._mirrorShowing.updateState(null, Boolean.valueOf(z));
        }
        if (z) {
            makeAnimationAndRun(1.0f, 0.0f, 150);
        } else {
            makeAnimationAndRun(0.0f, 1.0f, 200);
        }
    }
}
