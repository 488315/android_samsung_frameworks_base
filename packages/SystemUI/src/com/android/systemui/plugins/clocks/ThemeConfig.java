package com.android.systemui.plugins.clocks;

import android.R;
import android.content.Context;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ThemeConfig {
    public static final int $stable = 0;
    private final boolean isDarkTheme;
    private final Integer seedColor;

    public ThemeConfig(boolean z, Integer num) {
        this.isDarkTheme = z;
        this.seedColor = num;
    }

    public static /* synthetic */ ThemeConfig copy$default(ThemeConfig themeConfig, boolean z, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            z = themeConfig.isDarkTheme;
        }
        if ((i & 2) != 0) {
            num = themeConfig.seedColor;
        }
        return themeConfig.copy(z, num);
    }

    public final boolean component1() {
        return this.isDarkTheme;
    }

    public final Integer component2() {
        return this.seedColor;
    }

    public final ThemeConfig copy(boolean z, Integer num) {
        return new ThemeConfig(z, num);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ThemeConfig)) {
            return false;
        }
        ThemeConfig themeConfig = (ThemeConfig) obj;
        return this.isDarkTheme == themeConfig.isDarkTheme && Intrinsics.areEqual(this.seedColor, themeConfig.seedColor);
    }

    public final int getDefaultColor(Context context) {
        Integer num = this.seedColor;
        if (num == null) {
            return this.isDarkTheme ? context.getResources().getColor(R.color.system_accent1_100) : context.getResources().getColor(R.color.system_accent2_600);
        }
        num.getClass();
        return num.intValue();
    }

    public final Integer getSeedColor() {
        return this.seedColor;
    }

    public int hashCode() {
        int iHashCode = Boolean.hashCode(this.isDarkTheme) * 31;
        Integer num = this.seedColor;
        return iHashCode + (num == null ? 0 : num.hashCode());
    }

    public final boolean isDarkTheme() {
        return this.isDarkTheme;
    }

    public String toString() {
        return "ThemeConfig(isDarkTheme=" + this.isDarkTheme + ", seedColor=" + this.seedColor + ")";
    }
}
