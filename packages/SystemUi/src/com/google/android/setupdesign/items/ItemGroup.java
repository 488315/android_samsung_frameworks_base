package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import com.google.android.setupdesign.items.ItemHierarchy;
import com.google.android.setupdesign.items.ItemInflater;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ItemGroup extends AbstractItemHierarchy implements ItemInflater.ItemParent, ItemHierarchy.Observer {
    public final List children;
    public int count;
    public boolean dirty;
    public final SparseIntArray hierarchyStart;

    public ItemGroup() {
        this.children = new ArrayList();
        this.hierarchyStart = new SparseIntArray();
        this.count = 0;
        this.dirty = false;
    }

    @Override // com.google.android.setupdesign.items.ItemInflater.ItemParent
    public final void addChild(ItemHierarchy itemHierarchy) {
        this.dirty = true;
        ((ArrayList) this.children).add(itemHierarchy);
        AbstractItemHierarchy abstractItemHierarchy = (AbstractItemHierarchy) itemHierarchy;
        abstractItemHierarchy.observers.add(this);
        int count = abstractItemHierarchy.getCount();
        if (count > 0) {
            notifyItemRangeInserted(getChildPosition(abstractItemHierarchy), count);
        }
    }

    public final int getChildPosition(ItemHierarchy itemHierarchy) {
        List list = this.children;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            if (arrayList.get(i) == itemHierarchy) {
                break;
            }
            i++;
        }
        updateDataIfNeeded();
        if (i == -1) {
            return -1;
        }
        int size2 = ((ArrayList) list).size();
        int i2 = -1;
        while (i2 < 0 && i < size2) {
            i2 = this.hierarchyStart.get(i, -1);
            i++;
        }
        if (i2 >= 0) {
            return i2;
        }
        updateDataIfNeeded();
        return this.count;
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy
    public final int getCount() {
        updateDataIfNeeded();
        return this.count;
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy
    public final AbstractItem getItemAt(int i) {
        int keyAt;
        updateDataIfNeeded();
        if (i < 0 || i >= this.count) {
            throw new IndexOutOfBoundsException("size=" + this.count + "; index=" + i);
        }
        SparseIntArray sparseIntArray = this.hierarchyStart;
        int size = sparseIntArray.size() - 1;
        int i2 = 0;
        while (true) {
            if (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                int valueAt = sparseIntArray.valueAt(i3);
                if (valueAt >= i) {
                    if (valueAt <= i) {
                        keyAt = sparseIntArray.keyAt(i3);
                        break;
                    }
                    size = i3 - 1;
                } else {
                    i2 = i3 + 1;
                }
            } else {
                keyAt = sparseIntArray.keyAt(i2 - 1);
                break;
            }
        }
        if (keyAt >= 0) {
            return ((ItemHierarchy) ((ArrayList) this.children).get(keyAt)).getItemAt(i - sparseIntArray.get(keyAt));
        }
        throw new IllegalStateException("Cannot have item start index < 0");
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy.Observer
    public final void onItemRangeChanged(ItemHierarchy itemHierarchy, int i) {
        int childPosition = getChildPosition(itemHierarchy);
        if (childPosition >= 0) {
            notifyItemRangeChanged(childPosition + i, 1);
            return;
        }
        Log.e("ItemGroup", "Unexpected child change " + itemHierarchy);
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy.Observer
    public final void onItemRangeInserted(ItemHierarchy itemHierarchy, int i, int i2) {
        this.dirty = true;
        int childPosition = getChildPosition(itemHierarchy);
        if (childPosition >= 0) {
            notifyItemRangeInserted(childPosition + i, i2);
            return;
        }
        Log.e("ItemGroup", "Unexpected child insert " + itemHierarchy);
    }

    public final void updateDataIfNeeded() {
        if (!this.dirty) {
            return;
        }
        this.count = 0;
        SparseIntArray sparseIntArray = this.hierarchyStart;
        sparseIntArray.clear();
        int i = 0;
        while (true) {
            List list = this.children;
            if (i >= ((ArrayList) list).size()) {
                this.dirty = false;
                return;
            }
            ItemHierarchy itemHierarchy = (ItemHierarchy) ((ArrayList) list).get(i);
            if (itemHierarchy.getCount() > 0) {
                sparseIntArray.put(i, this.count);
            }
            this.count = itemHierarchy.getCount() + this.count;
            i++;
        }
    }

    public ItemGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.children = new ArrayList();
        this.hierarchyStart = new SparseIntArray();
        this.count = 0;
        this.dirty = false;
    }
}
