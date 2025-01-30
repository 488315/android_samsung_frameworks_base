package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import androidx.appcompat.view.menu.MenuView;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MenuAdapter extends BaseAdapter {
    public final MenuBuilder mAdapterMenu;
    public int mExpandedIndex = -1;
    public boolean mForceShowIcon;
    public final LayoutInflater mInflater;
    public int mInitPaddingBottom;
    public int mInitPaddingTop;
    public final int mItemLayoutRes;
    public final boolean mOverflowOnly;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater layoutInflater, boolean z, int i) {
        this.mOverflowOnly = z;
        this.mInflater = layoutInflater;
        this.mAdapterMenu = menuBuilder;
        this.mItemLayoutRes = i;
        findExpandedIndex();
    }

    public final void findExpandedIndex() {
        MenuBuilder menuBuilder = this.mAdapterMenu;
        MenuItemImpl menuItemImpl = menuBuilder.mExpandedItem;
        if (menuItemImpl != null) {
            menuBuilder.flagActionItems();
            ArrayList arrayList = menuBuilder.mNonActionItems;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((MenuItemImpl) arrayList.get(i)) == menuItemImpl) {
                    this.mExpandedIndex = i;
                    return;
                }
            }
        }
        this.mExpandedIndex = -1;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        ArrayList visibleItems;
        if (this.mOverflowOnly) {
            MenuBuilder menuBuilder = this.mAdapterMenu;
            menuBuilder.flagActionItems();
            visibleItems = menuBuilder.mNonActionItems;
        } else {
            visibleItems = this.mAdapterMenu.getVisibleItems();
        }
        return this.mExpandedIndex < 0 ? visibleItems.size() : visibleItems.size() - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(this.mItemLayoutRes, viewGroup, false);
            this.mInitPaddingTop = view.getPaddingTop();
            this.mInitPaddingBottom = view.getPaddingBottom();
        }
        int i2 = getItem(i).mGroup;
        int i3 = i - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        boolean z = this.mAdapterMenu.isGroupDividerEnabled() && i2 != (i3 >= 0 ? getItem(i3).mGroup : i2);
        ImageView imageView = listMenuItemView.mGroupDivider;
        if (imageView != null) {
            imageView.setVisibility((listMenuItemView.mHasListDivider || !z) ? 8 : 0);
        }
        MenuView.ItemView itemView = (MenuView.ItemView) view;
        if (this.mForceShowIcon) {
            listMenuItemView.mForceShowIcon = true;
            listMenuItemView.mPreserveIconSpacing = true;
        }
        itemView.initialize(getItem(i));
        int dimensionPixelSize = view.getResources().getDimensionPixelSize(R.dimen.sesl_popup_menu_first_last_item_vertical_edge_padding);
        int i4 = this.mInitPaddingTop + dimensionPixelSize;
        int i5 = this.mInitPaddingBottom + dimensionPixelSize;
        int paddingLeft = view.getPaddingLeft();
        if (i != 0) {
            i4 = this.mInitPaddingTop;
        }
        int paddingRight = view.getPaddingRight();
        if (i != getCount() - 1) {
            i5 = this.mInitPaddingBottom;
        }
        view.setPadding(paddingLeft, i4, paddingRight, i5);
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        findExpandedIndex();
        super.notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public final MenuItemImpl getItem(int i) {
        ArrayList visibleItems;
        if (this.mOverflowOnly) {
            MenuBuilder menuBuilder = this.mAdapterMenu;
            menuBuilder.flagActionItems();
            visibleItems = menuBuilder.mNonActionItems;
        } else {
            visibleItems = this.mAdapterMenu.getVisibleItems();
        }
        int i2 = this.mExpandedIndex;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return (MenuItemImpl) visibleItems.get(i);
    }
}
