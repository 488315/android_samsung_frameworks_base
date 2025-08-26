package androidx.compose.animation.core;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpOffset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public abstract class VisibilityThresholdsKt {
    public static final Rect RectVisibilityThreshold;
    public static final Map VisibilityThresholdMap;

    static {
        Float fValueOf = Float.valueOf(0.5f);
        RectVisibilityThreshold = new Rect(0.5f, 0.5f, 0.5f, 0.5f);
        int i = IntCompanionObject.$r8$clinit;
        Pair pair = new Pair(VectorConvertersKt.IntToVector, Float.valueOf(1.0f));
        IntSize.Companion companion = IntSize.Companion;
        Pair pair2 = new Pair(VectorConvertersKt.IntSizeToVector, Float.valueOf(1.0f));
        IntOffset.Companion companion2 = IntOffset.Companion;
        Pair pair3 = new Pair(VectorConvertersKt.IntOffsetToVector, Float.valueOf(1.0f));
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        Pair pair4 = new Pair(VectorConvertersKt.FloatToVector, Float.valueOf(0.01f));
        Pair pair5 = new Pair(VectorConvertersKt.RectToVector, fValueOf);
        Size.Companion companion3 = Size.Companion;
        Pair pair6 = new Pair(VectorConvertersKt.SizeToVector, fValueOf);
        Offset.Companion companion4 = Offset.Companion;
        Pair pair7 = new Pair(VectorConvertersKt.OffsetToVector, fValueOf);
        Dp.Companion companion5 = Dp.Companion;
        Pair pair8 = new Pair(VectorConvertersKt.DpToVector, Float.valueOf(0.1f));
        int i2 = DpOffset.$r8$clinit;
        VisibilityThresholdMap = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, new Pair(VectorConvertersKt.DpOffsetToVector, Float.valueOf(0.1f)));
    }

    public static final long getVisibilityThreshold() {
        long j = 1;
        long j2 = (j & 4294967295L) | (j << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return j2;
    }

    public static final long getVisibilityThreshold$3() {
        long j = 1;
        long j2 = (j & 4294967295L) | (j << 32);
        IntSize.Companion companion = IntSize.Companion;
        return j2;
    }
}
