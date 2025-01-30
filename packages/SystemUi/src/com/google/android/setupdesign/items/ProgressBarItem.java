package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ProgressBarItem extends Item {
    public ProgressBarItem() {
    }

    @Override // com.google.android.setupdesign.items.Item
    public final int getDefaultLayoutResource() {
        return R.layout.sud_items_progress_bar;
    }

    @Override // com.google.android.setupdesign.items.Item, com.google.android.setupdesign.items.AbstractItem
    public final boolean isEnabled() {
        return false;
    }

    public ProgressBarItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.setupdesign.items.Item, com.google.android.setupdesign.items.AbstractItem
    public final void onBindView(View view) {
    }
}
