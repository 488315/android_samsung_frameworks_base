package androidx.compose.ui.text.font;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidFontResolveInterceptor implements PlatformResolveInterceptor {
    public final int fontWeightAdjustment;

    public AndroidFontResolveInterceptor(int i) {
        this.fontWeightAdjustment = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AndroidFontResolveInterceptor) && this.fontWeightAdjustment == ((AndroidFontResolveInterceptor) obj).fontWeightAdjustment;
    }

    public final int hashCode() {
        return Integer.hashCode(this.fontWeightAdjustment);
    }

    @Override // androidx.compose.ui.text.font.PlatformResolveInterceptor
    public final FontWeight interceptFontWeight(FontWeight fontWeight) {
        int i = this.fontWeightAdjustment;
        return (i == 0 || i == Integer.MAX_VALUE) ? fontWeight : new FontWeight(RangesKt___RangesKt.coerceIn(fontWeight.weight + i, 1, 1000));
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("AndroidFontResolveInterceptor(fontWeightAdjustment="), this.fontWeightAdjustment, ')');
    }
}
