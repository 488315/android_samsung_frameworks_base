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

    public final List shades(Cam sourceColor) {
        double hue = this.hue.get(sourceColor);
        double chroma = this.chroma.get(sourceColor);
        int[] shades = Shades.of((float) hue, (float) chroma);
        return (List) Arrays.stream(shades).boxed().collect(Collectors.toList());
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
