package com.android.wm.shell.common.pip;

import android.util.Size;

/* loaded from: classes3.dex */
public interface SizeSpecSource {
    default int getOverrideMinEdgeSize() {
        Size overrideMinSize = ((PhoneSizeSpecSource) this).getOverrideMinSize();
        if (overrideMinSize == null) {
            return 0;
        }
        return Math.min(overrideMinSize.getWidth(), overrideMinSize.getHeight());
    }
}
