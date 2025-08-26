package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;
import com.android.systemui.statusbar.phone.ConfigurationForwarder;

/* loaded from: classes3.dex */
public interface ConfigurationController extends CallbackController, ConfigurationForwarder {

    public interface ConfigurationListener {
        default void onConfigChanged(Configuration configuration) {
        }

        default void onLayoutDirectionChanged(boolean z) {
        }

        default void onOrientationChanged(int i) {
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

        default void onMovedToDisplay(int i, Configuration configuration) {
        }
    }
}
