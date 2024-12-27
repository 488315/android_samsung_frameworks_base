package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;

public final class ColorSettingImpl implements ColorSetting {
    public final Context context;
    private final SettingsHelper settingsHelper;

    public ColorSettingImpl(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ColorSetting
    public final void addColorCallback(Runnable runnable) {
        int color = this.context.getColor(R.color.light_navbar_default_opaque_color);
        this.settingsHelper.setNavigationBarColor(color);
        this.settingsHelper.setNavigationBarCurrentColor(color);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ColorSetting
    public final int getNavigationBarColor() {
        return this.context.getColor(R.color.light_navbar_background_opaque);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ColorSetting
    public final void setNavigationBarColor(int i) {
    }
}
