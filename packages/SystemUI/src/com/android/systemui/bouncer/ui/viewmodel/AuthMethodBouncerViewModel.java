package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.domain.interactor.AuthenticationResult;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public abstract class AuthMethodBouncerViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _animateFailure;
    public final ReadonlyStateFlow animateFailure;
    public final BufferedChannel authenticationRequests;
    public final BouncerHapticPlayer bouncerHapticPlayer;
    public final BouncerInteractor interactor;
    public final StateFlow isInputEnabled;

    public final class AuthenticationRequest {
        public final List input;
        public final boolean useAutoConfirm;

        public AuthenticationRequest(List<? extends Object> list, boolean z) {
            this.input = list;
            this.useAutoConfirm = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AuthenticationRequest)) {
                return false;
            }
            AuthenticationRequest authenticationRequest = (AuthenticationRequest) obj;
            return Intrinsics.areEqual(this.input, authenticationRequest.input) && this.useAutoConfirm == authenticationRequest.useAutoConfirm;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.useAutoConfirm) + (this.input.hashCode() * 31);
        }

        public final String toString() {
            return "AuthenticationRequest(input=" + this.input + ", useAutoConfirm=" + this.useAutoConfirm + ")";
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AuthMethodBouncerViewModel.onActivated$suspendImpl(AuthMethodBouncerViewModel.this, this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = AuthMethodBouncerViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((AuthenticationRequest) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            AuthenticationRequest authenticationRequest;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AuthenticationRequest authenticationRequest2 = (AuthenticationRequest) this.L$0;
                if (!((Boolean) AuthMethodBouncerViewModel.this.isInputEnabled.getValue()).booleanValue()) {
                    return Unit.INSTANCE;
                }
                BouncerInteractor bouncerInteractor = AuthMethodBouncerViewModel.this.interactor;
                List list = authenticationRequest2.input;
                this.L$0 = authenticationRequest2;
                this.label = 1;
                Object objAuthenticate = bouncerInteractor.authenticate(list, authenticationRequest2.useAutoConfirm, this);
                if (objAuthenticate == coroutineSingletons) {
                    return coroutineSingletons;
                }
                authenticationRequest = authenticationRequest2;
                obj = objAuthenticate;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                authenticationRequest = (AuthenticationRequest) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            AuthenticationResult authenticationResult = (AuthenticationResult) obj;
            if (authenticationResult == AuthenticationResult.SKIPPED && authenticationRequest.useAutoConfirm) {
                return Unit.INSTANCE;
            }
            AuthMethodBouncerViewModel.this.getClass();
            StateFlowImpl stateFlowImpl = AuthMethodBouncerViewModel.this._animateFailure;
            AuthenticationResult authenticationResult2 = AuthenticationResult.SUCCEEDED;
            stateFlowImpl.updateState(null, Boolean.valueOf(authenticationResult != authenticationResult2));
            AuthMethodBouncerViewModel.this.clearInput();
            if (authenticationResult == authenticationResult2) {
                AuthMethodBouncerViewModel.this.onSuccessfulAuthentication();
            }
            return Unit.INSTANCE;
        }
    }

    public /* synthetic */ AuthMethodBouncerViewModel(BouncerInteractor bouncerInteractor, StateFlow stateFlow, String str, BouncerHapticPlayer bouncerHapticPlayer, DefaultConstructorMarker defaultConstructorMarker) {
        this(bouncerInteractor, stateFlow, str, bouncerHapticPlayer);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0054, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CoroutineSingletons onActivated$suspendImpl(AuthMethodBouncerViewModel authMethodBouncerViewModel, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = authMethodBouncerViewModel.new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelAsFlow channelAsFlowReceiveAsFlow = FlowKt.receiveAsFlow(authMethodBouncerViewModel.authenticationRequests);
            AnonymousClass2 anonymousClass2 = authMethodBouncerViewModel.new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (FlowKt.collectLatest(channelAsFlowReceiveAsFlow, anonymousClass2, anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        anonymousClass1.label = 2;
    }

    public static void tryAuthenticate$default(AuthMethodBouncerViewModel authMethodBouncerViewModel, List list, boolean z, int i) {
        if ((i & 1) != 0) {
            list = authMethodBouncerViewModel.getInput();
        }
        if ((i & 2) != 0) {
            z = false;
        }
        authMethodBouncerViewModel.authenticationRequests.mo3476trySendJP2dKIU(new AuthenticationRequest(list, z));
    }

    public abstract void clearInput();

    public abstract AuthenticationMethodModel getAuthenticationMethod();

    public abstract List getInput();

    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    public Object onActivated(Continuation continuation) {
        return onActivated$suspendImpl(this, (ContinuationImpl) continuation);
    }

    public final void onFailureAnimationShown() {
        this._animateFailure.updateState(null, Boolean.FALSE);
    }

    /* renamed from: onKeyEvent-uiMRsoQ, reason: not valid java name */
    public boolean mo1058onKeyEventuiMRsoQ(int i, int i2) {
        return false;
    }

    public /* synthetic */ AuthMethodBouncerViewModel(BouncerInteractor bouncerInteractor, StateFlow stateFlow, String str, BouncerHapticPlayer bouncerHapticPlayer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bouncerInteractor, stateFlow, str, (i & 8) != 0 ? null : bouncerHapticPlayer, null);
    }

    private AuthMethodBouncerViewModel(BouncerInteractor bouncerInteractor, StateFlow stateFlow, String str, BouncerHapticPlayer bouncerHapticPlayer) {
        this.interactor = bouncerInteractor;
        this.isInputEnabled = stateFlow;
        this.bouncerHapticPlayer = bouncerHapticPlayer;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._animateFailure = stateFlowImplMutableStateFlow;
        this.animateFailure = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.authenticationRequests = ChannelKt.Channel$default(-2, null, null, 6);
    }

    public void onSuccessfulAuthentication() {
    }
}
