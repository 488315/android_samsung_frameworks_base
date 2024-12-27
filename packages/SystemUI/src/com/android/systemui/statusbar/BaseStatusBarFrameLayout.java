package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class BaseStatusBarFrameLayout extends FrameLayout implements StatusIconDisplayable {
    public BaseStatusBarFrameLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    public BaseStatusBarFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ BaseStatusBarFrameLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public BaseStatusBarFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
