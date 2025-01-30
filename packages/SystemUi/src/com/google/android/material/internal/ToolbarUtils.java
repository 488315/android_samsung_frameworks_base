package com.google.android.material.internal;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ToolbarUtils {
    public static final C43071 VIEW_TOP_COMPARATOR = new Comparator() { // from class: com.google.android.material.internal.ToolbarUtils.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((View) obj).getTop() - ((View) obj2).getTop();
        }
    };

    private ToolbarUtils() {
    }

    public static List getTextViewsWithText(Toolbar toolbar, CharSequence charSequence) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View childAt = toolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (TextUtils.equals(textView.getText(), charSequence)) {
                    arrayList.add(textView);
                }
            }
        }
        return arrayList;
    }
}
