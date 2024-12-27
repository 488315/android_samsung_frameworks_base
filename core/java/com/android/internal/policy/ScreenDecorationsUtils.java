package com.android.internal.policy;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.RoundedCorners;

import com.android.internal.R;

public class ScreenDecorationsUtils {
    public static float getWindowCornerRadius(Context context) {
        Resources resources = context.getResources();
        if (!supportsRoundedCornersOnWindows(resources)) {
            return 0.0f;
        }
        String displayUniqueId = context.getDisplayNoVerify().getUniqueId();
        float defaultRadius =
                RoundedCorners.getRoundedCornerRadius(resources, displayUniqueId)
                        - RoundedCorners.getRoundedCornerRadiusAdjustment(
                                resources, displayUniqueId);
        float topRadius =
                RoundedCorners.getRoundedCornerTopRadius(resources, displayUniqueId)
                        - RoundedCorners.getRoundedCornerRadiusTopAdjustment(
                                resources, displayUniqueId);
        if (topRadius == 0.0f) {
            topRadius = defaultRadius;
        }
        float bottomRadius =
                RoundedCorners.getRoundedCornerBottomRadius(resources, displayUniqueId)
                        - RoundedCorners.getRoundedCornerRadiusBottomAdjustment(
                                resources, displayUniqueId);
        if (bottomRadius == 0.0f) {
            bottomRadius = defaultRadius;
        }
        float scale = getPhysicalPixelDisplaySizeRatio(context);
        if (scale != 1.0f) {
            topRadius *= scale;
            bottomRadius *= scale;
        }
        return Math.min(topRadius, bottomRadius);
    }

    static float getPhysicalPixelDisplaySizeRatio(Context context) {
        DisplayInfo displayInfo = new DisplayInfo();
        context.getDisplay().getDisplayInfo(displayInfo);
        Display.Mode maxDisplayMode =
                DisplayUtils.getMaximumResolutionDisplayMode(displayInfo.supportedModes);
        if (maxDisplayMode == null) {
            return 1.0f;
        }
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(
                maxDisplayMode.getPhysicalWidth(),
                maxDisplayMode.getPhysicalHeight(),
                displayInfo.getNaturalWidth(),
                displayInfo.getNaturalHeight());
    }

    public static boolean supportsRoundedCornersOnWindows(Resources resources) {
        return resources.getBoolean(R.bool.config_supportsRoundedCornersOnWindows);
    }
}
