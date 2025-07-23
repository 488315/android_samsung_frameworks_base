package com.google.android.material.oneui.floatingdock.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.AppCompatImageButton;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FloatingMenuItemView extends AppCompatImageButton {
    public FloatingMenuItemView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public /* synthetic */ FloatingMenuItemView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public FloatingMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackground(context.getDrawable(SeslMisc.isLightTheme(context) ? R.drawable.sesl_floating_pane_menu_item_background_ripple : R.drawable.sesl_floating_pane_menu_item_background_ripple_dark));
    }
}
