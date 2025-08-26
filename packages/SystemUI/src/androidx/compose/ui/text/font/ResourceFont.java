package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontLoadingStrategy;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontVariation;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes.dex */
public final class ResourceFont implements Font {
    public final int loadingStrategy;
    public final int resId;
    public final int style;
    public final FontVariation.Settings variationSettings;
    public final FontWeight weight;

    public /* synthetic */ ResourceFont(int i, FontWeight fontWeight, int i2, FontVariation.Settings settings, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, fontWeight, i2, settings, i3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceFont)) {
            return false;
        }
        ResourceFont resourceFont = (ResourceFont) obj;
        if (this.resId != resourceFont.resId) {
            return false;
        }
        if (!Intrinsics.areEqual(this.weight, resourceFont.weight)) {
            return false;
        }
        int i = resourceFont.style;
        FontStyle.Companion companion = FontStyle.Companion;
        if (this.style != i || !Intrinsics.areEqual(this.variationSettings, resourceFont.variationSettings)) {
            return false;
        }
        int i2 = resourceFont.loadingStrategy;
        FontLoadingStrategy.Companion companion2 = FontLoadingStrategy.Companion;
        return this.loadingStrategy == i2;
    }

    @Override // androidx.compose.ui.text.font.Font
    /* renamed from: getLoadingStrategy-PKNRLFQ */
    public final int mo759getLoadingStrategyPKNRLFQ() {
        return this.loadingStrategy;
    }

    @Override // androidx.compose.ui.text.font.Font
    /* renamed from: getStyle-_-LCdwA */
    public final int mo762getStyle_LCdwA() {
        return this.style;
    }

    @Override // androidx.compose.ui.text.font.Font
    public final FontWeight getWeight() {
        return this.weight;
    }

    public final int hashCode() {
        int i = ((this.resId * 31) + this.weight.weight) * 31;
        FontStyle.Companion companion = FontStyle.Companion;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.style, i, 31);
        FontLoadingStrategy.Companion companion2 = FontLoadingStrategy.Companion;
        return this.variationSettings.settings.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.loadingStrategy, iM, 31);
    }

    public final String toString() {
        return "ResourceFont(resId=" + this.resId + ", weight=" + this.weight + ", style=" + ((Object) FontStyle.m767toStringimpl(this.style)) + ", loadingStrategy=" + ((Object) FontLoadingStrategy.m765toStringimpl(this.loadingStrategy)) + ')';
    }

    private ResourceFont(int i, FontWeight fontWeight, int i2, FontVariation.Settings settings, int i3) {
        this.resId = i;
        this.weight = fontWeight;
        this.style = i2;
        this.variationSettings = settings;
        this.loadingStrategy = i3;
    }

    public ResourceFont(int i, FontWeight fontWeight, int i2, FontVariation.Settings settings, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        if ((i4 & 2) != 0) {
            FontWeight.Companion.getClass();
            fontWeight = FontWeight.Normal;
        }
        FontWeight fontWeight2 = fontWeight;
        if ((i4 & 4) != 0) {
            FontStyle.Companion.getClass();
            i5 = 0;
        } else {
            i5 = i2;
        }
        if ((i4 & 8) != 0) {
            FontVariation.Setting[] settingArr = new FontVariation.Setting[0];
            FontVariation.INSTANCE.getClass();
            SpreadBuilder spreadBuilder = new SpreadBuilder(3);
            int i6 = fontWeight2.weight;
            if (1 > i6 || i6 >= 1001) {
                InlineClassHelperKt.throwIllegalArgumentException("'wght' value must be in [1, 1000]. Actual: " + i6);
            }
            spreadBuilder.add(new FontVariation.SettingInt("wght", i6));
            float f = i5;
            if (0.0f > f || f > 1.0f) {
                InlineClassHelperKt.throwIllegalArgumentException("'ital' must be in 0.0f..1.0f. Actual: " + f);
            }
            spreadBuilder.add(new FontVariation.SettingFloat("ital", f));
            spreadBuilder.addSpread(settingArr);
            settings = new FontVariation.Settings((FontVariation.Setting[]) spreadBuilder.list.toArray(new FontVariation.Setting[spreadBuilder.list.size()]));
        }
        FontVariation.Settings settings2 = settings;
        if ((i4 & 16) != 0) {
            FontLoadingStrategy.Companion.getClass();
            i3 = FontLoadingStrategy.Async;
        }
        this(i, fontWeight2, i5, settings2, i3, null);
    }
}
