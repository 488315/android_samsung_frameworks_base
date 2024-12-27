package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaProjectionAppSelectorController {
    public final ComponentName appSelectorComponentName;
    public final String callerPackageName;
    public final ScreenCaptureDevicePolicyResolver devicePolicyResolver;
    public final int hostUid;
    public final UserHandle hostUserHandle;
    public final boolean isFirstStart;
    public final MediaProjectionMetricsLogger logger;
    public final RecentTaskListProvider recentTaskListProvider;
    public final CoroutineScope scope;
    public final RecentTaskThumbnailLoader thumbnailLoader;
    public final MediaProjectionAppSelectorView view;

    public MediaProjectionAppSelectorController(RecentTaskListProvider recentTaskListProvider, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver, UserHandle userHandle, CoroutineScope coroutineScope, ComponentName componentName, String str, RecentTaskThumbnailLoader recentTaskThumbnailLoader, boolean z, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, int i) {
        this.recentTaskListProvider = recentTaskListProvider;
        this.view = mediaProjectionAppSelectorView;
        this.devicePolicyResolver = screenCaptureDevicePolicyResolver;
        this.hostUserHandle = userHandle;
        this.scope = coroutineScope;
        this.appSelectorComponentName = componentName;
        this.callerPackageName = str;
        this.thumbnailLoader = recentTaskThumbnailLoader;
        this.isFirstStart = z;
        this.logger = mediaProjectionMetricsLogger;
        this.hostUid = i;
    }
}
