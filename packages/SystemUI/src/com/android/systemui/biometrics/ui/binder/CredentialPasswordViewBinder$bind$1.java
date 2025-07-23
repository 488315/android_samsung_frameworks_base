package com.android.systemui.biometrics.ui.binder;

import android.view.inputmethod.InputMethodManager;
import android.widget.ImeAwareEditText;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.lifecycle.LifecycleOwner;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.ui.CredentialPasswordView;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
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
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            final OnBackInvokedDispatcher findOnBackInvokedDispatcher = this.$view.findOnBackInvokedDispatcher();
            if (findOnBackInvokedDispatcher != null) {
                StandaloneCoroutine launchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(findOnBackInvokedDispatcher, this.$onBackInvokedCallback, null), 7);
                final OnBackInvokedCallback onBackInvokedCallback = this.$onBackInvokedCallback;
                launchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1$4$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0108, code lost:
    
        if (androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r3, r14, r4, r13) != r0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0054, code lost:
    
        if (r14 == r0) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a8  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
