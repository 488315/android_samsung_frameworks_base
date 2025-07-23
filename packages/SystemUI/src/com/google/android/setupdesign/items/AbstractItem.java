package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
}
