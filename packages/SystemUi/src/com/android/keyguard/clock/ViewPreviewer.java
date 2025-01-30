package com.android.keyguard.clock;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewPreviewer {
    public final Handler mMainHandler = new Handler(Looper.getMainLooper());

    public static void dispatchVisibilityAggregated(View view, boolean z) {
        boolean z2 = view.getVisibility() == 0;
        if (z2 || !z) {
            view.onVisibilityAggregated(z);
        }
        if (view instanceof ViewGroup) {
            boolean z3 = z2 && z;
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                dispatchVisibilityAggregated(viewGroup.getChildAt(i), z3);
            }
        }
    }
}
