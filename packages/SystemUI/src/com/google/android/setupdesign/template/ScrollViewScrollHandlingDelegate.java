package com.google.android.setupdesign.template;

import android.util.Log;
import android.widget.ScrollView;
import com.google.android.setupdesign.view.BottomScrollView;

/* loaded from: classes4.dex */
public class ScrollViewScrollHandlingDelegate implements BottomScrollView.BottomScrollListener {
    public ScrollViewScrollHandlingDelegate(RequireScrollMixin requireScrollMixin, ScrollView scrollView) {
        if (scrollView instanceof BottomScrollView) {
            return;
        }
        Log.w("ScrollViewDelegate", "Cannot set non-BottomScrollView. Found=" + scrollView);
    }
}
