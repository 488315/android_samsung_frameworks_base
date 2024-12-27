package com.android.systemui.statusbar.phone.knox.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class KnoxStatusBarTextView extends TextView implements DarkIconDispatcher.DarkReceiver {
    public KnoxStatusBarTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        setTextColor(DarkIconDispatcher.getTint(arrayList, this, i));
    }

    public KnoxStatusBarTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public KnoxStatusBarTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ KnoxStatusBarTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public KnoxStatusBarTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
