package com.android.systemui.mediaprojection.appselector.view;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import com.android.systemui.mediaprojection.appselector.data.BadgedAppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class RecentTaskViewHolder$bind$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComponentName $component;
    final /* synthetic */ RecentTask $task;
    int label;
    final /* synthetic */ RecentTaskViewHolder this$0;

    public RecentTaskViewHolder$bind$1$1$1(RecentTaskViewHolder recentTaskViewHolder, RecentTask recentTask, ComponentName componentName, Continuation continuation) {
        super(2, continuation);
        this.this$0 = recentTaskViewHolder;
        this.$task = recentTask;
        this.$component = componentName;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RecentTaskViewHolder$bind$1$1$1(this.this$0, this.$task, this.$component, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RecentTaskViewHolder$bind$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BadgedAppIconLoader badgedAppIconLoader = this.this$0.iconLoader;
            RecentTask recentTask = this.$task;
            int i2 = recentTask.userId;
            RecentTask.UserType userType = recentTask.userType;
            ComponentName componentName = this.$component;
            this.label = 1;
            obj = badgedAppIconLoader.loadIcon(i2, userType, componentName, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.iconView.setImageDrawable((Drawable) obj);
        return Unit.INSTANCE;
    }
}
