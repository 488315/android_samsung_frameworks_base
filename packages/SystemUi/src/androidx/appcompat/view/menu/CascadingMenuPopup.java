package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.AppCompatPopupWindow;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CascadingMenuPopup extends MenuPopup implements View.OnKeyListener, PopupWindow.OnDismissListener {
    public View mAnchorView;
    public final Context mContext;
    public boolean mForceShowIcon;
    public boolean mHasXOffset;
    public boolean mHasYOffset;
    public int mLastPosition;
    public final int mMenuMaxWidth;
    public final boolean mOverflowOnly;
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public MenuPresenter.Callback mPresenterCallback;
    public boolean mShouldCloseImmediately;
    public boolean mShowTitle;
    public View mShownAnchorView;
    public final Handler mSubMenuHoverHandler;
    public ViewTreeObserver mTreeObserver;
    public int mXOffset;
    public int mYOffset;
    public final List mPendingMenus = new ArrayList();
    public final List mShowingMenus = new ArrayList();
    public final ViewTreeObserverOnGlobalLayoutListenerC00411 mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            if (!CascadingMenuPopup.this.isShowing() || ((ArrayList) CascadingMenuPopup.this.mShowingMenus).size() <= 0 || ((CascadingMenuInfo) ((ArrayList) CascadingMenuPopup.this.mShowingMenus).get(0)).window.mModal) {
                return;
            }
            View view = CascadingMenuPopup.this.mShownAnchorView;
            if (view == null || !view.isShown()) {
                CascadingMenuPopup.this.dismiss();
                return;
            }
            Iterator it = ((ArrayList) CascadingMenuPopup.this.mShowingMenus).iterator();
            while (it.hasNext()) {
                ((CascadingMenuInfo) it.next()).window.show();
            }
        }
    };
    public final ViewOnAttachStateChangeListenerC00422 mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = CascadingMenuPopup.this.mTreeObserver;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                CascadingMenuPopup cascadingMenuPopup = CascadingMenuPopup.this;
                cascadingMenuPopup.mTreeObserver.removeGlobalOnLayoutListener(cascadingMenuPopup.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    };
    public final C00433 mMenuItemHoverListener = new MenuItemHoverListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3
        @Override // androidx.appcompat.widget.MenuItemHoverListener
        public final void onItemHoverEnter(final MenuBuilder menuBuilder, final MenuItemImpl menuItemImpl) {
            CascadingMenuPopup cascadingMenuPopup = CascadingMenuPopup.this;
            cascadingMenuPopup.mSubMenuHoverHandler.removeCallbacksAndMessages(null);
            int size = ((ArrayList) cascadingMenuPopup.mShowingMenus).size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (menuBuilder == ((CascadingMenuInfo) ((ArrayList) cascadingMenuPopup.mShowingMenus).get(i)).menu) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                return;
            }
            int i2 = i + 1;
            final CascadingMenuInfo cascadingMenuInfo = i2 < ((ArrayList) cascadingMenuPopup.mShowingMenus).size() ? (CascadingMenuInfo) ((ArrayList) cascadingMenuPopup.mShowingMenus).get(i2) : null;
            cascadingMenuPopup.mSubMenuHoverHandler.postAtTime(new Runnable() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3.1
                @Override // java.lang.Runnable
                public final void run() {
                    CascadingMenuInfo cascadingMenuInfo2 = cascadingMenuInfo;
                    if (cascadingMenuInfo2 != null) {
                        CascadingMenuPopup.this.mShouldCloseImmediately = true;
                        cascadingMenuInfo2.menu.close(false);
                        CascadingMenuPopup.this.mShouldCloseImmediately = false;
                    }
                    if (menuItemImpl.isEnabled() && menuItemImpl.hasSubMenu()) {
                        menuBuilder.performItemAction(menuItemImpl, null, 4);
                    }
                }
            }, menuBuilder, SystemClock.uptimeMillis() + 200);
        }

        @Override // androidx.appcompat.widget.MenuItemHoverListener
        public final void onItemHoverExit(MenuBuilder menuBuilder, MenuItem menuItem) {
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(menuBuilder);
        }
    };
    public int mDropDownGravity = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(MenuPopupWindow menuPopupWindow, MenuBuilder menuBuilder, int i) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = i;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.appcompat.view.menu.CascadingMenuPopup$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.appcompat.view.menu.CascadingMenuPopup$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.appcompat.view.menu.CascadingMenuPopup$3] */
    public CascadingMenuPopup(Context context, View view, int i, int i2, boolean z) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.popupTheme, typedValue, false);
        if (typedValue.data != 0) {
            this.mContext = new ContextThemeWrapper(context, typedValue.data);
        } else {
            this.mContext = context;
        }
        this.mAnchorView = view;
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
        this.mOverflowOnly = z;
        this.mForceShowIcon = false;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.mLastPosition = ViewCompat.Api17Impl.getLayoutDirection(view) != 1 ? 1 : 0;
        this.mMenuMaxWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mSubMenuHoverHandler = new Handler();
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final void dismiss() {
        int size = ((ArrayList) this.mShowingMenus).size();
        if (size > 0) {
            CascadingMenuInfo[] cascadingMenuInfoArr = (CascadingMenuInfo[]) ((ArrayList) this.mShowingMenus).toArray(new CascadingMenuInfo[size]);
            for (int i = size - 1; i >= 0; i--) {
                CascadingMenuInfo cascadingMenuInfo = cascadingMenuInfoArr[i];
                if (cascadingMenuInfo.window.isShowing()) {
                    cascadingMenuInfo.window.dismiss();
                }
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final DropDownListView getListView() {
        if (((ArrayList) this.mShowingMenus).isEmpty()) {
            return null;
        }
        return ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(r1.size() - 1)).window.mDropDownList;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final boolean isShowing() {
        return ((ArrayList) this.mShowingMenus).size() > 0 && ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(0)).window.isShowing();
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        int size = ((ArrayList) this.mShowingMenus).size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (menuBuilder == ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(i)).menu) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0) {
            return;
        }
        int i2 = i + 1;
        if (i2 < ((ArrayList) this.mShowingMenus).size()) {
            ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(i2)).menu.close(false);
        }
        CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) ((ArrayList) this.mShowingMenus).remove(i);
        cascadingMenuInfo.menu.removeMenuPresenter(this);
        boolean z2 = this.mShouldCloseImmediately;
        MenuPopupWindow menuPopupWindow = cascadingMenuInfo.window;
        if (z2) {
            menuPopupWindow.getClass();
            AppCompatPopupWindow appCompatPopupWindow = menuPopupWindow.mPopup;
            appCompatPopupWindow.setExitTransition(null);
            appCompatPopupWindow.setAnimationStyle(0);
        }
        menuPopupWindow.dismiss();
        int size2 = ((ArrayList) this.mShowingMenus).size();
        if (size2 > 0) {
            this.mLastPosition = ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(size2 - 1)).position;
        } else {
            View view = this.mAnchorView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mLastPosition = ViewCompat.Api17Impl.getLayoutDirection(view) == 1 ? 0 : 1;
        }
        if (size2 != 0) {
            if (z) {
                ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(0)).menu.close(false);
                return;
            }
            return;
        }
        dismiss();
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, true);
        }
        ViewTreeObserver viewTreeObserver = this.mTreeObserver;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            }
            this.mTreeObserver = null;
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        throw null;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        int size = ((ArrayList) this.mShowingMenus).size();
        int i = 0;
        while (true) {
            if (i >= size) {
                cascadingMenuInfo = null;
                break;
            }
            cascadingMenuInfo = (CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(i);
            if (!cascadingMenuInfo.window.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false);
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
        Iterator it = ((ArrayList) this.mShowingMenus).iterator();
        while (it.hasNext()) {
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) it.next();
            if (subMenuBuilder == cascadingMenuInfo.menu) {
                cascadingMenuInfo.window.mDropDownList.requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        subMenuBuilder.addMenuPresenter(this, this.mContext);
        if (isShowing()) {
            showMenu(subMenuBuilder);
        } else {
            ((ArrayList) this.mPendingMenus).add(subMenuBuilder);
        }
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setVerticalOffset(int i) {
        this.mHasYOffset = true;
        this.mYOffset = 0;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final void show() {
        if (isShowing()) {
            return;
        }
        Iterator it = ((ArrayList) this.mPendingMenus).iterator();
        while (it.hasNext()) {
            showMenu((MenuBuilder) it.next());
        }
        ((ArrayList) this.mPendingMenus).clear();
        View view = this.mAnchorView;
        this.mShownAnchorView = view;
        if (view != null) {
            boolean z = this.mTreeObserver == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.mTreeObserver = viewTreeObserver;
            if (z) {
                viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
            }
            this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x014e, code lost:
    
        if (((r6.getWidth() + r7[0]) + r3) > r8.right) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0156, code lost:
    
        r6 = 1;
        r7 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0159, code lost:
    
        r6 = 1;
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0154, code lost:
    
        if ((r7[0] - r3) < 0) goto L66;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showMenu(MenuBuilder menuBuilder) {
        boolean z;
        CascadingMenuInfo cascadingMenuInfo;
        View view;
        int i;
        int i2;
        int i3;
        MenuAdapter menuAdapter;
        int i4;
        int firstVisiblePosition;
        LayoutInflater from = LayoutInflater.from(this.mContext);
        int size = menuBuilder.size();
        int i5 = 0;
        while (true) {
            if (i5 >= size) {
                z = false;
                break;
            }
            if ((((MenuItemImpl) menuBuilder.getItem(i5)).mFlags & 4) != 0) {
                z = true;
                break;
            }
            i5++;
        }
        MenuAdapter menuAdapter2 = z ? new MenuAdapter(menuBuilder, from, this.mOverflowOnly, com.android.systemui.R.layout.sesl_popup_sub_menu_item_layout) : new MenuAdapter(menuBuilder, from, this.mOverflowOnly, com.android.systemui.R.layout.sesl_cascading_menu_item_layout);
        if (!isShowing() && this.mForceShowIcon) {
            menuAdapter2.mForceShowIcon = true;
        } else if (isShowing()) {
            menuAdapter2.mForceShowIcon = MenuPopup.shouldPreserveIconSpacing(menuBuilder);
        }
        int measureIndividualMenuWidth = MenuPopup.measureIndividualMenuWidth(menuAdapter2, this.mContext, this.mMenuMaxWidth);
        MenuItem menuItem = null;
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.mHoverListener = this.mMenuItemHoverListener;
        menuPopupWindow.mItemClickListener = this;
        AppCompatPopupWindow appCompatPopupWindow = menuPopupWindow.mPopup;
        appCompatPopupWindow.setOnDismissListener(this);
        menuPopupWindow.mDropDownAnchorView = this.mAnchorView;
        menuPopupWindow.mDropDownGravity = this.mDropDownGravity;
        menuPopupWindow.mModal = true;
        appCompatPopupWindow.setFocusable(true);
        appCompatPopupWindow.setInputMethodMode(2);
        menuPopupWindow.setAdapter(menuAdapter2);
        menuPopupWindow.setContentWidth(measureIndividualMenuWidth);
        menuPopupWindow.mDropDownGravity = this.mDropDownGravity;
        if (((ArrayList) this.mShowingMenus).size() > 0) {
            ArrayList arrayList = (ArrayList) this.mShowingMenus;
            cascadingMenuInfo = (CascadingMenuInfo) arrayList.get(arrayList.size() - 1);
            MenuBuilder menuBuilder2 = cascadingMenuInfo.menu;
            int size2 = menuBuilder2.size();
            int i6 = 0;
            while (true) {
                if (i6 >= size2) {
                    break;
                }
                MenuItem item = menuBuilder2.getItem(i6);
                if (item.hasSubMenu() && menuBuilder == item.getSubMenu()) {
                    menuItem = item;
                    break;
                }
                i6++;
            }
            if (menuItem != null) {
                DropDownListView dropDownListView = cascadingMenuInfo.window.mDropDownList;
                ListAdapter adapter = dropDownListView.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    i4 = headerViewListAdapter.getHeadersCount();
                    menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    menuAdapter = (MenuAdapter) adapter;
                    i4 = 0;
                }
                int count = menuAdapter.getCount();
                int i7 = 0;
                while (true) {
                    if (i7 >= count) {
                        i7 = -1;
                        break;
                    } else if (menuItem == menuAdapter.getItem(i7)) {
                        break;
                    } else {
                        i7++;
                    }
                }
                if (i7 != -1 && (firstVisiblePosition = (i7 + i4) - dropDownListView.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < dropDownListView.getChildCount()) {
                    view = dropDownListView.getChildAt(firstVisiblePosition);
                    if (view == null) {
                        appCompatPopupWindow.setTouchModal(false);
                        appCompatPopupWindow.setEnterTransition(null);
                        DropDownListView dropDownListView2 = ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(r6.size() - 1)).window.mDropDownList;
                        int[] iArr = new int[2];
                        dropDownListView2.getLocationOnScreen(iArr);
                        Rect rect = new Rect();
                        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
                        if (this.mLastPosition == 1) {
                        }
                        boolean z2 = i2 == i;
                        this.mLastPosition = i2;
                        menuPopupWindow.mDropDownAnchorView = view;
                        if ((this.mDropDownGravity & 5) == 5) {
                            if (!z2) {
                                measureIndividualMenuWidth = view.getWidth();
                                i3 = 0 - measureIndividualMenuWidth;
                            }
                            i3 = measureIndividualMenuWidth + 0;
                        } else {
                            if (z2) {
                                measureIndividualMenuWidth = view.getWidth();
                                i3 = measureIndividualMenuWidth + 0;
                            }
                            i3 = 0 - measureIndividualMenuWidth;
                        }
                        menuPopupWindow.mDropDownHorizontalOffset = i3;
                        menuPopupWindow.mOverlapAnchorSet = true;
                        menuPopupWindow.mOverlapAnchor = true;
                        menuPopupWindow.setVerticalOffset(0);
                    } else {
                        if (this.mHasXOffset) {
                            menuPopupWindow.mDropDownHorizontalOffset = this.mXOffset;
                        }
                        if (this.mHasYOffset) {
                            menuPopupWindow.setVerticalOffset(this.mYOffset);
                        }
                        Rect rect2 = this.mEpicenterBounds;
                        menuPopupWindow.mEpicenterBounds = rect2 != null ? new Rect(rect2) : null;
                    }
                    ((ArrayList) this.mShowingMenus).add(new CascadingMenuInfo(menuPopupWindow, menuBuilder, this.mLastPosition));
                    menuPopupWindow.show();
                    DropDownListView dropDownListView3 = menuPopupWindow.mDropDownList;
                    dropDownListView3.setOnKeyListener(this);
                    if (cascadingMenuInfo == null || !this.mShowTitle || menuBuilder.mHeaderTitle == null) {
                        return;
                    }
                    FrameLayout frameLayout = (FrameLayout) from.inflate(com.android.systemui.R.layout.sesl_popup_menu_header_item_layout, (ViewGroup) dropDownListView3, false);
                    TextView textView = (TextView) frameLayout.findViewById(R.id.title);
                    frameLayout.setEnabled(false);
                    textView.setText(menuBuilder.mHeaderTitle);
                    dropDownListView3.addHeaderView(frameLayout, null, false);
                    menuPopupWindow.show();
                    return;
                }
            }
        } else {
            cascadingMenuInfo = null;
        }
        view = null;
        if (view == null) {
        }
        ((ArrayList) this.mShowingMenus).add(new CascadingMenuInfo(menuPopupWindow, menuBuilder, this.mLastPosition));
        menuPopupWindow.show();
        DropDownListView dropDownListView32 = menuPopupWindow.mDropDownList;
        dropDownListView32.setOnKeyListener(this);
        if (cascadingMenuInfo == null) {
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        Iterator it = ((ArrayList) this.mShowingMenus).iterator();
        while (it.hasNext()) {
            ListAdapter adapter = ((CascadingMenuInfo) it.next()).window.mDropDownList.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((MenuAdapter) adapter).notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onRestoreInstanceState(Parcelable parcelable) {
    }
}
