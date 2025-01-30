package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SwitchItem extends Item implements CompoundButton.OnCheckedChangeListener {
    public boolean checked;

    public SwitchItem() {
        this.checked = false;
    }

    @Override // com.google.android.setupdesign.items.Item
    public int getDefaultLayoutResource() {
        return R.layout.sud_items_switch;
    }

    @Override // com.google.android.setupdesign.items.Item, com.google.android.setupdesign.items.AbstractItem
    public void onBindView(View view) {
        super.onBindView(view);
        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.sud_items_switch);
        switchCompat.setOnCheckedChangeListener(null);
        switchCompat.setChecked(this.checked);
        switchCompat.setOnCheckedChangeListener(this);
        switchCompat.setEnabled(this.enabled);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.checked = z;
    }

    public SwitchItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.checked = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudSwitchItem);
        this.checked = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }
}
