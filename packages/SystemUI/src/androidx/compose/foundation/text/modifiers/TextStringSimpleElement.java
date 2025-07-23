package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextStringSimpleElement extends ModifierNodeElement<TextStringSimpleNode> {
    public final ColorProducer color;
    public final FontFamily.Resolver fontFamilyResolver;
    public final int maxLines;
    public final int minLines;
    public final int overflow;
    public final boolean softWrap;
    public final TextStyle style;
    public final String text;

    public /* synthetic */ TextStringSimpleElement(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, ColorProducer colorProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, textStyle, resolver, i, z, i2, i3, colorProducer);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new TextStringSimpleNode(this.text, this.style, this.fontFamilyResolver, this.overflow, this.softWrap, this.maxLines, this.minLines, this.color, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextStringSimpleElement)) {
            return false;
        }
        TextStringSimpleElement textStringSimpleElement = (TextStringSimpleElement) obj;
        if (!Intrinsics.areEqual(this.color, textStringSimpleElement.color) || !Intrinsics.areEqual(this.text, textStringSimpleElement.text) || !Intrinsics.areEqual(this.style, textStringSimpleElement.style) || !Intrinsics.areEqual(this.fontFamilyResolver, textStringSimpleElement.fontFamilyResolver)) {
            return false;
        }
        int i = textStringSimpleElement.overflow;
        TextOverflow.Companion companion = TextOverflow.Companion;
        return this.overflow == i && this.softWrap == textStringSimpleElement.softWrap && this.maxLines == textStringSimpleElement.maxLines && this.minLines == textStringSimpleElement.minLines;
    }

    public final int hashCode() {
        int hashCode = (this.fontFamilyResolver.hashCode() + SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(this.text.hashCode() * 31, 31, this.style)) * 31;
        TextOverflow.Companion companion = TextOverflow.Companion;
        int m = (((TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.overflow, hashCode, 31), 31, this.softWrap) + this.maxLines) * 31) + this.minLines) * 31;
        ColorProducer colorProducer = this.color;
        return m + (colorProducer != null ? colorProducer.hashCode() : 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001e, code lost:
    
        if (r3.spanStyle.hasSameNonLayoutAttributes$ui_text_release(r0.spanStyle) != false) goto L10;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0073  */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(androidx.compose.ui.Modifier.Node r11) {
        /*
            r10 = this;
            androidx.compose.foundation.text.modifiers.TextStringSimpleNode r11 = (androidx.compose.foundation.text.modifiers.TextStringSimpleNode) r11
            androidx.compose.ui.graphics.ColorProducer r0 = r11.overrideColor
            androidx.compose.ui.graphics.ColorProducer r1 = r10.color
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r0)
            r11.overrideColor = r1
            r1 = 0
            r2 = 1
            androidx.compose.ui.text.TextStyle r3 = r10.style
            if (r0 == 0) goto L26
            androidx.compose.ui.text.TextStyle r0 = r11.style
            if (r3 == r0) goto L21
            androidx.compose.ui.text.SpanStyle r4 = r3.spanStyle
            androidx.compose.ui.text.SpanStyle r0 = r0.spanStyle
            boolean r0 = r4.hasSameNonLayoutAttributes$ui_text_release(r0)
            if (r0 == 0) goto L26
            goto L24
        L21:
            r3.getClass()
        L24:
            r0 = r1
            goto L27
        L26:
            r0 = r2
        L27:
            java.lang.String r4 = r11.text
            java.lang.String r5 = r10.text
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L32
            goto L38
        L32:
            r11.text = r5
            r1 = 0
            r11.textSubstitution = r1
            r1 = r2
        L38:
            androidx.compose.ui.text.TextStyle r4 = r11.style
            boolean r4 = r4.hasSameLayoutAffectingAttributes(r3)
            r4 = r4 ^ r2
            r11.style = r3
            int r3 = r11.minLines
            int r5 = r10.minLines
            if (r3 == r5) goto L4a
            r11.minLines = r5
            r4 = r2
        L4a:
            int r3 = r11.maxLines
            int r5 = r10.maxLines
            if (r3 == r5) goto L53
            r11.maxLines = r5
            r4 = r2
        L53:
            boolean r3 = r11.softWrap
            boolean r5 = r10.softWrap
            if (r3 == r5) goto L5c
            r11.softWrap = r5
            r4 = r2
        L5c:
            androidx.compose.ui.text.font.FontFamily$Resolver r3 = r11.fontFamilyResolver
            androidx.compose.ui.text.font.FontFamily$Resolver r5 = r10.fontFamilyResolver
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r5)
            if (r3 != 0) goto L69
            r11.fontFamilyResolver = r5
            r4 = r2
        L69:
            int r3 = r11.overflow
            androidx.compose.ui.text.style.TextOverflow$Companion r5 = androidx.compose.ui.text.style.TextOverflow.Companion
            int r10 = r10.overflow
            if (r3 != r10) goto L73
            r2 = r4
            goto L75
        L73:
            r11.overflow = r10
        L75:
            if (r1 != 0) goto L79
            if (r2 == 0) goto L9c
        L79:
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r10 = r11.getLayoutCache()
            java.lang.String r3 = r11.text
            androidx.compose.ui.text.TextStyle r4 = r11.style
            androidx.compose.ui.text.font.FontFamily$Resolver r5 = r11.fontFamilyResolver
            int r6 = r11.overflow
            boolean r7 = r11.softWrap
            int r8 = r11.maxLines
            int r9 = r11.minLines
            r10.text = r3
            r10.style = r4
            r10.fontFamilyResolver = r5
            r10.overflow = r6
            r10.softWrap = r7
            r10.maxLines = r8
            r10.minLines = r9
            r10.markDirty()
        L9c:
            boolean r10 = r11.isAttached
            if (r10 != 0) goto La1
            goto Lbb
        La1:
            if (r1 != 0) goto La9
            if (r0 == 0) goto Lac
            kotlin.jvm.functions.Function1 r10 = r11.semanticsTextLayoutResult
            if (r10 == 0) goto Lac
        La9:
            androidx.compose.ui.node.SemanticsModifierNodeKt.invalidateSemantics(r11)
        Lac:
            if (r1 != 0) goto Lb0
            if (r2 == 0) goto Lb6
        Lb0:
            androidx.compose.ui.node.LayoutModifierNodeKt.invalidateMeasurement(r11)
            androidx.compose.ui.node.DrawModifierNodeKt.invalidateDraw(r11)
        Lb6:
            if (r0 == 0) goto Lbb
            androidx.compose.ui.node.DrawModifierNodeKt.invalidateDraw(r11)
        Lbb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleElement.update(androidx.compose.ui.Modifier$Node):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStringSimpleElement(java.lang.String r13, androidx.compose.ui.text.TextStyle r14, androidx.compose.ui.text.font.FontFamily.Resolver r15, int r16, boolean r17, int r18, int r19, androidx.compose.ui.graphics.ColorProducer r20, int r21, kotlin.jvm.internal.DefaultConstructorMarker r22) {
        /*
            r12 = this;
            r0 = r21
            r1 = r0 & 8
            if (r1 == 0) goto Lf
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r6 = r1
            goto L11
        Lf:
            r6 = r16
        L11:
            r1 = r0 & 16
            r2 = 1
            if (r1 == 0) goto L18
            r7 = r2
            goto L1a
        L18:
            r7 = r17
        L1a:
            r1 = r0 & 32
            if (r1 == 0) goto L23
            r1 = 2147483647(0x7fffffff, float:NaN)
            r8 = r1
            goto L25
        L23:
            r8 = r18
        L25:
            r1 = r0 & 64
            if (r1 == 0) goto L2b
            r9 = r2
            goto L2d
        L2b:
            r9 = r19
        L2d:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L34
            r0 = 0
            r10 = r0
            goto L36
        L34:
            r10 = r20
        L36:
            r11 = 0
            r2 = r12
            r3 = r13
            r4 = r14
            r5 = r15
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleElement.<init>(java.lang.String, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, int, boolean, int, int, androidx.compose.ui.graphics.ColorProducer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private TextStringSimpleElement(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, ColorProducer colorProducer) {
        this.text = str;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.color = colorProducer;
    }
}
