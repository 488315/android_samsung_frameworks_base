package com.android.wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.windowdecor.AppHandleAnimator;
import com.android.wm.shell.windowdecor.WindowManagerWrapper;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AppHandleViewHolder extends WindowDecorationViewHolder {
    public final AppHandleAnimator animator;
    public final ImageButton captionHandle;
    public final View captionView;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final Handler handler;
    public final InputManager inputManager;
    public AdditionalSystemViewContainer statusBarInputLayer;
    public boolean statusBarInputLayerExists;
    public ActivityManager.RunningTaskInfo taskInfo;
    public final WindowManagerWrapper windowManagerWrapper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HandleData extends WindowDecorationViewHolder.Data {
        public final int height;
        public final boolean isCaptionVisible;
        public final Point position;
        public final boolean showInputLayer;
        public final ActivityManager.RunningTaskInfo taskInfo;
        public final int width;

        public HandleData(ActivityManager.RunningTaskInfo runningTaskInfo, Point point, int i, int i2, boolean z, boolean z2) {
            this.taskInfo = runningTaskInfo;
            this.position = point;
            this.width = i;
            this.height = i2;
            this.showInputLayer = z;
            this.isCaptionVisible = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HandleData)) {
                return false;
            }
            HandleData handleData = (HandleData) obj;
            return Intrinsics.areEqual(this.taskInfo, handleData.taskInfo) && Intrinsics.areEqual(this.position, handleData.position) && this.width == handleData.width && this.height == handleData.height && this.showInputLayer == handleData.showInputLayer && this.isCaptionVisible == handleData.isCaptionVisible;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isCaptionVisible) + TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.height, ReorderTile$$ExternalSyntheticOutline0.m(this.width, (this.position.hashCode() + (this.taskInfo.hashCode() * 31)) * 31, 31), 31), 31, this.showInputLayer);
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
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCaptionVisible, ")");
        }
    }

    public AppHandleViewHolder(View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener, WindowManagerWrapper windowManagerWrapper, Handler handler, DesktopModeUiEventLogger desktopModeUiEventLogger) {
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
        requireViewById.setOnTouchListener(onTouchListener);
        imageButton.setOnTouchListener(onTouchListener);
        imageButton.setOnClickListener(onClickListener);
        imageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder.1
            @Override // android.view.View.AccessibilityDelegate
            public final void sendAccessibilityEvent(View view2, int i) {
                View view3;
                if (i != 128 && i != 256) {
                    super.sendAccessibilityEvent(view2, i);
                    return;
                }
                AdditionalSystemViewContainer additionalSystemViewContainer = AppHandleViewHolder.this.statusBarInputLayer;
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
    }

    public final void disposeStatusBarInputLayer() {
        View view;
        if (this.statusBarInputLayerExists) {
            this.statusBarInputLayerExists = false;
            AdditionalSystemViewContainer additionalSystemViewContainer = this.statusBarInputLayer;
            if (additionalSystemViewContainer != null && (view = additionalSystemViewContainer.view) != null) {
                view.setOnTouchListener(null);
            }
            this.handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder$disposeStatusBarInputLayer$1
                @Override // java.lang.Runnable
                public final void run() {
                    AdditionalSystemViewContainer additionalSystemViewContainer2 = AppHandleViewHolder.this.statusBarInputLayer;
                    if (additionalSystemViewContainer2 != null) {
                        additionalSystemViewContainer2.releaseView();
                    }
                    AppHandleViewHolder.this.statusBarInputLayer = null;
                }
            });
        }
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuClosed() {
        this.animator.animateCaptionHandleAlpha(0.0f, 1.0f);
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuOpened() {
        this.animator.animateCaptionHandleAlpha(1.0f, 0.0f);
    }
}
