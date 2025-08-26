package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import android.hardware.biometrics.PromptContentView;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.plugins.AuthContextPlugins;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl;
import com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class CredentialViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $animatePanel;
    final /* synthetic */ Button $cancelButton;
    final /* synthetic */ LinearLayout $customizedViewContainer;
    final /* synthetic */ TextView $descriptionView;
    final /* synthetic */ Button $emergencyButtonView;
    final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
    final /* synthetic */ TextView $errorView;
    final /* synthetic */ CredentialView.Host $host;
    final /* synthetic */ ImageView $iconView;
    final /* synthetic */ Spaghetti.Callback $legacyCallback;
    final /* synthetic */ long $maxErrorDuration;
    final /* synthetic */ AuthPanelController $panelViewController;
    final /* synthetic */ AuthContextPlugins $plugins;
    final /* synthetic */ TextView $subtitleView;
    final /* synthetic */ TextView $titleView;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ CredentialViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Button $cancelButton;
        final /* synthetic */ LinearLayout $customizedViewContainer;
        final /* synthetic */ TextView $descriptionView;
        final /* synthetic */ Button $emergencyButtonView;
        final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
        final /* synthetic */ TextView $errorView;
        final /* synthetic */ CredentialView.Host $host;
        final /* synthetic */ ImageView $iconView;
        final /* synthetic */ Spaghetti.Callback $legacyCallback;
        final /* synthetic */ long $maxErrorDuration;
        final /* synthetic */ AuthContextPlugins $plugins;
        final /* synthetic */ TextView $subtitleView;
        final /* synthetic */ TextView $titleView;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ CredentialViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2, reason: invalid class name and collision with other inner class name */
        final class C00902 extends SuspendLambda implements Function2 {
            final /* synthetic */ LinearLayout $customizedViewContainer;
            final /* synthetic */ TextView $descriptionView;
            final /* synthetic */ Button $emergencyButtonView;
            final /* synthetic */ ImageView $iconView;
            final /* synthetic */ Spaghetti.Callback $legacyCallback;
            final /* synthetic */ TextView $subtitleView;
            final /* synthetic */ TextView $titleView;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ CredentialViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00902(CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, LinearLayout linearLayout, Spaghetti.Callback callback, ImageView imageView, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$titleView = textView;
                this.$view = viewGroup;
                this.$subtitleView = textView2;
                this.$descriptionView = textView3;
                this.$customizedViewContainer = linearLayout;
                this.$legacyCallback = callback;
                this.$iconView = imageView;
                this.$emergencyButtonView = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00902(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00902) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final CredentialViewModel credentialViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = credentialViewModel.header;
                    final TextView textView = this.$titleView;
                    final ViewGroup viewGroup = this.$view;
                    final TextView textView2 = this.$subtitleView;
                    final TextView textView3 = this.$descriptionView;
                    final LinearLayout linearLayout = this.$customizedViewContainer;
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    final ImageView imageView = this.$iconView;
                    final Button button = this.$emergencyButtonView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BiometricPromptHeaderViewModelImpl biometricPromptHeaderViewModelImpl = (BiometricPromptHeaderViewModelImpl) ((CredentialHeaderViewModel) obj2);
                            textView.setText(biometricPromptHeaderViewModelImpl.title);
                            viewGroup.announceForAccessibility(biometricPromptHeaderViewModelImpl.title);
                            CredentialViewBinderKt.access$setTextOrHide(textView2, biometricPromptHeaderViewModelImpl.subtitle);
                            CredentialViewBinderKt.access$setTextOrHide(textView3, biometricPromptHeaderViewModelImpl.description);
                            BiometricCustomizedViewBinder biometricCustomizedViewBinder = BiometricCustomizedViewBinder.INSTANCE;
                            LinearLayout linearLayout2 = linearLayout;
                            PromptContentView promptContentView = biometricPromptHeaderViewModelImpl.contentView;
                            biometricCustomizedViewBinder.getClass();
                            RepeatWhenAttachedKt.repeatWhenAttached(linearLayout2, EmptyCoroutineContext.INSTANCE, new BiometricCustomizedViewBinder$bind$1(promptContentView, callback, null));
                            ImageView imageView2 = imageView;
                            if (imageView2 != null) {
                                imageView2.setImageDrawable(biometricPromptHeaderViewModelImpl.icon);
                            }
                            boolean z = biometricPromptHeaderViewModelImpl.showEmergencyCallButton;
                            final CredentialViewModel credentialViewModel2 = credentialViewModel;
                            if (z) {
                                button.setVisibility(0);
                                Button button2 = button;
                                final ViewGroup viewGroup2 = viewGroup;
                                button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.2.1.1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        CredentialViewModel credentialViewModel3 = credentialViewModel2;
                                        Context context = viewGroup2.getContext();
                                        credentialViewModel3.getClass();
                                        Object systemService = context.getSystemService((Class<Object>) TelecomManager.class);
                                        systemService.getClass();
                                        context.startActivity(((TelecomManager) systemService).createLaunchEmergencyDialerIntent(null).setFlags(335544320));
                                    }
                                });
                            }
                            if (((Boolean) credentialViewModel2.animateContents.$$delegate_0.getValue()).booleanValue()) {
                                final ViewGroup viewGroup3 = viewGroup;
                                viewGroup3.setTranslationY(viewGroup3.getResources().getDimension(R.dimen.biometric_dialog_credential_translation_offset));
                                viewGroup3.setAlpha(0.0f);
                                viewGroup3.postOnAnimation(new Runnable() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinderKt$animateCredentialViewIn$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        viewGroup3.animate().translationY(0.0f).setDuration(150L).alpha(1.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).withLayer().start();
                                    }
                                });
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $cancelButton;
            final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
            final /* synthetic */ TextView $errorView;
            final /* synthetic */ long $maxErrorDuration;
            final /* synthetic */ CredentialViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                final /* synthetic */ CoroutineScope $$this$launch;
                final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
                final /* synthetic */ long $maxErrorDuration;
                final /* synthetic */ CredentialViewModel $viewModel;
                /* synthetic */ Object L$0;
                int label;

                /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$1$1, reason: invalid class name and collision with other inner class name */
                final class C00921 extends SuspendLambda implements Function2 {
                    final /* synthetic */ long $maxErrorDuration;
                    final /* synthetic */ CredentialViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C00921(long j, CredentialViewModel credentialViewModel, Continuation continuation) {
                        super(2, continuation);
                        this.$maxErrorDuration = j;
                        this.$viewModel = credentialViewModel;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C00921(this.$maxErrorDuration, this.$viewModel, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C00921) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                public AnonymousClass1(Ref$ObjectRef<Job> ref$ObjectRef, CoroutineScope coroutineScope, long j, CredentialViewModel credentialViewModel, Continuation continuation) {
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
                    if (!StringsKt__StringsKt.isBlank(str)) {
                        this.$errorTimer.element = CoroutineTracingKt.launchTraced$default(this.$$this$launch, null, null, new C00921(this.$maxErrorDuration, this.$viewModel, null), 7);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(CredentialViewModel credentialViewModel, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$errorTimer = ref$ObjectRef;
                this.$maxErrorDuration = j;
                this.$errorView = textView;
                this.$cancelButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$viewModel, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, continuation);
                anonymousClass3.L$0 = obj;
                return anonymousClass3;
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
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    CredentialViewModel credentialViewModel = this.$viewModel;
                    FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(credentialViewModel.errorMessage, new AnonymousClass1(this.$errorTimer, coroutineScope, this.$maxErrorDuration, credentialViewModel, null));
                    final TextView textView = this.$errorView;
                    final Button button = this.$cancelButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.3.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            String str = (String) obj2;
                            boolean z = str == null || StringsKt__StringsKt.isBlank(str);
                            textView.setVisibility(z ? button != null ? 4 : 8 : 0);
                            TextView textView2 = textView;
                            if (z) {
                                str = "";
                            }
                            textView2.setText(str);
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

        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ CredentialView.Host $host;
            final /* synthetic */ CredentialViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(CredentialViewModel credentialViewModel, CredentialView.Host host, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$host = host;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$host, continuation);
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
                    final ReadonlyStateFlow readonlyStateFlow = this.$viewModel.remainingAttempts;
                    Flow flow = new Flow() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$4$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$4$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$4$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                }
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    if (((RemainingAttempts) obj).remaining != null) {
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final CredentialView.Host host = this.$host;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.4.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            RemainingAttempts remainingAttempts = (RemainingAttempts) obj2;
                            Integer num = remainingAttempts.remaining;
                            num.getClass();
                            ((AuthContainerView) host).onCredentialAttemptsRemaining(num.intValue(), remainingAttempts.message);
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
        public AnonymousClass2(AuthContextPlugins authContextPlugins, ViewGroup viewGroup, CredentialViewModel credentialViewModel, TextView textView, TextView textView2, TextView textView3, LinearLayout linearLayout, Spaghetti.Callback callback, ImageView imageView, Button button, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView4, Button button2, CredentialView.Host host, Continuation continuation) {
            super(2, continuation);
            this.$view = viewGroup;
            this.$viewModel = credentialViewModel;
            this.$titleView = textView;
            this.$subtitleView = textView2;
            this.$descriptionView = textView3;
            this.$customizedViewContainer = linearLayout;
            this.$legacyCallback = callback;
            this.$iconView = imageView;
            this.$emergencyButtonView = button;
            this.$errorTimer = ref$ObjectRef;
            this.$maxErrorDuration = j;
            this.$errorView = textView4;
            this.$cancelButton = button2;
            this.$host = host;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null, this.$view, this.$viewModel, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, this.$host, continuation);
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
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00902(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$host, null), 7);
                    this.label = 1;
                    if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            } catch (Throwable unused) {
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewBinder$bind$1(boolean z, AuthPanelController authPanelController, AuthContextPlugins authContextPlugins, ViewGroup viewGroup, CredentialViewModel credentialViewModel, TextView textView, TextView textView2, TextView textView3, LinearLayout linearLayout, Spaghetti.Callback callback, ImageView imageView, Button button, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView4, Button button2, CredentialView.Host host, Continuation continuation) {
        super(3, continuation);
        this.$animatePanel = z;
        this.$panelViewController = authPanelController;
        this.$view = viewGroup;
        this.$viewModel = credentialViewModel;
        this.$titleView = textView;
        this.$subtitleView = textView2;
        this.$descriptionView = textView3;
        this.$customizedViewContainer = linearLayout;
        this.$legacyCallback = callback;
        this.$iconView = imageView;
        this.$emergencyButtonView = button;
        this.$errorTimer = ref$ObjectRef;
        this.$maxErrorDuration = j;
        this.$errorView = textView4;
        this.$cancelButton = button2;
        this.$host = host;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CredentialViewBinder$bind$1 credentialViewBinder$bind$1 = new CredentialViewBinder$bind$1(this.$animatePanel, this.$panelViewController, null, this.$view, this.$viewModel, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, this.$host, (Continuation) obj3);
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
                int i2 = authPanelController.mContainerWidth;
                int i3 = authPanelController.mContainerHeight;
                if (i2 == 0 || i3 == 0) {
                    Log.w("BiometricPrompt/AuthPanelController", "Not done measuring yet");
                } else {
                    authPanelController.mMargin = 0;
                    authPanelController.mCornerRadius = 0.0f;
                    authPanelController.mContentWidth = i2;
                    authPanelController.mContentHeight = i3;
                    authPanelController.mPanelView.invalidateOutline();
                }
            }
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null, this.$view, this.$viewModel, this.$titleView, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, this.$host, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass2, this) == coroutineSingletons) {
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
