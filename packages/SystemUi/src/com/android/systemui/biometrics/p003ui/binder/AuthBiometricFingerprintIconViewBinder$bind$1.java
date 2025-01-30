package com.android.systemui.biometrics.p003ui.binder;

import android.view.Display;
import android.view.DisplayInfo;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.p003ui.viewmodel.AuthBiometricFingerprintViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1", m277f = "AuthBiometricFingerprintIconViewBinder.kt", m278l = {38}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class AuthBiometricFingerprintIconViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ LottieAnimationView $view;
    final /* synthetic */ AuthBiometricFingerprintViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1$1", m277f = "AuthBiometricFingerprintIconViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1$1 */
    final class C11001 extends SuspendLambda implements Function2 {
        final /* synthetic */ LottieAnimationView $view;
        final /* synthetic */ AuthBiometricFingerprintViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1$1$1", m277f = "AuthBiometricFingerprintIconViewBinder.kt", m278l = {43}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder$bind$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $view;
            final /* synthetic */ AuthBiometricFingerprintViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(AuthBiometricFingerprintViewModel authBiometricFingerprintViewModel, LottieAnimationView lottieAnimationView, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = authBiometricFingerprintViewModel;
                this.$view = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$view, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.iconAsset;
                    final LottieAnimationView lottieAnimationView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.AuthBiometricFingerprintIconViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            LottieAnimationView.this.setAnimation(((Number) obj2).intValue());
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11001(LottieAnimationView lottieAnimationView, AuthBiometricFingerprintViewModel authBiometricFingerprintViewModel, Continuation<? super C11001> continuation) {
            super(2, continuation);
            this.$view = lottieAnimationView;
            this.$viewModel = authBiometricFingerprintViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11001 c11001 = new C11001(this.$view, this.$viewModel, continuation);
            c11001.L$0 = obj;
            return c11001;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11001) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            DisplayInfo displayInfo = new DisplayInfo();
            Display display = this.$view.getContext().getDisplay();
            if (display != null) {
                display.getDisplayInfo(displayInfo);
            }
            AuthBiometricFingerprintViewModel authBiometricFingerprintViewModel = this.$viewModel;
            authBiometricFingerprintViewModel.rotation = displayInfo.rotation;
            ((DisplayStateInteractorImpl) authBiometricFingerprintViewModel.interactor).screenSizeFoldProvider.onConfigurationChange(this.$view.getContext().getResources().getConfiguration());
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$view, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthBiometricFingerprintIconViewBinder$bind$1(LottieAnimationView lottieAnimationView, AuthBiometricFingerprintViewModel authBiometricFingerprintViewModel, Continuation<? super AuthBiometricFingerprintIconViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$view = lottieAnimationView;
        this.$viewModel = authBiometricFingerprintViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AuthBiometricFingerprintIconViewBinder$bind$1 authBiometricFingerprintIconViewBinder$bind$1 = new AuthBiometricFingerprintIconViewBinder$bind$1(this.$view, this.$viewModel, (Continuation) obj3);
        authBiometricFingerprintIconViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return authBiometricFingerprintIconViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C11001 c11001 = new C11001(this.$view, this.$viewModel, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c11001, this) == coroutineSingletons) {
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
