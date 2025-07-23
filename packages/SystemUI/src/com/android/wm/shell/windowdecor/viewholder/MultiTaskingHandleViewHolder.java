package com.android.wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.windowdecor.AppHandleAnimator;
import com.android.wm.shell.windowdecor.CaptionGlobalState;
import com.android.wm.shell.windowdecor.HandleHideAnimator;
import com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0;
import com.android.wm.shell.windowdecor.WindowManagerWrapper;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiTaskingHandleViewHolder extends WindowDecorationViewHolder {
    public final AppHandleAnimator animator;
    public final ImageButton captionHandle;
    public final View captionView;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final HandleHideAnimator handleHideAnimator;
    public final Rect handleInputBounds;
    public boolean handleTouchEnabled;
    public final Handler handler;
    public final InputManager inputManager;
    public boolean isKeepScreenOn;
    public final MultiWindowManager multiWindowManager;
    public AdditionalSystemViewContainer statusBarInputLayer;
    public boolean statusBarInputLayerExists;
    public boolean statusBarVisibility;
    public ActivityManager.RunningTaskInfo taskInfo;
    public final UiModeManager uiModeManager;
    public final WindowManagerWrapper windowManagerWrapper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HandleData extends WindowDecorationViewHolder.Data {
        public final int height;
        public final boolean isCaptionVisible;
        public final boolean isDesktopModeSupportedOnDisplay;
        public final boolean isStatusBarVisible;
        public final Point position;
        public final boolean showInputLayer;
        public final ActivityManager.RunningTaskInfo taskInfo;
        public final int width;

        public HandleData(ActivityManager.RunningTaskInfo runningTaskInfo, Point point, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4) {
            this.taskInfo = runningTaskInfo;
            this.position = point;
            this.width = i;
            this.height = i2;
            this.showInputLayer = z;
            this.isCaptionVisible = z2;
            this.isStatusBarVisible = z3;
            this.isDesktopModeSupportedOnDisplay = z4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HandleData)) {
                return false;
            }
            HandleData handleData = (HandleData) obj;
            return Intrinsics.areEqual(this.taskInfo, handleData.taskInfo) && Intrinsics.areEqual(this.position, handleData.position) && this.width == handleData.width && this.height == handleData.height && this.showInputLayer == handleData.showInputLayer && this.isCaptionVisible == handleData.isCaptionVisible && this.isStatusBarVisible == handleData.isStatusBarVisible && this.isDesktopModeSupportedOnDisplay == handleData.isDesktopModeSupportedOnDisplay;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isDesktopModeSupportedOnDisplay) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.height, ReorderTile$$ExternalSyntheticOutline0.m(this.width, (this.position.hashCode() + (this.taskInfo.hashCode() * 31)) * 31, 31), 31), 31, this.showInputLayer), 31, this.isCaptionVisible), 31, this.isStatusBarVisible);
        }

        public final String toString() {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.taskInfo;
            Point point = this.position;
            StringBuilder sb = new StringBuilder("HandleData(taskInfo=");
            sb.append(runningTaskInfo);
            sb.append(", position=");
            sb.append(point);
            sb.append(", width=");
            sb.append(this.width);
            sb.append(", height=");
            sb.append(this.height);
            sb.append(", showInputLayer=");
            sb.append(this.showInputLayer);
            sb.append(", isCaptionVisible=");
            sb.append(this.isCaptionVisible);
            sb.append(", isStatusBarVisible=");
            sb.append(this.isStatusBarVisible);
            sb.append(", isDesktopModeSupportedOnDisplay=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isDesktopModeSupportedOnDisplay, ")");
        }
    }

    public MultiTaskingHandleViewHolder(View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener, WindowManagerWrapper windowManagerWrapper, Handler handler, DesktopModeUiEventLogger desktopModeUiEventLogger) {
        super(view);
        this.windowManagerWrapper = windowManagerWrapper;
        this.handler = handler;
        this.desktopModeUiEventLogger = desktopModeUiEventLogger;
        View requireViewById = view.requireViewById(R.id.desktop_mode_caption);
        this.captionView = requireViewById;
        ImageButton imageButton = (ImageButton) view.requireViewById(R.id.caption_handle);
        this.captionHandle = imageButton;
        this.inputManager = (InputManager) this.context.getSystemService(InputManager.class);
        this.animator = new AppHandleAnimator(view, imageButton);
        this.multiWindowManager = MultiWindowManager.getInstance();
        this.handleTouchEnabled = true;
        this.statusBarVisibility = true;
        this.handleInputBounds = new Rect();
        this.handleHideAnimator = new HandleHideAnimator(handler, requireViewById);
        this.uiModeManager = (UiModeManager) this.context.getSystemService("uimode");
        requireViewById.setOnTouchListener(onTouchListener);
        imageButton.setOnTouchListener(onTouchListener);
        imageButton.setOnClickListener(onClickListener);
        imageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder.1
            @Override // android.view.View.AccessibilityDelegate
            public final void sendAccessibilityEvent(View view2, int i) {
                View view3;
                if (i != 128 && i != 256) {
                    super.sendAccessibilityEvent(view2, i);
                    return;
                }
                if (CoreRune.MW_CAPTION_HANDLE && !MultiTaskingHandleViewHolder.this.statusBarInputLayerExists) {
                    super.sendAccessibilityEvent(view2, i);
                    return;
                }
                AdditionalSystemViewContainer additionalSystemViewContainer = MultiTaskingHandleViewHolder.this.statusBarInputLayer;
                if (additionalSystemViewContainer == null || (view3 = additionalSystemViewContainer.view) == null) {
                    return;
                }
                view3.sendAccessibilityEvent(i);
            }
        });
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.animator.cancel();
        if (CoreRune.MW_CAPTION_HANDLE_ANIM) {
            HandleHideAnimator handleHideAnimator = this.handleHideAnimator;
            handleHideAnimator.cancelAllHandleAnim();
            handleHideAnimator.mHandleView.setVisibility(0);
            handleHideAnimator.mIsHandleViewVisible = true;
            handleHideAnimator.mHandleView.setAlpha(1.0f);
        }
    }

    public final void disposeStatusBarInputLayer() {
        View view;
        if (this.statusBarInputLayerExists) {
            this.statusBarInputLayerExists = false;
            AdditionalSystemViewContainer additionalSystemViewContainer = this.statusBarInputLayer;
            if (additionalSystemViewContainer != null && (view = additionalSystemViewContainer.view) != null) {
                view.setOnTouchListener(null);
            }
            this.handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder$disposeStatusBarInputLayer$1
                @Override // java.lang.Runnable
                public final void run() {
                    AdditionalSystemViewContainer additionalSystemViewContainer2 = MultiTaskingHandleViewHolder.this.statusBarInputLayer;
                    if (additionalSystemViewContainer2 != null) {
                        additionalSystemViewContainer2.releaseView();
                    }
                    MultiTaskingHandleViewHolder.this.statusBarInputLayer = null;
                }
            });
        }
    }

    public final int getCaptionHandleColor(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        if (!CoreRune.MW_CAPTION_HANDLE) {
            ActivityManager.TaskDescription taskDescription = runningTaskInfo.taskDescription;
            return (taskDescription == null || (Color.alpha(taskDescription.getStatusBarColor()) == 0 || runningTaskInfo.getWindowingMode() != 5 ? (taskDescription.getSystemBarsAppearance() & 8) != 0 : ((double) Color.valueOf(taskDescription.getStatusBarColor()).luminance()) >= 0.5d)) ? this.context.getColor(R.color.desktop_mode_caption_handle_bar_dark) : this.context.getColor(R.color.desktop_mode_caption_handle_bar_light);
        }
        boolean isNightModeActive = runningTaskInfo.configuration.isNightModeActive();
        ComponentName componentName = runningTaskInfo.realActivity;
        UiModeManager uiModeManager = this.uiModeManager;
        if (uiModeManager != null && componentName != null) {
            isNightModeActive = isNightModeActive || (uiModeManager.getPackageNightMode(componentName.getPackageName()) == 32);
        }
        if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() != 1 || this.multiWindowManager.getMultiWindowModeStates(0) == 1)) {
            return CaptionGlobalState.COLOR_THEME_ENABLED ? isNightModeActive ? this.context.getColor(17171428) : this.context.getColor(17171426) : this.context.getColor(R.color.mw_handle_color_focused);
        }
        if (CoreRune.MW_CAPTION_DESKTOP && runningTaskInfo.getWindowingMode() == 1 && z) {
            if (isNightModeActive) {
                this.context.getColor(R.color.mw_caption_desktop_full_screen_handle_color_dark);
            } else {
                this.context.getColor(R.color.mw_caption_desktop_full_screen_handle_color_light);
            }
        }
        return this.context.getColor(R.color.mw_handle_color_unfocused);
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuClosed() {
        if (!CoreRune.MW_CAPTION_HANDLE) {
            this.animator.animateCaptionHandleAlpha(0.0f, 1.0f);
            return;
        }
        HandleHideAnimator handleHideAnimator = this.handleHideAnimator;
        handleHideAnimator.cancelAllHandleAnim();
        handleHideAnimator.show(handleHideAnimator.mIsHandleHideEnabled ? new HandleHideAnimator$$ExternalSyntheticLambda0(handleHideAnimator, 1) : null, true);
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuOpened() {
        if (!CoreRune.MW_CAPTION_HANDLE) {
            this.animator.animateCaptionHandleAlpha(1.0f, 0.0f);
            return;
        }
        HandleHideAnimator handleHideAnimator = this.handleHideAnimator;
        handleHideAnimator.mIsHandleMenuActive = true;
        handleHideAnimator.cancelAllHandleAnim();
        handleHideAnimator.hide(true);
    }

    public final boolean updateHandleInputBounds(Point point, int i, int i2) {
        Rect rect = this.handleInputBounds;
        if (rect.left == point.x && rect.top == point.y && rect.width() == i && this.handleInputBounds.height() == i2) {
            return false;
        }
        Rect rect2 = this.handleInputBounds;
        int i3 = point.x;
        int i4 = point.y;
        rect2.set(i3, i4, i + i3, i2 + i4);
        return true;
    }
}
