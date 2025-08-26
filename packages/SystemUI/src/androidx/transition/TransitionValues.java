package androidx.transition;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n", "    view = ");
        sbM.append(this.view);
        sbM.append("\n");
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sbM.toString(), "    values:");
        for (String str : ((HashMap) this.values).keySet()) {
            strM = strM + "    " + str + ": " + ((HashMap) this.values).get(str) + "\n";
        }
        return strM;
    }

    public TransitionValues(View view) {
        this.view = view;
    }
}
