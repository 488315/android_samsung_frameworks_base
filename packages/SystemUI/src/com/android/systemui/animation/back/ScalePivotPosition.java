package com.android.systemui.animation.back;

import kotlin.enums.EnumEntriesKt;

public final class ScalePivotPosition {
    public static final /* synthetic */ ScalePivotPosition[] $VALUES;
    public static final ScalePivotPosition BOTTOM_CENTER;
    public static final ScalePivotPosition CENTER;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScalePivotPosition.values().length];
            try {
                iArr[ScalePivotPosition.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScalePivotPosition.BOTTOM_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        ScalePivotPosition scalePivotPosition = new ScalePivotPosition("CENTER", 0);
        CENTER = scalePivotPosition;
        ScalePivotPosition scalePivotPosition2 = new ScalePivotPosition("BOTTOM_CENTER", 1);
        BOTTOM_CENTER = scalePivotPosition2;
        ScalePivotPosition[] scalePivotPositionArr = {scalePivotPosition, scalePivotPosition2};
        $VALUES = scalePivotPositionArr;
        EnumEntriesKt.enumEntries(scalePivotPositionArr);
    }

    private ScalePivotPosition(String str, int i) {
    }

    public static ScalePivotPosition valueOf(String str) {
        return (ScalePivotPosition) Enum.valueOf(ScalePivotPosition.class, str);
    }

    public static ScalePivotPosition[] values() {
        return (ScalePivotPosition[]) $VALUES.clone();
    }
}
