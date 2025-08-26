package android.view.autofill;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.window.WindowMetricsHelper;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class AutofillPopupWindow extends PopupWindow {
    private static final String TAG = "AutofillPopupWindow";
    private boolean mFullScreen;
    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: android.view.autofill.AutofillPopupWindow.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            AutofillPopupWindow.this.dismiss();
        }
    };
    private WindowManager.LayoutParams mWindowLayoutParams;
    private final WindowPresenter mWindowPresenter;

    @Override // android.widget.PopupWindow
    protected boolean hasContentView() {
        return true;
    }

    @Override // android.widget.PopupWindow
    protected boolean hasDecorView() {
        return true;
    }

    public AutofillPopupWindow(IAutofillWindowPresenter iAutofillWindowPresenter) {
        this.mWindowPresenter = new WindowPresenter(this, iAutofillWindowPresenter);
        setTouchModal(false);
        setOutsideTouchable(true);
        setInputMethodMode(2);
        setFocusable(true);
        setIsClippedToScreen(true);
    }

    @Override // android.widget.PopupWindow
    protected WindowManager.LayoutParams getDecorViewLayoutParams() {
        return this.mWindowLayoutParams;
    }

    public void update(final View view, int i, int i2, int i3, int i4, Rect rect) {
        boolean z = i3 == -1;
        this.mFullScreen = z;
        setWindowLayoutType(z ? 2008 : 1005);
        if (this.mFullScreen) {
            Rect boundsExcludingNavigationBarAndCutout = WindowMetricsHelper.getBoundsExcludingNavigationBarAndCutout(((WindowManager) view.getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics());
            i3 = boundsExcludingNavigationBarAndCutout.width();
            i2 = i4 != -1 ? boundsExcludingNavigationBarAndCutout.height() - i4 : 0;
            i = 0;
        } else if (rect != null) {
            final int[] iArr = {rect.left, rect.top};
            View view2 = new View(this, view.getContext()) { // from class: android.view.autofill.AutofillPopupWindow.2
                @Override // android.view.View
                public void getLocationOnScreen(int[] iArr2) {
                    int[] iArr3 = iArr;
                    iArr2[0] = iArr3[0];
                    iArr2[1] = iArr3[1];
                }

                @Override // android.view.View
                public int getAccessibilityViewId() {
                    return view.getAccessibilityViewId();
                }

                @Override // android.view.View
                public ViewTreeObserver getViewTreeObserver() {
                    return view.getViewTreeObserver();
                }

                @Override // android.view.View
                public IBinder getApplicationWindowToken() {
                    return view.getApplicationWindowToken();
                }

                @Override // android.view.View
                public View getRootView() {
                    return view.getRootView();
                }

                @Override // android.view.View
                public int getLayoutDirection() {
                    return view.getLayoutDirection();
                }

                @Override // android.view.View
                public void getWindowDisplayFrame(Rect rect2) {
                    view.getWindowDisplayFrame(rect2);
                }

                @Override // android.view.View
                public void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
                    view.addOnAttachStateChangeListener(onAttachStateChangeListener);
                }

                @Override // android.view.View
                public void removeOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
                    view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
                }

                @Override // android.view.View
                public boolean isAttachedToWindow() {
                    return view.isAttachedToWindow();
                }

                @Override // android.view.View
                public boolean requestRectangleOnScreen(Rect rect2, boolean z2) {
                    return view.requestRectangleOnScreen(rect2, z2);
                }

                @Override // android.view.View
                public IBinder getWindowToken() {
                    return view.getWindowToken();
                }
            };
            view2.setLeftTopRightBottom(rect.left, rect.top, rect.right, rect.bottom);
            view2.setScrollX(view.getScrollX());
            view2.setScrollY(view.getScrollY());
            view.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: android.view.autofill.AutofillPopupWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnScrollChangeListener
                public final void onScrollChange(View view3, int i5, int i6, int i7, int i8) {
                    AutofillPopupWindow.lambda$update$0(iArr, view3, i5, i6, i7, i8);
                }
            });
            view2.setWillNotDraw(true);
            view = view2;
        }
        if (!this.mFullScreen) {
            setAnimationStyle(-1);
        } else if (i4 == -1) {
            setAnimationStyle(0);
        } else {
            setAnimationStyle(R.style.AutofillHalfScreenAnimation);
        }
        if (!isShowing()) {
            setWidth(i3);
            setHeight(i4);
            showAsDropDown(view, i, i2);
            return;
        }
        update(view, i, i2, i3, i4);
    }

    static /* synthetic */ void lambda$update$0(int[] iArr, View view, int i, int i2, int i3, int i4) {
        iArr[0] = iArr[0] - (i - i3);
        iArr[1] = iArr[1] - (i2 - i4);
    }

    @Override // android.widget.PopupWindow
    protected void update(View view, WindowManager.LayoutParams layoutParams) {
        this.mWindowPresenter.show(layoutParams, getTransitionEpicenter(), isLayoutInsetDecor(), view != null ? view.getLayoutDirection() : 3);
    }

    @Override // android.widget.PopupWindow
    protected boolean findDropDownPosition(View view, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (this.mFullScreen) {
            layoutParams.x = i;
            layoutParams.y = i2;
            layoutParams.width = i3;
            layoutParams.height = i4;
            layoutParams.gravity = i5;
            return false;
        }
        return super.findDropDownPosition(view, layoutParams, i, i2, i3, i4, i5, z);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (Helper.sVerbose) {
            Log.v(TAG, "showAsDropDown(): anchor=" + view + ", xoff=" + i + ", yoff=" + i2 + ", isShowing(): " + isShowing());
        }
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
        this.mWindowPresenter.show(layoutParamsCreatePopupLayoutParams, getTransitionEpicenter(), isLayoutInsetDecor(), view.getLayoutDirection());
    }

    @Override // android.widget.PopupWindow
    protected void attachToAnchor(View view, int i, int i2, int i3) {
        super.attachToAnchor(view, i, i2, i3);
        view.addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
    }

    @Override // android.widget.PopupWindow
    protected void detachFromAnchor() {
        View anchor = getAnchor();
        if (anchor != null) {
            anchor.removeOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
        }
        super.detachFromAnchor();
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        setShowing(false);
        setTransitioningToDismiss(true);
        this.mWindowPresenter.hide(getTransitionEpicenter());
        detachFromAnchor();
        if (getOnDismissListener() != null) {
            getOnDismissListener().onDismiss();
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

    private class WindowPresenter {
        final IAutofillWindowPresenter mPresenter;

        WindowPresenter(AutofillPopupWindow autofillPopupWindow, IAutofillWindowPresenter iAutofillWindowPresenter) {
            this.mPresenter = iAutofillWindowPresenter;
        }

        void show(WindowManager.LayoutParams layoutParams, Rect rect, boolean z, int i) {
            try {
                this.mPresenter.show(layoutParams, rect, z, i);
            } catch (RemoteException e) {
                Log.w(AutofillPopupWindow.TAG, "Error showing fill window", e);
                e.rethrowFromSystemServer();
            }
        }

        void hide(Rect rect) {
            try {
                this.mPresenter.hide(rect);
            } catch (RemoteException e) {
                Log.w(AutofillPopupWindow.TAG, "Error hiding fill window", e);
                e.rethrowFromSystemServer();
            }
        }
    }
}
