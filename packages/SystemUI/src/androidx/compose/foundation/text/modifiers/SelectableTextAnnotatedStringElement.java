package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.LayoutModifierNodeKt;
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
        int iHashCode = (this.fontFamilyResolver.hashCode() + SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(this.text.hashCode() * 31, 31, this.style)) * 31;
        Function1 function1 = this.onTextLayout;
        int iHashCode2 = (iHashCode + (function1 != null ? function1.hashCode() : 0)) * 31;
        TextOverflow.Companion companion = TextOverflow.Companion;
        int iM = (((TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.overflow, iHashCode2, 31), 31, this.softWrap) + this.maxLines) * 31) + this.minLines) * 31;
        List list = this.placeholders;
        int iHashCode3 = (iM + (list != null ? list.hashCode() : 0)) * 31;
        Function1 function12 = this.onPlaceholderLayout;
        int iHashCode4 = (iHashCode3 + (function12 != null ? function12.hashCode() : 0)) * 31;
        SelectionController selectionController = this.selectionController;
        int iHashCode5 = (iHashCode4 + (selectionController != null ? selectionController.hashCode() : 0)) * 31;
        TextAutoSize textAutoSize = this.autoSize;
        int iHashCode6 = (iHashCode5 + (textAutoSize != null ? textAutoSize.hashCode() : 0)) * 31;
        ColorProducer colorProducer = this.color;
        return iHashCode6 + (colorProducer != null ? colorProducer.hashCode() : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0029  */
    @Override // androidx.compose.ui.node.ModifierNodeElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Modifier.Node node) {
        boolean z;
        SelectableTextAnnotatedStringNode selectableTextAnnotatedStringNode = (SelectableTextAnnotatedStringNode) node;
        List list = this.placeholders;
        TextAnnotatedStringNode textAnnotatedStringNode = selectableTextAnnotatedStringNode.textAnnotatedStringNode;
        ColorProducer colorProducer = textAnnotatedStringNode.overrideColor;
        ColorProducer colorProducer2 = this.color;
        boolean zAreEqual = Intrinsics.areEqual(colorProducer2, colorProducer);
        textAnnotatedStringNode.overrideColor = colorProducer2;
        TextStyle textStyle = this.style;
        if (zAreEqual) {
            TextStyle textStyle2 = textAnnotatedStringNode.style;
            if (textStyle == textStyle2) {
                textStyle.getClass();
            } else if (textStyle.spanStyle.hasSameNonLayoutAttributes$ui_text_release(textStyle2.spanStyle)) {
            }
            z = false;
        } else {
            z = true;
        }
        boolean z2 = z;
        boolean zUpdateText$foundation_release = textAnnotatedStringNode.updateText$foundation_release(this.text);
        boolean zM232updateLayoutRelatedArgsy0kMQk = selectableTextAnnotatedStringNode.textAnnotatedStringNode.m232updateLayoutRelatedArgsy0kMQk(textStyle, list, this.minLines, this.maxLines, this.softWrap, this.fontFamilyResolver, this.overflow, this.autoSize);
        Function1 function1 = selectableTextAnnotatedStringNode.onShowTranslation;
        Function1 function12 = this.onTextLayout;
        Function1 function13 = this.onPlaceholderLayout;
        SelectionController selectionController = this.selectionController;
        textAnnotatedStringNode.doInvalidations(z2, zUpdateText$foundation_release, zM232updateLayoutRelatedArgsy0kMQk, textAnnotatedStringNode.updateCallbacks(function12, function13, selectionController, function1));
        selectableTextAnnotatedStringNode.selectionController = selectionController;
        LayoutModifierNodeKt.invalidateMeasurement(selectableTextAnnotatedStringNode);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SelectableTextAnnotatedStringElement(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        Function1 function13 = (i4 & 8) != 0 ? null : function1;
        if ((i4 & 16) != 0) {
            TextOverflow.Companion.getClass();
            i5 = TextOverflow.Clip;
        } else {
            i5 = i;
        }
        this(annotatedString, textStyle, resolver, function13, i5, (i4 & 32) != 0 ? true : z, (i4 & 64) != 0 ? Integer.MAX_VALUE : i2, (i4 & 128) != 0 ? 1 : i3, (i4 & 256) != 0 ? null : list, (i4 & 512) != 0 ? null : function12, (i4 & 1024) != 0 ? null : selectionController, (i4 & 2048) != 0 ? null : colorProducer, (i4 & 4096) != 0 ? null : textAutoSize, null);
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
