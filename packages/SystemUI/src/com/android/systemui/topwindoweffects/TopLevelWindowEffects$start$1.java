package com.android.systemui.topwindoweffects;

import android.content.Context;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.systemui.topwindoweffects.ui.compose.EffectsWindowRoot;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class TopLevelWindowEffects$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ TopLevelWindowEffects this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.topwindoweffects.TopLevelWindowEffects$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$ObjectRef<EffectsWindowRoot> $root;
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ TopLevelWindowEffects this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.topwindoweffects.TopLevelWindowEffects$start$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03941 extends SuspendLambda implements Function2 {
            final /* synthetic */ Ref$ObjectRef<EffectsWindowRoot> $root;
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ TopLevelWindowEffects this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03941(Ref$ObjectRef<EffectsWindowRoot> ref$ObjectRef, TopLevelWindowEffects topLevelWindowEffects, Continuation continuation) {
                super(2, continuation);
                this.$root = ref$ObjectRef;
                this.this$0 = topLevelWindowEffects;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03941 c03941 = new C03941(this.$root, this.this$0, continuation);
                c03941.Z$0 = ((Boolean) obj).booleanValue();
                return c03941;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((C03941) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference failed for: r2v0, types: [T, com.android.systemui.topwindoweffects.ui.compose.EffectsWindowRoot] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (this.Z$0) {
                    Ref$ObjectRef<EffectsWindowRoot> ref$ObjectRef = this.$root;
                    if (ref$ObjectRef.element == null) {
                        TopLevelWindowEffects topLevelWindowEffects = this.this$0;
                        Context context = topLevelWindowEffects.context;
                        final Ref$ObjectRef<EffectsWindowRoot> ref$ObjectRef2 = this.$root;
                        final TopLevelWindowEffects topLevelWindowEffects2 = this.this$0;
                        ref$ObjectRef.element = new EffectsWindowRoot(context, new Function0() { // from class: com.android.systemui.topwindoweffects.TopLevelWindowEffects$start$1$1$1$$ExternalSyntheticLambda0
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Ref$ObjectRef ref$ObjectRef3 = Ref$ObjectRef.this;
                                EffectsWindowRoot effectsWindowRoot = (EffectsWindowRoot) ref$ObjectRef3.element;
                                if (effectsWindowRoot != null && effectsWindowRoot.isAttachedToWindow()) {
                                    topLevelWindowEffects2.windowManager.removeView((View) ref$ObjectRef3.element);
                                    ref$ObjectRef3.element = null;
                                }
                                return Unit.INSTANCE;
                            }
                        }, topLevelWindowEffects.viewModelFactory);
                        EffectsWindowRoot effectsWindowRoot = this.$root.element;
                        if (effectsWindowRoot != null) {
                            WindowManager windowManager = this.this$0.windowManager;
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2015, 312, -2);
                            layoutParams.privateFlags |= 536873042;
                            layoutParams.layoutInDisplayCutoutMode = 3;
                            layoutParams.setTitle("TopLevelWindowEffects");
                            layoutParams.setFitInsetsTypes(WindowInsets.Type.systemOverlays());
                            layoutParams.gravity = 48;
                            windowManager.addView(effectsWindowRoot, layoutParams);
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TopLevelWindowEffects topLevelWindowEffects, Ref$ObjectRef<EffectsWindowRoot> ref$ObjectRef, Continuation continuation) {
            super(2, continuation);
            this.this$0 = topLevelWindowEffects;
            this.$root = ref$ObjectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$root, continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.Z$0) {
                    TopLevelWindowEffects topLevelWindowEffects = this.this$0;
                    Flow flow = topLevelWindowEffects.keyEventInteractor.isPowerButtonDown;
                    C03941 c03941 = new C03941(this.$root, topLevelWindowEffects, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, c03941, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
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
    public TopLevelWindowEffects$start$1(TopLevelWindowEffects topLevelWindowEffects, Continuation continuation) {
        super(2, continuation);
        this.this$0 = topLevelWindowEffects;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TopLevelWindowEffects$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TopLevelWindowEffects$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            TopLevelWindowEffects topLevelWindowEffects = this.this$0;
            Flow flow = topLevelWindowEffects.squeezeEffectInteractor.isSqueezeEffectEnabled;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(topLevelWindowEffects, ref$ObjectRef, null);
            this.label = 1;
            if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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
