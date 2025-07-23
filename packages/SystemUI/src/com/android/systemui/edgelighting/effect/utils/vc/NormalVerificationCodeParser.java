package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class NormalVerificationCodeParser extends VerificationCodeParserBase {
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
