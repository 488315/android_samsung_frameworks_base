package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RecentTaskListProvider recentTaskListProvider = this.this$0.recentTaskListProvider;
            this.label = 1;
            obj = ((ShellRecentTaskListProvider) recentTaskListProvider).loadRecentTasks(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$0;
            ResultKt.throwOnFailure(obj);
            ((MediaProjectionAppSelectorActivity) this.this$0.view).bind(list);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        final MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.this$0;
        mediaProjectionAppSelectorController.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (List) obj) {
            if (mediaProjectionAppSelectorController.devicePolicyResolver.isScreenCaptureAllowed(UserHandle.of(((RecentTask) obj2).userId), mediaProjectionAppSelectorController.hostUserHandle)) {
                arrayList.add(obj2);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList.get(i2);
            i2++;
            if (!Intrinsics.areEqual(((RecentTask) obj3).topActivityComponent, mediaProjectionAppSelectorController.appSelectorComponentName)) {
                arrayList2.add(obj3);
            }
        }
        List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$sortedTasks$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj4, Object obj5) {
                ComponentName componentName = ((RecentTask) obj4).topActivityComponent;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(componentName != null ? componentName.getPackageName() : null, mediaProjectionAppSelectorController.callerPackageName));
                ComponentName componentName2 = ((RecentTask) obj5).topActivityComponent;
                return ComparisonsKt__ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(Intrinsics.areEqual(componentName2 != null ? componentName2.getPackageName() : null, mediaProjectionAppSelectorController.callerPackageName)));
            }
        });
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController2 = this.this$0;
        this.L$0 = listSortedWith;
        this.label = 2;
        mediaProjectionAppSelectorController2.getClass();
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2(listSortedWith, mediaProjectionAppSelectorController2, null), this);
        if (objCoroutineScope != CoroutineSingletons.COROUTINE_SUSPENDED) {
            objCoroutineScope = Unit.INSTANCE;
        }
        if (objCoroutineScope != coroutineSingletons) {
            list = listSortedWith;
            ((MediaProjectionAppSelectorActivity) this.this$0.view).bind(list);
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }
}
