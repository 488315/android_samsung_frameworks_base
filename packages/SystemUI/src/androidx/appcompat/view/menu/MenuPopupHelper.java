package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MenuPopupHelper {
    public final boolean mAllowScrollingAnchorParent;
    public View mAnchorView;
    public final Context mContext;
    public int mDropDownGravity;
    public boolean mForceShowIcon;
    public final AnonymousClass1 mInternalOnDismissListener;
    public final MenuBuilder mMenu;
    public AnonymousClass1 mOnDismissListener;
    public final boolean mOverflowOnly;
    public StandardMenuPopup mPopup;
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public MenuPresenter.Callback mPresenterCallback;
    public final int mSeslPopupHeight;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.appcompat.view.menu.MenuPopupHelper$1, reason: invalid class name */
    public class AnonymousClass1 implements PopupWindow.OnDismissListener {
        public AnonymousClass1() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public final void onDismiss() {
            MenuPopupHelper.this.onDismiss();
        }
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0);
    }

    public final MenuPopup getPopup() {
        MenuPopupWindow menuPopupWindow;
        if (this.mPopup == null) {
            Display defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            Math.min(point.x, point.y);
            this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width);
            StandardMenuPopup standardMenuPopup = new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
            int i = this.mSeslPopupHeight;
            if (i != -1 && (menuPopupWindow = standardMenuPopup.mPopup) != null) {
                if (i < 0 && -2 != i && -1 != i) {
                    throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
                }
                menuPopupWindow.mDropDownHeight = i;
            }
            if (!this.mAllowScrollingAnchorParent) {
                standardMenuPopup.mAllowScrollingAnchorParent = false;
            }
            standardMenuPopup.mOnDismissListener = this.mInternalOnDismissListener;
            standardMenuPopup.mAnchorView = this.mAnchorView;
            standardMenuPopup.mPresenterCallback = this.mPresenterCallback;
            standardMenuPopup.mAdapter.mForceShowIcon = this.mForceShowIcon;
            standardMenuPopup.mDropDownGravity = this.mDropDownGravity;
            this.mPopup = standardMenuPopup;
        }
        return this.mPopup;
    }

    public final boolean isShowing() {
        StandardMenuPopup standardMenuPopup = this.mPopup;
        return standardMenuPopup != null && standardMenuPopup.isShowing();
    }

    public void onDismiss() {
        this.mPopup = null;
        AnonymousClass1 anonymousClass1 = this.mOnDismissListener;
        if (anonymousClass1 != null) {
            anonymousClass1.onDismiss();
        }
    }

    public final void showPopup(boolean z, boolean z2) {
        MenuPopup popup = getPopup();
        ((StandardMenuPopup) popup).mShowTitle = z2;
        if (z) {
            View view = this.mAnchorView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z3 = view.getLayoutDirection() == 1;
            int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sesl_menu_popup_offset_horizontal);
            if (z3) {
                ((StandardMenuPopup) popup).mPopup.mDropDownHorizontalOffset = dimensionPixelOffset;
            } else {
                ((StandardMenuPopup) popup).mPopup.mDropDownHorizontalOffset = 0 - dimensionPixelOffset;
            }
            ((StandardMenuPopup) popup).mPopup.setVerticalOffset(0);
            int i = (int) ((this.mContext.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            int i2 = 0 - i;
            popup.mEpicenterBounds = new Rect(i2, i2, i, i);
        }
        ((StandardMenuPopup) popup).show();
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean z, int i) {
        this(context, menuBuilder, view, z, i, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean z, int i, int i2) {
        this.mDropDownGravity = 8388611;
        this.mAllowScrollingAnchorParent = true;
        this.mSeslPopupHeight = -1;
        this.mInternalOnDismissListener = new AnonymousClass1();
        this.mContext = context;
        this.mMenu = menuBuilder;
        this.mAnchorView = view;
        this.mOverflowOnly = z;
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
    }
}
