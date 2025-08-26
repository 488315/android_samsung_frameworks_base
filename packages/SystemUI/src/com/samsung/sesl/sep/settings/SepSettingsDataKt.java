package com.samsung.sesl.sep.settings;

import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.samsung.sesl.platform.settings.SettingDB;
import com.samsung.sesl.platform.settings.SettingsData;

/* loaded from: classes4.dex */
public abstract class SepSettingsDataKt {
    public static final SettingsData.SettingsBooleanData ShowButtonBackgroundSettingData = new SettingsData.SettingsBooleanData(SettingsHelper.INDEX_SHOW_BUTTON_BACKGROUND, false, R.id.sesl_compose_show_button_background_tag, SettingDB.Global);
}
