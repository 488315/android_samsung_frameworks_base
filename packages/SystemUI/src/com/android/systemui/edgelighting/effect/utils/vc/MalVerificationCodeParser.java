package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MalVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public MalVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("ORC/MalVerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, this.mContext.getResources().getStringArray(R.array.verification_code_strong_mal), this.mContext.getResources().getStringArray(R.array.verification_code_weak_mal));
    }
}
