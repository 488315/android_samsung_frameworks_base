package androidx.fragment.app;

import android.view.View;
import androidx.transition.FragmentTransitionSupport;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentTransition {
    public static final FragmentTransitionCompat21 PLATFORM_IMPL = new FragmentTransitionCompat21();
    public static final FragmentTransitionImpl SUPPORT_IMPL;

    static {
        FragmentTransitionImpl fragmentTransitionImpl;
        try {
            fragmentTransitionImpl = (FragmentTransitionImpl) FragmentTransitionSupport.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            fragmentTransitionImpl = null;
        }
        SUPPORT_IMPL = fragmentTransitionImpl;
    }

    private FragmentTransition() {
    }

    public static void setViewVisibility(ArrayList arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((View) arrayList.get(size)).setVisibility(i);
        }
    }
}
