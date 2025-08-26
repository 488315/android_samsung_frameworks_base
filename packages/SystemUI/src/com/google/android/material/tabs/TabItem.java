package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R$styleable;

/* loaded from: classes4.dex */
public class TabItem extends View {
    public final int customLayout;
    public final Drawable icon;
    public final CharSequence text;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R$styleable.TabItem);
        this.text = tintTypedArrayObtainStyledAttributes.mWrapped.getText(2);
        this.icon = tintTypedArrayObtainStyledAttributes.getDrawable(0);
        this.customLayout = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(1, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }
}
