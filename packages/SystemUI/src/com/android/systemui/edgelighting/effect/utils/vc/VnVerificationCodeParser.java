package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;
import com.android.systemui.R;

public final class VnVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public VnVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("ORC/VnVerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, this.mContext.getResources().getStringArray(R.array.verification_code_strong_vn), this.mContext.getResources().getStringArray(R.array.verification_code_weak_vn));
    }
}
