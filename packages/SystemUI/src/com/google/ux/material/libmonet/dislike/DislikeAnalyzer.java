package com.google.ux.material.libmonet.dislike;

import com.google.ux.material.libmonet.hct.Hct;

/* loaded from: classes4.dex */
public final class DislikeAnalyzer {
    private DislikeAnalyzer() {
        throw new UnsupportedOperationException();
    }

    public static Hct fixIfDisliked(Hct hct) {
        return (((((double) Math.round(hct.hue)) > 90.0d ? 1 : (((double) Math.round(hct.hue)) == 90.0d ? 0 : -1)) >= 0 && (((double) Math.round(hct.hue)) > 111.0d ? 1 : (((double) Math.round(hct.hue)) == 111.0d ? 0 : -1)) <= 0) && ((((double) Math.round(hct.chroma)) > 16.0d ? 1 : (((double) Math.round(hct.chroma)) == 16.0d ? 0 : -1)) > 0) && (((double) Math.round(hct.tone)) < 65.0d)) ? Hct.from(hct.hue, hct.chroma, 70.0d) : hct;
    }
}
