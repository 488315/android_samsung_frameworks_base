package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperStateRepository {
    public final StateFlowImpl _state;
    public final CoroutineDispatcher backgroundDispatcher;
    public final InputManager inputManager;
    public final ReadonlyStateFlow state;

    public ShortcutHelperStateRepository(InputManager inputManager, CoroutineDispatcher coroutineDispatcher) {
        this.inputManager = inputManager;
        this.backgroundDispatcher = coroutineDispatcher;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(ShortcutHelperState.Inactive.INSTANCE);
        this._state = MutableStateFlow;
        this.state = FlowKt.asStateFlow(MutableStateFlow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r5v11, types: [kotlinx.coroutines.flow.MutableStateFlow] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object show(java.lang.Integer r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$show$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$show$1 r0 = (com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$show$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$show$1 r0 = new com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$show$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.flow.MutableStateFlow r5 = (kotlinx.coroutines.flow.MutableStateFlow) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L55
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.flow.StateFlowImpl r7 = r5._state
            if (r6 == 0) goto L3f
            int r5 = r6.intValue()
            goto L5d
        L3f:
            r0.L$0 = r7
            r0.label = r3
            com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$findPhysicalKeyboardId$2 r6 = new com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository$findPhysicalKeyboardId$2
            r2 = 0
            r6.<init>(r5, r2)
            kotlinx.coroutines.CoroutineDispatcher r5 = r5.backgroundDispatcher
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r0)
            if (r5 != r1) goto L52
            return r1
        L52:
            r4 = r7
            r7 = r5
            r5 = r4
        L55:
            java.lang.Number r7 = (java.lang.Number) r7
            int r6 = r7.intValue()
            r7 = r5
            r5 = r6
        L5d:
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState$Active r6 = new com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState$Active
            r6.<init>(r5)
            r7.setValue(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository.show(java.lang.Integer, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
