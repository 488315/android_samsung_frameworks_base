package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import com.airbnb.lottie.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CompoundTrimPathContent {
    public final List contents = new ArrayList();

    public final void apply(Path path) {
        ArrayList arrayList = (ArrayList) this.contents;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            TrimPathContent trimPathContent = (TrimPathContent) arrayList.get(size);
            Utils.C06051 c06051 = Utils.threadLocalPathMeasure;
            if (trimPathContent != null && !trimPathContent.hidden) {
                Utils.applyTrimPathIfNeeded(path, trimPathContent.startAnimation.getFloatValue() / 100.0f, trimPathContent.endAnimation.getFloatValue() / 100.0f, trimPathContent.offsetAnimation.getFloatValue() / 360.0f);
            }
        }
    }
}
