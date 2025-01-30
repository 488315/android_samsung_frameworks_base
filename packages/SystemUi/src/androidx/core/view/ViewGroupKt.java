package androidx.core.view;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ViewGroupKt {
    public static final View get(ViewGroup viewGroup, int i) {
        View childAt = viewGroup.getChildAt(i);
        if (childAt != null) {
            return childAt;
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("Index: ", i, ", Size: ");
        m1m.append(viewGroup.getChildCount());
        throw new IndexOutOfBoundsException(m1m.toString());
    }
}
