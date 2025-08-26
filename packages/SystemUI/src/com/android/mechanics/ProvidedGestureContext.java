package com.android.mechanics;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.mechanics.spec.InputDirection;

/* loaded from: classes.dex */
public final class ProvidedGestureContext implements MutableDragOffsetGestureContext {
    public final MutableState direction$delegate;
    public final MutableFloatState dragOffset$delegate;

    public ProvidedGestureContext(float f, InputDirection inputDirection) {
        this.direction$delegate = SnapshotStateKt.mutableStateOf$default(inputDirection);
        this.dragOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final float getDragOffset() {
        return this.dragOffset$delegate.getFloatValue();
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final void setDragOffset(float f) {
        ((SnapshotMutableFloatStateImpl) this.dragOffset$delegate).setFloatValue(f);
    }
}
