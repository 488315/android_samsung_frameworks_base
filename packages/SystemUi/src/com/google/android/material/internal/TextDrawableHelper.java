package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TextDrawableHelper {
    public WeakReference delegate;
    public TextAppearance textAppearance;
    public float textWidth;
    public final TextPaint textPaint = new TextPaint(1);
    public final C43051 fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.internal.TextDrawableHelper.1
        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public final void onFontRetrievalFailed(int i) {
            TextDrawableHelper textDrawableHelper = TextDrawableHelper.this;
            textDrawableHelper.textWidthDirty = true;
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) textDrawableHelper.delegate.get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }

        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public final void onFontRetrieved(Typeface typeface, boolean z) {
            if (z) {
                return;
            }
            TextDrawableHelper textDrawableHelper = TextDrawableHelper.this;
            textDrawableHelper.textWidthDirty = true;
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) textDrawableHelper.delegate.get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }
    };
    public boolean textWidthDirty = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TextDrawableDelegate {
        int[] getState();

        boolean onStateChange(int[] iArr);

        void onTextSizeChange();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.material.internal.TextDrawableHelper$1] */
    public TextDrawableHelper(TextDrawableDelegate textDrawableDelegate) {
        this.delegate = new WeakReference(null);
        this.delegate = new WeakReference(textDrawableDelegate);
    }

    public final float getTextWidth(String str) {
        if (!this.textWidthDirty) {
            return this.textWidth;
        }
        float measureText = str == null ? 0.0f : this.textPaint.measureText((CharSequence) str, 0, str.length());
        this.textWidth = measureText;
        this.textWidthDirty = false;
        return measureText;
    }

    public final void setTextAppearance(TextAppearance textAppearance, Context context) {
        if (this.textAppearance != textAppearance) {
            this.textAppearance = textAppearance;
            if (textAppearance != null) {
                TextPaint textPaint = this.textPaint;
                C43051 c43051 = this.fontCallback;
                textAppearance.updateMeasureState(context, textPaint, c43051);
                TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) this.delegate.get();
                if (textDrawableDelegate != null) {
                    textPaint.drawableState = textDrawableDelegate.getState();
                }
                textAppearance.updateDrawState(context, textPaint, c43051);
                this.textWidthDirty = true;
            }
            TextDrawableDelegate textDrawableDelegate2 = (TextDrawableDelegate) this.delegate.get();
            if (textDrawableDelegate2 != null) {
                textDrawableDelegate2.onTextSizeChange();
                textDrawableDelegate2.onStateChange(textDrawableDelegate2.getState());
            }
        }
    }
}
