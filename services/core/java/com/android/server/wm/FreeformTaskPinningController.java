package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FreeformTaskPinningController {
    public Task mPinnedTask;
    public final TaskDisplayArea mTaskDisplayArea;

    public FreeformTaskPinningController(TaskDisplayArea taskDisplayArea) {
        this.mTaskDisplayArea = taskDisplayArea;
        taskDisplayArea.setFreeformTaskPinning(1);
    }
}
