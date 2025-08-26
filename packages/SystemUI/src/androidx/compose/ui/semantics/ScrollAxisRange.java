package androidx.compose.ui.semantics;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ScrollAxisRange {
    public final Function0 maxValue;
    public final boolean reverseScrolling;
    public final Function0 value;

    public ScrollAxisRange(Function0 function0, Function0 function02, boolean z) {
        this.value = function0;
        this.maxValue = function02;
        this.reverseScrolling = z;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ScrollAxisRange(value=");
        sb.append(((Number) this.value.invoke()).floatValue());
        sb.append(", maxValue=");
        sb.append(((Number) this.maxValue.invoke()).floatValue());
        sb.append(", reverseScrolling=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.reverseScrolling, ')');
    }

    public /* synthetic */ ScrollAxisRange(Function0 function0, Function0 function02, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, function02, (i & 4) != 0 ? false : z);
    }
}
