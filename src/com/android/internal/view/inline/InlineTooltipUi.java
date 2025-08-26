package com.android.internal.view.inline;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.transition.Transition;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.autofill.AutofillFeatureFlags;
import android.view.autofill.Helper;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.inline.InlineContentView;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public final class InlineTooltipUi extends PopupWindow implements AutoCloseable {
    private static final int FIRST_TIME_SHOW_DEFAULT_DELAY_MS = 250;
    private static final String TAG = "InlineTooltipUi";
    private final ViewGroup mContentContainer;
    private DelayShowRunnable mDelayShowTooltip;
    private boolean mHasEverDetached;
    private boolean mShowing;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private final WindowManager mWm;
    private boolean mDelayShowAtStart = true;
    private boolean mDelaying = false;
    private final Rect mTmpRect = new Rect();
    private final View.OnAttachStateChangeListener mAnchorOnAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.internal.view.inline.InlineTooltipUi.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            InlineTooltipUi.this.mHasEverDetached = true;
            InlineTooltipUi.this.dismiss();
        }
    };
    private final View.OnLayoutChangeListener mAnchoredOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.internal.view.inline.InlineTooltipUi.2
        int mHeight;

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9;
            if (InlineTooltipUi.this.mHasEverDetached || this.mHeight == (i9 = i4 - i2)) {
                return;
            }
            this.mHeight = i9;
            InlineTooltipUi.this.adjustPosition();
        }
    };
    private int mShowDelayConfigMs = DeviceConfig.getInt(Context.AUTOFILL_MANAGER_SERVICE, AutofillFeatureFlags.DEVICE_CONFIG_AUTOFILL_TOOLTIP_SHOW_UP_DELAY, 250);

    @Override // android.widget.PopupWindow
    protected boolean hasContentView() {
        return true;
    }

    @Override // android.widget.PopupWindow
    protected boolean hasDecorView() {
        return true;
    }

    public InlineTooltipUi(Context context) {
        this.mContentContainer = new LinearLayout(new ContextWrapper(context));
        this.mWm = (WindowManager) context.getSystemService(WindowManager.class);
        setTouchModal(false);
        setOutsideTouchable(true);
        setInputMethodMode(2);
        setFocusable(false);
    }

    public void setTooltipView(InlineContentView inlineContentView) throws Resources.NotFoundException {
        this.mContentContainer.removeAllViews();
        this.mContentContainer.addView(inlineContentView);
        this.mContentContainer.setVisibility(0);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        dismiss();
    }

    @Override // android.widget.PopupWindow
    protected WindowManager.LayoutParams getDecorViewLayoutParams() {
        return this.mWindowLayoutParams;
    }

    public void update(View view) {
        if (view == null) {
            View anchor = getAnchor();
            if (anchor != null) {
                removeDelayShowTooltip(anchor);
                return;
            }
            return;
        }
        if (this.mDelayShowAtStart) {
            this.mDelayShowAtStart = false;
            this.mDelaying = true;
            if (this.mDelayShowTooltip == null) {
                this.mDelayShowTooltip = new DelayShowRunnable(view);
            }
            int iFixScale = this.mShowDelayConfigMs;
            try {
                iFixScale = (int) (iFixScale * WindowManager.fixScale(Settings.Global.getFloat(view.getContext().getContentResolver(), "animator_duration_scale")));
            } catch (Settings.SettingNotFoundException unused) {
            }
            view.postDelayed(this.mDelayShowTooltip, iFixScale);
            return;
        }
        if (this.mDelaying) {
            return;
        }
        updateInner(view);
    }

    private void removeDelayShowTooltip(View view) {
        DelayShowRunnable delayShowRunnable = this.mDelayShowTooltip;
        if (delayShowRunnable != null) {
            view.removeCallbacks(delayShowRunnable);
            this.mDelayShowTooltip = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInner(View view) {
        if (this.mHasEverDetached) {
            return;
        }
        setWindowLayoutType(1005);
        int preferHeight = (-view.getHeight()) - getPreferHeight(view);
        if (!isShowing()) {
            setWidth(-2);
            setHeight(-2);
            showAsDropDown(view, 0, preferHeight, 49);
            return;
        }
        update(view, 0, preferHeight, -2, -2);
    }

    private int getPreferHeight(View view) {
        int height = this.mContentContainer.getHeight();
        return height == 0 ? view.getHeight() : height;
    }

    @Override // android.widget.PopupWindow
    protected boolean findDropDownPosition(View view, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean zFindDropDownPosition = super.findDropDownPosition(view, layoutParams, i, i2, i3, i4, i5, z);
        Object parent = view.getParent();
        if (parent instanceof View) {
            Rect rect = this.mTmpRect;
            ((View) parent).getGlobalVisibleRect(rect);
            if (zFindDropDownPosition) {
                layoutParams.y = rect.top - getPreferHeight(view);
                return zFindDropDownPosition;
            }
            layoutParams.y = rect.bottom + 1;
        }
        return zFindDropDownPosition;
    }

    @Override // android.widget.PopupWindow
    protected void update(View view, WindowManager.LayoutParams layoutParams) {
        if (view.isVisibleToUser()) {
            show(layoutParams);
        } else {
            hide();
        }
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (isShowing()) {
            return;
        }
        setShowing(true);
        setDropDown(true);
        attachToAnchor(view, i, i2, i3);
        WindowManager.LayoutParams layoutParamsCreatePopupLayoutParams = createPopupLayoutParams(view.getWindowToken());
        this.mWindowLayoutParams = layoutParamsCreatePopupLayoutParams;
        updateAboveAnchor(findDropDownPosition(view, layoutParamsCreatePopupLayoutParams, i, i2, layoutParamsCreatePopupLayoutParams.width, layoutParamsCreatePopupLayoutParams.height, i3, getAllowScrollingAnchorParent()));
        layoutParamsCreatePopupLayoutParams.accessibilityIdOfAnchor = view.getAccessibilityViewId();
        layoutParamsCreatePopupLayoutParams.packageName = view.getContext().getPackageName();
        show(layoutParamsCreatePopupLayoutParams);
    }

    @Override // android.widget.PopupWindow
    protected void attachToAnchor(View view, int i, int i2, int i3) {
        super.attachToAnchor(view, i, i2, i3);
        view.addOnAttachStateChangeListener(this.mAnchorOnAttachStateChangeListener);
    }

    @Override // android.widget.PopupWindow
    protected void detachFromAnchor() {
        View anchor = getAnchor();
        if (anchor != null) {
            anchor.removeOnAttachStateChangeListener(this.mAnchorOnAttachStateChangeListener);
            removeDelayShowTooltip(anchor);
        }
        this.mHasEverDetached = true;
        super.detachFromAnchor();
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        setTransitioningToDismiss(true);
        hide();
        detachFromAnchor();
        if (getOnDismissListener() != null) {
            getOnDismissListener().onDismiss();
        }
        super.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustPosition() {
        View anchor = getAnchor();
        if (anchor == null) {
            return;
        }
        update(anchor);
    }

    private void show(WindowManager.LayoutParams layoutParams) {
        this.mWindowLayoutParams = layoutParams;
        try {
            layoutParams.packageName = "android";
            layoutParams.setTitle("Autofill Inline Tooltip");
            if (!this.mShowing) {
                if (Helper.sVerbose) {
                    Slog.v(TAG, "show()");
                }
                layoutParams.flags = 40;
                layoutParams.privateFlags |= 4194304;
                this.mContentContainer.addOnLayoutChangeListener(this.mAnchoredOnLayoutChangeListener);
                this.mWm.addView(this.mContentContainer, layoutParams);
                this.mShowing = true;
                return;
            }
            this.mWm.updateViewLayout(this.mContentContainer, layoutParams);
        } catch (WindowManager.BadTokenException unused) {
            Slog.d(TAG, "Failed with token " + layoutParams.token + " gone.");
        } catch (IllegalStateException e) {
            Slog.wtf(TAG, "Exception showing window " + layoutParams, e);
        }
    }

    private void hide() {
        try {
            if (this.mShowing) {
                if (Helper.sVerbose) {
                    Slog.v(TAG, "hide()");
                }
                this.mContentContainer.removeOnLayoutChangeListener(this.mAnchoredOnLayoutChangeListener);
                this.mWm.removeView(this.mContentContainer);
                this.mShowing = false;
            }
        } catch (IllegalStateException e) {
            Slog.e(TAG, "Exception hiding window ", e);
        }
    }

    @Override // android.widget.PopupWindow
    public int getAnimationStyle() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public Drawable getBackground() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public View getContentView() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public float getElevation() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public Transition getEnterTransition() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public Transition getExitTransition() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setBackgroundDrawable(Drawable drawable) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setContentView(View view) {
        if (view != null) {
            throw new IllegalStateException("You can't call this!");
        }
    }

    @Override // android.widget.PopupWindow
    public void setElevation(float f) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setEnterTransition(Transition transition) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setExitTransition(Transition transition) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        throw new IllegalStateException("You can't call this!");
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        if (this.mContentContainer != null) {
            printWriter.print(str);
            printWriter.print("Window: ");
            String str2 = str + "  ";
            printWriter.println();
            printWriter.print(str2);
            printWriter.print("showing: ");
            printWriter.println(this.mShowing);
            printWriter.print(str2);
            printWriter.print("view: ");
            printWriter.println(this.mContentContainer);
            if (this.mWindowLayoutParams != null) {
                printWriter.print(str2);
                printWriter.print("params: ");
                printWriter.println(this.mWindowLayoutParams);
            }
            printWriter.print(str2);
            printWriter.print("screen coordinates: ");
            ViewGroup viewGroup = this.mContentContainer;
            if (viewGroup == null) {
                printWriter.println("N/A");
                return;
            }
            int[] locationOnScreen = viewGroup.getLocationOnScreen();
            printWriter.print(locationOnScreen[0]);
            printWriter.print("x");
            printWriter.println(locationOnScreen[1]);
        }
    }

    private class DelayShowRunnable implements Runnable {
        WeakReference<View> mAnchor;

        DelayShowRunnable(View view) {
            this.mAnchor = new WeakReference<>(view);
        }

        @Override // java.lang.Runnable
        public void run() {
            InlineTooltipUi.this.mDelaying = false;
            View view = this.mAnchor.get();
            if (view != null) {
                InlineTooltipUi.this.updateInner(view);
            }
        }

        public void setAnchor(View view) {
            this.mAnchor.clear();
            this.mAnchor = new WeakReference<>(view);
        }
    }
}
