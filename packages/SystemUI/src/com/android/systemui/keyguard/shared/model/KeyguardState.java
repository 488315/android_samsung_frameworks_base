package com.android.systemui.keyguard.shared.model;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardState {
    public static final /* synthetic */ KeyguardState[] $VALUES;
    public static final KeyguardState ALTERNATE_BOUNCER;
    public static final KeyguardState AOD;
    public static final Companion Companion;
    public static final KeyguardState DOZING;
    public static final KeyguardState DREAMING;
    public static final KeyguardState DREAMING_LOCKSCREEN_HOSTED;
    public static final KeyguardState GLANCEABLE_HUB;
    public static final KeyguardState GONE;
    public static final KeyguardState LOCKSCREEN;
    public static final KeyguardState OCCLUDED;
    public static final KeyguardState OFF;
    public static final KeyguardState PRIMARY_BOUNCER;
    public static final KeyguardState UNDEFINED;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[KeyguardState.values().length];
                try {
                    iArr[KeyguardState.OFF.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[KeyguardState.DOZING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[KeyguardState.DREAMING.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[KeyguardState.DREAMING_LOCKSCREEN_HOSTED.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[KeyguardState.AOD.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                try {
                    iArr[KeyguardState.LOCKSCREEN.ordinal()] = 9;
                } catch (NoSuchFieldError unused9) {
                }
                try {
                    iArr[KeyguardState.GONE.ordinal()] = 10;
                } catch (NoSuchFieldError unused10) {
                }
                try {
                    iArr[KeyguardState.OCCLUDED.ordinal()] = 11;
                } catch (NoSuchFieldError unused11) {
                }
                try {
                    iArr[KeyguardState.UNDEFINED.ordinal()] = 12;
                } catch (NoSuchFieldError unused12) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public static boolean deviceIsAwakeInState(KeyguardState keyguardState) {
            switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    return false;
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    return true;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.DREAMING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.DREAMING_LOCKSCREEN_HOSTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[KeyguardState.UNDEFINED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        KeyguardState keyguardState = new KeyguardState("OFF", 0);
        OFF = keyguardState;
        KeyguardState keyguardState2 = new KeyguardState("DOZING", 1);
        DOZING = keyguardState2;
        KeyguardState keyguardState3 = new KeyguardState("DREAMING", 2);
        DREAMING = keyguardState3;
        KeyguardState keyguardState4 = new KeyguardState("DREAMING_LOCKSCREEN_HOSTED", 3);
        DREAMING_LOCKSCREEN_HOSTED = keyguardState4;
        KeyguardState keyguardState5 = new KeyguardState("AOD", 4);
        AOD = keyguardState5;
        KeyguardState keyguardState6 = new KeyguardState("ALTERNATE_BOUNCER", 5);
        ALTERNATE_BOUNCER = keyguardState6;
        KeyguardState keyguardState7 = new KeyguardState("PRIMARY_BOUNCER", 6);
        PRIMARY_BOUNCER = keyguardState7;
        KeyguardState keyguardState8 = new KeyguardState("LOCKSCREEN", 7);
        LOCKSCREEN = keyguardState8;
        KeyguardState keyguardState9 = new KeyguardState("GLANCEABLE_HUB", 8);
        GLANCEABLE_HUB = keyguardState9;
        KeyguardState keyguardState10 = new KeyguardState("GONE", 9);
        GONE = keyguardState10;
        KeyguardState keyguardState11 = new KeyguardState(PeripheralBarcodeConstants.Symbology.UNDEFINED, 10);
        UNDEFINED = keyguardState11;
        KeyguardState keyguardState12 = new KeyguardState("OCCLUDED", 11);
        OCCLUDED = keyguardState12;
        KeyguardState[] keyguardStateArr = {keyguardState, keyguardState2, keyguardState3, keyguardState4, keyguardState5, keyguardState6, keyguardState7, keyguardState8, keyguardState9, keyguardState10, keyguardState11, keyguardState12};
        $VALUES = keyguardStateArr;
        EnumEntriesKt.enumEntries(keyguardStateArr);
        Companion = new Companion(null);
    }

    private KeyguardState(String str, int i) {
    }

    public static KeyguardState valueOf(String str) {
        return (KeyguardState) Enum.valueOf(KeyguardState.class, str);
    }

    public static KeyguardState[] values() {
        return (KeyguardState[]) $VALUES.clone();
    }
}
