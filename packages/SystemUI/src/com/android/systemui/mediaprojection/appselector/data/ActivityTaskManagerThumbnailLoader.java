package com.android.systemui.mediaprojection.appselector.data;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import android.window.TaskSnapshot;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
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
public final class ActivityTaskManagerThumbnailLoader implements RecentTaskThumbnailLoader {
    public final ActivityManagerWrapper activityManager;
    public final CoroutineDispatcher coroutineDispatcher;

    /* renamed from: com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader$captureThumbnail$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $taskId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$taskId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActivityTaskManagerThumbnailLoader.this.new AnonymousClass2(this.$taskId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            TaskSnapshot taskSnapshotTakeTaskSnapshot;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ActivityManagerWrapper activityManagerWrapper = ActivityTaskManagerThumbnailLoader.this.activityManager;
            int i = this.$taskId;
            activityManagerWrapper.getClass();
            try {
                taskSnapshotTakeTaskSnapshot = ActivityTaskManager.getService().takeTaskSnapshot(i, true);
            } catch (RemoteException e) {
                Log.w("ActivityManagerWrapper", "Failed to take task snapshot", e);
                taskSnapshotTakeTaskSnapshot = null;
            }
            ThumbnailData thumbnailDataFromSnapshot = taskSnapshotTakeTaskSnapshot != null ? ThumbnailData.fromSnapshot(taskSnapshotTakeTaskSnapshot) : new ThumbnailData();
            if (thumbnailDataFromSnapshot.thumbnail != null) {
                return thumbnailDataFromSnapshot;
            }
            return null;
        }
    }

    /* renamed from: com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader$loadThumbnail$2, reason: invalid class name and case insensitive filesystem */
    final class C09662 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $taskId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09662(int i, Continuation continuation) {
            super(2, continuation);
            this.$taskId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActivityTaskManagerThumbnailLoader.this.new C09662(this.$taskId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09662) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            TaskSnapshot taskSnapshot;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ActivityManagerWrapper activityManagerWrapper = ActivityTaskManagerThumbnailLoader.this.activityManager;
            int i = this.$taskId;
            activityManagerWrapper.getClass();
            try {
                taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(i, false);
            } catch (RemoteException e) {
                Log.w("ActivityManagerWrapper", "Failed to retrieve task snapshot", e);
                taskSnapshot = null;
            }
            ThumbnailData thumbnailDataFromSnapshot = taskSnapshot != null ? ThumbnailData.fromSnapshot(taskSnapshot) : new ThumbnailData();
            if (thumbnailDataFromSnapshot.thumbnail != null) {
                return thumbnailDataFromSnapshot;
            }
            return null;
        }
    }

    public ActivityTaskManagerThumbnailLoader(CoroutineDispatcher coroutineDispatcher, ActivityManagerWrapper activityManagerWrapper) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.activityManager = activityManagerWrapper;
    }

    public final Object captureThumbnail(int i, Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new AnonymousClass2(i, null), continuation);
    }

    public final Object loadThumbnail(int i, Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new C09662(i, null), continuation);
    }
}
