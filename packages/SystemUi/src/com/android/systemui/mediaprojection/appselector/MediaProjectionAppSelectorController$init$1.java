package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$init$1", m277f = "MediaProjectionAppSelectorController.kt", m278l = {44}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class MediaProjectionAppSelectorController$init$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MediaProjectionAppSelectorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionAppSelectorController$init$1(MediaProjectionAppSelectorController mediaProjectionAppSelectorController, Continuation<? super MediaProjectionAppSelectorController$init$1> continuation) {
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
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RecentTaskListProvider recentTaskListProvider = this.this$0.recentTaskListProvider;
            this.label = 1;
            obj = ((ShellRecentTaskListProvider) recentTaskListProvider).loadRecentTasks(this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        final MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.this$0;
        mediaProjectionAppSelectorController.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (List) obj) {
            if (mediaProjectionAppSelectorController.devicePolicyResolver.isScreenCaptureAllowed(UserHandle.of(((RecentTask) obj2).userId), mediaProjectionAppSelectorController.hostUserHandle)) {
                arrayList.add(obj2);
            }
        }
        mediaProjectionAppSelectorController.getClass();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (!Intrinsics.areEqual(((RecentTask) next).topActivityComponent, mediaProjectionAppSelectorController.appSelectorComponentName)) {
                arrayList2.add(next);
            }
        }
        mediaProjectionAppSelectorController.getClass();
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$sortedTasks$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj3, Object obj4) {
                ComponentName componentName = ((RecentTask) obj3).topActivityComponent;
                Boolean valueOf = Boolean.valueOf(Intrinsics.areEqual(componentName != null ? componentName.getPackageName() : null, MediaProjectionAppSelectorController.this.callerPackageName));
                ComponentName componentName2 = ((RecentTask) obj4).topActivityComponent;
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Boolean.valueOf(Intrinsics.areEqual(componentName2 != null ? componentName2.getPackageName() : null, MediaProjectionAppSelectorController.this.callerPackageName)));
            }
        });
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController = ((MediaProjectionAppSelectorActivity) this.this$0.view).recentsViewController;
        if (mediaProjectionRecentsViewController == null) {
            mediaProjectionRecentsViewController = null;
        }
        mediaProjectionRecentsViewController.bind(sortedWith);
        return Unit.INSTANCE;
    }
}
