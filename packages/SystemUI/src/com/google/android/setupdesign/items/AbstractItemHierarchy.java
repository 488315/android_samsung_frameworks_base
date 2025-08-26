package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$styleable;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract class AbstractItemHierarchy implements ItemHierarchy {
    public final int id;
    public final ArrayList observers;

    public AbstractItemHierarchy() {
        this.observers = new ArrayList();
        this.id = -1;
    }

    public AbstractItemHierarchy(Context context, AttributeSet attributeSet) {
        this.observers = new ArrayList();
        this.id = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudAbstractItem);
        this.id = typedArrayObtainStyledAttributes.getResourceId(0, -1);
        typedArrayObtainStyledAttributes.recycle();
    }
}
