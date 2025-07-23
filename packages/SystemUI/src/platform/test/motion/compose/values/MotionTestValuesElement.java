package platform.test.motion.compose.values;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class MotionTestValuesElement extends ModifierNodeElement<MotionTestValuesNode> {
    public final Function1 values;

    public MotionTestValuesElement(Function1 function1) {
        this.values = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new MotionTestValuesNode(this.values);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MotionTestValuesElement) && Intrinsics.areEqual(this.values, ((MotionTestValuesElement) obj).values);
    }

    public final int hashCode() {
        return this.values.hashCode();
    }

    public final String toString() {
        return "MotionTestValuesElement(values=" + this.values + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((MotionTestValuesNode) node).values = this.values;
    }
}
