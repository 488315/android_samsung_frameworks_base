package com.android.systemui.statusbar.data.model;

import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarMode {
    public static final /* synthetic */ StatusBarMode[] $VALUES;
    public static final StatusBarMode LIGHTS_OUT;
    public static final StatusBarMode LIGHTS_OUT_TRANSPARENT;
    public static final StatusBarMode OPAQUE;
    public static final StatusBarMode SEMI_TRANSPARENT;
    public static final StatusBarMode TRANSPARENT;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[StatusBarMode.values().length];
            try {
                iArr[StatusBarMode.SEMI_TRANSPARENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[StatusBarMode.LIGHTS_OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[StatusBarMode.LIGHTS_OUT_TRANSPARENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[StatusBarMode.OPAQUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[StatusBarMode.TRANSPARENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        StatusBarMode statusBarMode = new StatusBarMode("SEMI_TRANSPARENT", 0);
        SEMI_TRANSPARENT = statusBarMode;
        StatusBarMode statusBarMode2 = new StatusBarMode("LIGHTS_OUT", 1);
        LIGHTS_OUT = statusBarMode2;
        StatusBarMode statusBarMode3 = new StatusBarMode("LIGHTS_OUT_TRANSPARENT", 2);
        LIGHTS_OUT_TRANSPARENT = statusBarMode3;
        StatusBarMode statusBarMode4 = new StatusBarMode("OPAQUE", 3);
        OPAQUE = statusBarMode4;
        StatusBarMode statusBarMode5 = new StatusBarMode("TRANSPARENT", 4);
        TRANSPARENT = statusBarMode5;
        StatusBarMode[] statusBarModeArr = {statusBarMode, statusBarMode2, statusBarMode3, statusBarMode4, statusBarMode5};
        $VALUES = statusBarModeArr;
        EnumEntriesKt.enumEntries(statusBarModeArr);
    }

    private StatusBarMode(String str, int i) {
    }

    public static StatusBarMode valueOf(String str) {
        return (StatusBarMode) Enum.valueOf(StatusBarMode.class, str);
    }

    public static StatusBarMode[] values() {
        return (StatusBarMode[]) $VALUES.clone();
    }

    public final int toTransitionModeInt() {
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 3;
        }
        if (i == 3) {
            return 6;
        }
        if (i == 4) {
            return 4;
        }
        if (i == 5) {
            return 0;
        }
        throw new NoWhenBranchMatchedException();
    }
}
