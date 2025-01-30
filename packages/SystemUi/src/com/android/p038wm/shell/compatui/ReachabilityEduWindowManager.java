package com.android.p038wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ReachabilityEduWindowManager extends CompatUIWindowManagerAbstract {
    public final CompatUIConfiguration mCompatUIConfiguration;
    public boolean mHasLetterboxSizeChanged;
    public boolean mHasUserDoubleTapped;
    public boolean mIsActivityLetterboxed;
    ReachabilityEduLayout mLayout;
    public int mLetterboxHorizontalPosition;
    public int mLetterboxVerticalPosition;
    public final ShellExecutor mMainExecutor;
    public long mNextHideTime;
    public int mTopActivityLetterboxHeight;
    public int mTopActivityLetterboxWidth;

    public ReachabilityEduWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIConfiguration compatUIConfiguration, ShellExecutor shellExecutor) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mNextHideTime = -1L;
        this.mIsActivityLetterboxed = taskInfo.isLetterboxDoubleTapEnabled;
        this.mLetterboxVerticalPosition = taskInfo.topActivityLetterboxVerticalPosition;
        this.mLetterboxHorizontalPosition = taskInfo.topActivityLetterboxHorizontalPosition;
        this.mTopActivityLetterboxWidth = taskInfo.topActivityLetterboxWidth;
        this.mTopActivityLetterboxHeight = taskInfo.topActivityLetterboxHeight;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mMainExecutor = shellExecutor;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        ReachabilityEduLayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        inflateLayout.getClass();
        updateVisibilityOfViews();
        return this.mLayout;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        return this.mIsActivityLetterboxed && !(this.mLetterboxVerticalPosition == -1 && this.mLetterboxHorizontalPosition == -1);
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        ReachabilityEduLayout reachabilityEduLayout = this.mLayout;
        if (reachabilityEduLayout == null) {
            return new WindowManager.LayoutParams();
        }
        Rect taskBounds = getTaskBounds();
        reachabilityEduLayout.measure(View.MeasureSpec.makeMeasureSpec(taskBounds.width(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(taskBounds.height(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
        return getWindowLayoutParams(reachabilityEduLayout.getMeasuredWidth(), reachabilityEduLayout.getMeasuredHeight());
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getWindowManagerLayoutParamsFlags() {
        return 24;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public ReachabilityEduLayout inflateLayout() {
        return (ReachabilityEduLayout) LayoutInflater.from(this.mContext).inflate(R.layout.reachability_ui_layout, (ViewGroup) null);
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
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

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        boolean z2 = this.mIsActivityLetterboxed;
        int i = this.mLetterboxVerticalPosition;
        int i2 = this.mLetterboxHorizontalPosition;
        int i3 = this.mTopActivityLetterboxWidth;
        int i4 = this.mTopActivityLetterboxHeight;
        this.mIsActivityLetterboxed = taskInfo.isLetterboxDoubleTapEnabled;
        this.mLetterboxVerticalPosition = taskInfo.topActivityLetterboxVerticalPosition;
        this.mLetterboxHorizontalPosition = taskInfo.topActivityLetterboxHorizontalPosition;
        this.mTopActivityLetterboxWidth = taskInfo.topActivityLetterboxWidth;
        this.mTopActivityLetterboxHeight = taskInfo.topActivityLetterboxHeight;
        this.mHasUserDoubleTapped = taskInfo.isFromLetterboxDoubleTap;
        if (!super.updateCompatInfo(taskInfo, taskListener, z)) {
            return false;
        }
        int i5 = this.mTopActivityLetterboxWidth;
        this.mHasLetterboxSizeChanged = (i3 == i5 && i4 == this.mTopActivityLetterboxHeight) ? false : true;
        if (this.mHasUserDoubleTapped || z2 != this.mIsActivityLetterboxed || i != this.mLetterboxVerticalPosition || i2 != this.mLetterboxHorizontalPosition || i3 != i5 || i4 != this.mTopActivityLetterboxHeight) {
            updateVisibilityOfViews();
        }
        return true;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public void updateSurfacePosition() {
        if (this.mLayout == null || this.mLeash == null) {
            return;
        }
        int i = 0;
        this.mSyncQueue.runInSync(new CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0(this, i, i));
    }

    /* JADX WARN: Type inference failed for: r12v1, types: [com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r12v3, types: [com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1] */
    public final void updateVisibilityOfViews() {
        int i;
        int i2;
        if (this.mLayout == null) {
            return;
        }
        TaskInfo taskInfo = this.mTaskInfo;
        final int i3 = 0;
        final int i4 = 2;
        final int i5 = 1;
        boolean z = !this.mCompatUIConfiguration.mCompatUISharedPreferences.getBoolean("has_seen_horizontal_reachability_education@" + taskInfo.userId, false) || (this.mHasUserDoubleTapped && ((i2 = this.mLetterboxHorizontalPosition) == 0 || i2 == 2));
        SharedPreferences sharedPreferences = this.mCompatUIConfiguration.mCompatUISharedPreferences;
        int i6 = taskInfo.userId;
        StringBuilder sb = new StringBuilder("has_seen_vertical_reachability_education@");
        sb.append(i6);
        boolean z2 = !sharedPreferences.getBoolean(sb.toString(), false) || (this.mHasUserDoubleTapped && ((i = this.mLetterboxVerticalPosition) == 0 || i == 2));
        if (!this.mIsActivityLetterboxed || (!z && !z2)) {
            this.mLayout.hideAllImmediately();
            return;
        }
        int width = getTaskBounds().width() - this.mTopActivityLetterboxWidth;
        int height = getTaskBounds().height() - this.mTopActivityLetterboxHeight;
        ReachabilityEduLayout reachabilityEduLayout = this.mLayout;
        int i7 = this.mLetterboxVerticalPosition;
        int i8 = this.mLetterboxHorizontalPosition;
        CompatUIConfiguration compatUIConfiguration = this.mCompatUIConfiguration;
        reachabilityEduLayout.hideAllImmediately();
        if (z && i8 != -1) {
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveUpButton);
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveDownButton);
            reachabilityEduLayout.mLastTopMargin = -1;
            reachabilityEduLayout.mLastBottomMargin = -1;
            int i9 = width / 2;
            int i10 = i8 * i9;
            int i11 = width - i10;
            if (i10 >= reachabilityEduLayout.mMoveLeftButton.getMeasuredWidth()) {
                int measuredWidth = (i9 - reachabilityEduLayout.mMoveLeftButton.getMeasuredWidth()) / 2;
                if (reachabilityEduLayout.mLastLeftMargin == -1) {
                    reachabilityEduLayout.mLastLeftMargin = measuredWidth;
                }
                int i12 = reachabilityEduLayout.mLastLeftMargin;
                if (i12 != measuredWidth) {
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveLeftButton, new ReachabilityEduLayout$$ExternalSyntheticLambda0(2), new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            switch (i4) {
                                case 0:
                                    int i13 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).topMargin = ((Integer) obj2).intValue();
                                    break;
                                case 1:
                                    int i14 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).bottomMargin = ((Integer) obj2).intValue();
                                    break;
                                case 2:
                                    int i15 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).leftMargin = ((Integer) obj2).intValue();
                                    break;
                                default:
                                    int i16 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).rightMargin = ((Integer) obj2).intValue();
                                    break;
                            }
                        }
                    }, i12, measuredWidth).start();
                } else {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) reachabilityEduLayout.mMoveLeftButton.getLayoutParams();
                    layoutParams.leftMargin = reachabilityEduLayout.mLastLeftMargin;
                    reachabilityEduLayout.mMoveLeftButton.setLayoutParams(layoutParams);
                }
                reachabilityEduLayout.showItem(reachabilityEduLayout.mMoveLeftButton);
            } else {
                reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveLeftButton);
                reachabilityEduLayout.mLastLeftMargin = -1;
            }
            if (i11 >= reachabilityEduLayout.mMoveRightButton.getMeasuredWidth()) {
                int measuredWidth2 = (i9 - reachabilityEduLayout.mMoveRightButton.getMeasuredWidth()) / 2;
                if (reachabilityEduLayout.mLastRightMargin == -1) {
                    reachabilityEduLayout.mLastRightMargin = measuredWidth2;
                }
                int i13 = reachabilityEduLayout.mLastRightMargin;
                if (i13 != measuredWidth2) {
                    final int i14 = 3;
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveRightButton, new ReachabilityEduLayout$$ExternalSyntheticLambda0(3), new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            switch (i14) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).topMargin = ((Integer) obj2).intValue();
                                    break;
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).bottomMargin = ((Integer) obj2).intValue();
                                    break;
                                case 2:
                                    int i15 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).leftMargin = ((Integer) obj2).intValue();
                                    break;
                                default:
                                    int i16 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).rightMargin = ((Integer) obj2).intValue();
                                    break;
                            }
                        }
                    }, i13, measuredWidth2).start();
                } else {
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) reachabilityEduLayout.mMoveRightButton.getLayoutParams();
                    layoutParams2.rightMargin = reachabilityEduLayout.mLastRightMargin;
                    reachabilityEduLayout.mMoveRightButton.setLayoutParams(layoutParams2);
                }
                reachabilityEduLayout.showItem(reachabilityEduLayout.mMoveRightButton);
            } else {
                reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveRightButton);
                reachabilityEduLayout.mLastRightMargin = -1;
            }
            compatUIConfiguration.mCompatUISharedPreferences.edit().putBoolean("has_seen_horizontal_reachability_education@" + taskInfo.userId, true).apply();
        } else if (z2 && i7 != -1) {
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveLeftButton);
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveRightButton);
            reachabilityEduLayout.mLastLeftMargin = -1;
            reachabilityEduLayout.mLastRightMargin = -1;
            int i15 = height / 2;
            int i16 = i7 * i15;
            int i17 = height - i16;
            if (i16 >= reachabilityEduLayout.mMoveUpButton.getMeasuredHeight()) {
                int measuredHeight = (i15 - reachabilityEduLayout.mMoveUpButton.getMeasuredHeight()) / 2;
                if (reachabilityEduLayout.mLastTopMargin == -1) {
                    reachabilityEduLayout.mLastTopMargin = measuredHeight;
                }
                int i18 = reachabilityEduLayout.mLastTopMargin;
                if (i18 != measuredHeight) {
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveUpButton, new ReachabilityEduLayout$$ExternalSyntheticLambda0(0), new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            switch (i3) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).topMargin = ((Integer) obj2).intValue();
                                    break;
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).bottomMargin = ((Integer) obj2).intValue();
                                    break;
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).leftMargin = ((Integer) obj2).intValue();
                                    break;
                                default:
                                    int i162 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).rightMargin = ((Integer) obj2).intValue();
                                    break;
                            }
                        }
                    }, i18, measuredHeight).start();
                } else {
                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) reachabilityEduLayout.mMoveUpButton.getLayoutParams();
                    layoutParams3.topMargin = reachabilityEduLayout.mLastTopMargin;
                    reachabilityEduLayout.mMoveUpButton.setLayoutParams(layoutParams3);
                }
                reachabilityEduLayout.showItem(reachabilityEduLayout.mMoveUpButton);
            } else {
                reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveUpButton);
                reachabilityEduLayout.mLastTopMargin = -1;
            }
            if (i17 >= reachabilityEduLayout.mMoveDownButton.getMeasuredHeight()) {
                int measuredHeight2 = (i15 - reachabilityEduLayout.mMoveDownButton.getMeasuredHeight()) / 2;
                if (reachabilityEduLayout.mLastBottomMargin == -1) {
                    reachabilityEduLayout.mLastBottomMargin = measuredHeight2;
                }
                int i19 = reachabilityEduLayout.mLastBottomMargin;
                if (i19 != measuredHeight2) {
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveDownButton, new ReachabilityEduLayout$$ExternalSyntheticLambda0(1), new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            switch (i5) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).topMargin = ((Integer) obj2).intValue();
                                    break;
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).bottomMargin = ((Integer) obj2).intValue();
                                    break;
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).leftMargin = ((Integer) obj2).intValue();
                                    break;
                                default:
                                    int i162 = ReachabilityEduLayout.$r8$clinit;
                                    ((FrameLayout.LayoutParams) obj).rightMargin = ((Integer) obj2).intValue();
                                    break;
                            }
                        }
                    }, i19, measuredHeight2).start();
                } else {
                    FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) reachabilityEduLayout.mMoveDownButton.getLayoutParams();
                    layoutParams4.bottomMargin = reachabilityEduLayout.mLastBottomMargin;
                    reachabilityEduLayout.mMoveDownButton.setLayoutParams(layoutParams4);
                }
                reachabilityEduLayout.showItem(reachabilityEduLayout.mMoveDownButton);
            } else {
                reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveDownButton);
                reachabilityEduLayout.mLastBottomMargin = -1;
            }
            compatUIConfiguration.mCompatUISharedPreferences.edit().putBoolean("has_seen_vertical_reachability_education@" + taskInfo.userId, true).apply();
        }
        if (!this.mHasLetterboxSizeChanged) {
            this.mNextHideTime = SystemClock.uptimeMillis() + 4000;
            ((HandlerExecutor) this.mMainExecutor).executeDelayed(4000L, new Runnable() { // from class: com.android.wm.shell.compatui.ReachabilityEduWindowManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ReachabilityEduWindowManager reachabilityEduWindowManager = ReachabilityEduWindowManager.this;
                    if (reachabilityEduWindowManager.mLayout != null) {
                        if (SystemClock.uptimeMillis() >= reachabilityEduWindowManager.mNextHideTime) {
                            reachabilityEduWindowManager.mLayout.hideAllImmediately();
                        }
                    }
                }
            });
        }
        this.mHasUserDoubleTapped = false;
    }
}
