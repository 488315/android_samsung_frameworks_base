package com.android.systemui.biometrics.domain.model;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.hardware.biometrics.PromptContentView;
import android.hardware.biometrics.PromptInfo;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Biometric(android.hardware.biometrics.PromptInfo r12, com.android.systemui.biometrics.shared.model.BiometricUserInfo r13, com.android.systemui.biometrics.domain.model.BiometricOperationInfo r14, com.android.systemui.biometrics.shared.model.BiometricModalities r15, java.lang.String r16) {
            /*
                r11 = this;
                r9 = r11
                java.lang.CharSequence r0 = r12.getTitle()
                java.lang.String r10 = ""
                if (r0 == 0) goto L12
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L10
                goto L12
            L10:
                r1 = r0
                goto L13
            L12:
                r1 = r10
            L13:
                java.lang.CharSequence r0 = r12.getSubtitle()
                if (r0 == 0) goto L22
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L20
                goto L22
            L20:
                r2 = r0
                goto L23
            L22:
                r2 = r10
            L23:
                java.lang.CharSequence r0 = r12.getDescription()
                if (r0 == 0) goto L32
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L30
                goto L32
            L30:
                r3 = r0
                goto L33
            L32:
                r3 = r10
            L33:
                android.hardware.biometrics.PromptContentView r4 = r12.getContentView()
                boolean r7 = r12.isShowEmergencyCallButton()
                r8 = 0
                r0 = r11
                r5 = r13
                r6 = r14
                r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
                r0 = r15
                r9.modalities = r0
                r0 = r16
                r9.opPackageName = r0
                android.graphics.Bitmap r0 = r12.getLogo()
                r9.logoBitmap = r0
                java.lang.String r0 = r12.getLogoDescription()
                r9.logoDescription = r0
                java.lang.CharSequence r0 = r12.getNegativeButtonText()
                if (r0 == 0) goto L63
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L62
                goto L63
            L62:
                r10 = r0
            L63:
                r9.negativeButtonText = r10
                android.content.ComponentName r0 = r12.getComponentNameForConfirmDeviceCredentialActivity()
                r9.componentNameForConfirmDeviceCredentialActivity = r0
                boolean r0 = r12.isAllowBackgroundAuthentication()
                r9.allowBackgroundAuthentication = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric.<init>(android.hardware.biometrics.PromptInfo, com.android.systemui.biometrics.shared.model.BiometricUserInfo, com.android.systemui.biometrics.domain.model.BiometricOperationInfo, com.android.systemui.biometrics.shared.model.BiometricModalities, java.lang.String):void");
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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Credential(android.hardware.biometrics.PromptInfo r12, com.android.systemui.biometrics.shared.model.BiometricUserInfo r13, com.android.systemui.biometrics.domain.model.BiometricOperationInfo r14) {
            /*
                r11 = this;
                java.lang.CharSequence r0 = r12.getDeviceCredentialTitle()
                if (r0 != 0) goto La
                java.lang.CharSequence r0 = r12.getTitle()
            La:
                java.lang.String r1 = ""
                if (r0 == 0) goto L17
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L15
                goto L17
            L15:
                r3 = r0
                goto L18
            L17:
                r3 = r1
            L18:
                java.lang.CharSequence r0 = r12.getDeviceCredentialSubtitle()
                if (r0 != 0) goto L22
                java.lang.CharSequence r0 = r12.getSubtitle()
            L22:
                if (r0 == 0) goto L2d
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L2b
                goto L2d
            L2b:
                r4 = r0
                goto L2e
            L2d:
                r4 = r1
            L2e:
                java.lang.CharSequence r0 = r12.getDeviceCredentialDescription()
                if (r0 != 0) goto L38
                java.lang.CharSequence r0 = r12.getDescription()
            L38:
                if (r0 == 0) goto L43
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L41
                goto L43
            L41:
                r5 = r0
                goto L44
            L43:
                r5 = r1
            L44:
                android.hardware.biometrics.PromptContentView r6 = r12.getContentView()
                boolean r9 = r12.isShowEmergencyCallButton()
                r10 = 0
                r2 = r11
                r7 = r13
                r8 = r14
                r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential.<init>(android.hardware.biometrics.PromptInfo, com.android.systemui.biometrics.shared.model.BiometricUserInfo, com.android.systemui.biometrics.domain.model.BiometricOperationInfo):void");
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
