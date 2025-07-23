package androidx.compose.ui.platform;

import android.view.View;
import androidx.compose.runtime.Recomposer;
import androidx.lifecycle.LifecycleOwner;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Recomposer $recomposer;
    final /* synthetic */ WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2 $self;
    final /* synthetic */ LifecycleOwner $source;
    final /* synthetic */ Ref$ObjectRef<MotionDurationScaleImpl> $systemDurationScaleSettingConsumer;
    final /* synthetic */ View $this_createLifecycleAwareWindowRecomposer;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1(Ref$ObjectRef<MotionDurationScaleImpl> ref$ObjectRef, Recomposer recomposer, LifecycleOwner lifecycleOwner, WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2 windowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2, View view, Continuation continuation) {
        super(2, continuation);
        this.$systemDurationScaleSettingConsumer = ref$ObjectRef;
        this.$recomposer = recomposer;
        this.$source = lifecycleOwner;
        this.$self = windowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2;
        this.$this_createLifecycleAwareWindowRecomposer = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1 windowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1 = new WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1(this.$systemDurationScaleSettingConsumer, this.$recomposer, this.$source, this.$self, this.$this_createLifecycleAwareWindowRecomposer, continuation);
        windowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1.L$0 = obj;
        return windowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0081  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L1d
            if (r1 != r3) goto L15
            java.lang.Object r0 = r8.L$0
            kotlinx.coroutines.Job r0 = (kotlinx.coroutines.Job) r0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L12
            goto L68
        L12:
            r9 = move-exception
            goto L7f
        L15:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1d:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            kotlin.jvm.internal.Ref$ObjectRef<androidx.compose.ui.platform.MotionDurationScaleImpl> r1 = r8.$systemDurationScaleSettingConsumer     // Catch: java.lang.Throwable -> L56
            T r1 = r1.element     // Catch: java.lang.Throwable -> L56
            androidx.compose.ui.platform.MotionDurationScaleImpl r1 = (androidx.compose.ui.platform.MotionDurationScaleImpl) r1     // Catch: java.lang.Throwable -> L56
            if (r1 == 0) goto L59
            android.view.View r4 = r8.$this_createLifecycleAwareWindowRecomposer     // Catch: java.lang.Throwable -> L56
            android.content.Context r4 = r4.getContext()     // Catch: java.lang.Throwable -> L56
            android.content.Context r4 = r4.getApplicationContext()     // Catch: java.lang.Throwable -> L56
            kotlinx.coroutines.flow.StateFlow r4 = androidx.compose.ui.platform.WindowRecomposer_androidKt.access$getAnimationScaleFlowFor(r4)     // Catch: java.lang.Throwable -> L56
            java.lang.Object r5 = r4.getValue()     // Catch: java.lang.Throwable -> L56
            java.lang.Number r5 = (java.lang.Number) r5     // Catch: java.lang.Throwable -> L56
            float r5 = r5.floatValue()     // Catch: java.lang.Throwable -> L56
            androidx.compose.runtime.MutableFloatState r6 = r1.scaleFactor$delegate     // Catch: java.lang.Throwable -> L56
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r6 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r6     // Catch: java.lang.Throwable -> L56
            r6.setFloatValue(r5)     // Catch: java.lang.Throwable -> L56
            androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1$1$1 r5 = new androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1$1$1     // Catch: java.lang.Throwable -> L56
            r5.<init>(r4, r1, r2)     // Catch: java.lang.Throwable -> L56
            r1 = 3
            kotlinx.coroutines.StandaloneCoroutine r9 = kotlinx.coroutines.BuildersKt.launch$default(r9, r2, r2, r5, r1)     // Catch: java.lang.Throwable -> L56
            goto L5a
        L56:
            r9 = move-exception
            r0 = r2
            goto L7f
        L59:
            r9 = r2
        L5a:
            androidx.compose.runtime.Recomposer r1 = r8.$recomposer     // Catch: java.lang.Throwable -> L7b
            r8.L$0 = r9     // Catch: java.lang.Throwable -> L7b
            r8.label = r3     // Catch: java.lang.Throwable -> L7b
            java.lang.Object r1 = r1.runRecomposeAndApplyChanges(r8)     // Catch: java.lang.Throwable -> L7b
            if (r1 != r0) goto L67
            return r0
        L67:
            r0 = r9
        L68:
            if (r0 == 0) goto L6d
            r0.cancel(r2)
        L6d:
            androidx.lifecycle.LifecycleOwner r9 = r8.$source
            androidx.lifecycle.Lifecycle r9 = r9.getLifecycle()
            androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2 r8 = r8.$self
            r9.removeObserver(r8)
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L7b:
            r0 = move-exception
            r7 = r0
            r0 = r9
            r9 = r7
        L7f:
            if (r0 == 0) goto L84
            r0.cancel(r2)
        L84:
            androidx.lifecycle.LifecycleOwner r0 = r8.$source
            androidx.lifecycle.Lifecycle r0 = r0.getLifecycle()
            androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2 r8 = r8.$self
            r0.removeObserver(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
