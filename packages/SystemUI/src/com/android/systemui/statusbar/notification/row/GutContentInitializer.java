package com.android.systemui.statusbar.notification.row;

/* loaded from: classes3.dex */
public interface GutContentInitializer {

    public interface OnSettingsClickListener {
        void onClick();
    }

    boolean initializeGutContentView(ExpandableNotificationRow expandableNotificationRow);
}
