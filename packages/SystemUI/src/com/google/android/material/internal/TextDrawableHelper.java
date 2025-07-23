package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TextDrawableHelper {
    public final WeakReference delegate;
    public TextAppearance textAppearance;
    public float textWidth;
    public final TextPaint textPaint = new TextPaint(1);
    public final AnonymousClass1 fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.internal.TextDrawableHelper.1
        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public final void onFontRetrievalFailed(int i) {
            TextDrawableHelper textDrawableHelper = TextDrawableHelper.this;
            textDrawableHelper.textSizeDirty = true;
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
            textDrawableHelper.textSizeDirty = true;
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) textDrawableHelper.delegate.get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }
    };
    public boolean textSizeDirty = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (!this.textSizeDirty) {
            return this.textWidth;
        }
        this.textWidth = str != null ? this.textPaint.measureText((CharSequence) str, 0, str.length()) : 0.0f;
        if (str != null) {
            Math.abs(this.textPaint.getFontMetrics().ascent);
        }
        this.textSizeDirty = false;
        return this.textWidth;
    }

    public final void setTextAppearance(TextAppearance textAppearance, Context context) {
        if (this.textAppearance != textAppearance) {
            this.textAppearance = textAppearance;
            if (textAppearance != null) {
                TextPaint textPaint = this.textPaint;
                AnonymousClass1 anonymousClass1 = this.fontCallback;
                textAppearance.updateMeasureState(context, textPaint, anonymousClass1);
                TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) this.delegate.get();
                if (textDrawableDelegate != null) {
                    this.textPaint.drawableState = textDrawableDelegate.getState();
                }
                textAppearance.updateDrawState(context, this.textPaint, anonymousClass1);
                this.textSizeDirty = true;
            }
            TextDrawableDelegate textDrawableDelegate2 = (TextDrawableDelegate) this.delegate.get();
            if (textDrawableDelegate2 != null) {
                textDrawableDelegate2.onTextSizeChange();
                textDrawableDelegate2.onStateChange(textDrawableDelegate2.getState());
            }
        }
    }
}
