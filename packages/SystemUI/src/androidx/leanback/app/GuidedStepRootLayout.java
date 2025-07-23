package androidx.leanback.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.leanback.widget.Util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class GuidedStepRootLayout extends LinearLayout {
    public GuidedStepRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        View focusSearch = super.focusSearch(view, i);
        if ((i != 17 && i != 66) || Util.isDescendant(focusSearch, this)) {
            return focusSearch;
        }
        getLayoutDirection();
        return view;
    }

    public GuidedStepRootLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
