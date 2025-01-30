package androidx.leanback.transition;

import android.content.Context;
import android.transition.Slide;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SlideNoPropagation extends Slide {
    public SlideNoPropagation() {
    }

    @Override // android.transition.Slide
    public final void setSlideEdge(int i) {
        super.setSlideEdge(i);
        setPropagation(null);
    }

    public SlideNoPropagation(int i) {
        super(i);
    }

    public SlideNoPropagation(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
