package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontVariation;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class DeviceFontFamilyNameFont extends AndroidFont {
    public final String familyName;
    public final int style;
    public final FontWeight weight;

    public /* synthetic */ DeviceFontFamilyNameFont(String str, FontWeight fontWeight, int i, FontVariation.Settings settings, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, fontWeight, i, settings);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceFontFamilyNameFont)) {
            return false;
        }
        DeviceFontFamilyNameFont deviceFontFamilyNameFont = (DeviceFontFamilyNameFont) obj;
        if (!Intrinsics.areEqual(this.familyName, deviceFontFamilyNameFont.familyName)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.weight, deviceFontFamilyNameFont.weight)) {
            return false;
        }
        int i = deviceFontFamilyNameFont.style;
        FontStyle.Companion companion = FontStyle.Companion;
        if (this.style == i) {
            return Intrinsics.areEqual(this.variationSettings, deviceFontFamilyNameFont.variationSettings);
        }
        return false;
    }

    @Override // androidx.compose.ui.text.font.Font
    /* renamed from: getStyle-_-LCdwA, reason: not valid java name */
    public final int mo762getStyle_LCdwA() {
        return this.style;
    }

    @Override // androidx.compose.ui.text.font.Font
    public final FontWeight getWeight() {
        return this.weight;
    }

    public final int hashCode() {
        int iHashCode = ((this.familyName.hashCode() * 31) + this.weight.weight) * 31;
        FontStyle.Companion companion = FontStyle.Companion;
        return this.variationSettings.settings.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.style, iHashCode, 31);
    }

    public final String toString() {
        return "Font(familyName=\"" + ((Object) ("DeviceFontFamilyName(name=" + this.familyName + ')')) + "\", weight=" + this.weight + ", style=" + ((Object) FontStyle.m767toStringimpl(this.style)) + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private DeviceFontFamilyNameFont(String str, FontWeight fontWeight, int i, FontVariation.Settings settings) {
        super(FontLoadingStrategy.OptionalLocal, NamedFontLoader.INSTANCE, settings, null);
        FontLoadingStrategy.Companion.getClass();
        this.familyName = str;
        this.weight = fontWeight;
        this.style = i;
    }
}
