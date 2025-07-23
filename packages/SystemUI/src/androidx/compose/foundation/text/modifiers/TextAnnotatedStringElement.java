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
public final class TextAnnotatedStringElement extends ModifierNodeElement<TextAnnotatedStringNode> {
    public final TextAutoSize autoSize;
    public final ColorProducer color;
    public final FontFamily.Resolver fontFamilyResolver;
    public final int maxLines;
    public final int minLines;
    public final Function1 onPlaceholderLayout;
    public final Function1 onShowTranslation;
    public final Function1 onTextLayout;
    public final int overflow;
    public final List placeholders;
    public final SelectionController selectionController;
    public final boolean softWrap;
    public final TextStyle style;
    public final AnnotatedString text;

    public /* synthetic */ TextAnnotatedStringElement(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize, function13);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new TextAnnotatedStringNode(this.text, this.style, this.fontFamilyResolver, this.onTextLayout, this.overflow, this.softWrap, this.maxLines, this.minLines, this.placeholders, this.onPlaceholderLayout, this.selectionController, this.color, this.autoSize, this.onShowTranslation, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextAnnotatedStringElement)) {
            return false;
        }
        TextAnnotatedStringElement textAnnotatedStringElement = (TextAnnotatedStringElement) obj;
        if (!Intrinsics.areEqual(this.color, textAnnotatedStringElement.color) || !Intrinsics.areEqual(this.text, textAnnotatedStringElement.text) || !Intrinsics.areEqual(this.style, textAnnotatedStringElement.style) || !Intrinsics.areEqual(this.placeholders, textAnnotatedStringElement.placeholders) || !Intrinsics.areEqual(this.fontFamilyResolver, textAnnotatedStringElement.fontFamilyResolver) || this.onTextLayout != textAnnotatedStringElement.onTextLayout || this.onShowTranslation != textAnnotatedStringElement.onShowTranslation) {
            return false;
        }
        int i = textAnnotatedStringElement.overflow;
        TextOverflow.Companion companion = TextOverflow.Companion;
        return this.overflow == i && this.softWrap == textAnnotatedStringElement.softWrap && this.maxLines == textAnnotatedStringElement.maxLines && this.minLines == textAnnotatedStringElement.minLines && this.onPlaceholderLayout == textAnnotatedStringElement.onPlaceholderLayout && Intrinsics.areEqual(this.selectionController, textAnnotatedStringElement.selectionController);
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
        ColorProducer colorProducer = this.color;
        int hashCode6 = (hashCode5 + (colorProducer != null ? colorProducer.hashCode() : 0)) * 31;
        Function1 function13 = this.onShowTranslation;
        return hashCode6 + (function13 != null ? function13.hashCode() : 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001d, code lost:
    
        if (r1.spanStyle.hasSameNonLayoutAttributes$ui_text_release(r11.spanStyle) != false) goto L10;
     */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(androidx.compose.ui.Modifier.Node r11) {
        /*
            r10 = this;
            r0 = r11
            androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode r0 = (androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode) r0
            androidx.compose.ui.graphics.ColorProducer r11 = r0.overrideColor
            androidx.compose.ui.graphics.ColorProducer r1 = r10.color
            boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r11)
            r0.overrideColor = r1
            if (r11 == 0) goto L25
            androidx.compose.ui.text.TextStyle r11 = r0.style
            androidx.compose.ui.text.TextStyle r1 = r10.style
            if (r1 == r11) goto L20
            androidx.compose.ui.text.SpanStyle r1 = r1.spanStyle
            androidx.compose.ui.text.SpanStyle r11 = r11.spanStyle
            boolean r11 = r1.hasSameNonLayoutAttributes$ui_text_release(r11)
            if (r11 == 0) goto L25
            goto L23
        L20:
            r1.getClass()
        L23:
            r11 = 0
            goto L26
        L25:
            r11 = 1
        L26:
            androidx.compose.ui.text.AnnotatedString r1 = r10.text
            boolean r9 = r0.updateText$foundation_release(r1)
            java.util.List r2 = r10.placeholders
            int r7 = r10.overflow
            androidx.compose.foundation.text.TextAutoSize r8 = r10.autoSize
            androidx.compose.ui.text.TextStyle r1 = r10.style
            int r3 = r10.minLines
            int r4 = r10.maxLines
            boolean r5 = r10.softWrap
            androidx.compose.ui.text.font.FontFamily$Resolver r6 = r10.fontFamilyResolver
            boolean r1 = r0.m231updateLayoutRelatedArgsy0kMQk(r1, r2, r3, r4, r5, r6, r7, r8)
            androidx.compose.foundation.text.modifiers.SelectionController r2 = r10.selectionController
            kotlin.jvm.functions.Function1 r3 = r10.onShowTranslation
            kotlin.jvm.functions.Function1 r4 = r10.onTextLayout
            kotlin.jvm.functions.Function1 r10 = r10.onPlaceholderLayout
            boolean r10 = r0.updateCallbacks(r4, r10, r2, r3)
            r0.doInvalidations(r11, r9, r1, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextAnnotatedStringElement.update(androidx.compose.ui.Modifier$Node):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextAnnotatedStringElement(androidx.compose.ui.text.AnnotatedString r20, androidx.compose.ui.text.TextStyle r21, androidx.compose.ui.text.font.FontFamily.Resolver r22, kotlin.jvm.functions.Function1 r23, int r24, boolean r25, int r26, int r27, java.util.List r28, kotlin.jvm.functions.Function1 r29, androidx.compose.foundation.text.modifiers.SelectionController r30, androidx.compose.ui.graphics.ColorProducer r31, androidx.compose.foundation.text.TextAutoSize r32, kotlin.jvm.functions.Function1 r33, int r34, kotlin.jvm.internal.DefaultConstructorMarker r35) {
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
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextAnnotatedStringElement.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, kotlin.jvm.functions.Function1, int, boolean, int, int, java.util.List, kotlin.jvm.functions.Function1, androidx.compose.foundation.text.modifiers.SelectionController, androidx.compose.ui.graphics.ColorProducer, androidx.compose.foundation.text.TextAutoSize, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private TextAnnotatedStringElement(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List<AnnotatedString.Range<Placeholder>> list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13) {
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
        this.onShowTranslation = function13;
    }
}
