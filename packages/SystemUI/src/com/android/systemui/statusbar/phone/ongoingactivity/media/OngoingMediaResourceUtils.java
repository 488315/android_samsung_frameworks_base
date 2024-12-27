package com.android.systemui.statusbar.phone.ongoingactivity.media;

import com.samsung.android.wallpaper.legibilitycolors.ColorHSV;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityColorByHSV;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityDefinition;

public final class OngoingMediaResourceUtils {
    public static final OngoingMediaResourceUtils INSTANCE = new OngoingMediaResourceUtils();

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LegibilityDefinition.ColorType.values().length];
            try {
                iArr[LegibilityDefinition.ColorType.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LegibilityDefinition.ColorType.DARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private OngoingMediaResourceUtils() {
    }

    public static OngoingMediaResourceUiType getMediaCardUiType$default(OngoingMediaResourceUtils ongoingMediaResourceUtils, int i) {
        ongoingMediaResourceUtils.getClass();
        float[] fArr = new float[3];
        ColorHSV.colorToHSV(i, fArr);
        LegibilityDefinition.ColorType legibilityColorType = LegibilityColorByHSV.getLegibilityColorType(fArr[0], fArr[1], fArr[2]);
        int i2 = legibilityColorType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[legibilityColorType.ordinal()];
        return i2 != 1 ? i2 != 2 ? OngoingMediaResourceUiType.DARK : OngoingMediaResourceUiType.DARK : OngoingMediaResourceUiType.LIGHT;
    }
}
