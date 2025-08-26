package com.google.android.setupdesign.items;

import android.content.Context;

/* loaded from: classes4.dex */
public class ItemInflater extends ReflectionInflater {
    public ItemInflater(Context context) {
        super(context);
        this.defaultPackage = Item.class.getPackage().getName() + ".";
    }

    @Override // com.google.android.setupdesign.items.SimpleInflater
    public final void onAddChildItem(Object obj, Object obj2) {
        throw new IllegalArgumentException("Cannot add child item to " + ((ItemHierarchy) obj));
    }
}
