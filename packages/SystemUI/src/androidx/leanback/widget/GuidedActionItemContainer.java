package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class GuidedActionItemContainer extends NonOverlappingLinearLayoutWithForeground {
    public final boolean mFocusOutAllowed;

    public GuidedActionItemContainer(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        if (this.mFocusOutAllowed || !Util.isDescendant(view, this)) {
            return super.focusSearch(view, i);
        }
        View focusSearch = super.focusSearch(view, i);
        if (Util.isDescendant(focusSearch, this)) {
            return focusSearch;
        }
        return null;
    }

    public GuidedActionItemContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GuidedActionItemContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFocusOutAllowed = true;
    }
}
