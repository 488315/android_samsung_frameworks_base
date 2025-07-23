package com.android.internal.policy;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.RoundedCorners;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class ScreenDecorationsUtils {
    public static float getWindowCornerRadius(Context context) {
        Resources resources = context.getResources();
        if (!supportsRoundedCornersOnWindows(resources)) {
            return 0.0f;
        }
        Display displayNoVerify = context.getDisplayNoVerify();
        if (displayNoVerify.getType() != 1) {
            return 0.0f;
        }
        String uniqueId = displayNoVerify.getUniqueId();
        float roundedCornerRadius = RoundedCorners.getRoundedCornerRadius(resources, uniqueId) - RoundedCorners.getRoundedCornerRadiusAdjustment(resources, uniqueId);
        float roundedCornerTopRadius = RoundedCorners.getRoundedCornerTopRadius(resources, uniqueId) - RoundedCorners.getRoundedCornerRadiusTopAdjustment(resources, uniqueId);
        if (roundedCornerTopRadius == 0.0f) {
            roundedCornerTopRadius = roundedCornerRadius;
        }
        float roundedCornerBottomRadius = RoundedCorners.getRoundedCornerBottomRadius(resources, uniqueId) - RoundedCorners.getRoundedCornerRadiusBottomAdjustment(resources, uniqueId);
        if (roundedCornerBottomRadius != 0.0f) {
            roundedCornerRadius = roundedCornerBottomRadius;
        }
        float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio(context);
        if (physicalPixelDisplaySizeRatio != 1.0f) {
            roundedCornerTopRadius *= physicalPixelDisplaySizeRatio;
            roundedCornerRadius *= physicalPixelDisplaySizeRatio;
        }
        return Math.min(roundedCornerTopRadius, roundedCornerRadius);
    }

    static float getPhysicalPixelDisplaySizeRatio(Context context) {
        DisplayInfo displayInfo = new DisplayInfo();
        context.getDisplay().getDisplayInfo(displayInfo);
        Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(displayInfo.supportedModes);
        if (maximumResolutionDisplayMode == null) {
            return 1.0f;
        }
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode.getPhysicalHeight(), displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
    }

    public static boolean supportsRoundedCornersOnWindows(Resources resources) {
        return resources.getBoolean(R.bool.config_supportsRoundedCornersOnWindows);
    }
}
