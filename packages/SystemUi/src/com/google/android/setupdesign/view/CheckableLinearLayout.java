package com.google.android.setupdesign.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CheckableLinearLayout extends LinearLayout implements Checkable {
    public boolean checked;

    public CheckableLinearLayout(Context context) {
        super(context);
        this.checked = false;
        setFocusable(true);
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.checked;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        return this.checked ? LinearLayout.mergeDrawableStates(super.onCreateDrawableState(i + 1), new int[]{R.attr.state_checked}) : super.onCreateDrawableState(i);
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        this.checked = z;
        refreshDrawableState();
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!isChecked());
    }

    public CheckableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.checked = false;
        setFocusable(true);
    }

    public CheckableLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.checked = false;
        setFocusable(true);
    }

    public CheckableLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.checked = false;
        setFocusable(true);
    }
}
