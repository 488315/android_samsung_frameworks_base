package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AuthMethodBouncerViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _animateFailure;
    public final ReadonlyStateFlow animateFailure;
    public final BufferedChannel authenticationRequests;
    public final BouncerHapticPlayer bouncerHapticPlayer;
    public final BouncerInteractor interactor;
    public final StateFlow isInputEnabled;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public /* synthetic */ AuthMethodBouncerViewModel(BouncerInteractor bouncerInteractor, StateFlow stateFlow, String str, BouncerHapticPlayer bouncerHapticPlayer, DefaultConstructorMarker defaultConstructorMarker) {
        this(bouncerInteractor, stateFlow, str, bouncerHapticPlayer);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0054, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0056, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.collectLatest(r7, r2, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static kotlin.coroutines.intrinsics.CoroutineSingletons onActivated$suspendImpl(com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2e:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L57
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.BufferedChannel r7 = r6.authenticationRequests
            kotlinx.coroutines.flow.ChannelAsFlow r7 = kotlinx.coroutines.flow.FlowKt.receiveAsFlow(r7)
            com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$2 r2 = new com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel$onActivated$2
            r5 = 0
            r2.<init>(r6, r5)
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.collectLatest(r7, r2, r0)
            if (r6 != r1) goto L4e
            goto L56
        L4e:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r6 != r1) goto L57
        L56:
            return r1
        L57:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel.onActivated$suspendImpl(com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    public static void tryAuthenticate$default(AuthMethodBouncerViewModel authMethodBouncerViewModel, List list, boolean z, int i) {
        if ((i & 1) != 0) {
            list = authMethodBouncerViewModel.getInput();
        }
        if ((i & 2) != 0) {
            z = false;
        }
        authMethodBouncerViewModel.authenticationRequests.mo3456trySendJP2dKIU(new AuthenticationRequest(list, z));
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
    public boolean mo1056onKeyEventuiMRsoQ(int i, int i2) {
        return false;
    }

    public /* synthetic */ AuthMethodBouncerViewModel(BouncerInteractor bouncerInteractor, StateFlow stateFlow, String str, BouncerHapticPlayer bouncerHapticPlayer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bouncerInteractor, stateFlow, str, (i & 8) != 0 ? null : bouncerHapticPlayer, null);
    }

    private AuthMethodBouncerViewModel(BouncerInteractor bouncerInteractor, StateFlow stateFlow, String str, BouncerHapticPlayer bouncerHapticPlayer) {
        this.interactor = bouncerInteractor;
        this.isInputEnabled = stateFlow;
        this.bouncerHapticPlayer = bouncerHapticPlayer;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._animateFailure = MutableStateFlow;
        this.animateFailure = FlowKt.asStateFlow(MutableStateFlow);
        this.authenticationRequests = ChannelKt.Channel$default(-2, null, null, 6);
    }

    public void onSuccessfulAuthentication() {
    }
}
