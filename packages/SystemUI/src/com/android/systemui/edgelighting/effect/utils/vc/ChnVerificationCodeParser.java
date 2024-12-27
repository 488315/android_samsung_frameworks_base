package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;

public final class ChnVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;
    public int mCodeBehindStartIndex = -1;
    public int mCodeBehindEndIndex = -1;
    public int mCodeFrontStartIndex = -1;
    public int mCodeFrontEndIndex = -1;

    public ChnVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getVerificationCode(java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 670
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.effect.utils.vc.ChnVerificationCodeParser.getVerificationCode(java.lang.String):java.lang.String");
    }
}
