package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.qs.pipeline.shared.TileSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MutableSelectionState {
    public final MutableState selection$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState placementEnabled$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    public final MutableState placementEvent$delegate = SnapshotStateKt.mutableStateOf$default(null);

    public final boolean getPlacementEnabled() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.placementEnabled$delegate).getValue()).booleanValue();
    }

    public final TileSpec getSelection() {
        return (TileSpec) ((SnapshotMutableStateImpl) this.selection$delegate).getValue();
    }

    public final void setPlacementEnabled(boolean z) {
        ((SnapshotMutableStateImpl) this.placementEnabled$delegate).setValue(Boolean.valueOf(z));
    }

    public final void setSelection(TileSpec tileSpec) {
        ((SnapshotMutableStateImpl) this.selection$delegate).setValue(tileSpec);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tileStateFor(com.android.systemui.qs.pipeline.shared.TileSpec r5, com.android.systemui.qs.panels.ui.compose.selection.TileState r6, boolean r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r4 = this;
            boolean r0 = r8 instanceof com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState$tileStateFor$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState$tileStateFor$1 r0 = (com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState$tileStateFor$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState$tileStateFor$1 r0 = new com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState$tileStateFor$1
            r0.<init>(r4, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r8)
            goto L69
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r8)
            boolean r8 = r4.getPlacementEnabled()
            if (r8 == 0) goto L45
            com.android.systemui.qs.pipeline.shared.TileSpec r8 = r4.getSelection()
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r5)
            if (r8 == 0) goto L45
            com.android.systemui.qs.panels.ui.compose.selection.TileState r4 = com.android.systemui.qs.panels.ui.compose.selection.TileState.Placeable
            return r4
        L45:
            boolean r8 = r4.getPlacementEnabled()
            if (r8 == 0) goto L4e
            com.android.systemui.qs.panels.ui.compose.selection.TileState r4 = com.android.systemui.qs.panels.ui.compose.selection.TileState.GreyedOut
            return r4
        L4e:
            com.android.systemui.qs.pipeline.shared.TileSpec r4 = r4.getSelection()
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L6c
            com.android.systemui.qs.panels.ui.compose.selection.TileState r4 = com.android.systemui.qs.panels.ui.compose.selection.TileState.None
            if (r6 != r4) goto L69
            if (r7 == 0) goto L69
            r0.label = r3
            r4 = 250(0xfa, double:1.235E-321)
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r4, r0)
            if (r4 != r1) goto L69
            return r1
        L69:
            com.android.systemui.qs.panels.ui.compose.selection.TileState r4 = com.android.systemui.qs.panels.ui.compose.selection.TileState.Selected
            return r4
        L6c:
            if (r7 == 0) goto L71
            com.android.systemui.qs.panels.ui.compose.selection.TileState r4 = com.android.systemui.qs.panels.ui.compose.selection.TileState.Removable
            return r4
        L71:
            com.android.systemui.qs.panels.ui.compose.selection.TileState r4 = com.android.systemui.qs.panels.ui.compose.selection.TileState.None
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState.tileStateFor(com.android.systemui.qs.pipeline.shared.TileSpec, com.android.systemui.qs.panels.ui.compose.selection.TileState, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void unSelect() {
        setSelection(null);
        setPlacementEnabled(false);
    }
}
