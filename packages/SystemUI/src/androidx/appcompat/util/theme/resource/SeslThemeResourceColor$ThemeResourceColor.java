package androidx.appcompat.util.theme.resource;

import android.content.Context;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslMisc;

/* loaded from: classes.dex */
public final class SeslThemeResourceColor$ThemeResourceColor extends SeslThemeResourceColor$ResourceColor {
    public final int darkThemeResId;
    public final int lightThemeResId;

    public SeslThemeResourceColor$ThemeResourceColor(int i, int i2) {
        this.lightThemeResId = i;
        this.darkThemeResId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslThemeResourceColor$ThemeResourceColor)) {
            return false;
        }
        SeslThemeResourceColor$ThemeResourceColor seslThemeResourceColor$ThemeResourceColor = (SeslThemeResourceColor$ThemeResourceColor) obj;
        return this.lightThemeResId == seslThemeResourceColor$ThemeResourceColor.lightThemeResId && this.darkThemeResId == seslThemeResourceColor$ThemeResourceColor.darkThemeResId;
    }

    @Override // androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ResourceColor
    public final int getColor(Context context) {
        return SeslMisc.isLightTheme(context) ? this.lightThemeResId : this.darkThemeResId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.darkThemeResId) + (Integer.hashCode(this.lightThemeResId) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ThemeResourceColor(lightThemeResId=");
        sb.append(this.lightThemeResId);
        sb.append(", darkThemeResId=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.darkThemeResId, ')');
    }

    public SeslThemeResourceColor$ThemeResourceColor(int i) {
        this(i, i);
    }
}
