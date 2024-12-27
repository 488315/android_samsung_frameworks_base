package com.android.systemui.common.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PackageInstallerMonitor$_installSessions$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public PackageInstallerMonitor$_installSessions$1$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PackageInstallerMonitor$_installSessions$1$2 packageInstallerMonitor$_installSessions$1$2 = new PackageInstallerMonitor$_installSessions$1$2(continuation);
        packageInstallerMonitor$_installSessions$1$2.Z$0 = ((Boolean) obj).booleanValue();
        return packageInstallerMonitor$_installSessions$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((PackageInstallerMonitor$_installSessions$1$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(!this.Z$0);
    }
}
