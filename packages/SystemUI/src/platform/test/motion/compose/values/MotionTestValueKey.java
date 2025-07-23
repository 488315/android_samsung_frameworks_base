package platform.test.motion.compose.values;

import androidx.compose.ui.semantics.SemanticsPropertyKey;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MotionTestValueKey {
    public final SemanticsPropertyKey semanticsPropertyKey;

    public MotionTestValueKey(String str) {
        this.semanticsPropertyKey = new SemanticsPropertyKey(str, null, 2, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MotionTestValueKey)) {
            return false;
        }
        return Intrinsics.areEqual(this.semanticsPropertyKey, ((MotionTestValueKey) obj).semanticsPropertyKey);
    }

    public final int hashCode() {
        return this.semanticsPropertyKey.hashCode();
    }
}
