package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class BasicPackageManagerAppIconLoader$loadIcon$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComponentName $component;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ BasicPackageManagerAppIconLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicPackageManagerAppIconLoader$loadIcon$2(BasicPackageManagerAppIconLoader basicPackageManagerAppIconLoader, ComponentName componentName, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = basicPackageManagerAppIconLoader;
        this.$component = componentName;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BasicPackageManagerAppIconLoader$loadIcon$2(this.this$0, this.$component, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicPackageManagerAppIconLoader$loadIcon$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PackageManagerWrapper packageManagerWrapper = this.this$0.packageManagerWrapper;
        ComponentName componentName = this.$component;
        int i = this.$userId;
        packageManagerWrapper.getClass();
        ActivityInfo activityInfo = PackageManagerWrapper.getActivityInfo(componentName, i);
        if (activityInfo != null) {
            return activityInfo.loadIcon(this.this$0.packageManager);
        }
        return null;
    }
}
