package com.android.keyguard;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActiveUnlockConfig implements Dumpable {
    public final ContentResolver contentResolver;
    public KeyguardUpdateMonitor keyguardUpdateMonitor;
    public boolean requestActiveUnlockOnBioFail;
    public boolean requestActiveUnlockOnUnlockIntent;
    public boolean requestActiveUnlockOnWakeup;
    public final SecureSettings secureSettings;
    public final Set faceErrorsToTriggerBiometricFailOn = new LinkedHashSet();
    public final Set faceAcquireInfoToTriggerBiometricFailOn = new LinkedHashSet();
    public final Set onUnlockIntentWhenBiometricEnrolled = new LinkedHashSet();
    public final Set wakeupsConsideredUnlockIntents = new LinkedHashSet();
    public final Set wakeupsToForceDismissKeyguard = new LinkedHashSet();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ActiveUnlockRequestOrigin {
        WAKE,
        UNLOCK_INTENT,
        BIOMETRIC_FAIL,
        ASSISTANT
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BiometricType {
        NONE(0),
        ANY_FACE(1),
        ANY_FINGERPRINT(2),
        UNDER_DISPLAY_FINGERPRINT(3);

        private final int intValue;

        BiometricType(int i) {
            this.intValue = i;
        }

        public final int getIntValue() {
            return this.intValue;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ActiveUnlockRequestOrigin.values().length];
            try {
                iArr[ActiveUnlockRequestOrigin.WAKE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.UNLOCK_INTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.BIOMETRIC_FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ActiveUnlockRequestOrigin.ASSISTANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public ActiveUnlockConfig(Handler handler, SecureSettings secureSettings, ContentResolver contentResolver, DumpManager dumpManager) {
        this.secureSettings = secureSettings;
        this.contentResolver = contentResolver;
        ActiveUnlockConfig$settingsObserver$1 activeUnlockConfig$settingsObserver$1 = new ActiveUnlockConfig$settingsObserver$1(this, handler);
        Iterator it = CollectionsKt__CollectionsKt.listOf(activeUnlockConfig$settingsObserver$1.wakeUri, activeUnlockConfig$settingsObserver$1.unlockIntentUri, activeUnlockConfig$settingsObserver$1.bioFailUri, activeUnlockConfig$settingsObserver$1.faceErrorsUri, activeUnlockConfig$settingsObserver$1.faceAcquireInfoUri, activeUnlockConfig$settingsObserver$1.unlockIntentWhenBiometricEnrolledUri, activeUnlockConfig$settingsObserver$1.wakeupsConsideredUnlockIntentsUri, activeUnlockConfig$settingsObserver$1.wakeupsToForceDismissKeyguardUri).iterator();
        while (it.hasNext()) {
            activeUnlockConfig$settingsObserver$1.this$0.contentResolver.registerContentObserver((Uri) it.next(), false, activeUnlockConfig$settingsObserver$1, -1);
        }
        activeUnlockConfig$settingsObserver$1.onChange(true, new ArrayList(), 0, KeyguardUpdateMonitor.getCurrentUser());
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Unit unit;
        String str;
        printWriter.println("Settings:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("   requestActiveUnlockOnWakeup=", this.requestActiveUnlockOnWakeup, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("   requestActiveUnlockOnUnlockIntent=", this.requestActiveUnlockOnUnlockIntent, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("   requestActiveUnlockOnBioFail=", this.requestActiveUnlockOnBioFail, printWriter);
        Set set = this.onUnlockIntentWhenBiometricEnrolled;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        Iterator it = set.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            BiometricType[] values = BiometricType.values();
            int length = values.length;
            int i = 0;
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
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        if (keyguardUpdateMonitor != null) {
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("   shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment=", shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment(), printWriter);
            printWriter.println("   faceEnrolled=" + keyguardUpdateMonitor.mIsFaceEnrolled);
            printWriter.println("   fpEnrolled=" + keyguardUpdateMonitor.getCachedIsUnlockWithFingerprintPossible(KeyguardUpdateMonitor.getCurrentUser()));
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("   udfpsEnrolled=", keyguardUpdateMonitor.isUdfpsEnrolled(), printWriter);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            printWriter.println("   keyguardUpdateMonitor is uninitialized");
        }
    }

    public final boolean shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment() {
        KeyguardUpdateMonitor keyguardUpdateMonitor;
        if (this.requestActiveUnlockOnBioFail && (keyguardUpdateMonitor = this.keyguardUpdateMonitor) != null) {
            boolean z = keyguardUpdateMonitor.mIsFaceEnrolled;
            boolean cachedIsUnlockWithFingerprintPossible = keyguardUpdateMonitor.getCachedIsUnlockWithFingerprintPossible(KeyguardUpdateMonitor.getCurrentUser());
            boolean isUdfpsEnrolled = keyguardUpdateMonitor.isUdfpsEnrolled();
            Set set = this.onUnlockIntentWhenBiometricEnrolled;
            if (!z && !cachedIsUnlockWithFingerprintPossible) {
                return set.contains(Integer.valueOf(BiometricType.NONE.getIntValue()));
            }
            if (!z && cachedIsUnlockWithFingerprintPossible) {
                return set.contains(Integer.valueOf(BiometricType.ANY_FINGERPRINT.getIntValue())) || (isUdfpsEnrolled && set.contains(Integer.valueOf(BiometricType.UNDER_DISPLAY_FINGERPRINT.getIntValue())));
            }
            if (!cachedIsUnlockWithFingerprintPossible && z) {
                return set.contains(Integer.valueOf(BiometricType.ANY_FACE.getIntValue()));
            }
        }
        return false;
    }
}
