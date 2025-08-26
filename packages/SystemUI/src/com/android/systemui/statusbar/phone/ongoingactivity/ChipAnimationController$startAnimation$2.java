package com.android.systemui.statusbar.phone.ongoingactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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

/* loaded from: classes3.dex */
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
        final class C05421 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ boolean $show;
            final /* synthetic */ View $v;
            int label;
            final /* synthetic */ ChipAnimationController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05421(ChipAnimationController chipAnimationController, View view, boolean z, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = chipAnimationController;
                this.$v = view;
                this.$show = z;
                this.$$this$coroutineScope = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C05421(this.this$0, this.$v, this.$show, this.$$this$coroutineScope, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05421) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
                    objectAnimatorOfFloat.setDuration(z ? 600L : 500L);
                    objectAnimatorOfFloat.setInterpolator(ChipAnimationController.AlPHA_INTERPOLATOR);
                    objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateAlpha$2$alphaAnimator$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            if (cancellableContinuationImpl.isActive()) {
                                ChipAnimationController chipAnimationController2 = chipAnimationController;
                                View view2 = view;
                                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                                chipAnimationController2.getClass();
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("animateAlpha() Cancelled! V:", ChipAnimationController.viewInfo(view2), "{ChipAnimationController}");
                                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                                int i2 = Result.$r8$clinit;
                                cancellableContinuation.resumeWith(Unit.INSTANCE);
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            if (cancellableContinuationImpl.isActive()) {
                                ChipAnimationController chipAnimationController2 = chipAnimationController;
                                View view2 = view;
                                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                                chipAnimationController2.getClass();
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("animateAlpha() End! V:", ChipAnimationController.viewInfo(view2), "{ChipAnimationController}");
                                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                                int i2 = Result.$r8$clinit;
                                cancellableContinuation.resumeWith(Unit.INSTANCE);
                                View view3 = chipAnimationController.clockView;
                                if (view3 != null) {
                                    view3.invalidate();
                                }
                                CoroutineScopeKt.cancel(coroutineScope, new AnimationCompleted("Alpha Animation ended!"));
                            }
                        }
                    });
                    cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateAlpha$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            objectAnimatorOfFloat.cancel();
                            return Unit.INSTANCE;
                        }
                    });
                    objectAnimatorOfFloat.start();
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            BuildersKt.launch$default(coroutineScope, null, null, new C05421(this.this$0, this.$v, this.$show, coroutineScope, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$v, this.$show, null), 3);
            return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, this.$v, this.$show, null), 3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$v, this.$show, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                if (this.$show) {
                    ChipAnimationController chipAnimationController2 = this.this$0;
                    View view = this.$v;
                    PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController2.showView(view);
                } else {
                    ChipAnimationController chipAnimationController3 = this.this$0;
                    View view2 = this.$v;
                    int i2 = this.$state;
                    PathInterpolator pathInterpolator2 = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController3.hideView(view2, i2);
                }
                chipAnimationController = this.this$0;
            } catch (CancellationException e) {
                ChipAnimationController chipAnimationController4 = this.this$0;
                View view3 = this.$v;
                PathInterpolator pathInterpolator3 = ChipAnimationController.AlPHA_INTERPOLATOR;
                chipAnimationController4.getClass();
                Log.d("{ChipAnimationController}", "View:" + ChipAnimationController.viewInfo(view3) + " Animations were cancelled: " + e.getMessage());
                if (this.$show) {
                    this.this$0.showView(this.$v);
                } else {
                    this.this$0.hideView(this.$v, this.$state);
                }
                chipAnimationController = this.this$0;
            }
            chipAnimationController.animationMap.remove(this.$v);
            return Unit.INSTANCE;
        } finally {
            if (this.$show) {
                ChipAnimationController chipAnimationController5 = this.this$0;
                View view4 = this.$v;
                PathInterpolator pathInterpolator4 = ChipAnimationController.AlPHA_INTERPOLATOR;
                chipAnimationController5.showView(view4);
            } else {
                ChipAnimationController chipAnimationController6 = this.this$0;
                View view5 = this.$v;
                int i3 = this.$state;
                PathInterpolator pathInterpolator5 = ChipAnimationController.AlPHA_INTERPOLATOR;
                chipAnimationController6.hideView(view5, i3);
            }
            this.this$0.animationMap.remove(this.$v);
        }
    }
}
