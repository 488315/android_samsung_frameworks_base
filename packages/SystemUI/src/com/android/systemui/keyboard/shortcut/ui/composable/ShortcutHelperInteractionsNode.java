package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperInteractionsNode extends Modifier.Node implements DrawModifierNode {
    public final InteractionSource interactionSource;
    public final InteractionsConfig interactionsConfig;
    public final MutableState isFocused;
    public final MutableState isHovered;
    public final MutableState isPressed;

    public ShortcutHelperInteractionsNode(InteractionSource interactionSource, InteractionsConfig interactionsConfig) {
        this.interactionSource = interactionSource;
        this.interactionsConfig = interactionsConfig;
        Boolean bool = Boolean.FALSE;
        this.isFocused = SnapshotStateKt.mutableStateOf$default(bool);
        this.isHovered = SnapshotStateKt.mutableStateOf$default(bool);
        this.isPressed = SnapshotStateKt.mutableStateOf$default(bool);
    }

    public static final Rect draw$getRectangleWithPadding(ShortcutHelperInteractionsNode shortcutHelperInteractionsNode, LayoutNodeDrawScope layoutNodeDrawScope, float f, long j) {
        Offset.Companion.getClass();
        Rect m411Recttz77jQw = RectKt.m411Recttz77jQw(0L, j);
        Dp.Companion companion = Dp.Companion;
        return Float.compare(shortcutHelperInteractionsNode.interactionsConfig.focusOutlinePadding, (float) 0) > 0 ? m411Recttz77jQw.inflate(layoutNodeDrawScope.mo57toPx0680j_4(f)) : m411Recttz77jQw.inflate(-layoutNodeDrawScope.mo57toPx0680j_4(-f));
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        long j;
        InteractionsConfig interactionsConfig;
        CanvasDrawScope canvasDrawScope;
        layoutNodeDrawScope.drawContent();
        boolean booleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.isHovered).getValue()).booleanValue();
        InteractionsConfig interactionsConfig2 = this.interactionsConfig;
        CanvasDrawScope canvasDrawScope2 = layoutNodeDrawScope.canvasDrawScope;
        if (booleanValue) {
            Rect draw$getRectangleWithPadding = draw$getRectangleWithPadding(this, layoutNodeDrawScope, interactionsConfig2.pressedPadding, canvasDrawScope2.mo545getSizeNHjbRc());
            float mo57toPx0680j_4 = layoutNodeDrawScope.mo57toPx0680j_4(interactionsConfig2.surfaceCornerRadius);
            long floatToRawIntBits = (Float.floatToRawIntBits(mo57toPx0680j_4) << 32) | (Float.floatToRawIntBits(mo57toPx0680j_4) & 4294967295L);
            CornerRadius.Companion companion = CornerRadius.Companion;
            j = 4294967295L;
            canvasDrawScope = canvasDrawScope2;
            interactionsConfig = interactionsConfig2;
            DrawScope.m541drawRoundRectuAw5IA$default(layoutNodeDrawScope, interactionsConfig2.hoverOverlayColor, draw$getRectangleWithPadding.m409getTopLeftF1C5BW0(), draw$getRectangleWithPadding.m408getSizeNHjbRc(), floatToRawIntBits, null, interactionsConfig2.hoverOverlayAlpha, 208);
        } else {
            j = 4294967295L;
            interactionsConfig = interactionsConfig2;
            canvasDrawScope = canvasDrawScope2;
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.isPressed).getValue()).booleanValue()) {
            Rect draw$getRectangleWithPadding2 = draw$getRectangleWithPadding(this, layoutNodeDrawScope, interactionsConfig.pressedPadding, canvasDrawScope.mo545getSizeNHjbRc());
            float mo57toPx0680j_42 = layoutNodeDrawScope.mo57toPx0680j_4(interactionsConfig.surfaceCornerRadius);
            long floatToRawIntBits2 = (Float.floatToRawIntBits(mo57toPx0680j_42) << 32) | (Float.floatToRawIntBits(mo57toPx0680j_42) & j);
            CornerRadius.Companion companion2 = CornerRadius.Companion;
            DrawScope.m541drawRoundRectuAw5IA$default(layoutNodeDrawScope, interactionsConfig.pressedOverlayColor, draw$getRectangleWithPadding2.m409getTopLeftF1C5BW0(), draw$getRectangleWithPadding2.m408getSizeNHjbRc(), floatToRawIntBits2, null, interactionsConfig.pressedOverlayAlpha, 208);
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.isFocused).getValue()).booleanValue()) {
            Rect draw$getRectangleWithPadding3 = draw$getRectangleWithPadding(this, layoutNodeDrawScope, interactionsConfig.focusOutlinePadding, canvasDrawScope.mo545getSizeNHjbRc());
            Stroke stroke = new Stroke(layoutNodeDrawScope.mo57toPx0680j_4(interactionsConfig.focusOutlineStrokeWidth), 0.0f, 0, 0, null, 30, null);
            long m409getTopLeftF1C5BW0 = draw$getRectangleWithPadding3.m409getTopLeftF1C5BW0();
            long m408getSizeNHjbRc = draw$getRectangleWithPadding3.m408getSizeNHjbRc();
            float mo57toPx0680j_43 = layoutNodeDrawScope.mo57toPx0680j_4(interactionsConfig.focusOutlineCornerRadius);
            long floatToRawIntBits3 = (Float.floatToRawIntBits(mo57toPx0680j_43) << 32) | (Float.floatToRawIntBits(mo57toPx0680j_43) & j);
            CornerRadius.Companion companion3 = CornerRadius.Companion;
            DrawScope.m541drawRoundRectuAw5IA$default(layoutNodeDrawScope, interactionsConfig.focusOutlineColor, m409getTopLeftF1C5BW0, m408getSizeNHjbRc, floatToRawIntBits3, stroke, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        CoroutineTracingKt.launchTraced$default(getCoroutineScope(), null, null, new ShortcutHelperInteractionsNode$onAttach$1(this, null), 7);
    }
}
