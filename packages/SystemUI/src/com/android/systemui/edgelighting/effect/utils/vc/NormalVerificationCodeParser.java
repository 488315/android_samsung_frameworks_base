package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
