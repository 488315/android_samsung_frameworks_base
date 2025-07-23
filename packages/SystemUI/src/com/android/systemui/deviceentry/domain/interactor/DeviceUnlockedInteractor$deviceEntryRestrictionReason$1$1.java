package com.android.systemui.deviceentry.domain.interactor;

import android.os.SystemProperties;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceUnlockedInteractor$deviceEntryRestrictionReason$1$1 extends SuspendLambda implements Function5 {
    final /* synthetic */ DeviceEntryFaceAuthInteractor $faceAuthInteractor;
    final /* synthetic */ boolean $trustEnabled;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceUnlockedInteractor$deviceEntryRestrictionReason$1$1(DeviceUnlockedInteractor deviceUnlockedInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, boolean z, Continuation continuation) {
        super(5, continuation);
        this.this$0 = deviceUnlockedInteractor;
        this.$faceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.$trustEnabled = z;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        DeviceUnlockedInteractor$deviceEntryRestrictionReason$1$1 deviceUnlockedInteractor$deviceEntryRestrictionReason$1$1 = new DeviceUnlockedInteractor$deviceEntryRestrictionReason$1$1(this.this$0, this.$faceAuthInteractor, this.$trustEnabled, (Continuation) obj5);
        deviceUnlockedInteractor$deviceEntryRestrictionReason$1$1.L$0 = (AuthenticationFlags) obj;
        deviceUnlockedInteractor$deviceEntryRestrictionReason$1$1.Z$0 = booleanValue;
        deviceUnlockedInteractor$deviceEntryRestrictionReason$1$1.Z$1 = booleanValue2;
        deviceUnlockedInteractor$deviceEntryRestrictionReason$1$1.Z$2 = booleanValue3;
        return deviceUnlockedInteractor$deviceEntryRestrictionReason$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AuthenticationFlags authenticationFlags = (AuthenticationFlags) this.L$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        if (authenticationFlags.isPrimaryAuthRequiredAfterReboot) {
            this.this$0.systemPropertiesHelper.getClass();
            if (Intrinsics.areEqual(SystemProperties.get("sys.boot.reason.last"), "reboot,mainline_update")) {
                return DeviceEntryRestrictionReason.DeviceNotUnlockedSinceMainlineUpdate;
            }
        }
        if (authenticationFlags.isPrimaryAuthRequiredAfterReboot) {
            return DeviceEntryRestrictionReason.DeviceNotUnlockedSinceReboot;
        }
        if (authenticationFlags.isPrimaryAuthRequiredAfterDpmLockdown) {
            return DeviceEntryRestrictionReason.PolicyLockdown;
        }
        if (authenticationFlags.isInUserLockdown) {
            return DeviceEntryRestrictionReason.UserLockdown;
        }
        if (authenticationFlags.isPrimaryAuthRequiredForUnattendedUpdate) {
            return DeviceEntryRestrictionReason.UnattendedUpdate;
        }
        if (authenticationFlags.isPrimaryAuthRequiredAfterTimeout) {
            return DeviceEntryRestrictionReason.SecurityTimeout;
        }
        if (z2) {
            return DeviceEntryRestrictionReason.StrongBiometricsLockedOut;
        }
        if (z && this.$faceAuthInteractor.isFaceAuthStrong()) {
            return DeviceEntryRestrictionReason.StrongBiometricsLockedOut;
        }
        if (z) {
            return DeviceEntryRestrictionReason.NonStrongFaceLockedOut;
        }
        if (authenticationFlags.isSomeAuthRequiredAfterAdaptiveAuthRequest) {
            return DeviceEntryRestrictionReason.AdaptiveAuthRequest;
        }
        if (this.$trustEnabled && !z3 && (authenticationFlags.someAuthRequiredAfterTrustAgentExpired || authenticationFlags.someAuthRequiredAfterUserRequest)) {
            return DeviceEntryRestrictionReason.TrustAgentDisabled;
        }
        if (authenticationFlags.strongerAuthRequiredAfterNonStrongBiometricsTimeout) {
            return DeviceEntryRestrictionReason.NonStrongBiometricsSecurityTimeout;
        }
        return null;
    }
}
