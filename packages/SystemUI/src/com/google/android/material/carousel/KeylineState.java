package com.google.android.material.carousel;

import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class KeylineState {
    public final float itemSize;
    public final List keylines;

    public final class Keyline {
        public final float loc;
        public final float locOffset;
        public final float mask;
        public final float maskedItemSize;

        public Keyline(float f, float f2, float f3, float f4) {
            this(f, f2, f3, f4, false, 0.0f, 0.0f, 0.0f);
        }

        public Keyline(float f, float f2, float f3, float f4, boolean z, float f5, float f6, float f7) {
            this.loc = f;
            this.locOffset = f2;
            this.mask = f3;
            this.maskedItemSize = f4;
        }
    }

    private KeylineState(float f, List<Keyline> list, int i, int i2) {
        this.itemSize = f;
        this.keylines = Collections.unmodifiableList(list);
    }
}
