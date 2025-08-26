package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import androidx.compose.material3.ContentColorKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.platform.ViewCompositionStrategy;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelKairos;
import com.android.systemui.statusbar.pipeline.shared.ui.composable.StackedMobileIconKt;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class StackedMobileIconBinder {
    public static final StackedMobileIconBinder INSTANCE = new StackedMobileIconBinder();

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function4 {
        final /* synthetic */ KairosNetwork $kairosNetwork;
        final /* synthetic */ StackedMobileIconViewModelKairos.Factory $kairosViewModelFactory;
        final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
        final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;
        /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            final /* synthetic */ KairosNetwork $kairosNetwork;
            final /* synthetic */ StackedMobileIconViewModelKairos.Factory $kairosViewModelFactory;
            final /* synthetic */ StateFlow $tintFlow;
            final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
            final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C05661 extends SuspendLambda implements Function2 {
                final /* synthetic */ KairosNetwork $kairosNetwork;
                final /* synthetic */ StackedMobileIconViewModelKairos.Factory $kairosViewModelFactory;
                final /* synthetic */ StateFlow $tintFlow;
                final /* synthetic */ SingleBindableStatusBarComposeIconView $view;
                final /* synthetic */ StackedMobileIconViewModelImpl.Factory $viewModelFactory;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05661(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, KairosNetwork kairosNetwork, StackedMobileIconViewModelKairos.Factory factory, StackedMobileIconViewModelImpl.Factory factory2, StateFlow stateFlow, Continuation continuation) {
                    super(2, continuation);
                    this.$view = singleBindableStatusBarComposeIconView;
                    this.$kairosNetwork = kairosNetwork;
                    this.$kairosViewModelFactory = factory;
                    this.$viewModelFactory = factory2;
                    this.$tintFlow = stateFlow;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05661(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, this.$tintFlow, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05661) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x004b  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            if ((((Number) obj3).intValue() & 3) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder.bind.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (StackedMobileIconBinder.kt:60)");
                                    }
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer;
                                    composerImpl2.startReplaceGroup(-450896716);
                                    composerImpl2.startReplaceGroup(1509478137);
                                    StackedMobileIconViewModelImpl.Factory factory3 = this.$viewModelFactory;
                                    boolean zChangedInstance = composerImpl2.changedInstance(factory3);
                                    Object objRememberedValue = composerImpl2.rememberedValue();
                                    if (!zChangedInstance) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new StackedMobileIconBinder$$ExternalSyntheticLambda0(factory3, 1);
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        composerImpl2.end(false);
                                        final StackedMobileIconViewModelImpl stackedMobileIconViewModelImpl = (StackedMobileIconViewModelImpl) SysUiViewModelKt.rememberViewModel("StackedMobileIconBinder", null, (Function0) objRememberedValue, composerImpl2, 6, 2);
                                        composerImpl2.end(false);
                                        MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(this.$tintFlow, composer);
                                        if (((Boolean) stackedMobileIconViewModelImpl.isIconVisible$delegate.getValue()).booleanValue()) {
                                            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(ColorKt.Color(((Number) mutableStateCollectAsStateWithLifecycle.getValue()).intValue()))), ComposableLambdaKt.rememberComposableLambda(1623118970, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$bind$2$1$1$1$1.1
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj4, Object obj5) {
                                                    Composer composer2 = (Composer) obj4;
                                                    if ((((Number) obj5).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                        if (composerImpl3.getSkipping()) {
                                                            composerImpl3.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder.bind.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (StackedMobileIconBinder.kt:73)");
                                                            }
                                                            StackedMobileIconKt.StackedMobileIcon(stackedMobileIconViewModelImpl, null, composer2, 0);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer), composer, 56);
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
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
                    C05661 c05661 = new C05661(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, this.$tintFlow, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05661, this) == coroutineSingletons) {
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
        public AnonymousClass2(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, KairosNetwork kairosNetwork, StackedMobileIconViewModelKairos.Factory factory, StackedMobileIconViewModelImpl.Factory factory2, Continuation continuation) {
            super(4, continuation);
            this.$view = singleBindableStatusBarComposeIconView;
            this.$kairosNetwork = kairosNetwork;
            this.$kairosViewModelFactory = factory;
            this.$viewModelFactory = factory2;
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$view, this.$kairosNetwork, this.$kairosViewModelFactory, this.$viewModelFactory, (Continuation) obj4);
            anonymousClass2.L$0 = (StateFlow) obj3;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
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

    private StackedMobileIconBinder() {
    }

    public static SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2 bind(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, MobileIconsViewModel mobileIconsViewModel, StackedMobileIconViewModelImpl.Factory factory, StackedMobileIconViewModelKairos.Factory factory2, KairosNetwork kairosNetwork) {
        SingleBindableStatusBarComposeIconView.Companion companion = SingleBindableStatusBarComposeIconView.Companion;
        StackedMobileIconBinder$$ExternalSyntheticLambda0 stackedMobileIconBinder$$ExternalSyntheticLambda0 = new StackedMobileIconBinder$$ExternalSyntheticLambda0(mobileIconsViewModel, 0);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(singleBindableStatusBarComposeIconView, kairosNetwork, factory2, factory, null);
        companion.getClass();
        return SingleBindableStatusBarComposeIconView.Companion.withDefaultBinding(singleBindableStatusBarComposeIconView, stackedMobileIconBinder$$ExternalSyntheticLambda0, anonymousClass2);
    }
}
