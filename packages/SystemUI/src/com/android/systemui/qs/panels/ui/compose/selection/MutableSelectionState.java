package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes2.dex */
public final class MutableSelectionState {
    public final MutableState selection$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState placementEnabled$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    public final MutableState placementEvent$delegate = SnapshotStateKt.mutableStateOf$default(null);

    /* renamed from: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState$tileStateFor$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MutableSelectionState.this.tileStateFor(null, null, false, this);
        }
    }

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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object tileStateFor(TileSpec tileSpec, TileState tileState, boolean z, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (getPlacementEnabled() && Intrinsics.areEqual(getSelection(), tileSpec)) {
                return TileState.Placeable;
            }
            if (getPlacementEnabled()) {
                return TileState.GreyedOut;
            }
            if (!Intrinsics.areEqual(getSelection(), tileSpec)) {
                return z ? TileState.Removable : TileState.None;
            }
            if (tileState == TileState.None && z) {
                anonymousClass1.label = 1;
                if (DelayKt.delay(250L, anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return TileState.Selected;
    }

    public final void unSelect() {
        setSelection(null);
        setPlacementEnabled(false);
    }
}
