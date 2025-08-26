package com.android.systemui.biometrics.domain.model;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.hardware.biometrics.PromptContentView;
import android.hardware.biometrics.PromptInfo;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class BiometricPromptRequest {
    public final PromptContentView contentView;
    public final String description;
    public final BiometricOperationInfo operationInfo;
    public final boolean showEmergencyCallButton;
    public final String subtitle;
    public final String title;
    public final BiometricUserInfo userInfo;

    public final class Biometric extends BiometricPromptRequest {
        public final boolean allowBackgroundAuthentication;
        public final ComponentName componentNameForConfirmDeviceCredentialActivity;
        public final Bitmap logoBitmap;
        public final String logoDescription;
        public final BiometricModalities modalities;
        public final String negativeButtonText;
        public final String opPackageName;

        /* JADX WARN: Illegal instructions before constructor call */
        public Biometric(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, BiometricModalities biometricModalities, String str) {
            String string;
            String string2;
            String string3;
            String string4;
            CharSequence title = promptInfo.getTitle();
            String str2 = "";
            String str3 = (title == null || (string4 = title.toString()) == null) ? "" : string4;
            CharSequence subtitle = promptInfo.getSubtitle();
            String str4 = (subtitle == null || (string3 = subtitle.toString()) == null) ? "" : string3;
            CharSequence description = promptInfo.getDescription();
            super(str3, str4, (description == null || (string2 = description.toString()) == null) ? "" : string2, promptInfo.getContentView(), biometricUserInfo, biometricOperationInfo, promptInfo.isShowEmergencyCallButton(), null);
            this.modalities = biometricModalities;
            this.opPackageName = str;
            this.logoBitmap = promptInfo.getLogo();
            this.logoDescription = promptInfo.getLogoDescription();
            CharSequence negativeButtonText = promptInfo.getNegativeButtonText();
            if (negativeButtonText != null && (string = negativeButtonText.toString()) != null) {
                str2 = string;
            }
            this.negativeButtonText = str2;
            this.componentNameForConfirmDeviceCredentialActivity = promptInfo.getRealCallerForConfirmDeviceCredentialActivity();
            this.allowBackgroundAuthentication = promptInfo.isAllowBackgroundAuthentication();
        }
    }

    public abstract class Credential extends BiometricPromptRequest {

        public final class Password extends Credential {
            public Password(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
            }
        }

        public final class Pattern extends Credential {
            public final boolean stealthMode;

            public Pattern(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, boolean z) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
                this.stealthMode = z;
            }
        }

        public final class Pin extends Credential {
            public Pin(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
            }
        }

        public /* synthetic */ Credential(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, DefaultConstructorMarker defaultConstructorMarker) {
            this(promptInfo, biometricUserInfo, biometricOperationInfo);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private Credential(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
            String string;
            String string2;
            String string3;
            CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
            deviceCredentialTitle = deviceCredentialTitle == null ? promptInfo.getTitle() : deviceCredentialTitle;
            String str = (deviceCredentialTitle == null || (string3 = deviceCredentialTitle.toString()) == null) ? "" : string3;
            CharSequence deviceCredentialSubtitle = promptInfo.getDeviceCredentialSubtitle();
            deviceCredentialSubtitle = deviceCredentialSubtitle == null ? promptInfo.getSubtitle() : deviceCredentialSubtitle;
            String str2 = (deviceCredentialSubtitle == null || (string2 = deviceCredentialSubtitle.toString()) == null) ? "" : string2;
            CharSequence deviceCredentialDescription = promptInfo.getDeviceCredentialDescription();
            deviceCredentialDescription = deviceCredentialDescription == null ? promptInfo.getDescription() : deviceCredentialDescription;
            super(str, str2, (deviceCredentialDescription == null || (string = deviceCredentialDescription.toString()) == null) ? "" : string, promptInfo.getContentView(), biometricUserInfo, biometricOperationInfo, promptInfo.isShowEmergencyCallButton(), null);
        }
    }

    public /* synthetic */ BiometricPromptRequest(String str, String str2, String str3, PromptContentView promptContentView, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, promptContentView, biometricUserInfo, biometricOperationInfo, z);
    }

    private BiometricPromptRequest(String str, String str2, String str3, PromptContentView promptContentView, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, boolean z) {
        this.title = str;
        this.subtitle = str2;
        this.description = str3;
        this.contentView = promptContentView;
        this.userInfo = biometricUserInfo;
        this.operationInfo = biometricOperationInfo;
        this.showEmergencyCallButton = z;
    }
}
