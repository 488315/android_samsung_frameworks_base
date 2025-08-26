package com.android.systemui.communal.widgets;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class RoundedCornerEnforcement {
    public static final RoundedCornerEnforcement INSTANCE = new RoundedCornerEnforcement();

    private RoundedCornerEnforcement() {
    }

    public static void accumulateViewsWithId(View view, List list) {
        if (view.getId() == R.id.background) {
            ((ArrayList) list).add(view);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                accumulateViewsWithId(viewGroup.getChildAt(i), list);
            }
        }
    }

    public static View findUndefinedBackground(View view) {
        View view2 = null;
        if (view.getVisibility() == 0) {
            if (view.getVisibility() != 0 || (view.willNotDraw() && view.getForeground() == null && view.getBackground() == null)) {
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int childCount = viewGroup.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View viewFindUndefinedBackground = findUndefinedBackground(viewGroup.getChildAt(i));
                        if (viewFindUndefinedBackground != null) {
                            if (view2 == null) {
                                view2 = viewFindUndefinedBackground;
                            }
                        }
                    }
                }
            }
            return view;
        }
        return view2;
    }
}
