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
import com.android.systemui.LsRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverWindowDelegate {
    public FrameLayout mBackgroundDecorView;
    public final Context mContext;
    public Display mCoverDisplay;
    public ViewCoverDecorView mDecorView;
    public boolean mIsShowing;
    public WindowManager mWindowManager;
    public final Rect mWindowRect;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public CoverWindowDelegate(Context context, Rect rect) {
        this.mContext = context;
        if (rect != null) {
            this.mWindowRect = new Rect(rect);
        }
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
        Context context = this.mContext;
        ViewCoverDecorView viewCoverDecorView2 = new ViewCoverDecorView(context);
        this.mDecorView = viewCoverDecorView2;
        WindowManager windowManager = this.mWindowManager;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Rect rect = this.mWindowRect;
        if (rect != null) {
            layoutParams.type = 2630;
            layoutParams.setTitle("VIRTUAL-COVER");
            layoutParams.x = rect.left;
            layoutParams.y = rect.top;
            layoutParams.width = 1;
            layoutParams.height = 1;
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
        windowManager.addView(viewCoverDecorView2, layoutParams);
        if (LsRune.COVER_VIRTUAL_DISPLAY && rect != null) {
            FrameLayout frameLayout = this.mBackgroundDecorView;
            if (frameLayout != null && frameLayout.getWindowToken() != null) {
                this.mWindowManager.removeViewImmediate(this.mBackgroundDecorView);
            }
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.mBackgroundDecorView = frameLayout2;
            WindowManager windowManager2 = this.mWindowManager;
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
            layoutParams2.type = 2631;
            layoutParams2.setTitle("VIRTUAL-COVER-BACKGROUND");
            layoutParams2.width = -1;
            layoutParams2.height = -1;
            layoutParams2.flags = 1800;
            layoutParams2.semAddPrivateFlags(16);
            layoutParams2.screenOrientation = 5;
            layoutParams2.rotationAnimation = 2;
            layoutParams2.layoutInDisplayCutoutMode = 1;
            layoutParams2.setFitInsetsTypes(layoutParams2.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()) & (~WindowInsets.Type.statusBars()));
            windowManager2.addView(frameLayout2, layoutParams2);
        }
        return this.mDecorView;
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

    public final void minimize() {
        WindowManager.LayoutParams layoutParams;
        ViewCoverDecorView viewCoverDecorView = this.mDecorView;
        if (viewCoverDecorView == null || (layoutParams = (WindowManager.LayoutParams) viewCoverDecorView.getLayoutParams()) == null) {
            return;
        }
        if (layoutParams.width == 1 && layoutParams.height == 1) {
            return;
        }
        layoutParams.width = 1;
        layoutParams.height = 1;
        layoutParams.flags = (layoutParams.flags | 8) & (-131073);
        layoutParams.samsungFlags &= -262145;
        this.mWindowManager.updateViewLayout(this.mDecorView, layoutParams);
    }
}
