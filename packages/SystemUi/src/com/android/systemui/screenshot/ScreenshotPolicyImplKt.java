package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.screenshot.ScreenshotPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ScreenshotPolicyImplKt {
    public static final ScreenshotPolicy.DisplayContentInfo toDisplayContentInfo(ActivityTaskManager.RootTaskInfo rootTaskInfo) {
        ComponentName componentName = rootTaskInfo.topActivity;
        if (componentName == null) {
            throw new IllegalStateException("should not be null".toString());
        }
        int[] iArr = rootTaskInfo.childTaskIds;
        int length = iArr.length - 1;
        return new ScreenshotPolicy.DisplayContentInfo(componentName, rootTaskInfo.childTaskBounds[length], UserHandle.of(rootTaskInfo.childTaskUserIds[length]), iArr[length]);
    }
}
