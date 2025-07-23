package com.android.wm.shell.compatui;

import android.app.AppCompatTaskInfo;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ReachabilityEduWindowManager extends CompatUIWindowManagerAbstract {
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final Function mDisappearTimeSupplier;
    public boolean mHasLetterboxSizeChanged;
    public boolean mHasUserDoubleTapped;
    public boolean mIsLetterboxDoubleTapEnabled;
    ReachabilityEduLayout mLayout;
    public int mLetterboxHorizontalPosition;
    public int mLetterboxVerticalPosition;
    public final ShellExecutor mMainExecutor;
    public long mNextHideTime;
    public final BiConsumer mOnDismissCallback;
    public int mTopActivityLetterboxHeight;
    public int mTopActivityLetterboxWidth;

    public ReachabilityEduWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIConfiguration compatUIConfiguration, ShellExecutor shellExecutor, BiConsumer<TaskInfo, ShellTaskOrganizer.TaskListener> biConsumer, Function<Integer, Integer> function) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mNextHideTime = -1L;
        AppCompatTaskInfo appCompatTaskInfo = taskInfo.appCompatTaskInfo;
        this.mIsLetterboxDoubleTapEnabled = appCompatTaskInfo.isLetterboxDoubleTapEnabled();
        this.mLetterboxVerticalPosition = appCompatTaskInfo.topActivityLetterboxVerticalPosition;
        this.mLetterboxHorizontalPosition = appCompatTaskInfo.topActivityLetterboxHorizontalPosition;
        this.mTopActivityLetterboxWidth = appCompatTaskInfo.topActivityLetterboxWidth;
        this.mTopActivityLetterboxHeight = appCompatTaskInfo.topActivityLetterboxHeight;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mMainExecutor = shellExecutor;
        this.mOnDismissCallback = biConsumer;
        this.mDisappearTimeSupplier = function;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        ReachabilityEduLayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        inflateLayout.getClass();
        updateVisibilityOfViews$2();
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        if (this.mIsLetterboxDoubleTapEnabled) {
            return (this.mLetterboxVerticalPosition == -1 && this.mLetterboxHorizontalPosition == -1) ? false : true;
        }
        return false;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        ReachabilityEduLayout reachabilityEduLayout = this.mLayout;
        if (reachabilityEduLayout == null) {
            return new WindowManager.LayoutParams();
        }
        Rect taskBounds = getTaskBounds();
        reachabilityEduLayout.measure(View.MeasureSpec.makeMeasureSpec(taskBounds.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(taskBounds.height(), 1073741824));
        return getWindowLayoutParams(reachabilityEduLayout.getMeasuredWidth(), reachabilityEduLayout.getMeasuredHeight());
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getWindowManagerLayoutParamsFlags() {
        return 24;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public ReachabilityEduLayout inflateLayout() {
        return (ReachabilityEduLayout) LayoutInflater.from(this.mContext).inflate(R.layout.reachability_ui_layout, (ViewGroup) null);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void onParentBoundsChanged() {
        if (this.mLayout == null) {
            return;
        }
        WindowManager.LayoutParams windowLayoutParams = getWindowLayoutParams();
        this.mLayout.setLayoutParams(windowLayoutParams);
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost == null) {
            return;
        }
        surfaceControlViewHost.relayout(windowLayoutParams);
        updateSurfacePosition();
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        boolean z2 = this.mIsLetterboxDoubleTapEnabled;
        int i = this.mLetterboxVerticalPosition;
        int i2 = this.mLetterboxHorizontalPosition;
        int i3 = this.mTopActivityLetterboxWidth;
        int i4 = this.mTopActivityLetterboxHeight;
        AppCompatTaskInfo appCompatTaskInfo = taskInfo.appCompatTaskInfo;
        this.mIsLetterboxDoubleTapEnabled = appCompatTaskInfo.isLetterboxDoubleTapEnabled();
        this.mLetterboxVerticalPosition = appCompatTaskInfo.topActivityLetterboxVerticalPosition;
        this.mLetterboxHorizontalPosition = appCompatTaskInfo.topActivityLetterboxHorizontalPosition;
        this.mTopActivityLetterboxWidth = appCompatTaskInfo.topActivityLetterboxWidth;
        this.mTopActivityLetterboxHeight = appCompatTaskInfo.topActivityLetterboxHeight;
        this.mHasUserDoubleTapped = appCompatTaskInfo.isFromLetterboxDoubleTap();
        if (!super.updateCompatInfo(taskInfo, taskListener, z)) {
            return false;
        }
        int i5 = this.mTopActivityLetterboxWidth;
        this.mHasLetterboxSizeChanged = (i3 == i5 && i4 == this.mTopActivityLetterboxHeight) ? false : true;
        if (this.mHasUserDoubleTapped || z2 != this.mIsLetterboxDoubleTapEnabled || i != this.mLetterboxVerticalPosition || i2 != this.mLetterboxHorizontalPosition || i3 != i5 || i4 != this.mTopActivityLetterboxHeight) {
            updateVisibilityOfViews$2();
        }
        return true;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public void updateSurfacePosition() {
        if (this.mLayout == null || this.mLeash == null) {
            return;
        }
        this.mSyncQueue.runInSync(new CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0(this, 0, 0));
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0289, code lost:
    
        if (r1.mCompatUISharedPreferences.getBoolean("has_seen_horizontal_reachability_education@" + r2.userId, false) != false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x02a9, code lost:
    
        r3 = 1;
        ((com.android.wm.shell.common.HandlerExecutor) r17.mMainExecutor).executeDelayed(new com.android.wm.shell.compatui.ReachabilityEduWindowManager$$ExternalSyntheticLambda0(r17, r3), r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x02a7, code lost:
    
        if (r1.mCompatUISharedPreferences.getBoolean("has_seen_vertical_reachability_education@" + r2.userId, false) != false) goto L90;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVisibilityOfViews$2() {
        /*
            Method dump skipped, instructions count: 704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.compatui.ReachabilityEduWindowManager.updateVisibilityOfViews$2():void");
    }
}
