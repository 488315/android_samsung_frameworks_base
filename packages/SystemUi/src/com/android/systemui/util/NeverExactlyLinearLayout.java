package com.android.systemui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NeverExactlyLinearLayout extends LinearLayout {
    public NeverExactlyLinearLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    public static Triple getNonExactlyMeasureSpec(int i) {
        boolean z = View.MeasureSpec.getMode(i) == 1073741824;
        int size = View.MeasureSpec.getSize(i);
        if (z) {
            i = View.MeasureSpec.makeMeasureSpec(size, VideoPlayer.MEDIA_ERROR_SYSTEM);
        }
        return new Triple(Boolean.valueOf(z), Integer.valueOf(i), Integer.valueOf(size));
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Triple nonExactlyMeasureSpec = getNonExactlyMeasureSpec(i);
        boolean booleanValue = ((Boolean) nonExactlyMeasureSpec.component1()).booleanValue();
        int intValue = ((Number) nonExactlyMeasureSpec.component2()).intValue();
        int intValue2 = ((Number) nonExactlyMeasureSpec.component3()).intValue();
        Triple nonExactlyMeasureSpec2 = getNonExactlyMeasureSpec(i2);
        boolean booleanValue2 = ((Boolean) nonExactlyMeasureSpec2.component1()).booleanValue();
        int intValue3 = ((Number) nonExactlyMeasureSpec2.component2()).intValue();
        int intValue4 = ((Number) nonExactlyMeasureSpec2.component3()).intValue();
        super.onMeasure(intValue, intValue3);
        if (booleanValue || booleanValue2) {
            if (!booleanValue) {
                intValue2 = getMeasuredWidth();
            }
            if (!booleanValue2) {
                intValue4 = getMeasuredHeight();
            }
            setMeasuredDimension(intValue2, intValue4);
        }
    }

    public NeverExactlyLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ NeverExactlyLinearLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public NeverExactlyLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
