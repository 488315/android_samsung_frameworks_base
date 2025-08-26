package platform.test.motion.compose.values;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class MotionTestValuesNode extends Modifier.Node implements SemanticsModifierNode, CompositionLocalConsumerModifierNode {
    public Function1 values;

    /* renamed from: platform.test.motion.compose.values.MotionTestValuesNode$applySemantics$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ SemanticsPropertyReceiver $this_applySemantics;

        public AnonymousClass1(SemanticsPropertyReceiver semanticsPropertyReceiver) {
            this.$this_applySemantics = semanticsPropertyReceiver;
        }

        public final void exportAs(Object obj, MotionTestValueKey motionTestValueKey) {
            ((SemanticsConfiguration) this.$this_applySemantics).set(motionTestValueKey.semanticsPropertyKey, obj);
        }
    }

    public MotionTestValuesNode(Function1 function1) {
        this.values = function1;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        if (((Boolean) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, LocalEnableMotionTestingKt.LocalEnableMotionTestValueCollection)).booleanValue()) {
            this.values.mo781invoke(new AnonymousClass1(semanticsPropertyReceiver));
        }
    }
}
