package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.view.KeyEvent;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.input.key.KeyEventType;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.keyguard.PinShapeAdapter;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PinBouncerViewModel extends AuthMethodBouncerViewModel {
    public final StateFlowImpl _backspaceButtonAppearance;
    public final StateFlowImpl _confirmButtonAppearance;
    public final StateFlowImpl _digitButtonBackgroundColor;
    public final StateFlowImpl _digitButtonBackgroundColorAlpha;
    public final StateFlowImpl _digitButtonContentColor;
    public final StateFlowImpl _dotColor;
    public final StateFlowImpl _hintedPinLength;
    public final StateFlowImpl _isDigitButtonAnimationEnabled;
    public final StateFlowImpl _isWhiteBg;
    public final Context applicationContext;
    public final AuthenticationMethodModel authenticationMethod;
    public final ReadonlyStateFlow backspaceButtonAppearance;
    public final ReadonlyStateFlow confirmButtonAppearance;
    public final PinBouncerViewModel$special$$inlined$map$1 confirmButtonEnabled;
    public final ReadonlyStateFlow digitButtonBackgroundColor;
    public final ReadonlyStateFlow digitButtonBackgroundColorAlpha;
    public final ReadonlyStateFlow digitButtonContentColor;
    public final StateFlowImpl dotColor;
    public final ReadonlyStateFlow errorDialogMessage;
    public final ReadonlyStateFlow hintedPinLength;
    public final ReadonlyStateFlow isDigitButtonAnimationEnabled;
    public final ReadonlyStateFlow isLockedEsim;
    public final boolean isSimAreaVisible;
    public final StateFlowImpl isSimUnlockingDialogVisible;
    public final ReadonlyStateFlow isWhiteBg;
    public final StateFlowImpl mutablePinInput;
    public final Function0 onIntentionalUserInput;
    public final StateFlowImpl pinInput;
    public final PinShapeAdapter pinShapes;
    public final BufferedChannel requests;
    public final SimBouncerInteractor simBouncerInteractor;
    public final WallpaperManager wallpaperManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        PinBouncerViewModel create(StateFlow stateFlow, Function0 function0, AuthenticationMethodModel authenticationMethodModel, BouncerHapticPlayer bouncerHapticPlayer);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnAuthenticateButtonClickedForSim implements Request {
        public static final OnAuthenticateButtonClickedForSim INSTANCE = new OnAuthenticateButtonClickedForSim();

        private OnAuthenticateButtonClickedForSim() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OnAuthenticateButtonClickedForSim);
        }

        public final int hashCode() {
            return 1881040527;
        }

        public final String toString() {
            return "OnAuthenticateButtonClickedForSim";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnErrorDialogDismissed implements Request {
        public static final OnErrorDialogDismissed INSTANCE = new OnErrorDialogDismissed();

        private OnErrorDialogDismissed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OnErrorDialogDismissed);
        }

        public final int hashCode() {
            return 533298264;
        }

        public final String toString() {
            return "OnErrorDialogDismissed";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Request {
    }

    /* JADX WARN: Type inference failed for: r9v8, types: [com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1] */
    public PinBouncerViewModel(Context context, BouncerInteractor bouncerInteractor, SimBouncerInteractor simBouncerInteractor, BouncerHapticPlayer bouncerHapticPlayer, StateFlow stateFlow, Function0 function0, AuthenticationMethodModel authenticationMethodModel, UpdateInteractor updateInteractor, WallpaperManager wallpaperManager) {
        super(bouncerInteractor, stateFlow, "PinBouncerViewModel", bouncerHapticPlayer, null);
        this.applicationContext = context;
        this.simBouncerInteractor = simBouncerInteractor;
        this.onIntentionalUserInput = function0;
        this.authenticationMethod = authenticationMethodModel;
        this.wallpaperManager = wallpaperManager;
        this.isSimAreaVisible = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Sim.INSTANCE);
        this.isLockedEsim = simBouncerInteractor.isLockedEsim;
        this.errorDialogMessage = simBouncerInteractor.errorDialogMessage;
        Boolean bool = Boolean.FALSE;
        this.isSimUnlockingDialogVisible = StateFlowKt.MutableStateFlow(bool);
        this.pinShapes = new PinShapeAdapter(context);
        PinInputViewModel.Companion.getClass();
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new PinInputViewModel(Collections.singletonList(new EntryToken.ClearAll(0, 1, null))));
        this.mutablePinInput = MutableStateFlow;
        this.pinInput = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._hintedPinLength = MutableStateFlow2;
        this.hintedPinLength = FlowKt.asStateFlow(MutableStateFlow2);
        ActionButtonAppearance actionButtonAppearance = ActionButtonAppearance.Hidden;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(actionButtonAppearance);
        this._backspaceButtonAppearance = MutableStateFlow3;
        this.backspaceButtonAppearance = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(actionButtonAppearance);
        this._confirmButtonAppearance = MutableStateFlow4;
        this.confirmButtonAppearance = FlowKt.asStateFlow(MutableStateFlow4);
        this.confirmButtonEnabled = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel) r5
                        java.util.List r5 = r5.getPin()
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._isWhiteBg = MutableStateFlow5;
        this.isWhiteBg = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pattern_dot_color))));
        this._dotColor = MutableStateFlow6;
        this.dotColor = MutableStateFlow6;
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pin_background_color))));
        this._digitButtonBackgroundColor = MutableStateFlow7;
        this.digitButtonBackgroundColor = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(Float.valueOf(0.2f));
        this._digitButtonBackgroundColorAlpha = MutableStateFlow8;
        this.digitButtonBackgroundColorAlpha = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pin_background_color))));
        this._digitButtonContentColor = MutableStateFlow9;
        this.digitButtonContentColor = FlowKt.asStateFlow(MutableStateFlow9);
        this.requests = ChannelKt.Channel$default(-2, null, null, 6);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!((Boolean) bouncerInteractor.isPinEnhancedPrivacyEnabled.$$delegate_0.getValue()).booleanValue()));
        this._isDigitButtonAnimationEnabled = MutableStateFlow10;
        this.isDigitButtonAnimationEnabled = FlowKt.asStateFlow(MutableStateFlow10);
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void clearInput() {
        StateFlowImpl stateFlowImpl = this.mutablePinInput;
        stateFlowImpl.updateState(null, ((PinInputViewModel) stateFlowImpl.getValue()).clearAll());
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final List getInput() {
        return ((PinInputViewModel) this.mutablePinInput.getValue()).getPin();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel, com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2 r5 = new com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void onAuthenticateButtonClicked() {
        if (Intrinsics.areEqual(this.authenticationMethod, AuthenticationMethodModel.Sim.INSTANCE)) {
            ChannelResult.m3457boximpl(this.requests.mo3456trySendJP2dKIU(OnAuthenticateButtonClickedForSim.INSTANCE));
        } else {
            AuthMethodBouncerViewModel.tryAuthenticate$default(this, null, false, 1);
        }
    }

    public final void onBackspaceButtonClicked() {
        StateFlowImpl stateFlowImpl = this.mutablePinInput;
        PinInputViewModel pinInputViewModel = (PinInputViewModel) stateFlowImpl.getValue();
        if (!(CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll)) {
            pinInputViewModel = new PinInputViewModel(CollectionsKt___CollectionsKt.take(pinInputViewModel.input, r0.size() - 1));
        }
        stateFlowImpl.updateState(null, pinInputViewModel);
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    /* renamed from: onKeyEvent-uiMRsoQ */
    public final boolean mo1056onKeyEventuiMRsoQ(int i, int i2) {
        KeyEventType.Companion companion = KeyEventType.Companion;
        companion.getClass();
        if (i == KeyEventType.KeyUp) {
            if (!KeyEvent.isConfirmKey(i2)) {
                return false;
            }
            onAuthenticateButtonClicked();
            return true;
        }
        companion.getClass();
        if (i == KeyEventType.KeyDown) {
            if (i2 == 67) {
                onBackspaceButtonClicked();
                return true;
            }
            if (7 <= i2 && i2 < 17) {
                onPinButtonClicked(i2 - 7);
                return true;
            }
            if (144 <= i2 && i2 < 154) {
                onPinButtonClicked(i2 - 144);
                return true;
            }
        }
        return false;
    }

    public final void onPinButtonClicked(int i) {
        StateFlowImpl stateFlowImpl = this.mutablePinInput;
        PinInputViewModel pinInputViewModel = (PinInputViewModel) stateFlowImpl.getValue();
        this.onIntentionalUserInput.invoke();
        Integer num = (Integer) this.hintedPinLength.$$delegate_0.getValue();
        if (((ArrayList) pinInputViewModel.getPin()).size() < (num != null ? num.intValue() : Integer.MAX_VALUE)) {
            stateFlowImpl.updateState(null, pinInputViewModel.append(i));
            AuthMethodBouncerViewModel.tryAuthenticate$default(this, null, true, 1);
        }
    }
}
