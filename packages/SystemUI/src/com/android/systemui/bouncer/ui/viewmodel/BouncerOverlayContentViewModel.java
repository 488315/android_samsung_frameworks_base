package com.android.systemui.bouncer.ui.viewmodel;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerOverlayContentViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _actionButton;
    public final StateFlowImpl _authMethodViewModel;
    public final StateFlowImpl _dialogViewModel;
    public final StateFlowImpl _isFoldSplitRequired;
    public final StateFlowImpl _isInputEnabled;
    public final StateFlowImpl _isInputPreferredOnLeftSide;
    public final StateFlowImpl _isOneHandedModeSupported;
    public final StateFlowImpl _isUserSwitcherVisible;
    public final StateFlowImpl _selectedUserImage;
    public final StateFlowImpl _userSwitcherDropdown;
    public final ReadonlyStateFlow actionButton;
    public final BouncerActionButtonInteractor actionButtonInteractor;
    public final Context applicationContext;
    public final ReadonlyStateFlow authMethodViewModel;
    public final AuthenticationInteractor authenticationInteractor;
    public final BouncerActionButtonInteractor bouncerActionButtonInteractor;
    public final BouncerHapticPlayer bouncerHapticPlayer;
    public final BouncerInteractor bouncerInteractor;
    public final BouncerMessageViewModel.Factory bouncerMessageViewModelFactory;
    public final DevicePolicyManager devicePolicyManager;
    public final ReadonlyStateFlow dialogViewModel;
    public final ReadonlyStateFlow isFoldSplitRequired;
    public final ReadonlyStateFlow isInputEnabled;
    public final ReadonlyStateFlow isInputPreferredOnLeftSide;
    public final ReadonlyStateFlow isOneHandedModeSupported;
    public final ReadonlyStateFlow isUserSwitcherVisible;
    public final KeyguardDismissActionInteractor keyguardDismissActionInteractor;
    public final KeyguardMediaKeyInteractor keyguardMediaKeyInteractor;
    public final StateFlowImpl lockoutDialogMessage;
    public final Lazy message$delegate;
    public final PasswordBouncerViewModel.Factory passwordViewModelFactory;
    public final PatternBouncerViewModel.Factory patternViewModelFactory;
    public final PinBouncerViewModel.Factory pinViewModelFactory;
    public final ReadonlyStateFlow scale;
    public final ReadonlyStateFlow selectedUserImage;
    public final UserSwitcherViewModel userSwitcher;
    public final ReadonlyStateFlow userSwitcherDropdown;
    public final StateFlowImpl wipeDialogMessage;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DialogViewModel {
        public final Function0 onDismiss;
        public final String text;

        public DialogViewModel(String str, Function0 function0) {
            this.text = str;
            this.onDismiss = function0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DialogViewModel)) {
                return false;
            }
            DialogViewModel dialogViewModel = (DialogViewModel) obj;
            return Intrinsics.areEqual(this.text, dialogViewModel.text) && Intrinsics.areEqual(this.onDismiss, dialogViewModel.onDismiss);
        }

        public final int hashCode() {
            return this.onDismiss.hashCode() + (this.text.hashCode() * 31);
        }

        public final String toString() {
            return "DialogViewModel(text=" + this.text + ", onDismiss=" + this.onDismiss + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        BouncerOverlayContentViewModel create();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UserSwitcherDropdownItemViewModel {
        public final Icon icon;
        public final Function0 onClick;
        public final Text text;

        public UserSwitcherDropdownItemViewModel(Icon icon, Text text, Function0 function0) {
            this.icon = icon;
            this.text = text;
            this.onClick = function0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UserSwitcherDropdownItemViewModel)) {
                return false;
            }
            UserSwitcherDropdownItemViewModel userSwitcherDropdownItemViewModel = (UserSwitcherDropdownItemViewModel) obj;
            return Intrinsics.areEqual(this.icon, userSwitcherDropdownItemViewModel.icon) && Intrinsics.areEqual(this.text, userSwitcherDropdownItemViewModel.text) && Intrinsics.areEqual(this.onClick, userSwitcherDropdownItemViewModel.onClick);
        }

        public final int hashCode() {
            return this.onClick.hashCode() + ((this.text.hashCode() + (this.icon.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "UserSwitcherDropdownItemViewModel(icon=" + this.icon + ", text=" + this.text + ", onClick=" + this.onClick + ")";
        }
    }

    public BouncerOverlayContentViewModel(Context context, BouncerInteractor bouncerInteractor, AuthenticationInteractor authenticationInteractor, DevicePolicyManager devicePolicyManager, BouncerMessageViewModel.Factory factory, UserSwitcherViewModel userSwitcherViewModel, BouncerActionButtonInteractor bouncerActionButtonInteractor, PinBouncerViewModel.Factory factory2, PatternBouncerViewModel.Factory factory3, PasswordBouncerViewModel.Factory factory4, BouncerHapticPlayer bouncerHapticPlayer, KeyguardMediaKeyInteractor keyguardMediaKeyInteractor, BouncerActionButtonInteractor bouncerActionButtonInteractor2, KeyguardDismissActionInteractor keyguardDismissActionInteractor) {
        this.applicationContext = context;
        this.bouncerInteractor = bouncerInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.devicePolicyManager = devicePolicyManager;
        this.bouncerMessageViewModelFactory = factory;
        this.userSwitcher = userSwitcherViewModel;
        this.actionButtonInteractor = bouncerActionButtonInteractor;
        this.pinViewModelFactory = factory2;
        this.patternViewModelFactory = factory3;
        this.passwordViewModelFactory = factory4;
        this.bouncerHapticPlayer = bouncerHapticPlayer;
        this.keyguardMediaKeyInteractor = keyguardMediaKeyInteractor;
        this.bouncerActionButtonInteractor = bouncerActionButtonInteractor2;
        this.keyguardDismissActionInteractor = keyguardDismissActionInteractor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._selectedUserImage = MutableStateFlow;
        this.selectedUserImage = FlowKt.asStateFlow(MutableStateFlow);
        this.message$delegate = LazyKt__LazyJVMKt.lazy(new BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(this, 2));
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._userSwitcherDropdown = MutableStateFlow2;
        this.userSwitcherDropdown = FlowKt.asStateFlow(MutableStateFlow2);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isUserSwitcherVisible = MutableStateFlow3;
        this.isUserSwitcherVisible = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._authMethodViewModel = MutableStateFlow4;
        this.authMethodViewModel = FlowKt.asStateFlow(MutableStateFlow4);
        this.lockoutDialogMessage = StateFlowKt.MutableStateFlow(null);
        this.wipeDialogMessage = StateFlowKt.MutableStateFlow(null);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(createDialogViewModel());
        this._dialogViewModel = MutableStateFlow5;
        this.dialogViewModel = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(null);
        this._actionButton = MutableStateFlow6;
        this.actionButton = FlowKt.asStateFlow(MutableStateFlow6);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._isOneHandedModeSupported = MutableStateFlow7;
        this.isOneHandedModeSupported = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._isInputPreferredOnLeftSide = MutableStateFlow8;
        this.isInputPreferredOnLeftSide = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!(((AuthMethodBouncerViewModel) r5.$$delegate_0.getValue()) instanceof PasswordBouncerViewModel)));
        this._isFoldSplitRequired = MutableStateFlow9;
        this.isFoldSplitRequired = FlowKt.asStateFlow(MutableStateFlow9);
        this.scale = bouncerInteractor.scale;
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) authenticationInteractor.repository;
        long lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(Boolean.valueOf((authenticationRepositoryImpl.clock.elapsedRealtime() < lockoutAttemptDeadline ? Long.valueOf(lockoutAttemptDeadline) : null) == null));
        this._isInputEnabled = MutableStateFlow10;
        this.isInputEnabled = FlowKt.asStateFlow(MutableStateFlow10);
    }

    public static final void access$onIntentionalUserInput(BouncerOverlayContentViewModel bouncerOverlayContentViewModel) {
        bouncerOverlayContentViewModel.getMessage().resetToDefault.tryEmit(Boolean.FALSE);
        BouncerInteractor bouncerInteractor = bouncerOverlayContentViewModel.bouncerInteractor;
        bouncerInteractor.deviceEntryFaceAuthInteractor.onPrimaryBouncerUserInput();
        PowerInteractor.onUserTouch$default(bouncerInteractor.powerInteractor);
        bouncerInteractor.falsingInteractor.collector.updateFalseConfidence(FalsingClassifier.Result.passed(0.6d));
    }

    public final DialogViewModel createDialogViewModel() {
        String str = (String) this.wipeDialogMessage.getValue();
        String str2 = (String) this.lockoutDialogMessage.getValue();
        if (str != null) {
            return new DialogViewModel(str, new BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(this, 0));
        }
        if (str2 != null) {
            return new DialogViewModel(str2, new BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(this, 1));
        }
        return null;
    }

    public final BouncerMessageViewModel getMessage() {
        return (BouncerMessageViewModel) this.message$delegate.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2b:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L50
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.bouncer.domain.interactor.BouncerInteractor r6 = r5.bouncerInteractor
            com.android.systemui.bouncer.data.repository.BouncerRepository r6 = r6.repository
            kotlinx.coroutines.flow.StateFlowImpl r6 = r6.scale
            r2 = 1065353216(0x3f800000, float:1.0)
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r4 = 0
            r6.updateState(r4, r2)
            com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2 r6 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2
            r6.<init>(r5, r4)
            r0.label = r3
            java.lang.Object r5 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0)
            if (r5 != r1) goto L50
            return r1
        L50:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
