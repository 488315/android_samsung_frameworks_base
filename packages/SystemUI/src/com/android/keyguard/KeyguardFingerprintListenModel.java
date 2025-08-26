package com.android.keyguard;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.common.buffer.RingBuffer;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes.dex */
public final class KeyguardFingerprintListenModel extends KeyguardListenModel {
    public static final List TABLE_HEADERS;
    public boolean allowOnCurrentOccludingActivity;
    public boolean alternateBouncerShowing;
    public final Lazy asStringList$delegate;
    public boolean biometricEnabledForUser;
    public boolean biometricPromptShowing;
    public boolean bouncerIsOrWillShow;
    public boolean canSkipBouncer;
    public boolean communalShowing;
    public boolean credentialAttempted;
    public boolean deviceInteractive;
    public boolean dreaming;
    public boolean fingerprintDisabled;
    public boolean fingerprintLockedOut;
    public boolean goingToSleep;
    public boolean keyguardGoingAway;
    public boolean keyguardIsVisible;
    public boolean keyguardOccluded;
    public boolean listening;
    public boolean occludingAppRequestingFp;
    public boolean shouldListenForFingerprintAssistant;
    public boolean strongerAuthRequired;
    public boolean switchingUser;
    public boolean systemUser;
    public long timeMillis;
    public boolean udfps;
    public boolean userDoesNotHaveTrust;
    public int userId;

    public final class Buffer {
        public final RingBuffer buffer = new RingBuffer(20, new KeyguardFingerprintListenModel$Buffer$$ExternalSyntheticLambda1());

