package androidx.transition;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
