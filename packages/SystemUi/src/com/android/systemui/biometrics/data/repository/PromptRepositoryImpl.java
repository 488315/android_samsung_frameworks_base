package com.android.systemui.biometrics.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PromptRepositoryImpl implements PromptRepository {
    public final StateFlowImpl _challenge;
    public final StateFlowImpl _isConfirmationRequired;
    public final StateFlowImpl _kind;
    public final StateFlowImpl _promptInfo;
    public final StateFlowImpl _userId;
    public final AuthController authController;
    public final ReadonlyStateFlow challenge;
    public final ReadonlyStateFlow kind;
    public final ReadonlyStateFlow promptInfo;
    public final ReadonlyStateFlow userId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public PromptRepositoryImpl(AuthController authController) {
        this.authController = authController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        PromptRepositoryImpl$isShowing$1 promptRepositoryImpl$isShowing$1 = new PromptRepositoryImpl$isShowing$1(this, null);
        conflatedCallbackFlow.getClass();
        ConflatedCallbackFlow.conflatedCallbackFlow(promptRepositoryImpl$isShowing$1);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._promptInfo = MutableStateFlow;
        this.promptInfo = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._challenge = MutableStateFlow2;
        this.challenge = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._userId = MutableStateFlow3;
        this.userId = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(new PromptKind.Biometric(null, 1, null));
        this._kind = MutableStateFlow4;
        this.kind = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isConfirmationRequired = MutableStateFlow5;
        FlowKt.asStateFlow(MutableStateFlow5);
    }
}
