package androidx.appcompat.util.theme.resource;

import android.content.Context;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslMisc;

/* loaded from: classes.dex */
public final class SeslThemeResourceDrawable$ThemeResourceDrawable extends SeslThemeResourceDrawable$ResourceDrawable {
    public final int darkThemeResId;
    public final int lightThemeResId;

    public SeslThemeResourceDrawable$ThemeResourceDrawable(int i, int i2) {
        this.lightThemeResId = i;
        this.darkThemeResId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslThemeResourceDrawable$ThemeResourceDrawable)) {
            return false;
        }
        SeslThemeResourceDrawable$ThemeResourceDrawable seslThemeResourceDrawable$ThemeResourceDrawable = (SeslThemeResourceDrawable$ThemeResourceDrawable) obj;
        return this.lightThemeResId == seslThemeResourceDrawable$ThemeResourceDrawable.lightThemeResId && this.darkThemeResId == seslThemeResourceDrawable$ThemeResourceDrawable.darkThemeResId;
    }

    @Override // androidx.appcompat.util.theme.resource.SeslThemeResourceDrawable$ResourceDrawable
    public final int getDrawable(Context context) {
        return SeslMisc.isLightTheme(context) ? this.lightThemeResId : this.darkThemeResId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.darkThemeResId) + (Integer.hashCode(this.lightThemeResId) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ThemeResourceDrawable(lightThemeResId=");
        sb.append(this.lightThemeResId);
        sb.append(", darkThemeResId=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.darkThemeResId, ')');
    }

    public SeslThemeResourceDrawable$ThemeResourceDrawable(int i) {
        this(i, i);
    }
}
