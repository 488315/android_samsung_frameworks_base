package com.samsung.android.desktopsystemui.sharedlib.system;

import android.graphics.Rect;
import com.samsung.android.desktopsystemui.sharedlib.recents.model.ThumbnailData;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface RecentsAnimationListener {
    void onAnimationCanceled(HashMap<Integer, ThumbnailData> hashMap);

    void onAnimationStart(RecentsAnimationControllerCompat recentsAnimationControllerCompat, RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr, RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr2, Rect rect, Rect rect2);

    void onTasksAppeared(RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr);
}
