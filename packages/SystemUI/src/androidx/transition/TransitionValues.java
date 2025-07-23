package androidx.transition;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TransitionValues {
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
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n", "    view = ");
        m.append(this.view);
        m.append("\n");
        String m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m.toString(), "    values:");
        for (String str : ((HashMap) this.values).keySet()) {
            m2 = m2 + "    " + str + ": " + ((HashMap) this.values).get(str) + "\n";
        }
        return m2;
    }

    public TransitionValues(View view) {
        this.view = view;
    }
}
