package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PromptKind {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Biometric implements PromptKind {
        public final BiometricModalities activeModalities;
        public final PaneType paneType;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class PaneType {
            public static final /* synthetic */ PaneType[] $VALUES;
            public static final PaneType ONE_PANE_LARGE_SCREEN_LANDSCAPE;
            public static final PaneType ONE_PANE_NO_SENSOR_LANDSCAPE;
            public static final PaneType ONE_PANE_PORTRAIT;
            public static final PaneType TWO_PANE_LANDSCAPE;

            static {
                PaneType paneType = new PaneType("TWO_PANE_LANDSCAPE", 0);
                TWO_PANE_LANDSCAPE = paneType;
                PaneType paneType2 = new PaneType("ONE_PANE_PORTRAIT", 1);
                ONE_PANE_PORTRAIT = paneType2;
                PaneType paneType3 = new PaneType("ONE_PANE_NO_SENSOR_LANDSCAPE", 2);
                ONE_PANE_NO_SENSOR_LANDSCAPE = paneType3;
                PaneType paneType4 = new PaneType("ONE_PANE_LARGE_SCREEN_LANDSCAPE", 3);
                ONE_PANE_LARGE_SCREEN_LANDSCAPE = paneType4;
                PaneType[] paneTypeArr = {paneType, paneType2, paneType3, paneType4};
                $VALUES = paneTypeArr;
                EnumEntriesKt.enumEntries(paneTypeArr);
            }

            private PaneType(String str, int i) {
            }

            public static PaneType valueOf(String str) {
                return (PaneType) Enum.valueOf(PaneType.class, str);
            }

            public static PaneType[] values() {
                return (PaneType[]) $VALUES.clone();
            }
        }

        public Biometric() {
            this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Biometric)) {
                return false;
            }
            Biometric biometric = (Biometric) obj;
            return Intrinsics.areEqual(this.activeModalities, biometric.activeModalities) && this.paneType == biometric.paneType;
        }

        public final int hashCode() {
            return this.paneType.hashCode() + (this.activeModalities.hashCode() * 31);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isBiometric() {
            return true;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isCredential() {
            return DefaultImpls.isCredential(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isOnePaneNoSensorLandscapeBiometric() {
            return DefaultImpls.isOnePaneNoSensorLandscapeBiometric(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isTwoPaneLandscapeBiometric() {
            return DefaultImpls.isTwoPaneLandscapeBiometric(this);
        }

        public final String toString() {
            return "Biometric(activeModalities=" + this.activeModalities + ", paneType=" + this.paneType + ")";
        }

        public Biometric(BiometricModalities biometricModalities, PaneType paneType) {
            this.activeModalities = biometricModalities;
            this.paneType = paneType;
        }

        public /* synthetic */ Biometric(BiometricModalities biometricModalities, PaneType paneType, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? new BiometricModalities(null, null, 3, null) : biometricModalities, (i & 2) != 0 ? PaneType.ONE_PANE_PORTRAIT : paneType);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class DefaultImpls {
        public static boolean isCredential(PromptKind promptKind) {
            return (promptKind instanceof Pin) || (promptKind instanceof Pattern) || (promptKind instanceof Password);
        }

        public static boolean isOnePaneNoSensorLandscapeBiometric(PromptKind promptKind) {
            Biometric biometric = promptKind instanceof Biometric ? (Biometric) promptKind : null;
            return (biometric != null ? biometric.paneType : null) == Biometric.PaneType.ONE_PANE_NO_SENSOR_LANDSCAPE;
        }

        public static boolean isTwoPaneLandscapeBiometric(PromptKind promptKind) {
            Biometric biometric = promptKind instanceof Biometric ? (Biometric) promptKind : null;
            return (biometric != null ? biometric.paneType : null) == Biometric.PaneType.TWO_PANE_LANDSCAPE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class None implements PromptKind {
        public static final None INSTANCE = new None();

        private None() {
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isBiometric() {
            return this instanceof Biometric;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isCredential() {
            return DefaultImpls.isCredential(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isOnePaneNoSensorLandscapeBiometric() {
            return DefaultImpls.isOnePaneNoSensorLandscapeBiometric(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isTwoPaneLandscapeBiometric() {
            return DefaultImpls.isTwoPaneLandscapeBiometric(this);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Password implements PromptKind {
        public static final Password INSTANCE = new Password();

        private Password() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Password);
        }

        public final int hashCode() {
            return 1266244087;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isBiometric() {
            return this instanceof Biometric;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isCredential() {
            return DefaultImpls.isCredential(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isOnePaneNoSensorLandscapeBiometric() {
            return DefaultImpls.isOnePaneNoSensorLandscapeBiometric(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isTwoPaneLandscapeBiometric() {
            return DefaultImpls.isTwoPaneLandscapeBiometric(this);
        }

        public final String toString() {
            return "Password";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Pattern implements PromptKind {
        public static final Pattern INSTANCE = new Pattern();

        private Pattern() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Pattern);
        }

        public final int hashCode() {
            return 873066676;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isBiometric() {
            return this instanceof Biometric;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isCredential() {
            return DefaultImpls.isCredential(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isOnePaneNoSensorLandscapeBiometric() {
            return DefaultImpls.isOnePaneNoSensorLandscapeBiometric(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isTwoPaneLandscapeBiometric() {
            return DefaultImpls.isTwoPaneLandscapeBiometric(this);
        }

        public final String toString() {
            return "Pattern";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Pin implements PromptKind {
        public static final Pin INSTANCE = new Pin();

        private Pin() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Pin);
        }

        public final int hashCode() {
            return -693442375;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isBiometric() {
            return this instanceof Biometric;
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isCredential() {
            return DefaultImpls.isCredential(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isOnePaneNoSensorLandscapeBiometric() {
            return DefaultImpls.isOnePaneNoSensorLandscapeBiometric(this);
        }

        @Override // com.android.systemui.biometrics.shared.model.PromptKind
        public final boolean isTwoPaneLandscapeBiometric() {
            return DefaultImpls.isTwoPaneLandscapeBiometric(this);
        }

        public final String toString() {
            return "Pin";
        }
    }

    boolean isBiometric();

    boolean isCredential();

    boolean isOnePaneNoSensorLandscapeBiometric();

    boolean isTwoPaneLandscapeBiometric();
}
