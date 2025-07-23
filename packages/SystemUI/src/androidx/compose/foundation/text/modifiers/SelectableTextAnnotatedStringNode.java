package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.GlobalPositionAwareModifierNode;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SelectableTextAnnotatedStringNode extends DelegatingNode implements LayoutModifierNode, DrawModifierNode, GlobalPositionAwareModifierNode {
    public final Function1 onShowTranslation;
    public SelectionController selectionController;
    public final TextAnnotatedStringNode textAnnotatedStringNode;

    public /* synthetic */ SelectableTextAnnotatedStringNode(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize, function13);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        this.textAnnotatedStringNode.draw(layoutNodeDrawScope);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return this.textAnnotatedStringNode.maxIntrinsicHeight(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return this.textAnnotatedStringNode.maxIntrinsicWidth(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        return this.textAnnotatedStringNode.mo4measure3p2s80s(measureScope, measurable, j);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return this.textAnnotatedStringNode.minIntrinsicHeight(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return this.textAnnotatedStringNode.minIntrinsicWidth(lookaheadCapablePlaceable, intrinsicMeasurable, i);
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        SelectionController selectionController = this.selectionController;
        if (selectionController == null) {
            return;
        }
        selectionController.params = StaticTextSelectionParams.copy$default(selectionController.params, nodeCoordinator, null, 2);
        throw null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SelectableTextAnnotatedStringNode(androidx.compose.ui.text.AnnotatedString r20, androidx.compose.ui.text.TextStyle r21, androidx.compose.ui.text.font.FontFamily.Resolver r22, kotlin.jvm.functions.Function1 r23, int r24, boolean r25, int r26, int r27, java.util.List r28, kotlin.jvm.functions.Function1 r29, androidx.compose.foundation.text.modifiers.SelectionController r30, androidx.compose.ui.graphics.ColorProducer r31, androidx.compose.foundation.text.TextAutoSize r32, kotlin.jvm.functions.Function1 r33, int r34, kotlin.jvm.internal.DefaultConstructorMarker r35) {
        /*
            r19 = this;
            r0 = r34
            r1 = r0 & 8
            r2 = 0
            if (r1 == 0) goto L9
            r7 = r2
            goto Lb
        L9:
            r7 = r23
        Lb:
            r1 = r0 & 16
            if (r1 == 0) goto L18
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r8 = r1
            goto L1a
        L18:
            r8 = r24
        L1a:
            r1 = r0 & 32
            r3 = 1
            if (r1 == 0) goto L21
            r9 = r3
            goto L23
        L21:
            r9 = r25
        L23:
            r1 = r0 & 64
            if (r1 == 0) goto L2c
            r1 = 2147483647(0x7fffffff, float:NaN)
            r10 = r1
            goto L2e
        L2c:
            r10 = r26
        L2e:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L34
            r11 = r3
            goto L36
        L34:
            r11 = r27
        L36:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L3c
            r12 = r2
            goto L3e
        L3c:
            r12 = r28
        L3e:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L44
            r13 = r2
            goto L46
        L44:
            r13 = r29
        L46:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L4c
            r14 = r2
            goto L4e
        L4c:
            r14 = r30
        L4e:
            r1 = r0 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L54
            r15 = r2
            goto L56
        L54:
            r15 = r31
        L56:
            r1 = r0 & 4096(0x1000, float:5.74E-42)
            if (r1 == 0) goto L5d
            r16 = r2
            goto L5f
        L5d:
            r16 = r32
        L5f:
            r0 = r0 & 8192(0x2000, float:1.148E-41)
            if (r0 == 0) goto L66
            r17 = r2
            goto L68
        L66:
            r17 = r33
        L68:
            r18 = 0
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r22
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringNode.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, kotlin.jvm.functions.Function1, int, boolean, int, int, java.util.List, kotlin.jvm.functions.Function1, androidx.compose.foundation.text.modifiers.SelectionController, androidx.compose.ui.graphics.ColorProducer, androidx.compose.foundation.text.TextAutoSize, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private SelectableTextAnnotatedStringNode(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List<AnnotatedString.Range<Placeholder>> list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13) {
        this.selectionController = selectionController;
        this.onShowTranslation = function13;
        TextAnnotatedStringNode textAnnotatedStringNode = new TextAnnotatedStringNode(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize, function13, null);
        delegate(textAnnotatedStringNode);
        this.textAnnotatedStringNode = textAnnotatedStringNode;
        if (this.selectionController != null) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("Do not use SelectionCapableStaticTextModifier unless selectionController != null");
        throw new KotlinNothingValueException();
    }
}
