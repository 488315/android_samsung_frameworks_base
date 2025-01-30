package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.items.ItemHierarchy;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AbstractItemHierarchy implements ItemHierarchy {

    /* renamed from: id */
    public final int f465id;
    public final ArrayList observers;

    public AbstractItemHierarchy() {
        this.observers = new ArrayList();
        this.f465id = -1;
    }

    public final void notifyItemRangeChanged(int i, int i2) {
        if (i < 0) {
            IconCompat$$ExternalSyntheticOutline0.m30m("notifyItemRangeChanged: Invalid position=", i, "AbstractItemHierarchy");
            return;
        }
        Iterator it = this.observers.iterator();
        while (it.hasNext()) {
            ((ItemHierarchy.Observer) it.next()).onItemRangeChanged(this, i);
        }
    }

    public final void notifyItemRangeInserted(int i, int i2) {
        if (i < 0) {
            IconCompat$$ExternalSyntheticOutline0.m30m("notifyItemRangeInserted: Invalid position=", i, "AbstractItemHierarchy");
        } else {
            if (i2 < 0) {
                IconCompat$$ExternalSyntheticOutline0.m30m("notifyItemRangeInserted: Invalid itemCount=", i2, "AbstractItemHierarchy");
                return;
            }
            Iterator it = this.observers.iterator();
            while (it.hasNext()) {
                ((ItemHierarchy.Observer) it.next()).onItemRangeInserted(this, i, i2);
            }
        }
    }

    public AbstractItemHierarchy(Context context, AttributeSet attributeSet) {
        this.observers = new ArrayList();
        this.f465id = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudAbstractItem);
        this.f465id = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
    }
}
