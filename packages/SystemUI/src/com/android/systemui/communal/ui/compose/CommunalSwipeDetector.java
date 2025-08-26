package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.unit.Density;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.SwipeDetector;
import com.android.compose.animation.scene.SwipeSource;
import com.android.compose.animation.scene.SwipeSourceDetector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CommunalSwipeDetector implements SwipeSourceDetector, SwipeDetector {
    public SwipeSource.Resolved lastDirection;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CommunalSwipeDetector() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // com.android.compose.animation.scene.SwipeDetector
    public final boolean detectSwipe(PointerInputChange pointerInputChange) {
        if (Float.intBitsToFloat((int) (PointerEventKt.positionChangeInternal(pointerInputChange, false) >> 32)) > 0.0f) {
            this.lastDirection = Edge.Resolved.Left;
        } else {
            this.lastDirection = Edge.Resolved.Right;
        }
        return Math.abs(Float.intBitsToFloat((int) (PointerEventKt.positionChangeInternal(pointerInputChange, false) >> 32)) / Float.intBitsToFloat((int) (PointerEventKt.positionChangeInternal(pointerInputChange, false) & 4294967295L))) > 0.5f;
    }

    @Override // com.android.compose.animation.scene.SwipeSourceDetector
    /* renamed from: source-NDhlJko */
    public final SwipeSource.Resolved mo926sourceNDhlJko(long j, long j2, Density density, Orientation orientation) {
        return this.lastDirection;
    }

    public CommunalSwipeDetector(SwipeSource.Resolved resolved) {
        this.lastDirection = resolved;
    }

    public /* synthetic */ CommunalSwipeDetector(SwipeSource.Resolved resolved, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : resolved);
    }
}
