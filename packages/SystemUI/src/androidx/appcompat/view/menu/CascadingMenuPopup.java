package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyEvent;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final void onGlobalLayout() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.appcompat.view.menu.CascadingMenuPopup$3, reason: invalid class name */
    public class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ((CascadingMenuInfo) AlertController$$ExternalSyntheticOutline0.m((ArrayList) this.mShowingMenus, 1)).window.mDropDownList;
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
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
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
    public final void show() {
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

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0141, code lost:
    
        if (((r5.getWidth() + r6[0]) + r0) > r7.right) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0143, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0145, code lost:
    
        r5 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x014a, code lost:
    
        if ((r6[0] - r0) < 0) goto L57;
     */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0179  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showMenu(androidx.appcompat.view.menu.MenuBuilder r15) {
        /*
            Method dump skipped, instructions count: 411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.CascadingMenuPopup.showMenu(androidx.appcompat.view.menu.MenuBuilder):void");
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
