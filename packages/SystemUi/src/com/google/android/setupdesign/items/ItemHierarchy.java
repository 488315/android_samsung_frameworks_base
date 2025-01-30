package com.google.android.setupdesign.items;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ItemHierarchy {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Observer {
        void onItemRangeChanged(ItemHierarchy itemHierarchy, int i);

        void onItemRangeInserted(ItemHierarchy itemHierarchy, int i, int i2);
    }

    int getCount();

    AbstractItem getItemAt(int i);
}
