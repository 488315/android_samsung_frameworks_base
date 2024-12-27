package com.android.server.wm;

public final class FreeformTaskPinningController {
    public Task mPinnedTask;
    public final TaskDisplayArea mTaskDisplayArea;

    public FreeformTaskPinningController(TaskDisplayArea taskDisplayArea) {
        this.mTaskDisplayArea = taskDisplayArea;
        taskDisplayArea.setFreeformTaskPinning(1);
    }
}
