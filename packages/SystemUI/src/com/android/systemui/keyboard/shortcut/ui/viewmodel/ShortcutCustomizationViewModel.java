package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import android.content.Context;
import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key;
import com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutCustomizationViewModel extends ExclusiveActivatable {
    public static final List SUPPORTED_MODIFIERS;
    public final StateFlowImpl _shortcutCustomizationUiState;
    public final Context context;
    public KeyEvent keyDownEventCache;
    public final ShortcutCustomizationInteractor shortcutCustomizationInteractor;
    public final ReadonlyStateFlow shortcutCustomizationUiState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ShortcutCustomizationViewModel create();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ShortcutCustomizationRequestResult.values().length];
            try {
                iArr[ShortcutCustomizationRequestResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ShortcutCustomizationRequestResult.ERROR_RESERVED_COMBINATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ShortcutCustomizationRequestResult.ERROR_OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        Key.Companion companion = Key.Companion;
        companion.getClass();
        Key m575boximpl = Key.m575boximpl(Key.MetaLeft);
        companion.getClass();
        Key m575boximpl2 = Key.m575boximpl(Key.MetaRight);
        companion.getClass();
        Key m575boximpl3 = Key.m575boximpl(Key.CtrlRight);
        companion.getClass();
        Key m575boximpl4 = Key.m575boximpl(Key.CtrlLeft);
        companion.getClass();
        Key m575boximpl5 = Key.m575boximpl(Key.AltLeft);
        companion.getClass();
        Key m575boximpl6 = Key.m575boximpl(Key.AltRight);
        companion.getClass();
        Key m575boximpl7 = Key.m575boximpl(Key.ShiftLeft);
        companion.getClass();
        Key m575boximpl8 = Key.m575boximpl(Key.ShiftRight);
        companion.getClass();
        Key m575boximpl9 = Key.m575boximpl(Key.Function);
        companion.getClass();
        SUPPORTED_MODIFIERS = Arrays.asList(m575boximpl, m575boximpl2, m575boximpl3, m575boximpl4, m575boximpl5, m575boximpl6, m575boximpl7, m575boximpl8, m575boximpl9, Key.m575boximpl(Key.Symbol));
    }

    public ShortcutCustomizationViewModel(Context context, ShortcutCustomizationInteractor shortcutCustomizationInteractor) {
        this.context = context;
        this.shortcutCustomizationInteractor = shortcutCustomizationInteractor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(ShortcutCustomizationUiState.Inactive.INSTANCE);
        this._shortcutCustomizationUiState = MutableStateFlow;
        this.shortcutCustomizationUiState = FlowKt.asStateFlow(MutableStateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x005e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getErrorMessageForPressedKeys(com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel r5, java.util.List r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1
            r0.<init>(r5, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            boolean r5 = r0.Z$0
            java.lang.Object r6 = r0.L$0
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel r6 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L55
        L30:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L38:
            kotlin.ResultKt.throwOnFailure(r7)
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            boolean r6 = r6.isEmpty()
            r0.L$0 = r5
            r0.Z$0 = r6
            r0.label = r3
            com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor r7 = r5.shortcutCustomizationInteractor
            com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository r7 = r7.customShortcutRepository
            java.lang.Object r7 = r7.isSelectedKeyCombinationAvailable(r0)
            if (r7 != r1) goto L52
            return r1
        L52:
            r4 = r6
            r6 = r5
            r5 = r4
        L55:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            r5 = r5 | r7
            if (r5 == 0) goto L61
            java.lang.String r5 = ""
            return r5
        L61:
            android.content.Context r5 = r6.context
            r6 = 2131956735(0x7f1313ff, float:1.9550034E38)
            java.lang.String r5 = r5.getString(r6)
            r5.getClass()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.access$getErrorMessageForPressedKeys(com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel, java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object deleteShortcutCurrentlyBeingCustomized(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$deleteShortcutCurrentlyBeingCustomized$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$deleteShortcutCurrentlyBeingCustomized$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$deleteShortcutCurrentlyBeingCustomized$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$deleteShortcutCurrentlyBeingCustomized$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$deleteShortcutCurrentlyBeingCustomized$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel r5 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L45
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r3
            com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor r6 = r5.shortcutCustomizationInteractor
            com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository r6 = r6.customShortcutRepository
            java.lang.Object r6 = r6.deleteShortcutCurrentlyBeingCustomized(r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult r6 = (com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult) r6
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5._shortcutCustomizationUiState
        L49:
            java.lang.Object r0 = r5.getValue()
            r1 = r0
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState r1 = (com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState) r1
            int[] r2 = com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.WhenMappings.$EnumSwitchMapping$0
            int r4 = r6.ordinal()
            r2 = r2[r4]
            if (r2 != r3) goto L5c
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$Inactive r1 = com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.Inactive.INSTANCE
        L5c:
            boolean r0 = r5.compareAndSet(r0, r1)
            if (r0 == 0) goto L49
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.deleteShortcutCurrentlyBeingCustomized(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$1
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
            goto L46
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor r5 = r4.shortcutCustomizationInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.pressedKeys
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$2 r2 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$2
            r2.<init>(r4)
            r0.label = r3
            kotlinx.coroutines.flow.StateFlow r4 = r5.$$delegate_0
            java.lang.Object r4 = r4.collect(r2, r0)
            if (r4 != r1) goto L46
            return r1
        L46:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onSetShortcut(kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onSetShortcut$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onSetShortcut$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onSetShortcut$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onSetShortcut$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onSetShortcut$1
            r0.<init>(r8, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r8 = r0.L$0
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel r8 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L45
        L2b:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L33:
            kotlin.ResultKt.throwOnFailure(r9)
            r0.L$0 = r8
            r0.label = r3
            com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor r9 = r8.shortcutCustomizationInteractor
            com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository r9 = r9.customShortcutRepository
            java.lang.Object r9 = r9.confirmAndSetShortcutCurrentlyBeingCustomized(r0)
            if (r9 != r1) goto L45
            return r1
        L45:
            com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult r9 = (com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult) r9
            kotlinx.coroutines.flow.StateFlowImpl r0 = r8._shortcutCustomizationUiState
        L49:
            java.lang.Object r1 = r0.getValue()
            r2 = r1
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState r2 = (com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState) r2
            int[] r4 = com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.WhenMappings.$EnumSwitchMapping$0
            int r5 = r9.ordinal()
            r4 = r4[r5]
            if (r4 == r3) goto L9b
            r5 = 2
            r6 = 29
            r7 = 0
            if (r4 == r5) goto L82
            r5 = 3
            if (r4 != r5) goto L7c
            android.content.Context r4 = r8.context
            r5 = 2131956734(0x7f1313fe, float:1.9550032E38)
            java.lang.String r4 = r4.getString(r5)
            boolean r5 = r2 instanceof com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.AddShortcutDialog
            if (r5 == 0) goto L74
            r5 = r2
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$AddShortcutDialog r5 = (com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.AddShortcutDialog) r5
            goto L75
        L74:
            r5 = r7
        L75:
            if (r5 == 0) goto L9d
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$AddShortcutDialog r2 = com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.AddShortcutDialog.copy$default(r5, r4, r7, r7, r6)
            goto L9d
        L7c:
            kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
            r8.<init>()
            throw r8
        L82:
            android.content.Context r4 = r8.context
            r5 = 2131956735(0x7f1313ff, float:1.9550034E38)
            java.lang.String r4 = r4.getString(r5)
            boolean r5 = r2 instanceof com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.AddShortcutDialog
            if (r5 == 0) goto L93
            r5 = r2
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$AddShortcutDialog r5 = (com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.AddShortcutDialog) r5
            goto L94
        L93:
            r5 = r7
        L94:
            if (r5 == 0) goto L9d
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$AddShortcutDialog r2 = com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.AddShortcutDialog.copy$default(r5, r4, r7, r7, r6)
            goto L9d
        L9b:
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$Inactive r2 = com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.Inactive.INSTANCE
        L9d:
            boolean r1 = r0.compareAndSet(r1, r2)
            if (r1 == 0) goto L49
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.onSetShortcut(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object resetAllCustomShortcuts(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$resetAllCustomShortcuts$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$resetAllCustomShortcuts$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$resetAllCustomShortcuts$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$resetAllCustomShortcuts$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$resetAllCustomShortcuts$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel r5 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L45
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r3
            com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor r6 = r5.shortcutCustomizationInteractor
            com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository r6 = r6.customShortcutRepository
            java.lang.Object r6 = r6.resetAllCustomShortcuts(r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult r6 = (com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult) r6
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5._shortcutCustomizationUiState
        L49:
            java.lang.Object r0 = r5.getValue()
            r1 = r0
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState r1 = (com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState) r1
            int[] r2 = com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.WhenMappings.$EnumSwitchMapping$0
            int r4 = r6.ordinal()
            r2 = r2[r4]
            if (r2 != r3) goto L5c
            com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState$Inactive r1 = com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState.Inactive.INSTANCE
        L5c:
            boolean r0 = r5.compareAndSet(r0, r1)
            if (r0 == 0) goto L49
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel.resetAllCustomShortcuts(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
