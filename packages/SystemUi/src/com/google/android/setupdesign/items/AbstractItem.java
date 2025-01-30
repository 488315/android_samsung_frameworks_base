package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AbstractItem extends AbstractItemHierarchy {
    public AbstractItem() {
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy
    public int getCount() {
        return 1;
    }

    public abstract int getLayoutResource();

    public abstract boolean isEnabled();

    public abstract void onBindView(View view);

    public AbstractItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy
    public final AbstractItem getItemAt(int i) {
        return this;
    }
}
