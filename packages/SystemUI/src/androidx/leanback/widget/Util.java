package androidx.leanback.widget;

import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class Util {
    private Util() {
    }

    public static boolean isDescendant(View view, ViewGroup viewGroup) {
        while (view != null) {
            if (view == viewGroup) {
                return true;
            }
            Object parent = view.getParent();
            if (!(parent instanceof View)) {
                return false;
            }
            view = (View) parent;
        }
        return false;
    }
}
