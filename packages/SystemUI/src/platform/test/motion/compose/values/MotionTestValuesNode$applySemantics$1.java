package platform.test.motion.compose.values;

import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MotionTestValuesNode$applySemantics$1 {
    public final /* synthetic */ SemanticsPropertyReceiver $this_applySemantics;

    public MotionTestValuesNode$applySemantics$1(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        this.$this_applySemantics = semanticsPropertyReceiver;
    }

    public final void exportAs(Object obj, MotionTestValueKey motionTestValueKey) {
        ((SemanticsConfiguration) this.$this_applySemantics).set(motionTestValueKey.semanticsPropertyKey, obj);
    }
}
