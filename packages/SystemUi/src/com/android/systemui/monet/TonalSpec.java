package com.android.systemui.monet;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TonalSpec {
    public final Chroma chroma;
    public final Hue hue;

    public TonalSpec(Hue hue, Chroma chroma) {
        this.hue = hue;
        this.chroma = chroma;
    }

    public /* synthetic */ TonalSpec(Hue hue, Chroma chroma, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new HueSource() : hue, chroma);
    }
}
