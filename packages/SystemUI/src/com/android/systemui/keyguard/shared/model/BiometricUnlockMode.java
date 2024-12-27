package com.android.systemui.keyguard.shared.model;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class BiometricUnlockMode {
    public static final /* synthetic */ BiometricUnlockMode[] $VALUES;
    public static final Companion Companion;
    public static final BiometricUnlockMode DISMISS_BOUNCER;
    public static final BiometricUnlockMode NONE;
    public static final BiometricUnlockMode ONLY_WAKE;
    public static final BiometricUnlockMode SHOW_BOUNCER;
    public static final BiometricUnlockMode UNLOCK_COLLAPSING;
    public static final BiometricUnlockMode WAKE_AND_UNLOCK;
    public static final BiometricUnlockMode WAKE_AND_UNLOCK_FROM_DREAM;
    public static final BiometricUnlockMode WAKE_AND_UNLOCK_PULSING;
    public static final Set dismissesKeyguardModes;
    public static final Set wakeAndUnlockModes;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        BiometricUnlockMode biometricUnlockMode = new BiometricUnlockMode(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
        NONE = biometricUnlockMode;
        BiometricUnlockMode biometricUnlockMode2 = new BiometricUnlockMode("WAKE_AND_UNLOCK", 1);
        WAKE_AND_UNLOCK = biometricUnlockMode2;
        BiometricUnlockMode biometricUnlockMode3 = new BiometricUnlockMode("WAKE_AND_UNLOCK_PULSING", 2);
        WAKE_AND_UNLOCK_PULSING = biometricUnlockMode3;
        BiometricUnlockMode biometricUnlockMode4 = new BiometricUnlockMode("SHOW_BOUNCER", 3);
        SHOW_BOUNCER = biometricUnlockMode4;
        BiometricUnlockMode biometricUnlockMode5 = new BiometricUnlockMode("ONLY_WAKE", 4);
        ONLY_WAKE = biometricUnlockMode5;
        BiometricUnlockMode biometricUnlockMode6 = new BiometricUnlockMode("UNLOCK_COLLAPSING", 5);
        UNLOCK_COLLAPSING = biometricUnlockMode6;
        BiometricUnlockMode biometricUnlockMode7 = new BiometricUnlockMode("DISMISS_BOUNCER", 6);
        DISMISS_BOUNCER = biometricUnlockMode7;
        BiometricUnlockMode biometricUnlockMode8 = new BiometricUnlockMode("WAKE_AND_UNLOCK_FROM_DREAM", 7);
        WAKE_AND_UNLOCK_FROM_DREAM = biometricUnlockMode8;
        BiometricUnlockMode[] biometricUnlockModeArr = {biometricUnlockMode, biometricUnlockMode2, biometricUnlockMode3, biometricUnlockMode4, biometricUnlockMode5, biometricUnlockMode6, biometricUnlockMode7, biometricUnlockMode8};
        $VALUES = biometricUnlockModeArr;
        EnumEntriesKt.enumEntries(biometricUnlockModeArr);
        Companion = new Companion(null);
        wakeAndUnlockModes = SetsKt__SetsKt.setOf(biometricUnlockMode2, biometricUnlockMode8, biometricUnlockMode3);
        dismissesKeyguardModes = SetsKt__SetsKt.setOf(biometricUnlockMode2, biometricUnlockMode3, biometricUnlockMode6, biometricUnlockMode8, biometricUnlockMode7);
    }

    private BiometricUnlockMode(String str, int i) {
    }

    public static BiometricUnlockMode valueOf(String str) {
        return (BiometricUnlockMode) Enum.valueOf(BiometricUnlockMode.class, str);
    }

    public static BiometricUnlockMode[] values() {
        return (BiometricUnlockMode[]) $VALUES.clone();
    }
}
