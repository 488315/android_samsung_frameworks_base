package com.android.keyguard;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.common.buffer.RingBuffer;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardFaceListenModel extends KeyguardListenModel {
    public static final List TABLE_HEADERS;
    public boolean alternateBouncerShowing;
    public final Lazy asStringList$delegate;
    public final boolean authInterruptActive;
    public boolean biometricSettingEnabledForUser;
    public boolean bouncerFullyShown;
    public boolean faceAndFpNotAuthenticated;
    public boolean faceAuthAllowed;
    public boolean faceDisabled;
    public boolean faceLockedOut;
    public boolean goingToSleep;
    public boolean keyguardAwake;
    public boolean keyguardGoingAway;
    public boolean listening;
    public boolean listeningForFaceAssistant;
    public boolean occludingAppRequestingFaceAuth;
    public boolean postureAllowsListening;
    public boolean secureCameraLaunched;
    public boolean supportsDetect;
    public boolean switchingUser;
    public boolean systemUser;
    public long timeMillis;
    public boolean udfpsFingerDown;
    public int userId;
    public boolean userNotTrustedOrDetectionIsNeeded;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Buffer {
        public final RingBuffer buffer = new RingBuffer(40, new Function0() { // from class: com.android.keyguard.KeyguardFaceListenModel$Buffer$buffer$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new KeyguardFaceListenModel(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 8388607, null);
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf(PhoneRestrictionPolicy.TIMESTAMP, "time_millis", "userId", "listening", "authInterruptActive", "biometricSettingEnabledForUser", "bouncerFullyShown", "faceAndFpNotAuthenticated", "faceAuthAllowed", "faceDisabled", "faceLockedOut", "goingToSleep", "keyguardAwake", "keyguardGoingAway", "listeningForFaceAssistant", "occludingAppRequestingFaceAuth", "postureAllowsListening", "secureCameraLaunched", "supportsDetect", "switchingUser", "systemUser", "udfpsBouncerShowing", "udfpsFingerDown", "userNotTrustedOrDetectionIsNeeded");
    }

    public KeyguardFaceListenModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 8388607, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardFaceListenModel)) {
            return false;
        }
        KeyguardFaceListenModel keyguardFaceListenModel = (KeyguardFaceListenModel) obj;
        return this.timeMillis == keyguardFaceListenModel.timeMillis && this.userId == keyguardFaceListenModel.userId && this.listening == keyguardFaceListenModel.listening && this.alternateBouncerShowing == keyguardFaceListenModel.alternateBouncerShowing && this.authInterruptActive == keyguardFaceListenModel.authInterruptActive && this.biometricSettingEnabledForUser == keyguardFaceListenModel.biometricSettingEnabledForUser && this.bouncerFullyShown == keyguardFaceListenModel.bouncerFullyShown && this.faceAndFpNotAuthenticated == keyguardFaceListenModel.faceAndFpNotAuthenticated && this.faceAuthAllowed == keyguardFaceListenModel.faceAuthAllowed && this.faceDisabled == keyguardFaceListenModel.faceDisabled && this.faceLockedOut == keyguardFaceListenModel.faceLockedOut && this.goingToSleep == keyguardFaceListenModel.goingToSleep && this.keyguardAwake == keyguardFaceListenModel.keyguardAwake && this.keyguardGoingAway == keyguardFaceListenModel.keyguardGoingAway && this.listeningForFaceAssistant == keyguardFaceListenModel.listeningForFaceAssistant && this.occludingAppRequestingFaceAuth == keyguardFaceListenModel.occludingAppRequestingFaceAuth && this.postureAllowsListening == keyguardFaceListenModel.postureAllowsListening && this.secureCameraLaunched == keyguardFaceListenModel.secureCameraLaunched && this.supportsDetect == keyguardFaceListenModel.supportsDetect && this.switchingUser == keyguardFaceListenModel.switchingUser && this.systemUser == keyguardFaceListenModel.systemUser && this.udfpsFingerDown == keyguardFaceListenModel.udfpsFingerDown && this.userNotTrustedOrDetectionIsNeeded == keyguardFaceListenModel.userNotTrustedOrDetectionIsNeeded;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.userId, Long.hashCode(this.timeMillis) * 31, 31);
        boolean z = this.listening;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m42m + i) * 31;
        boolean z2 = this.alternateBouncerShowing;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.authInterruptActive;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.biometricSettingEnabledForUser;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        boolean z5 = this.bouncerFullyShown;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int i10 = (i8 + i9) * 31;
        boolean z6 = this.faceAndFpNotAuthenticated;
        int i11 = z6;
        if (z6 != 0) {
            i11 = 1;
        }
        int i12 = (i10 + i11) * 31;
        boolean z7 = this.faceAuthAllowed;
        int i13 = z7;
        if (z7 != 0) {
            i13 = 1;
        }
        int i14 = (i12 + i13) * 31;
        boolean z8 = this.faceDisabled;
        int i15 = z8;
        if (z8 != 0) {
            i15 = 1;
        }
        int i16 = (i14 + i15) * 31;
        boolean z9 = this.faceLockedOut;
        int i17 = z9;
        if (z9 != 0) {
            i17 = 1;
        }
        int i18 = (i16 + i17) * 31;
        boolean z10 = this.goingToSleep;
        int i19 = z10;
        if (z10 != 0) {
            i19 = 1;
        }
        int i20 = (i18 + i19) * 31;
        boolean z11 = this.keyguardAwake;
        int i21 = z11;
        if (z11 != 0) {
            i21 = 1;
        }
        int i22 = (i20 + i21) * 31;
        boolean z12 = this.keyguardGoingAway;
        int i23 = z12;
        if (z12 != 0) {
            i23 = 1;
        }
        int i24 = (i22 + i23) * 31;
        boolean z13 = this.listeningForFaceAssistant;
        int i25 = z13;
        if (z13 != 0) {
            i25 = 1;
        }
        int i26 = (i24 + i25) * 31;
        boolean z14 = this.occludingAppRequestingFaceAuth;
        int i27 = z14;
        if (z14 != 0) {
            i27 = 1;
        }
        int i28 = (i26 + i27) * 31;
        boolean z15 = this.postureAllowsListening;
        int i29 = z15;
        if (z15 != 0) {
            i29 = 1;
        }
        int i30 = (i28 + i29) * 31;
        boolean z16 = this.secureCameraLaunched;
        int i31 = z16;
        if (z16 != 0) {
            i31 = 1;
        }
        int i32 = (i30 + i31) * 31;
        boolean z17 = this.supportsDetect;
        int i33 = z17;
        if (z17 != 0) {
            i33 = 1;
        }
        int i34 = (i32 + i33) * 31;
        boolean z18 = this.switchingUser;
        int i35 = z18;
        if (z18 != 0) {
            i35 = 1;
        }
        int i36 = (i34 + i35) * 31;
        boolean z19 = this.systemUser;
        int i37 = z19;
        if (z19 != 0) {
            i37 = 1;
        }
        int i38 = (i36 + i37) * 31;
        boolean z20 = this.udfpsFingerDown;
        int i39 = z20;
        if (z20 != 0) {
            i39 = 1;
        }
        int i40 = (i38 + i39) * 31;
        boolean z21 = this.userNotTrustedOrDetectionIsNeeded;
        return i40 + (z21 ? 1 : z21 ? 1 : 0);
    }

    public final String toString() {
        long j = this.timeMillis;
        int i = this.userId;
        boolean z = this.listening;
        boolean z2 = this.alternateBouncerShowing;
        boolean z3 = this.biometricSettingEnabledForUser;
        boolean z4 = this.bouncerFullyShown;
        boolean z5 = this.faceAndFpNotAuthenticated;
        boolean z6 = this.faceAuthAllowed;
        boolean z7 = this.faceDisabled;
        boolean z8 = this.faceLockedOut;
        boolean z9 = this.goingToSleep;
        boolean z10 = this.keyguardAwake;
        boolean z11 = this.keyguardGoingAway;
        boolean z12 = this.listeningForFaceAssistant;
        boolean z13 = this.occludingAppRequestingFaceAuth;
        boolean z14 = this.postureAllowsListening;
        boolean z15 = this.secureCameraLaunched;
        boolean z16 = this.supportsDetect;
        boolean z17 = this.switchingUser;
        boolean z18 = this.systemUser;
        boolean z19 = this.udfpsFingerDown;
        boolean z20 = this.userNotTrustedOrDetectionIsNeeded;
        StringBuilder sb = new StringBuilder("KeyguardFaceListenModel(timeMillis=");
        sb.append(j);
        sb.append(", userId=");
        sb.append(i);
        sb.append(", listening=");
        sb.append(z);
        sb.append(", alternateBouncerShowing=");
        sb.append(z2);
        sb.append(", authInterruptActive=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, this.authInterruptActive, ", biometricSettingEnabledForUser=", z3, ", bouncerFullyShown=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z4, ", faceAndFpNotAuthenticated=", z5, ", faceAuthAllowed=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z6, ", faceDisabled=", z7, ", faceLockedOut=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z8, ", goingToSleep=", z9, ", keyguardAwake=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z10, ", keyguardGoingAway=", z11, ", listeningForFaceAssistant=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z12, ", occludingAppRequestingFaceAuth=", z13, ", postureAllowsListening=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z14, ", secureCameraLaunched=", z15, ", supportsDetect=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z16, ", switchingUser=", z17, ", systemUser=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z18, ", udfpsFingerDown=", z19, ", userNotTrustedOrDetectionIsNeeded=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, z20, ")");
    }

    public /* synthetic */ KeyguardFaceListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & 128) != 0 ? false : z6, (i2 & 256) != 0 ? false : z7, (i2 & 512) != 0 ? false : z8, (i2 & 1024) != 0 ? false : z9, (i2 & 2048) != 0 ? false : z10, (i2 & 4096) != 0 ? false : z11, (i2 & 8192) != 0 ? false : z12, (i2 & 16384) != 0 ? false : z13, (i2 & 32768) != 0 ? false : z14, (i2 & 65536) != 0 ? false : z15, (i2 & 131072) != 0 ? false : z16, (i2 & 262144) != 0 ? false : z17, (i2 & 524288) != 0 ? false : z18, (i2 & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 ? false : z19, (i2 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? false : z20, (i2 & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0 ? false : z21);
    }

    public KeyguardFaceListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.alternateBouncerShowing = z2;
        this.authInterruptActive = z3;
        this.biometricSettingEnabledForUser = z4;
        this.bouncerFullyShown = z5;
        this.faceAndFpNotAuthenticated = z6;
        this.faceAuthAllowed = z7;
        this.faceDisabled = z8;
        this.faceLockedOut = z9;
        this.goingToSleep = z10;
        this.keyguardAwake = z11;
        this.keyguardGoingAway = z12;
        this.listeningForFaceAssistant = z13;
        this.occludingAppRequestingFaceAuth = z14;
        this.postureAllowsListening = z15;
        this.secureCameraLaunched = z16;
        this.supportsDetect = z17;
        this.switchingUser = z18;
        this.systemUser = z19;
        this.udfpsFingerDown = z20;
        this.userNotTrustedOrDetectionIsNeeded = z21;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.KeyguardFaceListenModel$asStringList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt__CollectionsKt.listOf(KeyguardListenModelKt.DATE_FORMAT.format(Long.valueOf(KeyguardFaceListenModel.this.timeMillis)), String.valueOf(KeyguardFaceListenModel.this.timeMillis), String.valueOf(KeyguardFaceListenModel.this.userId), String.valueOf(KeyguardFaceListenModel.this.listening), String.valueOf(KeyguardFaceListenModel.this.authInterruptActive), String.valueOf(KeyguardFaceListenModel.this.biometricSettingEnabledForUser), String.valueOf(KeyguardFaceListenModel.this.bouncerFullyShown), String.valueOf(KeyguardFaceListenModel.this.faceAndFpNotAuthenticated), String.valueOf(KeyguardFaceListenModel.this.faceAuthAllowed), String.valueOf(KeyguardFaceListenModel.this.faceDisabled), String.valueOf(KeyguardFaceListenModel.this.faceLockedOut), String.valueOf(KeyguardFaceListenModel.this.goingToSleep), String.valueOf(KeyguardFaceListenModel.this.keyguardAwake), String.valueOf(KeyguardFaceListenModel.this.keyguardGoingAway), String.valueOf(KeyguardFaceListenModel.this.listeningForFaceAssistant), String.valueOf(KeyguardFaceListenModel.this.occludingAppRequestingFaceAuth), String.valueOf(KeyguardFaceListenModel.this.postureAllowsListening), String.valueOf(KeyguardFaceListenModel.this.secureCameraLaunched), String.valueOf(KeyguardFaceListenModel.this.supportsDetect), String.valueOf(KeyguardFaceListenModel.this.switchingUser), String.valueOf(KeyguardFaceListenModel.this.systemUser), String.valueOf(KeyguardFaceListenModel.this.alternateBouncerShowing), String.valueOf(KeyguardFaceListenModel.this.udfpsFingerDown), String.valueOf(KeyguardFaceListenModel.this.userNotTrustedOrDetectionIsNeeded));
            }
        });
    }
}
