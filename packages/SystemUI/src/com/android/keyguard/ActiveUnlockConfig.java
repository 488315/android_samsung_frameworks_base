package com.android.keyguard;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ActiveUnlockConfig implements Dumpable {
    public final ContentResolver contentResolver;
    public final Lazy keyguardUpdateMonitor;
    public boolean requestActiveUnlockOnBioFail;
    public boolean requestActiveUnlockOnUnlockIntent;
    public boolean requestActiveUnlockOnUnlockIntentLegacy;
    public boolean requestActiveUnlockOnWakeup;
    public final SecureSettings secureSettings;
    public final SelectedUserInteractor selectedUserInteractor;
    public final Set faceErrorsToTriggerBiometricFailOn = new LinkedHashSet();
    public final Set faceAcquireInfoToTriggerBiometricFailOn = new LinkedHashSet();
    public final Set onUnlockIntentWhenBiometricEnrolled = new LinkedHashSet();
    public final Set wakeupsConsideredUnlockIntents = new LinkedHashSet();
    public final Set wakeupsToForceDismissKeyguard = new LinkedHashSet();

    public final class ActiveUnlockRequestOrigin {
        public static final /* synthetic */ ActiveUnlockRequestOrigin[] $VALUES;
        public static final ActiveUnlockRequestOrigin ASSISTANT;
        public static final ActiveUnlockRequestOrigin BIOMETRIC_FAIL;
        public static final ActiveUnlockRequestOrigin UNLOCK_INTENT;
        public static final ActiveUnlockRequestOrigin UNLOCK_INTENT_LEGACY;
        public static final ActiveUnlockRequestOrigin WAKE;

        static {
            ActiveUnlockRequestOrigin activeUnlockRequestOrigin = new ActiveUnlockRequestOrigin("WAKE", 0);
            WAKE = activeUnlockRequestOrigin;
            ActiveUnlockRequestOrigin activeUnlockRequestOrigin2 = new ActiveUnlockRequestOrigin("UNLOCK_INTENT", 1);
            UNLOCK_INTENT = activeUnlockRequestOrigin2;
            ActiveUnlockRequestOrigin activeUnlockRequestOrigin3 = new ActiveUnlockRequestOrigin("BIOMETRIC_FAIL", 2);
            BIOMETRIC_FAIL = activeUnlockRequestOrigin3;
            ActiveUnlockRequestOrigin activeUnlockRequestOrigin4 = new ActiveUnlockRequestOrigin("ASSISTANT", 3);
            ASSISTANT = activeUnlockRequestOrigin4;
            ActiveUnlockRequestOrigin activeUnlockRequestOrigin5 = new ActiveUnlockRequestOrigin("UNLOCK_INTENT_LEGACY", 4);
            UNLOCK_INTENT_LEGACY = activeUnlockRequestOrigin5;
            ActiveUnlockRequestOrigin[] activeUnlockRequestOriginArr = {activeUnlockRequestOrigin, activeUnlockRequestOrigin2, activeUnlockRequestOrigin3, activeUnlockRequestOrigin4, activeUnlockRequestOrigin5};
            $VALUES = activeUnlockRequestOriginArr;
            EnumEntriesKt.enumEntries(activeUnlockRequestOriginArr);
        }

        private ActiveUnlockRequestOrigin(String str, int i) {
        }

        public static ActiveUnlockRequestOrigin valueOf(String str) {
            return (ActiveUnlockRequestOrigin) Enum.valueOf(ActiveUnlockRequestOrigin.class, str);
        }

        public static ActiveUnlockRequestOrigin[] values() {
            return (ActiveUnlockRequestOrigin[]) $VALUES.clone();
        }
    }

    public final class BiometricType {
        public static final /* synthetic */ BiometricType[] $VALUES;
        public static final BiometricType ANY_FACE;
        public static final BiometricType ANY_FINGERPRINT;
        public static final BiometricType NONE;
        public static final BiometricType UNDER_DISPLAY_FINGERPRINT;
        private final int intValue;

        static {
            BiometricType biometricType = new BiometricType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0, 0);
            NONE = biometricType;
            BiometricType biometricType2 = new BiometricType("ANY_FACE", 1, 1);
            ANY_FACE = biometricType2;
            BiometricType biometricType3 = new BiometricType("ANY_FINGERPRINT", 2, 2);
            ANY_FINGERPRINT = biometricType3;
            BiometricType biometricType4 = new BiometricType("UNDER_DISPLAY_FINGERPRINT", 3, 3);
            UNDER_DISPLAY_FINGERPRINT = biometricType4;
            BiometricType[] biometricTypeArr = {biometricType, biometricType2, biometricType3, biometricType4};
            $VALUES = biometricTypeArr;
            EnumEntriesKt.enumEntries(biometricTypeArr);
        }

        private BiometricType(String str, int i, int i2) {
            this.intValue = i2;
        }

        public static BiometricType valueOf(String str) {
            return (BiometricType) Enum.valueOf(BiometricType.class, str);
        }

        public static BiometricType[] values() {
            return (BiometricType[]) $VALUES.clone();
        }

        public final int getIntValue() {
            return this.intValue;
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ActiveUnlockRequestOrigin.values().length];
            try {
                iArr[ActiveUnlockRequestOrigin.WAKE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.UNLOCK_INTENT_LEGACY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.UNLOCK_INTENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.BIOMETRIC_FAIL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.ASSISTANT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public ActiveUnlockConfig(Handler handler, SecureSettings secureSettings, ContentResolver contentResolver, SelectedUserInteractor selectedUserInteractor, Lazy lazy, DumpManager dumpManager) {
        this.secureSettings = secureSettings;
        this.contentResolver = contentResolver;
        this.selectedUserInteractor = selectedUserInteractor;
        this.keyguardUpdateMonitor = lazy;
        ActiveUnlockConfig$settingsObserver$1 activeUnlockConfig$settingsObserver$1 = new ActiveUnlockConfig$settingsObserver$1(this, handler);
        Iterator it = CollectionsKt__CollectionsKt.listOf(activeUnlockConfig$settingsObserver$1.wakeUri, activeUnlockConfig$settingsObserver$1.unlockIntentUri, activeUnlockConfig$settingsObserver$1.bioFailUri, activeUnlockConfig$settingsObserver$1.faceErrorsUri, activeUnlockConfig$settingsObserver$1.faceAcquireInfoUri, activeUnlockConfig$settingsObserver$1.unlockIntentWhenBiometricEnrolledUri, activeUnlockConfig$settingsObserver$1.wakeupsConsideredUnlockIntentsUri, activeUnlockConfig$settingsObserver$1.wakeupsToForceDismissKeyguardUri).iterator();
        while (it.hasNext()) {
            activeUnlockConfig$settingsObserver$1.this$0.contentResolver.registerContentObserver((Uri) it.next(), false, activeUnlockConfig$settingsObserver$1, -1);
        }
        activeUnlockConfig$settingsObserver$1.onChange(true, new ArrayList(), 0, activeUnlockConfig$settingsObserver$1.this$0.selectedUserInteractor.getSelectedUserId(false));
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        printWriter.println("Settings:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   requestActiveUnlockOnWakeup=", this.requestActiveUnlockOnWakeup, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   requestActiveUnlockOnUnlockIntentLegacy=", this.requestActiveUnlockOnUnlockIntentLegacy, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   requestActiveUnlockOnUnlockIntent=", this.requestActiveUnlockOnUnlockIntent, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   requestActiveUnlockOnBioFail=", this.requestActiveUnlockOnBioFail, printWriter);
        Set set = this.onUnlockIntentWhenBiometricEnrolled;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        Iterator it = set.iterator();
        while (true) {
            int i = 0;
            if (!it.hasNext()) {
                break;
            }
            int intValue = ((Number) it.next()).intValue();
            BiometricType[] values = BiometricType.values();
            int length = values.length;
            while (true) {
                if (i >= length) {
                    str = "UNKNOWN";
                    break;
                }
                BiometricType biometricType = values[i];
                if (biometricType.getIntValue() == intValue) {
                    str = biometricType.name();
                    break;
                }
                i++;
            }
            arrayList.add(str);
        }
        printWriter.println("   requestActiveUnlockOnUnlockIntentWhenBiometricEnrolled=" + arrayList);
        printWriter.println("   requestActiveUnlockOnFaceError=" + this.faceErrorsToTriggerBiometricFailOn);
        printWriter.println("   requestActiveUnlockOnFaceAcquireInfo=" + this.faceAcquireInfoToTriggerBiometricFailOn);
        Set set2 = this.wakeupsConsideredUnlockIntents;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(PowerManager.wakeReasonToString(((Number) it2.next()).intValue()));
        }
        printWriter.println("   activeUnlockWakeupsConsideredUnlockIntents=" + arrayList2);
        Set set3 = this.wakeupsToForceDismissKeyguard;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set3, 10));
        Iterator it3 = set3.iterator();
        while (it3.hasNext()) {
            arrayList3.add(PowerManager.wakeReasonToString(((Number) it3.next()).intValue()));
        }
        printWriter.println("   activeUnlockFromWakeupsToAlwaysDismissKeyguard=" + arrayList3);
        printWriter.println("Current state:");
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) this.keyguardUpdateMonitor.get();
        printWriter.println("   shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment=" + shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   isFaceEnabledAndEnrolled=", keyguardUpdateMonitor.isFaceEnabledAndEnrolled(), printWriter);
        printWriter.println("   fpUnlockPossible=" + keyguardUpdateMonitor.isUnlockWithFingerprintPossible(this.selectedUserInteractor.getSelectedUserId(false)));
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("   udfpsEnrolled=", keyguardUpdateMonitor.isUdfpsEnrolled(), printWriter);
    }

    public final boolean shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment() {
        if (!this.requestActiveUnlockOnBioFail) {
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) this.keyguardUpdateMonitor.get();
        boolean isFaceEnabledAndEnrolled = keyguardUpdateMonitor.isFaceEnabledAndEnrolled();
        boolean isUnlockWithFingerprintPossible = keyguardUpdateMonitor.isUnlockWithFingerprintPossible(this.selectedUserInteractor.getSelectedUserId(false));
        boolean isUdfpsEnrolled = keyguardUpdateMonitor.isUdfpsEnrolled();
        if (!isFaceEnabledAndEnrolled && !isUnlockWithFingerprintPossible) {
            return this.onUnlockIntentWhenBiometricEnrolled.contains(Integer.valueOf(BiometricType.NONE.getIntValue()));
        }
        if (!isFaceEnabledAndEnrolled && isUnlockWithFingerprintPossible) {
            return this.onUnlockIntentWhenBiometricEnrolled.contains(Integer.valueOf(BiometricType.ANY_FINGERPRINT.getIntValue())) || (isUdfpsEnrolled && this.onUnlockIntentWhenBiometricEnrolled.contains(Integer.valueOf(BiometricType.UNDER_DISPLAY_FINGERPRINT.getIntValue())));
        }
        if (isUnlockWithFingerprintPossible || !isFaceEnabledAndEnrolled) {
            return false;
        }
        return this.onUnlockIntentWhenBiometricEnrolled.contains(Integer.valueOf(BiometricType.ANY_FACE.getIntValue()));
    }
}
