package androidx.compose.ui.text;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.font.DelegatingFontLoaderForDeprecatedUsage_androidKt;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextLayoutInput {
    public final long constraints;
    public final Density density;
    public final FontFamily.Resolver fontFamilyResolver;
    public final LayoutDirection layoutDirection;
    public final int maxLines;
    public final int overflow;
    public final List placeholders;
    public final boolean softWrap;
    public final TextStyle style;
    public final AnnotatedString text;

    public /* synthetic */ TextLayoutInput(AnnotatedString annotatedString, TextStyle textStyle, List list, int i, boolean z, int i2, Density density, LayoutDirection layoutDirection, Font.ResourceLoader resourceLoader, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, (List<AnnotatedString.Range<Placeholder>>) list, i, z, i2, density, layoutDirection, resourceLoader, j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextLayoutInput)) {
            return false;
        }
        TextLayoutInput textLayoutInput = (TextLayoutInput) obj;
        if (!Intrinsics.areEqual(this.text, textLayoutInput.text) || !Intrinsics.areEqual(this.style, textLayoutInput.style) || !Intrinsics.areEqual(this.placeholders, textLayoutInput.placeholders) || this.maxLines != textLayoutInput.maxLines || this.softWrap != textLayoutInput.softWrap) {
            return false;
        }
        int i = textLayoutInput.overflow;
        TextOverflow.Companion companion = TextOverflow.Companion;
        return this.overflow == i && Intrinsics.areEqual(this.density, textLayoutInput.density) && this.layoutDirection == textLayoutInput.layoutDirection && Intrinsics.areEqual(this.fontFamilyResolver, textLayoutInput.fontFamilyResolver) && Constraints.m815equalsimpl0(this.constraints, textLayoutInput.constraints);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m((PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.placeholders, SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(this.text.hashCode() * 31, 31, this.style), 31) + this.maxLines) * 31, 31, this.softWrap);
        TextOverflow.Companion companion = TextOverflow.Companion;
        int hashCode = (this.fontFamilyResolver.hashCode() + ((this.layoutDirection.hashCode() + ((this.density.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.overflow, m, 31)) * 31)) * 31)) * 31;
        Constraints.Companion companion2 = Constraints.Companion;
        return Long.hashCode(this.constraints) + hashCode;
    }

    public final String toString() {
        return "TextLayoutInput(text=" + ((Object) this.text) + ", style=" + this.style + ", placeholders=" + this.placeholders + ", maxLines=" + this.maxLines + ", softWrap=" + this.softWrap + ", overflow=" + ((Object) TextOverflow.m812toStringimpl(this.overflow)) + ", density=" + this.density + ", layoutDirection=" + this.layoutDirection + ", fontFamilyResolver=" + this.fontFamilyResolver + ", constraints=" + ((Object) Constraints.m824toStringimpl(this.constraints)) + ')';
    }

    public /* synthetic */ TextLayoutInput(AnnotatedString annotatedString, TextStyle textStyle, List list, int i, boolean z, int i2, Density density, LayoutDirection layoutDirection, FontFamily.Resolver resolver, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, (List<AnnotatedString.Range<Placeholder>>) list, i, z, i2, density, layoutDirection, resolver, j);
    }

    private TextLayoutInput(AnnotatedString annotatedString, TextStyle textStyle, List<AnnotatedString.Range<Placeholder>> list, int i, boolean z, int i2, Density density, LayoutDirection layoutDirection, Font.ResourceLoader resourceLoader, FontFamily.Resolver resolver, long j) {
        this.text = annotatedString;
        this.style = textStyle;
        this.placeholders = list;
        this.maxLines = i;
        this.softWrap = z;
        this.overflow = i2;
        this.density = density;
        this.layoutDirection = layoutDirection;
        this.fontFamilyResolver = resolver;
        this.constraints = j;
    }

    private TextLayoutInput(AnnotatedString annotatedString, TextStyle textStyle, List<AnnotatedString.Range<Placeholder>> list, int i, boolean z, int i2, Density density, LayoutDirection layoutDirection, Font.ResourceLoader resourceLoader, long j) {
        this(annotatedString, textStyle, list, i, z, i2, density, layoutDirection, resourceLoader, DelegatingFontLoaderForDeprecatedUsage_androidKt.createFontFamilyResolver(resourceLoader), j);
    }

    private TextLayoutInput(AnnotatedString annotatedString, TextStyle textStyle, List<AnnotatedString.Range<Placeholder>> list, int i, boolean z, int i2, Density density, LayoutDirection layoutDirection, FontFamily.Resolver resolver, long j) {
        this(annotatedString, textStyle, list, i, z, i2, density, layoutDirection, (Font.ResourceLoader) null, resolver, j);
    }
}
