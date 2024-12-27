package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;

public final class KeyguardQuickAffordancePosition {
    public static final /* synthetic */ KeyguardQuickAffordancePosition[] $VALUES;
    public static final KeyguardQuickAffordancePosition BOTTOM_END;
    public static final KeyguardQuickAffordancePosition BOTTOM_START;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardQuickAffordancePosition.values().length];
            try {
                iArr[KeyguardQuickAffordancePosition.BOTTOM_START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardQuickAffordancePosition.BOTTOM_END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        KeyguardQuickAffordancePosition keyguardQuickAffordancePosition = new KeyguardQuickAffordancePosition("BOTTOM_START", 0);
        BOTTOM_START = keyguardQuickAffordancePosition;
        KeyguardQuickAffordancePosition keyguardQuickAffordancePosition2 = new KeyguardQuickAffordancePosition("BOTTOM_END", 1);
        BOTTOM_END = keyguardQuickAffordancePosition2;
        KeyguardQuickAffordancePosition[] keyguardQuickAffordancePositionArr = {keyguardQuickAffordancePosition, keyguardQuickAffordancePosition2};
        $VALUES = keyguardQuickAffordancePositionArr;
        EnumEntriesKt.enumEntries(keyguardQuickAffordancePositionArr);
    }

    private KeyguardQuickAffordancePosition(String str, int i) {
    }

    public static KeyguardQuickAffordancePosition valueOf(String str) {
        return (KeyguardQuickAffordancePosition) Enum.valueOf(KeyguardQuickAffordancePosition.class, str);
    }

    public static KeyguardQuickAffordancePosition[] values() {
        return (KeyguardQuickAffordancePosition[]) $VALUES.clone();
    }

    public final String toSlotId() {
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            return "bottom_start";
        }
        if (i == 2) {
            return "bottom_end";
        }
        throw new NoWhenBranchMatchedException();
    }
}
