package platform.test.motion.compose.values;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MotionTestValuesNode extends Modifier.Node implements SemanticsModifierNode, CompositionLocalConsumerModifierNode {
    public Function1 values;

    public MotionTestValuesNode(Function1 function1) {
        this.values = function1;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        if (((Boolean) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, LocalEnableMotionTestingKt.LocalEnableMotionTestValueCollection)).booleanValue()) {
            this.values.mo779invoke(new MotionTestValuesNode$applySemantics$1(semanticsPropertyReceiver));
        }
    }
}