        public final List toList() {
            return SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.buffer), new KeyguardFingerprintListenModel$Buffer$$ExternalSyntheticLambda0()));
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = Arrays.asList(PhoneRestrictionPolicy.TIMESTAMP, "time_millis", "userId", "listening", "allowOnCurrentOccludingActivity", "alternateBouncerShowing", "biometricAllowedForUser", "biometricPromptShowing", "bouncerIsOrWillShow", "canSkipBouncer", "credentialAttempted", "deviceInteractive", "dreaming", "fingerprintDisabled", "fingerprintLockedOut", "goingToSleep", "keyguardGoingAway", "keyguardIsVisible", "keyguardOccluded", "occludingAppRequestingFp", "shouldListenSidFingerprintState", "shouldListenForFingerprintAssistant", "strongAuthRequired", "switchingUser", "systemUser", "underDisplayFingerprint", "userDoesNotHaveTrust", "communalShowing");
    }

    public KeyguardFingerprintListenModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 67108863, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardFingerprintListenModel)) {
            return false;
        }
        KeyguardFingerprintListenModel keyguardFingerprintListenModel = (KeyguardFingerprintListenModel) obj;
        return this.timeMillis == keyguardFingerprintListenModel.timeMillis && this.userId == keyguardFingerprintListenModel.userId && this.listening == keyguardFingerprintListenModel.listening && this.allowOnCurrentOccludingActivity == keyguardFingerprintListenModel.allowOnCurrentOccludingActivity && this.alternateBouncerShowing == keyguardFingerprintListenModel.alternateBouncerShowing && this.biometricEnabledForUser == keyguardFingerprintListenModel.biometricEnabledForUser && this.biometricPromptShowing == keyguardFingerprintListenModel.biometricPromptShowing && this.bouncerIsOrWillShow == keyguardFingerprintListenModel.bouncerIsOrWillShow && this.canSkipBouncer == keyguardFingerprintListenModel.canSkipBouncer && this.credentialAttempted == keyguardFingerprintListenModel.credentialAttempted && this.deviceInteractive == keyguardFingerprintListenModel.deviceInteractive && this.dreaming == keyguardFingerprintListenModel.dreaming && this.fingerprintDisabled == keyguardFingerprintListenModel.fingerprintDisabled && this.fingerprintLockedOut == keyguardFingerprintListenModel.fingerprintLockedOut && this.goingToSleep == keyguardFingerprintListenModel.goingToSleep && this.keyguardGoingAway == keyguardFingerprintListenModel.keyguardGoingAway && this.keyguardIsVisible == keyguardFingerprintListenModel.keyguardIsVisible && this.keyguardOccluded == keyguardFingerprintListenModel.keyguardOccluded && this.occludingAppRequestingFp == keyguardFingerprintListenModel.occludingAppRequestingFp && this.shouldListenForFingerprintAssistant == keyguardFingerprintListenModel.shouldListenForFingerprintAssistant && this.strongerAuthRequired == keyguardFingerprintListenModel.strongerAuthRequired && this.switchingUser == keyguardFingerprintListenModel.switchingUser && this.systemUser == keyguardFingerprintListenModel.systemUser && this.udfps == keyguardFingerprintListenModel.udfps && this.userDoesNotHaveTrust == keyguardFingerprintListenModel.userDoesNotHaveTrust && this.communalShowing == keyguardFingerprintListenModel.communalShowing;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.communalShowing) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.userId, Long.hashCode(this.timeMillis) * 31, 31), 31, this.listening), 31, this.allowOnCurrentOccludingActivity), 31, this.alternateBouncerShowing), 31, this.biometricEnabledForUser), 31, this.biometricPromptShowing), 31, this.bouncerIsOrWillShow), 31, this.canSkipBouncer), 31, this.credentialAttempted), 31, this.deviceInteractive), 31, this.dreaming), 31, this.fingerprintDisabled), 31, this.fingerprintLockedOut), 31, this.goingToSleep), 31, this.keyguardGoingAway), 31, this.keyguardIsVisible), 31, this.keyguardOccluded), 31, this.occludingAppRequestingFp), 31, this.shouldListenForFingerprintAssistant), 31, this.strongerAuthRequired), 31, this.switchingUser), 31, this.systemUser), 31, this.udfps), 31, this.userDoesNotHaveTrust);
    }

    public final String toString() {
        return "KeyguardFingerprintListenModel(timeMillis=" + this.timeMillis + ", userId=" + this.userId + ", listening=" + this.listening + ", allowOnCurrentOccludingActivity=" + this.allowOnCurrentOccludingActivity + ", alternateBouncerShowing=" + this.alternateBouncerShowing + ", biometricEnabledForUser=" + this.biometricEnabledForUser + ", biometricPromptShowing=" + this.biometricPromptShowing + ", bouncerIsOrWillShow=" + this.bouncerIsOrWillShow + ", canSkipBouncer=" + this.canSkipBouncer + ", credentialAttempted=" + this.credentialAttempted + ", deviceInteractive=" + this.deviceInteractive + ", dreaming=" + this.dreaming + ", fingerprintDisabled=" + this.fingerprintDisabled + ", fingerprintLockedOut=" + this.fingerprintLockedOut + ", goingToSleep=" + this.goingToSleep + ", keyguardGoingAway=" + this.keyguardGoingAway + ", keyguardIsVisible=" + this.keyguardIsVisible + ", keyguardOccluded=" + this.keyguardOccluded + ", occludingAppRequestingFp=" + this.occludingAppRequestingFp + ", shouldListenForFingerprintAssistant=" + this.shouldListenForFingerprintAssistant + ", strongerAuthRequired=" + this.strongerAuthRequired + ", switchingUser=" + this.switchingUser + ", systemUser=" + this.systemUser + ", udfps=" + this.udfps + ", userDoesNotHaveTrust=" + this.userDoesNotHaveTrust + ", communalShowing=" + this.communalShowing + ")";
    }

    public /* synthetic */ KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, boolean z22, boolean z23, boolean z24, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & 128) != 0 ? false : z6, (i2 & 256) != 0 ? false : z7, (i2 & 512) != 0 ? false : z8, (i2 & 1024) != 0 ? false : z9, (i2 & 2048) != 0 ? false : z10, (i2 & 4096) != 0 ? false : z11, (i2 & 8192) != 0 ? false : z12, (i2 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? false : z13, (32768 & i2) != 0 ? false : z14, (i2 & 65536) != 0 ? false : z15, (i2 & 131072) != 0 ? false : z16, (i2 & 262144) != 0 ? false : z17, (i2 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? false : z18, (i2 & 1048576) != 0 ? false : z19, (i2 & 2097152) != 0 ? false : z20, (i2 & 4194304) != 0 ? false : z21, (i2 & 8388608) != 0 ? false : z22, (i2 & 16777216) != 0 ? false : z23, (i2 & 33554432) != 0 ? false : z24);
    }

    public KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, boolean z22, boolean z23, boolean z24) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.allowOnCurrentOccludingActivity = z2;
        this.alternateBouncerShowing = z3;
        this.biometricEnabledForUser = z4;
        this.biometricPromptShowing = z5;
        this.bouncerIsOrWillShow = z6;
        this.canSkipBouncer = z7;
        this.credentialAttempted = z8;
        this.deviceInteractive = z9;
        this.dreaming = z10;
        this.fingerprintDisabled = z11;
        this.fingerprintLockedOut = z12;
        this.goingToSleep = z13;
        this.keyguardGoingAway = z14;
        this.keyguardIsVisible = z15;
        this.keyguardOccluded = z16;
        this.occludingAppRequestingFp = z17;
        this.shouldListenForFingerprintAssistant = z18;
        this.strongerAuthRequired = z19;
        this.switchingUser = z20;
        this.systemUser = z21;
        this.udfps = z22;
        this.userDoesNotHaveTrust = z23;
        this.communalShowing = z24;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list = KeyguardFingerprintListenModel.TABLE_HEADERS;
                SimpleDateFormat simpleDateFormat = KeyguardListenModelKt.DATE_FORMAT;
                KeyguardFingerprintListenModel keyguardFingerprintListenModel = this.f$0;
                return Arrays.asList(simpleDateFormat.format(Long.valueOf(keyguardFingerprintListenModel.timeMillis)), String.valueOf(keyguardFingerprintListenModel.timeMillis), String.valueOf(keyguardFingerprintListenModel.userId), String.valueOf(keyguardFingerprintListenModel.listening), String.valueOf(keyguardFingerprintListenModel.allowOnCurrentOccludingActivity), String.valueOf(keyguardFingerprintListenModel.alternateBouncerShowing), String.valueOf(keyguardFingerprintListenModel.biometricEnabledForUser), String.valueOf(keyguardFingerprintListenModel.biometricPromptShowing), String.valueOf(keyguardFingerprintListenModel.bouncerIsOrWillShow), String.valueOf(keyguardFingerprintListenModel.canSkipBouncer), String.valueOf(keyguardFingerprintListenModel.credentialAttempted), String.valueOf(keyguardFingerprintListenModel.deviceInteractive), String.valueOf(keyguardFingerprintListenModel.dreaming), String.valueOf(keyguardFingerprintListenModel.fingerprintDisabled), String.valueOf(keyguardFingerprintListenModel.fingerprintLockedOut), String.valueOf(keyguardFingerprintListenModel.goingToSleep), String.valueOf(keyguardFingerprintListenModel.keyguardGoingAway), String.valueOf(keyguardFingerprintListenModel.keyguardIsVisible), String.valueOf(keyguardFingerprintListenModel.keyguardOccluded), String.valueOf(keyguardFingerprintListenModel.occludingAppRequestingFp), String.valueOf(keyguardFingerprintListenModel.shouldListenForFingerprintAssistant), String.valueOf(keyguardFingerprintListenModel.strongerAuthRequired), String.valueOf(keyguardFingerprintListenModel.switchingUser), String.valueOf(keyguardFingerprintListenModel.systemUser), String.valueOf(keyguardFingerprintListenModel.udfps), String.valueOf(keyguardFingerprintListenModel.userDoesNotHaveTrust), String.valueOf(keyguardFingerprintListenModel.communalShowing));
            }
        });
    }
}
