package androidx.fragment.app;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.PathInterpolator;

/* loaded from: classes.dex */
public class SeslFragmentTransitionHelper {
    public final View mView;

    static {
        new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        new PathInterpolator(0.22f, 0.5f, 0.0f, 1.0f);
    }

    public SeslFragmentTransitionHelper(View view) {
        this.mView = view;
        view.getContext();
        int i = Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
