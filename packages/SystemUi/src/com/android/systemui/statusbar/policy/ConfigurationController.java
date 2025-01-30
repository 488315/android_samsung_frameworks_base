package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ConfigurationController extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ConfigurationListener {
        default void onConfigChanged(Configuration configuration) {
        }

        default void onLayoutDirectionChanged(boolean z) {
        }

        default void onDensityOrFontScaleChanged() {
        }

        default void onDisplayDeviceTypeChanged() {
        }

        default void onLocaleListChanged() {
        }

        default void onMaxBoundsChanged() {
        }

        default void onSmallestScreenWidthChanged() {
        }

        default void onThemeChanged() {
        }

        default void onUiModeChanged() {
        }
    }
}
