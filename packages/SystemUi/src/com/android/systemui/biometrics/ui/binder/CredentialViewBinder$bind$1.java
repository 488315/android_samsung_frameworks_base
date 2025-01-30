package com.android.systemui.biometrics.ui.binder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl;
import com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1;
import com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1", m277f = "CredentialViewBinder.kt", m278l = {66}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class CredentialViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $animatePanel;
    final /* synthetic */ TextView $descriptionView;
    final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
    final /* synthetic */ TextView $errorView;
    final /* synthetic */ CredentialView.Host $host;
    final /* synthetic */ ImageView $iconView;
    final /* synthetic */ long $maxErrorDuration;
    final /* synthetic */ AuthPanelController $panelViewController;
    final /* synthetic */ TextView $subtitleView;
    final /* synthetic */ TextView $titleView;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ CredentialViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2", m277f = "CredentialViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2 */
    final class C11042 extends SuspendLambda implements Function2 {
        final /* synthetic */ TextView $descriptionView;
        final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
        final /* synthetic */ TextView $errorView;
        final /* synthetic */ CredentialView.Host $host;
        final /* synthetic */ ImageView $iconView;
        final /* synthetic */ long $maxErrorDuration;
        final /* synthetic */ TextView $subtitleView;
        final /* synthetic */ TextView $titleView;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ CredentialViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$1", m277f = "CredentialViewBinder.kt", m278l = {69}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ TextView $descriptionView;
            final /* synthetic */ ImageView $iconView;
            final /* synthetic */ TextView $subtitleView;
            final /* synthetic */ TextView $titleView;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ CredentialViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, ImageView imageView, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$titleView = textView;
                this.$view = viewGroup;
                this.$subtitleView = textView2;
                this.$descriptionView = textView3;
                this.$iconView = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$iconView, continuation);
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
                    final CredentialViewModel credentialViewModel = this.$viewModel;
                    CredentialViewModel$special$$inlined$map$1 credentialViewModel$special$$inlined$map$1 = credentialViewModel.header;
                    final TextView textView = this.$titleView;
                    final ViewGroup viewGroup = this.$view;
                    final TextView textView2 = this.$subtitleView;
                    final TextView textView3 = this.$descriptionView;
                    final ImageView imageView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BiometricPromptHeaderViewModelImpl biometricPromptHeaderViewModelImpl = (BiometricPromptHeaderViewModelImpl) ((CredentialHeaderViewModel) obj2);
                            textView.setText(biometricPromptHeaderViewModelImpl.title);
                            final ViewGroup viewGroup2 = viewGroup;
                            viewGroup2.announceForAccessibility(biometricPromptHeaderViewModelImpl.title);
                            CredentialViewBinderKt.access$setTextOrHide(textView2, biometricPromptHeaderViewModelImpl.subtitle);
                            CredentialViewBinderKt.access$setTextOrHide(textView3, biometricPromptHeaderViewModelImpl.description);
                            ImageView imageView2 = imageView;
                            if (imageView2 != null) {
                                imageView2.setImageDrawable(biometricPromptHeaderViewModelImpl.icon);
                            }
                            if (((Boolean) credentialViewModel.animateContents.getValue()).booleanValue()) {
                                viewGroup2.setTranslationY(viewGroup2.getResources().getDimension(R.dimen.biometric_dialog_credential_translation_offset));
                                viewGroup2.setAlpha(0.0f);
                                viewGroup2.postOnAnimation(new Runnable() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinderKt$animateCredentialViewIn$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        viewGroup2.animate().translationY(0.0f).setDuration(150L).alpha(1.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).withLayer().start();
                                    }
                                });
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (credentialViewModel$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2", m277f = "CredentialViewBinder.kt", m278l = {97}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
            final /* synthetic */ TextView $errorView;
            final /* synthetic */ long $maxErrorDuration;
            final /* synthetic */ CredentialViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2$1", m277f = "CredentialViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                final /* synthetic */ CoroutineScope $$this$launch;
                final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
                final /* synthetic */ long $maxErrorDuration;
                final /* synthetic */ CredentialViewModel $viewModel;
                /* synthetic */ Object L$0;
                int label;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2$1$1", m277f = "CredentialViewBinder.kt", m278l = {92}, m279m = "invokeSuspend")
                /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2$1$1, reason: invalid class name and collision with other inner class name */
                final class C48651 extends SuspendLambda implements Function2 {
                    final /* synthetic */ long $maxErrorDuration;
                    final /* synthetic */ CredentialViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C48651(long j, CredentialViewModel credentialViewModel, Continuation<? super C48651> continuation) {
                        super(2, continuation);
                        this.$maxErrorDuration = j;
                        this.$viewModel = credentialViewModel;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C48651(this.$maxErrorDuration, this.$viewModel, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C48651) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            long j = this.$maxErrorDuration;
                            this.label = 1;
                            if (DelayKt.delay(j, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        this.$viewModel.credentialInteractor._verificationError.setValue(null);
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(Ref$ObjectRef<Job> ref$ObjectRef, CoroutineScope coroutineScope, long j, CredentialViewModel credentialViewModel, Continuation<? super AnonymousClass1> continuation) {
                    super(2, continuation);
                    this.$errorTimer = ref$ObjectRef;
                    this.$$this$launch = coroutineScope;
                    this.$maxErrorDuration = j;
                    this.$viewModel = credentialViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$errorTimer, this.$$this$launch, this.$maxErrorDuration, this.$viewModel, continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((String) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Type inference failed for: r5v5, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    String str = (String) this.L$0;
                    Job job = this.$errorTimer.element;
                    if (job != null) {
                        job.cancel(null);
                    }
                    if (!StringsKt__StringsJVMKt.isBlank(str)) {
                        this.$errorTimer.element = BuildersKt.launch$default(this.$$this$launch, null, null, new C48651(this.$maxErrorDuration, this.$viewModel, null), 3);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(CredentialViewModel credentialViewModel, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$errorTimer = ref$ObjectRef;
                this.$maxErrorDuration = j;
                this.$errorView = textView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$errorTimer, this.$maxErrorDuration, this.$errorView, continuation);
                anonymousClass2.L$0 = obj;
                return anonymousClass2;
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
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    CredentialViewModel credentialViewModel = this.$viewModel;
                    FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(credentialViewModel.errorMessage, new AnonymousClass1(this.$errorTimer, coroutineScope, this.$maxErrorDuration, credentialViewModel, null));
                    final TextView textView = this.$errorView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            CredentialViewBinderKt.access$setTextOrHide(textView, (String) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3", m277f = "CredentialViewBinder.kt", m278l = {104}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ CredentialView.Host $host;
            final /* synthetic */ CredentialViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(CredentialViewModel credentialViewModel, CredentialView.Host host, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$host = host;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$host, continuation);
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
                    final ReadonlyStateFlow readonlyStateFlow = this.$viewModel.remainingAttempts;
                    Flow flow = new Flow() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2 */
                        public final class C11032 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2", m277f = "CredentialViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return C11032.this.emit(null, this);
                                }
                            }

                            public C11032(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                        anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            if (((RemainingAttempts) obj).remaining != null) {
                                                anonymousClass1.label = 1;
                                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new C11032(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final CredentialView.Host host = this.$host;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.3.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            RemainingAttempts remainingAttempts = (RemainingAttempts) obj2;
                            Integer num = remainingAttempts.remaining;
                            Intrinsics.checkNotNull(num);
                            ((AuthContainerView) CredentialView.Host.this).onCredentialAttemptsRemaining(num.intValue(), remainingAttempts.message);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
        public C11042(CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, ImageView imageView, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView4, CredentialView.Host host, Continuation<? super C11042> continuation) {
            super(2, continuation);
            this.$viewModel = credentialViewModel;
            this.$titleView = textView;
            this.$view = viewGroup;
            this.$subtitleView = textView2;
            this.$descriptionView = textView3;
            this.$iconView = imageView;
            this.$errorTimer = ref$ObjectRef;
            this.$maxErrorDuration = j;
            this.$errorView = textView4;
            this.$host = host;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11042 c11042 = new C11042(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$iconView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$host, continuation);
            c11042.L$0 = obj;
            return c11042;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11042) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$iconView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$errorTimer, this.$maxErrorDuration, this.$errorView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$host, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewBinder$bind$1(boolean z, AuthPanelController authPanelController, CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, ImageView imageView, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView4, CredentialView.Host host, Continuation<? super CredentialViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$animatePanel = z;
        this.$panelViewController = authPanelController;
        this.$viewModel = credentialViewModel;
        this.$titleView = textView;
        this.$view = viewGroup;
        this.$subtitleView = textView2;
        this.$descriptionView = textView3;
        this.$iconView = imageView;
        this.$errorTimer = ref$ObjectRef;
        this.$maxErrorDuration = j;
        this.$errorView = textView4;
        this.$host = host;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CredentialViewBinder$bind$1 credentialViewBinder$bind$1 = new CredentialViewBinder$bind$1(this.$animatePanel, this.$panelViewController, this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$iconView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$host, (Continuation) obj3);
        credentialViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return credentialViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            if (this.$animatePanel) {
                AuthPanelController authPanelController = this.$panelViewController;
                authPanelController.mUseFullScreen = true;
                authPanelController.updateForContentDimensions(authPanelController.mContainerWidth, authPanelController.mContainerHeight, 0);
            }
            Lifecycle.State state = Lifecycle.State.STARTED;
            C11042 c11042 = new C11042(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$iconView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$host, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c11042, this) == coroutineSingletons) {
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
