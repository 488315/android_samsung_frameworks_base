package com.android.wm.shell.compatui.coverlauncher;

/* loaded from: classes3.dex */
public final /* synthetic */ class CoverLauncherAppCompatUIController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ CoverLauncherAppCompatUIController f$0;

    @Override // java.lang.Runnable
    public final void run() {
        CoverLauncherAppCompatUIController coverLauncherAppCompatUIController = this.f$0;
        coverLauncherAppCompatUIController.mController.removeLayouts(coverLauncherAppCompatUIController.mTaskInfo.taskId);
    }
}
