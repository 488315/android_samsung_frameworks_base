package com.android.server.wm;

public final class TaskbarController {
    public final DisplayPolicy mDisplayPolicy;
    public WindowState mTaskbarWin;

    public TaskbarController(DisplayPolicyExt displayPolicyExt) {
        WindowManagerService windowManagerService = displayPolicyExt.mService;
        this.mDisplayPolicy = displayPolicyExt.mDisplayPolicy;
    }
}
