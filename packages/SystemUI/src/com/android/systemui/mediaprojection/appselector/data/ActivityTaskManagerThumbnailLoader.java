package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
