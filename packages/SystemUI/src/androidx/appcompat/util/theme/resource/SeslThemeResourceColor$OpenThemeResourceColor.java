package androidx.appcompat.util.theme.resource;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SeslThemeResourceColor$OpenThemeResourceColor extends SeslThemeResourceColor$ResourceColor {
    public final SeslThemeResourceColor$ThemeResourceColor defaultThemeResource;
    public final SeslThemeResourceColor$ThemeResourceColor openThemeResource;

    public SeslThemeResourceColor$OpenThemeResourceColor(SeslThemeResourceColor$ThemeResourceColor seslThemeResourceColor$ThemeResourceColor, SeslThemeResourceColor$ThemeResourceColor seslThemeResourceColor$ThemeResourceColor2) {
        this.defaultThemeResource = seslThemeResourceColor$ThemeResourceColor;
        this.openThemeResource = seslThemeResourceColor$ThemeResourceColor2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslThemeResourceColor$OpenThemeResourceColor)) {
            return false;
        }
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = (SeslThemeResourceColor$OpenThemeResourceColor) obj;
        return Intrinsics.areEqual(this.defaultThemeResource, seslThemeResourceColor$OpenThemeResourceColor.defaultThemeResource) && Intrinsics.areEqual(this.openThemeResource, seslThemeResourceColor$OpenThemeResourceColor.openThemeResource);
    }

    @Override // androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ResourceColor
    public final int getColor(Context context) {
        return TextUtils.isEmpty(Settings.System.getString(context.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE)) ? this.defaultThemeResource.getColor(context) : this.openThemeResource.getColor(context);
    }

    public final int hashCode() {
        return this.openThemeResource.hashCode() + (this.defaultThemeResource.hashCode() * 31);
    }

    public final String toString() {
        return "OpenThemeResourceColor(defaultThemeResource=" + this.defaultThemeResource + ", openThemeResource=" + this.openThemeResource + ')';
    }
}
