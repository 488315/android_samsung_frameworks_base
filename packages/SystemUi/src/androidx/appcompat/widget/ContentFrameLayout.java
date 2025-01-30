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
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ContentFrameLayout extends FrameLayout {
    public AppCompatDelegateImpl.C00305 mAttachListener;
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
        AppCompatDelegateImpl.C00305 c00305 = this.mAttachListener;
        if (c00305 != null) {
            c00305.getClass();
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
        AppCompatDelegateImpl.C00305 c00305 = this.mAttachListener;
        if (c00305 != null) {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            DecorContentParent decorContentParent = appCompatDelegateImpl.mDecorContentParent;
            if (decorContentParent != null) {
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decorContentParent;
                actionBarOverlayLayout.pullChildren();
                ActionMenuView actionMenuView = ((ToolbarWidgetWrapper) actionBarOverlayLayout.mDecorToolbar).mToolbar.mMenuView;
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ae  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        int makeMeasureSpec;
        TypedValue typedValue;
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
        if (mode == Integer.MIN_VALUE) {
            TypedValue typedValue2 = z3 ? this.mFixedWidthMinor : this.mFixedWidthMajor;
            if (typedValue2 != null && (i6 = typedValue2.type) != 0) {
                if (i6 == 5) {
                    fraction3 = typedValue2.getDimension(displayMetrics);
                } else if (i6 == 6) {
                    int i9 = displayMetrics.widthPixels;
                    fraction3 = typedValue2.getFraction(i9, i9);
                } else {
                    i7 = 0;
                    if (i7 > 0) {
                        Rect rect = this.mDecorPadding;
                        i = View.MeasureSpec.makeMeasureSpec(Math.min(i7 - (rect.left + rect.right), View.MeasureSpec.getSize(i)), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                        z = true;
                        if (mode2 == Integer.MIN_VALUE) {
                            TypedValue typedValue3 = z3 ? this.mFixedHeightMajor : this.mFixedHeightMinor;
                            if (typedValue3 != null && (i4 = typedValue3.type) != 0) {
                                if (i4 == 5) {
                                    fraction2 = typedValue3.getDimension(displayMetrics);
                                } else if (i4 == 6) {
                                    int i10 = displayMetrics.heightPixels;
                                    fraction2 = typedValue3.getFraction(i10, i10);
                                } else {
                                    i5 = 0;
                                    if (i5 > 0) {
                                        Rect rect2 = this.mDecorPadding;
                                        i2 = View.MeasureSpec.makeMeasureSpec(Math.min(i5 - (rect2.top + rect2.bottom), View.MeasureSpec.getSize(i2)), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                    }
                                }
                                i5 = (int) fraction2;
                                if (i5 > 0) {
                                }
                            }
                        }
                        super.onMeasure(i, i2);
                        makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                        if (!z && mode == Integer.MIN_VALUE) {
                            typedValue = !z3 ? this.mMinWidthMinor : this.mMinWidthMajor;
                            if (typedValue != null && (i3 = typedValue.type) != 0) {
                                if (i3 == 5) {
                                    if (i3 == 6) {
                                        updateAvailableWidth();
                                        float f = this.mAvailableWidth;
                                        fraction = typedValue.getFraction(f, f);
                                    }
                                    if (i8 > 0) {
                                        Rect rect3 = this.mDecorPadding;
                                        i8 -= rect3.left + rect3.right;
                                    }
                                    makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                    if (z2) {
                                        super.onMeasure(makeMeasureSpec, i2);
                                        return;
                                    }
                                    return;
                                }
                                fraction = typedValue.getDimension(displayMetrics);
                                i8 = (int) fraction;
                                if (i8 > 0) {
                                }
                                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                                if (z2) {
                                }
                            }
                        }
                        z2 = false;
                        if (z2) {
                        }
                    }
                }
                i7 = (int) fraction3;
                if (i7 > 0) {
                }
            }
        }
        z = false;
        if (mode2 == Integer.MIN_VALUE) {
        }
        super.onMeasure(i, i2);
        makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        if (!z) {
            if (!z3) {
            }
            if (typedValue != null) {
                if (i3 == 5) {
                }
                i8 = (int) fraction;
                if (i8 > 0) {
                }
                makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                if (z2) {
                }
            }
        }
        z2 = false;
        if (z2) {
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
