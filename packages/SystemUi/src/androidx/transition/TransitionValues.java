package androidx.transition;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TransitionValues {
    public final View view;
    public final Map values = new HashMap();
    public final ArrayList mTargetedTransitions = new ArrayList();

    @Deprecated
    public TransitionValues() {
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof TransitionValues)) {
            return false;
        }
        TransitionValues transitionValues = (TransitionValues) obj;
        return this.view == transitionValues.view && this.values.equals(transitionValues.values);
    }

    public final int hashCode() {
        return this.values.hashCode() + (this.view.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder m2m = AbstractC0000x2c234b15.m2m("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n", "    view = ");
        m2m.append(this.view);
        m2m.append("\n");
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m2m.toString(), "    values:");
        Map map = this.values;
        for (String str : ((HashMap) map).keySet()) {
            m14m = m14m + "    " + str + ": " + ((HashMap) map).get(str) + "\n";
        }
        return m14m;
    }

    public TransitionValues(View view) {
        this.view = view;
    }
}
