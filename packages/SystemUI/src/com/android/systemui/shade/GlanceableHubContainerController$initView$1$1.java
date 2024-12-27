package com.android.systemui.shade;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.communal.ui.compose.CommunalContainerKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

final class GlanceableHubContainerController$initView$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ComposeView $this_apply;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ GlanceableHubContainerController this$0;

    /* renamed from: com.android.systemui.shade.GlanceableHubContainerController$initView$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ ComposeView $this_apply;
        int label;
        final /* synthetic */ GlanceableHubContainerController this$0;

        /* renamed from: com.android.systemui.shade.GlanceableHubContainerController$initView$1$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01791 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ ComposeView $this_apply;
            int label;
            final /* synthetic */ GlanceableHubContainerController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01791(ComposeView composeView, LifecycleOwner lifecycleOwner, GlanceableHubContainerController glanceableHubContainerController, Continuation continuation) {
                super(2, continuation);
                this.$this_apply = composeView;
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.this$0 = glanceableHubContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01791(this.$this_apply, this.$$this$repeatWhenAttached, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01791) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ComposeView composeView = this.$this_apply;
                ViewTreeOnBackPressedDispatcherOwner.set(composeView, new OnBackPressedDispatcherOwner(this.$$this$repeatWhenAttached, composeView) { // from class: com.android.systemui.shade.GlanceableHubContainerController.initView.1.1.1.1.1
                    public final Lifecycle lifecycle;
                    public final OnBackPressedDispatcher onBackPressedDispatcher;

                    {
                        OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(null, 1, null);
                        onBackPressedDispatcher.setOnBackInvokedDispatcher(composeView.getViewRootImpl().getOnBackInvokedDispatcher());
                        this.onBackPressedDispatcher = onBackPressedDispatcher;
                        this.lifecycle = r4.getLifecycle();
                    }

                    @Override // androidx.lifecycle.LifecycleOwner
                    public final Lifecycle getLifecycle() {
                        return this.lifecycle;
                    }

                    @Override // androidx.activity.OnBackPressedDispatcherOwner
                    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
                        return this.onBackPressedDispatcher;
                    }
                });
                ComposeView composeView2 = this.$this_apply;
                final GlanceableHubContainerController glanceableHubContainerController = this.this$0;
                composeView2.setContent(new ComposableLambdaImpl(1456454857, true, new Function2() { // from class: com.android.systemui.shade.GlanceableHubContainerController.initView.1.1.1.1.2
                    {
                        super(2);
                    }

                    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.shade.GlanceableHubContainerController$initView$1$1$1$1$2$1, kotlin.jvm.internal.Lambda] */
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer = (Composer) obj2;
                        if ((((Number) obj3).intValue() & 11) == 2) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey = ComposerKt.invocation;
                        final GlanceableHubContainerController glanceableHubContainerController2 = GlanceableHubContainerController.this;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1323935507, composer, new Function2() { // from class: com.android.systemui.shade.GlanceableHubContainerController.initView.1.1.1.1.2.1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj4, Object obj5) {
                                Composer composer2 = (Composer) obj4;
                                if ((((Number) obj5).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                GlanceableHubContainerController glanceableHubContainerController3 = GlanceableHubContainerController.this;
                                CommunalContainerKt.CommunalContainer(null, glanceableHubContainerController3.communalViewModel, glanceableHubContainerController3.dataSourceDelegator, glanceableHubContainerController3.communalColors, glanceableHubContainerController3.communalContent, composer2, 33344, 1);
                                return Unit.INSTANCE;
                            }
                        }), composer, 48, 1);
                        return Unit.INSTANCE;
                    }
                }));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LifecycleOwner lifecycleOwner, ComposeView composeView, GlanceableHubContainerController glanceableHubContainerController, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$this_apply = composeView;
            this.this$0 = glanceableHubContainerController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$this_apply, this.this$0, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C01791 c01791 = new C01791(this.$this_apply, lifecycleOwner, this.this$0, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c01791, this) == coroutineSingletons) {
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
    public GlanceableHubContainerController$initView$1$1(ComposeView composeView, GlanceableHubContainerController glanceableHubContainerController, Continuation continuation) {
        super(3, continuation);
        this.$this_apply = composeView;
        this.this$0 = glanceableHubContainerController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        GlanceableHubContainerController$initView$1$1 glanceableHubContainerController$initView$1$1 = new GlanceableHubContainerController$initView$1$1(this.$this_apply, this.this$0, (Continuation) obj3);
        glanceableHubContainerController$initView$1$1.L$0 = (LifecycleOwner) obj;
        return glanceableHubContainerController$initView$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$this_apply, this.this$0, null), 3);
        return Unit.INSTANCE;
    }
}
