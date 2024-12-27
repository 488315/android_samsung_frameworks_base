package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.UserHandle;
import com.android.systemui.screenshot.ScreenshotPolicy;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
