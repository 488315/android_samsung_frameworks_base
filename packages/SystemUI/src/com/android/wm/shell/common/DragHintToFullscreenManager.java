package com.android.wm.shell.common;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopTasksController$onDragPositioningEnd$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragHintToFullscreenManager extends FrameLayout {
    public boolean mAddWindowRequested;
    public boolean mNeedToRemoveWindow;
    public DragHintToFullscreen mView;
    public final WindowManager mWindowManager;

    public DragHintToFullscreenManager(Context context) {
        super(context);
        this.mWindowManager = (WindowManager) getContext().getSystemService("window");
    }

    public final WindowManager.LayoutParams generateLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2608, 16778040, -2);
        layoutParams.setTitle("DragHintToFullscreen");
        layoutParams.privateFlags |= 80;
        layoutParams.samsungFlags |= 131072;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.y = 0;
        layoutParams.x = 0;
        Point point = new Point();
        getContext().getDisplay().getRealSize(point);
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        layoutParams.gravity = 8388659;
        return layoutParams;
    }

    public final void hide(DesktopTasksController$onDragPositioningEnd$1 desktopTasksController$onDragPositioningEnd$1) {
        DragHintToFullscreen dragHintToFullscreen = this.mView;
        if (!dragHintToFullscreen.mVisible) {
            if (desktopTasksController$onDragPositioningEnd$1 != null) {
                Log.i("DragHintToFullscreen", "already mVisible=false but the callback should be run.");
                desktopTasksController$onDragPositioningEnd$1.run();
                return;
            }
            return;
        }
        dragHintToFullscreen.mVisible = false;
        Log.i("DragHintToFullscreen", "hide");
        dragHintToFullscreen.clearAnimation();
        dragHintToFullscreen.mHideAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.common.DragHintToFullscreen.2
            public final /* synthetic */ Runnable val$animationEndCallback;

            public AnonymousClass2(Runnable desktopTasksController$onDragPositioningEnd$12) {
                r2 = desktopTasksController$onDragPositioningEnd$12;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                int i = DragHintToFullscreen.$r8$clinit;
                Log.i("DragHintToFullscreen", "hide-Run callback");
                DragHintToFullscreen.this.setVisibility(4);
                Runnable runnable = r2;
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        dragHintToFullscreen.startAnimation(dragHintToFullscreen.mHideAnimation);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onAttachedToWindow  mNeedToRemoveWindow="), this.mNeedToRemoveWindow, "DragHintToFullscreenManager");
        if (this.mNeedToRemoveWindow) {
            removeWindow(true);
        }
    }

    public final void removeWindow(boolean z) {
        Log.i("DragHintToFullscreenManager", "removeWindow  isAttachedToWindow=" + isAttachedToWindow() + "  addWindowRequested=" + this.mAddWindowRequested);
        clearAnimation();
        this.mNeedToRemoveWindow = false;
        DragHintToFullscreen dragHintToFullscreen = this.mView;
        if (dragHintToFullscreen != null && z && dragHintToFullscreen.mVisible) {
            dragHintToFullscreen.mVisible = false;
            dragHintToFullscreen.clearAnimation();
            dragHintToFullscreen.setVisibility(4);
        }
        if (isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(this);
            this.mAddWindowRequested = false;
        } else if (this.mAddWindowRequested) {
            this.mNeedToRemoveWindow = true;
        }
    }
}
