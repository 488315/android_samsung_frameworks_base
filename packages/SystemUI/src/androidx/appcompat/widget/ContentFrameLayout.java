package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDelegateImpl;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class ContentFrameLayout extends FrameLayout {
    public AppCompatDelegateImpl.AnonymousClass5 mAttachListener;
    public float mAvailableWidth;
    public final Rect mDecorPadding;
    public TypedValue mFixedHeightMajor;
    public TypedValue mFixedHeightMinor;
    public TypedValue mFixedWidthMajor;
    public TypedValue mFixedWidthMinor;
    public TypedValue mMinWidthMajor;
    public TypedValue mMinWidthMinor;

    public ContentFrameLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        AppCompatDelegateImpl.AnonymousClass5 anonymousClass5 = this.mAttachListener;
        if (anonymousClass5 != null) {
            anonymousClass5.getClass();
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mMinWidthMinor == null) {
            this.mMinWidthMinor = new TypedValue();
        }
        getContext().getTheme().resolveAttribute(R.attr.windowMinWidthMinor, this.mMinWidthMinor, true);
        if (this.mMinWidthMajor == null) {
            this.mMinWidthMajor = new TypedValue();
        }
        getContext().getTheme().resolveAttribute(R.attr.windowMinWidthMajor, this.mMinWidthMajor, true);
        updateAvailableWidth();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        ActionMenuPresenter actionMenuPresenter;
        super.onDetachedFromWindow();
        AppCompatDelegateImpl.AnonymousClass5 anonymousClass5 = this.mAttachListener;
        if (anonymousClass5 != null) {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            DecorContentParent decorContentParent = appCompatDelegateImpl.mDecorContentParent;
            if (decorContentParent != null) {
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
                actionBarOverlayLayout.pullChildren();
                ActionMenuView actionMenuView = actionBarOverlayLayout.mDecorToolbar.mToolbar.mMenuView;
                if (actionMenuView != null && (actionMenuPresenter = actionMenuView.mPresenter) != null) {
                    actionMenuPresenter.hideOverflowMenu();
                    ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
                    if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
                        actionButtonSubmenu.mPopup.dismiss();
                    }
                }
            }
            if (appCompatDelegateImpl.mActionModePopup != null) {
                appCompatDelegateImpl.mWindow.getDecorView().removeCallbacks(appCompatDelegateImpl.mShowActionModePopup);
                if (appCompatDelegateImpl.mActionModePopup.isShowing()) {
                    try {
                        appCompatDelegateImpl.mActionModePopup.dismiss();
                    } catch (IllegalArgumentException unused) {
                    }
                }
                appCompatDelegateImpl.mActionModePopup = null;
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = appCompatDelegateImpl.mFadeAnim;
            if (viewPropertyAnimatorCompat != null) {
                viewPropertyAnimatorCompat.cancel();
            }
            MenuBuilder menuBuilder = appCompatDelegateImpl.getPanelState(0).menu;
            if (menuBuilder != null) {
                menuBuilder.close(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d9  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        float fraction;
        int i4;
        int i5;
        float fraction2;
        int i6;
        int i7;
        float fraction3;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        boolean z2 = true;
        int i8 = 0;
        boolean z3 = displayMetrics.widthPixels < displayMetrics.heightPixels;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode != Integer.MIN_VALUE) {
            z = false;
        } else {
            TypedValue typedValue = z3 ? this.mFixedWidthMinor : this.mFixedWidthMajor;
            if (typedValue != null && (i6 = typedValue.type) != 0) {
                if (i6 == 5) {
                    fraction3 = typedValue.getDimension(displayMetrics);
                } else if (i6 == 6) {
                    int i9 = displayMetrics.widthPixels;
                    fraction3 = typedValue.getFraction(i9, i9);
                } else {
                    i7 = 0;
                    if (i7 <= 0) {
                        Rect rect = this.mDecorPadding;
                        i = View.MeasureSpec.makeMeasureSpec(Math.min(i7 - (rect.left + rect.right), View.MeasureSpec.getSize(i)), 1073741824);
                        z = true;
                    }
                }
                i7 = (int) fraction3;
                if (i7 <= 0) {
                }
            }
        }
        if (mode2 == Integer.MIN_VALUE) {
            TypedValue typedValue2 = z3 ? this.mFixedHeightMajor : this.mFixedHeightMinor;
            if (typedValue2 != null && (i4 = typedValue2.type) != 0) {
                if (i4 == 5) {
                    fraction2 = typedValue2.getDimension(displayMetrics);
                } else if (i4 == 6) {
                    int i10 = displayMetrics.heightPixels;
                    fraction2 = typedValue2.getFraction(i10, i10);
                } else {
                    i5 = 0;
                    if (i5 > 0) {
                        Rect rect2 = this.mDecorPadding;
                        i2 = View.MeasureSpec.makeMeasureSpec(Math.min(i5 - (rect2.top + rect2.bottom), View.MeasureSpec.getSize(i2)), 1073741824);
                    }
                }
                i5 = (int) fraction2;
                if (i5 > 0) {
                }
            }
        }
        super.onMeasure(i, i2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        if (z || mode != Integer.MIN_VALUE) {
            z2 = false;
        } else {
            TypedValue typedValue3 = z3 ? this.mMinWidthMinor : this.mMinWidthMajor;
            if (typedValue3 != null && (i3 = typedValue3.type) != 0) {
                if (i3 == 5) {
                    fraction = typedValue3.getDimension(displayMetrics);
                } else {
                    if (i3 == 6) {
                        updateAvailableWidth();
                        float f = this.mAvailableWidth;
                        fraction = typedValue3.getFraction(f, f);
                    }
                    if (i8 > 0) {
                        Rect rect3 = this.mDecorPadding;
                        i8 -= rect3.left + rect3.right;
                    }
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
                }
                i8 = (int) fraction;
                if (i8 > 0) {
                }
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
            }
        }
        if (z2) {
            super.onMeasure(iMakeMeasureSpec, i2);
        }
    }

    public final void updateAvailableWidth() {
        this.mAvailableWidth = TypedValue.applyDimension(1, r0.getConfiguration().screenWidthDp, getResources().getDisplayMetrics());
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDecorPadding = new Rect();
        updateAvailableWidth();
    }
}
