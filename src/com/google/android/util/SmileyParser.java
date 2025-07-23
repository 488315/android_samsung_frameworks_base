package com.google.android.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import com.google.android.util.AbstractMessageParser;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SmileyParser extends AbstractMessageParser {
    private SmileyResources mRes;

    public SmileyParser(String str, SmileyResources smileyResources) {
        super(str, true, false, false, false, false, false);
        this.mRes = smileyResources;
    }

    @Override // com.google.android.util.AbstractMessageParser
    protected AbstractMessageParser.Resources getResources() {
        return this.mRes;
    }

    public CharSequence getSpannableString(Context context) {
        int smileyRes;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (getPartCount() == 0) {
            return "";
        }
        ArrayList<AbstractMessageParser.Token> tokens = getPart(0).getTokens();
        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            AbstractMessageParser.Token token = tokens.get(i);
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append((CharSequence) token.getRawText());
            if (token.getType() == AbstractMessageParser.Token.Type.SMILEY && (smileyRes = this.mRes.getSmileyRes(token.getRawText())) != -1) {
                spannableStringBuilder.setSpan(new ImageSpan(context, smileyRes), length, spannableStringBuilder.length(), 33);
            }
        }
        return spannableStringBuilder;
    }
}
