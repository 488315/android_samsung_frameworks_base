package com.android.wm.shell.compatui;

import android.app.AppCompatTaskInfo;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;

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
        ReachabilityEduLayout reachabilityEduLayoutInflateLayout = inflateLayout();
        this.mLayout = reachabilityEduLayoutInflateLayout;
        reachabilityEduLayoutInflateLayout.getClass();
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

    /* JADX WARN: Removed duplicated region for block: B:87:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVisibilityOfViews$2() {
        int i;
        int i2;
        int i3;
        int i4;
        if (this.mLayout == null) {
            return;
        }
        TaskInfo taskInfo = this.mTaskInfo;
        boolean z = this.mCompatUIConfiguration.mCompatUISharedPreferences.getBoolean("has_seen_horizontal_reachability_education@" + taskInfo.userId, false);
        boolean z2 = this.mCompatUIConfiguration.mCompatUISharedPreferences.getBoolean("has_seen_vertical_reachability_education@" + taskInfo.userId, false);
        boolean z3 = !z || (this.mHasUserDoubleTapped && ((i4 = this.mLetterboxHorizontalPosition) == 0 || i4 == 2));
        boolean z4 = !z2 || (this.mHasUserDoubleTapped && ((i3 = this.mLetterboxVerticalPosition) == 0 || i3 == 2));
        if (!this.mIsLetterboxDoubleTapEnabled || (!z3 && !z4)) {
            this.mLayout.hideAllImmediately();
            return;
        }
        int iWidth = getTaskBounds().width() - this.mTopActivityLetterboxWidth;
        int iHeight = getTaskBounds().height() - this.mTopActivityLetterboxHeight;
        ReachabilityEduLayout reachabilityEduLayout = this.mLayout;
        int i5 = this.mLetterboxVerticalPosition;
        int i6 = this.mLetterboxHorizontalPosition;
        CompatUIConfiguration compatUIConfiguration = this.mCompatUIConfiguration;
        reachabilityEduLayout.hideAllImmediately();
        if (z3 && i6 != -1) {
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveUpButton);
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveDownButton);
            reachabilityEduLayout.mLastTopMargin = -1;
            reachabilityEduLayout.mLastBottomMargin = -1;
            int i7 = iWidth / 2;
            int i8 = i6 * i7;
            int i9 = iWidth - i8;
            if (i8 >= reachabilityEduLayout.mMoveLeftButton.getMeasuredWidth()) {
                int measuredWidth = (i7 - reachabilityEduLayout.mMoveLeftButton.getMeasuredWidth()) / 2;
                if (reachabilityEduLayout.mLastLeftMargin == -1) {
                    reachabilityEduLayout.mLastLeftMargin = measuredWidth;
                }
                int i10 = reachabilityEduLayout.mLastLeftMargin;
                if (i10 != measuredWidth) {
                    final int i11 = 2;
                    final int i12 = 2;
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveLeftButton, new Function() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) obj;
                            switch (i11) {
                                case 0:
                                    int i13 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams.topMargin);
                                case 1:
                                    int i14 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams.bottomMargin);
                                case 2:
                                    int i15 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams.leftMargin);
                                default:
                                    int i16 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams.rightMargin);
                            }
                        }
                    }, new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) obj;
                            Integer num = (Integer) obj2;
                            switch (i12) {
                                case 0:
                                    int i13 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams.topMargin = num.intValue();
                                    break;
                                case 1:
                                    int i14 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams.bottomMargin = num.intValue();
                                    break;
                                case 2:
                                    int i15 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams.leftMargin = num.intValue();
                                    break;
                                default:
                                    int i16 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams.rightMargin = num.intValue();
                                    break;
                            }
                        }
                    }, i10, measuredWidth).start();
                } else {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) reachabilityEduLayout.mMoveLeftButton.getLayoutParams();
                    layoutParams.leftMargin = reachabilityEduLayout.mLastLeftMargin;
                    reachabilityEduLayout.mMoveLeftButton.setLayoutParams(layoutParams);
                }
                reachabilityEduLayout.showItem(reachabilityEduLayout.mMoveLeftButton);
                i2 = -1;
            } else {
                reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveLeftButton);
                i2 = -1;
                reachabilityEduLayout.mLastLeftMargin = -1;
            }
            if (i9 >= reachabilityEduLayout.mMoveRightButton.getMeasuredWidth()) {
                int measuredWidth2 = (i7 - reachabilityEduLayout.mMoveRightButton.getMeasuredWidth()) / 2;
                if (reachabilityEduLayout.mLastRightMargin == i2) {
                    reachabilityEduLayout.mLastRightMargin = measuredWidth2;
                }
                int i13 = reachabilityEduLayout.mLastRightMargin;
                if (i13 != measuredWidth2) {
                    final int i14 = 3;
                    final int i15 = 3;
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveRightButton, new Function() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) obj;
                            switch (i14) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams2.topMargin);
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams2.bottomMargin);
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams2.leftMargin);
                                default:
                                    int i16 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams2.rightMargin);
                            }
                        }
                    }, new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) obj;
                            Integer num = (Integer) obj2;
                            switch (i15) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams2.topMargin = num.intValue();
                                    break;
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams2.bottomMargin = num.intValue();
                                    break;
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams2.leftMargin = num.intValue();
                                    break;
                                default:
                                    int i16 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams2.rightMargin = num.intValue();
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
        } else if (z4 && i5 != -1) {
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveLeftButton);
            reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveRightButton);
            reachabilityEduLayout.mLastLeftMargin = -1;
            reachabilityEduLayout.mLastRightMargin = -1;
            int i16 = iHeight / 2;
            int i17 = i5 * i16;
            int i18 = iHeight - i17;
            if (i17 >= reachabilityEduLayout.mMoveUpButton.getMeasuredHeight()) {
                int measuredHeight = (i16 - reachabilityEduLayout.mMoveUpButton.getMeasuredHeight()) / 2;
                if (reachabilityEduLayout.mLastTopMargin == -1) {
                    reachabilityEduLayout.mLastTopMargin = measuredHeight;
                }
                int i19 = reachabilityEduLayout.mLastTopMargin;
                if (i19 != measuredHeight) {
                    final int i20 = 0;
                    final int i21 = 0;
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveUpButton, new Function() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            FrameLayout.LayoutParams layoutParams22 = (FrameLayout.LayoutParams) obj;
                            switch (i20) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.topMargin);
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.bottomMargin);
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.leftMargin);
                                default:
                                    int i162 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.rightMargin);
                            }
                        }
                    }, new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            FrameLayout.LayoutParams layoutParams22 = (FrameLayout.LayoutParams) obj;
                            Integer num = (Integer) obj2;
                            switch (i21) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.topMargin = num.intValue();
                                    break;
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.bottomMargin = num.intValue();
                                    break;
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.leftMargin = num.intValue();
                                    break;
                                default:
                                    int i162 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.rightMargin = num.intValue();
                                    break;
                            }
                        }
                    }, i19, measuredHeight).start();
                } else {
                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) reachabilityEduLayout.mMoveUpButton.getLayoutParams();
                    layoutParams3.topMargin = reachabilityEduLayout.mLastTopMargin;
                    reachabilityEduLayout.mMoveUpButton.setLayoutParams(layoutParams3);
                }
                reachabilityEduLayout.showItem(reachabilityEduLayout.mMoveUpButton);
                i = -1;
            } else {
                reachabilityEduLayout.hideItem(reachabilityEduLayout.mMoveUpButton);
                i = -1;
                reachabilityEduLayout.mLastTopMargin = -1;
            }
            if (i18 >= reachabilityEduLayout.mMoveDownButton.getMeasuredHeight()) {
                int measuredHeight2 = (i16 - reachabilityEduLayout.mMoveDownButton.getMeasuredHeight()) / 2;
                if (reachabilityEduLayout.mLastBottomMargin == i) {
                    reachabilityEduLayout.mLastBottomMargin = measuredHeight2;
                }
                int i22 = reachabilityEduLayout.mLastBottomMargin;
                if (i22 != measuredHeight2) {
                    final int i23 = 1;
                    final int i24 = 1;
                    ReachabilityEduLayout.marginAnimator(reachabilityEduLayout.mMoveDownButton, new Function() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            FrameLayout.LayoutParams layoutParams22 = (FrameLayout.LayoutParams) obj;
                            switch (i23) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.topMargin);
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.bottomMargin);
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.leftMargin);
                                default:
                                    int i162 = ReachabilityEduLayout.$r8$clinit;
                                    return Integer.valueOf(layoutParams22.rightMargin);
                            }
                        }
                    }, new BiConsumer() { // from class: com.android.wm.shell.compatui.ReachabilityEduLayout$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            FrameLayout.LayoutParams layoutParams22 = (FrameLayout.LayoutParams) obj;
                            Integer num = (Integer) obj2;
                            switch (i24) {
                                case 0:
                                    int i132 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.topMargin = num.intValue();
                                    break;
                                case 1:
                                    int i142 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.bottomMargin = num.intValue();
                                    break;
                                case 2:
                                    int i152 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.leftMargin = num.intValue();
                                    break;
                                default:
                                    int i162 = ReachabilityEduLayout.$r8$clinit;
                                    layoutParams22.rightMargin = num.intValue();
                                    break;
                            }
                        }
                    }, i22, measuredHeight2).start();
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
            this.mNextHideTime = ((Integer) this.mDisappearTimeSupplier.apply(3)).intValue() + SystemClock.uptimeMillis();
            long jIntValue = ((Integer) this.mDisappearTimeSupplier.apply(3)).intValue();
            final int i25 = 0;
            ((HandlerExecutor) this.mMainExecutor).executeDelayed(new Runnable(this) { // from class: com.android.wm.shell.compatui.ReachabilityEduWindowManager$$ExternalSyntheticLambda0
                public final /* synthetic */ ReachabilityEduWindowManager f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i26 = i25;
                    ReachabilityEduWindowManager reachabilityEduWindowManager = this.f$0;
                    switch (i26) {
                        case 0:
                            if (reachabilityEduWindowManager.mLayout != null && SystemClock.uptimeMillis() >= reachabilityEduWindowManager.mNextHideTime) {
                                reachabilityEduWindowManager.mLayout.hideAllImmediately();
                                break;
                            }
                            break;
                        default:
                            reachabilityEduWindowManager.mOnDismissCallback.accept(reachabilityEduWindowManager.mTaskInfo, reachabilityEduWindowManager.mTaskListener);
                            break;
                    }
                }
            }, jIntValue);
            if (!z) {
                CompatUIConfiguration compatUIConfiguration2 = this.mCompatUIConfiguration;
                TaskInfo taskInfo2 = this.mTaskInfo;
                if (compatUIConfiguration2.mCompatUISharedPreferences.getBoolean("has_seen_horizontal_reachability_education@" + taskInfo2.userId, false)) {
                    final int i26 = 1;
                    ((HandlerExecutor) this.mMainExecutor).executeDelayed(new Runnable(this) { // from class: com.android.wm.shell.compatui.ReachabilityEduWindowManager$$ExternalSyntheticLambda0
                        public final /* synthetic */ ReachabilityEduWindowManager f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i262 = i26;
                            ReachabilityEduWindowManager reachabilityEduWindowManager = this.f$0;
                            switch (i262) {
                                case 0:
                                    if (reachabilityEduWindowManager.mLayout != null && SystemClock.uptimeMillis() >= reachabilityEduWindowManager.mNextHideTime) {
                                        reachabilityEduWindowManager.mLayout.hideAllImmediately();
                                        break;
                                    }
                                    break;
                                default:
                                    reachabilityEduWindowManager.mOnDismissCallback.accept(reachabilityEduWindowManager.mTaskInfo, reachabilityEduWindowManager.mTaskListener);
                                    break;
                            }
                        }
                    }, jIntValue);
                } else if (!z2) {
                    CompatUIConfiguration compatUIConfiguration3 = this.mCompatUIConfiguration;
                    TaskInfo taskInfo3 = this.mTaskInfo;
                    if (compatUIConfiguration3.mCompatUISharedPreferences.getBoolean("has_seen_vertical_reachability_education@" + taskInfo3.userId, false)) {
                    }
                }
            }
        }
        this.mHasUserDoubleTapped = false;
    }
}
