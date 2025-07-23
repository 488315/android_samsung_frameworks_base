package com.android.systemui.communal.shared.model;

import com.android.systemui.communal.shared.model.SpanValue;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SpanValueKt {
    public static final int toFixed(SpanValue spanValue) {
        if (spanValue instanceof SpanValue.Fixed) {
            return ((SpanValue.Fixed) spanValue).value;
        }
        if (spanValue instanceof SpanValue.Responsive) {
            return RangesKt___RangesKt.coerceIn(((SpanValue.Responsive) spanValue).value * 3, 3, 6);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final int toResponsive(SpanValue spanValue) {
        if (spanValue instanceof SpanValue.Responsive) {
            return ((SpanValue.Responsive) spanValue).value;
        }
        if (!(spanValue instanceof SpanValue.Fixed)) {
            throw new NoWhenBranchMatchedException();
        }
        int i = ((SpanValue.Fixed) spanValue).value / 3;
        if (i > 1) {
            return 1;
        }
        return i;
    }
}
