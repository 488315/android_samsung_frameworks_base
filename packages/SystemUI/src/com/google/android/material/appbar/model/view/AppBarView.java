package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public abstract class AppBarView extends FrameLayout {
    /* JADX WARN: Multi-variable type inference failed */
    public AppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public /* synthetic */ AppBarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public AppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void updateResource(Context context) {
    }
}
