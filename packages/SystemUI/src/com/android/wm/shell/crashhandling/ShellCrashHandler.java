package com.android.wm.shell.crashhandling;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.window.DesktopExperienceFlags;
import android.window.TaskAppearedInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.HomeIntentProvider;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class ShellCrashHandler {
    public final DesktopState desktopState;
    public final HomeIntentProvider homeIntentProvider;
    public final ShellTaskOrganizer shellTaskOrganizer;

    public ShellCrashHandler(ShellTaskOrganizer shellTaskOrganizer, HomeIntentProvider homeIntentProvider, DesktopState desktopState, ShellInit shellInit) {
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.homeIntentProvider = homeIntentProvider;
        this.desktopState = desktopState;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.crashhandling.ShellCrashHandler.1
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                ArrayList arrayList;
                ShellCrashHandler shellCrashHandler = ShellCrashHandler.this;
                if (!((DesktopStateImpl) shellCrashHandler.desktopState).canEnterDesktopMode || DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                    return;
                }
                ShellTaskOrganizer shellTaskOrganizer2 = shellCrashHandler.shellTaskOrganizer;
                synchronized (shellTaskOrganizer2.mLock) {
                    try {
                        arrayList = new ArrayList();
                        for (int i = 0; i < shellTaskOrganizer2.mTasks.size(); i++) {
                            arrayList.add(((TaskAppearedInfo) shellTaskOrganizer2.mTasks.valueAt(i)).getTaskInfo());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                int size = arrayList.size();
                boolean z = false;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    if (((ActivityManager.RunningTaskInfo) obj).getWindowingMode() == 5) {
                        z = true;
                    }
                    if (z) {
                        ShellTaskOrganizer shellTaskOrganizer3 = shellCrashHandler.shellTaskOrganizer;
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        shellCrashHandler.homeIntentProvider.addLaunchHomePendingIntent(windowContainerTransaction, 0, null);
                        shellTaskOrganizer3.applyTransaction(windowContainerTransaction);
                        return;
                    }
                }
            }
        }, this);
    }
}
