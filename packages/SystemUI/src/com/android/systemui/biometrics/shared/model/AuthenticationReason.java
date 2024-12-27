package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;

public interface AuthenticationReason {

    public final class BiometricPromptAuthentication implements AuthenticationReason {
        public static final BiometricPromptAuthentication INSTANCE = new BiometricPromptAuthentication();

        private BiometricPromptAuthentication() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof BiometricPromptAuthentication);
        }

        public final int hashCode() {
            return -1668294596;
        }

        public final String toString() {
            return "BiometricPromptAuthentication";
        }
    }

    public final class DeviceEntryAuthentication implements AuthenticationReason {
        public static final DeviceEntryAuthentication INSTANCE = new DeviceEntryAuthentication();

        private DeviceEntryAuthentication() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DeviceEntryAuthentication);
        }

        public final int hashCode() {
            return 268387260;
        }

        public final String toString() {
            return "DeviceEntryAuthentication";
        }
    }

    public final class NotRunning implements AuthenticationReason {
        public static final NotRunning INSTANCE = new NotRunning();

        private NotRunning() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotRunning);
        }

        public final int hashCode() {
            return 1553231364;
        }

        public final String toString() {
            return "NotRunning";
        }
    }

    public final class OtherAuthentication implements AuthenticationReason {
        public static final OtherAuthentication INSTANCE = new OtherAuthentication();

        private OtherAuthentication() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OtherAuthentication);
        }

        public final int hashCode() {
            return 1596116496;
        }

        public final String toString() {
            return "OtherAuthentication";
        }
    }

    public final class SettingsAuthentication implements AuthenticationReason {
        public final SettingsOperations settingsOperation;

        public SettingsAuthentication(SettingsOperations settingsOperations) {
            this.settingsOperation = settingsOperations;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SettingsAuthentication) && this.settingsOperation == ((SettingsAuthentication) obj).settingsOperation;
        }

        public final int hashCode() {
            return this.settingsOperation.hashCode();
        }

        public final String toString() {
            return "SettingsAuthentication(settingsOperation=" + this.settingsOperation + ")";
        }
    }

    public final class SettingsOperations {
        public static final /* synthetic */ SettingsOperations[] $VALUES;
        public static final SettingsOperations ENROLL_ENROLLING;
        public static final SettingsOperations ENROLL_FIND_SENSOR;
        public static final SettingsOperations OTHER;

        static {
            SettingsOperations settingsOperations = new SettingsOperations("ENROLL_ENROLLING", 0);
            ENROLL_ENROLLING = settingsOperations;
            SettingsOperations settingsOperations2 = new SettingsOperations("ENROLL_FIND_SENSOR", 1);
            ENROLL_FIND_SENSOR = settingsOperations2;
            SettingsOperations settingsOperations3 = new SettingsOperations("OTHER", 2);
            OTHER = settingsOperations3;
            SettingsOperations[] settingsOperationsArr = {settingsOperations, settingsOperations2, settingsOperations3};
            $VALUES = settingsOperationsArr;
            EnumEntriesKt.enumEntries(settingsOperationsArr);
        }

        private SettingsOperations(String str, int i) {
        }

        public static SettingsOperations valueOf(String str) {
            return (SettingsOperations) Enum.valueOf(SettingsOperations.class, str);
        }

        public static SettingsOperations[] values() {
            return (SettingsOperations[]) $VALUES.clone();
        }
    }

    public final class Unknown implements AuthenticationReason {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unknown);
        }

        public final int hashCode() {
            return 367281330;
        }

        public final String toString() {
            return "Unknown";
        }
    }
}
