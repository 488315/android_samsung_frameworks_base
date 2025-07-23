package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BiometricStatusInteractorImpl$sfpsAuthenticationReason$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricStatusInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricStatusInteractorImpl$sfpsAuthenticationReason$1(BiometricStatusInteractorImpl biometricStatusInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = biometricStatusInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricStatusInteractorImpl$sfpsAuthenticationReason$1 biometricStatusInteractorImpl$sfpsAuthenticationReason$1 = new BiometricStatusInteractorImpl$sfpsAuthenticationReason$1(this.this$0, (Continuation) obj3);
        biometricStatusInteractorImpl$sfpsAuthenticationReason$1.L$0 = (AuthenticationReason) obj;
        biometricStatusInteractorImpl$sfpsAuthenticationReason$1.L$1 = (FingerprintSensorType) obj2;
        return biometricStatusInteractorImpl$sfpsAuthenticationReason$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0051, code lost:
    
        if (r2.equals("com.android.settings.biometrics.fingerprint.FingerprintSettings") != false) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r3) {
        /*
            r2 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r2.label
            if (r0 != 0) goto L58
            kotlin.ResultKt.throwOnFailure(r3)
            java.lang.Object r3 = r2.L$0
            com.android.systemui.biometrics.shared.model.AuthenticationReason r3 = (com.android.systemui.biometrics.shared.model.AuthenticationReason) r3
            java.lang.Object r0 = r2.L$1
            com.android.systemui.biometrics.shared.model.FingerprintSensorType r0 = (com.android.systemui.biometrics.shared.model.FingerprintSensorType) r0
            r0.getClass()
            com.android.systemui.biometrics.shared.model.FingerprintSensorType r1 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.POWER_BUTTON
            if (r0 != r1) goto L55
            com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractorImpl r2 = r2.this$0
            android.app.ActivityTaskManager r2 = r2.activityTaskManager
            com.android.systemui.biometrics.shared.model.AuthenticationReason$DeviceEntryAuthentication r0 = com.android.systemui.biometrics.shared.model.AuthenticationReason.DeviceEntryAuthentication.INSTANCE
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
            if (r0 == 0) goto L25
            goto L55
        L25:
            com.android.systemui.biometrics.shared.model.AuthenticationReason$SettingsAuthentication r0 = new com.android.systemui.biometrics.shared.model.AuthenticationReason$SettingsAuthentication
            com.android.systemui.biometrics.shared.model.AuthenticationReason$SettingsOperations r1 = com.android.systemui.biometrics.shared.model.AuthenticationReason.SettingsOperations.OTHER
            r0.<init>(r1)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
            if (r0 == 0) goto L54
            r0 = 1
            java.util.List r2 = r2.getTasks(r0)
            java.lang.Object r2 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r2)
            android.app.ActivityManager$RunningTaskInfo r2 = (android.app.ActivityManager.RunningTaskInfo) r2
            if (r2 == 0) goto L49
            android.content.ComponentName r2 = r2.topActivity
            if (r2 == 0) goto L49
            java.lang.String r2 = r2.getClassName()
            if (r2 != 0) goto L4b
        L49:
            java.lang.String r2 = ""
        L4b:
            java.lang.String r0 = "com.android.settings.biometrics.fingerprint.FingerprintSettings"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L54
            goto L55
        L54:
            return r3
        L55:
            com.android.systemui.biometrics.shared.model.AuthenticationReason$NotRunning r2 = com.android.systemui.biometrics.shared.model.AuthenticationReason.NotRunning.INSTANCE
            return r2
        L58:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractorImpl$sfpsAuthenticationReason$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
