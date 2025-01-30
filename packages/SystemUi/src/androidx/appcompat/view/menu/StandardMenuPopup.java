package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.AppCompatPopupWindow;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.widget.SeslPopupWindowReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StandardMenuPopup extends MenuPopup implements PopupWindow.OnDismissListener, View.OnKeyListener {
    public final MenuAdapter mAdapter;
    public View mAnchorView;
    public int mContentWidth;
    public final Context mContext;
    public boolean mForceShowUpper;
    public boolean mHasContentWidth;
    public final boolean mIsSubMenu;
    public final MenuBuilder mMenu;
    public PopupWindow.OnDismissListener mOnDismissListener;
    public final boolean mOverflowOnly;
    public boolean mOverlapAnchor;
    public boolean mOverlapAnchorSet;
    public final MenuPopupWindow mPopup;
    public final int mPopupMaxWidth;
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public MenuPresenter.Callback mPresenterCallback;
    public boolean mShowTitle;
    public View mShownAnchorView;
    public ViewTreeObserver mTreeObserver;
    public boolean mWasDismissed;
    public DropDownListView mTmpListView = null;
    public boolean mAllowScrollingAnchorParent = true;
    public final ViewTreeObserverOnGlobalLayoutListenerC00461 mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.StandardMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            if (StandardMenuPopup.this.isShowing()) {
                View view = StandardMenuPopup.this.mShownAnchorView;
                if (view == null || !view.isShown()) {
                    StandardMenuPopup.this.dismiss();
                } else {
                    StandardMenuPopup.this.mPopup.show();
                }
            }
        }
    };
    public final ViewOnAttachStateChangeListenerC00472 mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.StandardMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = StandardMenuPopup.this.mTreeObserver;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    StandardMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                StandardMenuPopup standardMenuPopup = StandardMenuPopup.this;
                standardMenuPopup.mTreeObserver.removeGlobalOnLayoutListener(standardMenuPopup.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    };
    public int mDropDownGravity = 0;

    /* JADX WARN: Type inference failed for: r3v0, types: [androidx.appcompat.view.menu.StandardMenuPopup$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [androidx.appcompat.view.menu.StandardMenuPopup$2] */
    public StandardMenuPopup(Context context, MenuBuilder menuBuilder, View view, int i, int i2, boolean z) {
        boolean z2 = false;
        this.mIsSubMenu = false;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.popupTheme, typedValue, false);
        if (typedValue.data != 0) {
            this.mContext = new ContextThemeWrapper(context, typedValue.data);
        } else {
            this.mContext = context;
        }
        this.mMenu = menuBuilder;
        this.mIsSubMenu = menuBuilder instanceof SubMenuBuilder;
        this.mOverflowOnly = z;
        LayoutInflater from = LayoutInflater.from(context);
        int size = menuBuilder.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            if ((((MenuItemImpl) this.mMenu.getItem(i3)).mFlags & 4) != 0) {
                z2 = true;
                break;
            }
            i3++;
        }
        if (z2) {
            this.mAdapter = new MenuAdapter(menuBuilder, from, this.mOverflowOnly, com.android.systemui.R.layout.sesl_popup_sub_menu_item_layout);
        } else {
            this.mAdapter = new MenuAdapter(menuBuilder, from, this.mOverflowOnly, com.android.systemui.R.layout.sesl_popup_menu_item_layout);
        }
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
        this.mPopupMaxWidth = context.getResources().getDisplayMetrics().widthPixels - (this.mContext.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_menu_popup_offset_horizontal) * 2);
        this.mAnchorView = view;
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, i, i2);
        this.mPopup = menuPopupWindow;
        menuPopupWindow.mIsOverflowPopup = this.mOverflowOnly;
        menuBuilder.addMenuPresenter(this, context);
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final DropDownListView getListView() {
        return this.mPopup.mDropDownList;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final boolean isShowing() {
        return !this.mWasDismissed && this.mPopup.isShowing();
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (menuBuilder != this.mMenu) {
            return;
        }
        dismiss();
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z);
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.mWasDismissed = true;
        this.mMenu.close(true);
        ViewTreeObserver viewTreeObserver = this.mTreeObserver;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            }
            this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            this.mTreeObserver = null;
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final Parcelable onSaveInstanceState() {
        return null;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        MenuItem menuItem;
        if (subMenuBuilder.hasVisibleItems()) {
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.mContext, subMenuBuilder, this.mShownAnchorView, this.mOverflowOnly, this.mPopupStyleAttr, this.mPopupStyleRes);
            MenuPresenter.Callback callback = this.mPresenterCallback;
            menuPopupHelper.mPresenterCallback = callback;
            StandardMenuPopup standardMenuPopup = menuPopupHelper.mPopup;
            if (standardMenuPopup != null) {
                standardMenuPopup.mPresenterCallback = callback;
            }
            boolean shouldPreserveIconSpacing = MenuPopup.shouldPreserveIconSpacing(subMenuBuilder);
            menuPopupHelper.mForceShowIcon = shouldPreserveIconSpacing;
            StandardMenuPopup standardMenuPopup2 = menuPopupHelper.mPopup;
            if (standardMenuPopup2 != null) {
                standardMenuPopup2.mAdapter.mForceShowIcon = shouldPreserveIconSpacing;
            }
            menuPopupHelper.mOnDismissListener = this.mOnDismissListener;
            View view = null;
            this.mOnDismissListener = null;
            int size = this.mMenu.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    menuItem = null;
                    break;
                }
                menuItem = this.mMenu.getItem(i);
                if (menuItem.hasSubMenu() && subMenuBuilder == menuItem.getSubMenu()) {
                    break;
                }
                i++;
            }
            int count = this.mAdapter.getCount();
            int i2 = 0;
            while (true) {
                if (i2 >= count) {
                    i2 = -1;
                    break;
                }
                if (menuItem == this.mAdapter.getItem(i2)) {
                    break;
                }
                i2++;
            }
            DropDownListView dropDownListView = this.mTmpListView;
            if (dropDownListView != null) {
                int firstVisiblePosition = i2 - dropDownListView.getFirstVisiblePosition();
                if (firstVisiblePosition >= 0) {
                    this.mTmpListView.getChildCount();
                }
                view = this.mTmpListView.getChildAt(firstVisiblePosition);
            }
            if (view != null) {
                view.getMeasuredHeight();
            }
            menuPopupHelper.mDropDownGravity = this.mDropDownGravity;
            this.mMenu.close(false);
            if (menuPopupHelper.tryShow$1()) {
                MenuPresenter.Callback callback2 = this.mPresenterCallback;
                if (callback2 == null) {
                    return true;
                }
                callback2.onOpenSubMenu(subMenuBuilder);
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setVerticalOffset(int i) {
        this.mPopup.setVerticalOffset(0);
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final void show() {
        View view;
        Rect rect;
        AppCompatPopupWindow appCompatPopupWindow;
        Method declaredMethod;
        boolean z = true;
        if (!isShowing()) {
            if (this.mWasDismissed || (view = this.mAnchorView) == null) {
                z = false;
            } else {
                this.mShownAnchorView = view;
                if (this.mOverlapAnchorSet) {
                    MenuPopupWindow menuPopupWindow = this.mPopup;
                    boolean z2 = this.mOverlapAnchor;
                    menuPopupWindow.mOverlapAnchorSet = true;
                    menuPopupWindow.mOverlapAnchor = z2;
                    menuPopupWindow.mForceShowUpper = this.mForceShowUpper;
                }
                boolean z3 = this.mAllowScrollingAnchorParent;
                if (!z3 && (appCompatPopupWindow = this.mPopup.mPopup) != null && (declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslPopupWindowReflector.mClass, "setAllowScrollingAnchorParent", Boolean.TYPE)) != null) {
                    SeslBaseReflector.invoke(appCompatPopupWindow, declaredMethod, Boolean.valueOf(z3));
                }
                this.mPopup.mPopup.setOnDismissListener(this);
                MenuPopupWindow menuPopupWindow2 = this.mPopup;
                menuPopupWindow2.mItemClickListener = this;
                menuPopupWindow2.mModal = true;
                menuPopupWindow2.mPopup.setFocusable(true);
                View view2 = this.mShownAnchorView;
                boolean z4 = this.mTreeObserver == null;
                ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
                this.mTreeObserver = viewTreeObserver;
                if (z4) {
                    viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
                }
                view2.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
                MenuPopupWindow menuPopupWindow3 = this.mPopup;
                menuPopupWindow3.mDropDownAnchorView = view2;
                menuPopupWindow3.mDropDownGravity = this.mDropDownGravity;
                if (!this.mHasContentWidth) {
                    this.mContentWidth = MenuPopup.measureIndividualMenuWidth(this.mAdapter, this.mContext, this.mPopupMaxWidth);
                    this.mHasContentWidth = true;
                }
                this.mPopup.setContentWidth(this.mContentWidth);
                this.mPopup.mPopup.setInputMethodMode(2);
                MenuPopupWindow menuPopupWindow4 = this.mPopup;
                Rect rect2 = this.mEpicenterBounds;
                if (rect2 != null) {
                    menuPopupWindow4.getClass();
                    rect = new Rect(rect2);
                } else {
                    rect = null;
                }
                menuPopupWindow4.mEpicenterBounds = rect;
                this.mPopup.show();
                DropDownListView dropDownListView = this.mPopup.mDropDownList;
                dropDownListView.setOnKeyListener(this);
                boolean z5 = this.mIsSubMenu;
                this.mTmpListView = z5 ? null : dropDownListView;
                if (this.mShowTitle && this.mMenu.mHeaderTitle != null && !z5) {
                    FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(com.android.systemui.R.layout.sesl_popup_menu_header_item_layout, (ViewGroup) dropDownListView, false);
                    TextView textView = (TextView) frameLayout.findViewById(R.id.title);
                    if (textView != null) {
                        textView.setText(this.mMenu.mHeaderTitle);
                    }
                    frameLayout.setEnabled(false);
                    dropDownListView.addHeaderView(frameLayout, null, false);
                }
                this.mPopup.setAdapter(this.mAdapter);
                this.mPopup.show();
            }
        }
        if (!z) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        this.mHasContentWidth = false;
        MenuAdapter menuAdapter = this.mAdapter;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onRestoreInstanceState(Parcelable parcelable) {
    }
}
