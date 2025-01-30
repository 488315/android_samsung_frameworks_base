package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionAppSelectorController {
    public final ComponentName appSelectorComponentName;
    public final String callerPackageName;
    public final ScreenCaptureDevicePolicyResolver devicePolicyResolver;
    public final UserHandle hostUserHandle;
    public final RecentTaskListProvider recentTaskListProvider;
    public final CoroutineScope scope;
    public final MediaProjectionAppSelectorView view;

    public MediaProjectionAppSelectorController(RecentTaskListProvider recentTaskListProvider, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver, UserHandle userHandle, CoroutineScope coroutineScope, ComponentName componentName, String str) {
        this.recentTaskListProvider = recentTaskListProvider;
        this.view = mediaProjectionAppSelectorView;
        this.devicePolicyResolver = screenCaptureDevicePolicyResolver;
        this.hostUserHandle = userHandle;
        this.scope = coroutineScope;
        this.appSelectorComponentName = componentName;
        this.callerPackageName = str;
    }

    public final void init() {
        BuildersKt.launch$default(this.scope, null, null, new MediaProjectionAppSelectorController$init$1(this, null), 3);
    }
}
