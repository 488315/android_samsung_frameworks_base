package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FlowLineInfo {
    public /* synthetic */ FlowLineInfo(int i, int i2, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, f, f2);
    }

    private FlowLineInfo(int i, int i2, float f, float f2) {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public FlowLineInfo(int i, int i2, float f, float f2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        i = (i3 & 1) != 0 ? 0 : i;
        i2 = (i3 & 2) != 0 ? 0 : i2;
        if ((i3 & 4) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i3 & 8) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        this(i, i2, f, f2, null);
    }
}
