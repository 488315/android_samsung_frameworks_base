package com.android.wm.shell.startingsurface;

import android.app.ActivityManager;
import android.graphics.Paint;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.IWindowSession;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowManagerGlobal;
import android.window.ClientWindowFrames;
import android.window.TaskSnapshot;
import com.android.internal.view.BaseIWindow;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskSnapshotWindow {
    public final Paint mBackgroundPaint;
    public final Runnable mClearWindowHandler;
    public boolean mHasDrawn;
    public final boolean mHasImeSurface;
    public final int mOrientationOnCreation;
    public final IWindowSession mSession;
    public final ShellExecutor mSplashScreenExecutor;
    public final Window mWindow;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Window extends BaseIWindow {
        public WeakReference mOuter;

        public final void resized(ClientWindowFrames clientWindowFrames, final boolean z, final MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
            final TaskSnapshotWindow taskSnapshotWindow = (TaskSnapshotWindow) this.mOuter.get();
            if (taskSnapshotWindow == null) {
                return;
            }
            ((HandlerExecutor) taskSnapshotWindow.mSplashScreenExecutor).execute(new Runnable() { // from class: com.android.wm.shell.startingsurface.TaskSnapshotWindow$Window$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MergedConfiguration mergedConfiguration2 = mergedConfiguration;
                    TaskSnapshotWindow taskSnapshotWindow2 = taskSnapshotWindow;
                    boolean z5 = z;
                    if (mergedConfiguration2 != null && taskSnapshotWindow2.mOrientationOnCreation != mergedConfiguration2.getMergedConfiguration().orientation) {
                        ((HandlerExecutor) taskSnapshotWindow2.mSplashScreenExecutor).executeDelayed(0L, taskSnapshotWindow2.mClearWindowHandler);
                    } else if (z5 && taskSnapshotWindow2.mHasDrawn) {
                        try {
                            taskSnapshotWindow2.mSession.finishDrawing(taskSnapshotWindow2.mWindow, (SurfaceControl.Transaction) null, Integer.MAX_VALUE);
                        } catch (RemoteException unused) {
                            ((HandlerExecutor) taskSnapshotWindow2.mSplashScreenExecutor).executeDelayed(0L, taskSnapshotWindow2.mClearWindowHandler);
                        }
                    }
                }
            });
        }
    }

    public TaskSnapshotWindow(TaskSnapshot taskSnapshot, ActivityManager.TaskDescription taskDescription, int i, Runnable runnable, ShellExecutor shellExecutor) {
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        this.mSplashScreenExecutor = shellExecutor;
        IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
        this.mSession = windowSession;
        Window window = new Window();
        this.mWindow = window;
        window.setSession(windowSession);
        int backgroundColor = taskDescription.getBackgroundColor();
        paint.setColor(backgroundColor == 0 ? -1 : backgroundColor);
        this.mOrientationOnCreation = i;
        this.mClearWindowHandler = runnable;
        this.mHasImeSurface = taskSnapshot.hasImeSurface();
    }
}
