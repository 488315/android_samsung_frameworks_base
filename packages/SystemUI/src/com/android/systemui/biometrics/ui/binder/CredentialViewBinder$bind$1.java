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
import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl;
import com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    final /* synthetic */ TextView $subtitleView;
    final /* synthetic */ TextView $titleView;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ CredentialViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        final /* synthetic */ TextView $subtitleView;
        final /* synthetic */ TextView $titleView;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ CredentialViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
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
            public AnonymousClass1(CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, LinearLayout linearLayout, Spaghetti.Callback callback, ImageView imageView, Button button, Continuation continuation) {
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
                return new AnonymousClass1(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = credentialViewModel.header;
                    final TextView textView = this.$titleView;
                    final ViewGroup viewGroup = this.$view;
                    final TextView textView2 = this.$subtitleView;
                    final TextView textView3 = this.$descriptionView;
                    final LinearLayout linearLayout = this.$customizedViewContainer;
                    final Spaghetti.Callback callback = this.$legacyCallback;
                    final ImageView imageView = this.$iconView;
                    final Button button = this.$emergencyButtonView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BiometricPromptHeaderViewModelImpl biometricPromptHeaderViewModelImpl = (BiometricPromptHeaderViewModelImpl) ((CredentialHeaderViewModel) obj2);
                            textView.setText(biometricPromptHeaderViewModelImpl.title);
                            viewGroup.announceForAccessibility(biometricPromptHeaderViewModelImpl.title);
                            CredentialViewBinderKt.access$setTextOrHide(textView2, biometricPromptHeaderViewModelImpl.subtitle);
                            CredentialViewBinderKt.access$setTextOrHide(textView3, biometricPromptHeaderViewModelImpl.description);
                            if (Flags.customBiometricPrompt()) {
                                com.android.systemui.Flags.constraintBp();
                                BiometricCustomizedViewBinder biometricCustomizedViewBinder = BiometricCustomizedViewBinder.INSTANCE;
                                LinearLayout linearLayout2 = linearLayout;
                                PromptContentView promptContentView = biometricPromptHeaderViewModelImpl.contentView;
                                biometricCustomizedViewBinder.getClass();
                                RepeatWhenAttachedKt.repeatWhenAttached(linearLayout2, EmptyCoroutineContext.INSTANCE, new BiometricCustomizedViewBinder$bind$1(promptContentView, callback, null));
                            }
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
                                button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.1.1.1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        CredentialViewModel credentialViewModel3 = CredentialViewModel.this;
                                        Context context = viewGroup2.getContext();
                                        credentialViewModel3.getClass();
                                        Object systemService = context.getSystemService((Class<Object>) TelecomManager.class);
                                        Intrinsics.checkNotNull(systemService);
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2, reason: invalid class name and collision with other inner class name */
        final class C00422 extends SuspendLambda implements Function2 {
            final /* synthetic */ Button $cancelButton;
            final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
            final /* synthetic */ TextView $errorView;
            final /* synthetic */ long $maxErrorDuration;
            final /* synthetic */ CredentialViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                final /* synthetic */ CoroutineScope $$this$launch;
                final /* synthetic */ Ref$ObjectRef<Job> $errorTimer;
                final /* synthetic */ long $maxErrorDuration;
                final /* synthetic */ CredentialViewModel $viewModel;
                /* synthetic */ Object L$0;
                int label;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$2$1$1, reason: invalid class name and collision with other inner class name */
                final class C00431 extends SuspendLambda implements Function2 {
                    final /* synthetic */ long $maxErrorDuration;
                    final /* synthetic */ CredentialViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C00431(long j, CredentialViewModel credentialViewModel, Continuation continuation) {
                        super(2, continuation);
                        this.$maxErrorDuration = j;
                        this.$viewModel = credentialViewModel;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C00431(this.$maxErrorDuration, this.$viewModel, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C00431) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

                /* JADX WARN: Type inference failed for: r5v5, types: [T, kotlinx.coroutines.Job] */
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
                        this.$errorTimer.element = BuildersKt.launch$default(this.$$this$launch, null, null, new C00431(this.$maxErrorDuration, this.$viewModel, null), 3);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00422(CredentialViewModel credentialViewModel, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView, Button button, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$errorTimer = ref$ObjectRef;
                this.$maxErrorDuration = j;
                this.$errorView = textView;
                this.$cancelButton = button;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00422 c00422 = new C00422(this.$viewModel, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, continuation);
                c00422.L$0 = obj;
                return c00422;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00422) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder.bind.1.2.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            String str = (String) obj2;
                            boolean z = true ^ (str == null || StringsKt__StringsJVMKt.isBlank(str));
                            textView.setVisibility(z ? 0 : button != null ? 4 : 8);
                            TextView textView2 = textView;
                            if (!z) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ CredentialView.Host $host;
            final /* synthetic */ CredentialViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(CredentialViewModel credentialViewModel, CredentialView.Host host, Continuation continuation) {
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

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L44
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    r6 = r5
                                    com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts r6 = (com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts) r6
                                    java.lang.Integer r6 = r6.remaining
                                    if (r6 == 0) goto L44
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L44
                                    return r1
                                L44:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$1$2$3$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
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
        public AnonymousClass2(CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, LinearLayout linearLayout, Spaghetti.Callback callback, ImageView imageView, Button button, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView4, Button button2, CredentialView.Host host, Continuation continuation) {
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
            this.$errorTimer = ref$ObjectRef;
            this.$maxErrorDuration = j;
            this.$errorView = textView4;
            this.$cancelButton = button2;
            this.$host = host;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, this.$host, continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new C00422(this.$viewModel, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$host, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewBinder$bind$1(boolean z, AuthPanelController authPanelController, CredentialViewModel credentialViewModel, TextView textView, ViewGroup viewGroup, TextView textView2, TextView textView3, LinearLayout linearLayout, Spaghetti.Callback callback, ImageView imageView, Button button, Ref$ObjectRef<Job> ref$ObjectRef, long j, TextView textView4, Button button2, CredentialView.Host host, Continuation continuation) {
        super(3, continuation);
        this.$animatePanel = z;
        this.$panelViewController = authPanelController;
        this.$viewModel = credentialViewModel;
        this.$titleView = textView;
        this.$view = viewGroup;
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
        CredentialViewBinder$bind$1 credentialViewBinder$bind$1 = new CredentialViewBinder$bind$1(this.$animatePanel, this.$panelViewController, this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, this.$host, (Continuation) obj3);
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
                if (authPanelController.mContainerWidth == 0 || authPanelController.mContainerHeight == 0) {
                    Log.w("BiometricPrompt/AuthPanelController", "Not done measuring yet");
                } else {
                    int dimension = authPanelController.mUseFullScreen ? 0 : (int) authPanelController.mContext.getResources().getDimension(R.dimen.biometric_dialog_border_padding);
                    float dimension2 = authPanelController.mUseFullScreen ? 0.0f : authPanelController.mContext.getResources().getDimension(R.dimen.biometric_dialog_corner_size);
                    authPanelController.mMargin = dimension;
                    authPanelController.mCornerRadius = dimension2;
                    authPanelController.mContentWidth = i2;
                    authPanelController.mContentHeight = i3;
                    authPanelController.mPanelView.invalidateOutline();
                }
            }
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$titleView, this.$view, this.$subtitleView, this.$descriptionView, this.$customizedViewContainer, this.$legacyCallback, this.$iconView, this.$emergencyButtonView, this.$errorTimer, this.$maxErrorDuration, this.$errorView, this.$cancelButton, this.$host, null);
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
