package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutModifierNodeKt;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        int iHashCode = (this.fontFamilyResolver.hashCode() + SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(this.text.hashCode() * 31, 31, this.style)) * 31;
        TextOverflow.Companion companion = TextOverflow.Companion;
        int iM = (((TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.overflow, iHashCode, 31), 31, this.softWrap) + this.maxLines) * 31) + this.minLines) * 31;
        ColorProducer colorProducer = this.color;
        return iM + (colorProducer != null ? colorProducer.hashCode() : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Modifier.Node node) {
        boolean z;
        TextStringSimpleNode textStringSimpleNode = (TextStringSimpleNode) node;
        ColorProducer colorProducer = textStringSimpleNode.overrideColor;
        ColorProducer colorProducer2 = this.color;
        boolean zAreEqual = Intrinsics.areEqual(colorProducer2, colorProducer);
        textStringSimpleNode.overrideColor = colorProducer2;
        boolean z2 = false;
        boolean z3 = true;
        TextStyle textStyle = this.style;
        if (zAreEqual) {
            TextStyle textStyle2 = textStringSimpleNode.style;
            if (textStyle == textStyle2) {
                textStyle.getClass();
            } else if (textStyle.spanStyle.hasSameNonLayoutAttributes$ui_text_release(textStyle2.spanStyle)) {
            }
            z = false;
        } else {
            z = true;
        }
        String str = textStringSimpleNode.text;
        String str2 = this.text;
        if (!Intrinsics.areEqual(str, str2)) {
            textStringSimpleNode.text = str2;
            textStringSimpleNode.textSubstitution = null;
            z2 = true;
        }
        boolean z4 = !textStringSimpleNode.style.hasSameLayoutAffectingAttributes(textStyle);
        textStringSimpleNode.style = textStyle;
        int i = textStringSimpleNode.minLines;
        int i2 = this.minLines;
        if (i != i2) {
            textStringSimpleNode.minLines = i2;
            z4 = true;
        }
        int i3 = textStringSimpleNode.maxLines;
        int i4 = this.maxLines;
        if (i3 != i4) {
            textStringSimpleNode.maxLines = i4;
            z4 = true;
        }
        boolean z5 = textStringSimpleNode.softWrap;
        boolean z6 = this.softWrap;
        if (z5 != z6) {
            textStringSimpleNode.softWrap = z6;
            z4 = true;
        }
        FontFamily.Resolver resolver = textStringSimpleNode.fontFamilyResolver;
        FontFamily.Resolver resolver2 = this.fontFamilyResolver;
        if (!Intrinsics.areEqual(resolver, resolver2)) {
            textStringSimpleNode.fontFamilyResolver = resolver2;
            z4 = true;
        }
        int i5 = textStringSimpleNode.overflow;
        TextOverflow.Companion companion = TextOverflow.Companion;
        int i6 = this.overflow;
        if (i5 == i6) {
            z3 = z4;
        } else {
            textStringSimpleNode.overflow = i6;
        }
        if (z2 || z3) {
            ParagraphLayoutCache layoutCache = textStringSimpleNode.getLayoutCache();
            String str3 = textStringSimpleNode.text;
            TextStyle textStyle3 = textStringSimpleNode.style;
            FontFamily.Resolver resolver3 = textStringSimpleNode.fontFamilyResolver;
            int i7 = textStringSimpleNode.overflow;
            boolean z7 = textStringSimpleNode.softWrap;
            int i8 = textStringSimpleNode.maxLines;
            int i9 = textStringSimpleNode.minLines;
            layoutCache.text = str3;
            layoutCache.style = textStyle3;
            layoutCache.fontFamilyResolver = resolver3;
            layoutCache.overflow = i7;
            layoutCache.softWrap = z7;
            layoutCache.maxLines = i8;
            layoutCache.minLines = i9;
            layoutCache.markDirty();
        }
        if (textStringSimpleNode.isAttached) {
            if (z2 || (z && textStringSimpleNode.semanticsTextLayoutResult != null)) {
                SemanticsModifierNodeKt.invalidateSemantics(textStringSimpleNode);
            }
            if (z2 || z3) {
                LayoutModifierNodeKt.invalidateMeasurement(textStringSimpleNode);
                DrawModifierNodeKt.invalidateDraw(textStringSimpleNode);
            }
            if (z) {
                DrawModifierNodeKt.invalidateDraw(textStringSimpleNode);
            }
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStringSimpleElement(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, ColorProducer colorProducer, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        if ((i4 & 8) != 0) {
            TextOverflow.Companion.getClass();
            i5 = TextOverflow.Clip;
        } else {
            i5 = i;
        }
        this(str, textStyle, resolver, i5, (i4 & 16) != 0 ? true : z, (i4 & 32) != 0 ? Integer.MAX_VALUE : i2, (i4 & 64) != 0 ? 1 : i3, (i4 & 128) != 0 ? null : colorProducer, null);
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
