package com.android.systemui.statusbar.phone.ongoingactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

final class ChipAnimationController$startAnimation$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $show;
    final /* synthetic */ int $state;
    final /* synthetic */ View $v;
    int label;
    final /* synthetic */ ChipAnimationController this$0;

    /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startAnimation$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $show;
        final /* synthetic */ View $v;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ChipAnimationController this$0;

        /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startAnimation$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C02101 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ boolean $show;
            final /* synthetic */ View $v;
            int label;
            final /* synthetic */ ChipAnimationController this$0;

            public C02101(ChipAnimationController chipAnimationController, View view, boolean z, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = chipAnimationController;
                this.$v = view;
                this.$show = z;
                this.$$this$coroutineScope = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02101(this.this$0, this.$v, this.$show, this.$$this$coroutineScope, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final ChipAnimationController chipAnimationController = this.this$0;
                    final View view = this.$v;
                    boolean z = this.$show;
                    final CoroutineScope coroutineScope = this.$$this$coroutineScope;
                    this.label = 1;
                    chipAnimationController.getClass();
                    final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                    cancellableContinuationImpl.initCancellability();
                    view.setAlpha(z ? 0.0f : 1.0f);
                    final ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
                    ofFloat.setDuration(z ? 500L : 200L);
                    ofFloat.setInterpolator(new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f));
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateAlpha$2$alphaAnimator$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            if (CancellableContinuation.this.isActive()) {
                                ChipAnimationController chipAnimationController2 = chipAnimationController;
                                View view2 = view;
                                int i2 = ChipAnimationController.$r8$clinit;
                                chipAnimationController2.getClass();
                                Log.d("{ChipAnimationController}", "animateAlpha() Cancelled! V:".concat(ChipAnimationController.viewInfo(view2)));
                                CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                                int i3 = Result.$r8$clinit;
                                cancellableContinuation.resumeWith(Unit.INSTANCE);
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            if (CancellableContinuation.this.isActive()) {
                                ChipAnimationController chipAnimationController2 = chipAnimationController;
                                View view2 = view;
                                int i2 = ChipAnimationController.$r8$clinit;
                                chipAnimationController2.getClass();
                                Log.d("{ChipAnimationController}", "animateAlpha() End! V:".concat(ChipAnimationController.viewInfo(view2)));
                                CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                                int i3 = Result.$r8$clinit;
                                cancellableContinuation.resumeWith(Unit.INSTANCE);
                                CoroutineScopeKt.cancel(coroutineScope, new AnimationCompleted("Alpha Animation ended!"));
                            }
                        }
                    });
                    cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateAlpha$2$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            ofFloat.cancel();
                            return Unit.INSTANCE;
                        }
                    });
                    ofFloat.start();
                    Object result = cancellableContinuationImpl.getResult();
                    if (result != coroutineSingletons) {
                        result = Unit.INSTANCE;
                    }
                    if (result == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startAnimation$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $show;
            final /* synthetic */ View $v;
            int label;
            final /* synthetic */ ChipAnimationController this$0;

            public AnonymousClass2(ChipAnimationController chipAnimationController, View view, boolean z, Continuation continuation) {
                super(2, continuation);
                this.this$0 = chipAnimationController;
                this.$v = view;
                this.$show = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, this.$v, this.$show, continuation);
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
                    ChipAnimationController chipAnimationController = this.this$0;
                    View view = this.$v;
                    DynamicAnimation.ViewProperty viewProperty = DynamicAnimation.SCALE_X;
                    float f = this.$show ? 1.0f : 0.0f;
                    this.label = 1;
                    if (ChipAnimationController.access$animateSpring(chipAnimationController, view, viewProperty, f, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startAnimation$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $show;
            final /* synthetic */ View $v;
            int label;
            final /* synthetic */ ChipAnimationController this$0;

            public AnonymousClass3(ChipAnimationController chipAnimationController, View view, boolean z, Continuation continuation) {
                super(2, continuation);
                this.this$0 = chipAnimationController;
                this.$v = view;
                this.$show = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, this.$v, this.$show, continuation);
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
                    ChipAnimationController chipAnimationController = this.this$0;
                    View view = this.$v;
                    DynamicAnimation.ViewProperty viewProperty = DynamicAnimation.SCALE_Y;
                    float f = this.$show ? 1.0f : 0.0f;
                    this.label = 1;
                    if (ChipAnimationController.access$animateSpring(chipAnimationController, view, viewProperty, f, this) == coroutineSingletons) {
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

        public AnonymousClass1(ChipAnimationController chipAnimationController, View view, boolean z, Continuation continuation) {
            super(2, continuation);
            this.this$0 = chipAnimationController;
            this.$v = view;
            this.$show = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$v, this.$show, continuation);
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
            BuildersKt.launch$default(coroutineScope, null, null, new C02101(this.this$0, this.$v, this.$show, coroutineScope, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$v, this.$show, null), 3);
            return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, this.$v, this.$show, null), 3);
        }
    }

    public ChipAnimationController$startAnimation$2(ChipAnimationController chipAnimationController, View view, boolean z, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = chipAnimationController;
        this.$v = view;
        this.$show = z;
        this.$state = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ChipAnimationController$startAnimation$2(this.this$0, this.$v, this.$show, this.$state, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChipAnimationController$startAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ChipAnimationController chipAnimationController;
        View view;
        int i;
        ChipAnimationController chipAnimationController2;
        View view2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        Object obj2 = null;
        try {
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$v, this.$show, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (CancellationException e) {
                ChipAnimationController chipAnimationController3 = this.this$0;
                View view3 = this.$v;
                int i3 = ChipAnimationController.$r8$clinit;
                chipAnimationController3.getClass();
                Log.d("{ChipAnimationController}", "View:" + ChipAnimationController.viewInfo(view3) + " Animations were cancelled: " + e.getMessage());
                if (this.$show) {
                    if (e instanceof AnimationCompleted) {
                        ChipAnimationController.access$nudgeClockIfNeeded(this.this$0, this.$v);
                    }
                    chipAnimationController2 = this.this$0;
                    view2 = this.$v;
                } else {
                    chipAnimationController = this.this$0;
                    view = this.$v;
                    i = this.$state;
                }
            }
            if (this.$show) {
                chipAnimationController2 = this.this$0;
                view2 = this.$v;
                int i4 = ChipAnimationController.$r8$clinit;
                chipAnimationController2.showView(view2);
                return Unit.INSTANCE;
            }
            chipAnimationController = this.this$0;
            view = this.$v;
            i = this.$state;
            int i5 = ChipAnimationController.$r8$clinit;
            chipAnimationController.hideView(view, i);
            return Unit.INSTANCE;
        } finally {
            if (this.$show) {
                if (obj2 instanceof AnimationCompleted) {
                    ChipAnimationController.access$nudgeClockIfNeeded(this.this$0, this.$v);
                }
                ChipAnimationController chipAnimationController4 = this.this$0;
                View view4 = this.$v;
                int i6 = ChipAnimationController.$r8$clinit;
                chipAnimationController4.showView(view4);
            } else {
                ChipAnimationController chipAnimationController5 = this.this$0;
                View view5 = this.$v;
                int i7 = this.$state;
                int i8 = ChipAnimationController.$r8$clinit;
                chipAnimationController5.hideView(view5, i7);
            }
        }
    }
}
