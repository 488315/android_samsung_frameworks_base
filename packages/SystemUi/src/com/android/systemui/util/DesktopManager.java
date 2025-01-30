package com.android.systemui.util;

import com.samsung.android.desktopmode.SemDesktopModeState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface DesktopManager {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        default void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        }

        default void onPrivacyItemStateRequested() {
        }
    }
}
