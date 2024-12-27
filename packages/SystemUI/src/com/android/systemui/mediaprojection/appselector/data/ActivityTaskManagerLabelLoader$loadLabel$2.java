package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ActivityTaskManagerLabelLoader$loadLabel$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComponentName $componentName;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ ActivityTaskManagerLabelLoader this$0;

    public ActivityTaskManagerLabelLoader$loadLabel$2(ActivityTaskManagerLabelLoader activityTaskManagerLabelLoader, ComponentName componentName, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = activityTaskManagerLabelLoader;
        this.$componentName = componentName;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivityTaskManagerLabelLoader$loadLabel$2(this.this$0, this.$componentName, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityTaskManagerLabelLoader$loadLabel$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            return this.this$0.packageManager.getUserBadgedLabel(this.this$0.packageManager.getApplicationLabel(this.this$0.packageManager.getApplicationInfoAsUser(this.$componentName.getPackageName(), PackageManager.ApplicationInfoFlags.of(0L), this.$userId)), new UserHandle(this.$userId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(this.this$0.TAG, "Unable to get application info", e);
            return null;
        }
    }
}
