package com.google.android.setupdesign.items;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ItemInflater extends ReflectionInflater {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ItemParent {
        void addChild(ItemHierarchy itemHierarchy);
    }

    public ItemInflater(Context context) {
        super(context);
        this.defaultPackage = Item.class.getPackage().getName() + ".";
    }

    @Override // com.google.android.setupdesign.items.SimpleInflater
    public final void onAddChildItem(Object obj, Object obj2) {
        ItemHierarchy itemHierarchy = (ItemHierarchy) obj;
        ItemHierarchy itemHierarchy2 = (ItemHierarchy) obj2;
        if (itemHierarchy instanceof ItemParent) {
            ((ItemParent) itemHierarchy).addChild(itemHierarchy2);
        } else {
            throw new IllegalArgumentException("Cannot add child item to " + itemHierarchy);
        }
    }
}
