package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;

/* loaded from: classes2.dex */
public class SecQSDetailContentView extends LinearLayout {
    public SecQSDetailContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setMinimumHeight(((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getDetailContentViewMinHeight(context));
    }
}
