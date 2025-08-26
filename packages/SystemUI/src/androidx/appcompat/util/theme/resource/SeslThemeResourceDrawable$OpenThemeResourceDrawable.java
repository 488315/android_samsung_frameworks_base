package androidx.appcompat.util.theme.resource;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SeslThemeResourceDrawable$OpenThemeResourceDrawable extends SeslThemeResourceDrawable$ResourceDrawable {
    public final SeslThemeResourceDrawable$ThemeResourceDrawable defaultThemeResource;
    public final SeslThemeResourceDrawable$ThemeResourceDrawable openThemeResource;

    public SeslThemeResourceDrawable$OpenThemeResourceDrawable(SeslThemeResourceDrawable$ThemeResourceDrawable seslThemeResourceDrawable$ThemeResourceDrawable, SeslThemeResourceDrawable$ThemeResourceDrawable seslThemeResourceDrawable$ThemeResourceDrawable2) {
        this.defaultThemeResource = seslThemeResourceDrawable$ThemeResourceDrawable;
        this.openThemeResource = seslThemeResourceDrawable$ThemeResourceDrawable2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslThemeResourceDrawable$OpenThemeResourceDrawable)) {
            return false;
        }
        SeslThemeResourceDrawable$OpenThemeResourceDrawable seslThemeResourceDrawable$OpenThemeResourceDrawable = (SeslThemeResourceDrawable$OpenThemeResourceDrawable) obj;
        return Intrinsics.areEqual(this.defaultThemeResource, seslThemeResourceDrawable$OpenThemeResourceDrawable.defaultThemeResource) && Intrinsics.areEqual(this.openThemeResource, seslThemeResourceDrawable$OpenThemeResourceDrawable.openThemeResource);
    }

    @Override // androidx.appcompat.util.theme.resource.SeslThemeResourceDrawable$ResourceDrawable
    public final int getDrawable(Context context) {
        return TextUtils.isEmpty(Settings.System.getString(context.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE)) ? this.defaultThemeResource.getDrawable(context) : this.openThemeResource.getDrawable(context);
    }

    public final int hashCode() {
        return this.openThemeResource.hashCode() + (this.defaultThemeResource.hashCode() * 31);
    }

    public final String toString() {
        return "OpenThemeResourceDrawable(defaultThemeResource=" + this.defaultThemeResource + ", openThemeResource=" + this.openThemeResource + ')';
    }
}
