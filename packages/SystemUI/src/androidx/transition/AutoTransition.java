package androidx.transition;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AutoTransition extends TransitionSet {
    public AutoTransition() {
        init();
    }

    public final void init() {
        setOrdering(1);
        addTransition(new Fade(2));
        addTransition(new ChangeBounds());
        addTransition(new Fade(1));
    }

    public AutoTransition(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
}
