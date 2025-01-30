package com.google.android.setupdesign.template;

import android.util.Log;
import android.widget.ScrollView;
import com.google.android.setupdesign.view.BottomScrollView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScrollViewScrollHandlingDelegate implements BottomScrollView.BottomScrollListener {
    public ScrollViewScrollHandlingDelegate(RequireScrollMixin requireScrollMixin, ScrollView scrollView) {
        if (scrollView instanceof BottomScrollView) {
            return;
        }
        Log.w("ScrollViewDelegate", "Cannot set non-BottomScrollView. Found=" + scrollView);
    }
}
