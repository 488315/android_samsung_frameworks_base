package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialTextView extends AppCompatTextView {
    public MaterialTextView(Context context) {
        this(context, null);
    }

    public final void applyLineHeightFromViewAppearance(int i, Resources.Theme theme) {
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(i, R$styleable.MaterialTextAppearance);
        Context context = getContext();
        int[] iArr = {1, 2};
        int i2 = -1;
        for (int i3 = 0; i3 < 2 && i2 < 0; i3++) {
            i2 = MaterialResources.getDimensionPixelSize(context, obtainStyledAttributes, iArr[i3], -1);
        }
        obtainStyledAttributes.recycle();
        if (i2 >= 0) {
            setLineHeight(i2);
        }
    }

    public final void initialize(AttributeSet attributeSet, int i, int i2) {
        Context context = getContext();
        if (MaterialAttributes.resolveBoolean(context, R.attr.textAppearanceLineHeightEnabled, true)) {
            Resources.Theme theme = context.getTheme();
            int[] iArr = R$styleable.MaterialTextView;
            TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, iArr, i, i2);
            int[] iArr2 = {1, 2};
            int i3 = -1;
            for (int i4 = 0; i4 < 2 && i3 < 0; i4++) {
                i3 = MaterialResources.getDimensionPixelSize(context, obtainStyledAttributes, iArr2[i4], -1);
            }
            obtainStyledAttributes.recycle();
            if (i3 != -1) {
                return;
            }
            TypedArray obtainStyledAttributes2 = theme.obtainStyledAttributes(attributeSet, iArr, i, i2);
            int resourceId = obtainStyledAttributes2.getResourceId(0, -1);
            obtainStyledAttributes2.recycle();
            if (resourceId != -1) {
                applyLineHeightFromViewAppearance(resourceId, theme);
            }
        }
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (MaterialAttributes.resolveBoolean(context, R.attr.textAppearanceLineHeightEnabled, true)) {
            applyLineHeightFromViewAppearance(i, context.getTheme());
        }
    }

    public MaterialTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.textViewStyle);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 0), attributeSet, i);
        initialize(attributeSet, i, 0);
    }

    @Deprecated
    public MaterialTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, i2), attributeSet, i);
        initialize(attributeSet, i, i2);
    }
}
