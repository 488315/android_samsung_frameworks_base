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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ActivityTaskManagerLabelLoader implements RecentTaskLabelLoader {
    public final CoroutineDispatcher coroutineDispatcher;
    public final PackageManager packageManager;

    /* renamed from: com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerLabelLoader$loadLabel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ComponentName $componentName;
        final /* synthetic */ int $userId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ComponentName componentName, int i, Continuation continuation) {
            super(2, continuation);
            this.$componentName = componentName;
            this.$userId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActivityTaskManagerLabelLoader.this.new AnonymousClass2(this.$componentName, this.$userId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                return ActivityTaskManagerLabelLoader.this.packageManager.getUserBadgedLabel(ActivityTaskManagerLabelLoader.this.packageManager.getApplicationLabel(ActivityTaskManagerLabelLoader.this.packageManager.getApplicationInfoAsUser(this.$componentName.getPackageName(), PackageManager.ApplicationInfoFlags.of(0L), this.$userId)), new UserHandle(this.$userId));
            } catch (PackageManager.NameNotFoundException e) {
                ActivityTaskManagerLabelLoader.this.getClass();
                Log.e("RecentTaskLabelLoader", "Unable to get application info", e);
                return null;
            }
        }
    }

    public ActivityTaskManagerLabelLoader(CoroutineDispatcher coroutineDispatcher, PackageManager packageManager) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.packageManager = packageManager;
    }

    public final Object loadLabel(int i, ComponentName componentName, Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new AnonymousClass2(componentName, i, null), continuation);
    }
}
