package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import com.airbnb.lottie.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class CompoundTrimPathContent {
    private List<TrimPathContent> contents = new ArrayList();

    final void addTrimPath(TrimPathContent trimPathContent) {
        ((ArrayList) this.contents).add(trimPathContent);
    }

    public final void apply(Path path) {
        int size = ((ArrayList) this.contents).size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            TrimPathContent trimPathContent = (TrimPathContent) ((ArrayList) this.contents).get(size);
            int i = Utils.$r8$clinit;
            if (trimPathContent != null && !trimPathContent.isHidden()) {
                Utils.applyTrimPathIfNeeded(path, trimPathContent.getStart().getFloatValue() / 100.0f, trimPathContent.getEnd().getFloatValue() / 100.0f, trimPathContent.getOffset().getFloatValue() / 360.0f);
            }
        }
    }
}
