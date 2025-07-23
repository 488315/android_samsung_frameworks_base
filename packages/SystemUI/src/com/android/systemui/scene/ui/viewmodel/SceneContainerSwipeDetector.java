package com.android.systemui.scene.ui.viewmodel;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.FixedSizeEdgeDetector;
import com.android.compose.animation.scene.SwipeSource;
import com.android.compose.animation.scene.SwipeSourceDetector;
import com.android.systemui.scene.ui.viewmodel.SceneContainerArea;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerSwipeDetector implements SwipeSourceDetector {
    public final FixedSizeEdgeDetector fixedEdgeDetector;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Edge.Resolved.values().length];
            try {
                iArr[Edge.Resolved.Left.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Edge.Resolved.Bottom.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Edge.Resolved.Right.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Edge.Resolved.Top.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ SceneContainerSwipeDetector(float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(f);
    }

    @Override // com.android.compose.animation.scene.SwipeSourceDetector
    /* renamed from: source-NDhlJko */
    public final SwipeSource.Resolved mo924sourceNDhlJko(long j, long j2, Density density, Orientation orientation) {
        Edge.Resolved mo924sourceNDhlJko = this.fixedEdgeDetector.mo924sourceNDhlJko(j, j2, density, orientation);
        int i = mo924sourceNDhlJko == null ? -1 : WhenMappings.$EnumSwitchMapping$0[mo924sourceNDhlJko.ordinal()];
        if (i == -1) {
            IntOffset.Companion companion = IntOffset.Companion;
            return ((float) ((int) (j2 >> 32))) < ((float) ((int) (j >> 32))) * 0.5f ? SceneContainerArea.Resolved.LeftHalf.INSTANCE : SceneContainerArea.Resolved.RightHalf.INSTANCE;
        }
        if (i == 1) {
            return SceneContainerArea.Resolved.LeftEdge.INSTANCE;
        }
        if (i == 2) {
            return SceneContainerArea.Resolved.BottomEdge.INSTANCE;
        }
        if (i == 3) {
            return SceneContainerArea.Resolved.RightEdge.INSTANCE;
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        IntOffset.Companion companion2 = IntOffset.Companion;
        return ((float) ((int) (j2 >> 32))) < ((float) ((int) (j >> 32))) * 0.5f ? SceneContainerArea.Resolved.TopEdgeLeftHalf.INSTANCE : SceneContainerArea.Resolved.TopEdgeRightHalf.INSTANCE;
    }

    private SceneContainerSwipeDetector(float f) {
        this.fixedEdgeDetector = new FixedSizeEdgeDetector(f, null);
    }
}
