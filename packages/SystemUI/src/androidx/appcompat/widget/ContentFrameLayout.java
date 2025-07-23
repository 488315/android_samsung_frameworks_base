package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDelegateImpl;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r14, int r15) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ContentFrameLayout.onMeasure(int, int):void");
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
