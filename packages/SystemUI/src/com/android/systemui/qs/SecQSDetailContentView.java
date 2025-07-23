package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecQSDetailContentView extends LinearLayout {
    public SecQSDetailContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setMinimumHeight(((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getDetailContentViewMinHeight(context));
    }
}
