package androidx.compose.ui.input.rotary;

import androidx.compose.ui.node.DelegatableNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface RotaryInputModifierNode extends DelegatableNode {
    boolean onPreRotaryScrollEvent(RotaryScrollEvent rotaryScrollEvent);

    boolean onRotaryScrollEvent(RotaryScrollEvent rotaryScrollEvent);
}
