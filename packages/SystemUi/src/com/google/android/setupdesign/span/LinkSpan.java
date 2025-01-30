package com.google.android.setupdesign.span;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LinkSpan extends ClickableSpan {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnLinkClickListener {
    }

    public LinkSpan(String str) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        if (view instanceof OnLinkClickListener) {
            ((OnLinkClickListener) view).getClass();
        }
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
        }
        Log.w("LinkSpan", "Dropping click event. No listener attached.");
        if (view instanceof TextView) {
            CharSequence text = ((TextView) view).getText();
            if (text instanceof Spannable) {
                Selection.setSelection((Spannable) text, 0);
            }
        }
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setUnderlineText(false);
    }
}
