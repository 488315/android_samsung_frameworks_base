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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LinkSpan extends ClickableSpan {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnLinkClickListener {
        boolean onLinkClick(LinkSpan linkSpan);
    }

    public LinkSpan(String str) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        boolean onLinkClick = view instanceof OnLinkClickListener ? ((OnLinkClickListener) view).onLinkClick(this) : false;
        if (!onLinkClick) {
            for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            }
        }
        if (onLinkClick) {
            view.cancelPendingInputEvents();
        } else {
            Log.w("LinkSpan", "Dropping click event. No listener attached.");
        }
        if (view instanceof TextView) {
            CharSequence text = ((TextView) view).getText();
            if (text instanceof Spannable) {
                Selection.setSelection((Spannable) text, 0);
            }
        }
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setUnderlineText(false);
    }
}
