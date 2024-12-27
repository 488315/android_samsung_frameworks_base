package com.android.systemui.cover;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverWindowDelegate {
    public FrameLayout mBackgroundDecorView;
    public final Context mContext;
    public Display mCoverDisplay;
    public ViewCoverDecorView mDecorView;
    public boolean mIsShowing;
    public WindowManager mWindowManager;
    public Rect mWindowRect;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ViewCoverDecorView extends FrameLayout {
        public ViewCoverDecorView(Context context) {
            super(context);
            CoverWindowDelegate.this.mWindowManager = (WindowManager) getContext().getSystemService("window");
        }

        @Override // android.view.View
        public final boolean onHoverEvent(MotionEvent motionEvent) {
            if (CoverWindowDelegate.this.mCoverDisplay == null || motionEvent.getToolType(0) != 1) {
                return super.onHoverEvent(motionEvent);
            }
            motionEvent.setDisplayId(CoverWindowDelegate.this.mCoverDisplay.getDisplayId());
            InputManager.getInstance().injectInputEvent(motionEvent, 0);
            return true;
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            Display display = CoverWindowDelegate.this.mCoverDisplay;
            if (display != null) {
                motionEvent.setDisplayId(display.getDisplayId());
                InputManager.getInstance().injectInputEvent(motionEvent, 0);
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    public CoverWindowDelegate(Context context, WindowManager windowManager) {
        this.mWindowManager = windowManager;
        this.mContext = context;
    }

    public static boolean isHideInternal(WindowManager.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("isMinimizeInternal : alpha: ");
        sb.append(layoutParams.alpha);
        sb.append("  , w:");
        sb.append(layoutParams.width);
        sb.append(" , h:");
        TooltipPopup$$ExternalSyntheticOutline0.m(layoutParams.height, "CoverWindowDelegate", sb);
        return layoutParams.alpha == 0.0f;
    }

    public final ViewCoverDecorView attach() {
        if (this.mIsShowing) {
            Log.w("CoverWindowDelegate", "attach : it is showing");
            return this.mDecorView;
        }
        this.mIsShowing = true;
        ViewCoverDecorView viewCoverDecorView = this.mDecorView;
        if (viewCoverDecorView != null && viewCoverDecorView.getWindowToken() != null) {
            this.mWindowManager.removeViewImmediate(viewCoverDecorView);
        }
        ViewCoverDecorView viewCoverDecorView2 = new ViewCoverDecorView(this.mContext);
        this.mDecorView = viewCoverDecorView2;
        this.mWindowManager.addView(viewCoverDecorView2, createLayoutParams());
        if (LsRune.COVER_VIRTUAL_DISPLAY && this.mWindowRect != null) {
            FrameLayout frameLayout = this.mBackgroundDecorView;
            if (frameLayout != null && frameLayout.getWindowToken() != null) {
                this.mWindowManager.removeViewImmediate(this.mBackgroundDecorView);
            }
            FrameLayout frameLayout2 = new FrameLayout(this.mContext);
            this.mBackgroundDecorView = frameLayout2;
            this.mWindowManager.addView(frameLayout2, createBackgroundLayoutParams());
        }
        return this.mDecorView;
    }

    public WindowManager.LayoutParams createBackgroundLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (this.mWindowRect != null) {
            layoutParams.type = 2631;
            layoutParams.setTitle("VIRTUAL-COVER-BACKGROUND");
            layoutParams.width = -1;
            layoutParams.height = -1;
        }
        layoutParams.flags = 1800;
        layoutParams.semAddPrivateFlags(16);
        layoutParams.screenOrientation = 5;
        layoutParams.rotationAnimation = 2;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()) & (~WindowInsets.Type.statusBars()));
        return layoutParams;
    }

    public WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (this.mWindowRect != null) {
            layoutParams.type = 2630;
            layoutParams.setTitle("VIRTUAL-COVER");
            Rect rect = this.mWindowRect;
            layoutParams.x = rect.left;
            layoutParams.y = rect.top;
            layoutParams.width = rect.width();
            layoutParams.height = this.mWindowRect.height();
            layoutParams.alpha = 0.0f;
            layoutParams.gravity = 51;
        } else {
            layoutParams.type = 2099;
            layoutParams.setTitle("COVER");
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.semSetScreenTimeout(6000L);
            layoutParams.semSetScreenDimDuration(0L);
        }
        layoutParams.flags = 132864;
        layoutParams.semAddPrivateFlags(16);
        layoutParams.screenOrientation = 5;
        layoutParams.rotationAnimation = 2;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()) & (~WindowInsets.Type.statusBars()));
        return layoutParams;
    }

    public final void detach() {
        FrameLayout frameLayout;
        if (!this.mIsShowing) {
            Log.w("CoverWindowDelegate", "detach : it is NOT showing");
            return;
        }
        ViewCoverDecorView viewCoverDecorView = this.mDecorView;
        if (viewCoverDecorView == null) {
            Log.w("CoverWindowDelegate", "detach : decorView is null");
            return;
        }
        this.mIsShowing = false;
        try {
            this.mWindowManager.removeViewImmediate(viewCoverDecorView);
        } catch (Exception e) {
            Log.e("CoverWindowDelegate", "removeViewImmediate\n" + Log.getStackTraceString(e));
        }
        viewCoverDecorView.removeAllViews();
        this.mDecorView = null;
        if (!LsRune.COVER_VIRTUAL_DISPLAY || (frameLayout = this.mBackgroundDecorView) == null) {
            return;
        }
        try {
            this.mWindowManager.removeViewImmediate(frameLayout);
        } catch (Exception e2) {
            Log.e("CoverWindowDelegate", "removeViewImmediate\n" + Log.getStackTraceString(e2));
        }
        this.mBackgroundDecorView.removeAllViews();
        this.mBackgroundDecorView = null;
    }

    public final void hide() {
        WindowManager.LayoutParams layoutParams;
        ViewCoverDecorView viewCoverDecorView = this.mDecorView;
        if (viewCoverDecorView == null || (layoutParams = (WindowManager.LayoutParams) viewCoverDecorView.getLayoutParams()) == null || isHideInternal(layoutParams)) {
            return;
        }
        layoutParams.alpha = 0.0f;
        layoutParams.flags = (layoutParams.flags | 8) & (-131073);
        layoutParams.samsungFlags &= -262145;
        this.mWindowManager.updateViewLayout(this.mDecorView, layoutParams);
    }

    public final void show() {
        ViewCoverDecorView viewCoverDecorView = this.mDecorView;
        if (viewCoverDecorView == null || this.mWindowRect == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) viewCoverDecorView.getLayoutParams();
        if (isHideInternal(layoutParams)) {
            layoutParams.alpha = 1.0f;
            layoutParams.flags = (layoutParams.flags & (-9)) | 131072;
            layoutParams.samsungFlags |= 262144;
            this.mWindowManager.updateViewLayout(this.mDecorView, layoutParams);
        }
    }
}
