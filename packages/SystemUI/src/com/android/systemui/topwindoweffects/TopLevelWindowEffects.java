package com.android.systemui.topwindoweffects;

import android.content.Context;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyevent.domain.interactor.KeyEventInteractor;
import com.android.systemui.topwindoweffects.domain.interactor.SqueezeEffectInteractor;
import com.android.systemui.topwindoweffects.ui.compose.EffectsWindowRoot;
import com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class TopLevelWindowEffects implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final Context context;
    public final KeyEventInteractor keyEventInteractor;
    public final SqueezeEffectInteractor squeezeEffectInteractor;
    public final SqueezeEffectViewModel.Factory viewModelFactory;
    public final WindowManager windowManager;

    /* renamed from: com.android.systemui.topwindoweffects.TopLevelWindowEffects$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.topwindoweffects.TopLevelWindowEffects$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C06041 extends SuspendLambda implements Function2 {
            final /* synthetic */ Ref$ObjectRef<EffectsWindowRoot> $root;
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ TopLevelWindowEffects this$0;

            /* renamed from: com.android.systemui.topwindoweffects.TopLevelWindowEffects$start$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C06051 extends SuspendLambda implements Function2 {
                final /* synthetic */ Ref$ObjectRef<EffectsWindowRoot> $root;
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ TopLevelWindowEffects this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C06051(Ref$ObjectRef<EffectsWindowRoot> ref$ObjectRef, TopLevelWindowEffects topLevelWindowEffects, Continuation continuation) {
                    super(2, continuation);
                    this.$root = ref$ObjectRef;
                    this.this$0 = topLevelWindowEffects;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C06051 c06051 = new C06051(this.$root, this.this$0, continuation);
                    c06051.Z$0 = ((Boolean) obj).booleanValue();
                    return c06051;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return ((C06051) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                    Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef2;
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
            public C06041(TopLevelWindowEffects topLevelWindowEffects, Ref$ObjectRef<EffectsWindowRoot> ref$ObjectRef, Continuation continuation) {
                super(2, continuation);
                this.this$0 = topLevelWindowEffects;
                this.$root = ref$ObjectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06041 c06041 = new C06041(this.this$0, this.$root, continuation);
                c06041.Z$0 = ((Boolean) obj).booleanValue();
                return c06041;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((C06041) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                        C06051 c06051 = new C06051(this.$root, topLevelWindowEffects, null);
                        this.label = 1;
                        if (FlowKt.collectLatest(flow, c06051, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TopLevelWindowEffects.this.new AnonymousClass1(continuation);
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
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                TopLevelWindowEffects topLevelWindowEffects = TopLevelWindowEffects.this;
                Flow flow = topLevelWindowEffects.squeezeEffectInteractor.isSqueezeEffectEnabled;
                C06041 c06041 = new C06041(topLevelWindowEffects, ref$ObjectRef, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, c06041, this) == coroutineSingletons) {
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

    public TopLevelWindowEffects(Context context, CoroutineScope coroutineScope, WindowManager windowManager, SqueezeEffectInteractor squeezeEffectInteractor, KeyEventInteractor keyEventInteractor, SqueezeEffectViewModel.Factory factory) {
        this.context = context;
        this.applicationScope = coroutineScope;
        this.windowManager = windowManager;
        this.squeezeEffectInteractor = squeezeEffectInteractor;
        this.keyEventInteractor = keyEventInteractor;
        this.viewModelFactory = factory;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.applicationScope, null, null, new AnonymousClass1(null), 3);
    }
}
