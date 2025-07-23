package android.transition;

import android.util.ArrayMap;
import android.view.View;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes4.dex */
public class TransitionValues {
    public View view;
    public final Map<String, Object> values = new ArrayMap();
    final ArrayList<Transition> targetedTransitions = new ArrayList<>();

    @Deprecated
    public TransitionValues() {
    }

    public TransitionValues(View view) {
        this.view = view;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TransitionValues)) {
            return false;
        }
        TransitionValues transitionValues = (TransitionValues) obj;
        return this.view == transitionValues.view && this.values.equals(transitionValues.values);
    }

    public int hashCode() {
        return (this.view.hashCode() * 31) + this.values.hashCode();
    }

    public String toString() {
        String str = (("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.view + ShaderAssembler.NEWLINE) + "    values:";
        for (String str2 : this.values.keySet()) {
            str = str + "    " + str2 + ": " + this.values.get(str2) + ShaderAssembler.NEWLINE;
        }
        return str;
    }
}
