package com.google.android.material.navigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.StandardMenuPopup;
import androidx.appcompat.view.menu.SubMenuBuilder;
import com.android.systemui.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.internal.ParcelableSparseArray;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NavigationBarPresenter extends BaseMenuPresenter {

    /* renamed from: id */
    public int f460id;
    public final HandlerC43181 mAnimationHandler;
    public Context mContext;
    public OverflowPopup mOverflowPopup;
    public final PopupPresenterCallback mPopupPresenterCallback;
    public OpenOverflowRunnable mPostedOpenRunnable;
    public MenuBuilder menu;
    public NavigationBarMenuView menuView;
    public boolean updateSuspended;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OpenOverflowRunnable implements Runnable {
        public final OverflowPopup mPopup;

        @Override // java.lang.Runnable
        public final void run() {
            MenuBuilder.Callback callback;
            MenuBuilder menuBuilder = NavigationBarPresenter.this.menu;
            if (menuBuilder != null && (callback = menuBuilder.mCallback) != null) {
                callback.onMenuModeChange(menuBuilder);
            }
            NavigationBarMenuView navigationBarMenuView = NavigationBarPresenter.this.menuView;
            if (navigationBarMenuView != null && navigationBarMenuView.getWindowToken() != null && this.mPopup.tryShow$1()) {
                NavigationBarPresenter.this.mOverflowPopup = this.mPopup;
            }
            NavigationBarPresenter.this.mPostedOpenRunnable = null;
        }

        private OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverflowPopup extends MenuPopupHelper {
        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public final void onDismiss() {
            NavigationBarPresenter navigationBarPresenter = NavigationBarPresenter.this;
            MenuBuilder menuBuilder = navigationBarPresenter.menu;
            if (menuBuilder != null) {
                menuBuilder.close(true);
            }
            navigationBarPresenter.mOverflowPopup = null;
            super.onDismiss();
        }

        private OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, R.attr.actionOverflowBottomMenuStyle);
            this.mDropDownGravity = 8388613;
            PopupPresenterCallback popupPresenterCallback = NavigationBarPresenter.this.mPopupPresenterCallback;
            this.mPresenterCallback = popupPresenterCallback;
            StandardMenuPopup standardMenuPopup = this.mPopup;
            if (standardMenuPopup != null) {
                standardMenuPopup.mPresenterCallback = popupPresenterCallback;
            }
            this.mAnchorView = view;
            this.mOverlapAnchorSet = true;
            this.mOverlapAnchor = false;
            this.mForceShowUpper = true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopupPresenterCallback implements MenuPresenter.Callback {
        public PopupPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = NavigationBarPresenter.this.mCallback;
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuPresenter.Callback callback;
            return (menuBuilder == null || (callback = NavigationBarPresenter.this.mCallback) == null || !callback.onOpenSubMenu(menuBuilder)) ? false : true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.google.android.material.navigation.NavigationBarPresenter.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public ParcelableSparseArray badgeSavedStates;
        public int selectedItemId;

        public SavedState() {
        }

        public SavedState(Parcel parcel) {
            this.selectedItemId = parcel.readInt();
            this.badgeSavedStates = (ParcelableSparseArray) parcel.readParcelable(SavedState.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.selectedItemId);
            parcel.writeParcelable(this.badgeSavedStates, 0);
        }
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.android.material.navigation.NavigationBarPresenter$1] */
    public NavigationBarPresenter(Context context) {
        super(context, R.layout.sesl_action_menu_layout, R.layout.sesl_action_menu_item_layout);
        this.updateSuspended = false;
        this.mAnimationHandler = new Handler(Looper.getMainLooper()) { // from class: com.google.android.material.navigation.NavigationBarPresenter.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == 100) {
                    final NavigationBarPresenter navigationBarPresenter = NavigationBarPresenter.this;
                    if (navigationBarPresenter.menuView == null) {
                        return;
                    }
                    final PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(navigationBarPresenter.menuView, "y", r0.getHeight());
                    ofFloat.setDuration(400L);
                    ofFloat.setInterpolator(pathInterpolator);
                    ofFloat.start();
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.navigation.NavigationBarPresenter.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            NavigationBarPresenter.this.menuView.buildMenuView();
                            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(NavigationBarPresenter.this.menuView, "y", 0.0f);
                            ofFloat2.setDuration(400L);
                            ofFloat2.setInterpolator(pathInterpolator);
                            ofFloat2.start();
                            super.onAnimationEnd(animator);
                        }
                    });
                }
            }
        };
        this.mPopupPresenterCallback = new PopupPresenterCallback();
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final int getId() {
        return this.f460id;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
        this.menuView.menu = menuBuilder;
        this.mContext = context;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            NavigationBarMenuView navigationBarMenuView = this.menuView;
            SavedState savedState = (SavedState) parcelable;
            int i = savedState.selectedItemId;
            int size = navigationBarMenuView.menu.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                MenuItem item = navigationBarMenuView.menu.getItem(i2);
                if (i == item.getItemId()) {
                    navigationBarMenuView.selectedItemId = i;
                    navigationBarMenuView.selectedItemPosition = i2;
                    item.setChecked(true);
                    break;
                }
                i2++;
            }
            Context context = this.menuView.getContext();
            ParcelableSparseArray parcelableSparseArray = savedState.badgeSavedStates;
            SparseArray sparseArray = new SparseArray(parcelableSparseArray.size());
            for (int i3 = 0; i3 < parcelableSparseArray.size(); i3++) {
                int keyAt = parcelableSparseArray.keyAt(i3);
                BadgeState.State state = (BadgeState.State) parcelableSparseArray.valueAt(i3);
                if (state == null) {
                    throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
                }
                sparseArray.put(keyAt, BadgeDrawable.createFromSavedState(context, state));
            }
            NavigationBarMenuView navigationBarMenuView2 = this.menuView;
            navigationBarMenuView2.getClass();
            for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                int keyAt2 = sparseArray.keyAt(i4);
                if (navigationBarMenuView2.badgeDrawables.indexOfKey(keyAt2) < 0) {
                    navigationBarMenuView2.badgeDrawables.append(keyAt2, (BadgeDrawable) sparseArray.get(keyAt2));
                }
            }
            NavigationBarItemView[] navigationBarItemViewArr = navigationBarMenuView2.buttons;
            if (navigationBarItemViewArr != null) {
                for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                    if (navigationBarItemView != null) {
                        navigationBarItemView.setBadge((BadgeDrawable) navigationBarMenuView2.badgeDrawables.get(navigationBarItemView.getId()));
                    }
                }
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        NavigationBarMenuView navigationBarMenuView = this.menuView;
        savedState.selectedItemId = navigationBarMenuView.selectedItemId;
        SparseArray sparseArray = navigationBarMenuView.badgeDrawables;
        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            BadgeDrawable badgeDrawable = (BadgeDrawable) sparseArray.valueAt(i);
            if (badgeDrawable == null) {
                throw new IllegalArgumentException("badgeDrawable cannot be null");
            }
            parcelableSparseArray.put(keyAt, badgeDrawable.state.overridingState);
        }
        savedState.badgeSavedStates = parcelableSparseArray;
        return savedState;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public final void showOverflowMenu(MenuBuilder menuBuilder) {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if ((overflowPopup != null && overflowPopup.isShowing()) || menuBuilder == null || this.menuView == null || this.mPostedOpenRunnable != null) {
            return;
        }
        menuBuilder.flagActionItems();
        if (menuBuilder.mNonActionItems.isEmpty()) {
            return;
        }
        OverflowPopup overflowPopup2 = new OverflowPopup(this.mContext, menuBuilder, this.menuView.mOverflowButton, true);
        this.mOverflowPopup = overflowPopup2;
        OpenOverflowRunnable openOverflowRunnable = new OpenOverflowRunnable(overflowPopup2);
        this.mPostedOpenRunnable = openOverflowRunnable;
        this.menuView.post(openOverflowRunnable);
        super.onSubMenuSelected(null);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        if (this.updateSuspended) {
            return;
        }
        if (z) {
            this.menuView.buildMenuView();
        } else {
            this.menuView.updateMenuView();
        }
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public final void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
    }
}
