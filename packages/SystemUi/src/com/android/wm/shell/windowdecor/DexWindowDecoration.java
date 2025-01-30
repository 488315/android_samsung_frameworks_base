package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.systemui.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DexWindowDecoration extends WindowDecoration {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mIsShowingRestart;
    public WindowManager.LayoutParams mLayoutParam;
    public WindowDecoration.AdditionalWindow mRestart;
    public Context mSnackbarContext;
    public final SyncTransactionQueue mSyncQueue;

    public DexWindowDecoration(Context context, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue) {
        super(context, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl);
        this.mSyncQueue = syncTransactionQueue;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration, java.lang.AutoCloseable
    public final void close() {
        super.close();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mTaskInfo = runningTaskInfo;
        WindowDecoration.AdditionalWindow additionalWindow = this.mRestart;
        if (additionalWindow == null || additionalWindow.mWindowViewHost == null || this.mLayoutParam == null) {
            return;
        }
        this.mContext.getMainThreadHandler().post(new DexWindowDecoration$$ExternalSyntheticLambda0(this, 1));
    }

    public final void showRestartSnackbar(View view) {
        ActivityInfo activityInfo = this.mTaskInfo.topActivityInfo;
        Snackbar make = Snackbar.make(view, this.mSnackbarContext.getResources().getString(R.string.dex_restart_popup_message, (activityInfo != null ? activityInfo.applicationInfo.loadLabel(this.mSnackbarContext.getPackageManager()) : "app").toString()), 0);
        make.setAction(this.mSnackbarContext.getResources().getString(R.string.dex_restart), new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.DexWindowDecoration.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                try {
                    ActivityTaskManager.getService().startActivityForDexRestart(DexWindowDecoration.this.mTaskInfo.taskId);
                } catch (RemoteException unused) {
                    int i = DexWindowDecoration.$r8$clinit;
                    Log.d("DexWindowDecoration", "startActivityFromRecents remote exception");
                }
            }
        });
        make.addCallback(new BaseTransientBottomBar.BaseCallback() { // from class: com.android.wm.shell.windowdecor.DexWindowDecoration.3
            @Override // com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
            public final void onDismissed(Object obj) {
                DexWindowDecoration dexWindowDecoration = DexWindowDecoration.this;
                dexWindowDecoration.mContext.getMainThreadHandler().post(new DexWindowDecoration$$ExternalSyntheticLambda0(dexWindowDecoration, 0));
            }
        });
        this.mIsShowingRestart = true;
        make.show();
    }
}
