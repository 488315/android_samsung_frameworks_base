package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        int iHashCode = (((fontFamily == null ? 0 : fontFamily.hashCode()) * 31) + this.fontWeight.weight) * 31;
        FontStyle.Companion companion = FontStyle.Companion;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.fontStyle, iHashCode, 31);
        FontSynthesis.Companion companion2 = FontSynthesis.Companion;
        int iM2 = ReorderTile$$ExternalSyntheticOutline0.m(this.fontSynthesis, iM, 31);
        Object obj = this.resourceLoaderCacheKey;
        return iM2 + (obj != null ? obj.hashCode() : 0);
    }

    public final String toString() {
        return "TypefaceRequest(fontFamily=" + this.fontFamily + ", fontWeight=" + this.fontWeight + ", fontStyle=" + ((Object) FontStyle.m767toStringimpl(this.fontStyle)) + ", fontSynthesis=" + ((Object) FontSynthesis.m769toStringimpl(this.fontSynthesis)) + ", resourceLoaderCacheKey=" + this.resourceLoaderCacheKey + ')';
    }

    private TypefaceRequest(FontFamily fontFamily, FontWeight fontWeight, int i, int i2, Object obj) {
        this.fontFamily = fontFamily;
        this.fontWeight = fontWeight;
        this.fontStyle = i;
        this.fontSynthesis = i2;
        this.resourceLoaderCacheKey = obj;
    }
}
