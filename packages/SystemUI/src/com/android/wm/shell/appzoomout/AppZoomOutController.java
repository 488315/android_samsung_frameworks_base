package com.android.wm.shell.appzoomout;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Slog;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellInit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AppZoomOutController implements RemoteCallable, ShellTaskOrganizer.FocusListener, DisplayChangeController.OnDisplayChangingListener {
    public final Context mContext;
    public final AppZoomOutDisplayAreaOrganizer mDisplayAreaOrganizer;
    public final DisplayController mDisplayController;
    public final ShellExecutor mMainExecutor;
    public final AppZoomOutImpl mImpl = new AppZoomOutImpl(this, 0);
    public final AnonymousClass1 mDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.appzoomout.AppZoomOutController.1
        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayAdded(int i) {
            if (i != 0) {
                return;
            }
            AppZoomOutController appZoomOutController = AppZoomOutController.this;
            DisplayLayout displayLayout = appZoomOutController.mDisplayController.getDisplayLayout(i);
            if (displayLayout == null) {
                Slog.w("AppZoomOutController", "Failed to get new DisplayLayout.");
            } else {
                appZoomOutController.mDisplayAreaOrganizer.mDisplayLayout.set(displayLayout);
            }
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            if (i != 0) {
                return;
            }
            AppZoomOutController appZoomOutController = AppZoomOutController.this;
            DisplayLayout displayLayout = appZoomOutController.mDisplayController.getDisplayLayout(i);
            if (displayLayout == null) {
                Slog.w("AppZoomOutController", "Failed to get new DisplayLayout.");
            } else {
                appZoomOutController.mDisplayAreaOrganizer.mDisplayLayout.set(displayLayout);
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AppZoomOutImpl {
        public /* synthetic */ AppZoomOutImpl(AppZoomOutController appZoomOutController, int i) {
            this();
        }

        private AppZoomOutImpl() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.wm.shell.appzoomout.AppZoomOutController$1] */
    public AppZoomOutController(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, AppZoomOutDisplayAreaOrganizer appZoomOutDisplayAreaOrganizer, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mDisplayController = displayController;
        this.mDisplayAreaOrganizer = appZoomOutDisplayAreaOrganizer;
        this.mMainExecutor = shellExecutor;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        if (i3 != -1) {
            Context context = this.mContext;
            DisplayLayout displayLayout = this.mDisplayAreaOrganizer.mDisplayLayout;
            if (displayLayout.mRotation == i3) {
                return;
            }
            displayLayout.rotateTo(context.getResources(), i3);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.FocusListener
    public final void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo != null && runningTaskInfo.getActivityType() == 2) {
            boolean z = runningTaskInfo.isFocused;
            AppZoomOutDisplayAreaOrganizer appZoomOutDisplayAreaOrganizer = this.mDisplayAreaOrganizer;
            Boolean bool = appZoomOutDisplayAreaOrganizer.mIsHomeTaskFocused;
            if (bool == null || bool.booleanValue() != z) {
                appZoomOutDisplayAreaOrganizer.mIsHomeTaskFocused = Boolean.valueOf(z);
                appZoomOutDisplayAreaOrganizer.apply();
            }
        }
    }
}
