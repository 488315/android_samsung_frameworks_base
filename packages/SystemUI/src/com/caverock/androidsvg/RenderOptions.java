package com.caverock.androidsvg;

import com.caverock.androidsvg.SVG;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RenderOptions {
    public SVG.Box viewPort;

    public RenderOptions() {
        this.viewPort = null;
    }

    public RenderOptions(RenderOptions renderOptions) {
        this.viewPort = null;
        if (renderOptions == null) {
            return;
        }
        this.viewPort = renderOptions.viewPort;
    }
}
