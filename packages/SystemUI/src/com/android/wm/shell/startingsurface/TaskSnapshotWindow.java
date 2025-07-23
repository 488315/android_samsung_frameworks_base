package com.android.wm.shell.startingsurface;

import android.app.ActivityManager;
import android.graphics.Paint;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.IWindowSession;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowManagerGlobal;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import android.window.TaskSnapshot;
import com.android.internal.view.BaseIWindow;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.startingsurface.TaskSnapshotWindow;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskSnapshotWindow {
    public final Paint mBackgroundPaint;
    public final Runnable mClearWindowHandler;
    public boolean mHasDrawn;
    public final boolean mHasImeSurface;
    public final int mOrientationOnCreation;
    public final IWindowSession mSession;
    public final ShellExecutor mSplashScreenExecutor;
    public final Window mWindow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Window extends BaseIWindow {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final WeakReference mOuter;

        public Window(TaskSnapshotWindow taskSnapshotWindow) {
            this.mOuter = new WeakReference(taskSnapshotWindow);
        }

        public final void resized(ClientWindowFrames clientWindowFrames, final boolean z, final MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
            final TaskSnapshotWindow taskSnapshotWindow = (TaskSnapshotWindow) this.mOuter.get();
            if (taskSnapshotWindow == null) {
                return;
            }
            taskSnapshotWindow.mSplashScreenExecutor.execute(new Runnable() { // from class: com.android.wm.shell.startingsurface.TaskSnapshotWindow$Window$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MergedConfiguration mergedConfiguration2 = mergedConfiguration;
                    TaskSnapshotWindow taskSnapshotWindow2 = taskSnapshotWindow;
                    boolean z5 = z;
                    int i3 = TaskSnapshotWindow.Window.$r8$clinit;
                    if (mergedConfiguration2 != null) {
                        if (taskSnapshotWindow2.mOrientationOnCreation != mergedConfiguration2.getMergedConfiguration().orientation) {
                            taskSnapshotWindow2.clearWindowSynced();
                            return;
                        }
                    }
                    if (z5 && taskSnapshotWindow2.mHasDrawn) {
                        try {
                            taskSnapshotWindow2.mSession.finishDrawing(taskSnapshotWindow2.mWindow, (SurfaceControl.Transaction) null, Integer.MAX_VALUE);
                        } catch (RemoteException unused) {
                            taskSnapshotWindow2.clearWindowSynced();
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
        Window window = new Window(this);
        this.mWindow = window;
        window.setSession(windowSession);
        int backgroundColor = taskDescription.getBackgroundColor();
        paint.setColor(backgroundColor == 0 ? -1 : backgroundColor);
        this.mOrientationOnCreation = i;
        this.mClearWindowHandler = runnable;
        this.mHasImeSurface = taskSnapshot.hasImeSurface();
    }

    public final void clearWindowSynced() {
        ((HandlerExecutor) this.mSplashScreenExecutor).executeDelayed(this.mClearWindowHandler, 0L);
    }
}
