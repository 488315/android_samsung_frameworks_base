package com.android.systemui.controls.p005ui;

import android.os.VibrationEffect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Vibrations {
    public static final Vibrations INSTANCE = new Vibrations();
    public static final VibrationEffect rangeEdgeEffect;
    public static final VibrationEffect rangeMiddleEffect;

    static {
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        startComposition.addPrimitive(7, 0.5f);
        rangeEdgeEffect = startComposition.compose();
        VibrationEffect.Composition startComposition2 = VibrationEffect.startComposition();
        startComposition2.addPrimitive(7, 0.1f);
        rangeMiddleEffect = startComposition2.compose();
    }

    private Vibrations() {
    }
}
