package com.android.internal.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.android.internal.C4337R;
import com.android.internal.view.menu.MenuPresenter;

/* loaded from: classes5.dex */
public class MenuPopupHelper implements MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private View mAnchorView;
    private final Context mContext;
    private int mDropDownGravity;
    private boolean mForceShowIcon;
    private final PopupWindow.OnDismissListener mInternalOnDismissListener;
    private boolean mIsContextMenu;
    private boolean mIsThemeDeviceDefaultFamily;
    private final MenuBuilder mMenu;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private MenuPopup mPopup;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;

    public MenuPopupHelper(Context context, MenuBuilder menu) {
        this(context, menu, null, false, 16843520, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView) {
        this(context, menu, anchorView, false, 16843520, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly, int popupStyleAttr) {
        this(context, menu, anchorView, overflowOnly, popupStyleAttr, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly, int popupStyleAttr, int popupStyleRes) {
        this.mDropDownGravity = Gravity.START;
        this.mInternalOnDismissListener = new PopupWindow.OnDismissListener() { // from class: com.android.internal.view.menu.MenuPopupHelper.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                MenuPopupHelper.this.onDismiss();
            }
        };
        this.mContext = context;
        this.mMenu = menu;
        this.mAnchorView = anchorView;
        this.mOverflowOnly = overflowOnly;
        this.mPopupStyleAttr = popupStyleAttr;
        this.mPopupStyleRes = popupStyleRes;
        TypedValue themeValue = new TypedValue();
        context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, themeValue, true);
        this.mIsThemeDeviceDefaultFamily = themeValue.data != 0;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public void setAnchorView(View anchor) {
        this.mAnchorView = anchor;
    }

    public void setForceShowIcon(boolean forceShowIcon) {
        this.mForceShowIcon = forceShowIcon;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup != null) {
            menuPopup.setForceShowIcon(forceShowIcon);
        }
    }

    public void setGravity(int gravity) {
        this.mDropDownGravity = gravity;
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int x, int y) {
        if (!tryShow(x, y)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public MenuPopup getPopup() {
        if (this.mPopup == null) {
            this.mPopup = createPopup();
        }
        return this.mPopup;
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int x, int y) {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(x, y, true, true);
        return true;
    }

    private MenuPopup createPopup() {
        MenuPopup popup;
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        Rect maxWindowBounds = windowManager.getMaximumWindowMetrics().getBounds();
        int smallestWidth = Math.min(maxWindowBounds.width(), maxWindowBounds.height());
        int minSmallestWidthCascading = this.mContext.getResources().getDimensionPixelSize(C4337R.dimen.cascading_menus_min_smallest_width);
        boolean enableCascadingSubmenus = smallestWidth >= minSmallestWidthCascading;
        if (enableCascadingSubmenus) {
            popup = new CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
        } else {
            popup = new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
        }
        popup.addMenu(this.mMenu);
        popup.setOnDismissListener(this.mInternalOnDismissListener);
        popup.setAnchorView(this.mAnchorView);
        popup.setCallback(this.mPresenterCallback);
        popup.setForceShowIcon(this.mForceShowIcon);
        popup.setGravity(this.mDropDownGravity);
        return popup;
    }

    private void showPopup(int xOffset, int yOffset, boolean useOffsets, boolean showTitle) {
        MenuPopup popup = getPopup();
        popup.setShowTitle(showTitle);
        if (useOffsets) {
            int hgrav = Gravity.getAbsoluteGravity(this.mDropDownGravity, this.mAnchorView.getLayoutDirection()) & 7;
            if (this.mIsThemeDeviceDefaultFamily && !this.mIsContextMenu) {
                boolean isRtl = this.mAnchorView.getLayoutDirection() == 1;
                xOffset -= this.mContext.getResources().getDimensionPixelOffset(C4337R.dimen.sem_menu_popup_horizontal_offset);
                popup.setHorizontalOffset(isRtl ? -xOffset : xOffset);
            } else {
                if (hgrav == 5) {
                    xOffset -= this.mAnchorView.getWidth();
                }
                popup.setHorizontalOffset(xOffset);
            }
            popup.setVerticalOffset(yOffset);
            float density = this.mContext.getResources().getDisplayMetrics().density;
            int halfSize = (int) ((48.0f * density) / 2.0f);
            Rect epicenter = new Rect(xOffset - halfSize, yOffset - halfSize, xOffset + halfSize, yOffset + halfSize);
            popup.setEpicenterBounds(epicenter);
        }
        popup.show();
    }

    @Override // com.android.internal.view.menu.MenuHelper
    public void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    protected void onDismiss() {
        this.mPopup = null;
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public boolean isShowing() {
        MenuPopup menuPopup = this.mPopup;
        return menuPopup != null && menuPopup.isShowing();
    }

    @Override // com.android.internal.view.menu.MenuHelper
    public void setPresenterCallback(MenuPresenter.Callback cb) {
        this.mPresenterCallback = cb;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup != null) {
            menuPopup.setCallback(cb);
        }
    }

    protected void setIsContextMenuPopup(boolean isContextMenu) {
        this.mIsContextMenu = isContextMenu;
    }
}
