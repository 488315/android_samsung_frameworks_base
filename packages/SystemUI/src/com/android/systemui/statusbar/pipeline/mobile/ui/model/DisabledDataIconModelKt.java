package com.android.systemui.statusbar.pipeline.mobile.ui.model;

public abstract class DisabledDataIconModelKt {
    public static final DisabledDataIconModel EMPTY_DISABLED_DATA_ICON = new DisabledDataIconModel(IconLocation.DATA_ICON, 0);
    public static final DisabledDataIconModel EMPTY_DISABLED_DATA_ROAMING_ICON = new DisabledDataIconModel(IconLocation.ROAMING_ICON, 0);
}
