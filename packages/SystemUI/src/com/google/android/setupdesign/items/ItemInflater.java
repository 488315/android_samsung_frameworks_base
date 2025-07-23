package com.google.android.setupdesign.items;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
