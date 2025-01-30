package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NavigationMenuPresenter implements MenuPresenter {
    public NavigationMenuAdapter adapter;
    public int dividerInsetEnd;
    public int dividerInsetStart;
    public boolean hasCustomItemIconSize;
    public LinearLayout headerLayout;
    public ColorStateList iconTintList;

    /* renamed from: id */
    public int f459id;
    public Drawable itemBackground;
    public RippleDrawable itemForeground;
    public int itemHorizontalPadding;
    public int itemIconPadding;
    public int itemIconSize;
    public int itemMaxLines;
    public int itemVerticalPadding;
    public LayoutInflater layoutInflater;
    public MenuBuilder menu;
    public NavigationMenuView menuView;
    public int paddingSeparator;
    public int paddingTopDefault;
    public ColorStateList subheaderColor;
    public int subheaderInsetStart;
    public ColorStateList textColor;
    public int subheaderTextAppearance = 0;
    public int textAppearance = 0;
    public boolean isBehindStatusBar = true;
    public int overScrollMode = -1;
    public final ViewOnClickListenerC43001 onClickListener = new View.OnClickListener() { // from class: com.google.android.material.internal.NavigationMenuPresenter.1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) view;
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            NavigationMenuAdapter navigationMenuAdapter = navigationMenuPresenter.adapter;
            boolean z = true;
            if (navigationMenuAdapter != null) {
                navigationMenuAdapter.updateSuspended = true;
            }
            MenuItemImpl menuItemImpl = navigationMenuItemView.itemData;
            boolean performItemAction = navigationMenuPresenter.menu.performItemAction(menuItemImpl, navigationMenuPresenter, 0);
            if (menuItemImpl != null && menuItemImpl.isCheckable() && performItemAction) {
                NavigationMenuPresenter.this.adapter.setCheckedItem(menuItemImpl);
            } else {
                z = false;
            }
            NavigationMenuPresenter navigationMenuPresenter2 = NavigationMenuPresenter.this;
            NavigationMenuAdapter navigationMenuAdapter2 = navigationMenuPresenter2.adapter;
            if (navigationMenuAdapter2 != null) {
                navigationMenuAdapter2.updateSuspended = false;
            }
            if (z) {
                navigationMenuPresenter2.updateMenuView(false);
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NavigationMenuAdapter extends RecyclerView.Adapter {
        public MenuItemImpl checkedItem;
        public final ArrayList items = new ArrayList();
        public boolean updateSuspended;

        public NavigationMenuAdapter() {
            prepareMenuItems();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.items.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            NavigationMenuItem navigationMenuItem = (NavigationMenuItem) this.items.get(i);
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (navigationMenuItem instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (navigationMenuItem instanceof NavigationMenuTextItem) {
                return ((NavigationMenuTextItem) navigationMenuItem).menuItem.hasSubMenu() ? 1 : 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            int itemViewType = getItemViewType(i);
            ArrayList arrayList = this.items;
            View view = ((ViewHolder) viewHolder).itemView;
            final boolean z = false;
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            if (itemViewType != 0) {
                if (itemViewType != 1) {
                    if (itemViewType == 2) {
                        NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuSeparatorItem) arrayList.get(i);
                        view.setPadding(navigationMenuPresenter.dividerInsetStart, navigationMenuSeparatorItem.paddingTop, navigationMenuPresenter.dividerInsetEnd, navigationMenuSeparatorItem.paddingBottom);
                        return;
                    } else {
                        if (itemViewType != 3) {
                            return;
                        }
                        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() { // from class: com.google.android.material.internal.NavigationMenuPresenter.NavigationMenuAdapter.1
                            @Override // androidx.core.view.AccessibilityDelegateCompat
                            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                                NavigationMenuPresenter navigationMenuPresenter2;
                                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                                int i2 = i;
                                int i3 = 0;
                                int i4 = i2;
                                while (true) {
                                    navigationMenuPresenter2 = NavigationMenuPresenter.this;
                                    if (i3 >= i2) {
                                        break;
                                    }
                                    if (navigationMenuPresenter2.adapter.getItemViewType(i3) == 2) {
                                        i4--;
                                    }
                                    i3++;
                                }
                                if (navigationMenuPresenter2.headerLayout.getChildCount() == 0) {
                                    i4--;
                                }
                                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(r3, i4, 1, 1, 1, view2.isSelected()));
                            }
                        });
                        return;
                    }
                }
                TextView textView = (TextView) view;
                textView.setText(((NavigationMenuTextItem) arrayList.get(i)).menuItem.mTitle);
                int i2 = navigationMenuPresenter.subheaderTextAppearance;
                if (i2 != 0) {
                    textView.setTextAppearance(i2);
                }
                textView.setPadding(navigationMenuPresenter.subheaderInsetStart, textView.getPaddingTop(), 0, textView.getPaddingBottom());
                ColorStateList colorStateList = navigationMenuPresenter.subheaderColor;
                if (colorStateList != null) {
                    textView.setTextColor(colorStateList);
                }
                ViewCompat.setAccessibilityDelegate(textView, new AccessibilityDelegateCompat() { // from class: com.google.android.material.internal.NavigationMenuPresenter.NavigationMenuAdapter.1
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        NavigationMenuPresenter navigationMenuPresenter2;
                        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                        int i22 = i;
                        int i3 = 0;
                        int i4 = i22;
                        while (true) {
                            navigationMenuPresenter2 = NavigationMenuPresenter.this;
                            if (i3 >= i22) {
                                break;
                            }
                            if (navigationMenuPresenter2.adapter.getItemViewType(i3) == 2) {
                                i4--;
                            }
                            i3++;
                        }
                        if (navigationMenuPresenter2.headerLayout.getChildCount() == 0) {
                            i4--;
                        }
                        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(r3, i4, 1, 1, 1, view2.isSelected()));
                    }
                });
                return;
            }
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) view;
            ColorStateList colorStateList2 = navigationMenuPresenter.iconTintList;
            navigationMenuItemView.iconTintList = colorStateList2;
            navigationMenuItemView.hasIconTintList = colorStateList2 != null;
            MenuItemImpl menuItemImpl = navigationMenuItemView.itemData;
            if (menuItemImpl != null) {
                navigationMenuItemView.setIcon(menuItemImpl.getIcon());
            }
            int i3 = navigationMenuPresenter.textAppearance;
            if (i3 != 0) {
                navigationMenuItemView.textView.setTextAppearance(i3);
            }
            ColorStateList colorStateList3 = navigationMenuPresenter.textColor;
            if (colorStateList3 != null) {
                navigationMenuItemView.textView.setTextColor(colorStateList3);
            }
            Drawable drawable = navigationMenuPresenter.itemBackground;
            Drawable newDrawable = drawable != null ? drawable.getConstantState().newDrawable() : null;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(navigationMenuItemView, newDrawable);
            RippleDrawable rippleDrawable = navigationMenuPresenter.itemForeground;
            if (rippleDrawable != null) {
                navigationMenuItemView.setForeground(rippleDrawable.getConstantState().newDrawable());
            }
            NavigationMenuTextItem navigationMenuTextItem = (NavigationMenuTextItem) arrayList.get(i);
            navigationMenuItemView.needsEmptyIcon = navigationMenuTextItem.needsEmptyIcon;
            int i4 = navigationMenuPresenter.itemHorizontalPadding;
            int i5 = navigationMenuPresenter.itemVerticalPadding;
            navigationMenuItemView.setPadding(i4, i5, i4, i5);
            navigationMenuItemView.textView.setCompoundDrawablePadding(navigationMenuPresenter.itemIconPadding);
            if (navigationMenuPresenter.hasCustomItemIconSize) {
                navigationMenuItemView.iconSize = navigationMenuPresenter.itemIconSize;
            }
            navigationMenuItemView.textView.setMaxLines(navigationMenuPresenter.itemMaxLines);
            navigationMenuItemView.initialize(navigationMenuTextItem.menuItem);
            ViewCompat.setAccessibilityDelegate(navigationMenuItemView, new AccessibilityDelegateCompat() { // from class: com.google.android.material.internal.NavigationMenuPresenter.NavigationMenuAdapter.1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    NavigationMenuPresenter navigationMenuPresenter2;
                    this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                    int i22 = i;
                    int i32 = 0;
                    int i42 = i22;
                    while (true) {
                        navigationMenuPresenter2 = NavigationMenuPresenter.this;
                        if (i32 >= i22) {
                            break;
                        }
                        if (navigationMenuPresenter2.adapter.getItemViewType(i32) == 2) {
                            i42--;
                        }
                        i32++;
                    }
                    if (navigationMenuPresenter2.headerLayout.getChildCount() == 0) {
                        i42--;
                    }
                    accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(z, i42, 1, 1, 1, view2.isSelected()));
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            RecyclerView.ViewHolder normalViewHolder;
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            if (i == 0) {
                normalViewHolder = new NormalViewHolder(navigationMenuPresenter.layoutInflater, recyclerView, navigationMenuPresenter.onClickListener);
            } else if (i == 1) {
                normalViewHolder = new SubheaderViewHolder(navigationMenuPresenter.layoutInflater, recyclerView);
            } else {
                if (i != 2) {
                    if (i != 3) {
                        return null;
                    }
                    return new HeaderViewHolder(navigationMenuPresenter.headerLayout);
                }
                normalViewHolder = new SeparatorViewHolder(navigationMenuPresenter.layoutInflater, recyclerView);
            }
            return normalViewHolder;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            if (viewHolder2 instanceof NormalViewHolder) {
                NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) viewHolder2.itemView;
                FrameLayout frameLayout = navigationMenuItemView.actionArea;
                if (frameLayout != null) {
                    frameLayout.removeAllViews();
                }
                navigationMenuItemView.textView.setCompoundDrawables(null, null, null, null);
            }
        }

        public final void prepareMenuItems() {
            boolean z;
            if (this.updateSuspended) {
                return;
            }
            this.updateSuspended = true;
            ArrayList arrayList = this.items;
            arrayList.clear();
            arrayList.add(new NavigationMenuHeaderItem());
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            int size = navigationMenuPresenter.menu.getVisibleItems().size();
            boolean z2 = false;
            int i = -1;
            int i2 = 0;
            boolean z3 = false;
            int i3 = 0;
            while (i2 < size) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) navigationMenuPresenter.menu.getVisibleItems().get(i2);
                if (menuItemImpl.isChecked()) {
                    setCheckedItem(menuItemImpl);
                }
                if (menuItemImpl.isCheckable()) {
                    menuItemImpl.setExclusiveCheckable(z2);
                }
                if (menuItemImpl.hasSubMenu()) {
                    SubMenuBuilder subMenuBuilder = menuItemImpl.mSubMenu;
                    if (subMenuBuilder.hasVisibleItems()) {
                        if (i2 != 0) {
                            arrayList.add(new NavigationMenuSeparatorItem(navigationMenuPresenter.paddingSeparator, z2 ? 1 : 0));
                        }
                        arrayList.add(new NavigationMenuTextItem(menuItemImpl));
                        int size2 = subMenuBuilder.size();
                        int i4 = z2 ? 1 : 0;
                        int i5 = i4;
                        while (i4 < size2) {
                            MenuItemImpl menuItemImpl2 = (MenuItemImpl) subMenuBuilder.getItem(i4);
                            if (menuItemImpl2.isVisible()) {
                                if (i5 == 0 && menuItemImpl2.getIcon() != null) {
                                    i5 = 1;
                                }
                                if (menuItemImpl2.isCheckable()) {
                                    menuItemImpl2.setExclusiveCheckable(z2);
                                }
                                if (menuItemImpl.isChecked()) {
                                    setCheckedItem(menuItemImpl);
                                }
                                arrayList.add(new NavigationMenuTextItem(menuItemImpl2));
                            }
                            i4++;
                            z2 = false;
                        }
                        if (i5 != 0) {
                            int size3 = arrayList.size();
                            for (int size4 = arrayList.size(); size4 < size3; size4++) {
                                ((NavigationMenuTextItem) arrayList.get(size4)).needsEmptyIcon = true;
                            }
                        }
                    }
                    z = true;
                } else {
                    int i6 = menuItemImpl.mGroup;
                    if (i6 != i) {
                        i3 = arrayList.size();
                        z3 = menuItemImpl.getIcon() != null;
                        if (i2 != 0) {
                            i3++;
                            int i7 = navigationMenuPresenter.paddingSeparator;
                            arrayList.add(new NavigationMenuSeparatorItem(i7, i7));
                        }
                    } else if (!z3 && menuItemImpl.getIcon() != null) {
                        int size5 = arrayList.size();
                        for (int i8 = i3; i8 < size5; i8++) {
                            ((NavigationMenuTextItem) arrayList.get(i8)).needsEmptyIcon = true;
                        }
                        z = true;
                        z3 = true;
                        NavigationMenuTextItem navigationMenuTextItem = new NavigationMenuTextItem(menuItemImpl);
                        navigationMenuTextItem.needsEmptyIcon = z3;
                        arrayList.add(navigationMenuTextItem);
                        i = i6;
                    }
                    z = true;
                    NavigationMenuTextItem navigationMenuTextItem2 = new NavigationMenuTextItem(menuItemImpl);
                    navigationMenuTextItem2.needsEmptyIcon = z3;
                    arrayList.add(navigationMenuTextItem2);
                    i = i6;
                }
                i2++;
                z2 = false;
            }
            this.updateSuspended = z2 ? 1 : 0;
        }

        public final void setCheckedItem(MenuItemImpl menuItemImpl) {
            if (this.checkedItem == menuItemImpl || !menuItemImpl.isCheckable()) {
                return;
            }
            MenuItemImpl menuItemImpl2 = this.checkedItem;
            if (menuItemImpl2 != null) {
                menuItemImpl2.setChecked(false);
            }
            this.checkedItem = menuItemImpl;
            menuItemImpl.setChecked(true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NavigationMenuHeaderItem implements NavigationMenuItem {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface NavigationMenuItem {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NavigationMenuSeparatorItem implements NavigationMenuItem {
        public final int paddingBottom;
        public final int paddingTop;

        public NavigationMenuSeparatorItem(int i, int i2) {
            this.paddingTop = i;
            this.paddingBottom = i2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NavigationMenuTextItem implements NavigationMenuItem {
        public final MenuItemImpl menuItem;
        public boolean needsEmptyIcon;

        public NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            this.menuItem = menuItemImpl;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NavigationMenuViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        public NavigationMenuViewAccessibilityDelegate(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override // androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate, androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int i;
            int i2;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            NavigationMenuPresenter navigationMenuPresenter = NavigationMenuPresenter.this;
            if (navigationMenuPresenter.headerLayout.getChildCount() == 0) {
                i = 0;
                i2 = 0;
            } else {
                i = 0;
                i2 = 1;
            }
            while (i < navigationMenuPresenter.adapter.getItemCount()) {
                int itemViewType = navigationMenuPresenter.adapter.getItemViewType(i);
                if (itemViewType == 0 || itemViewType == 1) {
                    i2++;
                }
                i++;
            }
            accessibilityNodeInfoCompat.setCollectionInfo(new AccessibilityNodeInfoCompat.CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i2, 1, false)));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubheaderViewHolder extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final int getId() {
        return this.f459id;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.layoutInflater = LayoutInflater.from(context);
        this.menu = menuBuilder;
        this.paddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onRestoreInstanceState(Parcelable parcelable) {
        MenuItemImpl menuItemImpl;
        View actionView;
        ParcelableSparseArray parcelableSparseArray;
        MenuItemImpl menuItemImpl2;
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:list");
            if (sparseParcelableArray != null) {
                this.menuView.restoreHierarchyState(sparseParcelableArray);
            }
            Bundle bundle2 = bundle.getBundle("android:menu:adapter");
            if (bundle2 != null) {
                NavigationMenuAdapter navigationMenuAdapter = this.adapter;
                navigationMenuAdapter.getClass();
                int i = bundle2.getInt("android:menu:checked", 0);
                ArrayList arrayList = navigationMenuAdapter.items;
                if (i != 0) {
                    navigationMenuAdapter.updateSuspended = true;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        NavigationMenuItem navigationMenuItem = (NavigationMenuItem) arrayList.get(i2);
                        if ((navigationMenuItem instanceof NavigationMenuTextItem) && (menuItemImpl2 = ((NavigationMenuTextItem) navigationMenuItem).menuItem) != null && menuItemImpl2.mId == i) {
                            navigationMenuAdapter.setCheckedItem(menuItemImpl2);
                            break;
                        }
                        i2++;
                    }
                    navigationMenuAdapter.updateSuspended = false;
                    navigationMenuAdapter.prepareMenuItems();
                }
                SparseArray sparseParcelableArray2 = bundle2.getSparseParcelableArray("android:menu:action_views");
                if (sparseParcelableArray2 != null) {
                    int size2 = arrayList.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        NavigationMenuItem navigationMenuItem2 = (NavigationMenuItem) arrayList.get(i3);
                        if ((navigationMenuItem2 instanceof NavigationMenuTextItem) && (menuItemImpl = ((NavigationMenuTextItem) navigationMenuItem2).menuItem) != null && (actionView = menuItemImpl.getActionView()) != null && (parcelableSparseArray = (ParcelableSparseArray) sparseParcelableArray2.get(menuItemImpl.mId)) != null) {
                            actionView.restoreHierarchyState(parcelableSparseArray);
                        }
                    }
                }
            }
            SparseArray sparseParcelableArray3 = bundle.getSparseParcelableArray("android:menu:header");
            if (sparseParcelableArray3 != null) {
                this.headerLayout.restoreHierarchyState(sparseParcelableArray3);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        if (this.menuView != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.menuView.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray("android:menu:list", sparseArray);
        }
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter != null) {
            navigationMenuAdapter.getClass();
            Bundle bundle2 = new Bundle();
            MenuItemImpl menuItemImpl = navigationMenuAdapter.checkedItem;
            if (menuItemImpl != null) {
                bundle2.putInt("android:menu:checked", menuItemImpl.mId);
            }
            SparseArray<? extends Parcelable> sparseArray2 = new SparseArray<>();
            ArrayList arrayList = navigationMenuAdapter.items;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                NavigationMenuItem navigationMenuItem = (NavigationMenuItem) arrayList.get(i);
                if (navigationMenuItem instanceof NavigationMenuTextItem) {
                    MenuItemImpl menuItemImpl2 = ((NavigationMenuTextItem) navigationMenuItem).menuItem;
                    View actionView = menuItemImpl2 != null ? menuItemImpl2.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                        actionView.saveHierarchyState(parcelableSparseArray);
                        sparseArray2.put(menuItemImpl2.mId, parcelableSparseArray);
                    }
                }
            }
            bundle2.putSparseParcelableArray("android:menu:action_views", sparseArray2);
            bundle.putBundle("android:menu:adapter", bundle2);
        }
        if (this.headerLayout != null) {
            SparseArray<? extends Parcelable> sparseArray3 = new SparseArray<>();
            this.headerLayout.saveHierarchyState(sparseArray3);
            bundle.putSparseParcelableArray("android:menu:header", sparseArray3);
        }
        return bundle;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter != null) {
            navigationMenuAdapter.prepareMenuItems();
            navigationMenuAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
    }
}
