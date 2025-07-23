package com.google.android.material.resources;

import android.graphics.Typeface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CancelableFontCallback extends TextAppearanceFontCallback {
    public final ApplyFont applyFont;
    public boolean cancelled;
    public final Typeface fallbackFont;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public CancelableFontCallback(ApplyFont applyFont, Typeface typeface) {
        this.fallbackFont = typeface;
        this.applyFont = applyFont;
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public final void onFontRetrievalFailed(int i) {
        Typeface typeface = this.fallbackFont;
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(typeface);
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public final void onFontRetrieved(Typeface typeface, boolean z) {
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(typeface);
    }
}
