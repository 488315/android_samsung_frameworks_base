package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$styleable;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudAbstractItem);
        this.id = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
    }
}
