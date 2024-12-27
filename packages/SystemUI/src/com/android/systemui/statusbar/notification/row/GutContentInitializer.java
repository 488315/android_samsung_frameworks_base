package com.android.systemui.statusbar.notification.row;

public interface GutContentInitializer {

    public interface OnSettingsClickListener {
        void onClick();
    }

    boolean initializeGutContentView(ExpandableNotificationRow expandableNotificationRow);
}
