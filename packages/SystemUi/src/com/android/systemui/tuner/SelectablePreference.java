package com.android.systemui.tuner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.preference.CheckBoxPreference;
import com.android.systemui.R;
import com.android.systemui.statusbar.ScalingDrawableWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SelectablePreference extends CheckBoxPreference {
    public final int mSize;

    public SelectablePreference(Context context) {
        super(context);
        this.mWidgetLayoutResId = R.layout.preference_widget_radiobutton;
        setSelectable(true);
        this.mSize = (int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics());
    }

    @Override // androidx.preference.Preference
    public final void setIcon(Drawable drawable) {
        super.setIcon(new ScalingDrawableWrapper(drawable, this.mSize / drawable.getIntrinsicWidth()));
    }

    @Override // androidx.preference.Preference
    public String toString() {
        return "";
    }
}
