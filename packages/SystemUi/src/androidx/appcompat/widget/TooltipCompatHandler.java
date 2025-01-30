package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.reflect.provider.SeslSettingsReflector$SeslSystemReflector;
import androidx.reflect.view.SeslPointerIconReflector;
import androidx.reflect.view.SeslViewReflector;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TooltipCompatHandler implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    public static TooltipCompatHandler sActiveHandler = null;
    public static boolean sIsForceActionBarX = false;
    public static boolean sIsForceBelow = false;
    public static boolean sIsTooltipNull = false;
    public static TooltipCompatHandler sPendingHandler;
    public final View mAnchor;
    public int mAnchorX;
    public int mAnchorY;
    public boolean mFromTouch;
    public final TooltipCompatHandler$$ExternalSyntheticLambda1 mHideRunnable;
    public final int mHoverSlop;
    public int mLastOrientation;
    public TooltipCompatHandler$$ExternalSyntheticLambda0 mLayoutChangeListener;
    public TooltipPopup mPopup;
    public final TooltipCompatHandler$$ExternalSyntheticLambda1 mShowRunnable;
    public final CharSequence mTooltipText;
    public final RunnableC01061 mCheckHoverRunnable = new Runnable() { // from class: androidx.appcompat.widget.TooltipCompatHandler.1
        @Override // java.lang.Runnable
        public final void run() {
            View view = TooltipCompatHandler.this.mAnchor;
            if (view == null || view.isHovered()) {
                return;
            }
            Log.i("TooltipCompatHandler", "isHovered is false. Hide!!");
            TooltipCompatHandler.this.hide();
        }
    };
    public boolean mIsShowRunnablePostDelayed = false;
    public int mLastHoverEvent = -1;
    public boolean mInitialWindowFocus = false;
    public boolean mIsForceExitDelay = false;
    public boolean mForceNextChangeSignificant = true;

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.appcompat.widget.TooltipCompatHandler$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.appcompat.widget.TooltipCompatHandler$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.appcompat.widget.TooltipCompatHandler$1] */
    private TooltipCompatHandler(View view, CharSequence charSequence) {
        final int i = 0;
        this.mShowRunnable = new Runnable(this) { // from class: androidx.appcompat.widget.TooltipCompatHandler$$ExternalSyntheticLambda1
            public final /* synthetic */ TooltipCompatHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        this.f$0.show(false);
                        break;
                    default:
                        this.f$0.hide();
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mHideRunnable = new Runnable(this) { // from class: androidx.appcompat.widget.TooltipCompatHandler$$ExternalSyntheticLambda1
            public final /* synthetic */ TooltipCompatHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        this.f$0.show(false);
                        break;
                    default:
                        this.f$0.hide();
                        break;
                }
            }
        };
        this.mAnchor = view;
        this.mTooltipText = charSequence;
        this.mHoverSlop = ViewConfiguration.get(view.getContext()).getScaledHoverSlop();
        view.setOnLongClickListener(this);
        view.setOnHoverListener(this);
    }

    public static void setPendingHandler(TooltipCompatHandler tooltipCompatHandler) {
        TooltipCompatHandler tooltipCompatHandler2 = sPendingHandler;
        if (tooltipCompatHandler2 != null) {
            tooltipCompatHandler2.mAnchor.removeCallbacks(tooltipCompatHandler2.mShowRunnable);
        }
        sPendingHandler = tooltipCompatHandler;
        if (tooltipCompatHandler != null) {
            tooltipCompatHandler.mAnchor.postDelayed(tooltipCompatHandler.mShowRunnable, ViewConfiguration.getLongPressTimeout());
        }
    }

    public final void hide() {
        if (sActiveHandler == this) {
            sActiveHandler = null;
            TooltipPopup tooltipPopup = this.mPopup;
            if (tooltipPopup != null) {
                tooltipPopup.hide();
                this.mPopup = null;
                this.mForceNextChangeSignificant = true;
                this.mAnchor.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        this.mIsShowRunnablePostDelayed = false;
        if (sPendingHandler == this) {
            setPendingHandler(null);
        }
        this.mAnchor.removeCallbacks(this.mHideRunnable);
        if (!this.mFromTouch) {
            this.mAnchor.removeCallbacks(this.mCheckHoverRunnable);
            this.mAnchor.removeOnLayoutChangeListener(this.mLayoutChangeListener);
        }
        sIsTooltipNull = false;
    }

    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        boolean z;
        if (this.mPopup != null && this.mFromTouch) {
            return false;
        }
        if (this.mAnchor == null) {
            Log.i("TooltipCompatHandler", "TooltipCompat Anchor view is null");
            return false;
        }
        Context context = view.getContext();
        if (motionEvent.isFromSource(16386)) {
            if (!(Settings.System.getInt(this.mAnchor.getContext().getContentResolver(), SeslSettingsReflector$SeslSystemReflector.getField_SEM_PEN_HOVERING(), 0) == 1)) {
                if (this.mAnchor.isEnabled() && this.mPopup != null && context != null) {
                    SeslViewReflector.semSetPointerIcon(view, PointerIcon.getSystemIcon(context, SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT()));
                }
                return false;
            }
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mAnchor.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        this.mLastHoverEvent = action;
        if (action != 7) {
            if (action == 9) {
                this.mInitialWindowFocus = this.mAnchor.hasWindowFocus();
                if (this.mAnchor.isEnabled() && this.mPopup == null && context != null) {
                    SeslViewReflector.semSetPointerIcon(view, PointerIcon.getSystemIcon(context, SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_MORE()));
                }
            } else if (action == 10) {
                Log.i("TooltipCompatHandler", "MotionEvent.ACTION_HOVER_EXIT : hide SeslTooltipPopup");
                if (this.mAnchor.isEnabled() && this.mPopup != null && context != null) {
                    SeslViewReflector.semSetPointerIcon(view, PointerIcon.getSystemIcon(context, SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT()));
                }
                TooltipPopup tooltipPopup = this.mPopup;
                if (tooltipPopup == null || !tooltipPopup.isShowing() || Math.abs(motionEvent.getX() - this.mAnchorX) >= 4.0f || Math.abs(motionEvent.getY() - this.mAnchorY) >= 4.0f) {
                    hide();
                } else {
                    this.mIsForceExitDelay = true;
                    this.mAnchor.removeCallbacks(this.mHideRunnable);
                    this.mAnchor.postDelayed(this.mHideRunnable, 2500L);
                }
            }
        } else if (this.mAnchor.isEnabled() && this.mPopup == null) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (this.mForceNextChangeSignificant || Math.abs(x - this.mAnchorX) > this.mHoverSlop || Math.abs(y - this.mAnchorY) > this.mHoverSlop) {
                this.mAnchorX = x;
                this.mAnchorY = y;
                this.mForceNextChangeSignificant = false;
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.mAnchorX = (int) motionEvent.getX();
                this.mAnchorY = (int) motionEvent.getY();
                if (!this.mIsShowRunnablePostDelayed || this.mIsForceExitDelay) {
                    setPendingHandler(this);
                    this.mIsForceExitDelay = false;
                    this.mIsShowRunnablePostDelayed = true;
                }
            }
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        this.mAnchorX = view.getWidth() / 2;
        this.mAnchorY = view.getHeight() / 2;
        show(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        hide();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.view.View$OnLayoutChangeListener, androidx.appcompat.widget.TooltipCompatHandler$$ExternalSyntheticLambda0] */
    public final void show(boolean z) {
        long longPressTimeout;
        long j;
        long j2;
        View view = this.mAnchor;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isAttachedToWindow(view)) {
            setPendingHandler(null);
            TooltipCompatHandler tooltipCompatHandler = sActiveHandler;
            if (tooltipCompatHandler != null) {
                tooltipCompatHandler.hide();
            }
            sActiveHandler = this;
            this.mFromTouch = z;
            TooltipPopup tooltipPopup = new TooltipPopup(this.mAnchor.getContext());
            this.mPopup = tooltipPopup;
            if (sIsTooltipNull) {
                return;
            }
            boolean z2 = sIsForceBelow;
            View view2 = tooltipPopup.mContentView;
            Context context = tooltipPopup.mContext;
            WindowManager.LayoutParams layoutParams = tooltipPopup.mLayoutParams;
            TextView textView = tooltipPopup.mMessageView;
            if (z2 || sIsForceActionBarX) {
                View view3 = this.mAnchor;
                boolean z3 = this.mFromTouch;
                CharSequence charSequence = this.mTooltipText;
                boolean z4 = sIsForceActionBarX;
                if (tooltipPopup.isShowing()) {
                    tooltipPopup.hide();
                }
                textView.setText(charSequence);
                tooltipPopup.computePosition(view3, z3, layoutParams, z2, z4);
                ((WindowManager) context.getSystemService("window")).addView(view2, layoutParams);
                sIsForceBelow = false;
                sIsForceActionBarX = false;
            } else {
                View view4 = this.mAnchor;
                boolean z5 = this.mFromTouch;
                CharSequence charSequence2 = this.mTooltipText;
                if (tooltipPopup.isShowing()) {
                    tooltipPopup.hide();
                }
                textView.setText(charSequence2);
                tooltipPopup.computePosition(view4, z5, layoutParams, false, false);
                ((WindowManager) context.getSystemService("window")).addView(view2, layoutParams);
            }
            final Resources resources = this.mAnchor.getContext().getResources();
            this.mLastOrientation = resources.getConfiguration().orientation;
            ?? r0 = new View.OnLayoutChangeListener() { // from class: androidx.appcompat.widget.TooltipCompatHandler$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view5, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    TooltipPopup tooltipPopup2;
                    TooltipCompatHandler tooltipCompatHandler2 = TooltipCompatHandler.this;
                    Resources resources2 = resources;
                    tooltipCompatHandler2.getClass();
                    if (resources2.getConfiguration().orientation != tooltipCompatHandler2.mLastOrientation && (tooltipPopup2 = tooltipCompatHandler2.mPopup) != null && tooltipPopup2.isShowing()) {
                        tooltipCompatHandler2.hide();
                    }
                    tooltipCompatHandler2.mLastOrientation = resources2.getConfiguration().orientation;
                }
            };
            this.mLayoutChangeListener = r0;
            this.mAnchor.addOnLayoutChangeListener(r0);
            this.mAnchor.addOnAttachStateChangeListener(this);
            if (this.mFromTouch) {
                j2 = 2500;
            } else {
                if ((ViewCompat.Api16Impl.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1) {
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    j = 3000;
                } else {
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    j = 15000;
                }
                j2 = j - longPressTimeout;
            }
            this.mAnchor.removeCallbacks(this.mHideRunnable);
            this.mAnchor.postDelayed(this.mHideRunnable, j2);
            if (!this.mFromTouch) {
                this.mAnchor.removeCallbacks(this.mCheckHoverRunnable);
                this.mAnchor.postDelayed(this.mCheckHoverRunnable, 300L);
            }
            if (this.mLastHoverEvent != 7 || this.mAnchor.hasWindowFocus() || this.mInitialWindowFocus == this.mAnchor.hasWindowFocus()) {
                return;
            }
            hide();
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
