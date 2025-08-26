package com.android.settingslib.notification.modes;

import android.R;
import com.android.settingslib.notification.modes.ZenIcon;
import com.google.common.collect.ImmutableMap;

/* loaded from: classes.dex */
public class ZenIconKeys {
    public static final ZenIcon.Key MANUAL_DND = ZenIcon.Key.forSystemResource(R.drawable.menu_popup_panel_holo_dark);
    public static final ZenIcon.Key IMPLICIT_MODE_DEFAULT = ZenIcon.Key.forSystemResource(R.drawable.menu_popup_panel_holo_dark);
    public static final ImmutableMap TYPE_DEFAULTS = ImmutableMap.of(ZenIcon.Key.forSystemResource(R.drawable.menu_popup_panel_holo_dark), ZenIcon.Key.forSystemResource(R.drawable.menu_hardkey_panel_holo_light), ZenIcon.Key.forSystemResource(R.drawable.menu_panel_holo_light), ZenIcon.Key.forSystemResource(R.drawable.menu_panel_holo_dark), ZenIcon.Key.forSystemResource(R.drawable.menu_background_fill_parent_width), ZenIcon.Key.forSystemResource(R.drawable.menu_dropdown_panel_holo_dark), ZenIcon.Key.forSystemResource(R.drawable.menu_dropdown_panel_holo_light), ZenIcon.Key.forSystemResource(R.drawable.menu_popup_panel_holo_light), ZenIcon.Key.forSystemResource(R.drawable.menu_hardkey_panel_holo_dark));
    public static final ZenIcon.Key FOR_UNEXPECTED_TYPE = ZenIcon.Key.forSystemResource(R.drawable.menu_popup_panel_holo_dark);

    private ZenIconKeys() {
    }
}
