package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.platform.ViewCompositionStrategy;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelKairos;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StackedMobileIconBinder$bind$2 extends SuspendLambda implements Function4 {
    final /* synthetic */ KairosNetwork $kairosNetwork;
    final /* synthetic */ StackedMobileIconViewModelKairos.Factory $kairosViewModelFactory;
    final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
    final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;
    /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ KairosNetwork $kairosNetwork;
        final /* synthetic */ StackedMobileIconViewModelKairos.Factory $kairosViewModelFactory;
        final /* synthetic */ StateFlow $tintFlow;
        final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
        final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C03641 extends SuspendLambda implements Function2 {
            final /* synthetic */ KairosNetwork $kairosNetwork;
            final /* synthetic */ StackedMobileIconViewModelKairos.Factory $kairosViewModelFactory;
            final /* synthetic */ StateFlow $tintFlow;
            final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
            final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03641(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, KairosNetwork kairosNetwork, StackedMobileIconViewModelKairos.Factory factory, StackedMobileIconViewModelImpl.Factory factory2, StateFlow stateFlow, Continuation continuation) {
                super(2, continuation);
                this.$view = singleBindableStatusBarComposeIconView;
                this.$kairosNetwork = kairosNetwork;
                this.$kairosViewModelFactory = factory;
                this.$viewModelFactory = factory2;
                this.$tintFlow = stateFlow;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03641(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, this.$tintFlow, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03641) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ComposeView composeView = this.$view.composeView;
                if (composeView == null) {
                    composeView = null;
                }
                final KairosNetwork kairosNetwork = this.$kairosNetwork;
                final StackedMobileIconViewModelKairos.Factory factory = this.$kairosViewModelFactory;
                final StackedMobileIconViewModelImpl.Factory factory2 = this.$viewModelFactory;
                final StateFlow stateFlow = this.$tintFlow;
                composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed.INSTANCE);
                composeView.setContent(new ComposableLambdaImpl(1810854367, true, new Function2(kairosNetwork, factory, factory2, stateFlow) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1$1$1
                    public final /* synthetic */ StateFlow $tintFlow;
                    public final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;

                    {
                        this.$viewModelFactory = factory2;
                        this.$tintFlow = stateFlow;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:15:0x0049, code lost:
                    
                        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                     */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8) {
                        /*
                            r6 = this;
                            androidx.compose.runtime.Composer r7 = (androidx.compose.runtime.Composer) r7
                            java.lang.Number r8 = (java.lang.Number) r8
                            int r8 = r8.intValue()
                            r8 = r8 & 3
                            r0 = 2
                            if (r8 != r0) goto L1c
                            r8 = r7
                            androidx.compose.runtime.ComposerImpl r8 = (androidx.compose.runtime.ComposerImpl) r8
                            boolean r0 = r8.getSkipping()
                            if (r0 != 0) goto L17
                            goto L1c
                        L17:
                            r8.skipToGroupEnd()
                            goto Laf
                        L1c:
                            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r8 == 0) goto L27
                            java.lang.String r8 = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder.bind.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (StackedMobileIconBinder.kt:60)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r8)
                        L27:
                            r3 = r7
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r8 = -450896716(0xffffffffe51fdcb4, float:-4.718297E22)
                            r3.startReplaceGroup(r8)
                            r8 = 1509478137(0x59f8cef9, float:8.7541704E15)
                            r3.startReplaceGroup(r8)
                            com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$Factory r8 = r6.$viewModelFactory
                            boolean r0 = r3.changedInstance(r8)
                            java.lang.Object r1 = r3.rememberedValue()
                            if (r0 != 0) goto L4b
                            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                            r0.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r1 != r0) goto L54
                        L4b:
                            com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$$ExternalSyntheticLambda0 r1 = new com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$$ExternalSyntheticLambda0
                            r0 = 1
                            r1.<init>(r8, r0)
                            r3.updateRememberedValue(r1)
                        L54:
                            r2 = r1
                            kotlin.jvm.functions.Function0 r2 = (kotlin.jvm.functions.Function0) r2
                            r8 = 0
                            r3.end(r8)
                            r4 = 6
                            r5 = 2
                            java.lang.String r0 = "StackedMobileIconBinder"
                            r1 = 0
                            java.lang.Object r0 = com.android.systemui.lifecycle.SysUiViewModelKt.rememberViewModel(r0, r1, r2, r3, r4, r5)
                            com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl) r0
                            r3.end(r8)
                            kotlinx.coroutines.flow.StateFlow r6 = r6.$tintFlow
                            androidx.compose.runtime.MutableState r6 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r6, r7)
                            androidx.compose.runtime.State r8 = r0.isIconVisible$delegate
                            java.lang.Object r8 = r8.getValue()
                            java.lang.Boolean r8 = (java.lang.Boolean) r8
                            boolean r8 = r8.booleanValue()
                            if (r8 == 0) goto La6
                            androidx.compose.runtime.DynamicProvidableCompositionLocal r8 = androidx.compose.material3.ContentColorKt.LocalContentColor
                            java.lang.Object r6 = r6.getValue()
                            java.lang.Number r6 = (java.lang.Number) r6
                            int r6 = r6.intValue()
                            long r1 = androidx.compose.ui.graphics.ColorKt.Color(r6)
                            androidx.compose.ui.graphics.Color r6 = androidx.compose.ui.graphics.Color.m454boximpl(r1)
                            androidx.compose.runtime.ProvidedValue r6 = r8.defaultProvidedValue$runtime_release(r6)
                            com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1$1$1$1 r8 = new com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1$1$1$1
                            r8.<init>()
                            r0 = 1623118970(0x60bed47a, float:1.10006E20)
                            androidx.compose.runtime.internal.ComposableLambdaImpl r8 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r0, r8, r7)
                            r0 = 56
                            androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(r6, r8, r7, r0)
                        La6:
                            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r6 == 0) goto Laf
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        Laf:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1$1$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, KairosNetwork kairosNetwork, StackedMobileIconViewModelKairos.Factory factory, StackedMobileIconViewModelImpl.Factory factory2, StateFlow stateFlow, Continuation continuation) {
            super(3, continuation);
            this.$view = singleBindableStatusBarComposeIconView;
            this.$kairosNetwork = kairosNetwork;
            this.$kairosViewModelFactory = factory;
            this.$viewModelFactory = factory2;
            this.$tintFlow = stateFlow;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, this.$tintFlow, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C03641 c03641 = new C03641(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, this.$tintFlow, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c03641, this) == coroutineSingletons) {
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
    public StackedMobileIconBinder$bind$2(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, KairosNetwork kairosNetwork, StackedMobileIconViewModelKairos.Factory factory, StackedMobileIconViewModelImpl.Factory factory2, Continuation continuation) {
        super(4, continuation);
        this.$view = singleBindableStatusBarComposeIconView;
        this.$kairosNetwork = kairosNetwork;
        this.$kairosViewModelFactory = factory;
        this.$viewModelFactory = factory2;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        StackedMobileIconBinder$bind$2 stackedMobileIconBinder$bind$2 = new StackedMobileIconBinder$bind$2(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, (Continuation) obj4);
        stackedMobileIconBinder$bind$2.L$0 = (StateFlow) obj3;
        return stackedMobileIconBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        StateFlow stateFlow = (StateFlow) this.L$0;
        SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView = this.$view;
        RepeatWhenAttachedKt.repeatWhenAttached(singleBindableStatusBarComposeIconView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(singleBindableStatusBarComposeIconView, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, stateFlow, null));
        return Unit.INSTANCE;
    }
}
