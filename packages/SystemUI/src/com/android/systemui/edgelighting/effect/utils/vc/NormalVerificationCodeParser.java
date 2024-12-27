package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;

public final class NormalVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public NormalVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("Normal/VerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, new String[0], new String[0]);
    }
}
