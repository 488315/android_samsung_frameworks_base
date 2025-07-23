package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TypefaceRequest {
    public final FontFamily fontFamily;
    public final int fontStyle;
    public final int fontSynthesis;
    public final FontWeight fontWeight;
    public final Object resourceLoaderCacheKey;

    public /* synthetic */ TypefaceRequest(FontFamily fontFamily, FontWeight fontWeight, int i, int i2, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(fontFamily, fontWeight, i, i2, obj);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypefaceRequest)) {
            return false;
        }
        TypefaceRequest typefaceRequest = (TypefaceRequest) obj;
        if (!Intrinsics.areEqual(this.fontFamily, typefaceRequest.fontFamily) || !Intrinsics.areEqual(this.fontWeight, typefaceRequest.fontWeight)) {
            return false;
        }
        FontStyle.Companion companion = FontStyle.Companion;
        if (this.fontStyle == typefaceRequest.fontStyle) {
            FontSynthesis.Companion companion2 = FontSynthesis.Companion;
            return this.fontSynthesis == typefaceRequest.fontSynthesis && Intrinsics.areEqual(this.resourceLoaderCacheKey, typefaceRequest.resourceLoaderCacheKey);
        }
        return false;
    }

    public final int hashCode() {
        FontFamily fontFamily = this.fontFamily;
        int hashCode = (((fontFamily == null ? 0 : fontFamily.hashCode()) * 31) + this.fontWeight.weight) * 31;
        FontStyle.Companion companion = FontStyle.Companion;
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.fontStyle, hashCode, 31);
        FontSynthesis.Companion companion2 = FontSynthesis.Companion;
        int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.fontSynthesis, m, 31);
        Object obj = this.resourceLoaderCacheKey;
        return m2 + (obj != null ? obj.hashCode() : 0);
    }

    public final String toString() {
        return "TypefaceRequest(fontFamily=" + this.fontFamily + ", fontWeight=" + this.fontWeight + ", fontStyle=" + ((Object) FontStyle.m765toStringimpl(this.fontStyle)) + ", fontSynthesis=" + ((Object) FontSynthesis.m767toStringimpl(this.fontSynthesis)) + ", resourceLoaderCacheKey=" + this.resourceLoaderCacheKey + ')';
    }

    private TypefaceRequest(FontFamily fontFamily, FontWeight fontWeight, int i, int i2, Object obj) {
        this.fontFamily = fontFamily;
        this.fontWeight = fontWeight;
        this.fontStyle = i;
        this.fontSynthesis = i2;
        this.resourceLoaderCacheKey = obj;
    }
}
