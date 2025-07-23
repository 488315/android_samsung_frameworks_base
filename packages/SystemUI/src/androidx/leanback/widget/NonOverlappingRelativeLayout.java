package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class NonOverlappingRelativeLayout extends RelativeLayout {
    public NonOverlappingRelativeLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public NonOverlappingRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public NonOverlappingRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
