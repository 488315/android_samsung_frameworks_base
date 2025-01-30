package com.android.systemui.biometrics.domain.model;

import android.hardware.biometrics.PromptInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BiometricPromptRequest {
    public final String description;
    public final BiometricOperationInfo operationInfo;
    public final String subtitle;
    public final String title;
    public final BiometricUserInfo userInfo;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Biometric extends BiometricPromptRequest {
        public final BiometricModalities modalities;
        public final String negativeButtonText;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Biometric(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, BiometricModalities biometricModalities) {
            super(r3, r4, (r0 == null || (r0 = r0.toString()) == null) ? "" : r0, biometricUserInfo, biometricOperationInfo, null);
            String obj;
            String obj2;
            String obj3;
            String obj4;
            CharSequence title = promptInfo.getTitle();
            String str = "";
            String str2 = (title == null || (obj4 = title.toString()) == null) ? "" : obj4;
            CharSequence subtitle = promptInfo.getSubtitle();
            String str3 = (subtitle == null || (obj3 = subtitle.toString()) == null) ? "" : obj3;
            CharSequence description = promptInfo.getDescription();
            this.modalities = biometricModalities;
            CharSequence negativeButtonText = promptInfo.getNegativeButtonText();
            if (negativeButtonText != null && (obj = negativeButtonText.toString()) != null) {
                str = obj;
            }
            this.negativeButtonText = str;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Credential extends BiometricPromptRequest {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Password extends Credential {
            public Password(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Pattern extends Credential {
            public final boolean stealthMode;

            public Pattern(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, boolean z) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
                this.stealthMode = z;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Pin extends Credential {
            public Pin(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
            }
        }

        public /* synthetic */ Credential(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, DefaultConstructorMarker defaultConstructorMarker) {
            this(promptInfo, biometricUserInfo, biometricOperationInfo);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private Credential(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
            super(r3, r4, (r0 == null || (r10 = r0.toString()) == null) ? "" : r10, biometricUserInfo, biometricOperationInfo, null);
            String obj;
            String obj2;
            String obj3;
            CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
            deviceCredentialTitle = deviceCredentialTitle == null ? promptInfo.getTitle() : deviceCredentialTitle;
            String str = (deviceCredentialTitle == null || (obj3 = deviceCredentialTitle.toString()) == null) ? "" : obj3;
            CharSequence deviceCredentialSubtitle = promptInfo.getDeviceCredentialSubtitle();
            deviceCredentialSubtitle = deviceCredentialSubtitle == null ? promptInfo.getSubtitle() : deviceCredentialSubtitle;
            String str2 = (deviceCredentialSubtitle == null || (obj2 = deviceCredentialSubtitle.toString()) == null) ? "" : obj2;
            CharSequence deviceCredentialDescription = promptInfo.getDeviceCredentialDescription();
            deviceCredentialDescription = deviceCredentialDescription == null ? promptInfo.getDescription() : deviceCredentialDescription;
        }
    }

    public /* synthetic */ BiometricPromptRequest(String str, String str2, String str3, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, biometricUserInfo, biometricOperationInfo);
    }

    private BiometricPromptRequest(String str, String str2, String str3, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
        this.title = str;
        this.subtitle = str2;
        this.description = str3;
        this.userInfo = biometricUserInfo;
        this.operationInfo = biometricOperationInfo;
    }
}
