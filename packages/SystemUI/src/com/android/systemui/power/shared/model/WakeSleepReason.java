package com.android.systemui.power.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WakeSleepReason {
    public static final /* synthetic */ WakeSleepReason[] $VALUES;
    public static final WakeSleepReason BIOMETRIC;
    public static final Companion Companion;
    public static final WakeSleepReason FOLD;
    public static final WakeSleepReason GESTURE;
    public static final WakeSleepReason KEY;
    public static final WakeSleepReason LID;
    public static final WakeSleepReason LIFT;
    public static final WakeSleepReason MOTION;
    public static final WakeSleepReason OTHER;
    public static final WakeSleepReason POWER_BUTTON;
    public static final WakeSleepReason TAP;
    public static final WakeSleepReason UNFOLD;
    private final boolean isTouch;
    private final int powerManagerWakeReason;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static WakeSleepReason fromPowerManagerWakeReason(int i) {
            if (i == 1) {
                return WakeSleepReason.POWER_BUTTON;
            }
            if (i == 4) {
                return WakeSleepReason.GESTURE;
            }
            if (i == 9) {
                return WakeSleepReason.LID;
            }
            if (i == 12) {
                return WakeSleepReason.UNFOLD;
            }
            if (i == 6) {
                return WakeSleepReason.KEY;
            }
            if (i == 7) {
                return WakeSleepReason.MOTION;
            }
            switch (i) {
                case 15:
                    return WakeSleepReason.TAP;
                case 16:
                    return WakeSleepReason.LIFT;
                case 17:
                    return WakeSleepReason.BIOMETRIC;
                default:
                    return WakeSleepReason.OTHER;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        WakeSleepReason wakeSleepReason = new WakeSleepReason("POWER_BUTTON", 0, false, 1);
        POWER_BUTTON = wakeSleepReason;
        WakeSleepReason wakeSleepReason2 = new WakeSleepReason("TAP", 1, true, 15);
        TAP = wakeSleepReason2;
        WakeSleepReason wakeSleepReason3 = new WakeSleepReason("GESTURE", 2, true, 4);
        GESTURE = wakeSleepReason3;
        WakeSleepReason wakeSleepReason4 = new WakeSleepReason("KEY", 3, false, 6);
        KEY = wakeSleepReason4;
        WakeSleepReason wakeSleepReason5 = new WakeSleepReason("MOTION", 4, false, 7);
        MOTION = wakeSleepReason5;
        WakeSleepReason wakeSleepReason6 = new WakeSleepReason("LID", 5, false, 9);
        LID = wakeSleepReason6;
        WakeSleepReason wakeSleepReason7 = new WakeSleepReason("UNFOLD", 6, false, 12);
        UNFOLD = wakeSleepReason7;
        WakeSleepReason wakeSleepReason8 = new WakeSleepReason("LIFT", 7, false, 16);
        LIFT = wakeSleepReason8;
        WakeSleepReason wakeSleepReason9 = new WakeSleepReason("BIOMETRIC", 8, false, 17);
        BIOMETRIC = wakeSleepReason9;
        WakeSleepReason wakeSleepReason10 = new WakeSleepReason("OTHER", 9, false, 0);
        OTHER = wakeSleepReason10;
        WakeSleepReason wakeSleepReason11 = new WakeSleepReason("FOLD", 10, false, 13);
        FOLD = wakeSleepReason11;
        WakeSleepReason[] wakeSleepReasonArr = {wakeSleepReason, wakeSleepReason2, wakeSleepReason3, wakeSleepReason4, wakeSleepReason5, wakeSleepReason6, wakeSleepReason7, wakeSleepReason8, wakeSleepReason9, wakeSleepReason10, wakeSleepReason11};
        $VALUES = wakeSleepReasonArr;
        EnumEntriesKt.enumEntries(wakeSleepReasonArr);
        Companion = new Companion(null);
    }

    private WakeSleepReason(String str, int i, boolean z, int i2) {
        this.isTouch = z;
        this.powerManagerWakeReason = i2;
    }

    public static WakeSleepReason valueOf(String str) {
        return (WakeSleepReason) Enum.valueOf(WakeSleepReason.class, str);
    }

    public static WakeSleepReason[] values() {
        return (WakeSleepReason[]) $VALUES.clone();
    }
}
