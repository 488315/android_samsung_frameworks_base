package com.android.systemui.keyguard.shared.model;

import android.util.Log;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardState {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ KeyguardState[] $VALUES;
    public static final KeyguardState ALTERNATE_BOUNCER;
    public static final KeyguardState AOD;
    public static final Companion Companion;
    public static final KeyguardState DOZING;
    public static final KeyguardState DREAMING;
    public static final KeyguardState GLANCEABLE_HUB;
    public static final KeyguardState GONE;
    public static final KeyguardState LOCKSCREEN;
    public static final KeyguardState OCCLUDED;
    public static final KeyguardState OFF;
    public static final KeyguardState PRIMARY_BOUNCER;
    public static final KeyguardState UNDEFINED;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 4;
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
                    iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[KeyguardState.LOCKSCREEN.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                try {
                    iArr[KeyguardState.GONE.ordinal()] = 9;
                } catch (NoSuchFieldError unused9) {
                }
                try {
                    iArr[KeyguardState.OCCLUDED.ordinal()] = 10;
                } catch (NoSuchFieldError unused10) {
                }
                try {
                    iArr[KeyguardState.UNDEFINED.ordinal()] = 11;
                } catch (NoSuchFieldError unused11) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean deviceIsAsleepInState(KeyguardState keyguardState) {
            return !deviceIsAwakeInState(keyguardState);
        }

        public static boolean deviceIsAwakeInState(KeyguardState keyguardState) {
            keyguardState.checkValidState();
            switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 5:
                    return false;
                case 4:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    return true;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                iArr[KeyguardState.AOD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardState.UNDEFINED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
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
        KeyguardState keyguardState4 = new KeyguardState("AOD", 3);
        AOD = keyguardState4;
        KeyguardState keyguardState5 = new KeyguardState("ALTERNATE_BOUNCER", 4);
        ALTERNATE_BOUNCER = keyguardState5;
        KeyguardState keyguardState6 = new KeyguardState("PRIMARY_BOUNCER", 5);
        PRIMARY_BOUNCER = keyguardState6;
        KeyguardState keyguardState7 = new KeyguardState("LOCKSCREEN", 6);
        LOCKSCREEN = keyguardState7;
        KeyguardState keyguardState8 = new KeyguardState("GLANCEABLE_HUB", 7);
        GLANCEABLE_HUB = keyguardState8;
        KeyguardState keyguardState9 = new KeyguardState("GONE", 8);
        GONE = keyguardState9;
        KeyguardState keyguardState10 = new KeyguardState(PeripheralBarcodeConstants.Symbology.UNDEFINED, 9);
        UNDEFINED = keyguardState10;
        KeyguardState keyguardState11 = new KeyguardState("OCCLUDED", 10);
        OCCLUDED = keyguardState11;
        KeyguardState[] keyguardStateArr = {keyguardState, keyguardState2, keyguardState3, keyguardState4, keyguardState5, keyguardState6, keyguardState7, keyguardState8, keyguardState9, keyguardState10, keyguardState11};
        $VALUES = keyguardStateArr;
        $ENTRIES = EnumEntriesKt.enumEntries(keyguardStateArr);
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

    public final void checkValidState() {
        if (this != UNDEFINED) {
            return;
        }
        Log.e("KeyguardState", this + " is not a valid state when scene container is disabled");
    }
}
