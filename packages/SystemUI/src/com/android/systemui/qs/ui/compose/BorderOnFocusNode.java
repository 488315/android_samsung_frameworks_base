package com.android.systemui.qs.ui.compose;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class BorderOnFocusNode extends Modifier.Node implements FocusEventModifierNode, DrawModifierNode {
    public long color;
    public CornerSize cornerSize;
    public final MutableState focused$delegate;
    public float padding;
    public float strokeWidth;

    public /* synthetic */ BorderOnFocusNode(long j, CornerSize cornerSize, float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, cornerSize, f, f2);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        layoutNodeDrawScope.drawContent();
        Offset.Companion.getClass();
        Rect rectInflate = RectKt.m413Recttz77jQw(0L, layoutNodeDrawScope.canvasDrawScope.mo547getSizeNHjbRc()).inflate(layoutNodeDrawScope.mo58toPx0680j_4(this.padding));
        if (((Boolean) ((SnapshotMutableStateImpl) this.focused$delegate).getValue()).booleanValue()) {
            long j = this.color;
            long jM411getTopLeftF1C5BW0 = rectInflate.m411getTopLeftF1C5BW0();
            long jM410getSizeNHjbRc = rectInflate.m410getSizeNHjbRc();
            float fMo185toPxTmRCtEA = this.cornerSize.mo185toPxTmRCtEA(layoutNodeDrawScope, rectInflate.m410getSizeNHjbRc());
            long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo185toPxTmRCtEA) << 32) | (Float.floatToRawIntBits(fMo185toPxTmRCtEA) & 4294967295L);
            CornerRadius.Companion companion = CornerRadius.Companion;
            DrawScope.m543drawRoundRectuAw5IA$default(layoutNodeDrawScope, j, jM411getTopLeftF1C5BW0, jM410getSizeNHjbRc, jFloatToRawIntBits, new Stroke(layoutNodeDrawScope.mo58toPx0680j_4(this.strokeWidth), 0.0f, 0, 0, null, 30, null), 0.0f, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
        }
    }

    @Override // androidx.compose.ui.focus.FocusEventModifierNode
    public final void onFocusEvent(FocusStateImpl focusStateImpl) {
        boolean zIsFocused = focusStateImpl.isFocused();
        ((SnapshotMutableStateImpl) this.focused$delegate).setValue(Boolean.valueOf(zIsFocused));
    }

    private BorderOnFocusNode(long j, CornerSize cornerSize, float f, float f2) {
        this.color = j;
        this.cornerSize = cornerSize;
        this.strokeWidth = f;
        this.padding = f2;
        this.focused$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    }
}
