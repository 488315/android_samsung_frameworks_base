package com.android.systemui.brightness.ui.compose;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.MutableState;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class BrightnessSliderKt$BrightnessSlider$6$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $iconActiveAlphaAnimatable;
    final /* synthetic */ Animatable<Float, AnimationVector1D> $iconInactiveAlphaAnimatable;
    final /* synthetic */ MutableState<Boolean> $showIconActive$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable<Float, AnimationVector1D> $iconActiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconActiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$iconActiveAlphaAnimatable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable<Float, AnimationVector1D> animatable = this.$iconActiveAlphaAnimatable;
                this.label = 1;
                Float f = new Float(1.0f);
                AnimationSpecs.INSTANCE.getClass();
                if (Animatable.animateTo$default(animatable, f, AnimationSpecs.IconAppearSpec, null, null, this, 12) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$1$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable<Float, AnimationVector1D> $iconInactiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconInactiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$iconInactiveAlphaAnimatable, continuation);
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
                Animatable<Float, AnimationVector1D> animatable = this.$iconInactiveAlphaAnimatable;
                this.label = 1;
                Float f = new Float(0.0f);
                AnimationSpecs.INSTANCE.getClass();
                if (Animatable.animateTo$default(animatable, f, AnimationSpecs.IconDisappearSpec, null, null, this, 12) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$1$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable<Float, AnimationVector1D> $iconActiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconActiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$iconActiveAlphaAnimatable, continuation);
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
                Animatable<Float, AnimationVector1D> animatable = this.$iconActiveAlphaAnimatable;
                this.label = 1;
                Float f = new Float(0.0f);
                AnimationSpecs.INSTANCE.getClass();
                if (Animatable.animateTo$default(animatable, f, AnimationSpecs.IconDisappearSpec, null, null, this, 12) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.brightness.ui.compose.BrightnessSliderKt$BrightnessSlider$6$1$1$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable<Float, AnimationVector1D> $iconInactiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconInactiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.$iconInactiveAlphaAnimatable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable<Float, AnimationVector1D> animatable = this.$iconInactiveAlphaAnimatable;
                this.label = 1;
                Float f = new Float(1.0f);
                AnimationSpecs.INSTANCE.getClass();
                if (Animatable.animateTo$default(animatable, f, AnimationSpecs.IconAppearSpec, null, null, this, 12) == coroutineSingletons) {
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
    public BrightnessSliderKt$BrightnessSlider$6$1$1(MutableState<Boolean> mutableState, Animatable<Float, AnimationVector1D> animatable, Animatable<Float, AnimationVector1D> animatable2, Continuation continuation) {
        super(2, continuation);
        this.$showIconActive$delegate = mutableState;
        this.$iconActiveAlphaAnimatable = animatable;
        this.$iconInactiveAlphaAnimatable = animatable2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BrightnessSliderKt$BrightnessSlider$6$1$1 brightnessSliderKt$BrightnessSlider$6$1$1 = new BrightnessSliderKt$BrightnessSlider$6$1$1(this.$showIconActive$delegate, this.$iconActiveAlphaAnimatable, this.$iconInactiveAlphaAnimatable, continuation);
        brightnessSliderKt$BrightnessSlider$6$1$1.L$0 = obj;
        return brightnessSliderKt$BrightnessSlider$6$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BrightnessSliderKt$BrightnessSlider$6$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        if (((Boolean) this.$showIconActive$delegate.getValue()).booleanValue()) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.$iconActiveAlphaAnimatable, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$iconInactiveAlphaAnimatable, null), 7);
        } else {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$iconActiveAlphaAnimatable, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$iconInactiveAlphaAnimatable, null), 7);
        }
        return Unit.INSTANCE;
    }
}
