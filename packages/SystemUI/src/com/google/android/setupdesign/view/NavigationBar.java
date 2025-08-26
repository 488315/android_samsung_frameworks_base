package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* loaded from: classes4.dex */
public class NavigationBar extends LinearLayout implements View.OnClickListener {
    public NavigationBar(Context context) {
        super(getThemedContext(context));
        init();
    }

    public static Context getThemedContext(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.sudNavBarTheme, android.R.attr.colorForeground, android.R.attr.colorBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId == 0) {
            float[] fArr = new float[3];
            float[] fArr2 = new float[3];
            Color.colorToHSV(typedArrayObtainStyledAttributes.getColor(1, 0), fArr);
            Color.colorToHSV(typedArrayObtainStyledAttributes.getColor(2, 0), fArr2);
            resourceId = fArr[2] > fArr2[2] ? R.style.SudNavBarThemeDark : R.style.SudNavBarThemeLight;
        }
        typedArrayObtainStyledAttributes.recycle();
        return new ContextThemeWrapper(context, resourceId);
    }

    public final void init() {
        if (isInEditMode()) {
            return;
        }
        View.inflate(getContext(), R.layout.sud_navbar_view, this);
    }

    public NavigationBar(Context context, AttributeSet attributeSet) {
        super(getThemedContext(context), attributeSet);
        init();
    }

    public NavigationBar(Context context, AttributeSet attributeSet, int i) {
        super(getThemedContext(context), attributeSet, i);
        init();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
    }
}
