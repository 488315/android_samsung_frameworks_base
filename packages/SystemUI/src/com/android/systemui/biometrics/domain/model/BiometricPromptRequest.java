package com.android.systemui.biometrics.domain.model;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.hardware.biometrics.PromptContentView;
import android.hardware.biometrics.PromptInfo;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BiometricPromptRequest {
    public final PromptContentView contentView;
    public final String description;
    public final BiometricOperationInfo operationInfo;
    public final boolean showEmergencyCallButton;
    public final String subtitle;
    public final String title;
    public final BiometricUserInfo userInfo;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public Biometric(android.hardware.biometrics.PromptInfo r11, com.android.systemui.biometrics.shared.model.BiometricUserInfo r12, com.android.systemui.biometrics.domain.model.BiometricOperationInfo r13, com.android.systemui.biometrics.shared.model.BiometricModalities r14, java.lang.String r15) {
            /*
                r10 = this;
                java.lang.CharSequence r0 = r11.getTitle()
                java.lang.String r9 = ""
                if (r0 == 0) goto L11
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto Lf
                goto L11
            Lf:
                r1 = r0
                goto L12
            L11:
                r1 = r9
            L12:
                java.lang.CharSequence r0 = r11.getSubtitle()
                if (r0 == 0) goto L21
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L1f
                goto L21
            L1f:
                r2 = r0
                goto L22
            L21:
                r2 = r9
            L22:
                java.lang.CharSequence r0 = r11.getDescription()
                if (r0 == 0) goto L31
                java.lang.String r0 = r0.toString()
                if (r0 != 0) goto L2f
                goto L31
            L2f:
                r3 = r0
                goto L32
            L31:
                r3 = r9
            L32:
                android.hardware.biometrics.PromptContentView r4 = r11.getContentView()
                boolean r7 = r11.isShowEmergencyCallButton()
                r8 = 0
                r0 = r10
                r5 = r12
                r6 = r13
                r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
                r10.modalities = r14
                r10.opPackageName = r15
                android.graphics.Bitmap r1 = r11.getLogo()
                r10.logoBitmap = r1
                java.lang.String r1 = r11.getLogoDescription()
                r10.logoDescription = r1
                java.lang.CharSequence r1 = r11.getNegativeButtonText()
                if (r1 == 0) goto L5f
                java.lang.String r1 = r1.toString()
                if (r1 != 0) goto L5e
                goto L5f
            L5e:
                r9 = r1
            L5f:
                r10.negativeButtonText = r9
                android.content.ComponentName r1 = r11.getRealCallerForConfirmDeviceCredentialActivity()
                r10.componentNameForConfirmDeviceCredentialActivity = r1
                boolean r1 = r11.isAllowBackgroundAuthentication()
                r10.allowBackgroundAuthentication = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric.<init>(android.hardware.biometrics.PromptInfo, com.android.systemui.biometrics.shared.model.BiometricUserInfo, com.android.systemui.biometrics.domain.model.BiometricOperationInfo, com.android.systemui.biometrics.shared.model.BiometricModalities, java.lang.String):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Credential extends BiometricPromptRequest {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Password extends Credential {
            public Password(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Pattern extends Credential {
            public final boolean stealthMode;

            public Pattern(PromptInfo promptInfo, BiometricUserInfo biometricUserInfo, BiometricOperationInfo biometricOperationInfo, boolean z) {
                super(promptInfo, biometricUserInfo, biometricOperationInfo, null);
                this.stealthMode = z;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
