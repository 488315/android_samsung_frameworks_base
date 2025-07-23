package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ActivityTaskManagerThumbnailLoader implements RecentTaskThumbnailLoader {
    public final ActivityManagerWrapper activityManager;
    public final CoroutineDispatcher coroutineDispatcher;

    public ActivityTaskManagerThumbnailLoader(CoroutineDispatcher coroutineDispatcher, ActivityManagerWrapper activityManagerWrapper) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.activityManager = activityManagerWrapper;
    }

    public final Object captureThumbnail(int i, Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ActivityTaskManagerThumbnailLoader$captureThumbnail$2(this, i, null), continuation);
    }

    public final Object loadThumbnail(int i, Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ActivityTaskManagerThumbnailLoader$loadThumbnail$2(this, i, null), continuation);
    }
}
