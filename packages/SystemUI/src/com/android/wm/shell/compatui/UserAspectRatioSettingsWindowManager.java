package com.android.wm.shell.compatui;

import android.app.AppCompatTaskInfo;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIController;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class UserAspectRatioSettingsWindowManager extends CompatUIWindowManagerAbstract {
    final CompatUIController.CompatUIHintsState mCompatUIHintsState;
    public final Function mDisappearTimeSupplier;
    boolean mHasUserAspectRatioSettingsButton;
    public UserAspectRatioSettingsLayout mLayout;
    public final Rect mLayoutBounds;
    public long mNextButtonHideTimeMs;
    public final BiConsumer mOnButtonClicked;
    public final ShellExecutor mShellExecutor;
    public final Supplier mUserAspectRatioButtonShownChecker;
    public final Consumer mUserAspectRatioButtonStateConsumer;

    public static void $r8$lambda$mgsdsccZUsXLSfO5dZyQ22DCT6g(UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager) {
        if (userAspectRatioSettingsWindowManager.mLayout != null) {
            if (SystemClock.uptimeMillis() >= userAspectRatioSettingsWindowManager.mNextButtonHideTimeMs) {
                UserAspectRatioSettingsLayout userAspectRatioSettingsLayout = userAspectRatioSettingsWindowManager.mLayout;
                userAspectRatioSettingsLayout.setViewVisibility(R.id.user_aspect_ratio_settings_button, false);
                userAspectRatioSettingsLayout.setViewVisibility(R.id.user_aspect_ratio_settings_hint, false);
            }
        }
    }

    /* renamed from: $r8$lambda$qcC5U0asMBEasgAnScT0e3j-tE0, reason: not valid java name */
    public static void m3227$r8$lambda$qcC5U0asMBEasgAnScT0e3jtE0(UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager) {
        UserAspectRatioSettingsLayout userAspectRatioSettingsLayout = userAspectRatioSettingsWindowManager.mLayout;
        if (userAspectRatioSettingsLayout == null) {
            return;
        }
        userAspectRatioSettingsLayout.setViewVisibility(R.id.user_aspect_ratio_settings_button, true);
        userAspectRatioSettingsWindowManager.mUserAspectRatioButtonStateConsumer.accept(Boolean.TRUE);
        if (userAspectRatioSettingsWindowManager.mCompatUIHintsState.mHasShownUserAspectRatioSettingsButtonHint) {
            return;
        }
        userAspectRatioSettingsWindowManager.mLayout.setViewVisibility(R.id.user_aspect_ratio_settings_hint, true);
        userAspectRatioSettingsWindowManager.mCompatUIHintsState.mHasShownUserAspectRatioSettingsButtonHint = true;
    }

    public UserAspectRatioSettingsWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIController.CompatUIHintsState compatUIHintsState, BiConsumer<TaskInfo, ShellTaskOrganizer.TaskListener> biConsumer, ShellExecutor shellExecutor, Function<Integer, Integer> function, Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mNextButtonHideTimeMs = -1L;
        this.mLayoutBounds = new Rect();
        this.mShellExecutor = shellExecutor;
        this.mUserAspectRatioButtonShownChecker = supplier;
        this.mUserAspectRatioButtonStateConsumer = consumer;
        this.mHasUserAspectRatioSettingsButton = shouldShowUserAspectRatioSettingsButton(taskInfo.appCompatTaskInfo, taskInfo.baseIntent);
        this.mCompatUIHintsState = compatUIHintsState;
        this.mOnButtonClicked = biConsumer;
        this.mDisappearTimeSupplier = function;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        UserAspectRatioSettingsLayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        inflateLayout.mWindowManager = this;
        updateVisibilityOfViews();
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        return this.mHasUserAspectRatioSettingsButton;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public UserAspectRatioSettingsLayout inflateLayout() {
        return (UserAspectRatioSettingsLayout) LayoutInflater.from(this.mContext).inflate(R.layout.user_aspect_ratio_settings_layout, (ViewGroup) null);
    }

    public boolean isShowingButton() {
        if (((Boolean) this.mUserAspectRatioButtonShownChecker.get()).booleanValue()) {
            return SystemClock.uptimeMillis() < this.mNextButtonHideTimeMs;
        }
        return false;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayoutBounds.setEmpty();
        this.mLayout = null;
    }

    public final boolean shouldShowUserAspectRatioSettingsButton(AppCompatTaskInfo appCompatTaskInfo, Intent intent) {
        Rect taskStableBounds = getTaskStableBounds();
        return (taskStableBounds.height() > appCompatTaskInfo.topActivityLetterboxHeight || taskStableBounds.width() > appCompatTaskInfo.topActivityLetterboxWidth || appCompatTaskInfo.isUserFullscreenOverrideEnabled()) && appCompatTaskInfo.eligibleForUserAspectRatioButton() && (appCompatTaskInfo.isTopActivityLetterboxed() || appCompatTaskInfo.isUserFullscreenOverrideEnabled()) && !appCompatTaskInfo.isSystemFullscreenOverrideEnabled() && "android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.LAUNCHER") && (!((Boolean) this.mUserAspectRatioButtonShownChecker.get()).booleanValue() || isShowingButton());
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        boolean z2 = this.mHasUserAspectRatioSettingsButton;
        this.mHasUserAspectRatioSettingsButton = shouldShowUserAspectRatioSettingsButton(taskInfo.appCompatTaskInfo, taskInfo.baseIntent);
        if (!super.updateCompatInfo(taskInfo, taskListener, z)) {
            return false;
        }
        if (z2 == this.mHasUserAspectRatioSettingsButton) {
            return true;
        }
        updateVisibilityOfViews();
        return true;
    }

    public final void updateLayoutBounds$1() {
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
        updateLayoutBounds$1();
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

    public void updateVisibilityOfViews() {
        if (!this.mHasUserAspectRatioSettingsButton) {
            ((HandlerExecutor) this.mShellExecutor).removeCallbacks(new UserAspectRatioSettingsWindowManager$$ExternalSyntheticLambda0(this, 0));
            this.mShellExecutor.execute(new UserAspectRatioSettingsWindowManager$$ExternalSyntheticLambda0(this, 1));
            return;
        }
        ((HandlerExecutor) this.mShellExecutor).executeDelayed(new UserAspectRatioSettingsWindowManager$$ExternalSyntheticLambda0(this, 0), 500L);
        long intValue = ((Integer) this.mDisappearTimeSupplier.apply(4)).intValue();
        this.mNextButtonHideTimeMs = SystemClock.uptimeMillis() + intValue;
        ((HandlerExecutor) this.mShellExecutor).executeDelayed(new UserAspectRatioSettingsWindowManager$$ExternalSyntheticLambda0(this, 1), intValue);
    }

    public void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        updateLayoutBounds$1();
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
