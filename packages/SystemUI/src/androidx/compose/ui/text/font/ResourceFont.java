package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontLoadingStrategy;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontVariation;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final int mo757getLoadingStrategyPKNRLFQ() {
        return this.loadingStrategy;
    }

    @Override // androidx.compose.ui.text.font.Font
    /* renamed from: getStyle-_-LCdwA */
    public final int mo760getStyle_LCdwA() {
        return this.style;
    }

    @Override // androidx.compose.ui.text.font.Font
    public final FontWeight getWeight() {
        return this.weight;
    }

    public final int hashCode() {
        int i = ((this.resId * 31) + this.weight.weight) * 31;
        FontStyle.Companion companion = FontStyle.Companion;
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.style, i, 31);
        FontLoadingStrategy.Companion companion2 = FontLoadingStrategy.Companion;
        return this.variationSettings.settings.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.loadingStrategy, m, 31);
    }

    public final String toString() {
        return "ResourceFont(resId=" + this.resId + ", weight=" + this.weight + ", style=" + ((Object) FontStyle.m765toStringimpl(this.style)) + ", loadingStrategy=" + ((Object) FontLoadingStrategy.m763toStringimpl(this.loadingStrategy)) + ')';
    }

    private ResourceFont(int i, FontWeight fontWeight, int i2, FontVariation.Settings settings, int i3) {
        this.resId = i;
        this.weight = fontWeight;
        this.style = i2;
        this.variationSettings = settings;
        this.loadingStrategy = i3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ResourceFont(int r8, androidx.compose.ui.text.font.FontWeight r9, int r10, androidx.compose.ui.text.font.FontVariation.Settings r11, int r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 2
            if (r14 == 0) goto Lb
            androidx.compose.ui.text.font.FontWeight$Companion r9 = androidx.compose.ui.text.font.FontWeight.Companion
            r9.getClass()
            androidx.compose.ui.text.font.FontWeight r9 = androidx.compose.ui.text.font.FontWeight.Normal
        Lb:
            r2 = r9
            r9 = r13 & 4
            r14 = 0
            if (r9 == 0) goto L18
            androidx.compose.ui.text.font.FontStyle$Companion r9 = androidx.compose.ui.text.font.FontStyle.Companion
            r9.getClass()
            r3 = r14
            goto L19
        L18:
            r3 = r10
        L19:
            r9 = r13 & 8
            if (r9 == 0) goto L90
            androidx.compose.ui.text.font.FontVariation r9 = androidx.compose.ui.text.font.FontVariation.INSTANCE
            androidx.compose.ui.text.font.FontVariation$Setting[] r10 = new androidx.compose.ui.text.font.FontVariation.Setting[r14]
            r9.getClass()
            androidx.compose.ui.text.font.FontVariation$Settings r11 = new androidx.compose.ui.text.font.FontVariation$Settings
            kotlin.jvm.internal.SpreadBuilder r9 = new kotlin.jvm.internal.SpreadBuilder
            r14 = 3
            r9.<init>(r14)
            int r14 = r2.weight
            r0 = 1
            if (r0 > r14) goto L36
            r0 = 1001(0x3e9, float:1.403E-42)
            if (r14 >= r0) goto L36
            goto L47
        L36:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "'wght' value must be in [1, 1000]. Actual: "
            r0.<init>(r1)
            r0.append(r14)
            java.lang.String r0 = r0.toString()
            androidx.compose.ui.text.internal.InlineClassHelperKt.throwIllegalArgumentException(r0)
        L47:
            androidx.compose.ui.text.font.FontVariation$SettingInt r0 = new androidx.compose.ui.text.font.FontVariation$SettingInt
            java.lang.String r1 = "wght"
            r0.<init>(r1, r14)
            r9.add(r0)
            float r14 = (float) r3
            r0 = 0
            int r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1))
            if (r0 > 0) goto L5f
            r0 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r0 > 0) goto L5f
            goto L70
        L5f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "'ital' must be in 0.0f..1.0f. Actual: "
            r0.<init>(r1)
            r0.append(r14)
            java.lang.String r0 = r0.toString()
            androidx.compose.ui.text.internal.InlineClassHelperKt.throwIllegalArgumentException(r0)
        L70:
            androidx.compose.ui.text.font.FontVariation$SettingFloat r0 = new androidx.compose.ui.text.font.FontVariation$SettingFloat
            java.lang.String r1 = "ital"
            r0.<init>(r1, r14)
            r9.add(r0)
            r9.addSpread(r10)
            java.util.ArrayList r10 = r9.list
            int r10 = r10.size()
            androidx.compose.ui.text.font.FontVariation$Setting[] r10 = new androidx.compose.ui.text.font.FontVariation.Setting[r10]
            java.util.ArrayList r9 = r9.list
            java.lang.Object[] r9 = r9.toArray(r10)
            androidx.compose.ui.text.font.FontVariation$Setting[] r9 = (androidx.compose.ui.text.font.FontVariation.Setting[]) r9
            r11.<init>(r9)
        L90:
            r4 = r11
            r9 = r13 & 16
            if (r9 == 0) goto L9c
            androidx.compose.ui.text.font.FontLoadingStrategy$Companion r9 = androidx.compose.ui.text.font.FontLoadingStrategy.Companion
            r9.getClass()
            int r12 = androidx.compose.ui.text.font.FontLoadingStrategy.Async
        L9c:
            r5 = r12
            r6 = 0
            r0 = r7
            r1 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.ResourceFont.<init>(int, androidx.compose.ui.text.font.FontWeight, int, androidx.compose.ui.text.font.FontVariation$Settings, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
