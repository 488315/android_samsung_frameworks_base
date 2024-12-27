package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.UserHandle;
import com.android.systemui.screenshot.ScreenshotPolicy;
import kotlin.jvm.internal.Intrinsics;

public abstract class ScreenshotPolicyImplKt {
    public static final ScreenshotPolicy.DisplayContentInfo toDisplayContentInfo(ActivityTaskManager.RootTaskInfo rootTaskInfo) {
        ComponentName componentName = rootTaskInfo.topActivity;
        if (componentName == null) {
            throw new IllegalStateException("should not be null".toString());
        }
        int[] iArr = rootTaskInfo.childTaskIds;
        int length = iArr.length - 1;
        int i = iArr[length];
        int i2 = rootTaskInfo.childTaskUserIds[length];
        Rect rect = rootTaskInfo.childTaskBounds[length];
        Intrinsics.checkNotNull(rect);
        return new ScreenshotPolicy.DisplayContentInfo(componentName, rect, UserHandle.of(i2), i);
    }
}
