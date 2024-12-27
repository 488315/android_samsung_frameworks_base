package com.android.systemui.biometrics.domain.interactor;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ComponentName componentName;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AuthenticationReason authenticationReason = (AuthenticationReason) this.L$0;
        FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) this.L$1;
        fingerprintSensorType.getClass();
        if (fingerprintSensorType == FingerprintSensorType.POWER_BUTTON) {
            ActivityTaskManager activityTaskManager = this.this$0.activityTaskManager;
            if (!Intrinsics.areEqual(authenticationReason, AuthenticationReason.DeviceEntryAuthentication.INSTANCE)) {
                if (!Intrinsics.areEqual(authenticationReason, new AuthenticationReason.SettingsAuthentication(AuthenticationReason.SettingsOperations.OTHER))) {
                    return authenticationReason;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(activityTaskManager.getTasks(1));
                String className = (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName();
                if (className == null) {
                    className = "";
                }
                if (!className.equals("com.android.settings.biometrics.fingerprint.FingerprintSettings")) {
                    return authenticationReason;
                }
            }
        }
        return AuthenticationReason.NotRunning.INSTANCE;
    }
}
