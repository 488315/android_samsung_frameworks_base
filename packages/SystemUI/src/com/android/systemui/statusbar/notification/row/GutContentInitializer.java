package com.android.systemui.statusbar.notification.row;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface GutContentInitializer {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnSettingsClickListener {
        void onClick();
    }

    boolean initializeGutContentView(ExpandableNotificationRow expandableNotificationRow);
}
