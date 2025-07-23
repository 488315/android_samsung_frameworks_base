package com.samsung.android.wallpaper.colortheme.monet;

import com.android.internal.graphics.cam.Cam;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: ColorScheme.java */
/* loaded from: classes6.dex */
final class TonalSpec {
    private final Chroma chroma;
    private final Hue hue;

    public final List shades(Cam cam) {
        return (List) Arrays.stream(Shades.of((float) this.hue.get(cam), (float) this.chroma.get(cam))).boxed().collect(Collectors.toList());
    }

    public Hue getHue() {
        return this.hue;
    }

    public Chroma getChroma() {
        return this.chroma;
    }

    public TonalSpec(Hue hue, Chroma chroma) {
        this.hue = hue;
        this.chroma = chroma;
    }
}
