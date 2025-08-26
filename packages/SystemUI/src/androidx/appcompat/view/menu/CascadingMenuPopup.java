package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.MenuPopupWindow;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class CascadingMenuPopup extends MenuPopup implements View.OnKeyListener, PopupWindow.OnDismissListener {
    public final View mAnchorView;
    public final Context mContext;
    public final boolean mForceShowIcon;
    public int mLastPosition;
    public final int mMenuMaxWidth;
    public final boolean mOverflowOnly;
    public final int mPopupStyleAttr;
    public final int mPopupStyleRes;
    public final MenuPresenter.Callback mPresenterCallback;
    public boolean mShouldCloseImmediately;
    public View mShownAnchorView;
    public final Handler mSubMenuHoverHandler;
    public ViewTreeObserver mTreeObserver;
    public final List mPendingMenus = new ArrayList();
    public final List mShowingMenus = new ArrayList();
    public final AnonymousClass1 mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() throws Resources.NotFoundException {
            if (!CascadingMenuPopup.this.isShowing() || ((ArrayList) CascadingMenuPopup.this.mShowingMenus).size() <= 0) {
                return;
            }
            int i = 0;
            if (((CascadingMenuInfo) ((ArrayList) CascadingMenuPopup.this.mShowingMenus).get(0)).window.mModal) {
                return;
            }
            View view = CascadingMenuPopup.this.mShownAnchorView;
            if (view == null || !view.isShown()) {
                CascadingMenuPopup.this.dismiss();
                return;
            }
            ArrayList arrayList = (ArrayList) CascadingMenuPopup.this.mShowingMenus;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((CascadingMenuInfo) obj).window.show();
            }
        }
    };
    public final AnonymousClass2 mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.2
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
    public final AnonymousClass3 mMenuItemHoverListener = new AnonymousClass3();
    public final int mDropDownGravity = 0;

    /* renamed from: androidx.appcompat.view.menu.CascadingMenuPopup$3, reason: invalid class name */
    public class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    public class CascadingMenuInfo {
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
        this.mLastPosition = view.getLayoutDirection() != 1 ? 1 : 0;
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
                if (cascadingMenuInfo.window.mPopup.isShowing()) {
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
        return ((CascadingMenuInfo) AlertController$$ExternalSyntheticOutline0.m(1, (ArrayList) this.mShowingMenus)).window.mDropDownList;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final boolean isShowing() {
        return ((ArrayList) this.mShowingMenus).size() > 0 && ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(0)).window.mPopup.isShowing();
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
            menuPopupWindow.mPopup.setExitTransition(null);
            menuPopupWindow.mPopup.setAnimationStyle(0);
        }
        menuPopupWindow.dismiss();
        int size2 = ((ArrayList) this.mShowingMenus).size();
        if (size2 > 0) {
            this.mLastPosition = ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(size2 - 1)).position;
        } else {
            this.mLastPosition = this.mAnchorView.getLayoutDirection() == 1 ? 0 : 1;
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
            if (!cascadingMenuInfo.window.mPopup.isShowing()) {
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
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) throws Resources.NotFoundException {
        ArrayList arrayList = (ArrayList) this.mShowingMenus;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) obj;
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

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final void show() throws Resources.NotFoundException {
        if (isShowing()) {
            return;
        }
        ArrayList arrayList = (ArrayList) this.mPendingMenus;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            showMenu((MenuBuilder) obj);
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

    /* JADX WARN: Removed duplicated region for block: B:50:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showMenu(MenuBuilder menuBuilder) throws Resources.NotFoundException {
        MenuAdapter menuAdapter;
        View childAt;
        int i;
        MenuItem item;
        MenuAdapter menuAdapter2;
        int headersCount;
        int firstVisiblePosition;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        int size = menuBuilder.mItems.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                menuAdapter = new MenuAdapter(menuBuilder, layoutInflaterFrom, this.mOverflowOnly, com.android.systemui.R.layout.sesl_cascading_menu_item_layout);
                break;
            } else {
                if ((((MenuItemImpl) menuBuilder.getItem(i2)).mFlags & 4) != 0) {
                    menuAdapter = new MenuAdapter(menuBuilder, layoutInflaterFrom, this.mOverflowOnly, com.android.systemui.R.layout.sesl_popup_sub_menu_item_layout);
                    break;
                }
                i2++;
            }
        }
        if (!isShowing() && this.mForceShowIcon) {
            menuAdapter.mForceShowIcon = true;
        } else if (isShowing()) {
            menuAdapter.mForceShowIcon = MenuPopup.shouldPreserveIconSpacing(menuBuilder);
        }
        int iMeasureIndividualMenuWidth = MenuPopup.measureIndividualMenuWidth(menuAdapter, this.mContext, this.mMenuMaxWidth);
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.mHoverListener = this.mMenuItemHoverListener;
        menuPopupWindow.mItemClickListener = this;
        menuPopupWindow.mPopup.setOnDismissListener(this);
        menuPopupWindow.mDropDownAnchorView = this.mAnchorView;
        menuPopupWindow.mDropDownGravity = this.mDropDownGravity;
        menuPopupWindow.mModal = true;
        menuPopupWindow.mPopup.setFocusable(true);
        menuPopupWindow.mPopup.setInputMethodMode(2);
        menuPopupWindow.setAdapter(menuAdapter);
        menuPopupWindow.setContentWidth(iMeasureIndividualMenuWidth);
        menuPopupWindow.mDropDownGravity = this.mDropDownGravity;
        if (((ArrayList) this.mShowingMenus).size() > 0) {
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) AlertController$$ExternalSyntheticOutline0.m(1, (ArrayList) this.mShowingMenus);
            MenuBuilder menuBuilder2 = cascadingMenuInfo.menu;
            int size2 = menuBuilder2.mItems.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size2) {
                    item = null;
                    break;
                }
                item = menuBuilder2.getItem(i3);
                if (item.hasSubMenu() && menuBuilder == item.getSubMenu()) {
                    break;
                } else {
                    i3++;
                }
            }
            if (item == null) {
                childAt = null;
            } else {
                DropDownListView dropDownListView = cascadingMenuInfo.window.mDropDownList;
                ListAdapter adapter = dropDownListView.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    menuAdapter2 = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    menuAdapter2 = (MenuAdapter) adapter;
                    headersCount = 0;
                }
                int count = menuAdapter2.getCount();
                int i4 = 0;
                while (true) {
                    if (i4 >= count) {
                        i4 = -1;
                        break;
                    } else if (item == menuAdapter2.getItem(i4)) {
                        break;
                    } else {
                        i4++;
                    }
                }
                if (i4 != -1 && (firstVisiblePosition = (i4 + headersCount) - dropDownListView.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < dropDownListView.getChildCount()) {
                    childAt = dropDownListView.getChildAt(firstVisiblePosition);
                }
            }
        }
        if (childAt != null) {
            menuPopupWindow.mPopup.setTouchModal(false);
            menuPopupWindow.mPopup.setEnterTransition(null);
            DropDownListView dropDownListView2 = ((CascadingMenuInfo) AlertController$$ExternalSyntheticOutline0.m(1, (ArrayList) this.mShowingMenus)).window.mDropDownList;
            int[] iArr = new int[2];
            dropDownListView2.getLocationOnScreen(iArr);
            Rect rect = new Rect();
            this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
            if (this.mLastPosition == 1) {
                i = (dropDownListView2.getWidth() + iArr[0]) + iMeasureIndividualMenuWidth > rect.right ? 0 : 1;
            } else if (iArr[0] - iMeasureIndividualMenuWidth < 0) {
            }
            boolean z = i == 1;
            this.mLastPosition = i;
            menuPopupWindow.mDropDownAnchorView = childAt;
            if ((this.mDropDownGravity & 5) != 5) {
                iMeasureIndividualMenuWidth = z ? childAt.getWidth() : 0 - iMeasureIndividualMenuWidth;
            } else if (!z) {
                iMeasureIndividualMenuWidth = 0 - childAt.getWidth();
            }
            menuPopupWindow.mDropDownHorizontalOffset = iMeasureIndividualMenuWidth;
            menuPopupWindow.mOverlapAnchorSet = true;
            menuPopupWindow.mOverlapAnchor = true;
            menuPopupWindow.setVerticalOffset(0);
        } else {
            Rect rect2 = this.mEpicenterBounds;
            menuPopupWindow.mEpicenterBounds = rect2 != null ? new Rect(rect2) : null;
        }
        ((ArrayList) this.mShowingMenus).add(new CascadingMenuInfo(menuPopupWindow, menuBuilder, this.mLastPosition));
        menuPopupWindow.show();
        menuPopupWindow.mDropDownList.setOnKeyListener(this);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        ArrayList arrayList = (ArrayList) this.mShowingMenus;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ListAdapter adapter = ((CascadingMenuInfo) obj).window.mDropDownList.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((MenuAdapter) adapter).notifyDataSetChanged();
        }
    }
}
