package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class AuthMethodBouncerViewModel {
    public final StateFlowImpl _animateFailure;
    public final ReadonlyStateFlow animateFailure;
    public final BouncerInteractor interactor;
    public final StateFlow isInputEnabled;
    public final CoroutineScope viewModelScope;

    public /* synthetic */ AuthMethodBouncerViewModel(CoroutineScope coroutineScope, BouncerInteractor bouncerInteractor, StateFlow stateFlow, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, bouncerInteractor, stateFlow);
    }

    public abstract AuthenticationMethodModel getAuthenticationMethod();

    private AuthMethodBouncerViewModel(CoroutineScope coroutineScope, BouncerInteractor bouncerInteractor, StateFlow stateFlow) {
        this.viewModelScope = coroutineScope;
        FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(Boolean.FALSE));
    }
}
