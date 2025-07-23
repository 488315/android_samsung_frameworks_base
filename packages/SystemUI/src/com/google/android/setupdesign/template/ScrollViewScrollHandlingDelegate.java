package com.google.android.setupdesign.template;

import android.util.Log;
import android.widget.ScrollView;
import com.google.android.setupdesign.view.BottomScrollView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ScrollViewScrollHandlingDelegate implements BottomScrollView.BottomScrollListener {
    public ScrollViewScrollHandlingDelegate(RequireScrollMixin requireScrollMixin, ScrollView scrollView) {
        if (scrollView instanceof BottomScrollView) {
            return;
        }
        Log.w("ScrollViewDelegate", "Cannot set non-BottomScrollView. Found=" + scrollView);
    }
}
