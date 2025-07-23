package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SelectableTextAnnotatedStringElement extends ModifierNodeElement<SelectableTextAnnotatedStringNode> {
    public final TextAutoSize autoSize;
    public final ColorProducer color;
    public final FontFamily.Resolver fontFamilyResolver;
    public final int maxLines;
    public final int minLines;
    public final Function1 onPlaceholderLayout;
    public final Function1 onTextLayout;
    public final int overflow;
    public final List placeholders;
    public final SelectionController selectionController;
    public final boolean softWrap;
    public final TextStyle style;
    public final AnnotatedString text;

    public /* synthetic */ SelectableTextAnnotatedStringElement(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new SelectableTextAnnotatedStringNode(this.text, this.style, this.fontFamilyResolver, this.onTextLayout, this.overflow, this.softWrap, this.maxLines, this.minLines, this.placeholders, this.onPlaceholderLayout, this.selectionController, this.color, this.autoSize, null, 8192, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SelectableTextAnnotatedStringElement)) {
            return false;
        }
        SelectableTextAnnotatedStringElement selectableTextAnnotatedStringElement = (SelectableTextAnnotatedStringElement) obj;
        if (!Intrinsics.areEqual(this.color, selectableTextAnnotatedStringElement.color) || !Intrinsics.areEqual(this.text, selectableTextAnnotatedStringElement.text) || !Intrinsics.areEqual(this.style, selectableTextAnnotatedStringElement.style) || !Intrinsics.areEqual(this.placeholders, selectableTextAnnotatedStringElement.placeholders) || !Intrinsics.areEqual(this.fontFamilyResolver, selectableTextAnnotatedStringElement.fontFamilyResolver) || !Intrinsics.areEqual(this.autoSize, selectableTextAnnotatedStringElement.autoSize) || this.onTextLayout != selectableTextAnnotatedStringElement.onTextLayout) {
            return false;
        }
        int i = selectableTextAnnotatedStringElement.overflow;
        TextOverflow.Companion companion = TextOverflow.Companion;
        return this.overflow == i && this.softWrap == selectableTextAnnotatedStringElement.softWrap && this.maxLines == selectableTextAnnotatedStringElement.maxLines && this.minLines == selectableTextAnnotatedStringElement.minLines && this.onPlaceholderLayout == selectableTextAnnotatedStringElement.onPlaceholderLayout && Intrinsics.areEqual(this.selectionController, selectableTextAnnotatedStringElement.selectionController);
    }

    public final int hashCode() {
        int hashCode = (this.fontFamilyResolver.hashCode() + SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(this.text.hashCode() * 31, 31, this.style)) * 31;
        Function1 function1 = this.onTextLayout;
        int hashCode2 = (hashCode + (function1 != null ? function1.hashCode() : 0)) * 31;
        TextOverflow.Companion companion = TextOverflow.Companion;
        int m = (((TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.overflow, hashCode2, 31), 31, this.softWrap) + this.maxLines) * 31) + this.minLines) * 31;
        List list = this.placeholders;
        int hashCode3 = (m + (list != null ? list.hashCode() : 0)) * 31;
        Function1 function12 = this.onPlaceholderLayout;
        int hashCode4 = (hashCode3 + (function12 != null ? function12.hashCode() : 0)) * 31;
        SelectionController selectionController = this.selectionController;
        int hashCode5 = (hashCode4 + (selectionController != null ? selectionController.hashCode() : 0)) * 31;
        TextAutoSize textAutoSize = this.autoSize;
        int hashCode6 = (hashCode5 + (textAutoSize != null ? textAutoSize.hashCode() : 0)) * 31;
        ColorProducer colorProducer = this.color;
        return hashCode6 + (colorProducer != null ? colorProducer.hashCode() : 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0020, code lost:
    
        if (r1.spanStyle.hasSameNonLayoutAttributes$ui_text_release(r0.spanStyle) != false) goto L10;
     */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(androidx.compose.ui.Modifier.Node r13) {
        /*
            r12 = this;
            androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringNode r13 = (androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringNode) r13
            java.util.List r2 = r12.placeholders
            androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode r9 = r13.textAnnotatedStringNode
            androidx.compose.ui.graphics.ColorProducer r0 = r9.overrideColor
            androidx.compose.ui.graphics.ColorProducer r1 = r12.color
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r0)
            r9.overrideColor = r1
            androidx.compose.ui.text.TextStyle r1 = r12.style
            if (r0 == 0) goto L29
            androidx.compose.ui.text.TextStyle r0 = r9.style
            if (r1 == r0) goto L23
            androidx.compose.ui.text.SpanStyle r3 = r1.spanStyle
            androidx.compose.ui.text.SpanStyle r0 = r0.spanStyle
            boolean r0 = r3.hasSameNonLayoutAttributes$ui_text_release(r0)
            if (r0 == 0) goto L29
            goto L26
        L23:
            r1.getClass()
        L26:
            r0 = 0
        L27:
            r10 = r0
            goto L2b
        L29:
            r0 = 1
            goto L27
        L2b:
            androidx.compose.ui.text.AnnotatedString r0 = r12.text
            boolean r11 = r9.updateText$foundation_release(r0)
            boolean r5 = r12.softWrap
            androidx.compose.ui.text.font.FontFamily$Resolver r6 = r12.fontFamilyResolver
            androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode r0 = r13.textAnnotatedStringNode
            int r3 = r12.minLines
            int r4 = r12.maxLines
            int r7 = r12.overflow
            androidx.compose.foundation.text.TextAutoSize r8 = r12.autoSize
            boolean r0 = r0.m231updateLayoutRelatedArgsy0kMQk(r1, r2, r3, r4, r5, r6, r7, r8)
            kotlin.jvm.functions.Function1 r1 = r13.onShowTranslation
            kotlin.jvm.functions.Function1 r2 = r12.onTextLayout
            kotlin.jvm.functions.Function1 r3 = r12.onPlaceholderLayout
            androidx.compose.foundation.text.modifiers.SelectionController r12 = r12.selectionController
            boolean r1 = r9.updateCallbacks(r2, r3, r12, r1)
            r9.doInvalidations(r10, r11, r0, r1)
            r13.selectionController = r12
            androidx.compose.ui.node.LayoutModifierNodeKt.invalidateMeasurement(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringElement.update(androidx.compose.ui.Modifier$Node):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SelectableTextAnnotatedStringElement(androidx.compose.ui.text.AnnotatedString r19, androidx.compose.ui.text.TextStyle r20, androidx.compose.ui.text.font.FontFamily.Resolver r21, kotlin.jvm.functions.Function1 r22, int r23, boolean r24, int r25, int r26, java.util.List r27, kotlin.jvm.functions.Function1 r28, androidx.compose.foundation.text.modifiers.SelectionController r29, androidx.compose.ui.graphics.ColorProducer r30, androidx.compose.foundation.text.TextAutoSize r31, int r32, kotlin.jvm.internal.DefaultConstructorMarker r33) {
        /*
            r18 = this;
            r0 = r32
            r1 = r0 & 8
            r2 = 0
            if (r1 == 0) goto L9
            r7 = r2
            goto Lb
        L9:
            r7 = r22
        Lb:
            r1 = r0 & 16
            if (r1 == 0) goto L18
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r8 = r1
            goto L1a
        L18:
            r8 = r23
        L1a:
            r1 = r0 & 32
            r3 = 1
            if (r1 == 0) goto L21
            r9 = r3
            goto L23
        L21:
            r9 = r24
        L23:
            r1 = r0 & 64
            if (r1 == 0) goto L2c
            r1 = 2147483647(0x7fffffff, float:NaN)
            r10 = r1
            goto L2e
        L2c:
            r10 = r25
        L2e:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L34
            r11 = r3
            goto L36
        L34:
            r11 = r26
        L36:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L3c
            r12 = r2
            goto L3e
        L3c:
            r12 = r27
        L3e:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L44
            r13 = r2
            goto L46
        L44:
            r13 = r28
        L46:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L4c
            r14 = r2
            goto L4e
        L4c:
            r14 = r29
        L4e:
            r1 = r0 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L54
            r15 = r2
            goto L56
        L54:
            r15 = r30
        L56:
            r0 = r0 & 4096(0x1000, float:5.74E-42)
            if (r0 == 0) goto L5d
            r16 = r2
            goto L5f
        L5d:
            r16 = r31
        L5f:
            r17 = 0
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringElement.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, kotlin.jvm.functions.Function1, int, boolean, int, int, java.util.List, kotlin.jvm.functions.Function1, androidx.compose.foundation.text.modifiers.SelectionController, androidx.compose.ui.graphics.ColorProducer, androidx.compose.foundation.text.TextAutoSize, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private SelectableTextAnnotatedStringElement(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List<AnnotatedString.Range<Placeholder>> list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize) {
        this.text = annotatedString;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.onTextLayout = function1;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.placeholders = list;
        this.onPlaceholderLayout = function12;
        this.selectionController = selectionController;
        this.color = colorProducer;
        this.autoSize = textAutoSize;
    }
}
