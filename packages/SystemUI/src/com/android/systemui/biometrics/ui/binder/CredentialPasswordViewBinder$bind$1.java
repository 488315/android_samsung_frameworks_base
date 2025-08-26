package com.android.systemui.biometrics.ui.binder;

import android.os.UserHandle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImeAwareEditText;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.ui.CredentialPasswordView;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl;
import com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class CredentialPasswordViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ CredentialView.Host $host;
    final /* synthetic */ InputMethodManager $imeManager;
    final /* synthetic */ OnBackInvokedCallback $onBackInvokedCallback;
    final /* synthetic */ ImeAwareEditText $passwordField;
    final /* synthetic */ boolean $requestFocusForInput;
    final /* synthetic */ CredentialPasswordView $view;
    final /* synthetic */ CredentialViewModel $viewModel;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ CredentialView.Host $host;
        final /* synthetic */ InputMethodManager $imeManager;
        final /* synthetic */ OnBackInvokedCallback $onBackInvokedCallback;
        final /* synthetic */ ImeAwareEditText $passwordField;
        final /* synthetic */ CredentialPasswordView $view;
        final /* synthetic */ CredentialViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$4$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ CredentialView.Host $host;
            final /* synthetic */ InputMethodManager $imeManager;
            final /* synthetic */ ImeAwareEditText $passwordField;
            final /* synthetic */ CredentialPasswordView $view;
            final /* synthetic */ CredentialViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(CredentialViewModel credentialViewModel, InputMethodManager inputMethodManager, CredentialPasswordView credentialPasswordView, CredentialView.Host host, ImeAwareEditText imeAwareEditText, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = credentialViewModel;
                this.$imeManager = inputMethodManager;
                this.$view = credentialPasswordView;
                this.$host = host;
                this.$passwordField = imeAwareEditText;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$imeManager, this.$view, this.$host, this.$passwordField, continuation);
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
                    ReadonlySharedFlow readonlySharedFlow = this.$viewModel.validatedAttestation;
                    final InputMethodManager inputMethodManager = this.$imeManager;
                    final CredentialPasswordView credentialPasswordView = this.$view;
                    final CredentialView.Host host = this.$host;
                    final ImeAwareEditText imeAwareEditText = this.$passwordField;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder.bind.1.4.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            byte[] bArr = (byte[]) obj2;
                            if (bArr != null) {
                                inputMethodManager.hideSoftInputFromWindow(credentialPasswordView.getWindowToken(), 0);
                                AuthContainerView authContainerView = (AuthContainerView) host;
                                authContainerView.mCredentialAttestation = bArr;
                                authContainerView.animateAway(7, true);
                            } else {
                                imeAwareEditText.setText("");
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlySharedFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$4$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ OnBackInvokedCallback $onBackInvokedCallback;
            final /* synthetic */ OnBackInvokedDispatcher $onBackInvokedDispatcher;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(OnBackInvokedDispatcher onBackInvokedDispatcher, OnBackInvokedCallback onBackInvokedCallback, Continuation continuation) {
                super(2, continuation);
                this.$onBackInvokedDispatcher = onBackInvokedDispatcher;
                this.$onBackInvokedCallback = onBackInvokedCallback;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$onBackInvokedDispatcher, this.$onBackInvokedCallback, continuation);
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
                    this.$onBackInvokedDispatcher.registerOnBackInvokedCallback(0, this.$onBackInvokedCallback);
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
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(CredentialPasswordView credentialPasswordView, CredentialViewModel credentialViewModel, InputMethodManager inputMethodManager, CredentialView.Host host, ImeAwareEditText imeAwareEditText, OnBackInvokedCallback onBackInvokedCallback, Continuation continuation) {
            super(2, continuation);
            this.$view = credentialPasswordView;
            this.$viewModel = credentialViewModel;
            this.$imeManager = inputMethodManager;
            this.$host = host;
            this.$passwordField = imeAwareEditText;
            this.$onBackInvokedCallback = onBackInvokedCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$view, this.$viewModel, this.$imeManager, this.$host, this.$passwordField, this.$onBackInvokedCallback, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$imeManager, this.$view, this.$host, this.$passwordField, null), 7);
            final OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher = this.$view.findOnBackInvokedDispatcher();
            if (onBackInvokedDispatcherFindOnBackInvokedDispatcher != null) {
                StandaloneCoroutine standaloneCoroutineLaunchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(onBackInvokedDispatcherFindOnBackInvokedDispatcher, this.$onBackInvokedCallback, null), 7);
                final OnBackInvokedCallback onBackInvokedCallback = this.$onBackInvokedCallback;
                standaloneCoroutineLaunchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$4$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        onBackInvokedDispatcherFindOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
                        return Unit.INSTANCE;
                    }
                });
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialPasswordViewBinder$bind$1(CredentialViewModel credentialViewModel, ImeAwareEditText imeAwareEditText, boolean z, OnBackInvokedCallback onBackInvokedCallback, CredentialPasswordView credentialPasswordView, InputMethodManager inputMethodManager, CredentialView.Host host, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = credentialViewModel;
        this.$passwordField = imeAwareEditText;
        this.$requestFocusForInput = z;
        this.$onBackInvokedCallback = onBackInvokedCallback;
        this.$view = credentialPasswordView;
        this.$imeManager = inputMethodManager;
        this.$host = host;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CredentialPasswordViewBinder$bind$1 credentialPasswordViewBinder$bind$1 = new CredentialPasswordViewBinder$bind$1(this.$viewModel, this.$passwordField, this.$requestFocusForInput, this.$onBackInvokedCallback, this.$view, this.$imeManager, this.$host, (Continuation) obj3);
        credentialPasswordViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return credentialPasswordViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0108, code lost:
    
        if (androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r3, r14, r4, r13) != r0) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ba  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        LifecycleOwner lifecycleOwner;
        final CredentialHeaderViewModel credentialHeaderViewModel;
        LifecycleOwner lifecycleOwner2;
        Integer num;
        final LifecycleOwner lifecycleOwner3;
        Integer num2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            lifecycleOwner = (LifecycleOwner) this.L$0;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.header;
            this.L$0 = lifecycleOwner;
            this.label = 1;
            obj = FlowKt.first(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            lifecycleOwner = (LifecycleOwner) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i == 2) {
                credentialHeaderViewModel = (CredentialHeaderViewModel) this.L$1;
                lifecycleOwner2 = (LifecycleOwner) this.L$0;
                ResultKt.throwOnFailure(obj);
                num = (Integer) obj;
                if (num != null) {
                    ImeAwareEditText imeAwareEditText = this.$passwordField;
                    CredentialPasswordView credentialPasswordView = this.$view;
                    imeAwareEditText.setContentDescription(credentialPasswordView.getContext().getString(num.intValue()));
                }
                CredentialViewModel$special$$inlined$map$1 credentialViewModel$special$$inlined$map$1 = this.$viewModel.inputFlags;
                this.L$0 = lifecycleOwner2;
                this.L$1 = credentialHeaderViewModel;
                this.label = 3;
                obj = FlowKt.firstOrNull(credentialViewModel$special$$inlined$map$1, this);
                if (obj != coroutineSingletons) {
                    lifecycleOwner3 = lifecycleOwner2;
                    num2 = (Integer) obj;
                    if (num2 != null) {
                    }
                    if (this.$requestFocusForInput) {
                    }
                    ImeAwareEditText imeAwareEditText2 = this.$passwordField;
                    final CredentialViewModel credentialViewModel = this.$viewModel;
                    imeAwareEditText2.setOnEditorActionListener(new OnImeSubmitListener(new Function1() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner3), null, null, new CredentialPasswordViewBinder$bind$1$3$1(credentialViewModel, (CharSequence) obj2, credentialHeaderViewModel, null), 7);
                            return Unit.INSTANCE;
                        }
                    }));
                    this.$passwordField.setOnKeyListener(new OnBackButtonListener(this.$onBackInvokedCallback));
                    this.$view.findViewById(R.id.pin_pad);
                    Lifecycle.State state = Lifecycle.State.STARTED;
                    AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$view, this.$viewModel, this.$imeManager, this.$host, this.$passwordField, this.$onBackInvokedCallback, null);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 4;
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            credentialHeaderViewModel = (CredentialHeaderViewModel) this.L$1;
            lifecycleOwner3 = (LifecycleOwner) this.L$0;
            ResultKt.throwOnFailure(obj);
            num2 = (Integer) obj;
            if (num2 != null) {
                this.$passwordField.setInputType(num2.intValue());
            }
            if (this.$requestFocusForInput) {
                this.$passwordField.requestFocus();
                this.$passwordField.scheduleShowSoftInput();
            }
            ImeAwareEditText imeAwareEditText22 = this.$passwordField;
            final CredentialViewModel credentialViewModel2 = this.$viewModel;
            imeAwareEditText22.setOnEditorActionListener(new OnImeSubmitListener(new Function1() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner3), null, null, new CredentialPasswordViewBinder$bind$1$3$1(credentialViewModel2, (CharSequence) obj2, credentialHeaderViewModel, null), 7);
                    return Unit.INSTANCE;
                }
            }));
            this.$passwordField.setOnKeyListener(new OnBackButtonListener(this.$onBackInvokedCallback));
            this.$view.findViewById(R.id.pin_pad);
            Lifecycle.State state2 = Lifecycle.State.STARTED;
            AnonymousClass4 anonymousClass42 = new AnonymousClass4(this.$view, this.$viewModel, this.$imeManager, this.$host, this.$passwordField, this.$onBackInvokedCallback, null);
            this.L$0 = null;
            this.L$1 = null;
            this.label = 4;
        }
        CredentialHeaderViewModel credentialHeaderViewModel2 = (CredentialHeaderViewModel) obj;
        this.$passwordField.setTextOperationUser(UserHandle.of(((BiometricPromptHeaderViewModelImpl) credentialHeaderViewModel2).user.userIdForPasswordEntry));
        CredentialViewModel$special$$inlined$map$2 credentialViewModel$special$$inlined$map$2 = this.$viewModel.inputBoxContentDescription;
        this.L$0 = lifecycleOwner;
        this.L$1 = credentialHeaderViewModel2;
        this.label = 2;
        Object objFirstOrNull = FlowKt.firstOrNull(credentialViewModel$special$$inlined$map$2, this);
        if (objFirstOrNull != coroutineSingletons) {
            LifecycleOwner lifecycleOwner4 = lifecycleOwner;
            credentialHeaderViewModel = credentialHeaderViewModel2;
            obj = objFirstOrNull;
            lifecycleOwner2 = lifecycleOwner4;
            num = (Integer) obj;
            if (num != null) {
            }
            CredentialViewModel$special$$inlined$map$1 credentialViewModel$special$$inlined$map$12 = this.$viewModel.inputFlags;
            this.L$0 = lifecycleOwner2;
            this.L$1 = credentialHeaderViewModel;
            this.label = 3;
            obj = FlowKt.firstOrNull(credentialViewModel$special$$inlined$map$12, this);
            if (obj != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
