package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.InputTransferToken;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;

/* loaded from: classes3.dex */
public abstract class CompatUIWindowManagerAbstract extends WindowlessWindowManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public final int mDisplayId;
    public DisplayLayout mDisplayLayout;
    public SurfaceControl mLeash;
    public final Rect mStableBounds;
    public final SyncTransactionQueue mSyncQueue;
    public Configuration mTaskConfig;
    public final int mTaskId;
    public TaskInfo mTaskInfo;
    public ShellTaskOrganizer.TaskListener mTaskListener;
    public SurfaceControlViewHost mViewHost;

    public CompatUIWindowManagerAbstract(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout) {
        super(taskInfo.configuration, (SurfaceControl) null, (InputTransferToken) null);
        this.mTaskInfo = taskInfo;
        this.mContext = context;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskConfig = taskInfo.configuration;
        this.mDisplayId = context.getDisplayId();
        this.mTaskId = taskInfo.taskId;
        this.mTaskListener = taskListener;
        this.mDisplayLayout = displayLayout;
        Rect rect = new Rect();
        this.mStableBounds = rect;
        this.mDisplayLayout.getStableBounds(rect, false);
    }

    public void attachToParentSurface(SurfaceControl.Builder builder) {
        this.mTaskListener.attachChildSurfaceToTask(this.mTaskId, builder);
    }

    public abstract View createLayout();

    public boolean createLayout(boolean z) {
        if (!eligibleToShowLayout()) {
            return false;
        }
        if (!z || getLayout() != null) {
            return true;
        }
        if (this.mViewHost != null) {
            throw new IllegalStateException("A UI has already been created with this window manager.");
        }
        SurfaceControlViewHost surfaceControlViewHostCreateSurfaceViewHost = createSurfaceViewHost();
        this.mViewHost = surfaceControlViewHostCreateSurfaceViewHost;
        surfaceControlViewHostCreateSurfaceViewHost.setView(createLayout(), getWindowLayoutParams());
        updateSurfacePosition();
        return true;
    }

    public SurfaceControlViewHost createSurfaceViewHost() {
        Context context = this.mContext;
        return new SurfaceControlViewHost(context, context.getDisplay(), this, getClass().getSimpleName());
    }

    public abstract boolean eligibleToShowLayout();

    public abstract View getLayout();

    public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
        String simpleName = getClass().getSimpleName();
        SurfaceControl.Builder callsite = new SurfaceControl.Builder().setContainerLayer().setName(simpleName.concat("Leash")).setHidden(false).setCallsite(simpleName.concat("#attachToParentSurface"));
        attachToParentSurface(callsite);
        final SurfaceControl surfaceControlBuild = callsite.build();
        this.mLeash = surfaceControlBuild;
        final int zOrder = getZOrder();
        this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                SurfaceControl surfaceControl = surfaceControlBuild;
                int i = CompatUIWindowManagerAbstract.$r8$clinit;
                CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = this.f$0;
                if (surfaceControl == null || !surfaceControl.isValid()) {
                    Log.w(compatUIWindowManagerAbstract.getClass().getSimpleName(), "The leash has been released.");
                } else {
                    transaction.setLayer(surfaceControl, zOrder);
                }
            }
        });
        return this.mLeash;
    }

    public final Rect getTaskBounds() {
        return this.mTaskConfig.windowConfiguration.getBounds();
    }

    public final Rect getTaskStableBounds() {
        Rect rect = new Rect(this.mStableBounds);
        rect.intersect(getTaskBounds());
        return rect;
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        View layout = getLayout();
        if (layout == null) {
            return new WindowManager.LayoutParams();
        }
        layout.measure(0, 0);
        return getWindowLayoutParams(layout.getMeasuredWidth(), layout.getMeasuredHeight());
    }

    public int getWindowManagerLayoutParamsFlags() {
        return 40;
    }

    public abstract int getZOrder();

    public boolean needsToBeRecreated(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return (this.mTaskInfo.configuration.uiMode == taskInfo.configuration.uiMode && this.mTaskListener.equals(taskListener)) ? false : true;
    }

    public void onParentBoundsChanged() {
        updateSurfacePosition();
    }

    public void release() {
        View layout = getLayout();
        if (layout != null) {
            layout.setVisibility(8);
        }
        removeLayout();
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            this.mViewHost = null;
        }
        final SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl != null) {
            this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.compatui.CompatUIWindowManagerAbstract$$ExternalSyntheticLambda2
                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                    SurfaceControl surfaceControl2 = surfaceControl;
                    int i = CompatUIWindowManagerAbstract.$r8$clinit;
                    transaction.remove(surfaceControl2);
                }
            });
            this.mLeash = null;
        }
    }

    public abstract void removeLayout();

    public final void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
        this.mContext = this.mContext.createConfigurationContext(configuration);
    }

    public final void setTouchRegion(Region region) {
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        IBinder windowToken = (surfaceControlViewHost == null || surfaceControlViewHost.getView() == null) ? null : this.mViewHost.getView().getWindowToken();
        if (windowToken == null) {
            return;
        }
        super.setTouchRegion(windowToken, region);
    }

    public boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        this.mTaskInfo = taskInfo;
        Configuration configuration = this.mTaskConfig;
        ShellTaskOrganizer.TaskListener taskListener2 = this.mTaskListener;
        Configuration configuration2 = taskInfo.configuration;
        this.mTaskConfig = configuration2;
        this.mTaskListener = taskListener;
        setConfiguration(configuration2);
        if (!eligibleToShowLayout()) {
            release();
            return false;
        }
        View layout = getLayout();
        if (layout != null && taskListener2 == taskListener) {
            Configuration configuration3 = this.mTaskConfig;
            if (configuration3.uiMode == configuration.uiMode) {
                boolean zEquals = configuration3.windowConfiguration.getBounds().equals(configuration.windowConfiguration.getBounds());
                boolean z2 = this.mTaskConfig.getLayoutDirection() != configuration.getLayoutDirection();
                if (!zEquals || z2) {
                    onParentBoundsChanged();
                }
                if (z2) {
                    layout.setLayoutDirection(this.mTaskConfig.getLayoutDirection());
                }
                return true;
            }
        }
        release();
        return createLayout(z);
    }

    public void updateDisplayLayout(DisplayLayout displayLayout) {
        Rect rect = this.mStableBounds;
        Rect rect2 = new Rect();
        displayLayout.getStableBounds(rect2, false);
        this.mDisplayLayout = displayLayout;
        if (rect.equals(rect2)) {
            return;
        }
        this.mStableBounds.set(rect2);
        onParentBoundsChanged();
    }

    public abstract void updateSurfacePosition();

    public void updateVisibility(boolean z) {
        View layout = getLayout();
        if (layout == null) {
            createLayout(z);
            return;
        }
        int i = (z && eligibleToShowLayout()) ? 0 : 8;
        if (layout.getVisibility() != i) {
            layout.setVisibility(i);
        }
    }

    public final WindowManager.LayoutParams getWindowLayoutParams(int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2038, getWindowManagerLayoutParamsFlags(), -3);
        layoutParams.token = new Binder();
        layoutParams.setTitle(getClass().getSimpleName() + this.mTaskId);
        layoutParams.privateFlags = layoutParams.privateFlags | 536870976;
        return layoutParams;
    }
}
