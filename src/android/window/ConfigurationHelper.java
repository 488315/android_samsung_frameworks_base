package android.window;

import android.app.ResourcesManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.IBinder;

/* loaded from: classes5.dex */
public class ConfigurationHelper {
    public static boolean isDifferentDisplay(int i, int i2) {
        return (i2 == -1 || i == i2) ? false : true;
    }

    private ConfigurationHelper() {
    }

    public static void freeTextLayoutCachesIfNeeded(int i) {
        if ((i & 4) != 0) {
            Canvas.freeTextLayoutCaches();
        }
    }

    public static boolean shouldUpdateResources(IBinder iBinder, Configuration configuration, Configuration configuration2, Configuration configuration3, boolean z, Boolean bool) {
        if (configuration == null || z || !ResourcesManager.getInstance().isSameResourcesOverrideConfig(iBinder, configuration3) || shouldUpdateWindowMetricsBounds(configuration, configuration2) || isDisplayRotationChanged(configuration, configuration2)) {
            return true;
        }
        return bool == null ? configuration.diff(configuration2) != 0 : bool.booleanValue();
    }

    private static boolean shouldUpdateWindowMetricsBounds(Configuration configuration, Configuration configuration2) {
        return (configuration.windowConfiguration.getBounds().equals(configuration2.windowConfiguration.getBounds()) && configuration.windowConfiguration.getMaxBounds().equals(configuration2.windowConfiguration.getMaxBounds())) ? false : true;
    }

    private static boolean isDisplayRotationChanged(Configuration configuration, Configuration configuration2) {
        int displayRotation = configuration.windowConfiguration.getDisplayRotation();
        int displayRotation2 = configuration2.windowConfiguration.getDisplayRotation();
        return (displayRotation2 == -1 || displayRotation == -1 || displayRotation == displayRotation2) ? false : true;
    }
}
