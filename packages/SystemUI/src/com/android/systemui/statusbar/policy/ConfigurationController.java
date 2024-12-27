package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;

public interface ConfigurationController extends CallbackController {

    public interface ConfigurationListener {
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

        default void onConfigChanged(Configuration configuration) {
        }

        default void onLayoutDirectionChanged(boolean z) {
        }

        default void onOrientationChanged(int i) {
        }
    }
}
