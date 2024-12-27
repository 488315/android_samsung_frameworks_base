package com.android.systemui.util.leak;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class RotationUtils {
    public static final int ROTATION_LANDSCAPE = 1;
    public static final int ROTATION_NONE = 0;
    public static final int ROTATION_SEASCAPE = 3;
    public static final int ROTATION_UPSIDE_DOWN = 2;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Rotation {
    }

    public static int getExactRotation(Context context) {
        int rotation = context.getDisplay().getRotation();
        if (rotation == 1) {
            return 1;
        }
        if (rotation == 3) {
            return 3;
        }
        return rotation == 2 ? 2 : 0;
    }

    public static Resources getResourcesForRotation(int i, Context context) {
        int i2 = 1;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown rotation: "));
                    }
                }
            }
            i2 = 2;
        }
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.orientation = i2;
        return context.createConfigurationContext(configuration).getResources();
    }

    public static int getRotation(Context context) {
        int rotation = context.getDisplay().getRotation();
        if (rotation == 1) {
            return 1;
        }
        return rotation == 3 ? 3 : 0;
    }

    public static String toString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "Unknown (", ")") : "Seascape (3)" : "Upside down (2)" : "Landscape (1)" : "None (0)";
    }
}
