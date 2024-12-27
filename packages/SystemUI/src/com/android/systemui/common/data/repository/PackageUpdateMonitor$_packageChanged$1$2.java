package com.android.systemui.common.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class PackageUpdateMonitor$_packageChanged$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ PackageUpdateMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageUpdateMonitor$_packageChanged$1$2(PackageUpdateMonitor packageUpdateMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = packageUpdateMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PackageUpdateMonitor$_packageChanged$1$2 packageUpdateMonitor$_packageChanged$1$2 = new PackageUpdateMonitor$_packageChanged$1$2(this.this$0, continuation);
        packageUpdateMonitor$_packageChanged$1$2.Z$0 = ((Boolean) obj).booleanValue();
        return packageUpdateMonitor$_packageChanged$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((PackageUpdateMonitor$_packageChanged$1$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        if (z) {
            PackageUpdateMonitor packageUpdateMonitor = this.this$0;
            packageUpdateMonitor.register(packageUpdateMonitor.context, packageUpdateMonitor.user, packageUpdateMonitor.bgHandler);
        } else {
            PackageUpdateMonitor packageUpdateMonitor2 = this.this$0;
            if (packageUpdateMonitor2.isActive) {
                packageUpdateMonitor2.unregister();
            }
        }
        this.this$0.isActive = z;
        return Unit.INSTANCE;
    }
}
