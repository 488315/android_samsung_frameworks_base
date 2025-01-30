package com.android.systemui.screenshot;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$managed$1", m277f = "ScreenshotPolicyImpl.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class ScreenshotPolicyImpl$isManagedProfile$managed$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$isManagedProfile$managed$1(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super ScreenshotPolicyImpl$isManagedProfile$managed$1> continuation) {
        super(2, continuation);
        this.this$0 = screenshotPolicyImpl;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotPolicyImpl$isManagedProfile$managed$1(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotPolicyImpl$isManagedProfile$managed$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.userMgr.isManagedProfile(this.$userId));
    }
}
