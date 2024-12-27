package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;
import com.android.systemui.R;

public final class KorVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public KorVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("ORC/KorVerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCodeWithoutDefault(str, this.mContext.getResources().getStringArray(R.array.verification_code_strong_kor), this.mContext.getResources().getStringArray(R.array.verification_code_weak_kor));
    }
}
