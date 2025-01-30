package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class FlexPanelController extends PackagesChange {
  public static boolean isImmersiveVideoControlsEnabled(WindowState windowState) {
    DisplayContent displayContent = windowState.getDisplayContent();
    if (displayContent == null) {
      return false;
    }
    Task rootSideStageTask = displayContent.getDefaultTaskDisplayArea().getRootSideStageTask();
    ActivityRecord topVisibleActivity =
        rootSideStageTask != null ? rootSideStageTask.getTopVisibleActivity() : null;
    String className = topVisibleActivity != null ? topVisibleActivity.getClassName() : "";
    return "com.android.wm.shell.controlpanel.activity.VideoControlsDimActivity".equals(className)
        || "com.android.wm.shell.controlpanel.activity.VideoControlsActivity".equals(className);
  }
}
