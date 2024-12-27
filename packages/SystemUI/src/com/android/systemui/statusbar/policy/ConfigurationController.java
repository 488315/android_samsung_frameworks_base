package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface ConfigurationController extends CallbackController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
