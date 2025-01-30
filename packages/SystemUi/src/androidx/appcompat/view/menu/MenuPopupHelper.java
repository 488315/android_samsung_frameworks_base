package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MenuPopupHelper {
    public final boolean mAllowScrollingAnchorParent;
    public View mAnchorView;
    public final Context mContext;
    public int mDropDownGravity;
    public boolean mForceShowIcon;
    public boolean mForceShowUpper;
    public final C00451 mInternalOnDismissListener;
    public final MenuBuilder mMenu;
    public PopupWindow.OnDismissListener mOnDismissListener;
    public final boolean mOverflowOnly;
    public boolean mOverlapAnchor;
    public boolean mOverlapAnchorSet;
    public StandardMenuPopup mPopup;
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public MenuPresenter.Callback mPresenterCallback;

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0);
    }

    public final MenuPopup getPopup() {
        if (this.mPopup == null) {
            Context context = this.mContext;
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            Math.min(point.x, point.y);
            context.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width);
            StandardMenuPopup standardMenuPopup = new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
            if (this.mOverlapAnchorSet) {
                boolean z = this.mOverlapAnchor;
                standardMenuPopup.mOverlapAnchorSet = true;
                standardMenuPopup.mOverlapAnchor = z;
                standardMenuPopup.mForceShowUpper = this.mForceShowUpper;
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
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public final void showPopup(boolean z, boolean z2) {
        MenuPopup popup = getPopup();
        ((StandardMenuPopup) popup).mShowTitle = z2;
        if (z) {
            View view = this.mAnchorView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z3 = ViewCompat.Api17Impl.getLayoutDirection(view) == 1;
            Context context = this.mContext;
            int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.sesl_menu_popup_offset_horizontal);
            if (z3) {
                ((StandardMenuPopup) popup).mPopup.mDropDownHorizontalOffset = dimensionPixelOffset + 0;
            } else {
                ((StandardMenuPopup) popup).mPopup.mDropDownHorizontalOffset = 0 - dimensionPixelOffset;
            }
            popup.setVerticalOffset(0);
            int i = (int) ((context.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            int i2 = 0 - i;
            int i3 = 0 + i;
            popup.mEpicenterBounds = new Rect(i2, i2, i3, i3);
        }
        ((StandardMenuPopup) popup).show();
    }

    public final boolean tryShow$1() {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(true, true);
        return true;
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean z, int i) {
        this(context, menuBuilder, view, z, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.appcompat.view.menu.MenuPopupHelper$1] */
    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean z, int i, int i2) {
        this.mDropDownGravity = 8388611;
        this.mForceShowUpper = false;
        this.mAllowScrollingAnchorParent = true;
        this.mInternalOnDismissListener = new PopupWindow.OnDismissListener() { // from class: androidx.appcompat.view.menu.MenuPopupHelper.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                MenuPopupHelper.this.onDismiss();
            }
        };
        this.mContext = context;
        this.mMenu = menuBuilder;
        this.mAnchorView = view;
        this.mOverflowOnly = z;
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
    }
}
