package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.ViewCompat;
import com.samsung.android.biometrics.app.setting.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
final class CascadingMenuPopup extends MenuPopup implements View.OnKeyListener, PopupWindow.OnDismissListener {
    private View mAnchorView;
    private final Context mContext;
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private int mLastPosition;
    private final int mMenuMaxWidth;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    boolean mShouldCloseImmediately;
    private boolean mShowTitle;
    View mShownAnchorView;
    final Handler mSubMenuHoverHandler;
    ViewTreeObserver mTreeObserver;
    private int mXOffset;
    private int mYOffset;
    private final List<MenuBuilder> mPendingMenus = new ArrayList();
    final List<CascadingMenuInfo> mShowingMenus = new ArrayList();
    final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            if (!CascadingMenuPopup.this.isShowing() || ((ArrayList) CascadingMenuPopup.this.mShowingMenus).size() <= 0 || ((CascadingMenuInfo) ((ArrayList) CascadingMenuPopup.this.mShowingMenus).get(0)).window.isModal()) {
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
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.2
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
    private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3
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
    private int mRawDropDownGravity = 0;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon = false;

    private static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(MenuPopupWindow menuPopupWindow, MenuBuilder menuBuilder, int i) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = i;
        }
    }

    public CascadingMenuPopup(Context context, View view, int i, int i2, boolean z) {
        this.mContext = context;
        this.mAnchorView = view;
        this.mPopupStyleAttr = i;
        this.mPopupStyleRes = i2;
        this.mOverflowOnly = z;
        this.mLastPosition = ViewCompat.getLayoutDirection(view) != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.mMenuMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.mSubMenuHoverHandler = new Handler();
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0129, code lost:
    
        if (((r9.getWidth() + r10[0]) + r4) > r11.right) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0131, code lost:
    
        r9 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0133, code lost:
    
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x012f, code lost:
    
        if ((r10[0] - r4) < 0) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x015f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void showMenu(MenuBuilder menuBuilder) {
        CascadingMenuInfo cascadingMenuInfo;
        View view;
        int i;
        int i2;
        MenuItem menuItem;
        MenuAdapter menuAdapter;
        int i3;
        int firstVisiblePosition;
        LayoutInflater from = LayoutInflater.from(this.mContext);
        MenuAdapter menuAdapter2 = new MenuAdapter(menuBuilder, from, this.mOverflowOnly, R.layout.abc_cascading_menu_item_layout);
        if (!isShowing() && this.mForceShowIcon) {
            menuAdapter2.setForceShowIcon(true);
        } else if (isShowing()) {
            menuAdapter2.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(menuBuilder));
        }
        int measureIndividualMenuWidth = MenuPopup.measureIndividualMenuWidth(menuAdapter2, this.mContext, this.mMenuMaxWidth);
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.mAnchorView);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        menuPopupWindow.setModal();
        menuPopupWindow.setInputMethodMode();
        menuPopupWindow.setAdapter(menuAdapter2);
        menuPopupWindow.setContentWidth(measureIndividualMenuWidth);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        if (((ArrayList) this.mShowingMenus).size() > 0) {
            ArrayList arrayList = (ArrayList) this.mShowingMenus;
            cascadingMenuInfo = (CascadingMenuInfo) arrayList.get(arrayList.size() - 1);
            MenuBuilder menuBuilder2 = cascadingMenuInfo.menu;
            int size = menuBuilder2.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    menuItem = null;
                    break;
                }
                menuItem = menuBuilder2.getItem(i4);
                if (menuItem.hasSubMenu() && menuBuilder == menuItem.getSubMenu()) {
                    break;
                } else {
                    i4++;
                }
            }
            if (menuItem != null) {
                ListView listView = cascadingMenuInfo.window.getListView();
                ListAdapter adapter = listView.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    i3 = headerViewListAdapter.getHeadersCount();
                    menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    menuAdapter = (MenuAdapter) adapter;
                    i3 = 0;
                }
                int count = menuAdapter.getCount();
                int i5 = 0;
                while (true) {
                    if (i5 >= count) {
                        i5 = -1;
                        break;
                    } else if (menuItem == menuAdapter.getItem(i5)) {
                        break;
                    } else {
                        i5++;
                    }
                }
                if (i5 != -1 && (firstVisiblePosition = (i5 + i3) - listView.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < listView.getChildCount()) {
                    view = listView.getChildAt(firstVisiblePosition);
                    if (view == null) {
                        menuPopupWindow.setTouchModal();
                        menuPopupWindow.setEnterTransition();
                        ArrayList arrayList2 = (ArrayList) this.mShowingMenus;
                        ListView listView2 = ((CascadingMenuInfo) arrayList2.get(arrayList2.size() - 1)).window.getListView();
                        int[] iArr = new int[2];
                        listView2.getLocationOnScreen(iArr);
                        Rect rect = new Rect();
                        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
                        if (this.mLastPosition == 1) {
                        }
                        boolean z = i == 1;
                        this.mLastPosition = i;
                        menuPopupWindow.setAnchorView(view);
                        if ((this.mDropDownGravity & 5) == 5) {
                            if (!z) {
                                measureIndividualMenuWidth = view.getWidth();
                                i2 = 0 - measureIndividualMenuWidth;
                            }
                            i2 = measureIndividualMenuWidth + 0;
                        } else {
                            if (z) {
                                measureIndividualMenuWidth = view.getWidth();
                                i2 = measureIndividualMenuWidth + 0;
                            }
                            i2 = 0 - measureIndividualMenuWidth;
                        }
                        menuPopupWindow.setHorizontalOffset(i2);
                        menuPopupWindow.setOverlapAnchor();
                        menuPopupWindow.setVerticalOffset(0);
                    } else {
                        if (this.mHasXOffset) {
                            menuPopupWindow.setHorizontalOffset(this.mXOffset);
                        }
                        if (this.mHasYOffset) {
                            menuPopupWindow.setVerticalOffset(this.mYOffset);
                        }
                        menuPopupWindow.setEpicenterBounds(getEpicenterBounds());
                    }
                    ((ArrayList) this.mShowingMenus).add(new CascadingMenuInfo(menuPopupWindow, menuBuilder, this.mLastPosition));
                    menuPopupWindow.show();
                    ListView listView3 = menuPopupWindow.getListView();
                    listView3.setOnKeyListener(this);
                    if (cascadingMenuInfo == null || !this.mShowTitle || menuBuilder.mHeaderTitle == null) {
                        return;
                    }
                    FrameLayout frameLayout = (FrameLayout) from.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView3, false);
                    TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
                    frameLayout.setEnabled(false);
                    textView.setText(menuBuilder.mHeaderTitle);
                    listView3.addHeaderView(frameLayout, null, false);
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
        ListView listView32 = menuPopupWindow.getListView();
        listView32.setOnKeyListener(this);
        if (cascadingMenuInfo == null) {
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void addMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (isShowing()) {
            showMenu(menuBuilder);
        } else {
            ((ArrayList) this.mPendingMenus).add(menuBuilder);
        }
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
    public final ListView getListView() {
        if (((ArrayList) this.mShowingMenus).isEmpty()) {
            return null;
        }
        return ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(r1.size() - 1)).window.getListView();
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
            menuPopupWindow.setExitTransition();
            menuPopupWindow.setAnimationStyle();
        }
        menuPopupWindow.dismiss();
        int size2 = ((ArrayList) this.mShowingMenus).size();
        if (size2 > 0) {
            this.mLastPosition = ((CascadingMenuInfo) ((ArrayList) this.mShowingMenus).get(size2 - 1)).position;
        } else {
            this.mLastPosition = ViewCompat.getLayoutDirection(this.mAnchorView) == 1 ? 0 : 1;
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
        this.mOnDismissListener.onDismiss();
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
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        Iterator it = ((ArrayList) this.mShowingMenus).iterator();
        while (it.hasNext()) {
            CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) it.next();
            if (subMenuBuilder == cascadingMenuInfo.menu) {
                cascadingMenuInfo.window.getListView().requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        addMenu(subMenuBuilder);
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setAnchorView(View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view;
            this.mDropDownGravity = Gravity.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(view));
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setGravity(int i) {
        if (this.mRawDropDownGravity != i) {
            this.mRawDropDownGravity = i;
            this.mDropDownGravity = Gravity.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this.mAnchorView));
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setHorizontalOffset(int i) {
        this.mHasXOffset = true;
        this.mXOffset = i;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setShowTitle(boolean z) {
        this.mShowTitle = z;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public final void setVerticalOffset(int i) {
        this.mHasYOffset = true;
        this.mYOffset = i;
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

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        Iterator it = ((ArrayList) this.mShowingMenus).iterator();
        while (it.hasNext()) {
            ListAdapter adapter = ((CascadingMenuInfo) it.next()).window.getListView().getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((MenuAdapter) adapter).notifyDataSetChanged();
        }
    }
}
