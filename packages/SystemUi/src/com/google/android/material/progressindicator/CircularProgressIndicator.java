package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CircularProgressIndicator extends BaseProgressIndicator {
    public static final /* synthetic */ int $r8$clinit = 0;

    public CircularProgressIndicator(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public final BaseProgressIndicatorSpec createSpec(Context context, AttributeSet attributeSet) {
        return new CircularProgressIndicatorSpec(context, attributeSet);
    }

    public CircularProgressIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 2132019103);
        Context context2 = getContext();
        CircularProgressIndicatorSpec circularProgressIndicatorSpec = (CircularProgressIndicatorSpec) this.spec;
        setIndeterminateDrawable(new IndeterminateDrawable(context2, circularProgressIndicatorSpec, new CircularDrawingDelegate(circularProgressIndicatorSpec), new CircularIndeterminateAnimatorDelegate(circularProgressIndicatorSpec)));
        Context context3 = getContext();
        CircularProgressIndicatorSpec circularProgressIndicatorSpec2 = (CircularProgressIndicatorSpec) this.spec;
        setProgressDrawable(new DeterminateDrawable(context3, circularProgressIndicatorSpec2, new CircularDrawingDelegate(circularProgressIndicatorSpec2)));
    }
}
