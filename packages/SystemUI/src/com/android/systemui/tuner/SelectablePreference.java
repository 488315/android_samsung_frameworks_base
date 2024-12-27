package com.android.systemui.tuner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.preference.CheckBoxPreference;
import com.android.systemui.R;
import com.android.systemui.statusbar.ScalingDrawableWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
