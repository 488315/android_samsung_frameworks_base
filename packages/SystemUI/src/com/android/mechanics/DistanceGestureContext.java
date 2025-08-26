package com.android.mechanics;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.mechanics.spec.InputDirection;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public final class DistanceGestureContext implements MutableDragOffsetGestureContext {
    public final MutableFloatState _directionChangeSlop$delegate;
    public final MutableFloatState _dragOffset$delegate;
    public final MutableState direction$delegate;
    public final MutableFloatState furthestDragOffset$delegate;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[InputDirection.values().length];
            try {
                iArr[InputDirection.Max.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[InputDirection.Min.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DistanceGestureContext(float f, InputDirection inputDirection, float f2) {
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException(("directionChangeSlop must be greater than 0, was " + f2).toString());
        }
        this.direction$delegate = SnapshotStateKt.mutableStateOf$default(inputDirection);
        this.furthestDragOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this._dragOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this._directionChangeSlop$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f2);
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final float getDragOffset() {
        return ((SnapshotMutableFloatStateImpl) this._dragOffset$delegate).getFloatValue();
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final void setDragOffset(float f) {
        InputDirection inputDirection;
        ((SnapshotMutableFloatStateImpl) this._dragOffset$delegate).setFloatValue(f);
        int i = WhenMappings.$EnumSwitchMapping$0[((InputDirection) ((SnapshotMutableStateImpl) this.direction$delegate).getValue()).ordinal()];
        MutableFloatState mutableFloatState = this.furthestDragOffset$delegate;
        MutableFloatState mutableFloatState2 = this._directionChangeSlop$delegate;
        if (i == 1) {
            SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
            if (snapshotMutableFloatStateImpl.getFloatValue() - f > ((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue()) {
                ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(f);
                inputDirection = InputDirection.Min;
            } else {
                ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(Math.max(f, snapshotMutableFloatStateImpl.getFloatValue()));
                inputDirection = InputDirection.Max;
            }
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl2 = (SnapshotMutableFloatStateImpl) mutableFloatState;
            if (f - snapshotMutableFloatStateImpl2.getFloatValue() > ((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue()) {
                ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(f);
                inputDirection = InputDirection.Max;
            } else {
                ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(Math.min(f, snapshotMutableFloatStateImpl2.getFloatValue()));
                inputDirection = InputDirection.Min;
            }
        }
        ((SnapshotMutableStateImpl) this.direction$delegate).setValue(inputDirection);
    }
}
