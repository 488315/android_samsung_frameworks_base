package androidx.appcompat.widget;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.view.menu.CascadingMenuPopup;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MenuPopupWindow extends ListPopupWindow {
    public CascadingMenuPopup.AnonymousClass3 mHoverListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MenuDropDownListView extends DropDownListView {
        public final int mAdvanceKey;
        public MenuPopupWindow mHoverListener;
        public MenuItemImpl mHoveredMenuItem;
        public final int mRetreatKey;

        public MenuDropDownListView(Context context, boolean z) {
            super(context, z);
            if (1 == MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context)) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
            } else {
                this.mAdvanceKey = 22;
                this.mRetreatKey = 21;
            }
        }

        @Override // androidx.appcompat.widget.DropDownListView, android.view.View
        public final boolean onHoverEvent(MotionEvent motionEvent) {
            MenuAdapter menuAdapter;
            int i;
            final CascadingMenuPopup.AnonymousClass3 anonymousClass3;
            CascadingMenuPopup.AnonymousClass3 anonymousClass32;
            int pointToPosition;
            int i2;
            if (this.mHoverListener != null) {
                ListAdapter adapter = getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    i = headerViewListAdapter.getHeadersCount();
                    menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
                } else {
                    menuAdapter = (MenuAdapter) adapter;
                    i = 0;
                }
                final MenuItemImpl item = (motionEvent.getAction() == 10 || (pointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) == -1 || (i2 = pointToPosition - i) < 0 || i2 >= menuAdapter.getCount()) ? null : menuAdapter.getItem(i2);
                MenuItemImpl menuItemImpl = this.mHoveredMenuItem;
                if (menuItemImpl != item) {
                    final MenuBuilder menuBuilder = menuAdapter.mAdapterMenu;
                    if (menuItemImpl != null && (anonymousClass32 = this.mHoverListener.mHoverListener) != null) {
                        CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(menuBuilder);
                    }
                    this.mHoveredMenuItem = item;
                    if (item != null && (anonymousClass3 = this.mHoverListener.mHoverListener) != null) {
                        CascadingMenuPopup cascadingMenuPopup = CascadingMenuPopup.this;
                        cascadingMenuPopup.mSubMenuHoverHandler.removeCallbacksAndMessages(null);
                        int size = ((ArrayList) cascadingMenuPopup.mShowingMenus).size();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= size) {
                                i3 = -1;
                                break;
                            }
                            if (menuBuilder == ((CascadingMenuPopup.CascadingMenuInfo) ((ArrayList) cascadingMenuPopup.mShowingMenus).get(i3)).menu) {
                                break;
                            }
                            i3++;
                        }
                        if (i3 != -1) {
                            int i4 = i3 + 1;
                            final CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo = i4 < ((ArrayList) cascadingMenuPopup.mShowingMenus).size() ? (CascadingMenuPopup.CascadingMenuInfo) ((ArrayList) cascadingMenuPopup.mShowingMenus).get(i4) : null;
                            cascadingMenuPopup.mSubMenuHoverHandler.postAtTime(new Runnable() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3.1
                                public final /* synthetic */ MenuItem val$item;
                                public final /* synthetic */ MenuBuilder val$menu;
                                public final /* synthetic */ CascadingMenuInfo val$nextInfo;

                                public AnonymousClass1(final CascadingMenuInfo cascadingMenuInfo2, final MenuItem item2, final MenuBuilder menuBuilder2) {
                                    r2 = cascadingMenuInfo2;
                                    r3 = item2;
                                    r4 = menuBuilder2;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    CascadingMenuInfo cascadingMenuInfo2 = r2;
                                    if (cascadingMenuInfo2 != null) {
                                        CascadingMenuPopup.this.mShouldCloseImmediately = true;
                                        cascadingMenuInfo2.menu.close(false);
                                        CascadingMenuPopup.this.mShouldCloseImmediately = false;
                                    }
                                    if (r3.isEnabled() && r3.hasSubMenu()) {
                                        r4.performItemAction(r3, null, 4);
                                    }
                                }
                            }, menuBuilder2, SystemClock.uptimeMillis() + 200);
                        }
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
        public final boolean onKeyDown(int i, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
            if (listMenuItemView != null && i == this.mAdvanceKey) {
                if (listMenuItemView.isEnabled() && listMenuItemView.mItemData.hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView == null || i != this.mRetreatKey) {
                return super.onKeyDown(i, keyEvent);
            }
            setSelection(-1);
            ListAdapter adapter = getAdapter();
            (adapter instanceof HeaderViewListAdapter ? (MenuAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter() : (MenuAdapter) adapter).mAdapterMenu.close(false);
            return true;
        }
    }

    public MenuPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.appcompat.widget.ListPopupWindow
    public final DropDownListView createDropDownListView(Context context, boolean z) {
        MenuDropDownListView menuDropDownListView = new MenuDropDownListView(context, z);
        menuDropDownListView.mHoverListener = this;
        return menuDropDownListView;
    }
}
