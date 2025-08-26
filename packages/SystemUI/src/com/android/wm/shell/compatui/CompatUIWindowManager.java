package com.android.wm.shell.compatui;

import android.app.AppCompatTaskInfo;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.window.DesktopModeFlags;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class CompatUIWindowManager extends CompatUIWindowManagerAbstract {
    public final Consumer mCallback;
    public final CompatUIConfiguration mCompatUIConfiguration;
    CompatUIController.CompatUIHintsState mCompatUIHintsState;
    public final DesktopState mDesktopState;
    boolean mHasSizeCompat;
    public final float mHideScmTolerance;
    CompatUILayout mLayout;
    public final Rect mLayoutBounds;
    public final Consumer mOnRestartButtonClicked;

    public CompatUIWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, Consumer<CompatUIEvents> consumer, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIController.CompatUIHintsState compatUIHintsState, CompatUIConfiguration compatUIConfiguration, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer2, DesktopState desktopState) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mLayoutBounds = new Rect();
        this.mCallback = consumer;
        this.mHasSizeCompat = taskInfo.appCompatTaskInfo.isTopActivityInSizeCompat();
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode && DesktopModeFlags.ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS.isTrue()) {
            this.mHasSizeCompat &= !taskInfo.isFreeform();
        }
        this.mCompatUIHintsState = compatUIHintsState;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mOnRestartButtonClicked = consumer2;
        this.mHideScmTolerance = compatUIConfiguration.mHideSizeCompatRestartButtonTolerance;
        this.mDesktopState = desktopState;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        CompatUILayout compatUILayoutInflateLayout = inflateLayout();
        this.mLayout = compatUILayoutInflateLayout;
        compatUILayoutInflateLayout.mWindowManager = this;
        updateVisibilityOfViews$1();
        if (this.mHasSizeCompat) {
            this.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonAppeared(this.mTaskId));
        }
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        return this.mHasSizeCompat && shouldShowSizeCompatRestartButton(this.mTaskInfo);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public CompatUILayout inflateLayout() {
        return (CompatUILayout) LayoutInflater.from(this.mContext).inflate(R.layout.compat_ui_layout, (ViewGroup) null);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayoutBounds.setEmpty();
        this.mLayout = null;
    }

    public boolean shouldShowSizeCompatRestartButton(TaskInfo taskInfo) {
        if (taskInfo.configuration.smallestScreenWidthDp < 600) {
            return true;
        }
        AppCompatTaskInfo appCompatTaskInfo = taskInfo.appCompatTaskInfo;
        int i = appCompatTaskInfo.topActivityLetterboxWidth;
        int i2 = appCompatTaskInfo.topActivityLetterboxHeight;
        Rect taskStableBounds = getTaskStableBounds();
        int iWidth = taskStableBounds.width();
        int iHeight = taskStableBounds.height();
        if (iWidth > i && iHeight > i2) {
            return true;
        }
        float f = this.mHideScmTolerance;
        this.mCompatUIConfiguration.getClass();
        if (f != 100 && iWidth == i) {
            return false;
        }
        int i3 = i * i2;
        int i4 = iWidth * iHeight;
        return (i3 == 0 || i4 == 0 || (((float) i3) / ((float) i4)) * 100.0f >= this.mHideScmTolerance) ? false : true;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        boolean z2 = this.mHasSizeCompat;
        this.mHasSizeCompat = taskInfo.appCompatTaskInfo.isTopActivityInSizeCompat();
        if (((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode && DesktopModeFlags.ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS.isTrue()) {
            this.mHasSizeCompat &= !taskInfo.isFreeform();
        }
        if (!super.updateCompatInfo(taskInfo, taskListener, z)) {
            return false;
        }
        if (z2 != this.mHasSizeCompat) {
            updateVisibilityOfViews$1();
        }
        return true;
    }

    public final void updateLayoutBounds() {
        if (this.mLayout == null) {
            this.mLayoutBounds.setEmpty();
            return;
        }
        Rect taskBounds = getTaskBounds();
        Rect taskStableBounds = getTaskStableBounds();
        int measuredWidth = this.mLayout.getMeasuredWidth();
        int measuredHeight = this.mLayout.getMeasuredHeight();
        int i = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1 ? taskStableBounds.left - taskBounds.left : (taskStableBounds.right - taskBounds.left) - measuredWidth;
        int i2 = (taskStableBounds.bottom - taskBounds.top) - measuredHeight;
        this.mLayoutBounds.set(i, i2, measuredWidth + i, measuredHeight + i2);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public void updateSurfacePosition() {
        updateLayoutBounds();
        if (this.mLayoutBounds.isEmpty()) {
            return;
        }
        Rect rect = this.mLayoutBounds;
        int i = rect.left;
        int i2 = rect.top;
        if (this.mLeash == null) {
            return;
        }
        this.mSyncQueue.runInSync(new CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0(this, i, i2));
    }

    public final void updateVisibilityOfViews$1() {
        CompatUILayout compatUILayout = this.mLayout;
        if (compatUILayout == null) {
            return;
        }
        boolean z = this.mHasSizeCompat;
        View viewFindViewById = compatUILayout.findViewById(R.id.size_compat_restart_button);
        int i = z ? 0 : 8;
        if (viewFindViewById.getVisibility() != i) {
            viewFindViewById.setVisibility(i);
        }
        if (!z) {
            View viewFindViewById2 = compatUILayout.findViewById(R.id.size_compat_hint);
            if (viewFindViewById2.getVisibility() != 8) {
                viewFindViewById2.setVisibility(8);
            }
        }
        if (!this.mHasSizeCompat || this.mCompatUIHintsState.mHasShownSizeCompatHint) {
            return;
        }
        View viewFindViewById3 = this.mLayout.findViewById(R.id.size_compat_hint);
        if (viewFindViewById3.getVisibility() != 0) {
            viewFindViewById3.setVisibility(0);
        }
        this.mCompatUIHintsState.mHasShownSizeCompatHint = true;
    }

    public void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        updateLayoutBounds();
        if (this.mLayoutBounds.isEmpty()) {
            return;
        }
        Rect rect = this.mLayoutBounds;
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null) {
            return;
        }
        transaction.setPosition(surfaceControl, rect.left, rect.top).setWindowCrop(this.mLeash, rect.width(), rect.height());
    }
}
