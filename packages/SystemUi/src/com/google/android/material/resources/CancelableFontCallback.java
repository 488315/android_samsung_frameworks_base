package com.google.android.material.resources;

import android.graphics.Typeface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CancelableFontCallback extends TextAppearanceFontCallback {
    public final ApplyFont applyFont;
    public boolean cancelled;
    public final Typeface fallbackFont;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public CancelableFontCallback(ApplyFont applyFont, Typeface typeface) {
        this.fallbackFont = typeface;
        this.applyFont = applyFont;
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public final void onFontRetrievalFailed(int i) {
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(this.fallbackFont);
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public final void onFontRetrieved(Typeface typeface, boolean z) {
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(typeface);
    }
}
