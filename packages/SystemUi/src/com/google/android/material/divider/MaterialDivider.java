package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialDivider extends View {
    public int color;
    public final MaterialShapeDrawable dividerDrawable;
    public final int insetEnd;
    public final int insetStart;
    public final int thickness;

    public MaterialDivider(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int width;
        int i;
        super.onDraw(canvas);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z = ViewCompat.Api17Impl.getLayoutDirection(this) == 1;
        int i2 = z ? this.insetEnd : this.insetStart;
        if (z) {
            width = getWidth();
            i = this.insetStart;
        } else {
            width = getWidth();
            i = this.insetEnd;
        }
        this.dividerDrawable.setBounds(i2, 0, width - i, getBottom() - getTop());
        this.dividerDrawable.draw(canvas);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        int measuredHeight = getMeasuredHeight();
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int i3 = this.thickness;
            if (i3 > 0 && measuredHeight != i3) {
                measuredHeight = i3;
            }
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
    }

    public MaterialDivider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialDividerStyle);
    }

    public MaterialDivider(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019140), attributeSet, i);
        Context context2 = getContext();
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.dividerDrawable = materialShapeDrawable;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.MaterialDivider, i, 2132019140, new int[0]);
        this.thickness = obtainStyledAttributes.getDimensionPixelSize(3, getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
        this.insetStart = obtainStyledAttributes.getDimensionPixelOffset(2, 0);
        this.insetEnd = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
        int defaultColor = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 0).getDefaultColor();
        if (this.color != defaultColor) {
            this.color = defaultColor;
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(defaultColor));
            invalidate();
        }
        obtainStyledAttributes.recycle();
    }
}
