package com.android.compose.animation.scene;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import com.android.compose.animation.scene.Edge;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FixedSizeEdgeDetector implements SwipeSourceDetector {
    public final float size;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ FixedSizeEdgeDetector(float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(f);
    }

    private FixedSizeEdgeDetector(float f) {
        this.size = f;
    }

    @Override // com.android.compose.animation.scene.SwipeSourceDetector
    /* renamed from: source-NDhlJko, reason: not valid java name */
    public final Edge.Resolved mo926sourceNDhlJko(long j, long j2, Density density, Orientation orientation) {
        int i;
        int i2;
        Edge.Resolved resolved;
        Edge.Resolved resolved2;
        int i3 = WhenMappings.$EnumSwitchMapping$0[orientation.ordinal()];
        if (i3 == 1) {
            i = (int) (j >> 32);
            IntOffset.Companion companion = IntOffset.Companion;
            i2 = (int) (j2 >> 32);
            resolved = Edge.Resolved.Left;
            resolved2 = Edge.Resolved.Right;
        } else {
            if (i3 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            i = (int) (j & 4294967295L);
            IntOffset.Companion companion2 = IntOffset.Companion;
            i2 = (int) (j2 & 4294967295L);
            resolved = Edge.Resolved.Top;
            resolved2 = Edge.Resolved.Bottom;
        }
        float fMo58toPx0680j_4 = density.mo58toPx0680j_4(this.size);
        float f = i2;
        if (f <= fMo58toPx0680j_4) {
            return resolved;
        }
        if (f >= i - fMo58toPx0680j_4) {
            return resolved2;
        }
        return null;
    }
}
