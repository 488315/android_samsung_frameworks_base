package com.android.systemui.tuner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.preference.CheckBoxPreference;
import com.android.systemui.R;
import com.android.systemui.statusbar.ScalingDrawableWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
