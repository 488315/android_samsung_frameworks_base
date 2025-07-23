package com.android.systemui.mediaprojection.appselector;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaProjectionAppSelectorController$init$1 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ MediaProjectionAppSelectorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionAppSelectorController$init$1(MediaProjectionAppSelectorController mediaProjectionAppSelectorController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionAppSelectorController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaProjectionAppSelectorController$init$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionAppSelectorController$init$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0030, code lost:
    
        if (r10 == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L21
            if (r1 == r2) goto L1d
            if (r1 != r3) goto L15
            java.lang.Object r0 = r9.L$0
            java.util.List r0 = (java.util.List) r0
            kotlin.ResultKt.throwOnFailure(r10)
            goto Lb1
        L15:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1d:
            kotlin.ResultKt.throwOnFailure(r10)
            goto L34
        L21:
            kotlin.ResultKt.throwOnFailure(r10)
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController r10 = r9.this$0
            com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider r10 = r10.recentTaskListProvider
            r9.label = r2
            com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider r10 = (com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider) r10
            java.lang.Object r10 = r10.loadRecentTasks(r9)
            if (r10 != r0) goto L34
            goto Laf
        L34:
            java.util.List r10 = (java.util.List) r10
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController r1 = r9.this$0
            r1.getClass()
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r10 = r10.iterator()
        L46:
            boolean r4 = r10.hasNext()
            if (r4 == 0) goto L67
            java.lang.Object r4 = r10.next()
            r5 = r4
            com.android.systemui.mediaprojection.appselector.data.RecentTask r5 = (com.android.systemui.mediaprojection.appselector.data.RecentTask) r5
            int r5 = r5.userId
            android.os.UserHandle r5 = android.os.UserHandle.of(r5)
            android.os.UserHandle r6 = r1.hostUserHandle
            com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver r7 = r1.devicePolicyResolver
            boolean r5 = r7.isScreenCaptureAllowed(r5, r6)
            if (r5 == 0) goto L46
            r2.add(r4)
            goto L46
        L67:
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            int r4 = r2.size()
            r5 = 0
        L71:
            if (r5 >= r4) goto L8a
            java.lang.Object r6 = r2.get(r5)
            int r5 = r5 + 1
            r7 = r6
            com.android.systemui.mediaprojection.appselector.data.RecentTask r7 = (com.android.systemui.mediaprojection.appselector.data.RecentTask) r7
            android.content.ComponentName r7 = r7.topActivityComponent
            android.content.ComponentName r8 = r1.appSelectorComponentName
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
            if (r7 != 0) goto L71
            r10.add(r6)
            goto L71
        L8a:
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$sortedTasks$$inlined$sortedBy$1 r2 = new com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$sortedTasks$$inlined$sortedBy$1
            r2.<init>()
            java.util.List r10 = kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(r10, r2)
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController r1 = r9.this$0
            r9.L$0 = r10
            r9.label = r3
            r1.getClass()
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2 r2 = new com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2
            r3 = 0
            r2.<init>(r10, r1, r3)
            java.lang.Object r1 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r2, r9)
            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r1 != r2) goto Lab
            goto Lad
        Lab:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        Lad:
            if (r1 != r0) goto Lb0
        Laf:
            return r0
        Lb0:
            r0 = r10
        Lb1:
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController r9 = r9.this$0
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorView r9 = r9.view
            com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity r9 = (com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity) r9
            r9.bind(r0)
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$init$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
