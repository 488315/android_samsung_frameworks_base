package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.view.KeyEvent;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.input.key.KeyEventType;
import com.android.bouncer.ui.SettingsInteractor;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PasswordBouncerViewModel extends AuthMethodBouncerViewModel {
    public static final Companion Companion = new Companion(null);
    public static final long DELAY_TO_FETCH_IMES;
    public final StateFlowImpl _entryBackgroundColor;
    public final StateFlowImpl _isImeSwitcherButtonVisible;
    public final StateFlowImpl _isTextFieldFocusRequested;
    public final StateFlowImpl _isWhiteBg;
    public final StateFlowImpl _password;
    public final StateFlowImpl _selectedUserId;
    public final StateFlowImpl _textColor;
    public final Context applicationContext;
    public final AuthenticationMethodModel.Password authenticationMethod;
    public final ConnectedDisplayInteractor connectedDisplayInteractor;
    public final ReadonlyStateFlow entryBackgroundColor;
    public final InputMethodInteractor inputMethodInteractor;
    public boolean isExternalDesktopWindowing;
    public final ReadonlyStateFlow isImeSwitcherButtonVisible;
    public final ReadonlyStateFlow isShowLastPassword;
    public final ReadonlyStateFlow isTextFieldFocusRequested;
    public final StateFlowImpl isTextFieldFocused;
    public final ReadonlyStateFlow isWhiteBg;
    public final KeyguardViewMediator keyguardViewMediator;
    public final Function0 onIntentionalUserInput;
    public final ReadonlyStateFlow password;
    public final BufferedChannel requests;
    public final ReadonlyStateFlow selectedUserId;
    public final SelectedUserInteractor selectedUserInteractor;
    public final ReadonlyStateFlow textColor;
    public final WallpaperManager wallpaperManager;
    public boolean wasSuccessfullyAuthenticated;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: getDELAY_TO_FETCH_IMES-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m1057getDELAY_TO_FETCH_IMESUwyO8pc$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        PasswordBouncerViewModel create(StateFlow stateFlow, Function0 function0);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnImeDismissed implements Request {
        public static final OnImeDismissed INSTANCE = new OnImeDismissed();

        private OnImeDismissed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OnImeDismissed);
        }

        public final int hashCode() {
            return -1774139777;
        }

        public final String toString() {
            return "OnImeDismissed";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnImeSwitcherButtonClicked implements Request {
        public final int displayId;

        public OnImeSwitcherButtonClicked(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnImeSwitcherButtonClicked) && this.displayId == ((OnImeSwitcherButtonClicked) obj).displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("OnImeSwitcherButtonClicked(displayId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Request {
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DELAY_TO_FETCH_IMES = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
    }

    public PasswordBouncerViewModel(BouncerInteractor bouncerInteractor, InputMethodInteractor inputMethodInteractor, SelectedUserInteractor selectedUserInteractor, StateFlow stateFlow, Function0 function0, Context context, UpdateInteractor updateInteractor, SettingsInteractor settingsInteractor, WallpaperManager wallpaperManager, KeyguardViewMediator keyguardViewMediator, ConnectedDisplayInteractor connectedDisplayInteractor) {
        super(bouncerInteractor, stateFlow, "PasswordBouncerViewModel", null, 8, null);
        this.inputMethodInteractor = inputMethodInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.onIntentionalUserInput = function0;
        this.applicationContext = context;
        this.wallpaperManager = wallpaperManager;
        this.keyguardViewMediator = keyguardViewMediator;
        this.connectedDisplayInteractor = connectedDisplayInteractor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._password = MutableStateFlow;
        this.password = FlowKt.asStateFlow(MutableStateFlow);
        this.authenticationMethod = AuthenticationMethodModel.Password.INSTANCE;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isImeSwitcherButtonVisible = MutableStateFlow2;
        this.isImeSwitcherButtonVisible = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this.isTextFieldFocused = MutableStateFlow3;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(Boolean.valueOf(((Boolean) stateFlow.getValue()).booleanValue() && !((Boolean) MutableStateFlow3.getValue()).booleanValue()));
        this._isTextFieldFocusRequested = MutableStateFlow4;
        this.isTextFieldFocusRequested = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(Integer.valueOf(selectedUserInteractor.getSelectedUserId()));
        this._selectedUserId = MutableStateFlow5;
        this.selectedUserId = FlowKt.asStateFlow(MutableStateFlow5);
        this.requests = ChannelKt.Channel$default(-2, null, null, 6);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isWhiteBg = MutableStateFlow6;
        this.isWhiteBg = FlowKt.asStateFlow(MutableStateFlow6);
        this.isShowLastPassword = settingsInteractor.isShowLastPassword;
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_password_entry_background_color))));
        this._entryBackgroundColor = MutableStateFlow7;
        this.entryBackgroundColor = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_password_text_color))));
        this._textColor = MutableStateFlow8;
        this.textColor = FlowKt.asStateFlow(MutableStateFlow8);
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void clearInput() {
        this._password.updateState(null, "");
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final List getInput() {
        char[] charArray = ((String) this._password.getValue()).toCharArray();
        int length = charArray.length;
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        if (length == 1) {
            return Collections.singletonList(Character.valueOf(charArray[0]));
        }
        ArrayList arrayList = new ArrayList(charArray.length);
        for (char c : charArray) {
            arrayList.add(Character.valueOf(c));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel, com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            java.lang.Object r4 = r0.L$0
            com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel r4 = (com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel) r4
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L33
            goto L49
        L33:
            r5 = move-exception
            goto L4f
        L35:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2 r5 = new com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2     // Catch: java.lang.Throwable -> L33
            r2 = 0
            r5.<init>(r4, r2)     // Catch: java.lang.Throwable -> L33
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L33
            r0.label = r3     // Catch: java.lang.Throwable -> L33
            java.lang.Object r5 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)     // Catch: java.lang.Throwable -> L33
            if (r5 != r1) goto L49
            return r1
        L49:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L33
            r5.<init>()     // Catch: java.lang.Throwable -> L33
            throw r5     // Catch: java.lang.Throwable -> L33
        L4f:
            r0 = 0
            r4.wasSuccessfullyAuthenticated = r0
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    /* renamed from: onKeyEvent-uiMRsoQ */
    public final boolean mo1056onKeyEventuiMRsoQ(int i, int i2) {
        if (!KeyEvent.isConfirmKey(i2) || i2 == 62) {
            return false;
        }
        KeyEventType.Companion.getClass();
        if (i != KeyEventType.KeyUp) {
            return false;
        }
        ComposeBouncerFlags.INSTANCE.getClass();
        return false;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void onSuccessfulAuthentication() {
        this.wasSuccessfullyAuthenticated = true;
    }
}
