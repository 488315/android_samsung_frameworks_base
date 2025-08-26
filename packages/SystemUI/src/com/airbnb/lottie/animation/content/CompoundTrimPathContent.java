package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import com.airbnb.lottie.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CompoundTrimPathContent {
    public final List contents = new ArrayList();

    public final void apply(Path path) {
        for (int size = ((ArrayList) this.contents).size() - 1; size >= 0; size--) {
            TrimPathContent trimPathContent = (TrimPathContent) ((ArrayList) this.contents).get(size);
            Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
            if (trimPathContent != null && !trimPathContent.hidden) {
                Utils.applyTrimPathIfNeeded(path, trimPathContent.startAnimation.getFloatValue() / 100.0f, trimPathContent.endAnimation.getFloatValue() / 100.0f, trimPathContent.offsetAnimation.getFloatValue() / 360.0f);
            }
        }
    }
}
