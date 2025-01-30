package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.android.systemui.R;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ButtonItem extends AbstractItem implements View.OnClickListener {
    public Button button;
    public final boolean enabled;
    public final CharSequence text;
    public final int theme;

    public ButtonItem() {
        this.enabled = true;
        this.theme = R.style.SudButtonItem;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem, com.google.android.setupdesign.items.ItemHierarchy
    public final int getCount() {
        return 0;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final int getLayoutResource() {
        return 0;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final boolean isEnabled() {
        return this.enabled;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final void onBindView(View view) {
        throw new UnsupportedOperationException("Cannot bind to ButtonItem's view");
    }

    public ButtonItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.enabled = true;
        this.theme = R.style.SudButtonItem;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudButtonItem);
        this.enabled = obtainStyledAttributes.getBoolean(1, true);
        this.text = obtainStyledAttributes.getText(3);
        this.theme = obtainStyledAttributes.getResourceId(0, R.style.SudButtonItem);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
    }
}
