package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class NotificationGuts extends FrameLayout {
    public int mActualHeight;
    public Drawable mBackground;
    public int mClipBottomAmount;
    public int mClipTopAmount;
    public OnGutsClosedListener mClosedListener;
    public OnGutsClosedListener mClosedListenerForOngoingActivity;
    public boolean mExposed;
    public final AnonymousClass2 mFalsingCheck;
    public GutsContent mGutsContent;
    public final AnonymousClass1 mGutsContentAccessibilityDelegate;
    public final Handler mHandler;
    public OnHeightChangedListener mHeightListener;
    public boolean mNeedsFalsingProtection;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimateCloseListener extends AnimatorListenerAdapter {
        public final GutsContent mGutsContent;
        public final View mView;

        public /* synthetic */ AnimateCloseListener(NotificationGuts notificationGuts, View view, GutsContent gutsContent, int i) {
            this(view, gutsContent);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            if (NotificationGuts.this.mExposed) {
                return;
            }
            this.mView.setVisibility(8);
            this.mGutsContent.onFinishedClosing();
        }

        private AnimateCloseListener(View view, GutsContent gutsContent) {
            this.mView = view;
            this.mGutsContent = gutsContent;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimateOpenListener extends AnimatorListenerAdapter {
        public final Runnable mOnAnimationEnd;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            Runnable runnable = this.mOnAnimationEnd;
            if (runnable != null) {
                runnable.run();
            }
        }

        private AnimateOpenListener(Runnable runnable) {
            this.mOnAnimationEnd = runnable;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnGutsClosedListener {
        void onGutsClosed(NotificationGuts notificationGuts);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnHeightChangedListener {
        void onHeightChanged();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.row.NotificationGuts$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.row.NotificationGuts$2] */
    public NotificationGuts(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGutsContentAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.notification.row.NotificationGuts.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (super.performAccessibilityAction(view, i, bundle)) {
                    return true;
                }
                if (i != 32) {
                    return false;
                }
                NotificationGuts.this.closeControls(view, false);
                return true;
            }
        };
        setWillNotDraw(false);
        this.mHandler = new Handler();
        this.mFalsingCheck = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationGuts.2
            @Override // java.lang.Runnable
            public final void run() {
                NotificationGuts notificationGuts = NotificationGuts.this;
                if (notificationGuts.mNeedsFalsingProtection && notificationGuts.mExposed) {
                    notificationGuts.closeControls(-1, -1, false, false);
                }
            }
        };
    }

    public void animateClose(int i, int i2) {
        if (isAttachedToWindow()) {
            animate().alpha(0.0f).setDuration(240L).setInterpolator(Interpolators.ALPHA_OUT).setListener(new AnimateCloseListener(this, this, this.mGutsContent, 0)).start();
        } else {
            Log.w("NotificationGuts", "Failed to animate guts close");
            this.mGutsContent.onFinishedClosing();
        }
    }

    public final void closeControls(View view, boolean z) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr);
        view.getLocationOnScreen(iArr2);
        closeControls((iArr2[0] - iArr[0]) + (view.getWidth() / 2), (iArr2[1] - iArr[1]) + (view.getHeight() / 2), z, false);
    }

    @Override // android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        Drawable drawable = this.mBackground;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Drawable drawable = this.mBackground;
        int i = this.mClipTopAmount;
        int i2 = this.mActualHeight - this.mClipBottomAmount;
        if (drawable == null || i >= i2) {
            return;
        }
        drawable.setBounds(0, i, getWidth(), i2);
        drawable.draw(canvas);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Drawable drawable = ((FrameLayout) this).mContext.getDrawable(R.drawable.notification_guts_bg);
        this.mBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mExposed && getVisibility() == 0) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void resetFalsingCheck() {
        this.mHandler.removeCallbacks(this.mFalsingCheck);
        if (this.mNeedsFalsingProtection && this.mExposed) {
            this.mHandler.postDelayed(this.mFalsingCheck, 8000L);
        }
    }

    public void setExposed(boolean z, boolean z2) {
        GutsContent gutsContent;
        boolean z3 = this.mExposed;
        this.mExposed = z;
        this.mNeedsFalsingProtection = z2;
        if (z && z2) {
            resetFalsingCheck();
        } else {
            this.mHandler.removeCallbacks(this.mFalsingCheck);
        }
        if (z3 == this.mExposed || (gutsContent = this.mGutsContent) == null) {
            return;
        }
        View contentView = gutsContent.getContentView();
        contentView.sendAccessibilityEvent(32);
        if (this.mExposed) {
            contentView.requestAccessibilityFocus();
        }
    }

    public final void setGutsContent(GutsContent gutsContent) {
        gutsContent.setGutsParent(this);
        gutsContent.setAccessibilityDelegate(this.mGutsContentAccessibilityDelegate);
        this.mGutsContent = gutsContent;
        removeAllViews();
        ViewGroup viewGroup = (ViewGroup) this.mGutsContent.getContentView().getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        addView(this.mGutsContent.getContentView());
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mBackground;
    }

    public NotificationGuts(Context context) {
        this(context, null);
    }

    public final void closeControls(int i, int i2, boolean z, boolean z2) {
        if (this.mClosedListenerForOngoingActivity != null) {
            Log.i("NotificationGuts", "NotificationGuts: Call mClosedListenerForOngoingActivity");
            this.mClosedListenerForOngoingActivity.onGutsClosed(this);
        }
        if (getWindowToken() == null) {
            OnGutsClosedListener onGutsClosedListener = this.mClosedListener;
            if (onGutsClosedListener != null) {
                onGutsClosedListener.onGutsClosed(this);
                return;
            }
            return;
        }
        GutsContent gutsContent = this.mGutsContent;
        if (gutsContent == null || !gutsContent.handleCloseControls(z, z2)) {
            animateClose(i, i2);
            setExposed(false, this.mNeedsFalsingProtection);
            OnGutsClosedListener onGutsClosedListener2 = this.mClosedListener;
            if (onGutsClosedListener2 != null) {
                onGutsClosedListener2.onGutsClosed(this);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface GutsContent {
        int getActualHeight();

        View getContentView();

        boolean handleCloseControls(boolean z, boolean z2);

        default boolean isLeavebehind() {
            return false;
        }

        boolean needsFalsingProtection();

        void setAccessibilityDelegate(View.AccessibilityDelegate accessibilityDelegate);

        void setGutsParent(NotificationGuts notificationGuts);

        boolean shouldBeSavedOnClose();

        boolean willBeRemoved();

        default void onFinishedClosing() {
        }
    }
}
