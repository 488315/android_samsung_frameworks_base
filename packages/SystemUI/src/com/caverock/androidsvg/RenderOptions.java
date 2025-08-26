package com.caverock.androidsvg;

import com.caverock.androidsvg.SVG;

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
