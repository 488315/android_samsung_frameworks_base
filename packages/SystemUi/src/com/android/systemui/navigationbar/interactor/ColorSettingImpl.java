package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ColorSettingImpl implements ColorSetting {
    public final Context context;
    public final SettingsHelper settingsHelper;

    public ColorSettingImpl(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ColorSetting
    public final void addColorCallback(Runnable runnable) {
        int color = this.context.getColor(R.color.light_navbar_default_opaque_color);
        SettingsHelper settingsHelper = this.settingsHelper;
        Settings.Global.putInt(settingsHelper.mResolver, "navigationbar_color", color);
        Settings.Global.putInt(settingsHelper.mResolver, "navigationbar_current_color", color);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ColorSetting
    public final int getNavigationBarColor() {
        return this.context.getColor(R.color.light_navbar_background_opaque);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ColorSetting
    public final void setNavigationBarColor(int i) {
    }
}
