package androidx.core.view;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public abstract class ViewGroupKt {
    public static final View get(ViewGroup viewGroup, int i) {
        View childAt = viewGroup.getChildAt(i);
        if (childAt != null) {
            return childAt;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index: ", ", Size: ");
        sbM.append(viewGroup.getChildCount());
        throw new IndexOutOfBoundsException(sbM.toString());
    }
}
