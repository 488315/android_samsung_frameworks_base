package com.samsung.android.desktopsystemui.sharedlib.statusbar;

import android.graphics.Rect;
import android.view.KeyEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CommandQueueCallbacks {
    default void animateExpandSettingsPanel(String str) {
    }

    default void handleSystemKey(KeyEvent keyEvent) {
    }

    default void onFocusedDisplayChanged(int i) {
    }

    default void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) {
    }

    default void showRecentApps(boolean z) {
    }

    default void toggleKeyboardShortcutsMenu(int i) {
    }

    default void animateExpandNotificationsPanel() {
    }

    default void toggleRecentApps() {
    }

    default void abortTransient(int i, int i2) {
    }

    default void animateCollapsePanels(int i, boolean z) {
    }

    default void hideRecentApps(boolean z, boolean z2) {
    }

    default void disable(int i, int i2, int i3) {
    }

    default void setWindowState(int i, int i2, int i3) {
    }

    default void showTransient(int i, int i2, Boolean bool) {
    }

    default void onSystemBarAttributesChanged(int i, int i2, Rect rect, Boolean bool, int i3, Boolean bool2, String str) {
    }
}
