package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordancePosition {
    public static final /* synthetic */ KeyguardQuickAffordancePosition[] $VALUES;
    public static final KeyguardQuickAffordancePosition BOTTOM_END;
    public static final KeyguardQuickAffordancePosition BOTTOM_START;
    public static final Companion Companion;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Companion = new Companion(null);
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
