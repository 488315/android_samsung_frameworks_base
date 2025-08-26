package com.google.android.setupdesign.span;

import android.content.Context;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class BoldLinkSpan extends LinkSpan {
    static final int BOLD_TEXT_ADJUSTMENT = 300;
    public final Context context;

    public BoldLinkSpan(Context context, String str) {
        super(str);
        this.context = context;
    }

    @Override // com.google.android.setupdesign.span.LinkSpan, android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setFakeBoldText(this.context.getResources().getConfiguration().fontWeightAdjustment == 300);
        textPaint.setUnderlineText(true);
    }
}
