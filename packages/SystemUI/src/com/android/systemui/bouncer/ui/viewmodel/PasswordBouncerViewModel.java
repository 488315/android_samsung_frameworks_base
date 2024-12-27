package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PasswordBouncerViewModel extends AuthMethodBouncerViewModel {
    public static final Companion Companion = new Companion(null);
    public static final long DELAY_TO_FETCH_IMES;
    public final StateFlowImpl _password;
    public final AuthenticationMethodModel.Password authenticationMethod;
    public final InputMethodInteractor inputMethodInteractor;
    public final ReadonlyStateFlow isImeSwitcherButtonVisible;
    public final ReadonlyStateFlow isTextFieldFocusRequested;
    public final StateFlowImpl isTextFieldFocused;
    public final Function0 onIntentionalUserInput;
    public final ReadonlyStateFlow password;
    public final ReadonlyStateFlow selectedUserId;
    public final SelectedUserInteractor selectedUserInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getDELAY_TO_FETCH_IMES-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m931getDELAY_TO_FETCH_IMESUwyO8pc$annotations() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DELAY_TO_FETCH_IMES = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
    }

    public PasswordBouncerViewModel(CoroutineScope coroutineScope, StateFlow stateFlow, BouncerInteractor bouncerInteractor, Function0 function0, InputMethodInteractor inputMethodInteractor, SelectedUserInteractor selectedUserInteractor) {
        super(coroutineScope, bouncerInteractor, stateFlow, null);
        this.inputMethodInteractor = inputMethodInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(""));
        this.authenticationMethod = AuthenticationMethodModel.Password.INSTANCE;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        BuildersKt.launch$default(this.viewModelScope, null, null, new PasswordBouncerViewModel$imeSwitcherRefreshingFlow$1(this, MutableStateFlow, null), 3);
        FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow, MutableStateFlow2, new PasswordBouncerViewModel$isTextFieldFocusRequested$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((Boolean) stateFlow.getValue()).booleanValue() && !((Boolean) MutableStateFlow2.getValue()).booleanValue()));
        FlowKt.stateIn(selectedUserInteractor.selectedUser, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Integer.valueOf(selectedUserInteractor.getSelectedUserId(false)));
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }
}
